/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Apr-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.xstream;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.EventCallbackResponse;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.EventCallbackSoRequest;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * This class would act as an Utility Function for XStream conversion of
 * Objects.
 * 
 * @author Infosys
 * @version 1.0
 */
public class XStreamUtility {
	private Logger logger = Logger.getLogger(XStreamUtility.class);

	/**
	 * This method is for the conversion of the request xml to RetrieveRequest
	 * object using Xstream conversion .
	 * 
	 * @param soRequestXml
	 *            String
	 * @return retrieveSORequest
	 */
	public EventCallbackSoRequest getRetrieveRequestObject(String soRequestXml) {
		logger.info("Entering XStreamUtility.getRetrieveRequestObject()");
		XStream xstream = new XStream(new DomDriver());
		Class<?>[] retrieveClasses = new Class[] { EventCallbackSoRequest.class };
		xstream.processAnnotations(retrieveClasses);
		EventCallbackSoRequest retrieveSORequest = (EventCallbackSoRequest) xstream.fromXML(soRequestXml);
		logger.info(retrieveSORequest);
		logger.info("Leaving XStreamUtility.getRetrieveRequestObject()");
		return retrieveSORequest;
	}
	
	public String getEventCallbackResponseXML(EventCallbackResponse response) {
		logger.info("Entering XStreamUtility.getEventCallbackResponseXML()");
		XStream retrieveXstream = new XStream(new DomDriver());
		retrieveXstream.registerConverter(new ResultConvertor());
		retrieveXstream.registerConverter(new ErrorConvertor());		
		Class<?>[] responseClasses = new Class[] { EventCallbackResponse.class,
				Results.class, Result.class, ErrorResult.class};
				retrieveXstream.processAnnotations(responseClasses);
		String responseXML = (String) retrieveXstream.toXML(response);
		logger.info("Leaving XStreamUtility.getEventCallbackResponseXML()");
		return responseXML;
	}

}
