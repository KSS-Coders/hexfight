package org.cjforge.hexed.states.play.board;

import org.apache.log4j.Logger;
import org.cjforge.hexed.context.Context;
import org.cjforge.hexed.game.Player;
import org.cjforge.hexed.game.PlayerColors;
import org.cjforge.hexed.game.Tile;
import org.cjforge.hexed.utils.ColorFill;
import org.cjforge.hexed.utils.Hexagon;
import org.cjforge.hexed.utils.Profiler;
import org.cjforge.hexed.utils.TileCalculator;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by mrakr_000 on 2014-05-21.
 */
public class MainTileDrawer implements TileDrawer {

    private final TileDrawingLayer GRID_LAYER;
    private final TileDrawingLayer ACTIVE_LAYER;
    private final TileDrawingLayer OWNED_LAYER;
    private final TileDrawingLayer FORCES_LAYER;
    private final TileDrawingLayer EXHAUSTED_LAYER;
    private Logger l = Logger.getLogger(this.getClass());
    private Context context;
    private TileCalculator calculator;
    private boolean showGrid = true;
    private TileDrawingLayer[] locations;
    private TileDrawingLayer[] units;

    public MainTileDrawer(TileCalculator calculator) {
        this.calculator = calculator;
        this.context = Context.getInstance();
        Image tilesImg = context.resources().getImage("tiles");
        SpriteSheet tiles = new SpriteSheet(tilesImg, tilesImg.getHeight(), tilesImg.getHeight());
        Image unitsImg = context.resources().getImage("units");
        SpriteSheet unitsSs = new SpriteSheet(unitsImg, unitsImg.getHeight(), unitsImg.getHeight());
        GRID_LAYER = new GridLines();
        ACTIVE_LAYER = new ActiveLayer();
        OWNED_LAYER = new OwnedTileLayer();
        FORCES_LAYER = new EnforcedTileLayer();
        EXHAUSTED_LAYER = new ExhaustedLayer();
        locations = new TileDrawingLayer[tiles.getHorizontalCount()];
        for (int i = 0; i < tiles.getHorizontalCount(); ++i) {
            locations[i] = new LocationLayer(tiles.getSubImage(i, 0));
        }
        units = new TileDrawingLayer[unitsSs.getHorizontalCount()];
        for (int i = 0; i < unitsSs.getHorizontalCount(); ++i) {
            units[i] = new UnitLayer(unitsSs.getSubImage(i, 0));
        }
    }

    @Override
    public Shape getShape(int x, int y) {
        return calculator.getShape(x, y);
    }

    @Override
    public Collection<TileDrawingLayer> getDrawing(Tile tile) {
        Profiler p = new Profiler("TileDrawer", Profiler.MICROS);
        p.setEnabled(false);
        p.start();
        Collection<TileDrawingLayer> tileDrawing = new ArrayList<>(10);
        p.log("Create array");
        if (tile.isOwned()) {
            tileDrawing.add(OWNED_LAYER);
            p.log("Add owned");
        }
        if (tile.isPlanet()) {
            tileDrawing.add(locations[tile.getTileNo()]);
            p.log("Add location");
        } else if(tile.hasFleet()) {
            tileDrawing.add(units[tile.getOwner().getID()]);
            p.log("Add fleet");
        }
        if (showGrid) {
            tileDrawing.add(GRID_LAYER);
            p.log("Add grid");
        }
        if (tile.isActive()) {
            tileDrawing.add(ACTIVE_LAYER);
            p.log("Add active");
        }
        if (!tile.getForces().isEmpty() || tile.isOwned()) {
            tileDrawing.add(FORCES_LAYER);
            p.log("Add forces");
        }
        if(tile.isExhausted()){
            tileDrawing.add(EXHAUSTED_LAYER);
        }
        p.logFromStart("Whole method");
        return tileDrawing;
    }

    private Color playerColor(Player p) {
        return PlayerColors.getColorForPlayer(p);
    }

    @Override
    public int getX(int x, int y) {
        return calculator.getScreenXFor(x, y);
    }

    @Override
    public int getY(int x, int y) {
        return calculator.getScreenYFor(x, y);
    }

    @Override
    public int getTileSize() {
        return calculator.getTileSize();
    }

