package org.cjforge.hexed.states.play.board;

import org.apache.log4j.Logger;
import org.cjforge.hexed.context.Context;
import org.cjforge.hexed.states.play.GUIRequest;
import org.cjforge.hexed.utils.ColorFill;
import org.cjforge.hexed.utils.Hexagon;
import org.cjforge.hexed.utils.Profiler;
import org.cjforge.hexed.utils.components.ButtonAction;
import org.cjforge.hexed.utils.components.SimpleContent;
import org.cjforge.hexed.utils.components.TextButton;
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

    private String msg1;
    private String msg2;
    private int maxVal;
    private int minVal;
    private GUIRequest<Integer> request;

    private TextButton commit;
    private TextButton increment;
    private TextButton max;
    private TextButton decrement;
    private TextButton min;

    private Font font;

    private Integer counter;

    public ForcesCountPrompt(GUIContext context, StateBasedGame game, int width, int height) throws SlickException {
        super(width, height);
        font = Context.getInstance().resources().getFont("forces-squared", 24);
        String commitTxt = "Move";
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
                if (counter < maxVal) counter++;
            }
        });
        max = new TextButton(context, font, "max", 0, 0, game);
        max.addAction(new ButtonAction() {
            @Override
            public void performAction() {
                counter = maxVal;
            }
        });
        decrement = new TextButton(context, font, "-", 0, 0, game);
        decrement.addAction(new ButtonAction() {
            @Override
            public void performAction() {
                if (counter > minVal) counter--;
            }
        });
        min = new TextButton(context, font, "min", 0, 0, game);
        min.addAction(new ButtonAction() {
            @Override
            public void performAction() {
                counter = minVal;
            }
        });
        commit.setEnabled(true);
        increment.setEnabled(true);
        decrement.setEnabled(true);
        max.setEnabled(true);
        min.setEnabled(true);
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
        increment.setCenterLocation((int) (x + getWidth() * 0.8), y + getCenterY());
        max.setCenterLocation((int) (x + getWidth() * 0.8) , y + getCenterY() + 20);
        decrement.setCenterLocation((int) (x + getWidth() * 0.2), y + getCenterY());
        min.setCenterLocation((int) (x + getWidth() * 0.2) , y + getCenterY() + 20);
        g.fill(bg, new ColorFill(new Color(50, 0, 0, 200)));
        font.drawString((float) (x - font.getWidth(msg1) / 2.0 + getCenterX()), (float) (y + 0.2 * getHeight()), msg1);
        font.drawString((float) (x - font.getWidth(msg2) / 2.0 + getCenterX()), (float) (y + 0.3 * getHeight()), msg2);
        font.drawString(x - font.getWidth(counter.toString()) / 2 + getCenterX(), y + getCenterY(), counter.toString());
        commit.render(container, g);
        increment.render(container, g);
        decrement.render(container, g);
        max.render(container, g);
        min.render(container, g);
        p.logFromStart("DialogBox render");
    }

    public GUIRequest<Integer> startRequest(String msg1, String msg2, int min, int max) {
        this.msg1 = msg1;
        this.msg2 = msg2;
        this.minVal = min;
        this.maxVal = max;
        counter = min;
        this.request = new GUIRequest<>();
        return request;
    }

}