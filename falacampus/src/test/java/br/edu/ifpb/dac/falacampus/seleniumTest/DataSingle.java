package br.edu.ifpb.dac.falacampus.seleniumTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DataSingle {
    private static DataSingle instance;
    private WebDriver driver;
    private static String registration= "202025020025";
    private static String password = "Eze2017";
    private static String nome = "Ezequias Soares de Oliveira";

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

