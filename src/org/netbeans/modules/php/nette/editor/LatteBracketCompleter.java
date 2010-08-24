
package org.netbeans.modules.php.nette.editor;

import java.util.ArrayList;
import java.util.List;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.csl.api.KeystrokeHandler;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.php.nette.lexer.LatteTokenId;
import org.netbeans.modules.php.nette.lexer.LatteTopTokenId;

/**
 * TODO: when text selected encapsulate it with " ' etc
 * @author Radek Ježdík
 */
public class LatteBracketCompleter implements KeystrokeHandler {

	public boolean beforeCharInserted(Document doc, int i, JTextComponent jtc, char ch)
			throws BadLocationException {
		if(!isCompletable(ch))
			return false;

		TokenSequence<LatteTokenId> ts = getLatteSequence(doc, i);

		if(ts == null) {
			return false;
		}

		if(ts.token().id() == LatteTokenId.STRING) {
			return false;
		}

		doc.insertString(i, ch + "" + getMatching(ch), null) ;
		jtc.getCaret().setDot(i + 1);
		return true;
	}

	public boolean charBackspaced(Document doc, int i, JTextComponent jtc, char ch)
			throws BadLocationException {
		if(!isCompletable(ch))
			return false;

		TokenSequence<LatteTokenId> ts = getLatteSequence(doc, i);

		if(ts == null) {
			return false;
		}

		if(ts.token().id() == LatteTokenId.STRING) {
			if(doc.getText(i-1, 1).charAt(0) == '\\') {
				return false;
			}
		}

		if(doc.getText(i, 1).charAt(0) == getMatching(ch)) {
			doc.remove(i, 1);
			return true;
		}
		return false;
	}

	public boolean afterCharInserted(Document doc, int i, JTextComponent jtc, char ch)
			throws BadLocationException {
		return false;
	}

	public int beforeBreak(Document doc, int i, JTextComponent jtc)
			throws BadLocationException {
		return -1;
	}

	public OffsetRange findMatching(Document doc, int i) {
		return OffsetRange.NONE;
	}

	public List<OffsetRange> findLogicalRanges(ParserResult pr, int i) {
		return new ArrayList<OffsetRange>();
	}

	public int getNextWordOffset(Document doc, int i, boolean bln) {
		return -1;
	}

	private boolean isCompletable(char ch) {
		switch(ch) {
			//case '{': return '}';
			case '(':
			case '[':
			case '"':
			case '\'':
				return true;

			default:
				return false;
		}
	}

	private char getMatching(char ch) {
		switch(ch) {
			//case '{': return '}';
			case '(': return ')';
			case '[': return ']';
			case '"': return '"';
			case '\'': return '\'';
			
			default:
				return ch;
		}
	}

	private TokenSequence<LatteTokenId> getLatteSequence(Document doc, int pos)
			throws BadLocationException {
        TokenHierarchy<String> th = TokenHierarchy.create(doc.getText(0, doc.getLength()), LatteTopTokenId.language());
        TokenSequence<LatteTopTokenId> ts = th.tokenSequence(LatteTopTokenId.language());

		ts.move(pos);

		if(!ts.moveNext() && !ts.movePrevious()) {
			return null;
		}

		if(ts.token().id() == LatteTopTokenId.LATTE) {
			TokenHierarchy<CharSequence> th2 = TokenHierarchy.create(ts.token().text(), LatteTokenId.language());
			TokenSequence<LatteTokenId> ts2 = th2.tokenSequence(LatteTokenId.language());

			ts2.move(pos - ts.offset());

			if(!ts2.moveNext() && !ts2.movePrevious()) {
				return null;
			}
			
			return ts2;
		}

		return null;
	}

}