    @Override
    public int getCenterX(int x, int y) {
        return getX(x, y) + calculator.getTileSize() / 2;
    }

    @Override
    public int getCenterY(int x, int y) {
        return getY(x, y) + calculator.getTileSize() / 2;
    }

    private class UnitLayer extends TileDrawingLayer {

        private final Image unitImg;

        public UnitLayer(Image unitImg) {
            this.unitImg = unitImg;
        }

        @Override
        public void render(TileDrawing tileDrawing, GUIContext container, Graphics g, int xOffset, int yOffset) {
            int x = tileDrawing.getCenterX() - unitImg.getWidth() / 2 - xOffset;
            int y = tileDrawing.getCenterY() - unitImg.getHeight() / 2 - yOffset;
            g.drawImage(unitImg, x, y);
        }
    }

    private class OwnedTileLayer extends TileDrawingLayer {
        private final Image border;

        private OwnedTileLayer() {
            this.border = context.resources().getImage("hex-border");
        }

        @Override
        public void render(TileDrawing tileDrawing, GUIContext container, Graphics g, int xOffset, int yOffset) {
            g.drawImage(border, tileDrawing.getX() - xOffset, tileDrawing.getY() - yOffset, playerColor(tileDrawing.getTile().getOwner()));
        }
    }

    private class LocationLayer extends TileDrawingLayer {
        private final Image tileImg;

        public LocationLayer(Image tileImg) {
            this.tileImg = tileImg;
        }

        @Override
        public void render(TileDrawing tileDrawing, GUIContext container, Graphics g, int xOffset, int yOffset) {
            int x = tileDrawing.getCenterX() - tileImg.getWidth() / 2 - xOffset;
            int y = tileDrawing.getCenterY() - tileImg.getHeight() / 2 - yOffset;
            g.drawImage(tileImg, x, y);
        }
    }

    private class EnforcedTileLayer extends TileDrawingLayer {
        private final Font font;

        public EnforcedTileLayer() {
            font = context.resources().getFont("forces-squared", (int) (getTileSize() / 2.5));
        }

        @Override
        public void render(TileDrawing tileDrawing, GUIContext container, Graphics g, int xOffset, int yOffset) {
            String s = "" + tileDrawing.getTile().getForces().getStrength();
            int x = tileDrawing.getX() + (getTileSize() - font.getWidth(s)) / 2 - xOffset;
            int y = tileDrawing.getY() + (getTileSize() - font.getHeight(s)) / 2 - yOffset;
            font.drawString(x, y, s);
        }
    }

    private class GridLines extends TileDrawingLayer {
        @Override
        public void render(TileDrawing drawing, GUIContext container, Graphics g, int xOffset, int yOffset) {
            Shape shape = new Hexagon(drawing.getSize(), drawing.getX() - xOffset, drawing.getY() - yOffset);
            g.draw(shape, new ShapeFill() {
                @Override
                public Color colorAt(Shape shape, float x, float y) {
                    return Color.white;
                }

                @Override
                public Vector2f getOffsetAt(Shape shape, float x, float y) {
                    return new Vector2f(new float[]{0, 0});
                }
            });
        }
    }

    private class ActiveLayer extends TileDrawingLayer {
        private final ColorFill c;

        public ActiveLayer() {
            c = new ColorFill(new Color(255, 255, 255, 75));
        }

        @Override
        public void render(TileDrawing tileDrawing, GUIContext container, Graphics g, int xOffset, int yOffset) {
            int x = tileDrawing.getX() - xOffset;
            int y = tileDrawing.getY() - yOffset;
            g.fill(new Hexagon(getTileSize(), x, y), c);
        }
    }

    private class ExhaustedLayer extends TileDrawingLayer {
        private final ColorFill c;

        public ExhaustedLayer() {
            c = new ColorFill(new Color(50, 50, 50, 150));
        }

        @Override
        public void render(TileDrawing tileDrawing, GUIContext container, Graphics g, int xOffset, int yOffset) {
            int x = tileDrawing.getX() - xOffset;
            int y = tileDrawing.getY() - yOffset;
            g.fill(new Hexagon(getTileSize(), x, y), c);
        }
    }
}
