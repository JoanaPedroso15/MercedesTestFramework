package framework;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {
	
	private static ExtentReports extent;
	private static String fileSeparator = System.getProperty("file.separator");
	
	public static ExtentReports getInstance() {
		if (extent == null)
		extent = createInstance(); 
		// extent = new ExtentReports();
		return extent;
	}
	
    public static ExtentReports createInstance() {

        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(new File(System.getProperty("user.dir")+ fileSeparator + "Reports" + fileSeparator + "Cucumber-Reports" + fileSeparator + "ExtentSpark.html"));
     
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        return extent;
    }

}
