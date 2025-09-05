package steps;


import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.simple.JSONObject;
import pages.CoreProductPage;
import utils.JsonDataReader;


public class CoreProduct extends BaseSteps {

    protected JSONObject testData;
    private CoreProductPage CProductPage = new CoreProductPage();

    public CoreProduct() {
        String dataFile = System.getProperty("testdata.json", "CPData.json");
        this.testData = JsonDataReader.getTestData(dataFile);
    }

    @Then("I will capture prices relates to all the jackets")
    public void iWillCapturePricesRelatesToAllTheJackets() {
        try {
            CProductPage.captureAllJacketPrices(testData.get("filePath").toString());
            // Write code here that turns the phrase above into concrete actions
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Given("I navigate to Core Product Page")
    public void iNavigateToCoreProductPage() {
        try {
            CProductPage.navigateToCoreProductPage(testData.get("url").toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @When("I click on Shop and then click Men's")
    public void iClickOnShopAndThenClickMenS() {
        try {
            CProductPage.clickOnMenMenuOfShopMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @When("I click on Jackets Menu from Sub Menu Of Men")
    public void iClickOnJacketsMenuFromSubMenuOfMen() {
        // Write code here that turns the phrase above into concrete actions
        try {
            CProductPage.ClickOnJacketMenuOfMen();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
