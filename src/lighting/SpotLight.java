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

    /**
     * Sets the direction vector of the spotlight and returns the SpotLight object itself,
     * enabling fluent configuration. This method allows for setting the direction of the
     * spotlight's beam, which is essential for its focused lighting effect.
     *
     * @param direction the direction vector of the spotlight
     * @return the SpotLight object, enabling method chaining
     */
    public SpotLight setDirection(Vector direction) {
        this.direction = direction.normalize();
        return this;
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
//        return super.getIntensity(p).
//                scale(Math.pow(Math.max(0, direction.dotProduct(super.getL(p))), 1));
    }
}
