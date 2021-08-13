# Test-Framework

Test Framework built in Java with Selenium, Cucumber and JUnit. 


PRE-REQUISITES:

- Install JDK 8 or above; 
- Install the browser where you want to run the tests
- The framework automatically downloads the appropriate Driver for the browser



TO RUN THE TESTS

- Clone the project to your machine
- Go to the testing-framework/ folder and run the command "mvn clean install"
- The default browser is Google Chrome. If you want to run the tests in other browsers you can add to the command:
   
	-Dbrowser=firefox -> runs on Firefox
   	-Dbrowser=explorer -> runs of Internet Explorer


REPORTING

Folder "Reports" contains:

 - Screenshots of results;
 - Text file with the lowest and highest prices;


EXPLANATION:
src/Configs folder:
 - Contain the Configuration.properties files that definies the application URL, default browser, default wait time when driver is being loaded. 
   This avoids hardcoding those values in the project. 

src/main/java folder:
 - The project follows the Page Object Model (POM) design pattern in which each page of the website (defined by a different URL) is represented by a Java class
where the WebElements present on that page are defined using its xpath, id, classname or other attributes. Each class contains the methods that make certain actions
on those web elements and thus implement the steps of the scenario. 
       - The classes that represent each one of the pages are in the folder src/main/java/pageObjects
       - Framework core to allow a more modular construction as well as some auxiliary classes that allow functionalities such as taking screenshots or 
          creating the output .txt file are on the /src/main/java/framework folder

src/test/resources folder: 
 - Feature file with the test case written in Gherkins (Location: src/test/resources/features)
 - Also contains configuration files, for example log4j.xml is necessary to allow the logger library used to print messages in the console

src/test/java folder: 
 - StepDefinitions: class that maps each step of the scenario into code (Location: src/test/java/steps)
 - TestRunner: class that provides the Cucumber framework the point necessary to map each step into Java code (Location: src/test/java/steps)


