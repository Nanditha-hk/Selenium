package Assessment;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.apache.poi.ss.usermodel.DataFormatter;
import Assessment.OrangeHrmLoginPage;
import Assessment.OrangeHrmRecruitmentPage;
import Assessment.OrangeHrmCandidatePage;

public class OrangeHrm1{

    public static void main(String[] args) throws IOException, InterruptedException {

        // Read common data from properties file
        FileInputStream fis = new FileInputStream("./src/test/resources/DDT/OrangeHrm1.properties");

        Properties p = new Properties();
        p.load(fis);

        // Read test data from Excel
        FileInputStream excel = new FileInputStream("./src/test/resources/DDT/OrangeHrm1.xlsx");

        Workbook wb = WorkbookFactory.create(excel);
        DataFormatter df = new DataFormatter();

        String firstName = df.formatCellValue(wb.getSheet("Sheet1").getRow(1).getCell(0));
        String middleName = df.formatCellValue(wb.getSheet("Sheet1").getRow(1).getCell(1));
        String lastName = df.formatCellValue(wb.getSheet("Sheet1").getRow(1).getCell(2));
        String vacancy = df.formatCellValue(wb.getSheet("Sheet1").getRow(1).getCell(3));
        String email = df.formatCellValue(wb.getSheet("Sheet1").getRow(1).getCell(4));
        String mobileNumber = df.formatCellValue(wb.getSheet("Sheet1").getRow(1).getCell(5));
        String resume = df.formatCellValue(wb.getSheet("Sheet1").getRow(1).getCell(6));
        String applicationDate = df.formatCellValue(wb.getSheet("Sheet1").getRow(1).getCell(7));
        String jobTitle = df.formatCellValue(wb.getSheet("Sheet1").getRow(1).getCell(8));
        String searchVacancy = df.formatCellValue(wb.getSheet("Sheet1").getRow(1).getCell(9));
        String hiringManager = df.formatCellValue(wb.getSheet("Sheet1").getRow(1).getCell(10));
        String status = df.formatCellValue(wb.getSheet("Sheet1").getRow(1).getCell(11));
        String candidateName = df.formatCellValue(wb.getSheet("Sheet1").getRow(1).getCell(12));
        String searchApplicationDate = df.formatCellValue(wb.getSheet("Sheet1").getRow(1).getCell(13));

        // Read common data from properties
        String Browser = p.getProperty("browser");
        String url = p.getProperty("url");
        String Username = p.getProperty("username");
        String Password = p.getProperty("password");

        // Launch browser
        WebDriver driver = null;

		if (Browser.equals("chrome")) {

		    ChromeOptions option = new ChromeOptions();

		    Map<String, Object> prefs = new HashMap<>();
		    prefs.put("profile.password_manager_leak_detection", false);
		    option.setExperimentalOption("prefs", prefs);

		    driver = new ChromeDriver(option);
		}

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Open OrangeHRM
        driver.get(url);

        // Login
        OrangeHrmLoginPage login = new OrangeHrmLoginPage(driver);
        login.getUsername(Username);
        login.getPassword(Password);
        login.getLogin();

        // Recruitment
        driver.findElement(By.xpath("//span[text()='Recruitment']")).click();

        // Add Candidate
        driver.findElement(By.xpath("//button[normalize-space()='Add']")).click();

        OrangeHrmRecruitmentPage recruitment = new OrangeHrmRecruitmentPage(driver);

        recruitment.getFirstname(firstName);
        recruitment.getMiddlename(middleName);
        recruitment.getLastname(lastName);
        recruitment.getVacancy(vacancy);
        recruitment.getemail(email);
        recruitment.getNumber(mobileNumber);
        recruitment.getresume(resume);
        recruitment.getDateOfApplication(applicationDate);
        recruitment.getSavebtn();

        // Candidates
        driver.findElement(By.xpath("//a[text()='Candidates']")).click();

        OrangeHrmCandidatePage candidates = new OrangeHrmCandidatePage(driver);

        candidates.getJobTitle(jobTitle);
        candidates.getVacancy(searchVacancy);
        candidates.getHiringManager(hiringManager);
        candidates.getStatus(status);
        candidates.getCandidateName(candidateName);
        candidates.getApplicationDate(searchApplicationDate);
        candidates.getSearch();

        Thread.sleep(2000);

        // Verify candidate
        WebElement candidateRecord = driver.findElement(By.xpath("//div[@role='row']//div[contains(text(),'" + candidateName + "')]"));

        if (candidateRecord.isDisplayed()) 
        {
            System.out.println("Candidate is successfully added and displayed in Records Found");
        } else {
            System.out.println("Candidate is NOT displayed in Records Found");
        }

        // Logout
        driver.findElement(By.className("oxd-userdropdown-name")).click();
        driver.findElement(By.xpath("//a[text()='Logout']")).click();

        driver.quit();
    }
}
