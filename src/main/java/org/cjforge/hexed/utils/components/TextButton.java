package org.cjforge.hexed.utils.components;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrakr_000 on 2014-05-12.
 */
public class TextButton extends MouseOverArea {
    private final Font font;
    private final String text;
    private final StateBasedGame game;
    private boolean isEnabled;
    private List<ButtonAction> actions;

    public TextButton(GUIContext container, Font font, String text, int x, int y, StateBasedGame game) throws SlickException {
        super(container, new Image(0, 0), x, y, font.getWidth(text), font.getHeight(text));
        this.font = font;
        this.text = text;
        this.game = game;
        this.actions = new ArrayList<>();
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    @Override
    public void render(GUIContext container, Graphics g) {
        Color c = Color.gray;
        if (isEnabled) {
            if (isMouseOver()) {
                c = Color.red;
            } else {
                c = Color.orange;
            }
        }
        font.drawString(getX(), getY(), text, c); //draw the text with that settings
        super.render(container, g);
    }

    public void addAction(ButtonAction action) {
        actions.add(action);
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        if (isMouseOver() && isEnabled) {
            for (ButtonAction action : actions) {
                action.performAction();
            }
        }
    }

    public void setCenterLocation(int x, int y) {
        setLocation(x - font.getWidth(text) / 2, y - font.getHeight(text) / 2);
    }

}
