package org.cjcoders.hexfight.board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Created by mrakr_000 on 27.04.14.
 */
public class Board extends JPanel{

    public static final Color BGCOLOR = Color.WHITE;
    public static final Color CELLBG = Color.ORANGE;
    public static final Color CELLBG_CLICKED = Color.RED;
    public static final Color CELLBORDER = Color.BLACK;

    public static final int MIN_SIDE_SIZE = 10;
    public static final int MAX_SIDE_SIZE = 60;
    public static final int SIDE_SIZE_STEP = 1;

    public static final Double DEG60 = Math.PI/3.0;
    public static final Double SIN60 = Math.sin(DEG60);
    public static final Double COS60 = Math.cos(DEG60);

    private Dimension size;
    private Integer xPad;
    private Integer dx;
    private Integer dy;
    private Integer s;
    private Integer xOffset = 100;
    private Integer yOffset = 200;
    private List<List<Tile>> board;
    private Point draggingPoint;
    private boolean dragging = false;

    public Integer getBoardHeight(){
        return size.height * 2 * dy + dy;
    }
    public Integer getBoardWidth(){
        return size.width * dx + xPad;
    }
    public boolean[][] clicked;

    public Board(Dimension size, Integer hexSize){
        setBackground(BGCOLOR);
        BoardMouseListener ml = new BoardMouseListener();
        addMouseListener(ml);
        addMouseMotionListener(ml);
        addMouseWheelListener(ml);
        clicked = new boolean[size.width][size.height];

        s = hexSize;
        this.size = size;

        calc();
    }

    private void calc(){
        xPad = new Double(s * COS60).intValue();
        dy = new Double(s * SIN60).intValue();
        dx = s + xPad;
    }

    public void loadGrid(int rows, int col){

    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        super.paintComponent(g2);
        //draw grid
        for(int h = 0; h < size.height; ++h){
            for(int w = 0; w < size.width; ++w){
                Point p = new Point(w, h);
                if(shouldPrint(p)){
                    Polygon poly = createHex(p);
                    if(clicked[w][h]) g2.setColor(CELLBG_CLICKED);
                    else g2.setColor(CELLBG);
                    g2.fillPolygon(poly);
                    g2.setColor(CELLBORDER);
                    g2.drawPolygon(poly);
                }
            }
        }
    }

    private boolean shouldPrint(Point gridXY) {
        Point panelXY = gridToPanelXY(gridXY);
        return inRange(panelXY.x - xPad) ||
                inRange(panelXY.x + dx) ||
                inRange(panelXY.y)||
                inRange(panelXY.y + 2 * dy);
    }
    private boolean inRange(int n){
        return n > 0 && n < getHeight();
    }

    private Point gridToPanelXY(Point gridXY){
        int x = xPad + gridXY.x * dx;
        int y = gridXY.y * dy * 2;
        if(gridXY.x % 2 == 1) y += dy;
        return new Point(x - xOffset, y - yOffset);
    }
    private Point panelToGridXY(Point panelXY){
        int x = (panelXY.x + xOffset - xPad) / dx;
        int y = (panelXY.y + yOffset) / (2 * dy);
        if(x % 2 == 1) y = (panelXY.y + yOffset - dy) / (2 * dy);
        return new Point(x, y);
    }
    public Polygon createHex(Point gridXY){
        Polygon hexagon = new Polygon();
        Point panelXY = gridToPanelXY(gridXY);

        hexagon.addPoint(panelXY.x, panelXY.y);
        hexagon.addPoint(panelXY.x + s, panelXY.y);
        hexagon.addPoint(panelXY.x + dx, panelXY.y + dy);
        hexagon.addPoint(panelXY.x + s, panelXY.y + 2 * dy);
        hexagon.addPoint(panelXY.x, panelXY.y + 2 * dy);
        hexagon.addPoint(panelXY.x - xPad, panelXY.y + dy);
        return hexagon;
    }

    class BoardMouseListener implements MouseListener, MouseMotionListener, MouseWheelListener {
        private boolean inArea;

        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            Point gridP = panelToGridXY(new Point(x, y));
            clicked[gridP.x][gridP.y] = !clicked[gridP.x][gridP.y];
            repaint();
        }
        public void mousePressed(MouseEvent e){
            draggingPoint = new Point(e.getX(), e.getY());
            dragging = true;
        }
        public void mouseReleased(MouseEvent e){
            dragging = false;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            inArea = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            inArea = false;
        }

        public void mouseDragged(MouseEvent e){
                int x = e.getX();
                int y = e.getY();
                xOffset += draggingPoint.x - x;
                yOffset += draggingPoint.y - y;
            draggingPoint = new Point(x,y);
            if(dragging && inArea){
                repaint();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            int toChange = e.getUnitsToScroll();
            int ns = s;
            ns += toChange * SIDE_SIZE_STEP;
            if(ns > MIN_SIDE_SIZE && ns < MAX_SIDE_SIZE) s = ns; calc(); repaint();
        }
    }

}
