package office365testcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import PageObjects.O365PageObjects;
import PageObjects.RecognitionPageObject;
import resources.BaseO365AccountMismatch;
import slacktestcases.SlackIdentityUpgradeViewFlow;

//Execute this Fifth (5th) when Account created is different from Office 365 account
public class O365DifferentAccountAuthorizeError extends BaseO365AccountMismatch {
	public static Logger log = LogManager.getLogger(O365DifferentAccountAuthorizeError.class.getName());
	public O365PageObjects office365object;
	public RecognitionPageObject recogobject;
	public WebDriver driver;
	SlackIdentityUpgradeViewFlow slackIm = new SlackIdentityUpgradeViewFlow();

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {
		driver = initializeDriver();
		office365object = new O365PageObjects(driver);
	}

	@Test(priority = 1)
	public void SignInViaEmail() throws InterruptedException {
		System.out.println("Signed in using email and password");
		selectManageSideNavbar();
		Thread.sleep(1000L);
		validateManagePageAssertion();
		System.out.println("Testcase-1 passed since application landed on Manage page and Assertion passed");
	}

	// validate Queued invites flow when selected I'll send them later
	@Test(priority = 2)
	public void validateAccountMismatchErrorCapture() throws InterruptedException {
		O365IconPagevalidateOnClick();
		Thread.sleep(2000L);
		office365object.ConnectOffice365Button().click();
		Thread.sleep(1000L);
		provideEmailPwd();
		Thread.sleep(5000L);
		CaptureAssemblyErrorMessage();
		ValidateConnectPageAssertion();
		System.out.println("Testcase-2 passed since application captured error message");
	}

	// Function to validate Office365 first page - Setup/Connect
	public void O365IconPagevalidateOnClick() throws InterruptedException {
		office365object.Office365Icon().click();
		Thread.sleep(2000L);
		String actualFirstStepText = office365object.SetupStepText().getText();
		System.out.println("Set up text is ===> " + actualFirstStepText);
		Assert.assertEquals(actualFirstStepText, "Set up Office365 as your identity provider");

	}

	// Select different assembly account and capture error message
	public void CaptureAssemblyErrorMessage() {
		String errorAccountAssociateMessage = office365object.SetUpAlertMessage().getText();
		System.out.println("The error message is: " + errorAccountAssociateMessage);
		Assert.assertEquals(errorAccountAssociateMessage, "Sorry, your Office 365 account is already associated with another Assembly account");
	}

	// validate Manage landing page when Office365 is not connected
	public void validateManagePageAssertion() throws InterruptedException {
		Thread.sleep(2000L);
		String managePageLandingText = office365object.ConnectIdentityText().getText();
		System.out.println("the text is " + managePageLandingText);
		Assert.assertEquals(managePageLandingText, "Connect your identity provider");
	}

	// Select Manage text from side nav
	public void selectManageSideNavbar() throws InterruptedException {
		office365object.avatarIcon().click();
		office365object.AdminText().click();
		office365object.UsersText().click();
		office365object.ManageText().click();
		Thread.sleep(5000L);

	}

	// Provide email and password to signin
	public void provideEmailPwd() throws InterruptedException {
		office365object.O365Email().sendKeys("hema2508@joinassembly123.onmicrosoft.com");
		Thread.sleep(1000L);
		office365object.SubmitEmail().click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password']")));
		office365object.O365Pwd().sendKeys("Elizebeth@123");
		office365object.SubmitEmail().click();
		Thread.sleep(2000L);
		office365object.O365Consent().click();
		office365object.AcceptO365Consent().click();
		Thread.sleep(3000L);
	}
	
	// Validate Step1 content for Office 365 Connect
			public void ValidateConnectPageAssertion() {
				String stepCountText = office365object.StepCount().getText();
				System.out.println("Step count is ===> " + stepCountText);
				Assert.assertEquals(stepCountText, "Step 1 of 4");
			}
}
