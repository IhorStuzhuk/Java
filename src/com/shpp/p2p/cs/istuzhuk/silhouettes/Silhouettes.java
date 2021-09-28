package com.shpp.p2p.cs.istuzhuk.silhouettes;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * This class counts the silhouettes in the image
 */
public class Silhouettes {
    // RGB of black color.
    private static final int BLACK = 0;
    // RGB of white color.
    private static final int WHITE = 255;
    // The boundary between black and white.
    private static final int BORDER_BETWEEN_WHITE_AND_BLACK = 127;
    // Path to image.
    private static final String PATH_FILE = "com/shpp/p2p/cs/istuzhuk/silhouettes/test48.jpg";
    // Min silhouette size in pixels.
    private static final int MIN_SILHOUETTE_SIZE = 250;

    public static void main(String[] args) throws IOException {
        File file;

        if(args.length == 1 )
            file = new File(args[0]);
        else file = new File(PATH_FILE);

        BufferedImage image = ImageIO.read(file);

        int[][] pixels = sortPixels(image);

        System.out.println(findSilhouettes(pixels) + " silhouettes");
    }

    /**
     * This method reads an image and sorts the array of pixels
     *
     * @param image input image
     * @return an array of pixels.
     */
    public static int[][] sortPixels(BufferedImage image) {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        int[][] pixels = new int[imageWidth][imageHeight];

        for (int i = 0; i < imageWidth; i++)
            for (int j = 0; j < imageHeight; j++) {
                Color color = new Color(image.getRGB(i, j), true);
                pixels[i][j] = color.getRed() <= BORDER_BETWEEN_WHITE_AND_BLACK && color.getAlpha() == 255 ? BLACK : WHITE;
            }
        return pixels;
    }

    /**
     * This method finds silhouettes in the image.
     *
     * @param pixels an array of image pixels.
     * @return count of silhouettes.
     */
    private static int findSilhouettes(int[][] pixels) {
        int silhouetteSize;
        int silhouettes = 0;
        for (int i = 0; i < pixels.length; i++)
            for (int j = 1; j < pixels[i].length; j++)
                if (pixels[i][j] != pixels[i][j - 1]) {
                    silhouetteSize = getSilhouetteSize(pixels, i, j);
                    //clear trash
                    if (silhouetteSize > MIN_SILHOUETTE_SIZE)
                        silhouettes++;
                }

        return silhouettes;
    }

    /**
     * This method calculates the size of a potential silhouette.
     *
     * @param pixels The array of bytes of the image.
     * @param row    row of pixel.
     * @param col    column of pixel.
     * @return potential silhouette size
     */
    private static int getSilhouetteSize(int[][] pixels, int row, int col) {
        int silhouettePixels = 1;
        int[] startPixel = new int[]{row, col};
        LinkedList<int[]> currentPixelCoordinates = new LinkedList<>(Arrays.asList(startPixel));
        pixels[row][col] = pixels[row][col - 1];
        while (!currentPixelCoordinates.isEmpty()) {
            int y = currentPixelCoordinates.get(0)[0];
            int x = currentPixelCoordinates.get(0)[1];
            silhouettePixels++;
            if (y != 0 && pixels[y][x] != pixels[y - 1][x]) {
                currentPixelCoordinates.add(new int[]{y - 1, x});
                pixels[y - 1][x] = pixels[y][x];
            }
            if (x != pixels[0].length - 1 && pixels[y][x] != pixels[y][x + 1]) {
                currentPixelCoordinates.add(new int[]{y, x + 1});
                pixels[y][x + 1] = pixels[y][x];
            }
            if (y != pixels.length - 1 && pixels[y][x] != pixels[y + 1][x]) {
                currentPixelCoordinates.add(new int[]{y + 1, x});
                pixels[y + 1][x] = pixels[y][x];
            }
            if (x != 0 && pixels[y][x] != pixels[y][x - 1]) {
                currentPixelCoordinates.add(new int[]{y, x - 1});
                pixels[y][x - 1] = pixels[y][x];
            }
            currentPixelCoordinates.pollFirst();
        }
        return silhouettePixels;
    }
}