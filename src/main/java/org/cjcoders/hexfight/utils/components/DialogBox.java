package org.cjcoders.hexfight.utils.components;

import org.cjcoders.hexfight.board.Hexagon;
import org.cjcoders.hexfight.context.Context;
import org.cjcoders.hexfight.game.GUIRequest;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by mrakr_000 on 2014-06-02.
 */
public class DialogBox {

    private String msg;
    private int x;
    private int y;
    private int width;
    private int height;
    private GUIRequest<Integer> request;
    private TextButton commit;
    private Shape bg;

    public boolean isInitialized() {
        return initialized;
    }

    private boolean initialized;
    private Font font;
    private String commitTxt = "Commit";

    public DialogBox(String msg, int x, int y) throws SlickException {
        this.msg = msg;
        this.x = x;
        this.y = y;
        request = new GUIRequest<>();
    }

    public void init(GUIContext context, StateBasedGame game, int stateId, int width, int height) throws SlickException {
        this.width = width;
        this.height = height;
        font = Context.getInstance().resources().getFont("forces-squared", 24);
        commit = new TextButton(context, font, commitTxt, (int) (x - font.getWidth(commitTxt)/2.0), (int) (y + 0.3 * height), game, stateId);
        commit.addAction(new ButtonAction() {
            @Override
            public void performAction() {
                request.setResult(128);
                request.setFinished(true);
            }
        });
        commit.setEnabled(true);
        bg = new Hexagon(width, (int) (x - 0.5 * width), (int) (y - 0.5 * height));
        initialized = true;
    }

    public void render(GUIContext context, Graphics g){
        g.fill(bg, new ShapeFill() {
            @Override
            public Color colorAt(Shape shape, float x, float y) {
                return new Color(64, 64, 64, (float) 0.5);
            }

            @Override
            public Vector2f getOffsetAt(Shape shape, float x, float y) {
                return new Vector2f(2,0);
            }
        });
        font.drawString((float) (x - font.getWidth(msg)/2.0), (float) (y - 0.2 * height), msg);
        commit.render(context, g);
    }

    public GUIRequest getRequest() {
        return request;
    }

    public void updateOffset(int dx, int dy) {
        x += dx;
        y += dy;
        bg = new Hexagon(width, (int) (x - 0.5 * width), (int) (y - 0.5 * height));
        commit.setLocation((int) (x - font.getWidth(commitTxt)/2.0), (int) (y + 0.3 * height));
    }
}
