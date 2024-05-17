package primitives;

import geometries.Polygon;
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
//TODO: לחלק את הבדיקות למקרי קצה ומחלקות שקילות
    /**
     * Test method for {@link Vector#Vector(double, double, double)}.
     */
    @Test
    public void testConstructor() {
        // TC01: The given vector is zero
        assertThrows(IllegalArgumentException.class, () -> new Vector(0,0,0), "Constructed a zero vector");
    }
    /**
     * Test method for {@link Vector#Vector(Double3)}.
     */
    @Test
    public void testConstructor2() {
        // TC01: The given vector is zero
        assertThrows(IllegalArgumentException.class, () -> new Vector(0,0,0), "Constructed a zero vector");
    }

    /**
     * Test method for {@link Vector#add(Vector)}.
     */
    //איפה אני עושה את הבדיקות של חיסור בוקטור כי הרי אין לוקטור פונק חיסור
    @Test
    void testAdd() {
        assertThrows(IllegalArgumentException.class, () -> v1.add(v1Opposite), "add() Vector + itself does not throw an exception"); //?
        assertThrows(IllegalArgumentException.class, () -> v1.add(v1Opposite), "add() Vector + itself throws wrong exception");
        assertEquals(v1Opposite, v1.add(v2), "add(): Vector + Vector does not work correctly");
    }

    /**
     * Test method for {@link primitives.Vector#subtract(Point)}.
     */
    @Test
    void testSubtract() {
        assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1),
                "subtract() Vector - itself does not throw an exception"); //?
        assertThrows(IllegalArgumentException.class, () -> v1.add(v1Opposite),
                "subtract() Vector + itself throws wrong exception");
        assertEquals(v1Opposite, v1.add(v2),
                "subtract(): Vector + Vector does not work correctly");

    }

    /**
     * Test method for {@link Vector#dotProduct(Vector)}.
     */
    @Test
    void testDotProduct() {
        assertEquals(0, v1.dotProduct(v3), DELTA,
                " dotProduct(): for orthogonal vectors is not zero");
        assertEquals(0, v1.dotProduct(v2) + 28, DELTA,
                " dotProduct(): for orthogonal vectors is not zero");
    }

    /**
     * Test method for {@link primitives.Vector#subtract(Point)}.
     */
    @Test
    void testCrossProduct() {
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

    /**
     * Test method for {@link Vector#lengthSquared()}.
     */
    @Test
    void testLengthSquared() {
        assertEquals(0, v4.lengthSquared() - 9, DELTA, "lengthSquared() wrong length");
    }

    /**
     * Test method for {@link Vector#length()}.
     */
    @Test
    void testLength() {
        assertEquals(0, v4.length() - 3, "length() wrong length");
    }

    /**
     * Test method for {@link Vector#normalize()}.
     */
    @Test
    void testNormalize() {
        Vector v = new Vector(0, 3, 4);
        Vector n = v.normalize();
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(1d, n.lengthSquared(), DELTA, "normalize() the normalized vector is not a unit vector");
        assertThrows(IllegalArgumentException.class, () -> v.crossProduct(n), "normalize() normalized vector is not in the same direction");
        assertEquals(new Vector(0, 0.6, 0.8), n, "normalize() wrong normalized vector");
        assertTrue(v.dotProduct(n) > 0,
                "normalize() the normalized vector is opposite to the original one"); //?

    }

}