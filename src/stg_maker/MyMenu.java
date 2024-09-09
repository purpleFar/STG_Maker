/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker;

import java.awt.BorderLayout;
import java.util.Vector;
import javax.swing.*;

/**
 *
 * @author ASUS
 */
public class MyMenu {
    public JMenuBar menuBar;
    public JMenu[] menu, subMenu;
    public JMenuItem[][] menuItem, subMenuItem;
    public String path = "menu32/";
    public String[] menuName = {"檔案","編輯","檢視","執行","工具","說明"},
             subMenuName = {"工具列"};
    public String[][] menuItemName = {{"新增頁面","新增專案","開啟專案","存檔","另存新檔","刪除頁面","刪除專案"},
                                       {"返回","取消返回"},
                                       {"工具列"},
                                       {"編譯","編譯並執行"},
                                       {"選項"},
                                       {"內容說明","關於"}},
               subMenuItemName = {{"檔案","返回/取消返回","執行"}};
    public Vector<MenuAction> menuAction = null;
    
    //選單工具列
    public JToolBar menuToolBar;
    public String[] menuList = {"新增頁面","新增專案","開啟專案","刪除頁面",
                                    "刪除專案","返回","取消返回","編譯並執行"};   
    public String[] menuImgName = {"newPage.png","newProject.png","openProject.png","deletePage.png",
                                    "deleteProject.png","back.png","next.png","play.png"};   
    
    MyMenu(GameMaker parent){
        menuBar = new JMenuBar();
        parent.mainWin.setJMenuBar(menuBar);
        //選單
        menu = new JMenu[menuName.length];
        subMenu = new JMenu[subMenuName.length];
        menuItem = new JMenuItem[menuName.length][];
        subMenuItem = new JMenuItem[subMenuItemName.length][];
        menuAction = new Vector<MenuAction>();
        
        int temp = 0, tempOfTool = 0;
        
        for(int i=0; i<menuName.length; i++){
            menu[i] = new JMenu(menuName[i]);
            menu[i].setFont(parent.titleFont);
            menuBar.add(menu[i]);
            
            menuItem[i]= new JMenuItem[menuItemName[i].length];
            for(int j=0; j<menuItemName[i].length; j++){
                if(menuItemName[i][j].equals(subMenuName[temp])){
                    subMenu[temp] = new JMenu(subMenuName[temp]);
                    subMenu[temp].setFont(parent.titleFont);
                    menu[i].add(subMenu[temp]);
                    
                    subMenuItem[temp] = new JMenuItem[subMenuItemName[temp].length];
                    for(int k=0; k<subMenuItemName[temp].length; k++){
                        subMenuItem[temp][k] = new JMenuItem(subMenuItemName[temp][k]);
                        subMenuItem[temp][k].setFont(parent.titleFont);
                        subMenu[temp].add(subMenuItem[temp][k]);
                        subMenu[temp].addSeparator();
                        if(menuList[tempOfTool].equals(subMenuItem[temp][k].getText())){
                            parent.imgURL = parent.getClass().getClassLoader().getResource(path+menuImgName[tempOfTool]);
                            ImageIcon icon = new ImageIcon(parent.imgURL);
                            menuAction.add(new MenuAction(parent, subMenuItem[temp][k].getText(),icon));
                            tempOfTool=++tempOfTool%menuImgName.length;
                        }
                        else
                            menuAction.add(new MenuAction(parent, subMenuItem[temp][k].getText()));
                        subMenuItem[temp][k].addActionListener(menuAction.lastElement());
                    }
                    temp=++temp%subMenuName.length;
                }
                else{
                    menuItem[i][j] = new JMenuItem(menuItemName[i][j]);
                    menuItem[i][j].setFont(parent.titleFont);
                    menu[i].add(menuItem[i][j]);
                    if(menuList[tempOfTool].equals(menuItem[i][j].getText())){
                        parent.imgURL = parent.getClass().getClassLoader().getResource(path+menuImgName[tempOfTool]);
                        ImageIcon icon = new ImageIcon(parent.imgURL);
                        menuAction.add(new MenuAction(parent, menuItem[i][j].getText(),icon));
                        tempOfTool=++tempOfTool%menuImgName.length;
                    }
                    else
                        menuAction.add(new MenuAction(parent, menuItem[i][j].getText()));
                    menuItem[i][j].addActionListener(menuAction.lastElement());
                }
                menu[i].addSeparator();
            }
        }

        //選單工具列
        menuToolBar = new JToolBar();
        temp=0;
        for(int i=0;i<menuAction.size();i++){
            if(menuList[temp].equals(menuAction.get(i).label)){
                JButton btn = menuToolBar.add(menuAction.get(i));
                btn.setToolTipText(menuAction.get(i).label);
                btn.setBorderPainted(false);
                btn.setFocusPainted(false);
                temp=++temp%menuImgName.length;
            }
        }
        parent.mainWin.getContentPane().add(menuToolBar, BorderLayout.NORTH);    
        
    }
}
