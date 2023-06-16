package br.edu.ifpb.dac.falacampus.seleniumTest;
import ch.qos.logback.core.net.SyslogOutputStream;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.timeout;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest extends FalaCampusWebTest{
    LoginTest(){
        url = "http://localhost:3000/login";
    }
    String input1 = "/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[1]/input";
    String input2 = "/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[2]/input";
    String button = "/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/button";


    void insert(String m, String s){
        deleteXpath(input1);
        deleteXpath(input2);
        driver.findElement(By.xpath(input1)).sendKeys(m);
        driver.findElement(By.xpath(input2)).sendKeys(s);
    }

    @Test
    @Order(1)
    void loginSucessTest(){
        logar(DataSingle.getRegistration(),DataSingle.getPassword());
        timeOut();

        assertEquals("http://localhost:3000/viewCommentsHome",driver.getCurrentUrl());
        assertEquals(driver.findElement(By.xpath(" /html/body/div[2]")).getText(), "×\nSucesso\n" +
                DataSingle.getNome()+", você está logado!");
        timeOut();
        timeOut();

        driver.findElement(By.xpath("//*[@id=\"goOut\"]")).click();
    }

    @Test
    @Order(2)
    void emptyRegistrationTest(){
        insert("","728582423");
        clickButton(button);
        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Campo matricula é obrigatorio");
        timeOut();
    }

    @Test
    @Order(3)
    void emptyPasswordTest(){
        insert("3892823423523","");
        clickButton(button);
        timeOut();
        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Campo senha é obrigatorio");
        timeOut();
    }

    @Test
    @Order(4)
    void invalidPasswordTest() {
        insert(DataSingle.getRegistration(),"regergrferf");
        clickButton(button);
        timeOut();
        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Usuário e/ou senha incorreto(s)");
        timeOut();
        timeOut();
    }

    @Disabled
    @Test
    @Order(5)
    void unavailableServerTest(){
        insert(DataSingle.getRegistration(),DataSingle.getPassword());
        clickButton(button);
        timeOut();
        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Servidor Indisponivel");
    }

    @Test
    @Order(6)
    void attemptLimitTest() throws InterruptedException {
        insert(DataSingle.getRegistration(),"regergrferf");
        for(int i = 0; i < 6; i++){
            clickButton(button);
            Thread.sleep(500);
        }
        timeOut();
        assertTrue(driver.findElement(By.xpath(" /html/body/div[2]")).getText().contains("Pedido foi limitado"));
    }
}
