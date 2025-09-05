package reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportPath = System.getProperty("user.dir") + "/target/reports/ExtentReport_" + timestamp + ".html";
            createInstance(reportPath);
        }
        return extent;
    }

    private static ExtentReports createInstance(String filePath) {
        ExtentSparkReporter spark = new ExtentSparkReporter(filePath);
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setReportName("Automation Test Report");
        spark.config().setDocumentTitle("Cucumber + TestNG + Selenium Report");

        extent = new ExtentReports();
        extent.attachReporter(spark);

        // Add system/environment details
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("Browser", System.getProperty("browser", "chrome"));

        return extent;
    }
}
