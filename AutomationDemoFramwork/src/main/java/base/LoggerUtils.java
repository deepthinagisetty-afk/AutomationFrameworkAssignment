package utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

public class LoggerUtils {
    private static final Logger logger = LogManager.getLogger(LoggerUtils.class);

    public static void info(String message) {
        logger.info(message);
        ExtentCucumberAdapter.addTestStepLog(message);
    }

    public static void warn(String message) {
        logger.warn(message);
        ExtentCucumberAdapter.addTestStepLog("[WARN] " + message);
    }

    public static void error(String message) {
        logger.error(message);
        ExtentCucumberAdapter.addTestStepLog("[ERROR] " + message);
    }

}