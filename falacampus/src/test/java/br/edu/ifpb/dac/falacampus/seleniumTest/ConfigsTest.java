package br.edu.ifpb.dac.falacampus.seleniumTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class ConfigsTest {

	protected WebDriver driver;
	protected String url;

	@BeforeAll
	void setupConfig() throws Exception{
		driver = DataSingle.getDriver();
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

	void logarUser1(){
		insert(	"/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[1]/input", DataSingle.getRegistration());
		insert(	"/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[2]/input", DataSingle.getPassword());
		timeOut();
		clickButton("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/button");
	}
	void logarUser2(){
		insert(	"/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[1]/input", DataSingle.getRegistration2());
		insert(	"/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/div[2]/input", DataSingle.getPassword2());

		timeOut();
		clickButton("/html/body/div/div[1]/div/div/div/div/div/div/div/div/form/fieldset/button");
	}
   void insert(String xpath, String args){
	   driver.findElement(By.xpath(xpath))
			   .sendKeys(args);
   }

	void scroll(){
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		}catch (Exception e){

		}
	}

	void scrollUp(){
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollTo(0, 0)");
		}catch (Exception e){}
	}

	void home(){
		driver.findElement(By.xpath("//*[@id=\"answers\"]")).click();
	}

}
