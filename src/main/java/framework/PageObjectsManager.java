package framework;

import org.openqa.selenium.WebDriver;

import pageobjects.ConfigurationPage;
import pageobjects.Homepage;

public class PageObjectsManager {
	
	private WebDriver driver;
	private Homepage homePage;
	private ConfigurationPage configPage; 
	
	public PageObjectsManager(WebDriver driver) {
		this.driver = driver;

	}
	
	
	 public Homepage getHomePage() {

		return (homePage == null) ? homePage = new Homepage(driver) : homePage;

	}
	 
	 public ConfigurationPage getConfigurationPage() {

		return (configPage == null) ? configPage = new ConfigurationPage(driver) : configPage;

	}


}
