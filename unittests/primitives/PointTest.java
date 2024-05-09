package primitives;

import static java.lang.System.out;

import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point class
 *
 * @author chagit r
 */

class PointTest {
    public static final double DELTA = 0.00001;
    Point p1 = new Point(1, 2, 3);
    Point p2 = new Point(2, 4, 6);
    Point p3 = new Point(2, 4, 5);
    Vector v1 = new Vector(1, 2, 3);
    Vector v1Opposite = new Vector(-1, -2, -3);

    @Test
    void subtract() { //לחלק את הבדיקות למחלקות שקילות/מקרי קצה
        // Subtract points
//        if (!p2.subtract(p1).equals(v1))
//            out.println("ERROR: (point2 - point1) does not work correctly");
        assertEquals(p2.subtract(p1), v1, "subtract(): (point2 - point1) does not work correctly");
//        try {
//            p1.subtract(p1);
//            out.println("ERROR: (point - itself) does not throw an exception"); מתי אני אמורה לזרוק את החריגה הזאת ואיזה חריגה לזרוק? לכאורה אין סיבה שהוא ייזרוק אם אין
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1), "subtract(): (point - itself) does not throw an exception");
//        } catch (IllegalArgumentException ignore) {
//        } catch (Exception ignore) {
//            out.println("ERROR: (point - itself) throws wrong exception");
//        }
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1), "subtract(): (point - itself) throws wrong exception");
    }


    /**
     * Test method for {@link primitives.Point#add(primitives.Vector)}.
     */
    @Test
    void add() {
        // Add vector to point
//        if (!(p1.add(v1).equals(p2)))
//            out.println("ERROR: (point + vector) = other point does not work correctly");
//        if (!(p1.add(v1Opposite).equals(Point.ZERO)))
//            out.println("ERROR: (point + vector) = center of coordinates does not work correctly");
        assertEquals(p2 , p1.add(v1), "add(): (point + vector) = center of coordinates does not work correctly");
        assertEquals( Point.ZERO ,p1.add(v1Opposite), "add(): (point + vector) = center of coordinates does not work correctly");

    }

    //    // distances
//        if (!isZero(p1.distanceSquared(p1)))
//            out.println("ERROR: point squared distance to itself is not zero");
//        if (!isZero(p1.distance(p1)))
//            out.println("ERROR: point distance to itself is not zero");
//        if (!isZero(p1.distanceSquared(p3) - 9))
//            out.println("ERROR: squared distance between points is wrong");
//        if (!isZero(p3.distanceSquared(p1) - 9))
//            out.println("ERROR: squared distance between points is wrong");
//        if (!isZero(p1.distance(p3) - 3))
//            out.println("ERROR: distance between points to itself is wrong");
//        if (!isZero(p3.distance(p1) - 3))
//            out.println("ERROR: distance between points to itself is wrong");
    @Test
    void distanceSquared() {
        assertEquals(0, p1.distanceSquared(p1), DELTA, "distanceSquared(): point squared distance to itself is not zero");
        assertEquals(9,p1.distanceSquared(p3) , DELTA, "distanceSquared(): squared distance between points is wrong");
        assertEquals(9,p3.distanceSquared(p1) , DELTA, "distanceSquared(): squared distance between points is wrong");
    }

    @Test
    void distance() {
        assertEquals(0, p1.distance(p1), DELTA, "distance(): point squared distance to itself is not zero");
        assertEquals(3,p1.distanceSquared(p3) , DELTA, "distance(): distance between points is wrong");
        assertEquals(3,p3.distanceSquared(p1) , DELTA, "distance(): distance between points is wrong");
    }
}