package office365testcases;

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

import officePageObjects.O365PageObjects;
import signInViaSSO.O365SsoSignIn;
import slacktestcases.SlackIdentityUpgradeViewFlow;

//Execute this Third(3rd) when Slack is not connected and account has free plan
public class O365UpgradeViewFlow_DONOTUSE extends O365SsoSignIn  {
	public static Logger log = LogManager.getLogger(SlackIdentityUpgradeViewFlow.class.getName());
	public O365PageObjects office365Object;

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {

		driver = initializeDriver();
		office365Object = new O365PageObjects(driver);
	}

	// Select Identity Management and Upgrade flow covered
	@Test(priority = 1)
	public void selectSlackIMProvider() throws InterruptedException {
		selectManagePage();
		Thread.sleep(2000L);
		validateManagePageAssertion();
		System.out.println("Testcase-1 passed since application landed on Manage page and Assertion passed");
	}

	// validate connecting to Office365 and disconnecting on click of cancel button
	// and
	// click on sure button
	@Test(priority = 2)
	public void connectOffice365OnCancel() throws InterruptedException {
		O365IconPagevalidateOnClick();
		Thread.sleep(2000L);
		office365Object.ConnectOffice365Button().click();
		Thread.sleep(4000L);
		validateSignInOffice365AppAssertion();
		Thread.sleep(2000L);
		Office365WorkspaceFlow();
		nevermindButtonValidate();
		System.out.println("User has clicked on Nevermind button");
		Thread.sleep(1000L);
		// validateSelectPeopleAssertion();
		sureButtonClickValidate();
		System.out.println("User has clicked on Sure button");
		Thread.sleep(1000L);
		// validateConnectPageAssertion();
		System.out.println("Testcase-2 passed since application connected to Office 365 by entering details and"
				+ " disonnected on click of I'm sure button after cancel button clicked");
	}

	// Validate Upgrade now flow
	@Test(priority = 3)
	public void SlackFlowValidateUpgradeNowEmptyFields() throws InterruptedException {
		Thread.sleep(2000L);
		O365IconPagevalidateOnClick();
		Thread.sleep(2000L);
		office365Object.ConnectOffice365Button().click();
		Thread.sleep(4000L);
		validateSignInOffice365AppAssertion();
		Thread.sleep(4000L);
		Office365WorkspaceFlow();
		Thread.sleep(1000L);
		office365Object.UpgradeNowFeature().click();
		String actualUpgradeFromText = office365Object.UpgradePlanText().getText();
		System.out.println(actualUpgradeFromText);
		Assert.assertEquals(actualUpgradeFromText, "Upgrade from the Free plan to Team or Business plan");
		Thread.sleep(1000L);
		office365Object.UpgradePlanNextButton().click();
		String actualSetupPaymentText = office365Object.UpgradePlanText().getText();
		System.out.println(actualSetupPaymentText);
		Assert.assertEquals(actualSetupPaymentText, "Setup your payment method");
		office365Object.SetUpPaymentNextButton().click();
		System.out.println(office365Object.SetUpAlertMessage().getText());
		if (office365Object.SetUpAlertMessage().getText() != null) {
			System.out.println("entering into billing details");
			validateCardValidDetails();
			System.out.println("Testcase-3 passed since billing details are provided and approved");
		}
	}

	// View Invite Flow
	@Test(priority = 4)
	public void ViewInvitesFlow() throws InterruptedException {
		validateSelectPeopleViewFlow();
		System.out.println(
				"Testcase-4 passed since group  is selected and page landed on Manage after view people button click");
	}
	
	@Test(priority = 5)
	public void validateAssemblyAccountsViewPeopleFunc() throws InterruptedException{
		Thread.sleep(5000L);
		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Disconnect Office 365')]")));
		validateAssemblyAccountsViewPeople();
		System.out.println("Testcase-5 passed since view people button is clicked");
	}

	// Edit Connection flow
	// View Edit Connection Flow
	@Test(priority = 6)
	public void editConnectionFlow() throws InterruptedException {
		office365Object.EditConnectionButton().click();
		validateSelectPeopleViewFlow();
		System.out.println("Testcase-6 passed since Edit Connection flow is completed");
	}

	// Function to validate landing page of Manage
	public void selectManagePage() throws InterruptedException {
		Thread.sleep(2000L);
		office365Object.avatarIcon().click();
		office365Object.AdminText().click();
		office365Object.UsersText().click();
		office365Object.ManageText().click();
		Thread.sleep(5000L);

	}

