/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.ui_setting;

import java.awt.*;
import javax.swing.*;
import stg_maker.*;
/**
 *
 * @author ASUS
 */
public class DrawSpace  extends JPanel{
    GameMaker parent;
    GridBagConstraints gbc;
    public DrawSpace(GameMaker parent){
        super();
        this.parent = parent;
        Setting(750);       
    }
    
    void Setting(int weight){
        GroupLayout jPanel1Layout = new GroupLayout(this);
        parent.drawTP.setFont(parent.contextFont);
        setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(parent.drawTP, GroupLayout.DEFAULT_SIZE, weight, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(parent.drawTP)
        );
    }
}
