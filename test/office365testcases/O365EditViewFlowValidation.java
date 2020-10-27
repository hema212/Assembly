package office365testcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import PageObjects.O365PageObjects;
import resources.O365SsoSignIn;
import slacktestcases.SlackQueuedInviteFlowValidation;

//Execute this Second(2nd) when users are in Queued Invites when slack is disconnected
public class O365EditViewFlowValidation extends O365SsoSignIn  {
	public static Logger log = LogManager.getLogger(SlackQueuedInviteFlowValidation.class.getName());
	public O365PageObjects office365Object;

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {
		driver = initializeDriver();
		office365Object = new O365PageObjects(driver);
	}

	@Test(priority = 1)
	public void EditInvitesValidationOnCancel() throws InterruptedException {
		selectManageSideNavbar();
		office365Object.InviteText().click();
		String breadcrum = office365Object.InvitesBreadcrum().getText();
		Assert.assertEquals(breadcrum, "> Invites");
		selectDepartmentStatusOnEditModalCancel();
		System.out.println("Testcase-1 passed since Edit is cancelled and user landed on Invites page");
	}
	
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
		System.out.println("After Bulk Approval data is " +  office365Object.NoDataAvailableText().getText());
		String breadcrum = office365Object.InvitesBreadcrum().getText();
		Assert.assertEquals(breadcrum, "> Invites");
	}
	
	public void bulkApprove() throws InterruptedException {
		Thread.sleep(1000L);
		office365Object.SelectAllCheckBox().click();
		office365Object.BulkApprove().click();
		String bulkApproveModalHeader = office365Object.BulkApproveModalHeaderText().getText();
		Assert.assertEquals(bulkApproveModalHeader, "Are you sure you want to approve all queued members?");
		office365Object.ImSureButton().click();
		Thread.sleep(5000L);
		String noDataInTable = office365Object.NoDataAvailableText().getText();
		Assert.assertEquals(noDataInTable, "No Invitees");
	}

	// function to validate landing page of Manage
	public void selectManageSideNavbar() throws InterruptedException {
		Thread.sleep(4000L);
		office365Object.avatarIcon().click();
		office365Object.AdminText().click();
		office365Object.UsersText().click();
		office365Object.ManageText().click();
		Thread.sleep(1000L);
	}

	// function to validate edit bulk users by selecting dept, status and click on
	// Cancel button
	public void selectDepartmentStatusOnEditModalCancel() throws InterruptedException {
		Thread.sleep(1000L);
		office365Object.SelectAllCheckBox().click();
		office365Object.EditButton().click();
		editModalDepartmentAssertion();
		office365Object.DepartmentDropdown().click();
		office365Object.DesignerDept().click();
		office365Object.SelectStatus().click();
		office365Object.GiverStatus().click();
		office365Object.EditCancelButton().click();
		Thread.sleep(2000L);
		getDeptStatusTextUserOne();
	}

	// function to validate edit bulk users by selecting dept, status and click on
	// Cancel button
	public void selectDepartmentStatusOnEditModalConfirm() throws InterruptedException {
		Thread.sleep(1000L);
		office365Object.SelectAllCheckBox().click();
		office365Object.EditButton().click();
		editModalDepartmentAssertion();
		office365Object.DepartmentDropdown().click();
		office365Object.DesignerDept().click();
		office365Object.SelectStatus().click();
		office365Object.GiverStatus().click();
		office365Object.ConfirmInvite().click();
		Thread.sleep(2000L);
		getDeptStatusTextUserTwo();
	}

	// Edit modal assertion validation
	public void editModalDepartmentAssertion() {
		String editModalDeptHeader = office365Object.DepartmentLabel().getText();
		Assert.assertEquals(editModalDeptHeader, "Department");
	}
	
	// Validate default dept and status in Invites flow
	public void getDeptStatusTextUserOne() {
		System.out.println("The number of rows are : " + office365Object.Row().size());
		System.out.println("The Department of first user is "+office365Object.UserOneDept().getText());
		System.out.println("The Staus of first user is "+office365Object.UserOneStatus().getText());
	}
	
	// Validate default dept and status in Invites flow
		public void getDeptStatusTextUserTwo() {
			System.out.println("The number of rows are : " + office365Object.Row().size());
			System.out.println("The Department of Second user is "+office365Object.UserTwoDept().getText());
			System.out.println("The Status of Second user is "+office365Object.UserTwoStatus().getText());
		}
		
	// Validate Single edit flow
	public void singleEdit() throws InterruptedException {
		office365Object.SingleUserSelectToEdit().click();
		office365Object.EditButton().click();
		Thread.sleep(1000L);
		office365Object.DepartmentDropdownEditEmployee().click();
		office365Object.HumanResourceDept().click();
		Thread.sleep(1000L);
		office365Object.BirthMonthDropDown().click();
		office365Object.BirthMonthSelect().click();
		office365Object.ConfirmInvite().click();
		String monthErrorText = office365Object.HelperBirthMonthError().getText();
		System.out.println("The error text is "+ monthErrorText);
		if(monthErrorText != null) {
			office365Object.BirthDayDropDown().click();
			office365Object.BirthDayDate().click();
			office365Object.ConfirmInvite().click();
		}
	}
	
	
}
