
package io.devassure.visualtest.graph;

import org.testng.annotations.Test;

import io.devassure.visualtest.BaseTest;
import io.devassure.visualtest.pages.GraphPage;
import io.devassure.visualtest.visual.VisualComparisonService;

public class GraphTests extends BaseTest {

    @Test
    public void testBarGraph() {
        GraphPage page = new GraphPage(driver);
        driver.get("https://linegraphmaker.co/bar-graph");
        page.waitForChartLoad();
        page.enterValue("10, 7, 5, 70");
        new VisualComparisonService().visualCompareElement(page.chart, "bargraph");
    }
}