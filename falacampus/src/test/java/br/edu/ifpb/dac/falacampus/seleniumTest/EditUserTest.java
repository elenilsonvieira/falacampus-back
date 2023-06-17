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
        logar();
        timeOut();
        driver.findElement(By.xpath("//*[@id=\"users\"]")).click();
        assertEquals("http://localhost:3000/viewUsers",driver.getCurrentUrl());
        viewEdit();
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
        driver.findElement(By.xpath("//*[@id=\"inputEmail\"]")).sendKeys("TestTest.com");

        scroll();
        timeOut();
        clickButton("//*[@id=\"button-save\"]");

        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Informe email valido");
        timeOut();
    }

    @Test
    @Order(4)
    void alterEmailValidTest(){

        deleteXpath("//*[@id=\"inputEmail\"]");
        driver.findElement(By.xpath("//*[@id=\"inputEmail\"]")).sendKeys("Test@Test.com");
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
    void removeAdminTest(){
        viewEdit();
        scroll();
        timeOut();

        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"selectRole\"]")));
        select.selectByValue("REMOVE");

        clickButton("//*[@id=\"button-save\"]");
        timeOut();
        assertEquals(driver.findElement(By.xpath(" /html/body/div[2]")).getText(), "×\nSucesso\n" +
                "Usuário atualizado com sucesso!");
    }

    @Test
    @Order(6)
    void removeAdminErrorTest(){
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
    @Order(6)
    void addAdminTest(){
        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"selectRole\"]")));
        select.selectByValue("ADMIN");
        timeOut();

        clickButton("//*[@id=\"button-save\"]");
        timeOut();
        assertEquals(driver.findElement(By.xpath(" /html/body/div[2]")).getText(), "×\nSucesso\n" +
                "Usuário atualizado com sucesso!");
    }

    @Test
    @Order(6)
    void cancelTest(){
        viewEdit();
        scroll();

        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"selectRole\"]")));
        select.selectByValue("REMOVE");
        timeOut();

        scroll();
        timeOut();
        clickButton("//*[@id=\"button-cancel\"]");


        assertEquals(driver.findElement(
                By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[3]/div/div/table/tbody/tr[1]/td[5]"))
                .getText(), "ADMIN");
    }

    @AfterAll
    void exit(){
       home();
    }

}
