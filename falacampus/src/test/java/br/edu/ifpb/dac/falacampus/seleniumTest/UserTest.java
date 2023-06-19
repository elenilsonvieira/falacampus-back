package br.edu.ifpb.dac.falacampus.seleniumTest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest extends ConfigsTest{

    UserTest(){
        url = "http://localhost:3000/login";
    }

    @BeforeAll
    void before(){
        timeOut();
        clickButton("//*[@id=\"users\"]");
        assertEquals("http://localhost:3000/viewUsers",driver.getCurrentUrl());
    }

    @Test
    @Order(1)
    void nullFindUser(){
        timeOut();
        insert("//*[@id=\"inputUserName\"]", "    ");
        timeOut();
        clickButton("//*[@id=\"idPesquisar\"]");
        timeOut();

        WebElement table = driver.findElement(By.xpath("/html/body/div/div[1]/div/div/div[3]/div/div/table"));
        assertTrue((table.findElement(By.tagName("tbody")).findElements(By.tagName("tr")).isEmpty()));

        deleteXpath("//*[@id=\"inputUserName\"]");
    }

    @Test
    @Order(2)
    void findUser(){
        timeOut();
        clickButton("/html/body/div/div[1]/div/div/div[1]/div/div/form/fieldset/div/input");
        timeOut();
        insert("//*[@id=\"inputUserName\"]", DataSingle.getNome2());
        timeOut();
        clickButton("//*[@id=\"idPesquisar\"]");
        timeOut();
        assertEquals(DataSingle.getNome2(), driver.findElement(By.xpath(" //*[@id=\"root\"]/div[1]/div/div/div[3]/div/div/table/tbody/tr[1]/td[2]")).getText());
        timeOut();
        deleteXpath("//*[@id=\"inputUserName\"]");
        timeOut();
    }


}
