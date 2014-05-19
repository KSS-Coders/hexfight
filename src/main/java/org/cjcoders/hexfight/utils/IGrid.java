package org.cjcoders.hexfight.utils;
import java.util.Iterator;
/**
 * Created by Max on 2014-05-16.
 */
public interface IGrid<T> extends Iterable {

    public void set(T tile, int row, int col);
    public T get(int row, int col);

    public int rows();
    public int cols();

}