	// Function to validate Cancel button on all pages
	public void cancelButtonClickValidate() throws InterruptedException {
		office365Object.CancelButton().click();
		Thread.sleep(5000L);
		validateManagePageAssertion();
	}

	// Function to validate 'I'm sure' on click of cancel button
	public void sureButtonClickValidate() throws InterruptedException {
		office365Object.CancelButton().click();
		Thread.sleep(2000L);
		ValidateCancelModalHeaderAssertion();
		office365Object.ImSureButton().click();
		Thread.sleep(2000L);
		validateManagePageAssertion();
	}

	// Function to validate 'Never mind' on click of cancel button
	public void nevermindButtonValidate() throws InterruptedException {
		Thread.sleep(5000L);
		office365Object.CancelButton().click();
		Thread.sleep(2000L);
		ValidateCancelModalHeaderAssertion();
		office365Object.NevermindButton().click();
		validateSelectPeopleAssertion();

	}

	// Function to validate Office365 first page - Setup/Connect
	public void O365IconPagevalidateOnClick() throws InterruptedException {
		office365Object.Office365Icon().click();
		Thread.sleep(2000L);
		String actualFirstStepText = office365Object.SetupStepText().getText();
		System.out.println("Set up text is ===> " + actualFirstStepText);
		Assert.assertEquals(actualFirstStepText, "Set up Office365 as your identity provider");

	}

	// Function to validate Disconnect slack
	public void disconnectIdentityManagementOnKeepIt() throws InterruptedException {
		Thread.sleep(5000L);
		office365Object.DisconnectOffice365Button().click();
		ValidateDisconnectSlackModalAssertion();
		office365Object.NokeepItIM().click();
	}

	// Function to validate Disconnect slack
	public void disconnectIdentityManagementOnYes() throws InterruptedException {
		office365Object.DisconnectOffice365Button().click();
		ValidateDisconnectSlackModalAssertion();
		office365Object.YesDisconnectIM().click();
		validateManagePageAssertion();
	}

	// Assertion Validation
	// validate Sign in to workspace landing page
	public void validateSignInOffice365AppAssertion() throws InterruptedException {
		Thread.sleep(3000L);
		String OfficeSignInWorkspaceText = office365Object.Office365SignInWorkspaceText().getText();
		System.out.println(OfficeSignInWorkspaceText);
		Assert.assertEquals(OfficeSignInWorkspaceText, "Pick an account");
	}

	// Validate Manage landing page when slack is not connected
	public void validateManagePageAssertion() throws InterruptedException {
		Thread.sleep(2000L);
		String managePageLandingText = office365Object.ConnectIdentityText().getText();
		System.out.println("the text is " + managePageLandingText);
		Assert.assertEquals(managePageLandingText, "Connect your identity provider");
	}

	// Validate Manage landing page when slack is connected
	public void validateManageSlackConnectedAssertion() throws InterruptedException {
		Thread.sleep(2000L);
		String managePageLandingText = office365Object.QuickSettingsText().getText();
		System.out.println("the text is " + managePageLandingText);
		Assert.assertEquals(managePageLandingText, "Quick Settings");
	}

	// Validate Step1 content for Slack Connect
	public void validateConnectPageAssertion() {
		String stepCountText = office365Object.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 1 of 4");
	}

	// Validate Step2 content for Slack Select People
	public void validateSelectPeopleAssertion() {
		String stepCountText = office365Object.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 2 of 4");
	}

	// Validate Step3 content for Slack Configure Invites
	public void validateConfigureInvitesAssertion() {
		String stepCountText = office365Object.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 3 of 4");
	}

	// Validate Step2 content for Slack Assembly Accounts
	public void validateCreateAccountAssertion() {
		String stepCountText = office365Object.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 4 of 4");
	}

	// Validate Cancel modal header content
	public void ValidateCancelModalHeaderAssertion() {
		String actualCancelHeaderModalText = office365Object.CancelModalHeader().getText();
		System.out.println("The header of Cancel modal is  " + actualCancelHeaderModalText);
		Assert.assertEquals(actualCancelHeaderModalText, "Are you sure you want to cancel Office 365 setup?");
	}

