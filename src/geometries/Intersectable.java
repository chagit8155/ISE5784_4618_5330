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
/////////////////////////////////////////private - maybe should move to simple ray..

    /**
     * Finds the closest intersection point of a ray with the scene geometry.
     *
     * @param ray the ray to be traced
     * @return the closest intersection point or null if no intersection
     */
    public GeoPoint findClosestIntersection(Ray ray) {
        var intersections = findGeoIntersections(ray);
        return intersections == null ? null : ray.findClosestGeoPoint(intersections);
    }
//////////////////////////////////////////

    /**
     * Finds the intersection points between the geometry and a given ray.
     *
     * @param ray The ray to intersect with the geometry.
     * @return A list of points where the ray intersects the geometry.
     */
    public List<Point> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(geoPoint -> geoPoint.point).toList();
    }


    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);


}

