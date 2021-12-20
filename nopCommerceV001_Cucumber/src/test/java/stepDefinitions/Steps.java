package stepDefinitions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.Before;
import cucumber.api.java.en.*;
import pageObjects.AddcustomerPage;
import pageObjects.LoginPage;
import pageObjects.SearchCustomerPage;

public class Steps extends BaseClass{
	@Before
	public void setup() throws IOException
	{
		//Logging
				logger=Logger.getLogger("nopCommerceSDET");
				PropertyConfigurator.configure("Log4j.properties");
				logger.setLevel(Level.DEBUG);
				
				//Load properties file
				configProp= new Properties();
				FileInputStream configPropfile = new FileInputStream("config.properties");
				configProp.load(configPropfile);
				
				String br=configProp.getProperty("browser"); //getting the browser name from config.properties file
				
				//Launching browser
				if (br.equals("firefox")) {
					System.setProperty("webdriver.gecko.driver",configProp.getProperty("firefoxpath"));
					driver = new FirefoxDriver();
				}

				else if (br.equals("chrome")) {
					System.setProperty("webdriver.chrome.driver",configProp.getProperty("chromepath"));
					driver = new ChromeDriver();
				}
	}
	
	@Given("user launch chrome browser")
	public void user_launch_chrome_browser() {
		logger.info("************* Launching Browser *****************");
		lp=new LoginPage(driver);
	    
	}

	@When("user opens URL {string}")
	public void user_opens_URL(String url) {
		logger.info("************* Opening URL  *****************");
		driver.get(url);
		driver.manage().window().maximize();
	}

	@When("user enters email as {string} and password as {string}")
	public void user_enters_email_as_and_password_as(String email, String password ) {
		logger.info("************* Prvding user and password *****************");
		lp.setUserName(email);
		lp.setPassword(password);
	}

	@When("Click on Login")
	public void click_on_Login() {
		logger.info("************* click on login *****************");
		lp.clickLogin();
	}

	@Then("Page title should be {string}")
	public void page_title_should_be(String title) throws InterruptedException {
		if(driver.getPageSource().contains("Login was unsuccessful"))
		{
			logger.info("************* Login failed *****************");
			driver.close();
			Assert.assertTrue(false);
		}
		else
		{
			logger.info("************* Login Passed *****************");
			Assert.assertEquals(title, driver.getTitle());
		}
		Thread.sleep(3000);
	}

	@When("User click on Logout link")
	public void user_click_on_Logout_link() throws InterruptedException {
		logger.info("************* clciking on logout *****************");
		lp.clickLogout();
	    Thread.sleep(3000);
	}

	@Then("close browser")
	public void close_browser() {
		logger.info("************* cloding browser *****************");
		driver.quit();
	}
	
	@Then("user can view dashboard")
	public void user_can_view_dashboard() {
		addCust=new AddcustomerPage(driver);
		logger.info("********* Verifying Dashboad page title after login successful **************");
		Assert.assertEquals("Dashboard / nopCommerce administration",addCust.getPageTitle());
			   
	}

	@When("user click on Customers menu")
	public void user_click_on_Customers_menu() throws InterruptedException {
		Thread.sleep(3000);
		logger.info("********* Clicking on customer main menu **************");
		addCust.clickOnCustomersMenu();
	}

	@When("click on customers menu item")
	public void click_on_customers_menu_item() throws InterruptedException {
		Thread.sleep(2000);
		logger.info("********* Clicking on customer sub menu **************");
		addCust.clickOnCustomersMenuItem();
	}

	@When("click on add new button")
	public void click_on_add_new_button() throws InterruptedException {
		addCust.clickOnAddnew();
		Thread.sleep(2000);
	}

	@Then("user can view add new customer page")
	public void user_can_view_add_new_customer_page() {
	    Assert.assertEquals("Add a new customer / nopCommerce administration", addCust.getPageTitle());
	}

	@When("user enter customer info")
	public void user_enter_customer_info() throws InterruptedException {
	   String email=randomestring()+"@gmail.com";
	   addCust.setEmail(email);
	   addCust.setPassword("test123");
		// Registered - default
		// The customer cannot be in both 'Guests' and 'Registered' customer roles
		// Add the customer to 'Guests' or 'Registered' customer role
	   addCust.setCustomerRoles("Guest");
	   Thread.sleep(3000);

		addCust.setManagerOfVendor("Vendor 2");
		addCust.setGender("Male");
		addCust.setFirstName("Pavan");
		addCust.setLastName("Kumar");
		addCust.setDob("7/05/1985"); // Format: D/MM/YYY
		addCust.setCompanyName("busyQA");
		addCust.setAdminContent("This is for testing.........");
	}

	@When("click on save button")
	public void click_on_save_button() throws InterruptedException {
		logger.info("********* Saving customer details **************");   
		addCust.clickOnSave();
		Thread.sleep(2000);
	}

	@Then("the user can view confirmation mesage {string}")
	public void the_user_can_view_confirmation_mesage(String msg) {
	    Assert.assertTrue(driver.findElement(By.tagName("body")).getText()
	    		.contains("The new customer has been added successfully"));
	}
	@When("Enter customer email")
	public void enter_customer_email() {
		searchCust=new SearchCustomerPage(driver);
		logger.info("********* Searching customer details by Email **************");
		searchCust.setEmail("victoria_victoria@nopCommerce.com");
	}

	@When("Click on search button")
	public void click_on_search_button() throws InterruptedException {
		searchCust.clickSearch();
		Thread.sleep(3000);
	}
	@Then("User should found email in the Search table")
	public void user_should_found_email_in_the_Search_table() throws InterruptedException {
		boolean status=searchCust.searchCustomerByEmail("victoria_victoria@nopCommerce.com");
		Thread.sleep(3000);
		Assert.assertEquals(true, status);
	}
	@When("Enter customer FirstName")
	public void enter_customer_FirstName() {
		logger.info("********* Searching customer details by Name **************");
		searchCust=new SearchCustomerPage(driver);
		searchCust.setFirstName("Victoria");
	}

	@When("Enter customer LastName")
	public void enter_customer_LastName() {
		searchCust.setLastName("Terces");
	}

	@Then("User should found Name in the Search table")
	public void user_should_found_Name_in_the_Search_table() throws InterruptedException {
		boolean status=searchCust.searchCustomerByName("Victoria Terces" );
		Assert.assertEquals(true, status);
	}



}
