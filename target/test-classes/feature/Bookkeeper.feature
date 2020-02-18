@bookkeeper
Feature: Bookeeper feature
  Bookkeeper should be able to retrieve tax relief data for each working class hero

  	Scenario: Bookkeeper queries for tax relief data via API and UI
  	Given the database has the following tax relief data
  	 	| birthday 	| gender | name 			| natid 			| salary | tax 	|
  		| 01012020	| M		 	 | Andy 			| 100-6648311 | 0			 | 0	  |
  		| 02022002	| F		 	 | Brenda 		| 200-6648311 | 1500	 | 50	  |
  		| 03032009	| M		 	 | Chris 			| 300-6648311 | 500		 | 0	  |
  		| 04012001	| F		 	 | Denise			| 400-6648311 | 1750	 | 66	  |
  		| 12121984	| M		 	 | Ethan 			| 500-6648311 | 5555	 | 356  |
  		| 24021994	| F		 	 | Felcia 		| 600-6648311 | 4055	 | 234  |
  		| 09041983	| M		 	 | George 		| 700-6648311 | 6230	 | 880  |
  		| 29051969	| F		 	 | Helen			| 800-6648311 | 2350	 | 150  |
  		| 25011977	| M		 	 | Iilan			| 900-6648311 | 12500	 | 2830 |
  		| 01011969	| F		 	 | Jenn 			| 101-6648311 | 4500	 | 280  |
  		| 17011945	| M		 	 | Ken 				| 202-6648311 | 3010	 | 190	|
  		| 01021957	| F		 	 | Lisa				| 303-6648311 | 4930	 | 588	|
  		| 05011944	| M		 	 | Marc				| 404-6648311 | 1840	 | 55	  |
  		| 01021920	| F		 	 | Nina	 			| 505-6648311 | 0			 | 0	  |
  		| 23121931	| M		 	 | Perry 			| 606-6648311 | 1000	 | 30	  |
  	When the Bookkeeper queries for tax relief data via API
  	Then he should expect the following computed tax relief results
  		| natid 			| name 		| relief 	|
  		| 100-$$$$$$$	| Andy 		| 0			 	|
  		| 200-$$$$$$$	| Brenda  | 1950.00 |
  		| 300-$$$$$$$	| Chris 	|	500.00	|
  		| 400-$$$$$$$ | Denise	| 1847.00 |
  		| 500-$$$$$$$ | Ethan 	| 4159.00 |
  		| 600-$$$$$$$ | Felcia 	|	3556.81 |
  		| 700-$$$$$$$ | George 	| 2675.00 |
  		| 800-$$$$$$$ | Helen		| 1600.00 |
  		| 900-$$$$$$$ | Iilan		| 4835.00 |
  		| 101-$$$$$$$	| Jenn 		| 2048.74 |
  		| 202-$$$$$$$ | Ken 		|	1034.94 |
  		| 303-$$$$$$$ | Lisa		| 2093.52 |
  		| 404-$$$$$$$ | Marc		|	89.00		| 
  		| 505-$$$$$$$ | Nina	 	|	0 			|
  		| 606-$$$$$$$	| Perry 	|	50.00 	|
  	When the Bookkeeper visits the Citizen Disbursement Portal
  	Then he should see the same following computed tax reflief results in the UI
  	  | natid 			| name 		| relief 	|
  		| 100-$$$$$$$	| Andy 		| 0			 	|
  		| 200-$$$$$$$	| Brenda  | 1950.00 |
  		| 300-$$$$$$$	| Chris 	|	500.00	|
  		| 400-$$$$$$$ | Denise	| 1847.00 |
  		| 500-$$$$$$$ | Ethan 	| 4159.00 |
  		| 600-$$$$$$$ | Felcia 	|	3556.81 |
  		| 700-$$$$$$$ | George 	| 2675.00 |
  		| 800-$$$$$$$ | Helen		| 1600.00 |
  		| 900-$$$$$$$ | Iilan		| 4835.00 |
  		| 101-$$$$$$$	| Jenn 		| 2048.74 |
  		| 202-$$$$$$$ | Ken 		|	1034.94 |
  		| 303-$$$$$$$ | Lisa		| 2093.52 |
  		| 404-$$$$$$$ | Marc		|	89.00		| 
  		| 505-$$$$$$$ | Nina	 	|	0 			|
  		| 606-$$$$$$$	| Perry 	|	50.00 	|
  	And I proceed to close the portal	