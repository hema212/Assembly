package adptestcases;

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

import adpPageObjects.ADPIdentityObjects;
import signInViaSSO.ADPSsoSignIn;

public class ADPQueuedInviteFlowValidation extends ADPSsoSignIn {
	public static Logger log = LogManager.getLogger(ADPQueuedInviteFlowValidation.class.getName());
	public ADPIdentityObjects adppageobjects;

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {
		driver = initializeDriver();
		adppageobjects = new ADPIdentityObjects(driver);
	}

	// validate Manage landing page when ADP is not connected
	@Test(priority = 1)
	public void managePageSetup() throws InterruptedException {
		selectManageSideNavbar();
		validateManagePageAssertion();
		System.out.println("Testcase-1 passed since application landed on Manage page and Assertion passed");
	}

	// validate Cancel button click on cancel
	@Test(priority = 2)
	public void validateCancelButtonOnClick() throws InterruptedException {
		ADPIconPagevalidateOnClick();
		validateAuthorizeADP();
		System.out.println("Step count passed");
		Thread.sleep(3000L);
		adppageobjects.ContinueNext().click();
		Thread.sleep(10000L);
		System.out.println("Is cancel button enabled? " + adppageobjects.CancelButton().isEnabled());
		System.out.println("Is Continue with button is enabled? " + adppageobjects.ContinueNext().isEnabled());
		sureButtonClickvalidate();
		validateManagePageAssertion();
		System.out.println(
				"Testcase-2 passed since application landed on Manage page and Assertion passed after clicked on Cancel button in Connect page");
	}

	// validate connecting to ADP and disconnecting on click of cancel button and
	// click on sure button

	@Test(priority = 2)
	public void connectADPOnCancel() throws InterruptedException {
		ADPIconPagevalidateOnClick();
		validateAuthorizeADP();
		System.out.println("Step count passed");
		Thread.sleep(3000L);
		adppageobjects.ContinueNext().click();
		Thread.sleep(10000L);
		nevermindButtonvalidate();
		System.out.println("User has clicked on Nevermind button");
		Thread.sleep(1000L);
		validateSelectPeopleAssertion();
		sureButtonClickvalidate();
		System.out.println("User has clicked on Sure button");
		Thread.sleep(1000L); 
		validateManagePageAssertion();
		System.out.println("Testcase-3 passed since application connected to ADP by entering details and"
				+ " disonnected on click of I'm sure button after cancel button clicked");
	}

	// validate Queued invites flow when selected I'll send them later

