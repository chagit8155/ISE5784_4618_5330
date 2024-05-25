package primitives;

import static java.lang.System.out;

import static primitives.Util.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point class
 *
 * @author H & H
 */

class PointTest {
    public static final double DELTA = 0.00001;
    Point p1 = new Point(1, 2, 3);
    Point p2 = new Point(2, 4, 6);
    Point p3 = new Point(2, 4, 5);
    Vector v1 = new Vector(1, 2, 3);
    Vector v1Opposite = new Vector(-1, -2, -3);

    /**
     * Test method for {@link primitives.Point#subtract(Point)}.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the new point is the right one
        assertEquals(p2.subtract(p1), v1, "subtract(): (point2 - point1) does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC02: Test that exception is thrown for zero vector
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1), "subtract(): (point - itself) does not throw an exception");
        assertThrows(Exception.class, () -> p1.subtract(p1), "subtract(): (point - itself) throws wrong exception");
    }


    /**
     * Test method for {@link primitives.Point#add(primitives.Vector)}.
     */
    @Test
    void testAdd() { // Add vector to point
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the new point is the right one
        assertEquals(p2, p1.add(v1), "add(): (point + vector) = center of coordinates does not work correctly");
        // TC02: Test adding an opposite vector to point the result is the zero point
        assertEquals(Point.ZERO, p1.add(v1Opposite), "add(): (point + vector) = center of coordinates does not work correctly");

    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(Point)} .
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the new point is the right one
        assertEquals(9, p1.distanceSquared(p3), DELTA, "distanceSquared(): squared distance between points is wrong");
        assertEquals(9, p3.distanceSquared(p1), DELTA, "distanceSquared(): squared distance between points is wrong");

        // =============== Boundary Values Tests ==================
        // TC02: Tests that distanceSquared works for the distance of a point from itself
        assertEquals(0, p1.distanceSquared(p1), DELTA, "distanceSquared(): point squared distance to itself is not zero");

    }

    /**
     * Test method for {@link primitives.Point#distance(Point)} .
     */
    @Test
    void distance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the new point is the right one
        assertEquals(3, p1.distance(p3), DELTA, "distance(): distance between points is wrong");
        assertEquals(3, p3.distance(p1), DELTA, "distance(): distance between points is wrong");

        // =============== Boundary Values Tests ==================
        // TC01: Tests that distance works for the distance of a point from itself
        assertEquals(0, p1.distance(p1), DELTA, "distance(): point squared distance to itself is not zero");
    }
}