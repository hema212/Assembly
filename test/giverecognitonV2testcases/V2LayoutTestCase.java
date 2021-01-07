package giverecognitonV2testcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import resources.InitiateDriver;
import v2ScreenPageObjects.V2screenPageObjects;

public class V2LayoutTestCase extends InitiateDriver{
	public static Logger log = LogManager.getLogger(V2LayoutTestCase.class.getName());
	public V2screenPageObjects v2PageObjects;

	@BeforeTest
	public void init() throws FileNotFoundException, IOException{
		driver = initializeDriver();
		v2PageObjects = new V2screenPageObjects(driver);
	}
	
	@Test(priority = 1)
	public void launchURL() {
		driver.get("https://my.joinassembly.com/v2/home");
		driver.manage().window().maximize();
		log.info("landed onto v2 layout");
	}
}
