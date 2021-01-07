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

import resources.Base;
import slackPageObjects.SlackIdentityObjects;

//Execute this First(1st) when Slack is not connected and doesn't have slack users in it
public class SlackQueuedInviteFlowValidation extends Base {
	public static Logger log = LogManager.getLogger(SlackQueuedInviteFlowValidation.class.getName());
	public SlackIdentityObjects slackobject;

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {
		driver = initializeDriver();
		slackobject = new SlackIdentityObjects(driver);
	}

	// validate Manage landing page when slack is not connected
	@Test(priority = 0)
	public void managePageSetup() throws InterruptedException {
		selectManageSideNavbar();
		validateManagePageAssertion();
		log.info("Testcase-1 passed since application landed on Manage page and Assertion passed");
	}

	// validate Cancel button click on cancel
	@Test(priority = 1)
	public void validateCancelButtonOnClick() throws InterruptedException {
		slackIconPagevalidateOnClick();
		validateConnectPageAssertion();
		log.info("Is cancel button Enabled in Connect page? " + slackobject.CancelButton().isEnabled());
		log.info(
				"Is Connect Slack button Enabled in Connect page? " + slackobject.ConnectSlackButton().isEnabled());
		cancelButtonClickvalidate();
		validateManagePageAssertion();
		log.info(
				"Testcase-2 passed since application landed on Manage page and Assertion passed after clicked on Cancel button in Connect page");
	}

	// validate connecting to slack and disconnecting on click of cancel button and
	// click on sure button
	@Test(priority = 2)
	public void connectSlackOnCancel() throws InterruptedException {
		slackIconPagevalidateOnClick();
		slackobject.ConnectSlackButton().click();
		validateSignInSlackAppAssertion();
		slackWorkspaceFlow();
		nevermindButtonvalidate();
		log.info("User has clicked on Nevermind button");
		Thread.sleep(1000L);
		// validateSelectPeopleAssertion();
		sureButtonClickvalidate();
		log.info("User has clicked on Sure button");
		Thread.sleep(1000L);
		// validateConnectPageAssertion();
		log.info("Testcase-3 passed since application connected to slack by entering details and"
				+ " disonnected on click of I'm sure button after cancel button clicked");
	}

