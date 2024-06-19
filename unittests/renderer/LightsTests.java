package renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import scene.Scene;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class LightsTests {
    /**
     * First scene for some of tests
     */
    private final Scene scene1 = new Scene("Test scene");
    /**
     * Second scene for some of tests
     */
    private final Scene scene2 = new Scene("Test scene")
            .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

    /**
     * First camera builder for some of tests
     */
    private final Camera.Builder camera1 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene1))
            .setLocation(new Point(0, 0, 1000))
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpSize(150, 150).setVpDistance(1000);
    /**
     * Second camera builder for some of tests
     */
    private final Camera.Builder camera2 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene2))
            .setLocation(new Point(0, 0, 1000))
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpSize(200, 200).setVpDistance(1000);

    /**
     * Shininess value for most of the geometries in the tests
     */
    private  final int SHININESS = 301;
    /**
     * Diffusion attenuation factor for some of the geometries in the tests
     */
    private  final double KD = 0.5;
    /**
     * Diffusion attenuation factor for some of the geometries in the tests
     */
    private  final Double3 KD3 = new Double3(0.2, 0.6, 0.4);

    /**
     * Specular attenuation factor for some of the geometries in the tests
     */
    private  final double KS = 0.5;
    /**
     * Specular attenuation factor for some of the geometries in the tests
     */
    private  final Double3 KS3 = new Double3(0.2, 0.4, 0.3);

    /**
     * Material for some of the geometries in the tests
     */
    private final Material material = new Material().setKd(KD3).setKs(KS3).setShininess(SHININESS);
    /**
     * Light color for tests with triangles
     */
    private final Color trianglesLightColor = new Color(800, 500, 250);
    /**
     * Light color for tests with sphere
     */
    private final Color sphereLightColor = new Color(800, 500, 0);
    /**
     * Color of the sphere
     */
    private final Color sphereColor = new Color(BLUE).reduce(2);

    /**
     * Center of the sphere
     */
    private final Point sphereCenter = new Point(0, 0, -50);
    /**
     * Radius of the sphere
     */
    private final double SPHERE_RADIUS = 50d;

    /**
     * The triangles' vertices for the tests with triangles
     */
    private final Point[] vertices =
            {
                    // the shared left-bottom:
                    new Point(-110, -110, -150),
                    // the shared right-top:
                    new Point(95, 100, -150),
                    // the right-bottom
                    new Point(110, -110, -150),
                    // the left-top
                    new Point(-75, 78, 100)
            };
    /**
     * Position of the light in tests with sphere
     */
    private final Point sphereLightPosition = new Point(-50, -50, 25);
    /**
     * Light direction (directional and spot) in tests with sphere
     */
    private final Vector sphereLightDirection = new Vector(1, 1, -0.5);
    /**
     * Position of the light in tests with triangles
     */
    private final Point trianglesLightPosition = new Point(30, 10, -100);
    /**
     * Light direction (directional and spot) in tests with triangles
     */
    private final Vector trianglesLightDirection = new Vector(-2, -2, -2);

    /**
     * The sphere in appropriate tests
     */
    private final Geometry sphere = new Sphere(SPHERE_RADIUS, sphereCenter)
            .setEmission(sphereColor).setMaterial(new Material().setKd(KD).setKs(KS).setShininess(SHININESS));
    /**
     * The first triangle in appropriate tests
     */
    private final Geometry triangle1 = new Triangle(vertices[0], vertices[1], vertices[2])
            .setMaterial(material);
    /**
     * The first triangle in appropriate tests
     */
    private final Geometry triangle2 = new Triangle(vertices[0], vertices[1], vertices[3])
            .setMaterial(material);

    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void sphereDirectional() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new DirectionalLight(sphereLightColor, sphereLightDirection));

        camera1.setImageWriter(new ImageWriter("lightSphereDirectional", 500, 500))
                .build();
        camera1.renderImage();
        camera1.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a point light
     */
    @Test
    public void spherePoint() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new PointLight(sphereLightColor, sphereLightPosition)
                .setKL(0.001).setKQ(0.0002));

        camera1.setImageWriter(new ImageWriter("lightSpherePoint", 500, 500))
                .build();
        camera1.renderImage();
        camera1.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spotlight
     */
    @Test
    public void sphereSpot() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new SpotLight(sphereLightColor, sphereLightPosition, sphereLightDirection)
                .setKL(0.001).setKQ(0.0001));

        camera1.setImageWriter(new ImageWriter("lightSphereSpot", 500, 500))
                .build();
        camera1.renderImage();
        camera1.writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a directional light
     */
    @Test
    public void trianglesDirectional() {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new DirectionalLight(trianglesLightColor, trianglesLightDirection));

        camera2.setImageWriter(new ImageWriter("lightTrianglesDirectional", 500, 500)) //
                .build(); //
        camera2.renderImage(); //
        camera2.writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a point light
     */
    @Test
    public void trianglesPoint() {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new PointLight(trianglesLightColor, trianglesLightPosition)
                .setKL(0.001).setKQ(0.0002));

        camera2.setImageWriter(new ImageWriter("lightTrianglesPoint", 500, 500)) //
                .build(); //
        camera2.renderImage(); //
        camera2.writeToImage(); //
    }

    /**
     * Produce a picture of two triangles lighted by a spotlight
     */
    @Test
    public void trianglesSpot() {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
                .setKL(0.001).setKQ(0.0001));

        camera2.setImageWriter(new ImageWriter("lightTrianglesSpot", 500, 500))
                .build();
        camera2.renderImage();
        camera2.writeToImage();
    }


