package com.newco.marketplace.api.services;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.ExcludeValidation;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.common.UserIdentificationRequest;
import com.newco.marketplace.api.beans.search.providerProfile.ProviderResults;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.APIValidatationException;
import com.newco.marketplace.api.common.APIValidator;
import com.newco.marketplace.api.common.DataTypes;
import com.newco.marketplace.api.common.IAPIRequest;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.Identification;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.security.APIFieldMasker;
import com.newco.marketplace.api.security.SecurityProcess;
import com.newco.marketplace.api.server.v1_1.SearchRequestProcessor;
import com.newco.marketplace.api.services.search.SearchProviderByZipCodeService;
import com.newco.marketplace.api.utils.validators.ReqResValidator;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.hi.IAPILoggingBO;
import com.newco.marketplace.business.iBusiness.om.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.UnresolvableException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.utils.SimpleCache;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * This class serve as base class for all service classes. There should be one service class per API and 
 * it should extend BaseService class. 
 * <p> 
 * The requestProcess class should call doSubmit method and pass received parameters. The doSubmit method will
 * validate the request and then call execute method. In the service class one should override execute method.  
 * The actual login for the API should be written in the execute method (e.g. Go to back-end to add funds to wallet).
 * <p> 
 * The request and response XML validations are done in this class and there is no need to put extra logic in 
 * Service class  (Unless you have API specific validation like mapping a zip code to a state).
 * Get Request and URL parameters are not automatically validated. Call following methods in service class constructor 
 * to validate Get and  URL parameters. 
 * 
 * {@link #addOptionalGetParam} (In eclipse use Ctrl + click to goto the method)<p> 
 * {@link #addRequiredGetParam} <p> 
 * {@link #addRequiredURLParam} <p> 
 * 
 * After returning from execute method of sub class it will call getResponseXML to create XML response.  
 * The getResponseXML will create XML and then validate it against response class XSD (check {@link} ), 
 * in case of an error it will send error code 0004 (Internal Server Error) otherwise it will return formed XML.
 * <p> 
 * Following is the actual calls flow:<p> 
 *  Request comes to Request Processor class -> It calls doSubmit of BaseService <p> 
 *  	-> doSubmit calls validation (e.g. for get request it calls addOptionalGetParam) <p> 
 *  	   -> doSubmit calls execute of sub class -> doSubmit calls getResponseXML and returns XML  <p> 
 *  		-> Request Processor returns actual XML response. <p> 
 *  <p> 
 *  Get-Request Call flow example(SearchProviderByZip) : (Ctrl + click to goto the method) <p> 
 *  	{@link SearchRequestProcessor#getProvidersByZipCode} -> {@link #doSubmit(APIRequestVO)}  <p> 
 *  		-> {@link #validateGetParams [Validate Get Parameters]} <p> 
 *  			-> {@link SearchProviderByZipCodeService#execute(APIRequestVO)} -> #getResponseXML <p> 
 *  
 *  <p> 
 *  This class has two constructors. Read their descriptions for usages.
 *  
 * @author Nilesh Dixit(ndixit@searshc.com) & Shekhar Nirkhe(chandra@servicelive.com)
 * 
 * @since 10/01/2009
 *
 *
 */
public abstract class BaseService {

	private static final String MEDIA_TYPE_JSON_STR = "application/json";

	private static Logger logger = Logger.getLogger(BaseService.class);
	public String requestXsd, responseXsd;
	public String namespace;

	public String schemaLocationReq;
	public String schemaLocationRes;
	private Class<?> requestClass, responseClass; 
	ArrayList<Class> beanClasses = new ArrayList<Class>();
	private String errorString, errorCode;
	ReqResValidator xmlValidator;
	private static SecurityProcess securityProcess;
	private static IServiceOrderBO serviceOrderBO;
	private IAPILoggingBO apiLoggingBO;
	public enum RequestType {Put,Post,Delete,Get}
	public enum SourceType {Bank,CC}
	private Map<String, DataTypes>  requiredGetParam;
	private Map<String, DataTypes>  optionalGetParam;
	private Map<String, DataTypes>  requiredURLParam; //there is no optional params for obvious purpose.
	private String buyerIdParam;
	private boolean  doValidation = true;
	private APIFieldMasker apiFieldMasker;
	private XStream xstreamXML, xstreamJSON;
	/**
	 * This is an old implementation and should be avoided for new development. It will be deprecated in
	 * next release. Use parameter less constructor instead.
	 * 
	 * @param requestXsd
	 * @param responseXsd
	 * @param namespace
	 * @param resourceSchema
	 * @param schemaLocation
	 * @param requestClass
	 * @param responseClass
	 */

	public BaseService(final String requestXsd, final String responseXsd, final String namespace, 
			final String resourceSchema, final String schemaLocation,
			Class<?> requestClass, Class<?> responseClass) {
		this.responseXsd = responseXsd;
		this.requestXsd = requestXsd;
		this.namespace = namespace;
		this.schemaLocationReq = resourceSchema;
		this.schemaLocationRes = resourceSchema;
		this.requestClass = requestClass;
		this.responseClass = responseClass;

		beanClasses.add(Results.class);
		beanClasses.add(Result.class);
		beanClasses.add(Identification.class);
		beanClasses.add(ErrorResult.class);
		beanClasses.add(requestClass);
		beanClasses.add(responseClass);
		xmlValidator = new ReqResValidator();
		requiredGetParam = new HashMap<String, DataTypes> ();
		optionalGetParam = new HashMap<String, DataTypes> ();
		requiredURLParam = new HashMap<String, DataTypes> ();
	}
	/**
	 * Use this constructor with Annotations. Following annotations cab be used:
	 *  XSD(Name, Path)
	 *  APIRequestClass
	 *  APIResponseClass
	 *  Namespace
	 *  
	 *  Every Service class calling this constructor should be annotated with APIRequestClass(only for Post/Put), 
	 *  APIResponseClass and Namespace. The corresponding request and response classes should be annotated with XSD. 
	 *    
	 *  Please note that the annotated Namespace and namespace present in corresponding response XSD (xmlns) should match 
	 *  otherwise validator will throw XML validation exception.
	 *  
	 *  You can see implementation example here:
	 *  Service Class: {@link SearchProviderByZipCodeService}
	 *  Response Class : {@link ProviderResults}
	 *    
	 */
	public BaseService() {

		xmlValidator = new ReqResValidator();

		requiredGetParam = new HashMap<String, DataTypes> ();
		optionalGetParam = new HashMap<String, DataTypes> ();
		requiredURLParam = new HashMap<String, DataTypes> ();
		APIResponseClass annoResClass = this.getClass().getAnnotation(APIResponseClass.class);
		if (annoResClass != null) {
			responseClass = annoResClass.value();
			XSD annoRes = ((Class<?>)responseClass).getAnnotation(XSD.class);
			if (annoRes == null) {
				throw new java.lang.RuntimeException("XSD annotation is not defined in respons class:" +
						responseClass.getName());
			}

			this.responseXsd  = annoRes.name();
			this.schemaLocationRes  = annoRes.path();
		} else {
			throw new java.lang.RuntimeException("ResponseClass annotation is not defined in service class:" +
					this.getClass().getName());
		}

		APIRequestClass annoReqClass = this.getClass().getAnnotation(APIRequestClass.class);
		if (annoReqClass != null) {
			requestClass = annoReqClass.value();

			XSD anno = ((Class<?>)requestClass).getAnnotation(XSD.class);
			if (anno == null) {
				throw new java.lang.RuntimeException("XSD annotation is not defined in request class:" +
						responseClass.getName());
			}

			this.requestXsd  = anno.name();
			this.schemaLocationReq  = anno.path();
		} else {
			this.requestXsd  = null;
			this.schemaLocationReq  = null;
		}
		Namespace annoName = this.getClass().getAnnotation(Namespace.class);
		if (annoName != null) {
			this.namespace = annoName.value();
		} else {
			throw new java.lang.RuntimeException("Namespace annotation is not defined in service class:" +
					this.getClass().getName());
		}

		ExcludeValidation validationClass = this.getClass().getAnnotation(ExcludeValidation.class);
		if (validationClass != null)
			this.doValidation = false; 
		beanClasses.add(Results.class);
		beanClasses.add(Result.class);
		beanClasses.add(Identification.class);
		beanClasses.add(ErrorResult.class);
		beanClasses.add(requestClass);
		beanClasses.add(responseClass);
	}
	/**
	 * Use this method to validate required get request parameters. 
	 * Validator return with error code 0007 if the required parameter is missing in the request or hold 
	 * invalid value (e.g. string in a Integer data type is invalid). 
	 *  
	 * @param param
	 * @param type
	 */
	public void addRequiredGetParam(String param, DataTypes type) {
		this.requiredGetParam.put(param.toLowerCase(), type);
	}
	/**RequestType
	 * Use this method to validate  parameters present in the URL. 
	 *  
	 * @param param
	 * @param type
	 */
	public void addRequiredURLParam(String param, DataTypes type) {
		this.requiredURLParam.put(param.toUpperCase(), type);
	}
	/**
	 * This method is to get Security Context for B2C clients.
	 * 
	 * @param userName String
	 * @return SecurityContext
	 * @deprecated - use getSecurityContext(Integer buyerResourceId)
	 */
	
	public SecurityContext getSecurityContext(String userName) throws UnresolvableException{
		logger.info("Entering SecurityProcess.getSecurityContext()");
		SecurityContext securityContext = null;
		if (securityProcess == null)
			securityProcess = (SecurityProcess)loadBeanFromContext("SecurityProcess");
		securityContext = securityProcess.getSecurityContext(userName, null);
		if (securityProcess == null){
			logger.info("Security context is null, user may not exists in the system");
			throw new UnresolvableException("Security context is null");
		}
		logger.info("Leaving SecurityProcess.getSecurityContext()");
		return securityContext;
	}
	public static SecurityContext getSecurityContextForVendorAdmin(Integer vendorId) {

		logger.info("Entering SecurityProcess.getSecurityContext()");
		final String key = "/SecurityContext/vendor/rid/";
		String tempKey = key + vendorId;

		SecurityContext securityContext = (SecurityContext)SimpleCache.getInstance().get(tempKey);

		if (securityContext == null) {
			if (securityProcess == null)
				securityProcess = (SecurityProcess)loadBeanFromContext("SecurityProcess");

			securityContext = securityProcess.getSecurityContextForVendorAdmin(vendorId);
			if (securityContext != null){
				//
				//FIXME: Commenting the following code since security context is cached incorrectly 
				//SimpleCache.getInstance().put(tempKey, securityContext, SimpleCache.ONE_HOUR);
			} else {
				logger.info("Security context is null, user may not exists in the system");
				//throw new UnresolvableException("Security context is null");
			}
		}
		logger.info("Leaving SecurityProcess.getSecurityContext()");
		return securityContext;
	}

	public static SecurityContext getSecurityContextForVendor(Integer vendorResourceId) {

		logger.info("Entering SecurityProcess.getSecurityContext()");
		final String key = "/SecurityContext/vendor/rid/";
		String tempKey = key + vendorResourceId;

		SecurityContext securityContext = (SecurityContext)SimpleCache.getInstance().get(tempKey);

		if (securityContext == null) {
			if (securityProcess == null)
				securityProcess = (SecurityProcess)loadBeanFromContext("SecurityProcess");

			securityContext = securityProcess.getSecurityContextForVendor(vendorResourceId);
			if (securityContext != null){
				//FIXME: Commenting the following code since security context is cached incorrectly 
				//SimpleCache.getInstance().put(tempKey, securityContext, SimpleCache.ONE_HOUR);
			} else {
				logger.info("Security context is null, user may not exists in the system");
				//throw new UnresolvableException("Security context is null");
			}
		}
		logger.info("Leaving SecurityProcess.getSecurityContext()");
		return securityContext;
	}
	public static boolean isBuyerExist (Integer buyerId) {

		final String key = "/SecurityContext/buyer/exist/id/";
		String tempKey = key + buyerId;
		Boolean bool = (Boolean)SimpleCache.getInstance().get(tempKey);
		if (bool != null && bool == true)
			return bool;
		else {
			bool = securityProcess.isBuyerExist(buyerId);
			//FIXME: Commenting the following code since security context is cached incorrectly 
			//SimpleCache.getInstance().put(tempKey, bool, SimpleCache.ONE_HOUR);
		}

		return bool;
	}
	public static boolean isProviderExist (Integer providerId) {

		final String key = "/SecurityContext/provider/exist/id/";
		String tempKey = key + providerId;
		Boolean bool = (Boolean)SimpleCache.getInstance().get(tempKey);
		if (bool != null && bool == true)
			return bool;
		else {
			bool = securityProcess.isProviderExist(providerId);
			//FIXME: Commenting the following code since security context is cached incorrectly 
			//SimpleCache.getInstance().put(tempKey, bool, SimpleCache.ONE_HOUR);
		}

		return bool;
	}

	/**
	 * @deprecated Use getSecurityContextForBuyer or getSecurityContextForVendor
	 * @param buyerResourceId
	 * @return
	 */
	public SecurityContext getSecurityContext (Integer buyerResourceId) {
		return getSecurityContextForBuyer(buyerResourceId);
	}
	public static SecurityContext getSecurityContextForBuyer(Integer buyerResourceId) {

		logger.info("Entering SecurityProcess.getSecurityContext()");
		final String key = "/SecurityContext/buyer/rid/";
		String tempKey = key + buyerResourceId;

		SecurityContext securityContext = (SecurityContext)SimpleCache.getInstance().get(tempKey);

		if (securityContext == null) {
			if (securityProcess == null)
				securityProcess = (SecurityProcess)loadBeanFromContext("SecurityProcess");

			securityContext = securityProcess.getSecurityContext(buyerResourceId);
			if (securityContext != null){
				//FIXME: Commenting the following code since security context is cached incorrectly 
				//SimpleCache.getInstance().put(tempKey, securityContext, SimpleCache.ONE_HOUR);
			} else {
				logger.info("Security context is null, user may not exists in the system");

			}
		}
		logger.info("Leaving SecurityProcess.getSecurityContext()");
		return securityContext;
	}
	/**
	 * This method is used to get the Security Context using Buyer Id
	 * @param BuyerId
	 * @return
	 */
	public static SecurityContext getSecurityContextForBuyerAdmin	(Integer BuyerId) {		
		logger.info("Entering SecurityProcess.getSecurityContextForBuyerAdmin()");
		final String key = "/SecurityContext/buyerAdmin/rid/";
		String tempKey = key + BuyerId;

		SecurityContext securityContext = 
				(SecurityContext)SimpleCache.getInstance().get(tempKey);

		if (securityContext == null) {
			if (securityProcess == null)
				securityProcess = (SecurityProcess)loadBeanFromContext
				("SecurityProcess");

			securityContext = securityProcess.getSecurityContextBuyer(BuyerId);
			if (securityContext != null){
				//FIXME: Commenting the following code since security context is cached incorrectly 
				//SimpleCache.getInstance().put(tempKey, 
				//securityContext, SimpleCache.ONE_HOUR);
			} else {
				logger.info("Security context is null, user may not exists in the system");

			}
		}
		logger.info("Leaving SecurityProcess.getSecurityContextForBuyerAdmin()");
		return securityContext;
	}

	/**
	 * This is a dummy method. You must override it in child class.
	 * @param Object xmlRequestFromPost
	 * @param Map requestMapFromGet
	 * @return
	 */
	public abstract IAPIResponse execute (APIRequestVO apiVO);
	/**
	 * This is a common method for all the APIs, based on the request type it will do the validations
	 * and call execute from respective API service class. In case of Put/Post it will construct request 
	 * object which in turn will do request XML validation as well. In case of an error it will return 
	 * error code 0001.  In case of Get/Delete it will call validateGetParams to validate request.
	 * For all type of request it will call validateURLParams to validate parameters present in 
	 * URL.
	 * 
	 * Also :
	 * @see #addOptionalGetParam
	 * @see #addRequiredGetParam
	 * @see #addRequiredURLParam
	 * @see #validateGetParams
	 * @see #validateURLParams
	 * 
	 * @param apiVO
	 * @return
	 */
	public String doSubmit(APIRequestVO apiVO){
		logger.info("Entering doSubmit method");
		boolean validation = true;
		Object request = null;

		switch(apiVO.getRequestType()) {
		case Get:
			if (this.doValidation)
				validation  = validateGetParams(apiVO);
			break;
		case Delete:
			break;
		case Post:
		case Put:
		default:
			String requestString = (String) apiVO.getRequestFromPostPut();
			logger.info("Request::" + requestString);
			// check and convert request string from json to xml
			if (MEDIA_TYPE_JSON_STR.equalsIgnoreCase(apiVO.getContentType()) && IAPIRequest.class.isAssignableFrom(requestClass)) {
				requestString = convertJSONtoXML(requestString);
			}

			request = getRequestObj(requestString);
			if (request == null)
				return getFailedResponse(apiVO.getAcceptHeader());
			if (this.doValidation) {

				if (request instanceof UserIdentificationRequest) {
					UserIdentificationRequest ui = (UserIdentificationRequest)request;
					String type  = ui.getIdentification().getType();
					if (type != null) {
						Integer id = ui.getIdentification().getId();
						if (type.equalsIgnoreCase(Identification.ID_TYPE_BUYER_RESOURCE_ID)) {
							apiVO.setBuyerResourceId(ui.getIdentification().getId());
						} else {
							apiVO.setProviderResourceId(id);
						}

						validation = validateIdentificaiton(apiVO);
					}

				}
			}
			if (validation == true)

				apiVO.setRequestFromPostPut(request);
		}
		if (this.doValidation && validation) {

			validation = validateURLParams(apiVO);
		}
		if (validation == false){ //error
			return getFailedResponse(apiVO.getAcceptHeader());
		}
		IAPIResponse response = null;
		try {
			response =  execute(apiVO);
		} catch (Exception e) { 
			// I know its a bad idea to catch Exception. But this is required to print stack trace.
			// Otherwise CFX will consume the exception and send HTTP 500 error, then we will never
			// get to know the route cause of the problem.
			logger.error("Error in API execution", e);

			return getFailedResponse(apiVO.getAcceptHeader());
		}
		//API Masking
		getApiFieldMasker().checkRules(apiVO.getConsumerKey(), response);
		String responseString = getResponseString(response, apiVO.getAcceptHeader());
		if (logger.isDebugEnabled()){ 
			logger.info("Response string : " + responseString);
		}

		logger.info("Exiting doSubmit method");
		return responseString;
	}
	/**
	 * Convert from json string to xml 
	 * 
	 * @param requestString
	 * @return
	 */
	private String convertJSONtoXML(String requestString) {
		logger.info("Entering BaseService convertJSONtoXML");

		// check for valid json string
		try {
			new JSONObject(requestString);
		} catch (JSONException ex) {
			try {
				new JSONArray(requestString);
			} catch (JSONException ex1) {
				logger.error("Request string is not in correct JSON format.");
				return null;
			}
		}

		XStream xstreamJSON = getXstreamJSON();
		IAPIRequest iapiRequest = (IAPIRequest) xstreamJSON.fromXML(requestString);
		// getting namespace from annotation
		iapiRequest.setNamespace(null != this.requestClass.getAnnotation(Namespace.class) ? this.requestClass
				.getAnnotation(Namespace.class).value() : "");
		iapiRequest.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		iapiRequest.setSchemaLocation(schemaLocationReq+" "+this.requestXsd);
		iapiRequest.setVersion(PublicAPIConstant.SORESPONSE_VERSION);
		XStream xstreamXML = getXstreamXML();
		String xmlString = xstreamXML.toXML(iapiRequest);
		if (logger.isDebugEnabled()) {
			logger.info("Converted(json to) xml string - " + xmlString);
		}
		logger.info("Exiting BaseService convertJSONtoXML");
		return xmlString;
	}
	private XStream getXstreamXML() {
		if (xstreamXML == null) {
			xstreamXML = new XStream(new DomDriver());
			configureXStreamDriver(xstreamXML);

		}
		return xstreamXML;
	}
	private XStream getXstreamJSON() {
		if (xstreamJSON == null) {
			xstreamJSON = new XStream(new JettisonMappedXmlDriver());
			configureXStreamDriver(xstreamJSON);

		}
		return xstreamJSON;
	}

	/**
	 * Configure common fields for xstream object
	 * @param xstream
	 */
	private void configureXStreamDriver(XStream xstream) {
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		xstream.registerConverter(new DateConverter("yyyy-MM-dd", new String[0]));
		xstream.addDefaultImplementation(java.sql.Date.class, java.util.Date.class);
		Class<?>[] requestClasses = getClassesArray();
		xstream.processAnnotations(requestClasses);
	}
	private String getResponseString(IAPIResponse obj, String acceptType) {

		logger.info("Entering BaseService getResponseString");
		obj.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		obj.setNamespace(this.namespace);
		obj.setSchemaLocation(this.namespace + " " + this.responseXsd);
		// getting xml to validate against XSD
		String responseString = getXstreamXML().toXML(obj);
		
		// logger.info("responseString:::"+responseString);
		if (!xmlValidator.validateXML(responseString, this.schemaLocationRes, this.responseXsd)) {
			this.errorString = ResultsCode.INTERNAL_SERVER_ERROR.getMessage();
			this.errorCode = ResultsCode.INTERNAL_SERVER_ERROR.getCode();
			responseString =  getFailedResponse(acceptType);
		} else {
			// Converting Object to required media type
			responseString = getCreateResponseString(obj, acceptType);
		}

		logger.info("Exiting getResponseString");

		return responseString;
	}
	private Object getRequestObj(String request) {
		logger.info("Entering getRequestObj");
		if (null == request || !xmlValidator.validateXML(request, this.schemaLocationReq, this.requestXsd)) {
			this.errorString = xmlValidator.getErrorMsg();
			this.errorCode = ResultsCode.INTERNAL_SERVER_ERROR.getCode();
			return null;
		}
		try {

			XStream xstream = getXstreamXML();
			Object obj = xstream.fromXML(request);
			logger.info("Exiting getRequestObj()");

			return obj;

		} catch (Exception e) {
			logger.error("Error while geting request object", e);
		}
		return null;
	}
	/**
	 * This method should be used to add classes to XStream XML conversion utility. These classes will be used to 
	 * construct XML response. If you have some Objects in response which are not available to XStream then it
	 * will throw an exception. Following classes are automatically added by BaseService.
	 *  
	 * RequestClass
	 * ResponseClass
	 * Results.java
	 * Result.class;
	 * Identification.java
	 * ErrorResult.java
	 * 
	 * @param cls
	 */
	protected void addMoreClass(Class<?> cls) {
		beanClasses.add(cls);
	}
	private Class<?>[] getClassesArray() {
		Class<?>[] cType = new Class<?>[beanClasses.size()];
		Class<?>[] responseClasses = beanClasses.toArray(cType);
		return responseClasses;
	}
	private String getFailedResponse(String acceptType) {
		return getFailureResponse(this.errorString,
				this.errorCode, 
				this.schemaLocationRes, 
				this.namespace, 
				this.responseXsd, acceptType);
	}
	/**
	 * It creates error response XML if the validation fails.
	 * 
	 * @param message
	 * @param errorCode
	 * @param schemaLocation
	 * @param namespace
	 * @param schemaInstance
	 * @return String
	 */
	private String getFailureResponse(String message,String errorCode, String schemaLocation, String namespace, String schemaInstance, String acceptType) {
		logger.info("Entering getFailureResponse()");
		IAPIResponse response;
		try {
			response = (IAPIResponse)this.responseClass.newInstance();
		} catch (InstantiationException e) {

			logger.error("Error while creating instance of response.", e);
			return null;
		} catch (IllegalAccessException e) {

			logger.error("Error while creating instance of response.", e);
			return null;
		}
		Results results = new Results();
		List<Result> resultList=new ArrayList<Result>();
		List<ErrorResult> errorList=new ArrayList<ErrorResult>();
		ErrorResult errorResult = new ErrorResult();
		errorResult.setMessage(message);
		errorResult.setCode(errorCode);
		errorList.add(errorResult);
		results.setResult(resultList);
		results.setError(errorList);
		response.setResults(results);
		response.setVersion(PublicAPIConstant.SORESPONSE_VERSION);
		response.setSchemaLocation(schemaLocation);
		response.setNamespace(namespace);
		response.setSchemaInstance(schemaInstance);
		String responseString = getCreateResponseString(response, acceptType);
		logger.info("Leaving getFailureResponse()");
		return responseString;
	}
	/**
	 * This method is for the conversion of the createResponse object 
	 * to json or xml using Xstream conversion .
	 * 
	 * @param createResponse   SOCreateResponse
	 * @return createResponseXml
	 */
	private String getCreateResponseString(Object response, String acceptType){
		logger.info("Entering getCreateResponseString()");
		String createResponseString;
		// get required media type XStream instance
		if(MEDIA_TYPE_JSON_STR.equalsIgnoreCase(acceptType)){
			IAPIResponse iAPIResponse =(IAPIResponse)response;
			iAPIResponse.setNamespace(null);
			iAPIResponse.setSchemaInstance(null);
			iAPIResponse.setSchemaLocation(null);
			 createResponseString =	getXstreamJSON().toXML(iAPIResponse);
		}
		else{
			createResponseString=getXstreamXML().toXML(response);
		}
		
		logger.info("Leaving getCreateResponseString()");

		return createResponseString;
	}
	/**
	 * It will validate Get Request parameters. In case on validation error it will return
	 * error code either 0007 or 0008.
	 *  
	 * @see BaseService#addOptionalGetParam
	 * @param apiVO
	 * @return
	 */
	private boolean validateGetParams(APIRequestVO apiVO) {
		boolean validation  = true; // it is true , don't make it false
		for (String key :requiredGetParam.keySet()) {
			String obj = apiVO.getRequestFromGetDelete().get(key);

			validation  = false;
			if (obj != null) {
				DataTypes dataType = requiredGetParam.get(key);
				validation = DataTypes.validate(obj, dataType);
			}
			if (validation == false) {
				this.errorString = ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS.getMessage() + ":" + key;
				this.errorCode = ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS.getCode();
				break;
			}
		}

		if (validation == true ) {
			for (String key :optionalGetParam.keySet()) {
				String obj = apiVO.getRequestFromGetDelete().get(key);
				validation  = false;
				if (obj != null) {
					DataTypes dataType = optionalGetParam.get(key);
					validation = DataTypes.validate(obj, dataType);
				} else { // it is optional so null is valid
					validation = true;
				}
				if (validation == false) {
					this.errorString = ResultsCode.INVALID_OPTIONAL_PARAMS.getMessage() + ":" + key;
					this.errorCode = ResultsCode.INVALID_OPTIONAL_PARAMS.getCode();
					break;
				}
			}
		}
		return validation;
	}
	/**
	 * @see #addRequiredURLParam
	 * @param apiVO
	 * @return
	 */
	private boolean validateURLParams(APIRequestVO apiVO) {
		boolean validation  = true;
		// this is true, don't make it false
		//Integer buyerId  = apiVO.getBuyerIdInteger();
		//Integer buyerResourceId = apiVO.getBuyerResourceIdInteger();
		String soId = apiVO.getSOId();
		for (String key :requiredURLParam.keySet()) {
			Object obj = (Object)apiVO.getProperty(key);			
			validation  = false;
			if (obj != null) {
				DataTypes dataType = requiredURLParam.get(key);
				validation = DataTypes.validate(obj, dataType);
			}
			if (validation == false) {
				this.errorString = ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS.getMessage() + ":" + key;
				this.errorCode = ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS.getCode();
				break;
			}
		}

		try {
			if (validation == true) {
				APIValidator.getInstance().validateEntity(apiVO);

				if (soId != null){
					// SO might not present in the message
					APIValidator.getInstance().validateSO(soId,apiVO);
				}
			}
		} catch (APIValidatationException e) {
			this.errorCode = e.getCode();
			this.errorString = e.getMessage();
			validation = false;
		}
		return validation;
	}
	private boolean validateIdentificaiton(APIRequestVO apiVO) {
		try {

			APIValidator.getInstance().validateEntity(apiVO);
			return true;

		} catch (APIValidatationException e) {
			this.errorCode = e.getCode();
			this.errorString = e.getMessage();
		}
		return false;

	}


	public void setSecurityProcess(SecurityProcess securityProcess) {
		BaseService.securityProcess = securityProcess;
	}
	public static Object loadBeanFromContext(String beanName) {
		return MPSpringLoaderPlugIn.getCtx().getBean(beanName);

	}

	public Map<String, DataTypes> getRequiredGetParam() {
		return requiredGetParam;
	}

	public void setRequiredGetParam(Map<String, DataTypes> requiredGetParam) {
		this.requiredGetParam = requiredGetParam;
	}

	public Map<String, DataTypes> getOptionalGetParam() {
		return optionalGetParam;
	}

	public void setOptionalGetParam(Map<String, DataTypes> optionalGetParam) {
		this.optionalGetParam = optionalGetParam;
	}
	/**
	 * Use this method to validate optional get request parameters. 
	 * Validator return with error code 0007 if the required parameter holds 
	 * invalid value. If the parameter is missing in the request validator will
	 * not give any error as it is an optional.
	 *  
	 * @param param
	 * @param type
	 */
	public void addOptionalGetParam(String param, DataTypes type) {
		this.optionalGetParam.put(param.toLowerCase(), type);
	}
	public void validateBuyerId(String buyerIdParam) {
		this.buyerIdParam = buyerIdParam;
	}
	public static ServiceOrder getSO(String soId) {
		ServiceOrder serviceOrder = null;
		try {
			serviceOrder = getServiceOrderBOStatic().getServiceOrder(soId);
		} catch (BusinessServiceException e) {
			logger.warn("Get SO Error:" + e.getMessage());
		}
		return serviceOrder;
	}
	/**
	 * Returns the list of service orders for the group id. SL-15642
	 * @param groupId : Service order group Id
	 * @return List<ServiceOrder>. Returns null for invalid group order ID
	 * */
	public static ServiceOrder getGroupedSO(String groupId) {
		ServiceOrder listServiceOrder = null;
		try {
			listServiceOrder = getServiceOrderBOStatic().getGroupedServiceOrders(groupId);
		} catch (BusinessServiceException e) {
			logger.warn("Get SO Error:" + e.getMessage());
		}
		return listServiceOrder;
	}
	/**
	 * @param number
	 * @param source
	 * @return
	 * Mask the bank account and credit card number before returning to the client.
	 */
	public String mask(String number, SourceType source) throws StringIndexOutOfBoundsException {
		Integer digits;
		Integer maskDigits;
		//Flag to by pass the masking logic if data is not correct. This flag should never be 
		//set after all validations are in place.    
		if (number == null) {
			return number;

		}

		StringBuilder sb = new StringBuilder();


		digits = number.length();
		maskDigits = digits - 4;

		for(int i = 0; i < digits; i++) {

			if (i < maskDigits) {

				sb.append('*');

			} else {

				sb.append(number.charAt(i));

			}

		}

		return sb.toString();
	}

	public boolean validateBank(Account account) {
		//Check if the account  type is not CC.
		if(account.getAccountTypeId() == 30){
			return false;
		}
		if (account.getAccountDescription() != null
				&& account.getAccountDescription().length() > 50) {
			return false;
		}
		if (account.getAccount_type_id() == null) {
			return false;
		}
		if (account.getRoutingNumber() == null
				|| account.getRoutingNumber().length() < 1) {
			return false;

		}
		if (account.getRoutingNumber() != null
				&& (account.getRoutingNumber().length()>0 && account.getRoutingNumber().length() < 9)) {
			return false;
		}
		if (account.getRoutingNumber() != null && account.getRoutingNumber().length() == 9) {

			if (!account.getRoutingNumber().startsWith("X")) {
				try {

					boolean validRoutingNumber = this
							.validateRoutingNumber(account.getRoutingNumber());
					if (!validRoutingNumber) {
						return false;
					}
				} catch (NumberFormatException nf) {
					return false;
				}

			}

			if (account.getAccount_no() == null
					|| account.getAccount_no().length() < 1) {
				return false;

			}
			if (account.getAccount_no() != null
					&& account.getAccount_no().length() > 0 && (account.getAccount_no().length() > 17 || account.getAccount_no().length()< 3)) {
				return false;
			}
		}
		return true;

	}
	public boolean validateRoutingNumber(String routingNo){
		char[] routingChar = routingNo.toCharArray();
		char[] weightChar = {'3','7','1','3','7','1','3','7'};
		double mulval = 0;
		double divval = 0;
		int j = 0;
		for(int i=0; i<8; i++){
			mulval = mulval + new Integer(Character.toString(routingChar[j]))* new Integer(Character.toString(weightChar[j]));
			j++;
		}
		divval = Math.ceil((mulval/10));
		divval = divval*10;
		double checkDigit = divval - mulval;
		double lastroutingDigit = new Double(Character.toString(routingNo.charAt(8)));
		if(checkDigit == lastroutingDigit){
			return true;
		}else{
			return false;
		}
	}



	private static IServiceOrderBO getServiceOrderBOStatic() {
		if (serviceOrderBO == null) {
			serviceOrderBO = (IServiceOrderBO)loadBeanFromContext("soBOAOP");
		}
		return serviceOrderBO;
	}

	public static void setServiceOrderBOStatic(IServiceOrderBO serviceOrderBO) {
		BaseService.serviceOrderBO = serviceOrderBO;
	}

	public APIFieldMasker getApiFieldMasker() {
		if (apiFieldMasker == null) {
			apiFieldMasker = (APIFieldMasker)loadBeanFromContext("apiFieldMasker");
		}
		return apiFieldMasker;
	}

	public void setApiFieldMasker(APIFieldMasker apiFieldMasker) {
		this.apiFieldMasker = apiFieldMasker;
	}
	public boolean isOrderPriceAboveMaxTransactionLimit( ServiceOrder so , SecurityContext securityContext, boolean isNew) throws BusinessServiceException 
	{

		double max = OrderConstants.NO_SPEND_LIMIT;
		Double labor  = so.getSpendLimitLabor()!= null ? so.getSpendLimitLabor() :0.0;
		Double parts  = so.getSpendLimitParts() != null ? so.getSpendLimitParts() :0.0;
		Double postingFee = so.getPostingFee() != null ? so.getPostingFee() : 0.0;
		double total =  0.0;
		total = labor + parts + postingFee;
		if (!isNew){
			double increasedSpendLimit  = so.getIncreaseSpendLimit() != null ?so.getIncreaseSpendLimit().doubleValue() :0.0;
			if(so.getPriceModel().equalsIgnoreCase("NAME_PRICE"))

				total = MoneyUtil.subtract(increasedSpendLimit , total);

			else 
				total = increasedSpendLimit;

		}
		if (null != securityContext) {
			max = securityContext.getMaxSpendLimitPerSO();
		}
		// check total against Max spend limit
		if (max == OrderConstants.NO_SPEND_LIMIT || max >= total) {
			return false;
		} else {
			return true;
		}

	}
	/**
	 * Used to log request and response fot API.
	 * @param String request, String methodType, String apiName, APIRequestVO apiVO,String response, String loggingType,String status
	 * @return none
	 */

	public void logAPIHistory(String request, String methodType, String apiName, APIRequestVO apiVO,String response, String loggingType,String status) {
		Integer loggingId =null;
		try{
			loggingId = apiLoggingBO.logAPIRequest(request,methodType,apiName,apiVO.getProviderFirmId(),response,loggingType,status); 
		}catch (Exception e) {
			logger.error("Exception in logging request for HI and ignoring"+ e.getMessage());
		}
	} 
	/**
	 * This method will fetch the api logging switch value.
	 * @param none
	 * @return String
	 */

	public String apiLoggingSwitch() {
		String keyName = "/API/Security/Application/APILoggingSwitch";
		String key = (String)SimpleCache.getInstance().get(keyName);
		if(null==key){
			try{
				key = apiLoggingBO.apiLoggingSwitch();
				SimpleCache.getInstance().put(keyName, key, SimpleCache.TEN_MINUTES);
			}catch (Exception e) {
				logger.error("Exception in retrieving api logging key: "+ e.getMessage());
			}
		}

		return key;

	}
	//If we are not using this method, remove it
	public void updateAPIHistory(Integer logginId, String responseXML,String status) {
		try{
			apiLoggingBO.logAPIHistoryResponse(logginId,responseXML,status);
		}catch (Exception e) {
			logger.error("Exception in logging response for HI and ignoring"+ e.getMessage());
		}
	}
	public String logStatus(Results results) {
		String status=null;

		if(null!=results.getResult() && !results.getResult().isEmpty())
		{

			List<Result> resultList =results.getResult();

			if(null!=resultList && !resultList.isEmpty()){
				status= ProviderConstants.SUCCESS;
			}
		}else if(null!=results.getError() && !results.getError().isEmpty()){
			status= ProviderConstants.ERROR;
		}
		return status;
	}
	public IAPILoggingBO getApiLoggingBO() {
		return apiLoggingBO;
	}

	public void setApiLoggingBO(IAPILoggingBO apiLoggingBO) {
		this.apiLoggingBO = apiLoggingBO;
	}
}
