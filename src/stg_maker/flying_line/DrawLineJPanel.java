/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.flying_line;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.Timer;
import stg_maker.object.GameObject;

/**
 *
 * @author ASUS
 */
public class DrawLineJPanel extends JPanel{
    FlyingLineJDialog parent;
    Point mousePoint,p;
    public boolean isDraw=false;
    public boolean hideLine=false;
    public Timer timer,delayTimer;
    MyPoint p1,p2;
    long lt=0;
    long orgTime;
    public Vector<MyPoint> points=null;
    double count=0;
    long temp;
    double x=0,y=0;
    int index;
    GameObject ob;
    
    public DrawLineJPanel(FlyingLineJDialog parent){
        super();
        this.parent = parent;
        setLayout(null);
        setBackground(Color.white);
        setLocation(35,65);
        setSize(parent.getWidth()-getX()*2,parent.getHeight()-getY()*2);
        addEvent();
        
        ob = new GameObject(System.getProperty("user.dir")+"/enemy/"+"demo.png");
        add(ob);
    }
    
    public void addEvent(){
        addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
            
            }
            public void mouseReleased(MouseEvent e){
                isDraw=true;
                points.add(new MyPoint(e.getPoint(),System.currentTimeMillis()-orgTime));
            }
            public void mousePressed(MouseEvent e){
                if(!isDraw){
                    orgTime = System.currentTimeMillis()/10;
                    points = new Vector<MyPoint>();
                    points.add(new MyPoint(e.getPoint(),System.currentTimeMillis()-orgTime));
                }
            }      
        });
        addMouseMotionListener(new MouseAdapter(){
            public void mouseDragged(MouseEvent e){
                if(!isDraw){
                    mousePoint = e.getPoint();
                    if(mousePoint.x>=0&&mousePoint.y>=0&&
                            mousePoint.x<DrawLineJPanel.this.getWidth()&&
                            mousePoint.y<DrawLineJPanel.this.getHeight()){
                        points.add(new MyPoint(e.getPoint(),System.currentTimeMillis()-orgTime));
                        repaint();
                    }
                }
            }
        });
    }
    
    public void showMovement(){
        timer=new Timer(1,new ActionListener(){
            public void actionPerformed(ActionEvent  e){
                if(count<=0){
                    while(true){
                        if(index==points.size()-1){
                                x = y = 0;
                                timer.stop();
                                delay(500);
                                break;
                        }
                        p1 = points.get(index++);
                        p2 = points.get(index);
                        if((p2.t-p1.t)==0)
                            continue;
                        count=(double)((p2.t-p1.t)/parent.speed);
                        x=(p2.x-p1.x)/count;
                        y=(p2.y-p1.y)/count;
                        if(x!=0||y!=0){
                            break;
                        }
                    }
                }
                ob.moveX+=x;
                ob.moveY+=y;
                ob.setLocation((int)(ob.orgPoint.x+ob.moveX), (int)(ob.orgPoint.y+ob.moveY));
                count--;
            }
        });
        start();
    }
    
    public void start(){
        index=0;
        count=0;
        p1=p2=null;
        MyPoint temp = points.firstElement();
        ob.setBounds(temp.x, temp.y, 40, 40);
        ob.orgPoint = ob.getLocation();
        ob.moveX=ob.moveY=0;
        ob.setVisible(true);
        timer.start();
    }
    
    public void closeMovement(){
        if(delayTimer!=null)
            delayTimer.stop();
        if(delayTimer!=null)
            timer.stop();
        ob.setVisible(false);
    }

    public void delay(int n){
        delayTimer = new Timer(n,new ActionListener(){
            public void actionPerformed(ActionEvent  e){
                DrawLineJPanel.this.start();
                delayTimer.stop();
            }
        });
        delayTimer.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(10.0f));
	g2.setColor(Color.red);
        if(points!=null&&!hideLine)
        {
            Point last=null;
            for(MyPoint point: points)
            {
                if(last!=null&&!last.equals(point)){
                    g2.drawLine(last.x, last.y, point.x, point.y);  
                }
                last = new Point(point);
            }
        }
    }
    
    
}
