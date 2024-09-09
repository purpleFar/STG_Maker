/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.*;
import java.io.*;
import java.util.Vector;
import javax.swing.*;
import stg_maker.object.*;
import stg_maker.property.*;

/**
 *
 * @author ASUS
 */
public class Project implements Serializable{
            
    public transient GameMaker parent;
    public GameMap curGameMap;
    public String musicFile=null;
    public String savePath=null;
    public String winShow = "You win!";
    public String loseShow = "Gameover";
    public Dimension showHealth;
    public Point showHealthLocation;
    public int delay = 1;
    
    public Project(GameMaker parent){
        this.parent = parent;
        parent.curProject = this;
        showHealth = new Dimension(80,35);
        showHealthLocation = new Point(0,0);
        setGameProperty(parent);
        curGameMap = new GameMap(parent,"map"); 
    }
    
    public void setGameProperty(GameMaker parent){
        GameProperty gameProperty = new GameProperty(parent,null);
        gameProperty.setParent(parent, null);
        parent.propertyTP.addTab(gameProperty.typeName, gameProperty);
        gameProperty.updatePropertySpace();
    }

}
