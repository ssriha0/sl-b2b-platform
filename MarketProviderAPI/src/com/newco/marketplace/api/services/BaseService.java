
package com.newco.marketplace.api.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.ExcludeValidation;
import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.common.UserIdentificationRequest;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.APIValidatationException;
import com.newco.marketplace.api.common.APIValidator;
import com.newco.marketplace.api.common.DataTypes;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.Identification;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.provider.utils.validators.ReqResValidator;
import com.newco.marketplace.api.security.APIFieldMasker;
import com.newco.marketplace.api.security.SecurityProcess;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOLoggingBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOValidationBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.UnresolvableException;
import com.newco.marketplace.utils.SimpleCache;
import com.newco.marketplace.vo.mobile.MobileSOLoggingVO;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
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

	private static Logger logger = Logger.getLogger(BaseService.class);
	
	public String requestXsd, responseXsd;
	public String namespace;	
	public String schemaLocationReq;
	public String schemaLocationRes;
	private Class<?> requestClass, responseClass; 
	ArrayList<Class> beanClasses = new ArrayList<Class>();
	private String errorString, errorCode;
	private ReqResValidator xmlValidator;
	private static SecurityProcess securityProcess;
	private static IServiceOrderBO serviceOrderBO;
	private IMobileSOValidationBO validationBO;
	private IMobileSOLoggingBO mobileSOLoggingBO;

	public enum RequestType {Put,Post,Delete,Get}
	public enum SourceType {Bank,CC}
	
	private Map<String, DataTypes>  requiredGetParam;
	private Map<String, DataTypes>  optionalGetParam;
	private Map<String, DataTypes>  requiredURLParam; //there is no optional params for obvious purpose.
	private boolean  doValidation = true;
	private APIFieldMasker apiFieldMasker;
	private XStream xstreamRequest, xstreamResponse;
	
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
		
		/*Namespace annoName = this.getClass().getAnnotation(Namespace.class);
		if (annoName != null) {
			this.namespace = annoName.value();
		} else {
			throw new java.lang.RuntimeException("Namespace annotation is not defined in service class:" +
					this.getClass().getName());
		}*/		
		
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
			logger.info("Request::"+(String)apiVO.getRequestFromPostPut());
			request = getRequestObj((String)apiVO.getRequestFromPostPut());
			if (request == null)
				return getFailedResponseXML();
			
			if (this.doValidation) {				
				if (request instanceof UserIdentificationRequest) {
					UserIdentificationRequest ui = (UserIdentificationRequest)request;
					String type  = ui.getIdentification().getType();
					if (type != null) {
						Integer id = ui.getIdentification().getId();
						/*if (type.equalsIgnoreCase(Identification.ID_TYPE_BUYER_RESOURCE_ID)) {
							apiVO.setBuyerResourceId(ui.getIdentification().getId());
						} else {*/
							apiVO.setProviderResourceId(id);
						//}						
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
			return getFailedResponseXML();
		}
		
		IAPIResponse response = null;
		try {
			response =  execute(apiVO);
		} catch (Exception e) { 
			// I know its a bad idea to catch Exception. But this is required to print stack trace.
			// Otherwise CFX will consume the exception and send HTTP 500 error, then we will never
			// get to know the route cause of the problem.
			logger.error("Error in API execution", e);	
			return getFailedResponseXML();
		}
		
		//API Masking
		getApiFieldMasker().checkRules(apiVO.getConsumerKey(), response);
		
		
		String responseXML = getResponseXML(response);
		
		if (logger.isDebugEnabled()){ 
			logger.info(responseXML);
			logger.info("Exiting doSubmit method");
		}		
		
		return responseXML;
	}
	
	
	
	public Integer logSOMobileHistory(String request, Integer actionId,
			Integer resourceId,String soId,String httpMethod) {
		try {
			long loggingStart = System.currentTimeMillis();		
			
			Integer loggingId = mobileSOLoggingBO.logSOMobileHistory(request,
					actionId, resourceId,soId,httpMethod);
			long loggingEnd = System.currentTimeMillis();
			logger.info("Total time taken for logging  :::"+(loggingEnd -loggingStart));
			return loggingId;
		} catch (Exception e) {
			logger.info("exception while logging SO Mobile history" + e);
			return null;
		}
	}
	public Integer logSOMobileHistory(String request, Integer actionId,String httpMethod,APIRequestVO apiVO) {		
		 MobileSOLoggingVO loggingVo = new MobileSOLoggingVO(request,actionId,httpMethod);
		 loggingVo.setSoId(apiVO.getSOId());
		 loggingVo.setCreatedBy(apiVO.getProviderResourceId());
		 loggingVo.setRoleId(apiVO.getRoleId());
		 Integer loggingId =null;
		 try {
		    loggingId = mobileSOLoggingBO.logSOMobileHistory(loggingVo);
		   }catch (Exception e) {
			logger.info("exception while logging SO Mobile history" + e);
			}
		 return loggingId;
	}
	public Integer getResourceId(String username){
		try{
		Integer resourceId=mobileSOLoggingBO.getResourceId(username);
		return resourceId;
		}catch(Exception e){
			logger.info("exception while getting resourceId" + e);
			return null;	
		}

	}
	public void updateSOMobileResponse(Integer loggingId,String response){
		try{
		long loggingStart = System.currentTimeMillis();		
		mobileSOLoggingBO.updateSOMobileResponse(loggingId, response);
		long loggingEnd = System.currentTimeMillis();
		logger.info("Total time taken for update logging  :::"+(loggingEnd -loggingStart));
		}catch(Exception e){
			logger.info("exception while updating SOMobileResponse" + e);
		}
	}

	
	
	private XStream getXstreamResponse() {
		if (xstreamResponse == null) {
			Class<?>[] responseClasses = getClassesArray();
			xstreamResponse = new XStream(new DomDriver());
			xstreamResponse.registerConverter(new ResultConvertor());
			xstreamResponse.registerConverter(new ErrorConvertor());
			xstreamResponse.registerConverter(new DateConverter("yyyy-MM-dd", new String[0]));
			xstreamResponse.addDefaultImplementation(java.sql.Date.class, java.util.Date.class);
			xstreamResponse.processAnnotations(responseClasses);	
		}
		return xstreamResponse;
	}
	
	private XStream getXstreamRequest() {
		if (xstreamRequest == null) {
			Class<?>[] responseClasses = getClassesArray();
			xstreamRequest = new XStream(new DomDriver());
			xstreamRequest.registerConverter(new ResultConvertor());
			xstreamRequest.registerConverter(new ErrorConvertor());
			xstreamRequest.registerConverter(new DateConverter("yyyy-MM-dd", new String[0]));
			xstreamRequest.addDefaultImplementation(java.sql.Date.class, java.util.Date.class);
			xstreamRequest.processAnnotations(responseClasses);	
		}
		return xstreamRequest;
	}
	
	private String getResponseXML(IAPIResponse obj) {					
		logger.info("Entering BaseService getResponseXML");
		XStream xStream = getXstreamResponse();	
		obj.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		obj.setNamespace(this.namespace);
		obj.setSchemaLocation(this.namespace + " " + this.responseXsd);
		String xml  = (String) xStream.toXML(obj);
        logger.info(xml);
        logger.info(this.schemaLocationRes);
        logger.info(this.responseXsd);
        
		if (xmlValidator.validateXML(xml, this.schemaLocationRes, this.responseXsd) == false) {
			this.errorString = ResultsCode.INTERNAL_SERVER_ERROR.getMessage();
			this.errorCode = ResultsCode.INTERNAL_SERVER_ERROR.getCode();
			xml = getFailedResponseXML();
		}
        
		logger.info("Exiting getResponseXML");		
		return xml;
	}
	
	private Object getRequestObj(String request) {		
		if (xmlValidator.validateXML(request, this.schemaLocationReq, this.requestXsd) == false) {
			this.errorString = xmlValidator.getErrorMsg();
			this.errorCode = ResultsCode.INTERNAL_SERVER_ERROR.getCode();
			return null;
		}
		
		try {		
			XStream xstream = getXstreamRequest();
			Object obj = (Object) xstream.fromXML(request);
			logger.info("Exiting getRequestObj()");		
			return obj;		
		} catch (Exception e) {
			e.printStackTrace();
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
	
	
	private String getFailedResponseXML() {
		return getFailureResponse(this.errorString,
				this.errorCode, 
				this.schemaLocationRes, 
				this.namespace, 
				this.responseXsd);
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
	private String getFailureResponse(String message,String errorCode, String schemaLocation, String namespace, String schemaInstance) {
		logger.info("Entering getFailureResponse()");
		IAPIResponse response;
		try {
			response = (IAPIResponse)this.responseClass.newInstance();
		} catch (InstantiationException e) {						
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {			
			e.printStackTrace();
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
		String responseXML = getCreateResponseXML(response);
		logger.info("Leaving getFailureResponse()");
		return responseXML;
	}
	
	/**
	 * This method is for the conversion of the createResponse object 
	 * to xml using Xstream conversion .
	 * 
	 * @param createResponse   SOCreateResponse
	 * @return createResponseXml
	 */
	protected String getCreateResponseXML(Object response){
		logger.info("Entering getCreateResponseXML()");
		XStream xstream = getXstreamResponse();		
		String createResponseXml = (String) xstream.toXML(response);
		logger.info("Leaving getCreateResponseXML()");		
		return createResponseXml;
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
		boolean validation  = true;	// this is true, don't make it false
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
				APIValidator.getInstance().validateRole(apiVO);	
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
		
		
		// Returns the contents of the file in a byte array.
		   public  String getBytesFromFile(File file) {
			   try{
		       // Get the size of the file
		       long length = file.length();

		       // You cannot create an array using a long type.
		       // It needs to be an int type.
		       // Before converting to an int type, check
		       // to ensure that file is not larger than Integer.MAX_VALUE.
		       if (length > Integer.MAX_VALUE) {
		           // File is too large
		           throw new IOException("File is too large!");
		       }

		       // Create the byte array to hold the data
		       byte[] bytes = new byte[(int)length];

		       // Read in the bytes
		       int offset = 0;
		       int numRead = 0;

		       InputStream is = new FileInputStream(file);
		       try {
		           while (offset < bytes.length
		                  && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
		               offset += numRead;
		           }
		       } finally {
		           is.close(); 
		       }

		       // Ensure all the bytes have been read in
		       if (offset < bytes.length) {
		           throw new IOException("Could not completely read file "+file.getName());
		       }
		       return new String(bytes);
			   }catch(Exception e){
				   logger.info(""+e);
				   return null;
			   }
		   }
		
		   public Integer validateResourceId(String providerId)
		   {
			   Integer resourceId = null;
			   try{
				   if(StringUtils.isNumeric(providerId)){
					   resourceId =Integer.parseInt(providerId);
				   }
				   return resourceId;
				   
			   }catch(Exception e){
				   logger.info(" error in parsing resource Id"+e);
				   return null;
			   }
		   }
		   
		public Integer validateProviderId(String providerId) throws BusinessServiceException{
				return validationBO.validateProviderId(providerId);
		}
		
		public boolean isValidProvider(String providerId) throws BusinessServiceException
		{
			
			return validationBO.isValidProvider(providerId);
		}
		
		public boolean isValidServiceOrder(String soId) throws BusinessServiceException
		{
			return validationBO.isValidServiceOrder(soId);
		}
		
		public boolean isAuthorizedInViewSODetails(String soId,String resourceId) throws BusinessServiceException
		{
			return validationBO.isAuthorizedInViewSODetails(soId, resourceId);
		}

		public static SecurityProcess getSecurityProcess() {
			return securityProcess;
		}

		public IMobileSOValidationBO getValidationBO() {
			return validationBO;
		}

		public void setValidationBO(IMobileSOValidationBO validationBO) {
			this.validationBO = validationBO;
		}

		public ReqResValidator getXmlValidator() {
			return xmlValidator;
		}

		public void setXmlValidator(ReqResValidator xmlValidator) {
			this.xmlValidator = xmlValidator;
		}

		public IMobileSOLoggingBO getMobileSOLoggingBO() {
			return mobileSOLoggingBO;
		}

		public void setMobileSOLoggingBO(IMobileSOLoggingBO mobileSOLoggingBO) {
			this.mobileSOLoggingBO = mobileSOLoggingBO;
		}

		
		
		
		
		
}
