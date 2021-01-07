package resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class InitiateDriver {

	public static Logger log = LogManager.getLogger(InitiateDriver.class.getName());

	public WebDriver driver;
	public String baseurl;
	public String homepageurl;

	@BeforeTest
	public WebDriver initializeDriver() throws IOException, FileNotFoundException {
		System.out.println("first one to enter base class");
		Properties prop = new Properties();

		FileInputStream fis = new FileInputStream(
				"C:\\Users\\Jayasurya S\\Assembly\\joinassembly\\src\\main\\java\\resources\\data.properties");
		prop.load(fis);
		String browserName = prop.getProperty("browser");
		baseurl = prop.getProperty("baseurl");
		homepageurl = prop.getProperty("homepageurl");
		log.info("executing on the browser " + homepageurl);
		log.info("executing on the browser " + browserName);

		if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\Work\\chromedriver.exe");
			try {
				ChromeOptions options = new ChromeOptions();
				options.addArguments(Arrays.asList("--no-sandbox", "--ignore-certificate-errors",
						"--homepage=about:blank", "--no-first-run"));
				options.addArguments("disable-infobars");
				options.setCapability(ChromeOptions.CAPABILITY, options);
				options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				driver = new ChromeDriver(options);
				System.out.println("options are : " +options);
			}

			catch (Exception e) {

				throw new Error(e);

			}
		} else if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.firefox.driver", "C:\\Users\\Work\\gecodriver.exe");
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		return driver;
	}

	@Test
	public void validateLandingPage() throws InterruptedException {
		driver.manage().window().maximize();
		driver.get(baseurl);
		
	}

}
