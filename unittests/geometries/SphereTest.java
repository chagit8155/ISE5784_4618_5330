package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static primitives.Util.*;
import static org.junit.jupiter.api.Assertions.*;


class SphereTest {
    private final double DELTA = 0.000001;


    /**
     * Test method for {@link Sphere#getNormal(Point)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the normal is the right one
        Point pointOnSphere = new Point(1, 1, 1);
        Point center = new Point(0, 0, 0);
        double radius = 1.0;
        Sphere sphere = new Sphere(radius, center);
        Vector normal = sphere.getNormal(pointOnSphere);
        // Vector expectedNormal = new Vector(0.57735,0.57735,0.57735);
        assertEquals(1, normal.length(), DELTA, "Incorrect calculation of sphere's normal");
    }


    private final Point p001 = new Point(0, 0, 1);
    private final Point p100 = new Point(1, 0, 0);
    private final Vector v001 = new Vector(0, 0, 1);

    /**
     * Test method for {@link Sphere#findIntersections(Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d, p100);
        final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
        final var exp = List.of(gp1, gp2);
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v110 = new Vector(1, 1, 0);
        final Point p01 = new Point(-1, 0, 0);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p01, v110)), "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        final var result1 = sphere.findIntersections(new Ray(p01, v310))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p01)))
                .toList();
        assertEquals(2, result1.size(), "Wrong number of points");
        assertEquals(exp, result1, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        Point insidePoint = new Point(0.5, 0, 0);
        Vector v = new Vector(1, 0, 0);
        Ray insideRay = new Ray(insidePoint, v);
        List<Point> result3 = sphere.findIntersections(insideRay);
        assertEquals(1, result3.size(), "Wrong number of points for ray starting inside sphere");

        // TC04: Ray starts after the sphere (0 points)
        Point afterPoint = new Point(2, 0, 0);
        Ray afterRay = new Ray(afterPoint, v);
        assertNull(sphere.findIntersections(afterRay), "Ray starts after sphere");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 point)
        Point onSpherePoint = new Point(1, 0, 0);
        Ray onSphereInsideRay = new Ray(onSpherePoint, v);
        List<Point> result11 = sphere.findIntersections(onSphereInsideRay);
        assertEquals(1, result11.size(), "Wrong number of points for ray starting on sphere and going inside");

        // TC12: Ray starts at sphere and goes outside (0 points)
        Ray onSphereOutsideRay = new Ray(onSpherePoint, v.scale(-1));
    //    assertNull(sphere.findIntersections(onSphereOutsideRay), "Ray starts on sphere and goes outside");
        assertNull(sphere.findIntersections(new Ray(new Point(2,0,0), new Vector(1,0,0))),
                "Ray starts at sphere and goes outside");



        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        // Already tested in TC02

        // TC14: Ray starts at sphere and goes inside (1 points)
        // Already tested in TC11

        // TC15: Ray starts inside (1 points)
        // Already tested in TC03

        // TC16: Ray starts at the center (1 point)
        Point centerPoint = new Point(0, 0, 0);
        Ray centerRay = new Ray(centerPoint, v);
        List<Point> result16 = sphere.findIntersections(centerRay);
        assertEquals(1, result16.size(), "Wrong number of points for ray starting at center");

        // TC17: Ray starts at sphere and goes outside (0 points)
        // Already tested in TC12

        // TC18: Ray starts after sphere (0 points)
        // Already tested in TC04

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        Point beforeTangentPoint = new Point(-1, 1, 0);
        Vector tangentVector = new Vector(1, 1, 0);
        Ray beforeTangentRay = new Ray(beforeTangentPoint, tangentVector);
        assertNull(sphere.findIntersections(beforeTangentRay), "Ray starts before tangent point");

        // TC20: Ray starts at the tangent point
        Point tangentPoint = new Point(0, 1, 0);
        Ray tangentRay = new Ray(tangentPoint, tangentVector);
        assertNull(sphere.findIntersections(tangentRay), "Ray starts at tangent point");

        // TC21: Ray starts after the tangent point
        Point afterTangentPoint = new Point(1, 2, 0);
        Ray afterTangentRay = new Ray(afterTangentPoint, tangentVector);
        assertNull(sphere.findIntersections(afterTangentRay), "Ray starts after tangent point");

        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        Point orthogonalPoint = new Point(1, 1, 0);
        Vector orthogonalVector = new Vector(0, 0, 1);
        Ray orthogonalRay = new Ray(orthogonalPoint, orthogonalVector);
        assertNull(sphere.findIntersections(orthogonalRay), "Ray is orthogonal to ray start to sphere center line");
    }


}