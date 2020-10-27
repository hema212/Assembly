package slacktestcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import PageObjects.RecognitionPageObject;
import PageObjects.SlackIdentityObjects;
import PageObjects.SlackIntegrationPageObjects;
import resources.BaseSignInViaSlack;

//Execute this only when slack is connected and Integrating for the first time / Execute this 6th
public class SlackIntegrationFlowValidation extends BaseSignInViaSlack {
	public static Logger log = LogManager.getLogger(SlackQueuedInviteFlowValidation.class.getName());
	public SlackIntegrationPageObjects slackintegrationobject;
	public SlackIdentityObjects slackobject;
	public RecognitionPageObject recogobject;

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {
		driver = initializeDriver();
		slackintegrationobject = new SlackIntegrationPageObjects(driver);
		slackobject = new SlackIdentityObjects(driver);
		recogobject = new RecognitionPageObject(driver);
	}

	//Validate SSO sign in functionality
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

	//Validate channel validation and select slack-im channel and head to the feed
	@Test(priority = 2)
	public void AnnounceLaterFunction() throws InterruptedException {
		System.out.println("Landing on Integrations page");
		Thread.sleep(1000L);
		selectIntegrationSideNavbar();
		Boolean isButtonDisplayed = slackintegrationobject.GetStartedButton().isDisplayed();
		System.out.println("The value of Get started button is " + isButtonDisplayed);
		if (isButtonDisplayed == true) {
			System.out.println("Get started button is displayed");
			slackintegrationobject.GetStartedButton().click();
			Thread.sleep(5000L);
			slackobject.AllowButton().click();
			Thread.sleep(5000L);
			slackintegrationobject.ChannelNextButton().click();
			String emptyErrorMessage = slackintegrationobject.ChannelErrorMessage().getText();
			System.out.println("Error message when channel is empty : " + emptyErrorMessage);
			Thread.sleep(1000L);
			slackintegrationobject.CreateChannelTab().click();
			slackintegrationobject.CreateChannelInputField().sendKeys("slack-im");
			slackintegrationobject.ChannelNextButton().click();
			String duplicateMessage = slackintegrationobject.AlertMessage().getText();
			Thread.sleep(2000L);
			System.out.println("Error message when duplicate channel name is provided : " + duplicateMessage);
			slackintegrationobject.SelectChannelTab().click();
			slackintegrationobject.SelectChannelDropDown().click();
			slackintegrationobject.ChannelCancelButton().click();

			Thread.sleep(1000L);
			slackintegrationobject.FinishSetUpButton().click();
			selectDefaultChannelHeaderAssertion();
			slackintegrationobject.SelectChannelDropDown().click();
			for (int i = 0; i < 2; i++) {
				Thread.sleep(1000L);
				slackintegrationobject.SelectChannelDropDown().sendKeys(Keys.ARROW_DOWN);
			}
			slackintegrationobject.SelectChannelDropDown().sendKeys(Keys.ENTER);
			slackintegrationobject.ChannelNextButton().click();
			customizeTrophyHeaderAssertion();
			CustomizeTrophy();
			AnnouncementLaterFunc();
			System.out.println("Testcase-2 passed since user clicked on head to my feed button");
			Thread.sleep(2000L);
			GiveRecogPageAssertion();
		}

	}

	//Validate Quick settings flow in Integrations page
	@Test(priority = 3)
	public void ValidateQuickSettingsFlow() throws InterruptedException {
		Thread.sleep(1000L);
		selectIntegrationSideNavbar();
		validateManageSlackIntegrationConnectedAssertion();
		Thread.sleep(1000L);
		validateQuickSettingsSlackIntegrationFunction();
		Thread.sleep(1000L);
		IntegrationsBreadcrumAssertion();
		System.out.println("The success toast message after saving is: " +slackobject.successToast().getText());
	}

	//Function to customize trophy
	public void CustomizeTrophy() throws InterruptedException {
		// customize currency
		Thread.sleep(3000L);
		slackintegrationobject.CustomizeCurrencyInput().clear();
		Thread.sleep(1000L);
		String emptyCustomTrophyError = slackintegrationobject.EmptyCustomTrophyError().getText();
		System.out.println("When custom trophy filed is empty then the error message is " + emptyCustomTrophyError);
		slackintegrationobject.CustomizeCurrencyInput().sendKeys(Keys.chord(Keys.CONTROL, "a"), "Prizeeeeeeeeeeeeeeeeeeeeee");
		Thread.sleep(2000L);
		String pluralText = slackintegrationobject.PluralCustomTrophy().getAttribute("value");
		System.out.println("The plural form of the trophy is :" + pluralText);
		Thread.sleep(1000L);
		slackintegrationobject.ChannelNextButton().click();
	}

