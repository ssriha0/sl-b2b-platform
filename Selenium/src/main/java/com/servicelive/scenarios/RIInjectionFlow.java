package com.servicelive.scenarios;

import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.servicelive.logger.LoggerClass;

public class RIInjectionFlow {


	public static FirefoxProfile profile=null;

	public static String createRIOrder(Properties properties) {
		
		LoggerClass loggerClass=new LoggerClass();
		String result = "";
		String generatedOrderNum ="";
	
		File pathToBinary = new File(properties.getProperty("ffLocation"));
		FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		WebDriver webDriver = new FirefoxDriver(ffBinary,firefoxProfile); 
			WebDriverWait wait = new WebDriverWait(webDriver, 100);
			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date dateNow = new Date();
	
			loggerClass.debugTracker("                                    ");
			loggerClass.debugTracker("RIAccept.java");
			loggerClass.debugTracker("^^^^^^^^^^^^^^^STARTS^^^^^^^^^^^^^^^");
			loggerClass.debugTracker(dateFormat.format(dateNow));
			String soId = "";
			
			//ORDER CREATION USING RTI TOOL
			try{
			String filepath = properties.getProperty("omsFileLocn");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);
	 
			// Get the root element
			
			Node orderNum = doc.getElementsByTagName("ServiceOrderNumber").item(0);
			SecureRandom random = new SecureRandom();
			generatedOrderNum =new BigInteger(25, random).toString(10);
			orderNum.setTextContent(generatedOrderNum);
			
			Node promisedDate = doc.getElementsByTagName("PromisedDate").item(0);
			String currentDate = promisedDate.getTextContent();
			
			String dateToIncr = currentDate;
			String dt="";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd"); 
			Calendar c = Calendar.getInstance();
			try {
			    c.setTime(sdf.parse(dateToIncr));
			} catch (Exception e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			} 
			c.add(Calendar.DAY_OF_MONTH, 3);  // number of days to add
			dt = sdf.format(c.getTime());
			promisedDate.setTextContent(dt);
			loggerClass.debugTracker("Order NUM = "+generatedOrderNum);
	 
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult stResult = new StreamResult(new File(filepath));
			transformer.transform(source, stResult);
	 
			loggerClass.debugTracker("Done");
			
			webDriver.get(properties.getProperty("RTIUrl"));
		    new Select(webDriver.findElement(By.id("doUpload_environmentQue"))).selectByVisibleText(properties.getProperty("ENV"));
		    new Select(webDriver.findElement(By.id("doUpload_correlationId"))).selectByVisibleText("1");
		    webDriver.findElement(By.id("doUpload_upload")).sendKeys(properties.getProperty("omsFileLocn"));
		    webDriver.findElement(By.id("doUpload_0")).click();
		    
		    Thread.sleep(10000);
		    
		    webDriver.get(properties.getProperty("HomeUrl"));
			loggerClass.debugTracker("Home Page loaded");

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='topNav']/div[3]/a/img")));
			WebElement logInButton=webDriver.findElement(By.xpath("//*[@id='topNav']/div[3]/a/img"));
			logInButton.click();
			
