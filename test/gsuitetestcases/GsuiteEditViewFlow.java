package gsuitetestcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import gsuitePageObjects.GooglePageObjects;
import signInViaSSO.GoogleSsoSignIn;
import slacktestcases.SlackEditViewFlow;

// change to Extends GSuiteBase
public class GsuiteEditViewFlow extends GoogleSsoSignIn {
	public static Logger log = LogManager.getLogger(SlackEditViewFlow.class.getName());
	public GooglePageObjects googleobject;

	@BeforeTest
	public void init() throws FileNotFoundException, IOException {
		driver = initializeDriver();
		googleobject = new GooglePageObjects(driver);
	}

	// Verify user lands on Invites page

	@Test(priority = 1)
	public void EditInvitesValidationOnCancel() throws InterruptedException {
		selectManageSideNavbar();
		googleobject.InviteText().click();
		String breadcrum = googleobject.InvitesBreadcrum().getText();
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
		System.out.println("Is checkbox selected earlier? " + googleobject.SelectAllCheckBox().isSelected());
		Thread.sleep(1000L);
		bulkApprove();
		Thread.sleep(1000L);
		System.out.println("Testcase-2 passed since Bulk Approval is completed");
		System.out.println("After Bulk Approval data is :" + googleobject.NoDataAvailableText().getText());
		String breadcrum = googleobject.InvitesBreadcrum().getText();
		Assert.assertEquals(breadcrum, "> Invites");
	}

	// Validate Manage page against saved details

	@Test(priority = 3)
	public void ValidateManagePageUserDetails() throws InterruptedException {
		Thread.sleep(1000L);
		googleobject.ManageText().click();
		String breadcrum = googleobject.InvitesBreadcrum().getText();
		Assert.assertEquals(breadcrum, "> Manage");
		System.out.println("Validating user-1 details in Manage page");
		getDeptStatusTextUserOne();
		if (googleobject.UserOneDept().getText().equalsIgnoreCase("Human Resources")) {
			Assert.assertTrue(true);
			System.out.println("The department matches with the edited one");
		}
		if (googleobject.UserOneStatus().getText().equalsIgnoreCase("Receiver")) {
			Assert.assertTrue(true);
			System.out.println("The Status matches with the edited one");
		}
	}

	//Delete selected users from assembly
	@Test(priority = 4)
	public <List> void ValidateDeletingSelectedUser() throws InterruptedException {
		selectManageSideNavbar();
		int userSize = googleobject.AssemblyUserText().size();
		Thread.sleep(1000L);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		for (int i = 0; i < userSize; i++) {
			googleobject.AssemblyUserText().get(i).click();
			js.executeScript("window.scrollBy(0, 200)");

		}
		js.executeScript("window.scrollBy(0, -200)");
		googleobject.ManagePageDeleteIcon().click();
		googleobject.RemovButtonText().click();
		System.out.println("Success toast is: " + googleobject.successToast().getText());
	}


	
	// function to validate landing page of Manage
	public void selectManageSideNavbar() throws InterruptedException {
		googleobject.avatarIcon().click();
		googleobject.AdminText().click();
		googleobject.UsersText().click();
		googleobject.ManageText().click();
		Thread.sleep(1000L);
	}

	// function to validate edit bulk users by selecting dept, status and click on
	// Cancel button
	public void selectDepartmentStatusOnEditModalCancel() throws InterruptedException {
		Thread.sleep(1000L);
		googleobject.SelectAllCheckBox().click();
		googleobject.EditButton().click();
		editModalDepartmentAssertion();
		googleobject.DepartmentDropdown().click();
		googleobject.DesignerDept().click();
		googleobject.SelectStatus().click();
		googleobject.GiverStatus().click();
		googleobject.EditCancelButton().click();
		Thread.sleep(2000L);
		getDeptStatusTextUserOne();
	}

	// function to validate edit bulk users by selecting dept, status and click on
	// Cancel button
	public void selectDepartmentStatusOnEditModalConfirm() throws InterruptedException {
		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//th/span")));
		Thread.sleep(5000L);
		googleobject.SelectAllCheckBox().click();
		googleobject.EditButton().click();
		editModalDepartmentAssertion();
		googleobject.DepartmentDropdown().click();
		googleobject.DesignerDept().click();
		googleobject.SelectStatus().click();
		googleobject.ReceiverStatus().click();
		googleobject.ConfirmInvite().click();
		Thread.sleep(2000L);
		getDeptStatusTextUserTwo();
	}

	// Edit modal assertion validation
	public void editModalDepartmentAssertion() {
		String editModalDeptHeader = googleobject.DepartmentLabel().getText();
		Assert.assertEquals(editModalDeptHeader, "Department");
	}

	// Validate default dept and status in Invites flow
	public void getDeptStatusTextUserOne() throws InterruptedException {
		Thread.sleep(1000L);
		System.out.println("The number of rows are : " + googleobject.Row().size());
		System.out.println("The Department of first user is " + googleobject.UserOneDept().getText());
		System.out.println("The Status of first user is " + googleobject.UserOneStatus().getText());
	}

	// Validate default dept and status in Invites flow
	public void getDeptStatusTextUserTwo() throws InterruptedException {
		Thread.sleep(1000L);
		System.out.println("The number of rows are : " + googleobject.Row().size());
		System.out.println("The Department of Second user is " + googleobject.UserTwoDept().getText());
		System.out.println("The Status of Second user is " + googleobject.UserTwoStatus().getText());
	}

	// Validate Single edit flow
	public void singleEdit() throws InterruptedException {
		googleobject.SingleUserSelectToEdit().click();
		googleobject.EditButton().click();
		Thread.sleep(1000L);
		googleobject.DepartmentDropdownEditEmployee().click();
		googleobject.HumanResourceDept().click();
		Thread.sleep(1000L);
		googleobject.BirthMonthDropDown().click();
		googleobject.BirthMonthSelect().click();
		googleobject.ConfirmInvite().click();

		String monthErrorText = googleobject.HelperBirthMonthError().getText();
		System.out.println("The error text is " + monthErrorText);
		if (monthErrorText != null) {
			googleobject.BirthDayDropDown().click();
			googleobject.BirthDayDate().click();
			googleobject.ConfirmInvite().click();
			System.out.println("User details Updated!");
		}
	}

	public void bulkApprove() throws InterruptedException {
		Thread.sleep(2000L);
		googleobject.SelectAllCheckBox().click();
		googleobject.BulkApprove().click();
		String bulkApproveModalHeader = googleobject.BulkApproveModalHeaderText().getText();
		Assert.assertEquals(bulkApproveModalHeader, "Are you sure you want to approve all queued members?");
		googleobject.ImSureButton().click();
		Thread.sleep(5000L);
		String noDataInTable = googleobject.NoDataAvailableText().getText();
		Assert.assertEquals(noDataInTable, "No Invitees");
	}
}
