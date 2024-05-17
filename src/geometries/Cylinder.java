package geometries;

import primitives.*;

import static primitives.Util.*;



/**
 * Represents a cylinder in three-dimensional space, extending along a specified axis.
 * The cylinder is defined by its radius, axis (direction), and height.
 */
public class Cylinder extends Tube {
    /**
     * The height of the cylinder.
     */
    private final double height;

    /**
     * Constructs a cylinder with the specified radius, axis, and height.
     *
     * @param radius The radius of the cylinder.
     * @param axis   The axis of the cylinder, represented by a ray.
     * @param height The height of the cylinder.
     */
    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        this.height = height;
    }


    @Override
    public Vector getNormal(Point point) {
        Point p0 = axis.getHead();
        Vector v = axis.getDirection();

        //returns v because it is in the direction of the axis
        if (point.equals(p0))
            return v;

        //project point-p0 on the ray
        Vector u = point.subtract(p0);

        // distance from p0 to p1
        double t = alignZero(u.dotProduct(v));

        //if the given point is at the base of the cylinder, return direction vector
        if (t == 0 || isZero(height - t))
            return v;

        //Calculates the other point on the axis facing the given point
        Point p1= p0.add(v.scale(t));

        //return the normalized vector
        return point.subtract(p1).normalize();
    }
}

