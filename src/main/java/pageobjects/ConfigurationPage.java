package pageobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.core.pattern.FullLocationPatternConverter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationPage {
	
	
	private static final Logger LOG = LoggerFactory.getLogger(ConfigurationPage.class);
	private WebDriver driver;
	private WebDriverWait wait;
	
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
	
	


	public ConfigurationPage(WebDriver _driver) {
		this.driver = _driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 50);
	}
	
	public void filtersBy (String filter, String value) {
		switch (filter) {
		case "fuel":
			filterByFuel(value);
			break; 
		case "transmission":
			break; 
		}
	}
	
	private void filterByFuel (String value) {
		LOG.info("Filter by fuel type: " + value);
		switch (value.toLowerCase()) {
		case "diesel":
			filterFuelDiesel.click(); 
			break; 
		case "petrol":
			filterFuelPetrol.click();
			break; 
		case "hybrid":
			filterFuelHybrid.click();
			break;
		}
	}
	
	
	
	public int obtainsNumberResults () {
		int nrResults =0;
		String message = driver.findElement(By.className(RESULTS_MESSAGE)).getText();
		String pagination = driver.findElement(By.className(PAGINATION)).getText();
		LOG.info("Message " + message);
		LOG.info("Pagination " + pagination);
		return nrResults; 
	}
	
	public void analyzesResults (int numberResults) {
		String headerExpression = "";
		String technicalDataExpression = "";
		String price = "";
		String name = "";
		String fuelType = "";
		String transmission = "";
		String power = "";
		String acceleration = "";
		String fuelConsumption = "";
		String efficiency ="";

		List <Map <String, String>> listOfVehicles = new ArrayList <>  ();
		for (int i = 1; i <= numberResults; i++) {
			Map <String, String> characteristicsOfVehicle = new HashMap <>(); 
			headerExpression = LIST_RESULTS + "/cc-slave-slide[" + i + "]/div/cc-motorization-header/div/div";
			technicalDataExpression = LIST_RESULTS + "cc-slave-slide[" + i + "]/div/cc-technical-data/ul";
			
			price = driver.findElement(By.xpath(headerExpression + "/div/div/div[1]")).getText();
			name  = driver.findElement(By.xpath(headerExpression +  "/div/div/div[2]")).getText(); 
			
			fuelType = driver.findElement(By.xpath(technicalDataExpression +  "/li[1]/div")).getText(); 
			power = driver.findElement(By.xpath(technicalDataExpression +  "/li[2]/div")).getText(); 
			transmission = driver.findElement(By.xpath(technicalDataExpression +  "/li[3]/div")).getText(); 
			acceleration = driver.findElement(By.xpath(technicalDataExpression +  "/li[4]/div")).getText(); 
			fuelConsumption = driver.findElement(By.xpath(technicalDataExpression +  "/li[5]/div")).getText(); 
			efficiency = driver.findElement(By.xpath(technicalDataExpression +  "/li[6]/div")).getText(); 
			
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
	}
	
	public void takesScreenshotResults (int numberOfResults) {
		
	}
	
	
	private String savesPrices (int numberOfResults) {
		LOG.info("Saves the prices of each vehicle");
		String price = "";
		String expression = "";
		for (int i = 1; i <= numberOfResults; i++) {
			expression = LIST_RESULTS + "/cc-slave-slide[" + i + "]/";
		}
		
		return price; 
	}

	public void outputsPricesToTextFile () {
		LOG.info("Outputs the prices of the vehicles to a text file");
		
	}
	
}
