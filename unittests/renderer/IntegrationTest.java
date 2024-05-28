package renderer;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;
import renderer.Camera;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for Camera ray construction and geometries intersection.
 */
public class IntegrationTest {

    private static final int Nx = 3;
    private static final int Ny = 3;

    private Camera createCamera() {
        return Camera.getBuilder()
                .setLocation(new Point(0, 0, 0))
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(3, 3)
                .setVpDistance(1)
                .build();
    }


    @Test
    public void testSphereIntersections() {
        Camera camera = createCamera();
        Sphere sphere = new Sphere(1, new Point(0, 0, -3));

        int intersections = countIntersections(camera, sphere);
        assertEquals(2, intersections, "Wrong number of intersections with sphere");
    }

    @Test
    public void testPlaneIntersections() {
        Camera camera = createCamera();
        Plane plane = new Plane(new Point(0, 0, -3), new Vector(0, 0, 1));

        int intersections = countIntersections(camera, plane);
        assertEquals(9, intersections, "Wrong number of intersections with plane");
    }

    @Test
    public void testTriangleIntersections() {
        Camera camera = createCamera();
        Triangle triangle = new Triangle(
                new Point(0, 1, -2),
                new Point(1, -1, -2),
                new Point(-1, -1, -2));

        int intersections = countIntersections(camera, triangle);
        assertEquals(1, intersections, "Wrong number of intersections with triangle");
    }

    private int countIntersections(Camera camera, Intersectable geometry) {
        int count = 0;
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                Ray ray = camera.constructRay(Nx, Ny, j, i);
                if (geometry.findIntersections(ray) != null) {
                    count += geometry.findIntersections(ray).size();
                }
            }
        }
        return count;
    }
}