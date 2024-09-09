/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.property;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;
import stg_maker.*;
import stg_maker.unit.MaskLayer;

/**
 *
 * @author ASUS
 */
public class ObjectProperty extends BaseProperty{
    public static String[] labelName = {"寬度","長度","位置X","位置Y"};
    public JTextField wTf,hTf,xTf,yTf;
    public MaskLayer mask=null;

    
    public ObjectProperty(GameMaker parent, GameMap gm){
        super(parent,gm);
        typeName = "物件";
        version = "object";
    }

    public void setting(){
        super.setting();
        startSpace = 85;
        JLabel label = new JLabel(gm.activeOBJ.name,JLabel.CENTER);
        label.setFont(new Font("微软正黑",Font.BOLD,28));
        label.setBounds(0, 20, 400, 35);
        //label.setBorder(BorderFactory.createLineBorder(Color.BLACK,10));
        contact.add(label);
        
        for(int i=0;i<labelName.length;i++){
            label = new JLabel(labelName[i]+":",JLabel.RIGHT);
            label.setFont(parent.contextFont);
            label.setBounds(0, startSpace+i*midSpace, 180, 35);
            //label.setBorder(BorderFactory.createLineBorder(Color.BLACK,10));
            contact.add(label);
        }
        wTf = setJTextField(gm.activeOBJ.getWidth()+"",startSpace+0*midSpace);
        hTf = setJTextField(gm.activeOBJ.getHeight()+"",startSpace+1*midSpace);      
        xTf = setJTextField(gm.activeOBJ.getX()+"",startSpace+2*midSpace);   
        yTf = setJTextField(gm.activeOBJ.getY()+"",startSpace+3*midSpace);   

        contact.setPreferredSize(new Dimension(0,startSpace+3*midSpace+35));
    }
    
    public void addEvent(){
        addJTextFieldEvent(wTf);
        addJTextFieldEvent(hTf);
        addJTextFieldEvent(xTf);
        addJTextFieldEvent(yTf);
                
    }
    
    public void addMask(Component component){
        if(mask==null)
            mask = new MaskLayer(parent);
        mask.put(component, contact); 
    }
    
    public void update(){
        if(gm.activeOBJ!=null)
        {
            try{
                gm.activeOBJ.setBounds(
                        Integer.parseInt(xTf.getText()),
                        Integer.parseInt(yTf.getText()),
                        Integer.parseInt(wTf.getText()),
                        Integer.parseInt(hTf.getText()));
            }catch(NumberFormatException e){}
            gm.activeOBJ.showOutline();
        }
        gm.updateUI();
    }
    
    public void updatePropertySpace(){
        wTf.setText(gm.activeOBJ.getWidth()+"");
        hTf.setText(gm.activeOBJ.getHeight()+"");
        xTf.setText(gm.activeOBJ.getX()+"");
        yTf.setText(gm.activeOBJ.getY()+"");
    }
}
