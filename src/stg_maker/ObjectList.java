/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker;

import java.awt.Image;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.jar.*;
import javax.swing.*;
import javax.swing.event.*;
import stg_maker.object.*;

/**
 *
 * @author ASUS
 */
public class ObjectList extends JList{
    public transient GameMaker parent;
    public JScrollPane sp;
    public DefaultListModel listModel;
    public Vector<String> imgStrings;
    public Vector<Image> imgs;
    public File file;
    public File[] files;
    
    public ObjectList(GameMaker parent){
        super();
        this.parent = parent;
        setFont(parent.contextFont);
        imgs = new Vector<>();
        imgStrings = new Vector<>();
        listModel =  new DefaultListModel();
        setModel(listModel);
        sp = new JScrollPane();
        sp.setViewportView(this);
        setMode();
        //addEvent();
    }
 /*   
    public void addEvent(){
        addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
		eventAction();
            }
        });
    }
    
    public void eventAction(){
        if(parent.propertyTP!=null){
        //parent.propertyTP.removeAll();
            switch(getSelectedIndex()){
                case 0:
                    //parent.propertyTP.addTab(parent.bg.typeName, parent.bg);
                    break;
                case 1:

                    break;
            }
        }
    }
*/
    public void setMode(){
        listModel.removeAllElements();
        imgs.removeAllElements();
        imgStrings.removeAllElements();
        //file = new File(Main.class.getClassLoader().getResource(parent.mode.name()+"/").getPath());
        file = new File(System.getProperty("user.dir")+"/"+parent.mode.name()+"/");
        String[] files = file.list();
        if(files!=null)
            for (String file2 : files) {
                showImg(file2,120);
            }
        else{
            String[] jarInfo = Main.class.getClassLoader().getResource(parent.mode.name()+"/").getPath().split("!");
            String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
            System.out.println(jarFilePath);
            try{
                JarFile jarFile = new JarFile(jarFilePath);
                Enumeration<JarEntry> entrys = jarFile.entries();
                while (entrys.hasMoreElements()) {
                    JarEntry jarEntry = entrys.nextElement();
                    String entryName = jarEntry.getName();
                    System.out.println(entryName);
                    if(entryName.split("/").length>1&&entryName.split("/")[0].equals(parent.mode.name()))
                        showImg(entryName.split("/")[1],120);
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        setSelectedIndex(0);
    }
    
    public void showImg(String s,int n){
        ImageIcon icon = new ImageIcon(System.getProperty("user.dir")+"/"+parent.mode.name()+"/"+s);
        //URL imgURL = parent.getClass().getClassLoader().getResource(parent.mode.name()+"/"+s);
        //ImageIcon icon = new ImageIcon(imgURL);
        Image image = icon.getImage();
        imgs.add(image);
        imgStrings.add(parent.mode.name()+"/"+s);
        Image newimg = image.getScaledInstance((int)(n*(icon.getIconWidth()/(double)icon.getIconHeight())),n, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        listModel.addElement(icon);
    }
}
