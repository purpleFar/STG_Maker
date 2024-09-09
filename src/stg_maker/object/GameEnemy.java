/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import stg_maker.GameMaker;
import stg_maker.GameMap;
import stg_maker.flying_line.DrawLineJPanel;
import stg_maker.flying_line.MyPoint;
import stg_maker.property.BaseProperty;

/**
 *
 * @author ASUS
 */
public class GameEnemy extends Actor{
    public boolean booleanTemp= false;
    public int temp=0;
    public double lineSpeedX=0;
    public double lineSpeedY=0;
    public double lineMoveX=0;
    public double lineMoveY=0;
    public MyPoint p1,p2;
    public int index=0;
    public double count=0;
    public DrawLineJPanel line;
    
    public GameEnemy(GameMaker parent,GameMap gm) {
        super(parent,gm);
        bulletAngle = -90;
        bulletSpeed = 3;
        speed=1;
        shootingSpeed = 100;
        bulletSize = new Dimension(10,20);
        linePath =  System.getProperty("user.dir")+"/"+BaseProperty.linePath+"/"+"就定位demo";
        name = "敵人";
    }
    
    public void shoot2Point(Point p){
        if(!this.launchers.isEmpty())
        {
            Point mp = this.launchers.firstElement().getLocation();
            double angle = -Math.atan2(p.y-mp.y,p.x-mp.x)*180/Math.PI;
            if(angle>-165 && angle<-15){
                bulletAngle = angle;
            }
            else{
                bulletAngle = -90;
            }
        }
    }   
    
}
