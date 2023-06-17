package br.edu.ifpb.dac.falacampus.seleniumTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CommentTest extends ConfigsTest {

    public CommentTest(){
        url = "http://localhost:3000/viewCommentsHome";
    }
    String input1 = "/html/body/div/div[1]/div/div/div[1]/div/div/form/fieldset/div/input";
    String button = "/html/body/div/div[1]/div/div/div[1]/div/div/form/fieldset/button";
    String data;
    String data2;
    @BeforeAll
    void up(){
        logar();
        timeOut();
        driver.findElement(By.xpath("//*[@id=\"comments\"]")).click();
        assertEquals("http://localhost:3000/viewComments",driver.getCurrentUrl());
    }
    @Test
    @Order(1)
    void findCommentTest(){
        assertEquals( driver.findElement(By.xpath(" //*[@id=\"root\"]/div[1]/div/div/div[3]/div/div/p"))
                .getText(),"Seus Comentarios");

         data = driver.findElement(
                        By.xpath("/html/body/div/div[1]/div/div/div[3]/div/div/table/tbody/tr/td[1]"))
                .getText();

        driver.findElement(
                By.xpath(input1)).sendKeys("l");

        driver.findElement(
                By.xpath(button)).click();

         data2 = driver.findElement(
                        By.xpath("/html/body/div/div[1]/div/div/div[3]/div/div/table/tbody/tr/td[1]"))
                .getText();

        assertEquals(data, data2);

        deleteXpath(input1);

    }

    @Test
    @Order(2)
    void findCommentNullTest(){
        driver.findElement(
                By.xpath(input1)).sendKeys("       ");

        driver.findElement(
                By.xpath(button)).click();

        data2 = driver.findElement(
                        By.xpath("/html/body/div/div[1]/div/div/div[3]/div/div/table/tbody/tr/td[1]"))
                .getText();

        assertEquals(data,data2);
    }

}
