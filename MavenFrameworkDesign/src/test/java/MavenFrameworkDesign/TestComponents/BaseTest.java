package MavenFrameworkDesign.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import MavenFrameworkDesign.PageObjects.loginPage;

public class BaseTest {
	
	public WebDriver driver;
	public loginPage loginPage;
	
	public WebDriver initializeDriver() throws IOException {
		
		//Retrive browser details from global
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/MavenFrameworkDesign/resources/GlobalData.properties");
		prop.load(fis);
		String browsername = prop.getProperty("browser");
		
		if (browsername.equalsIgnoreCase("chrome")) {
			
			driver = new ChromeDriver();
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;
	}
	
	
	public List<HashMap<String, String>> jsonDataToHashMap(String filePath) throws IOException {
		String jsonContent=FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
		//Import Jackson Databind is user to convert json to hashmap
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String,String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>(){});
		return data;
	}
	
	
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File destination = new File(System.getProperty("user.dir")+"//reports//"+ testCaseName + ".png");
		FileUtils.copyFile(source, destination);
		return System.getProperty("user.dir")+"//reports//"+ testCaseName + ".png";
	}
	
	
	@BeforeMethod(alwaysRun=true)
	public loginPage landOnSite() throws IOException {
		driver = initializeDriver();
		loginPage = new loginPage(driver);
		loginPage.launchSite();
		return loginPage;
	}
	
	@AfterMethod(alwaysRun=true)
	public void closeBrowser() {
		driver.close();	
	}

}
