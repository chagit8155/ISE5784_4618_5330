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
    void testSubtract() { //לחלק את הבדיקות למחלקות שקילות/מקרי קצה
        assertEquals(p2.subtract(p1), v1,
                "subtract(): (point2 - point1) does not work correctly");
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1),
                "subtract(): (point - itself) does not throw an exception");
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1),
                "subtract(): (point - itself) throws wrong exception");
    }


    /**
     * Test method for {@link primitives.Point#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        // Add vector to point
        assertEquals(p2, p1.add(v1),
                "add(): (point + vector) = center of coordinates does not work correctly");
        assertEquals(Point.ZERO, p1.add(v1Opposite),
                "add(): (point + vector) = center of coordinates does not work correctly");

    }

    @Test
    void testDistanceSquared() {
        assertEquals(0, p1.distanceSquared(p1), DELTA,
                "distanceSquared(): point squared distance to itself is not zero");
        assertEquals(9, p1.distanceSquared(p3), DELTA,
                "distanceSquared(): squared distance between points is wrong");
        assertEquals(9, p3.distanceSquared(p1), DELTA,
                "distanceSquared(): squared distance between points is wrong");
    }

    @Test
    void distance() {
        assertEquals(0, p1.distance(p1), DELTA, "distance(): point squared distance to itself is not zero");
        assertEquals(3, p1.distance(p3), DELTA, "distance(): distance between points is wrong");
        assertEquals(3, p3.distance(p1), DELTA, "distance(): distance between points is wrong");
    }
}