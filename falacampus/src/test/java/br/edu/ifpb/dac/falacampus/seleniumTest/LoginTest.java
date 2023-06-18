package br.edu.ifpb.dac.falacampus.seleniumTest;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends ConfigsTest {
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
        timeOut();
        logarUser1();
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
        timeOut();
        insert(DataSingle.getRegistration(),"regergrferf");
        clickButton(button);
        timeOut();
        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Usuário e/ou senha incorreto(s)");
        timeOut();
        timeOut();
    }
    @Test
    @Order(5)
    void invalidRegistrationTest(){
        timeOut();
        insert("6343545235424","regergrferf");
        clickButton(button);
        timeOut();
        timeOut();
        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Número de Matrícula Incorreto");
        timeOut();
        timeOut();
    }



    @Test
    @Order(6)
    void attemptLimitTest() throws InterruptedException {
        timeOut();
        insert(DataSingle.getRegistration(),"regergrferf");
        timeOut();
        for(int i = 0; i < 6; i++){
            clickButton(button);
            Thread.sleep(500);
        }
        timeOut();
        assertTrue(driver.findElement(By.xpath(" /html/body/div[2]")).getText().contains("Pedido foi limitado"));

        timeOut();

    }
    @Disabled
    @Test
    @Order(7)
    void unavailableServerTest(){
        insert(DataSingle.getRegistration(),DataSingle.getPassword());
        clickButton(button);
        timeOut();
        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Servidor Indisponivel");
    }

    void exit(){
        driver.quit();
    }

}
