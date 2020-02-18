@governor
Feature: Governor feature
  Governor should be able to dispense money to each working class hero

   	Scenario: Governor is able to dispense cash to working class hero via the portal
  	Given the Governor visits the Citizen Disbursement Portal
  	When the Governor clicks on the dispense now button
  	Then he should see the cash dispensed message
  	And I proceed to close the portal