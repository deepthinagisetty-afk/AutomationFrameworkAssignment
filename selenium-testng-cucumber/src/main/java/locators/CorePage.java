package locators;

import org.openqa.selenium.By;

public class CorePage {

    public static By  shopMenu= By.cssSelector("nav[aria-label='header-primary-menu']>ul> li[data-testid='nav-item-https://shop.warriors.com/']");
    public static By MensCategory = By.cssSelector("nav[aria-label='header-primary-menu']>ul> li[data-testid='nav-item-https://shop.warriors.com/'] a[title=\"Men's\"]");

    public static By dialogCloseButton = By.xpath("//div[@role='dialog']//div[text()='x']");
    public static By MensMenu=By.cssSelector("a[aria-label='Men']");
    public  static By MensJacketCategory=By.xpath("//a[@data-talos='topNavMenuItem' and contains(@href,'-men-jackets')]/div[text()='Jackets']");
    public static By pageCount=By.cssSelector("div.pagination-component ul[aria-label='Page Numbers']");
    public static By PaginationElements =By.cssSelector("div.pagination-component ul a[data-talos='linkSearchResultsPage']");
    public static By productCards =By.cssSelector("div.column");
    public static By NextPage = By.cssSelector("div.pagination-component a[aria-label='next page']");

    public static By  getLocatorOfJacketPrice(String index){
        return By.xpath("(//div[@class='column']//div[@class='prices'])["+index+"]");
    }

    public static By getLocatorOfProductTitle(String index){
        return By.xpath("(//div[@class='column']//div[@class='product-card-title'])["+index+"]");
    }

    public static By getLocatorOfTopSellerMessage(String index){
        return By.xpath("(//div[@class='column']//div[@class='product-vibrancy-container'])["+index+"]");
    }
}
