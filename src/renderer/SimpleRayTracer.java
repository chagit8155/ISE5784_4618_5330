package renderer;

import primitives.*;
import scene.Scene;

import static geometries.Intersectable.GeoPoint;

import java.util.List;

public class SimpleRayTracer extends RayTracerBase {


    /**
     * Constructs a SimpleRayTracer with the given scene.
     *
     * @param scene the scene to be rendered
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) {
            return scene.background;
        }

        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint);
    }

    /**
     * Calculates the color at a given point.
     *
     * @param gp the point to calculate the color at
     * @return the color at the given point
     */


    private Color calcColor(GeoPoint gp) {
        // return scene.ambientLight.getIntensity(); - stage 5

        return scene.ambientLight.getIntensity().add(gp.geometry.getEmission());
    }
}
