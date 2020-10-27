package giverecognitionpagetestcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import PageObjects.RecognitionPageObject;
import resources.Base;

public class TrophyCountDisplayValidation extends Base {
	public static Logger log = LogManager.getLogger(TrophyCountDisplayValidation.class.getName());
	public RecognitionPageObject recogobject;

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {

		driver = initializeDriver();
		recogobject = new RecognitionPageObject(driver);

	}

	// Get Giving allowance trophy count
	@Test(priority = 1)
	public void getTotalTropyCountFunction() throws InterruptedException {
		recogobject.givingAllowanceTabClick().click();
		String getExpiresText = recogobject.refreshesOnDate().getText();
		boolean refreshDateDisplayed = recogobject.refreshesOnDate().isDisplayed();
		System.out.println("The trophy count " + getExpiresText);
		Assert.assertTrue(refreshDateDisplayed);
		System.out.println("Refreshes on date is displayed");
		Thread.sleep(2000L);
		String getTrophyTotalCountText = recogobject.givingCarrotBalance().getText();
		int getTrophyTotalCount = Integer.parseInt(getTrophyTotalCountText);
	//	System.out.println("Giving allowance trophy count is " + getTrophyTotalCount);
		if (getTrophyTotalCount == 0) {
			Actions action = new Actions(driver);
			action.moveToElement(recogobject.getAddTrophiesDropdown()).build().perform();
			Thread.sleep(1000L);
			System.out.println("Is dropdown selected " + recogobject.disabledDropdownTextOnHover().isSelected());
			Assert.fail("Giving trophy count is Zero");
		} else if (getTrophyTotalCount < 0) {
			System.out.println("Trophy count went Negative!");
			Assert.fail("Trophy count has Negative value");
		} else if (getTrophyTotalCount > 0) {
			System.out.println("Remaining trophy count is " + getTrophyTotalCount);
			Assert.fail("Write your custom error message");
		}

	}

	// Get Earned balance trophy count
	@Test(priority = 2)
	public void getEarnedTropyCountFunction() throws InterruptedException {
		recogobject.earnedBalanceTabClick().click();
		boolean IsearnedTrophyVisible = recogobject.earnedCarrotBalance().isDisplayed();
		Assert.assertTrue(IsearnedTrophyVisible);
		System.out.println("Earned trophy balance is displayed");
		Thread.sleep(1000L);
		String getEarnedTrophyTotalCountText = recogobject.earnedCarrotBalance().getText();
		int getEarnedTrophyTotalCount = Integer.parseInt(getEarnedTrophyTotalCountText);
		//System.out.println("Earned trophy count is " + getEarnedTrophyTotalCount);
		if (getEarnedTrophyTotalCount == 0) {
			System.out.println("You haven't given any trophies");
		} else if (getEarnedTrophyTotalCount < 0) {
			System.out.println("Earned trophy count went Negative!");
			Assert.fail("Trophy count has Negative value");
		} else if (getEarnedTrophyTotalCount > 0) {
			System.out.println("Earned trophy count is " + getEarnedTrophyTotalCount);
		}

	}

}
