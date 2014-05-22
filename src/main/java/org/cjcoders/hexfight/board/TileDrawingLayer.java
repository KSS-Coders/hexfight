package org.cjcoders.hexfight.board;

import org.apache.log4j.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

/**
 * Created by mrakr_000 on 2014-05-21.
 */
public abstract class TileDrawingLayer {
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

    public final void render(TileDrawing tileDrawing, GUIContext container, Graphics g){
        selfRender(tileDrawing, container, g);
        drawNextLayer(tileDrawing, container, g);
    }

    protected void drawNextLayer(TileDrawing tileDrawing, GUIContext container, Graphics g) {
        nextLayer.render(tileDrawing, container, g);
    }
    protected void initNextLayer(TileDrawing tileDrawing) {
        nextLayer.init(tileDrawing);
    }

    protected abstract void selfRender(TileDrawing tileDrawing, GUIContext container, Graphics g);

    public TileDrawingLayer setNextLayer(TileDrawingLayer td){
        nextLayer = nextLayer.setNextLayer(td);
        return this;
    }

    static class FinalLayer extends TileDrawingLayer {

        public FinalLayer(){
            super(null);
        }

        Logger l = Logger.getLogger(this.getClass().getName());
        protected void selfRender(TileDrawing tileDrawing, GUIContext container, Graphics g) {}

        @Override
        protected void drawNextLayer(TileDrawing tileDrawing, GUIContext container, Graphics g) {}

        @Override
        protected void initNextLayer(TileDrawing tileDrawing){}

        @Override
        public TileDrawingLayer setNextLayer(TileDrawingLayer td) {
            return td;
        }
    }
}
