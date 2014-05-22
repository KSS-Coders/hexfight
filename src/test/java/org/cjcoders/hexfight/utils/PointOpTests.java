package org.cjcoders.hexfight.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Max on 2014-05-06.
 */
public class PointOpTests {
    @Test
    public void pointCreationTest(){
        Point newPoint = new Point(2,3);
        assertEquals("Point x should be 2", 2, newPoint.x);
        assertEquals("Point y should be 3", 3, newPoint.y);
    }

    @Test
    public void isNearbyTest(){
        Point firstPoint = new Point(2,3);
        Point secondPoint = new Point(3,3);
        Point thirdPoint = new Point(3,4);
        HexCalculator distCalcObj = new HexCalculator();
        assertEquals("Result should be true(these points are nearby each others)", true, distCalcObj.isNearby(firstPoint, secondPoint));
        //assertEquals("Result should be true(these points are nearby each others)", true, distCalcObj.isNearby(firstPoint, thirdPoint));
    }

    @Test
    public void distanceIsProperlyCalculated(){
        Point firstPoint = new Point(5,1);
        Point secondPoint = new Point(1,7);
        HexCalculator distCalc = new HexCalculator();

        assertEquals("Distance between firstPoint(5,1) and secondPoint(1,7) should be 8",8,distCalc.distCalculate(firstPoint, secondPoint));
/*
        firstPoint.setX(3);
        firstPoint.setY(4);
        secondPoint.setX(5);
        secondPoint.setY(6);
        assertEquals("Distance between firstPoint(2,3) and secondPoint(5,3) should be 3",3,distCalc.distCalculate(firstPoint, secondPoint));

        firstPoint.setX(3);
        firstPoint.setY(4);
        secondPoint.setX(5);
        secondPoint.setY(6);
        assertEquals("Distance between firstPoint(2,3) and secondPoint(5,3) should be 3",10 ,distCalc.distCalculate(firstPoint, secondPoint));
*/
    }

}
