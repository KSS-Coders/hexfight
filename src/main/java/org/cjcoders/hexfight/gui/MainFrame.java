package org.cjcoders.hexfight.gui;

import net.miginfocom.swing.MigLayout;
import org.cjcoders.hexfight.board.Board;
import org.cjcoders.hexfight.gui.board.BoardPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main frame for Hexfight game.
 *
 * @author Szymon Janota
 */
public class MainFrame extends JFrame{

    /*=============================================
        CONSTANTS
     ============================================*/
    /** Default frame size. */
    public final static int FRAME_SIZE = 500;

    /*=============================================
        COMPONENTS
     ============================================*/
    /** Panel containing game board. */
    private BoardPanel boardPanel;

    /*=============================================
        OTHER STATE VARIABLES
     ============================================*/
    /** Indicates fullscreen mode */
    private boolean fullScreen = true;

    /*=============================================
        CONSTRUCTORS
     ============================================*/
    public MainFrame(){
        super("Hexfight");
        boardPanel = new BoardPanel();
        boardPanel.setBoard(new Board(new Dimension(5, 5)));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new MigLayout("","30[]30","50[]50"));
        add(boardPanel,"push, grow, wrap");
        JButton switchFs = new JButton("Switch fullscreen");
        switchFs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFullScreen(!fullScreen);
            }
        });
        add(switchFs);
        setFullScreen(fullScreen);
        setVisible(true);
    }

    /*=============================================
        OTHER METHODS
     ============================================*/
    public void start() {

    }

    /*=============================================
        GETTERS AND SETTERS
     ============================================*/
    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    /**
     * Set fullscreen mode for main frame.
     * @param fullScreen indicates if fullscreen should be enabled or disabled
     */
    public void setFullScreen(boolean fullScreen){
        dispose();
        this.fullScreen = fullScreen;
        if(fullScreen){
            setLocation(0, 0);
            setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize());
        }
        else setSize(FRAME_SIZE, FRAME_SIZE);
        setUndecorated(fullScreen);
        setResizable(!fullScreen);
        setVisible(true);
    }
}
