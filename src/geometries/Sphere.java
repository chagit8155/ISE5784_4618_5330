package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
    public List<Point> findIntersections(Ray ray) {
        //(-1,0,0) (3,1,0)
        Point p0 = ray.getHead();
        Vector dir = ray.getDirection();

        // Deals with case where ray starts from the center of the sphere
        if (p0.equals(center))
            return List.of(ray.getPoint(this.radius));

        // Finding the hypotenuse, base and perpendicular of the triangle formed by
        // ray's starting point, the center of the sphere and the intersection point of
        // the ray and the perpendicular line crossing the sphere's center.
        Vector hypotenuse = this.center.subtract(p0);
        double base = dir.dotProduct(hypotenuse);
        double perpendicular = Math.sqrt(hypotenuse.dotProduct(hypotenuse) - base * base);

        // Dealing with a case in which the ray is perpendicular to the sphere at the
        // intersection point.
        if (perpendicular == this.radius)
            return null;

        // Returning intersection points, ensuring that only those intersected by the
        // ray are returned.
        double inside = Math.sqrt(Math.pow(this.radius, 2) - Math.pow(perpendicular, 2));
        if (alignZero(base - inside) > 0 && alignZero(base + inside) > 0)
            return List.of(ray.getPoint(base - inside), ray.getPoint(base + inside));
        else if (base - inside > 0)
            return List.of(ray.getPoint(base - inside));
        else if (base + inside > 0)
            return List.of(ray.getPoint(base + inside));
        return null;
    }

}
