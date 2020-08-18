/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-May-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */

package com.newco.marketplace.mobile.api.utils.validators;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.create.SOCreateResponse;
import com.newco.marketplace.api.beans.so.search.SOSearchResponse;
import com.newco.marketplace.api.mobile.security.LoggingProcess;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
/**
 * This class would act as the validator class for response xml with xsd.
 * 
 * @author Infosys
 * @version 1.0
 */
public class ResponseValidator {
	private Logger logger = Logger.getLogger(ResponseValidator.class);
	private XStreamUtility conversionUtility;
	private LoggingProcess loggingProcess;
	
	/**
	 * Controller method for the response validation.it validate the output xml
	 * with the xsd
	 * 
	 * @param strResponseXML String
	 * @param xsdFileName String
	 * @throws Exception
	 * @return String
	 * @deprecated replaced by  {@link #validateResponseXML(String, String, String)}
	 */
	public void validateResponseXML(String strResponseXML,String xsdPathName,String xsdFileName){
		logger.info("Entering ResponseValidator.validateResponseXML()");
		boolean validationFlag = true;
		try {
			URL xsdUrl = ResponseValidator.class.getClassLoader().getResource(
					xsdPathName + xsdFileName);

			InputStream is = new ByteArrayInputStream(strResponseXML
					.getBytes(PublicAPIConstant.UTF));
			Source xmlFile = new StreamSource(is);
			Validator validator = createValidator(xsdUrl);

			validator.validate(xmlFile);
			logger.info("A valid Response XML file");

		} catch (SAXException e) {
			logger.error(" Not a valid Response XML file");
			logger.error("Reason: " + e.getLocalizedMessage());
		} catch (UnsupportedEncodingException e) {
			logger.error("UnSupported Encoding Exception " 
					+"Occurred while Validating Response xml" + e);
		} catch (ParserConfigurationException e) {
			logger.error("ParserConfiguration Exception Occurred" 
							+" while Validating Response xml " + e);
		} catch (IOException e) {
			logger.error("IO Exception Occurred while Validating Response xml"
					+ e);
		}catch (Exception e) {
			logger.error("Exception Occurred while Validating Response xml"
					+ e);
		}finally{
			
		}
		
		logger.info("Leaving ResponseValidator.validateResponseXML()");
	}
	
	/**
	 * This method validates the response XML string and retunrs true or false.
	 * 
	 * @param strResponseXML
	 * @param xsdPathName
	 * @param xsdFileName
	 * @return boolean
	 */
	public boolean validateResponseXMLString(String strResponseXML,String xsdPathName,String xsdFileName){
		logger.info("Entering ResponseValidator.validateResponseXML()");
		boolean validationFlag = false;
		try {
			URL xsdUrl = ResponseValidator.class.getClassLoader().getResource(
					xsdPathName + xsdFileName);

			InputStream is = new ByteArrayInputStream(strResponseXML
					.getBytes(PublicAPIConstant.UTF));
			Source xmlFile = new StreamSource(is);
			Validator validator = createValidator(xsdUrl);
			logger.info("A valid Response XML1 file");
			validator.validate(xmlFile);
			logger.info("A valid Response XML file");
			validationFlag = true;
		} catch (SAXException e) {
			logger.error(" Not a valid Response XML file");
			logger.error("Reason: " + e.getLocalizedMessage());
			if (logger.isInfoEnabled())
				e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			logger.error("UnSupported Encoding Exception " 
					+"Occurred while Validating Response xml" + e);
		} catch (ParserConfigurationException e) {
			logger.error("ParserConfiguration Exception Occurred" 
							+" while Validating Response xml " + e);
		} catch (IOException e) {
			logger.error("IO Exception Occurred while Validating Response xml"
					+ e);
		}
		catch (Exception e) {
			logger.error(" Exception Occurred: "
					+ e);
		}
		
		logger.info("Leaving ResponseValidator.validateResponseXML()");
		return validationFlag;
	}
	
	
	/**
	 * triggers the api response logging process
	 * 
	 * @param strResponseXml :
	 *            output response xml
	 */
	public void logResponse(String strResponseXml,int logId) {
		loggingProcess.logResponse(strResponseXml,logId);
	}
	
