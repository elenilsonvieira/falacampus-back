package br.edu.ifpb.dac.falacampus.seleniumTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListDepartmentTest extends ConfigsTest {

    ListDepartmentTest() {
        url = "http://localhost:3000/login";
    }

     @BeforeAll
    void before(){
        logarUser1();
     }

     @Test
     @Order(1)
     void AtualizandoDepartment() throws InterruptedException {
         Thread.sleep(2000);

         clickButton("/html/body/div/nav/div/div/div/li[1]/a");
         Thread.sleep(1000);

         clickButton("//*[@id=\"idListar\"]");
        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Atualizando Departamentos, Isso pode demorar um pouco!");
     }

     @Test
     @Order(2)
     void departmant(){
         assertEquals(driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[3]/div/div/table/tbody/tr[3]/td[2]")).getText(), "COMISSAO ELEITORAL DO CONSELHO DIRETOR - CAMPUS MONTEIRO");
     }

     @Test
     @Order(3)
     void filterDepartment(){
        clickButton("/html/body/div/div[1]/div/div/div[1]/div/div/form/fieldset/div/input");
        driver.findElement(By.xpath("//*[@id=\"inputDepartamentName\"]")).sendKeys("COORDENAÇÃO DE ASSISTÊNCIA ESTUDANTIL - CAMPUS MONTEIRO");
        clickButton("//*[@id=\"btn-search\"]");

    }



}
