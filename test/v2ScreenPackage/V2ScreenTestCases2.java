package v2ScreenPackage;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import adpfunctions.ADPfunction;
import giveRecognitionPageObjects.RecognitionPageObject;
import giveRecognitionPageObjects.loginPageObjects;
import resources.InitiateDriver;
import slackPageObjects.SlackIdentityObjects;

public class V2ScreenTestCases2 extends InitiateDriver{

	public loginPageObjects loginobject;

	@BeforeTest
	public void init2() throws FileNotFoundException, IOException {
		driver = initializeDriver();
		loginobject = new loginPageObjects(driver);
	}
	
	@Test
	public void validateLandingPage2() throws InterruptedException{
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
	        System.out.println("Thank yyou!");
			
	    }
		
	//	driver.get("http://google.com");
	

}