			//login page
			loggerClass.debugTracker("Buyer Login");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='username']")));
			WebElement userNameTextBox=webDriver.findElement(By.xpath("//*[@id='username']"));
			userNameTextBox.clear();			
			userNameTextBox.sendKeys("jizqui3@searshc.com");			
			WebElement passwordTextBox=webDriver.findElement(By.xpath("//*[@id='password']"));
			passwordTextBox.clear();
			passwordTextBox.sendKeys(properties.getProperty("password"));
			WebElement submitButton=webDriver.findElement(By.xpath("//*[@id='submit']"));
			submitButton.click();
			
			//dashboard
			webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			loggerClass.debugTracker("dashboard page loaded");
			
			//clicking on service order monitor page
			webDriver.findElement(By.xpath("//*[@id='serviceOrderMonitor']")).click();
			loggerClass.debugTracker("service order monitor link clicked");
			
			Thread.sleep(1000);
			webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

			//waiting for search title to load
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='page']/div[4]/div/div[7]/div/span")));

			webDriver.findElement(By.xpath("//div[@id='page']/div[4]/div/div[7]/div/span")).click();

			loggerClass.debugTracker("search tab clicked");
			Thread.sleep(20000);
			//searching
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchType")));
			Thread.sleep(8000);
			webDriver.findElement(By.id("searchType")).click();
			//Thread.sleep(1000);
			new Select(webDriver.findElement(By.id("searchType"))).selectByVisibleText("Ref: ORDER NUMBER");
			Thread.sleep(1000);
			webDriver.findElement(By.id("searchValue")).click();
			webDriver.findElement(By.id("searchValue")).clear();
			webDriver.findElement(By.id("searchValue")).sendKeys(generatedOrderNum);
			//webDriver.findElement(By.id("searchValue")).sendKeys("25444373");
			Thread.sleep(3000);
			webDriver.findElement(By.xpath("//div[@id='searchTerms']/table/tbody/tr/td[3]/input")).click();
			Thread.sleep(3000);
			webDriver.findElement(By.xpath("//div[@id='searchTerms']/table/tbody/tr/td[4]/input")).click();
			Thread.sleep(5000);
			loggerClass.debugTracker("search complete");
			Thread.sleep(8000);
			
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='SearchmyIframe']")));
			webDriver.switchTo().frame(webDriver.findElement(By.xpath("//*[@id='SearchmyIframe']")));
			
			Boolean isPresent = webDriver.findElements(By.xpath("//*[@id='searchCount']")).size()>0;
			//Boolean isPresent2 =  webDriver.findElements(By.xpath("/html/body/div/ul/li/span/table/tbody/tr/td[3]")).size()>0;
			if(isPresent){
				loggerClass.debugTracker("RI order created successfully");
				WebElement e = webDriver.findElement(By.xpath("/html/body/div/ul/li/span/table/tbody/tr/td[3]"));
				soId = e.getText();
				loggerClass.debugTracker("SOID>> "+soId);
				webDriver.switchTo().defaultContent();
				webDriver.findElement(By.linkText("Logout")).click();
				
			}else{
				loggerClass.debugTracker("RI order creation unsuccessful");
				webDriver.close();
				result = "RI order creation unsuccessful"; 
				webDriver.close();
			}
		    
			}catch(UnhandledAlertException e){
				loggerClass.debugTracker("Unexpected Modal dialog present : Continuing");					
				}
				
			
			catch (Exception e) {
				e.printStackTrace();
				loggerClass.debugTracker("RI order creation unsuccessful ERROR>> "+ e);
				result = "RI order creation unsuccessful ERROR>> "+ e;
				webDriver.close();
			}
			
			
			//PROVIDER ACCEPTING THE ORDER
			try{

			//home page
			webDriver.get(properties.getProperty("HomeUrl"));
			loggerClass.debugTracker("Home Page loaded");

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='topNav']/div[3]/a/img")));
			WebElement logInButton=webDriver.findElement(By.xpath("//*[@id='topNav']/div[3]/a/img"));
			logInButton.click();
			
			//login page
			loggerClass.debugTracker("Provider loggin in for accepting the order");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='username']")));
			WebElement userNameTextBox=webDriver.findElement(By.xpath("//*[@id='username']"));
			userNameTextBox.clear();			
			userNameTextBox.sendKeys(properties.getProperty("providerName"));			
			WebElement passwordTextBox=webDriver.findElement(By.xpath("//*[@id='password']"));
			passwordTextBox.clear();
			passwordTextBox.sendKeys(properties.getProperty("password"));
			WebElement submitButton=webDriver.findElement(By.xpath("//*[@id='submit']"));
			submitButton.click();
			//dashboard
			webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			loggerClass.debugTracker("dashboard page loaded");
			
			//clicking on service order monitor page
			webDriver.findElement(By.xpath("//*[@id='serviceOrderMonitor']")).click();
			loggerClass.debugTracker("service order monitor link clicked");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a.modalCloseImg.modalClose")));
			Thread.sleep(2000);
			webDriver.findElement(By.cssSelector("a.modalCloseImg.modalClose")).click();
			Thread.sleep(1000);
			webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

			//waiting for search title to load
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(properties.getProperty("providerSOIdSearchTab"))));

			webDriver.findElement(By.xpath((properties.getProperty("providerSOIdSearchTab")))).click();
			loggerClass.debugTracker("search tab clicked");

			//searching
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchType")));
			
			loggerClass.debugTracker("search complete");
			
			String url2 = properties.getProperty("urlSOBidCompleteProviderPart1")+soId+properties.getProperty("urlSOBidCompleteProviderPart2");
			
			webDriver.get(url2);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ui-tabs-loading")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("10254")));
			Thread.sleep(5000);
			webDriver.findElement(By.id("10254")).click();
			Thread.sleep(3000);
			webDriver.findElement(By.id("acceptButton")).click();
			Thread.sleep(5000);
			boolean rescheduleButtonPresent = false;
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//form[@id='frmQuickLink']/img)[5]")));
			rescheduleButtonPresent = webDriver.findElements(By.xpath("(//form[@id='frmQuickLink']/img)[5]")).size()>0;
			
			if(rescheduleButtonPresent){
				loggerClass.debugTracker("SO Accepted succesfully");
				webDriver.findElement(By.linkText("Logout")).click();
			}else{
				loggerClass.debugTracker("Error Occurred::Not able to accept service order!!");
				result = "Error Occurred::Not able to accept service order!!";
				webDriver.close();
			}
			
		}catch(Exception e){
			e.printStackTrace();
			loggerClass.debugTracker("Error while accepting order. ERROR>> "+ e);
			result = "Error while accepting order. ERROR>> "+e+"@@"+soId;
			webDriver.close();
		}
		
			//BUYER PLACING RESCHEDULE REQUEST
			try{
				webDriver.get(properties.getProperty("HomeUrl"));
				loggerClass.debugTracker("Home Page loaded");

				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='topNav']/div[3]/a/img")));
				WebElement logInButton=webDriver.findElement(By.xpath("//*[@id='topNav']/div[3]/a/img"));
				logInButton.click();
				
				loggerClass.debugTracker("Buyer loggin in for placing reschedule request");
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='username']")));
				WebElement userNameTextBox=webDriver.findElement(By.xpath("//*[@id='username']"));
				userNameTextBox.clear();			
				userNameTextBox.sendKeys("jizqui3@searshc.com");			
				WebElement passwordTextBox=webDriver.findElement(By.xpath("//*[@id='password']"));
				passwordTextBox.clear();
				passwordTextBox.sendKeys(properties.getProperty("password"));
				WebElement submitButton=webDriver.findElement(By.xpath("//*[@id='submit']"));
				submitButton.click();
				
				//dashboard
				webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
				loggerClass.debugTracker("dashboard page loaded");
				
				//clicking on service order monitor page
				webDriver.findElement(By.xpath("//*[@id='serviceOrderMonitor']")).click();
				loggerClass.debugTracker("service order monitor link clicked");
				
				Thread.sleep(1000);
				webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

				//waiting for search title to load
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='page']/div[4]/div/div[7]/div/span")));

				String url2 = properties.getProperty("urlSOBidCompleteProviderPart1")+soId+properties.getProperty("urlSOBidCompleteProviderPart2");
				
				webDriver.get(url2);
				
				webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ui-tabs-loading")));
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//form[@id='frmQuickLink']/img)[5]")));
				webDriver.findElement(By.xpath("(//form[@id='frmQuickLink']/img)[5]")).click();
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id("rescheduleForm")));
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id("rescheduleReasonCode")));
				 new Select(webDriver.findElement(By.id("rescheduleReasonCode"))).selectByVisibleText("Customer: Reschedule Requested");
				
				 webDriver.findElement(By.xpath("//*[@id='rangeOfDates0']")).click();
				 webDriver.findElement(By.xpath("/html/body/div[26]/div/form/div[3]/div/div/table[3]/tbody/tr/td/table/tbody/tr/td/table/tbody/tr[2]/td/input[2]")).click();
				 	Thread.sleep(3000);
					wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/table/tbody/tr/td[3]/a[2]")));
				    webDriver.findElement(By.xpath("/html/body/div/table/tbody/tr/td[3]/a[2]")).click();
				    Thread.sleep(1000);
				    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/table/tbody/tr[5]/td[5]/a")));
				    webDriver.findElement(By.xpath("/html/body/div/table/tbody/tr[5]/td[5]/a")).click();
				    Thread.sleep(3000);

				    webDriver.findElement(By.id("rescheduleComments")).clear();
				    Thread.sleep(1000);
				    webDriver.findElement(By.id("rescheduleComments")).sendKeys("upoiuu");
				    Thread.sleep(1000);
				    webDriver.findElement(By.id("rescheduleSubmitBtn")).click();
				    Thread.sleep(50000);
				    boolean cancelButtonVisible=false;
				    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[2]/div/div[5]/div/div[2]/p/div/div[3]/div[2]/form[8]/input[2]"))); 		
				    cancelButtonVisible =  webDriver.findElements(By.xpath("/html/body/div[2]/div/div[5]/div/div[2]/p/div/div[3]/div[2]/form[8]/input[2]")).size()>0;
				    
				    if(cancelButtonVisible){
				    	loggerClass.debugTracker("Successfully placed reschedule request");
				    	webDriver.findElement(By.linkText("Logout")).click();
				    }else{
				    	loggerClass.debugTracker("Not able to place reschedule request");
				    	webDriver.close();
				    }
				    
			}catch(Exception e){
				e.printStackTrace();
				loggerClass.debugTracker("Error Occured while placing reschedule request by buyer. ERROR>> "+ e);
				result = "Error Occured while placing reschedule request by buyer. ERROR>> "+ e+"@@"+soId;
			}
			
			//PROVIDER ACCEPTING RESCHEDULE REQUEST
			
			try{
				webDriver.get(properties.getProperty("HomeUrl"));
				loggerClass.debugTracker("Home Page loaded");

				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='topNav']/div[3]/a/img")));
				WebElement logInButton=webDriver.findElement(By.xpath("//*[@id='topNav']/div[3]/a/img"));
				logInButton.click();
				
				loggerClass.debugTracker("Provider loggin in for accepting the reschedule request");
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='username']")));
				WebElement userNameTextBox=webDriver.findElement(By.xpath("//*[@id='username']"));
				userNameTextBox.clear();			
				userNameTextBox.sendKeys(properties.getProperty("providerName"));			
				WebElement passwordTextBox=webDriver.findElement(By.xpath("//*[@id='password']"));
				passwordTextBox.clear();
				passwordTextBox.sendKeys(properties.getProperty("password"));
				WebElement submitButton=webDriver.findElement(By.xpath("//*[@id='submit']"));
				submitButton.click();
				
				//dashboard
				webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
				loggerClass.debugTracker("dashboard page loaded");
				
				//clicking on service order monitor page
				webDriver.findElement(By.xpath("//*[@id='serviceOrderMonitor']")).click();
				loggerClass.debugTracker("service order monitor link clicked");
				wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a.modalCloseImg.modalClose")));
				webDriver.findElement(By.cssSelector("a.modalCloseImg.modalClose")).click();
				Thread.sleep(1000);
				webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

				//waiting for search title to load
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(properties.getProperty("providerSOIdSearchTab"))));

				webDriver.findElement(By.xpath((properties.getProperty("providerSOIdSearchTab")))).click();
				loggerClass.debugTracker("search tab clicked");

				//searching
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchType")));
				String url2 = properties.getProperty("urlSOBidCompleteProviderPart1")+soId+properties.getProperty("urlSOBidCompleteProviderPart2");
				
				webDriver.get(url2);
				webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//form[@id='foo']/input[2]")));
				webDriver.findElement(By.xpath("//form[@id='foo']/input[2]")).click();
				Thread.sleep(8000);
				loggerClass.debugTracker("Successfully accepted the reschedule request");
				//webDriver.close();
			}catch(Exception e){
				e.printStackTrace();
				loggerClass.debugTracker("Error occured while accepting the reschedule request. ERROR >> "+ e);
				result = "Error occured while accepting the reschedule request. ERROR >> "+ e+"@@"+soId;
			}
			
			//PROVIDER ACTIVATING THE SERVICE ORDER
			try{
				//wait for time on site title to load
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ui-tabs-loading")));
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(properties.getProperty("timeOnSiteProviderComplete"))));
				webDriver.findElement(By.xpath(properties.getProperty("timeOnSiteProviderComplete"))).click();
				loggerClass.debugTracker("time on site link clicked");
				Thread.sleep(2000);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='AddEditDeleteButton']/input[2]")));
				webDriver.findElement(By.xpath("//*[@id='AddEditDeleteButton']/input[2]")).click();

				loggerClass.debugTracker("add entries button clicked");

				//waiting for arrival date text box to load
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='arrivalDate']")));

				//loading date manually by reading the date on site
				webDriver.findElement(By.xpath("//*[@id='arrivalDate']")).clear();
				webDriver.findElement(By.xpath("//*[@id='arrivalDate']")).sendKeys("12/02/2013");

				String dateDashboard=webDriver.findElement(By.xpath("//*[@id='date_dashboard']")).getText();

				loggerClass.debugTracker(dateDashboard);

				String[] dateParts=dateDashboard.split(" ");
				
				for(int i=0;i<dateParts.length;i++){
					loggerClass.debugTracker(dateParts[i]);
				}
				
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
				Thread.sleep(8000);

				loggerClass.debugTracker("time on site complete");
			}catch(Exception e){
				e.printStackTrace();
				loggerClass.debugTracker("Error occured while adding time on site. ERROR >>"+ e);
				result = "Error occured while adding time on site. ERROR >>"+ e+"@@"+soId;
			}
			
			//PROVIDER REPORTING PROBLEM
			try{
				/*webDriver.get(properties.getProperty("HomeUrl"));
				loggerClass.debugTracker("Home Page loaded");

				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='topNav']/div[3]/a/img")));
				WebElement logInButton=webDriver.findElement(By.xpath("//*[@id='topNav']/div[3]/a/img"));
				logInButton.click();
				
				loggerClass.debugTracker("Provider loggin in for accepting the reschedule request");
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='username']")));
				WebElement userNameTextBox=webDriver.findElement(By.xpath("//*[@id='username']"));
				userNameTextBox.clear();			
				userNameTextBox.sendKeys(properties.getProperty("providerName"));			
				WebElement passwordTextBox=webDriver.findElement(By.xpath("//*[@id='password']"));
				passwordTextBox.clear();
				passwordTextBox.sendKeys(properties.getProperty("password"));
				WebElement submitButton=webDriver.findElement(By.xpath("//*[@id='submit']"));
				submitButton.click();
				
				//dashboard
				webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
				loggerClass.debugTracker("dashboard page loaded");
				
				//clicking on service order monitor page
				webDriver.findElement(By.xpath("//*[@id='serviceOrderMonitor']")).click();
				loggerClass.debugTracker("service order monitor link clicked");
				wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a.modalCloseImg.modalClose")));
				webDriver.findElement(By.cssSelector("a.modalCloseImg.modalClose")).click();
				Thread.sleep(1000);
				webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

				//waiting for search title to load
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(properties.getProperty("providerSOIdSearchTab"))));

				webDriver.findElement(By.xpath((properties.getProperty("providerSOIdSearchTab")))).click();
				loggerClass.debugTracker("search tab clicked");

				//searching
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchType")));
				String url2 = properties.getProperty("urlSOBidCompleteProviderPart1")+soId+properties.getProperty("urlSOBidCompleteProviderPart2");
				
				webDriver.get(url2);
				webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);
				*/
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ui-tabs-loading")));
				webDriver.findElement(By.xpath("/html/body/div[2]/div/div[4]/ul/li[6]/a")).click();	
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//form[@id='frmReportProblem']/div[2]/p[2]/select")));
			
			new Select(webDriver.findElement(By.xpath("//form[@id='frmReportProblem']/div[2]/p[2]/select"))).selectByVisibleText("Additional Part Required");
			webDriver.findElement(By.xpath("//form[@id='frmReportProblem']/div[2]/p[3]/textarea")).clear();
			webDriver.findElement(By.xpath("//form[@id='frmReportProblem']/div[2]/p[3]/textarea")).sendKeys("sgfdgdgdd");
			webDriver.findElement(By.xpath("//form[@id='frmReportProblem']/div[3]/div/input")).click();
			
			webDriver.findElement(By.linkText("Logout")).click();
			}catch(Exception e){
				e.printStackTrace();
				loggerClass.debugTracker("Error occured while Reporting Problem. ERROR >>"+ e);
				result = "Error occured while Reporting Problem. ERROR >>"+ e+"@@"+soId;
			}
			
			//BUYER RESOLVING PROBLEM
			try{
				webDriver.get(properties.getProperty("HomeUrl"));
				loggerClass.debugTracker("Home Page loaded");

				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='topNav']/div[3]/a/img")));
				WebElement logInButton=webDriver.findElement(By.xpath("//*[@id='topNav']/div[3]/a/img"));
				logInButton.click();
				
				
				loggerClass.debugTracker("Buyer loggin in for resolving the problem");
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='username']")));
				webDriver.findElement(By.xpath("//*[@id='username']")).clear();

				webDriver.findElement(By.xpath("//*[@id='username']")).sendKeys("jizqui3@searshc.com");
				
				
				webDriver.findElement(By.xpath("//*[@id='password']")).clear();
				webDriver.findElement(By.xpath("//*[@id='password']")).sendKeys("Test123!");

				webDriver.findElement(By.xpath("//*[@id='submit']")).submit();
				
				//dashboard
				webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
				loggerClass.debugTracker("dashboard page loaded");
				
				//clicking on service order monitor page
				webDriver.findElement(By.xpath("//*[@id='serviceOrderMonitor']")).click();
				loggerClass.debugTracker("service order monitor link clicked");
				Thread.sleep(1000);
				webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

				//waiting for search title to load
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='page']/div[4]/div/div[7]/div/span")));

			
				loggerClass.debugTracker("search complete");
				String url2 = properties.getProperty("urlSOBidCompleteProviderPart1")+soId+properties.getProperty("urlSOBidCompleteProviderPart2");
				
				webDriver.get(url2);
				webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);
				
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ui-tabs-loading")));
				
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='tabs']/ul/li[7]/a")));
				webDriver.findElement(By.xpath("//div[@id='tabs']/ul/li[7]/a")).click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ui-tabs-loading")));
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='resComment']")));
				webDriver.findElement(By.xpath("//*[@id='resComment']")).clear();
				webDriver.findElement(By.xpath("//*[@id='resComment']")).sendKeys("Resolving...");
				webDriver.findElement(By.xpath("/html/body/div[2]/div/div[11]/div/form/div/div[2]/div/div[2]/table/tbody/tr[5]/td/div/img")).click();
				webDriver.findElement(By.linkText("Logout")).click();
			}catch(Exception e){
				e.printStackTrace();
				loggerClass.debugTracker("Error occured while Resolving Problem. ERROR >>"+ e);
				result = "Error occured while Resolving Problem. ERROR >>"+ e+"@@"+soId;
			}
			
			//PROVIDER ACTIVATING THE ORDER
			try{
				webDriver.get(properties.getProperty("HomeUrl"));
				loggerClass.debugTracker("Home Page loaded");

				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='topNav']/div[3]/a/img")));
				WebElement logInButton=webDriver.findElement(By.xpath("//*[@id='topNav']/div[3]/a/img"));
				logInButton.click();
				
				loggerClass.debugTracker("Provider loggin in for accepting the reschedule request");
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='username']")));
				WebElement userNameTextBox=webDriver.findElement(By.xpath("//*[@id='username']"));
				userNameTextBox.clear();			
				userNameTextBox.sendKeys(properties.getProperty("providerName"));			
				WebElement passwordTextBox=webDriver.findElement(By.xpath("//*[@id='password']"));
				passwordTextBox.clear();
				passwordTextBox.sendKeys(properties.getProperty("password"));
				WebElement submitButton=webDriver.findElement(By.xpath("//*[@id='submit']"));
				submitButton.click();
				
				//dashboard
				webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
				loggerClass.debugTracker("dashboard page loaded");
				
				//clicking on service order monitor page
				webDriver.findElement(By.xpath("//*[@id='serviceOrderMonitor']")).click();
				loggerClass.debugTracker("service order monitor link clicked");
				wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a.modalCloseImg.modalClose")));
				webDriver.findElement(By.cssSelector("a.modalCloseImg.modalClose")).click();
				Thread.sleep(1000);
				webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

				//waiting for search title to load
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(properties.getProperty("providerSOIdSearchTab"))));

				webDriver.findElement(By.xpath((properties.getProperty("providerSOIdSearchTab")))).click();
				loggerClass.debugTracker("search tab clicked");

				//searching
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchType")));
				Thread.sleep(3000);
				String url2 = properties.getProperty("urlSOBidCompleteProviderPart1")+soId+properties.getProperty("urlSOBidCompleteProviderPart2");
				
				webDriver.get(url2);
				webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);
				
				//wait for time on site title to load
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ui-tabs-loading")));
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(properties.getProperty("timeOnSiteProviderComplete"))));
				webDriver.findElement(By.xpath(properties.getProperty("timeOnSiteProviderComplete"))).click();
				loggerClass.debugTracker("time on site link clicked");
				Thread.sleep(2000);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='AddEditDeleteButton']/input[2]")));
				webDriver.findElement(By.xpath("//*[@id='AddEditDeleteButton']/input[2]")).click();

				loggerClass.debugTracker("add entries button clicked");

				//waiting for arrival date text box to load
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='arrivalDate']")));

				//loading date manually by reading the date on site
				webDriver.findElement(By.xpath("//*[@id='arrivalDate']")).clear();
				webDriver.findElement(By.xpath("//*[@id='arrivalDate']")).sendKeys("12/02/2013");

				String dateDashboard=webDriver.findElement(By.xpath("//*[@id='date_dashboard']")).getText();

				loggerClass.debugTracker(dateDashboard);

				String[] dateParts=dateDashboard.split(" ");
				
				for(int i=0;i<dateParts.length;i++){
					loggerClass.debugTracker(dateParts[i]);
				}
				
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
				Thread.sleep(8000);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ui-tabs-loading")));
				loggerClass.debugTracker("time on site complete");
			}catch(Exception e){
				e.printStackTrace();
				loggerClass.debugTracker("Error occured while entering time on site. ERROR >>"+ e);
				result = "Error occured while entering time on site. ERROR >>"+ e+"@@"+soId;
			}
			
			//PROVIDER COMPLETING THE ORDER
			try{
				/*webDriver.get(properties.getProperty("HomeUrl"));
				loggerClass.debugTracker("Home Page loaded");

				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='topNav']/div[3]/a/img")));
				WebElement logInButton=webDriver.findElement(By.xpath("//*[@id='topNav']/div[3]/a/img"));
				logInButton.click();
				
				loggerClass.debugTracker("Provider loggin in for accepting the reschedule request");
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='username']")));
				WebElement userNameTextBox=webDriver.findElement(By.xpath("//*[@id='username']"));
				userNameTextBox.clear();			
				userNameTextBox.sendKeys(properties.getProperty("providerName"));			
				WebElement passwordTextBox=webDriver.findElement(By.xpath("//*[@id='password']"));
				passwordTextBox.clear();
				passwordTextBox.sendKeys(properties.getProperty("password"));
				WebElement submitButton=webDriver.findElement(By.xpath("//*[@id='submit']"));
				submitButton.click();
				
				//dashboard
				webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
				loggerClass.debugTracker("dashboard page loaded");
				
				//clicking on service order monitor page
				webDriver.findElement(By.xpath("//*[@id='serviceOrderMonitor']")).click();
				loggerClass.debugTracker("service order monitor link clicked");
				wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a.modalCloseImg.modalClose")));
				Thread.sleep(1000);
				webDriver.findElement(By.cssSelector("a.modalCloseImg.modalClose")).click();
				
				webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

				//waiting for search title to load
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(properties.getProperty("providerSOIdSearchTab"))));

				webDriver.findElement(By.xpath((properties.getProperty("providerSOIdSearchTab")))).click();
				loggerClass.debugTracker("search tab clicked");

				//searching
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchType")));
 				String url2 = properties.getProperty("urlSOBidCompleteProviderPart1")+soId+properties.getProperty("urlSOBidCompleteProviderPart2");
				
				webDriver.get(url2);*/
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ui-tabs-loading")));
				webDriver.findElement(By.linkText("Complete for Payment")).click();

				//waiting for page load
				webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);

				loggerClass.debugTracker("complete for payment clicked");

				//waiting for invisibility of 'Loading..' tab
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ui-tabs-loading")));

				//Thread.sleep(12000);
				
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id("servicecompletion")));
				webDriver.findElement(By.xpath("/html/body/div[27]/div/div/form/div[4]/input")).click();

				webDriver.findElement(By.id("resComments")).clear();
				webDriver.findElement(By.id("resComments")).sendKeys("Resolution Comments");
				webDriver.findElement(By.id("soMaxLabor")).click();
				webDriver.findElement(By.id("soMaxLabor")).clear();
				webDriver.findElement(By.id("soMaxLabor")).sendKeys(Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,"50.00");

				Integer size =  webDriver.findElements(By.id("referenceValue")).size();
				loggerClass.debugTracker("size" + size);
	            for(int i = 0;i<size;i++){
	            	webDriver.findElement(By.name("buyerRefs["+i+"].referenceValue")).sendKeys("1234");
	            }

				//giving 15 sec wait for manually attaching the document
	            
	            webDriver.switchTo().frame(webDriver.findElement(By.xpath("//*[@id='inner_document_grid']")));
	           List<WebElement> lists=  webDriver.findElements(By.xpath("//*")); 
	           Thread.sleep(1000);
	           
	           wait.until(ExpectedConditions.presenceOfElementLocated(By.id("document.upload")));
	           webDriver.findElement(By.id("browseButton")).click();
				webDriver.findElement(By.id("document.upload")).sendKeys(properties.getProperty("docFileLocn"));
				new Select(webDriver.findElement(By.name("docTitle"))).selectByVisibleText("Signed Customer Copy Including Waiver of Lien");
				webDriver.findElement(By.xpath("//*[@id='attachDocumentBtn']")).click();
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id("fileUploadSuccess")));
				webDriver.switchTo().defaultContent();
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='submitDiv']/img")));
				webDriver.findElement(By.xpath("//*[@id='submitDiv']/img")).click();

				//wait for page load
				webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);
				//waiting for summary div to load
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='ui-tabs-3']/div")));

				loggerClass.debugTracker("successfully completed the service order for payment.");

				loggerClass.debugTracker("Service order Id: "+soId);
				//waiting for logout to load
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='topNav']/div/a[6]")));
				Thread.sleep(3000);
				webDriver.findElement(By.linkText("Logout")).click();

				loggerClass.debugTracker("provider logged out after completing the service order");
				
			}catch(Exception e){
				e.printStackTrace();
				loggerClass.debugTracker("Error occured while completing the order. ERROR>> "+ e);
				result = "Error occured while completing the order. ERROR>> "+ e+"@@"+soId;
			}
			
			//BUYER CLOSING THE ORDER
			
			try{
				webDriver.get(properties.getProperty("HomeUrl"));
				loggerClass.debugTracker("Home Page loaded");

				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='topNav']/div[3]/a/img")));
				WebElement logInButton=webDriver.findElement(By.xpath("//*[@id='topNav']/div[3]/a/img"));
				logInButton.click();
				
				loggerClass.debugTracker("Provider loggin in for accepting the reschedule request");
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='username']")));
				WebElement userNameTextBox=webDriver.findElement(By.xpath("//*[@id='username']"));
				userNameTextBox.clear();			
				userNameTextBox.sendKeys(properties.getProperty("providerName"));			
				WebElement passwordTextBox=webDriver.findElement(By.xpath("//*[@id='password']"));
				passwordTextBox.clear();
				passwordTextBox.sendKeys(properties.getProperty("password"));
				WebElement submitButton=webDriver.findElement(By.xpath("//*[@id='submit']"));
				submitButton.click();
				
				//dashboard
				webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
				loggerClass.debugTracker("dashboard page loaded");
				
				//clicking on service order monitor page
				webDriver.findElement(By.xpath("//*[@id='serviceOrderMonitor']")).click();
				loggerClass.debugTracker("service order monitor link clicked");
				wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a.modalCloseImg.modalClose")));
				Thread.sleep(1000);
				webDriver.findElement(By.cssSelector("a.modalCloseImg.modalClose")).click();
				Thread.sleep(1000);
				webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

				//waiting for search title to load
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(properties.getProperty("providerSOIdSearchTab"))));

				webDriver.findElement(By.xpath((properties.getProperty("providerSOIdSearchTab")))).click();
				loggerClass.debugTracker("search tab clicked");

				//searching
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchType")));
				String url2 = properties.getProperty("urlSOBidCompleteProviderPart1")+soId+properties.getProperty("urlSOBidCompleteProviderPart2");
				
				webDriver.get(url2);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='topNav']/div[3]/a/img")));
				webDriver.findElement(By.xpath("//*[@id='topNav']/div[3]/a/img")).click();

				loggerClass.debugTracker("Buyer logging in for closing the service order");
				//login page
				//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='username']")));
				webDriver.findElement(By.xpath("//*[@id='username']")).clear();

				webDriver.findElement(By.xpath("//*[@id='username']")).sendKeys("jizqui3@searshc.com");
				
				
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

				loggerClass.debugTracker("search complete");

				String url3=properties.getProperty("urlSOBidCloseBuyerPart1")+soId+properties.getProperty("urlSOBidCloseBuyerPart2");

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

				loggerClass.debugTracker("Buyer logged out after closing the order #: "+soId);

				loggerClass.debugTracker("**************************************************************");

				loggerClass.debugTracker("closing...");
				loggerClass.debugTracker("^^^^^^^^^^^^^^^^ENDS^^^^^^^^^^^^^^^^");
				loggerClass.debugTracker("                                    ");
				webDriver.close();
				result = "Successfully closed the SO@@"+soId;
			}
			catch(Exception e){

				loggerClass.debugTracker("Exception :"+ e.getMessage());
				loggerClass.debugTracker("^^^^^^^^^^^^^^^^ENDS^^^^^^^^^^^^^^^^");
				loggerClass.debugTracker("                                    ");
				e.printStackTrace();
				result = "Error while closing order. ERROR >>" + e+"@@"+soId;
			}finally{
				webDriver.close();
			}
			return result;
	}
	
	
	


}
