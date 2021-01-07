package v2ScreenPageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class V2screenPageObjects {
	public WebDriver driver;
	
	// left drawer content
	By profileIcon = By.xpath("//button[@data-id='profile']");
	By assemblyIcon = By.xpath("//button[@data-id='assembly']");
	By notificationsIcon = By.xpath("//button[@data-id='notifications']");
	
	// Carrot information
	By trophyCountEarned = By.tagName("h6");
	By trophyEarnedInfo = By.xpath("//div[contains(text(), 'My Carrot Balance')]/../div[2]");
	By totalMonthlyAllowance = By.xpath("//div[@class='sc-bYwzba ikiypV']/div[2]");
	By totalMonthlyAllowanceInfo = By.xpath("//div[contains(text(), 'Total monthly allowance')]/../div[2]");
	By trophyLeftForMonth = By.xpath("//div[@class='sc-bYwzba kHyUUS']/div[2]");
	By trophyLeftInfo = By.xpath("//div[contains(text(), 'Remaining this month')]/../div[2]");
	By trophyRefreshesOn = By.xpath("//div[@class='sc-fujyUd dSxJLv']/div");
	
	By tooltipInfo = By.xpath("//div[@class='sc-hmbsMR ejsngC']");
	
	By mainFeedText = By.xpath("//div[contains(text(), 'Main Feed')]");
	By rewardsText = By.xpath("//div[contains(text(), 'Rewards')]");
	
	// Right drawer content
	By rightDrawerButton = By.xpath("//button[@role='navigation']");
	
	public V2screenPageObjects(WebDriver driver){
		this.driver = driver;
	}
	
	public WebElement profileIcon() {
		return this.driver.findElement(profileIcon);
	}
	
	public WebElement assemblyIcon() {
		return this.driver.findElement(assemblyIcon);
	}
	
	public WebElement notificationsIcon() {
		return this.driver.findElement(notificationsIcon);
	}
	
	public WebElement trophyCountEarned() {
		return this.driver.findElement(trophyCountEarned);
	}
	
	public WebElement trophyEarnedInfo() {
		return this.driver.findElement(trophyEarnedInfo);
	}
	
	public WebElement totalMonthlyAllowance() {
		return this.driver.findElement(totalMonthlyAllowance);
	}
	
	public WebElement totalMonthlyAllowanceInfo() {
		return this.driver.findElement(totalMonthlyAllowanceInfo);
	}
	
	public WebElement trophyLeftForMonth() {
		return this.driver.findElement(trophyLeftForMonth);
	}
	
	public WebElement trophyLeftInfo() {
		return this.driver.findElement(trophyLeftInfo);
	}
	
	public WebElement trophyRefreshesOn() {
		return this.driver.findElement(trophyRefreshesOn);
	}
	
	public WebElement tooltipInfo() {
		return this.driver.findElement(tooltipInfo);
	}
	
	public WebElement mainFeedText() {
		return this.driver.findElement(mainFeedText);
	}
	
	public WebElement rewardsText() {
		return this.driver.findElement(rewardsText);
	}
	
	public WebElement rightDrawerButton() {
		return this.driver.findElement(rightDrawerButton);
	}

}
