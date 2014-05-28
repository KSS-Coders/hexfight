package org.cjcoders.hexfight.board;

import org.cjcoders.hexfight.context.Context;
import org.cjcoders.hexfight.game.Player;
import org.cjcoders.hexfight.utils.TileCalculator;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;

/**
 * Created by mrakr_000 on 2014-05-21.
 */
public class TileDrawer {

    private static final Color[] colors = new Color[]{Color.blue, Color.green, Color.red, Color.yellow, Color.darkGray, Color.cyan};

    private Context context;
    private SpriteSheet tiles;
    private TileCalculator calculator;
    private boolean showGrid = true;

    public TileDrawer(TileCalculator calculator) {
        this.calculator = calculator;
        this.context = Context.getInstance();
        Image tiles = context.resources().getImage("tiles");
        this.tiles = new SpriteSheet(tiles, tiles.getHeight(), tiles.getHeight());
    }

    public TileDrawingLayer getDrawing(Tile tile) {
        TileDrawingLayer tileDrawing = TileDrawingLayer.FINAL_LAYER;
        if(tile.getTileNo() < tiles.getHorizontalCount()){
            Image img = tiles.getSubImage(tile.getTileNo(), 0);
            tileDrawing = tileDrawing.setNextLayer(new LocationLayer(img));
        }
        if(tile.isOwned()){
            tileDrawing = tileDrawing.setNextLayer(new OwnedTileLayer());
        }
        if(showGrid){
            tileDrawing = tileDrawing.setNextLayer(new GridLines());
        }
        if(tile.isActive()){
            tileDrawing = tileDrawing.setNextLayer(new ActiveLayer(tile.getOwner().getID()));
        }
        if(!tile.getForces().isEmpty()){
            tileDrawing = tileDrawing.setNextLayer(new EnforcedTileLayer());
        }
        return tileDrawing;
    }

    private Color playerColor(Player p){
        return colors[p.getID()];
    }

    public int getX(int x, int y) {
        return calculator.getScreenXFor(x, y);
    }

    public int getY(int x, int y) {
        return calculator.getScreenYFor(x, y);
    }

    public int getTileSize() {
        return calculator.getTileSize();
    }

    public int getCenterX(int x, int y) {
        return getX(x, y) + calculator.getTileSize()/2;
    }

    public int getCenterY(int x, int y) {
        return getY(x, y) + calculator.getTileSize()/2;
    }

    private class OwnedTileLayer extends TileDrawingLayer {
        private Image border;

        private OwnedTileLayer() {
            this.border = context.resources().getImage("hex-border");
        }

        @Override
        protected void selfRender(TileDrawing tileDrawing, GUIContext container, Graphics g, int xOffset, int yOffset) {
            g.drawImage(border, tileDrawing.getX() + xOffset, tileDrawing.getY() + yOffset, playerColor(tileDrawing.getTile().getOwner()));
        }

    }

    private class LocationLayer extends TileDrawingLayer {
        private final Image tileImg;

        public LocationLayer(Image tileImg) {
            this.tileImg = tileImg;
        }

        @Override
        protected void selfRender(TileDrawing tileDrawing, GUIContext container, Graphics g, int xOffset, int yOffset) {
            int x = tileDrawing.getCenterX() - tileImg.getWidth()/2 + xOffset;
            int y = tileDrawing.getCenterY() - tileImg.getHeight()/2 + yOffset;
            g.drawImage(tileImg, x+1, y+1);
        }
    }

    private class EnforcedTileLayer extends TileDrawingLayer {
        private Font font;

        @Override
        public void selfInit(TileDrawing drawing) {
            font = context.resources().getFont("forces-squared", getTileSize()/3);
        }

        @Override
        protected void selfRender(TileDrawing tileDrawing, GUIContext container, Graphics g, int xOffset, int yOffset) {
            String s = "" + tileDrawing.getTile().getForces().getStrength();
            int x = tileDrawing.getX() + (getTileSize() - font.getWidth(s))/2 + xOffset;
            int y = tileDrawing.getY() + (getTileSize() - font.getHeight(s))/2 + yOffset;
            font.drawString(x, y, s);
        }
    }

    private class GridLines extends TileDrawingLayer {
        @Override
        protected void selfRender(TileDrawing drawing, GUIContext container, Graphics g, int xOffset, int yOffset) {
            Shape shape = new Hexagon(drawing.getSize(), drawing.getX() + xOffset, drawing.getY() + yOffset);
            g.draw(shape, new ShapeFill() {
                @Override
                public Color colorAt(Shape shape, float x, float y) {
                    return Color.white;
                }

                @Override
                public Vector2f getOffsetAt(Shape shape, float x, float y) {
                    return new Vector2f(new float[]{0,0});
                }
            });
        }
    }

    private class ActiveLayer extends TileDrawingLayer {
        private final SpriteSheet tileImg;

        public ActiveLayer(int num){
            tileImg = new SpriteSheet(Context.getInstance().resources().getImage("units"),70,70);
        }

        @Override
        protected void selfRender(TileDrawing tileDrawing, GUIContext container, Graphics g, int xOffset, int yOffset) {
            Image img = tileImg.getSubImage(tileDrawing.getTile().getOwner().getID(),0);
            int x = tileDrawing.getCenterX() - img.getWidth()/2 + xOffset;
            int y = tileDrawing.getCenterY() - img.getHeight()/2 + yOffset;
            g.drawImage(img, x, y);
        }
    }
}
