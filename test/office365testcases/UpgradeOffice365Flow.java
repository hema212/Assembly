package office365testcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import officePageObjects.O365PageObjects;
import resources.Base;

public class UpgradeOffice365Flow extends Base {
	public static Logger log = LogManager.getLogger(UpgradeOffice365Flow.class.getName());
	public O365PageObjects office365Object;

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {

		driver = initializeDriver();
		office365Object = new O365PageObjects(driver);
	}

	// Select Identity Management and Upgrade flow covered
	@Test(priority = 1)
	public void selectOffice365IMProvider() throws InterruptedException {
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
		office365Object.ConnectOffice365Button().click();
		Thread.sleep(4000L);
		validateSignInOffice365AppAssertion();
		Office365WorkspaceFlow();
		nevermindButtonValidate();
		System.out.println("User has clicked on Nevermind button");
		sureButtonClickValidate();
		System.out.println("User has clicked on Sure button");
		System.out.println("Testcase-2 passed since application connected to Office 365 by entering details and"
				+ " disonnected on click of I'm sure button after cancel button clicked");
	}

	// Validate Upgrade now flow

	@Test(priority = 3)
	public void O365FlowValidateUpgradeNowEmptyFields() throws InterruptedException {
		Thread.sleep(2000L);
		O365IconPagevalidateOnClick();
		Thread.sleep(1000L);
		office365Object.ConnectOffice365Button().click();
		Thread.sleep(4000L);
		validateSignInOffice365AppAssertion();
		Thread.sleep(1000L);
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

	// Validate disconnect office 365 on click on No, keep it
	@Test(priority = 5)
	public void validateAssemblyAccountsViewPeopleFunc() throws InterruptedException {
		validateAssemblyAccountsViewPeople();
		System.out.println("Testcase-5 passed since view people button is clicked");
	}
	
	// Edit Connection flow
		// View Edit Connection Flow
		@Test(priority = 6)
		public void editConnectionFlow() throws InterruptedException {
			Thread.sleep(1000L);
			System.out.println("Clicking on Edit connection button");
			office365Object.EditConnectionButton().click();
			office365Object.ContinueWithEveryoneButton().click();
			office365Object.CreateAssemblyAccountsButton().click();
			validateAssemblyAccountsViewPeople();
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

	// Validate Manage landing page when Office365 is not connected
	public void validateManagePageAssertion() throws InterruptedException {
		Thread.sleep(3000L);
		String managePageLandingText = office365Object.ConnectIdentityText().getText();
		System.out.println("the text is " + managePageLandingText);
		Assert.assertEquals(managePageLandingText, "Connect your identity provider");
	}

	// Function to validate Office365 first page - Setup/Connect
	public void O365IconPagevalidateOnClick() throws InterruptedException {
		office365Object.Office365Icon().click();
		Thread.sleep(2000L);
		String actualFirstStepText = office365Object.SetupStepText().getText();
		System.out.println("Set up text is ===> " + actualFirstStepText);
		Assert.assertEquals(actualFirstStepText, "Set up Office365 as your identity provider");

	}

	// Assertion Validation
	// validate Sign in to workspace landing page
	public void validateSignInOffice365AppAssertion() throws InterruptedException {
		Thread.sleep(3000L);
		String OfficeSignInWorkspaceText = office365Object.Office365SignInWorkspaceText().getText();
		System.out.println(OfficeSignInWorkspaceText);
		Assert.assertEquals(OfficeSignInWorkspaceText, "Pick an account");
	}

	// validate Office365app connection flow
	public void Office365WorkspaceFlow() throws InterruptedException {
		Thread.sleep(1000L);
		office365Object.Office365UserMailId().click();
		office365Object.O365Consent().click();
		office365Object.AcceptO365Consent().click();
		Thread.sleep(1000L);

	}

	// Function to validate 'Never mind' on click of cancel button
	public void nevermindButtonValidate() throws InterruptedException {
		Thread.sleep(1000L);
		office365Object.CancelButton().click();
		Thread.sleep(1000L);
		ValidateCancelModalHeaderAssertion();
		office365Object.NevermindButton().click();
		validateSelectPeopleAssertion();

	}

	// Function to validate 'I'm sure' on click of cancel button
	public void sureButtonClickValidate() throws InterruptedException {
		office365Object.CancelButton().click();
		Thread.sleep(1000L);
		ValidateCancelModalHeaderAssertion();
		office365Object.ImSureButton().click();
		Thread.sleep(1000L);
		validateManagePageAssertion();
	}

	// Validate Cancel modal header content
	public void ValidateCancelModalHeaderAssertion() {
		String actualCancelHeaderModalText = office365Object.CancelModalHeader().getText();
		System.out.println("The header of Cancel modal is  " + actualCancelHeaderModalText);
		Assert.assertEquals(actualCancelHeaderModalText, "Are you sure you want to cancel Office 365 setup?");
	}

	// Validate Step2 content for Office365 Select People
	public void validateSelectPeopleAssertion() {
		String stepCountText = office365Object.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 2 of 4");
	}

	// Validate Step3 content for Office365 Configure Invites
	public void validateConfigureInvitesAssertion() {
		String stepCountText = office365Object.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 3 of 4");
	}

	// Validate Step2 content for Office365 Assembly Accounts
	public void validateCreateAccountAssertion() {
		String stepCountText = office365Object.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 4 of 4");
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
		String billingContent = office365Object.ReviewBillingContent().getText();
		System.out.println("The reviewed billing content is: " + billingContent);
		Thread.sleep(1000L);
		office365Object.ReviewBillConfirm().click();
		Thread.sleep(3000L);

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
		Thread.sleep(8000L);
		System.out.println("Landed on create Accounts page");
		disconnectIdentityManagementOnKeepIt();
		System.out.println("User has click on 'No, Keep it button in Create Accounts page");
		validateCreateAccountAssertion();
		System.out.println("View People button is displayed? " + office365Object.viewPeopleInviteButton().isEnabled());
		// System.out.println("View people button should be clicked");
		office365Object.viewPeopleInviteButton().click();
		validateCreateAccountAssertion();
		validateManageO365ConnectedAssertion();

	}

	// Function to validate Disconnect Office365
	public void disconnectIdentityManagementOnKeepIt() throws InterruptedException {
		Thread.sleep(9000L);
		System.out.println("Clicking on Disconnect button");
		Thread.sleep(1000L);
		office365Object.DisconnectOffice365Button().click();
		Thread.sleep(1000L);
		System.out.println("Clicked on Disconnect O365 button");
		ValidateDisconnectOffice365ModalAssertion();
		office365Object.NokeepItIM().click();
	}

	// Validate Manage landing page when Office365 is connected
	public void validateManageO365ConnectedAssertion() throws InterruptedException {
		Thread.sleep(2000L);
		String managePageLandingText = office365Object.QuickSettingsText().getText();
		System.out.println("the text is " + managePageLandingText);
		Assert.assertEquals(managePageLandingText, "Quick Settings");
	}

	// Validate Disconnect Office 365 modal header content
	public void ValidateDisconnectOffice365ModalAssertion() throws InterruptedException {
		Thread.sleep(1000L);
		String disconnectOffice365ModalHeader = office365Object.DisconnectOffice365ModalHeader().getText();
		System.out.println(disconnectOffice365ModalHeader);
		Assert.assertEquals(disconnectOffice365ModalHeader, "Disconnect Office365?");
	}
}
