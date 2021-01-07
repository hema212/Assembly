package giverecognitionpagetestcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import giveRecognitionPageObjects.RecognitionPageObject;
import resources.Base;

public class GiveRecognitionValidation extends Base {
	public static Logger log = LogManager.getLogger(GiveRecognitionValidation.class.getName());
	public int getUserListSize;
	public RecognitionPageObject recogobject;
	String alertMessageForRemoval;

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {

		driver = initializeDriver();
		recogobject = new RecognitionPageObject(driver);
	}

	@Test(priority = 1)
	public void ValidateGiveRecognitionUsingMessage() throws InterruptedException {
		Thread.sleep(2000L);
		String getRecogText = recogobject.giveRecognitionText().getText();
		Assert.assertEquals(getRecogText, "Give Recognition");
		System.out.println("Give recog text is displayed");
		System.out.println("Application landed on give recognition page");
		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("integration-downshift-multiple")));
		String giveRecogButtonStatus = recogobject.getGiveRecognitionButton().getAttribute("disabled");
		System.out.println("The status of Give recognition button is disabled?" + giveRecogButtonStatus);
		giverecognitionToUserOne();
		recogobject.closePopUp().click();
		recogobject.addMessage().sendKeys("Thanks for the support!!");
		recogobject.getGiveRecognitionButton().click();
		System.out.println("Is give recognition button enabled? "
				+ recogobject.getGiveRecognitionButton().getAttribute("disabled"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='client-snackbar']")));
		if (recogobject.getSuccessSendRecognitionToast().isDisplayed()) {
			System.out.println("Success toast is displayed after post");
		}
		Thread.sleep(3000L);
		validateEmptyFieldAfterPost();

	}

	@Test(priority = 2)
	public void ValidateGiveRecognitionUsingBadgeOnly() throws InterruptedException {
		Thread.sleep(8000L);
		giverecognitionToUserTwo();
		recogobject.addMessage().sendKeys("You did an amazing job!!");
		recogobject.getAddbadge().click();
		boolean badgeIsDisplayed = recogobject.getIdeaMakerBadge().isDisplayed();
		System.out.println("Is Idea Maker badge displayed? " + badgeIsDisplayed);
		recogobject.getIdeaMakerBadge().click();
		recogobject.getGiveRecognitionButton().click();

		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='client-snackbar']")));
		if (recogobject.getSuccessSendRecognitionToast().isDisplayed()) {
			System.out.println("Success toast is displayed after post");
		}
		Thread.sleep(3000L);
		validateEmptyFieldAfterPost();

	}

	@Test(priority = 3)
	public void ValidateGiveRecognitionUsingImageEmoji() throws InterruptedException {
		Thread.sleep(8000L);

		giverecognitionToUserOne();
		recogobject.addMessage().sendKeys("Uploading an Image!!");
		recogobject.getAddImage().sendKeys("C:\\Users\\Jayasurya S\\Documents\\AssemblySS\\Namefield.png");
		Thread.sleep(1000L);
		System.out.println("Uploaded .png image");
		recogobject.allowUploadImage().click();
		Thread.sleep(1000L);
		recogobject.getAddEmoji().click();
		recogobject.selectThumbsupEmoji().click();
		recogobject.getGiveRecognitionButton().click();

		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='client-snackbar']")));
		if (recogobject.getSuccessSendRecognitionToast().isDisplayed()) {
			System.out.println("Success toast is displayed after post");
		}
		Thread.sleep(3000L);
		validateEmptyFieldAfterPost();

	}

	@Test(priority = 4)
	public void ValidateGiveRecognitionUsingGifOnly() throws InterruptedException {
		Thread.sleep(8000L);
		giverecognitionToUserTwo();
		recogobject.addMessage().sendKeys("Uploading a GIF!!");
		recogobject.getAddGif().click();
		recogobject.enterTextInGif().sendKeys("Welcome");
		recogobject.enterTextInGif().sendKeys(Keys.ENTER);

		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='listbox']/div[1]")));
		recogobject.selectGif().click();
		Thread.sleep(1000L);
		recogobject.getGiveRecognitionButton().click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='client-snackbar']")));
		if (recogobject.getSuccessSendRecognitionToast().isDisplayed()) {
			System.out.println("Success toast is displayed after post");
		}
		Thread.sleep(3000L);
		validateEmptyFieldAfterPost();

	}

	@Test(priority = 5)
	public void checkTrophiesDropdownZeroTrophy() throws InterruptedException {

		Thread.sleep(7000L);
		giverecognitionToUserOne();
		recogobject.addMessage().clear();
		recogobject.addMessage().sendKeys("Give all trophies");
		addCustomTrophyRemaining();
		recogobject.getGiveRecognitionButton().click();
		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='client-snackbar']")));
		Thread.sleep(3000L);
		String messageWhenNoTrophies = recogobject.getTextNoTrophies().getAttribute("title");
		System.out.println("The message is ---> " + messageWhenNoTrophies);
		Assert.assertEquals(messageWhenNoTrophies,
				"This field is disabled. You’ve used your allowance but you can still give recognitions");
		recogobject.getSearchCoworker().click();
		recogobject.getdropdownMemberList().get(1).click();
		recogobject.addMessage().sendKeys("Giving recognition without trophies");
		recogobject.getGiveRecognitionButton().click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='client-snackbar']")));
		if (recogobject.getSuccessSendRecognitionToast().isDisplayed()) {
			System.out.println("Success toast is displayed after post");
		}
		Thread.sleep(3000L);
		validateEmptyFieldAfterPost();

	}

	public void giverecognitionToUserOne() throws InterruptedException {
		Thread.sleep(1000L);
		recogobject.getSearchCoworker().click();
		getUserListSize = recogobject.getdropdownMemberList().size();
		System.out.println("The number of users in the list is " + getUserListSize);

		if (getUserListSize >= 1) {
			recogobject.getdropdownMemberList().get(0).click();
			recogobject.getAddTrophiesDropdown().click();
			recogobject.getAddTenTrophies().click();
		} else {
			System.out.println("No users Available! Please invite users");
		}
	}

	public void giverecognitionToUserTwo() throws InterruptedException {
		Thread.sleep(1000L);
		recogobject.getSearchCoworker().click();
		getUserListSize = recogobject.getdropdownMemberList().size();
		System.out.println("The number of users in the list is " + getUserListSize);

		if (getUserListSize >= 1) {
			recogobject.getdropdownMemberList().get(1).click();
			recogobject.getAddTrophiesDropdown().click();
			recogobject.getAddTwentyThropies().click();
		} else {
			System.out.println("No users Available! Please invite users");
		}
	}

	public void validateEmptyFieldAfterPost() {
		String ActualRecogTitle = recogobject.giveRecognitionText().getText();
		Assert.assertEquals(ActualRecogTitle, "Give Recognition");
		String getCoworkerFieldText = recogobject.getSearchCoworker().getText();
		// System.out.println("The value of coworker field
		// isEmpty?"+getCoworkerFieldText.length());
		if (getCoworkerFieldText.length() == 0) {
			System.out.println("No coworker selected");
		} else {
			System.out.println("Previous coworker value selected");
		}
		boolean trophyFieldIsSelected = recogobject.getAddTrophiesDropdown().isSelected();
		if (trophyFieldIsSelected == false) {
			System.out.println("No Previous trophy value");
		} else {
			System.out.println("Previous trophy value exists!");
		}
		// System.out.println("By default any trophies selected?"
		// +trophyFieldIsSelected);
		String addMessageIsEmpty = recogobject.addMessage().getText();
		System.out.println("The length of the message is " + addMessageIsEmpty.length());
		if (addMessageIsEmpty.length() == 0) {
			System.out.println("No previous message");
		} else if (addMessageIsEmpty.length() > 0) {
			System.out.println("Previous message exists");
		}

	}

	public void addCustomTrophyRemaining() throws InterruptedException {
		Thread.sleep(1000L);
		recogobject.getAddTrophiesDropdown().click();
		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 20);

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[contains(text(), '+ Enter a custom amount')]")));
		Thread.sleep(1000L);
		recogobject.enterCustomTrophy().click();
		System.out.println(recogobject.customTrophyTitle().getText());
		Thread.sleep(1000L);
		String giveUptoTrophyCount = recogobject.giveUptoTrophyText().getText();
		// Thread.sleep(1000L);
		System.out.println(giveUptoTrophyCount.substring(giveUptoTrophyCount.length() - 3));
		giveUptoTrophyCount = giveUptoTrophyCount.substring(giveUptoTrophyCount.length() - 3);
		recogobject.customAmountTextField().sendKeys(giveUptoTrophyCount);
		recogobject.enterbutton().click();
	}
}
