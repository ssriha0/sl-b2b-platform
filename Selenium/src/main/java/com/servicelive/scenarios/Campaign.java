package com.servicelive.scenarios;
/************************
 * Class for creating campaign
 * 
 */


import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.servicelive.logger.LoggerClass;



public class Campaign {	
	
	
	private static final long pageTimeout = 40;  
	
	public static String createCampaign(Properties properties) {
		
		//specify the location of firefox
		File pathToBinary = new File(properties.getProperty("ffLocation"));
		FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		WebDriver driver = new FirefoxDriver(ffBinary,firefoxProfile); 
		LoggerClass loggerClass=new LoggerClass();
		String result = "";
		
		try {
			
			loggerClass.debugTracker("****************************");			
			loggerClass.debugTracker("Create Campaign");
			loggerClass.debugTracker("****************************");
			
			//specify the property file in order to connect to an env
		
			
			WebDriverWait wait = new WebDriverWait(driver, pageTimeout);
			driver.get(properties.getProperty("HomeUrl"));
			loggerClass.debugTracker("Home page Loaded sucessfully");
			
			//Login page
			driver.findElement(By.cssSelector(properties.getProperty("Login"))).click();
			loggerClass.debugTracker("Clicked at login");
			driver.findElement(By.id("username")).clear();
			driver.findElement(By.id("username")).sendKeys(properties.getProperty("userName"));
			driver.findElement(By.id("password")).clear();
			driver.findElement(By.id("password")).sendKeys(properties.getProperty("password"));
			driver.findElement(By.id("submit")).click();
			loggerClass.debugTracker("Login Details submitted");
			Thread.sleep(3000);   
			    
			//Administrator Office
			try{
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='page']/table/tbody/tr[2]/td[2]/div/div[1]")));
			}catch(UnhandledAlertException e){
			    loggerClass.debugTracker("Unexpected Modal dialog present : Continuing");
			}
			    
			Actions action = new Actions(driver);
		    WebElement we = driver.findElement(By.xpath(properties.getProperty("Adminoffice")));
		    action.moveToElement(we).build().perform();
		    driver.findElement(By.xpath(properties.getProperty("Adminoffice"))).isSelected();
		    loggerClass.debugTracker("Clicked at AdminOffice");    
			    
		    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='spnMonitorAction_']")));
			driver.findElement(By.cssSelector("a[href*='spnMonitorAction_']")).click();
			
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(properties.getProperty("Createcampaign"))));    
			driver.findElement(By.cssSelector(properties.getProperty("Createcampaign"))).click();
			loggerClass.debugTracker("Clicked at create campaign");
			new Select(driver.findElement(By.id("spnList"))).selectByIndex(1);
			
			loggerClass.debugTracker("selected SHC RE Facilities - Computer & Network Services: Sears Facilities Select Provider Network");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loadSPNSpinner']/div[2]/div[1]/img")));    
			   
			// During selection of start & End date it should satisfy validations.			    
			driver.findElement(By.id("startDate")).click();
			loggerClass.debugTracker("Startdate selected");
			Thread.sleep(1000);
			driver.findElement(By.xpath("/html/body/div[2]/div/a[2]")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//a[contains(@href, '#')])[5]")));
			driver.findElement(By.xpath("(//a[contains(@href, '#')])[5]")).click();
			driver.findElement(By.id("endDate")).click();
			loggerClass.debugTracker("End date selected");
			    
			driver.findElement(By.xpath("/html/body/div[2]/div/a[2]")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='ui-datepicker-div']/div/a[2]")));
			Thread.sleep(2000);   
			driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div/a[2]")).click();	      
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='ui-datepicker-div']/table/tbody/tr[5]/td[3]/a")));
			driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/table/tbody/tr[5]/td[3]/a")).click();
			driver.findElement(By.id("approvalItems.isAllMarketsSelected")).click();
			driver.findElement(By.xpath("//*[@id='approvalItems.isAllStatesSelected']")).click();
			loggerClass.debugTracker("Market selected");
			new Select(driver.findElement(By.id("approvalItems.selectedMinimumRating"))).selectByVisibleText("3 to 5 Star");
			driver.findElement(By.id("approvalItems.minimumCompletedServiceOrders")).click();
			driver.findElement(By.id("approvalItems.minimumCompletedServiceOrders")).clear();
			driver.findElement(By.id("approvalItems.minimumCompletedServiceOrders")).sendKeys("12");
			    
			driver.findElement(By.cssSelector("span.plus.c-cred")).click();
		    driver.findElement(By.cssSelector("div.toggle.c-cred > fieldset > div.clearfix > div.half.multiselect > div.picked.pickedClick > label")).click();
		    driver.findElement(By.name("approvalItems.selectedVendorCredTypes")).click();
		    driver.findElement(By.xpath("//*[@id='vendorCredTypesWithCategories']/div[1]/label")).click();		    

		    SecureRandom random = new SecureRandom();
			String strng="SeleniumCampaign_"+new BigInteger(25, random).toString(10);
			loggerClass.debugTracker("random campaign name generated");
			loggerClass.debugTracker(strng);
			
			driver.findElement(By.name("campaignHeader.campaignName")).clear();
			driver.findElement(By.name("campaignHeader.campaignName")).sendKeys(strng);
			loggerClass.debugTracker("Campaign name is typed");
			driver.findElement(By.name("method:saveAndDone")).click();
			Thread.sleep(3000);
			Boolean isPresent = driver.findElements(By.xpath("//*[@id='numNetworkResultsDiv']")).size() > 0;
		    if(isPresent){
		    	driver.findElement(By.xpath("//*[@id='numCampaignResultsDiv']")).click();
		    	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loadDiv']")));
		    }
		    
			List<WebElement> lists = driver.findElements(By.className("nwk-title"));
			boolean success=false;
		    for(WebElement list:lists){
		    	String campaignName = list.getText();
		    	if(campaignName.trim().equals(strng.trim())){
		    		success=true;
		    		break;
		    	}
		    }
		    if(success){
		    	loggerClass.debugTracker("Campaign created succesfully");
		    	result = "Campaign created succesfully>>"+strng;
		    }
		    else{
		    	loggerClass.debugTracker("Failed to create Campaign!!");
		    	result = "Failed to create Campaign!!";
		    }
		    
		} catch (Exception e) {
			e.printStackTrace();
			loggerClass.debugTracker("ERROR OCCURED. Not able to create Campaign. Error is >> "+e);
			result = "ERROR OCCURED. Not able to create Campaign. Error is >> "+e;
		}
		finally{
			driver.close();
		}
		return result;
	}

}
