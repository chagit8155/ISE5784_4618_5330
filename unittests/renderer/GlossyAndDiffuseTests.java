package renderer;

import static java.awt.Color.*;

import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

public class GlossyAndDiffuseTests {

    private final Scene scene = new Scene("Glossy Test Scene").setCBR();
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
                .build()
                .renderImage()
                .writeToImage()
                ;
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
                .build()
                .renderImage()
                .writeToImage();
    }










    @Test
    public void megaTest() {

        Camera.Builder camera = Camera.getBuilder().setLocation(new Point(0, -243, 0))
                .setDirection(new Vector(0, 1, 0), new Vector(0, 0, 1))
                .setVpSize(200d, 200).setVpDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add(
                new Sphere(30d, new Point(-80, 10, -30))
                        .setMaterial(new Material().setKd(0.3).setKs(0.5).setShininess(10).setKr(0.5)// .setKt(0.55)
                                .setGlossiness(12)),
                new Sphere(35d, new Point(60, 0, 0))
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(3).setKr(0.4).setKt(0.2))
                        .setEmission(new Color(252, 148, 3)),
                new Sphere(25d, new Point(-15, -40, 10))
                        .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(5).setKr(0.7).setKt(0.2))
                        .setEmission(new Color(130, 3, 200)),

                new Polygon(new Point(-251, -251, -150), new Point(251, -251, -150), new Point(251, 251, -150),
                        new Point(-251, 251, -150))
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(3).setKr(0.4)),

                new Polygon(new Point(-80, 60, -60), new Point(80, 60, -60), new Point(80, 100, 10),
                        new Point(-80, 100, 10)).setMaterial(new Material().setKd(0.6).setKr(0.4))
                        .setEmission(new Color(3, 150, 70)),

                new Triangle(new Point(-10, 10, 100), new Point(100, 0, 100), new Point(50, 90, 100))
                        .setMaterial(new Material().setKd(0.2).setKs(0.6).setShininess(8).setKt(0.6).setDiffuseness(5))
                        .setEmission(new Color(250, 70, 0)));//,

//				new Triangle(new Point(-20, 20, -60), new Point(30, 70, 0), new Point(-20, 70, 0))
//						.setMaterial(new Material().setKd(0.2).setKs(0.6).setShininess(8).setKt(0.6))
//						.setEmission(new Color(java.awt.Color.RED)));

        scene.geometries.add( //
                new Polygon(new Point(-150, -150, -125), new Point(150, -150, -125), new Point(75, -75, -150), new Point(-75, -75, -150)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.5).setShininess(40).setKr(0.5)));

