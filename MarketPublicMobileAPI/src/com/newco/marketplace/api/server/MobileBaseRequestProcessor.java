package com.newco.marketplace.api.server;

import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.mobile.beans.DownLoadResponse;
import com.newco.marketplace.api.mobile.beans.DownloadResults;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.mobile.api.utils.validators.ResponseValidator;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;

public abstract class MobileBaseRequestProcessor {

	protected ResponseValidator responseValidator;
	protected CommonUtility commonUtility;
	


	/**
	 * @return the responseValidator
	 */
	public ResponseValidator getResponseValidator() {
		return responseValidator;
	}

	/**
	 * @param responseValidator
	 *            the responseValidator to set
	 */
	public void setResponseValidator(ResponseValidator responseValidator) {
		this.responseValidator = responseValidator;
	}

	/**
	 * @return the commonUtility
	 */
	public CommonUtility getCommonUtility() {
		return commonUtility;
	}

	/**
	 * @param commonUtility
	 *            the commonUtility to set
	 */
	public void setCommonUtility(CommonUtility commonUtility) {
		this.commonUtility = commonUtility;
	}
	
	// Common methods to convert Object to String and String to Object
	/**
	 * call this method to convert request Object to XML string
	 * 
	 * @param request
	 * @param classz
	 * @return XML String
	 */
	protected String convertReqObjectToXMLString(Object request, Class<?> classz) {
		XStream xstream = this.getXstream(classz);
		return xstream.toXML(request).toString();
	}

	/**
	 * call this method to convert XML string to object
	 * 
	 * @param request
	 * @param classz
	 * @return Object
	 */
	protected Object convertXMLStringtoRespObject(String request,
			Class<?> classz) {
		XStream xstream = this.getXstream(classz);
		return (Object) xstream.fromXML(request);
	}

	/**
	 * 
	 * @param classz
	 * @return
	 */
	protected XStream getXstream(Class<?> classz) {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		xstream.registerConverter(new DateConverter(
				PublicMobileAPIConstant.DATE_FORMAT_YYYY_MM_DD, new String[0]));
		xstream.addDefaultImplementation(java.sql.Date.class,
				java.util.Date.class);
		xstream.processAnnotations(classz);
		return xstream;
	}
	protected DownLoadResponse createDownloadResponse(String code,String message){
		DownLoadResponse errorResponse=new DownLoadResponse();
		DownloadResults result=DownloadResults.getError(message, code);
		errorResponse.setResults(result);
		return errorResponse;
	}
	//It is used for download document method only.
    /**
     * @param object
     * @return
     */
    protected String convertXMLStringToJsonObject(DownLoadResponse object){
    	XStream xstream = new XStream(new JettisonMappedXmlDriver());
        xstream.autodetectAnnotations(true);
        xstream.setMode(XStream.NO_REFERENCES);
        xstream.alias(object.getClass().getSimpleName(),DownLoadResponse.class);
        return xstream.toXML(object);
    }
}
