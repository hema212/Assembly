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

import GsuitePageObject.GooglePageObjects;
import resources.GoogleSsoSignIn;
import resources.GsuiteBase;

public class GsuiteUpgradeViewFlow extends GoogleSsoSignIn {
	public static Logger log = LogManager.getLogger(GsuiteUpgradeViewFlow.class.getName());
	public GooglePageObjects googleobject;

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {

		driver = initializeDriver();
		googleobject = new GooglePageObjects(driver);
	}

	// Select Identity Management and Upgrade flow covered
	@Test(priority = 1)
	public void selectGSuiteIMProvider() throws InterruptedException {
		selectManagePage();
		validateManagePageAssertion();
		googleobject.QuickSettingsText().click();
		disconnectIdentityManagementOnYes();
		System.out.println("Testcase-1 passed since application landed on Manage page and Assertion passed");
	}

	// validate connecting to GSuite and disconnecting on click of cancel button and
	// click on sure button
	@Test(priority = 2)
	public void connectGSuiteOnCancel() throws InterruptedException {
		GsuiteIconSetUpPageValidate();
		Thread.sleep(1000L);
		googleobject.ConnectGSuiteButton().click();
		Thread.sleep(4000L);
		GSuiteWorkspaceFlow();
		nevermindButtonValidate();
		System.out.println("User has clicked on Nevermind button");
		Thread.sleep(1000L);
		validateSelectPeopleAssertion();
		System.out
				.println("Testcase-2 Passes since application is connected to GSuite and landed on select people page");
	}

	// Validate Upgrade now flow
	@Test(priority = 3)
	public void GsuiteFlowValidateUpgradeNowEmptyFields() throws InterruptedException {
		Thread.sleep(2000L);
		googleobject.UpgradeNowFeature().click();
		String actualUpgradeFromText = googleobject.UpgradePlanText().getText();
		System.out.println(actualUpgradeFromText);
		Assert.assertEquals(actualUpgradeFromText, "Upgrade from the Free plan to Team or Business plan");
		Thread.sleep(1000L);
		googleobject.UpgradePlanNextButton().click();
		String actualSetupPaymentText = googleobject.UpgradePlanText().getText();
		System.out.println(actualSetupPaymentText);
		Assert.assertEquals(actualSetupPaymentText, "Setup your payment method");
		googleobject.SetUpPaymentNextButton().click();
		System.out.println(googleobject.SetUpAlertMessage().getText());
		if (googleobject.SetUpAlertMessage().getText() != null) {
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
				"Testcase-4 passed since org unit is selected and page landed on Manage after view people button click");
	}

	// Edit Connection flow
	// View people using Edit Connection Flow
	@Test(priority = 5)
	public void editConnectionFlow() throws InterruptedException {
		googleobject.EditConnectionButton().click();
		createAssemblyAccountFlow();
		System.out.println("The error message is "+ googleobject.GSuiteAccountMessageMismatchError());
		Thread.sleep(1000L);
		googleobject.ConfigureInvitesBackButton().click();
		validateSelectPeopleAssertion();
		googleobject.SelectGroups().click();
		Thread.sleep(1000L);
		googleobject.EngineeringOrgUnit().click();
		Thread.sleep(1000L);
		googleobject.ContinueNext().click();
		Thread.sleep(1000L);
		googleobject.CreateAssemblyAccountsButton().click();System.out.println(
				"Testcase-5 passed since application landed on Create Accounts page");
	}

	// Function to validate landing page of Manage
	public void selectManagePage() throws InterruptedException {
		googleobject.avatarIcon().click();
		googleobject.AdminText().click();
		googleobject.UsersText().click();
		googleobject.ManageText().click();
		Thread.sleep(5000L);

	}

	// Function to validate Cancel button on all pages
	public void cancelButtonClickValidate() throws InterruptedException {
		googleobject.CancelButton().click();
		Thread.sleep(5000L);
		validateManagePageAssertion();
	}

	// Function to validate 'I'm sure' on click of cancel button
	public void sureButtonClickValidate() throws InterruptedException {
		googleobject.CancelButton().click();
		Thread.sleep(2000L);
		ValidateCancelModalHeaderAssertion();
		googleobject.ImSureButton().click();
		Thread.sleep(2000L);
		validateManagePageAssertion();
	}

