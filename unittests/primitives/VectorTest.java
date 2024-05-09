package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

class VectorTest {
    Vector v1 = new Vector(1, 2, 3);
    Vector v1Opposite = new Vector(-1, -2, -3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);
    Vector v4 = new Vector(1, 2, 2);
    public static final double DELTA = 0.00001;

    // צריך פה גם טסט לctor??
    // Test add & subtract
//        try {
//        v1.add(v1Opposite);
//        out.println("ERROR: Vector + itself does not throw an exception");
//    } catch (IllegalArgumentException ignore) {
//    } catch (Exception ignore) {
//        out.println("ERROR: Vector + itself throws wrong exception");
//    }
//        try {
//        v1.subtract(v1);
//        out.println("ERROR: Vector - itself does not throw an exception");
//    } catch (IllegalArgumentException ignore) {
//    } catch (Exception ignore) {
//        out.println("ERROR: Vector + itself throws wrong exception");
//    }
//        if (!v1.add(v2).equals(v1Opposite))
//            out.println("ERROR: Vector + Vector does not work correctly");
//        if (!v1.subtract(v2).equals(new Vector(3, 6, 9)))
//            out.println("ERROR: Vector + Vector does not work correctly");

    //איפה אני עושה את הבדיקות של חיסור בוקטור כי הרי אין לוקטור פונק חיסור
    @Test
    void add() {
        assertThrows(IllegalArgumentException.class, () -> v1.add(v1Opposite), "add() Vector + itself does not throw an exception");
        assertThrows(IllegalArgumentException.class, () -> v1.add(v1Opposite), "add() Vector + itself throws wrong exception");
        assertEquals(v1Opposite, v1.add(v2), "add(): Vector + Vector does not work correctly");
    }

    @Test
    void scale() {
    }

    //// test Dot-Product
//        if (!isZero(v1.dotProduct(v3)))
//            out.println("ERROR: dotProduct() for orthogonal vectors is not zero");
//        if (!isZero(v1.dotProduct(v2) + 28))
//            out.println("ERROR: dotProduct() wrong value");
    @Test
    void dotProduct() {
        assertEquals(0, v1.dotProduct(v3), DELTA, " dotProduct(): for orthogonal vectors is not zero");
        assertEquals(0, v1.dotProduct(v2) + 28, DELTA, " dotProduct(): for orthogonal vectors is not zero");
    }

//    // test Cross-Product
//        try { // test zero vector
//        v1.crossProduct(v2);
//        out.println("ERROR: crossProduct() for parallel vectors does not throw an exception");
//    } catch (Exception e) {
//    }
//    Vector vr = v1.crossProduct(v3);
//        if (!isZero(vr.length() - v1.length() * v3.length()))
//            out.println("ERROR: crossProduct() wrong result length");
//        if (!isZero(vr.dotProduct(v1)) || !isZero(vr.dotProduct(v3)))
//            out.println("ERROR: crossProduct() result is not orthogonal to its operands");
    /**
     * Test method for
     * {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void crossProduct() {

        // ============ Equivalence Partitions Tests ==============
        Vector vr = v1.crossProduct(v3);
        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v3.length(), vr.length(), DELTA, "crossProduct() wrong result length");
        // TC02: Test cross-product result orthogonality to its operands
        assertEquals(0, vr.dotProduct(v1), "crossProduct() result is not orthogonal to 1st operand");
        assertEquals(0, vr.dotProduct(v3), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of parallel vectors
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2),
                "crossProduct() for parallel vectors does not throw an exception");

    }


    // test length
//        if (!isZero(v4.lengthSquared() - 9))
//            out.println("ERROR: lengthSquared() wrong value");
//        if (!isZero(v4.length() - 3))
//            out.println("ERROR: length() wrong value");
    @Test
    void lengthSquared() {
        assertEquals(0, v4.lengthSquared() - 9, DELTA, "lengthSquared() wrong length");
    }

    @Test
    void length() {
        assertEquals(0, v4.length() - 3, "length() wrong length");
    }
    // test vector normalization vs vector length and cross-product
//    Vector v = new Vector(1, 2, 3);
//    Vector u = v.normalize();
//        if (!isZero(u.length() - 1))
//            out.println("ERROR: the normalized vector is not a unit vector");
//        try { // test that the vectors are co-lined
//        v.crossProduct(u);
//        out.println("ERROR: the normalized vector is not parallel to the original one");
//    } catch (Exception e) {
//    }
//        if (v.dotProduct(u) < 0)
//            out.println("ERROR: the normalized vector is opposite to the original one");
//
//        out.println("If there were no any other outputs - all tests succeeded!");
//}

    @Test
    void normalize() {
        Vector v = new Vector(0, 3, 4);
        Vector n = v.normalize();
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(1d, n.lengthSquared(), DELTA, "normalize() the normalized vector is not a unit vector");
        assertThrows(IllegalArgumentException.class, () -> v.crossProduct(n), "normalize() normalized vector is not in the same direction");
        assertEquals(new Vector(0, 0.6, 0.8), n, "normalize() wrong normalized vector");
        assertTrue(v.dotProduct(n) < 0, "normalize() the normalized vector is opposite to the original one");

    }

}