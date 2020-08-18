/********************************************************
Class	:BuyerBid
Author	:Sreekanth Vadassery
Date	:04-12-2013
Purpose :Create a B2C Bid order and post it to providers.
         Accept the order and take it to closure. 
 *******************************************************/

package com.servicelive.scenarios;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.servicelive.logger.LoggerClass;




public class BuyerBid {

	public static FirefoxProfile profile=null;

	public static String createBid(Properties properties) {
		
		LoggerClass loggerClass=new LoggerClass();
		String result = "";
		
		//home page
		File pathToBinary = new File(properties.getProperty("ffLocation"));
		FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		WebDriver webDriver = new FirefoxDriver(ffBinary,firefoxProfile); 
		String id="";
		
		try{
			WebDriverWait wait = new WebDriverWait(webDriver, 100);
			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date dateNow = new Date();
	
			loggerClass.debugTracker("                                    ");
			loggerClass.debugTracker("BuyerBid.java");
			loggerClass.debugTracker("^^^^^^^^^^^^^^^STARTS^^^^^^^^^^^^^^^");
			loggerClass.debugTracker(dateFormat.format(dateNow));

			//home page
			webDriver.get(properties.getProperty("HomeUrl"));
			loggerClass.debugTracker("Home Page loaded");

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='topNav']/div[3]/a/img")));
			WebElement logInButton=webDriver.findElement(By.xpath("//*[@id='topNav']/div[3]/a/img"));
			logInButton.click();

			/////////////////////////////////////BUYER LOGIN FOR CREATING BID ORDER//////////////////////////////////////////////

			//login page
			loggerClass.debugTracker("Buyer logging in for creating bid order");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='username']")));
			WebElement userNameTextBox=webDriver.findElement(By.xpath("//*[@id='username']"));
			userNameTextBox.clear();			
			userNameTextBox.sendKeys(properties.getProperty("bidBuyer"));			
			WebElement passwordTextBox=webDriver.findElement(By.xpath("//*[@id='password']"));
			passwordTextBox.clear();
			passwordTextBox.sendKeys(properties.getProperty("password"));
			WebElement submitButton=webDriver.findElement(By.xpath("//*[@id='submit']"));
			submitButton.click();

