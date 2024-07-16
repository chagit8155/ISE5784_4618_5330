package renderer;

import primitives.*;

import java.util.MissingResourceException;

import static primitives.Util.isZero;

/**
 * @author H & H
 */
public class Camera implements Cloneable {
    private Point location;
    private Vector vRight;
    private Vector vTo;
    private Vector vUp;
    //view plane
    private double height = 0d;
    private double width = 0d;
    private double distance = 0d;
    //
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    private Camera() {
    }

// ***************** Getters **********************

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
        if (!Util.isZero(xJ))
            pIJ = pIJ.add(vRight.scale(xJ));
        if (!Util.isZero(yI))
            pIJ = pIJ.add(vUp.scale(yI));

        Vector vIJ = pIJ.subtract(location);
        return new Ray(location, vIJ);
    }


    @Override
    protected Camera clone() {
        // Creates and returns a copy of this object.
        //return a clone of this instance
        try {
            return (Camera) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Should never happen
        }
    }

    //======================================= Builder =============================================

    public static class Builder {
        private final Camera camera = new Camera();

        /**
         * Builds and returns the Camera instance.
         *
         * @return the constructed Camera instance
         * @throws MissingResourceException if any required field is missing
         */
        public Camera build() {
            final String missing = "Missing rendering data";
            final String cameraStr = "Camera";
            if (camera.location == null) {
                throw new MissingResourceException(missing, cameraStr, "location");
            }
            if (camera.vTo == null) {
                throw new MissingResourceException(missing, cameraStr, "vTo");
            }
            if (camera.vUp == null) {
                throw new MissingResourceException(missing, cameraStr, "vUp");
            }
            if (Util.alignZero(camera.width) <= 0) {
                throw new MissingResourceException(missing, cameraStr, "width");
            }
            if (Util.alignZero(camera.height) <= 0) {
                throw new MissingResourceException(missing, cameraStr, "height");
            }
            if (Util.alignZero(camera.distance) <= 0) {
                throw new MissingResourceException(missing, cameraStr, "distance");
            }
            if (camera.imageWriter == null) {
                throw new MissingResourceException(missing, cameraStr, "imageWriter");
            }
            if (camera.rayTracer == null) {
                throw new MissingResourceException(missing, cameraStr, "rayTracer");
            }
            camera.vRight = (camera.vTo.crossProduct(camera.vUp)).normalize();
            return (Camera) camera.clone();
        }

        /**
         * Casts a ray through the specified pixel.
         *
         * @param nX the number of pixels in the x direction
         * @param nY the number of pixels in the y direction
         * @param i  the x index of the pixel
         * @param j  the y index of the pixel
         */
        private void castRay(int nX, int nY, int i, int j) {
//            Ray ray = camera.constructRay(nX, nY, i, j);
//            Color color = camera.rayTracer.traceRay(ray);
//            camera.imageWriter.writePixel(i, j, color);
            camera.imageWriter.writePixel(j, i, camera.rayTracer.traceRay(camera.constructRay(nX, nY, j, i)));
        }


        /**
         * Renders the image by casting rays through each pixel and returns the camera instance.
         *
         * @return the Camera instance after rendering the image.
         * @throws UnsupportedOperationException if the ImageWriter or RayTracer is not initialized.
         */
        public Builder renderImage() {
            if (camera.imageWriter == null || camera.rayTracer == null) {
                throw new UnsupportedOperationException("ImageWriter or RayTracer is not initialized");
            }

            for (int i = 0; i < camera.imageWriter.getNx(); i++) {
                for (int j = 0; j < camera.imageWriter.getNy(); j++) {
                    castRay(camera.imageWriter.getNx(), camera.imageWriter.getNy(), i, j);
                }
            }
            return this;
            }

        /**
         * Prints a grid on the image with the specified interval and color.
         *
         * @param interval the interval between grid lines
         * @param color    the color of the grid lines
         */
        public Builder printGrid(int interval, Color color) {
            for (int i = 0; i < camera.imageWriter.getNx(); i++) {
                for (int j = 0; j < camera.imageWriter.getNy(); j++) {
                    if (i % interval == 0 || j % interval == 0) {
                        camera.imageWriter.writePixel(i, j, color); //?
                    }
                }
            }
            return this;
        }

        /**
         * Writes the image to a file.
         */
        public Builder writeToImage() {
            camera.imageWriter.writeToImage();
            return this;
        }


        // ******************************** Setters ********************************************

        /**
         * Sets the location of the camera.
         *
         * @param location the Point representing the camera's location. Must not be null.
         * @return the current Builder instance for method chaining.
         * @throws IllegalArgumentException if the location is null.
         */
        public Builder setLocation(Point location) {
//            if (location == null) {
//                throw new IllegalArgumentException("location cannot be null");
//            }
            camera.location = location;
            return this;
        }

        /**
         * Sets the ImageWriter for the camera.
         *
         * @param imageWriter the ImageWriter instance. Must not be null.
         * @return the current Builder instance for method chaining.
         * @throws IllegalArgumentException if the imageWriter is null.
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            if (imageWriter == null) {
                throw new IllegalArgumentException("imageWriter cannot be null");
            }
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Sets the RayTracer for the camera.
         *
         * @param rayTracer the RayTracerBase instance. Must not be null.
         * @return the current Builder instance for method chaining.
         * @throws IllegalArgumentException if the rayTracer is null.
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            if (rayTracer == null) {
                throw new IllegalArgumentException("rayTracer cannot be null");
            }
            camera.rayTracer = rayTracer;
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


    }


}
