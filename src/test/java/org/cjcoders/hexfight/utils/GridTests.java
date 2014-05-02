package org.cjcoders.hexfight.utils;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by mrakr_000 on 30.04.14.
 */

public class GridTests {

    @Test
    public void gridCreationTest(){
        Grid<Object> grid = new Grid<>(2,3);
        assertEquals("Grid rows should be 2", 2, grid.rows());
        assertEquals("Grid columns should be 3", 3, grid.cols());
    }

    @Test
    public void elementIsProperlyInsertedAndObtainedTest(){
        Grid<Integer> grid = new Grid<>(2,3);
        Integer expected = 1;
        grid.set(expected, 1, 1);
        assertEquals("Element on index (1, 1) should be 1", expected, grid.get(1, 1));
    }

}
