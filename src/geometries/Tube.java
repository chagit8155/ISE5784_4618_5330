package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

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


    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray , double maxDistance) {
        //credit to the site: https://mrl.cs.nyu.edu/~dzorin/rend05/lecture2.pdf

        Point p0 = ray.getHead();
        Vector v = ray.getDirection();
        Point pa = this.axis.getHead();
        Vector va = this.axis.getDirection();
        double radius2 = this.radius * this.radius;

        double a, b, c; //ax^2 + bx + c

        // a = (v-(v,va)va)^2
        // b = 2(v-(v,va)va,delP-(delP,va)va)
        // c = (delP-(delP,va)va)^2 - r^2
        // delP = p0-pa

        //note: (v,u) = v dot product u = vu = v*u

        //calculates a:
        Vector vecA = v;
        try {
            double vva = v.dotProduct(va); //(v,va)
            if (!isZero(vva)) vecA = v.subtract(va.scale(vva)); //v-(v,va)va
            a = vecA.lengthSquared(); //(v-(v,va)va)^2
        }
        catch (IllegalArgumentException e) {
            return null; //if a=0 there are no intersections because Ray is parallel to axisRay
        }

        //calculates deltaP (delP), b, c:
        try {
            Vector deltaP = p0.subtract(pa); //p0-pa
            Vector deltaPMinusDeltaPVaVa = deltaP;
            double deltaPVa = deltaP.dotProduct(va); //(delP,va)va)
            if (!isZero(deltaPVa)) deltaPMinusDeltaPVaVa = deltaP.subtract(va.scale(deltaPVa)); //(delP-(delP,va)va)
            b = 2 * (vecA.dotProduct(deltaPMinusDeltaPVaVa)); //2(v-(v,va)va,delP-(delP,va)va)
            c = deltaPMinusDeltaPVaVa.lengthSquared() - radius2; //(delP-(delP,va)va)^2 - r^2
        }
        catch (IllegalArgumentException e) {
            b = 0; //if delP = 0, or (delP-(delP,va)va = (0, 0, 0)
            c = -1 * radius2;
        }

        //solving the quadratic equation: ax^2 + bx + c = 0
        double discriminator = alignZero(b * b - 4 * a * c); //discriminator: b^2 - 4ac
        if (discriminator <= 0) return null; //there are no intersections because Ray is parallel to axisRay

        //the solutions for the equation: (-b +- discriminator) / 2a
        double sqrtDiscriminator = Math.sqrt(discriminator);
        double t1 = alignZero(-b + sqrtDiscriminator) / (2 * a);
        double t2 = alignZero(-b - sqrtDiscriminator) / (2 * a);

        //if t1 or t2 are bigger than maxDistance they wll be set to negative value
        if (alignZero(t1 - maxDistance) > 0) t1 = -1;
        if (alignZero(t2 - maxDistance) > 0) t2 = -1;

        //takes all positive solutions
        if (t1 > 0 && t2 > 0)
            return twoPoints(ray, new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
        if (t1 > 0) return List.of(new GeoPoint(this, ray.getPoint(t1)));
        if (t2 > 0) return List.of(new GeoPoint(this, ray.getPoint(t2)));

        return null;
    }


    /**
     * "If the distance from the ray's start point to the first point is less than the distance from the ray's start point
     * to the second point, return the first point followed by the second point, otherwise return the second point followed
     * by the first point."
     *
     * The function is used in the following function:
     *
     * @param ray the ray we're checking for intersections with
     * @param p1 The first intersection point.
     * @param p2 The point on the ray that is closest to the intersection point.
     * @return A list of two points.
     */
    protected List<GeoPoint> twoPoints(Ray ray, GeoPoint p1, GeoPoint p2) {
        Point p0 = ray.getHead();
        return p0.distanceSquared(p1.point) <= p0.distanceSquared(p2.point) ? List.of(p1, p2) : List.of(p2, p1);
    }
}

