package slacktestcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import signInViaEmailFlow.SlackSignInViaEmail;
import slackPageObjects.SlackIdentityObjects;

//Execute this when users are in Queued state
public class DeleteInviteUsersSlack extends SlackSignInViaEmail {
	public static Logger log = LogManager.getLogger(DeleteInviteUsersSlack.class.getName());
	public SlackIdentityObjects slackobject;

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {
		driver = initializeDriver();
		slackobject = new SlackIdentityObjects(driver);
	}

	// Verify user lands on Invites page and does bulk delete operation
	@Test(priority = 1)
	public void DeleteUsersFromQueuedInvites() throws InterruptedException {
		selectManageSideNavbar();
		slackobject.InviteText().click();
		String breadcrum = slackobject.InvitesBreadcrum().getText();
		Assert.assertEquals(breadcrum, "> Invites");
		slackobject.SelectAllCheckBox().click();
		slackobject.DeleteButton().click();
		deleteModalAssertion();
		Thread.sleep(1000L);
		slackobject.ImSureButton().click();
		System.out.println("The success toast message after deleting users from queued invites is: "
				+ slackobject.successToast().getText());
		driver.close();
	}

	// function to validate landing page of Manage
	public void selectManageSideNavbar() throws InterruptedException {
		slackobject.avatarIcon().click();
		slackobject.AdminText().click();
		Thread.sleep(1000L);
		slackobject.UsersText().click();
		slackobject.ManageText().click();
		Thread.sleep(1000L);
	}

	// Delete modal assertion validation
	public void deleteModalAssertion() {
		String deleteModalHeader = slackobject.DeleteModalHeader().getText();
		Assert.assertEquals(deleteModalHeader, "Are you sure you want to delete all queued members?");
	}

}
