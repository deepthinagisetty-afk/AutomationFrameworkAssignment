package hooks;

import base.DriverFactory;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import io.cucumber.java.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks {
    private static final Logger log = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        log.info("🚀 Starting scenario: " + scenario.getName());

        // ✅ Start browser (default = chrome if not passed from testng.xml)
        String browser = System.getProperty("browser", "chrome");
        DriverFactory.initDriver(browser);
    }

    @AfterStep
    public void attachScreenshot(Scenario scenario) {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failed Step Screenshot");
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        log.info("✅ Finished scenario: " + scenario.getName() +
                " | Status: " + scenario.getStatus());
        DriverFactory.quitDriver();
    }
}
