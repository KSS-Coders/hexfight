package org.cjcoders.hexfight.old.utils;

/**
 * Created by mrakr_000 on 30.04.14.
 */
public class Grid<T> {

    private Object[][] grid;
    private int rows;
    private int cols;

    public Grid(int rows, int cols){
        grid = new Object[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }
    public void set(T tile, int row, int col){
        grid[row][col] = tile;
    }

    @SuppressWarnings("unchecked")
    public T get(int row, int col){
        return (T)grid[row][col];
    }

    public int rows(){ return rows; }
    public int cols(){ return cols; }

    public static void main(String... args){}
}
