package billingFlow;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import billingPageObjects.AccountPlanPageObjects;
import giveRecognitionPageObjects.RecognitionPageObject;
import giveRecognitionPageObjects.loginPageObjects;
import resources.InitiateDriver;
import slackPageObjects.SlackIdentityObjects;

public class FreePlanBillingFlow extends InitiateDriver {
	public WebDriver driver;
	public static Logger log = LogManager.getLogger(FreePlanBillingFlow.class.getName());
	public AccountPlanPageObjects billingpageobject;
	public SlackIdentityObjects slackobject;
	public loginPageObjects loginobject;

	@BeforeTest
	public void initFreePlanBillingFlow() throws FileNotFoundException, IOException {
		driver = initializeDriver();
		billingpageobject = new AccountPlanPageObjects(driver);
		slackobject = new SlackIdentityObjects(driver);
		loginobject = new loginPageObjects(driver);
	}

	@Test(priority = 1)
	public void validateValidLogin() throws InterruptedException {
		Thread.sleep(1000L);
		log.info("executing on the browser " + baseurl);
		log.info("Navigated to the provided URL");
		// Create an object forloginObjects class of pageObjects
		loginPageObjects loginobject = new loginPageObjects(driver);
		String actualheader = loginobject.getHeader().getText();
		String expectedheader = "Welcome back!";
		// Check for Assertion
		Assert.assertEquals(actualheader, expectedheader);
		log.info("Assertion passed for login page");
		loginobject.getUsernameObject().sendKeys("hema+devtestuser1@joinassembly.com");
		loginobject.getPasswordObject().sendKeys("testing");
		Thread.sleep(1000L);
		loginobject.signinObject().click();
		RecognitionPageObject recogobject = new RecognitionPageObject(driver);
		String mainContent = recogobject.giveRecognitionText().getText();
		if (mainContent == null) {
			log.info("failed to login!");
		} else {
			log.info("Successfully logged into Assembly homepage!");
			Assert.assertEquals(mainContent, "Give Recognition");
			log.info("Assertion Passed for homepage landing");
		}
	}

	// validate Account Plan page
	@Test(priority = 2)
	public void accountPlan() throws InterruptedException {
		// log.info("Landed on Give Recog page");
		selectAccountPlanSideNavbar();
		Thread.sleep(1000L);
		validateBillingPageAssertion();
		log.info("Testcase-1 passed since application landed on Manage page and Assertion passed");
	}

	// Validate user is able to cancel downgrading to free plan
	@Test(priority = 3)
	public void switchToFreePlanOnKeepPlan() throws InterruptedException {
		Thread.sleep(1000L);
		switchToPlan();
		billingpageobject.KeepPlanButton().click();
		validateBillingPageAssertion();
		log.info("Testcase-2 passed since user clicked on Keep plan button & landed on Account Plan page");
	}

	// Validate user is able to change to free plan
	@Test(priority = 4)
	public void switchToFreePlanOnDowngradeButtonClick() throws InterruptedException {
		Thread.sleep(1000L);
		switchToPlan();
		billingpageobject.DowngradeButton().click();
		validateBillingPageAssertion();
		log.info("Testcase-3 passed since user clicked on Downgrade button & landed on Account Plan page");
		billingpageobject.ToastButton().getText();
		log.info("Message after downgrading to free plan is " + billingpageobject.ToastButton().getText());
		validateFreeplanPageAssertion();
	}

	public void selectAccountPlanSideNavbar() throws InterruptedException {
		Thread.sleep(1000L);
		slackobject.avatarIcon().click();
		slackobject.AdminText().click();
		Thread.sleep(1000L);
		billingpageobject.BillingText().click();
		billingpageobject.AccountPlanText().click();
	}

	public void validateBillingPageAssertion() throws InterruptedException {
		Thread.sleep(1000L);
		String accountPlanBreadcrum = billingpageobject.BreadcrumText().getText();
		log.info("Account plan landing page text is " + accountPlanBreadcrum);
		Assert.assertEquals(accountPlanBreadcrum, "> Account Plan");
	}

	public void switchToPlan() throws InterruptedException {
		billingpageobject.ChangePlanText().click();
		billingpageobject.SelectPlanDropdown().click();
		billingpageobject.PlanListTwo().click();
		billingpageobject.PreviewChangesButton().click();
		Thread.sleep(3000L);
		previewPlanAssertion();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1000)");
		billingpageobject.AcceptCheckbox().click();
	}

	@SuppressWarnings("unused")
	public void previewPlanAssertion() throws InterruptedException {
		Boolean downgradeButtonIsDisplayed = billingpageobject.DowngradeButton().isDisplayed();
		log.info("Downgrade button is :" + downgradeButtonIsDisplayed);
		if (true) {
			log.info("Downgarde now button is displayed");
			String getPreviewHeader = billingpageobject.headerText().getText();
			Assert.assertEquals(getPreviewHeader, "Preview your plan changes");
		} else {
			log.info("Downgarde now button is hidden");
		}
		Thread.sleep(1000L);

	}

	public void validateFreeplanPageAssertion() throws InterruptedException {
		Thread.sleep(1000L);
		String freePlanHeaderText = billingpageobject.FreePlanHeaderText().getText();
		log.info("Account plan landing page text is " + freePlanHeaderText);
		Assert.assertEquals(freePlanHeaderText,
				"Your account is on the limited Free Plan, upgrade Assembly and improve your team engagement today!");
	}

}
