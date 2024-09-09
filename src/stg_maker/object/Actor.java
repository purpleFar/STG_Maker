/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.object;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Vector;
import stg_maker.GameMaker;
import stg_maker.GameMap;
import stg_maker.Mode;
import stg_maker.flying_line.DrawLineJPanel;
import stg_maker.game.Launcher;
import stg_maker.property.BaseProperty;

/**
 *
 * @author ASUS
 */
public class Actor extends GameObject{
    public Vector<Launcher> launchers;
    public String bulletPath=null;
    public double health = 100;
    public double damage = 1;
    public double touchDamage = 0.1;
    public double speed = 50;
    public Dimension bulletSize;
    public double bulletSpeed = 8;
    public int shootingSpeed = 10;
    public double bulletAngle=48763;//管他什麼數字反正就是代替null
    public String shootSound=null;
    public String linePath;
    
    public Actor(GameMaker parent,GameMap gm) {
        super(parent,gm);
        bulletSize = new Dimension(5,10);
        bulletPath = System.getProperty("user.dir")+"/"+BaseProperty.bulletPath+"/"+"demo.png";
    }
    public Actor(GameMaker parent,GameMap gm,String path,Mode mode) {
        super(parent,gm,path,mode);
        bulletSize = new Dimension(5,10);
        bulletPath = System.getProperty("user.dir")+"/"+BaseProperty.bulletPath+"/"+"demo.png";
    }
}
