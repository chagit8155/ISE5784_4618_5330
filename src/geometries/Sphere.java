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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<Point> intersections = this.findIntersectionsHelp(ray);
        return intersections == null ? null
                : intersections.stream().map(point -> new GeoPoint(this,point)).toList();
    }


    private List<Point> findIntersectionsHelp(Ray ray) {

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
        if (dPerpendicular == this.radius) // 0 points
            return null;

        // Returning intersection points, ensuring that only those intersected by the
        // ray are returned.
        double thInside = Math.sqrt(Math.pow(this.radius, 2) - Math.pow(dPerpendicular, 2));

        if (alignZero(tmBase - thInside) > 0 && alignZero(tmBase + thInside) > 0) // 2 points
            return List.of(ray.getPoint(tmBase - thInside), ray.getPoint(tmBase + thInside));

        else if (tmBase - thInside > 0)  // 1 point
            return List.of(ray.getPoint(tmBase - thInside));

        else if (tmBase + thInside > 0)  // 1 point
            return List.of(ray.getPoint(tmBase + thInside));

        return null; // else 0 points
    }

}
