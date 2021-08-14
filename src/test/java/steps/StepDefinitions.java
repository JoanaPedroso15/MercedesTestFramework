package steps;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageobjects.ConfigurationPage;
import pageobjects.Homepage;
import pageobjects.TestInputs;

public class StepDefinitions {

	private static final Logger LOG = LoggerFactory.getLogger(StepDefinitions.class);

	private WebDriver driver;
	private TestContext testContext;
	ExtentReports extent;
	ExtentTest logger;
	Scenario scenario;

	private Homepage homepage;
	private ConfigurationPage configPage;

	public StepDefinitions(TestContext context) {
		LOG.info("StepDefinitions constructor");
		this.testContext = context;
		this.driver = testContext.getWebDriverFactory().getDriver();

	}

	@Given("user opens Mercedes-Benz United Kingdom marketplace")
	public void user_opens_mercedes_benz_united_kingdom_marketplace() {
		this.homepage = testContext.getPageObjectManager().getHomePage();
		this.homepage.navigateToHomePage();
	}

	@When("user scrolls to Our Models section and selects model {string}")
	public void user_scrolls_to_our_models_section_and_selects_model(String modelToSelect) {
		this.homepage.selectsModel(modelToSelect);
		TestInputs.setModel(modelToSelect);

	}

	@When("selects {string} of the {string} model available")
	public void selects_of_the_model_available(String actionToTake, String model) {
		this.homepage.actionOnSpecificModel(actionToTake, model);
		TestInputs.setSubModel(model);
	}

	@When("filters by {string}: {string}")
	public void filters_by(String filter, String value) {
		this.configPage = testContext.getPageObjectManager().getConfigurationPage();
		this.configPage.filtersBy(filter, value);
	}

	@Then("takes a screenshot of all the results available")
	public void takes_a_screenshot_of_all_the_results_available() {
		int numberResults = this.configPage.obtainsNumberResults();
		List<Map<String, String>> listResults = this.configPage.analyzesResults(numberResults);
		TestInputs.setListResults(listResults);
		this.configPage.validatesFilters(listResults);
	}

	
	@Then("outputs the lowest and highest price results")
	public void outputs_the_lowest_and_highest_price_results() {
		List<Map<String, String>> listResults = TestInputs.getListResults();
		this.configPage.outputsPricesToTextFile(listResults);
	}

	@Before()
	public void beforeScenario() throws IOException {

		if ((new File(Screenshot.screenshotdir)).exists())
			FileUtils.cleanDirectory(new File(Screenshot.screenshotdir));
		

	}

	@After
	public void endOfTest(Scenario scenario) {
		if (scenario.isFailed()) {
			Screenshot.getScreenshot(driver, "Failed scenario");
		}
		testContext.getWebDriverFactory().closeDriver();
	}

}