//				new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(java.awt.Color.BLUE)) //
//						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

        scene.lights.add(new SpotLight(new Color(300, 300, 300), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setKL(4E-5).setKQ(2E-7));

        ImageWriter imageWriter = new ImageWriter("MPCombination-3", 800, 800);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new SimpleRayTracer(scene))
                .build()
                .renderImage()
                .writeToImage();
    }

















    @Test
    public void mp1Test() {
        int nX = 600;
        int nY = 400;
        double zWall = -1000;
        double yTable = 30;
        double zTable = -120;
        double n = 10;
        double h = 500;
        double xWall = nX / 2 - 2 * nX / 3 - 50;
        double b = 5;
        double bb = 1.5 * b;
        double radiusBall = 7;
        double x = -100;
        double z = (-110454545.45 - 490909.09 * x) / 36818.18;
        scene.setBackground(new Color(WHITE));
//        Point p1=new Point(5,3,-10);
//        Point p2=new Point(10,3,0);
//        Point p3=new Point(-5,3,-10);
//    Polygon rectangle=new Polygon(p1,new Point(-5,3,-10),new Point(-5,-3,-10),new Point(5,-3,-10));
        scene.geometries.add(
                new Plane(new Point(nX / 2, nY / 2, zWall), new Point(xWall, nY / 2, zWall)
                        , new Point(xWall, -5 * nY / 44, zWall))
                        .setEmission(new Color(89, 52, 0)),
                //.setMaterial(new Material().setKR(1)),
                new Plane(new Point(nX / 2, -5 * nY / 44, zWall), new Point(xWall, -5 * nY / 44, zWall), new Point(-nX / 2, -nY / 2, 1000))
                        .setEmission(new Color(245, 234, 180)),
                new Plane(new Point(xWall, nY / 2, zWall), new Point(xWall, -5 * nY / 44, zWall), new Point(-nX / 2, -nY / 2, 1000))
                        .setEmission(new Color(89, 52, 0)),
                new Polygon(new Point(-100, 30, -120), new Point(300, 30, -120)
                        , new Point(115, -55, 200), new Point(-190, -55, 200))
                        .setMaterial(new Material().setKs(0.5)),
                new Polygon(new Point(115, -55, 200), new Point(-190, -55, 200),
                        new Point(-190 + bb, -55 - bb, 200), new Point(115, -55 - bb, 200))/////////
                        .setMaterial(new Material().setKs(0.5)),
                new Polygon(new Point(300, 30, -120), new Point(115, -55, 200),
                        new Point(115, -55 - bb, 200), new Point(300, 30 - bb, -120))////////
                        .setMaterial(new Material().setKs(0.5)),
                new Polygon(new Point(-100 + b, 30 + 0.0001, -120), new Point(300 - b, 30 + 0.0001, -120)
                        , new Point(115 - b, -55 + 0.0001, 200), new Point(-190 + b, -55 + 0.0001, 200))
                        .setEmission(new Color(29, 148, 0)),//*********
                new Triangle(new Point(115, -55, 200), new Point(115 - b, -55, 200), new Point(115, -285, 200)),
                new Triangle(new Point(115, -55, 200), new Point(125, -55, 190), new Point(115, -285, 200)),
                new Triangle(new Point(-190, -55, 200), new Point(-190 + bb, -55, 200), new Point(-190, -285, 200)),
                new Triangle(new Point(-190, -55, 200), new Point(-190, -55, 190), new Point(-190, -285, 200)),
                new Triangle(new Point(300, 30, -120), new Point(300 - b, 30, -120), new Point(300, -285, -120)),
                new Triangle(new Point(300, 30, -120), new Point(300 - 2 * b, 30, -120), new Point(300, -285, -120)),
                new Triangle(new Point(-100, 30, -120), new Point(-100 + b, 30, -120), new Point(-100, -285, -120)),
                new Triangle(new Point(-100, 20, -120), new Point(-100 - b, 20, -110), new Point(-100, -285, -120)),
                new Triangle(new Point(-190 + bb, -55 - bb, 200), new Point(-190 + 5 * radiusBall + bb, -55 - bb, 200)
                        , new Point(-190 + 6 * radiusBall + bb, -55 - 2.5 * radiusBall - bb, 200 - radiusBall))
                        .setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.8).setKr(0.3)),
                new Triangle(new Point(-190 + bb, -55 - bb, 200), new Point(-190 + 5 * radiusBall + bb, -55 - bb, 200 - 3 * radiusBall)
                        , new Point(-190 + 6 * radiusBall + bb, -55 - 2.5 * radiusBall - bb, 200 - radiusBall))
                        .setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.8).setKr(0.3)),
                new Sphere(radiusBall, new Point(-190 + 4 * radiusBall + bb, -55 - 2 * radiusBall, 200 - radiusBall - bb))
                        .setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point(115, -55 - bb, 200), new Point(100 - 5 * radiusBall + bb, -55 - bb, 200)
                        , new Point(100 - 6 * radiusBall + bb, -55 - 2.5 * radiusBall - bb, 200 - radiusBall))
                        .setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Triangle(new Point(115, -55 - bb, 200), new Point(100 - 5 * radiusBall + bb, -55 - bb, 200 - 3 * radiusBall)
                        , new Point(100 - 6 * radiusBall + bb, -55 - 2.5 * radiusBall - bb, 200 - radiusBall))
                        .setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Triangle(new Point(-200, -105, 100), new Point(-200, 90, 120), new Point(-200, 100, 80))
                        .setEmission(new Color(214,165,143)),
                new Triangle(new Point(-200, -105, 55), new Point(-200, 100, 35), new Point(-200, 90, 75))
                        .setEmission(new Color(214,165,143)),
                new Triangle(new Point(-200, -105, 10), new Point(-200, 100, -10), new Point(-200, 90, 30))
                        .setEmission(new Color(214,165,143)),
                new Triangle(new Point(-200, -105, -35), new Point(-200, 100, -55), new Point(-200, 90, -15))
                        .setEmission(new Color(214,165,143)),
                new Polygon(new Point(-1, 200, 0), new Point(1, 200, 0), new Point(1, 140, 0)
                        , new Point(-1, 140, 0))
                        .setEmission(new Color(PINK)),
                new Sphere(30, new Point(0, 110, 0)).setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)),
                //  .setMaterial(new Material().setKs(0.00000000000001).setKT(0.8).setNShininess(50)),
                new Sphere(radiusBall, new Point(-60,10+2*radiusBall,-60)).setEmission(new Color (RED))
                        .setMaterial(new Material().setKd(0.1).setKs(0.7)),
                new Sphere(radiusBall, new Point(0,10+radiusBall,-10)).setEmission(new Color (ORANGE))
                        .setMaterial(new Material().setKd(0.1).setKs(0.7)),
                new Sphere(radiusBall, new Point(90,-15,75)).setEmission(new Color (WHITE))//
                        .setMaterial(new Material().setKd(0.1).setKs(0.7)),
                new Sphere(radiusBall, new Point(205,2,40)).setEmission(new Color (BLACK))
                        .setMaterial(new Material().setKd(0.1).setKs(0.7)),
                new Sphere(radiusBall, new Point(-120,-20,95)).setEmission(new Color (BLUE))
                        .setMaterial(new Material().setKd(0.1).setKs(0.7)),
                new Sphere(radiusBall, new Point(-75,-40,180)).setEmission(new Color (ORANGE))
                        .setMaterial(new Material().setKd(0.1).setKs(0.7)),
                new Sphere(radiusBall, new Point(85,-38,170)).setEmission(new Color (YELLOW))
                        .setMaterial(new Material().setKd(0.1).setKs(0.7)),
                new Sphere(radiusBall, new Point(65,-41,185)).setEmission(new Color (89,40,89))
                        .setMaterial(new Material().setKd(0.1).setKs(0.7)),
                new Sphere(radiusBall, new Point(-15,-15,75)).setEmission(new Color (GREEN))
                        .setMaterial(new Material().setKd(0.1).setKs(0.7))
