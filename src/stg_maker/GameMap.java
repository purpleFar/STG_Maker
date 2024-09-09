/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
//import javax.swing.*;
import java.lang.*;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import stg_maker.object.*;
import stg_maker.property.BackgroundProperty;
import stg_maker.property.BaseProperty;
import stg_maker.object.BackgroundPanel;
import stg_maker.unit.MyPopupMenu;

/**
 *
 * @author ASUS
 */
public class GameMap extends JPanel{
    public transient GameMaker parent;
    public String name="map";
    public JScrollPane drawScrollPane;
    public JPanel contact;
    public transient BackgroundProperty bg=null;
    public Status s = Status.standBy;
    public GameObject activeOBJ=null;
    public GamePlayer player;  
    public Point lp,cp;
    public int w=800,h=1000000;
    public int x1, y1,w1, h1;
    public boolean controlDrawRect=false;
    public String mapName;
    public BackgroundPanel backgroundPanel;
 
    GameMap(GameMaker parent,String mapName){
        super();
        this.parent = parent;
        this.mapName = mapName;
        setLayout(null); 
        contact = new JPanel(new FlowLayout(FlowLayout.CENTER,40,40));
        contact.add(this);
        contact.setBackground(Color.lightGray);
        
        this.setPreferredSize(new Dimension(w,h));
        setBackground(Color.white);
        player = new GamePlayer(parent,this,System.getProperty("user.dir")+"/player/demo.png");
        add(player,0);
        
        add(parent.popupMenu);
        
        drawScrollPane = new JScrollPane();
        drawScrollPane.setViewportView(contact);
        drawScrollPane.getViewport().setBackground(Color.black);
        parent.drawTP.addTab(mapName, drawScrollPane);
        drawScrollPane.setViewportBorder(BorderFactory.createLineBorder(new Color(0,0,0),1));
        backgroundPanel = new BackgroundPanel(getPreferredSize().width,getPreferredSize().height);
        add(backgroundPanel);
        setBg(parent,this);
        addEvent();
        
        //滑至最底端 
        Point p = new  Point();   
        p.setLocation(0,h);   
        drawScrollPane.getViewport().setViewPosition(p); 
        //滾輪
        drawScrollPane.getVerticalScrollBar().setUnitIncrement(20);
    }
    public void setBg(GameMaker parent,GameMap gm){
        if(bg==null)
            bg = new BackgroundProperty(parent,gm);
        bg.setParent(parent, gm);
        parent.propertyTP.addTab(bg.typeName, bg);
        bg.addEvent();
        bg.updatePropertySpace();
    }

    
    public void addEvent(){
        addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                clicked();
                if((e.getModifiers()&e.BUTTON3_MASK)!=0){
                    parent.popupMenu.show(e.getComponent(),GameMap.this.getMousePosition().x,GameMap.this.getMousePosition().y);
                    for(int i=0;i<parent.popupMenu.labelName.length;i++){
                        parent.popupMenu.menuItems[i].setEnabled(false);
                    }
                    if(parent.gameObjCopy!=null)
                        parent.popupMenu.menuItems[2].setEnabled(true);
                }
            }
            
            public void mouseReleased(MouseEvent e)
            {
                released(e.getPoint());
            }           
            public void mousePressed(MouseEvent e)
            {
                pressed(e.getPoint());
            }
        });
        
        addMouseMotionListener(new MouseAdapter()
        {
            public void mouseDragged(MouseEvent e)
            {
                dragged(e.getPoint());
            }
        });
    }

    public void clicked(){
        bg.updatePropertySpace();
        parent.propertyTP.setSelectedComponent(bg);
    }
    
    public void released(Point p){
        if(cp!=null)
        {
            Graphics g = getGraphics();
            g.setXORMode(Color.white);
            g.drawRect(Math.min(lp.x, cp.x), Math.min(lp.y, cp.y), Math.abs(cp.x-lp.x), Math.abs(cp.y-lp.y));
            if(Math.abs(cp.x-lp.x)*Math.abs(cp.y-lp.y)>400)
            {
                activeOBJ = parent.createObject(parent.mode);
                if(parent.mode==Mode.player)
                {
                    parent.curProject.curGameMap.remove(parent.curProject.curGameMap.player);
                    parent.curProject.curGameMap.player = (GamePlayer)activeOBJ;
                    parent.curProject.curGameMap.add(activeOBJ,0);
                }
                else
                    add(activeOBJ,1);
                activeOBJ.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                activeOBJ.setSize(Math.abs(cp.x-lp.x)+1, Math.abs(cp.y-lp.y)+1);
                activeOBJ.setLocation(new Point(Math.min(lp.x, cp.x), Math.min(lp.y, cp.y)));
                activeOBJ.showOutline();
                activeOBJ.status = Status.actived;
                s=Status.standBy;
                parent.addObjectProperty(activeOBJ.mode);
            }
            repaint();
        }    
    }
    
    public void pressed(Point p){
        BaseProperty baseProperty = (BaseProperty)parent.propertyTP.getSelectedComponent();
        baseProperty.update();
        if(activeOBJ!=null)
        {
            activeOBJ.hideOutline(true);
            activeOBJ.status=Status.inactived;
            activeOBJ.setCursor(null);
            activeOBJ=null;
            parent.removeObjectProperty();
        }
        s=Status.creatingElement;
        lp = p;
        cp = null;
    }
    
    public void dragged(Point p){
        if(s==Status.creatingElement)
        {
            Graphics g=getGraphics();
            if(cp!=null)
            {   
                g.setXORMode(Color.white);
                g.drawRect(Math.min(lp.x, cp.x), Math.min(lp.y, cp.y), Math.abs(cp.x-lp.x), Math.abs(cp.y-lp.y));
            }
            cp = p;
            g.setXORMode(Color.white);
            g.drawRect(Math.min(lp.x, cp.x), Math.min(lp.y, cp.y), Math.abs(cp.x-lp.x), Math.abs(cp.y-lp.y));
        }
    }
    /*
    @Override
    public void setBounds(int x, int y, int width, int height){
        super.setBounds(x, y, width, height);
        contact.setPreferredSize(new Dimension(getWidth(), getHeight()));
        bg.updatePropertySpace();
        //DrawScrollPane.repaint();
    }*/

    public void draw(int x,int y,int w,int h,boolean b){
        x1=x;
        y1=y;
        w1=w;
        h1=h;
        controlDrawRect=b;
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        if(controlDrawRect&&activeOBJ!=null){
            Color c = g.getColor();
            g.setXORMode(Color.white);
            g.drawRect(x1,y1,w1,h1);
            for(int i=0;i<8;i++){
                JPanel p = activeOBJ.outline.cp[i];
                g.fillRect(p.getX(), p.getY(), p.getWidth(), p.getHeight());
            }
            g.setColor(c);
        }
    }

}
