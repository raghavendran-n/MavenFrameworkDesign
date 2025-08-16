package MavenFrameworkDesign.tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import MavenFrameworkDesign.PageObjects.ConfirmationPage;
import MavenFrameworkDesign.PageObjects.cartPage;
import MavenFrameworkDesign.PageObjects.checkoutPage;
import MavenFrameworkDesign.PageObjects.loginPage;
import MavenFrameworkDesign.PageObjects.orderPage;
import MavenFrameworkDesign.PageObjects.pdp;
import MavenFrameworkDesign.TestComponents.BaseTest;


public class placeOrder extends BaseTest {

	//groups, groups= {"placeOrder"}
	@Test(dataProvider="getData")
	public void placeOrder(HashMap<String,String> input) throws IOException {
		
		pdp pdp = loginPage.loginAction(input.get("email"),input.get("password"));
		
		//Add to cart
		List<WebElement> products = pdp.getProduct();
		pdp.addProductToCart(input.get("productAddedToCart"));
		cartPage cartPage = pdp.goToCart();
		
		//validate Cart Details
		Boolean bol = cartPage.verifyProductInCart(input.get("productAddedToCart"));
		Assert.assertTrue(bol);	
		checkoutPage checkout = cartPage.goToCheckout();
		
		//validate Checkout Details
		checkout.selectCountry("india");
		ConfirmationPage confirmationPage = checkout.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		
	}
	
	
//	@Test(dependsOnMethods= {"placeOrder"})
//	public void verifyOrderDetails(HashMap<String,String> input) throws IOException {
//		
//		pdp pdp = loginPage.loginAction(input.get("email"),input.get("password"));
//		orderPage orderPage = pdp.goToOrder();
//		Assert.assertTrue(orderPage.verifyOrderDetails(input.get("productAddedToCart")));
//		
//	}
	
	@DataProvider
	public Object [][] getData() throws IOException{
		//HashMap<String,String> input1 = new HashMap<String,String>();
		//input1.put("email", "raghavendran.stage@gmail.com");
		//input1.put("password", "Raghav#2000");
		//input1.put("productAddedToCart", "ZARA COAT 3");
		
		//HashMap<String,String> input2 = new HashMap<String,String>();
		//input2.put("email", "raghavendran.stage@gmail.com");
		//input2.put("password", "Raghav#2000");
		//input2.put("productAddedToCart", "ADIDAS ORIGINAL");
		
		List<HashMap<String,String>> data =jsonDataToHashMap(System.getProperty("user.dir")+"//src//test//java//MavenFrameworkDesign//data//data.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}
	
	
		
	
}
