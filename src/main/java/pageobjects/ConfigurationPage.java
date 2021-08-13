package pageobjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import framework.FileCreator;
import framework.FileReaderManager;
import framework.Wait;

/**
 * @author jpedroso
 * @description 
 *  Contains WebElements displayed on the Configuration of cars page
 *  Contains the methods that manipulate those elements and implement the scenario steps
 */
public class ConfigurationPage {

	private static final Logger LOG = LoggerFactory.getLogger(ConfigurationPage.class);
	private WebDriver driver;
	private WebDriverWait wait;

	private static final String CONFIGURATION_FORM = "//*[@class='cc-motorization-heading cc-row']";
	private static final String FILTER_DIESEL = "//*[@aria-labelledby='Diesel']";
	private static final String FILTER_PETROL = "//*[@aria-labelledby='Petrol']";
	private static final String FILTER_HYBRID = "//*[@aria-labelledby='Hybrid']";
	private static final String FILTER_AUTOMATIC = "//*[@aria-labelledby='AUTOMATIC']";
	private static final String FILTER_MANUAL = "//*[@aria-labelledby='MANUAL']";
	private static final String PAGINATION = "cc-motorization-comparsion-status__pagination";
	private static final String RESULTS_MESSAGE = "cc-motorization-comparsion-status__info-text";
	private static final String LIST_RESULTS = "//cc-motorization-comparison/div/cc-slave-slider/div/div/div";

	@FindBy(how = How.XPATH, using = FILTER_DIESEL)
	private WebElement filterFuelDiesel;

	@FindBy(how = How.XPATH, using = FILTER_PETROL)
	private WebElement filterFuelPetrol;

	@FindBy(how = How.XPATH, using = FILTER_HYBRID)
	private WebElement filterFuelHybrid;

	@FindBy(how = How.XPATH, using = FILTER_AUTOMATIC)
	private WebElement filterTransmissionAutomatic;

	@FindBy(how = How.XPATH, using = FILTER_MANUAL)
	private WebElement filterTransmissionManual;

	@FindBy(how = How.CLASS_NAME, using = PAGINATION)
	private WebElement pagination;

	@FindBy(how = How.CLASS_NAME, using = RESULTS_MESSAGE)
	private WebElement resultsMessage;

	@FindBy(how = How.XPATH, using = LIST_RESULTS)
	private WebElement listOfResults;

