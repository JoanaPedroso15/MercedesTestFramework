#Author: joana.pedroso15@gmail.com


Feature: Configure vehicles on UK Marketplace 

  @happy-path @scenario-01
  Scenario: Validate Class-A models price 
    Given user opens Mercedes-Benz United Kingdom marketplace
    When user scrolls to Our Models section and selects model "Hatchbacks"
    And selects "Build your car" of the "A-Class" model available 
    And filters by "fuel": "Diesel"
    Then takes a screenshot of all the results available
    And outputs the lowest and highest price results 

 