Feature: Customers

 Background: Below are common steps for every scenario
 	Given user launch chrome browser
	When user opens URL "https://admin-demo.nopcommerce.com/login"
	And user enters email as "admin@yourstore.com" and password as "admin"
	And Click on Login
	Then user can view dashboard
	When user click on Customers menu
	And click on customers menu item
@sanity
Scenario: Add a new customer
	And click on add new button
	Then user can view add new customer page
	When user enter customer info
	And click on save button
	Then the user can view confirmation mesage "The new customer has been added successfully."
	And close browser
@regression
Scenario: Search Customer by Name
	When Enter customer email
	When Click on search button
	Then User should found email in the Search table
	And close browser 
@regression
Scenario: Search Customer by Name
	And Enter customer FirstName
	And Enter customer LastName
	When Click on search button
	Then User should found Name in the Search table
	And close browser 
	