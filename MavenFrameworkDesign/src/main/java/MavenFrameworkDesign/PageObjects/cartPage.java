package MavenFrameworkDesign.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import MavenFrameworkDesign.Utility.fileUtil;

public class cartPage extends fileUtil{
	
	WebDriver driver;
	
	@FindBy(xpath = "//div[@class='cartSection']/h3")
	List <WebElement> productTitles;
	
	@FindBy(xpath = "//button[contains(text(),'Checkout')]")
	WebElement checkoutButton;
	
	public cartPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public Boolean verifyProductInCart(String productAddedToCart) {
	
		Boolean bol = productTitles.stream().anyMatch(s->s.getText().equalsIgnoreCase(productAddedToCart));
		return bol;
	}
	
	public checkoutPage goToCheckout() {
		checkoutButton.click();
		checkoutPage checkout = new checkoutPage(driver);
		return checkout;
	}
}
