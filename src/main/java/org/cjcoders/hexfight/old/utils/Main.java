package org.cjcoders.hexfight.old.utils;

import org.cjcoders.hexfight.old.MainFrame;

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
