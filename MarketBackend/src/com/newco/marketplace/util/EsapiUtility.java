/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Apr-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.util;

import org.apache.log4j.Logger;
import org.owasp.esapi.ESAPI;

 
/**
 * This class would act as an Utility Function for ESAPI encoding.
 * 
 * @author Infosys
 * @version 1.0
 */
public class EsapiUtility{
	private static Logger logger = Logger.getLogger(EsapiUtility.class);

	/**
	 * This method is for encoding the string value of APIs.
	 * 
	 * @param String value
	 * @return encodedString
	 */
	public static String getEncodedString(String value){
		
		logger.info("Entering EsapiUtility.getEncodedString() input"+value);
		String echoString = ESAPI.encoder().canonicalize(value);
		String encodedString = ESAPI.encoder().encodeForHTML(echoString);
		logger.info("Leaving EsapiUtility.getEncodedString()output"+encodedString);		
		return encodedString;
		
	}
	
	
	/**
	 * This method is for decoding the string value of APIs.
	 * 
	 * @param String value
	 * @return encodedString
	 */
	public static String getDecodedString(String value){
		
		logger.info("Entering EsapiUtility.getDecodedString() input"+value);
		String echoString = ESAPI.encoder().canonicalize(value);
		String encodedString = ESAPI.encoder().decodeForHTML(echoString);
		logger.info("Leaving EsapiUtility.decodeForHTML()output"+encodedString);		
		return encodedString;
		
	}

	
	
}
