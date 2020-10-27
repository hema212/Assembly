package resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeSuite;

public class InitiateDriver {

	public static Logger log = LogManager.getLogger(Base.class.getName());

	public WebDriver driver;
	public String baseurl;
	public String homepageurl;
	
	@BeforeSuite
	public WebDriver initializeDriver() throws IOException, FileNotFoundException {
		Properties prop = new Properties();

		FileInputStream fis = new FileInputStream(
				"C:\\Users\\Jayasurya S\\Mavenjava\\src\\main\\java\\sources\\data.properties");
		prop.load(fis);
		String browserName = prop.getProperty("browser");
		baseurl = prop.getProperty("baseurl");
		homepageurl = prop.getProperty("homepageurl");
		log.info("executing on the browser " + homepageurl);
		log.info("executing on the browser " + browserName);
		if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\Work\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.firefox.driver", "C:\\Users\\Work\\gecodriver.exe");
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		return driver;
	}

}
