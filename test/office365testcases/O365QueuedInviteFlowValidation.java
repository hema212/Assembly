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

import PageObjects.O365PageObjects;
import PageObjects.RecognitionPageObject;
import resources.O365SsoSignIn;

//Execute this First(1st) when Office365 is not connected and doesn't have Office365 users in it
public class O365QueuedInviteFlowValidation extends O365SsoSignIn  {
	public static Logger log = LogManager.getLogger(O365QueuedInviteFlowValidation.class.getName());
	public O365PageObjects office365Object;
	public RecognitionPageObject recogobject;

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {
		driver = initializeDriver();
		office365Object = new O365PageObjects(driver);
		recogobject = new RecognitionPageObject(driver);
	}

	// validate Manage landing page when Office365 is not connected
	@Test(priority = 1)
	public void managePageSetup() throws InterruptedException {
		System.out.println("Entering here");
		selectManageSideNavbar();
		/*
		 * this.driver.switchTo().frame(office365Object.botFrame()); for(int count=0;
		 * count<2; count++) { office365Object.botobject().click(); }
		 * office365Object.botobject().click(); driver.switchTo().defaultContent();
		 */
		Thread.sleep(1000L);
		validateManagePageAssertion();
		System.out.println("Testcase-1 passed since application landed on Manage page and Assertion passed");
	}

	// validate Cancel button click on
	@Test(priority = 2)
	public void validateCancelButtonOnClick() throws InterruptedException {
		O365IconPagevalidateOnClick();
		validateConnectPageAssertion();
		System.out.println("Is cancel button Enabled in Connect page? " + office365Object.CancelButton().isEnabled());
		System.out.println("Is Connect Office 365 button Enabled in Connect page? "
				+ office365Object.ConnectOffice365Button().isEnabled());
		cancelButtonClickvalidate();
		validateManagePageAssertion();
		System.out.println(
				"Testcase-2 passed since application landed on Manage page and Assertion passed after clicked on Cancel button in Connect page");
	}

	// validate connecting to Office365 and disconnecting on click of cancel button
	// and
	// click on sure button
	@Test(priority = 3)
	public void connectOffice365OnCancel() throws InterruptedException {
		O365IconPagevalidateOnClick();
		Thread.sleep(2000L);
		office365Object.ConnectOffice365Button().click();
		Thread.sleep(4000L);
		validateSignInOffice365AppAssertion();
		Thread.sleep(4000L);
		Office365WorkspaceFlow();
		nevermindButtonvalidate();
		System.out.println("User has clicked on Nevermind button");
		Thread.sleep(1000L);
		// validateSelectPeopleAssertion();
		sureButtonClickvalidate();
		System.out.println("User has clicked on Sure button");
		Thread.sleep(1000L);
		// validateConnectPageAssertion();
		System.out.println("Testcase-3 passed since application connected to Office 365 by entering details and"
				+ " disonnected on click of I'm sure button after cancel button clicked");
	}

	// validate Queued invites flow when selected I'll send them later
	@Test(priority = 4)
	public void validateQueuedInvitesFlow() throws InterruptedException {
		O365IconPagevalidateOnClick();
		Thread.sleep(2000L);
		office365Object.ConnectOffice365Button().click();
		Thread.sleep(2000L);
		validateSignInOffice365AppAssertion();
		Thread.sleep(2000L);
		Office365WorkspaceFlow();
		validateSelectPeopleAssertion();
		System.out.println("Selected Everyone radio button option which is default when user lands");
		System.out.println("Is everyone radio button selected ? " + office365Object.SelectAllUsers().isSelected());
		Thread.sleep(1000L);
		office365Object.ContinueWithEveryoneButton().click();
		validateConfigureInvitesAssertion();
		Thread.sleep(1000L);
		System.out.println("Is Send Invitations now radio option selecetd ? "
				+ office365Object.SendInvitationsNowRadio().isSelected());
		System.out.println("Is Auto approve radio option selected ? " + office365Object.AutoApproveRadio().isSelected());
		if (true) {
			office365Object.SendThemLaterRadio().click();
		}
		Thread.sleep(1000L);
		System.out.println("Selected I'll send them later and admin approval Radio button option");
		office365Object.CreateAssemblyAccountsButton().click();
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
		js.executeScript("window.scrollBy(0,1000)");
		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Disconnect Office 365')]")));
		Thread.sleep(9000L);
		disconnectIdentityManagementOnKeepIt();
		System.out.println("User has click on 'No, Keep it button in Create Accounts page");
		validateCreateAccountAssertion();
		System.out.println(
				"View Queued Invites button is displayed? " + office365Object.viewPeopleInviteButton().isEnabled());
		System.out.println(
				"Testcase-4 passed since Queued Invites flow is selected on choosing send them" + " later button");
		office365Object.viewPeopleInviteButton().click();
		System.out.println("Testcase-5 passed since application landed on Invites Page On Queued invite");
		String breadcrum = office365Object.InvitesBreadcrum().getText();
		Assert.assertEquals(breadcrum, "> Invites");
	}

