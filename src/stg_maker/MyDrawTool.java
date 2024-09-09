/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker;

import java.awt.*;
import java.util.Vector;
import javax.swing.*;
import stg_maker.draw_btn.*;

/**
 *
 * @author ASUS
 */
public class MyDrawTool {
    public JToolBar drawToolBar;
    public String[] drawImgName = {"玩家","敵人","背景","道具","障礙物"};
    public String[] modelName = {"player","enemy","background","prop","obstacle"};
    public Vector<DrawToolBtn> drawBtns = null;
    public String path = "type50/";
    
    MyDrawTool(GameMaker parent){
        //繪畫工具列
        drawToolBar = new JToolBar();
        drawToolBar.setOrientation(JToolBar.VERTICAL);
        drawBtns = new Vector<DrawToolBtn>();
        int i=0;
        for(String s: drawImgName){
            parent.imgURL = parent.getClass().getClassLoader().getResource(path+modelName[i]+".png");
            ImageIcon icon = new ImageIcon(parent.imgURL);
            DrawToolBtn btn = new DrawToolBtn(parent, icon, modelName[i++]);
            btn.setToolTipText(s);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            drawToolBar.add(btn);
            drawBtns.add(btn);
        }
        //drawBtns.elementAt(mode.ordinal()).setBorderPainted(true);
        drawBtns.elementAt(parent.mode.ordinal()).setBackground(Color.LIGHT_GRAY);
        parent.mainWin.getContentPane().add(drawToolBar, BorderLayout.WEST);
        
    }
}
