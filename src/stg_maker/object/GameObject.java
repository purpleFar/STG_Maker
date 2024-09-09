/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.object;

import java.awt.Color;
import java.awt.*;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
//import javax.swing.*;
import stg_maker.*;
import stg_maker.game.GameAP;
import stg_maker.property.*;

/**
 *
 * @author ASUS
 */
public class GameObject extends JPanel{
    public transient GameMaker parent;
    public GameMap gm;
    public boolean die=false;
    public String path=null;
    public String boomPath=null;
    public int boomTime=60;
    public Mode mode=null;
    public transient Image image;
    public transient BufferedImage bufferedimage;
    public int imageAngle=0;
    public Status status = Status.inactived;
    public transient Outline outline=null;
    public String name;
    public Point orgPoint;
    public double moveX=0, moveY=0;
    Point op;
    int dx, dy, cpx, cpy, lpx, lpy;
    
    public GameObject(GameMaker parent, GameMap gm){
        super();
        setParent(parent,gm);
        this.mode = parent.mode;
        this.path = parent.objectList.imgStrings.get(parent.objectList.getSelectedIndex());
        boomPath = System.getProperty("user.dir")+"/"+BaseProperty.boomPath+"/"+"爆炸.gif";
        setImg(path);
        setOpaque(false);
        addEvent();  
    }
    
    public GameObject(GameMaker parent, GameMap gm,String path,Mode mode){
        super();
        setParent(parent,gm);
        this.mode = mode;
        this.path = path;
        boomPath = System.getProperty("user.dir")+"/"+BaseProperty.boomPath+"/"+"爆炸.gif";
        setImg(path);
        setOpaque(false);
        addEvent();  
    }

    public GameObject(GameAP parent, GameMap gm,Actor actor){
        super(null);
        this.path = actor.bulletPath;
        imageAngle = (int)(-actor.bulletAngle+90);
        setImg(path);
        setOpaque(false);
    }
    
    public GameObject(String path){
        super();
        this.path = path;
        setImg(path);
        setOpaque(false);
    }
    
    public void setParent(GameMaker parent,GameMap gm){
        this.parent = parent;
        this.gm = gm;
    }  
    public void addEvent(){
        addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if(gm.activeOBJ!=null){
                    gm.activeOBJ.hideOutline(true);
                    gm.activeOBJ.status=Status.inactived;
                }
                status = Status.actived;
                showOutline();
                gm.activeOBJ = GameObject.this;
                gm.activeOBJ.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                parent.addObjectProperty(mode);
                if((e.getModifiers()&e.BUTTON3_MASK)!=0){
                    parent.popupMenu.show(gm,gm.getMousePosition().x,gm.getMousePosition().y);
                    for(int i=0;i<parent.popupMenu.labelName.length;i++){
                        parent.popupMenu.menuItems[i].setEnabled(true);
                    }
                    if(parent.gameObjCopy==null)
                        parent.popupMenu.menuItems[2].setEnabled(false);
                    if(gm.activeOBJ.mode==Mode.player){
                        parent.popupMenu.menuItems[0].setEnabled(false);
                        parent.popupMenu.menuItems[3].setEnabled(false);
                    }
                }
            }
            public void mouseReleased(MouseEvent e)
            {
                if(status!=Status.inactived)
                {
                    if(GameObject.this.status==Status.ready2Move)
                    {
                        GameObject.this.status=Status.actived;
                        showOutline();
                    }
                }
                else
                gm.released(gm.getMousePosition());
            }           
            public void mousePressed(MouseEvent e)
            {
                BaseProperty baseProperty = (BaseProperty)parent.propertyTP.getSelectedComponent();
                baseProperty.update();
                if(status!=Status.inactived)
                {
                    if(status==Status.actived)
                    {
                        status = Status.ready2Move;
                        op=GameObject.this.getLocation();
                        lpx=e.getXOnScreen();
                        lpy=e.getYOnScreen();
                        GameObject.this.hideOutline(true);
                    }
                }
                else
                    gm.pressed(gm.getMousePosition());
            }
        });
        addMouseMotionListener(new MouseAdapter()
        {
            public void mouseDragged(MouseEvent e)
            {
                if(status!=Status.inactived)
                {
                    if(GameObject.this.status==Status.ready2Move){

                        cpx=e.getXOnScreen();
                        cpy=e.getYOnScreen();
                        dx=cpx-lpx;
                        dy=cpy-lpy;
                        op.x=op.x+dx;
                        op.y=op.y+dy;
                        GameObject.this.setLocation(op);
                        lpx=cpx;
                        lpy=cpy;
                    }
                    parent.objectProperty.updatePropertySpace();
                }
                else{
                    gm.dragged(new Point(e.getXOnScreen()-gm.getLocationOnScreen().x,e.getYOnScreen()-gm.getLocationOnScreen().y));}
            }
        });
    }
    
    public void setImg(String path){
        if(path!=null){
            try {
                String rPath = GameMaker.newDir(path);
                //URL imgURL = this.getClass().getClassLoader().getResource(path);
                bufferedimage = rotateImage(ImageIO.read(new File(rPath)),imageAngle);
                ImageIcon icon = new ImageIcon(rPath);
                image = icon.getImage();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void hideOutline(boolean isRemove)
    {
        if(outline!=null)
        {
            Point op;
            op = getLocation();
            int w = getWidth();
            int h = getHeight();

            for(JPanel cp: outline.cp)
            {
                cp.setVisible(false);
                if(isRemove)
                    gm.remove(cp);
            }
            gm.draw(op.x-outline.size/2, op.y-outline.size/2, w+outline.size, h+outline.size,false);
            if(isRemove)
                outline = null;
            gm.updateUI();
        }
    }
    
    public void showOutline()
    {
        if(outline==null)
          outline = new Outline(parent, gm);
        outline.setLocation(this);
        gm.updateUI();
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        if(path!=null){
            String[] grtType = path.split("\\.");
            if(grtType.length>0&&grtType[grtType.length-1].equals("gif")){
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
            else
                g.drawImage(bufferedimage, 0, 0, getWidth(), getHeight(), this);
        }
    }
    
  
    public BufferedImage rotateImage(BufferedImage bufferedimage, int degree) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        double radiusDegree = Math.toRadians(degree);

        double newH = Math.abs(h * Math.cos(radiusDegree)) + Math.abs(w * Math.sin(radiusDegree));
        double newW = Math.abs(h * Math.sin(radiusDegree)) + Math.abs(w * Math.cos(radiusDegree));

        double deltaX = (newW - w) / 2;
        double deltaY = (newH - h) / 2;

        BufferedImage img = new BufferedImage((int) newW, (int) newH, type);
        //BufferedImage img = new BufferedImage((int) w, (int) h, type);
        Graphics2D graphics2d = img.createGraphics();
        graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // 以下為矩陣相乘， [translate] * [rotate] * [image的x,y]
        // 所以行為為，先rotate，再translate
        graphics2d.translate(deltaX, deltaY);
        //this.setBounds((int)(getX()-deltaX), (int)(getY()-deltaY), (int)newW, (int)newH);
        graphics2d.rotate(radiusDegree, w / 2, h / 2);  //以中心點旋轉
        graphics2d.drawImage(bufferedimage, 0, 0, null);

        graphics2d.dispose();

        return img;
    }
}
