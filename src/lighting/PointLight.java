package lighting;

import primitives.*;

/**
 * Point light source that emits light from a specific position.
 */
public class PointLight extends Light implements LightSource {

    private  Point position;
    private double kC = 1.0;
    private double kL = 0.0;
    private double kQ = 0.0;

    /**
     * Constructor for PointLight.
     *
     * @param intensity The intensity of the light.
     * @param position  The position of the light source.
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Sets the constant attenuation factor.
     *
     * @param kC the constant attenuation factor.
     * @return this PointLight instance.
     */
    public PointLight setKC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the linear attenuation factor.
     *
     * @param kL the linear attenuation factor.
     * @return this PointLight instance.
     */
    public PointLight setKL(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets the quadratic attenuation factor.
     *
     * @param kQ the quadratic attenuation factor.
     * @return this PointLight instance.
     */
    public PointLight setKQ(double kQ) {
        this.kQ = kQ;
        return this;
    }


    /**
     * Constructor for Light.
     *
     * @param intensity The intensity of the light source.
     */
    protected PointLight(Color intensity) {
        super(intensity);
    }





    @Override
    public Color getIntensity(Point p) {
        double distance = position.distance(p);
        double v = kC + kL * distance + kQ * distance * distance;
        int attenuation = ((int) v);
        return intensity.reduce(attenuation);
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

//    @Override
//    public double getDistance(Point p) {
//        return position.distance(p);
//    }
}
