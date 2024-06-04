package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Abstract base class for ray tracing.
 * This class contains a reference to a scene and provides an abstract method for tracing rays.
 */
public abstract class RayTracerBase {
    /**
     * The scene to be rendered.
     */
    protected Scene scene;

    /**
     * Constructs a RayTracerBase with the given scene.
     *
     * @param scene the scene to be rendered
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Traces a ray and returns the color at the point where the ray intersects an object in the scene.
     *
     * @param ray the ray to be traced
     * @return the color at the intersection point
     */
    public abstract Color traceRay(Ray ray);
}
