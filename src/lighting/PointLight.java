package lighting;

import primitives.*;

/**
 * Point light source that emits light from a specific position.
 */
public class PointLight extends Light implements LightSource {

    private Point position;
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
     * Creates a point light.
     *
     * @param intensity light's intensity
     * @param position  light source's location
     * @param kC        constant attenuation factor
     * @param kL        linear attenuation factor
     * @param kQ        quadratic attenuation factor
     */
    public PointLight(Color intensity, Point position, double kC, double kL, double kQ) {
        super(intensity);
        this.position = position;
        this.kC = kC;
        this.kL = kL;
        this.kQ = kQ;
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


    @Override
    public Color getIntensity(Point p) {
        double distance = position.distance(p);
        double v = kC + kL * distance + kQ * distance * distance;
        return intensity.scale(1 / v);
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return point.distance(position);
    }

//    @Override
//    public double getDistance(Point p) {
//        return position.distance(p);
//    }
}
