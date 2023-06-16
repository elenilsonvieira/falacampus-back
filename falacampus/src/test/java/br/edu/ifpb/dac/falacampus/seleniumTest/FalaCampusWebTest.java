package br.edu.ifpb.dac.falacampus.seleniumTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class FalaCampusWebTest {

	protected WebDriver driver;
	protected String url;

	@BeforeAll
	void setupConfig() throws Exception{
		driver = DriverSingle.getDriver();
		driver.get(url);
	}

	void timeOut(){
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}


	void deleteXpath(String element){
		driver.findElement(By.xpath(element)).sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);

	}
	void clickButton(String element){
		driver.findElement(By.xpath(element)).click();
	}

	void logar(String registration,String password){

		driver.findElement(By.xpath(
				"/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[1]/input"))
				.sendKeys(registration);
		driver.findElement(By.xpath(
				"/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[2]/input"))
				.sendKeys(password);

		timeOut();

		clickButton("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/button");
	}

}
