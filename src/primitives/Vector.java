package primitives;

/**
 * Represents a vector in three-dimensional space with direction and magnitude.
 * Extends the Point class to inherit methods for vector operations.
 */
public class Vector extends Point {

    /**
     * Constructs a vector with the specified coordinates.
     *
     * @param x The x-coordinate of the vector.
     * @param y The y-coordinate of the vector.
     * @param z The z-coordinate of the vector.
     * @throws IllegalArgumentException if the given coordinates form a zero vector.
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("The given vector is zero");
        }
    }

    /**
     * Constructs a vector with the specified coordinates as a Double3 object.
     *
     * @param _xyz The coordinates of the vector as a Double3 object.
     * @throws IllegalArgumentException if the given coordinates form a zero vector.
     */
    public Vector(Double3 _xyz) {
        super(_xyz);
        if (_xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("The given vector is zero");
        }
     //   super.xyz = _xyz;
    }

    /**
     * Adds another vector to this vector, resulting in a new vector.
     *
     * @param other The vector to add.
     * @return The new vector resulting from adding the other vector to this vector.
     */
    public Vector add(Vector other) {
        return new Vector(this.xyz.add(other.xyz));
    }

    @Override
    public Vector subtract(Point p2) {
        return super.subtract(p2);
    }

    /**
     * Scales this vector by a scalar value, resulting in a new vector.
     *
     * @param scalar The scalar value to scale the vector by.
     * @return The new scaled vector.
     */
    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    /**
     * Computes the dot product of this vector with another vector.
     *
     * @param other The other vector.
     * @return The dot product of this vector and the other vector.
     */
    public double dotProduct(Vector other) {
        return xyz.d1 * other.xyz.d1 + xyz.d2 * other.xyz.d2 + xyz.d3 * other.xyz.d3;
    }


    /**
     * Computes the cross product of this vector with another vector.
     *
     * @param other The other vector.
     * @return The cross product of this vector and the other vector.
     */
    public Vector crossProduct(Vector other) {
        double x = xyz.d2 * other.xyz.d3 - xyz.d3 * other.xyz.d2;
        double y = xyz.d3 * other.xyz.d1 - xyz.d1 * other.xyz.d3;
        double z = xyz.d1 * other.xyz.d2 - xyz.d2 * other.xyz.d1;
        return new Vector(x, y, z);
    }


    /**
     * Calculates the squared length of this vector.
     *
     * @return The squared length of this vector.
     */
    public double lengthSquared() {
        return super.distanceSquared(new Point(0, 0, 0));
    }

    /**
     * Calculates the length of this vector.
     *
     * @return The length of this vector.
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Returns a new vector that is the normalization of this vector.
     *
     * @return The normalized vector.
     * @throws IllegalArgumentException if this vector is a zero vector.
     */
    public Vector normalize() {
        double length = length();
        if (length == 0) {
            throw new IllegalArgumentException("Cannot normalize zero vector");
        }
        return scale(1 / length);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Vector other && super.equals(other);
        //  return false;
    }

    @Override
    public String toString() {
        return "->" + super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

