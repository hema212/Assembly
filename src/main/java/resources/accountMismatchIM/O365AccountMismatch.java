package accountMismatchIM;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import accountSignInWhenIMConnected.SlackSignInWhenConnected;
import giveRecognitionPageObjects.RecognitionPageObject;
import giveRecognitionPageObjects.loginPageObjects;
import resources.InitiateDriver;

public class O365AccountMismatch extends InitiateDriver{
	public static Logger log = LogManager.getLogger(SlackSignInWhenConnected.class.getName());

	
	public void validateSlackLogin() throws InterruptedException {
		driver.manage().window().maximize();
		driver.get(baseurl);
		log.info("Navigated to the provided URL");
		// Create an object for loginObjects class of pageObjects
		loginPageObjects loginobject = new loginPageObjects(driver);
		String actualheader = loginobject.getHeader().getText();
		String expectedheader = "Welcome back!";
		// Check for Assertion
		Assert.assertEquals(actualheader, expectedheader);
		log.info("Assertion passed for login page");
		loginobject.getUsernameObject().sendKeys("hema+office365Test@joinassembly.com");
		loginobject.getPasswordObject().sendKeys("jonSnow09!");
		Thread.sleep(2000L);
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
	
	@Test
	public void validateSlackLoginWhenConnected() throws FileNotFoundException, IOException, InterruptedException {
		validateSlackLogin();
	}

}

