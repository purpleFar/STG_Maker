/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker.object;

import stg_maker.GameMaker;
import stg_maker.GameMap;

/**
 *
 * @author ASUS
 */
public class GameBackgroundObject extends GameObject{
    public GameBackgroundObject(GameMaker parent, GameMap gm){
        super(parent, gm);
        name = "背景物件";
    }
}
