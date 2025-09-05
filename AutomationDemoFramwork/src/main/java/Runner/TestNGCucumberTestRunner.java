package Runner;

import com.google.inject.Guice;
import com.google.inject.Injector;
import CommonFunctions.ExecutionContext;
import CommonFunctions.ExecutionModule;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = {},
        glue = {"steps","hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber-report.json",
                "tech.grasshopper.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        }
)
public class TestNGCucumberTestRunner extends AbstractTestNGCucumberTests {

    private static ExecutionContext context;

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser","featureFile","dataFile"})
    public void setup(String browser,String featureFile,String dataFile) {
        context = new ExecutionContext();
        context.setBrowser(browser);
        context.setFeatureFile(featureFile);
        context.setDataFile(dataFile);

        Injector injector = Guice.createInjector(new ExecutionModule(context));
        injector.injectMembers(this);

        System.setProperty("cucumber.features", featureFile);
    }

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
