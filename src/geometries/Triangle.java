package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Represents a triangle in three-dimensional space.
 * A triangle is defined by three vertices.
 */
public class Triangle extends Polygon {

    /**
     * Constructs a triangle with the specified vertices.
     *
     * @param a The first vertex of the triangle.
     * @param b The second vertex of the triangle.
     * @param c The third vertex of the triangle.
     */
    public Triangle(Point a, Point b, Point c) {
        super(a, b, c);
    }

    @Override
    public Vector getNormal(Point point) {
        return super.getNormal(point);//??
    }
    //בגופים גאומטרים לא צריך לזרוס את equals


    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        // Find intersections with the plane containing the triangle
        List<GeoPoint> intersections = this.plane.findGeoIntersectionsHelper(ray, maxDistance);
        if (intersections == null) {
            // If there is no intersection with the plane, there is no intersection with the triangle
            return null;
        }

        // Intersection point with the plane
        GeoPoint intersection = intersections.get(0);

        // Check if the intersection point is within the max distance
        if (ray.getHead().distance(intersection.point) > maxDistance) {
            return null;
        }

        // Vectors from ray head to each vertex of the triangle
        Vector v1 = vertices.get(0).subtract(ray.getHead());
        Vector v2 = vertices.get(1).subtract(ray.getHead());
        Vector v3 = vertices.get(2).subtract(ray.getHead());

        // Normals to the planes formed by the triangle's edges and the ray
        Vector n1 = (v1.crossProduct(v2)).normalize();
        Vector n2 = (v2.crossProduct(v3)).normalize();
        Vector n3 = (v3.crossProduct(v1)).normalize();

        // Dot products of the ray direction with the edge normals
        double vn1 = ray.getDirection().dotProduct(n1);
        double vn2 = ray.getDirection().dotProduct(n2);
        double vn3 = ray.getDirection().dotProduct(n3);

        // If any of the dot products are zero, the ray lies on the edge and does not intersect the triangle
        if (isZero(vn1) || isZero(vn2) || isZero(vn3)) {
            return null;
        }

        // If all dot products have the same sign, the intersection point is inside the triangle
        if ((vn1 > 0 && vn2 > 0 && vn3 > 0) || (vn1 < 0 && vn2 < 0 && vn3 < 0)) {
            return List.of(new GeoPoint(this, intersection.point));
        }

        // If the intersection point is outside the triangle
        return null;
    }

}

