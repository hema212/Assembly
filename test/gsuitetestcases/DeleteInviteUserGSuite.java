package gsuitetestcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import gsuitePageObjects.GooglePageObjects;
import signInViaEmailFlow.SlackSignInViaEmail;

public class DeleteInviteUserGSuite extends SlackSignInViaEmail {
	public static Logger log = LogManager.getLogger(DeleteInviteUserGSuite.class.getName());
	public GooglePageObjects gsuiteobject;

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {
		driver = initializeDriver();
		gsuiteobject = new GooglePageObjects(driver);
	}

	// Verify user lands on Invites page and does bulk delete operation
	@Test(priority = 1)
	public void DeleteUsersFromQueuedInvites() throws InterruptedException {
		selectManageSideNavbar();
		gsuiteobject.InviteText().click();
		String breadcrum = gsuiteobject.InvitesBreadcrum().getText();
		Assert.assertEquals(breadcrum, "> Invites");
		gsuiteobject.SelectAllCheckBox().click();
		gsuiteobject.EditButton().click();
		deleteModalAssertion();
		Thread.sleep(1000L);
		gsuiteobject.ImSureButton().click();
		System.out.println("The success toast message after deleting users from queued invites is:"
				+ gsuiteobject.successToast().getText());
	}

	// function to validate landing page of Manage
	public void selectManageSideNavbar() throws InterruptedException {
		gsuiteobject.avatarIcon().click();
		gsuiteobject.AdminText().click();
		gsuiteobject.UsersText().click();
		gsuiteobject.ManageText().click();
		Thread.sleep(1000L);
	}

	// Delete modal assertion validation
	public void deleteModalAssertion() {
		String deleteModalHeader = gsuiteobject.DeleteModalHeader().getText();
		Assert.assertEquals(deleteModalHeader, "Are you sure you want to delete all queued members?");
	}

}
