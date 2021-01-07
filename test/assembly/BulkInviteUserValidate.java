package assembly;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import giveRecognitionPageObjects.InvitesPageObject;
import giveRecognitionPageObjects.RecognitionPageObject;
import resources.Base;

public class BulkInviteUserValidate extends Base {
	public static Logger log = LogManager.getLogger(LoginPageValidate.class.getName());

	@Test(priority = 1)
	public void InviteMemberUsingBulkIcon() throws InterruptedException {
		RecognitionPageObject recogobject = new RecognitionPageObject(driver);
		String getNoWorkerText = recogobject.getSendInviteButtonText().getText();
		System.out.println(getNoWorkerText);
		Assert.assertEquals(getNoWorkerText, "SEND INVITES");
		// Logging info
		log.info("No users available hence clicking on SEND INVITES button");
		recogobject.getSendInviteButtonText().click();
		InvitesPageObject inviteworkerobject = new InvitesPageObject(driver);
		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='Bulk Invite']")));
		inviteworkerobject.getBulkIconXpath().click();
		// Thread.sleep(1000L);
		String getInviteTeamMemberText = inviteworkerobject.getInviteTeamText().getText();
		System.out.println(getInviteTeamMemberText);
		Assert.assertEquals(getInviteTeamMemberText, "Invite team members");
		log.info("Landed onto invite member page");
		String[] invitee = { "hema+testing1@joinassembly.com", "hema+testing2@joinassembly.com" };
		inviteworkerobject.getInviteeEmailField().sendKeys("testing.com");
		Thread.sleep(1000L);
		inviteworkerobject.getSendButton().click();
		String saveErrorInvalidEmail = inviteworkerobject.getInvalidEmailErrorMessage().getText();
		if (saveErrorInvalidEmail.equalsIgnoreCase(saveErrorInvalidEmail)) {
			log.info("User has entered invalid email id! Clicking on Cancel");
			inviteworkerobject.getCancelButton().click();
		} else {
			log.info("Application supports Invalid email format");
		}
		inviteworkerobject.getBulkIconXpath().click();
		System.out.println(invitee[0] + ',' + invitee[1]);
		inviteworkerobject.getInviteeEmailField().sendKeys(invitee[0] + ',' + invitee[1]);
		inviteworkerobject.getSendButton().click();
	}
}
