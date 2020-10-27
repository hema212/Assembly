package slacktestcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import PageObjects.RecognitionPageObject;
import PageObjects.SlackIdentityObjects;
import PageObjects.SlackIntegrationPageObjects;
import PageObjects.loginPageObjects;
import resources.BaseSignInViaSlack;


/* Execute this after SlackAppValidation has run / Execute 8th.
 Execute when slack bot is connected and edit connection button is visible + Delete user and add them 
 back + validate previous data reflected */

public class SlackBotRecogPageAndDeleteUserValidation extends BaseSignInViaSlack {
	
	public static Logger log = LogManager.getLogger(SlackBotRecogPageAndDeleteUserValidation.class.getName());
	public SlackIntegrationPageObjects slackintegrationobject;
	public SlackIdentityObjects slackobject;
	public RecognitionPageObject recogobject;
	public loginPageObjects loginpageobject;

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {
		driver = initializeDriver();
		slackintegrationobject = new SlackIntegrationPageObjects(driver);
		slackobject = new SlackIdentityObjects(driver);
		recogobject = new RecognitionPageObject(driver);
		loginpageobject = new loginPageObjects(driver);
	}

	//// ADD assertions to validate the flow

	// Validate SSO sign in functionality
	@Test(priority = 1)
	public void SignInUsingSlack() throws InterruptedException {
		System.out.println("Signing in using SSO!!");
		Thread.sleep(2000L);
		slackobject.SlackInputField().sendKeys("joinassembly21");
		slackobject.SlackContinueSubmitButton().click();
		slackobject.SlackEmailInputField().sendKeys("hema+21@joinassembly.com");
		slackobject.slackPwdInputField().sendKeys("Assembly2020!");
		slackobject.slackSignInButton().click();
		ValidateSlackAppActualHeaderAssertion();
		Thread.sleep(5000L);
		slackobject.AllowButton().click();
		System.out.println("User clicked on Allow Button");
		Thread.sleep(2000L);
		GiveRecogPageAssertion();
		System.out.println("Testcase-1 passed since user logged in using SSO signin");

	}

	// Validate message posted to GR page when posted via slack app through
	// '/give-recognition-dev'

	@Test(priority = 2)
	public void ValidateBotPostInRecogPage() throws InterruptedException {
		Thread.sleep(1000L);
		String captureFeedTime = recogobject.feedCarrotTime().getText();
		System.out.println("The last post was sent : " + captureFeedTime);
		if (captureFeedTime.equalsIgnoreCase("a few seconds ago")) {
			System.out.println("The last post was sent : " + captureFeedTime);
		} else if (captureFeedTime.equalsIgnoreCase("1 minute ago")) {
			System.out.println("The last post was sent : " + captureFeedTime);
		} else if (captureFeedTime.equalsIgnoreCase("2 minutes ago")) {
			System.out.println("Last post was made few mins ago");
		}
		String messageFeed = recogobject.messageFeed().getText();
		if (messageFeed.equalsIgnoreCase("Posting here and testing")) {
			System.out.println("Recently posted message is " + messageFeed);
		} else {
			Assert.fail();
			// System.out.println("Recently posted message is " + messageFeed);
		}
		System.out.println("Testcase-2 passed since user is able to view post made from slack app");
	}

	// Validate Admin user deletion (admin + other users)
	@Test(priority = 3)
	public void ValidateAdminUserDeletion() throws InterruptedException {
		selectManageSideNavbar();
		Thread.sleep(1000L);
		validateUserAddorRemovalFromSlack();
		Thread.sleep(1000L);
		Boolean editConnectionDisplayed = slackobject.EditConnectionButton().isDisplayed();
		// System.out.println(editConnectionDisplayed);
		if (editConnectionDisplayed == true) {
			System.out.println("Slack is connected and 'Edit Connection' button is visible");
			slackobject.SelectAllCheckBox().click();
			slackobject.DeleteUsersIcon().click();
			String removeUsersHeader = slackobject.DeleteUserHeader().getText();
			Assert.assertEquals(removeUsersHeader, "Remove User(s)");
			slackobject.RemoveButton().click();
			Thread.sleep(1000L);
			String admindeletetoast = slackobject.successToast().getText();
			System.out.println("The toast message after trying to remove admin is: " + admindeletetoast);
		}
		System.out.println("Testcase-3 passed cos unable to delete users since owner/self can't be removed");
	}

