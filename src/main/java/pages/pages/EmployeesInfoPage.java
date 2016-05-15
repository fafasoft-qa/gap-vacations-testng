package pages;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import base.Page;

public class EmployeesInfoPage  extends Page {
	
	public EmployeesInfoPage(WebDriver driver) {
		super(driver);
		this.PAGE_TITLE = "Vacations Management Site - Growth Acceleration Partners";
	}	
	
	public NewEmployeePage goToNewEmployeePage(){	
		click("CREATE_NEW_EMPLOYEE_LINK");
		if(isElementPresent("EMPLOYEE_FIRSTNAME",20))
			return new NewEmployeePage(driver);
		else 
			return null;
	}	
	
	public LoginPage goToLoginPage(){	
		click("LOGIN_HOMEPAGE");
		if(isElementPresent("EMAIL",20))
			return new LoginPage(driver);
		else 
			return null;
	}	
	
	public LoginPage logOut(){	
		click("LOGOUT_LINK");
		if(isElementPresent("EMAIL",20))
			return new LoginPage(driver);
		else 
			return null;
	}		
	
	public Boolean deleteJustCreatedUser(){
		List<WebElement> idValues = getElementList("ID_COLUMN",20);
		List<WebElement> deleteLinks = getElementList("DELETE_COLUMN",20);
		Iterator<WebElement> itIds = idValues.iterator();
		Iterator<WebElement> itDels = deleteLinks.iterator();
		Alert alt;
		Boolean result = false;		
		while(itIds.hasNext()) {
		    if(itIds.next().getText().trim().contains(CONFIG.getProperty("employeeId")))
		    {
		    	itDels.next().click();
		    	alt = driver.switchTo().alert();
		    	alt.accept();
		    	result = true;
		    }
		}
		return result;			
	}
}	
	
	

