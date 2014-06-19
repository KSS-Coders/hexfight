package org.cjforge.hexed.utils.components;

import org.apache.log4j.Logger;
import org.cjforge.hexed.utils.Hexagon;
import org.cjforge.hexed.context.Context;
import org.cjforge.hexed.states.play.GUIRequest;
import org.cjforge.hexed.utils.Profiler;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by mrakr_000 on 2014-06-02.
 */
public class DialogBox extends SimpleContent {

    private Logger l = Logger.getLogger(this.getClass());

    private String msg;
    private int max;
    private int min;
    private GUIRequest<Integer> request;

    private TextButton commit;
    private TextButton increment;
    private TextButton decrement;

    private Font font;
    private String commitTxt = "Commit";

    private Integer counter;

    public DialogBox(int width, int height) throws SlickException {
        super(width, height);
    }

    public void init(GUIContext context, StateBasedGame game, int stateId) throws SlickException {
        font = Context.getInstance().resources().getFont("forces-squared", 24);
        commit = new TextButton(context, font, commitTxt, 0,0, game, stateId);
        commit.addAction(new ButtonAction() {
            @Override
            public void performAction() {
                request.setResult(counter);
                request.setFinished(true);
            }
        });
        increment = new TextButton(context, font, "+",0,0, game, stateId);
        increment.addAction(new ButtonAction() {
            @Override
            public void performAction() {
                if(counter < max) counter++;
            }
        });
        decrement = new TextButton(context, font, "-",0,0, game, stateId);
        decrement.addAction(new ButtonAction() {
            @Override
            public void performAction() {
                if(counter > min) counter--;
            }
        });
        commit.setEnabled(true);
        increment.setEnabled(true);
        decrement.setEnabled(true);
    }

    @Override
    public void render(GUIContext container, Graphics g, Rectangle visibleArea) {
        Profiler p = new Profiler(this.getClass().getName(), Profiler.MICROS);
        p.start();
        p.setEnabled(false);
        int x = (int) visibleArea.getX();
        int y = (int) visibleArea.getY();
        Shape bg = new Hexagon(getWidth(), x, y);
        commit.setCenterLocation(x + getCenterX(), (int) (y + 0.8 * getHeight()));
        increment.setCenterLocation((int) (x + getWidth() * 0.9), y + getCenterY());
        decrement.setCenterLocation((int) (x + getWidth() * 0.1), y + getCenterY());
        g.fill(bg, new ShapeFill() {
            @Override
            public Color colorAt(Shape shape, float x, float y) {
                return new Color(0, 0, 0, (float) 0.8);
            }

            @Override
            public Vector2f getOffsetAt(Shape shape, float x, float y) {
                return new Vector2f(0, 0);
            }
        });
        font.drawString((float) (x - font.getWidth(msg) / 2.0 + getCenterX()), (float) (y + 0.3 * getHeight()), msg);
        font.drawString(x - font.getWidth(counter.toString())/2 + getCenterX(), y + getCenterY(), counter.toString());
        commit.render(container, g);
        increment.render(container, g);
        decrement.render(container, g);
        p.logFromStart("DialogBox render");
    }

    public GUIRequest<Integer> startRequest(String msg, int min, int max){
        this.msg = msg;
        this.min = min;
        this.max = max;
        counter = min;
        this.request = new GUIRequest<>();
        return request;
    }

}
