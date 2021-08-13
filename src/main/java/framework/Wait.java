package framework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Wait {
	
private static final Logger LOG = LoggerFactory.getLogger(Wait.class);

	

	public static boolean retryAndClickStaleElements(By by, WebDriver driver, int NrAttemps) {
		boolean result = false;
		int attempts = 0;
		while (attempts < NrAttemps) {
			try {
				driver.findElement(by).click();
				result = true;
				break;
			} catch (org.openqa.selenium.StaleElementReferenceException e) {
			}
			attempts++;
		}
		return result;
	}
	
	
	
	
	
}
