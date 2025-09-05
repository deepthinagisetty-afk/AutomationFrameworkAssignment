package base;

import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reporting.ExtentManager;

public class BaseTest {
    private static final Logger log = LogManager.getLogger(BaseTest.class);

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext context) {
        log.info("===== Test Suite Started: " + context.getSuite().getName() + " =====");
        ExtentManager.getInstance();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite(ITestContext context) {
        log.info("===== Test Suite Finished: " + context.getSuite().getName() + " =====");
        ExtentManager.getInstance().flush();
    }
}
