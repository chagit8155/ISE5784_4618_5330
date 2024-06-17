package lighting;

import primitives.*;


/**
 * Spot light source that emits light from a specific position and direction.
 */
public class SpotLight extends PointLight {


    private Vector direction;




    /**
     * Constructor for SpotLight.
     *
     * @param intensity The intensity of the light.
     * @param position  The position of the light source.
     * @param direction The direction of the light.
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }


    @Override
    public Color getIntensity(Point p) {
        Vector l = getL(p);
        double projection = l.dotProduct(direction);

        if (projection <= 0) {
            return Color.BLACK;
        }

        double factor = projection / direction.length();
        return super.getIntensity(p).scale(factor);
    }
}
