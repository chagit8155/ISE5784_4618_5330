package primitives;

import java.util.List;

import static geometries.Intersectable.GeoPoint;

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
    public Point getPoint(double t) {
        if (Util.isZero(t))
            return head;
        return head.add(direction.scale(t));
    }

    @Override
    public String toString() {
        return "Ray:" + head + "->" + direction;

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other) && head.equals(other.head) && direction.equals(other.direction);
    }


    public Point findClosestPoint(List<Point> intersections) {
        if (intersections == null || intersections.isEmpty()) {
            return null;
        }
        return findClosestGeoPoint(intersections.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }


    /**
     * Finds the closest GeoPoint to the ray's origin.
     *
     * @param points the list of GeoPoints to search through
     * @return the closest GeoPoint to the ray's origin
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
        if (points == null || points.isEmpty()) {
            return null;
        }

        GeoPoint closestPoint = null;
        double closestDistance = Double.MAX_VALUE;

        for (GeoPoint geoPoint : points) {
            double distance = geoPoint.point.distanceSquared(head);// we used distanceSquared just because its more efficient
            if (distance < closestDistance) {
                closestDistance = distance;
                closestPoint = geoPoint;
            }
        }

        return closestPoint;
    }
}

