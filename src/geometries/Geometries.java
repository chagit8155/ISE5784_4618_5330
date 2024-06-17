package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries extends Intersectable {

    final private List<Intersectable> geometries = new LinkedList<>();

    public Geometries() {
    }

    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    public void add(Intersectable... geometries) {
        this.geometries.addAll(List.of(geometries));
    }


    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersections = null;

        for (Intersectable geometry : geometries) {
            List<GeoPoint> geoPoints = geometry.findGeoIntersections(ray);
            if (geoPoints != null) {
                if (intersections == null) {
                    intersections = new LinkedList<>();
                }
                intersections.addAll(geoPoints);
            }
        }

        return intersections;
    }
}
