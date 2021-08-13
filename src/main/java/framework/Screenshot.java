package framework;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author jpedroso
 * @description - Auxiliary class with methods that take screenshots of the entire webpage
 * or of just one webelement and places the images in the desired destination folder
 */
public class Screenshot {

	
	private static final Logger LOG = LoggerFactory.getLogger(Screenshot.class);
	private static String fileSeparator = System.getProperty("file.separator");
	public static String screenshotdir = System.getProperty("user.dir") + fileSeparator + "Reports" + fileSeparator
			+ "Screenshots" + fileSeparator;

	public static void getScreenshotElement (WebElement element, String imageName) {

	
	}
	
	public static String getScreenshot(WebDriver driver, String screenshotName) {
		String dateName = new SimpleDateFormat("ddMMyyy_hhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		//LOG.info("Screenshot directory " + screenshotdir);
		String destination = screenshotdir + screenshotName + dateName + ".png";
		File finalDestination = new File(destination);
		try {
			FileUtils.copyFile(source, finalDestination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destination;
	}

	public static String getScreenshotBase64(WebDriver driver) {
		String Base64StringOfScreenshot = "";
		TakesScreenshot screenshotableDriver = (TakesScreenshot) driver;
		byte[] fileContent = screenshotableDriver.getScreenshotAs(OutputType.BYTES);

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYY_HHmmss");
		String sDate = sdf.format(date);
		Base64StringOfScreenshot = "data:image/png;base64," + Base64.getEncoder().encodeToString(fileContent);
		return Base64StringOfScreenshot;
	}
}
