package org.cjcoders.hexfight;

import org.cjcoders.hexfight.gui.MainFrame;

import javax.swing.*;

/**
 * Entry point to Hexfight game.
 *
 * @author Szymon Janota
 */
public class Main {
    public static void main(String... args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame();
            }
        });
    }
}
