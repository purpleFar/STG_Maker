/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.net.URL;
import javax.swing.*;
import stg_maker.*;
import stg_maker.property.*;

/**
 *
 * @author ASUS
 */
public class Outline implements Serializable{
    public transient GameMaker parent;
    public transient GameMap gm;
    public JPanel[] cp;
    public int size=9;
    Point origUpperLeftPoint,origBottomRightPoint;
    
    public Outline(GameMaker parent, GameMap gm){
        this.parent = parent;
        this.gm = gm;
        cp = new JPanel[8];
        for(int i=0;i<8;i++)
        {
            cp[i]=new JPanel();
            cp[i].setSize(size,size);
            cp[i].setBackground(null);
            cp[i].setOpaque(false);
            gm.add(cp[i],0);
            addEvent(cp[i],i);          
        }
    }
    
    public void addEvent(JPanel panel,int i){
        panel.addMouseListener(new MouseAdapter()
        {        
            public void mouseReleased(MouseEvent e)
            {
                gm.activeOBJ.showOutline();
            }      
            public void mousePressed(MouseEvent e)
            {
                BaseProperty baseProperty = (BaseProperty)parent.propertyTP.getSelectedComponent();
                baseProperty.update();
                origUpperLeftPoint = gm.activeOBJ.getLocation();
                origBottomRightPoint = new Point(
                        gm.activeOBJ.getLocation().x + gm.activeOBJ.getWidth(),
                        gm.activeOBJ.getLocation().y + gm.activeOBJ.getHeight()
                );
                gm.activeOBJ.hideOutline(false);
            }
            public void mouseEntered(MouseEvent e)
            {
                switch(i){
                    case 0:
                    case 4:
                        panel.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                        break;
                    case 1:
                    case 5:
                        panel.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                        break;
                    case 2:
                    case 6:
                        panel.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
                        break;
                    case 3:
                    case 7:
                        panel.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
                        break;
                }
            }
        });
        panel.addMouseMotionListener(new MouseAdapter()
        {
            public void mouseDragged(MouseEvent e)
            {
                    int w = gm.activeOBJ.getWidth();
                    int h = gm.activeOBJ.getHeight();
                    Point mousePoint = new Point(
                            e.getXOnScreen()-gm.getLocationOnScreen().x,
                            e.getYOnScreen()-gm.getLocationOnScreen().y
                    );
                    switch(i){
                        case 0:                           
                            gm.activeOBJ.setBounds(
                                    Math.min(mousePoint.x,origBottomRightPoint.x), 
                                    Math.min(mousePoint.y,origBottomRightPoint.y), 
                                    Math.abs(mousePoint.x-origBottomRightPoint.x), 
                                    Math.abs(mousePoint.y-origBottomRightPoint.y));
                            break;
                        case 1:
                            gm.activeOBJ.setBounds(
                                    origUpperLeftPoint.x, 
                                    Math.min(mousePoint.y,origBottomRightPoint.y), 
                                    w, 
                                    Math.abs(mousePoint.y-origBottomRightPoint.y));
                            break;
                        case 2:
                            gm.activeOBJ.setBounds(
                                    Math.min(mousePoint.x,origUpperLeftPoint.x), 
                                    Math.min(mousePoint.y,origBottomRightPoint.y), 
                                    Math.abs(mousePoint.x-origUpperLeftPoint.x), 
                                    Math.abs(mousePoint.y-origBottomRightPoint.y));
                            break;
                        case 3:
                            gm.activeOBJ.setBounds(
                                    Math.min(mousePoint.x,origUpperLeftPoint.x), 
                                    origUpperLeftPoint.y, 
                                    Math.abs(mousePoint.x-origUpperLeftPoint.x), 
                                    h);
                            break;
                        case 4:
                            gm.activeOBJ.setBounds(
                                    Math.min(mousePoint.x,origUpperLeftPoint.x), 
                                    Math.min(mousePoint.y,origUpperLeftPoint.y), 
                                    Math.abs(mousePoint.x-origUpperLeftPoint.x), 
                                    Math.abs(mousePoint.y-origUpperLeftPoint.y));
                            break;
                        case 5:
                            gm.activeOBJ.setBounds(
                                    origUpperLeftPoint.x, 
                                    Math.min(mousePoint.y,origUpperLeftPoint.y), 
                                    w, 
                                    Math.abs(mousePoint.y-origUpperLeftPoint.y));
                            break;
                        case 6:
                            gm.activeOBJ.setBounds(
                                    Math.min(mousePoint.x,origBottomRightPoint.x), 
                                    Math.min(mousePoint.y,origUpperLeftPoint.y), 
                                    Math.abs(mousePoint.x-origBottomRightPoint.x), 
                                    Math.abs(mousePoint.y-origUpperLeftPoint.y));
                            break;
                        case 7:
                            gm.activeOBJ.setBounds(
                                    Math.min(mousePoint.x,origBottomRightPoint.x), 
                                    origUpperLeftPoint.y, 
                                    Math.abs(mousePoint.x-origBottomRightPoint.x), 
                                    h);
                            break;
                    }
                    parent.objectProperty.updatePropertySpace();
                    gm.updateUI();
            }
        });
    }
    
    public void setLocation(GameObject panel){
        Point op;
        op = panel.getLocation();
        int w = panel.getWidth();
        int h = panel.getHeight();

        gm.draw(op.x-size/2, op.y-size/2, w+size, h+size,true);
        for(int i=0;i<8;i++)
        {            
            cp[i].setVisible(true);
            switch(i)
            {
                case 0:
                    cp[i].setLocation(op.x-size, op.y-size);
                    break;
                case 1:
                    cp[i].setLocation(op.x+(w-size)/2, op.y-size);
                    break;
                case 2:
                    cp[i].setLocation(op.x+w, op.y-size);
                    break;
                case 3:
                    cp[i].setLocation(op.x+w, op.y+(h-size)/2);
                    break;
                case 4:
                    cp[i].setLocation(op.x+w, op.y+h);
                    break;
                case 5:
                    cp[i].setLocation(op.x+(w-size)/2, op.y+h);
                    break;
                case 6:
                    cp[i].setLocation(op.x-size, op.y+h);
                    break;
                case 7:
                    cp[i].setLocation(op.x-size, op.y+(h-size)/2);
                    break;
            }
        }
    }
}
