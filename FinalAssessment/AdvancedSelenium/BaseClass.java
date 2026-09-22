// link -> enter some data in the What's on your mind? text field ->click on post->then verify whether the post is posted in the Recent post -> logout

package FinalAssessment;

import java.awt.AWTException;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;


public class OrangehrmBaseClass {
	protected WebDriver driver;
	protected String browser,url,un,pwd;
	protected String info;
	protected HomePage homePage;
	protected LoginPage loginPage;
	protected BuzzPage buzzPage;
	public void Data() throws IOException
	{
		FileInputStream fis = new FileInputStream("./src/test/resources/DDT/OrangeHRMFinalAssessment.properties");
		
		Properties p = new Properties();
		
		p.load(fis);
		
		browser = p.getProperty("browser");
		url = p.getProperty("url");
		un = p.getProperty("username");
		pwd = p.getProperty("password");
		info = p.getProperty("info");				
	}
	
	@BeforeClass
	public void beforeClass() throws IOException, AWTException
	{
		Data();
		if(browser.equals("chrome"))
		{
			ChromeOptions settings = new ChromeOptions();
			Map<String, Object> prefs = new HashMap<>();
			prefs.put("profile.password_manager_leak_detection", false);
			settings.setExperimentalOption("prefs", prefs);
			driver = new ChromeDriver(settings);
		}
		
		else if(browser.equals("edge"))
		{
			EdgeOptions settings = new EdgeOptions();
			Map<String, Object> prefs = new HashMap<>();
			prefs.put("profile.password_manager_leak_detection", false);
			settings.setExperimentalOption("prefs", prefs);
			driver = new EdgeDriver(settings);
		}
		
		else if(browser.equals("firefox"))
		{
			FirefoxOptions settings = new FirefoxOptions();
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("signon.management.page.breachAlert.enabled", false);
			settings.setProfile(profile);
			driver = new FirefoxDriver(settings);
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
		buzzPage = new BuzzPage(driver);
	
	}
	
	@BeforeMethod
	public void beforeMethod() throws InterruptedException
	{
		driver.get(url);
		loginPage.getUntf(un);
		loginPage.getPwdtf(pwd);
		Thread.sleep(1000);
		loginPage.getLoginButton();
		
	}
	
	@AfterMethod
	public void afterMethod()
	{
		homePage.getLogoutButton();
		System.out.println("Logout done");
	}
	
	
	@AfterClass
	public void afterClass()
	{
		driver.quit();
		System.out.println("closing the browser");
	}
}
