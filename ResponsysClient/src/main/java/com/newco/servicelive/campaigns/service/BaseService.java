package com.newco.servicelive.campaigns.service;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Properties;

import org.apache.log4j.Logger;

import responsys.ws57.Login;
import responsys.ws57.LoginResponse;
import responsys.ws57.Logout;
import responsys.ws57.LogoutResponse;
import responsys.ws57.SessionHeader;
import responsys.ws57.client.RIFault;
import responsys.ws57.client.ResponsysWS57Stub;

import com.rsys.tmws.LoginResult;
import com.rsys.tmws.client.AccountFault;
import com.rsys.tmws.client.TriggeredMessageWSStub;
import com.rsys.tmws.client.UnexpectedErrorFault;
import com.rsys.tmws.fault.ExceptionCode;

public class BaseService {

	private static final Logger logger = Logger.getLogger(BaseService.class.getName());
	private ResponsysWS57Stub stubWS;	
	private TriggeredMessageWSStub stubRTM;
	private SessionHeader sessionHeaderWS;
	private com.rsys.tmws.SessionHeader sessionHeaderRTM;
	private Properties alertServiceProperties;
	// Moving both the values for RTM_URL & RESPONSYS_BATCH_URL into configuration file local.properties.
	// public static final String RTM_URL = "http://rtm4.responsys.net:80/tmws/services/TriggeredMessageWS";
	// public static final String RESPONSYS_BATCH_URL = "https://ws4.responsys.net/webservices57/services/ResponsysWS57";
	public static final String EMAIL = "EMAIL";
	public static final String EMAIL_TEST = "_test";
	public static int POLLING_RETRY_LIMIT = 3;
	public static final String DELIMITER = "COMMA";
	public static final String ENCLOSING_SYMBOL = "DOUBLE_QUOTE";
	public static final String TIMESTAMP_COLUMN_NAME = "TIMESTAMP_";
	public static final String START_TIME = "01/01/2010 12:00 am";
	public static final String DATE_FORMAT = "MM/dd/yyyy hh:mm aa";
	
		/**
	 * For Responsys Login
	 * @return boolean
	 */
	protected boolean loginWS()
			throws RIFault, RemoteException {
		String sessionId = null;
		logger.info("About to log-in to Responsys");
		// login
		// stubWS = new ResponsysWS57Stub(RESPONSYS_BATCH_URL);		
		stubWS = new ResponsysWS57Stub(getResponsysBatchURL());
		Login login = new Login();
		login.setUsername(getUsername());
		login.setPassword(getPassword());
		LoginResponse response = stubWS.login(login);
		sessionId = response.getResult().getSessionId();

		if (sessionId != null) {
			sessionHeaderWS = new SessionHeader();
			sessionHeaderWS.setSessionId(sessionId);

			// maintain session between requests
			stubWS._getServiceClient().getOptions().setManageSession(true);
			stubWS._getServiceClient().getOptions().setTimeOutInMilliSeconds(
					1000 * 60 * 60);

			logger.info("Log-in to Responsys was successful");
		}

		return sessionHeaderWS != null;
	}

	/**
	 * For Responsys Logout
	 * 
	 * @return boolean
	 */
	protected boolean logoutWS() throws RIFault, RemoteException {
		LogoutResponse logoutResponse = stubWS.logout(new Logout(), sessionHeaderWS);

		if (logoutResponse.getResult()) {
			logger.info("Logged out");
		}

		return logoutResponse.getResult();
	}


