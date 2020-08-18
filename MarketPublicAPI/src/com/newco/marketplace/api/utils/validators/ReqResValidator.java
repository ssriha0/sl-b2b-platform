package com.newco.marketplace.api.utils.validators;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;


import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;

public class ReqResValidator {
	private Logger logger = Logger.getLogger(ReqResValidator.class);
	private String errorString;
	
	/**
	 * it validates the input xml with the xsd file
	 * 
	 * @param strRequestXML : input request xml
	 * @param xsdFileName : the xsd filename to be validated
	 * @return : status of the xsd validation
	 * @throws Exception
	 */
	public boolean validateXML(String strRequestXML, String schemaLocation, String xsd) {
		logger.info("Entering validateRequestXML()");
		URL xsdUrl = ReqResValidator.class.getClassLoader().getResource(
				schemaLocation + xsd);
		
		InputStream is;
		
		try {
			is = new ByteArrayInputStream(strRequestXML
					.getBytes(PublicAPIConstant.UTF));

			Source xmlFile = new StreamSource(is);
			Validator validator = createValidator(xsdUrl);
			validator.validate(xmlFile);
			logger.info("A valid XML file");
			return true;
		} catch (SAXException e) {
			logger.error(" Not a valid XML file");
			logger.error("Reason: " + e.getLocalizedMessage());
			errorString = CommonUtility.getMessage(PublicAPIConstant.INVALIDXML_ERROR_CODE)+": " + e.getMessage();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Leaving validateRequestXML()");
		return false;
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
		logger.info("Entering RequestValidator.createValidator() " +xsdUrl.toString());
		SchemaFactory schemaFactory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(xsdUrl);
		logger.info("Leaving RequestValidator.createValidator()");
		return schema.newValidator();
	}
	
	public String getErrorMsg() {
		return errorString;
	}
	

}
