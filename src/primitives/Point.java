package primitives;

/**
 * Represents a point in three-dimensional space with coordinates (x, y, z).
 */
public class Point {

    /**
     * The coordinates of the point in three-dimensional space.
     */
    protected final Double3 xyz;

    /**
     * Represents the origin point (0, 0, 0).
     */
    public static final Point ZERO = new Point(0, 0, 0);

    /**
     * Constructs a point with the specified coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @param z The z-coordinate of the point.
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
     * Constructs a point with the specified coordinates as a Double3 object.
     *
     * @param xyz The coordinates of the point as a Double3 object.
     */
    Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Computes the vector from this point to another point.
     *
     * @param p2 The other point.
     * @return The vector from this point to the other point.
     */
    public Vector subtract(Point p2) {
        return new Vector(this.xyz.subtract(p2.xyz));
    }

    /**
     * Adds a vector to this point, resulting in a new point.
     *
     * @param vec The vector to add.
     * @return The new point resulting from adding the vector to this point.
     */
    public Point add(Vector vec) {
        return new Point(this.xyz.add(vec.xyz));
    }

    /**
     * Computes the squared distance between this point and another point.
     *
     * @param other The other point.
     * @return The squared distance between this point and the other point.
     */
    public double distanceSquared(Point other) {
        Double3 tmp = xyz.subtract(other.xyz).product(xyz.subtract(other.xyz));
        return tmp.d1 + tmp.d2 + tmp.d3;
    }

    /**
     * Computes the distance between this point and another point.
     *
     * @param other The other point.
     * @return The distance between this point and the other point.
     */
    public double distance(Point other) {
        return Math.sqrt(distanceSquared(other));
    }
    /**
     * Get the value of x.
     *
     * @return the value of x
     */
    public double getX() {
        return xyz.d1;
    }
    /**
     * Retrieves the value of Y.
     *
     * @return the value of Y
     */
    public double getY() {
        return xyz.d2;
    }
    /**
     * Retrieves the value of Z.
     *
     * @return  the value of Z
     */
    public double getZ() {
        return xyz.d3;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) return true;
//        if (!(obj instanceof Point)) return false;
//        Point other = (Point) obj;
//        return this.xyz.equals(other.xyz);
//    }


    @Override
    public int hashCode() {
        return xyz.hashCode();
    }


//    @Override
//    public String toString() {
//        return xyz.toString();
//    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Point other && xyz.equals(other.xyz);
    }
    @Override
    public String toString() { return "" + xyz; }
}

