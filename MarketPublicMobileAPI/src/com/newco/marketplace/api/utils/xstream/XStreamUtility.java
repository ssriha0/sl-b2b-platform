/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Apr-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.xstream;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.beans.Results;

import com.newco.marketplace.api.beans.so.OrderStatus;
import com.newco.marketplace.api.beans.so.create.SOCreateResponse;
import com.newco.marketplace.api.beans.so.search.SOSearchResponse;
import com.newco.marketplace.api.beans.so.search.SearchResults;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
 
/**
 * This class would act as an Utility Function for XStream conversion of Objects.
 * 
 * @author Infosys
 * @version 1.0
 */
public class XStreamUtility{
	private Logger logger = Logger.getLogger(XStreamUtility.class);

	/**
	 * This method is for the conversion of the createResponse object 
	 * to xml using Xstream conversion .
	 * 
	 * @param createResponse   SOCreateResponse
	 * @return createResponseXml
	 */
	public String getCreateResponseXML(SOCreateResponse createResponse){
		logger.info("Entering XStreamUtility.getCreateResponseXML()");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		Class<?>[] createResponseClasses = new Class[] {
				SOCreateResponse.class, Results.class,Result.class,
				OrderStatus.class ,ErrorResult.class,};
		xstream.processAnnotations(createResponseClasses);
		String createResponseXml = (String) xstream.toXML(createResponse);
		logger.info("Leaving XStreamUtility.getCreateResponseXML()");		
		return createResponseXml;
	}
	
	/**
	 * This method is for the conversion of the response object to 
	 * xml String using Xstream conversion .
	 * 
	 * @param soSearchResponse   SoSearchResponse
	 * @return searchResponseXml String
	 */
	public String getSearchResponseXML(SOSearchResponse soSearchResponse) {
		logger.info("Entering XStreamUtility.getSearchResponseXML()");		
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		Class<?>[] searchClasses = new Class[] { SOSearchResponse.class,
				Results.class,Result.class,SearchResults.class,ErrorResult.class,
				OrderStatus.class };
		xstream.processAnnotations(searchClasses);
		String searchResponseXml = (String) xstream.toXML(soSearchResponse);
		logger.info("Leaving XStreamUtility.getSearchResponseXML()");	
		return searchResponseXml;
	}
	
	
	
}
