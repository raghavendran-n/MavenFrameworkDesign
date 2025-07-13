package MavenFrameworkDesign.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import MavenFrameworkDesign.Utility.fileUtil;




public class loginPage extends fileUtil{
	
	WebDriver driver;
	
	public loginPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(xpath="//input[@id='userEmail']")
	WebElement emailField;
	
	@FindBy(xpath="//input[@id='userPassword']")
	WebElement passwordField;
	
	@FindBy(xpath="//input[@id='login']")
	WebElement loginButton;
	
	@FindBy(xpath="//div[@aria-label='Incorrect email or password.']")
	WebElement errorCapture;
	
	public void launchSite() {
		driver.get("https://rahulshettyacademy.com/client");
	}
	
	public String grabError() {
		waitForWebElementToAppear(errorCapture);
		return errorCapture.getText();
	}
	
	public pdp loginAction(String email, String password) {
		emailField.sendKeys(email);
		passwordField.sendKeys(password);
		loginButton.click();
		pdp pdp = new pdp(driver);
		return pdp;
		
	}
	
}
