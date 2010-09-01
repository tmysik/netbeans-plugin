/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.modules.php.nette.wizards.newpresenter;

import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public final class NewPresenterVisualPanel1 extends JPanel {

    private DefaultTableModel tableModel = new NetteTableModel();

    private ImageIcon warningIcon = new ImageIcon(getClass().getResource("/org/netbeans/modules/php/nette/resources/warning_icon.png"));

    /** Creates new form NewPresenterVisualPanel1 */
    public NewPresenterVisualPanel1() {
        initComponents();

        warningLabel.setText("");
    }

    @Override
    public String getName() {
        return "Action and render";
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        actionNameText = new javax.swing.JTextField();
        addButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        actionTable = new javax.swing.JTable();
        deleteButton = new javax.swing.JButton();
        warningLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(NewPresenterVisualPanel1.class, "NewPresenterVisualPanel1.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(NewPresenterVisualPanel1.class, "NewPresenterVisualPanel1.jLabel2.text")); // NOI18N

        actionNameText.setText(org.openide.util.NbBundle.getMessage(NewPresenterVisualPanel1.class, "NewPresenterVisualPanel1.actionNameText.text")); // NOI18N
        actionNameText.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                actionNameTextCaretUpdate(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(addButton, org.openide.util.NbBundle.getMessage(NewPresenterVisualPanel1.class, "NewPresenterVisualPanel1.addButton.text")); // NOI18N
        addButton.setEnabled(false);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        actionTable.setModel(tableModel);
        actionTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(actionTable);

        org.openide.awt.Mnemonics.setLocalizedText(deleteButton, org.openide.util.NbBundle.getMessage(NewPresenterVisualPanel1.class, "NewPresenterVisualPanel1.deleteButton.text")); // NOI18N
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(warningLabel, org.openide.util.NbBundle.getMessage(NewPresenterVisualPanel1.class, "NewPresenterVisualPanel1.warningLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(NewPresenterVisualPanel1.class, "NewPresenterVisualPanel1.jLabel3.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(actionNameText, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(warningLabel, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(actionNameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteButton)
                .addGap(4, 4, 4)
                .addComponent(warningLabel))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        if (isValidAction(actionNameText.getText())) {
            tableModel.addRow(new Object[]{actionNameText.getText(), true, false});
            actionNameText.setText("");
            addButton.setEnabled(false);
            hideWarning();
            actionNameText.requestFocus();
        }
    }//GEN-LAST:event_addButtonActionPerformed

    private void actionNameTextCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_actionNameTextCaretUpdate
        if (isValidAction(actionNameText.getText())) {
            addButton.setEnabled(true);
        } else {
            if (actionNameText.getText().trim().isEmpty()) {
                hideWarning();
            }
            addButton.setEnabled(false);
        }
    }//GEN-LAST:event_actionNameTextCaretUpdate

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        int row = actionTable.getSelectedRow();
        if (row != -1) {
            int result = JOptionPane.showConfirmDialog(this, "Do you really want to delete this action row?", "Are you sure?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                tableModel.removeRow(row);
            }
        } else {
            JOptionPane.showMessageDialog(this, "You must select an action row before you can delete it.", "No row selected", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private boolean isValidAction(String action) {
        if (isValidActionFormat(action) && actionNotExists(action)) {
            hideWarning();

            return true;
        }
        
        return false;
    }

    private boolean isValidActionFormat(String action) {
        if (action.trim().matches("^[a-zA-Z0-9][a-zA-Z0-9_]*$")) {
            return true;
        }

        showWarning("Action name is not aplhanumeric string.");

        return false;
    }

    private boolean actionNotExists(String newAction) {
        String oldAction = null;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            oldAction = (String) tableModel.getValueAt(i, 0);
            if (oldAction.equals(newAction)) {
                showWarning("Action with this name already exists.");
                
                return false;
            }
        }

        return true;
    }

    private void showWarning(String warning) {
        warningLabel.setIcon(warningIcon);
        warningLabel.setText(warning);
    }

    private void hideWarning() {
        warningLabel.setIcon(null);
        warningLabel.setText("");
    }

    public Object[] getActions() {
        Object[] actions = new Object[tableModel.getRowCount()];

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            HashMap<String, Object> action = new HashMap<String, Object>();
            
            action.put("name", tableModel.getValueAt(i, 0));
            action.put("action", tableModel.getValueAt(i, 1));
            action.put("render", tableModel.getValueAt(i, 2));

            actions[i] = action;
        }

        return actions;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField actionNameText;
    private javax.swing.JTable actionTable;
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel warningLabel;
    // End of variables declaration//GEN-END:variables
}