	//Function to announce later
	public void AnnouncementLaterFunc() throws InterruptedException {
		AnnouncementHeaderAssertion();
		slackintegrationobject.NoAnnouncementRadioButton().click();
		slackintegrationobject.ChannelNextButton().click();
		AssemblysetUpHeaderAssertion();
		slackintegrationobject.HeadToMyFeed().click();

	}

	// Validate Slack App Actual header
	public void ValidateSlackAppActualHeaderAssertion() {
		String SlackActualHeader = slackobject.SlackSigninHeaderText().getText();
		Assert.assertEquals(SlackActualHeader,
				"Assembly v2 Dev is requesting permission to access the Joinassembly+21 Slack workspace");
		System.out.println("Assertion passed during request permission");
	}

	// function to validate landing page of Manage
	public void selectIntegrationSideNavbar() throws InterruptedException {
		Thread.sleep(1000L);
		slackintegrationobject.AvatarIcon().click();
		slackintegrationobject.AdminText().click();
		slackintegrationobject.IntegrationsText().click();
	}

	// Assertions
	// Default channel selection header
	public void selectDefaultChannelHeaderAssertion() throws InterruptedException {
		Thread.sleep(1000L);
		String defaultChannelHeader = slackintegrationobject.AnnouncementAndDefaultChannelHeader().getText();
		Assert.assertEquals(defaultChannelHeader, "Select a default channel to keep all recognition in one channel");
	}

	// Customize Currency  header
	public void customizeTrophyHeaderAssertion() throws InterruptedException {
		Thread.sleep(1000L);
		String customizeTrophyHeader = slackintegrationobject.CustomizeCurrencyHeader().getText();
		Assert.assertEquals(customizeTrophyHeader, "Customize your company’s own recognition currency.");
	}

	// Announcement Header
	public void AnnouncementHeaderAssertion() throws InterruptedException {
		Thread.sleep(1000L);
		String announcementHeader = slackintegrationobject.AnnouncementAndDefaultChannelHeader().getText();
		Assert.assertEquals(announcementHeader, "Want to announce Assembly in your default channel?");
	}

	// Announcement Header
	public void AssemblysetUpHeaderAssertion() throws InterruptedException {
		Thread.sleep(1000L);
		String assemblySetUpHeaderHeader = slackintegrationobject.AssemblySetUpHeader().getText();
		Assert.assertEquals(assemblySetUpHeaderHeader, "Congrats! Your Assembly is setup and ready to go");
	}

	//Landing page validation
	public void GiveRecogPageAssertion() {
		String getRecogText = recogobject.giveRecognitionText().getText();
		Assert.assertEquals(getRecogText, "Give Recognition");
		System.out.println("Application landed on give recognition page");
	}

	// Integrations breadcrum
	public void IntegrationsBreadcrumAssertion() throws InterruptedException {
		Thread.sleep(1000L);
		slackintegrationobject.IntegrationsText().click();
		String breadcrum = slackobject.InvitesBreadcrum().getText();
		Assert.assertEquals(breadcrum, "> Integrations");
	}

	// Check save changes button is enabled, and click on it
	public void validateQuickSettingsSlackIntegrationFunction() {
		slackintegrationobject.QuickSettingsArrowDropDown().click();
		String defaultChannelSelected = slackintegrationobject.CreateChannelInputField().getAttribute("value");
		System.out.println("Default channel is :" + defaultChannelSelected);
		Assert.assertEquals(defaultChannelSelected, "slack-im");
		System.out.println("Assertion passed since default channel value has been fetched");
		Boolean saveChangesActive = slackintegrationobject.SaveChanges().isEnabled();
		System.out.println(saveChangesActive);
		if (saveChangesActive) {
			System.out.println("The Save Changes button is Enabled");
			//System.out.println("User can now click on the save changes button");
			slackintegrationobject.SaveChanges().click();
			slackobject.CloseButton().click();
		} else {
			System.out.println("The Save Changes button is Disabled");
		}

	}

	// Validate Manage landing page when slack integration is connected
	public void validateManageSlackIntegrationConnectedAssertion() throws InterruptedException {
		Thread.sleep(2000L);
		String integrationPageLandingText = slackobject.QuickSettingsText().getText();
		System.out.println("the text is " + integrationPageLandingText);
		Assert.assertEquals(integrationPageLandingText, "Not announced, open ‘Quick Settings’ to announce");
	}
}
