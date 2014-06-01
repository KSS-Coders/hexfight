package org.cjcoders.hexfight.board;

import org.cjcoders.hexfight.context.Context;
import org.cjcoders.hexfight.game.Player;
import org.cjcoders.hexfight.utils.Profiler;
import org.cjcoders.hexfight.utils.TileCalculator;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;

import java.util.*;

/**
 * Created by mrakr_000 on 2014-05-21.
 */
public class TileDrawer {

    private static final Color[] colors = new Color[]{Color.blue, Color.green, Color.red, Color.yellow, Color.darkGray, Color.cyan};

    private Context context;
    private SpriteSheet tiles;
    private TileCalculator calculator;
    private boolean showGrid = true;

    private TileDrawingLayer[] locations;
    private TileDrawingLayer[] units;
    private final TileDrawingLayer GRID_LAYER;
    private final TileDrawingLayer ACTIVE_LAYER;
    private final TileDrawingLayer OWNED_LAYER;
    private final TileDrawingLayer FORCES_LAYER;

    public TileDrawer(TileCalculator calculator) {
        this.calculator = calculator;
        this.context = Context.getInstance();
        Image tiles = context.resources().getImage("tiles");
        this.tiles = new SpriteSheet(tiles, tiles.getHeight(), tiles.getHeight());
        GRID_LAYER = new GridLines();
        ACTIVE_LAYER = new ActiveLayer();
        OWNED_LAYER = new OwnedTileLayer();
        FORCES_LAYER = new EnforcedTileLayer();
        locations = new TileDrawingLayer[tiles.getWidth()];
        for(int i = 0; i < this.tiles.getHorizontalCount(); ++i){
            locations[i] = new LocationLayer(this.tiles.getSubImage(i, 0));
        }
    }

    public Collection<TileDrawingLayer> getDrawing(Tile tile) {
        Profiler p = new Profiler("TileDrawer", Profiler.MICROS);
        p.start();
        Collection<TileDrawingLayer> tileDrawing = new ArrayList<>(10);
        p.log("Create array");
        if(tile.getTileNo() < tiles.getHorizontalCount()){
            tileDrawing.add(locations[tile.getTileNo()]);
            p.log("Add location");
        }
        if(tile.isOwned()){
            tileDrawing.add(OWNED_LAYER);
            p.log("Add owned");
        }
        if(showGrid){
            tileDrawing.add(GRID_LAYER);
            p.log("Add grid");
        }
        if(tile.isActive()){
            tileDrawing.add(ACTIVE_LAYER);
            p.log("Add active");
        }
        if(!tile.getForces().isEmpty()){
            tileDrawing.add(FORCES_LAYER);
            p.log("Add forces");
        }
        p.logFromStart("Whole method");
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
        public void render(TileDrawing tileDrawing, GUIContext container, Graphics g, int xOffset, int yOffset) {
            g.drawImage(border, tileDrawing.getX() + xOffset, tileDrawing.getY() + yOffset, playerColor(tileDrawing.getTile().getOwner()));
        }
    }

    private class LocationLayer extends TileDrawingLayer {
        private final Image tileImg;

        public LocationLayer(Image tileImg) {
            this.tileImg = tileImg;
        }

        @Override
        public void render(TileDrawing tileDrawing, GUIContext container, Graphics g, int xOffset, int yOffset) {
            int x = tileDrawing.getCenterX() - tileImg.getWidth()/2 + xOffset;
            int y = tileDrawing.getCenterY() - tileImg.getHeight()/2 + yOffset;
            g.drawImage(tileImg, x+1, y+1);
        }
    }

    private class EnforcedTileLayer extends TileDrawingLayer {
        private Font font;

        public EnforcedTileLayer() {
            font = context.resources().getFont("forces-squared", getTileSize()/2);
        }

        @Override
        public void render(TileDrawing tileDrawing, GUIContext container, Graphics g, int xOffset, int yOffset) {
            String s = "" + tileDrawing.getTile().getForces().getStrength();
            int x = tileDrawing.getX() + (getTileSize() - font.getWidth(s))/2 + xOffset;
            int y = tileDrawing.getY() + (getTileSize() - font.getHeight(s))/2 + yOffset;
            font.drawString(x, y, s);
        }
    }

    private class GridLines extends TileDrawingLayer {
        @Override
        public void render(TileDrawing drawing, GUIContext container, Graphics g, int xOffset, int yOffset) {
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
        int i;

        public ActiveLayer(){
            tileImg = new SpriteSheet(Context.getInstance().resources().getImage("units"),70,70);
            i = new Random().nextInt(6);
        }

        @Override
        public void render(TileDrawing tileDrawing, GUIContext container, Graphics g, int xOffset, int yOffset) {
            Image img = tileImg.getSubImage(i,0);
            int x = tileDrawing.getCenterX() - img.getWidth()/2 + xOffset;
            int y = tileDrawing.getCenterY() - img.getHeight()/2 + yOffset;
            g.drawImage(img, x, y);
        }
    }
}
