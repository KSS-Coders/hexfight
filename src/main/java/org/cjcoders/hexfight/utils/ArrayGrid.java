package org.cjcoders.hexfight.utils;
import java.util.Iterator;
import java.lang.reflect.Array;
/**
 * Created by Max on 2014-05-16.
 */
public class ArrayGrid<T> implements IGrid<T> {

    private Object[][] grid;
    private int rows;
    private int cols;

    public ArrayGrid(int rows, int cols){
        grid = new Object[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    public void set(T tile, int row, int col){
        grid[row][col] = tile;
    }
    public T get(int row, int col){
        return (T)grid[row][col];
    }

    public int rows(){ return rows; }
    public int cols(){ return cols; }


    @Override
    public Iterator<T> iterator(){
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<T>{
        private int position = 0;
        private int newRow;
        private int newCol;

        public boolean hasNext(){
            return (position < rows*cols-1);
        }
        public T next() {
            newRow = position/cols;
            newCol = position%cols;
            position++;
            return (T)grid[newRow][newCol];

        }
        public void remove(){
            // nottin
        }
    }
}

