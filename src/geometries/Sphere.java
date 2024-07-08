package geometries;

import primitives.*;

import static geometries.Intersectable.GeoPoint;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * Represents a sphere in three-dimensional space.
 * A sphere is defined by its radius and center point.
 */
public class Sphere extends RadialGeometry {
    /**
     * The center point of the sphere.
     */
    private final Point center;

    /**
     * Constructs a sphere with the specified radius and center point.
     *
     * @param radius The radius of the sphere.
     * @param center The center point of the sphere.
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    @Override
    public Vector getNormal(Point point) {

        // normalize(  center  -  p )
        return point.subtract(center).normalize();

    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<Point> intersections = this.findIntersectionsHelp(ray, maxDistance);
        return intersections == null ? null
                : intersections.stream().map(point -> new GeoPoint(this, point)).toList();
    }



    private List<Point> findIntersectionsHelp(Ray ray, double maxDistance) {
        //(-1,0,0) (3,1,0)
        Point p0 = ray.getHead();
        Vector vDir = ray.getDirection();

        // Deals with case where ray starts from the center of the sphere
        if (p0.equals(center))  //one point
            return List.of(ray.getPoint(this.radius));

        // Finding the hypotenuse, base and perpendicular of the triangle formed by
        // ray's starting point, the center of the sphere and the intersection point of
        // the ray and the perpendicular line crossing the sphere's center.
        Vector uHypotenuse = this.center.subtract(p0);
        double tmBase = vDir.dotProduct(uHypotenuse);
        double dPerpendicular = Math.sqrt(uHypotenuse.dotProduct(uHypotenuse) - tmBase * tmBase);

        // Dealing with a case in which the ray is perpendicular to the sphere at the
        // intersection point.
        if (dPerpendicular >= this.radius) // 0 points
            return null;

        // Returning intersection points, ensuring that only those intersected by the
        // ray are returned.
        double thInside = Math.sqrt(Math.pow(this.radius, 2) - Math.pow(dPerpendicular, 2));

        double t1 = tmBase - thInside;
        double t2 = tmBase + thInside;

        boolean t1Valid = alignZero(t1) > 0 && alignZero(t1 - maxDistance) < 0;
        boolean t2Valid = alignZero(t2) > 0 && alignZero(t2 - maxDistance) < 0;

        if (t1Valid && t2Valid) // 2 points
            return List.of(ray.getPoint(t1), ray.getPoint(t2));

        else if (t1Valid)  // 1 point
            return List.of(ray.getPoint(t1));

        else if (t2Valid)  // 1 point
            return List.of(ray.getPoint(t2));

        return null; // else 0 points
    }
}


