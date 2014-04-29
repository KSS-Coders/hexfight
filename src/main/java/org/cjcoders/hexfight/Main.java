package org.cjcoders.hexfight;

import org.cjcoders.hexfight.gui.MainFrame;

import javax.swing.*;

/**
 * Created by mrakr_000 on 27.04.14.
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
