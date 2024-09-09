/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.unit;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
//import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import stg_maker.GameMaker;
import stg_maker.Mode;
import stg_maker.Status;
import stg_maker.object.GameObject;
import stg_maker.object.GamePlayer;

/**
 *
 * @author ASUS
 */
public class MyPopupMenu extends JPopupMenu{
    public GameMaker parent;
    public String labelName[]={"剪下","複製","貼上","刪除"};
    public JMenuItem[] menuItems;
    public Point selfPoint=null;
    int i;
    
    public MyPopupMenu(GameMaker parent){
        super();
        this.parent = parent;
        menuItems = new JMenuItem[labelName.length];
        for(i=0;i<labelName.length;i++){
            menuItems[i] = new JMenuItem();
            menuItems[i].setLabel(labelName[i]);
            menuItems[i].setFont(new Font("新細明體", 0, 24));
            add(menuItems[i]);
        }
        menuItems[2].setEnabled(false);
        this.addPopupMenuListener(new PopupMenuListener(){
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                selfPoint = parent.curProject.curGameMap.getMousePosition();
            }
            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {}
        });
        addEvent();
    }
    public void addEvent(){
        for(i=0;i<labelName.length;i++){
            menuItems[i].addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JMenuItem j = (JMenuItem)(e.getSource());
                    switch(j.getText())
                    {
                        case "剪下":
                            parent.gameObjCopy = parent.curProject.curGameMap.activeOBJ;
                            parent.gameObjCopy.hideOutline(true);
                            parent.curProject.curGameMap.activeOBJ=null;
                            parent.removeObjectProperty();
                            parent.curProject.curGameMap.remove(parent.gameObjCopy);
                            break;
                        case "複製":
                            parent.gameObjCopy = parent.curProject.curGameMap.activeOBJ;
                            break;
                        case "貼上":
                            if(parent.curProject.curGameMap.activeOBJ!=null)
                                parent.curProject.curGameMap.activeOBJ.hideOutline(true);
                            GameObject cloneObj = GameMaker.copy(parent.gameObjCopy);
                            cloneObj.parent = parent;
                            cloneObj.gm = parent.curProject.curGameMap;
                            cloneObj.setLocation(selfPoint);
                            cloneObj.setImg(cloneObj.path);
                            parent.curProject.curGameMap.activeOBJ=cloneObj;
                            parent.addObjectProperty(cloneObj.mode);
                            if(cloneObj.mode==Mode.player)
                            {
                                parent.curProject.curGameMap.remove(parent.curProject.curGameMap.player);
                                parent.curProject.curGameMap.player = (GamePlayer)cloneObj;
                                parent.curProject.curGameMap.add(cloneObj,0);
                            }
                            else
                                parent.curProject.curGameMap.add(cloneObj,1);
                            cloneObj.addEvent(); 
                            parent.curProject.curGameMap.activeOBJ.status = Status.actived;
                            cloneObj.showOutline();
                            break;
                        case "刪除":
                            parent.curProject.curGameMap.activeOBJ.hideOutline(true);
                            parent.curProject.curGameMap.remove(parent.curProject.curGameMap.activeOBJ);
                            parent.curProject.curGameMap.activeOBJ=null;
                            parent.removeObjectProperty();
                            break;
                    }
                    parent.curProject.curGameMap.updateUI();
                }
            });
        }
    }
}