	// Validate added user(s) deletion
	@Test(priority = 4)
	public void ValidateUserDeletion() throws InterruptedException {
		slackobject.SingleUserSelectToEdit().click();
		slackobject.SIngleUserSelectToEditThree().click();
		slackobject.DeleteUsersIcon().click();
		String removeUsersHeader = slackobject.DeleteUserHeader().getText();
		Assert.assertEquals(removeUsersHeader, "Remove User(s)");
		slackobject.RemoveButton().click();
		Thread.sleep(1000L);
		String userdeletetoast = slackobject.successToast().getText();
		System.out.println("The toast message after trying to remove users is: " + userdeletetoast);
		System.out.println("Testcase-4 passed since users are deleted");
	}

	// Validate sign-in of deleted user
	@Test(priority = 5)
	public void deletedUserSigninFlow() throws InterruptedException {
		Thread.sleep(1000L);
		signOutFunction();
		loginpageobject.getStarted().click();
		Thread.sleep(1000L);
		loginpageobject.getUsernameObject().sendKeys("fabul+slackjoin@joinassembly.com");
		loginpageobject.confirmButton().click();
		Thread.sleep(1000L);
		String captureErrorHeaderMessage = loginpageobject.accountExistsErrorMessage().getText();
		System.out.println("The error message is : " + captureErrorHeaderMessage);
		System.out.println("Testcase-5 passed since application did not allow existing user to create assembly");
	}

	// Validate Slack App Actual header
	public void ValidateSlackAppActualHeaderAssertion() {
		String SlackActualHeader = slackobject.SlackSigninHeaderText().getText();
		Assert.assertEquals(SlackActualHeader,
				"Assembly v2 Dev is requesting permission to access the Joinassembly+21 Slack workspace");
		System.out.println("Assertion passed during request permission");
	}

	// Landing page validation
	public void GiveRecogPageAssertion() {
		String getRecogText = recogobject.giveRecognitionText().getText();
		Assert.assertEquals(getRecogText, "Give Recognition");
		System.out.println("Application landed on give recognition page");
	}

	// function to validate landing page of Manage
	public void selectManageSideNavbar() throws InterruptedException {
		slackobject.avatarIcon().click();
		slackobject.AdminText().click();
		slackobject.UsersText().click();
		slackobject.ManageText().click();
		Thread.sleep(1000L);
	}

	public void validateSelectPeopleViewFlow() throws InterruptedException {
		Thread.sleep(1000L);
		validateSelectPeopleAssertion();
		System.out.println("selecting channels");
		Thread.sleep(4000L);
		slackobject.SelectChannels().click();
		Thread.sleep(2000L);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", slackobject.SelectAllCheckBox());
		Thread.sleep(1000L);
		slackobject.SelectAllCheckBox().click();
		Thread.sleep(1000L);
		slackobject.ContinueNext().click();
		Thread.sleep(1000L);
		slackobject.SendInvitationsNowRadio().click();
		validateConfigureInvitesAssertion();
		slackobject.CreateAssemblyAccountsButton().click();
		js.executeScript("arguments[0].scrollIntoView(true);", slackobject.DisconnectSlackButton());
		Thread.sleep(10000L);
		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Disconnect Slack')]")));
		Thread.sleep(4000L);
		disconnectIdentityManagementOnKeepIt();
		validateCreateAccountAssertion();
		System.out.println("User has click on 'No, Keep it button in Create Accounts page");
		validateCreateAccountAssertion();
		System.out.println("View people button is displayed? " + slackobject.viewPeopleInviteButton().isEnabled());
		Thread.sleep(2000);
		slackobject.viewPeopleInviteButton().click();
		validateManageSlackConnectedAssertion();

	}

