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
public class GamePropObject extends GameObject{
    public GamePropObject(GameMaker parent, GameMap gm){
        super(parent, gm);
        name = "道具";
    }
}
