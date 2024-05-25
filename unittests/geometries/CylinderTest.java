package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    /**
     * Test method for {@link Cylinder#getNormal(Point)}.
     */
    @Test
    void getNormal() {

        Ray axisRay = new Ray(new Point(1, 2, 3), new Vector(0, 0, 1));
        Cylinder cylinder = new Cylinder(1, axisRay, 5);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Point on the side surface
        Point pointOnSide = new Point(2, 2, 4);
        Vector normalSide = cylinder.getNormal(pointOnSide);
        assertEquals(new Vector(1, 0, 0), normalSide, "TC01: wrong normal to cylinder side surface");

        // TC02: Point on the bottom base
        Point pointOnBottomBase = new Point(1.5, 2, 3);
        Vector normalBottomBase = cylinder.getNormal(pointOnBottomBase);
        assertEquals(new Vector(0, 0, -1), normalBottomBase, "TC02: wrong normal to cylinder bottom base");

        // TC03: Point on the top base
        Point pointOnTopBase = new Point(1.5, 2, 8);
        Vector normalTopBase = cylinder.getNormal(pointOnTopBase);
        assertEquals(new Vector(0, 0, 1), normalTopBase, "TC03: wrong normal to cylinder top base");

        // =============== Boundary Values Tests ==================
        // TC04: Point on the edge of the bottom base
        Point pointOnBottomEdge = new Point(2, 2, 3);
        Vector normalBottomEdge = cylinder.getNormal(pointOnBottomEdge);
        assertEquals(new Vector(0, 0, -1), normalBottomEdge, "TC04: wrong normal to cylinder bottom edge");

        // TC05: Point on the edge of the top base
        Point pointOnTopEdge = new Point(2, 2, 8);
        Vector normalTopEdge = cylinder.getNormal(pointOnTopEdge);
        assertEquals(new Vector(0, 0, 1), normalTopEdge, "TC05: wrong normal to cylinder top edge");

        // TC06: Point at the center of the bottom base
        Point pointCenterBottom = new Point(1, 2, 3);
        Vector normalCenterBottom = cylinder.getNormal(pointCenterBottom);
        assertEquals(new Vector(0, 0, -1), normalCenterBottom, "TC06: wrong normal to cylinder center bottom");

        // TC07: Point at the center of the top base
        Point pointCenterTop = new Point(1, 2, 8);
        Vector normalCenterTop = cylinder.getNormal(pointCenterTop);
        assertEquals(new Vector(0, 0, 1), normalCenterTop, "TC07: wrong normal to cylinder center top");

    }
    }
