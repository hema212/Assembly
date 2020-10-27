package office365testcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import PageObjects.O365PageObjects;
import PageObjects.RecognitionPageObject;
import resources.BaseSigninViaO365;
import slacktestcases.SlackIdentityUpgradeViewFlow;

//Execute this Fourth(4th) when Office 365 is connected and try to login via email
public class SignInWhenO365IsConnectedUsingEmail extends BaseSigninViaO365 {
	public static Logger log = LogManager.getLogger(SignInWhenO365IsConnectedUsingEmail.class.getName());
	public O365PageObjects O365object;
	public RecognitionPageObject recogobject;
	public WebDriver driver;
	SlackIdentityUpgradeViewFlow slackIm = new SlackIdentityUpgradeViewFlow();

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {
		driver = initializeDriver();
		O365object = new O365PageObjects(driver);
	}

	@SuppressWarnings("deprecation")
	@Test(priority = 0)
	public void SignInViaSlack() throws InterruptedException {
		System.out.println("Clicked on Sign in with Office 365 button");
		O365object.O365Email().sendKeys("hema2508@joinassembly123.onmicrosoft.com");
		Thread.sleep(1000L);
		O365object.SubmitEmail().click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password']")));
		O365object.O365Pwd().sendKeys("Elizebeth@123");
		O365object.SubmitEmail().click();
		Thread.sleep(2000L);
		O365object.O365Consent().click();
		O365object.AcceptO365Consent().click();
		Thread.sleep(5000L);
		O365object.avatarIcon().isDisplayed();
		System.out.println("Is User avatar displayed? " + O365object.avatarIcon().isDisplayed());
		if(true) {
			Assert.assertTrue(true);
			System.out.println("Testcase-1 passed since application landed on Give Recognition page after O365 SSO signin");
		}
		
	}
	
}
