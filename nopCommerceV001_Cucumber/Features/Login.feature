Feature: Login
@sanity
Scenario: Sucessfull login with Valid credentials
Given user launch chrome browser
When user opens URL "https://admin-demo.nopcommerce.com/login"
And user enters email as "admin@yourstore.com" and password as "admin"
And Click on Login
Then Page title should be "Dashboard / nopCommerce administration"
When User click on Logout link
Then Page title should be "Your store. Login"
And close browser
@regression
Scenario Outline: Login Data Driven
Given user launch chrome browser
When user opens URL "https://admin-demo.nopcommerce.com/login"
And user enters email as "<email>" and password as "<password>"
And Click on Login
Then Page title should be "Dashboard / nopCommerce administration"
When User click on Logout link
Then Page title should be "Your store. Login"
And close browser

Examples:
		| email | password |
		| admin@yourstore.com | admin |
		| admin@yourstore.com | admin|
