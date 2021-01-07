package giverecognitionpagetestcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import giveRecognitionPageObjects.RecognitionPageObject;
import resources.Base;

public class PostedRecognitionValidation extends Base {

	public static Logger log = LogManager.getLogger(PostedRecognitionValidation.class.getName());
	public RecognitionPageObject recogobject;
	String alertMessageForRemoval;

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {

		driver = initializeDriver();
		recogobject = new RecognitionPageObject(driver);

	}

	@Test(priority = 0)
	public void ValidateLikeCommentOperation() throws InterruptedException {
		recogobject.closePopUp().click();
		addLike();
		addComment();
		deletePostCancelCollectTrophiesBack();
		deletePostCancelGiveTrophies();
		deletePostConfirmCollectTrophiesBack();
	}

	@Test(priority = 1)
	public void ValidateGifPostRemoveOperation() throws InterruptedException {
		Thread.sleep(1000L);
		ValidateCommentDeletion();
		Thread.sleep(1000L);
		ValidateLikeUnlike();
		Thread.sleep(1000L);
		removeImageOrGifFunctionOnRemove();
		Thread.sleep(2000L);
		deletePostConfirmCollectTrophiesBack();
	}

	@Test(priority = 2)
	public void ValidateImageEmojiRemoveoperation() throws InterruptedException {
		Thread.sleep(1000L);
		int commentCountTotal;
		for (commentCountTotal = 1; commentCountTotal < 4; commentCountTotal++) {
			addComment();
			Thread.sleep(1000L);
			// log.info("Total number of comments entered is" +commentCountTotal);
		}
		log.info("Total number of comments entered is" + commentCountTotal);
		addCommentUsingTrophy();
		removeCommentForTrophy();
		removeImageOrGifFunctionOncancel();
		deletePostConfirmCollectTrophiesBack();
	}

	@Test(priority = 3)
	public void ValidateBadgeRemoveOperation() throws InterruptedException {
		Thread.sleep(1000L);
		ValidateLikeUnlike();
		addLike();
		ValidateCommentDeletion();
		deletePostConfirmCollectTrophiesBack();
	}

	@Test(priority = 4)
	public void ValidateGiveRecognitionUsingMessage() throws InterruptedException {
		Thread.sleep(2000L);
		deletePostConfirmGiveTrophies();
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

}
