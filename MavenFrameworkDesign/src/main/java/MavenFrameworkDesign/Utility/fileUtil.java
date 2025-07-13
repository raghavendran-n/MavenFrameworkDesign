package MavenFrameworkDesign.Utility;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import MavenFrameworkDesign.PageObjects.cartPage;
import MavenFrameworkDesign.PageObjects.orderPage;

public class fileUtil {
	
	WebDriver driver;
	
	public fileUtil(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[routerlink*='cart']")
	WebElement cart;
	
	@FindBy(css="[routerlink*='myorders']")
	WebElement myOrders;

	public void waitForWebElementToAppear(WebElement findBy) {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(findBy));
	}
	
	public void waitForElementToAppear(By findBy) {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	
	public void waitForElementToDisappear(WebElement ele) {
	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));	
		wait.until(ExpectedConditions.invisibilityOf(ele));
	}
	
	public cartPage goToCart() {
		cart.click();
		cartPage cartPage = new cartPage(driver);
		return cartPage;
	}
	
	public orderPage goToOrder() {
		myOrders.click();
		orderPage orderPage = new orderPage(driver);
		return orderPage;
		
	}
}
