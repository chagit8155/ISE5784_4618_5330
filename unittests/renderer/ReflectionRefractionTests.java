/**
 *
 */
package renderer;

import static java.awt.Color.*;

import geometries.Polygon;
import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {
    /** Scene for the tests */
    private final Scene scene = new Scene("Test scene");
    /** Camera builder for the tests with triangles */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), Vector.Y)
            .setRayTracer(new SimpleRayTracer(scene));

    /** Produce a picture of a sphere lighted by a spot light */
    @Test
    public void twoSpheres() {
        scene.geometries.add(
                new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene.lights.add(
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                        .setKL(0.0004).setKQ(0.0000006));

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(150, 150)
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500))
                .renderImage()
                .writeToImage()
                .build();
    }

    /** Produce a picture of a sphere lighted by a spot light */
    @Test
    public void twoSpheresOnMirrors() {
        scene.geometries.add(
                new Sphere(400d,new Point(-950, -900, -1000) ).setEmission(new Color(0, 50, 100))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Sphere(200d,new Point(-950, -900, -1000) ).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4))
                .setKL(0.00001).setKQ(0.000005));

        cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000)
                .setVpSize(2500, 2500)
                .setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500))
                .renderImage()
                .writeToImage()
                .build();
    }

    /** Produce a picture of a two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow */
    @Test
    public void trianglesTransparentSphere() {
        scene.geometries.add(
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Sphere(30d,new Point(60, 50, -50) ).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        scene.lights.add(
                new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
                        .setKL(4E-5).setKQ(2E-7));

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("refractionShadow", 600, 600))
                .renderImage()
                .writeToImage()
                .build();
    }



    /** Produce a picture of multiple objects lighted by multiple light sources */

    @Test
    public void MultiObjectTest() {
        scene.geometries.add(
                // Transparent Sphere
                new Sphere(20d, new Point(-50, -50, -100)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(10d, new Point(-50, -50, -100)).setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
                // Sphere and mirrors
                new Sphere(100d, new Point(-450, -400, -500)).setEmission(new Color(0, 50, 100))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Sphere(50d, new Point(-450, -400, -500)).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point(750, -750, -750), new Point(-750, 750, -750),
                        new Point(335, 335, 1500))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(new Point(750, -750, -750), new Point(-750, 750, -750),
                        new Point(-750, -750, -1000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))),
                // Two triangles and a transparent sphere
                new Triangle(new Point(-100, -100, -115), new Point(100, -100, -135),
                        new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Triangle(new Point(-100, -100, -115), new Point(-50, 50, -140), new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Sphere(15d, new Point(75, 75, -100)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6))
        );

        // Add lights from all three scenes
        scene.lights.add(new SpotLight(new Color(1000, 600, 0), new Point(-150, -150, 250), new Vector(-1, -1, -2))
                .setKL(0.0004).setKQ(0.0000006));

        // Light from twoSpheresOnMirrors test
        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-375, -375, -75), new Vector(-1, -1, -4))
                .setKL(0.00001).setKQ(0.000005));

        // Light from trianglesTransparentSphere test
        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(30, 25, 0), new Vector(0, 0, -1))
                .setKL(4E-5).setKQ(2E-7));

        Camera.Builder cameraBuilder = Camera.getBuilder()
                .setLocation(new Point(0, 0, 500))
                .setDirection(new Vector(0, 0, -1), Vector.Y)
                .setVpDistance(500)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("MultiObjectTest", 600, 600))
                .setRayTracer(new SimpleRayTracer(scene));

        cameraBuilder.renderImage()
                .writeToImage()
                .build();
    }





    @Test
    public void bonusTest() {
        scene.geometries.add(
                // Sphere with transparency and reflection
                new Sphere(50d, new Point(0, 0, -100)).setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.5).setKr(0.5)),
                // Smaller sphere
                new Sphere(25d, new Point(70, 70, -100)).setEmission(new Color(GREEN))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
                // Triangle with reflection
                new Triangle(new Point(-100, -100, -200), new Point(100, -100, -200), new Point(0, 100, -200))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(0.7)),
                // Cube with reflection and transparency
                new Polygon(new Point(-50, -50, -300), new Point(-50, 50, -300), new Point(50, 50, -300), new Point(50, -50, -300))
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(0.5).setKr(0.5))
        );

        // Add lights
        scene.lights.add(new SpotLight(new Color(700, 400, 0), new Point(-200, -200, 200), new Vector(1, 1, -2))
                .setKL(0.0004).setKQ(0.0000006));
        scene.lights.add(new SpotLight(new Color(510, 200, 200), new Point(200, 200, 200), new Vector(-1, -1, -2))
                .setKL(0.00001).setKQ(0.000005));
        scene.lights.add(new PointLight(new Color(500, 300, 300), new Point(0, 0, 100))
                .setKL(4E-5).setKQ(2E-7));

        Camera.Builder cameraBuilder = Camera.getBuilder()
                .setLocation(new Point(0, 0, 1000))
                .setDirection(new Vector(0, 0, -1), Vector.Y)
                .setVpDistance(1000)
                .setVpSize(500, 500)
                .setImageWriter(new ImageWriter("bonusTest", 600, 600))
                .setRayTracer(new SimpleRayTracer(scene));

        cameraBuilder.renderImage()
                .writeToImage()
                .build();
    }

















}
