package br.edu.ifpb.dac.falacampus.seleniumTest;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class EditUserTest extends ConfigsTest {
    EditUserTest(){
        url = "http://localhost:3000/viewCommentsHome";
    }

    @BeforeAll
    void up(){
        timeOut();
        viewEdit();
        timeOut();
    }

    void viewEdit(){
        clickButton("//*[@id=\"buttonEdit\"]");
    }

    @Test
    @Order(1)
    void isViewEditUserTest(){
        assertEquals("http://localhost:3000/updateUser/1",driver.getCurrentUrl());
    }

    @Test
    @Order(2)
    void isUser(){
        assertEquals(DataSingle.getRegistration()
                ,driver.findElement(By.id("inputRegistration")).getAttribute("value"));

        assertEquals(DataSingle.getNome()
                ,driver.findElement(By.id("inputUserName")).getAttribute("value"));
    }

    @Test
    @Order(3)
    void alterEmailInvalidTest(){
        deleteXpath("//*[@id=\"inputEmail\"]");
        insert("//*[@id=\"inputEmail\"]","TestTest.com");

        scroll();
        timeOut();

        clickButton("//*[@id=\"button-save\"]");
        timeOut();
        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Informe email valido");

    }

    @Test
    @Order(4)
    void alterEmailValidTest(){

        deleteXpath("//*[@id=\"inputEmail\"]");
        insert("//*[@id=\"inputEmail\"]","Test@Test.com");
        timeOut();

        clickButton("//*[@id=\"button-save\"]");
        timeOut();

        assertEquals(driver.findElement(By.xpath(" /html/body/div[2]")).getText(), "×\nSucesso\n" +
                "Usuário atualizado com sucesso!");

        assertEquals("http://localhost:3000/viewUsers",driver.getCurrentUrl());
        timeOut();
    }

    @Test
    @Order(5)
    void removeSingleAdminErroTest(){
        viewEdit();
        timeOut();
        scroll();
        timeOut();

        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"selectRole\"]")));
        select.selectByValue("REMOVE");

        clickButton("//*[@id=\"button-save\"]");
        timeOut();

        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Não pode ser alterado, só existe um ADMIN!");
        timeOut();

        scrollUp();
        timeOut();
        driver.findElement(By.xpath("/html/body/div/nav/div/div/div/li[2]/a")).click();

    }
    @Test
    @Order(6)
    void addAdminTest(){
        timeOut();
        clickButton("/html/body/div/div[1]/div/div/div[3]/div/div/table/tbody/tr[2]/td[6]/button");
        scroll();
        timeOut();

        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"selectRole\"]")));
        select.selectByValue("ADMIN");
        timeOut();

        clickButton("//*[@id=\"button-save\"]");
        timeOut();
        assertEquals(driver.findElement(By.xpath(" /html/body/div[2]")).getText(), "×\nSucesso\n" +
                "Usuário atualizado com sucesso!");
    }

    @Test
    @Order(7)
    void removeAdminTest(){
        timeOut();
        viewEdit();

        timeOut();
        scroll();

        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"selectRole\"]")));
        select.selectByValue("REMOVE");
        scroll();
        timeOut();
        clickButton("//*[@id=\"button-save\"]");
        timeOut();
        assertEquals(driver.findElement(By.xpath(" /html/body/div[2]")).getText(), "×\nSucesso\n" +
                "Usuário atualizado com sucesso!");
    }

    @Test
    @Order(8)
    void removeAdminErrorTest(){
        timeOut();
        viewEdit();
        scroll();
        timeOut();

        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"selectRole\"]")));
        select.selectByValue("REMOVE");
        clickButton("//*[@id=\"button-save\"]");
        timeOut();
        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Usuario não é ADMIN!");

    }

    @Test
    @Order(9)
    void cancelTest(){
        timeOut();
        clickButton("//*[@id=\"button-cancel\"]");
        timeOut();
        assertEquals(driver.findElement(
                By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[3]/div/div/table/tbody/tr[1]/td[5]"))
                .getText(), "STUDENTS");

        timeOut();
    }

    @Test
    @Order(10)
    void createComments(){
        clickButton("//*[@id=\"comments\"]");
        timeOut();
        clickButton("//*[@id=\"cadastrar_comentario\"]");
        timeOut();

        scroll();
        timeOut();

        insert("//*[@id=\"inputCommentTitle\"]","Internet Ruim");
        timeOut();

        insert("//*[@id=\"MessageTextarea\"]", "internet do lab4 nao esta funcionando");
        timeOut();

        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"selectCommentType\"]")));
        select.selectByValue("REVIEW");
        timeOut();

        insert("//*[@id=\"input\"]","502 - Tecnologia em Análise e Desenvolvimento de Sistemas - Monteiro (CAMPUS MONTEIRO)");
        timeOut();
        scroll();
        timeOut();
        clickButton("//*[@id=\"button_salvar\"]");

        timeOut();

        //Comment 2
        scrollUp();
        timeOut();
        clickButton("//*[@id=\"cadastrar_comentario\"]");

        timeOut();

        scroll();
        timeOut();

        insert("//*[@id=\"inputCommentTitle\"]","Limpeza lab5");
        timeOut();

        insert("//*[@id=\"MessageTextarea\"]", "laboratorio esta sujo");
        timeOut();

        select = new Select(driver.findElement(By.xpath("//*[@id=\"selectCommentType\"]")));
        select.selectByValue("REVIEW");
        timeOut();

        insert("//*[@id=\"input\"]","502 - Tecnologia em Análise e Desenvolvimento de Sistemas - Monteiro (CAMPUS MONTEIRO)");
        timeOut();
        scroll();
        timeOut();
        clickButton("//*[@id=\"button_salvar\"]");
        timeOut();

    }

    @Test
    @Order(11)
    void exit(){
        scrollUp();
        timeOut();
        home();
    }

}
