package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    private final double DELTA = 0.000001;
    @Test
    void testConstructor() {
        Point point1 = new Point(1, 1, 1);
        Point point2 = new Point(2, 2, 2);
        Point point3 = new Point(3, 3, 3);

        assertThrows(IllegalArgumentException.class,
                () -> new Plane(point1, point2, point3),
                "The first and second points are connected, The points are on the same line");
    }

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a triangle
        Point[] pts =
                {new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0)};
        Triangle tri = new Triangle(pts[0], pts[1], pts[2]);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> tri.getNormal(new Point(0, 0, 1)), "add");
        // generate the test result
        Vector result = tri.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA, "Plane's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 3; ++i)
            assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 2: i - 1])), DELTA,
                    "Plane's normal is not orthogonal to one of the edges");
    }

//    @Test
//    void testGetNormal() {
//    }
}