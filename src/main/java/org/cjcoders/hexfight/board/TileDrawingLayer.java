package org.cjcoders.hexfight.board;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

/**
 * Created by mrakr_000 on 2014-05-21.
 */
public abstract class TileDrawingLayer {

    public static final TileDrawingLayer FINAL_LAYER = new FinalLayer();

    private TileDrawingLayer nextLayer;

    public TileDrawingLayer() {
        this(new FinalLayer());
    }
    protected TileDrawingLayer(TileDrawingLayer nextLayer){
        this.nextLayer = nextLayer;
    }

    public final void init(TileDrawing drawing){
        selfInit(drawing);
        initNextLayer(drawing);
    }

    protected void selfInit(TileDrawing drawing){}

    public final void render(TileDrawing tileDrawing, GUIContext container, Graphics g, int xOffset, int yOffset){
        selfRender(tileDrawing, container, g, xOffset, yOffset);
        drawNextLayer(tileDrawing, container, g, xOffset, yOffset);
    }

    protected void drawNextLayer(TileDrawing tileDrawing, GUIContext container, Graphics g, int xOffset, int yOffset) {
        nextLayer.render(tileDrawing, container, g, xOffset, yOffset);
    }
    protected void initNextLayer(TileDrawing tileDrawing) {
        nextLayer.init(tileDrawing);
    }

    protected abstract void selfRender(TileDrawing tileDrawing, GUIContext container, Graphics g, int xOffset, int yOffset);

    public TileDrawingLayer setNextLayer(TileDrawingLayer td){
        nextLayer = nextLayer.setNextLayer(td);
        return this;
    }

    private static class FinalLayer extends TileDrawingLayer {

        public FinalLayer(){
            super(null);
        }
        protected void selfRender(TileDrawing tileDrawing, GUIContext container, Graphics g, int xOffset, int yOffset) {}
        @Override
        protected void drawNextLayer(TileDrawing tileDrawing, GUIContext container, Graphics g, int xOffset, int yOffset) {}
        @Override
        protected void initNextLayer(TileDrawing tileDrawing){}
        @Override
        public TileDrawingLayer setNextLayer(TileDrawingLayer td) {
            return td;
        }
    }
}
