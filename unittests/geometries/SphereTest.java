package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;
import static org.junit.jupiter.api.Assertions.*;


class SphereTest {
    private final double DELTA = 0.000001;


    /**
     * Test method for {@link Sphere#getNormal(Point)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Point pointOnSphere = new Point(1, 1, 1);
        Point center = new Point(0, 0, 0);
        double radius = 1.0;
        Sphere sphere = new Sphere(radius, center);
        Vector normal = sphere.getNormal(pointOnSphere);
     // Vector expectedNormal = new Vector(0.57735,0.57735,0.57735);
        assertEquals(1, normal.length() ,DELTA,"Incorrect calculation of sphere's normal");
    }
}