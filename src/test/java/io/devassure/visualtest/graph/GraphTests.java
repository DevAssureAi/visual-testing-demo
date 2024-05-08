
package io.devassure.visualtest.graph;

import org.testng.annotations.Test;

import io.devassure.visualtest.BaseTest;
import io.devassure.visualtest.visual.VisualComparisonService;

public class GraphTests extends BaseTest{
    @Test
    public void LoginToWebsite(){
        driver.get("http://localhost:3000");
        new VisualComparisonService().visualComparePage(driver, "testing1");
    }
}