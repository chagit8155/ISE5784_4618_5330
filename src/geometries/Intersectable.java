package geometries;

import primitives.Point;
import primitives.Ray;
import java.util.List;

/**
 * Represents an interface for geometries that can be intersected by a ray.
 * Provides a method to find intersection points between the geometry and a given ray.
 */
public interface Intersectable {

    /**
     * Finds the intersection points between the geometry and a given ray.
     * @param ray The ray to intersect with the geometry.
     * @return A list of points where the ray intersects the geometry.
     */
    List<Point> findIntersections(Ray ray);
}

