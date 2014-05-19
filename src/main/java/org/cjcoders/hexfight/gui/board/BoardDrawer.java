package org.cjcoders.hexfight.gui.board;

import org.cjcoders.hexfight.Board;
import org.cjcoders.hexfight.Tile;
import org.cjcoders.hexfight.gui.utils.resources.Resources;
import org.cjcoders.hexfight.old.utils.Point;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrakr_000 on 2014-05-15.
 */
public class BoardDrawer extends MouseOverArea{

    private int side;
    private int scale;
    private Board board;
    private int width;
    private int height;
    public Point offset;
    public SpriteSheet tiles;
    Font num;
    public Image tileBorder;
    private List<Tile> tilesList;

    int x = 100, y =100;

    private Color[] colors = new Color[]{Color.blue, Color.green, Color.red, Color.yellow};

    public BoardDrawer(GUIContext c, int width, int height, int side, int scale, Board board) throws SlickException {
        super(c, new Image(0,0), 0, 0);
        this.width = width;
        this.height = height;
        this.side = side;
        this.scale = scale;
        this.board = board;
        this.offset = new Point(100, 100);
        this.tilesList = new ArrayList<>();
    }

    public void init(){
        tiles = new SpriteSheet(Resources.get().images.get("tiles"),70,70);
        num = Resources.get().fonts.get("forces-squared").getWithSize(side*scale/3);
        tileBorder = Resources.get().images.get("hex-border");

        for(int i = 0; i < 30; ++i){
            for(int j = 0; j < 30; ++j){
                tilesList.add(i * 30 + j, new Tile(this, i, j, side, scale, getRandColor(), getRandNum(), getRandomTile()));
            }
        }
    }

    private int getRandNum() {
        int min = 0;
        int max = 100;
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    private Color getRandColor() {
        int min = 0;
        int max = 10;
        int randVal = min + (int)(Math.random() * ((max - min) + 1));
        if(randVal < colors.length) return colors[randVal];
        else return Color.transparent;
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        int dx = newx - oldx;
        int dy = newy - oldy;
        System.out.println("Old: ["+x+", "+y+"]");
        x += dx;
        y += dy;
        System.out.println("New: ["+x+", "+y+"]");
        this.setOffset(x, y);
        this.setX(x);
        this.setY(y);
    }

    public void setOffset(int x, int y){
        offset = new Point(x, y);
    }

    public void render(GUIContext container, Graphics g){
        g.setFont(num);
        tiles.startUse();
        for(int i = 0; i < 30; ++i){
            if(i * 0.75 > container.getWidth()) break;
            for(int j = 0; j < 30; ++j){
                if(j * side + j % 2 * 0.5 * side > container.getHeight()) break;
                tilesList.get(i*29 + j).render(container,g);
            }
        }
        tiles.endUse();
    }

    public Image getRandomTile(){
        int min = 0;
        int max = tiles.getHorizontalCount()-1;
        return tiles.getSubImage(min + (int)(Math.random() * ((max - min) + 1)), 0);
    }

}
