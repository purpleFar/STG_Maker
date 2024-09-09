/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import stg_maker.*;
import stg_maker.object.*;
import stg_maker.object.GamePlayer;
import stg_maker.property.BackgroundProperty;

/**
 *
 * @author ASUS
 */
public class GameAP extends JDialog{
    Project project;
    GameMap gm;
    String str="100%";
    JLabel showHealth;
    Update update;
    Point mousePoint=null;
    Music music;
    int h;
    
    public GameAP(GameMaker parent,Project project){
        super(parent.mainWin);
        this.project = project;
        gm = project.curGameMap;
        setTitle("遊戲執行畫面");
        setResizable(false);
        setModal(true);
        getContentPane().setBackground(Color.black);
        setLayout(null);

        Dimension s = gm.getPreferredSize();
        setBounds(gm.getX()+parent.drawSp.getLocationOnScreen().x, 
                0,
                (int) s.getWidth(),
                (int) (parent.mainWin.screenSize.getHeight()));        
        add(gm);
        h = this.getHeight()-gm.player.getY()-gm.player.getHeight()-40;
        gm.setBounds(0, h, gm.getWidth(), gm.getHeight());
        
        Cursor cr;
        //cr = Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("").getImage(), new Point(0,0) ,"MyCursor" );
        cr = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
        setCursor( cr );
        update = new Update(this, project);
        update.start();
        music = new Music(parent.curProject.musicFile);
        music.run();
        showHealth = new JLabel("100%",SwingConstants.CENTER);
        showHealth.setBounds(project.showHealthLocation.x, project.showHealthLocation.y, project.showHealth.width, project.showHealth.height);
        showHealth.setFont(new Font("",Font.BOLD,showHealth.getHeight()-10));
        showHealth.setForeground(Color.red);
        add(showHealth,0);
        
        setWhenClose();
        setVisible(true);
    }
    
    
    public boolean outSide(JPanel a){
        if(a.getX()+a.getWidth()<0 || a.getX()>gm.getWidth() || a.getY()+a.getHeight()<-gm.getY() || a.getY()>-gm.getY()+this.getHeight()){        
            return true;
        }
        return false;
    }
    public void setWhenClose(){
        addWindowListener(new WindowAdapter() 
        {
            public void windowClosing(WindowEvent event) {
                music.stopMusic();
                update.stop();
                for(Component cp: gm.getComponents())
                {
                    if(cp instanceof Actor)
                    {
                        Actor actor = (Actor)cp;
                        if(actor.launchers!=null)
                        {
                            for(int i=actor.launchers.size()-1;i>=0;i--){
                                for(int j=actor.launchers.get(i).bullets.size()-1;j>=0;j--){
                                    actor.launchers.get(i).bullets.get(j).remove();
                                }
                                actor.launchers.remove(actor.launchers.get(i));
                            }
                        }
                    }
                }
            }
        });
    }
    
    public boolean isTouch(JPanel a, JPanel b){
        int minX1 = a.getX();
        int maxX1 = a.getX()+a.getWidth();
        int minY1 = a.getY();
        int maxY1 = a.getY()+a.getHeight();
        int minX2 = b.getX();
        int maxX2 = b.getX()+b.getWidth();
        int minY2 = b.getY();
        int maxY2 = b.getY()+b.getHeight();
        if(maxX1>minX2 && maxX2>minX1 && maxY1>minY2 && maxY2>minY1){
            return true;
        }
        return false;
    }

}
