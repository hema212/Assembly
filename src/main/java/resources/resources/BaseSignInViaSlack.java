package resources;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import PageObjects.SlackIdentityObjects;
import PageObjects.loginPageObjects;

public class BaseSignInViaSlack extends InitiateDriver{
	public static Logger log = LogManager.getLogger(BaseSignInViaSlack.class.getName());

	
	public void validateSlackLogin() throws InterruptedException {
		driver.manage().window().maximize();
		driver.get(baseurl);
		log.info("Navigated to the provided URL");
		// Create an object for loginObjects class of pageObjects
		loginPageObjects loginobject = new loginPageObjects(driver);
		SlackIdentityObjects slackobject = new SlackIdentityObjects(driver);
		String actualheader = loginobject.getHeader().getText();
		String expectedheader = "Welcome back!";
		// Check for Assertion
		Assert.assertEquals(actualheader, expectedheader);
		log.info("Assertion passed for login page");
		loginobject.getUsernameObject().sendKeys("hema+21@joinassembly.com");
		loginobject.getPasswordObject().sendKeys("jonSnow09!");
		Thread.sleep(1000L);
		loginobject.signinObject().click();
		Thread.sleep(4000L);
		String getLoginMessage = loginobject.SlackSSOHeader().getText();
		System.out.println("The Message is "+ getLoginMessage);
		if(getLoginMessage.equalsIgnoreCase("Please use your SSO")) {
			//System.out.println(getLoginMessage);
			slackobject.SignInWithSlackButton().click();
			
		}
		else if(getLoginMessage.equalsIgnoreCase("invalid email/password")) {
			System.out.println("Invalid mail/password");
		}
	}
	
	@Test
	public void validateSlackLoginWhenConnected() throws FileNotFoundException, IOException, InterruptedException {
		validateSlackLogin();
	}

}
