package gsuitetestcases;

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

import giveRecognitionPageObjects.RecognitionPageObject;
import gsuitePageObjects.GooglePageObjects;
import signInViaSSO.GoogleSsoSignIn;

//Execute this First(1st) when GSuite is not connected and doesn't have GSuite users in it
public class GsuiteQueuedInviteFlowValidation extends GoogleSsoSignIn {
	public static Logger log = LogManager.getLogger(GsuiteQueuedInviteFlowValidation.class.getName());
	public GooglePageObjects googleobject;
	public RecognitionPageObject recogobject;

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {
		driver = initializeDriver();
		googleobject = new GooglePageObjects(driver);
		recogobject = new RecognitionPageObject(driver);
	}

	// validate Manage landing page when GSuite is not connected
	@Test(priority = 1)
	public void managePageSetup() throws InterruptedException {
		// System.out.println("Landed on Give Recog page");
		selectManageSideNavbar();
		Thread.sleep(1000L);
		validateManagePageAssertion();
		System.out.println("Testcase-1 passed since application landed on Manage page and Assertion passed");
	}

	// validate Cancel button click on

	@Test(priority = 2)
	public void validateCancelButtonOnClick() throws InterruptedException {
		GsuiteIconPagevalidateOnClick();
		validateConnectPageAssertion();
		System.out.println("Is cancel button Enabled in Connect page? " + googleobject.CancelButton().isEnabled());
		System.out.println("Is Connect Gsuite 365 button Enabled in Connect page? "
				+ googleobject.ConnectGSuiteButton().isEnabled());
		cancelButtonClickvalidate();
		validateManagePageAssertion();
		System.out.println(
				"Testcase-2 passed since application landed on Manage page and Assertion passed after clicked on Cancel button in Connect page");
	}

	// validate connecting to GSuite and disconnecting on click of cancel button
	// and // click on sure button

	@Test(priority = 3)
	public void connectGSuiteOnCancel() throws InterruptedException {
		GsuiteIconPagevalidateOnClick();
		Thread.sleep(2000L);
		googleobject.ConnectGSuiteButton().click();
		Thread.sleep(4000L);
		GSuiteWorkspaceFlow();
		Thread.sleep(2000L);
		nevermindButtonvalidate();
		System.out.println("User has clicked on Nevermind button");
		Thread.sleep(1000L); // validateSelectPeopleAssertion();
		sureButtonClickvalidate();
		System.out.println("User has clicked on Sure button");
		System.out.println("The success toast is :" + googleobject.successToast().getText());
		Thread.sleep(1000L);
		// validateConnectPageAssertion();
		System.out.println("Testcase-3 passed since application connected to Gsuite 365 by entering details and"
				+ " disonnected on click of I'm sure button after cancel button clicked");

	}

	// validate Queued invites flow when selected I'll send them later

