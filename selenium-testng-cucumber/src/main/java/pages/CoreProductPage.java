package pages;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import hooks.Hooks;
import locators.CorePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v85.page.Page;
import org.openqa.selenium.support.ui.Wait;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CoreProductPage extends BasePage {

    public void navigateToCoreProductPage(String url) {
        try {
            System.out.println("Navigating to URL: " + url);
            navigateTo(url);  // comes from BasePage
            waitForPageLoad();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void clickOnMenMenuOfShopMenu() {
        waitForPageLoad();
        closeDialogIfPresent();
        ClickOnChildMenuFromParentMenu(CorePage.shopMenu, CorePage.MensCategory);
        waitForPageLoad();
    }



    public void ClickOnJacketMenuOfMen() {

        switchToNewTab();
        waitForPageLoad();
        ClickOnChildMenuFromParentMenu(CorePage.MensMenu, CorePage.MensJacketCategory);
        waitForPageLoad();
        waitForSeconds();
         }

    public void captureAllJacketPrices(String filePath) {

        try {
            File file = new File(filePath + ".txt");
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            try(FileWriter writer = new FileWriter(file)) {
                writer.write("Product Name,Price,TopSellingmessage\n");

                System.out.println("Capturing all jacket prices...");
                String PageCountText = getAttribute(CorePage.pageCount, "textContent");
                String pageCountNumber = PageCountText.substring(PageCountText.lastIndexOf(" "), PageCountText.length()).trim();
                System.out.println("Page Count Text: " + PageCountText + "Sub String " + pageCountNumber);

                List<WebElement> pageElements = FindElementsWithLocator(CorePage.PaginationElements);
                // This could involve locating price elements and extracting their text
                for (int i = 1; i < pageElements.size(); i++) {

                    List<WebElement> JacketPrices = FindElementsWithLocator(CorePage.productCards);
                    for (int j = 1; j <= JacketPrices.size(); j++) {
                        String price = getAttribute(CorePage.getLocatorOfJacketPrice(String.valueOf(j)), "outerText");
                        String title = getAttribute(CorePage.getLocatorOfProductTitle(String.valueOf(j)), "outerText");
                        String topSellerMessage = getAttribute(CorePage.getLocatorOfTopSellerMessage(String.valueOf(j)), "outerText");

                        if (topSellerMessage == null) topSellerMessage = "";

                        writer.write(String.format("%s,%s,%s\n", price, title, topSellerMessage));
                        System.out.println("Product Title: " + title + ", Price: " + price + ", Top Seller Message: " + topSellerMessage);
                    }
                    if (i < pageElements.size()) {
                        click(CorePage.NextPage);
                        waitForSeconds();
                    }

                }



                if (file.exists()) {
                    ExtentCucumberAdapter.getCurrentStep()
                            .log(Status.INFO,
                                    "Jacket Prices file generated: <a href='" + file.getAbsolutePath() + "' target='_blank'>Open File</a>");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void clickElementWithLocator(By Locator) {
        click(Locator);
    }

    private List<WebElement> FindElementsWithLocator(By paginationElements) {

        return driver.findElements(paginationElements);
    }
}
