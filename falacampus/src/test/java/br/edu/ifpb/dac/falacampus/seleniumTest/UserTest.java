package br.edu.ifpb.dac.falacampus.seleniumTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest extends ConfigsTest{

    UserTest(){
        url = "http://localhost:3000/login";
    }

    @BeforeAll
    void before(){
        logarUser1();
    }

    @Test
    @Order(1)
    void textUser(){
        timeOut();
        driver.findElement(By.xpath("/html/body/div/nav/div/div/div/li[2]/a")).click();
        assertEquals("http://localhost:3000/viewUsers", driver.getCurrentUrl());
        timeOut();
        assertEquals(driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[1]/div/h3")).getText(), "Usuários");

    }


    @Test
    @Order(2)
    void userPresent(){
        timeOut();

        assertEquals(DataSingle.getNome(), driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[3]/div/div/table/tbody/tr[1]/td[2]")).getText());
        assertEquals(DataSingle.getRegistration(), driver.findElement(By.xpath("/html/body/div/div[1]/div/div/div[3]/div/div/table/tbody/tr[1]/td[4]")).getText());

    }



}
