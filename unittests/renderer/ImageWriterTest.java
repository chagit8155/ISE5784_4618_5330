package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {


    @Test
    void testWriteToImage() {

        // Create an ImageWriter instance with 800x500 resolution
        ImageWriter imageWriter = new ImageWriter("test_image", 800, 500);

        // Define colors
        Color backgroundColor = new Color(java.awt.Color.YELLOW);
        Color gridColor = new Color(java.awt.Color.GREEN);

        // Fill the image with the background color
        for (int y = 0; y < 500; y++) {
            for (int x = 0; x < 800; x++) {
                imageWriter.writePixel(x, y, backgroundColor);
            }
        }

        // Draw the grid: 16x10 squares
        int rows = 10;
        int columns = 16;
        int rowHeight = 500 / rows;  //50
        int colWidth = 800 / columns;  //50

        for (int y = 0; y < 500; y++) {
            for (int x = 0; x < 800; x++) {
                if (x % colWidth == 0 || y % rowHeight == 0) {
                    imageWriter.writePixel(x, y, gridColor);
                }
            }
        }

        // Write the image to a file
        imageWriter.writeToImage();
    }

}