	@Test(priority = 6)
	public void validateQuickSettings() throws InterruptedException {
		disconnectOffice365ThroughQuickSettings();
		Thread.sleep(1000L);
		validateManagePageAssertion();
		String breadcrum = office365Object.InvitesBreadcrum().getText();
		Assert.assertEquals(breadcrum, "> Manage");
		System.out.println(
				"Testcase-6 passed since application landed on Manage page after disconnecting from quick settings");
	}

	// Function to validate landing page of Manage
	public void selectManageSideNavbar() throws InterruptedException {
		// System.out.println("Calling this function");
		office365Object.avatarIcon().click();
		office365Object.AdminText().click();
		office365Object.UsersText().click();
		office365Object.ManageText().click();
		Thread.sleep(1000L);
		
	}

	// Function to validate Cancel button on all pages
	public void cancelButtonClickvalidate() throws InterruptedException {
		office365Object.CancelButton().click();
		Thread.sleep(1000L);
	}

	// Function to validate 'I'm sure' on click of cancel button
	public void sureButtonClickvalidate() throws InterruptedException {
		office365Object.CancelButton().click();
		Thread.sleep(1000L);
		validateCancelModalHeaderAssertion();
		office365Object.ImSureButton().click();
		Thread.sleep(1000L);
		validateManagePageAssertion();
	}