///////////////////////////////////////part 2///////////////////////////////////////////////////////////////

    @Test
    public void trianglesMultipleLights() {
        // יצירת משולשים
        Geometry triangle1 = new Triangle(vertices[0], vertices[1], vertices[2]).setEmission(new Color(30,70,60));
        triangle1.setMaterial(new Material().setKd(KD3).setKs(KS3).setShininess(SHININESS));
        Geometry triangle2 = new Triangle(vertices[0], vertices[1], vertices[3])
                .setMaterial(new Material().setKd(KD3).setKs(KS3).setShininess(SHININESS));


        Scene scene = new Scene("Test scene XXX");
        scene.geometries.add(triangle1, triangle2);
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));


//        scene.addLight(new DirectionalLight(new Color(0,0,255), new Vector(-100, -10, -100)));
//        scene.addLight(new PointLight(new Color(300, 0, 200), new Point(-20, -20, -100))
//                .setKL(0.001).setKQ(0.0002));
//        scene.addLight(new SpotLight(new Color(300, 300, 500), new Point(-30, 90, -150), new Vector(-2, -2, -1))
//                .setKL(0.001).setKQ(0.0001));
        scene.addLight(new DirectionalLight(new Color(0, 0, 255), new Vector(-100, -50, -50)));
        scene.addLight(new PointLight(new Color(100, 300, 0), new Point(0, 50, -50))
                .setKL(0.001).setKQ(0.0002));
        scene.addLight(new SpotLight(new Color(100, 100, 300), new Point(-30, -30, -100), new Vector(1, 1, 1))
                .setKL(0.001).setKQ(0.0001));


        Camera camera3 = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(scene))
                .setLocation(new Point(0, 0, 1000))
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(200, 200)
                .setVpDistance(1000)
                .setImageWriter(new ImageWriter("trianglesMultipleLights", 500, 500))
                .renderImage()
                .writeToImage()
                .build();
    }


    /**
     * Produce a picture of two triangles with multiple light sources
     */
    @Test
    public void triangleMultiLightSource() {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new SpotLight(new Color(0, 0, 255), new Point(1, 100, 100), new Vector(0, -100, -1))
                .setKL(0.00000005).setKQ(0.0000004));
        scene2.lights
                .add(new PointLight(new Color(1, 255, 0), new Point(-10, -2500, -200)).setKL(0.0001).setKQ(0.0002));
        scene2.lights.add(new DirectionalLight(new Color(255, 1, 0), new Vector(-50, -10, -1000)));
        ImageWriter imageWriter = new ImageWriter("triangleMultiLightSource", 500, 500);
        camera2.setImageWriter(imageWriter) //
                .setRayTracer(new SimpleRayTracer(scene2))
                 //
                .renderImage() //
                .writeToImage()
                .build();//
    }


    @Test
    public void sphereMultiLightSource() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new SpotLight(new Color(242, 0, 0), new Point(0, 1000, 1000), new Vector(0, -1, -1))
                .setKL(0.000001).setKQ(0.0000004));
        scene1.lights
                .add(new PointLight(new Color(0, 203, 0), new Point(-25, 0, 25)).setKL(0.00000001).setKQ(0.000000002));
        scene1.lights.add(new DirectionalLight(new Color(241, 199, 0), new Vector(0, 0, -1)));
        ImageWriter imageWriter = new ImageWriter("sphereMultiLightSource", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new SimpleRayTracer(scene1)) .build();//
        camera1.renderImage();
        //
        camera1.writeToImage(); //

    }

    ////////////////////////////////*  bonus  *///////////////////////////////////

    /**
     * Produce a picture of a sphere lighted by a narrow spotlight
     */
    @Test
    public void sphereSpotSharp() {
        scene1.geometries.add(sphere);
        scene1.lights
                .add(new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5)).setNarrowBeam(10)
                        .setKL(0.001).setKQ(0.00004));


        camera1.setImageWriter(new ImageWriter("lightSphereSpotSharp", 500, 500))
                .build();
        camera1.renderImage();
        camera1.writeToImage();

    }

    /**
     * Produce a picture of two triangles lighted by a narrow spotlight
     */
    @Test
    public void trianglesSpotSharp() {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection).setNarrowBeam(10)
                .setKL(0.001).setKQ(0.00004));


        camera2.setImageWriter(new ImageWriter("lightTrianglesSpotSharp", 500, 500)).setRayTracer(new SimpleRayTracer(scene2))
                .build();
        camera2.renderImage();
        camera2.writeToImage();

    }

    //    @Test
//    public void sphereMultipleLights() {
//
//        Geometry sphere = new Sphere(SPHERE_RADIUS, sphereCenter)
//                .setEmission(sphereColor).setMaterial(new Material().setKd(KD).setKs(KS).setShininess(SHININESS));
//
//
//        Scene scene = new Scene("Test scene");
//        scene.geometries.add(sphere);
//        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.00001)));
//
//        // 3 light source
//        scene.addLight(new DirectionalLight(new Color(500, 400, 200), new Vector(-1, -1, -0.5)));
//        scene.addLight(new PointLight(new Color(200, 250, 250), new Point(50, 50, 0))
//                .setKL(0.01).setKQ(0.0001));
//        scene.addLight(new SpotLight(new Color(300, 300, 400), new Point(-30, -30, 20), new Vector(1, 1, -0.5))
//                .setKL(0.001).setKQ(0.0001));
//
//
//        Camera camera = Camera.getBuilder()
//                .setRayTracer(new SimpleRayTracer(scene))
//                .setLocation(new Point(0, 0, 1000))
//                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
//                .setVpSize(150, 150)
//                .setVpDistance(1000)
//                .setImageWriter(new ImageWriter("sphereMultipleLights", 500, 500))
//                .renderImage()
//                .writeToImage()
//                .build();
//    }
}
