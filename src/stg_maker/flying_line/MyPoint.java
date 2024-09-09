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
public class MyPoint extends Point implements Serializable{ 
    public long t;
    
    public MyPoint(Point p,long t){
        super(p);
        this.t=t;
    }
}
