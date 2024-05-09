package io.devassure.visualtest.visual;

import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.openqa.selenium.io.FileHandler;

public class ImageProcessor {

    private static final double DEFAULT_THRESHOLD = 0.1;

    public static boolean compareImages(File baseFile, File snapshotFile, String label) throws IOException {
        return compareImages(baseFile, snapshotFile, label, DEFAULT_THRESHOLD);
    }
    
    public static boolean compareImages(File baseFile, File snapshotFile, String label, double threshold) throws IOException {
        BufferedImage baseImage = ImageIO.read(baseFile);
        BufferedImage snapshot = ImageIO.read(snapshotFile);
        BufferedImage resultImage = ImageIO.read(snapshotFile);
        int w1 = baseImage.getWidth();
        int w2 = snapshot.getWidth();
        int h1 = baseImage.getHeight();
        int h2 = snapshot.getHeight();
        if (w1 != w2 || h1 != h2) {
            throw new IOException("Images are not of same dimensions. Base image: " + w1 + " x " + h1 
            + ". Current snapshot: " + w2 + " x " + h2);
        }
        long diff = 0;
        for (int j = 0; j < h1; j++) {
            for (int i = 0; i < w1; i++) {
                //Getting the RGB values of a pixel
                int pixel1 = baseImage.getRGB(i, j);
                Color color1 = new Color(pixel1, true);
                int r1 = color1.getRed();
                int g1 = color1.getGreen();
                int b1 = color1.getBlue();
                int pixel2 = snapshot.getRGB(i, j);
                Color color2 = new Color(pixel2, true);
                int r2 = color2.getRed();
                int g2 = color2.getGreen();
                int b2= color2.getBlue();
                //sum of differences of RGB values of the two images
                long data = Math.abs(r1-r2)+Math.abs(g1-g2)+ Math.abs(b1-b2);

                if(data != 0) {
                    int modifiedRGB= new Color(255, 0, 0).getRGB();
					resultImage.setRGB(i, j, modifiedRGB);
                } else {
                    Color color = new Color(resultImage.getRGB(i, j));
                    Color newColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 70);
                    resultImage.setRGB(i, j, newColor.getRGB());
                }
                diff = diff+data;
            }
        }
        System.out.println("Total pixel diff = " + diff);
        double avg = diff/(w1*h1*3);
        double percentage = (avg/255)*100;
        System.out.println("Percentage difference between images = " + percentage);
        saveResult(baseImage, snapshot, resultImage, label);
        return percentage <= threshold;
    }

    private static void saveResult(BufferedImage expected, BufferedImage actual, BufferedImage result, String label) throws IOException {
        FileHandler.createDir(new File("visual-test-results"));
        File baseFolder = new File("visual-test-results/" + label);
        FileHandler.createDir(baseFolder);
        ImageIO.write(result, "png", new File(baseFolder.getPath() + "/result.png"));
        ImageIO.write(expected, "png", new File(baseFolder.getPath() + "/expected.png"));
        ImageIO.write(actual, "png", new File(baseFolder.getPath() + "/actual.png"));
        FileHandler.copy(new File("devassure-visual-test-report.html"), new File(baseFolder.getPath() + "/devassure-visual-test-report.html"));
    }
}
