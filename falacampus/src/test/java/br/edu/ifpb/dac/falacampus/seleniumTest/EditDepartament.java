package br.edu.ifpb.dac.falacampus.seleniumTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditDepartament extends ConfigsTest {
    EditDepartament() {
        url = "http://localhost:3000/viewDepartaments";
    }

    @BeforeAll
    void before(){
        timeOut();
    }


    @Test
    @Order(1)
    void addResponseTest() {
        timeOut();
        scrollUp();
        clickButton("//*[@id=\"button_editar\"]");
        timeOut();

        scroll();
        timeOut();
        WebElement inputElement = driver.findElement(By.id("react-select-5-input"));
        inputElement.sendKeys(DataSingle.getNome());
        timeOut();
        inputElement.sendKeys(Keys.ENTER);

        timeOut();
        clickButton("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[3]/div/button");
        timeOut();
        assertEquals("Reponsável adicionado",driver.findElement(By.className("toast-message")).getText());
        timeOut();

        clickButton("//*[@id=\"button-update\"]");
    }

    @Test
    @Order(2)
    void removeResponseTest() {
        scrollUp();
        timeOut();
        clickButton("//*[@id=\"button_editar\"]");
        timeOut();

        scroll();
        timeOut();
        WebElement inputElement = driver.findElement(By.id("react-select-13-input"));
        inputElement.sendKeys(DataSingle.getNome2());
        timeOut();
        inputElement.sendKeys(Keys.ENTER);

        timeOut();
        clickButton("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[3]/div/button");
        timeOut();
        assertEquals("Reponsável adicionado",driver.findElement(By.className("toast-message")).getText());
        timeOut();

        inputElement = driver.findElement(By.id("react-select-15-input"));
        inputElement.sendKeys(DataSingle.getNome2());
        timeOut();
        inputElement.sendKeys(Keys.ENTER);
        timeOut();
        clickButton("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[2]/button");
        timeOut();
        assertEquals("Responsável removido",driver.findElement(By.className("toast-message")).getText());
        timeOut();

        clickButton("//*[@id=\"button-update\"]");
    }

}
