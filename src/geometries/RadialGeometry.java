package geometries;

/**
 * Abstract class representing a radial geometry in a three-dimensional Cartesian coordinate system.
 * Radial geometry is characterized by its radius.
 */
public abstract class RadialGeometry extends Geometry {

    // Radius of the radial geometry
    protected double radius;

    /**
     * Constructs a new radial geometry with the given radius.
     * @param radius the radius of the radial geometry
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }





}
