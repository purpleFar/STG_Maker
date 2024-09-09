/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import stg_maker.GameMap;
import static stg_maker.Mode.enemy;
import stg_maker.Project;
import stg_maker.object.Actor;
import stg_maker.object.GameEnemy;
import stg_maker.object.GameObject;
import stg_maker.object.GamePlayer;

/**
 *
 * @author ASUS
 */
public class Update {
    public GameAP parent;
    public Project project;
    public GameMap gm;
    public GamePlayer player;
    public Timer timer;
    public Vector<GameObject> gameObjects;
    public JLabel showWiner=null;
    double playerHealth;
    double screemMoveDis=0;
    double moveSpeed;
    int countEnemy=0;
    boolean win =false;
    GameEnemy enemy;
    
    
    public Update(GameAP parent, Project project){
        this.parent = parent;
        this.project = project;
        this.gm = project.curGameMap;
        this.player = gm.player;
        playerHealth = player.health;
        gameObjects = new Vector<GameObject>();
        player.launchers = new Vector<Launcher>();
        player.launchers.add(new Launcher(parent,gm,player));
        gameObjects.add(player);
        moveSpeed = gm.backgroundPanel.moveSpeed;
        
        
        timer=new Timer(project.delay,new ActionListener(){
            public void actionPerformed(ActionEvent  e){
                countEnemy=0;
                updateEnemy();
                updatePlayer();
                updateGameObjects();
                updateShowLabel();
                showWiner();
                updateWin();
        }});
    }
    
    public void start(){
        timer.start();
    }
    
    public void stop(){
        timer.stop();
    }
    
    public void showWiner(){
        if(countEnemy==0){
            if(showWiner==null){
                showWiner = new JLabel(project.winShow);
                showWiner.setBounds(0,parent.getHeight()/2-60,parent.getWidth(),100);
                showWiner.setFont(new Font("微软正黑",Font.BOLD,62));
                showWiner.setForeground(Color.red);
                showWiner.setHorizontalAlignment(JLabel.CENTER);
                parent.add(showWiner,0);
                player.speed=0;
            }
        }
        else if(!gm.isAncestorOf(player)){
            if(showWiner==null){
                showWiner = new JLabel(project.loseShow);
                showWiner.setBounds(0,parent.getHeight()/2-60,parent.getWidth(),100);
                showWiner.setFont(new Font("微软正黑",Font.BOLD,62));
                showWiner.setForeground(Color.red);
                showWiner.setHorizontalAlignment(JLabel.CENTER);
                parent.add(showWiner,0);
            }
        }
    }
    
    public void updateWin(){
        screemMoveDis += moveSpeed;
        gm.setBounds(0,(int)(parent.h+screemMoveDis),gm.getWidth(),gm.getHeight());
    }
    
    public void updatePlayer(){
        updateLauncher(player.launchers);
        Point mousePoint = gm.getMousePosition();
        if(mousePoint!=null){
            int mX=mousePoint.x,mY=mousePoint.y;
            double x = player.getX()-(mX-player.getWidth()/2);
            double y = player.getY()-(mY-player.getHeight()/2);
            double distance = Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
            double mult = player.speed/distance;
            double x2 = (double)((int)(player.orgPoint.x+player.moveX-x*mult))-(mX-player.getWidth()/2);
            double y2 = (double)((int)(player.orgPoint.y+player.moveY-y*mult))-(mY-player.getHeight()/2);
            //double distance2 = Math.sqrt(Math.pow(x2, 2)+Math.pow(y2, 2));
            if(x*x2<=0){
                player.moveX -= x;
            }
            else{
                player.moveX -= x*mult;
            }
            if(y*y2<=0){
                player.moveY -= y;
            }
            else{
                player.moveY -= y*mult;
            }
        }
        if(playerHealth<=0){
            updateDieActor(player);
        }
        else{
            player.moveY -= moveSpeed;
            player.setBounds(
                (int)(player.orgPoint.x+player.moveX), 
                (int)(player.orgPoint.y+player.moveY), 
                player.getWidth(), 
                player.getHeight()
                );
        }
    }
    
