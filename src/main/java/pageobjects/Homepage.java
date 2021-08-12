package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Homepage {
	
	private static final Logger LOG = LoggerFactory.getLogger(Homepage.class);
	private WebDriver driver;
	private WebDriverWait wait;

	public Homepage(WebDriver _driver) {
		this.driver = _driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 50);
	}

}
