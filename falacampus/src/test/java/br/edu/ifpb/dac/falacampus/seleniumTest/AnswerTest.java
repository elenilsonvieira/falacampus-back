package br.edu.ifpb.dac.falacampus.seleniumTest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnswerTest extends ConfigsTest {
    AnswerTest() {
        url = "http://localhost:3000/viewComments";
    }

    @BeforeAll
    void up() {
        logarUser1();
        timeOut();
        clickButton("//*[@id=\"comments\"]");
        timeOut();
        assertEquals("http://localhost:3000/viewComments", driver.getCurrentUrl());
    }

    @Test
    @Order(5)
    void answerValidSuccessTest() {
        clickButton("//*[@id=\"butonAnswer\"]/i");
        timeOut();

        insert("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[2]/textarea", "Texto com 23 caracteres");
        timeOut();

        clickButton("//*[@id=\"button-answer\"]");
        timeOut();

        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Comentário respondido!");
        assertEquals("http://localhost:3000/viewComments", driver.getCurrentUrl());
        timeOut();
    }

    @Test
    @Order(1)
    void answerNullTest() {
        clickButton("//*[@id=\"butonAnswer\"]/i");
        timeOut();

        clickButton("//*[@id=\"button-answer\"]");
        timeOut();

        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Campo Mensagem é obrigatório!");
        timeOut();
    }

    @Test
    @Order(4)
    void answerCanceledTest() {
        deleteXpath("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[2]/textarea");
        timeOut();

        insert("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[2]/textarea", "Cancelando a Resposta!");
        timeOut();

        clickButton("//*[@id=\"button-cancel\"]");
        timeOut();

        assertEquals("http://localhost:3000/viewComments", driver.getCurrentUrl());
        timeOut();
    }

    @Test
    @Order(2)
    void invalidAnswerMinimumCharactersTest() {
        insert("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[2]/textarea", "texto c 9");
        timeOut();

        clickButton("//*[@id=\"button-answer\"]");
        timeOut();

        assertEquals(driver.findElement(By.className("toast-message")).getText(), "A Mensagem da Resposta deve ter no mínimo 10 e no máximo 255 caracteres!");
        timeOut();
    }

    @Test
    @Order(3)
    void invalidAnswerMaximumCharactersTest() {
        deleteXpath("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[2]/textarea");
        timeOut();

        insert("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[2]/textarea", "Texto com 256 caracteres, Em amistoso na Espanha pautado pelo combate ao racismo, o Brasil aplicou uma goleada sobre Guiné, por 4 a 1, neste sábado. Joelinton, Rodrygo, Éder Militão e Vinicius Junior marcaram para a Seleção, que conseguiu a primeira vitóri");
        timeOut();

        clickButton("//*[@id=\"button-answer\"]");
        timeOut();

        assertEquals(driver.findElement(By.className("toast-message")).getText(), "A Mensagem da Resposta deve ter no mínimo 10 e no máximo 255 caracteres!");
        timeOut();
    }

    @Test
    @Order(6)
    void answerReturnedTest() {
        clickButton("//*[@id=\"butonAnswer\"]/i");
        timeOut();

        insert("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[2]/textarea", "Retornando o Comentario!");
        clickButton("//*[@id=\"button-returned\"]");
        timeOut();

        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Comentário retornado!");
        assertEquals("http://localhost:3000/viewComments", driver.getCurrentUrl());
        timeOut();
    }

    @Test
    @Order(7)
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
    @Order(8)
    void findCommentAnswer() {
        deleteXpath("/html/body/div/div[1]/div/div[1]/div/div/form/fieldset/div[1]/input");
        timeOut();

        insert("/html/body/div/div[1]/div/div[1]/div/div/form/fieldset/div[1]/input", "213231");
        timeOut();

        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div[1]/div/div/form/fieldset/div[2]/select")));
        select.selectByValue("comment");
        timeOut();

        clickButton("//*[@id=\"btn-search\"]");
        timeOut();

        assertEquals("213231", driver.findElement(By.xpath("//*[@id=\"teste\"]/div/div[2]/h4")).getText());
        timeOut();
    }

    @Test
    @Order(9)
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

    @AfterAll
    void afterAll() {
        home();
    }
}