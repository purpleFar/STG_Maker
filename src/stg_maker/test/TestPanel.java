/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.test;

import java.awt.Font;
import javax.swing.*;
import javax.swing.border.*;

/**
 *
 * @author ASUS
 */
public class TestPanel extends JPanel {
    Font contextFont = new Font("新細明體", 0, 24);
    /**
     * Creates new form NewJPanel
     */
    public TestPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane2 = new JScrollPane();
        jTree = new JTree();
        jScrollPane3 = new JScrollPane();
        jList1 = new JList<>();
        jTabbedPane3 = new JTabbedPane();
        jScrollPane1 = new JScrollPane();
        jScrollPane4 = new JScrollPane();
        jPanel2 = new JPanel();
        jTabbedPane1 = new JTabbedPane();
        jScrollPane5 = new JScrollPane();

        jTree.setFont(contextFont); // NOI18N
        jScrollPane2.setViewportView(jTree);

        jList1.setFont(contextFont); // NOI18N
        jList1.setModel(new AbstractListModel<String>() {
            String[] strings = { "敵機", "道具", "障礙物", "Boss" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jList1);

        jTabbedPane3.setFont(contextFont); // NOI18N
        jTabbedPane3.addTab("tab1", jScrollPane1);
        jTabbedPane3.addTab("tab2", jScrollPane4);

        jPanel2.setBorder(BorderFactory.createTitledBorder(null, "物件屬性", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new java.awt.Font("微軟正黑體", 0, 24))); // NOI18N

        jTabbedPane1.setFont(contextFont); // NOI18N
        jTabbedPane1.addTab("tab1", jScrollPane5);

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, GroupLayout.Alignment.TRAILING)
        );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane3, GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 411, GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTabbedPane3, GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
    }// </editor-fold>                        


    // Variables declaration - do not modify                     
    private JList<String> jList1;
    private JPanel jPanel2;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JScrollPane jScrollPane4;
    private JScrollPane jScrollPane5;
    private JTabbedPane jTabbedPane1;
    private JTabbedPane jTabbedPane3;
    private JTree jTree;
    // End of variables declaration                   
}
