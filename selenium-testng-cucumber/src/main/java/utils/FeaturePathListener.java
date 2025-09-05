package utils;

import org.testng.IAlterSuiteListener;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.List;

public class FeaturePathListener implements IAlterSuiteListener {

    @Override
    public void alter(List<XmlSuite> suites) {
        // This method runs early — before TestNG starts tests.
        for (XmlSuite suite : suites) {
            for (XmlTest test : suite.getTests()) {
                // TestNG parameter name used in your XML is "features"
                String featurePath = test.getParameter("features");
                if (featurePath == null) {
                    // fallback to common param at suite level
                    featurePath = suite.getParameter("features");
                }
                if (featurePath != null && !featurePath.isEmpty()) {
                    System.setProperty("cucumber.features", featurePath);
                    System.out.println("[FeaturePathListener] Set cucumber.features -> " + featurePath);
                }
                // If you also want to set testdata.file or browser, you can copy them as well:
                String dataFile = test.getParameter("dataFile");
                if (dataFile != null && !dataFile.isEmpty()) {
                    System.setProperty("testdata.file", dataFile);
                    System.out.println("[FeaturePathListener] Set testdata.file -> " + dataFile);
                }
                String browser = test.getParameter("browser");
                if (browser != null && !browser.isEmpty()) {
                    System.setProperty("browser", browser);
                    System.out.println("[FeaturePathListener] Set browser -> " + browser);
                }
            }
        }
    }
}
