package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.isZero;

public class Camera implements Cloneable {
    private Point location;
    private Vector vRight;
    private Vector vTo;
    private Vector vUp;
    //view plane
    private double height = 0d;
    private double width = 0d;
    private double distance = 0d;

    /**
     * @return the height of the view plane
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return the width of the view plane
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return the distance of the view plane from the camera
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Static method vTo get a new Builder instance.
     *
     * @return a new Builder instance
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Constructs a ray through a specific pixel on the view plane.
     *
     * @param nX the number of columns (width)
     * @param nY the number of rows (height)
     * @param j  the column index of the pixel
     * @param i  the row index of the pixel
     * @return the constructed ray through the specified center pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pc = location.add(vTo.scale(distance));
        double rY = height / nY;
        double rX = width / nX;
        double yI = -(i - (nY - 1) / 2.0) * rY;
        double xJ = (j - (nX - 1) / 2.0) * rX;
        Point pIJ = pc;
        if (xJ != 0) {
            pIJ = pIJ.add(vRight.scale(xJ));
        }
        if (yI != 0) {
            pIJ = pIJ.add(vUp.scale(yI));
        }
        Vector vIJ = pIJ.subtract(location);
        return new Ray(location, vIJ);
    }

    public static class Builder {
        private final Camera camera = new Camera();


        public Builder setLocation(Point location) {
            if (location == null) {
                throw new IllegalArgumentException("location cannot be null");
            }
            camera.location = location;
            return this;
        }

        /**
         * Sets the direction vectors of the camera.
         *
         * @param to the forward direction vector of the camera
         * @param up the upward direction vector of the camera
         * @return the current Builder instance
         */
        public Builder setDirection(Vector to, Vector up) {

            if (to == null || up == null) {
                throw new IllegalArgumentException("Direction vectors cannot be null");
            }

            if (!isZero(to.dotProduct(up))) {
                throw new IllegalArgumentException("Direction vectors must be orthogonal");
            }
            camera.vTo = to.normalize();
            camera.vUp = up.normalize();
            camera.vRight = to.crossProduct(up).normalize();
            return this;
        }


        /**
         * Sets the view plane dimensions.
         *
         * @param width  the width of the view plane
         * @param height the height of the view plane
         * @return the current Builder instance
         */
        public Builder setVpSize(double width, double height) {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("View plane dimensions must be positive");
            }
            camera.width = width;
            camera.height = height;
            return this;
        }


        /**
         * Sets the distance from the camera vTo the view plane.
         *
         * @param distance the distance from the camera vTo the view plane
         * @return the current Builder instance
         */
        public Builder setVpDistance(double distance) {
            if (distance <= 0) {
                throw new IllegalArgumentException("View plane distance must be positive");
            }
            camera.distance = distance;
            return this;
        }


        /**
         * Builds and returns the Camera instance.
         *
         * @return the constructed Camera instance
         * @throws MissingResourceException if any required field is missing
         */
        public Camera build() {
            ////
            final String missing = "Missing rendering data";
            if (camera.location == null) {
                throw new MissingResourceException(missing, "Camera", "location");
            }
            if (camera.vTo == null) {
                throw new MissingResourceException(missing, "Camera", "vTo");
            }
            if (camera.vUp == null) {
                throw new MissingResourceException(missing, "Camera", "vUp");
            }
            if (camera.width == 0) {
                throw new MissingResourceException(missing, "Camera", "width");
            }
            if (camera.height == 0) {
                throw new MissingResourceException(missing, "Camera", "height");
            }
            if (camera.distance == 0) {
                throw new MissingResourceException(missing, "Camera", "distance");
            }
            return (Camera) camera.clone();
        }
    }

    /**
     * Creates and returns a copy of this object.
     *
     * @return a clone of this instance
     */
    @Override
    protected Camera clone() {
        try {
            return (Camera) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Should never happen
        }
    }

}
