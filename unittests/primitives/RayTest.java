package primitives;

import org.junit.jupiter.api.Test;

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
}