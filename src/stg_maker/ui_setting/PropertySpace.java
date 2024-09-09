/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.ui_setting;

import java.awt.Color;
import javax.swing.*;
import javax.swing.border.*;
import stg_maker.*;

/**
 *
 * @author ASUS
 */
public class PropertySpace extends JPanel{
    GameMaker parent;
    public PropertySpace(GameMaker parent){
        super();
        this.parent = parent; 
        setting(400);
    }
    void setting(int weight){
        parent.propertyTP.setFont(parent.contextFont);
        TitledBorder tB = BorderFactory.createTitledBorder(null, "屬性", 
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, parent.titleFont);
        setBorder(tB);
        GroupLayout jPanel2Layout = new GroupLayout(this);
        setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(parent.propertyTP, GroupLayout.DEFAULT_SIZE, weight, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(parent.propertyTP, GroupLayout.Alignment.TRAILING)
        );
    }
}
