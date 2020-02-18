@clerk
Feature: Clerk Feature
  Clerk should be able to populate working class hero data via API and UI

 Scenario: Single record data upload via API sucessfully
  	Given the single record insertion api endpoint is set correctly
  	When the clerk sends a request for single record insertion with the following data
  		| birthday 	| gender | name 			| natid 				| salary | tax |
  		| 20021990	| F 		 | Apple Pie 	| 908-5648321   | 5000 	 | 200 |
  	Then the clerk should receive a HTTP response 202
  
  Scenario: Single record data upload via API failed with 404
  	Given the single record insertion api endpoint is set wrongly
  	When the clerk sends a request for single record insertion with the following data
  		| birthday 	| gender | name 			| natid 				| salary | tax |
  		| 20021990	| F 		 | Apple Pie 	| 404-4044044   | 5000 	 | 200 |
  	Then the clerk should receive a HTTP response 404
  
  Scenario: Single record data upload via API with invalid data values
  	Given the single record insertion api endpoint is set correctly
  	When the clerk sends a request for single record insertion with the following data
  		| birthday 		| gender | name 			| natid 				| salary | tax |
  		| 20-02-1990	| F 		 | Apple Pie  | 908-5648321  	| 5000 	 | 200 |
  	Then the clerk should receive a HTTP response 500
  
  Scenario: Multiple record data upload via API sucessfully
  	Given the multiple record insertion api endpoint is set correctly
  	When the clerk sends a request for multiple record insertion with the following data 
  	 	| birthday 	| gender | name 			| natid 			| salary | tax 	|
  		| 09101970	| M 		 | Oreo1 			| 412-6648311 | 10000  | 1000 |
  		| 04041980	| F 		 | M@ngo 			| 672-5625361 | 2500 	 | 100 	|
  		| 11111966	| M 		 | Pear 			| 372-7283912 | 3570 	 | 241 	|
  	Then the clerk should receive a HTTP response 202
 
   Scenario: Multiple record data upload via API failed with 404
  	Given the multiple record insertion api endpoint is set wrongly
  	When the clerk sends a request for multiple record insertion with the following data 
  	 	| birthday 	| gender | name 			| natid 			| salary | tax 	|
  		| 01011998	| M 		 | Oreo 			| 412-6648311 | 10000  | 1000 |
  		| 04041980	| F 		 | Mango 			| 672-5625361 | 2500 	 | 100 	|
  		| 11111966	| M 		 | Pear 			| 372-7283912 | 3570 	 | 241 	|
  	Then the clerk should receive a HTTP response 404 
  	
  	Scenario: Multiple record data upload via API with invaid data values
  	Given the multiple record insertion api endpoint is set correctly
  	When the clerk sends a request for multiple record insertion with the following data 
  	 	| birthday 	| gender | name 			| natid 			| salary | tax 	|
  		| 01011998	| Male 	 | Oreo 			| 412-6648311 | abc		 | 1000 |
  		| 02-02-1990| F 		 | Mango 			| 672-5625361 | 2500 	 | abc 	|
  		| 11111966	| M 		 | Pear 			| 372-7283912 | 3570 	 | 241 	|
  	Then the clerk should receive a HTTP response 500 
  	
  	Scenario: Clerk is able to upload working class hero data via the portal
  	Given the Clerk visits the Citizen Disbursement Portal 
  	When the clerk clicks on the upload file button
  	Then the clerk should be able to upload the file 
  	And I proceed to close the portal
  	