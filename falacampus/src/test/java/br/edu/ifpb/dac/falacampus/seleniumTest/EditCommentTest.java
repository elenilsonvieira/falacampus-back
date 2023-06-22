package br.edu.ifpb.dac.falacampus.seleniumTest;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditCommentTest extends ConfigsTest {
    public EditCommentTest() {
        url = "http://localhost:3000/viewCommentsHome";
    }

    String title;
    String message;

    @BeforeAll
    void up() {

        timeOut();
        driver.findElement(By.xpath("//*[@id=\"comments\"]")).click();
        timeOut();

        assertEquals("http://localhost:3000/viewComments", driver.getCurrentUrl());

        scroll();
        timeOut();
        clickButton("//*[@id=\"button_editar\"]/i");
        timeOut();

        WebElement elemento1 = driver.findElement(By.xpath("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[1]/input"));
        title = elemento1.getAttribute("value");
        WebElement elemento2 = driver.findElement(By.xpath("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[2]/textarea"));
        message = elemento2.getAttribute("value");

    }
    @Test
    @Order(1)
    void editNullTitleCommentTest() {
        deleteXpath("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[1]/input");
        timeOut();

        clickButton("//*[@id=\"button-update\"]");
        timeOut();

        assertEquals(driver.findElement(By.className("toast-message")).getText(), "O comentário não pode ser atualizado!");
        timeOut();

        insert("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[1]/input", title);
        timeOut();
    }
    @Test
    @Order(2)
    void editTitleInvalidMinCaracteresTest() {
        timeOut();
        deleteXpath("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[1]/input");
        insert("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[1]/input", "Ovo");
        timeOut();

        clickButton("//*[@id=\"button-update\"]");
        timeOut();

        assertEquals(driver.findElement(By.className("toast-message")).getText(), "O comentário não pode ser atualizado!");
        timeOut();

        deleteXpath("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[1]/input");
        insert("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[1]/input", title);
        timeOut();
    }
    @Test
    @Order(3)
    void editTitleInvalidMaxCaracteresTest() {
        timeOut();
        deleteXpath("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[1]/input");
        insert("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[1]/input", "Texto com mais de 50 caracteres para o teste maximo!");
        timeOut();

        clickButton("//*[@id=\"button-update\"]");
        timeOut();

        assertEquals(driver.findElement(By.className("toast-message")).getText(), "O comentário não pode ser atualizado!");
        timeOut();

        deleteXpath("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[1]/input");
        insert("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[1]/input", title);
        timeOut();
    }

    @Test
    @Order(4)
    void editNullMessageCommentTest() {
        deleteXpath("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[2]/textarea");
        timeOut();

        clickButton("//*[@id=\"button-update\"]");
        timeOut();

        assertEquals(driver.findElement(By.className("toast-message")).getText(), "O comentário não pode ser atualizado!");
        timeOut();

        insert("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[2]/textarea", message);
        timeOut();
    }

    @Test
    @Order(5)
    void editMessageInvalidMinCaracteresTest() {
        timeOut();
        deleteXpath("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[2]/textarea");
        insert("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[2]/textarea", "Nos");
        timeOut();

        clickButton("//*[@id=\"button-update\"]");
        timeOut();

        assertEquals(driver.findElement(By.className("toast-message")).getText(), "O comentário não pode ser atualizado!");
        timeOut();

        deleteXpath("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[2]/textarea");
        insert("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[2]/textarea", message);
        timeOut();
    }

    @Test
    @Order(6)
    void editInvalidTypeCommentTest() {
        Select select = new Select(driver.findElement(By.xpath("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[3]/select")));
        select.selectByIndex(0);
        timeOut();

        clickButton("//*[@id=\"button-update\"]");
        timeOut();

        assertEquals(driver.findElement(By.className("toast-message")).getText(), "O comentário não pode ser atualizado!");
        timeOut();
    }

    @Disabled
    @Test
    @Order(7)
    void editCommentSucessTest() {
        deleteXpath("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[1]/input");
        insert("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[1]/input", "Editando Comentario!");
        timeOut();

        deleteXpath("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[2]/textarea");
        insert("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[2]/textarea", "Teste para editar o comentario!");
        timeOut();

        Select select = new Select(driver.findElement(By.xpath("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[3]/select")));
        select.selectByValue("COMPLIMENT");
        timeOut();

        clickButton("//*[@id=\"button-update\"]");
        timeOut();

        assertEquals("Comentário atualizado com sucesso!",driver.findElement(By.className("toast-message")).getText());
        assertEquals("http://localhost:3000/viewComments", driver.getCurrentUrl());
        timeOut();
    }

    @AfterAll
    void exit() {
        scrollUp();
        timeOut();
        home();
        clickButton("//*[@id=\"goOut\"]");
    }
}