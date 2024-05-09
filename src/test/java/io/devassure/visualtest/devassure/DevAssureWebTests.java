package io.devassure.visualtest.devassure;

import org.testng.annotations.Test;

import io.devassure.visualtest.BaseTest;
import io.devassure.visualtest.visual.VisualComparisonService;

public class DevAssureWebTests extends BaseTest {
    @Test
    public void testLandinPage(){
        driver.get("https://app.devassure.io");
        new VisualComparisonService().visualComparePage(driver, "devassurelanding");
    }
}