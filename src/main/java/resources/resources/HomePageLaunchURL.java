package resources;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.annotations.Test;

import PageObjects.HomePageObjects;

public class HomePageLaunchURL extends InitiateDriver {
	HomePageObjects homepageobject = new HomePageObjects(driver);

	@Test
	public void validateHomePageURLLaunch() throws FileNotFoundException, IOException, InterruptedException {
		validateHomeURLLaunch();
		// emailEnterdataValidation();
	}

	public void validateHomeURLLaunch() throws InterruptedException {
		driver.manage().window().maximize();
		driver.get("https://www.joinassembly.com");
		Thread.sleep(1000L);
	}

}
