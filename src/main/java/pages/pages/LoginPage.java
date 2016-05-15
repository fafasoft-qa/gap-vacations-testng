package pages;

import base.Page;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginPage extends Page {
	
	public LoginPage(WebDriver driver) {
		super(driver);
		this.PAGE_TITLE = "Vacations Management Site - Growth Acceleration Partners";
	}

	public Boolean doLogin(){			
		input("EMAIL", CONFIG.getProperty("defaultUsername")); 
		input("PASSWORD",CONFIG.getProperty("defaultPassword"));
		click("LOGIN_BUTTON");
		if(getMessageText("LOGIN_OK", 20).trim().contains(CONFIG.getProperty("LOGIN_SUCCESS")))
			return true;
		else 
			return false;
	}
	
	public void loginPresent()
	{
		Assert.assertTrue(isElementPresent("EMAIL",20), "Login page could not be loaded.");
	}
	
	public FindEmployeePage goToFindEmployeePage(){	
		click("PUBLIC_SITE");
		if(isElementPresent("FIND_EMPLOYEE_BUTTON",20))
			return new FindEmployeePage(driver);
		else 
			return null;
	}		
	
	
}