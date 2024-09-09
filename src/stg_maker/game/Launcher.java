/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.game;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
import stg_maker.*;
import stg_maker.object.Actor;
import stg_maker.object.GameEnemy;

/**
 *
 * @author ASUS
 */
public class Launcher extends JPanel{
    public GameAP parent;
    public GameMap gm;
    public Actor actor;
    public Point startPoint;
    public Vector<Bullet> bullets;
    public int time;
    public int delay;
    
    public Launcher(GameAP parent,GameMap gm, Actor actor) {
        this.parent = parent;
        this.gm = gm;
        this.actor = actor;
        setOpaque(false);
        bullets = new Vector<Bullet>();
        setBounds(actor.getX()+actor.getWidth()/2,actor.getY(), 0, 0);
        if(actor instanceof GameEnemy){
            setBounds(actor.getX()+actor.getWidth()/2,actor.getY()+actor.getHeight(), 0, 0);
        }
        delay = actor.shootingSpeed;
        time = delay;
    }
    
    public void update(){
        if(!parent.outSide(this)){
            if(!actor.die)
                time-=1;
        }
        if(actor instanceof GameEnemy){
            setBounds(actor.getX()+actor.getWidth()/2,actor.getY()+actor.getHeight(), 0, 0);
        }
        else{
            setBounds(actor.getX()+actor.getWidth()/2, actor.getY(), 0, 0);
        }
        if(time==0)
        {
            Bullet bullet = new Bullet(parent,gm,actor,this);
            bullet.setSpeed(actor.bulletSpeed, actor.bulletAngle);
            bullets.add(bullet);
            gm.add(bullet,0);
            //gm.setComponentZOrder(bullet, 1);
            time = delay;
        }
    }
    
    public void remove(){
    
    }
    
}
