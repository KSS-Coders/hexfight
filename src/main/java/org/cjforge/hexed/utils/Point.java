package org.cjforge.hexed.utils;

/**
 * Created by mrakr_000 on 27.04.14.
 */
public class Point {
    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    public Point setX(int x) {
        return new Point(x, y);
    }

    public Point setY(int y) {
        return new Point(x, y);
    }

    public Point add(Point toAdd) {
        return new Point(x + toAdd.x, y + toAdd.y);
    }

    public Point negative() {
        return new Point(-x, -y);
    }

    public boolean isPositive() {
        return x >= 0 && y >= 0;
    }
}
