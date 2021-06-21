/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myPanel;

import Main.CheckoutFrame;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import Main.pinDataset;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.AbstractCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author hp
 */
public class panel3 extends javax.swing.JPanel {

    private static final Font font = new Font("Tahoma", Font.PLAIN, 20);
    public static int total = 0;

    private void load_pintable() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 4) {
//                    int jumlah = (int) getValueAt(row, column);
                    setValueAt(getValueAt(row, column), row, column);

                    return true;
                }
                return false;
            }
        };
        model.addColumn("pKey");
        model.addColumn("No");
        model.addColumn("Nama");
        model.addColumn("Katgegori");
        model.addColumn("Jumlah");
        model.addColumn("Harga");
        int no = 1;
        for (int i = 0; i < pinDataset.getSize(); i++) {
            Object[] temp = pinDataset.getRowValue(i);
            model.addRow(
                    new Object[]{
                        temp[0],
                        no++,
                        temp[1],
                        temp[2],
                        temp[3],
                        temp[4]});
        }

        jTable2.setModel(model);
        jTable2.revalidate();
        jTable2.removeColumn(jTable2.getColumnModel().getColumn(0));

//        Font font = new Font("Tahoma", Font.PLAIN, 20);
        JTableHeader header = jTable2.getTableHeader();
        header.setReorderingAllowed(false);
        header.setFont(font);

        jTable2.getColumnModel().getColumn(3).setCellEditor(new MyCellEditor());

//        JTextField textField = new JTextField();
//        textField.setFont(font);
//        textField.setBorder(new LineBorder(Color.BLACK));
//        DefaultCellEditor dce = new DefaultCellEditor( textField );
//        
//        jTable2.getColumnModel().getColumn(3).setCellEditor(dce);
        jTable2.getTableHeader().setResizingAllowed(false);
        jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(1);
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(400);
        jTable2.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTable2.getColumnModel().getColumn(3).setPreferredWidth(70);
        jTable2.getColumnModel().getColumn(4).setPreferredWidth(90);

        jTable2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        jTable2.setRowSelectionAllowed(true);
        jTable2.setSelectionBackground(new java.awt.Color(255, 120, 0));
        jTable2.setSelectionForeground(new java.awt.Color(255, 255, 255));

//        TableColumnModel colModel = jTable2.getColumnModel();
//        colModel.getColumn(3).setCellEditor(new MyCellEditor());
//        colModel.getColumn(3).setCellRenderer(new MyCellEditor());
    }

    private class MyCellEditor extends AbstractCellEditor implements TableCellEditor {

        private static final long serialVersionUID = 1L;
//        private JFormattedTextField renderer;
        private JFormattedTextField editor;
        private NumberFormat format = NumberFormat.getIntegerInstance();

        public MyCellEditor() {
            format.setGroupingUsed(false);
//            renderer = new JFormattedTextField(format);
//            renderer.setBorder(null);
            editor = new JFormattedTextField(format);
            editor.setFont(font);
            editor.setBorder(new LineBorder(Color.BLACK));
        }

//        @Override
//        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//            renderer.setValue(value);
//            return renderer;
//        }
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
//            int temp = String.valueOf(value).length();
            editor.setValue(value);
            return editor;
        }

        @Override
        public boolean stopCellEditing() {
            try {
                int selectedrow = jTable2.getSelectedRow();
                editor.commitEdit();
                Object[] temp = pinDataset.getRowValue(selectedrow);
                temp[3] = editor.getValue();
                pinDataset.setRowValueAt(selectedrow, temp);
                int jml = Math.toIntExact((long) temp[3]);

                int total2 = total;
                total = total - total2 + ((int) temp[4] * jml);
                if (pinDataset.hargalist.isEmpty()) {
                    pinDataset.hargalist.put((int) temp[0], total);
                    pinDataset.hargatotal = pinDataset.hargatotal + pinDataset.hargalist.get(temp[0]);
                    totaltxtField.setText(String.format("%,d", pinDataset.hargatotal));
                } else {
                    if (pinDataset.hargalist.containsKey((int) temp[0])) {
                        pinDataset.hargatotal = pinDataset.hargatotal - pinDataset.hargalist.get(temp[0]);
                        pinDataset.hargalist.replace((int) temp[0], total);
                        pinDataset.hargatotal = pinDataset.hargatotal + pinDataset.hargalist.get(temp[0]);
                        totaltxtField.setText(String.format("%,d", pinDataset.hargatotal));
                    } else {

                        pinDataset.hargalist.put((int) temp[0], total);
                        pinDataset.hargatotal = pinDataset.hargatotal + pinDataset.hargalist.get(temp[0]);
                        totaltxtField.setText(String.format("%,d", pinDataset.hargatotal));
                    }
                }

            } catch (ParseException e) {
                return false;
            }
            return super.stopCellEditing();
        }

        @Override
        public Object getCellEditorValue() {
            return editor.getValue();
        }
    }

    /**
     * Creates new form panel3
     */
    public panel3() {
        initComponents();
        totaltxtField.setText(String.format("%,d", pinDataset.hargatotal));
        load_pintable();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        checkoutButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        totaltxtField = new javax.swing.JLabel();
        deleteButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 153, 153));
        setLayout(new java.awt.GridBagLayout());

        jTable2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable2.setRowHeight(50);
        jTable2.setSelectionBackground(new java.awt.Color(255, 153, 0));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 450;
        gridBagConstraints.ipady = 25;
        add(jScrollPane2, gridBagConstraints);

        checkoutButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\hp\\Documents\\JavaApplication2\\icons8-question-mark-32.png")); // NOI18N
        checkoutButton.setText("Check Out");
        checkoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkoutButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        add(checkoutButton, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Total : ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 40, 5, 0);
        add(jLabel1, gridBagConstraints);

        totaltxtField.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        totaltxtField.setForeground(new java.awt.Color(255, 255, 255));
        totaltxtField.setText("jLabel2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 120, 5, 0);
        add(totaltxtField, gridBagConstraints);

        deleteButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\hp\\Documents\\JavaApplication2\\icons8-question-mark-32.png")); // NOI18N
        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 145);
        add(deleteButton, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked

    }//GEN-LAST:event_jTable2MouseClicked

    private void checkoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkoutButtonActionPerformed
        boolean status = false;
        for (int i = 0; i < pinDataset.getSize(); i++) {
            Object[] temp = pinDataset.getRowValue(i);

            if (temp[3] instanceof Integer) {
                if ((int) temp[3] <= 0) {
                    status = true;
                    System.out.println(status);
                }
            }
            if (temp[3] instanceof Long) {
                if ((long) temp[3] <= 0) {
                    status = true;
                    System.out.println(status);
                }
            }

        }
        if (pinDataset.getSize() <= 0) {
            JOptionPane.showMessageDialog(null, "Kosong");
        } else if (status) {
            JOptionPane.showMessageDialog(null, "Jumlah barang harus lebih besar dari 0");
        } else {
            Frame fm = new CheckoutFrame();
            fm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    load_pintable();
                    totaltxtField.setText(String.format("%,d", pinDataset.hargatotal));
                }
            });
            fm.show();
        }

    }//GEN-LAST:event_checkoutButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        int selectedrow = jTable2.getSelectedRow();
        pinDataset.deleteRowValueAt(selectedrow);
        load_pintable();
    }//GEN-LAST:event_deleteButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton checkoutButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel totaltxtField;
    // End of variables declaration//GEN-END:variables
}
