package geometries;

import primitives.*;

import java.util.List;

/**
 * Represents an abstract class for geometries that can be intersected by a ray.
 * Provides a method to find intersection points between the geometry and a given ray.
 */
public abstract class Intersectable {

    protected static boolean cbr = false;
//    public static long boxCheckCounter = 0;
//    public static long intersectionCounter = 0;

    /**
     * the box for the bvh
     */
    protected Border box = null;

    public static void setCbr() {
        Intersectable.cbr = true;
    }

    /**
     * class Border is a class that represents the box of the bvh
     *
     */
    public static class Border {

        /**
         * those values represent the minimum point of the geometry
         */
        protected double minX;
        protected double minY;
        protected double minZ;

        /**
         * this values represent the maximum point of the geometry
         */
        protected double maxX;
        protected double maxY;
        protected double maxZ;

        /**
         * constructor of a border by values
         *
         * @param x1 minimum x
         * @param y1 minimum y
         * @param z1 minimum z
         * @param x2 maximum x
         * @param y2 maximum y
         * @param z2 maximum z
         */
        public Border(double x1, double y1, double z1, double x2, double y2, double z2) {
            minX = x1;
            minY = y1;
            minZ = z1;
            maxX = x2;
            maxY = y2;
            maxZ = z2;
        }

        public Border() {
            minX = Double.POSITIVE_INFINITY;
            maxX = Double.NEGATIVE_INFINITY;
            minY = Double.POSITIVE_INFINITY;
            maxY = Double.NEGATIVE_INFINITY;
            minZ = Double.POSITIVE_INFINITY;
            maxZ = Double.NEGATIVE_INFINITY;
        }

        /**
         * this function calculate if the ray trace the border of the geometry
         *
         * @param ray the crosses ray
         * @return true for intersection, false for not intersection
         */
        protected boolean intersect(Ray ray) {
            //++boxCheckCounter;

            Point origin = ray.getHead();
            double originX = origin.getX();
            double originY = origin.getY();
            double originZ = origin.getZ();
            Vector dir = ray.getDirection();
            double dirX = dir.getX();
            double dirY = dir.getY();
            double dirZ = dir.getZ();

            // Initially will receive the values of tMinX and tMaxX
            double tMin = Double.NEGATIVE_INFINITY;
            double tMax = Double.POSITIVE_INFINITY;

            // the values are depend on the direction of the ray

            if (dirX > 0) {
                tMin = (minX - originX) / dirX; // b=D*t+O => y=mx+b => dirx*tmin+originx=minx
                tMax = (maxX - originX) / dirX;
            } else if (dirX < 0) {
                tMin = (maxX - originX) / dirX;
                tMax = (minX - originX) / dirX;
            }

            double tMinY = Double.NEGATIVE_INFINITY;
            double tMaxY = Double.POSITIVE_INFINITY;
            if (dirY > 0) {
                tMinY = (minY - originY) / dirY;
                tMaxY = (maxY - originY) / dirY;
            } else if (dirY < 0) {
                tMinY = (maxY - originY) / dirY;
                tMaxY = (minY - originY) / dirY;
            }

            // If either the max value of Y is smaller than overall min value, or min value
            // of Y is bigger than the overall
            // max, we can already return false.
            // Otherwise we'll update the overall min and max values and perform the same
            // check on the Z values.
            if ((tMin > tMaxY) || (tMinY > tMax))
                return false;

            if (tMinY > tMin)
                tMin = tMinY;
            if (tMaxY < tMax)
                tMax = tMaxY;

            double tMinZ = Double.NEGATIVE_INFINITY;
            double tMaxZ = Double.POSITIVE_INFINITY;
            if (dirZ > 0) {
                tMinZ = (minZ - originZ) / dirZ;
                tMaxZ = (maxZ - originZ) / dirZ;
            } else if (dirZ < 0) {
                tMinZ = (maxZ - originZ) / dirZ;
                tMaxZ = (minZ - originZ) / dirZ;
            }

            // If either the max value of Z is smaller than overall min value, or min value
            // of Z is bigger than the overall
            // max, we can already return false. Otherwise we can return true since no other
            // coordinate checks are needed.
            return tMin <= tMaxZ && tMinZ <= tMax;
        }

    }


/////////////////////////////////////GeoPoint//////////////////////////////////////////////

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
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);



    public final List<GeoPoint> findGeoIntersections(Ray ray) {
      //  return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
        return box != null && !box.intersect(ray) ? null : findGeoIntersectionsHelper(ray,Double.POSITIVE_INFINITY);
    }



}

