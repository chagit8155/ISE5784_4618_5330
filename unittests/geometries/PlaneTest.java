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
                {new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1)};
        Polygon pol = new Polygon(pts);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = pol.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA, "Plane's normal is not a unit vector");
        // ensure the result is orthogonal to all the plane
        for (int i = 0; i < 3; ++i)
            assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])), DELTA,
                    "Plane's normal is not orthogonal to one of the edges");
    }

    /**
     * Test method for {@link  Plane#findIntersections(Ray)}.
     */
    @Test
    void findIntersections() {

        // Create a plane
        Plane plane = new Plane(new Point(0, 0, 1), new Vector(0, 0, 1));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the plane (1 point)
        Ray ray1 = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        List<Point> result1 = plane.findIntersections(ray1);
        assertNotNull(result1, "EP01: No intersection point found");
        assertEquals(1, result1.size(), "EP01: Ray intersects the plane");
        assertEquals(new Point(0, 0, 1), result1.get(0), "EP01: Incorrect intersection point");

        // TC02: Ray is parallel to the plane and does not intersect it (0 points)
        Ray ray2 = new Ray(new Point(0, 0, 2), new Vector(1, 0, 0));
        List<Point> result2 = plane.findIntersections(ray2);
        assertNull(result2, "EP02: Ray is parallel to the plane and does not intersect it");

        // TC03: Ray is parallel to the plane and lies in the plane (0 points)
        Ray ray3 = new Ray(new Point(0, 0, 1), new Vector(1, 0, 0));
        List<Point> result3 = plane.findIntersections(ray3);
        assertNull(result3, "EP03: Ray is parallel to the plane and lies in the plane");

        // =============== Boundary Values Tests ==================
        // TC04: Ray is orthogonal to the plane and starts before the plane (1 point)
        Ray ray4 = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        List<Point> result4 = plane.findIntersections(ray4);
        assertEquals(1, result4.size(), "BVA01: Ray is orthogonal to the plane and starts before the plane");
        assertEquals(new Point(0, 0, 1), result4.get(0), "BVA01: Incorrect intersection point");

        // TC05: Ray is orthogonal to the plane and starts in the plane (0 points)
        Ray ray5 = new Ray(new Point(0, 0, 1), new Vector(0, 0, 1));
        List<Point> result5 = plane.findIntersections(ray5);
        assertNull(result5, "BVA02: Ray is orthogonal to the plane and starts in the plane");

        // TC06: Ray is orthogonal to the plane and starts after the plane (0 points)
        Ray ray6 = new Ray(new Point(0, 0, 2), new Vector(0, 0, 1));
        List<Point> result6 = plane.findIntersections(ray6);
        assertNull(result6, "BVA03: Ray is orthogonal to the plane and starts after the plane");

        // TC07: Ray begins at the plane and goes outside (0 points)
        Ray ray7 = new Ray(new Point(1, 1, 1), new Vector(1, 1, 1));
        List<Point> result7 = plane.findIntersections(ray7);
        assertNull(result7, "BVA04: Ray begins at the plane and goes outside");

        // TC08: Ray begins at the plane and goes inside (1 point)
        Ray ray8 = new Ray(new Point(1, 1, 1), new Vector(-1, -1, -1));
        List<Point> result8 = plane.findIntersections(ray8);
        assertEquals(1, result8.size(), "BVA05: Ray begins at the plane and goes inside");
        assertEquals(Point.ZERO, result8.get(0), "BVA05: Incorrect intersection point");


    }

//    @Test
//    void testGetNormal() {
//    }
}