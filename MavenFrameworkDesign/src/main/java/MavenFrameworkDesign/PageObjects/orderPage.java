package MavenFrameworkDesign.PageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import MavenFrameworkDesign.Utility.fileUtil;

public class orderPage extends fileUtil{
WebDriver driver;

@FindBy(xpath="//tr/td[2]")
private List<WebElement> orderDetails;
	
	public orderPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public Boolean verifyOrderDetails(String productAddedToCart) {
		Boolean match = orderDetails.stream().anyMatch(s->s.getText().equalsIgnoreCase(productAddedToCart));
		return match;
	}
	
}
