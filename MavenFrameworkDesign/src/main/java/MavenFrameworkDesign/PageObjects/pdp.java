package MavenFrameworkDesign.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import MavenFrameworkDesign.Utility.fileUtil;

public class pdp extends fileUtil{
WebDriver driver;
	
	public pdp(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(xpath="//div[contains(@class,'mb-3')]")
	List <WebElement> productList;
	
	@FindBy(css=".ng-animating")
	WebElement spinner;
	
	By prod = By.xpath("//div[contains(@class,'mb-3')]");
	By toastMessage = By.cssSelector("#toast-container");
	
	public List<WebElement> getProduct() {
		waitForElementToAppear(prod);
		return productList;
	}
	
	public void addProductToCart(String productAddedToCart) {
		WebElement ele = productList.stream().filter(s->s.findElement(By.cssSelector("b")).getText().equals(productAddedToCart)).findFirst().orElse(null);
		ele.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		waitForElementToAppear(toastMessage);
		waitForElementToDisappear(spinner);
	}
}
