package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {
    Vector v1 = new Vector(1, 2, 3);
    Vector v1Opposite = new Vector(-1, -2, -3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);
    Vector v4 = new Vector(1, 2, 2);
    public static final double DELTA = 0.00001;


    /**
     * Test method for {@link primitives.Vector#Vector(double, double, double)}.
     */
    @Test
    public void testConstructor() {
        // =============== Boundary Values Tests ==================
        // TC01: Tests that zero vector throws exception
        assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0), "ERROR: Vector() Constructed a zero vector");
    }

    /**
     * Test method for {@link primitives.Vector#Vector(Double3)}.
     */
    @Test
    public void testConstructor2() {
        // =============== Boundary Values Tests ==================
        // TC01: Tests that zero vector throws exception
        assertThrows(IllegalArgumentException.class, () -> new Vector(Double3.ZERO), "Constructed a zero vector");
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    public void testScalar(){
        Vector v1 = new Vector(1, 3, 5);
        // ============ Equivalence Partitions Tests ==============

        // TC01: Test that the new vector is the right one
        assertEquals(new Vector(2, 6, 10), v1.scale(2), "ERROR: scale() does not work correctly");

        // =============== Boundary Values Tests ==================

        // TC01: Test scaling by zero throws exception
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0),
                "ERROR: scale() does not throw exception for scaling by zero");

    }

    /**
     * Test method for {@link primitives.Vector#add(Vector)}.
     */

    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the new vector is the right one
        assertEquals(v1Opposite, v1.add(v2), "add(): Vector + Vector does not work correctly");
        // =============== Boundary Values Tests ==================
        // TC01: Test opposite direction vector throws exception
        assertThrows(IllegalArgumentException.class, () -> v1.add(v1Opposite), "add() Vector + itself does not throw an exception");
        // TC02: Test opposite direction vector throws right exception
        assertThrows(Exception.class, () -> v1.add(v1Opposite), "add() Vector + itself throws wrong exception");

    }

    /**
     * Test method for {@link primitives.Vector#subtract(Point)}.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the new vector is the right one
        assertEquals(new Vector(3,6,9), v1.subtract(v2), "subtract(): Vector - Vector does not work correctly");
        // =============== Boundary Values Tests ==================
        // TC01: Test subtraction of a vector from itself, which should throw an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1), "subtract() Vector - itself does not throw an exception"); //?


    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(Vector)}.
     */
    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Test vectors in acute angle
        Vector v6 = new Vector(1, 2, 3);
        Vector v7 = new Vector(4, 5, 6);
        assertEquals(32, v6.dotProduct(v7), "ERROR: dotProduct() for acute angle does not work correctly");
        // TC02: Test vectors in obtuse angle
        Vector v8 = new Vector(-1, -1, -3);
        assertEquals(-12, v6.dotProduct(v8), "ERROR: dotProduct() for obtuse angle does not work correctly");

        // ================== Boundary Values Tests ==================
        // TC03: Test orthogonal vectors
        assertEquals(0, v1.dotProduct(v3), DELTA, " dotProduct(): for orthogonal vectors is not zero");


        // TC04: Text dotProduct when one of the vectors is the unit vector
        Vector v5 = new Vector(1, 0, 0);
        assertEquals(1, v1.dotProduct(v5), "ERROR: dotProduct() for unit vector does not work correctly");
    }


    /**
     * Test method for {@link primitives.Vector#subtract(Point)}.
     */
    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector vr = v1.crossProduct(v3); //vr : ->(-13,2,3)
        // TC01: Test simple that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v3.length(), vr.length(), DELTA, "crossProduct() wrong result length");
        // TC02: Test cross-product result orthogonality to its operands
        assertEquals(0, vr.dotProduct(v1), "crossProduct() result is not orthogonal to 1st operand");
        assertEquals(0, vr.dotProduct(v3), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC3: test zero vector from cross-product of parallel vectors
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2),
                "crossProduct() for parallel vectors does not throw an exception");

    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test length squared
        assertEquals(9, v4.lengthSquared() , DELTA, "lengthSquared() wrong length squared");
    }


    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test length
        assertEquals(0, v4.length() - 3, "length() wrong length");
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    void testNormalize() {
        Vector v = new Vector(0, 3, 4);
        Vector n = v.normalize();
        Vector unit = new Vector(1, 0, 0);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(1d, n.lengthSquared(), DELTA, "normalize() the normalized vector is not a unit vector");
        // TC02: Ensure the normalized vector is in the same direction
        assertThrows(IllegalArgumentException.class, () -> v.crossProduct(n), "normalize() normalized vector is not in the same direction");
        assertEquals(new Vector(0, 0.6, 0.8), n, "normalize() wrong normalized vector");
        assertTrue(v.dotProduct(n) > 0, "normalize() the normalized vector is opposite to the original one"); //?
        // =============== Boundary Values Tests ==================
        // TC03: Check normalization of a unit vector remains a unit vector
        assertEquals(1d, unit.normalize().lengthSquared(), DELTA, "normalize() the normalized vector is not a unit vector");
    }

}