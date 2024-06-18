package renderer;

import lighting.LightSource;
import primitives.*;
import scene.*;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

import java.util.List;

public class SimpleRayTracer extends RayTracerBase {


    /**
     * Constructs a SimpleRayTracer with the given scene.
     *
     * @param scene the scene to be rendered
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) {
            return scene.background;
        }

        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint, ray);
    }

//    /**
//     * Calculates the color at a given point.
//     *
//     * @param gp the point to calculate the color at
//     * @return the color at the given point
//     */
//    private Color calcColor(GeoPoint gp) {
//        // return scene.ambientLight.getIntensity(); - stage 5
//
//        return scene.ambientLight.getIntensity().add(gp.geometry.getEmission());
//    }


    /**
     * Calculates the color at a given intersection point using Phong reflection model.
     *
     * @param gp  the intersection point
     * @param ray the ray that hits the geometry
     * @return the calculated color at the intersection point
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(gp.geometry.getEmission()) /////////
                .add(calcLocalEffects(gp, ray));
    }

    /**
     * Calculates the local effects at a given intersection point.
     *
     * @param intersection the intersection point
     * @param ray          the ray that hits the geometry
     * @return the color resulting from the local effects
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDirection().normalize();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;

        int nShininess = intersection.geometry.getMaterial().shininess;
        Double3 kd = intersection.geometry.getMaterial().kd;
        Double3 ks = intersection.geometry.getMaterial().ks;

        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point).normalize();
            double nl = alignZero(n.dotProduct(l));


            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color lightIntensity = lightSource.getIntensity(intersection.point);


                color = color.add(
                        calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;
    }

    /**
     * Calculates the diffusive component of the Phong reflection model.
     *
     * @param kd             the diffuse reflection coefficient
     * @param l              the light direction
     * @param n              the normal to the surface at the intersection point
     * @param lightIntensity the light intensity
     * @return the color resulting from the diffusive component
     */
    private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color lightIntensity) {
        double ln = Math.abs(l.dotProduct(n));
        return lightIntensity.scale(kd).scale(ln);
    }

    /**
     * Calculates the specular component of the Phong reflection model.
     *
     * @param ks             the specular reflection coefficient
     * @param l              the light direction
     * @param n              the normal to the surface at the intersection point
     * @param v              the viewer's direction
     * @param nShininess     the shininess exponent
     * @param lightIntensity the light intensity
     * @return the color resulting from the specular component
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(2 * l.dotProduct(n))).normalize();
        double vr = alignZero(v.dotProduct(r.scale(-1))); // חשוב לבדוק את הסימן של הכפלת הסקלר עם הכיוון ההפוך
        if (vr <= 0) return Color.BLACK;
        return lightIntensity.scale(ks.scale(Math.pow(vr, nShininess)));
//        double vr = v.dotProduct(r);
//        return (vr < 0) ? Color.BLACK : lightIntensity.scale(ks).scale(Math.pow(vr, nShininess));
    }
}
