/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.logging.*;
import javax.swing.*;
import stg_maker.flying_line.FlyingLineJDialog;
import stg_maker.game.*;
import stg_maker.property.BaseProperty;

/**
 *
 * @author ASUS
 */
public class MenuAction extends AbstractAction{
    String label;
    GameMaker parent;
    MenuAction(GameMaker parent, String s){
        super(s);
        this.parent = parent;
        label = s;
    }
    MenuAction(GameMaker parent, String s,Icon icon){
        super(s, icon);
        this.parent = parent;
        label = s;
    }

    
    public void actionPerformed(ActionEvent e) {
            System.out.println("你按了\""+label+"\"");
            BaseProperty baseProperty = (BaseProperty)parent.propertyTP.getSelectedComponent();
            baseProperty.update();
            switch(label){
                case "新增頁面":

                    break;
                case "新增專案":
                    parent.drawTP.removeAll();
                    parent.propertyTP.removeAll();
                    parent.curProject = new Project(parent);
                    break;
                case "開啟專案":
                    String name = openDialog("/project/");
                    if(name!=null){
                        Project project = (Project)GameMaker.load(System.getProperty("user.dir")+"/project/"+name);
                        if(project!=null){
                            project.savePath=System.getProperty("user.dir")+"/project/"+name;
                            parent.setCurProject(project);   
                        }
                    }
                    break;
                case "存檔":
                    if(parent.curProject.savePath==null){
                        parent.curProject.savePath = saveDialog("/project/");
                    }
                    if(parent.curProject.savePath!=null){
                        GameMaker.save(parent.curProject,parent.curProject.savePath);
                    }
                    break;
                case "另存新檔":
                    String path = saveDialog("/project/");
                    if(path!=null){
                        parent.curProject.savePath = path;
                        GameMaker.save(parent.curProject,path);
                    }
                    break;
                case "刪除頁面":

                    break;
                case "刪除專案":
                    if(parent.curProject.savePath!=null){
                        try{
                        File file = new File(parent.curProject.savePath);
                            if(file.delete()){
                                System.out.println(file.getName() + " is deleted!");
                                JOptionPane.showMessageDialog(parent.mainWin,"刪除成功");
                            }
                            else{
                                System.out.println("Delete operation is failed.");
                                JOptionPane.showMessageDialog(parent.mainWin,"刪除失敗");
                            }
                        }catch(Exception ex){JOptionPane.showMessageDialog(parent.mainWin,"錯誤");}
                    }
                    parent.drawTP.removeAll();
                    parent.propertyTP.removeAll();
                    parent.curProject = new Project(parent);
                    break;
                case "返回":
                    if(parent.curProject!=parent.projects.firstElement()){
                        int index = parent.projects.indexOf(parent.curProject);
                        parent.setCurProject(parent.projects.get(index-1));
                    }
                    break;
                case "取消返回":
                    if(parent.curProject!=parent.projects.lastElement()){
                        int index = parent.projects.indexOf(parent.curProject);
                        parent.setCurProject(parent.projects.get(index+1));
                    }
                    break;
                case "檔案":

                    break;
                case "返回/取消返回":

                    break;
                case "編譯":
                    if(parent.curProject.curGameMap.activeOBJ!=null){
                        parent.curProject.curGameMap.activeOBJ.hideOutline(true);
                        parent.curProject.curGameMap.activeOBJ.status=Status.inactived;
                        parent.curProject.curGameMap.activeOBJ.setCursor(null);
                        parent.curProject.curGameMap.activeOBJ=null;
                        parent.removeObjectProperty();
                    }
                    if(parent.curProject.savePath==null){
                        parent.curProject.savePath = saveDialog("/project/");
                    }
                    if(parent.curProject.savePath!=null){
                        GameMaker.save(parent.curProject,parent.curProject.savePath);
                    }
                    break;
                case "編譯並執行":
                    if(parent.curProject.curGameMap.activeOBJ!=null){
                        parent.curProject.curGameMap.activeOBJ.hideOutline(true);
                        parent.curProject.curGameMap.activeOBJ.status=Status.inactived;
                        parent.curProject.curGameMap.activeOBJ.setCursor(null);
                        parent.curProject.curGameMap.activeOBJ=null;
                        parent.removeObjectProperty();
                    }
                    if(parent.curProject.savePath==null){
                        parent.curProject.savePath = saveDialog("/project/");
                    }
                        if(parent.curProject.savePath!=null){
                        GameMaker.save(parent.curProject,parent.curProject.savePath);
                        Project loadProject = GameMaker.load(parent.curProject.savePath);
                        parent.addIn2Map(loadProject,false);
                        new GameAP(parent,loadProject);
                    }
                    break;
                case "選項":

                    break;
                case "內容說明":
                    String context = "STG_Maker是一款針對卷軸式射擊遊戲的遊戲開發引擎，\n"
                            + "希望使用者只需要使用簡單的操作就能開發出多樣化的卷\n軸式射擊遊戲。\n"
                            + "本軟體目前支援專案儲存、開啟；素材導入；物件行徑路\n線製作，以及多種參數調整。"; 
                    JOptionPane.showMessageDialog(parent.mainWin,context,"內容說明",JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "關於":
                    String text = "此軟體為\n屏東大學資工系軟體工程課程本組開發之專題\n"
                            + "分工名單:\n"
                            + "程式設計 - 林冠中\n"
                            + "美術素材蒐集 - 才維文、林冠中\n"
                            + "企劃書撰寫 - 劉宇軒、陳暐中、林冠中\n"
                            + "UML - 傅珣樸、林建丞、陳暐中、才維文"; 
                    JOptionPane.showMessageDialog(parent.mainWin,text,"關於",JOptionPane.INFORMATION_MESSAGE);
                    break;

            }
    }	
    public static String saveDialog(String path){
        try{
            JFileChooser fileDialog = new JFileChooser();
            fileDialog.setCurrentDirectory(new File(System.getProperty("user.dir")+path));
            int rVal = fileDialog.showSaveDialog(new JLabel());
            if (rVal == JFileChooser.APPROVE_OPTION) {
                return fileDialog.getCurrentDirectory().toString()+
                        "/"+fileDialog.getSelectedFile().getName();
            }
        }catch(Exception ex){}
        return null;
    }
    
    public static String openDialog(String path){
        try{
            JFileChooser fileDialog = new JFileChooser();
            fileDialog.setCurrentDirectory(new File(System.getProperty("user.dir")+path));
            int rVal = fileDialog.showOpenDialog(new JLabel());
            File dir = fileDialog.getCurrentDirectory();
            if (rVal == JFileChooser.APPROVE_OPTION) {
                return fileDialog.getSelectedFile().getName();                     
            }
        }catch(Exception ex){}
        return null;
    }
}
