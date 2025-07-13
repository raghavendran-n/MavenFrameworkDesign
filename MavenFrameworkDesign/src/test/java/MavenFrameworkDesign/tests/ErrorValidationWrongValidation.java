package MavenFrameworkDesign.tests;


import java.io.IOException;
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
import org.testng.annotations.Test;

import MavenFrameworkDesign.PageObjects.ConfirmationPage;
import MavenFrameworkDesign.PageObjects.cartPage;
import MavenFrameworkDesign.PageObjects.checkoutPage;
import MavenFrameworkDesign.PageObjects.loginPage;
import MavenFrameworkDesign.PageObjects.pdp;
import MavenFrameworkDesign.TestComponents.BaseTest;
import MavenFrameworkDesign.TestComponents.Retry;


public class ErrorValidationWrongValidation extends BaseTest {
	static String productAddedToCart = "ZARA COAT 3";
	static String email = "raghavendran.stage@gmail.com";
	static String password = "Raghav2000";

	@Test(retryAnalyzer=Retry.class)
	public void ErrorValidation() throws IOException {
		
		pdp pdp = loginPage.loginAction(email,password);
		System.out.println(loginPage.grabError());
		Assert.assertEquals("Incorrect mmemail or password.", loginPage.grabError());
		
	}
	
}
