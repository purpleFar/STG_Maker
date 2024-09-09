/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.net.URL;
import javax.swing.*;

/**
 *
 * @author ASUS
 */
public class BackgroundPanel extends JPanel{
    public String path=null;
    public double moveSpeed = 1;
    public transient Image img;
    
    public BackgroundPanel(int w, int h){
        super();
        this.setSize(w, h);
        this.setBackground(Color.white);
        setImg(path);
    }
    
    public void setImg(String path){
        if(path==null)
            img=null;
        else
        {
            ImageIcon icon = new ImageIcon(path);
            img = icon.getImage();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(path!=null)
        {
            int w = getWidth(),
                h = getHeight(),
                imgW = img.getWidth(this),
                imgH = img.getHeight(this);
            for(int i=0;i<h;i+=imgH){
                for(int j=0;j<w;j+=imgW){
                    g.drawImage(img, j, i,imgW,imgH, this);
                }
            }
        }
    }
    
}
