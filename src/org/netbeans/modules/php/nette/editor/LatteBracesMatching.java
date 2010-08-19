/*
 */
package org.netbeans.modules.php.nette.editor;

import org.netbeans.modules.php.nette.editor.completion.LatteCompletionProvider;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.BadLocationException;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.php.nette.lexer.LatteTokenId;
import org.netbeans.modules.php.nette.lexer.LatteTopTokenId;
import org.netbeans.modules.php.nette.macros.LatteMacro;
import org.netbeans.spi.editor.bracesmatching.BracesMatcher;
import org.netbeans.spi.editor.bracesmatching.MatcherContext;
import org.openide.util.Exceptions;

/**
 *
 * @author Radek Ježdík
 */
public class LatteBracesMatching implements BracesMatcher {

    private MatcherContext context;

    String macroName;					// stores macro name (used in both methods)
    boolean isEndMacro;					// is origin macro end macro? (used in both methods)

    int mStart;							// origin macro start
    int mLength;						// origin macro length

    public LatteBracesMatching(MatcherContext context) {
        this.context = context;
    }

    public int[] findOrigin() throws InterruptedException, BadLocationException {
		// hac
        int searchOffset = context.isSearchingBackward() ? context.getSearchOffset() : context.getSearchOffset() + 1;

        if(MatcherContext.isTaskCanceled())
            return null;													// abort

        TokenHierarchy<String> th = TokenHierarchy.create(context.getDocument().getText(0, context.getDocument().getLength()), LatteTopTokenId.language());
        TokenSequence<LatteTopTokenId> ts = th.tokenSequence(LatteTopTokenId.language());

        ts.move(searchOffset);
        if(ts.moveNext()) {													// moves to token next to caret
            if (context.isSearchingBackward() && ts.offset() + ts.token().length() < searchOffset) {
                //check whether the searched position doesn't overlap the token boundaries
                return null;
            }
            Token<LatteTopTokenId> t = ts.token();
            if(t.id() == LatteTopTokenId.LATTE) {							// process only latte
                if(t.text().charAt(0) != '{') {								// process only macros
                    return null;
                }
                TokenHierarchy<CharSequence> th2 = TokenHierarchy.create(t.text(), LatteTokenId.language());
                TokenSequence<LatteTokenId> ts2 = th2.tokenSequence(LatteTokenId.language());
                ts2.moveStart();
                int i = 0;												// used to check end macro slash position
                while(ts2.moveNext() && i < 3) {
                    Token<LatteTokenId> t2 = ts2.token();
                    // macro has name
                    if(t2.id() == LatteTokenId.MACRO) {								// it has macro name
                        macroName = t2.toString();
                        mStart = ts.offset();
                        mLength = t.length();
                        return new int[] {
                            ts.offset(), ts.offset() + t.length(),                  //whole area
                            ts.offset(), ts.offset() + ts2.offset() + t2.length(),  //left delimiter + macro
                            ts.offset() + t.length() - 1, ts.offset() + t.length()  //right delimiter
                        };
                    }
                    if(t2.id() == LatteTokenId.SLASH && i == 1) {					// it is end macro
                        isEndMacro = true;											// set bool to true!
                    }
                    i++;
                }
                // macro doesn't have name, hi-light just delimiters
                return new int[] {
                    ts.offset(), ts.offset() + t.length(),                          //whole area
                    ts.offset(), ts.offset() + 1,                                   //left delimiter
                    ts.offset() + t.length() - 1, ts.offset() + t.length()          //right delimiter
                };
            }
        }
        
        return null;
    }

