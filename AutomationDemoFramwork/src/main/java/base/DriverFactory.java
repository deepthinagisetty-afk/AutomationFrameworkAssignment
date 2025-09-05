package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    public static WebDriver createDriver(String browser) {
        if ("chrome".equalsIgnoreCase(browser)) return new ChromeDriver();
        if ("firefox".equalsIgnoreCase(browser)) return new FirefoxDriver();
        if("edge".equalsIgnoreCase(browser)) return new EdgeDriver();
        throw new IllegalArgumentException("Unsupported browser: " + browser);
    }
}
