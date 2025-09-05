package steps;

import com.google.inject.Inject;
import CommonFunctions.ExecutionContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import model.User;

import base.LoggerUtils;

public class LoginSteps {

    @Inject
    private ExecutionContext context;

    @Given("I open the login page")
    public void i_open_login() {

        LoggerUtils.info("Opening login page on: " + context.getBrowser());
        // context.getDriver().get("https://example.com/login");
    }

    @When("I login with valid credentials")
    public void i_login_valid() {
        User user = context.getTestData().getValidUser();
        LoggerUtils.info("Login with: " + user.getUsername());
        // Enter credentials in driver...
    }

    @Then("I should see the home page")
    public void i_see_home() {
        LoggerUtils.info("Validate home page");
    }
}