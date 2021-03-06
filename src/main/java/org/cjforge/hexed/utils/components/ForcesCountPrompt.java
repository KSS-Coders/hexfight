package org.cjforge.hexed.utils.components;

import org.apache.log4j.Logger;
import org.cjforge.hexed.context.Context;
import org.cjforge.hexed.states.play.GUIRequest;
import org.cjforge.hexed.utils.ColorFill;
import org.cjforge.hexed.utils.Hexagon;
import org.cjforge.hexed.utils.Profiler;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by mrakr_000 on 2014-06-19.
 */
public class ForcesCountPrompt extends SimpleContent {

    private Logger l = Logger.getLogger(this.getClass());

    private String msg;
    private int max;
    private int min;
    private GUIRequest<Integer> request;

    private TextButton commit;
    private TextButton increment;
    private TextButton decrement;

    private Font font;
    private String commitTxt = "Move";

    private Integer counter;

    public ForcesCountPrompt(GUIContext context, StateBasedGame game, int width, int height) throws SlickException {
        super(width, height);
        font = Context.getInstance().resources().getFont("forces-squared", 24);
        commit = new TextButton(context, font, commitTxt, 0, 0, game);
        commit.addAction(new ButtonAction() {
            @Override
            public void performAction() {
                request.setResult(counter);
                request.setFinished(true);
            }
        });
        increment = new TextButton(context, font, "+", 0, 0, game);
        increment.addAction(new ButtonAction() {
            @Override
            public void performAction() {
                if (counter < max) counter++;
            }
        });
        decrement = new TextButton(context, font, "-", 0, 0, game);
        decrement.addAction(new ButtonAction() {
            @Override
            public void performAction() {
                if (counter > min) counter--;
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
        g.fill(bg, new ColorFill(new Color(0, 0, 0, 0.8f)));
        font.drawString((float) (x - font.getWidth(msg) / 2.0 + getCenterX()), (float) (y + 0.3 * getHeight()), msg);
        font.drawString(x - font.getWidth(counter.toString()) / 2 + getCenterX(), y + getCenterY(), counter.toString());
        commit.render(container, g);
        increment.render(container, g);
        decrement.render(container, g);
        p.logFromStart("DialogBox render");
    }

    public GUIRequest<Integer> startRequest(String msg, int min, int max) {
        this.msg = msg;
        this.min = min;
        this.max = max;
        counter = min;
        this.request = new GUIRequest<>();
        return request;
    }

}