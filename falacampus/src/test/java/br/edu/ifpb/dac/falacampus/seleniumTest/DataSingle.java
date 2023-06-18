package br.edu.ifpb.dac.falacampus.seleniumTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DataSingle {
    private static DataSingle instance;
    private WebDriver driver;
    private static String registration= "201715020037";
    private static String password = "@senha0011";
    private static String nome = "Gabriel Oliveira Florencio da Silva";

    private DataSingle() {
        System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
        driver = new ChromeDriver();
    }
    public static WebDriver getDriver() {
        if (instance == null) {
            instance = new DataSingle();
        }
        return instance.driver;
    }

    public static String getRegistration() {
        return registration;
    }
    public static String getPassword() {
        return password;
    }
    public static String getNome() {
        return nome;
    }
}


