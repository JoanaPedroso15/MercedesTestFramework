package framework;


import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * @author jpedroso
 * @description - 
 * WebDriverFactory follows a singleton pattern: there's only one instance of this class. 
 * Its jobs is to manage (get, create and close) the instance of the webdriver to be used on the test
 * according to the browser chosen
 */
public class WebDriverFactory {
	
	private WebDriver driver;
	private String driverType = "";

	private static final Logger LOG = LoggerFactory.getLogger(WebDriverFactory.class);

	public WebDriverFactory() {
		LOG.info("WebDriverFactory constrcutor");

		if (System.getProperty("browser") == null) {
			driverType = FileReaderManager.getInstance().getConfigReader().getBrowser();
		} else {
			driverType = System.getProperty("browser");
		}
		LOG.info("Driver type " + driverType);
	}

	/**
	 * @return WebDriver instance if there's already one or creates a new one if there's none
	 */
	public WebDriver getDriver() {
		LOG.info("WebDriverFactory: getDriver()");
		if (driver == null)
			driver = createDriver();
		return driver;
	}

	/**
	 * @return Initializes a webdriver according to the browser selected by user.
	 * Uses the WebDriverManager (dependency with groupID = io.github.bonigarcia) to download and install the
	 * correct webdriver if the machine doesn't have it installed yet.
	 */
	private WebDriver createDriver() {
		LOG.info("WebDriverFactory: createDriver()");
		switch (driverType) {
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
		
			driver = new FirefoxDriver();
			break;
		case "chrome":
			WebDriverManager.chromedriver().setup();
			
			driver = new ChromeDriver();
			
			break;
		case "explorer":
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
			break;
		default:
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;

		}

		if (FileReaderManager.getInstance().getConfigReader().getBrowserWindowSize())
			driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(FileReaderManager.getInstance().getConfigReader().getImplicitlyWait(),
				TimeUnit.SECONDS);
		
		driver.manage().deleteAllCookies();
		return driver;
	}

	/**
	 * Closes the WebDriver 
	 */
	public void closeDriver() {
		driver.close();
		driver.quit();
	}

}
