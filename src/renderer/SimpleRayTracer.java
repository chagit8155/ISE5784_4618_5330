package renderer;

import lighting.LightSource;
import primitives.*;
import scene.*;

import static geometries.Intersectable.*;
import static primitives.Util.alignZero;

import java.util.List;

/**
 * SimpleRayTracer class extends RayTracerBase and is responsible for tracing rays in a scene and
 * calculating their color based on various lighting and material properties, including reflection and refraction.
 */
public class SimpleRayTracer extends RayTracerBase {
    private static final double DELTA = 0.1; // Small constant for moving the ray head to avoid self-intersection
    private static final int MAX_CALC_COLOR_LEVEL = 10; // Maximum recursion level for global effects
    private static final double MIN_CALC_COLOR_K = 0.001; // Minimum value for accumulated reflection/refraction coefficient

    /**
     * Constructs a SimpleRayTracer with the specified scene.
     *
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
     *
     * @param gp  the intersection point
     * @param ray the ray that intersected the geometry
     * @return the color at the intersection point
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, new Double3(1.0)).add(scene.ambientLight.getIntensity());
    }

    /**
     * Recursively calculates the color at a given intersection point, considering local and global effects.
     *
     * @param gp    the intersection point
     * @param ray   the ray that intersected the geometry
     * @param level the current recursion level
     * @param k     the accumulated reflection/refraction coefficient
     * @return the color at the intersection point
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
     *
     * @param gp  the intersection point
     * @param ray the ray that intersected the geometry
     * @param k   the accumulated reflection/refraction coefficient
     * @return the color at the intersection point due to local effects
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
                Double3 ktr = transparency(gp, lightSource, l, n, nv, ln);
                if (ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    continue;
                }

                Color lightIntensity = lightSource.getIntensity(gp.point).scale(ktr);
                color = color.add(
                        calcDiffusive(material.kd, l, n, lightIntensity),
                        calcSpecular(material.ks, l, n, v, material.shininess, lightIntensity)
                );
            }
        }
        return color;
    }

    /**
     * Calculates the transparency (shadow attenuation) for a given point and light source.
     *
     * @param gp            the intersection point
     * @param lightSource   the light source
     * @param l             the direction from the point to the light source
     * @param n             the normal at the intersection point
     * @param nv            the dot product of the normal and the view direction
     * @param ln            the dot product of the light direction and the normal
     * @return the transparency coefficient
     */
    private Double3 transparency(GeoPoint gp, LightSource lightSource, Vector l, Vector n, double nv, double ln) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, lightSource.getDistance(gp.point));
        if (intersections == null) {
            return Double3.ONE;
        }
        Double3 ktr = Double3.ONE;
        for (GeoPoint geopoint : intersections) {
            ktr = ktr.product(geopoint.geometry.getMaterial().kt);
            if (ktr.lowerThan(MIN_CALC_COLOR_K)) {
                return Double3.ZERO;
            }
        }
        return ktr;
    }

    /**
     * Calculates the global lighting effects (reflection and refraction) at a given intersection point.
     *
     * @param gp    the intersection point
     * @param ray   the ray that intersected the geometry
     * @param level the current recursion level
     * @param k     the accumulated reflection/refraction coefficient
     * @return the color at the intersection point due to global effects
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructRefractedRay(gp, ray), material.kt, level, k)
                .add(calcGlobalEffect(constructReflectedRay(gp, ray), material.kr, level, k));
    }

    /**
     * Calculates the color contribution from a global effect (reflection or refraction).
     *
     * @param ray   the reflection or refraction ray
     * @param kx    the reflection or refraction coefficient
     * @param level the current recursion level
     * @param k     the accumulated reflection/refraction coefficient
     * @return the color contribution from the global effect
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
     * Constructs a reflection ray from a given intersection point.
     *
     * @param gp  the intersection point
     * @param ray the incident ray
     * @return the reflection ray
     */
    private Ray constructReflectedRay(GeoPoint gp, Ray ray) {
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n)));
        return new Ray(gp.point, r, n);
    }

    /**
     * Constructs a refraction ray from a given intersection point.
     *
     * @param gp  the intersection point
     * @param ray the incident ray
     * @return the refraction ray
     */
    private Ray constructRefractedRay(GeoPoint gp, Ray ray) {
        return new Ray(gp.point, ray.getDirection(), gp.geometry.getNormal(gp.point));
    }

    /**
     * Calculates the diffusive component of the color.
     *
     * @param kd            the diffusive coefficient
     * @param l             the direction from the point to the light source
     * @param n             the normal at the intersection point
     * @param lightIntensity the intensity of the light
     * @return the diffusive color component
     */
    private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color lightIntensity) {
        double ln = alignZero(l.dotProduct(n));
        if (ln < 0) ln = -ln;
        return lightIntensity.scale(kd).scale(ln);
    }

    /**
     * Calculates the specular component of the color.
     *
     * @param ks            the specular coefficient
     * @param l             the direction from the point to the light source
     * @param n             the normal at the intersection point
     * @param v             the direction of the view
     * @param shininess     the shininess exponent
     * @param lightIntensity the intensity of the light
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







