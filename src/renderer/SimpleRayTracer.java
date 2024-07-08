package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

/**
 * Basic ray tracer class that calculates color based on local effects.
 */
public class SimpleRayTracer extends RayTracerBase {
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * Constructs a new SimpleRayTracer.
     * @param scene the scene to trace rays in
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = scene.geometries.findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }

    /**
     * Calculates the color at a given intersection point.
     * @param gp the intersection point
     * @param ray the ray that intersects the point
     * @return the color at the point
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, new Double3(1.0)).add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculates the color at a given intersection point, considering recursive levels and attenuation.
     * @param gp the intersection point
     * @param ray the ray that intersects the point
     * @param level the current recursion level
     * @param k the current attenuation factor
     * @return the color at the point
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        if (level == 1 || k.lowerThan(MIN_CALC_COLOR_K)) {
            return Color.BLACK;
        }

        Color color = calcLocalEffects(gp, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     * Calculates the local lighting effects at a given intersection point.
     * @param gp the intersection point
     * @param ray the ray that intersects the point
     * @param k the current attenuation factor
     * @return the color contributed by local lighting
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (Util.isZero(nv)) {
            return Color.BLACK;
        }

        Material material = gp.geometry.getMaterial();
        Color color = gp.geometry.getEmission();

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point).normalize();
            double ln = alignZero(l.dotProduct(n));
            double sign = ln > 0 ? 1 : -1;

            if (sign * nv > 0) {
                if (unshaded(gp, lightSource, l, n)) {
                    Color lightIntensity = lightSource.getIntensity(gp.point);
                    color = color.add(
                            calcDiffusive(material.kd, l, n, lightIntensity),
                            calcSpecular(material.ks, l, n, v, material.shininess, lightIntensity));
                }
            }
        }
        return color;
    }

    /**
     * Checks if a point is unshaded (i.e., not in shadow) with respect to a light source.
     * @param gp the intersection point
     * @param lightSource the light source
     * @param l the direction vector from the point to the light source
     * @param n the normal vector at the point
     * @return true if the point is unshaded, false otherwise
     */
    private boolean unshaded(GeoPoint gp, LightSource lightSource, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, lightSource.getDistance(gp.point));
        return intersections == null;
    }

    /**
     * Calculates the global lighting effects (reflection and refraction) at a given intersection point.
     * @param gp the intersection point
     * @param ray the ray that intersects the point
     * @param level the current recursion level
     * @param k the current attenuation factor
     * @return the color contributed by global lighting
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructRefractedRay(gp, ray), material.kt, level, k)
                .add(calcGlobalEffect(constructReflectedRay(gp, ray), material.kr, level, k));
    }

    /**
     * Calculates the global lighting effect for a single secondary ray.
     * @param ray the secondary ray
     * @param kx the reflection/refraction factor
     * @param level the current recursion level
     * @param k the current attenuation factor
     * @return the color contributed by the secondary ray
     */
    private Color calcGlobalEffect(Ray ray, Double3 kx, int level, Double3 k) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) {
            return Color.BLACK;
        }
        GeoPoint gp = scene.geometries.findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }

    /**
     * Constructs a reflected ray at a given intersection point.
     * @param gp the intersection point
     * @param ray the original ray
     * @return the reflected ray
     */
    private Ray constructReflectedRay(GeoPoint gp, Ray ray) {
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n)));
        return new Ray(gp.point, r, n);
    }

    /**
     * Constructs a refracted ray at a given intersection point.
     * @param gp the intersection point
     * @param ray the original ray
     * @return the refracted ray
     */
    private Ray constructRefractedRay(GeoPoint gp, Ray ray) {
        return new Ray(gp.point, ray.getDirection(), gp.geometry.getNormal(gp.point));
    }

    /**
     * Calculates the diffusive lighting component.
     * @param kd the diffusive reflection coefficient
     * @param l the direction vector from the point to the light source
     * @param n the normal vector at the point
     * @param lightIntensity the intensity of the light source
     * @return the diffusive color component
     */
    private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color lightIntensity) {
        double ln = alignZero(l.dotProduct(n));
        if (ln < 0) ln = -ln;
        return lightIntensity.scale(kd).scale(ln);
    }

    /**
     * Calculates the specular lighting component.
     * @param ks the specular reflection coefficient
     * @param l the direction vector from the point to the light source
     * @param n the normal vector at the point
     * @param v the direction vector from the camera to the point
     * @param shininess the shininess coefficient
     * @param lightIntensity the intensity of the light source
     * @return the specular color component
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, Vector v, double shininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(2 * l.dotProduct(n))).normalize();
        double vr = alignZero(v.dotProduct(r));
        if (vr >= 0) {
            return Color.BLACK;
        }
        return lightIntensity.scale(ks).scale(Math.pow(-vr, shininess));
    }
}








