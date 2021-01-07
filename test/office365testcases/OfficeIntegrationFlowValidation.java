package office365testcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import giveRecognitionPageObjects.RecognitionPageObject;
import officePageObjects.O365PageObjects;
import officePageObjects.Office365IntegrationPageObjects;
import signInViaSSO.O365SsoSignIn;

//Execute this only when slack is connected and Integrating for the first time / Execute this 6th
public class OfficeIntegrationFlowValidation extends O365SsoSignIn{
	public static Logger log = LogManager.getLogger(OfficeIntegrationFlowValidation.class.getName());
	public Office365IntegrationPageObjects officeintegrationobject;
	public O365PageObjects o365object;
	public RecognitionPageObject recogobject;

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {
		driver = initializeDriver();
		officeintegrationobject = new Office365IntegrationPageObjects(driver);
		o365object = new O365PageObjects(driver);
		recogobject = new RecognitionPageObject(driver);
	}

	/*
	 * //Validate SSO sign in functionality
	 * 
	 * @Test(priority = 1) public void SignInUsingSlack() throws
	 * InterruptedException { System.out.println("Signing in using SSO!!");
	 * Thread.sleep(2000L);
	 * o365object.Office365InputField().sendKeys("joinassembly21");
	 * o365object.Office365ContinueSubmitButton().click();
	 * o365object.Office365EmailInputField().sendKeys("hema+21@joinassembly.com");
	 * o365object.Office365PwdInputField().sendKeys("Assembly2020!");
	 * o365object.Office365SignInButton().click();
	 * ValidateSlackAppActualHeaderAssertion(); Thread.sleep(5000L);
	 * o365object.AllowButton().click();
	 * System.out.println("User clicked on Allow Button"); Thread.sleep(2000L);
	 * GiveRecogPageAssertion();
	 * System.out.println("Testcase-1 passed since user logged in using SSO signin"
	 * );
	 * 
	 * }
	 */
	//Validate channel validation and select slack-im channel and head to the feed
	@Test(priority = 2)
	public void AnnounceLaterFunction() throws InterruptedException {
		System.out.println("Landing on Integrations page");
		Thread.sleep(1000L);
		selectIntegrationSideNavbar();
		Boolean isButtonDisplayed = officeintegrationobject.GetStartedButton().isDisplayed();
		System.out.println("The value of Get started button is " + isButtonDisplayed);
		if (isButtonDisplayed == true) {
			System.out.println("Get started button is displayed");
			officeintegrationobject.GetStartedButton().click();
			Thread.sleep(5000L);
			officeintegrationobject.SelectChannelDropDown().click();
			for (int i = 0; i < 2; i++) {
				Thread.sleep(1000L);
				officeintegrationobject.SelectChannelDropDown().sendKeys(Keys.ARROW_DOWN);
			}
			officeintegrationobject.SelectChannelDropDown().sendKeys(Keys.ENTER);
			Thread.sleep(1000L);
			officeintegrationobject.ChannelNextButton().click();
			String emptyErrorMessage = officeintegrationobject.ChannelErrorMessage().getText();
			System.out.println("Error message when channel is empty : " + emptyErrorMessage);
			Thread.sleep(1000L);
			officeintegrationobject.CreateChannelTab().click();
			officeintegrationobject.CreateChannelInputField().sendKeys("slack-im");
			officeintegrationobject.ChannelNextButton().click();
			String duplicateMessage = officeintegrationobject.AlertMessage().getText();
			Thread.sleep(2000L);
			System.out.println("Error message when duplicate channel name is provided : " + duplicateMessage);
			officeintegrationobject.SelectChannelTab().click();
			officeintegrationobject.SelectChannelDropDown().click();
			officeintegrationobject.ChannelCancelButton().click();

			Thread.sleep(1000L);
			officeintegrationobject.FinishSetUpButton().click();
			selectDefaultChannelHeaderAssertion();
			officeintegrationobject.SelectChannelDropDown().click();
			for (int i = 0; i < 2; i++) {
				Thread.sleep(1000L);
				officeintegrationobject.SelectChannelDropDown().sendKeys(Keys.ARROW_DOWN);
			}
			officeintegrationobject.SelectChannelDropDown().sendKeys(Keys.ENTER);
			officeintegrationobject.ChannelNextButton().click();
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
		System.out.println("The success toast message after saving is: " +o365object.successToast().getText());
	}

	//Function to customize trophy
	public void CustomizeTrophy() throws InterruptedException {
		// customize currency
		Thread.sleep(3000L);
		officeintegrationobject.CustomizeCurrencyInput().clear();
		Thread.sleep(1000L);
		String emptyCustomTrophyError = officeintegrationobject.EmptyCustomTrophyError().getText();
		System.out.println("When custom trophy filed is empty then the error message is " + emptyCustomTrophyError);
		officeintegrationobject.CustomizeCurrencyInput().sendKeys(Keys.chord(Keys.CONTROL, "a"), "Prizeeeeeeeeeeeeeeeeeeeeee");
		Thread.sleep(2000L);
		String pluralText = officeintegrationobject.PluralCustomTrophy().getAttribute("value");
		System.out.println("The plural form of the trophy is :" + pluralText);
		Thread.sleep(1000L);
		officeintegrationobject.ChannelNextButton().click();
	}

	//Function to announce later
	public void AnnouncementLaterFunc() throws InterruptedException {
		AnnouncementHeaderAssertion();
		officeintegrationobject.NoAnnouncementRadioButton().click();
		officeintegrationobject.ChannelNextButton().click();
		AssemblysetUpHeaderAssertion();
		officeintegrationobject.HeadToMyFeed().click();

	}

	// Validate Slack App Actual header
	public void ValidateSlackAppActualHeaderAssertion() {
		String SlackActualHeader = o365object.Office365SigninHeaderText().getText();
		Assert.assertEquals(SlackActualHeader,
				"Assembly v2 Dev is requesting permission to access the Joinassembly+21 Slack workspace");
		System.out.println("Assertion passed during request permission");
	}

	// function to validate landing page of Manage
	public void selectIntegrationSideNavbar() throws InterruptedException {
		Thread.sleep(1000L);
		officeintegrationobject.AvatarIcon().click();
		officeintegrationobject.AdminText().click();
		officeintegrationobject.IntegrationsText().click();
	}

	// Assertions
	// Default channel selection header
	public void selectDefaultChannelHeaderAssertion() throws InterruptedException {
		Thread.sleep(1000L);
		String defaultChannelHeader = officeintegrationobject.AnnouncementAndDefaultChannelHeader().getText();
		Assert.assertEquals(defaultChannelHeader, "Select a default channel to keep all recognition in one channel");
	}

	// Customize Currency  header
	public void customizeTrophyHeaderAssertion() throws InterruptedException {
		Thread.sleep(1000L);
		String customizeTrophyHeader = officeintegrationobject.CustomizeCurrencyHeader().getText();
		Assert.assertEquals(customizeTrophyHeader, "Customize your company’s own recognition currency.");
	}

	// Announcement Header
	public void AnnouncementHeaderAssertion() throws InterruptedException {
		Thread.sleep(1000L);
		String announcementHeader = officeintegrationobject.AnnouncementAndDefaultChannelHeader().getText();
		Assert.assertEquals(announcementHeader, "Want to announce Assembly in your default channel?");
	}

	// Announcement Header
	public void AssemblysetUpHeaderAssertion() throws InterruptedException {
		Thread.sleep(1000L);
		String assemblySetUpHeaderHeader = officeintegrationobject.AssemblySetUpHeader().getText();
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
		officeintegrationobject.IntegrationsText().click();
		String breadcrum = o365object.InvitesBreadcrum().getText();
		Assert.assertEquals(breadcrum, "> Integrations");
	}

	// Check save changes button is enabled, and click on it
	public void validateQuickSettingsSlackIntegrationFunction() {
		officeintegrationobject.QuickSettingsArrowDropDown().click();
		String defaultChannelSelected = officeintegrationobject.CreateChannelInputField().getAttribute("value");
		System.out.println("Default channel is :" + defaultChannelSelected);
		Assert.assertEquals(defaultChannelSelected, "slack-im");
		System.out.println("Assertion passed since default channel value has been fetched");
		Boolean saveChangesActive = officeintegrationobject.SaveChanges().isEnabled();
		System.out.println(saveChangesActive);
		if (saveChangesActive) {
			System.out.println("The Save Changes button is Enabled");
			//System.out.println("User can now click on the save changes button");
			officeintegrationobject.SaveChanges().click();
			o365object.CloseButton().click();
		} else {
			System.out.println("The Save Changes button is Disabled");
		}

	}

	// Validate Manage landing page when slack integration is connected
	public void validateManageSlackIntegrationConnectedAssertion() throws InterruptedException {
		Thread.sleep(2000L);
		String integrationPageLandingText = o365object.QuickSettingsText().getText();
		System.out.println("the text is " + integrationPageLandingText);
		Assert.assertEquals(integrationPageLandingText, "Not announced, open ‘Quick Settings’ to announce");
	}
}
