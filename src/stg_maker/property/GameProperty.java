/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.property;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import stg_maker.GameMaker;
import stg_maker.GameMap;
import static stg_maker.property.BackgroundProperty.labelName;
import stg_maker.flying_line.FlyingLineJDialog;

/**
 *
 * @author ASUS
 */
public class GameProperty extends BaseProperty{
    public static String[] labelName = {"畫面刷新間格","背景音樂","狀態欄大小","狀態欄位置","新增路線"};
    public static String musicPath = "music";
    public JTextField delay,showHealth,showHealthLocation;
    public JComboBox music;
    public JButton FlyingLine;
    
    public GameProperty(GameMaker parent,GameMap gm){
        super(parent,gm);
        typeName = "遊戲";
    }

    @Override
    public void setting() {
        super.setting(); //To change body of generated methods, choose Tools | Templates.
        for(int i=0;i<labelName.length;i++){
            JLabel label = new JLabel(labelName[i]+":",JLabel.RIGHT);
            label.setFont(parent.contextFont);
            label.setBounds(0, startSpace+i*midSpace, 180, 35);
            contact.add(label);
        }
        int i=0;
        delay = setJTextField(parent.curProject.delay+"", startSpace+i++*midSpace);
        music = setJComboBox(musicPath,  startSpace+i++*midSpace,parent.curProject.musicFile);
        showHealth = setJTextField(parent.curProject.showHealth.width+","+parent.curProject.showHealth.height, startSpace+i++*midSpace);
        showHealthLocation = setJTextField(parent.curProject.showHealthLocation.x+","+parent.curProject.showHealthLocation.y, startSpace+i++*midSpace);
        FlyingLine = setJButton("開啟繪製介面",startSpace+i*midSpace);
        contact.setPreferredSize(new Dimension(0,startSpace+i*midSpace+35));
        boxs.add(music);
    }

    @Override
    public void addEvent() {
        super.addEvent(); //To change body of generated methods, choose Tools | Templates.
        addJTextFieldEvent(delay);
        addJTextFieldEvent(showHealth);
        addJTextFieldEvent(showHealthLocation);
        addJComboBoxEvent(music);
        addJButtonEvent(FlyingLine);
    }

    @Override
    public void update() {
        super.update(); //To change body of generated methods, choose Tools | Templates.
        try{
            parent.curProject.delay = Integer.parseInt(delay.getText());
            parent.curProject.musicFile = JComboBoxUpdate(music,musicPath);
            parent.curProject.showHealth.width = Integer.parseInt(showHealth.getText().split(",")[0]);
            parent.curProject.showHealth.height = Integer.parseInt(showHealth.getText().split(",")[1]);
            parent.curProject.showHealthLocation.x = Integer.parseInt(showHealthLocation.getText().split(",")[0]);
            parent.curProject.showHealthLocation.y = Integer.parseInt(showHealthLocation.getText().split(",")[1]);
        }catch(NumberFormatException e){}
    }
    
    public void addJButtonEvent(JButton jb){
        jb.addActionListener(new ActionListener(){
             @Override
            public void actionPerformed(ActionEvent e) {
                update();
                new FlyingLineJDialog(parent,parent.curProject);
            }
        });
    }
}
