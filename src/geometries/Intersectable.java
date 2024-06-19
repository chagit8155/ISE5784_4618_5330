package geometries;

import primitives.*;
import java.util.List;

/**
 * Represents an abstract class for geometries that can be intersected by a ray.
 * Provides a method to find intersection points between the geometry and a given ray.
 */
public abstract class Intersectable {

    /**
     * Inner static class to represent a geometric point.
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**
         * Constructor for GeoPoint.
         *
         * @param geometry the geometry object
         * @param point    the point of intersection
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof GeoPoint) || getClass() != obj.getClass()) return false;
            GeoPoint other = (GeoPoint) obj;
            return this.point.equals(other.point) && this.geometry == other.geometry;
        }

        @Override
        public String toString() {
            return "Point [geometry=" + geometry + ", point=" + point + "]";
        }
    }


    /**
     * Finds the intersection points between the geometry and a given ray.
     * @param ray The ray to intersect with the geometry.
     * @return A list of points where the ray intersects the geometry.
     */
    public List<Point> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(geoPoint -> geoPoint.point).toList();
    }


    /**
     * Finds intersections with the ray and returns a list of GeoPoints.
     *
     * @param ray the ray to check intersections with
     * @return list of GeoPoints or null if no intersections found
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray){

        return findGeoIntersectionsHelper(ray);
    }


    /**
     * Finds intersections between the ray and the object.
     *
     * @param ray the ray to check intersections with
     * @return list of GeoPoints of intersections
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);



}

