package geometries;

import primitives.Point;

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
}

