/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.unit;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import stg_maker.GameMaker;

/**
 *
 * @author ASUS
 */
public class MaskLayer extends JPanel{
    public GameMaker parent;
    public Point origPoint=null;
    public Component component;
    public JPanel panel;
    
    public MaskLayer(GameMaker parent){
        super();
        this.parent = parent;
        setOpaque(true);
        setBackground(Color.red);
        setLayout(null);
        addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                parent.mainWin.remove(MaskLayer.this);
                remove(component);
                panel.add(component);
                component.setLocation(origPoint);
                parent.mainWin.revalidate();
                System.out.println("已關閉遮罩");
            }
        });
        parent.mainWin.add(this,0);
        parent.mainWin.revalidate();
    }
   
    public void put(Component component, JPanel panel){
        this.component = component;
        System.out.println(component);
        this.panel = panel;
        origPoint = component.getLocation();
        Point panelPoint = component.getLocationOnScreen();
        Point selfPoint = this.getLocationOnScreen();
        panel.remove(component);
        add(component);
        component.setLocation(panelPoint.x-selfPoint.x, panelPoint.y-selfPoint.y);
        System.out.println(component);
        this.updateUI();
    }
}
