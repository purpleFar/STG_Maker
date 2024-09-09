/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.object;

import java.awt.*;
import java.io.Serializable;
import stg_maker.*;

/**
 *
 * @author ASUS
 */
public class GamePlayer extends Actor{
    int x,y;
    
    public GamePlayer(GameMaker parent,GameMap gm){
        super(parent,gm);
        name = "玩家";
        bulletAngle = 90;
        setSize(new Dimension(100,100));
        x = (gm.getPreferredSize().width - getWidth())/2;
        y = gm.getPreferredSize().height - getHeight();
        this.setLocation(x, y);
    }
    public GamePlayer(GameMaker parent, GameMap gm,String path){
        super(parent,gm,path,Mode.player);
        name = "玩家";
        bulletAngle = 90;
        setSize(new Dimension(100,100));
        x = (gm.getPreferredSize().width - getWidth())/2;
        y = gm.getPreferredSize().height - getHeight();
        this.setLocation(x, y);
    }
    
}
