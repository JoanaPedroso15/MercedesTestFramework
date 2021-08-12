package steps;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import framework.Screenshot;
import framework.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class StepDefinitions {
	
	private static final Logger LOG = LoggerFactory.getLogger(StepDefinitions.class);

	private WebDriver driver;
	private TestContext testContext;
	ExtentReports extent;
	ExtentTest logger;
	Scenario scenario;
	
	
	public StepDefinitions(TestContext context) {
		LOG.info("StepDefinitions constructor");
		this.testContext = context;
		this.driver = testContext.getWebDriverFactory().getDriver();
		//extent = ExtentReportManager.getInstance();
		//logger = extent.createTest("Scenario01");
	}
	
	
	@Before()
	public void beforeScenario() throws IOException  {	

		if ((new File(Screenshot.screenshotdir)).exists())
			FileUtils.cleanDirectory(new File(Screenshot.screenshotdir));
	}
	
	
	@After
	public void endOfTest(Scenario scenario) {
		if (scenario.isFailed()) {
			Screenshot.getScreenshot(driver, "Failed scenario");
		}
		testContext.getExtentReporter().endTest();
		testContext.getWebDriverFactory().closeDriver();
	}


}
