package org.cjcoders.hexfight.board;

import org.cjcoders.hexfight.context.Context;
import org.cjcoders.hexfight.game.Player;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;

/**
 * Created by mrakr_000 on 2014-05-21.
 */
public class TileDrawer {

    private static final Color[] colors = new Color[]{Color.blue, Color.green, Color.red, Color.yellow};

    private Context context;
    private SpriteSheet tiles;
    private boolean showGrid = true;

    public TileDrawer() {
        this.context = Context.getInstance();
        Image tiles = context.resources().getImage("tiles");
        this.tiles = new SpriteSheet(tiles, tiles.getHeight(), tiles.getHeight());
    }

    public TileDrawingLayer getDrawing(Tile tile) {
        TileDrawingLayer tileDrawing = TileDrawingLayer.FINAL_LAYER;
        if(tile.getTileNo() < tiles.getHorizontalCount()){
            Image img = tiles.getSubImage(tile.getTileNo(), 0);
            tileDrawing = tileDrawing.setNextLayer(new LocationLayer(img));
            if(tile.isOwned()){
                tileDrawing = tileDrawing.setNextLayer(new OwnedTileLayer());
            }
            if(!tile.getForces().isEmpty()){
                tileDrawing = tileDrawing.setNextLayer(new EnforcedTileLayer());
            }
        }
        if(showGrid){
            tileDrawing = tileDrawing.setNextLayer(new GridLines());
        }
        return tileDrawing;
    }

    private Color playerColor(Player p){
        return colors[p.getID()];
    }

    private class OwnedTileLayer extends TileDrawingLayer {
        private Image border;

        private OwnedTileLayer() {
            this.border = context.resources().getImage("hex-border").getScaledCopy(2);
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
            font = context.resources().getFont("forces-squared", drawing.getHeight()/3);
        }

        @Override
        protected void selfRender(TileDrawing tileDrawing, GUIContext container, Graphics g, int xOffset, int yOffset) {
            String s = "" + tileDrawing.getTile().getForces().getStrength();
            int x = tileDrawing.getX() + (tileDrawing.getWidth() - font.getWidth(s))/2 + xOffset;
            int y = tileDrawing.getY() + (tileDrawing.getHeight() - font.getHeight(s))/2 + yOffset;
            font.drawString(x, y, s);
        }
    }

    private class GridLines extends TileDrawingLayer {
        @Override
        protected void selfRender(TileDrawing drawing, GUIContext container, Graphics g, int xOffset, int yOffset) {
            Shape shape = new Hexagon(drawing.getWidth(), drawing.getX() + xOffset, drawing.getY() + yOffset);
            g.draw(shape, new ShapeFill() {
                @Override
                public Color colorAt(Shape shape, float x, float y) {
                    return Color.white;
                }

                @Override
                public Vector2f getOffsetAt(Shape shape, float x, float y) {
                    return new Vector2f(new float[]{1,0});
                }
            });
        }
    }
}
