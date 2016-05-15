package pages;

import base.Page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class FindEmployeePage extends Page {

	public FindEmployeePage(WebDriver driver) {
		super(driver);
		this.PAGE_TITLE = "Vacations Management Site - Growth Acceleration Partners";
	}
	
	public String verifyEmployeeInfo(){
		Assert.assertTrue(isElementPresent("IDENTIFICATION",20), "Find Employee page could not be loaded.");
		input("IDENTIFICATION", CONFIG.getProperty("employeeId")); 
		// Temporary workaround because autocomplete text interferes on button click
		WebElement inputText = driver.findElement(By.cssSelector(LOCATORS.getProperty("IDENTIFICATION")));
		inputText.sendKeys(Keys.ENTER);				
		//click("FIND_EMPLOYEE_BUTTON");		
		return getMessageText("RESULTS_FULLNAME", 20).trim();
	}
}
