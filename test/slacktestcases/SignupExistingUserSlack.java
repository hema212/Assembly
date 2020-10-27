package slacktestcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import PageObjects.loginPageObjects;
import resources.InitiateDriver;


//Execute this - 9 
public class SignupExistingUserSlack extends InitiateDriver {

	public static Logger log = LogManager.getLogger(SignupExistingUserSlack.class.getName());
	public loginPageObjects loginobject;
	public String captureErrorHeaderMessage;

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {
		WebDriver driver = initializeDriver();
		loginobject = new loginPageObjects(driver);
	}

	// Verify Existing user sign-up functionality
	@Test(priority = 1)
	public void ValidateExistingUserSignUp() {
		driver.manage().window().maximize();
		driver.get("https://dev.joinassembly.com");
		loginobject.getStarted().click();
		loginobject.getUsernameObject().sendKeys("gaurav+paidtest@joinassembly.com");
		loginobject.confirmButton().click();
		captureErrorHeaderMessage = loginobject.accountExistsErrorMessage().getText();
		log.info("The error message is : " + captureErrorHeaderMessage);
		Boolean isSignInButtonActive = loginobject.goBackToSignInButton().isEnabled();
		log.info("Is signin button displayed? " + isSignInButtonActive);
		Assert.assertEquals(captureErrorHeaderMessage, "Whoops, you already have an account!");
		log.info("Testcase-1 passed since Account Exists message is displayed");
	}

	// Verify Domain exists functionality
	@Test(priority = 2)
	public void ValidateSignUpDomainExistsFunc() {
		loginobject.goBackToSignInButton().click();
		loginobject.getUsernameObject().clear();
		loginobject.getUsernameObject().sendKeys(Keys.chord(Keys.CONTROL, "a"), "pranesh+join21@joinassembly.com");
		loginobject.confirmButton().click();
		captureErrorHeaderMessage = loginobject.accountExistsErrorMessage().getText();
		log.info("The error message is : " + captureErrorHeaderMessage);
		Boolean isUseDiffEmailButtonActive = loginobject.goBackToSignInButton().isEnabled();
		log.info("Use different email button displayed? " + isUseDiffEmailButtonActive);
		Assert.assertEquals(captureErrorHeaderMessage, "Sorry, this domain is already in use!");
		log.info("Testcase-2 passed since Domain in use message is displayed");
	}

}
