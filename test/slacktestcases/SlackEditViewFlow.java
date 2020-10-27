package slacktestcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import PageObjects.SlackIdentityObjects;
import resources.Base;

//Execute this Second(2nd) when users are in Queued Invites and slack is disconnected
public class SlackEditViewFlow extends Base {
	public static Logger log = LogManager.getLogger(SlackEditViewFlow.class.getName());
	public SlackIdentityObjects slackobject;

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {
		driver = initializeDriver();
		slackobject = new SlackIdentityObjects(driver);
	}

	//Verify user lands on Invites page 
	@Test(priority = 1)
	public void EditInvitesValidationOnCancel() throws InterruptedException {
		selectManageSideNavbar();
		slackobject.InviteText().click();
		String breadcrum = slackobject.InvitesBreadcrum().getText();
		Assert.assertEquals(breadcrum, "> Invites");
		selectDepartmentStatusOnEditModalCancel();
		System.out.println("Testcase-1 passed since Edit is cancelled and user landed on Invites page");
	}

	// Verify edited user details can be saved
	@Test(priority = 2)
	public void EditInvitesValidationOnConfirm() throws InterruptedException {
		selectDepartmentStatusOnEditModalConfirm();
		singleEdit();
		Thread.sleep(1000L);
		getDeptStatusTextUserOne();
		Thread.sleep(1000L);
		bulkApprove();
		Thread.sleep(1000L);
		System.out.println("Testcase-2 passed since Bulk Approval is completed");
		System.out.println("After Bulk Approval data is :" + slackobject.NoDataAvailableText().getText());
		String breadcrum = slackobject.InvitesBreadcrum().getText();
		Assert.assertEquals(breadcrum, "> Invites");
	}

	//Validate Manage page against saved details
	@Test(priority = 3)
	public void ValidateManagePageUserDetails() throws InterruptedException {
		Thread.sleep(1000L);
		slackobject.ManageText().click();
		String breadcrum = slackobject.InvitesBreadcrum().getText();
		Assert.assertEquals(breadcrum, "> Manage");
		System.out.println("Validating user-1 details in Manage page");
		getDeptStatusTextUserOne();
		if (slackobject.UserOneDept().getText().equalsIgnoreCase("Human Resources")) {
			Assert.assertTrue(true);
			System.out.println("The department matches with the edited one");
		}
		if (slackobject.UserOneStatus().getText().equalsIgnoreCase("Receiver")) {
			Assert.assertTrue(true);
			System.out.println("The Status matches with the edited one");
		}
	}

	// function to validate landing page of Manage
	public void selectManageSideNavbar() throws InterruptedException {
		slackobject.avatarIcon().click();
		slackobject.AdminText().click();
		slackobject.UsersText().click();
		slackobject.ManageText().click();
		Thread.sleep(1000L);
	}

	// function to validate edit bulk users by selecting dept, status and click on
	// Cancel button
	public void selectDepartmentStatusOnEditModalCancel() throws InterruptedException {
		Thread.sleep(1000L);
		slackobject.SelectAllCheckBox().click();
		slackobject.EditButton().click();
		editModalAssertion();
		slackobject.DepartmentDropdown().click();
		slackobject.DesignerDept().click();
		slackobject.SelectStatus().click();
		slackobject.GiverStatus().click();
		slackobject.EditCancelButton().click();
		Thread.sleep(2000L);
		getDeptStatusTextUserOne();
	}

	// function to validate edit bulk users by selecting dept, status and click on
	// Cancel button
	public void selectDepartmentStatusOnEditModalConfirm() throws InterruptedException {
		Thread.sleep(1000L);
		slackobject.SelectAllCheckBox().click();
		slackobject.EditButton().click();
		editModalAssertion();
		slackobject.DepartmentDropdown().click();
		slackobject.DesignerDept().click();
		slackobject.SelectStatus().click();
		slackobject.ReceiverStatus().click();
		slackobject.ConfirmInvite().click();
		Thread.sleep(2000L);
		getDeptStatusTextUserTwo();
	}

	// Edit modal assertion validation
	public void editModalAssertion() {
		String editModalHeader = slackobject.EditInviteModalHeader().getText();
		Assert.assertEquals(editModalHeader, "Edit 2 Pending Invite(s)");
	}

	// Validate default dept and status in Invites flow
	public void getDeptStatusTextUserOne() throws InterruptedException {
		Thread.sleep(1000L);
		System.out.println("The number of rows are : " + slackobject.Row().size());
		System.out.println("The Department of first user is " + slackobject.UserOneDept().getText());
		System.out.println("The Status of first user is " + slackobject.UserOneStatus().getText());
	}

	// Validate default dept and status in Invites flow
	public void getDeptStatusTextUserTwo() throws InterruptedException {
		Thread.sleep(1000L);
		System.out.println("The number of rows are : " + slackobject.Row().size());
		System.out.println("The Department of Second user is " + slackobject.UserTwoDept().getText());
		System.out.println("The Status of Second user is " + slackobject.UserTwoStatus().getText());
	}

	// Validate Single edit flow
	public void singleEdit() throws InterruptedException {
		slackobject.SingleUserSelectToEdit().click();
		slackobject.EditButton().click();
		Thread.sleep(1000L);
		slackobject.DepartmentDropdownEditEmployee().click();
		slackobject.HumanResourceDept().click();
		Thread.sleep(1000L);
		slackobject.BirthMonthDropDown().click();
		slackobject.BirthMonthSelect().click();
		slackobject.ConfirmInvite().click();

		String monthErrorText = slackobject.HelperBirthMonthError().getText();
		System.out.println("The error text is " + monthErrorText);
		if (monthErrorText != null) {
			slackobject.BirthDayDropDown().click();
			slackobject.BirthDayDate().click();
			slackobject.ConfirmInvite().click();
		}
		String nameErrorText = slackobject.HelperNameError().getText();
		System.out.println("The error text is " + nameErrorText);
		if (nameErrorText != null) {
			slackobject.UserNameFullName().sendKeys("SLACKUSER");
			slackobject.ConfirmInvite().click();
		}

	}

	public void bulkApprove() throws InterruptedException {
		Thread.sleep(2000L);
		slackobject.SelectAllCheckBox().click();
		slackobject.BulkApprove().click();
		String bulkApproveModalHeader = slackobject.BulkApproveModalHeaderText().getText();
		Assert.assertEquals(bulkApproveModalHeader, "Are you sure you want to approve all queued members?");
		slackobject.ImSureButton().click();
		Thread.sleep(5000L);
		String noDataInTable = slackobject.NoDataAvailableText().getText();
		Assert.assertEquals(noDataInTable, "No Invitees");
	}
}
