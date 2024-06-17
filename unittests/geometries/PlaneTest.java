package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    private final double DELTA = 0.000001;

    /**
     * Test method for {@link  Plane#Plane(Point, Point, Point)}.
     */
    @Test
    void testConstructor() {
        // ============ Boundary Values Tests ==================
        // TC01: Test two points are the same
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1, 2, 3), new Point(1, 2, 3), new Point(1, 2, 4)),
                "ERROR: does not throw exception for two points are the same");

        // TC02: Test three points are on the same line
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1, 2, 3), new Point(2, 4, 6), new Point(3, 6, 9)),
                "ERROR: does not throw exception for three points are on the same line");
    }

    /**
     * Test method for {@link Plane#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Point[] pts =
                {new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0)};
        Plane p = new Plane(pts[0], pts[1],pts[2]);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> p.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = p.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA, "Plane's normal is not a unit vector");
        // ensure the result is orthogonal to all the plane
        for (int i = 0; i < 3; ++i)
            assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1])), DELTA,           //חיסור בין שתי נקודות נותן את הווקטור שמייצג את הצלע של המשולש.
                    "Plane's normal is not orthogonal to one of the edges");
    }

    /**
     * Test method for {@link  Intersectable#findGeoIntersectionsHelper(Ray)}.
     */
    @Test
    void findIntersections() {
        final Point p001 = new Point(0, 0, 1);
        final Point p100 = new Point(1, 0, 0);
        final Point p010 = new Point(0, 1, 0);

        Plane plane = new Plane(p001, p010, p100);
        Point p200 = new Point(2, 0, 0);
        Vector v502 = new Vector(-5, 0, 2);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray starts outside the plane, not orthogonal, not parallel, cross the Plane
        List<Point> result1 = plane.findIntersections(new Ray(p200, v502));
        List<Point> exp = List.of(new Point(0.33333333333333326, 0, 0.6666666666666667));
        assertEquals(1, result1.size(), "Wrong number of points");
        assertEquals(exp, result1, "Ray starts outside the plane, not orthogonal, not paralal, cross the Plane");

        // TC02: Ray starts outside the plane, not orthogonal, not paralal, doesn't cross the Plane
        assertNull(plane.findGeoIntersectionsHelper(new Ray(p200, v502.scale(-1))), "Ray starts outside the plane, not orthogonal, not paralal, doesn't cross the Plane");

        // =============== Boundary Values Tests ==================

        //**** Group: Ray parallel to the Plane
        // TC03: Ray inside the plane
        assertNull(plane.findIntersections(new Ray(new Point(0.5, 0.25, 0.25), new Vector(-0.5, 0.2, 0.3))),
                "ERROR: findGeoIntersectionsHelper() did not return null when the ray is parallel to the plane and included in the plane");
        // TC04: Ray outside the plane
        assertNull(plane.findIntersections(new Ray(new Point(0.6, 0.25, 0.25), new Vector(-0.5, 0.2, 0.3))),
                "ERROR: findGeoIntersectionsHelper() did not return null when the ray is parallel to the plane and not included in the plane");

        //**** Group: Ray orthogonal to the Plane
        // TC05: Ray starts before the plane
        result1 = plane.findIntersections(new Ray(new Point(0.6, 0.25, 0.25), new Vector(-1, -1, -1)));
        assertEquals(1, result1.size(),
                "ERROR: findGeoIntersectionsHelper() returned incorrect number of points when the ray is orthogonal to the plane and begins before the plane");
        // TC06: Ray starts inside the plane
        assertNull(plane.findIntersections(new Ray(new Point(0.5, 0.25, 0.25), new Vector(-1, -1, -1))),
                "ERROR: findGeoIntersectionsHelper() did not return null when the ray is orthogonal to the plane and begins inside the plane");
        // TC07: Ray starts after the plane
        assertNull(plane.findIntersections(new Ray(new Point(0.4, 0.25, 0.25), new Vector(-1, -1, -1))),
                "ERROR: findGeoIntersectionsHelper() did not return null when the ray is orthogonal to the plane and begins after the plane");

        //**** Group: Ray not parallel and not orthogonal to the Plane
        // TC08: Ray starts inside the plane
        assertNull(plane.findIntersections(new Ray(new Point(0.5, 0.25, 0.25), new Vector(-3, 5, 2))),
                "ERROR: findGeoIntersectionsHelper() did not return null when the ray begins in the plane");
        // TC09: Ray starts in the reference point of the plane
        assertNull(plane.findIntersections(new Ray(p001, new Vector(-3, 5, 2))),
                "ERROR: findGeoIntersectionsHelper() did not return null when the ray begins in the same point which appears as reference point in the plane");

    }

//    @Test
//    void testGetNormal() {
//    }
}