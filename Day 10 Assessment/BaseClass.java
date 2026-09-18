package BaseclassImplementation;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseClass {

    public WebDriver driver;
    public Properties p;

    @BeforeClass
    public void setup() throws Exception {

        // Read property file
        FileInputStream fis = new FileInputStream("./src/test/resources/DDT/SauceDemo.properties");
        p = new Properties();
        p.load(fis);

        // Disable Chrome popups
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-features=PasswordLeakDetection");
        options.setExperimentalOption("prefs", java.util.Map.of("credentials_enable_service", false, "profile.password_manager_enabled", false));

        // Launch browser
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(p.getProperty("url"));
        Thread.sleep(2000);
    }

    @AfterClass
    public void closeBrowser() throws Exception {

        // Close browser
        Thread.sleep(1000);
        driver.quit();
    }
}
