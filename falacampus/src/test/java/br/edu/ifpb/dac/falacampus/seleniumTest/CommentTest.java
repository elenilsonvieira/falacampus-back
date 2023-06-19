package br.edu.ifpb.dac.falacampus.seleniumTest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


import static org.junit.jupiter.api.Assertions.*;

public class CommentTest extends ConfigsTest {

    public CommentTest(){
        url = "http://localhost:3000/viewCommentsHome";
    }
    String input1;
    String button;
    String data;
    String data2;

    @BeforeAll
    void up(){
        timeOut();
        driver.findElement(By.xpath("//*[@id=\"comments\"]")).click();
        timeOut();
        assertEquals("http://localhost:3000/viewComments",driver.getCurrentUrl());

        input1 = "/html/body/div/div[1]/div/div/div[1]/div/div/form/fieldset/div/input";
        button = "/html/body/div/div[1]/div/div/div[1]/div/div/form/fieldset/button";
    }

    @Test
    @Order(1)
    void findCommentTest(){
        assertEquals( driver.findElement(By.xpath(" //*[@id=\"root\"]/div[1]/div/div/div[3]/div/div/p"))
                .getText(),"Seus Comentarios");


        driver.findElement(
                By.xpath(input1)).sendKeys("l");

        driver.findElement(
                By.xpath(button)).click();
        timeOut();

         data2 = driver.findElement(
                        By.xpath("/html/body/div/div[1]/div/div/div[3]/div/div/table/tbody/tr/td[1]"))
                .getText();
        WebElement e =  driver.findElement(
                By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[3]/div/div/table"));

        WebElement table = driver.findElement(By.xpath("/html/body/div/div[1]/div/div/div[3]/div/div/table"));
        assertFalse((table.findElement(By.tagName("tbody")).findElements(By.tagName("tr")).isEmpty()));

        deleteXpath(input1);

    }


    @Test
    @Order(2)
    void findCommentNullTest(){
        driver.findElement(
                By.xpath(input1)).sendKeys("       ");

        driver.findElement(
                By.xpath(button)).click();

        WebElement e =  driver.findElement(
                        By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[3]/div/div/table"));

        WebElement table = driver.findElement(By.xpath("/html/body/div/div[1]/div/div/div[3]/div/div/table"));

        assertTrue((table.findElement(By.tagName("tbody")).findElements(By.tagName("tr")).isEmpty()));
        deleteXpath(input1);
    }

    @Test
    @Order(3)
    void buttonEditTest(){
        scroll();
        data =  driver.findElement(
                By.xpath("/html/body/div/div[1]/div/div/div[3]/div/div/table/tbody/tr[1]")).getText();
        timeOut();

        clickButton("//*[@id=\"button_editar\"]");
        timeOut();

        assertTrue(driver.getCurrentUrl()
                .contains("http://localhost:3000/updateComment/"));
        timeOut();
        scrollUp();
        timeOut();
        clickButton("//*[@id=\"comments\"]");

        timeOut();
        assertEquals(data,
                driver.findElement(
                        By.xpath("/html/body/div/div[1]/div/div/div[3]/div/div/table/tbody/tr[1]")).getText());
    }

    @Test
    @Order(4)
    void buttonDeleteTest(){
        scroll();
        timeOut();
        data =  driver.findElement(
                By.xpath("/html/body/div/div[1]/div/div/div[3]/div/div/table/tbody/tr[1]")).getText();
        timeOut();
        scroll();
        clickButton("//*[@id=\"button_excluir\"]");
        timeOut();
        data2 =  driver.findElement(
                By.xpath("/html/body/div/div[1]/div/div/div[3]/div/div/table/tbody/tr[1]")).getText();
        timeOut();

        assertNotEquals(data,data2);

    }
    @Test
    @Order(5)
    void tableDepartamentTest(){
        scroll();
        try {

            Boolean ok = driver.findElement(
                    By.xpath("/html/body/div/div[1]/div/div/div[4]/div/div/p")).getText().equals(
                    "Comentarios Do Departamento");

            if (ok) {

                WebElement e = driver.findElement(
                        By.xpath("/html/body/div/div[1]/div/div/div[4]"));

                WebElement table = driver.findElement(By.xpath("/html/body/div/div[1]/div/div/div[4]/div/div/table"));
                assertFalse((table.findElement(By.tagName("tbody")).findElements(By.tagName("tr")).isEmpty()));

                buttonResponseTest();

            }
        }catch (Exception e){
           assertTrue(false);
        }

        timeOut();
    }

    void buttonResponseTest(){

        data =  driver.findElement(
                By.xpath("/html/body/div/div[1]/div/div/div[4]/div/div/table/tbody/tr/td[3]")).getText();
        timeOut();

        clickButton("//*[@id=\"butonAnswer\"]");
        timeOut();

        assertTrue(driver.getCurrentUrl().contains("http://localhost:3000/createAnswer/"));
        timeOut();

        clickButton("//*[@id=\"comments\"]");
        scroll();

        timeOut();
        data2 = driver.findElement(
                By.xpath("/html/body/div/div[1]/div/div/div[4]/div/div/table/tbody/tr/td[3]")).getText();

        assertEquals(data,data2);
    }


    @AfterAll
    void exit(){
        scrollUp();
        timeOut();
        home();
        clickButton("//*[@id=\"goOut\"]");
    }

}
