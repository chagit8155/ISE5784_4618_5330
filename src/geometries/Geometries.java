package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

public class Geometries implements Intersectable {

    final private List<Intersectable> geometries = List.of();

    public Geometries() {}
    public Geometries(Intersectable... geometries) {

    }
    public void add(Intersectable... geometries) {

    }
    @Override
    public List<Point> findIntersections(Ray ray) {
        //return List.of();
        return null;
    }
}
