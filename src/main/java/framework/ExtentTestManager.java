package framework;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {
	

    static ExtentReports extent = ExtentReportManager.getInstance();
    static ExtentTest test; 

    public static ExtentTest getTest() {
       return test;
    }
    
    public void endTest() {
        extent.flush();
    }

    public  ExtentTest startTest(String testName) {
        ExtentTest _test = extent.createTest(testName);
        this.test = _test;
        return _test;
    }

}
