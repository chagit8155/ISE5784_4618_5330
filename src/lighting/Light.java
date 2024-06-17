package lighting;

import primitives.*;

/**
 * Abstract class to represent a light source.
 * Provides a common structure for all types of lights with an intensity attribute.
 */
abstract class Light {

    /**
     * The intensity of the light source.
     */
    protected Color intensity;

    /**
     * Constructor for Light.
     *
     * @param intensity The intensity of the light source.
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Gets the intensity of the light source.
     *
     * @return the intensity of the light source.
     */
    public Color getIntensity() {
        return intensity;
    }
}

