/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.flying_line;

import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class Line implements Serializable{ 
    public Point sp;
    public Point ep;
    public long st;
    public long et;
    
    public Line(Point s, Point e,long stime,long etime)
    {
        sp=s;
        ep=e;
        st = stime;
        et = etime;
    }
}
