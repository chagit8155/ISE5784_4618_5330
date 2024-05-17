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

            // Define cylinder parameters
            double radius = 2.0;
            Ray axisRay = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)); // Cylinder along the z-axis
            double height = 5.0;

            // Create the cylinder
            Cylinder cylinder = new Cylinder(radius, axisRay, height);

            // ============ Equivalence Partitions Tests ==============

            //TC01: Test normal vector at points on the lateral surface of the cylinder
        //TODO :
            Point pointOnLateralSurface = new Point(1, 2, 3); // Arbitrary point on the lateral surface
            Vector normalLateral = cylinder.getNormal(pointOnLateralSurface);
            Vector expectedNormalLateral = axisRay.getDirection(); // Normal should be the axis direction
          //  assertEquals(expectedNormalLateral, normalLateral, "Incorrect normal vector on the lateral surface");

            //TC02: Test normal vector at points on the top base of the cylinder
            Point pointOnTopBase = new Point(0, 0, height); // Top base point
            Vector normalTopBase = cylinder.getNormal(pointOnTopBase);
            Vector expectedNormalTopBase = new Vector(0, 0, 1); // Normal should be (0, 0, 1)
            assertEquals(expectedNormalTopBase, normalTopBase, "Incorrect normal vector on the top base");

            //TC03: Test normal vector at points on the bottom base of the cylinder
            Point pointOnBottomBase = new Point(0, 0, 0); // Bottom base point
            Vector normalBottomBase = cylinder.getNormal(pointOnBottomBase);
            Vector expectedNormalBottomBase = new Vector(0, 0, 1); // Normal should be (0, 0, 1)
            assertEquals(expectedNormalBottomBase, normalBottomBase, "Incorrect normal vector on the bottom base");

            // =============== Boundary Values Tests ==================
            //TC04: Test normal vector at the center of the top base of the cylinder
            Point centerTopBase = new Point(0, 0, height); // Center of the top base
            Vector normalCenterTopBase = cylinder.getNormal(centerTopBase);
            assertEquals(expectedNormalTopBase, normalCenterTopBase, "Incorrect normal vector at the center of the top base");

            //TC05: Test normal vector at the center of the bottom base of the cylinder
            Point centerBottomBase = new Point(0, 0, 0); // Center of the bottom base
            Vector normalCenterBottomBase = cylinder.getNormal(centerBottomBase);
            assertEquals(expectedNormalBottomBase, normalCenterBottomBase, "Incorrect normal vector at the center of the bottom base");
        }
    }
