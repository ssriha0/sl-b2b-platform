/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-May-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */

package com.newco.marketplace.api.provider.utils.validators;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.security.LoggingProcess;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;

/**
 * This class would act as the validator class for response xml with xsd.
 * 
 * @author Infosys
 * @version 1.0
 */
public class ResponseValidator {
	private Logger logger = Logger.getLogger(ResponseValidator.class);
	private LoggingProcess loggingProcess;
	private XStreamUtility conversionUtility;
		
	
	/**
	 * triggers the api response logging process
	 * 
	 * @param strResponseXml :
	 *            output response xml
	 */
	public void logResponse(String strResponseXml,int logId) {
		loggingProcess.logResponse(strResponseXml,logId);
	}
	
	public void setLoggingProcess(LoggingProcess loggingProcess) {
		this.loggingProcess = loggingProcess;
	}
	
	public void setConversionUtility(XStreamUtility conversionUtility) {
		this.conversionUtility = conversionUtility;
	}
	
}
