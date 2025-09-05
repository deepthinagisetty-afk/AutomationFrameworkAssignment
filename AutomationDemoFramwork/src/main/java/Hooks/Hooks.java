package Hooks;


    import com.google.inject.Inject;
import CommonFunctions.ExecutionContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import base.DriverFactory;

    public class Hooks {

        @Inject
        private ExecutionContext context;

        @Before
        public void beforeScenario(Scenario scenario) {
            context.setDriver(DriverFactory.createDriver(context.getBrowser()));
            context.getDriver().manage().window().maximize();
        }

        @After
        public void afterScenario(Scenario scenario) {
            if (scenario.isFailed()) {
                byte[] screenshot = ((TakesScreenshot) context.getDriver())
                        .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failure Screenshot");
            }
            if (context.getDriver() != null) context.getDriver().quit();
        }
    }
