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
import giveRecognitionPageObjects.RecognitionPageObject;
import giveRecognitionPageObjects.loginPageObjects;
import resources.InitiateDriver;
import slackPageObjects.SlackIdentityObjects;

public class changepaymentMethodFlow extends BillingSignIn {
	public WebDriver driver;
	public static Logger log = LogManager.getLogger(changepaymentMethodFlow.class.getName());
	public AccountPlanPageObjects billingpageobject;
	public SlackIdentityObjects slackobject;
	public loginPageObjects loginobject;

	@BeforeTest
	public void initchangepaymentMethodFlow() throws FileNotFoundException, IOException {
		driver = initializeDriver();
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
		selectAccountPlanSideNavbar();
		Thread.sleep(1000L);
		validateBillingPageAssertion();
		System.out.println("Testcase-1 passed since application landed on Manage page and Assertion passed");
	}

	// Validate user is able to change card details
	@Test(priority = 3)
	public void changeCardDetails() throws InterruptedException {
		Thread.sleep(1000L);
		updateCardDetails();
		Thread.sleep(1000L);
		billingpageobject.ChangeCardButton().click();
	}

	// Validate card details using Empty + valid data
	@Test(priority = 4)
	public void validateEnteredCardDetails() throws InterruptedException {
		validateEmptyCardDetails();
		Thread.sleep(2000L);
		billingpageobject.CardNameInputField().sendKeys("wayne");
		Thread.sleep(1000L);
		this.driver.switchTo().frame(billingpageobject.IframeCardNumber());
		billingpageobject.CardNumberInputField().sendKeys("4242424242424242");
		driver.switchTo().defaultContent();
		this.driver.switchTo().frame(billingpageobject.IframeExpiryDate());
		billingpageobject.CardExpiryDateField().sendKeys("1222");
		driver.switchTo().defaultContent();
		this.driver.switchTo().frame(billingpageobject.IframeCvcNumber());
		billingpageobject.CardCvcField().sendKeys("123");
		driver.switchTo().defaultContent();
		billingpageobject.UpdateCardButton().click();
		Thread.sleep(1000L);
		billingpageobject.ToastButton().getText();
		System.out.println("Message after Upgrading card is " + billingpageobject.ToastButton().getText());
		Assert.assertTrue(true);
	}

	public void selectAccountPlanSideNavbar() throws InterruptedException {
		Thread.sleep(1000L);
		slackobject.avatarIcon().click();
		slackobject.AdminText().click();
		Thread.sleep(1000L);
		billingpageobject.BillingText().click();
		billingpageobject.AccountPlanText().click();
	}

	public void updateCardDetails() throws InterruptedException {
		Thread.sleep(1000L);
		billingpageobject.ChangePaymentText().click();
		validatePaymentPageAssertion();
	}

	public void validateBillingPageAssertion() throws InterruptedException {
		Thread.sleep(1000L);
		String accountPlanBreadcrum = billingpageobject.BreadcrumText().getText();
		System.out.println("Account plan landing page text is " + accountPlanBreadcrum);
		Assert.assertEquals(accountPlanBreadcrum, "> Account Plan");
	}

	public void validatePaymentPageAssertion() throws InterruptedException {
		Thread.sleep(1000L);
		String paymentMethodBreadcrum = billingpageobject.BreadcrumText().getText();
		System.out.println("Account plan landing page text is " + paymentMethodBreadcrum);
		Assert.assertEquals(paymentMethodBreadcrum, "> Payment Method");
	}

	public void validateEmptyCardDetails() throws InterruptedException {
		Thread.sleep(1000L);
		billingpageobject.UpdateCardButton().click();
		String toastMessage = billingpageobject.ToastButton().getText();
		Thread.sleep(2000L);
		System.out.println("Message after Upgrading the plan is " + toastMessage);
		Assert.assertEquals(toastMessage, "Validating credit card");
	}
}
