package steps;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.simple.JSONObject;
import pages.BasePage;
import pages.CoreProductPage;
import pages.DP2Page;
import utils.JsonDataReader;

public class DerivedProduct2  {


    protected JSONObject testData;
    private DP2Page DP2Page = new DP2Page();

    public DerivedProduct2() {
        String dataFile = System.getProperty("testdata.json", "DP2Data.json");
        this.testData = JsonDataReader.getTestData(dataFile);
    }

    @Given("I navigate to Derived Product Page")
    public void iNavigateToDerivedProductPage() {
        try {
            DP2Page.navigateToDerivedProductPage(testData.get("url").toString());
            // Write code here that turns the phrase above into concrete actions
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @When("Scroll to Footer of the Page")
    public void scrollToFooterOfThePage() {
        // Write code here that turns the phrase above into concrete actions
      try{

          DP2Page.scrollToFooterOfThePage();

      }catch(Exception e){
            e.printStackTrace();
      }
    }

    @When("I capture all the Hyperlinks in Footer")
    public void iCaptureAllTheHyperlinksInFooter() {
        // Write code here that turns the phrase above into concrete actions
        try{
            DP2Page.captureAllLinksInFooter(testData.get("filePath").toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Then("I find duplicate links in Footer")
    public void iFindDuplicateLinksInFooter() {
        // Write code here that turns the phrase above into concrete actions
        try{
            DP2Page.findDuplicateLinksInFooter(testData.get("filePath").toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
