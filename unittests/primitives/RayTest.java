package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void getPoint() {
        Point p1 = new Point(1, 2, 3);
        Vector v1 = new Vector(1, 0, 0);
        Ray ray = new Ray(p1, v1);
        // ============ Equivalence Partitions Tests ==============

        // TC01: Test that the new point is the right one
        assertEquals(new Point(2, 2, 3), ray.getPoint(1), "getPoint() wrong result");
        // ============ Boundary Values Tests ==============

        // TC02: Test that the new point is the right one
        assertEquals(p1, ray.getPoint(0), "Didn't work for t=0");
    }

    @Test
    public void testFindClosestPoint() {
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: The middle point is the closest to the ray's origin
        List<Point> points = List.of(
                new Point(1, 2, 3),
                new Point(0.5, 0, 0), // This should be the closest point
                new Point(2, 2, 2)
        );
        assertEquals(new Point(0.5, 0, 0), ray.findClosestPoint(points), "TC01: The closest point is not found correctly");

        // =============== Boundary Values Tests ==================
        // TC02: The list is empty
        points = List.of();
        assertNull(ray.findClosestPoint(points), "TC02: The closest point for an empty list should be null");

        // TC03: The first point is the closest
        points = List.of(
                new Point(0.5, 0, 0), // This should be the closest point
                new Point(1, 2, 3),
                new Point(2, 2, 2)
        );
        assertEquals(new Point(0.5, 0, 0), ray.findClosestPoint(points), "TC03: The closest point is not found correctly when it's the first point");

        // TC04: The last point is the closest
        points = List.of(
                new Point(1, 2, 3),
                new Point(2, 2, 2),
                new Point(0.5, 0, 0) // This should be the closest point
        );
        assertEquals(new Point(0.5, 0, 0), ray.findClosestPoint(points), "TC04: The closest point is not found correctly when it's the last point");
    }
}