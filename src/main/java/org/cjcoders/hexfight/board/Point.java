package org.cjcoders.hexfight.board;

/**
* Created by mrakr_000 on 27.04.14.
*/
public class Point {
    public final int x;
    public final int y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    public String toString(){
        return "["+x+", "+y+"]";
    }
}
