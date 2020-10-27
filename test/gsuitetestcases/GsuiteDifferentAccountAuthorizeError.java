package gsuitetestcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import GsuitePageObject.GooglePageObjects;
import resources.BaseGsuiteAccountMismatch;
import resources.TOTPGenerator;

public class GsuiteDifferentAccountAuthorizeError extends BaseGsuiteAccountMismatch {
	public static Logger log = LogManager.getLogger(GsuiteDifferentAccountAuthorizeError.class.getName());
	public GooglePageObjects googleobject;
	public WebDriver driver;

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {
		driver = initializeDriver();
		googleobject = new GooglePageObjects(driver);
	}

	// Validate error message when logged in user is different from GSuite account
	// user
	@Test(priority = 1)
	public void ValidateAccountMismatch() throws InterruptedException {
		selectIdentitymanagement();
		Thread.sleep(1000L);
		ValidateManagePageAssertion();
		Thread.sleep(3000L);
		GsuiteIconSetUpPageValidate();
		googleobject.ConnectGSuiteButton().click();
		ValidateGsuiteCredFlow();
		Thread.sleep(5000L);
		String GSuiteAccountError = googleobject.GSuiteAccountMessageMismatchError().getText();
		System.out.println("The GSuite account error is :" + GSuiteAccountError);
		ValidateConnectPageAssertion();
		System.out.println("Testcase-1 passed since application captured error message");

	}

	// Validate Flow to Manage page
	public void selectIdentitymanagement() throws InterruptedException {
		Thread.sleep(1000L);
		googleobject.avatarIcon().click();
		googleobject.AdminText().click();
		Thread.sleep(1000L);
		googleobject.UsersText().click();
		googleobject.ManageText().click();
		Thread.sleep(5000L);

	}

	// Validate Manage landing page when GSuite is not connected
	public void ValidateManagePageAssertion() throws InterruptedException {
		Thread.sleep(2000L);
		String managePageLandingText = googleobject.ConnectIdentityText().getText();
		System.out.println("the text is " + managePageLandingText);
		Assert.assertEquals(managePageLandingText, "Connect your identity provider");
	}

	// Function to validate GSuite first page - Setup/Connect
	public void GsuiteIconSetUpPageValidate() throws InterruptedException {
		googleobject.GSuiteIdentityIcon().click();
		Thread.sleep(2000L);
		String actualFirstStepText = googleobject.SetupStepText().getText();
		System.out.println("Set up text is ===> " + actualFirstStepText);
		Assert.assertEquals(actualFirstStepText, "Set up G Suite as your identity provider");
		ValidateConnectPageAssertion();
	}


	// Validate Step1 content for GSuite Connect
	public void ValidateConnectPageAssertion() {
		String stepCountText = googleobject.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 1 of 4");
	}

	// Validate
	public void ValidateGsuiteCredFlow() throws InterruptedException {
		String winHandleBefore = driver.getWindowHandle();
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		String signInHeader = googleobject.GsuiteSignInHeader().getText();
		Assert.assertEquals(signInHeader, "Sign in");
		Thread.sleep(1000L);
		googleobject.GsuiteEmail().sendKeys("hemaramakrishna@my.joinassembly.com");
		googleobject.GsuiteNextButton().click();
		Thread.sleep(1000L);
		googleobject.GsuitePwd().sendKeys("smileAfter1%");
		Thread.sleep(1000L);
		googleobject.GsuiteNextButton().click();
		Thread.sleep(2000L);
		googleobject.otpField().sendKeys(TOTPGenerator.getTwoFactorCode());
		Thread.sleep(1000L);
		googleobject.GsuiteNextButton().click();
		Thread.sleep(3000L);
		googleobject.GsuiteAllow().click();
		Thread.sleep(1000L);
		driver.switchTo().window(winHandleBefore);
	}

}
