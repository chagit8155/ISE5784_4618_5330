package primitives;

public class Vector extends Point {

    public Vector(double x, double y, double z) {
        super(x,y,z);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("The given point is zero");
        }
    }
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("The given point is zero");
        }

    }
    public Vector add(Vector other) {
        return new Vector(super.add(other).xyz);
    }

    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }
    public double dotProduct(Vector other) {
        return xyz.d1 * other.xyz.d1 + xyz.d2 * other.xyz.d2 + xyz.d3 * other.xyz.d3;
    }
    public Vector crossProduct(Vector other) {
        double x = xyz.d2 * other.xyz.d3 - xyz.d3 * other.xyz.d2;
        double y = xyz.d3 * other.xyz.d1 - xyz.d1 * other.xyz.d3;
        double z = xyz.d1 * other.xyz.d2 - xyz.d2 * other.xyz.d1;
        return new Vector(x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vector)) return false;
        Vector other = (Vector) obj;
        return this.xyz.equals(other.xyz);
    }

    public double lengthSquared() {
        return super.distanceSquared(new Point(0, 0, 0));
    }

    /**
     * Calculates the length of this Vector.
     * @return the length
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Returns a new Vector that is the normalization of this Vector.
     * @return the normalized Vector
     */
    public Vector normalize() {
        double length = length();
        if (length == 0) {
            throw new IllegalArgumentException("Cannot normalize zero vector");
        }
        return scale(1 / length);
    }


}
