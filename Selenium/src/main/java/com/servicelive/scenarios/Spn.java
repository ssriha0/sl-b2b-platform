package com.servicelive.scenarios;

/****
 * Class for creating a SPN
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
import org.openqa.selenium.support.ui.WebDriverWait;

import com.servicelive.logger.LoggerClass;



public class Spn {
	
	
	private static final long pageTimeout = 400;  
	
	public static String createSpn(Properties properties) {
		
		//specify the location of firefox
		File pathToBinary = new File(properties.getProperty("ffLocation"));
		FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		WebDriver driver = new FirefoxDriver(ffBinary,firefoxProfile); 
		LoggerClass loggerClass=new LoggerClass();
		String result = "";
		try {
			loggerClass.debugTracker("****************************");			
			loggerClass.debugTracker("SelectProviderNetwork");
			loggerClass.debugTracker("****************************");
			
			//specify the property file in order to connect to an env
			
			
			WebDriverWait wait = new WebDriverWait(driver, pageTimeout);
			driver.get(properties.getProperty("HomeUrl"));
			loggerClass.debugTracker("Home page Loaded sucessfully");
			
			//Login Page	
			driver.findElement(By.cssSelector(properties.getProperty("Login"))).click();
			driver.findElement(By.id("username")).clear();
			driver.findElement(By.id("username")).sendKeys(properties.getProperty("userName"));
			driver.findElement(By.id("password")).clear();
			driver.findElement(By.id("password")).sendKeys(properties.getProperty("password"));
			driver.findElement(By.id("submit")).click();
			loggerClass.debugTracker("Login Page Action completed");
			   
			//Administrator office	
			try{
			    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='page']/table/tbody/tr[2]/td[2]/div/div[1]")));
			}catch(UnhandledAlertException e){
			    loggerClass.debugTracker("Unexpected Modal dialog present : Continuing");
			}			    
			    
			Actions action = new Actions(driver);
			WebElement we = driver.findElement(By.xpath(properties.getProperty("Adminoffice")));
			action.moveToElement(we).build().perform();
			driver.findElement(By.xpath(properties.getProperty("Adminoffice"))).isSelected();
			loggerClass.debugTracker("selected");
			    
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='spnMonitorAction_']")));
			driver.findElement(By.cssSelector("a[href*='spnMonitorAction_']")).click();
			loggerClass.debugTracker("Clicked at SPN monitor");			    
			loggerClass.debugTracker("SPN monitor page loaded");
			    
			driver.findElement(By.xpath(properties.getProperty("Spn"))).click();
			loggerClass.debugTracker("clicked at spn");
			    
			//Providing Values for SPN
			    
			// for generating random spn name			    
			SecureRandom random = new SecureRandom();
			String strng="SeleniumSPN_"+(String)new BigInteger(25, random).toString(10);
			loggerClass.debugTracker("random spn name generated");
			loggerClass.debugTracker(strng);	    
			    
			driver.findElement(By.id("spnHeader.spnName")).clear();
		    driver.findElement(By.id("spnHeader.spnName")).sendKeys(strng);
		    driver.findElement(By.id("spnHeader.contactName")).clear();
		    driver.findElement(By.id("spnHeader.contactName")).sendKeys("sibin");
		    driver.findElement(By.id("spnHeader.contactEmail")).clear();
		    driver.findElement(By.id("spnHeader.contactEmail")).sendKeys("sibin@gmail.com");
		    driver.findElement(By.id("spnHeader.contactPhone")).clear();
		    driver.findElement(By.id("spnHeader.contactPhone")).sendKeys("960-555-3250");
		    driver.findElement(By.id("spnHeader-spnDescription")).clear();
		    driver.findElement(By.id("spnHeader-spnDescription")).sendKeys("Description");
		    loggerClass.debugTracker("SPN Description  given");
		 
		    driver.findElement(By.cssSelector("div.picked.pickedClick > label")).click();
		    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@name='approvalItems.selectedMainServices'])[2]")));
		    driver.findElement(By.xpath("(//input[@name='approvalItems.selectedMainServices'])[2]")).click();		   
		    driver.findElement(By.xpath("//div[@id='tab-content']/div/div/fieldset/div[6]")).click();    
			    
		    Thread.sleep(3000);
		    driver.findElement(By.cssSelector("#servicesWithSkills > div.picked.pickedClick > label")).click();
		    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='servicesWithSkills']/div[2]/div[1]/input")));
		    driver.findElement(By.xpath("//*[@id='servicesWithSkills']/div[2]/div[1]/input")).click();
		    driver.findElement(By.xpath("//div[@id='tab-content']/div/div/fieldset/div[6]")).click();
		    
		    driver.findElement(By.cssSelector("h3.collapse.c-documents > span.plus")).click();
		    driver.findElement(By.cssSelector("span.closed")).click();
		    driver.findElement(By.id("uploadDocData.uploadDocTitle")).clear();
		    driver.findElement(By.id("uploadDocData.uploadDocTitle")).sendKeys("doc");
		    driver.findElement(By.id("uploadDocData.uploadDocDesc")).clear();
		    driver.findElement(By.id("uploadDocData.uploadDocDesc")).sendKeys("spnDesc");    
			  
		    //uploading file path should specified		    
		    driver.findElement(By.id("photoDoc")).sendKeys("D:\\WorkSpace\\Selenium\\Selenium\\src\\main\\resources\\Steps.txt");
		    loggerClass.debugTracker("file Uploaded");
		    
		    //saving SPN		    
		    driver.findElement(By.id("saveDoneButton")).click();
		    Boolean isPresent = driver.findElements(By.xpath("//*[@id='numNetworkResultsDiv']")).size() > 0;
		    if(isPresent){
		    	driver.findElement(By.xpath("//*[@id='numNetworkResultsDiv']")).click();
		    	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loadDiv']")));
		    }
		    
			List<WebElement> lists = driver.findElements(By.className("nwk-title"));
			boolean success=false;
		    for(WebElement list:lists){
		    	String spnName = list.getText();
		    	if(spnName.trim().equals(strng.trim())){
		    		success=true;
		    		break;
		    	}
		    }
		    if(success){
		    	loggerClass.debugTracker("SPN saved succesfully");
		    	result = "SPN saved succesfully>>"+strng;
		    	 
		    }else{
		    	loggerClass.debugTracker("Failed to save SPN!!");
		    	result = "Failed to save SPN!!";
		    }
		   
		} catch (Exception e) {
			e.printStackTrace();
			loggerClass.debugTracker("ERROR OCCURED. Not able to save SPN. Error is >> "+e);
			result = "ERROR OCCURED. Not able to save SPN. Error is >> "+e;
			
		}
		finally{
			driver.close();
		}
		return result;
}

}