	/**
	 * it creates the error response xml if the validation fails
	 * 
	 * @param message :
	 *            error message
	 * @return : response error xml
	 *  @deprecated replaced by  {@link #getFailureResponse(String, String, String, String, String)}
	 */
	public String getFailureResponse(String message,String errorCode) {
		logger.info("Entering ResponseValidator.getFailureResponse()");
		SOCreateResponse response = new SOCreateResponse();
		Results results = new Results();
		List<Result> resultList=new ArrayList<Result>();
		List<ErrorResult> errorList=new ArrayList<ErrorResult>();
		Result result = new Result();
		result.setCode(PublicAPIConstant.ZERO);
		result.setMessage("");
		ErrorResult errorResult = new ErrorResult();
		errorResult.setMessage(message);
		errorResult.setCode(errorCode);
		resultList.add(result);
		errorList.add(errorResult);
		results.setResult(resultList);
		results.setError(errorList);
		response.setResults(results);
		response.setVersion(PublicAPIConstant.SORESPONSE_VERSION);
		response.setSchemaLocation(PublicAPIConstant.SORESPONSE_SCHEMALOCATION);
		response.setNamespace(PublicAPIConstant.SORESPONSE_NAMESPACE);
		response.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		String responseXML = conversionUtility.getCreateResponseXML(response);
		logger.info("Leaving ResponseValidator.getFailureResponse()");
		return responseXML;
	}
	
	
	/**
	 * it creates the error response xml if the validation fails
	 * 
	 * @param message
	 * @param errorCode
	 * @param schemaLocation
	 * @param namespace
	 * @param schemaInstance
	 * @return String
	 */
	public String getFailureResponse(String message,String errorCode, String schemaLocation, String namespace, String schemaInstance) {
		logger.info("Entering ResponseValidator.getFailureResponse()");
		SOCreateResponse response = new SOCreateResponse();
		Results results = new Results();
		List<Result> resultList=new ArrayList<Result>();
		List<ErrorResult> errorList=new ArrayList<ErrorResult>();
		//Result result = new Result();
		//result.setCode(PublicAPIConstant.ZERO);
		//result.setMessage("");
		ErrorResult errorResult = new ErrorResult();
		errorResult.setMessage(message);
		errorResult.setCode(errorCode);
		//resultList.add(result);
		errorList.add(errorResult);
		results.setResult(resultList);
		results.setError(errorList);
		response.setResults(results);
		response.setVersion(PublicAPIConstant.SORESPONSE_VERSION);
		response.setSchemaLocation(schemaLocation);
		response.setNamespace(namespace);
		response.setSchemaInstance(schemaInstance);
		String responseXML = conversionUtility.getCreateResponseXML(response);
		logger.info("Leaving ResponseValidator.getFailureResponse()");
		return responseXML;
	}


	

	/**
	 * it creates the error response xml if the validation fails for Search
	 * 
	 * @param message String
	 * @return responseXML String
	 */
	public String getSearchFailureResponse(String message,String errorcode) {
		logger.info("Entering ResponseValidator.getSearchFailureResponse()");
		SOSearchResponse response = new SOSearchResponse();
		//OrderStatus orderStatus = new OrderStatus();
		Results results = new Results();
		List<Result> resultList=new ArrayList<Result>();
		List<ErrorResult> errorList=new ArrayList<ErrorResult>();
		Result result = new Result();
		result.setCode(PublicAPIConstant.ZERO);
		result.setMessage("");
		ErrorResult errorResult = new ErrorResult();
		errorResult.setMessage(message);
		errorResult.setCode(errorcode);
		resultList.add(result);
		errorList.add(errorResult);
		results.setResult(resultList);
		results.setError(errorList);
		response.setResults(results);
		response.setVersion(PublicAPIConstant.SEARCH_VERSION);
		response.setSchemaLocation(PublicAPIConstant.SEARCH_SCHEMALOCATION);
		response.setNamespace(PublicAPIConstant.SEARCHRESPONSE_NAMESPACE);
		response.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		String responseXML = conversionUtility.getSearchResponseXML(response);
		logger.info("Leaving ResponseValidator.getSearchFailureResponse()");
		return responseXML;
	}
	/**
	 * it generates the schema object
	 * 
	 * @param xsdUrl :
	 *            url object of the xsd file
	 * @return : schema object
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	private Validator createValidator(URL xsdUrl) throws SAXException,
	ParserConfigurationException {
		logger.info("Leaving ResponseValidator.createValidator()");
		SchemaFactory schemaFactory = SchemaFactory
		.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(xsdUrl);
		logger.info("Leaving ResponseValidator.createValidator()");
		return schema.newValidator();
	}


	public void setConversionUtility(XStreamUtility conversionUtility) {
		this.conversionUtility = conversionUtility;
	}
	
	public void setLoggingProcess(LoggingProcess loggingProcess) {
		this.loggingProcess = loggingProcess;
	}
	

}
