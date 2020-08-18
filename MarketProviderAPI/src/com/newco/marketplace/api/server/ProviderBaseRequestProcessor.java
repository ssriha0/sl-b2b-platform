package com.newco.marketplace.api.server;

import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.provider.constants.ProviderAPIConstant;
import com.newco.marketplace.api.provider.utils.validators.ResponseValidator;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

public abstract class ProviderBaseRequestProcessor {

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
				ProviderAPIConstant.DATE_FORMAT_YYYY_MM_DD, new String[0]));
		xstream.addDefaultImplementation(java.sql.Date.class,
				java.util.Date.class);
		xstream.processAnnotations(classz);
		return xstream;
	}
}
