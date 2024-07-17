package renderer;

import static java.awt.Color.*;

import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

public class GlossyAndDiffuseTests {

    private final Scene scene = new Scene("Glossy Test Scene");
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), Vector.Y)
            .setRayTracer(new SimpleRayTracer(scene));

    @Test
    public void glossySurfaceTest() {
        Camera.Builder cameraBuilder = Camera.getBuilder()
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setRayTracer(new SimpleRayTracer(scene));

        scene.setBackground(new Color(black));
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        // Adding a sphere and a glossy floor
        scene.geometries.add(
                new Sphere(40d, new Point(-50, 0, -100))
                        .setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.2).setKs(0.8).setShininess(300).setGlossiness(7)),
                new Sphere(25d, new Point(50, 0, -100))
                        .setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.2).setKs(0.8).setShininess(300).setGlossiness(7)),
                new Plane(new Point(0, -50, 0), new Vector(0, 1, 0))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100).setKr(0.5).setGlossiness(5))
        );

        scene.lights.add(new SpotLight(new Color(1000, 600, 400), new Point(100, 100, 200), new Vector(-1, -1, -2))
                .setKL(0.0001).setKQ(0.00005));

        cameraBuilder.setLocation(new Point(0, 0, 500))
                .setVpDistance(500)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("glossySurfaceTest", 500, 500))
                .renderImage()
                .writeToImage()
                .build();
    }


    @Test
    public void testBlurryGlass() {
        //  vTo = new Vector(0, 1, 0);
        Camera.Builder camera = Camera.getBuilder().setLocation(new Point(0, -243, 0))
                .setDirection(new Vector(0, 1, 0), new Vector(0, 0, 1))
                .setVpSize(200d, 200).setVpDistance(1000);


        scene.geometries.add(
                new Sphere(3, new Point(-20, -1.50, -3)).setEmission(new Color(cyan).reduce(6))
                        .setMaterial(new Material().setKd(0.2).setKs(1).setShininess(80).setKt(0d)),
                new Sphere(3, new Point(-20, 5, 3)).setEmission(new Color(255, 102, 102).reduce(2))
                        .setMaterial(new Material().setKd(0.2).setKs(1).setShininess(80).setKt(0d)),

                new Triangle(new Point(-24, -5, 5), new Point(-16, -5, 5), new Point(-16, -5, -11))
                        .setEmission(Color.BLACK.reduce(2))
                        .setMaterial(new Material().setKd(0.0001).setKs(0.0002).setDiffuseness(1000).setKt(0.95)
                        )
        );

        scene.geometries.add(
                new Sphere(3, new Point(-10, -1.50, -3)).setEmission(new Color(cyan).reduce(6))
                        .setMaterial(new Material().setKd(0.2).setKs(1).setShininess(80).setKt(0d)),
                new Sphere(3, new Point(-10, 5, 3)).setEmission(new Color(255, 102, 102).reduce(2))
                        .setMaterial(new Material().setKd(0.2).setKs(1).setShininess(80).setKt(0d)),
                new Sphere(3, new Point(-10, -8, -8)).setEmission(new Color(WHITE).reduce(6))
                        .setMaterial(new Material().setKd(0.2).setKs(1).setShininess(80).setKt(0d)),

                new Triangle(new Point(-14, -5, 5), new Point(-6, -5, 5), new Point(-6, -5, -11))
                        .setEmission(Color.BLACK.reduce(2))
                        .setMaterial(new Material().setKd(0.0001).setKs(0.0002).setDiffuseness(100).setKt(0.95)
                        )
        );

        scene.geometries.add(
                new Sphere(3, new Point(0, -1.50, -3)).setEmission(new Color(cyan).reduce(6))
                        .setMaterial(new Material().setKd(0.2).setKs(1).setShininess(80).setKt(0d)),
                new Sphere(3, new Point(0, 5, 3)).setEmission(new Color(255, 102, 102).reduce(2))
                        .setMaterial(new Material().setKd(0.2).setKs(1).setShininess(80).setKt(0d)),
                new Sphere(3, new Point(0, -8, -8)).setEmission(new Color(WHITE).reduce(6))
                        .setMaterial(new Material().setKd(0.2).setKs(1).setShininess(80).setKt(0d)),

                new Triangle(new Point(-4, -5, 5), new Point(4, -5, 5), new Point(4, -5, -11))
                        .setEmission(Color.BLACK.reduce(2))
                        .setMaterial(new Material().setKd(0.0001).setKs(0.0002).setDiffuseness(50).setKt(0.95)
                        )
        );

        scene.geometries.add(
                new Sphere(3, new Point(10, -1.50, -3)).setEmission(new Color(cyan).reduce(6))
                        .setMaterial(new Material().setKd(0.2).setKs(1).setShininess(80).setKt(0d)),
                new Sphere(3, new Point(10, 5, 3)).setEmission(new Color(255, 102, 102).reduce(2))
                        .setMaterial(new Material().setKd(0.2).setKs(1).setShininess(80).setKt(0d)),
                new Sphere(3, new Point(10, -8, -8)).setEmission(new Color(WHITE).reduce(6))
                        .setMaterial(new Material().setKd(0.2).setKs(1).setShininess(80).setKt(0d)),

                new Triangle(new Point(6, -5, 5), new Point(14, -5, 5), new Point(14, -5, -11))
                        .setEmission(Color.BLACK.reduce(2))
                        .setMaterial(new Material().setKd(0.0001).setKs(0.0002).setDiffuseness(25).setKt(0.95)
                        )
        );

        scene.geometries.add(
                new Sphere(3, new Point(20, -1.50, -3)).setEmission(new Color(cyan).reduce(6))
                        .setMaterial(new Material().setKd(0.2).setKs(1).setShininess(80).setKt(0d)),
                new Sphere(3, new Point(20, 5, 3)).setEmission(new Color(255, 102, 102).reduce(2))
                        .setMaterial(new Material().setKd(0.2).setKs(1).setShininess(80).setKt(0d)),
                new Triangle(new Point(16, -5, 5), new Point(24, -5, 5), new Point(24, -5, -11))
                        .setEmission(Color.BLACK.reduce(2))
                        .setMaterial(new Material().setKd(0.0001).setKs(0.0002).setDiffuseness(10).setKt(0.95))

        );


        scene.lights.add(new DirectionalLight(new Color(173, 216, 230), new Vector(-0.4, 1, 0)));
        scene.lights.add(new SpotLight(new Color(173, 216, 230), new Point(20.43303, -7.37104, 13.77329),
                new Vector(-20.43, 7.37, -13.77)).setKL(0.6));

        ImageWriter imageWriter = new ImageWriter("blurryGlassTest", 500, 500);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new SimpleRayTracer(scene))
                .renderImage()
                .writeToImage().build();
    }
}