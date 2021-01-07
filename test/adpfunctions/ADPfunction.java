package adpfunctions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import giveRecognitionPageObjects.RecognitionPageObject;
import giveRecognitionPageObjects.loginPageObjects;
import slacktestcases.DeleteInviteUsersSlack;

public class ADPfunction {
	public static Logger log = LogManager.getLogger(ADPfunction.class.getName());

	public WebDriver driver;
	public String baseurl;
	public String homepageurl;
	
public void func() throws InterruptedException {
	driver.manage().window().maximize();
	driver.get(baseurl);
	log.info("executing on the browser " + baseurl);
	log.info("Navigated to the provided URL");
	// Create an object for loginObjects class of pageObjects
	loginPageObjects loginobject = new loginPageObjects(driver);
	String actualheader = loginobject.getHeader().getText();
	String expectedheader = "Welcome back!";
	// Check for Assertion
	Assert.assertEquals(actualheader, expectedheader);
	log.info("Assertion passed for login page");
	loginobject.getUsernameObject().sendKeys("hema+21@joinassembly.com");
	loginobject.getPasswordObject().sendKeys("jonSnow09!");
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