    public int[] findMatches() throws InterruptedException, BadLocationException {
        try {
			// hack
            final int searchOffset = context.isSearchingBackward() ? context.getSearchOffset() : context.getSearchOffset() + 1;

            if(MatcherContext.isTaskCanceled()) {
                return new int[] { searchOffset, searchOffset };	// abort -> no hi-light
            }
            
            TokenHierarchy<String> th = TokenHierarchy.create(context.getDocument().getText(0, context.getDocument().getLength()), LatteTopTokenId.language());
            TokenSequence<LatteTopTokenId> ts = th.tokenSequence(LatteTopTokenId.language());

            List<String> friends = new ArrayList<String>();			// stores macro friends (else, elseif)
            List<String> endMacros = new ArrayList<String>();		// stores macro ends
            int embeddedMacros = 0;									// counts nested macros

            ts.move(searchOffset);

            if(!isEndMacro) {										// of not an end macro
                boolean isPair = false;								// is not pair so far (check below)
                for(LatteMacro m : LatteCompletionProvider.macros) {		// checking for pair macro
                    if(m.getMacroName().equals(macroName) && m.isPair()) {
                        isPair = true;								// set isPair bool
                        break;
                    }
                }
                for(String m : LatteCompletionProvider.friendMacros.keySet()) {			// checking for friend macro
                    for(LatteMacro f : LatteCompletionProvider.friendMacros.get(m)) {
                        if(f.getMacroName().equals(macroName) || m.equals(macroName)) {
                            if(!m.equals(macroName)) {
                                if(!friends.contains(m)) {
                                    friends.add(m);
                                }
                            }
                            for(LatteMacro macro : LatteCompletionProvider.friendMacros.get(m)) {
                                if(!friends.contains(macro.getMacroName())) {
                                    friends.add(macro.getMacroName());
                                }
                                if(!friends.contains(macro.getEndMacroName())) {
                                    friends.add(macro.getEndMacroName());
                                }
                            }
                            isPair = true;			// isPair = has friends
                            break;
                        }
                    }
                }
                if(!isPair) {
                    // it is not pair macro, return zero length offset = hack against matching error (red hi-light)
                    return new int[] { searchOffset, searchOffset };
                }
            }
            for(LatteMacro m : LatteCompletionProvider.getMacrosByEnd(macroName)) {
                if(!endMacros.contains(m.getMacroName())) {
                    endMacros.add(m.getMacroName());									// finds end macro
                }
            }
			// moves sequence offset accordingly to start/end macro (start forward, end backward )
            if(!isEndMacro) {
                ts.moveNext();
                if(ts.offset() >= mStart + mLength) {
                    ts.movePrevious();
                }
            } else {
                ts.movePrevious();
                if(ts.offset() < mStart) {
                    ts.moveNext();
                }
            }

			// finds start/friend/end macro
            while(isEndMacro ? ts.movePrevious() : ts.moveNext()) {
                Token<LatteTopTokenId> t = ts.token();
                if(t.id() == LatteTopTokenId.LATTE) {		// process only latte
                    if(t.text().charAt(0) != '{') {			// process only macros (no n:tag, n:attr)
                        continue;
                    }
					// go through inside macro tokens
                    TokenHierarchy<CharSequence> th2 = TokenHierarchy.create(t.text(), LatteTokenId.language());
                    TokenSequence<LatteTokenId> ts2 = th2.tokenSequence(LatteTokenId.language());
                    ts2.moveStart();
					
                    boolean isEndMacro2 = false;					// is parsed macro end macro?
                    int i = 0;										// used for checking end macro slash position
                    while(ts2.moveNext() && i < 3) {
                        Token<LatteTokenId> t2 = ts2.token();
                        if(t2.id() == LatteTokenId.MACRO) {						// macro name token reached
                            if(embeddedMacros == 0) {							// only if we are at the same nesting level
                                if((endMacros.contains(t2.text().toString()) && isEndMacro != isEndMacro2)
                                        || friends.contains(t2.text().toString())) {	// it is start/friend/end macro
                                    return new int[] {
											ts.offset(), ts.offset() + ts2.offset() + t2.length(), // hi-light {macro
											ts.offset() + t.length() - 1, ts.offset() + t.length() // hi-light }
										};
                                }
                            }
							// it is not a macro we want
                            LatteMacro m = LatteCompletionProvider.getMacro(t2.text().toString());
                            if(m != null && m.isPair()) {				// if it is pair macro
                                if(!isEndMacro2) {
                                    embeddedMacros++;					// add nesting level if start macro
                                } else {
                                    embeddedMacros--;					// remove nesting level if end macro
                                }
                            }
                        }
                        if(t2.id() == LatteTokenId.SLASH && i == 1) {			// end macro slash
                            isEndMacro2 = true;									// => it is end macro
                        }
                        i++;
                    }
                }
            }

            if(macroName != null && macroName.equals("block") && !isEndMacro) {	// last {/block} can be ommited
                return new int[] { searchOffset, searchOffset };				// in that case hi-light nothing
            }
        } catch(Exception e) {
            Exceptions.printStackTrace(e);
        }

        return null;							// no matching found => matching error (red hi-light)
    }
}