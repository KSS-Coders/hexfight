package org.cjcoders.hexfight.old;

import org.cjcoders.hexfight.board.TileChangedEvent;
import org.cjcoders.hexfight.board.TileChangedListener;
import org.cjcoders.hexfight.old.utils.Point;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mrakr_000 on 30.04.14.
 */
public class BoardDrawer implements ChangeListener, SideChangedListener, TileChangedListener {

    private final TileDrawer tileDrawer;
    private Set<OffscreenChangedListener> offscreenChangedListeners;

    public void addOffscreenChangedListener(OffscreenChangedListener listener){
        offscreenChangedListeners.add(listener);
    }
    private void fireOffscreenChanged() {
        for(OffscreenChangedListener listener : offscreenChangedListeners){
            listener.performOffscreenchanged();
        }
    }

    private BufferedImage offScreen;
    private BoardDrawingAssistant assistant;

    public BoardDrawer(BoardDrawingAssistant assistant){
        offscreenChangedListeners = new HashSet<>();
        tileDrawer = new TileDrawer(assistant);
        this.assistant = assistant;
        generateOffScreen();
        assistant.addSideChangedListener(this);
        assistant.addTileChangedListener(this);
    }

    public void paintBoard(Graphics g){
        Graphics2D g2 = (Graphics2D)g;

        g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        for(TileDrawing tile : assistant.getAllTiles()){
            tileDrawer.drawTile(tile, g2);
        }
    }

    // TODO Return generated off screen image
    public Image getOffScreen(int x, int y, int w, int h) {
        int boardWidth = (int) (assistant.getScale()*4*w);
        boardWidth = boardWidth > offScreen.getWidth() ? offScreen.getWidth()-x : boardWidth;
        int boardHeight = (int) (assistant.getScale()*4*h);
        boardHeight = boardHeight > offScreen.getHeight() ? offScreen.getHeight()-y : boardHeight;
//        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//
//
//        g.drawRenderableImage((java.awt.image.renderable.RenderableImage) offScreen.getSubimage(x, y, boardWidth, boardHeight), at);
//        return offScreen.getSubimage(x, y, boardWidth, boardHeight);
        BufferedImage off = offScreen.getSubimage(x, y, 4*w, 4*h);
        BufferedImage img = new BufferedImage(w*4, h*4, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        AffineTransform at = AffineTransform.getScaleInstance(assistant.getScale(),assistant.getScale());
        g.drawRenderedImage(off, at);
        return img.getSubimage(0,0,w,h);
    }

    void generateOffScreen() {
        System.out.println("Drawing");
        offScreen = new BufferedImage((int)(1.1 * assistant.getBoardWidth()), (int)(1.1 * assistant.getBoardHeight()), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = offScreen.createGraphics();
        paintBoard(g);
    }

    @Override
    public void update(int changedValue) {
        if(changedValue == TilesSettings.SIDE_SIZE_CHANGED)generateOffScreen();
    }

    public Point getOffScreenCenter(){
        return new Point(assistant.getBoardWidth()/2, assistant.getBoardHeight()/2);
    }

    @Override
    public void performSideChanged() {
 //       generateOffScreen();
        fireOffscreenChanged();
    }

    @Override
    public void performTileChanged(TileChangedEvent e) {
        generateOffScreen();
        fireOffscreenChanged();
    }
}
