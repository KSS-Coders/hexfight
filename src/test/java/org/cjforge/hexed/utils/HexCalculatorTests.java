package org.cjforge.hexed.utils;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by mrakr_000 on 2014-05-23.
 */
public class HexCalculatorTests {

    @Before
    public void setup(){
        BasicConfigurator.configure();
    }

    @Test
//    @Ignore
    public void obtainBoardCoordinatesFromGivenScreenCoordinatesTest(){
        int screenX[]   = new int[]{198,  321, 381, 394, 461};
        int screenY[]   = new int[]{1045, 272, 231, 77,  114};
        int expectedI[] = new int[]{2,    4,   4,   5,   5};
        int expectedJ[] = new int[]{10,   2,   2,   0,   0};
        int sideSize = 100;

        for(int i = 0 ; i < screenX.length ; ++i) {
            Point p = new HexCalculator(sideSize).getBorardCoordinates(screenX[i], screenY[i]);

            assertEquals("i should be " + expectedI[i] + " for coordinates [" + screenX[i] + ", " + screenY[i] + "].", expectedI[i], p.x);
            assertEquals("j should be " + expectedJ[i] + " for coordinates [" + screenX[i] + ", " + screenY[i] + "].", expectedJ[i], p.y);
        }
    }

}
