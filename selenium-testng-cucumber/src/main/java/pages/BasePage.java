package pages;

import base.DriverFactory;
import locators.CorePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigManager;

import java.time.Duration;
import java.util.List;

public class BasePage {
    protected static WebDriver driver;
    protected static WebDriverWait wait;

    public BasePage() {
        this.driver = DriverFactory.getDriver();   // always fetch from DriverFactory
        int timeout = ConfigManager.getInt("explicit.wait"); // default fallback
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    protected String getInnerText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getAttribute("innerText");
    }

    protected String getAttribute(By locator, String attribute) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getAttribute(attribute);
        }catch(Exception e){

            e.printStackTrace();
            return null;
        }
    }


    protected String getTitle() {
        return driver.getTitle();
    }

    protected void navigateTo(String url) {
        System.out.println("Navigating to URL: " + url);
        driver.get(url);
        driver.manage().window().maximize();
    }

    protected void waitForPageLoad() {
        wait.until(webDriver ->
                ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));
    }

    protected void waitForSeconds() {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void switchToNewTab() {
        Object[] windowHandles = driver.getWindowHandles().toArray();
        driver.switchTo().window((String) windowHandles[1]);
    }

    protected void closeDialogIfPresent() {
        try {

            wait.until(ExpectedConditions.elementToBeClickable(CorePage.dialogCloseButton)).click();

        } catch (NoSuchElementException e) {
            System.out.println("No dialog present to close.");
        }
    }

    public void scrollToElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }


    public List<WebElement> findElements(By locator) {

        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    protected void hoverOverElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

   /* protected void clickOnChildMenuFromParentMenu(By menu, By subMenu) {
        try {
            WebElement menuElement = driver.findElement(menu);
            Actions actions = new Actions(driver);
            actions.moveToElement(menuElement).pause(Duration.ofSeconds(2)).perform();
            WebElement subMenuElement = driver.findElement(subMenu);
            actions.moveToElement(subMenuElement).click().perform();
            System.out.println("Clicked on submenu: " + subMenuElement.getText());

        } catch (NoSuchElementException e) {
            System.out.println("Menu or Submenu not found: " + e.getMessage());
        }
    } */

    public static void ClickOnChildMenuFromParentMenu(By menu, By subMenu) {
        try {

            Actions actions = new Actions(driver);

            WebDriverWait menuWait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement menuElement = menuWait.until(ExpectedConditions.visibilityOfElementLocated(menu));
            actions.moveToElement(menuElement).perform();

            // Wait for submenu with its own custom time
            WebDriverWait subWait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement subMenuElement = subWait.until(ExpectedConditions.elementToBeClickable(subMenu));
            actions.moveToElement(subMenuElement).click().perform();
            System.out.println("Clicked on submenu: " + subMenuElement.getText());
        }  catch (TimeoutException e) {
            System.out.println("⏳ Timeout: Menu or submenu not found within wait period.");
        } catch (Exception e) {
            System.out.println("❌ Error while navigating menu: " + e.getMessage());
        }
    }
}
