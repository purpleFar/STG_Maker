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
import stg_maker.object.GamePlayer;
import static stg_maker.property.ActorProperty.labelName2;

/**
 *
 * @author ASUS
 */
public class PlayerProperty extends ActorProperty{
    public JTextField bulletAngle; 
    public static String[] labelNamePlayer = {"射擊方向(角度)"};
    
    public  PlayerProperty(GameMaker parent, GameMap gm){
        super(parent, gm);
    }
    
    public void setting(){
        super.setting();
        startSpace += ActorProperty.labelName2.length*midSpace;
        for(int i=0;i<labelNamePlayer.length;i++){
            JLabel label = new JLabel(labelNamePlayer[i]+":",JLabel.RIGHT);
            label.setFont(parent.contextFont);
            label.setBounds(0, startSpace+i*midSpace, 180, 35);
            contact.add(label);
        }
        GamePlayer actor = (GamePlayer)(gm.activeOBJ);
        bulletAngle = setJTextField(String.valueOf(actor.bulletAngle), startSpace+0*midSpace); 
        contact.setPreferredSize(new Dimension(0,startSpace+1*midSpace+35));
    }
    
    public void addEvent(){
        super.addEvent();
        addJTextFieldEvent(bulletAngle);
    }
    
    public void update(){
        super.update();
        if(gm.activeOBJ!=null)
        {
            GamePlayer actor = (GamePlayer)(gm.activeOBJ);
            try{
                actor.bulletAngle = Double.parseDouble(bulletAngle.getText());
            }catch(NumberFormatException e){}
        }
    }
}
