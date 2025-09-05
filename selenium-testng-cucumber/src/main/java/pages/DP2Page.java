package pages;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import locators.DP2PageRepo;
import org.openqa.selenium.WebElement;
import utils.CSVUtils;
import utils.JsonDataReader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DP2Page extends BasePage {



    public void navigateToDerivedProductPage(String url) {
      try {
          System.out.println("Navigating to URL: " + url);
          navigateTo(url);  // comes from BasePage
          waitForPageLoad();
      } catch (Exception e) {
          e.printStackTrace();
      }

    }

    public void scrollToFooterOfThePage() {
        try {
            scrollToElement(DP2PageRepo.footerSection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


        public void captureAllLinksInFooter(String filePath) {
          try{
              List<WebElement> links = findElements(DP2PageRepo.linksWithHref);

              String fileName = filePath + ".csv";

              List<List<String>> rows = new ArrayList<>();
                for(WebElement link : links){
                    String href = link.getAttribute("href");
                    String linkText = link.getText().replaceAll(",", " "); // Remove commas to avoid CSV issues
                    if(href != null && !href.isEmpty()){
                       rows.add(List.of(linkText, href));
                    }
                }
                CSVUtils.writeCSV(fileName, List.of("Link Text", "URL"), rows);
          }catch(Exception e){
                 e.printStackTrace();
          }
        }

        public void findDuplicateLinksInFooter(String filePath) {
          try{
              List<String> duplicates = CSVUtils.findDuplicatesInCSV(filePath+".csv");
              if(duplicates.isEmpty()){
                  System.out.println("No duplicate links found in the footer.");
              }else{

                  System.out.println("Duplicate links found in the footer:");
                  ExtentCucumberAdapter.getCurrentStep()
                          .log(Status.INFO,"Duplicate links found in the footer:");
                  for(String dup : duplicates){
                      System.out.println(dup);
                        ExtentCucumberAdapter.getCurrentStep()
                                .log(Status.INFO,dup);
                  }
              }
          }catch(Exception e){
              e.printStackTrace();
          }
        }


    }
