package org.cjforge.hexed.utils;

/**
 * Created by Max on 2014-05-16.
 */
public interface Grid<T> extends Iterable<T> {

    public void set(T tile, int row, int col);
    public T get(int row, int col);

    public int rows();
    public int cols();

}
