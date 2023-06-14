package br.edu.ifpb.dac.falacampus.seleniumTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

 class HomeTest extends FalaCampusWebTest{

    @Test
    @DisplayName("TitleTest")
    void homeTitleTest() throws InterruptedException {
        assertTrue(driver.getTitle().contentEquals("Fala Campus"));
    }

    @Test
    @DisplayName("TextHomeTest ")
    void homeTextTest() throws InterruptedException {
        assertEquals("Este espaço é destinado a toda a comunidade acadêmica, " +
                        "onde todos poderão propor sugestões, realizar críticas e elogios," +
                        " relacionados ao Instituto Federal da Paraíba, Campus Monteiro - PB."
                ,driver.findElement(By.xpath("/html/body/div/div[1]/div/div[1]/p[1]")).getText());
    }

    @Test
    @DisplayName("ButtonTest")
    void loginButtonTest() throws InterruptedException {
       driver.findElement(By.xpath("/html/body/div/nav/div/div/div/li/a")).click();
       assertEquals("http://localhost:3000/login",driver.getCurrentUrl());
    }
}