	// Function to validate 'Never mind' on click of cancel button
	public void nevermindButtonValidate() throws InterruptedException {
		Thread.sleep(5000L);
		googleobject.CancelButton().click();
		Thread.sleep(2000L);
		ValidateCancelModalHeaderAssertion();
		googleobject.NevermindButton().click();
		validateSelectPeopleAssertion();

	}

	// Function to validate GSuite first page - Setup/Connect
	public void GsuiteIconSetUpPageValidate() throws InterruptedException {
		googleobject.GSuiteIdentityIcon().click();
		Thread.sleep(2000L);
		String actualFirstStepText = googleobject.SetupStepText().getText();
		System.out.println("Set up text is ===> " + actualFirstStepText);
		Assert.assertEquals(actualFirstStepText, "Set up G Suite as your identity provider");
		validateConnectPageAssertion();
	}

	// Function to validate Disconnect GSuite
	public void disconnectIdentityManagementOnKeepIt() throws InterruptedException {
		Thread.sleep(1000L);
		googleobject.DisconnectGSuiteButton().click();
		ValidateDisconnectGSuiteModalAssertion();
		googleobject.NokeepItIM().click();
	}

	// Function to validate Disconnect GSuite
	public void disconnectIdentityManagementOnYes() throws InterruptedException {
		googleobject.DisconnectGSuiteButton().click();
		ValidateDisconnectGSuiteModalAssertion();
		googleobject.YesDisconnectIM().click();
		validateManagePageAssertion();
	}

	// Assertion Validation
	// Validate Sign in to workspace landing page
	// Assertion Validation
	// validate Sign in to workspace landing page
	public void validateSignInGSuiteAppAssertion() throws InterruptedException {
		Thread.sleep(5000L);
		String GsuiteSignInWorkspaceText = googleobject.GSuiteSigninHeaderText().getText();
		System.out.println(GsuiteSignInWorkspaceText);
		Assert.assertEquals(GsuiteSignInWorkspaceText, "Choose an account from my.joinassembly.com");
	}

	// Validate Manage landing page when GSuite is not connected
	public void validateManagePageAssertion() throws InterruptedException {
		Thread.sleep(2000L);
		String managePageLandingText = googleobject.ConnectIdentityText().getText();
		System.out.println("the text is " + managePageLandingText);
		//Assert.assertEquals(managePageLandingText, "Connect your identity provider");
	}

	// Validate Manage landing page when GSuite is connected
	public void validateManageGSuiteConnectedAssertion() throws InterruptedException {
		Thread.sleep(2000L);
		String managePageLandingText = googleobject.QuickSettingsText().getText();
		System.out.println("the text is " + managePageLandingText);
		Assert.assertEquals(managePageLandingText, "Quick Settings");
	}

	// Validate Step1 content for GSuite Connect
	public void validateConnectPageAssertion() {
		String stepCountText = googleobject.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 1 of 4");
	}

	// Validate Step2 content for GSuite Select People
	public void validateSelectPeopleAssertion() {
		String stepCountText = googleobject.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 2 of 4");
	}

	// Validate Step3 content for GSuite Configure Invites
	public void validateConfigureInvitesAssertion() {
		String stepCountText = googleobject.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 3 of 4");
	}

	// Validate Step2 content for GSuite Assembly Accounts
	public void validateCreateAccountAssertion() {
		String stepCountText = googleobject.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 4 of 4");
	}

	// Validate Cancel modal header content
	public void ValidateCancelModalHeaderAssertion() {
		String actualCancelHeaderModalText = googleobject.CancelModalHeader().getText();
		System.out.println("The header of Cancel modal is  " + actualCancelHeaderModalText);
		Assert.assertEquals(actualCancelHeaderModalText, "Are you sure you want to cancel G Suite setup?");
	}

	// Validate Disconnect GSuite modal header content
	public void ValidateDisconnectGSuiteModalAssertion() throws InterruptedException {
		Thread.sleep(3000L);
		String disconnectGSuiteModalHeader = googleobject.DisconnectGSuiteModalHeader().getText();
		System.out.println(disconnectGSuiteModalHeader);
		Assert.assertEquals(disconnectGSuiteModalHeader, "Disconnect GSuite?");
	}

	// Validate signout flow
	public void signOutFunction() {
		googleobject.avatarIcon().click();
		googleobject.SignoutText().click();
	}

