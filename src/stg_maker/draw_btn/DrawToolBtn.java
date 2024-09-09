/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.draw_btn;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import stg_maker.*;

/**
 *
 * @author ASUS
 */
public class DrawToolBtn extends JButton{
    public GameMaker parent;
    String s;
    public DrawToolBtn(GameMaker parent, ImageIcon icon,String s){
        super(icon);
        this.parent = parent;
        this.s = s;
        addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                action();
            }
        });
    }
    public void action(){
            parent.mode = Mode.valueOf(s);
            parent.objectList.setMode();
            for(DrawToolBtn b: parent.myDrawTool.drawBtns){
                    //b.setBorderPainted(false);
                    b.setBackground(null);
            }
            //this.setBorderPainted(true);
            this.setBackground(Color.LIGHT_GRAY);
    }
    
}
