package base;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Page {
	
	public WebDriver driver;
	public  Properties LOCATORS;
	public  Properties CONFIG;		
	public String PAGE_TITLE;

	public Page(WebDriver driver){
		this.driver = driver;		
		LOCATORS = new Properties();
		CONFIG = new Properties();
		try{
			FileInputStream fs = new FileInputStream("src\\main\\java\\config\\config.properties");
			CONFIG.load(fs);			
			fs = new FileInputStream("src\\main\\java\\config\\locators.properties");
			LOCATORS.load(fs);
			fs.close();
			}
		catch(Exception e){
				return;
			}			
	}
	
	public void loadPage(){
		Assert.assertEquals(driver.getTitle(), getPageTitle());
	}
	

	public void click(String cssKey){
		try{
	        driver.findElement(By.cssSelector(LOCATORS.getProperty(cssKey))).click();
		}catch(Exception e){
			System.out.println("error");
			e.printStackTrace();
		}
	}
	
	public void input(String cssKey, String text){
		try{
		driver.findElement(By.cssSelector(LOCATORS.getProperty(cssKey))).sendKeys(text);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void select(String cssKey, String text){
		try{
		Select sel = new Select(driver.findElement(By.cssSelector(LOCATORS.getProperty(cssKey))));
		sel.selectByVisibleText(text);
		}catch(Exception e){
			e.printStackTrace();
			throw new AssertionError("There was a problem setting select dropdown value!");
		}
	}

	public boolean isElementPresent(String cssKey, Integer timeout){
		try{
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			@SuppressWarnings("unused")
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LOCATORS.getProperty(cssKey))));
			return true;
		}catch(Exception e){
			return false;
		}		
	}
		
	public List<WebElement> getElementList(String cssKey, Integer timeout){
		List<WebElement> elements = null;
		try{
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			@SuppressWarnings("unused")
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LOCATORS.getProperty(cssKey))));
			elements = driver.findElements(By.cssSelector(LOCATORS.getProperty(cssKey)));			
		}catch(Exception e){
			e.printStackTrace();
		}
		return elements;	
	}	
	
	public String getMessageText(String cssKey, Integer timeout){
		try{
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LOCATORS.getProperty(cssKey))));
			return element.getText();
		}catch(Exception e){
			return "not found";
		}		
	}	
	
	public boolean isLinkPresent(String linkText){
		try{
			driver.findElement(By.linkText(linkText));
		}catch(Exception e){
			return false;
		}
		
		return true;
	}
	
	public String getPageTitle(){
		return PAGE_TITLE;
	}
	
	public void takeScreenshot(String fileName) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	    try {
			FileUtils.copyFile(scrFile, new File("screenshots\\"+ fileName + ".jpg" ));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
