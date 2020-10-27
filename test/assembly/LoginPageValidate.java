package assembly;

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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import PageObjects.RecognitionPageObject;
import resources.Base;

public class LoginPageValidate extends Base {

	public static Logger log = LogManager.getLogger(LoginPageValidate.class.getName());
	public int getUserListSize;
	public RecognitionPageObject recogobject;
	String alertMessageForRemoval;

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {

		driver = initializeDriver();
		recogobject = new RecognitionPageObject(driver);

	}

	/*
	 * @BeforeMethod public void checkTrophyCountAlways() throws
	 * InterruptedException { HomePageValidation homeobject = new
	 * HomePageValidation(driver); homeobject.getTotalTropyCountFunction(); }
	 */

	@Test(priority = 1)
	public void ValidateGiveRecognitionUsingMessage() throws InterruptedException {

		String getRecogText = recogobject.giveRecognitionText().getText();
		Assert.assertEquals(getRecogText, "Give Recognition");
		log.info("Application landed on give recognition page");

		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("integration-downshift-multiple")));
		boolean giveRecogButtonStatus = recogobject.getGiveRecognitionButton().isEnabled();
		log.info("The status of Give recognition button is " + giveRecogButtonStatus);
		giverecognitionToUserOne();
		recogobject.closePopUp().click();
		recogobject.addMessage().sendKeys("Thanks for the support!!");
		recogobject.getGiveRecognitionButton().click();
		log.info("The status of Give recognition is " + giveRecogButtonStatus);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='client-snackbar']")));
		System.out.println("Is success toast displayed after giving recognition? "
				+ recogobject.getSuccessSendRecognitionToast().isDisplayed());
		addLike();
		addComment();
		deletePostCancelCollectTrophiesBack();
		deletePostCancelGiveTrophies();
		deletePostConfirmCollectTrophiesBack();
	}

	@Test(priority = 2) public void ValidateGiveRecognitionUsingBadgeOnly()
	  throws InterruptedException { Thread.sleep(4000L);
	  giverecognitionToUserTwo();
	  recogobject.addMessage().sendKeys("You did an amazing job!!");
	  recogobject.getAddbadge().click(); boolean badgeIsDisplayed =
	  recogobject.getIdeaMakerBadge().isDisplayed();
	  System.out.println("Is Idea Maker badge displayed? " + badgeIsDisplayed);
	  recogobject.getIdeaMakerBadge().click(); addCustomTrophyRemaining();
	  recogobject.getGiveRecognitionButton().click(); 
	  // ValidateLikeComment();
	  ValidateLikeUnlike(); 
	  addLike(); 
	  
	  ValidateCommentDeletion(); 
	  

	 deletePostConfirmCollectTrophiesBack();

	}

	@Test(priority = 3)
	public void ValidateGiveRecognitionUsingImageEmoji() throws InterruptedException {
		Thread.sleep(2000L);

		giverecognitionToUserOne();
		recogobject.addMessage().sendKeys("Uploading an Image!!");
		recogobject.getAddImage().sendKeys("C:\\Users\\Jayasurya S\\Documents\\AssemblySS\\Namefield.png");
		Thread.sleep(1000L);
		log.info("Uploaded .png image");
		recogobject.allowUploadImage().click();
		Thread.sleep(1000L);
		recogobject.getAddEmoji().click();
		recogobject.selectThumbsupEmoji().click();
		recogobject.getGiveRecognitionButton().click();
		Thread.sleep(5000L);
		int commentCountTotal;
		for (commentCountTotal = 1; commentCountTotal < 4; commentCountTotal++) {
			addComment();
			Thread.sleep(1000L); //
			log.info("Total number of comments entered is" + commentCountTotal);
		}

		log.info("Total number of comments entered is" + commentCountTotal);

		addCommentUsingTrophy();
		removeCommentForTrophy();
		removeImageOrGifFunctionOncancel();
		deletePostConfirmCollectTrophiesBack();
	}

	

	@Test(priority = 4)
	public void ValidateGiveRecognitionUsingGifOnly() throws InterruptedException {
		Thread.sleep(5000L);
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
		Thread.sleep(1000L);
		ValidateCommentDeletion();
		Thread.sleep(1000L);
		ValidateLikeUnlike();
		Thread.sleep(1000L);
		removeImageOrGifFunctionOnRemove();
		Thread.sleep(2000L);
		//deletePostConfirmCollectTrophiesBack();
		deletePostConfirmGiveTrophies();
		Thread.sleep(1000L);
		validateGiveRecognitionInputFields();
	}

	@Test(priority = 5)
	public void checkTrophiesDropdownZeroTrophy() throws InterruptedException {

		Thread.sleep(2000L);
		recogobject.addMessage().clear();
		recogobject.addMessage().sendKeys("Give all trophies");
		addCustomTrophyRemaining();
		recogobject.getGiveRecognitionButton().click();
		Thread.sleep(2000L);

		String messageWhenNoTrophies = recogobject.getTextNoTrophies().getAttribute("title");
		System.out.println("The message is ---> " + messageWhenNoTrophies);
		Assert.assertEquals(messageWhenNoTrophies,
				"This field is disabled. You’ve used your allowance but you can still give recognitions");
		recogobject.getSearchCoworker().click();
		recogobject.getdropdownMemberList().get(1).click();
		recogobject.addMessage().sendKeys("Giving recognition without trophies");
		recogobject.getGiveRecognitionButton().click();
		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='client-snackbar']")));
		System.out.println("Is success toast displayed after giving recognition? "
				+ recogobject.getSuccessSendRecognitionToast().isDisplayed());
		Thread.sleep(10000L);
		recogobject.getGiveRecognitionButton().click();
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

	public void giverecognitionToUserOne() throws InterruptedException {
		Thread.sleep(1000L);
		recogobject.getSearchCoworker().click();
		getUserListSize = recogobject.getdropdownMemberList().size();
		log.info("The number of users in the list is " + getUserListSize);

		if (getUserListSize >= 1) {
			recogobject.getdropdownMemberList().get(0).click();
			recogobject.getAddTrophiesDropdown().click();
			recogobject.getAddThirtyThropies().click();
		} else {
			log.info("No users Available! Please invite users");
		}
	}

	public void giverecognitionToUserTwo() throws InterruptedException {
		Thread.sleep(1000L);
		recogobject.getSearchCoworker().click();
		getUserListSize = recogobject.getdropdownMemberList().size();
		log.info("The number of users in the list is " + getUserListSize);

		if (getUserListSize >= 1) {
			recogobject.getdropdownMemberList().get(1).click();
			recogobject.getAddTrophiesDropdown().click();
			recogobject.getAddTwentyThropies().click();
		} else {
			log.info("No users Available! Please invite users");
		}
	}

	public void ValidateCommentDeletion() throws InterruptedException {
		addComment();
		Thread.sleep(2000L);
		recogobject.commentIconButton().click();
		recogobject.deleteCommentThreedots().click();

		recogobject.deleteComment().click();
		Thread.sleep(1000L);
		String alertMessageForRemoval = recogobject.getCaptutreAlertText().getText();
		Assert.assertEquals(alertMessageForRemoval, "Are you sure you want to delete this comment?");
		recogobject.deleteCommentOrPost().click();
		log.info("The comment is removed");

	}

	public void addLike() {
		recogobject.likeButton().click();
		String storeLikeCount = recogobject.likeCount().getText();
		int likeCount = Integer.parseInt(storeLikeCount);
		if (likeCount != 0) {
			log.info("Liked recognition");
		} else {
			log.info("No likes");
		}
	}

	public void addComment() {
		recogobject.commentIconButton().click();
		String getCommentText = recogobject.enterComments().getText();
		if (getCommentText.equals(null)) {
			log.info("Cannot enter message here");
		} else {
			recogobject.enterComments().sendKeys("Testing comment here");
			recogobject.postComment().click();
		}
		recogobject.commentIconButton().click();
	}

	public void addCommentUsingTrophy() throws InterruptedException {
		recogobject.commentIconButton().click();
		String getCommentText = recogobject.enterComments().getText();
		if (getCommentText.equals(null)) {
			log.info("Cannot enter message here");
		} else {
			recogobject.enterComments().sendKeys("Testing comment here with trophies");
			recogobject.commentDropdown().click();
			Thread.sleep(2000L);
			recogobject.commentDropdownSelectTrophies().click();
			recogobject.postComment().click();
		}
		recogobject.commentIconButton().click();
	}

	public void removeCommentForTrophy() throws InterruptedException {
		Thread.sleep(2000L);
		recogobject.commentIconButton().click();
		Thread.sleep(3000L);
		recogobject.commentThreeDots().click();
		Thread.sleep(1000L);
		recogobject.deleteComment().click();
		alertMessageForRemoval = recogobject.getCaptutreAlertText().getText();
		Assert.assertEquals(alertMessageForRemoval, "Delete post or comment");
		Thread.sleep(1000L);
		recogobject.selectDropDownToHandleTrophies().click();
		recogobject.selectGiveBackTrophiesToSender().click();
		recogobject.deleteCommentOrPost().click();
		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 20);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='client-snackbar']")));
		System.out.println("Is success toast displayed? " + recogobject.getSuccessSendRecognitionToast().isDisplayed());
		Assert.assertTrue(true);

	}

	public void ValidateLikeUnlike() {
		for (int i = 0; i < 2; i++) {
			recogobject.likeButton().click();
		}
	}

	// Delete post - Collect trophies back
	public void deletePostConfirmCollectTrophiesBack() throws InterruptedException {
		validateDeletePostAllowSenderToKeepTrophies();
		recogobject.deleteCommentOrPost().click();
		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 20);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='client-snackbar']")));
		System.out.println("Is success toast displayed? " + recogobject.getSuccessSendRecognitionToast().isDisplayed());
		Assert.assertTrue(true);
	}

	// Cancel post deletion - Collect trophies back
	public void deletePostCancelCollectTrophiesBack() throws InterruptedException {
		validateDeletePostAllowSenderToKeepTrophies();
		recogobject.cancelDeletingPost().click();
		System.out.println("The post is not deleted");
		Assert.assertTrue(true);

	}

	// Cancel post deletion - allow receiver to keep trophies
	public void deletePostCancelGiveTrophies() throws InterruptedException {
		validateDeletePostAllowReceiverToKeepTrophies();
		recogobject.cancelDeletingPost().click();
		System.out.println("The post is not deleted");
		Assert.assertTrue(true);
	}

	// Delete post - allow receiver to keep trophies
	public void deletePostConfirmGiveTrophies() throws InterruptedException {
		validateDeletePostAllowReceiverToKeepTrophies();
		recogobject.deleteCommentOrPost().click();
		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 20);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='client-snackbar']")));
		System.out.println("Is success toast displayed? " + recogobject.getSuccessSendRecognitionToast().isDisplayed());
		Assert.assertTrue(true);
	}

	public void validateDeletePostAllowSenderToKeepTrophies() throws InterruptedException {
		recogobject.deletePostThreedots().click();
		recogobject.deletePostFromPage().click();

		Thread.sleep(1000L);
		alertMessageForRemoval = recogobject.getCaptutreAlertText().getText();
		Assert.assertEquals(alertMessageForRemoval, "Delete post or comment");
		Thread.sleep(1000L);
		recogobject.selectDropDownToHandleTrophies().click();
		recogobject.selectGiveBackTrophiesToSender().click();
	}

	public void validateDeletePostAllowReceiverToKeepTrophies() throws InterruptedException {
		recogobject.deletePostThreedots().click();
		recogobject.deletePostFromPage().click();
		Thread.sleep(1000L);
		alertMessageForRemoval = recogobject.getCaptutreAlertText().getText();
		Assert.assertEquals(alertMessageForRemoval, "Delete post or comment");
		recogobject.selectDropDownToHandleTrophies().click();
		Thread.sleep(1000L);
		recogobject.allowReceiverToKeepTrophies().click();
	}

	boolean imageIsdisplayed;
	boolean GifIsDisplayed;

	public void removeImageOrGifFunctionOncancel() throws InterruptedException {

		recogobject.deletePostThreedots().click();
		String removeImageGifText = recogobject.removeImageOrGifList().getText();
		if (removeImageGifText.equalsIgnoreCase("Remove Image")) {
			recogobject.removeImageOrGifList().click();
			System.out.println("Trying to remove image?");
			Thread.sleep(1000L);
			alertMessageForRemoval = recogobject.getCaptutreAlertText().getText();
			Assert.assertEquals(alertMessageForRemoval, "Are you sure you want to remove this image?");
			recogobject.cancelRemovingImageOrGif().click();
			System.out.println("Image is not deleted since user clicked on cancel");
			imageIsdisplayed = recogobject.checkImageIsdisplayed().isDisplayed();
			Assert.assertTrue(imageIsdisplayed);
			if (imageIsdisplayed) {
				System.out.println("Image is present");
			}

		} else if (removeImageGifText.equalsIgnoreCase("Remove Gif")) {
			recogobject.removeImageOrGifList().click();
			System.out.println("Trying to remove Gif?");
			alertMessageForRemoval = recogobject.getCaptutreAlertText().getText();
			Assert.assertEquals(alertMessageForRemoval, "Are you sure you want to remove this GIF?");
			recogobject.cancelRemovingImageOrGif().click();
			System.out.println("Gif is not deleted since user clicked on cancel");
			GifIsDisplayed = recogobject.checkImageIsdisplayed().isDisplayed();
			Assert.assertTrue(GifIsDisplayed);
			if (GifIsDisplayed) {
				System.out.println("Gif is present");
			}
		}

	}

	public void removeImageOrGifFunctionOnRemove() throws InterruptedException {
		recogobject.deletePostThreedots().click();
		String removeImageGifText = recogobject.removeImageOrGifList().getText();
		if (removeImageGifText.equalsIgnoreCase("Remove Image")) {
			recogobject.removeImageOrGifList().click();
			System.out.println("Trying to remove image?");
			alertMessageForRemoval = recogobject.getCaptutreAlertText().getText();
			Assert.assertEquals(alertMessageForRemoval, "Are you sure you want to remove this image?");
			recogobject.removeImageOrGif().click();
			System.out.println("Image is deleted since user clicked on Remove button");
			imageIsdisplayed = recogobject.checkImageIsdisplayed().isDisplayed();
			Assert.assertTrue(imageIsdisplayed);
			if (imageIsdisplayed) {
				System.out.println("Image is deleted");
			}

		} else if (removeImageGifText.equalsIgnoreCase("Remove Gif")) {
			recogobject.removeImageOrGifList().click();
			System.out.println("Trying to remove Gif?");
			Thread.sleep(1000L);
			alertMessageForRemoval = recogobject.getCaptutreAlertText().getText();
			Assert.assertEquals(alertMessageForRemoval, "Are you sure you want to remove this GIF?");
			recogobject.removeImageOrGif().click();
			System.out.println("Gif is deleted since user clicked on Remove button");
			GifIsDisplayed = recogobject.checkGifIsDisplayed().isDisplayed();
			Thread.sleep(1000L);
			Assert.assertTrue(GifIsDisplayed);
			if (GifIsDisplayed) {
				System.out.println("Gif is deleted");
			}
		}

	}

	public void validateGiveRecognitionInputFields() throws InterruptedException {
		String disabledbuttonclass = recogobject.getGiveRecognitionButton().getAttribute("disabled");
		System.out.println(disabledbuttonclass);
		Assert.assertTrue(true);
		if (true) {
			System.out.println("Give Recognition Button is disabled!");
			giverecognitionToUserTwo();
			System.out.println("The value of disabled button is" + disabledbuttonclass);
			recogobject.addMessage().sendKeys("All mandatory fields filled. Disabled button value should be false");
		}
		String checkIfDisabled = recogobject.getGiveRecognitionButton().getAttribute("disabled");
		if (checkIfDisabled == null)
			System.out.println("Give Recognition button is active");

	}
	/*
	 * @AfterTest public void closedriver() { driver.close(); }
	 */

	/*
	 * @Test() public void getTrophyCount(){ // JavascriptExecutor js =
	 * (JavascriptExecutor) driver; // js.executeScript("window.scrollBy(0, 1000)");
	 * //Actions actions = new Actions(driver);
	 * //actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
	 * 
	 * 
	 * RecognitionPageObject recogobject = new RecognitionPageObject(driver);
	 * JavascriptExecutor js = (JavascriptExecutor) driver; WebElement element =
	 * driver.findElement(By.xpath("//a[contains(text(),'Close this please')]"));
	 * js.executeScript("arguments[0].scrollIntoView(true);", element);
	 * 
	 * recogobject.getGivingAllowanceBalanceCount().click(); Dimension
	 * remainingAllowanceCount = recogobject.getTotalAllowanceCount().getSize();
	 * System.out.println("testing" + remainingAllowanceCount); }
	 */

	@DataProvider
	public Object[][] getData() {
		Object[][] logindata = new Object[2][2];
		logindata[0][0] = "hema+21@joinassembly.com";
		logindata[0][1] = "jonsnow09!";

		logindata[1][0] = "hema_21@joinassembly.com";
		logindata[1][1] = "jonsndow09!";

		return logindata;
	}

}
