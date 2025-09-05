package reporting;

import base.DriverFactory;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

public class ExtentCucumberPlugin implements ConcurrentEventListener {

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> scenarioNode = new ThreadLocal<>();
    private static ThreadLocal<ExtentTest> stepNode = new ThreadLocal<>();
    private static ThreadLocal<ExtentTest> featureNode = new ThreadLocal<>();

    // Keep a map of featureUri -> ExtentTest
    private static Map<String, ExtentTest> featureMap = new HashMap<>();

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestSourceRead.class, this::handleFeatureRead);
        publisher.registerHandlerFor(TestCaseStarted.class, this::handleScenarioStart);
        publisher.registerHandlerFor(TestStepStarted.class, this::handleStepStart);
        publisher.registerHandlerFor(TestStepFinished.class, this::handleStepFinish);
        publisher.registerHandlerFor(TestCaseFinished.class, this::handleScenarioFinish);
        publisher.registerHandlerFor(TestRunFinished.class, this::handleTestRunFinished);
    }

    // Capture feature name only once per file
    private void handleFeatureRead(TestSourceRead event) {
        String featureUri = event.getUri().toString();
        String featureName = featureUri.substring(featureUri.lastIndexOf("/") + 1);
        if (!featureMap.containsKey(featureUri)) {
            ExtentTest feature = extent.createTest(featureName);
            featureMap.put(featureUri, feature);
        }
    }

    // Capture scenario start
    private void handleScenarioStart(TestCaseStarted event) {
        String scenarioName = event.getTestCase().getName();

        if (scenarioName == null || scenarioName.trim().isEmpty()) {
            scenarioName = "Scenario at: " + event.getTestCase().getUri() +
                    " line: " + event.getTestCase().getLocation().getLine();
        }

        ExtentTest feature = featureNode.get();
        if (feature == null) {
            // fallback if feature wasn't initialized
            feature = extent.createTest("Unnamed Feature");
            featureNode.set(feature);
        }

        ExtentTest scenario = feature.createNode(scenarioName);
        scenarioNode.set(scenario);
    }


    // Capture step start
    private void handleStepStart(TestStepStarted event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
            String stepText = step.getStep().getKeyword() + step.getStep().getText();
            stepNode.set(scenarioNode.get().createNode(stepText));
        }
    }

    // Capture step result with screenshot
    private void handleStepFinish(TestStepFinished event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            WebDriver driver = DriverFactory.getDriver();
            String base64Screenshot = null;

            if (driver != null) {
                base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            }

            switch (event.getResult().getStatus()) {
                case FAILED:
                    if (base64Screenshot != null) {
                        stepNode.get()
                                .fail(event.getResult().getError())
                                .addScreenCaptureFromBase64String(base64Screenshot, "Failed Step Screenshot");
                    } else {
                        stepNode.get().fail(event.getResult().getError());
                    }
                    break;

                case PASSED:
                    stepNode.get().pass("✅ Step passed");
                    break;

                case SKIPPED:
                    stepNode.get().skip("⚠️ Step skipped");
                    break;

                default:
                    stepNode.get().info("ℹ️ Step result: " + event.getResult().getStatus());
                    break;
            }
        }
    }

    // Capture scenario finish
    private void handleScenarioFinish(TestCaseFinished event) {
        // You could log scenario status here if needed
    }

    // Flush report after all tests complete
    private void handleTestRunFinished(TestRunFinished event) {
        extent.flush();
    }
}
