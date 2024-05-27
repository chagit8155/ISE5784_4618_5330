package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.isZero;

public class Camera implements Cloneable {
    private Point location;
    private Vector right;
    private Vector to;
    private Vector up;
    //view plane
    private double height = 0;
    private double width = 0;
    private double distance = 0;

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
     * Static method to get a new Builder instance.
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
     * @return the constructed ray through the specified pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        // Method implementation will be added later
        return null;
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
            camera.to = to.normalize();
            camera.up = up.normalize();
            camera.right = to.crossProduct(up).normalize();
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
         * Sets the distance from the camera to the view plane.
         *
         * @param distance the distance from the camera to the view plane
         * @return the current Builder instance
         */
        public Builder setVpDistance(double distance){
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
        public Camera build(){
            ////
            if (camera.location == null) {
                throw new MissingResourceException("Missing rendering data", "Camera", "location");
            }
            if (camera.to == null) {
                throw new MissingResourceException("Missing rendering data", "Camera", "to");
            }
            if (camera.up == null) {
                throw new MissingResourceException("Missing rendering data", "Camera", "up");
            }
            if (camera.width == 0) {
                throw new MissingResourceException("Missing rendering data", "Camera", "width");
            }
            if (camera.height == 0) {
                throw new MissingResourceException("Missing rendering data", "Camera", "height");
            }
            if (camera.distance == 0) {
                throw new MissingResourceException("Missing rendering data", "Camera", "distance");
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
