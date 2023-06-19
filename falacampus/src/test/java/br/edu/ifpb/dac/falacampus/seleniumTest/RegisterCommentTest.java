package br.edu.ifpb.dac.falacampus.seleniumTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterCommentTest extends ConfigsTest{

    RegisterCommentTest(){
        url = "http://localhost:3000/login";
    }

    @BeforeAll
    void before(){
        logarUser1();
    }

    @Test
    @Order(1)
    void CommentErradoTitulo() throws InterruptedException {

        Thread.sleep(2000);
        clickButton("/html/body/div/nav/div/div/div/li[3]/a");
        clickButton("//*[@id=\"cadastrar_comentario\"]");

        driver.findElement(By.xpath("//*[@id=\"inputCommentTitle\"]")).sendKeys("Min");
        driver.findElement(By.xpath("//*[@id=\"MessageTextarea\"]")).sendKeys("Suficiente");

        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"selectCommentType\"]")));
        select.selectByValue("REVIEW");

        driver.findElement(By.xpath("//*[@id=\"input\"]")).sendKeys("Protocolo Virtual do Campus Monteiro");

        scroll();
        Thread.sleep(2000);


        driver.findElement(By.xpath("//*[@id=\"button_salvar\"]")).click();
        scrollUp();
        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Titulo Incorreto!");

        scroll();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"button_cancelar\"]")).click();




    }

    @Test
    @Order(2)
    void CommentErradoMensagem() throws InterruptedException {
        scrollUp();
        Thread.sleep(2000);

        clickButton("//*[@id=\"cadastrar_comentario\"]");

        driver.findElement(By.xpath("//*[@id=\"inputCommentTitle\"]")).sendKeys("Suficiente");
        driver.findElement(By.xpath("//*[@id=\"MessageTextarea\"]")).sendKeys("Min");


        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"selectCommentType\"]")));
        select.selectByValue("REVIEW");



        driver.findElement(By.xpath("//*[@id=\"input\"]")).sendKeys("Protocolo Virtual do Campus Monteiro");

        scroll();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"button_salvar\"]")).click();
        scrollUp();
        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Comentário incorreto!");

        scroll();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"button_cancelar\"]")).click();




    }

    @Test
    @Order(3)
    void CommentErradoTipo() throws InterruptedException {

        scrollUp();
        Thread.sleep(2000);

        clickButton("//*[@id=\"cadastrar_comentario\"]");
        driver.findElement(By.xpath("//*[@id=\"inputCommentTitle\"]")).sendKeys("Suficiente");
        driver.findElement(By.xpath("//*[@id=\"MessageTextarea\"]")).sendKeys("Suficiente");
        driver.findElement(By.xpath("//*[@id=\"input\"]")).sendKeys("Protocolo Virtual do Campus Monteiro");

        scroll();
        Thread.sleep(2000);


        clickButton("//*[@id=\"button_salvar\"]");
        scrollUp();
        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Tipo de comentario incorreto!");

        scroll();
        timeOut();

        clickButton("//*[@id=\"button_cancelar\"]");

    }

    @Test
    @Order(4)
    void CommentErradoDepartament(){
        scrollUp();
        timeOut();

        clickButton("//*[@id=\"cadastrar_comentario\"]");
        driver.findElement(By.xpath("//*[@id=\"inputCommentTitle\"]")).sendKeys("Suficiente");
        driver.findElement(By.xpath("//*[@id=\"MessageTextarea\"]")).sendKeys("Suficiente");

        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"selectCommentType\"]")));
        select.selectByValue("REVIEW");

        driver.findElement(By.xpath("//*[@id=\"input\"]")).sendKeys("Protocolo");

        scroll();
        timeOut();

        clickButton("//*[@id=\"button_salvar\"]");
        scrollUp();
        assertEquals(driver.findElement(By.className("toast-message")).getText(), "Departamento incorreto!");

        scroll();
        timeOut();

        driver.findElement(By.xpath("//*[@id=\"button_cancelar\"]")).click();

    }

    @Test
    @Order(5)
    void departmenResponse() throws InterruptedException {

        scrollUp();
        Thread.sleep(2000);

        clickButton("//*[@id=\"cadastrar_comentario\"]");

        driver.findElement(By.xpath("//*[@id=\"inputCommentTitle\"]")).sendKeys("Suficiente");
        driver.findElement(By.xpath("//*[@id=\"MessageTextarea\"]")).sendKeys("Suficiente");

        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"selectCommentType\"]")));
        select.selectByValue("REVIEW");

        driver.findElement(By.xpath("//*[@id=\"input\"]")).sendKeys("COORDENAÇÃO DE CONTROLE ACADÊMICO - CAMPUS MONTEIRO");

        scroll();
        Thread.sleep(2000);

        clickButton("//*[@id=\"button_salvar\"]");
        scrollUp();
        assertEquals(driver.findElement(By.className("toast-message")).getText(), "No momento não existe responsavel pelo departamento! Tente mais tarde");

        scroll();
        Thread.sleep(2000);

        clickButton("//*[@id=\"button_cancelar\"]");

    }




    @Test
    @Order(6)
    void abaComent() throws InterruptedException{

        scrollUp();
        timeOut();
        clickButton("/html/body/div/nav/div/div/div/li[3]/a");
        clickButton("//*[@id=\"cadastrar_comentario\"]");

        driver.findElement(By.xpath("//*[@id=\"inputCommentTitle\"]")).sendKeys("Internet do campus");
        driver.findElement(By.xpath("//*[@id=\"MessageTextarea\"]")).sendKeys("Problemas constantes na internet do campus");

        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"selectCommentType\"]")));
        select.selectByValue("REVIEW");

        driver.findElement(By.xpath("//*[@id=\"input\"]")).sendKeys("Protocolo Virtual do Campus Monteiro");

        scroll();
        timeOut();

        clickButton("//*[@id=\"button_salvar\"]");

        timeOut();
        assertEquals("Internet do campus", driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[3]/div/div/table/tbody/tr[2]/td[1]")).getText());
        assertEquals("Problemas constantes na internet do campus", driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[3]/div/div/table/tbody/tr[2]/td[2]")).getText());

        assertEquals("REVIEW", driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[3]/div/div/table/tbody/tr[1]/td[4]")).getText());
        assertEquals("NOT_SOLVED", driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[3]/div/div/table/tbody/tr[1]/td[5]")).getText());

    }



}
