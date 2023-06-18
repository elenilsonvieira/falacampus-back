package br.edu.ifpb.dac.falacampus.seleniumTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class ConfigsTest {

    protected WebDriver driver;
    protected String url;

    @BeforeAll
    void setupConfig() throws Exception {
        driver = DataSingle.getDriver();
        driver.get(url);
        timeOut();
        driver.manage().window().maximize();
    }

    void timeOut() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    void deleteXpath(String element) {
        driver.findElement(By.xpath(element)).sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);

    }

    void clickButton(String element) {
        driver.findElement(By.xpath(element)).click();
    }

    void logarUser1() {
        driver.findElement(By.xpath(
                        "/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[1]/input"))
                .sendKeys(DataSingle.getRegistration());
        driver.findElement(By.xpath(
                        "/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[2]/input"))
                .sendKeys(DataSingle.getPassword());
        timeOut();
        clickButton("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/button");
    }

    void logarUser2() {
        driver.findElement(By.xpath(
                        "/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[1]/input"))
                .sendKeys(DataSingle.getRegistration2());
        driver.findElement(By.xpath(
                        "/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[2]/input"))
                .sendKeys(DataSingle.getPassword2());
        timeOut();
        clickButton("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/button");
    }

    void insert(String xPath, String text) {
        driver.findElement(By.xpath(xPath))
                .sendKeys(text);
    }

    void scroll() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    void scrollUp() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0)");
    }

    void home() {
        driver.findElement(By.xpath("//*[@id=\"answers\"]")).click();
    }
}