	// Validate Disconnect Slack modal header content
	public void ValidateDisconnectSlackModalAssertion() {
		String disconnectOffice365ModalHeader = office365Object.DisconnectOffice365ModalHeader().getText();
		System.out.println(disconnectOffice365ModalHeader);
		Assert.assertEquals(disconnectOffice365ModalHeader, "Disconnect Office365?");
	}

	// Validate signout flow
	public void signOutFunction() {
		office365Object.avatarIcon().click();
		office365Object.SignoutText().click();
	}

	public void validateCardValidDetails() throws InterruptedException {
		office365Object.CardNameInputField().sendKeys("abc");
		Thread.sleep(1000L);
		this.driver.switchTo().frame(office365Object.IframeCardNumber());
		office365Object.CardNumberInputField().sendKeys("4242424242424242");
		driver.switchTo().defaultContent();
		this.driver.switchTo().frame(office365Object.IframeExpiryDate());
		office365Object.CardExpiryDateField().sendKeys("1222");
		driver.switchTo().defaultContent();
		this.driver.switchTo().frame(office365Object.IframeCvcNumber());
		office365Object.CardCvcField().sendKeys("123");
		driver.switchTo().defaultContent();
		Thread.sleep(1000L);
		office365Object.ApplyButton().click();
		if (office365Object.SetUpAlertMessage().getText() != null) {
			Thread.sleep(1000L);
			office365Object.PromCodeInputField().sendKeys("100OFF");
			office365Object.ApplyButton().click();
			Boolean successToast = office365Object.successToast().isDisplayed();
			System.out.println("Is promo applied successfully " + successToast);
		}
		office365Object.SetUpPaymentNextButton().click();
		Thread.sleep(1000L);
		office365Object.ReviewBillConfirm().click();
		Thread.sleep(3000L);

	}

	public void validateQuickSettingsSlackFunction() {
		office365Object.QuickSettingsText().click();
		Boolean saveSettingsActive = office365Object.SaveSettingsButton().isEnabled();
		System.out.println(saveSettingsActive);
		if (saveSettingsActive == true) {
			System.out.println("The Save Settings button is disabled");
		} else {
			office365Object.AdminApproveRadio().click();
			office365Object.SaveSettingsButton().click();
			office365Object.CloseButton().click();

		}

	}

	public void validateSelectPeopleViewFlow() throws InterruptedException {
		Thread.sleep(2000L);
		validateSelectPeopleAssertion();
		Thread.sleep(3000L);
		office365Object.SelectGroups().click();
		Thread.sleep(2000L);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", office365Object.SelectAllCheckBox());
		Thread.sleep(1000L);
		office365Object.SelectAllCheckBox().click();
		Thread.sleep(1000L);
		office365Object.ContinueNext().click();
		Thread.sleep(1000L);
		office365Object.SendInvitationsNowRadio().click();
		validateConfigureInvitesAssertion();
		office365Object.CreateAssemblyAccountsButton().click();
		System.out.println("Create Accounts button is clicked");
		}
	
	public void validateAssemblyAccountsViewPeople() throws InterruptedException {
		Thread.sleep(5000L);
		disconnectIdentityManagementOnKeepIt();
		System.out.println("User has click on 'No, Keep it button in Create Accounts page");
		validateCreateAccountAssertion();
		System.out.println("View People button is displayed? " + office365Object.viewPeopleInviteButton().isEnabled());
		//System.out.println("View people buuton should be clicked");
		office365Object.viewPeopleInviteButton().click();
		validateCreateAccountAssertion();
		validateManageSlackConnectedAssertion();

	
	}

	// validate quick settings flow
	public void validateQuickSettingsOffice365Function() {
		office365Object.QuickSettingsText().click();
		Boolean saveSettingsActive = office365Object.SaveSettingsButton().isEnabled();
		System.out.println(saveSettingsActive);
		if (saveSettingsActive == true) {
			System.out.println("The Save Settings button is enabled");
		} else {
			office365Object.AdminApproveRadio().click();
			office365Object.SaveSettingsButton().click();
			office365Object.CloseButton().click();

		}

	}

	// validate Office365app connection flow
	public void Office365WorkspaceFlow() throws InterruptedException {
		Thread.sleep(2000L);
		office365Object.Office365UserMailId().click();
		office365Object.O365Consent().click();
		office365Object.AcceptO365Consent().click();
		Thread.sleep(3000L);

	}

}
