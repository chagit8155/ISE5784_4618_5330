package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public class SimpleRayTracer extends RayTracerBase{


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
        return null;
    }
}
