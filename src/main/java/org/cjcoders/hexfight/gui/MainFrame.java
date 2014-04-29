package org.cjcoders.hexfight.gui;

import net.miginfocom.swing.MigLayout;
import org.cjcoders.hexfight.board.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mrakr_000 on 27.04.14.
 */
public class MainFrame extends JFrame{

    final static int SCRSIZE = 500; //screen size (vertical dimension).
    boolean fullScreen = true;

    void setFullScreen(boolean fullScreen){
        dispose();
        this.fullScreen = fullScreen;
        if(fullScreen){
            setLocation(0, 0);
            setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize());
        }
        else setSize(SCRSIZE,SCRSIZE);
        setUndecorated(fullScreen);
        setVisible(true);
    }

    public MainFrame(){
        super("Tile Testing");
        board = new Board(new Dimension(100,100),40);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new MigLayout("","30[]30","50[]50"));
        add(board,"push, grow, wrap");
        setResizable(!fullScreen);
        setUndecorated(fullScreen);
        setFullScreen(fullScreen);
        JButton switchFs = new JButton("Switch fullscreen");
        switchFs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFullScreen(!fullScreen);
            }
        });
        add(switchFs);
        setVisible(true);
    }

    private Board board;

    public void start() {

    }

    public Board getBoard() {
        return board;
    }
}
