/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.property;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import javax.swing.*;
import stg_maker.*;

/**
 *
 * @author ASUS
 */
public class BaseProperty extends JScrollPane{
    public transient GameMaker parent;
    public transient GameMap gm;
    public static String soundPath="sound";
    public static String backgroundPath="backgroundPanel";
    public static String boomPath="boom";
    public static String bulletPath="bullet";
    public static String linePath="line";
    public String typeName;
    public String version;
    public ArrayList boxs;
    public JPanel contact;
    int startSpace = 25,
        midSpace = 60;

    public BaseProperty(GameMaker parent, GameMap gm){
        super();
        setParent(parent,gm);
        contact = new JPanel();
        contact.setLayout(null);
        //contact.setPreferredSize(new Dimension(0,1000));
        contact.setBackground(Color.white);
        setViewportView(contact);
        boxs = new ArrayList();
        setting();
        addEvent();
        this.getVerticalScrollBar().setUnitIncrement(20);
    }
    
    public void setParent(GameMaker parent,GameMap gm){
        this.parent = parent;
        this.gm = gm;
    }   
    
    public void setting(){
    
    }
    
    public void addEvent(){
    
    }
    
    public JTextField setJTextField(String s,int y){
        JTextField textField = new JTextField(s);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(parent.contextFont);
        textField.setBounds(200, y, 160, 35);
        contact.add(textField); 
        return textField;
    }
    
    public void addJTextFieldEvent(JTextField textField){
        textField.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e) {
            }
            @Override
            public void focusLost(FocusEvent e) {
                update();
            }
        });
        textField.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                update();
                textField.setFocusable(false);
                textField.setFocusable(true);
            }
        });    
    }
    
    public JComboBox setJComboBox(String s, int y, String path){
        JComboBox box = new JComboBox();
        box.addItem("無");
        File file = new File(System.getProperty("user.dir")+"/"+s+"/");
        String[] files = file.list();
        if(files!=null)
        {
            for (String file2 : files) {
                box.addItem(file2);
            }
        }
        box.setBounds(200, y, 160, 35);
        box.setFont(parent.contextFont);
        contact.add(box);
        
        if(path==null)
            box.setSelectedIndex(0);
        else{
            String[] paths = path.split("/");
            String loadStr = paths[paths.length-1];
            box.setSelectedItem(loadStr);            
        }
        return box;
    }
    
    
    public void addJComboBoxEvent(JComboBox box){
        box.addActionListener(new ActionListener(){
             @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
    }
    
    public String JComboBoxUpdate(JComboBox box, String path){
        String s = (String) box.getSelectedItem();
        if(s.equals("無"))
            return null;
        else
            return System.getProperty("user.dir")+"/"+path+"/"+s;
    }
    
    public JButton setJButton(String s,int y){
        JButton jb = new JButton(s);
        jb.setHorizontalAlignment(JTextField.CENTER);
        jb.setFont(new Font("微軟正黑", Font.BOLD, 20));
        jb.setBounds(200, y, 160, 35);
        contact.add(jb); 
        return jb;
    }
    
    
    public void updatePropertySpace(){
    
    }
    public void update(){
    
    }
    
    @Override
    public void paint(Graphics g) {
        for(Object ob: boxs){
            if(ob!=null){
                JComboBox box = (JComboBox)ob;
                ((JLabel)box.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
            }
        }
        super.paint(g);
    }

}
