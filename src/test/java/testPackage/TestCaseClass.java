package testPackage;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCaseClass {
	public static WebDriver juiceShopApp;
	
	@Test(priority =1)
	public static void launchApplication() throws Exception {
		WebDriverManager.chromedriver().setup();
		juiceShopApp = new ChromeDriver();
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
		WebElement userSubmitButton =juiceShopApp.findElement(By.xpath("//button[@id='submitButton']"));
		Assert.assertFalse(userSubmitButton.isEnabled());
		System.out.println("Submit button is in disable mode before enter the card Details");
		Thread.sleep(2000);
		WebElement addNewCardButton=juiceShopApp.findElement(By.xpath("//*[normalize-space(text())='Add new card']"));
		addNewCardButton.click();
		Thread.sleep(2000);
		WebElement userCardName = juiceShopApp.findElement(By.xpath("//input[@type='text' and contains(@id,'3')]"));
		userCardName.sendKeys(readPropertiesFile("cardName"));
		Thread.sleep(2000);
		WebElement userCardNumber = juiceShopApp.findElement(By.xpath("//input[@type='number']"));
		userCardNumber.sendKeys(readPropertiesFile("CardNumber"));
		Thread.sleep(2000);
		List<WebElement> monthDropdown = juiceShopApp.findElements(By.xpath("//select[contains(@id,'5')]/option"));
		dropdownMethod(monthDropdown, readPropertiesFile("cardMonth"));
		Thread.sleep(2000);
		List<WebElement> yearDropdown = juiceShopApp.findElements(By.xpath("//select[contains(@id,'6')]/option"));
		dropdownMethod(yearDropdown, readPropertiesFile("cardYear"));
		Thread.sleep(2000);
		Assert.assertTrue(userSubmitButton.isEnabled());
		System.out.println("Card Submit button enable successfully after enter card details");
		juiceShopApp.close();
	}
	
	public static String readPropertiesFile(String propertyName) throws Exception{
		File file = new File(".\\utility\\Juice_Application.properties");
		FileInputStream fis = new FileInputStream(file);
		Properties prop = new Properties();
		prop.load(fis);
		 return prop.getProperty(propertyName);
	}
	
	public static void dropdownMethod(List<WebElement> elements, String userInput) {	
		for(WebElement unique: elements) {
			if(unique.getText().equals(userInput)) {
				unique.click();
			}
		}
	}

}