//                new Triangle(new Point(300 - 2 * b, 15, -150), new Point(260, 15, -50)
//                        , new Point(280, 0, -10))
//                        .setEmission(new Color(RED)),
//                   //     .setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(30).setKT(0.8).setKR(0.3)),
//                new Triangle(new Point(115, -55 - bb, 200), new Point(100 - 5 * radiusBall + bb, -55 - bb, 200 - 3 * radiusBall)
//                        , new Point(100 - 6 * radiusBall + bb, -55 - 2.5 * radiusBall - bb, 200 - radiusBall))
//                        .setEmission(new Color(WHITE))
//                    //    .setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(30).setKT(0.8).setKR(0.3))
//                new Polygon(new Point(-100+bb,30-bb,-120),new Point(300+bb,30-bb,-120)
//                        ,new Point(115+bb,-55-bb,200),new Point(-190+bb,-55-bb,200))
//                        .setMaterial(new Material().setKs(0.5)).setEmission(new Color(GRAY))
        );
        // scene.setAmbientLight(new AmbientLight(new Color(WHITE),0.2));
        //scene.lights.add(new SpotLight(new Color(RED),new Point(0, 110, 0),new Vector(0,-1,0)).setkL(4E-5).setkQ(2E-7));
        scene.lights.add(new PointLight(new Color(251,248,176),new Point(0, 110, 0)).setKL(4E-5).setKQ(2E-7));
        scene.lights.add(new DirectionalLight(new Color(239,104,132),new Vector(-1, -1, 4)));
        //  scene.lights.add(new DirectionalLight(new Color(RED), new Vector(0, 0, -950)));
        scene.lights.add(new SpotLight(new Color(RED), new Point(90+2*radiusBall,-15,75+radiusBall), new Vector(-1, 0, -1))
        ) ;//   .setkL(0.00001).setkQ(0.000005));
//        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
//        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4))
//                .setkL(0.00001).setkQ(0.000005));
        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000d)
                .setVpSize((double) nY, (double) nX)
                // .setAntiAliasingFactor(25)
            //    .setAntiAliasingFactor(9)
                .setMultithreading(3)
                .setImageWriter(new ImageWriter("mp1SNOOKER300rays", nX, nY))
                .build()
                .renderImage()
                .writeToImage();
    }


}