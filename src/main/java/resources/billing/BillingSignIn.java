package billing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import giveRecognitionPageObjects.RecognitionPageObject;
import giveRecognitionPageObjects.loginPageObjects;
import resources.InitiateDriver;

public class BillingSignIn extends InitiateDriver {
	public WebDriver driver;
	public static Logger log = LogManager.getLogger(BillingSignIn.class.getName());

	public loginPageObjects loginobject;

	@BeforeTest
	public void initvalidateValidLogin() throws FileNotFoundException, IOException {
		driver = initializeDriver();
		loginobject = new loginPageObjects(driver);
	}
	
	@Test
	public void validateValidLogin() throws InterruptedException {
		loginobject.getUsernameObject().sendKeys("hema+devtestuser1@joinassembly.com");
		loginobject.getPasswordObject().sendKeys("testing");
		Thread.sleep(1000L);
		loginobject.signinObject().click();
		RecognitionPageObject recogobject = new RecognitionPageObject(driver);
		String mainContent = recogobject.giveRecognitionText().getText();
		if (mainContent == null) {
			log.info("failed to login!");
		} else {
			log.info("Successfully logged into Assembly homepage!");
			Assert.assertEquals(mainContent, "Give Recognition");
			log.info("Assertion Passed for homepage landing");
		}
	}

}
