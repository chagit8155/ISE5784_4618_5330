package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

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
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null) {
            return scene.background;
        }

        Point closestPoint = ray.findClosestPoint(intersections);
        return calcColor(closestPoint);
    }
    /**
     * Calculates the color at a given point.
     *
     * @param point the point to calculate the color at
     * @return the color at the given point
     */
    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }
}
