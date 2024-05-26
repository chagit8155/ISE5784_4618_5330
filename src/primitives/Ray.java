package primitives;

/**
 * Represents a ray in three-dimensional space, consisting of a starting point (head) and a direction.
 */
public class Ray {
    /**
     * The starting point of the ray.
     */
    final private Point head;
    /**
     * The direction vector of the ray.
     */
    final private Vector direction;

    /**
     * Constructs a ray with the specified head (starting point) and direction.
     * The direction vector is normalized before storing it.
     *
     * @param head      The starting point of the ray.
     * @param direction The direction vector of the ray.
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        // Normalize the direction vector before storing it
        this.direction = direction.normalize();
    }

    /**
     * Returns the starting point of the ray.
     *
     * @return The starting point of the ray.
     */
    public Point getHead() {
        return head;
    }

    /**
     * Returns the direction vector of the ray.
     *
     * @return The direction vector of the ray.
     */
    public Vector getDirection() {
        return direction;
    }


    /**
     * Calculates a point on the ray at a given distance from the ray's origin.
     *
     * @param t The distance from the ray's origin.
     * @return The point on the ray at the distance t from the ray's origin. If t is zero, returns the origin point of the ray.
     */
    public  Point getPoint(double t) {
        if(Util.isZero(t))
            return head;
        return  head.add(direction.scale(t));
    }

    @Override
    public String toString() {
        return "Ray:" + head + "->" + direction;

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return  (obj instanceof Ray other) && head.equals(other.head) && direction.equals(other.direction);
    }
}

