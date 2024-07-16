package renderer;
import primitives.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static primitives.Util.isZero;

/**
 * TargetView class is responsible for generating rays targeted at the View Plane (VP) from a given location and direction.
 */
public class TargetView {
    private final static int DENSITY = 7;  // Density of the grid for ray construction
    Point location;  // The location of the camera or origin point
    Vector vTo;  // The direction vector towards the view plane
    Vector vRight;  // The right vector perpendicular to the direction vector
    Vector vUp;  // The up vector perpendicular to both direction and right vectors
    double heightViewPlane = 0.0;  // Height of the view plane
    double widthViewPlane = 0.0;  // Width of the view plane
    double distanceFromViewPlane = 100;  // Distance from the view plane
    Point viewPlaneCenter;  // Center point of the view plane

    /**
     * Constructor for TargetView.
     * Initializes the location and direction of the camera and calculates the view plane center and basis vectors.
     *
     * @param ray The ray representing the direction and origin of the camera
     * @param size The size of the view plane
     */
    public TargetView(Ray ray, double size) {
        location = ray.getHead();
        vTo = ray.getDirection();
        viewPlaneCenter = ray.getHead().add(vTo.scale(distanceFromViewPlane));
        double a = vTo.getX();
        double b = vTo.getY();
        double c = vTo.getZ();
        vRight = (a == b && b == c) ? new Vector(0, -a, a).normalize() : new Vector(b - c, c - a, a - b).normalize();
        vUp = vRight.crossProduct(vTo);
        this.heightViewPlane = this.widthViewPlane = size;
    }

    /**
     * Default constructor.
     * Initializes an empty TargetView object.
     */
    public TargetView() {}

    /**
     * Constructs a ray from the camera location through a specific pixel on the view plane.
     *
     * @param nX Width of the pixel grid
     * @param nY Height of the pixel grid
     * @param j Column index of the pixel
     * @param i Row index of the pixel
     * @return Ray object representing the constructed ray
     */
    public Ray constructRay(int nX, int nY, double j, double i) {
        double rY = heightViewPlane / nY;
        double rX = widthViewPlane / nX;
        double xJ = (j - ((nX - 1) / 2.0)) * rX;
        double yI = -(i - ((nY - 1) / 2.0)) * rY;
        Point pIJ = viewPlaneCenter;
        if (!isZero(xJ))
            pIJ = pIJ.add(vRight.scale(xJ));
        if (!isZero(yI))
            pIJ = pIJ.add(vUp.scale(yI));
        Vector Vij = pIJ.subtract(location);
        return new Ray(location, Vij);
    }

    /**
     * Constructs a grid of rays around the central ray to simulate soft shadows or anti-aliasing.
     *
     * @return List of Ray objects representing the grid of rays
     */
    public List<Ray> constructRayBeamGrid() {
        Random rand = new Random();
        List<Ray> rays = new LinkedList<>();
        for (int i = 0; i < DENSITY; i++) {
            for (int j = 0; j < DENSITY; j++) {
                rays.add(constructRay(DENSITY, DENSITY, rand.nextDouble() + j - 0.5,
                        rand.nextDouble() + i - 0.5));
            }
        }
        return rays;
    }
}
