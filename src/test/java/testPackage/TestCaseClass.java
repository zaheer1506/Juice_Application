package testPackage;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCaseClass {
	
	@Test(priority =1)
	public void launchApplication() throws Exception {
		WebDriverManager.chromedriver().setup();
		WebDriver juiceShopApp = new ChromeDriver();
		juiceShopApp.get(readPropertiesFile("Application_Url"));
		System.out.println("Juice Application Launch Successfully");
		juiceShopApp.manage().window().maximize();
		Assert.assertEquals(juiceShopApp.getTitle(), readPropertiesFile("Application_Title"));
		System.out.println("Juice Shop Application Title verified Successfully");
		
		WebElement dismissbuttonPopUp = juiceShopApp.findElement(By.xpath("//span[text()='Dismiss']/../.."));
		dismissbuttonPopUp.click();
		Thread.sleep(3000);
		WebElement accountLink = juiceShopApp.findElement(By.xpath("//span[normalize-space(text())='Account']/.."));
		accountLink.click();
		Thread.sleep(2000);
		WebElement loginButton = juiceShopApp.findElement(By.xpath("//span[normalize-space(text())='Login']/parent::button"));
		loginButton.click();
		Thread.sleep(2000);
		
		WebElement userNameTextField = juiceShopApp.findElement(By.xpath("//input[@id='email']"));
		userNameTextField.sendKeys(readPropertiesFile("userName"));
		Thread.sleep(2000);
		WebElement passwordTextField = juiceShopApp.findElement(By.xpath("//input[@id='password']"));
		passwordTextField.sendKeys(readPropertiesFile("Password"));
		Thread.sleep(2000);
//		WebElement UserLoginButton = juiceShopApp.findElement(By.xpath(""));
		WebElement UserLoginButton = juiceShopApp.findElement(By.xpath("//button[@id='loginButton']"));
		UserLoginButton.click();
		Thread.sleep(2000);
		accountLink.click();
		Thread.sleep(2000);
		String acutualloginUser = juiceShopApp.findElement(By.xpath("//button[contains(@aria-label,'user profile')]/span")).getText();
		Assert.assertEquals(acutualloginUser.trim(), readPropertiesFile("userName"));
		System.out.println("Login User Account verified Successfully");
		Thread.sleep(2000);
		WebElement orderPaymentTab = juiceShopApp.findElement(By.xpath("//span[contains(text(),'Orders & Payment')]/parent::button"));
		orderPaymentTab.click();
		WebElement paymentOptionsTab = juiceShopApp.findElement(By.xpath("//span[contains(text(),'Payment Options')]/parent::button"));
		paymentOptionsTab.click();
		Thread.sleep(3000);
		WebElement userPaymentOptionScreen = juiceShopApp.findElement(By.tagName("h1"));
		Assert.assertEquals(userPaymentOptionScreen.getText().trim(), readPropertiesFile("UserScreen"));
		System.out.println("User landing on Payment Options screen Successfully");
		Thread.sleep(3000);
		Assert.assertFalse(juiceShopApp.findElement(By.xpath("//button[@id='submitButton']")).isEnabled());
		System.out.println("Submit button is in disable mode before enter the card Details");
		Thread.sleep(2000);
		WebElement addNewCardButton=juiceShopApp.findElement(By.xpath("//*[normalize-space(text())='Add new card']"));
		addNewCardButton.click();
	}
	
	public String readPropertiesFile(String propertyName) throws Exception{
		File file = new File(".\\utility\\Juice_Application.properties");
		FileInputStream fis = new FileInputStream(file);
		Properties prop = new Properties();
		prop.load(fis);
		 return prop.getProperty(propertyName);
	}

}
