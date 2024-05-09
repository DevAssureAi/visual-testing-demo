package io.devassure.visualtest.visual;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;

public class VisualComparisonService {

    public void visualComparePage(WebDriver driver, String label) {
        visualCompare((TakesScreenshot) driver, label, false);
    }

    public void visualCompareElement(WebElement element, String label) {
        visualCompare((TakesScreenshot) element, label, false);
    }

    public void visualComparePage(WebDriver driver, String label, boolean optional) {
        visualCompare((TakesScreenshot) driver, label, optional);
    }

    public void visualCompareElement(WebElement element, String label, boolean optional) {
        visualCompare((TakesScreenshot) element, label, optional);
    }
    
    private void visualCompare(TakesScreenshot screenshotObj, String label, boolean optional) {
        File baseFile = new File("visual-compare-base/" + label + ".png");
        try {
            if(!baseFile.exists()) {
                System.out.println("File with label " + label + "is not present. Writing base snapshot!");
                this.saveBaseSnapshot((TakesScreenshot)screenshotObj, baseFile);
            } else {
                File screen = ((TakesScreenshot)screenshotObj).getScreenshotAs(OutputType.FILE);
                boolean result = ImageProcessor.compareImages(baseFile, screen, label);
                if(!result && !optional) {
                    Assert.fail("Visual comparison Failed! The current snapshot is deviating from base the base snapshot for label " + label);
                } else if(result) {
                    System.out.println("Visual comparison passed! Label: " + label);
                } else {
                    System.out.println("Visual comparison failed! Skipping optional check. Label: " + label);
                }
            }
        } catch (IOException e) {
            System.err.println("Error performing visual comparison");
            if(!optional) {
                Assert.fail("Error performing visual comparison", e);
            }
        }
    }

    private void saveBaseSnapshot(TakesScreenshot screenshotObj, File targetFile) throws IOException {
        FileHandler.createDir(new File("visual-compare-base")); //will be skipped if present
        File screen = ((TakesScreenshot)screenshotObj).getScreenshotAs(OutputType.FILE);
        FileHandler.copy(screen, targetFile);
    }


}
