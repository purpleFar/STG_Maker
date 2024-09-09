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
import stg_maker.object.GameEnemy;
import stg_maker.object.GamePlayer;
import static stg_maker.property.PlayerProperty.labelNamePlayer;

/**
 *
 * @author ASUS
 */
public class EnemyProperty extends ActorProperty{
    public JComboBox line;
    public static String[] labelNameEnemy = {"行徑路線"};
    
    public EnemyProperty(GameMaker parent, GameMap gm){
        super(parent, gm);
    }
    
    public void setting(){
        super.setting();
        startSpace += ActorProperty.labelName2.length*midSpace;
        for(int i=0;i<labelNameEnemy.length;i++){
            JLabel label = new JLabel(labelNameEnemy[i]+":",JLabel.RIGHT);
            label.setFont(parent.contextFont);
            label.setBounds(0, startSpace+i*midSpace, 180, 35);
            contact.add(label);
        }
        int i=0;
        GameEnemy actor = (GameEnemy)(gm.activeOBJ);
        line = setJComboBox(linePath,startSpace+i*midSpace, actor.linePath);
        contact.setPreferredSize(new Dimension(0,startSpace+i*midSpace+35));
        boxs.add(line);
    }
    
    public void addEvent(){
        super.addEvent();
        addJComboBoxEvent(line);
    }
    
    public void update(){
        super.update();
        if(gm.activeOBJ!=null)
        {
            GameEnemy actor = (GameEnemy)(gm.activeOBJ);
            try{
                actor.linePath = JComboBoxUpdate(line,linePath);
            }catch(NumberFormatException e){}
        }
    }
}