	// Function to validate 'Nevermind' on click of cancel button
	public void nevermindButtonvalidate() throws InterruptedException {
		Thread.sleep(5000L);
		office365Object.CancelButton().click();
		Thread.sleep(2000L);
		validateCancelModalHeaderAssertion();
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

	// Function to validate DisConnect Office 365
	public void disconnectIdentityManagementOnKeepIt() throws InterruptedException {
		Thread.sleep(5000L);
		office365Object.DisconnectOffice365Button().click();
		validateDisconnectOffice365ModalAssertion();
		office365Object.NokeepItIM().click();
	}

	// Function to validate DisConnect Office 365
	public void disconnectIdentityManagementOnYes() throws InterruptedException {
		office365Object.DisconnectOffice365Button().click();
		validateDisconnectOffice365ModalAssertion();
		office365Object.YesDisconnectIM().click();
	}

	// Assertion Validation
	// validate Sign in to workspace landing page
	public void validateSignInOffice365AppAssertion() throws InterruptedException {
		Thread.sleep(5000L);
		String OfficeSignInWorkspaceText = office365Object.Office365SignInWorkspaceText().getText();
		System.out.println(OfficeSignInWorkspaceText);
		Assert.assertEquals(OfficeSignInWorkspaceText, "Pick an account");
	}

	

	// validate Manage landing page when Office365 is not connected
	public void validateManagePageAssertion() throws InterruptedException {
		Thread.sleep(2000L);
		String managePageLandingText = office365Object.ConnectIdentityText().getText();
		System.out.println("the text is " + managePageLandingText);
		Assert.assertEquals(managePageLandingText, "Connect your identity provider");
	}

	// validate Manage landing page when Office365 is connected
	public void validateManageOffice365ConnectedAssertion() throws InterruptedException {
		Thread.sleep(2000L);
		String managePageLandingText = office365Object.QuickSettingsText().getText();
		System.out.println("the text is " + managePageLandingText);
		Assert.assertEquals(managePageLandingText, "Quick Settings");
	}

	// validate Step1 content for Office365 Connect
	public void validateConnectPageAssertion() {
		String stepCountText = office365Object.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 1 of 4");
	}

	// validate Step2 content for Office365 Select People
	public void validateSelectPeopleAssertion() {
		String stepCountText = office365Object.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 2 of 4");
	}

	// validate Step3 content for Office365 Configure Invites
	public void validateConfigureInvitesAssertion() {
		String stepCountText = office365Object.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 3 of 4");
	}

	// validate Step2 content for Office365 Assembly Accounts
	public void validateCreateAccountAssertion() {
		String stepCountText = office365Object.StepCount().getText();
		System.out.println("Step count is ===> " + stepCountText);
		Assert.assertEquals(stepCountText, "Step 4 of 4");
	}

	// validate Cancel modal header content
	public void validateCancelModalHeaderAssertion() {
		String actualCancelHeaderModalText = office365Object.CancelModalHeader().getText();
		System.out.println("The header of Cancel modal is  " + actualCancelHeaderModalText);
		Assert.assertEquals(actualCancelHeaderModalText, "Are you sure you want to cancel Office 365 setup?");
	}

	// validate DisConnect Office 365 modal header content
	public void validateDisconnectOffice365ModalAssertion() {
		String disconnectOffice365ModalHeader = office365Object.DisconnectOffice365ModalHeader().getText();
		System.out.println(disconnectOffice365ModalHeader);
		Assert.assertEquals(disconnectOffice365ModalHeader, "Disconnect Office365?");
	}

	// disConnect Office 365 using Quick settings
	public void disconnectOffice365ThroughQuickSettings() throws InterruptedException {
		office365Object.QuickSettingsText().click();
		System.out.println(
				"The state of Save settings before change is " + office365Object.SaveSettingsButton().isEnabled());
		office365Object.AdminApproveRadio().click();
		System.out.println(
				"The state of Save settings after change is " + office365Object.SaveSettingsButton().isEnabled());
		office365Object.CloseButton().click();
		office365Object.QuickSettingsText().click();
		office365Object.SaveSettingsButton().click();
		disconnectIdentityManagementOnYes();
		System.out.println("Testcase-6 passed since Office365 is disconnected from settings");
	}

	// validate signout flow
	public void signOutFunction() {
		office365Object.avatarIcon().click();
		office365Object.SignoutText().click();
	}

	// validate valid card details card
	public void validateCardValidDetails() throws InterruptedException {
		office365Object.CardNameInputField().sendKeys("abc");
		Thread.sleep(2000L);
		this.driver.switchTo().frame(office365Object.IframeCardNumber());
		office365Object.CardNumberInputField().sendKeys("4242424242424242");
		driver.switchTo().defaultContent();
		this.driver.switchTo().frame(office365Object.IframeExpiryDate());
		office365Object.CardExpiryDateField().sendKeys("1222");
		driver.switchTo().defaultContent();
		this.driver.switchTo().frame(office365Object.IframeCvcNumber());
		office365Object.CardCvcField().sendKeys("123");
		Thread.sleep(1000L);
		driver.switchTo().defaultContent();
		Thread.sleep(3000L);
		office365Object.ApplyButton().click();
		if (office365Object.SetUpAlertMessage().getText() == "Please enter Promo Code") {
			office365Object.PromCodeInputField().sendKeys("100OFF");
			office365Object.ApplyButton().click();
			Boolean successToast = office365Object.successToast().isDisplayed();
			System.out.println("Is promo applied successfully " + successToast);
		}
		office365Object.SetUpPaymentNextButton().click();
		office365Object.ReviewBillConfirm().click();
		office365Object.ContinueWithEveryoneButton().click();
		office365Object.SendInvitationsNowRadio().click();
		office365Object.CreateAssemblyAccountsButton().click();
		office365Object.viewPeopleInviteButton().click();

	}

	// validate quick settings flow
	public void validateQuickSettingsOffice365Function() {
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

	// validate Office365app connection flow
	public void Office365WorkspaceFlow() throws InterruptedException {
		Thread.sleep(4000L);
		office365Object.Office365UserMailId().click();
		office365Object.O365Consent().click();
		office365Object.AcceptO365Consent().click();
		Thread.sleep(3000L);

	}

}
