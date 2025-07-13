package MavenFrameworkDesign.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import MavenFrameworkDesign.Utility.fileUtil;

public class ConfirmationPage extends fileUtil{
WebDriver driver;
	
	public ConfirmationPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = ".hero-primary")
	WebElement confirmationMessage;
	public String getConfirmationMessage()
	{
		checkoutPage cp = new checkoutPage(driver);	
		return confirmationMessage.getText();
	}
}
