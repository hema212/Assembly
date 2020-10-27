package slacktestcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import PageObjects.SlackAppPageObjects;
import PageObjects.SlackIdentityObjects;
import resources.InitiateDriver;

//Execute this 7th once SlackIntegrationFlowValidation is done
public class SlackAppValidation extends InitiateDriver {
	public static Logger log = LogManager.getLogger(SlackAppValidation.class.getName());
	public SlackAppPageObjects slackappobject;
	public SlackIdentityObjects slackobject;

	@BeforeTest
	public void Init() throws FileNotFoundException, IOException {
		driver = initializeDriver();
		slackappobject = new SlackAppPageObjects(driver);
		slackobject = new SlackIdentityObjects(driver);

	}

	// Validate slack app url launch
	@Test(priority = 1)
	public void slackAppFunction() {
		driver.manage().window().maximize();
		driver.get("https://app.slack.com/client/T01ABD6LYCT/C01AB75N55Y");
		System.out.println("Slack App validation entry point");
		System.out.println("Testcase-1 passed since slack url is launched");
	}

	// Validate SSO sign in functionality
	@Test(priority = 2)
	public void SignInUsingSlack() throws InterruptedException {
		System.out.println("Signing in using SSO!!");
		Thread.sleep(2000L);
		slackobject.SlackInputField().sendKeys("joinassembly21");
		slackobject.SlackContinueSubmitButton().click();
		slackobject.SlackEmailInputField().sendKeys("hema+21@joinassembly.com");
		slackobject.slackPwdInputField().sendKeys("Assembly2020!");
		slackobject.slackSignInButton().click();
		System.out.println("Testcase-2 passed since Slack app sign in is successful");
	}

	// Validate Give Recognition using slack app

	@Test(priority = 3)
	public void ValidateGiveRecogUsingSlash() throws InterruptedException {
		slackappobject.inputTextField().sendKeys("/give-recognition-dev ");
		slackappobject.sendButton().isDisplayed();
		System.out.println("Is send button is Displayed? " + slackappobject.sendButton().isDisplayed());
		if (true) {
			slackappobject.sendButton().click();
		} 
		//slackappobject.continueWithoutAuthorizationButton().click();
		Thread.sleep(1000L);
		slackappobject.selectTeamMember().sendKeys("fabul");
		Thread.sleep(1000L);
		slackappobject.selectTeamMember().sendKeys(Keys.ENTER);
		slackappobject.giveRecognitionButton().click();
		String alertTextCapture = slackappobject.AlertText().getText();
		if (alertTextCapture.equalsIgnoreCase("Please complete this required field.")) {
			slackappobject.amountDropdown().click();
			slackappobject.customAmountText().click();
			slackappobject.customInputField().sendKeys("10");
			slackappobject.messageInputField().sendKeys("Posting here and testing");
			slackappobject.giveRecognitionButton().click();
		}
		Thread.sleep(1000L);
		System.out.println("The header text is " + slackappobject.recogSentHeader().getText());
		Assert.assertEquals(slackappobject.recogSentHeader().getText(), "Give recognition");
		System.out.println("Testcase-3 passed since recognition is given");
		slackappobject.closeButton().click();

	}

	// Validate removing existing user from slack when slack is connected and auto
		// approve is enabled
		@Test(priority = 4)
		public void RemoveExistingUser() throws InterruptedException {
			Thread.sleep(1000L);
			slackappobject.viewAllMemberButton().click();
			slackappobject.removeVijayButton().click();
			Thread.sleep(1000L);
			String actualRemovalHeader = slackappobject.removeModalHeader().getText();
			Assert.assertEquals(actualRemovalHeader, "Remove @vijay");
			System.out.println("Assertion passed since user Vijay is selected for removal");
			Thread.sleep(1000L);
			slackappobject.yesRemoveButton().click();
			System.out.println("Testcase-4 passed since user Vijay has been removed from channel");
		}
	
	// Validate adding user from slack when slack is connected and auto approve is
	// enabled
	@Test(priority = 5)
	public void AddNewUser() throws InterruptedException {
		Thread.sleep(1000L);
		slackappobject.addUser().click();
		slackappobject.addUserEmailInputField().sendKeys("pranesh+join21@joinassembly.com");
		Thread.sleep(1000L);
		slackappobject.addUserEmailInputField().sendKeys(Keys.ENTER);
		slackappobject.addButton().click();
		slackappobject.FinishedButton().click();
		System.out.println("Testcase-5 passed since user PRANESH has been added to the channel");
	}

	
}
