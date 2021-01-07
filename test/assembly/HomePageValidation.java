package assembly;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import giveRecognitionPageObjects.HomePageObjects;
import giveRecognitionPageObjects.loginPageObjects;
import resources.HomePageLaunchURL;

public class HomePageValidation extends HomePageLaunchURL {
	public static Logger log = LogManager.getLogger(HomePageValidation.class.getName());
	public HomePageObjects homepageobject;
	public loginPageObjects loginobject;
	public int emailFieldCount;
	public String captureErrorHeaderMessage;

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {
		driver = initializeDriver();
		homepageobject = new HomePageObjects(driver);
		loginobject = new loginPageObjects(driver);
	}

	// Testcase to find whether the user has logged onto proper webpage application
	@Test(priority = 0)
	public void joinAssemblyPageAssertion() {
		String actual_title = driver.getTitle();
		String expected_title = "Free Employee Recognition Software with Slack Integration | Assembly®";
		Assert.assertEquals(actual_title, expected_title);
		log.info("Assertion passed since application landed on Employee Free Recognition page");
	}

	// Testcase to verify the count of email fields and click on first email field
	// (If exists) Provide hema@gmail.com & hema@carrothr.com and check for output

	/*
	 * @Test(priority = 1)
	 * 
	 * public void validateNonWorkEmailLogin() throws IOException,
	 * InterruptedException { Thread.sleep(1000L); emailFieldCount =
	 * homepageobject.userEmail().size(); enterEmailClickTryButton(); String
	 * errorMessage =
	 * homepageobject.captureErrorMessage().getAttribute("placeholder");
	 * log.info("Error message after entering non-work email id is : " +
	 * errorMessage); Thread.sleep(2000L); homepageobject.emailOne().clear();
	 * homepageobject.emailOne().sendKeys("hema@carrothr.com");
	 * homepageobject.tryForFreeButton().get(0).click(); Thread.sleep(3000L); String
	 * captureOtpHeader = homepageobject.header().getText();
	 * Assert.assertEquals(captureOtpHeader, "Check your email"); // log.
	 * info("Assertion passed since user landed on OTP page after work email ID is passed"
	 * ); log.
	 * info("Testcase-1 passed since user landed on OTP page after work email ID is passed"
	 * );
	 * 
	 * }
	 * 
	 * // Validate Assembly for free flow
	 * 
	 * @Test(priority = 2) public void validateAssemblyForFreeFlow() throws
	 * IOException, InterruptedException { driver.navigate().back();
	 * homepageobject.tryAssemblyForFree().click(); Thread.sleep(3000L); String
	 * captureOtpHeader = homepageobject.header().getText();
	 * Assert.assertEquals(captureOtpHeader, "Create a new Assembly");
	 * log.info("Testcase-2 passed since user landed on create assembly page");
	 * Thread.sleep(1000L);
	 * loginobject.getUsernameObject().sendKeys("hema@joinassembly.com");
	 * loginobject.confirmButton().click(); captureErrorHeaderMessage =
	 * loginobject.accountExistsErrorMessage().getText();
	 * log.info("The error message is : " + captureErrorHeaderMessage); Boolean
	 * isSignInButtonActive = loginobject.goBackToSignInButton().isEnabled();
	 * log.info("Is signin button displayed? " + isSignInButtonActive);
	 * Assert.assertEquals(captureErrorHeaderMessage,
	 * "Whoops, you already have an account!");
	 * log.info("Testcase-2 passed since Account Exists message is displayed");
	 * Thread.sleep(1000L); loginobject.goBackToSignInButton().click();
	 * 
	 * }
	 * 
	 * // Validate Sign in flow
	 * 
	 * @Test(priority = 3) public void validateSignInFlow() throws IOException,
	 * InterruptedException { driver.navigate().back();
	 * homepageobject.signInButton().click(); Thread.sleep(5000L); String
	 * captureOtpHeader = homepageobject.header().getText();
	 * Assert.assertEquals(captureOtpHeader, "Welcome back!");
	 * log.info("Testcase-3 passed since user landed on Sign in page");
	 * 
	 * }
	 * 
	 * // Validate Home page content validation
	 * 
	 * @Test(priority = 4) public void homePageContentValidation() throws
	 * IOException, InterruptedException { driver.navigate().back();
	 * homepageobject.homeLink().click(); Thread.sleep(1000L); String
	 * captureOtpHeader = homepageobject.homeHeader().getText();
	 * Assert.assertEquals(captureOtpHeader, "Empower your team");
	 * log.info("Testcase-4 passed since user landed on Home page content");
	 * 
	 * }
	 * 
	 * // Validate Features page content validation
	 * 
	 * @Test(priority = 5) public void featureContentValidation() throws
	 * IOException, InterruptedException { String captureFeatureContent = null;
	 * homepageobject.featuresLink().click(); Thread.sleep(1000L); String
	 * captureOtpHeader = homepageobject.headernavLinkContent().getText();
	 * Assert.assertEquals(captureOtpHeader, "Grow and sustain your culture");
	 * log.info("Testcase-5 passed since user can view features page content");
	 * Thread.sleep(1000L); int featurelistsize =
	 * homepageobject.featureSideNavList().size();
	 * System.out.println("The feature list length is :" + featurelistsize); if
	 * (featurelistsize >= 0) { Thread.sleep(1000L);
	 * System.out.println("Is Recognition link is active by default? " +
	 * homepageobject.featureSideNavList().get(0).isEnabled());
	 * homepageobject.featureRecognitionList().get(0).isSelected();
	 * System.out.println("Is Birthday & Anniversary link is active by default? " +
	 * homepageobject.featureRecognitionList().get(0).isSelected());
	 * homepageobject.birthdayAnniversary().click(); }
	 * 
	 * for (int j = 0; j < featurelistsize; j++) { captureFeatureContent =
	 * homepageobject.featureSideNavList().get(j).getText();
	 * System.out.println("The feature list content is :" + captureFeatureContent);
	 * } System.out.println("The feature list content is :" +
	 * captureFeatureContent);
	 * 
	 * }
	 * 
	 * // Validate request a demo button functionality
	 * 
	 * @Test(priority = 6) public void requestDemoFunc() throws InterruptedException
	 * { homepageobject.requestDemoButton().click(); String captureDemoBookHeader =
	 * homepageobject.header().getText(); Assert.assertEquals(captureDemoBookHeader,
	 * "Book a demo with Assembly");
	 * driver.switchTo().frame(homepageobject.demoIframe()); Thread.sleep(1000L);
	 * homepageobject.selectDemoTime().click();
	 * System.out.println("Clicked on 8:30 button");
	 * homepageobject.firstName().sendKeys("Jonathan");
	 * homepageobject.lastName().sendKeys("fields");
	 * homepageobject.demoEmail().sendKeys("jon@gmail.com");
	 * homepageobject.messageTextarea().sendKeys("Slack");
	 * homepageobject.confirmButton().click(); }
	 * 
	 * // Validate request a demo button functionality
	 * 
	 * @Test(priority = 7) public void requestDemoFuncCont() throws
	 * InterruptedException { String captureDemoBookConfirmHeader =
	 * homepageobject.bookingConfirmedMessage().getText();
	 * Assert.assertEquals(captureDemoBookConfirmHeader, "Booking confirmed");
	 * System.out.println("Booked demo timing is : " +
	 * homepageobject.bookingTiming().getText());
	 * driver.switchTo().defaultContent(); Thread.sleep(1000L);
	 * homepageobject.closeModal().click(); Thread.sleep(1000L); // Validate booked
	 * details homepageobject.requestDemoButton().click(); String
	 * captureDemoBookHeader = homepageobject.header().getText();
	 * Assert.assertEquals(captureDemoBookHeader, "Book a demo with Assembly");
	 * homepageobject.closeModal().click();
	 * 
	 * }
	 */

	public void enterEmailClickTryButton() {
		int i;
		log.info("The total count of email fields are " + emailFieldCount);
		for (i = 0; i < emailFieldCount; i++) {
			homepageobject.userEmail().get(i).sendKeys("hema@gmail.com");
			i++;
		}
		System.out.println("the value of i is " + i);
		homepageobject.tryForFreeButton().get(0).click();
	}
}
