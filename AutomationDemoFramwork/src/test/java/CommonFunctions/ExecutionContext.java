package CommonFunctions;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.TestData;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class ExecutionContext {
    private WebDriver driver;
    private String browser;
    private String featureFile;
    private String dataFile;
    private TestData testData;

    public WebDriver getDriver() { return driver; }
    public void setDriver(WebDriver driver) { this.driver = driver; }

    public String getBrowser() { return browser; }
    public void setBrowser(String browser) { this.browser = browser; }

    public String getFeatureFile() { return featureFile; }
    public void setFeatureFile(String featureFile) { this.featureFile = featureFile; }

    public String getDataFile() { return dataFile; }
    public void setDataFile(String dataFile) {
        this.dataFile = dataFile;
        loadTestData();
    }

    public TestData getTestData() { return testData; }

    private void loadTestData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.testData = mapper.readValue(new File(this.dataFile), TestData.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load test data: " + dataFile, e);
        }
    }
}
