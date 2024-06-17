package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

/**
 * Represents a tube in three-dimensional space, extending along a specified axis.
 * The tube is defined by its radius and axis (direction).
 */
public class Tube extends RadialGeometry {
    /**
     * The axis (direction) of the tube, represented by a ray.
     */
    protected final Ray axis;

    /**
     * Constructs a tube with the specified radius and axis.
     *
     * @param radius The radius of the tube.
     * @param axis   The axis of the tube, represented by a ray.
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    @Override
    public Vector getNormal(Point point) {

        Vector axisDirection = axis.getDirection();
        // t =             v       dot        (p    -   p0)
        double t = axisDirection.dotProduct(point.subtract(axis.getHead()));
        // o =      p0 + t dot v
        Point o; //projectionPoint
        if (Util.isZero(t))
            o = axis.getHead();
        else
            o = axis.getPoint(t);
        // n = normalize( p - o )
        return point.subtract(o).normalize();
    }

    //     if (Util.isZero(t))
//    o=axis.getHead();
//        else
//    o=axis.getPoint(t);

    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        return null;
    }
}

