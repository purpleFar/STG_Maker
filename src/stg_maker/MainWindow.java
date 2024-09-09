/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stg_maker;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import stg_maker.GameMaker;

/**
 *
 * @author ASUS
 */
public class MainWindow extends JFrame{
    public Dimension screenSize;
    MainWindow(GameMaker gm)
    {
        super();
        screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, (int)screenSize.getWidth(),  (int)screenSize.getHeight()-40);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setTitle(gm.swTitle+" (version " + gm.version+")");

        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
