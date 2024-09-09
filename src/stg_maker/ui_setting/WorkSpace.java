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
public class WorkSpace extends JPanel{
    GameMaker parent;
    GridBagConstraints gbc;
    public WorkSpace(GameMaker parent){
        super();
        this.parent = parent;
        setting3();
        
    }
    
    void setting(){
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH ;//元件填滿方格
        gbc.insets = new Insets(3,3,3,3); //元件與格線距離

        addComponent(parent.jTreeSP,0,0,1,1,0,0);
        addComponent(parent.objectList.sp,0,1,1,1,0,0);
        addComponent(parent.drawSp,1,0,100,2,0,0);
        addComponent(parent.propertySp,101,0,1,2,0,0);
    }
    void setting2(){
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(parent.jTreeSP, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(parent.objectList.sp))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(parent.drawSp, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15)
                .addComponent(parent.propertySp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(parent.propertySp, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(parent.jTreeSP, GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(parent.objectList.sp, GroupLayout.PREFERRED_SIZE, 411, GroupLayout.PREFERRED_SIZE))
                    .addComponent(parent.drawSp, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }
    
    void setting3(){
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(parent.objectList.sp,GroupLayout.PREFERRED_SIZE, 170,GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(parent.drawSp, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15)
                .addComponent(parent.propertySp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(parent.objectList.sp))
                    .addComponent(parent.propertySp, GroupLayout.Alignment.TRAILING)
                    .addComponent(parent.drawSp, GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
    }
        
    public void addComponent(Component c, int row, int column, int width, int height, int weightx, int weighty) {
        gbc.gridx = row;
        gbc.gridy = column;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        add(c,gbc);
    }
}
