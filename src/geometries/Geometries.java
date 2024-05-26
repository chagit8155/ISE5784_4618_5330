package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {

    final private List<Intersectable> geometries = new LinkedList<>();

    public Geometries() {
    }

    public Geometries(Intersectable... geometries) {

    }

    public void add(Intersectable... geometries) {

    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = null;
        for (Intersectable geometry : geometries) {
            List<Point> currentIntersections = geometry.findIntersections(ray);
            if (currentIntersections != null) {
                if (intersections == null)
                    intersections = new LinkedList<>();
                intersections.addAll(currentIntersections);
            }

        }
        return intersections;
    }
}
