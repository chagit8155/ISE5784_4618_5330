package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Represents a plane in a three-dimensional Cartesian coordinate system.
 */
public class Plane implements Geometry {
    // Point on the plane
    private final Point point;

    // Normal vector to the plane
    private final Vector normal;

    /**
     * Constructs a new plane with three given points.
     *
     * @param p1 the first point
     * @param p2 the second point
     * @param p3 the third point
     */
    public Plane(Point p1, Point p2, Point p3) {
        // Calculate the normal vector to the plane using the given points
        //v1 = p2 - p1
        //v2 = p3 - p1
        // normalize(  v1            *             v2 )
        normal = p1.subtract(p2).crossProduct(p1.subtract(p3)).normalize();
        // this.normal = null; //  stage 1

        // Set one of the points as the reference point of the plane
        this.point = p1;
    }

    /**
     * Constructs a new plane with the given point and normal vector.
     *
     * @param point  a point on the plane
     * @param normal the normal vector to the plane
     */
    public Plane(Point point, Vector normal) {
        this.point = point;
        // Normalize the normal vector
        this.normal = normal.normalize();
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    public Vector getNormal() {
        // return getNormal(null); //
        return normal;
        //return  null; //stage 1
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        Vector v = ray.getDirection();
        double nv = normal.dotProduct(v);

        // If the ray is parallel to the plane (normal is orthogonal to direction), there are no intersections
        if (Util.isZero(nv))
            return null;

        // If the ray starts at the plane's reference point, return null
        if (point.equals(ray.getHead()))
            return null;

        // Calculate the intersection point t using the plane equation
        double t = Util.alignZero(normal.dotProduct(point.subtract(ray.getHead())) / nv);

        // If t is positive, return the intersection point; otherwise, return null
        if (t > 0)
            return List.of(ray.getPoint(t));
        return null;
    }

}
