package br.edu.ifpb.dac.falacampus.seleniumTest;
import br.edu.ifpb.dac.falacampus.FalaCampusApplication;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

}
