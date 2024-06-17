package lighting;

import primitives.*;


/**
 * AmbientLight class represents the ambient light in the scene.
 * It holds the intensity of the ambient light which is calculated
 * using the original light intensity and an attenuation factor.
 */
public class AmbientLight extends Light {



    /**
     * A constant for no ambient light
     */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);


    /**
     * Constructor for AmbientLight that takes a color and an attenuation factor (Double3)
     *
     * @param Ia the original light intensity
     * @param ka the attenuation factor
     */
    public AmbientLight(Color Ia, Double3 ka) {

       super(Ia.scale(ka));
    }

    /**
     * Constructor for AmbientLight that takes a color and an attenuation factor (double)
     *
     * @param Ia the original light intensity
     * @param ka the attenuation factor
     */
    public AmbientLight(Color Ia, double ka) {
       super(Ia.scale(ka));
    }



}
