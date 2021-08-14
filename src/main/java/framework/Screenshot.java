package framework;

import java.awt.image.BufferedImage;
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

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;


/**
 * @author jpedroso
 * @description - Auxiliary class with methods that take screenshots of the
 *              entire webpage or of just one webelement and places the images
 *              in the desired destination folder
 */
public class Screenshot {

	private static final Logger LOG = LoggerFactory.getLogger(Screenshot.class);
	private static String fileSeparator = System.getProperty("file.separator");
	public static String screenshotdir = System.getProperty("user.dir") + fileSeparator + "Reports" + fileSeparator
			+ "Screenshots" + fileSeparator;




	
	public static void getScreenshotElementAShot(WebDriver driver, WebElement element, String imageName)
			throws IOException {
		BufferedImage image = new AShot().takeScreenshot(driver, element).getImage();
		
		ImageIO.write(image, "png", new File(screenshotdir + imageName + ".png"));

	}

	
	/**
	 * Gets screenshot of full page
	 */
	public static String getScreenshot(WebDriver driver, String screenshotName) {
		String dateName = new SimpleDateFormat("ddMMyyy_hhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = screenshotdir + screenshotName + dateName + ".png";
		File finalDestination = new File(destination);
		try {
			FileUtils.copyFile(source, finalDestination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destination;
	}

	
	
	
}
