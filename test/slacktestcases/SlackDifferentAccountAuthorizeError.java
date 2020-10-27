package slacktestcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import PageObjects.SlackIdentityObjects;
import resources.BaseSlackAccountMismatch;

//Execute this Fifth (5th) when Account created is different from slack account
public class SlackDifferentAccountAuthorizeError extends BaseSlackAccountMismatch {
	public static Logger log = LogManager.getLogger(SlackDifferentAccountAuthorizeError.class.getName());
	public SlackIdentityObjects slackobject;
	public WebDriver driver;
	SlackIdentityUpgradeViewFlow slackIm = new SlackIdentityUpgradeViewFlow();

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {
		driver = initializeDriver();
		slackobject = new SlackIdentityObjects(driver);
	}

	// Validate error message when logged in user is different from Slack account user
	@Test(priority = 1)
	public void ValidateAccountMismatch() throws InterruptedException {
		selectIdentitymanagement();
		ValidateManagePageAssertion();
		SlackIconSetUpPageValidate();
		slackobject.ConnectSlackButton().click();
		ValidateSlackCredFlow();
		Thread.sleep(5000L);
		String SlackAccountError = slackobject.SlackAccountMessageMismatchError().getText();
		System.out.println("The slack account error is " + SlackAccountError);
		ValidateConnectPageAssertion();
		System.out.println("Testcase-1 passed since application captured error message"); 
	}

	// Validate Flow to Manage page
	public void selectIdentitymanagement() throws InterruptedException {
		slackobject.avatarIcon().click();
		slackobject.AdminText().click();
		slackobject.UsersText().click();
		slackobject.ManageText().click();
		Thread.sleep(5000L);

	}

	// Validate Manage landing page when slack is not connected
	public void ValidateManagePageAssertion() throws InterruptedException {
		Thread.sleep(2000L);
		String managePageLandingText = slackobject.ConnectIdentityText().getText();
		System.out.println("the text is " + managePageLandingText);
		Assert.assertEquals(managePageLandingText, "Connect your identity provider");
	}

	// Function to validate Slack first page - Setup/Connect
	public void SlackIconSetUpPageValidate() throws InterruptedException {
		slackobject.SlackIdentityIcon().click();
		Thread.sleep(2000L);
		String actualFirstStepText = slackobject.SetupStepText().getText();
		System.out.println("Set up text is ===> " + actualFirstStepText);
		Assert.assertEquals(actualFirstStepText, "Set up Slack as your identity provider");
		ValidateConnectPageAssertion();
	}

	// Validate Step1 content for Slack Connect
		public void ValidateConnectPageAssertion() {
			String stepCountText = slackobject.StepCount().getText();
			System.out.println("Step count is ===> " + stepCountText);
			Assert.assertEquals(stepCountText, "Step 1 of 4");
		}
		
	// Validate 
		public void ValidateSlackCredFlow() throws InterruptedException {
			Thread.sleep(2000L);
			slackobject.SlackInputField().sendKeys("joinassemblypaidtest");
			slackobject.SlackContinueSubmitButton().click();
			slackobject.SlackEmailInputField().sendKeys("hema+paidtest@joinassembly.com");
			slackobject.slackPwdInputField().sendKeys("Assembly2020!");
			slackobject.slackSignInButton().click();
			ValidateSlackAppActualHeaderAssertion();
			Thread.sleep(1000);
			slackobject.AllowButton().click();
		}
		
		// Validate Slack App Actual header
		public void ValidateSlackAppActualHeaderAssertion() {
			String SlackActualHeader = slackobject.SlackSigninHeaderText().getText();
			Assert.assertEquals(SlackActualHeader,
					"Assembly v2 Dev is requesting permission to access the Joinassemblypaidtest Slack workspace");
		}
}