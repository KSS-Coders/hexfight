package org.cjcoders.hexfight.utils;
import java.util.Iterator;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by mrakr_000 on 30.04.14.
 */

public class GridTests {

    @Test
    public void gridCreationTest(){
        Grid<Object> grid = new ArrayGrid<>(2,3);
        assertEquals("Grid rows should be 2", 2, grid.rows());
        assertEquals("Grid columns should be 3", 3, grid.cols());
    }

    @Test
    public void elementIsProperlyInsertedAndObtainedTest(){
        Grid<Integer> grid = new ArrayGrid<>(2,3);
        Integer expected = 1;
        grid.set(expected, 1, 1);
        assertEquals("Element on index (1, 1) should be 1", expected, grid.get(1, 1));
    }

    // Added by Max on 18.05.14
    @Test
    public void arrayGridCreationTest(){
        ArrayGrid<Object> grid = new ArrayGrid<>(2,3);
        assertEquals("Grid rows should be 2", 2, grid.rows());
        assertEquals("Grid columns should be 3", 3, grid.cols());
    }

    @Test
    public void arrayElementIsProperlyInsertedAndObtainedTest(){
        ArrayGrid<Integer> grid = new ArrayGrid<>(2,3);
        Integer expected = 1;
        grid.set(expected, 1, 1);
        assertEquals("Element on index (1, 1) should be 1", expected, grid.get(1, 1));
    }
    @Test
    public void iteratorHasNextTest()
    {
        ArrayGrid<Integer> grid = new ArrayGrid<>(1,1);
        Iterator it = grid.iterator();
        //assertTrue(it.hasNext());
        assertFalse(it.hasNext());
    }
    @Test
    public void iteratorNextTest(){
        ArrayGrid<Integer> grid = new ArrayGrid<>(2,3);
        grid.set(0, 0, 0);
        grid.set(5, 1, 1);
        Iterator it = grid.iterator();
        assertEquals("First element(0,0) should be 0", 0, it.next());
        it.next();
        it.next();
        it.next();
        assertEquals("Fifth element(1,1) should be 5", 5, it.next());

    }


}
