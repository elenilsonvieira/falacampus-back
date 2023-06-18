package br.edu.ifpb.dac.falacampus.seleniumTest;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CadastrarAnswerTest extends ConfigsTest {
    CadastrarAnswerTest() {
        url = "http://localhost:3000/viewComments";
    }



    @Test
    @Order(1)
    void cadastrarRespostaSucessTest(){

        driver.findElement(By.xpath("//*[@id=\"butonAnswer\"]/i")).click();
        timeOut();

        driver.findElement(By.xpath(
                        "/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[2]/textarea"))
                .sendKeys("Respondendo a pergunta, iremos analisar a situação e em seguida tomar as possiveis decisões para resolver o problema! Porque o erro?");
        timeOut();

        driver.findElement(By.xpath("//*[@id=\"button-answer\"]")).click();
        timeOut();

        assertEquals("http://localhost:3000/viewComments",driver.getCurrentUrl());
        assertEquals( driver.findElement(By.xpath(" //*[@id=\"commentDepartament\"]/div/div/table/tbody/tr[1]/td[6]"))
                .getText(),"SOLVED");
        timeOut();
    }
}
