package org.cjcoders.hexfight.old;

import org.cjcoders.hexfight.old.utils.Point;

import javax.swing.*;
import java.awt.*;

/**
 * Panel that contains game board.
 * This is component responsible for drawing game board on the screen.
 *
 * @author Szymon Janota
 */
public class BoardPanel extends JPanel implements OffsetChangedListener, OffscreenChangedListener {

    /*=============================================
        CONSTANTS
     ============================================*/
    /** Background color. */
    public static final Color BGCOLOR = Color.WHITE;
    public static final int DEFAULT_SIDE_SIZE = 20;
    /** Minimum size of single hexagon's side in pixels. */
    public static final int MIN_SIDE_SIZE = 10;
    /** Maximum size of single hexagon's side in pixels. */
    public static final int MAX_SIDE_SIZE = 60;

    /*=============================================
        FIELDS
     ============================================*/
    /** Board that will be displayed on this panel. */
    private BoardDrawingAssistant assistant;
    private final OffScreenPosition position;
    private final BoardDrawer boardDrawer;
    /** Offset of the upper left corner of the board from off screen upper left corner. */
    private Point offset;
    private int paddingX;
    private int paddingY;

    public BoardPanel(BoardDrawingAssistant assistant) {
        this.assistant = assistant;
        this.position = new OffScreenPosition();
        BoardMouseListener ml = new BoardMouseListener(assistant, position);
        boardDrawer = new BoardDrawer(assistant);
        boardDrawer.addOffscreenChangedListener(this);
        addMouseListener(ml);
        addMouseMotionListener(ml);
        addMouseWheelListener(ml);
        position.addOffsetChangedListener(this);
    }

    /*=============================================
        METHODS
     ============================================*/
    /** Paint off screen image of board. */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        setBackground(BGCOLOR);
//        log("Offset : "+offset);
//        log("Screen size : "+ new Point(Math.min(getBoardWidth() - 1, getWidth()), Math.min(getBoardHight() - 1, getHeight())));
//        log("Image size : "+ new Point(getBoardWidth(),getBoardHight() ));
//        log("End point : "+boardDrawer.getOffScreenCenter().add(offset));
//        log("X : ");
        Object o = null;
        log("1 "+o);
        log("2 "+position.getOffset().x);
        log("3 "+position.getOffset().y);
        log("4 "+assistant.getBoardWidth());
        log("5 "+assistant.getBoardHeight());
        log("6 "+getWidth());
        log("7 "+getHeight());
        log("8 "+boardDrawer);
        Graphics2D g2 = (Graphics2D)g;
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        Image boardOffScreen = boardDrawer.getOffScreen(
                -position.getOffset().x,
                -position.getOffset().y,
                Math.min(assistant.getBoardWidth(), getWidth()),
                Math.min(assistant.getBoardHeight(), getHeight()));
        g2.drawImage(boardOffScreen, paddingX, paddingY, Math.min(assistant.getBoardWidth(), getWidth()), Math.min(assistant.getBoardHeight(), getHeight()), BGCOLOR, null);
    }

    public Point getCenteredPoint(){
        return screenCenter().add(offset.negative());
    }
    public void setCenteredPoint(Point pointOnImage){
        setOffset(pointOnImage.add(screenCenter().negative()));
    }
    public void centerScreen(){
        setCenteredPoint(boardDrawer.getOffScreenCenter());
    }
    private Point screenCenter(){ return new Point(getWidth()/2, getHeight()/2); }


    /*=============================================
        GETTERS AND SETTERS
     ============================================*/

    public void setOffset(Point offset) {
        //log(fitsScreenVertically());
        if(fitsScreenVertically()) offset = centerBoardX(offset);
        else{
            paddingX = 0;
            offset = applyMinOffsetX(applyMaxOffsetX(offset));
        }
        //log(fitsScreenHorizontally());
        if(fitsScreenHorizontally()) offset = centerBoardY(offset);
        else{
            paddingY = 0;
            offset = applyMaxOffsetY(applyMinOffsetY(offset));
        }
        //log(offset);
        position.setOffset(offset);
    }
    public void updateOffset(int xOffset, int yOffset){
        setOffset(new Point(offset.x + xOffset, offset.y + yOffset));
    }
    private Point centerBoardX(Point offset){
        paddingX = (getWidth() - assistant.getBoardWidth())/2;
        return offset.setX(0);
    }
    private Point centerBoardY(Point offset){
        paddingY = (getHeight() - assistant.getBoardHeight())/2;
        return offset.setY(0);
    }


    private boolean fitsScreenVertically(){ /*log(getWidth()+" ; "+tilesSettings.calculateBoardWidth(board.getSize().width));*/ return getWidth() > assistant.getBoardWidth(); }
    private boolean fitsScreenHorizontally(){ return getHeight() > assistant.getBoardHeight(); }
    private Point applyMinOffsetX(Point offset){
        if(offset.x > 0) return offset.setX(0);
        return offset;
    }
    private Point applyMaxOffsetX(Point offset){
        int maxX = assistant.getBoardWidth() - getWidth();
        if(-offset.x > maxX) return offset.setX(-maxX);
        return offset;
    }
    private Point applyMinOffsetY(Point offset){
        if(offset.y > 0) return offset.setY(0);
        return offset;
    }
    private Point applyMaxOffsetY(Point offset){
        int maxY = assistant.getBoardHeight() - getHeight();
        if(-offset.y > maxY) return offset.setY(-maxY);
        return offset;
    }
    public void log(Object o){
        System.out.println(o);
    }

    @Override
    public void performOffsetChanged(OffsetChangedEvent e) {
        repaint();
    }

    @Override
    public void performOffscreenchanged() {
        repaint();
    }
}
