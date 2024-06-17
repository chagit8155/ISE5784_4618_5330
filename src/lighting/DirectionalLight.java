package lighting;

import primitives.*;


/**
 * Directional light source that emits light in a specific direction.
 */
public class DirectionalLight extends Light implements LightSource {


    private Vector direction;

    /**
     * Constructor for DirectionalLight.
     *
     * @param intensity The intensity of the light.
     * @param direction The direction of the light.
     */
    protected DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize(); ////
    }

    @Override
    public Color getIntensity(Point p) {
        return intensity;
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }




//    @Override
//    public double getDistance(Point point) {
//        return Double.POSITIVE_INFINITY; // Directional light is considered to be infinitely far
//    }

}