	/**
	 * For Responsys Login
	 * 
	 * @param
	 * @return
	 */
	protected boolean loginRTM() {

		boolean result = false;
		try {

			// stubRTM = new TriggeredMessageWSStub(RTM_URL);
			stubRTM = new TriggeredMessageWSStub(getResponsysURL());
			stubRTM._getServiceClient().getOptions().setManageSession(true);			
			com.rsys.tmws.Login login = new com.rsys.tmws.Login();
			login.setUsername(getUsername());
			login.setPassword(getPassword());
			com.rsys.tmws.LoginResponse response = null;
logger.info("Start Calling BaseService.java -- username - "+getUsername()+"----"+getPassword());
			try {
logger.info("Start Calling BaseService.java -- stubRTM.login");
				response = stubRTM.login(login);
logger.info("End Calling BaseService.java -- stubRTM.login");
			} catch (AccountFault accountFault) {
				ExceptionCode errorCode = accountFault.getFaultMessage()
						.getErrorCode();
logger.info(" BaseService.java accountFault error2  "+errorCode);
				if (errorCode == ExceptionCode.SERVER_SHUTDOWN) {
					/*
					 * Wait for some time and try login again .Only in case of
					 * login. In case of other methods . logout & login again so
					 * that you can connect to other servers
					 */
					Thread.sleep(30000);
					response = stubRTM.login(login);
				} else if (errorCode == ExceptionCode.TEMPORARILY_UNAVAILABLE) {
					// Wait for some time and try login again.
					// Try until login is successful.
					boolean retry = true;
					while (retry) {
						try {
							Thread.sleep(30000);
							response = stubRTM.login(login);
							retry = false;
						} catch (AccountFault acctFault) {
							errorCode = acctFault.getFaultMessage()
									.getErrorCode();
							if (errorCode == ExceptionCode.TEMPORARILY_UNAVAILABLE) {
								retry = true;
							} else {
								retry = false;
							}
						}
					}
				} else {
logger.info(" BaseService.java inside else part ");
					accountFault.printStackTrace();
					throw accountFault;
				}
			}
			LoginResult loginResult = response.getResult();
			String sessionId = loginResult.getSessionId();
logger.info("Session id   =    "+sessionId);	
			if (sessionId != null) {
				sessionHeaderRTM = new com.rsys.tmws.SessionHeader();
				sessionHeaderRTM.setSessionId(sessionId);
				result = true;
			}
		} catch (AccountFault accountEx) {
			logger.error("Exception Code = "
					+ accountEx.getFaultMessage().getErrorCode());
			logger.error("Exception Msg = "
					+ accountEx.getFaultMessage().getErrorMessage());
		} catch (UnexpectedErrorFault unexpectedEx) {
			logger.error("Exception Code = "
					+ unexpectedEx.getFaultMessage().getErrorCode());
			logger.error("Exception Msg = "
					+ unexpectedEx.getFaultMessage().getErrorMessage());
		} catch (Exception ex) {
			logger.error("Exception Msg: " + ex.getMessage());
			logger.error("Exception Stack Trace: " + ex);
		}
logger.info(" BaseService.java returning result ");		
		return result;
	}


	/**
	 * This method logs out of Responsys
	 * 
	 * @param
	 * @return
	 */
	protected void logoutRTM() {
		try {
			com.rsys.tmws.Logout logout = new com.rsys.tmws.Logout();
			com.rsys.tmws.LogoutResponse logoutResponse = stubRTM.logout(logout, sessionHeaderRTM);
			boolean loggedOut = logoutResponse.getResult();
			logger.info("Logout Result = " + loggedOut);
		} catch (AccountFault accountEx) {
			logger.error("Exception Code = "
					+ accountEx.getFaultMessage().getErrorCode());
			logger.error("Exception Msg = "
					+ accountEx.getFaultMessage().getErrorMessage());
		} catch (UnexpectedErrorFault unexpectedEx) {
			logger.error("Exception Code = "
					+ unexpectedEx.getFaultMessage().getErrorCode());
			logger.error("Exception Msg = "
					+ unexpectedEx.getFaultMessage().getErrorMessage());
		} catch (Exception ex) {
			logger.error("Exception Msg = " + ex.getMessage());
		}
	}
	
	protected void writeToFile(InputStream is, File file) {
		try {
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			int c;
			while((c = is.read()) != -1) {
				out.writeByte(c);
			}
			is.close();
			out.close();
		}
		catch(IOException e) {
			logger.error("Error Writing/Reading Streams.");
		}
	}

	protected String getDecoratedCampaignName(String name){	
		return isTestMode()?name.concat(EMAIL_TEST):name;
	}
		
	protected ResponsysWS57Stub getStubWS() {
		return stubWS;
	}

	protected void setStubWS(ResponsysWS57Stub stubWS) {
		this.stubWS = stubWS;
	}

	protected TriggeredMessageWSStub getStubRTM() {
		return stubRTM;
	}

	protected void setStubRTM(TriggeredMessageWSStub stubRTM) {
		this.stubRTM = stubRTM;
	}

	protected SessionHeader getSessionHeaderWS() {
		return sessionHeaderWS;
	}

	protected void setSessionHeaderWS(SessionHeader sessionHeaderWS) {
		this.sessionHeaderWS = sessionHeaderWS;
	}

	protected com.rsys.tmws.SessionHeader getSessionHeaderRTM() {
		return sessionHeaderRTM;
	}

	protected void setSessionHeaderRTM(com.rsys.tmws.SessionHeader sessionHeaderRTM) {
		this.sessionHeaderRTM = sessionHeaderRTM;
	}

	protected String getUsername() {
		return alertServiceProperties.getProperty("username");
	}

	protected String getPassword() {
		return alertServiceProperties.getProperty("password");
	}

	protected String getTmpDSLocation() {
		return alertServiceProperties.getProperty("tmpDSLocation");
	}
	protected String getResponsysURL() {
		return alertServiceProperties.getProperty("responsysURL");
	}
	protected String getResponsysBatchURL() {
		return alertServiceProperties.getProperty("responsysBatchURL");
	}

	private boolean isTestMode() {
		return Boolean.valueOf(alertServiceProperties.getProperty("testMode"));
	}
	
	protected Properties getAlertServiceProperties() {
		return alertServiceProperties;
	}

	public void setAlertServiceProperties(Properties alertServiceProperties) {
		this.alertServiceProperties = alertServiceProperties;
	}

}