	@Test(priority = 4)
	public void validateQueuedInvitesFlow() throws InterruptedException {
		GsuiteIconPagevalidateOnClick();
		Thread.sleep(2000L);
		googleobject.ConnectGSuiteButton().click();
		Thread.sleep(2000L);
		GSuiteWorkspaceFlow();
		Thread.sleep(1000L);
		validateSelectPeopleAssertion();
		System.out.println("Selected Everyone radio button option which is default when user lands");
		Thread.sleep(1000L);
		System.out.println("Is everyone radio button selected ? " + googleobject.SelectAllUsers().isSelected());
		googleobject.ContinueWithEveryoneButton().click();
		validateConfigureInvitesAssertion();
		Thread.sleep(1000L);
		System.out.println("Is Send Invitations now radio option selecetd ? "
				+ googleobject.SendInvitationsNowRadio().isSelected());
		System.out.println("Is Auto approve radio option selected ? " + googleobject.AutoApproveRadio().isSelected());
		if (true) {
			googleobject.SendThemLaterRadio().click();
		}
		Thread.sleep(1000L);
		System.out.println("Selected I'll send them later and admin approval Radio button option");
		googleobject.CreateAssemblyAccountsButton().click();

		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'View')]")));
		validateCreateAccountAssertion();
		System.out.println(
				"Testcase-4 passed since application clicked on I'll send them later button and landed on Create Accounts page");
	}

	// validate Queued invites flow on click of View Queued Invites Button

	@Test(priority = 5)
	public void validateQueuedInvitesFlowOnView() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,2000)");
		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Disconnect G Suite')]")));
		Thread.sleep(9000L);
		disconnectIdentityManagementOnKeepIt();
		System.out.println("User has click on 'No, Keep it button in Create Accounts page");
		validateCreateAccountAssertion();
		System.out.println(
				"View Queued Invites button is displayed? " + googleobject.viewPeopleInviteButton().isEnabled());
		System.out.println(
				"Testcase-4 passed since Queued Invites flow is selected on choosing send them" + " later button");
		Thread.sleep(1000L);
		googleobject.viewPeopleInviteButton().click();
		System.out.println("Testcase-5 passed since application landed on Invites Page On Queued invite");
		Thread.sleep(1000L);
		String breadcrum = googleobject.InvitesBreadcrum().getText();
		Assert.assertEquals(breadcrum, "> Invites");

	}

	@Test(priority = 6)
	public void validateQuickSettings() throws InterruptedException {
		disconnectGSuiteThroughQuickSettings();
		Thread.sleep(1000L);
		validateManagePageAssertion();
		String breadcrum = googleobject.InvitesBreadcrum().getText();
		Assert.assertEquals(breadcrum, "> Manage");
		System.out.println(
				"Testcase-6 passed since application landed on Manage page after disconnecting from quick settings");
	}

	// Function to validate landing page of Manage
	public void selectManageSideNavbar() throws InterruptedException {
		// System.out.println("Calling this function");
		Thread.sleep(1000L);
		googleobject.avatarIcon().click();
		googleobject.AdminText().click();
		googleobject.UsersText().click();
		googleobject.ManageText().click();
		Thread.sleep(1000L);

	}

	// Function to validate Cancel button on all pages
	public void cancelButtonClickvalidate() throws InterruptedException {
		googleobject.CancelButton().click();
		Thread.sleep(1000L);
	}

	// Function to validate 'I'm sure' on click of cancel button
	public void sureButtonClickvalidate() throws InterruptedException {
		googleobject.CancelButton().click();
		Thread.sleep(1000L);
		validateCancelModalHeaderAssertion();
		googleobject.ImSureButton().click();
		Thread.sleep(1000L);
		validateManagePageAssertion();
	}

	// Function to validate 'Nevermind' on click of cancel button
	public void nevermindButtonvalidate() throws InterruptedException {
		Thread.sleep(5000L);
		googleobject.CancelButton().click();
		Thread.sleep(2000L);
		validateCancelModalHeaderAssertion();
		googleobject.NevermindButton().click();
		validateSelectPeopleAssertion();

	}

	// Function to validate GSuite first page - Setup/Connect
	public void GsuiteIconPagevalidateOnClick() throws InterruptedException {
		googleobject.GSuiteIcon().click();
		Thread.sleep(2000L);
		String actualFirstStepText = googleobject.SetupStepText().getText();
		System.out.println("Set up text is ===> " + actualFirstStepText);
		Assert.assertEquals(actualFirstStepText, "Set up G Suite as your identity provider");

	}

	// Function to validate DisConnect Gsuite 365
	public void disconnectIdentityManagementOnKeepIt() throws InterruptedException {
		Thread.sleep(2000L);
		System.out.println("Trying to disconnect");
		Thread.sleep(2000L);
		googleobject.DisconnectGSuiteButton().click();
		validateDisconnectGSuiteModalAssertion();
		googleobject.NokeepItIM().click();
	}

	// Function to validate DisConnect Gsuite 365
	public void disconnectIdentityManagementOnYes() throws InterruptedException {
		googleobject.DisconnectGSuiteButton().click();
		validateDisconnectGSuiteModalAssertion();
		googleobject.YesDisconnectIM().click();
	}

	// Assertion Validation
	// validate Sign in to workspace landing page
	public void validateSignInGSuiteAppAssertion() throws InterruptedException {
		Thread.sleep(3000L);
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		Thread.sleep(5000L);
		String GsuiteSignInWorkspaceText = googleobject.GSuiteSigninHeaderText().getText();
		System.out.println(GsuiteSignInWorkspaceText);
		Assert.assertEquals(GsuiteSignInWorkspaceText, "Choose an account from my.joinassembly.com");
	}

	// validate Manage landing page when GSuite is not connected
	public void validateManagePageAssertion() throws InterruptedException {
		Thread.sleep(2000L);
		String managePageLandingText = googleobject.ConnectIdentityText().getText();
		System.out.println("the text is " + managePageLandingText);
		Assert.assertEquals(managePageLandingText, "Connect your identity provider");
	}

	// validate Manage landing page when GSuite is connected
	public void validateManageGSuiteConnectedAssertion() throws InterruptedException {
		Thread.sleep(2000L);
		String managePageLandingText = googleobject.QuickSettingsText().getText();
		System.out.println("the text is " + managePageLandingText);
		Assert.assertEquals(managePageLandingText, "Quick Settings");
	}

	// validate Step1 content for GSuite Connect
	public void validateConnectPageAssertion() {
		String stepCountText = googleobject.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 1 of 4");
	}

	// validate Step2 content for GSuite Select People
	public void validateSelectPeopleAssertion() {
		String stepCountText = googleobject.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 2 of 4");
	}

	// validate Step3 content for GSuite Configure Invites
	public void validateConfigureInvitesAssertion() {
		String stepCountText = googleobject.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 3 of 4");
	}

	// validate Step2 content for GSuite Assembly Accounts
	public void validateCreateAccountAssertion() {
		String stepCountText = googleobject.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 4 of 4");
	}

	// validate Cancel modal header content
	public void validateCancelModalHeaderAssertion() {
		String actualCancelHeaderModalText = googleobject.CancelModalHeader().getText();
		System.out.println("The header of Cancel modal is  " + actualCancelHeaderModalText);
		Assert.assertEquals(actualCancelHeaderModalText, "Are you sure you want to cancel G Suite setup?");
	}

	// validate DisConnect Gsuite 365 modal header content
	public void validateDisconnectGSuiteModalAssertion() {
		String disconnectGSuiteModalHeader = googleobject.DisconnectGSuiteModalHeader().getText();
		System.out.println(disconnectGSuiteModalHeader);
		Assert.assertEquals(disconnectGSuiteModalHeader, "Disconnect GSuite?");
	}

	// disConnect Gsuite 365 using Quick settings
	public void disconnectGSuiteThroughQuickSettings() throws InterruptedException {
		googleobject.QuickSettingsText().click();
		System.out.println(
				"The state of Save settings before change is " + googleobject.SaveSettingsButton().isEnabled());
		System.out.println("Is Auto approve radio option selected ? " + googleobject.AutoApproveRadio().isSelected());
		googleobject.AdminApproveRadio().click();
		System.out
				.println("The state of Save settings after change is " + googleobject.SaveSettingsButton().isEnabled());
		googleobject.CloseButton().click();
		googleobject.QuickSettingsText().click();
		googleobject.SaveSettingsButton().click();
		disconnectIdentityManagementOnYes();
		System.out.println("Testcase-6 passed since GSuite is disconnected from settings");
	}

	// validate signout flow
	public void signOutFunction() {
		googleobject.avatarIcon().click();
		googleobject.SignoutText().click();
	}

	// validate valid card details card
	public void validateCardValidDetails() throws InterruptedException {
		googleobject.CardNameInputField().sendKeys("abc");
		Thread.sleep(2000L);
		this.driver.switchTo().frame(googleobject.IframeCardNumber());
		googleobject.CardNumberInputField().sendKeys("4242424242424242");
		driver.switchTo().defaultContent();
		this.driver.switchTo().frame(googleobject.IframeExpiryDate());
		googleobject.CardExpiryDateField().sendKeys("1222");
		driver.switchTo().defaultContent();
		this.driver.switchTo().frame(googleobject.IframeCvcNumber());
		googleobject.CardCvcField().sendKeys("123");
		Thread.sleep(1000L);
		driver.switchTo().defaultContent();
		Thread.sleep(3000L);
		googleobject.ApplyButton().click();
		if (googleobject.SetUpAlertMessage().getText() == "Please enter Promo Code") {
			googleobject.PromCodeInputField().sendKeys("100OFF");
			googleobject.ApplyButton().click();
			Boolean successToast = googleobject.successToast().isDisplayed();
			System.out.println("Is promo applied successfully " + successToast);
		}
		googleobject.SetUpPaymentNextButton().click();
		googleobject.ReviewBillConfirm().click();
		googleobject.ContinueWithEveryoneButton().click();
		googleobject.SendInvitationsNowRadio().click();
		googleobject.CreateAssemblyAccountsButton().click();
		googleobject.viewPeopleInviteButton().click();

	}

	// validate quick settings flow
	public void validateQuickSettingsGSuiteFunction() {
		googleobject.QuickSettingsText().click();
		Boolean saveSettingsActive = googleobject.SaveSettingsButton().isEnabled();
		System.out.println(saveSettingsActive);
		if (saveSettingsActive == true) {
			System.out.println("The Save Settings button is disabled");
		} else {
			googleobject.AdminApproveRadio().click();
			googleobject.SaveSettingsButton().click();
			googleobject.CloseButton().click();

		}

	}

	// validate GSuiteapp connection flow
	public void GSuiteWorkspaceFlow() throws InterruptedException {
		Thread.sleep(3000L);
		String winHandleBefore = driver.getWindowHandle();
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		Thread.sleep(4000L);
		String GsuiteSignInWorkspaceText = googleobject.GSuiteSigninHeaderText().getText();
		System.out.println("Gsuite header message is" + GsuiteSignInWorkspaceText);
		Assert.assertEquals(GsuiteSignInWorkspaceText, "Choose an account from my.joinassembly.com");
		System.out.println("Assertion passed since the text is Visible");
		Thread.sleep(1000L);
		googleobject.SelectGsuiteUser().click();
		Thread.sleep(1000L);
		googleobject.AllowButtonSpan().click();
		Thread.sleep(3000L);
		driver.switchTo().window(winHandleBefore);
	}

}
