package br.edu.ifpb.dac.falacampus.seleniumTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListDepartmentTest extends ConfigsTest {

    ListDepartmentTest() {
        url = "http://localhost:3000/login";
    }

    @BeforeAll
    void before(){
         logarUser1();
         timeOut();
         clickButton("//*[@id=\"departments\"]");
     }

    @Test
    @Order(1)
    void filterNullDepartmentTest(){
        timeOut();
        clickButton("/html/body/div/div[1]/div/div/div[1]/div/div/form/fieldset/div/input");
        timeOut();
        insert("//*[@id=\"inputDepartamentName\"]", "     ");
        timeOut();
        clickButton("//*[@id=\"btn-search\"]");

        WebElement table = driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[3]/div/div/table"));
        assertTrue((table.findElement(By.tagName("tbody")).findElements(By.tagName("tr")).isEmpty()));

        timeOut();
        deleteXpath("/html/body/div/div[1]/div/div/div[1]/div/div/form/fieldset/div/input");

    }

    @Test
    @Order(2)
    void filterDepartmentTest(){
        clickButton("/html/body/div/div[1]/div/div/div[1]/div/div/form/fieldset/div/input");
        insert("//*[@id=\"inputDepartamentName\"]",
                "513 - Técnico em Instrumento Musical Subsequente - Monteiro (CAMPUS MONTEIRO)");
        timeOut();
        clickButton("//*[@id=\"btn-search\"]");

        assertEquals("513 - Técnico em Instrumento Musical Subsequente - Monteiro (CAMPUS MONTEIRO)",
                driver.findElement(
                        By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[3]/div/div/table/tbody/tr[1]/td[2]")).getText());

        timeOut();
        deleteXpath("/html/body/div/div[1]/div/div/div[1]/div/div/form/fieldset/div/input");
    }

    @Test
    @Order(3)
    void clickButtonEditTest(){
       timeOut();
       clickButton("/html/body/div/div[1]/div/div/div[3]/div/div/table/tbody/tr[1]/td[3]/button");
       timeOut();
       assertTrue(driver.getCurrentUrl().contains("http://localhost:3000/updateDepartament/"));
       timeOut();
       clickButton("//*[@id=\"departments\"]");
    }

    @Test
    @Order(4)
    void updateDepartmentTest() {
        timeOut();

        clickButton("/html/body/div/nav/div/div/div/li[1]/a");
        timeOut();

        clickButton("//*[@id=\"idListar\"]");
        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Atualizando Departamentos, Isso pode demorar um pouco!");
    }
}
