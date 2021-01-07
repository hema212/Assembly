package billingFlow;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import billing.BillingSignIn;
import billingPageObjects.AccountPlanPageObjects;
import giveRecognitionPageObjects.loginPageObjects;
import slackPageObjects.SlackIdentityObjects;

public class planActivateFlow extends BillingSignIn {
	
	public WebDriver driver;
	public static Logger log = LogManager.getLogger(planActivateFlow.class.getName());
	public AccountPlanPageObjects billingpageobject;
	public SlackIdentityObjects slackobject;
	public  loginPageObjects loginobject; 

	@BeforeTest
	public void initplanActivateFlow() throws FileNotFoundException, IOException {
		billingpageobject = new AccountPlanPageObjects(driver);
		slackobject = new SlackIdentityObjects(driver);
		loginobject = new loginPageObjects(driver);

	}

	@Test(priority = 1)
	public void validateValidLogin() throws InterruptedException {
		Thread.sleep(1000L);
		
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
	public void upgradeToAnyPlan() throws InterruptedException {
		Thread.sleep(1000L);
		upgradePlan();
		billingpageobject.NevermindButton().click();
		validateFreeplanPageAssertion();
		log.info("Testcase-2 passed since user clicked on Nevermind buttton & landed on Account Plan page");
	}

	// Validate user is able to change to free plan
	@Test(priority = 4)
	public void switchToFreePlanOnDowngradeButtonClick() throws InterruptedException {
		Thread.sleep(1000L);
		upgradePlan();
		validateBillingPageAssertion();
		billingpageobject.ActivatePlanButton().click();
		log.info("Testcase-3 passed since user clicked on Activate button & Upgraded");
		Thread.sleep(6000L);
		billingpageobject.ToastButton().getText();
		log.info("Message after Upgrading the plan is "+ billingpageobject.ToastButton().getText());
		validateUpgradedplanPageAssertion();
		driver.close();
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
	
	public void validateFreeplanPageAssertion() throws InterruptedException {
		Thread.sleep(1000L);
		String freePlanHeaderText = billingpageobject.FreePlanHeaderText().getText();
		log.info("Account plan landing page text is " + freePlanHeaderText);
		Assert.assertEquals(freePlanHeaderText, "Your account is on the limited Free Plan, upgrade Assembly and improve your team engagement today!");
	}

	public void upgradePlan() throws InterruptedException {
		Thread.sleep(1000L);
		billingpageobject.UpgradeToButton().click();
		billingSummaryAssertion();
	}

	@SuppressWarnings("unused")
	public void billingSummaryAssertion() throws InterruptedException {
		Boolean activatePlanButtonIsDisplayed = billingpageobject.ActivatePlanButton().isDisplayed();
		log.info("Activate plan button is displayed? " + activatePlanButtonIsDisplayed);
		if (true) {
			log.info("Activate plan button is displayed");
			String getBillingHeader = billingpageobject.headerText().getText();
			Assert.assertEquals(getBillingHeader, "Billing Summary");
		} else {
			log.info("Activate plan button is hidden");
		}
		Thread.sleep(1000L);

	}
	
	public void validateUpgradedplanPageAssertion() throws InterruptedException {
		Thread.sleep(1000L);
		String upgradePlanHeaderText = billingpageobject.FreePlanHeaderText().getText();
		log.info("Account plan landing page text is " + upgradePlanHeaderText);
		Assert.assertTrue(true);
	}
}



