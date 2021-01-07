package office365testcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import officePageObjects.O365PageObjects;
import signInViaEmailFlow.SlackSignInViaEmail;

public class DeleteInviteUserOffice365 extends SlackSignInViaEmail {
	public static Logger log = LogManager.getLogger(DeleteInviteUserOffice365.class.getName());
	public O365PageObjects officeobject;

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {
		driver = initializeDriver();
		officeobject = new O365PageObjects(driver);
	}

	// Verify user lands on Invites page and does bulk delete operation
	@Test(priority = 1)
	public void DeleteUsersFromQueuedInvites() throws InterruptedException {
		selectManageSideNavbar();
		officeobject.InviteText().click();
		String breadcrum = officeobject.InvitesBreadcrum().getText();
		Assert.assertEquals(breadcrum, "> Invites");
		officeobject.SelectAllCheckBox().click();
		officeobject.EditButton().click();
		deleteModalAssertion();
		Thread.sleep(1000L);
		officeobject.ImSureButton().click();
		System.out.println("The success toast message after deleting users from queued invites is:"
				+ officeobject.successToast().getText());
	}

	// function to validate landing page of Manage
	public void selectManageSideNavbar() throws InterruptedException {
		officeobject.avatarIcon().click();
		officeobject.AdminText().click();
		officeobject.UsersText().click();
		officeobject.ManageText().click();
		Thread.sleep(1000L);
	}

	// Delete modal assertion validation
	public void deleteModalAssertion() {
		String deleteModalHeader = officeobject.DeleteModalHeader().getText();
		Assert.assertEquals(deleteModalHeader, "Are you sure you want to delete all queued members?");
	}

}
