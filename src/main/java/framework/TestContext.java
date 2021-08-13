package framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jpedroso
 * @description 
 * Initizalizes the WebDriverFactory and the PageObjectsManager classes 
 */
public class TestContext {
	
private static final Logger LOG = LoggerFactory.getLogger(TestContext.class);
	
	private WebDriverFactory webDriverManager;
	private PageObjectsManager pageObjectsManager;
	private ExtentTestManager extentTest;


	public TestContext() {
		LOG.info("Test Context Constructor");
		webDriverManager = new WebDriverFactory();
		pageObjectsManager = new PageObjectsManager(webDriverManager.getDriver());
		extentTest = new ExtentTestManager ();
	}

	
	public WebDriverFactory getWebDriverFactory() {
		LOG.info("Test context: getWebDriver()");
		return webDriverManager; 
	}

	public PageObjectsManager getPageObjectManager() {
		LOG.info("Test context: getPageObjectManager()");
		return pageObjectsManager;
	}
	
	public ExtentTestManager getExtentReporter () {
		LOG.info("Test context: getExtentReporter()");
		return extentTest;
	}

}
