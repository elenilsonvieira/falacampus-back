package br.edu.ifpb.dac.falacampus.seleniumTest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterCommentTest extends ConfigsTest{

    RegisterCommentTest(){
        url = "http://localhost:3000/login";
    }

    @BeforeAll
    void before(){
        timeOut();
        logarUser2();
        timeOut();
        clickButton("/html/body/div/nav/div/div/div/li[3]/a");
        timeOut();
        clickButton("//*[@id=\"cadastrar_comentario\"]");
    }

    @Test
    @Order(1)
    void nullTitleErrorTest(){
        scroll();
        timeOut();
        driver.findElement(By.xpath("//*[@id=\"button_salvar\"]")).click();
        timeOut();
        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Titulo Incorreto!");

    }

    @Test
    @Order(2)
    void minTitleErrorTest() {
        insert("//*[@id=\"inputCommentTitle\"]","Min");
        scroll();
        timeOut();
        clickButton("//*[@id=\"button_salvar\"]");
        timeOut();

        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Titulo Incorreto!");
    }

    @Test
    @Order(3)
    void titleTest(){
        deleteXpath("//*[@id=\"inputCommentTitle\"]");
        timeOut();
        insert("//*[@id=\"inputCommentTitle\"]","Limpeza");
        timeOut();
    }

    @Test
    @Order(4)
    void nullCommentErrorTest(){
        insert("//*[@id=\"MessageTextarea\"]", "");
        timeOut();
        scroll();
        timeOut();
        clickButton("//*[@id=\"button_salvar\"]");
        timeOut();
        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Comentário incorreto!");
    }

    @Test
    @Order(5)
    void minCommentErrorTest() {

        insert("//*[@id=\"MessageTextarea\"]", "Min");
        scroll();
        timeOut();

        clickButton("//*[@id=\"button_salvar\"]");
        timeOut();
        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Comentário incorreto!");
        timeOut();
    }

    @Test
    @Order(6)
    void CommentTest() {
        timeOut();
        deleteXpath("//*[@id=\"MessageTextarea\"]");
        timeOut();
        insert("//*[@id=\"MessageTextarea\"]", "Limpeza do lab4");
    }



    @Test
    @Order(7)
    void typeCommentErrorTest() {
        scroll();
        timeOut();

        clickButton("//*[@id=\"button_salvar\"]");
        timeOut();
        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Tipo de comentario incorreto");

        timeOut();

    }
    @Test
    @Order(8)
    void typeCommentTest(){
        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"selectCommentType\"]")));
        select.selectByValue("REVIEW");
        timeOut();

    }
    @Test
    @Order(9)
    void nullDepartamentErrorTest()  {
        clickButton("//*[@id=\"button_salvar\"]");
        timeOut();
        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Departamento incorreto!");
        timeOut();
    }

    @Test
    @Order(10)
    void creatCommentSucess(){
        insert("//*[@id=\"input\"]","502 - Tecnologia em Análise e Desenvolvimento de Sistemas - Monteiro (CAMPUS MONTEIRO)");
        timeOut();
        clickButton("//*[@id=\"button_salvar\"]");
        timeOut();
        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Comentário criado com sucesso!");

    }
}