	/** Constructor of Page 
	 * @param _driver
	 */
	public ConfigurationPage(WebDriver _driver) {
		this.driver = _driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 60);
	}

	/** Scrolls to position of page where filters can be selected. 
	 * @param filter - name of the search filter (fuel, transmission)
	 * @param value - value chosen for the search filter
	 */
	public void filtersBy(String filter, String value) {
		WebElement configurationForm = driver.findElement(By.xpath(CONFIGURATION_FORM));
		new WebDriverWait(driver, 200).until(ExpectedConditions.elementToBeClickable(configurationForm));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", configurationForm);
		switch (filter) {
		case "fuel":
			TestInputs.setFilter("Fuel", value);
			filterByFuel(value);
			break;
		case "transmission":
			TestInputs.setFilter("Transmission", value);
			break;
		}
	}

	
	/** Method that checks the box of the type of fuel desired 
	 * @param value - type of fuel (Diesel, Petrol, Hybrid)
	 */
	private void filterByFuel(String value) {
		LOG.info("Filter by fuel type: " + value);
		switch (value.toLowerCase()) {
		case "diesel":
			selectFilterValue(filterFuelDiesel, FILTER_DIESEL);
			break;
		case "petrol":
			filterFuelPetrol.click();
			break;
		case "hybrid":
			filterFuelHybrid.click();
			break;
		}
	}

	
	/** Solves the problem of the ElementClickInterceptedException. 
	 * @param filter - WebElement of the checkbox 
	 * @param filterExpression - xpath expression of the checkbox
	 */
	private void selectFilterValue(WebElement filter, String filterExpression) {
		try {
			filterFuelDiesel.click();
		} catch (ElementClickInterceptedException e) {
			WebElement next = driver.findElement(By.xpath(filterExpression + "/following-sibling::*"));
			next.click();
		}

	}

	/** Returns the number of results/vehicles that obey the filters used as search criteria
	 * @return nrResults - number of results retrieved with the selected search criteria
	 */
	public int obtainsNumberResults() {
		int nrResults = 0;
		String message = driver.findElement(By.className(RESULTS_MESSAGE)).getText();
		String pagination = driver.findElement(By.className(PAGINATION)).getText();
		LOG.info("Message " + message);
		LOG.info("Pagination " + pagination);
		String[] tempArray = pagination.split(" / ");
		nrResults = Integer.valueOf(tempArray[1]);
		LOG.info("Number of results " + nrResults);
		return nrResults;
	}

	/** Checks all the characteristics of all vehicles retrieved in the search
	 * @param numberResults - number of vehicles displayed on the search
	 * @return listOfVehicles - Map with the characteristics of each one of the vehicles
	 *         key = name of characteristic; value = characteristic for that particular search result
	 */
	public List<Map<String, String>> analyzesResults(int numberResults) {
		String headerExpression = "";
		String technicalDataExpression = "";
		String price = "";
		String name = "";
		String fuelType = "";
		String transmission = "";
		String power = "";
		String acceleration = "";
		String fuelConsumption = "";
		String efficiency = "";

		List<Map<String, String>> listOfVehicles = new ArrayList<>();
		for (int i = 1; i <= numberResults; i++) {
			Map<String, String> characteristicsOfVehicle = new HashMap<>();
			headerExpression = LIST_RESULTS + "/cc-slave-slide[" + i + "]/div/cc-motorization-header/div/div";
			technicalDataExpression = LIST_RESULTS + "cc-slave-slide[" + i + "]/div/cc-technical-data/ul";

			price = driver.findElement(By.xpath(headerExpression + "/div/div/div[1]")).getText();
			name = driver.findElement(By.xpath(headerExpression + "/div/div/div[2]")).getText();
	
			LOG.info("Technical data " + technicalDataExpression);
			
			fuelType = driver.findElement(By.xpath(technicalDataExpression + "/li[1]/div")).getText();
			power = driver.findElement(By.xpath(technicalDataExpression + "/li[2]/div")).getText();
			transmission = driver.findElement(By.xpath(technicalDataExpression + "/li[3]/div")).getText();
			acceleration = driver.findElement(By.xpath(technicalDataExpression + "/li[4]/div")).getText();
			fuelConsumption = driver.findElement(By.xpath(technicalDataExpression + "/li[5]/div")).getText();
			efficiency = driver.findElement(By.xpath(technicalDataExpression + "/li[6]/div")).getText();

			characteristicsOfVehicle.put("Name", name);
			characteristicsOfVehicle.put("Price", price);
			characteristicsOfVehicle.put("Fuel type", fuelType);
			characteristicsOfVehicle.put("Transmission", transmission);
			characteristicsOfVehicle.put("Acceleration", acceleration);
			characteristicsOfVehicle.put("Power", power);
			characteristicsOfVehicle.put("Fuel consumption", fuelConsumption);
			characteristicsOfVehicle.put("Efficiency", efficiency);

			listOfVehicles.add(characteristicsOfVehicle);
		}
		LOG.info("Results " + listOfVehicles);
		return listOfVehicles;
	}

	/** Takes a screenshot of each of the the vehicles returned by the search
	 * @param numberOfResults - number of vehicles returned on the search
	 */
	public void takesScreenshotResults(int numberOfResults) {
		LOG.info("Takes Screenshots of Results");
	}


	
	/** Makes sure that all the vehicles presented in the search obey the rules selected on the search.
	 * For example, if the filter applied was Fuel = Diesel, all vehicles should list diesel as its fuel
	 * @param listResults - map generated by analyzesResults()
	 */
	public void validatesFilters(List<Map<String, String>> listResults) {
		LOG.info("Validates that all displayed results obey filter rules");
		Map<String, List<String>> filters = TestInputs.getFilter();
		List<String> values = new ArrayList<>();
		List<String> searchResults = new ArrayList<>();
		for (String typeFilter : filters.keySet()) {
			values = filters.get(typeFilter);
			searchResults = savesParticularCharacteristic(listResults, typeFilter);
			for (String el : searchResults) {
				Assert.assertTrue(values.contains(el));
			}
		}
	}

	/** Outputs the highest price and the lowest price found in a text file
	 * @param listResults - map generated by analyzesResults()
	 */
	public void outputsPricesToTextFile(List<Map<String, String>> listResults) {
		LOG.info("Outputs the prices of the vehicles to a text file");
		List<String> prices = savesParticularCharacteristic(listResults, "Price");
		List<String> sortedPrices = prices.stream().sorted().collect(Collectors.toList());
		sortedPrices.forEach(System.out::println);

		String lowestPrice = "";
		String highestPrice = "";
		try {
			FileCreator.writeTextFile(lowestPrice, highestPrice);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	
	/** Returns a list of the values of each one of the vehicles for a particular characteristic
	 * @param listResults - map generated by analyzesResults()
	 * @param key - name of characteristic 
	 * @return listCharacteristic - list with the value that each vehicle has for the particular
	 *         characteristic. 
	 */
	private List<String> savesParticularCharacteristic(List<Map<String, String>> listResults, String key) {
		LOG.info("Saves " + key + " of each vehicle");
		String characteristic = "";
		List<String> listCharacteristic = new ArrayList<>();
		for (Map<String, String> map : listResults) {
			characteristic = map.get(key);
			listCharacteristic.add(characteristic);
		}

		LOG.info("Prices " + listCharacteristic);
		return listCharacteristic;
	}

}
