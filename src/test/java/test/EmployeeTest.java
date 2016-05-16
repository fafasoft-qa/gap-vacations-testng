package test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import base.TestSuiteSetup;
import pages.EmployeesInfoPage;
import pages.FindEmployeePage;
import pages.LoginPage;
import pages.NewEmployeePage;

public class EmployeeTest extends TestSuiteSetup{	
	
	LoginPage loginPage;
	EmployeesInfoPage employeesInfoPage;
	NewEmployeePage newEmployeePage;
	FindEmployeePage findEmployeePage;
	
	@BeforeClass(alwaysRun = true)
	public void setup(){
		loginPage = new LoginPage(getDriver());  			
	}	
	
	@Test
	public void givenUserLoginOnVacationsManagement(){	
		try{
			loginPage.loginPresent();
			}
		catch(Throwable t){
			loginPage.takeScreenshot("LoginPageNotPresent");
			}
		try{
			Assert.assertTrue(loginPage.doLogin(), "User login failed.");
			}
		catch(Throwable t){
			loginPage.takeScreenshot("LoginAttemptFailed");
			}			
		
	}
	
	@Test
	public void whenUserCreatesNewEmployee() throws Exception {		
		employeesInfoPage = new EmployeesInfoPage(getDriver());
		Assert.assertEquals(employeesInfoPage.getMessageText("LOGIN_OK", 20), employeesInfoPage.CONFIG.getProperty("LOGIN_SUCCESS"),"Employee Info Page is not present after login attempt.");
		newEmployeePage = employeesInfoPage.goToNewEmployeePage();
		try{
			newEmployeePage.createNewEmployee();
			employeesInfoPage = new EmployeesInfoPage(getDriver());			
			Assert.assertEquals(employeesInfoPage.getMessageText("CREATED_OK", 20).trim(), employeesInfoPage.CONFIG.getProperty("CREATE_SUCCESS").trim(),"New Employee was not successfully created.");		
			Assert.assertTrue(employeesInfoPage.getMessageText("CREATED_FIRSTNAME", 20).trim().contains(employeesInfoPage.CONFIG.getProperty("employeeFirstname").trim()),"New Employee's Firstname is not correct.");
			Assert.assertTrue(employeesInfoPage.getMessageText("CREATED_LASTNAME", 20).trim().contains(employeesInfoPage.CONFIG.getProperty("employeeLastname").trim()),"New Employee's Lastname is not correct.");
			Assert.assertTrue(employeesInfoPage.getMessageText("CREATED_EMAIL", 20).trim().contains(employeesInfoPage.CONFIG.getProperty("employeeEmail").trim()),"New Employee's Email is not correct.");		
			Assert.assertTrue(employeesInfoPage.getMessageText("CREATED_ID", 20).trim().contains(employeesInfoPage.CONFIG.getProperty("employeeId").trim()),"New Employee's ID is not correct.");
			Assert.assertTrue(employeesInfoPage.getMessageText("CREATED_LEADERNAME", 20).trim().contains(employeesInfoPage.CONFIG.getProperty("employeeLeadername").trim()),"New Employee's Leadername is not correct.");				
			Assert.assertTrue(employeesInfoPage.getMessageText("CREATED_DATE", 20).trim().contains(employeesInfoPage.CONFIG.getProperty("startWorkingDate").trim()),"New Employee's Start Working Date is not correct.");	
			}
		catch(AssertionError e){
			employeesInfoPage.takeScreenshot("NewEmployeeWasNotSuccessfullyCreated");
			throw new AssertionError("There was a problem setting select dropdown value!");
			}		
	}		
	
	@Test
	public void whenUserVerifiesNewEmployeeInfoOnPublicSite(){		
		loginPage = employeesInfoPage.logOut();
		findEmployeePage = loginPage.goToFindEmployeePage();
		String expectedString = findEmployeePage.CONFIG.getProperty("employeeFirstname").trim() + " " +  findEmployeePage.CONFIG.getProperty("employeeLastname").trim();	
		try{
			Assert.assertTrue((findEmployeePage.verifyEmployeeInfo()).trim().contains(expectedString),"Employee fullname is not correct after seaching by Id.");
			}
		catch(Throwable t){
			findEmployeePage.takeScreenshot("EmployeeCannotBeFound");
			}		
	}	
	
	@Test
	public void thenUserDeletesEmployeeCreatedAndVerifiesItOnPublicSite(){		
		employeesInfoPage = new EmployeesInfoPage(getDriver());	
		loginPage = employeesInfoPage.goToLoginPage();
		Assert.assertTrue(loginPage.doLogin());
		employeesInfoPage = new EmployeesInfoPage(getDriver());			
		Assert.assertEquals(employeesInfoPage.getMessageText("LOGIN_OK", 20), employeesInfoPage.CONFIG.getProperty("LOGIN_SUCCESS"),"Employee Info Page is not present after login attempt.");
		try{
			Assert.assertTrue(employeesInfoPage.deleteJustCreatedUser(),"Employee could not be found and deleted.");
			}
		catch(Throwable t){
			findEmployeePage.takeScreenshot("EmployeeCannotBeDeleted");
			}
		loginPage = employeesInfoPage.logOut();
		findEmployeePage = loginPage.goToFindEmployeePage();		
		String expectedString = findEmployeePage.CONFIG.getProperty("EMPLOYEE_NOT_FOUND").trim();
		try{
			Assert.assertTrue((findEmployeePage.verifyEmployeeInfo()).trim().contains(expectedString),"Deleted employee was not expected to be found.");
			}
		catch(Throwable t){
			findEmployeePage.takeScreenshot("DeletedEmployeeShouldNotBeFound");
			}		
	}		
}