	@Test(priority = 3)
	public void validateQueuedInvitesFlow() throws InterruptedException {
		ADPIconPagevalidateOnClick();
		validateAuthorizeADP();
		System.out.println("Step count passed");
		Thread.sleep(3000L);
		adppageobjects.ContinueNext().click();
		Thread.sleep(10000L);
		System.out.println("Selecting Everyone radio button option");
		adppageobjects.SelectAllUsers().click();
		validateSelectPeopleAssertion();
		Thread.sleep(6000L);
		adppageobjects.ContinueWithEveryoneButton().click();
	
		Thread.sleep(2000L);
		validateConfigureInvitesAssertion();
		adppageobjects.SendThemLaterRadio().click();
		Thread.sleep(1000L);
		System.out.println("Selected I'll send them later and admin approval Radio button option");
		adppageobjects.CreateAssemblyAccountsButton().click();

		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'View')]")));
		validateCreateAccountAssertion();
		System.out.println("Testcase-4 passed since application clicked on I'll send them later button");
	}

	// validate Queued invites flow on click of View Queued Invites Button

	@Test(priority = 4)
	public void validateQueuedInvitesFlowOnView() throws InterruptedException {
		//JavascriptExecutor js = (JavascriptExecutor) driver;
		//js.executeScript("window.scrollBy(0,1000)");

		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Disconnect ADP')]")));
		Thread.sleep(25000L);
		disconnectIdentityManagementOnKeepIt();
		System.out.println("User has click on 'No, Keep it button in Create Accounts page");
		validateCreateAccountAssertion();
		System.out.println(
				"View Queued Invites button is displayed? " + adppageobjects.viewPeopleInviteButton().isEnabled());
		System.out.println("Testcase-4 passed since Queued Invites flow is selected on choosing send them" + " later button");
		adppageobjects.viewPeopleInviteButton().click();
		System.out.println("Testcase-5 passed since application landed on Invites Page On Queued invite");
		String breadcrum = adppageobjects.InvitesBreadcrum().getText();
		Assert.assertEquals(breadcrum, "> Invites");
	}

	@Test(priority = 5)
	public void validateQuickSettings() throws InterruptedException {
		disconnectADPThroughQuickSettings();
		Thread.sleep(1000L);
		validateManagePageAssertion();
		String breadcrum = adppageobjects.InvitesBreadcrum().getText();
		Assert.assertEquals(breadcrum, "> Manage");
		System.out.println("Testcase-6 passed since application landed on Manage page after disconnecting from quick settings");
		driver.close();
	}

	// Function to validate landing page of Manage
	public void selectManageSideNavbar() throws InterruptedException {
		adppageobjects.avatarIcon().click();
		adppageobjects.AdminText().click();
		Thread.sleep(1000L);
		adppageobjects.UsersText().click();
		adppageobjects.ManageText().click();
		Thread.sleep(1000L);
	}

	// Function to validate Cancel button on all pages
	public void cancelButtonClickvalidate() throws InterruptedException {
		adppageobjects.CancelButton().click();
		Thread.sleep(1000L);
		validateManagePageAssertion();
	}

	// Function to validate 'I'm sure' on click of cancel button
	public void sureButtonClickvalidate() throws InterruptedException {
		Thread.sleep(2000L);
		adppageobjects.CancelButton().click();
		Thread.sleep(1000L);
		validateCancelModalHeaderAssertion();
		adppageobjects.ImSureButton().click();
		Thread.sleep(1000L);
		validateManagePageAssertion();
	}

	// Function to validate 'Nevermind' on click of cancel button
	public void nevermindButtonvalidate() throws InterruptedException {
		Thread.sleep(5000L);
		adppageobjects.CancelButton().click();
		Thread.sleep(2000L);
		validateCancelModalHeaderAssertion();
		adppageobjects.NevermindButton().click();
		validateSelectPeopleAssertion();

	}

	// Function to validate ADP first page - Setup/Connect
	public void ADPIconPagevalidateOnClick() throws InterruptedException {
		adppageobjects.CompleteInstallation().click();
		Thread.sleep(2000L);
		/*
		 * String actualFirstStepText = adppageobjects.SetupStepText().getText();
		 * System.out.println("Set up text is ===> " + actualFirstStepText);
		 * Assert.assertEquals(actualFirstStepText,
		 * "Set up ADP as your identity provider"); validateConnectPageAssertion();
		 */
	}

	// Function to validate Disconnect ADP
	public void disconnectIdentityManagementOnKeepIt() throws InterruptedException {
		adppageobjects.DisconnectADPButton().click();
		validateDisconnectADPModalAssertion();
		adppageobjects.NokeepItIM().click();
	}

	// Function to validate Disconnect ADP
	public void disconnectIdentityManagementOnYes() throws InterruptedException {
		adppageobjects.DisconnectADPButton().click();
		validateDisconnectADPModalAssertion();
		adppageobjects.YesDisconnectIM().click();
	}

	// Assertion Validation
	// validate Sign in to workspace landing page
	public void validateSignInADPAppAssertion() {
		String ADPSignInWorkspaceText = adppageobjects.ADPSignInWorkspaceText().getText();
		System.out.println(ADPSignInWorkspaceText);
		Assert.assertEquals(ADPSignInWorkspaceText, "Sign in to your workspace");
	}

	// validate ADP App Actual header
	public void validateADPAppActualHeaderAssertion() {
		String ADPActualHeader = adppageobjects.ADPSigninHeaderText().getText();
		Assert.assertEquals(ADPActualHeader,
				"Assembly v2 Dev is requesting permission to access the Joinassembly+21 ADP workspace");
	}

	// validate Manage landing page when ADP is not connected
	public void validateManagePageAssertion() throws InterruptedException {
		Thread.sleep(5000L);
		String managePageLandingText = adppageobjects.ConnectIdentityText().getText();
		System.out.println("the text is " + managePageLandingText);
		Assert.assertEquals(managePageLandingText, "App installed");
	}

	// validate Manage landing page when ADP is connected
	public void validateManageADPConnectedAssertion() throws InterruptedException {
		Thread.sleep(2000L);
		String managePageLandingText = adppageobjects.QuickSettingsText().getText();
		System.out.println("the text is " + managePageLandingText);
		Assert.assertEquals(managePageLandingText, "Quick Settings");
	}

	// validate Step1 content for ADP Connect
	public void validateConnectPageAssertion() {
		String stepCountText = adppageobjects.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 1 of 6");
	}

	// validate Step2 content for ADP Connect
	public void validateSetupInfo() {
		String stepCountText = adppageobjects.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 2 of 6");
	}

	// validate Step3 content for ADP Connect
	public void validateAuthorizeADP() {
		String stepCountText = adppageobjects.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 3 of 6");
	}

	// validate Step4 content for ADP Select People
	public void validateSelectPeopleAssertion() {
		String stepCountText = adppageobjects.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 4 of 6");
	}

	// validate Step5 content for ADP Configure Invites
	public void validateConfigureInvitesAssertion() {
		String stepCountText = adppageobjects.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 5 of 6");
	}

	// validate Step6 content for ADP Assembly Accounts
	public void validateCreateAccountAssertion() {
		String stepCountText = adppageobjects.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 6 of 6");
	}

	// validate Cancel modal header content
	public void validateCancelModalHeaderAssertion() {
		String actualCancelHeaderModalText = adppageobjects.CancelModalHeaderh5().getText();
		System.out.println("The header of Cancel modal is  " + actualCancelHeaderModalText);
		Assert.assertEquals(actualCancelHeaderModalText, "Are you sure you want to cancel ADP setup?");
	}

	// validate Disconnect ADP modal header content
	public void validateDisconnectADPModalAssertion() {
		String disconnectADPModalHeader = adppageobjects.DisconnectADPModalHeader().getText();
		System.out.println(disconnectADPModalHeader);
		Assert.assertEquals(disconnectADPModalHeader, "Disconnect ADP?");
	}

	// validate Sign in with ADP flow
	public void signInWithSSOFunction() throws InterruptedException {
		adppageobjects.SignInWithADPSSOClick().click();
		adppageobjects.ADPInputField().sendKeys("joinassembly21");
		adppageobjects.ADPContinueSubmitButton().click();
		adppageobjects.ADPEmailInputField().sendKeys("hema+21@joinassembly.com");
		adppageobjects.ADPPwdInputField().sendKeys("Assembly2020!");
		adppageobjects.ADPSignInButton().click();
		validateADPAppActualHeaderAssertion();
		Thread.sleep(7000L);
		adppageobjects.AllowButton().click();
	}

	// disconnect ADP using Quick settings
	public void disconnectADPThroughQuickSettings() throws InterruptedException {
		adppageobjects.QuickSettingsText().click();
		System.out.println(
				"The state of Save settings before change is " + adppageobjects.SaveSettingsButton().isEnabled());
		adppageobjects.AdminApproveRadio().click();
		System.out.println(
				"The state of Save settings after change is " + adppageobjects.SaveSettingsButton().isEnabled());
		adppageobjects.CloseButton().click();
		adppageobjects.QuickSettingsText().click();
		adppageobjects.SaveSettingsButton().click();
		disconnectIdentityManagementOnYes();
		System.out.println("Testcase-6 passed since ADP is disconnected from settings");
	}

	// validate valid card details card
	public void validateCardValidDetails() throws InterruptedException {
		adppageobjects.CardNameInputField().sendKeys("abc");
		Thread.sleep(2000L);
		this.driver.switchTo().frame(adppageobjects.IframeCardNumber());
		adppageobjects.CardNumberInputField().sendKeys("4242424242424242");
		driver.switchTo().defaultContent();
		this.driver.switchTo().frame(adppageobjects.IframeExpiryDate());
		adppageobjects.CardExpiryDateField().sendKeys("1222");
		driver.switchTo().defaultContent();
		this.driver.switchTo().frame(adppageobjects.IframeCvcNumber());
		adppageobjects.CardCvcField().sendKeys("123");
		Thread.sleep(1000L);
		driver.switchTo().defaultContent();
		Thread.sleep(3000L);
		adppageobjects.ApplyButton().click();
		if (adppageobjects.SetUpAlertMessage().getText() == "Please enter Promo Code") {
			adppageobjects.PromCodeInputField().sendKeys("100OFF");
			adppageobjects.ApplyButton().click();
			Boolean successToast = adppageobjects.successToast().isDisplayed();
			System.out.println("Is promo applied successfully " + successToast);
		}
		adppageobjects.SetUpPaymentNextButton().click();
		adppageobjects.ReviewBillConfirm().click();
		adppageobjects.ContinueWithEveryoneButton().click();
		adppageobjects.SendInvitationsNowRadio().click();
		adppageobjects.CreateAssemblyAccountsButton().click();
		adppageobjects.viewPeopleInviteButton().click();

	}

	// validate quick settings flow
	public void validateQuickSettingsADPFunction() {
		adppageobjects.QuickSettingsText().click();
		Boolean saveSettingsActive = adppageobjects.SaveSettingsButton().isEnabled();
		System.out.println(saveSettingsActive);
		if (saveSettingsActive == true) {
			System.out.println("The Save Settings button is disabled");
		} else {
			adppageobjects.AdminApproveRadio().click();
			adppageobjects.SaveSettingsButton().click();
			adppageobjects.CloseButton().click();

		}

	}

	// validate ADPapp connection flow
	public void ADPWorkspaceFlow() throws InterruptedException {
		adppageobjects.ADPInputField().sendKeys("joinassembly21");
		adppageobjects.ADPContinueSubmitButton().click();
		adppageobjects.ADPEmailInputField().sendKeys("hema+21@joinassembly.com");
		adppageobjects.ADPPwdInputField().sendKeys("Assembly2020!");
		adppageobjects.ADPSignInButton().click();
		validateADPAppActualHeaderAssertion();
		Thread.sleep(4000L);
		adppageobjects.AllowButton().click();

	}

}
