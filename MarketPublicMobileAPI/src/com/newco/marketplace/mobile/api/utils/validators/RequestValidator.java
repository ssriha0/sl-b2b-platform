/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 05-May-2009	KMSTRSUP   	Infosys				1.0
 * 
 */

package com.newco.marketplace.mobile.api.utils.validators;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.HashMap;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.newco.marketplace.api.mobile.security.AuthenticationProcess;
import com.newco.marketplace.api.mobile.security.AuthorizationProcess;
import com.newco.marketplace.api.mobile.security.LoggingProcess;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.utils.CryptoUtil;

/**
 * The class acts as the entry point for the 
 * validation done on the publicAPI
 *
 */
public class RequestValidator {

	private LoggingProcess loggingProcess;
	private AuthenticationProcess authenticationProcess;
	private AuthorizationProcess authorizationProcess;
	private int appId;
	private Logger logger = Logger.getLogger(RequestValidator.class);

	/**
	 * B2B - Controller method for the public api validation.it validate the input xml
	 * with the xsd,authenticate and authorize the user.
	 * This method also peforms authentication and Authorization.
	 * 
	 * @param ipAddress : client ipaddress
	 * @param requestXML : input requestxml from the client
	 * @param xsdFileName : name of the xsd file to be validated
	 * @return : status of the validation
	 * 
	 */
	public String validateRequest(String ipAddress, String requestXML,
			String xsdFileName) {
		logger.info("Entering RequestValidator.validateRequest()");
		boolean isRequestValid = false;
		String validationStatus = null;

		HashMap<String, String> clientCredentialMap = getclientCredentials(requestXML);
		try {
			validationStatus = validateRequestXML(requestXML,PublicAPIConstant.SO_RESOURCES_SCHEMAS, xsdFileName);
		} catch (Exception e) {
			logger.error("Exception Occurred while Validating Request XMl"+e);
		}
		if (PublicAPIConstant.SUCCESS.equals(validationStatus)) {
			isRequestValid = authenticationProcess.authenticate(
					clientCredentialMap.get(PublicAPIConstant.LOGIN_USERNAME),
					clientCredentialMap.get(PublicAPIConstant.LOGIN_PASSWORD));
			if (isRequestValid) {
				try {
					appId = authorizationProcess.authorize(clientCredentialMap
							.get(PublicAPIConstant.LOGIN_USERNAME),
							clientCredentialMap.get(PublicAPIConstant.API_KEY),
							ipAddress);

					isRequestValid = (appId != 0) ? true : false;

				} catch (Exception e) {
					isRequestValid = false;
					logger.error("The request is invalid " + e);
				}
				if (!isRequestValid) {
					validationStatus = CommonUtility.getMessage(
								PublicAPIConstant.AUTHORIZATION_ERROR_CODE);
				}
			} else {
				validationStatus = CommonUtility.getMessage(
							PublicAPIConstant.AUTHENTICATION_ERROR_CODE);
			}
		}
		logger.info("Leaving RequestValidator.validateRequest()");
		return validationStatus;
	}

	/**
	 * B2C - Controller method for the public api validation.it validate the input xml
	 * with the xsd,authenticate and authorize the user.
	 * This method does not perform Authentication or Authorization.
	 * 
	 * @param ipAddress
	 * @param requestXML
	 * @param xsdPath
	 * @param xsdFileName
	 * @return String
	 */
	public String validateRequest(String ipAddress, String requestXML,
			String xsdFilePath, String xsdFileName) {
		logger.info("Entering RequestValidator.validateRequest()");
		String validationStatus = null;

		try {
			validationStatus = validateRequestXML(requestXML,xsdFilePath, xsdFileName);
		} catch (Exception e) {
			logger.error("Exception Occurred while Validating Request XMl"+e);
		}
		logger.info("Leaving RequestValidator.validateRequest()");
		return validationStatus;
	}

	

	/**
	 * triggers the api request logging process
	 * 
	 * @param ipAddress :
	 *            client ipaddress
	 * @param requestXML :
	 *            input request xml
	 */
	public int logRequest(String ipAddress, String requestXML) {
		return loggingProcess.logRequest(requestXML, ipAddress, appId);
	}



