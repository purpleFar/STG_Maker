/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.property;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.event.*;
import javax.swing.text.BadLocationException;
import stg_maker.*;

/**
 *
 * @author ASUS
 */
public class BackgroundProperty extends BaseProperty{
    public static String[] labelName = {"寬度","長度","視窗移動速度","背景樣式"};
    public JTextField wTf,hTf,moveSpeed;
    public JComboBox background;
    
    public BackgroundProperty(GameMaker parent,GameMap gm){
        super(parent,gm);
        typeName = "場景";
    }

    public void setting(){
        for(int i=0;i<labelName.length;i++){
            JLabel label = new JLabel(labelName[i]+":",JLabel.RIGHT);
            label.setFont(parent.contextFont);
            label.setBounds(0, startSpace+i*midSpace, 180, 35);
            contact.add(label);
        }
        
        wTf = setJTextField(gm.getPreferredSize().width+"", startSpace+0*midSpace);
        hTf = setJTextField(gm.getPreferredSize().height+"", startSpace+1*midSpace);
        moveSpeed = setJTextField(String.valueOf(gm.backgroundPanel.moveSpeed), startSpace+2*midSpace); 
        
        background = setJComboBox(backgroundPath,startSpace+3*midSpace, gm.backgroundPanel.path);
        
        contact.setPreferredSize(new Dimension(0,startSpace+3*midSpace+35));
        boxs.add(background);
    }
    
    public void addEvent(){
        addJTextFieldEvent(wTf);
        addJTextFieldEvent(hTf);
        addJComboBoxEvent(background);
    }
    
    public void update(){
        try{
            gm.setPreferredSize(new Dimension(Integer.parseInt(wTf.getText()),Integer.parseInt(hTf.getText())));
            gm.backgroundPanel.setSize(gm.getPreferredSize());
            gm.backgroundPanel.moveSpeed = Double.parseDouble(moveSpeed.getText());
            
            gm.backgroundPanel.path = JComboBoxUpdate(background,backgroundPath);
            gm.backgroundPanel.setImg(gm.backgroundPanel.path);
            gm.updateUI();
        }catch(NumberFormatException e){}
    }
    
    public void updatePropertySpace(){
        wTf.setText(gm.getPreferredSize().width+"");
        hTf.setText(gm.getPreferredSize().height+"");
    }

}