	// validate Queued invites flow when selected I'll send them later
	@Test(priority = 3)
	public void validateQueuedInvitesFlow() throws InterruptedException {
		slackIconPagevalidateOnClick();
		slackobject.ConnectSlackButton().click();
		validateSlackAppActualHeaderAssertion();
		Thread.sleep(3000L);
		slackobject.AllowButton().click();
		Thread.sleep(4000L);
		validateSelectPeopleAssertion();
		log.info("Selected Everyone radio button option which is default when user lands");
		Thread.sleep(3000L);
		slackobject.ContinueWithEveryoneButton().click();
		validateConfigureInvitesAssertion();
		slackobject.SendThemLaterRadio().click();
		Thread.sleep(1000L);
		log.info("Selected I'll send them later and admin approval Radio button option");
		slackobject.CreateAssemblyAccountsButton().click();
		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'View')]")));
		validateCreateAccountAssertion();
		log.info("Testcase-4 passed since application clicked on I'll send them later button");
	}

	// validate Queued invites flow on click of View Queued Invites Button
	@Test(priority = 4)
	public void validateQueuedInvitesFlowOnView() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1000)");
		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Disconnect Slack')]")));
		Thread.sleep(9000L);
		disconnectIdentityManagementOnKeepIt();
		log.info("User has click on 'No, Keep it button in Create Accounts page");
		validateCreateAccountAssertion();
		log.info(
				"View Queued Invites button is displayed? " + slackobject.viewPeopleInviteButton().isEnabled());
		log.info(
				"Testcase-4 passed since Queued Invites flow is selected on choosing send them" + " later button");
		slackobject.viewPeopleInviteButton().click();
		log.info("Testcase-5 passed since application landed on Invites Page On Queued invite");
		String breadcrum = slackobject.InvitesBreadcrum().getText();
		Assert.assertEquals(breadcrum, "> Invites");
	}

	@Test(priority = 5)
	public void validateQuickSettings() throws InterruptedException {
		disconnectSlackThroughQuickSettings();
		Thread.sleep(1000L);
		validateManagePageAssertion();
		String breadcrum = slackobject.InvitesBreadcrum().getText();
		Assert.assertEquals(breadcrum, "> Manage");
		log.info(
				"Testcase-6 passed since application landed on Manage page after disconnecting from quick settings");
		driver.close();
	}

	// Function to validate landing page of Manage
	public void selectManageSideNavbar() throws InterruptedException {
		slackobject.avatarIcon().click();
		slackobject.AdminText().click();
		Thread.sleep(1000L);
		slackobject.UsersText().click();
		slackobject.ManageText().click();
		Thread.sleep(1000L);
	}

	// Function to validate Cancel button on all pages
	public void cancelButtonClickvalidate() throws InterruptedException {
		slackobject.CancelButton().click();
		Thread.sleep(1000L);
		validateManagePageAssertion();
	}

	// Function to validate 'I'm sure' on click of cancel button
	public void sureButtonClickvalidate() throws InterruptedException {
		slackobject.CancelButton().click();
		Thread.sleep(1000L);
		validateCancelModalHeaderAssertion();
		slackobject.ImSureButton().click();
		Thread.sleep(1000L);
		validateManagePageAssertion();
	}

	// Function to validate 'Nevermind' on click of cancel button
	public void nevermindButtonvalidate() throws InterruptedException {
		Thread.sleep(5000L);
		slackobject.CancelButton().click();
		Thread.sleep(2000L);
		validateCancelModalHeaderAssertion();
		slackobject.NevermindButton().click();
		validateSelectPeopleAssertion();

	}

	// Function to validate Slack first page - Setup/Connect
	public void slackIconPagevalidateOnClick() throws InterruptedException {
		slackobject.SlackIdentityIcon().click();
		Thread.sleep(2000L);
		String actualFirstStepText = slackobject.SetupStepText().getText();
		log.info("Set up text is ===> " + actualFirstStepText);
		Assert.assertEquals(actualFirstStepText, "Set up Slack as your identity provider");
		validateConnectPageAssertion();
	}

	// Function to validate Disconnect slack
	public void disconnectIdentityManagementOnKeepIt() throws InterruptedException {
		slackobject.DisconnectSlackButton().click();
		validateDisconnectSlackModalAssertion();
		slackobject.NokeepItIM().click();
	}

	// Function to validate Disconnect slack
	public void disconnectIdentityManagementOnYes() throws InterruptedException {
		slackobject.DisconnectSlackButton().click();
		validateDisconnectSlackModalAssertion();
		slackobject.YesDisconnectIM().click();
	}

	// Assertion Validation
	// validate Sign in to workspace landing page
	public void validateSignInSlackAppAssertion() {
		String SlackSignInWorkspaceText = slackobject.SlackSignInWorkspaceText().getText();
		log.info(SlackSignInWorkspaceText);
		Assert.assertEquals(SlackSignInWorkspaceText, "Sign in to your workspace");
	}

	// validate Slack App Actual header
	public void validateSlackAppActualHeaderAssertion() {
		String SlackActualHeader = slackobject.SlackSigninHeaderText().getText();
		Assert.assertEquals(SlackActualHeader,
				"Assembly v2 Dev is requesting permission to access the Joinassembly+21 Slack workspace");
	}

	// validate Manage landing page when slack is not connected
	public void validateManagePageAssertion() throws InterruptedException {
		Thread.sleep(2000L);
		String managePageLandingText = slackobject.ConnectIdentityText().getText();
		log.info("the text is " + managePageLandingText);
		Assert.assertEquals(managePageLandingText, "Connect your identity provider");
	}

	// validate Manage landing page when slack is connected
	public void validateManageSlackConnectedAssertion() throws InterruptedException {
		Thread.sleep(2000L);
		String managePageLandingText = slackobject.QuickSettingsText().getText();
		log.info("the text is " + managePageLandingText);
		Assert.assertEquals(managePageLandingText, "Quick Settings");
	}

	// validate Step1 content for Slack Connect
	public void validateConnectPageAssertion() {
		String stepCountText = slackobject.StepCount().getText();
		log.info("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 1 of 4");
	}

	// validate Step2 content for Slack Select People
	public void validateSelectPeopleAssertion() {
		String stepCountText = slackobject.StepCount().getText();
		log.info("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 2 of 4");
	}

	// validate Step3 content for Slack Configure Invites
	public void validateConfigureInvitesAssertion() {
		String stepCountText = slackobject.StepCount().getText();
		log.info("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 3 of 4");
	}

	// validate Step2 content for Slack Assembly Accounts
	public void validateCreateAccountAssertion() {
		String stepCountText = slackobject.StepCount().getText();
		log.info("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 4 of 4");
	}

	// validate Cancel modal header content
	public void validateCancelModalHeaderAssertion() {
		String actualCancelHeaderModalText = slackobject.CancelModalHeader().getText();
		log.info("The header of Cancel modal is  " + actualCancelHeaderModalText);
		Assert.assertEquals(actualCancelHeaderModalText, "Are you sure you want to cancel Slack setup?");
	}

	// validate Disconnect Slack modal header content
	public void validateDisconnectSlackModalAssertion() {
		String disconnectSlackModalHeader = slackobject.DisconnectSlackModalHeader().getText();
		log.info(disconnectSlackModalHeader);
		Assert.assertEquals(disconnectSlackModalHeader, "Disconnect Slack?");
	}

	// validate Sign in with slack flow
	public void signInWithSSOFunction() throws InterruptedException {
		slackobject.SignInWithSlackSSOClick().click();
		slackobject.SlackInputField().sendKeys("joinassembly21");
		slackobject.SlackContinueSubmitButton().click();
		slackobject.SlackEmailInputField().sendKeys("hema+21@joinassembly.com");
		slackobject.slackPwdInputField().sendKeys("Assembly2020!");
		slackobject.slackSignInButton().click();
		validateSlackAppActualHeaderAssertion();
		Thread.sleep(7000L);
		slackobject.AllowButton().click();
	}

	// disconnect slack using Quick settings
	public void disconnectSlackThroughQuickSettings() throws InterruptedException {
		slackobject.QuickSettingsText().click();
		System.out
				.println("The state of Save settings before change is " + slackobject.SaveSettingsButton().isEnabled());
		slackobject.AdminApproveRadio().click();
		System.out
				.println("The state of Save settings after change is " + slackobject.SaveSettingsButton().isEnabled());
		slackobject.CloseButton().click();
		slackobject.QuickSettingsText().click();
		slackobject.SaveSettingsButton().click();
		disconnectIdentityManagementOnYes();
		log.info("Testcase-6 passed since slack is disconnected from settings");
	}

	// validate valid card details card
	public void validateCardValidDetails() throws InterruptedException {
		slackobject.CardNameInputField().sendKeys("abc");
		Thread.sleep(2000L);
		this.driver.switchTo().frame(slackobject.IframeCardNumber());
		slackobject.CardNumberInputField().sendKeys("4242424242424242");
		driver.switchTo().defaultContent();
		this.driver.switchTo().frame(slackobject.IframeExpiryDate());
		slackobject.CardExpiryDateField().sendKeys("1222");
		driver.switchTo().defaultContent();
		this.driver.switchTo().frame(slackobject.IframeCvcNumber());
		slackobject.CardCvcField().sendKeys("123");
		Thread.sleep(1000L);
		driver.switchTo().defaultContent();
		Thread.sleep(3000L);
		slackobject.ApplyButton().click();
		if (slackobject.SetUpAlertMessage().getText() == "Please enter Promo Code") {
			slackobject.PromCodeInputField().sendKeys("100OFF");
			slackobject.ApplyButton().click();
			Boolean successToast = slackobject.successToast().isDisplayed();
			log.info("Is promo applied successfully " + successToast);
		}
		slackobject.SetUpPaymentNextButton().click();
		slackobject.ReviewBillConfirm().click();
		slackobject.ContinueWithEveryoneButton().click();
		slackobject.SendInvitationsNowRadio().click();
		slackobject.CreateAssemblyAccountsButton().click();
		slackobject.viewPeopleInviteButton().click();

	}

	// validate quick settings flow
	public void validateQuickSettingsSlackFunction() {
		slackobject.QuickSettingsText().click();
		Boolean saveSettingsActive = slackobject.SaveSettingsButton().isEnabled();
		log.info(saveSettingsActive);
		if (saveSettingsActive == true) {
			log.info("The Save Settings button is disabled");
		} else {
			slackobject.AdminApproveRadio().click();
			slackobject.SaveSettingsButton().click();
			slackobject.CloseButton().click();

		}

	}

	// validate slackapp connection flow
	public void slackWorkspaceFlow() throws InterruptedException {
		slackobject.SlackInputField().sendKeys("joinassembly21");
		slackobject.SlackContinueSubmitButton().click();
		slackobject.SlackEmailInputField().sendKeys("hema+21@joinassembly.com");
		slackobject.slackPwdInputField().sendKeys("Assembly2020!");
		slackobject.slackSignInButton().click();
		validateSlackAppActualHeaderAssertion();
		Thread.sleep(4000L);
		slackobject.AllowButton().click();

	}

}
