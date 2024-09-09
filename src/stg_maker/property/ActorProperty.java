/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.property;

import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import stg_maker.GameMaker;
import stg_maker.GameMap;
import stg_maker.object.Actor;

/**
 *
 * @author ASUS
 */
public class ActorProperty extends ObjectProperty{
    public static String[] labelName2 = {"生命","傷害","碰撞損傷","移動速度","死亡特效","特效持續時間","子彈圖像","子彈大小","子彈速度","射擊間格","射擊音效"};
    public JTextField health;
    public JTextField damage;
    public JTextField touchDamage;
    public JTextField speed;
    public JComboBox boomImage;
    public JTextField boomTime;
    public JComboBox bulletImage;
    public JTextField bulletSize;
    public JTextField bulletSpeed;
    public JTextField shootingSpeed;
    public JComboBox shootSound;
    
    public  ActorProperty(GameMaker parent, GameMap gm){
        super(parent, gm);
        version = "actor";
    }
    public void setting(){
        super.setting();
        startSpace += ObjectProperty.labelName.length*midSpace;
        for(int i=0;i<labelName2.length;i++){
            JLabel label = new JLabel(labelName2[i]+":",JLabel.RIGHT);
            label.setFont(parent.contextFont);
            label.setBounds(0, startSpace+i*midSpace, 180, 35);
            contact.add(label);
        }
        Actor actor = (Actor)(gm.activeOBJ);
        int i=0;
        health = setJTextField(String.valueOf(actor.health), startSpace+i++*midSpace);
        damage = setJTextField(String.valueOf(actor.damage), startSpace+i++*midSpace);
        touchDamage = setJTextField(String.valueOf(actor.touchDamage), startSpace+i++*midSpace);
        speed = setJTextField(String.valueOf(actor.speed), startSpace+i++*midSpace);
        boomImage = setJComboBox(boomPath,startSpace+i++*midSpace, actor.boomPath);
        boomTime = setJTextField(String.valueOf(actor.boomTime), startSpace+i++*midSpace);
        bulletImage = setJComboBox(bulletPath,startSpace+i++*midSpace, actor.bulletPath);
        bulletSize = setJTextField(actor.bulletSize.width+","+actor.bulletSize.height, startSpace+i++*midSpace);
        bulletSpeed = setJTextField(String.valueOf(actor.bulletSpeed), startSpace+i++*midSpace);
        shootingSpeed = setJTextField(String.valueOf(actor.shootingSpeed), startSpace+i++*midSpace); 
        shootSound = setJComboBox(soundPath,startSpace+i*midSpace, actor.shootSound);
        contact.setPreferredSize(new Dimension(0,startSpace+i*midSpace+35));
        boxs.add(boomImage);
        boxs.add(bulletImage);
        boxs.add(shootSound);
    }
    
    public void addEvent(){
        super.addEvent();
        addJTextFieldEvent(health);
        addJTextFieldEvent(damage);
        addJTextFieldEvent(touchDamage);
        addJTextFieldEvent(speed);
        addJComboBoxEvent(boomImage);
        addJTextFieldEvent(boomTime);
        addJComboBoxEvent(bulletImage);
        addJTextFieldEvent(bulletSize);
        addJTextFieldEvent(bulletSpeed);
        addJTextFieldEvent(shootingSpeed);
        addJComboBoxEvent(shootSound);
    }
    
    public void update(){
        super.update();
        if(gm.activeOBJ!=null)
        {
            Actor actor = (Actor)(gm.activeOBJ);
            try{
                actor.health = Double.parseDouble(health.getText());
                actor.damage = Double.parseDouble(damage.getText());
                actor.touchDamage = Double.parseDouble(touchDamage.getText());
                actor.speed = Double.parseDouble(speed.getText());
                actor.boomPath = JComboBoxUpdate(boomImage,boomPath);
                actor.boomTime = Integer.parseInt(boomTime.getText());
                actor.bulletPath = JComboBoxUpdate(bulletImage,bulletPath);
                actor.bulletSize.width = Integer.parseInt(bulletSize.getText().split(",")[0]);
                actor.bulletSize.height = Integer.parseInt(bulletSize.getText().split(",")[1]);
                actor.bulletSpeed = Double.parseDouble(bulletSpeed.getText());
                actor.shootingSpeed = Integer.parseInt(shootingSpeed.getText());
                actor.shootSound = JComboBoxUpdate(shootSound,soundPath);
            }catch(NumberFormatException e){}
        }
    }
}
