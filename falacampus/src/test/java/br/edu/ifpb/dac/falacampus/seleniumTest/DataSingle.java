package br.edu.ifpb.dac.falacampus.seleniumTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DataSingle {
    private static DataSingle instance;
    private WebDriver driver;
    private static String nome = "";
    private static String registration= "";
    private static String password = "";
    private static String nome2 = "";
    private static String registration2= "";
    private static String password2 = "";


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
    public static String getRegistration2() {
        return registration2;
    }
    public static String getPassword2() {
        return password2;
    }
    public static String getNome2() {
        return nome2;
    }
}