    public void updateEnemy(){
        for(int i=0;i<gm.getComponentCount();i++){
            if(gm.getComponent(i) instanceof GameEnemy ){
                GameEnemy enemy = (GameEnemy)gm.getComponent(i);
                countEnemy+=1;
                if(!parent.outSide(enemy)){
                    if(enemy.launchers==null){
                        gameObjects.add(enemy);
                        enemy.launchers = new Vector<Launcher>();
                        enemy.launchers.add(new Launcher(parent,gm, enemy));
                        enemy.moveY+=1;
                    }
                    enemy.shoot2Point(new Point(player.getX()+player.getWidth()/2,player.getY()));
                    updateLauncher(enemy.launchers);
                    if(parent.isTouch(enemy, player)){
                        enemy.health-=player.touchDamage;
                        playerHealth-=enemy.touchDamage;
                        //System.out.println("幹幹幹幹漏電");
                    }
                    if(enemy.health<=0){
                        updateDieActor(enemy);
                    }
                    else{
                        enemy.moveY -= moveSpeed;
                        if(enemy.booleanTemp&&(((int)(enemy.orgPoint.y+enemy.moveY)+gm.getLocation().y)-enemy.temp)!=0)
                        {
                            enemy.moveY-=(((int)(enemy.orgPoint.y+enemy.moveY)+gm.getLocation().y)-enemy.temp);
                        }
                        if(!enemy.die&&enemy.count<=0){
                            while(true){
                                if(enemy.index==enemy.line.points.size()-1){
                                    enemy.lineSpeedX = 0;
                                    enemy.lineSpeedY = 0;
                                    break;
                                }
                                enemy.p1 = enemy.line.points.get(enemy.index++);
                                enemy.p2 = enemy.line.points.get(enemy.index);
                                if((enemy.p2.t-enemy.p1.t)==0){
                                    continue;
                                }
                                double scale=this.parent.getWidth()/enemy.line.getWidth();
                                enemy.count=(enemy.p2.t-enemy.p1.t)/project.delay/enemy.speed;
                                enemy.lineSpeedX = (enemy.p2.x-enemy.p1.x)*scale/enemy.count;
                                enemy.lineSpeedY = (enemy.p2.y-enemy.p1.y)*scale/enemy.count;
                                if(enemy.lineSpeedX!=0||enemy.lineSpeedY!=0){
                                    break;
                                }
                            }
                        }
                        enemy.count--;
                        enemy.lineMoveX+=enemy.lineSpeedX;
                        enemy.lineMoveY+=enemy.lineSpeedY;
                        enemy.setBounds(
                            (int)(enemy.orgPoint.x+enemy.moveX+enemy.lineMoveX), 
                            (int)(enemy.orgPoint.y+enemy.moveY+enemy.lineMoveY),
                            enemy.getWidth(), 
                            enemy.getHeight()
                            );
                        enemy.temp = (int)(enemy.orgPoint.y+enemy.moveY)+gm.getLocation().y;
                        enemy.booleanTemp=true;
                        this.enemy = enemy;
                    }
                    if(parent.outSide(enemy)){
                        enemy.die=true;
                        gm.remove(enemy);
                    }
                }
            }
        }
        
    }
    
    public void updateLauncher(Vector<Launcher> launchers){
        boolean temp=true;
        for(int i=launchers.size()-1;i>=0;i--){
            launchers.get(i).update();
            for(int j=launchers.get(i).bullets.size()-1;j>=0;j--){
                for(GameObject gameOb:gameObjects){
                    if(!gameOb.die){
                        if(parent.isTouch(gameOb, launchers.get(i).bullets.get(j)))
                        {
                            if(gameOb instanceof GameEnemy){
                                if(launchers.get(i).actor instanceof GamePlayer){
                                    ((GameEnemy) gameOb).health-=launchers.get(i).actor.damage;
                                    launchers.get(i).bullets.get(j).remove();
                                    temp=false;
                                    break;
                                }
                            }
                            if(gameOb==player){
                                if(launchers.get(i).actor instanceof GameEnemy){       
                                    playerHealth-=launchers.get(i).actor.damage;
                                    launchers.get(i).bullets.get(j).remove();
                                    temp=false;
                                    break;
                                }                    
                            }
                        }
                    }
                }
                if(temp)
                    launchers.get(i).bullets.get(j).update();
                temp=true;
            }
        }  
    }
    
    public void updateGameObjects(){
        for(int i=gameObjects.size()-1;i>=0;i--){
            if(!gm.isAncestorOf(gameObjects.get(i))){
                if(gameObjects.get(i) instanceof Actor){
                    updateLauncher(((Actor) gameObjects.get(i)).launchers);
                    if(((Actor) gameObjects.get(i)).launchers.isEmpty()){
                        gameObjects.remove(gameObjects.get(i));
                    }
                }
            }
        }
    }
    
    public void updateDieActor(Actor actor){
        actor.die=true;
        actor.boomTime-=1;
        actor.speed=0;
        actor.touchDamage=0;
        actor.path = actor.boomPath;
        actor.setImg(actor.path);
        if(actor.boomTime<0)
            gm.remove(actor);
    
    }
    
    public void updateShowLabel(){
        playerHealth = (playerHealth<0)?0:playerHealth;
        int n = (int)(playerHealth*100/player.health);
        parent.showHealth.setText(n+"%");
    }
    
}
