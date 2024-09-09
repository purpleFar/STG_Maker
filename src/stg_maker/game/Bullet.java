/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import stg_maker.*;
import stg_maker.object.Actor;
import stg_maker.object.GameObject;

/**
 *
 * @author ASUS
 */
public class Bullet extends GameObject{
    public GameAP parent;
    public GameMap gm;
    public Actor actor;
    public Launcher launcher;
    public double speedX=0,speedY=0;
    public Timer time;
    
    public Bullet(GameAP parent,GameMap gm,Actor actor,Launcher launcher){
        super(parent,gm,actor);
        this.parent = parent;
        this.gm = gm;
        this.actor = actor;
        this.launcher = launcher;
        setSize(actor.bulletSize);
        setBackground(Color.red);
        setLocation(launcher.getX()-(getWidth()/2), launcher.getY()-(getHeight()/2));
        orgPoint = getLocation();
        Music shootSound = new Music(actor.shootSound);
        shootSound.loop = false;
        shootSound.run();
    }
    
    public void setSpeed(double speed, double angle){
        if(angle!=48763)
        {
            speedX = Math.cos(angle* Math.PI/180)*speed;
            speedY = Math.sin(angle* Math.PI/180)*-speed;
        }
    }
    
    public void update(){
        moveX+=speedX;
        moveY+=speedY;
        setLocation((int)(orgPoint.x+moveX), (int)(orgPoint.y+moveY));
        if(parent.outSide(this)){
            remove();
        }
    }
    public void remove(){
        launcher.bullets.remove(this);
        gm.remove(this);
    }
}
