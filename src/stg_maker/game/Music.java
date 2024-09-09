/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.game;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author ASUS
 */
public class Music extends Thread{
    public String path;
    public boolean loop = true;
    boolean stop = false;
    AudioInputStream musicFile;
    Clip clip=null;
    File file;

    public Music(String s){
        path = s;
        if(path!=null)
        {
            try{
                file = new File(path);
                musicFile = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(musicFile);
            }catch(IOException ex){ex.printStackTrace();
            }catch (UnsupportedAudioFileException ex){ex.printStackTrace();
            }catch (LineUnavailableException ex) {ex.printStackTrace();}
        }
        
    }
    
    @Override
    public void run(){
        if(clip!=null)
        {
            if(loop){
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            else{
                clip.start();
            }
        }
    }
    
    public void stopMusic(){
        if(clip!=null)
            clip.stop();
    }
}
