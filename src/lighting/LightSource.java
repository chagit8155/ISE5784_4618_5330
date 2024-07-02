package lighting;

import primitives.*;

public interface LightSource {

    /**
     * Get intensity of the light source at a given point.
     *
     * @param point the point where to calculate the light intensity
     * @return the color of the light intensity
     */
    public Color getIntensity(Point point);

    /**
     * Get the direction of the light source from a given point.
     *
     * @param point the point where to calculate the direction of the light source
     * @return the direction vector
     */
    public Vector getL(Point point);

    /**
     * Get the distance from the light source to a given point.
     *
     * @param point the point to calculate the distance to
     * @return the distance
     */
    public double getDistance(Point point);

}
