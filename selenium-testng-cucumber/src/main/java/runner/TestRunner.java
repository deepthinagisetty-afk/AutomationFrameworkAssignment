package runner;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

@CucumberOptions(
       //features = "src/test/resources/features",
        glue = {"steps", "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-report/cucumber.html",
                "json:target/cucumber-report/cucumber.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"

        },
        monochrome = true)
public class TestRunner extends AbstractTestNGCucumberTests {

     /* @BeforeClass(alwaysRun = true)
    public void setup(ITestContext context) {
        String browser = context.getCurrentXmlTest().getParameter("browser");
        String featureFile = context.getCurrentXmlTest().getParameter("featureFile");
        String dataFile = context.getCurrentXmlTest().getParameter("dataFile");
        if (browser != null) System.setProperty("browser", browser);
        if (dataFile != null) System.setProperty("testdata.file", dataFile);
        if (featureFile != null) {
            // Override feature path for cucumber
            System.setProperty("cucumber.features", featureFile);
        }


    } */

    @Override
    @DataProvider(parallel = false) // keep parallel if needed
    public Object[][] scenarios() {
        // use the feature file passed from TestNG
               return super.scenarios();
    }



}
