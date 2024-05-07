package geometries;
import primitives.Point;
import primitives.Vector;
/**
 * Interface representing a geometric body in a three-dimensional Cartesian coordinate system.
 */
public interface Geometry {

    /**
     * Gets the normal vector (perpendicular) to the geometry at a given point.
     *
     * @param point the point on the geometry
     * @return the normal vector to the geometry at the given point
     */
   public Vector getNormal(Point point);
}