package br.edu.ifpb.dac.falacampus.seleniumTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverSingle {
    private static DriverSingle instance;
    private WebDriver driver;

    private DriverSingle() {
        System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    public static WebDriver getDriver() {
        if (instance == null) {
            instance = new DriverSingle();
        }
        return instance.driver;
    }
}


