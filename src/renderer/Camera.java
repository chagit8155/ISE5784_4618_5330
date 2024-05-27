package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Camera implements Cloneable {
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

        }

        /**
         * Sets the direction vectors of the camera.
         *
         * @param to the forward direction vector of the camera
         * @param up the upward direction vector of the camera
         * @return the current Builder instance
         */
        public Builder setDirections(Vector to, Vector up) {

        }


        /**
         * Sets the view plane dimensions.
         *
         * @param width  the width of the view plane
         * @param height the height of the view plane
         * @return the current Builder instance
         */
        public Builder setVpSize(double width, double height) {

        }


        /**
         * Sets the distance from the camera to the view plane.
         *
         * @param distance the distance from the camera to the view plane
         * @return the current Builder instance
         */
        public Builder setVpDistance(double distance){

        }
    }


}