	/**
	 * it validates the input xml with the xsd file
	 * 
	 * @param strRequestXML : input request xml
	 * @param xsdFileName : the xsd filename to be validated
	 * @return : status of the xsd validation
	 * @throws Exception
	 */
	private String validateRequestXML(String strRequestXML,String xsdPathName, String xsdFileName)
			throws Exception {
		logger.info("Entering RequestValidator.validateRequestXML()");
		URL xsdUrl = RequestValidator.class.getClassLoader().getResource(
				xsdPathName + xsdFileName);

		InputStream is = new ByteArrayInputStream(strRequestXML
				.getBytes(PublicAPIConstant.UTF));
		Source xmlFile = new StreamSource(is);
		Validator validator = createValidator(xsdUrl);
		try {
			validator.validate(xmlFile);
			logger.info("A valid XML file");

		} catch (SAXException e) {
			logger.error(" Not a valid XML file");
			logger.error("Reason: " + e.getLocalizedMessage());
			return CommonUtility.getMessage(PublicAPIConstant.INVALIDXML_ERROR_CODE)+": "+e.getMessage();
		}
		logger.info("Leaving RequestValidator.validateRequestXML()");
		return PublicAPIConstant.SUCCESS;

	}

	/**
	 * it generates the schema object
	 * 
	 * @param xsdUrl :  url object of the xsd file
	 * @return : schema object
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	private Validator createValidator(URL xsdUrl) throws SAXException,
			ParserConfigurationException {
		logger.info("Entering RequestValidator.createValidator()");
		SchemaFactory schemaFactory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(xsdUrl);
		logger.info("Leaving RequestValidator.createValidator()");
		return schema.newValidator();
	}

	/**
	 * it extracts the user information from the input xml and stores it in a
	 * map
	 * 
	 * @param strRequestXML :
	 *            input request xml
	 * @return : hashmap with the user credentials
	 */
	private HashMap<String, String> getclientCredentials(String strRequestXML) {
		logger.info("Entering RequestValidator.getclientCredentials()");
		HashMap<String, String> clientCredentialMap = new HashMap<String, String>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			factory.setNamespaceAware(true);
			Document document = null;
			XPath xpath = XPathFactory.newInstance().newXPath();
			XPathExpression expression;

			document = builder.parse(new InputSource(new StringReader(
					strRequestXML)));
			Object result;
			NodeList nodes;

			expression = xpath.compile("//"
					+ PublicAPIConstant.IDENTIFICATION_USERNAME + "/text()");
			result = expression.evaluate(document, XPathConstants.NODESET);
			nodes = (NodeList) result;
			clientCredentialMap.put(PublicAPIConstant.LOGIN_USERNAME, nodes
					.item(0).getNodeValue());
			expression = xpath.compile("//"
					+ PublicAPIConstant.IDENTIFICATION_APPLICATIONKEY
					+ "/text()");
			result = expression.evaluate(document, XPathConstants.NODESET);
			nodes = (NodeList) result;
			clientCredentialMap.put(PublicAPIConstant.API_KEY, nodes.item(0)
					.getNodeValue());
			expression = xpath.compile("//"
					+ PublicAPIConstant.IDENTIFICATION_PASSWORD + "/text()");
			result = expression.evaluate(document, XPathConstants.NODESET);
			nodes = (NodeList) result;
			clientCredentialMap.put(PublicAPIConstant.LOGIN_PASSWORD,
					CryptoUtil.generateHash(nodes.item(0).getNodeValue()));
		} catch (Exception e) {
			logger.error(e);
		}
		logger.info("Leaving RequestValidator.getclientCredentials()");
		return clientCredentialMap;
	}


	public void setAuthenticationProcess(
			AuthenticationProcess authenticationProcess) {
		this.authenticationProcess = authenticationProcess;
	}

	public void setAuthorizationProcess(
			AuthorizationProcess authorizationProcess) {
		this.authorizationProcess = authorizationProcess;
	}
	
	public void setLoggingProcess(LoggingProcess loggingProcess) {
		this.loggingProcess = loggingProcess;
	}

}
