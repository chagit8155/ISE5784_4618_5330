package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
    public Vector getNormal(Point p) {
        return null;
    }
}