			//ServiceLive Dashboard
			loggerClass.debugTracker("Buyer logged in");
			Thread.sleep(3000);
			try{
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='dbTile_serviceOrders']/div[2]/div/p[1]/strong/a")));
				
			}catch(UnhandledAlertException e){
				loggerClass.debugTracker("Unexpected Modal dialog present : Continuing");
			}
			
			WebElement createServiceOrderLink=webDriver.findElement(By.xpath("//*[@id='dbTile_serviceOrders']/div[2]/div/p[1]/strong/a"));
			createServiceOrderLink.click();
			loggerClass.debugTracker("create service order link clicked");

			//works when sarah_facilities login
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='skuCreationFacilityPage']/div/div[1]")));

			loggerClass.debugTracker("scope of work page present");
			//window handler now on pop up

			//zip code pop up
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='serviceLocationZipCode']")));			
			WebElement zipTextBox=webDriver.findElement(By.xpath("//*[@id='serviceLocationZipCode']"));
			zipTextBox.click();
			zipTextBox.clear();
			zipTextBox.sendKeys("60012");
			Thread.sleep(3000);

			loggerClass.debugTracker("zip code populated");

			ArrayList<WebElement> continueButtonList=(ArrayList<WebElement>) webDriver.findElements(By.className("btnBevel"));
			continueButtonList.get(1).click();

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("html/body/div[7]")));

			loggerClass.debugTracker("pop up closed");
			
			new Select(webDriver.findElement(By.id("mainServiceCategory"))).selectByVisibleText("Automotive");
			webDriver.findElement(By.id("soWizardScopeOfWorkCreate_scopeOfWorkDTO_tasks_0__taskName")).sendKeys("Task Name");
			Thread.sleep(5000);
			new Select(webDriver.findElement(By.id("categorySelection_0"))).selectByVisibleText("Air Conditioning");
			Thread.sleep(5000);
			new Select(webDriver.findElement(By.id("skillSelection_0"))).selectByVisibleText("Estimates");
			webDriver.findElement(By.id("serviceLocationContact.firstName")).clear();
			webDriver.findElement(By.id("serviceLocationContact.firstName")).sendKeys("first name");
			webDriver.findElement(By.id("serviceLocationContact.lastName")).clear();
			webDriver.findElement(By.id("serviceLocationContact.lastName")).sendKeys("Last name");
			webDriver.findElement(By.id("serviceLocationContact.streetName1")).clear();
			webDriver.findElement(By.id("serviceLocationContact.streetName1")).sendKeys("Street Name");
			webDriver.findElement(By.id("serviceLocationContact.aptNo")).clear();
			webDriver.findElement(By.id("serviceLocationContact.aptNo")).sendKeys("145");
			webDriver.findElement(By.id("serviceLocationContact.city")).clear();
			webDriver.findElement(By.id("serviceLocationContact.city")).sendKeys("trivandrum");
			webDriver.findElement(By.id("serviceLocationContact.zip4")).clear();
			webDriver.findElement(By.id("serviceLocationContact.zip4")).sendKeys("1234");
			webDriver.findElement(By.id("serviceLocationContact.email")).clear();
			webDriver.findElement(By.id("serviceLocationContact.email")).sendKeys("example@example.com");
			webDriver.findElement(By.id("serviceLocationContact.phones[0].areaCode")).clear();
			webDriver.findElement(By.id("serviceLocationContact.phones[0].areaCode")).sendKeys("960");
			webDriver.findElement(By.id("serviceLocationContact.phones[0].phonePart1")).clear();
			webDriver.findElement(By.id("serviceLocationContact.phones[0].phonePart1")).sendKeys("555");
			webDriver.findElement(By.id("serviceLocationContact.phones[0].phonePart2")).clear();
			webDriver.findElement(By.id("serviceLocationContact.phones[0].phonePart2")).sendKeys("3259");
			webDriver.findElement(By.id("serviceLocationContact.phones[0].ext")).clear();
			webDriver.findElement(By.id("serviceLocationContact.phones[0].ext")).sendKeys("1234");
			new Select(webDriver.findElement(By.id("phoneClass1"))).selectByVisibleText("Work");
			webDriver.findElement(By.id("title")).clear();
			webDriver.findElement(By.id("title")).sendKeys("title");
			webDriver.findElement(By.id("overview")).clear();
			webDriver.findElement(By.id("overview")).sendKeys("overview text");
			webDriver.findElement(By.id("buyerTandC")).clear();
			webDriver.findElement(By.id("buyerTandC")).sendKeys("Terms and conditions");
			webDriver.findElement(By.name("serviceDateType")).click();

			webDriver.findElement(By.id("modal2ConditionalChangeDate1")).click();
			Thread.sleep(3000);

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='ui-datepicker-div']")));

			webDriver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
			webDriver.findElement(By.linkText("10")).click();

			new Select(webDriver.findElement(By.id("conditionalStartTime"))).selectByVisibleText("01:45 AM");
			webDriver.findElement(By.cssSelector("div.formNavButtons > #next")).click();

			loggerClass.debugTracker("Scope of work page completed");

			//additional info page
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='page']/div/div[2]/div[2]/div[2]")));

			loggerClass.debugTracker("additional info page loaded");

			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("referenceValue")));
			
			webDriver.findElement(By.id("referenceValue")).clear();
            Integer size =  webDriver.findElements(By.id("referenceValue")).size();
            for(int i = 0;i<size;i++){
            	webDriver.findElement(By.name("buyerRef["+i+"].referenceValue")).sendKeys("1234");
            }
			webDriver.findElement(By.cssSelector("#soWizardAdditionalInfoCreate > div.clearfix > div.formNavButtons > #next")).click();

			loggerClass.debugTracker("additional info page completed");

			//parts page
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='page']/div/div[2]/div[2]/div[3]")));

			loggerClass.debugTracker("parts page loaded");

			Thread.sleep(2000);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='soWizardPartsCreate']/div[2]/div[1]")));

			webDriver.findElement(By.id("partsSuppliedBy3")).click();

			webDriver.findElement(By.cssSelector("div.formNavButtons > #next")).click();

			loggerClass.debugTracker("parts page completed");

			//providers page
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='page']/div/div[2]/div[2]/div[4]")));

			loggerClass.debugTracker("providers page loaded");

			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("selectTopProviders")));
			new Select(webDriver.findElement(By.id("selectTopProviders"))).selectByVisibleText("First 5");

			webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='page']/div/div[2]/div[2]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='soWizardProvidersCreate']/div[4]/div[1]")));

			webDriver.findElement(By.cssSelector("div.formNavButtons > #next")).click();

			loggerClass.debugTracker("providers page completed");

			//pricing page
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='page']/div/div[2]/div[2]/div[5]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='radioBid']")));

			loggerClass.debugTracker("pricing page loaded");

			webDriver.findElement(By.id("radioBid")).click();
			webDriver.findElement(By.id("sealedbid")).click();
			webDriver.findElement(By.id("checkShareLocation")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='soWizardPricingCreate']/div[2]/div[1]")));
			webDriver.findElement(By.cssSelector("div.formNavButtons > #next")).click();

			loggerClass.debugTracker("pricing page completed");

			//review page
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='page']/div/div[2]/div[2]")));

			loggerClass.debugTracker("review page loaded");

			//id was printing here 
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='acceptTermsAndConditions1']")));
			webDriver.findElement(By.id("acceptTermsAndConditions1")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='soWizardReviewCreate']/div[9]/div[1]")));

			//try to print serive order id
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='soWizardReviewCreate']/div[1]/div[2]/div/div/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[2]")));
			WebElement serviceOrderId=webDriver.findElement(By.xpath("//*[@id='soWizardReviewCreate']/div[1]/div[2]/div/div/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[2]"));
			id=serviceOrderId.getText();

			loggerClass.debugTracker("service order #: "+id);
			
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("createAndRoute")));
			webDriver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
			webDriver.findElement(By.id("createAndRoute")).click();

			loggerClass.debugTracker("review page completed");		

			//service order monitor page
			webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='Posted']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='Posted']/div[1]/div[2]/table")));

			loggerClass.debugTracker("service order monitor page loaded");

			loggerClass.debugTracker("service order monitor page completed..");
			Thread.sleep(3000);
			webDriver.findElement(By.linkText("Logout")).click();

			loggerClass.debugTracker("buyer logged out after creating bid order #:"+id);

			loggerClass.debugTracker("**************************************************************");

			////////////////////////////////////PROVIDER LOG IN TO PLACE BID///////////////////////////////////////////

			//copying from ProviderDummy.java
			loggerClass.debugTracker("provider logging in for giving bid values");

			webDriver.get(properties.getProperty("HomeUrl"));
			webDriver.findElement(By.cssSelector("img[alt=\"Login\"]")).click();

			//login page
			webDriver.findElement(By.id("username")).clear();
			webDriver.findElement(By.id("username")).sendKeys("northwoods.construction");
			webDriver.findElement(By.id("password")).clear();
			webDriver.findElement(By.id("password")).sendKeys("Test123!");
			webDriver.findElement(By.id("submit")).click();

			//dashboard
			webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			loggerClass.debugTracker("dashboard page loaded");
			
			//clicking on service order monitor page
			webDriver.findElement(By.xpath("//*[@id='serviceOrderMonitor']")).click();
			loggerClass.debugTracker("service order monitor link clicked");

			Thread.sleep(1000);
			webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

			//waiting for search title to load
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(properties.getProperty("providerSOIdSearchTab"))));
			
			String url=properties.getProperty("urlSOBidProviderPart1")+id+properties.getProperty("urlSOBidProviderPart2");
			webDriver.get(url);

			//waiting for total labour text box to load
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='totalLabor']")));

			webDriver.findElement(By.id("totalLabor")).click();
			webDriver.findElement(By.id("totalLabor")).clear();
			webDriver.findElement(By.id("totalLabor")).sendKeys("50.00");
			webDriver.findElement(By.id("partsMaterials")).clear();
			webDriver.findElement(By.id("partsMaterials")).sendKeys("50.00");
			webDriver.findElement(By.id("comment")).clear();
			webDriver.findElement(By.id("comment")).sendKeys("comments with bid");
			webDriver.findElement(By.id("termsAndConditions")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(properties.getProperty("bidSubmitButtonProvider"))));

			webDriver.findElement(By.xpath(properties.getProperty("bidSubmitButtonProvider"))).click();

			webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);
			Thread.sleep(4000);

			loggerClass.debugTracker("bid submitted by provider for order id "+id);

			Thread.sleep(3000);
			webDriver.findElement(By.linkText("Logout")).click();

			loggerClass.debugTracker("provider logged out after submitting bid");

			loggerClass.debugTracker("**************************************************************");

			//buyer logging in
			//home page
			
			////////////////////////////////////BUYER LOG IN TO ACCEPT BID///////////////////////////////////////////
			webDriver.get(properties.getProperty("HomeUrl"));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='topNav']/div[3]/a/img")));
			webDriver.findElement(By.xpath("//*[@id='topNav']/div[3]/a/img")).click();

			//login page
			//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='username']")));
			webDriver.findElement(By.xpath("//*[@id='username']")).clear();
			//for env1 buyer is shahroz_f
			
			webDriver.findElement(By.xpath("//*[@id='username']")).sendKeys("sarah_facilities");
			
			webDriver.findElement(By.xpath("//*[@id='password']")).clear();
			webDriver.findElement(By.xpath("//*[@id='password']")).sendKeys("Test123!");

			webDriver.findElement(By.xpath("//*[@id='submit']")).submit();

			loggerClass.debugTracker("Buyer logged in for accepting the bid");
			//System.out.println("Buyer logged in for accepting the bid");
			//dashboard page
			webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);

			loggerClass.debugTracker("Buyer dashboard page loaded");
			//System.out.println("Buyer dashboard page loaded");

			//clicking on the service order monitor
			webDriver.findElement(By.id("serviceOrderMonitor")).click();
			webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);

			loggerClass.debugTracker("service order monitor page loaded");
			//System.out.println("service order monitor page loaded");

			webDriver.findElement(By.xpath("//div[@id='page']/div[4]/div/div[7]/div/span")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchType")));

			webDriver.findElement(By.id("searchValue")).click();
			webDriver.findElement(By.id("searchValue")).clear();
			webDriver.findElement(By.id("searchValue")).sendKeys(id);

			loggerClass.debugTracker("search complete");
			//System.out.println("search complete");

			String url1=properties.getProperty("urlSOBidAcceptBuyerPart1")+id+properties.getProperty("urlSOBidAcceptBuyerPart2");
			webDriver.get(url1);
			webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(properties.getProperty("acceptConditionsButtonInBuyer"))));

			webDriver.findElement(By.xpath(properties.getProperty("acceptConditionsButtonInBuyer"))).click();
			webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);
			Thread.sleep(3000);
			webDriver.findElement(By.linkText("Logout")).click();

			loggerClass.debugTracker("Buyer logged out after accepting the bid");

			loggerClass.debugTracker("**************************************************************");

			/////////////////////////////////////////////////PROVIDER///////////////////////////////////////////////

			//provider logging in for service order completing

			loggerClass.debugTracker("provider logging in for service order completing");
			//System.out.println("provider logging in for service order completing");

			webDriver.get(properties.getProperty("HomeUrl"));
			webDriver.findElement(By.cssSelector("img[alt=\"Login\"]")).click();

			//login page
			webDriver.findElement(By.id("username")).clear();
			webDriver.findElement(By.id("username")).sendKeys("northwoods.construction");
			webDriver.findElement(By.id("password")).clear();
			webDriver.findElement(By.id("password")).sendKeys("Test123!");
			webDriver.findElement(By.id("submit")).click();

			webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);

			loggerClass.debugTracker("dashboard page loaded");

			webDriver.findElement(By.xpath("//*[@id='serviceOrderMonitor']")).click();

			loggerClass.debugTracker("service order monitor link clicked");

			webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);

			//waiting for search title to load
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(properties.getProperty("providerSOIdSearchTab"))));
			webDriver.findElement(By.xpath(properties.getProperty("providerSOIdSearchTab"))).click();

			loggerClass.debugTracker("search tab clicked");

			//searching
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchType")));
			
			loggerClass.debugTracker("search complete");

			String url2 = properties.getProperty("urlSOBidCompleteProviderPart1")+id+properties.getProperty("urlSOBidCompleteProviderPart2");
			
			webDriver.get(url2);

			webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);

			loggerClass.debugTracker("summary loaded");

			//wait for time on site title to load
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(properties.getProperty("timeOnSiteProviderComplete"))));
			webDriver.findElement(By.xpath(properties.getProperty("timeOnSiteProviderComplete"))).click();
			
			loggerClass.debugTracker("time on site link clicked");
			Thread.sleep(2000);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='AddEditDeleteButton']/input[2]")));
			webDriver.findElement(By.xpath("//*[@id='AddEditDeleteButton']/input[2]")).click();

			loggerClass.debugTracker("add entries button clicked");

			//waiting for arrival date text box to load
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='arrivalDate']")));

			String dateDashboard=webDriver.findElement(By.xpath("//*[@id='date_dashboard']")).getText();

			loggerClass.debugTracker(dateDashboard);

			String[] dateParts=dateDashboard.split(" ");
			
			String month=null;			
			
			if(dateParts[1].equals("January")){		    	
		    	month = "01";
		    }
		    else if(dateParts[1].equals("February")){
		    	month = "02";
		    }
		    else if(dateParts[1].equals("March")){
		    	month = "03";
		    }
		    else if(dateParts[1].equals("April")){
		    	month = "04";
		    }
		    else if(dateParts[1].equals("May")){
		    	month = "05";
		    }
		    else if(dateParts[1].equals("June")){
		    	month = "06";
		    }
		    else if(dateParts[1].equals("July")){
		    	month = "07";
		    }
		    else if(dateParts[1].equals("August")){
		    	month = "08";
		    }

		    else if(dateParts[1].equals("September")){
		    	month = "09";
		    }

		    else if(dateParts[1].equals("October")){
		    	month = "10";
		    }

		    else if(dateParts[1].equals("November")){
		    	month = "11";
		    }

		    else if(dateParts[1].equals("December")){
		    	month = "12";
		    }
			
			String[] day = dateParts[2].split(",");
			String date = month+"/"+day[0]+"/"+dateParts[3];

			loggerClass.debugTracker("date: "+date);

			webDriver.findElement(By.id("arrivalDate")).clear();
			webDriver.findElement(By.id("arrivalDate")).sendKeys(date);

			String[] timeParts=dateParts[4].split(":");
			String hour=timeParts[0];
			String minutes=timeParts[1];
			new Select(webDriver.findElement(By.id("arrivalTimeHourString"))).selectByVisibleText(hour);
			new Select(webDriver.findElement(By.id("arrivalTimeMinutesString"))).selectByVisibleText(minutes);
			new Select(webDriver.findElement(By.id("arrivalTimeAmPm"))).selectByVisibleText(dateParts[5]);
			webDriver.findElement(By.xpath("//*[@id='_saveinsert']")).click();
			 

			loggerClass.debugTracker("time on site complete");

			//clicking on complete for payment
			webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Complete for Payment")));
			webDriver.findElement(By.linkText("Complete for Payment")).click();

			//waiting for page load
			webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);

			loggerClass.debugTracker("complete for payment clicked");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='tabs']/ul/li[4]/a/em")));

			//Thread.sleep(12000);

			webDriver.findElement(By.id("resComments")).clear();
			webDriver.findElement(By.id("resComments")).sendKeys("Resolution Comments");
			webDriver.findElement(By.id("soMaxLabor")).click();
			webDriver.findElement(By.id("soMaxLabor")).clear();
			webDriver.findElement(By.id("soMaxLabor")).sendKeys(Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,"50.00");
			webDriver.findElement(By.id("finalPartPrice")).click();
			webDriver.findElement(By.id("finalPartPrice")).clear();
			webDriver.findElement(By.id("finalPartPrice")).sendKeys(Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,"50.00");
			webDriver.findElement(By.id("referenceValue")).clear();
			webDriver.findElement(By.id("referenceValue")).sendKeys("100");

			Thread.sleep(15000);

			//waiting for submit button
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='submitDiv']/img")));
			webDriver.findElement(By.xpath("//*[@id='submitDiv']/img")).click();

			//wait for page load
			webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);
			//waiting for summary div to load
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='ui-tabs-3']/div")));

			loggerClass.debugTracker("successfully completed the service order for payment.");

			loggerClass.debugTracker("Service order Id: "+id);
			//waiting for logout to load
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='topNav']/div/a[6]")));
			Thread.sleep(3000);
			webDriver.findElement(By.linkText("Logout")).click();

			loggerClass.debugTracker("provider logged out after completing the service order");

			loggerClass.debugTracker("**************************************************************");


			////////////////////////////////////////////BUYER////////////////////////////////////////////////


			webDriver.get(properties.getProperty("HomeUrl"));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='topNav']/div[3]/a/img")));
			webDriver.findElement(By.xpath("//*[@id='topNav']/div[3]/a/img")).click();

			loggerClass.debugTracker("Buyer logging in for closing the service order");
			//login page
			webDriver.findElement(By.xpath("//*[@id='username']")).clear();

			webDriver.findElement(By.xpath("//*[@id='username']")).sendKeys("sarah_facilities");
			
			
			webDriver.findElement(By.xpath("//*[@id='password']")).clear();
			webDriver.findElement(By.xpath("//*[@id='password']")).sendKeys("Test123!");

			webDriver.findElement(By.xpath("//*[@id='submit']")).submit();

			loggerClass.debugTracker("Buyer logged in");
			//dashboard page
			webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);

			loggerClass.debugTracker("Buyer dashboard page loaded");

			//clicking on the service order monitor
			webDriver.findElement(By.id("serviceOrderMonitor")).click();
			webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);

			loggerClass.debugTracker("service order monitor page loaded");

			webDriver.findElement(By.xpath("//div[@id='page']/div[4]/div/div[7]/div/span")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchType")));

			String url3=properties.getProperty("urlSOBidCloseBuyerPart1")+id+properties.getProperty("urlSOBidCloseBuyerPart2");

			webDriver.get(url3);
			webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);

			//waiting for close order and pay button
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='candpay']")));

			//clicking on close order and pay button
			webDriver.findElement(By.xpath("//*[@id='candpay']")).click();

			loggerClass.debugTracker("close order and pay button clicked");

			webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);

			//rating the provider starts
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='rateProvider']/div/div[3]/div[1]/p/input")));

			webDriver.findElement(By.name("q-4")).click();
			webDriver.findElement(By.name("q-2")).click();
			webDriver.findElement(By.name("q-1")).click();
			webDriver.findElement(By.name("q-3")).click();
			webDriver.findElement(By.name("q-5")).click();
			webDriver.findElement(By.name("q-6")).click();
			webDriver.findElement(By.id("surveyComments")).clear();
			webDriver.findElement(By.id("surveyComments")).sendKeys("Additional Comments");
			webDriver.findElement(By.cssSelector("input.btnBevel")).click();


			//waiting for ratings heading
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='ui-tabs-7']/div/div[2]/div[1]")));

			loggerClass.debugTracker("Provider has been rated by the buyer");
			//rating the provider ends

			//logging out
			Thread.sleep(3000);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='topNav']/div/a[6]")));
			webDriver.findElement(By.xpath("//*[@id='topNav']/div/a[6]")).click();

			loggerClass.debugTracker("Buyer logged out after closing the order #: "+id);

			loggerClass.debugTracker("**************************************************************");

			loggerClass.debugTracker("closing...");
			loggerClass.debugTracker("^^^^^^^^^^^^^^^^ENDS^^^^^^^^^^^^^^^^");
			loggerClass.debugTracker("                                    ");
			result = "Successfully closed the SO>>"+id;

		}
		catch(Exception e){

			loggerClass.debugTracker("Exception :"+ e.getMessage());
			loggerClass.debugTracker("^^^^^^^^^^^^^^^^ENDS^^^^^^^^^^^^^^^^");
			loggerClass.debugTracker("                                    ");
			e.printStackTrace();
			result = "Error occured. Error is >> "+e+"@@"+id;
		}finally{
			webDriver.close();
		}
		return result;
	}
}
