package pages;

import org.openqa.selenium.WebDriver;
import base.Page;

public class NewEmployeePage  extends Page {
	
	public NewEmployeePage(WebDriver driver) {
		super(driver);
		this.PAGE_TITLE = "Vacations Management Site - Growth Acceleration Partners";
	}

	public void createNewEmployee(){	
		try{
		System.out.println(isElementPresent("EMPLOYEE_FIRSTNAME",5000));
		input("EMPLOYEE_FIRSTNAME", CONFIG.getProperty("employeeFirstname")); 
		input("EMPLOYEE_LASTNAME",CONFIG.getProperty("employeeLastname"));
		input("EMPLOYEE_EMAIL", CONFIG.getProperty("employeeEmail")); 
		input("EMPLOYEE_ID",CONFIG.getProperty("employeeId"));	
		input("EMPLOYEE_LEADERNAME",CONFIG.getProperty("employeeLeadername"));			
		select("STARTWORKING_YEAR", CONFIG.getProperty("startWorkingYear")); 
		select("STARTWORKING_MONTH",CONFIG.getProperty("startWorkingMonth"));	
		select("STARTWORKING_DAY",CONFIG.getProperty("startWorkingDay"));
		click("CREATE_BUTTON");
		}
		 catch (AssertionError e) {
			 throw new AssertionError("There was a problem setting select dropdown value!");
		 }
	}		
}
