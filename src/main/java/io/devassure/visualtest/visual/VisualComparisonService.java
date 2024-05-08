package io.devassure.visualtest.visual;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class VisualComparisonService {
    
    public void visualComparePage(WebDriver driver, String label) {
        File baseFile = new File("visual-compare-base/" + label + ".png");
        try {
            if(!baseFile.exists()) {
                System.out.println("File with label " + label + "is not present. Writing base snapshot!");
                this.saveBaseSnapshot(driver, baseFile);
            } else {
                File screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
                ImageProcessor.compareImages(baseFile, screen, label);
            }
        } catch (IOException e) {
            System.err.println("Error doing visual comparison");
            e.printStackTrace();
        }
    }

    private void saveBaseSnapshot(WebDriver driver, File targetFile) throws IOException {
        FileHandler.createDir(new File("visual-compare-base")); //will be skipped if present
        File screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileHandler.copy(screen, targetFile);
    }

}
