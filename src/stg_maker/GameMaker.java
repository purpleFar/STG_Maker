/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker;

import stg_maker.draw_btn.DrawToolBtn;
import java.awt.*;
import java.io.*;
import java.io.Serializable;
import java.util.*;
import javax.swing.*;
import java.net.*;
import javax.swing.border.*;
import stg_maker.flying_line.FlyingLineJDialog;
import stg_maker.object.*;
import stg_maker.object.GamePlayer;
import stg_maker.property.*;
import stg_maker.ui_setting.*;
import stg_maker.object.BackgroundPanel;
import stg_maker.unit.MyPopupMenu;

//在寫這程式碼時 只有我和上帝明白
//現在只有上帝看得懂

public class GameMaker implements Serializable{
    public String swTitle="STG_Maker";
    public String version="0.1";
    public MainWindow mainWin;
    
    public Font titleFont, contextFont;
    public URL imgURL;
    public String path = "img50/";
    
    //選單
    public MyMenu myMenu; 
    
    //繪畫工具列
    public Mode mode = Mode.enemy;
    public MyDrawTool myDrawTool;
    
    //工作區
    public WorkSpace workSp;
        
    //目錄管理區
    public JScrollPane jTreeSP;
    public JTree jTree;

    //物件放置區
    public ObjectList objectList;

    //繪圖區
    public DrawSpace drawSp;
    public JTabbedPane drawTP;

    //物件屬性區
    public PropertySpace propertySp;
    public JTabbedPane propertyTP;
        
    //專案
    public Vector<Project> projects;
    public Project curProject;
    
    //其他小工具(右鍵選單、、物件複製)
    public MyPopupMenu popupMenu;
    public ObjectProperty objectProperty;
    public GameObject gameObjCopy=null;
    
    
    GameMaker(){
        popupMenu = new MyPopupMenu(this);
        mainWin = new MainWindow(this);
        titleFont = new Font("微软正黑",Font.BOLD,24);
        contextFont = new Font("新細明體", 0, 24);
         //選單
        myMenu = new MyMenu(this); 
        //繪畫工具列
        myDrawTool = new MyDrawTool(this);

        //目錄管理區
        jTree = new JTree();
        jTree.setFont(contextFont);
        jTreeSP = new JScrollPane();
        jTreeSP.setViewportView(jTree);

        //物件放置區
        objectList = new ObjectList(this);


        //繪圖區
        drawTP = new JTabbedPane();
        drawSp = new DrawSpace(this);

        //物件屬性區
        propertyTP = new JTabbedPane();
        propertySp = new PropertySpace(this);
        
        //工作區GUI排版
        workSp = new WorkSpace(this);   
        mainWin.add(workSp);
        //==================================================
        projects = new Vector<Project>();
        curProject = new Project(this);
        projects.add(curProject);
        
        mainWin.setVisible(true);//revalidate();
        popupMenu.setVisible(true);
        popupMenu.setVisible(false);
    }
    
    
    public void setCurProject(Project project){
        this.propertyTP.removeAll();
        curProject = project;
        project.parent = this;
        project.curGameMap.parent = this;
        addIn2Map(project,true);
        project.curGameMap.addEvent();
        drawTP.removeAll();
        drawTP.addTab(project.curGameMap.mapName, project.curGameMap.drawScrollPane);
        curProject.curGameMap.s = Status.standBy;
        curProject.curGameMap.activeOBJ = null;
        curProject.setGameProperty(this);
        curProject.curGameMap.setBg(this,this.curProject.curGameMap);
    }
    
    public void addIn2Map(Project project, boolean addEvent){
        for(Component c: project.curGameMap.getComponents())
        {
            if(c instanceof GameObject){
                GameObject gameObject= (GameObject)c;
                ((GameObject) c).status = Status.inactived;
                gameObject.setImg(gameObject.path);
                gameObject.setParent(null,project.curGameMap);
                if(addEvent){
                    gameObject.addEvent();
                    gameObject.setParent(this,this.curProject.curGameMap);
                }
                else{
                    gameObject.orgPoint = gameObject.getLocation();
                    if(c instanceof GameEnemy){
                        GameEnemy ge = (GameEnemy)c;
                        ge.line = GameMaker.load(GameMaker.newDir(ge.linePath));    
                    }    
                }
            }
            else if(c instanceof BackgroundPanel){
                BackgroundPanel backgroundPanel = (BackgroundPanel)c;
                backgroundPanel.setImg(backgroundPanel.path);
            }
        }
    }
    
    public void addObjectProperty(Mode mode){
        removeObjectProperty();
        switch(mode){
            case player:
                objectProperty = new PlayerProperty(this,curProject.curGameMap);
                break;
            case enemy:
                objectProperty = new EnemyProperty(this,curProject.curGameMap);
                break;
            case background:
                objectProperty = new BackgroundObjectProperty(this,curProject.curGameMap);
                break;
            case prop:
                objectProperty = new PropObjectProperty(this,curProject.curGameMap);
                break;
            case obstacle:
                objectProperty = new ObstacleProperty(this,curProject.curGameMap);
                break;
        }
        propertyTP.addTab(objectProperty.typeName, objectProperty);
        propertyTP.setSelectedComponent(objectProperty);
    }
    
    public void removeObjectProperty(){
        if(propertyTP.getComponentCount()>2){
            propertyTP.remove(objectProperty);
        }
    }
    
    
    public GameObject createObject(Mode mode){
        switch(mode){
            case player:
                return  new GamePlayer(this,curProject.curGameMap);
            case enemy:
                return  new GameEnemy(this,curProject.curGameMap);
            case background:
                return  new GameBackgroundObject(this,curProject.curGameMap);
            case prop:
                return  new GamePropObject(this,curProject.curGameMap);
            case obstacle:
                return  new GameObstacleObject(this,curProject.curGameMap);
        }    
        return null;
    }
    
    public static String newDir(String path){
        String[] pathSplit = path.split("/");
        return System.getProperty("user.dir")+"/"+pathSplit[pathSplit.length-2]+"/"+pathSplit[pathSplit.length-1];
    }
    
    public static <T extends Serializable> T copy(T input) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(input);
            oos.flush();
            oos.close();

            byte[] bytes = baos.toByteArray();
            baos.close();

            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            bis.close();

            Object result = ois.readObject();
            ois.close();
            return (T) result;
        } catch (IOException e) {
            throw new IllegalArgumentException("Object can't be copied", e);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unable to reconstruct serialized object due to invalid class definition", e);
        } 
    }
    
    public static void save(Object ob,String name) {   
        try {
            FileOutputStream fs = new FileOutputStream(name);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(ob);
            os.close();
            fs.close();
            System.out.println("Object is saved.");
        }
        catch (Exception ex) {
            System.out.println("Object is not saved.");
            JOptionPane.showMessageDialog(new JFrame(),"無法儲存");
        }
        finally {
            System.out.println("after Save .....");
        }
    }
    
    public static <T extends Serializable> T load(String name) {
        FileInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new FileInputStream(name);
            ois = new ObjectInputStream(bis);
            Object result = ois.readObject();
            ois.close();
            bis.close();
            System.out.println("Object is loaded.");
            return (T) result;
        } catch (IOException e) {
            System.out.println("Object is not loaded.");
            JOptionPane.showMessageDialog(new JFrame(),"無法載入");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Object is not loaded.");
            JOptionPane.showMessageDialog(new JFrame(),"無法載入");
            e.printStackTrace();
        } 
        finally {
            System.out.println("after Load .....");
        }
        return null;
    }
}
