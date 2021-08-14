package pageobjects;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import framework.FileReaderManager;


/**
 * @author jpedroso
 * @description 
 *  Contains WebElements displayed on the Homepage
 *  Contains the methods that manipulate those elements and implement the scenario steps
 */
public class Homepage {

	private static final Logger LOG = LoggerFactory.getLogger(Homepage.class);
	private WebDriver driver;
	private WebDriverWait wait;

	/** Constructor of the page
	 * @param _driver
	 */
	public Homepage(WebDriver _driver) {
		this.driver = _driver;
		PageFactory.initElements(driver, this);
	}

	
	private static final String SECTION_OUR_MODELS = "//h1[contains(text(),'Our models')]";
	private static final String BTN_BUILD_CAR = "//*[contains(text(), 'Build your car')]";
	private static final String BTN_BUY_ONLINE = "//*[contains(text(), 'Buy online')]";
	private static final String BTN_LEARN_MORE = "//*[contains(text(), 'Learn more')]";
	private static final String IFRAME_LIST = "vmos-cont";
	private static final String IFRAME_HTML = "//*[@class='datauri hydrated']";
	private static final String IFRAME_IMAGE = "//*[@class='plugincomponent__base vmos vmos_3WdD8 vmos_1Pai0']";
	private static final String IFRAME_IMAGE_HTML =  "//*[@class='datauri hydrated']";
	private static final String OPTIONS = "//*[@class='datauri hydrated']/body/div/vmos/div/div/div/div/div/div[4]/div/section/div/div/child::*/child::*/child::*";

	
	@FindBy(how = How.XPATH, using = SECTION_OUR_MODELS)
	private WebElement sectionOurModels;

	@FindBy(how = How.XPATH, using = BTN_BUILD_CAR)
	private WebElement btnBuildCar;

	@FindBy(how = How.XPATH, using = BTN_BUY_ONLINE)
	private WebElement btnBuyOnline;

	@FindBy(how = How.XPATH, using = BTN_LEARN_MORE)
	private WebElement btnLearnMore;

	@FindBy(how = How.ID, using = IFRAME_LIST)
	private WebElement iframe;
	

	@FindBy(how = How.XPATH, using = IFRAME_HTML)
	private WebElement iframe_html;
	
	@FindBy(how = How.XPATH, using = IFRAME_IMAGE)
	private WebElement iframe_images;
	
	@FindBy(how = How.XPATH, using = IFRAME_IMAGE_HTML)
	private WebElement iframe_images_html;
	
	@FindBy(how = How.XPATH, using = OPTIONS)
	private List <WebElement> options;

	/**
	 * Navigates to the application URL saved on the Configuration.properties file
	 */
	public void navigateToHomePage() {
		LOG.info("Navigate to homepage");
		driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
		handleCookies();
	}

	/**
	 * Accepts all cookies
	 */
	private void handleCookies() {
		LOG.info("Handle cookies");
		WebElement acceptCookies = driver.findElement(By.xpath("//*[contains(text(), 'Agree to all')]"));
		if (acceptCookies != null) {
			acceptCookies.click();
		}
	}

	/** Clicks on a general model/category
	 * @param modelToSelect - model of the vehicle (Hatchbacks, Saloons, etc)
	 */
	public void selectsModel(String modelToSelect) {
		LOG.info("Selects a model " + modelToSelect);
		driver = wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframe));
		driver = new WebDriverWait(driver, 30).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframe_html));
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		sectionOurModels = driver.findElement(By.xpath(SECTION_OUR_MODELS));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", sectionOurModels);
		options = driver.findElements(By.xpath(OPTIONS));
		switch (modelToSelect) {
		case "Hatchbacks":
		WebElement hatchbacks =	wait.until(ExpectedConditions.elementToBeClickable(options.get(0)));
		hatchbacks.click();
			break;
		case "Saloons":
			break;
		}
		

	}

	/** Hovers over a specific car model of those displayed
	 * @param actionToTake - Build your car, Buy online or Learn More
	 * @param specificModel - specific model, for example A-Class, B-Class
	 */
	public void actionOnSpecificModel(String actionToTake, String specificModel) {
		LOG.info("Choose " + actionToTake + " on model: " + specificModel);
		Actions action = new Actions(driver);
		WebElement element = driver.findElement(By.xpath("//*[@href='https://www.mercedes-benz.co.uk/passengercars/mercedes-benz-cars/models/a-class/hatchback-w177/explore.html']/div/div[3]"));
		action.moveToElement(element).build().perform();
		chooseAction(actionToTake);
	}

	/** Clicks on the desired action
	 * @param action - Build your car, Buy online or Learn More
	 */
	private void chooseAction(String action) {
		switch (action) {
		case "Build your car":
			wait.until(ExpectedConditions.elementToBeClickable(btnBuildCar));
			btnBuildCar.click();
			break;
		case "Learn more":
			btnLearnMore.click();
			break;
		case "Buy online":
			btnBuyOnline.click();
			break;
		}
	}

}
