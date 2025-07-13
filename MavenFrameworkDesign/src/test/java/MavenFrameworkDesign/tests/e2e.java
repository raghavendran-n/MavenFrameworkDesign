package MavenFrameworkDesign.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


public class e2e {
	static String productAddedToCart = "ZARA COAT 3";
	public static void main(String[] args){

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		login(driver);
		addToCart(driver);
		validateCartDetails(driver);
		validateCheckoutDetails(driver);
		
		

	}
	
	public static void login(WebDriver driver) {
		driver.findElement(By.xpath("//input[@id='userEmail']")).sendKeys("raghavendran.stage@gmail.com");
		driver.findElement(By.xpath("//input[@id='userPassword']")).sendKeys("Raghav#2000");
		driver.findElement(By.xpath("//input[@id='login']")).click();
		
	}
	
	public static void addToCart(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mb-3")));
		
		List<WebElement> products = driver.findElements(By.className("mb-3"));
		WebElement ele = products.stream().filter(s->s.findElement(By.cssSelector("b")).getText().equals(productAddedToCart)).findFirst().orElse(null);
		ele.findElement(By.xpath("//button[text()=' Add To Cart']")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
	}
	
	public static void validateCartDetails(WebDriver driver) {
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		List<WebElement> ele = driver.findElements(By.xpath("//div[@class='cartSection']/h3"));
		Boolean bol = ele.stream().anyMatch(s->s.getText().equalsIgnoreCase(productAddedToCart));
		Assert.assertTrue(bol);	
	}
	
	public static void validateCheckoutDetails(WebDriver driver) {
		driver.findElement(By.xpath("//button[contains(text(),'Checkout')]")).click();
		
		driver.findElement(By.xpath("//input[@placeholder='Select Country']")).sendKeys("Ind");
		driver.findElement(By.xpath("//span[text()=' India']")).click();
		
		driver.findElement(By.xpath("//a[text()='Place Order ']")).click();
		
		String message = driver.findElement(By.xpath("//h1")).getText();
		Assert.assertTrue(message.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		driver.close();
		
		
		
	}
	

}
