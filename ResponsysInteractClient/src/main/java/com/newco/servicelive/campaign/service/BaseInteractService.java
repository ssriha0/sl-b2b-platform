/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-Aug-2015	KMSLTVSZ     Infosys		   1.0
 */
package com.newco.servicelive.campaign.service;

import java.net.SocketTimeoutException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.rsys.ws.Login;
import com.rsys.ws.LoginResponse;
import com.rsys.ws.LoginResult;
import com.rsys.ws.Logout;
import com.rsys.ws.LogoutResponse;
import com.rsys.ws.SessionHeader;
import com.rsys.ws.client.AccountFault;
import com.rsys.ws.client.ResponsysWSServiceStub;
import com.rsys.ws.client.UnexpectedErrorFault;
import com.rsys.ws.fault.ExceptionCode;

/**
 * Base Service class for Responsys Interact Client API
 * SL-20653 Responsys Upgrade
 * @author Infosys
 * @version 1.0
 */
public class BaseInteractService {

	private static final Logger logger = Logger.getLogger(BaseInteractService.class.getName());
	private ResponsysWSServiceStub stubResponsysInteract;
	private SessionHeader sessionHeader;
	private Properties alertServicePropertiesResponsys;
	public static final String EMAIL = "EMAIL";
	public static final String EMAIL_TEST = "_test";
	


	/**
	 * For Responsys Login
	 * 
	 * @param
	 * @return
	 * @throws UnexpectedErrorFault 
	 * @throws AccountFault 
	 */
	protected boolean loginResponsysInteract() {
		
		String responsysURL = getProperty("responsysInteractURL");
		String userName = getProperty("responsysInteractUsername");
		String password = getProperty("responsysInteractPassword");
		
		boolean result = false;
		Login login = new Login();
		login.setUsername(userName);
		login.setPassword(password);
		LoginResponse response = null;
		try {
			stubResponsysInteract = new ResponsysWSServiceStub(responsysURL);
			stubResponsysInteract._getServiceClient().getOptions().setManageSession(true);			
			logger.info("Start Calling BaseInteractService.java loginResponsysInteract() -- username - "+userName);

			logger.info("Start Calling BaseInteractService.java -- stubResponsysInteract.login");
			response = stubResponsysInteract.login(login);
			logger.info("End Calling BaseInteractService.java -- stubResponsysInteract.login");
			
			LoginResult loginResult = response.getResult();
			String sessionId = loginResult.getSessionId();
			logger.info("Session id   =    "+sessionId);	
			if (sessionId != null) {
				sessionHeader = new SessionHeader();
				sessionHeader.setSessionId(sessionId);
				result = true;
			}
		} catch (AccountFault accountEx) {
			logger.error("Inside BaseInteractService:First AccountFault:Exception Code accountEx= " + accountEx.getFaultMessage().getExceptionCode());
			logger.error("Inside BaseInteractService:First AccountFault:Exception Msg accountEx= " + accountEx.getFaultMessage().getExceptionMessage());
			accountEx.printStackTrace();
			//TODO logic based on exception code SERVER_SHUTDOWN,TEMPORARILY_UNAVAILABLE refer RTM service
			ExceptionCode errorCode = accountEx.getFaultMessage().getExceptionCode();
			logger.info("Inside BaseInteractService:First AccountFault: Exception Code : "+errorCode);
			if (errorCode == ExceptionCode.SERVER_SHUTDOWN 
					|| errorCode == ExceptionCode.TEMPORARILY_UNAVAILABLE 
					||errorCode == ExceptionCode.SERVICE_UNAVAILABLE ) {
				// Wait for some time and try login again.
				// Try until login is successful.
				boolean retry = true;
				while (retry) {
					logger.info("Inside retry loop...");
				try {
						Thread.sleep(30000);
						response = stubResponsysInteract.login(login);
						retry = false;
					} catch (AccountFault acctFault) {
						logger.error("Inside BaseInteractService:Second AccountFault:Exception Code accountEx= " + accountEx.getFaultMessage().getExceptionCode());
						logger.error("Inside BaseInteractService:Second AccountFault:Exception Msg accountEx= " + accountEx.getFaultMessage().getExceptionMessage());
						errorCode = acctFault.getFaultMessage().getExceptionCode();
						if (errorCode == ExceptionCode.SERVER_SHUTDOWN 
								|| errorCode == ExceptionCode.TEMPORARILY_UNAVAILABLE 
								||errorCode == ExceptionCode.SERVICE_UNAVAILABLE ) {
							retry = true;
						} else {
							retry = false;
						}
					 } catch (Exception ex) {
						retry = false;
					 }
				}
			} 
		} catch (UnexpectedErrorFault unexpectedEx) {
			logger.error("Inside BaseInteractService:UnexpectedErrorFault block-Exception Code unexpectedEx= " + unexpectedEx.getFaultMessage().getExceptionCode());
			logger.error("Inside BaseInteractService:UnexpectedErrorFault block-Exception Msg unexpectedEx= " + unexpectedEx.getFaultMessage().getExceptionMessage());
			unexpectedEx.printStackTrace();
		} catch (Exception ex) {
			logger.error("Inside BaseInteractService:Exception block - Severe Exception Msg: ex" + ex.getMessage());
			logger.error("Inside BaseInteractService:Exception block - Stack Trace: " + ex);
			ex.printStackTrace();
			
			if(ex instanceof SocketTimeoutException){
				logger.error("Inside BaseInteractService:First Exception block - SocketTimeoutException occured while login... ");
				boolean retry = true;
				while (retry) {
					try {
						Thread.sleep(30000);
						response = stubResponsysInteract.login(login);
						retry = false;
					} catch (Exception ex1) {
						if(ex1 instanceof SocketTimeoutException){
							logger.error("Inside BaseInteractService:Second Exception block - SocketTimeoutException occured while login... ");
							retry = true;
						}else{
							retry = false;
						}
					}
				}
			}
		}
		logger.info(" BaseInteractService.java loginResponsysInteract() returning login result: "+result);		
		return result;
	}


