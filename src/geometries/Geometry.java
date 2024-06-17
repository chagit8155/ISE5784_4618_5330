package geometries;

import primitives.*;

import java.util.List;

/**
 * Interface representing a geometric body in a three-dimensional Cartesian coordinate system.
 *
 * @author H & H
 */
public abstract class Geometry extends Intersectable {

// stage 6 - Because we added a field, we changed the interface to an abstract class
// Field for emission color, initialized to black
protected Color emission = Color.BLACK;

    /**
     * Getter for the emission color.
     *
     * @return the emission color of the geometry
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Setter for the emission color, returns this object for method chaining.
     *
     * @param emission the emission color to set
     * @return this geometry object
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }



    /**
     * Gets the normal vector (perpendicular) to the geometry at a given point.
     *
     * @param point the point on the geometry
     * @return the normal vector to the geometry at the given point
     */
    public abstract Vector getNormal(Point point);




    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        return null;/////////////?
    }
}