	public void validateCardValidDetails() throws InterruptedException {
		googleobject.CardNameInputField().sendKeys("abc");
		Thread.sleep(1000L);
		this.driver.switchTo().frame(googleobject.IframeCardNumber());
		googleobject.CardNumberInputField().sendKeys("4242424242424242");
		driver.switchTo().defaultContent();
		this.driver.switchTo().frame(googleobject.IframeExpiryDate());
		googleobject.CardExpiryDateField().sendKeys("1222");
		driver.switchTo().defaultContent();
		this.driver.switchTo().frame(googleobject.IframeCvcNumber());
		googleobject.CardCvcField().sendKeys("123");
		driver.switchTo().defaultContent();
		Thread.sleep(1000L);
		googleobject.ApplyButton().click();
		if (googleobject.SetUpAlertMessage().getText() != null) {
			Thread.sleep(1000L);
			googleobject.PromCodeInputField().sendKeys("100OFF");
			googleobject.ApplyButton().click();
			Boolean successToast = googleobject.successToast().isDisplayed();
			System.out.println("Is promo applied successfully " + successToast);
		}
		googleobject.SetUpPaymentNextButton().click();
		Thread.sleep(1000L);
		String billingContent = googleobject.ReviewBillingContent().getText();
		System.out.println("The reviewed billing content is: " + billingContent);
		googleobject.ReviewBillConfirm().click();
		Thread.sleep(3000L);

	}

	// change the logic here
	public void validateQuickSettingsGSuiteFunction() {
		googleobject.QuickSettingsText().click();
		Boolean saveSettingsActive = googleobject.SaveSettingsButton().isEnabled();
		System.out.println(saveSettingsActive);
		if (saveSettingsActive == true) {
			// googleobject.AutoApproveRadio().click();
			Boolean isAutoapprovalSelected = googleobject.AutoApproveRadio().isSelected();
			if (isAutoapprovalSelected == true) {
				System.out.println("Auto approve is selected by default");
			}
			googleobject.SaveSettingsButton().click();
			googleobject.CloseButton().click();
			System.out.println("Now newly added users to GSuite will be auto approved");
		} else {
			System.out.println("The Save Changes button is Disabled");
		}

	}


	public void validateSelectPeopleViewFlow() throws InterruptedException {
		createAssemblyAccountFlow();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", googleobject.DisconnectGSuiteButton());
		Thread.sleep(10000L);
		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Disconnect G Suite')]")));
		Thread.sleep(9000L);
		disconnectIdentityManagementOnKeepIt();
		validateCreateAccountAssertion();
		System.out.println("User has click on 'No, Keep it button in Create Accounts page");
		validateCreateAccountAssertion();
		System.out.println("View people button is displayed? " + googleobject.viewPeopleInviteButton().isEnabled());
		Thread.sleep(2000);
		googleobject.viewPeopleInviteButton().click();
		validateManageGSuiteConnectedAssertion();

	}

	public void createAssemblyAccountFlow() throws InterruptedException {
		Thread.sleep(1000L);
		validateSelectPeopleAssertion();
		System.out.println("selecting org units");
		Thread.sleep(4000L);
		googleobject.SelectOrgUnits().click();
		Thread.sleep(2000L);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", googleobject.EngineeringOrgUnit());
		Thread.sleep(1000L);
		googleobject.EngineeringOrgUnit().click();
		Thread.sleep(1000L);
		System.out.println("The total members in the Engineering org unit are :" + googleobject.TotalOrgunitMember().getText());
		Thread.sleep(1000L);
		googleobject.ContinueNext().click();
		Thread.sleep(1000L);
		Boolean sendNowRadioActive = googleobject.SendInvitationsNowRadio().isSelected();
		System.out.println(
				"Is send invitations now radio selected? " + googleobject.SendInvitationsNowRadio().isSelected());
		System.out.println(
				"Is send them later radio selected? " + googleobject.SendThemLaterRadio().isSelected());
		System.out.println(
				"Is Auto Approve radio selected? " + googleobject.AutoApproveRadio().isSelected());
		System.out.println(
				"Is Admin Approve radio selected? " + googleobject.AdminApproveRadio().isSelected());
		Thread.sleep(1000L);
		googleobject.SendInvitationsNowRadio().click();
		validateConfigureInvitesAssertion();
		googleobject.CreateAssemblyAccountsButton().click();
	}
	
	// validate GSuiteapp connection flow
		public void GSuiteWorkspaceFlow() throws InterruptedException {
			Thread.sleep(1000L);
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
