/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.flying_line;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import stg_maker.GameMaker;
import stg_maker.Project;

/**
 *
 * @author ASUS
 */
public class FlyingLineJDialog extends JDialog{
    public DrawLineJPanel drawLineJPanel;
    public DrawLineJPanel copy;
    public JPanel contact;
    public JButton OKJButton;
    public JButton playJButton;
    public JButton speedJButton;
    public JButton cancelJButton;
    public String path;
    int btnGap = 5;
    int sizeX=80;
    double speed = 1;

    public FlyingLineJDialog(GameMaker parent, Project project){
        super(parent.mainWin);
        path = "";
        
        setTitle("路線繪製介面");
        setResizable(false);
        setModal(true);
        getContentPane().setBackground(Color.black);        
        setBounds(parent.drawSp.getLocationOnScreen().x, 0,450,900);  
        
        
        contact = new JPanel();
        contact.setBackground(Color.black);
        contact.setLayout(null);
        add(contact);
        drawLineJPanel = new DrawLineJPanel(this);
        contact.add(drawLineJPanel);
        
        playJButton  = new JButton("播放");
        playJButton.setFont(new Font("",Font.BOLD,20));
        playJButton.setSize(sizeX, 35);
        playJButton.setLocation(35, 15);
        contact.add(playJButton);
        playJButton.addActionListener(new ActionListener(){
             @Override
            public void actionPerformed(ActionEvent e) {
                if(drawLineJPanel.isDraw){
                    if(drawLineJPanel.hideLine){
                        playJButton.setText("播放");
                        drawLineJPanel.hideLine=false;
                        drawLineJPanel.closeMovement();
                    }
                    else{
                        playJButton.setText("路線");
                        drawLineJPanel.hideLine=true;
                        drawLineJPanel.showMovement();
                    }
                    drawLineJPanel.repaint();
                }
            }
        });
        
        OKJButton  = new JButton("儲存");
        OKJButton.setFont(new Font("",Font.BOLD,20));
        OKJButton.setSize(sizeX, 35);
        OKJButton.setLocation(35+sizeX+btnGap, 15);
        contact.add(OKJButton);
        OKJButton.addActionListener(new ActionListener(){
             @Override
            public void actionPerformed(ActionEvent e) {
                if(drawLineJPanel.isDraw){
                    String input=JOptionPane.showInputDialog("請輸入檔名");
                    if(input!=null){
                        GameMaker.save(drawLineJPanel, System.getProperty("user.dir")+"/line/"+input);
                        JOptionPane.showMessageDialog(FlyingLineJDialog.this,"已儲存成功");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(FlyingLineJDialog.this,"請先畫出路線");
                }
            }
        });
        
        cancelJButton  = new JButton("清除");
        cancelJButton.setFont(new Font("",Font.BOLD,20));
        cancelJButton.setSize(sizeX, 35);
        cancelJButton.setLocation(35+(sizeX+btnGap)*2, 15);
        contact.add(cancelJButton);
        cancelJButton.addActionListener(new ActionListener(){
             @Override
            public void actionPerformed(ActionEvent e) {
                contact.remove(drawLineJPanel);
                drawLineJPanel = new DrawLineJPanel(FlyingLineJDialog.this);
                contact.add(drawLineJPanel);
                drawLineJPanel.repaint();
                playJButton.setText("播放");
            }
        });
        
        speedJButton  = new JButton("速度×1");
        speedJButton.setFont(new Font("",Font.BOLD,20));
        speedJButton.setSize(125, 35);
        speedJButton.setLocation(35+(sizeX+btnGap)*3, 15);
        contact.add(speedJButton);
        speedJButton.addActionListener(new ActionListener(){
             @Override
            public void actionPerformed(ActionEvent e) {
                speed = (speed==8)?0.125:speed*2;
                if(speed>=1)
                    speedJButton.setText("速度×"+(int)(speed));
                else
                    speedJButton.setText("速度÷"+(int)(1/speed));
            }
        });
        
        
        addWindowListener(new WindowAdapter() 
        {
            public void windowClosing(WindowEvent event) {
                drawLineJPanel.closeMovement();
                drawLineJPanel=null;
            }
        });
        setVisible(true);
    }
}
