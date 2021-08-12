# Test-Framework

Test Framework built in Java with Selenium, Cucumber and JUnit. 


PRE-REQUISITES:

- Install JDK 8 or above; 
- Install the browser where you want to run the tests
- The framework automatically downloads the appropriate Driver for the browser



TO RUN THE TESTS

- Clone the project to your machine
- Go to the testing-framework/ folder and run the command "mvn clean test"
- The default browser is Google Chrome. If you want to run the tests in other browsers you can add to the command:
   
	  -Dbrowser=firefox -> runs on Firefox
   	-Dbrowser=explorer -> runs of Internet Explorer


REPORTING

Reports are generated to the folder Reports/Cucumber-Reports

 - Click on the ExtentSpark.html file inside the folder of the report to get an html report of the test.
