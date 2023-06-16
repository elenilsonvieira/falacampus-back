package br.edu.ifpb.dac.falacampus.seleniumTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Timeout;
import org.openqa.selenium.By;

public class EditUserTest extends FalaCampusWebTest{
    EditUserTest(){
        url = "http://localhost:3000/viewCommentsHome";
    }

    @BeforeAll
    void up(){
        logar(DataSingle.getRegistration(),DataSingle.getPassword());
        timeOut();
        driver.findElement(By.xpath("//*[@id=\"users\"]")).click();
    }

}
