package br.edu.ifpb.dac.falacampus.seleniumTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseTest extends ConfigsTest{
    @BeforeAll
    void up() {
        home();
    }

    @Test
    @Order(1)
    void findAuthorAnswer() {
        clickButton("//*[@id=\"answers\"]");
        timeOut();

        insert("/html/body/div/div[1]/div/div[1]/div/div/form/fieldset/div[1]/input", DataSingle.getNome2());
        timeOut();

        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div[1]/div/div/form/fieldset/div[2]/select")));
        select.selectByValue("author");
        timeOut();

        clickButton("//*[@id=\"btn-search\"]");
        timeOut();

        assertEquals(DataSingle.getNome2(), driver.findElement(By.xpath("//*[@id=\"teste\"]/div[1]/div[2]/p[1]")).getText());
        timeOut();
    }

    @Test
    @Order(2)
    void findCommentAnswer() {
        deleteXpath("/html/body/div/div[1]/div/div[1]/div/div/form/fieldset/div[1]/input");
        timeOut();

        insert("/html/body/div/div[1]/div/div[1]/div/div/form/fieldset/div[1]/input", "213231");
        timeOut();

        Select select = new Select(driver.findElement(By.xpath("/html/body/div/div[1]/div/div[1]/div/div/form/fieldset/div[2]/select")));
        select.selectByValue("comment");
        timeOut();

        clickButton("//*[@id=\"btn-search\"]");
        timeOut();

        assertEquals("213231", driver.findElement(By.xpath("//*[@id=\"teste\"]/div/div[2]/h4")).getText());
        timeOut();
    }

    @Test
    @Order(3)
    void findTitleAnswer() {
        deleteXpath("/html/body/div/div[1]/div/div[1]/div/div/form/fieldset/div[1]/input");
        timeOut();

        insert("/html/body/div/div[1]/div/div[1]/div/div/form/fieldset/div[1]/input", "Instalar protetores para a torneira");
        timeOut();

        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div[1]/div/div/form/fieldset/div[2]/select")));
        select.selectByValue("title");
        timeOut();

        clickButton("//*[@id=\"btn-search\"]");
        timeOut();

        assertEquals("Instalar protetores para a torneira", driver.findElement(By.xpath("//*[@id=\"teste\"]/div[1]/div[1]")).getText());
        timeOut();
    }

}
