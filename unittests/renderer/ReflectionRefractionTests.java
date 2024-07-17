/**
 *
 */
package renderer;

import static java.awt.Color.*;
import org.junit.jupiter.api.Test;
import geometries.*;
import lighting.*;
import primitives.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    /**
     * Scene for the tests
     */
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder for the tests with triangles
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), Vector.Y)
           // .setDirection(new Vector(0, 0, -100),new Vector(100,0,0))
            .setRayTracer(new SimpleRayTracer(scene));

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
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

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        scene.geometries.add(
                new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20))
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

    /**
     * Produce a picture of a two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        scene.geometries.add(
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE))
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


    /**
     * Produce a picture of multiple objects lighted by multiple light sources
     */

    @Test
    public void MultiObjectsTest() {
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
                .setImageWriter(new ImageWriter("MultiObjectsTest", 600, 600))
                .setRayTracer(new SimpleRayTracer(scene));

        cameraBuilder.renderImage()
                .writeToImage()
                .build();
    }


    @Test
    public void multiObjectsBonusTest() {
        scene.setBackground(new Color(89, 188, 217));
        scene.geometries.add(
                new Triangle(new Point(-150, -150, -115), new Point(250, -150, -135),
                        new Point(-150, -30, -50))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setKt(0.02))
                        .setEmission(new Color(12, 143, 12)),
                new Sphere(10d, new Point(30, 20, -50)).setEmission(new Color(110, 218, 230))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(1))///
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.8).setKr(0.6)),
                new Sphere(5d, new Point(50, 40, -50)).setEmission(new Color(0, 218, 230))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.8).setKr(0.6)),
                new Sphere(8d, new Point(60, 15, -50)).setEmission(new Color(0, 218, 230))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.8).setKr(0.6)),
                new Triangle(new Point(-55, 40, -50), new Point(-40, -70, -50), new Point(-68, -70, -50))
                        .setEmission(new Color(61, 38, 12))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKr(0.6)),
                new Triangle(new Point(-55, 40, -50), new Point(-40, -70, -50), new Point(-54, -70, 0))
                        .setEmission(new Color(61, 38, 12)),
                new Triangle(new Point(-55, 40, -50), new Point(-54, -70, 0), new Point(-68, -70, -50))
                        .setEmission(new Color(61, 38, 12)),
                new Sphere(15,new Point(-55, 45, -47)).setMaterial(new Material().setKd(0.1))
                        .setEmission(new Color(64, 173, 27)),///////////////
                new Sphere(12,new Point(-40, 30, -45)).setMaterial(new Material().setKd(0.1))
                        .setEmission(new Color(64, 173, 27)),
                new Sphere(13,new Point(-35, 50, -55)).setMaterial(new Material().setKd(0.1))
                        .setEmission(new Color(64, 173, 27)),
                new Sphere(17,new Point(-50, 60, -52)).setMaterial(new Material().setKd(0.1))
                        .setEmission(new Color(64, 173, 27)),
                new Sphere(13.5,new Point(-70, 55, -49)).setMaterial(new Material().setKd(0.1))
                        .setEmission(new Color(64, 173, 27)),
                new Sphere(10,new Point(-75, 35, -55)).setMaterial(new Material().setKd(0.1))
                        .setEmission(new Color(64, 173, 27)),
                new Sphere(16,new Point(-60, 30, -65)).setMaterial(new Material().setKd(0.1))
                        .setEmission(new Color(64, 173, 27)),
                new Sphere(13.8,new Point(-85, 44, -60)).setMaterial(new Material().setKd(0.1))
                        .setEmission(new Color(64, 173, 27)),
                new Sphere(15,new Point(-25, 40, -60)).setMaterial(new Material().setKd(0.1))
                        .setEmission(new Color(64, 173, 27)),
                new Sphere(5,new Point(-30, 59, -48)).setMaterial(new Material().setKd(0.1))
                        .setEmission(new Color(64, 173, 27))


        );
        scene.setAmbientLight(new AmbientLight(new Color(153, 217, 234), 0.15));
        scene.lights.add(
                new SpotLight(new Color(YELLOW), new Point(-100,100,0), new Vector(0,1,0)).setKL(4E-5).setKQ(2E-7)
        );
        scene.lights.add(
                new DirectionalLight(new Color(white), new Vector(20, -5, -11))
        );


        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000d)
                .setVpSize(200d, 200d)
                .setImageWriter(new ImageWriter("multiObjectBonus07", 600, 600))
                .renderImage()
                .writeToImage()
                .build();
    }
}




