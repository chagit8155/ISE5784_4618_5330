package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    /**
     * Test method for {@link Tube#getNormal(Point)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: There is a simple single test here (all the points are the same)
        // A point on the tube
        Point pointOnTube = new Point(1, 1, 1);

        // The direction of the tube's axis
        Vector tubeDirection = new Vector(1, 0, 0);

        // The axis of the tube
        Ray axis = new Ray(Point.ZERO, new Vector(1, 0, 0));

        // Create the tube
        Tube tube = new Tube(1, axis);

        // Calculate the normal vector at the point on the tube
        Vector normal = tube.getNormal(pointOnTube);

        // Check that the normal vector is perpendicular to the tube's axis direction vector,
        // i.e., their dot product is 0
        assertEquals(0, normal.dotProduct(tubeDirection), "Incorrect calculation of Tube's normal"); // אם המכפלה הסקלרית בן כיוון הצינור לנורמל שהתקבל שווה אפס אז הוא אכן מאונך

        // =============== Boundary Values Tests ==================
        //TC02: 1 extreme case when the vector (p-p0) is orthogonal to v

        Point orthogonalPoint = new Point(0, 1, 0);
        Vector orthogonalNormal = tube.getNormal(orthogonalPoint);
        assertEquals(0, orthogonalNormal.dotProduct(tubeDirection), "Incorrect calculation of Tube's normal for orthogonal point");
    }



    @Test
    void findIntersections() {

    }
}