	/**
	 * This method logs out of Responsys
	 * 
	 * @param
	 * @return
	 */
	protected void logoutResponsysInteract() {
		try {
			logger.info("Start Calling BaseInteractService.java logoutResponsysInteract()");
			Logout logout = new Logout();
			LogoutResponse logoutResponse = stubResponsysInteract.logout(logout, sessionHeader);
			boolean loggedOut = logoutResponse.getResult();
			logger.info("Logout Result = " + loggedOut);
		}catch (UnexpectedErrorFault unexpectedEx) {
			logger.error("Exception Code unexpectedEx= " + unexpectedEx.getFaultMessage().getExceptionCode());
			logger.error("Exception Msg unexpectedEx= " + unexpectedEx.getFaultMessage().getExceptionMessage());
			unexpectedEx.printStackTrace();
		} catch (Exception ex) {
			logger.error("Severe Exception Msg ex= " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	protected String getDecoratedCampaignName(String name){	
		return isTestMode()?name.concat(EMAIL_TEST):name;
	}

	private boolean isTestMode() {
		return Boolean.valueOf(alertServicePropertiesResponsys.getProperty("responsysInteractTestMode"));
	}
	
	public ResponsysWSServiceStub getStubResponsysInteract() {
		return stubResponsysInteract;
	}

	public void setStubResponsysInteract(
			ResponsysWSServiceStub stubResponsysInteract) {
		this.stubResponsysInteract = stubResponsysInteract;
	}

	public SessionHeader getSessionHeader() {
		return sessionHeader;
	}

	public void setSessionHeader(SessionHeader sessionHeader) {
		this.sessionHeader = sessionHeader;
	}


	public Properties getAlertServicePropertiesResponsys() {
		return alertServicePropertiesResponsys;
	}


	public void setAlertServicePropertiesResponsys(
			Properties alertServicePropertiesResponsys) {
		this.alertServicePropertiesResponsys = alertServicePropertiesResponsys;
	}


	protected String getProperty(final String key){
		return alertServicePropertiesResponsys.getProperty(key);
	}
	
}
