package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
        return null;
    }
}