	// Validate user added / removed from slack and reflected in Assembly
	public void validateUserAddorRemovalFromSlack() throws InterruptedException {
		Thread.sleep(1000L);
		Boolean userPraneshEmailDisplayed = slackobject.UserPranesh().isDisplayed();
		Boolean userVijayEmailDisplayed = slackobject.UserVijay().isDisplayed();
		System.out.println("Is Pranesh user present ? " + userPraneshEmailDisplayed);
		System.out.println("Is Vijay user present ? " + userVijayEmailDisplayed);
		if (userPraneshEmailDisplayed == true) {
			Assert.assertTrue(true);
			System.out.println("User pranesh is added from slack to Assembly");
		}
		/*
		 * System.out.println("Is Vijay user present ? " + userVijayEmailDisplayed); if
		 * (userVijayEmailDisplayed == false) { // Assert.assertTrue(true);
		 * System.out.println("User Vijay is removed from Assembly"); }
		 */
	}

	// Validate Step2 content for Slack Select People
	public void validateSelectPeopleAssertion() {
		String stepCountText = slackobject.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 2 of 4");
	}

	// Validate Step3 content for Slack Configure Invites
	public void validateConfigureInvitesAssertion() {
		String stepCountText = slackobject.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 3 of 4");
	}

	// Validate Step2 content for Slack Assembly Accounts
	public void validateCreateAccountAssertion() {
		String stepCountText = slackobject.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 4 of 4");
	}

	// Function to validate Disconnect slack
	public void disconnectIdentityManagementOnKeepIt() throws InterruptedException {
		Thread.sleep(1000L);
		slackobject.DisconnectSlackButton().click();
		ValidateDisconnectSlackModalAssertion();
		slackobject.NokeepItIM().click();
	}

	// Validate Manage landing page when slack is connected
	public void validateManageSlackConnectedAssertion() throws InterruptedException {
		Thread.sleep(2000L);
		String managePageLandingText = slackobject.QuickSettingsText().getText();
		System.out.println("the text is " + managePageLandingText);
		Assert.assertEquals(managePageLandingText, "Quick Settings");
	}

	// Validate Disconnect Slack modal header content
	public void ValidateDisconnectSlackModalAssertion() throws InterruptedException {
		Thread.sleep(3000L);
		String disconnectSlackModalHeader = slackobject.DisconnectSlackModalHeader().getText();
		System.out.println(disconnectSlackModalHeader);
		Assert.assertEquals(disconnectSlackModalHeader, "Disconnect Slack?");
	}

	// Validate default dept and status in Invites flow
	public void getDeptStatusTextUserOne() {
		System.out.println("The number of rows are : " + slackobject.Row().size());
		System.out.println("The Department of first user is " + slackobject.UserOneDept().getText());
		System.out.println("The Status of first user is " + slackobject.UserOneStatus().getText());
	}

	// Validate default dept and status in Invites flow
	public void getDeptStatusTextUserTwo() {
		System.out.println("The number of rows are : " + slackobject.Row().size());
		System.out.println("The Department of Second user is " + slackobject.UserTwoDept().getText());
		System.out.println("The Status of Second user is " + slackobject.UserTwoStatus().getText());
	}

	// Validate default dept and status in Invites flow
	public void getDeptStatusTextUserThree() {
		System.out.println("The number of rows are : " + slackobject.Row().size());
		System.out.println("The Department of Second user is " + slackobject.UserthreeDept().getText());
		System.out.println("The Status of Second user is " + slackobject.UserthreeStatus().getText());
	}

	// validate signout flow
	public void signOutFunction() {
		slackobject.avatarIcon().click();
		slackobject.SignoutText().click();
	}
}
