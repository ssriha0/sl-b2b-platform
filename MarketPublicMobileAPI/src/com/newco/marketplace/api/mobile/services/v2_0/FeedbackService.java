package com.newco.marketplace.api.mobile.services.v2_0;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.LpnResponse;
import com.newco.marketplace.api.mobile.beans.feedback.FeedbackRequest;
import com.newco.marketplace.api.mobile.beans.feedback.FeedbackResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOManagementBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.mobile.FeedbackVO;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/02/06
 * for feedback API for mobile
 *
 */
@APIRequestClass(FeedbackRequest.class)
@APIResponseClass(FeedbackResponse.class)
public class FeedbackService  extends BaseService{
    
	private Logger logger = Logger.getLogger(FeedbackService.class);
	private IMobileSOManagementBO mobileSOManagementBO;
	
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute method of FeedbackService");
		FeedbackResponse feedbackResponse = null;
		List<Result> sucesses = new ArrayList<Result>();
		Results results = new Results();
		List<ErrorResult> errors = null;
		Integer providerResourceId = apiVO.getProviderResourceId();
		FeedbackRequest feedbackRequest =  (FeedbackRequest) apiVO.getRequestFromPostPut();
		errors = validateRequest(feedbackRequest,providerResourceId);
		if(null!=errors && !errors.isEmpty()){
			results.setError(errors);
			feedbackResponse = new FeedbackResponse();
			feedbackResponse.setResults(results);
			return feedbackResponse;
		}
		FeedbackVO feedbackVO = mapFeedbackRequestToVO(feedbackRequest,apiVO.getProviderId(),providerResourceId);
		if(null != feedbackVO){
			// insert feedback details
			try{
				mobileSOManagementBO.insertFeedbackDetails(feedbackVO);
			}
			catch(BusinessServiceException e){
				
				logger.info("Exception in insert feedback details"+e.getMessage());
			}
		}
		sucesses = new ArrayList<Result>();
		sucesses = addSuccess(sucesses,
				ResultsCode.SUCCESSFULL_APP_FEEDBACK
				.getCode(),
				ResultsCode.SUCCESSFULL_APP_FEEDBACK
				.getMessage());
		results.setResult(sucesses);
		feedbackResponse = new FeedbackResponse();
		feedbackResponse.setResults(results);	
		return feedbackResponse;
	}
	
	


	/**
	 * @param feedbackRequest
	 * @param provideResourceId
	 * @return
	 */
	private List<ErrorResult> validateRequest(FeedbackRequest feedbackRequest, Integer provideResourceId) {
		
		List<ErrorResult> validationErrors = null;
		if(!validateUsernameForResourceId(feedbackRequest.getUserName(),provideResourceId)){
			validationErrors = new ArrayList<ErrorResult>();
			addError(validationErrors, ResultsCode.INVALID_USERID.getCode(), ResultsCode.INVALID_USERID.getMessage());
		}
		return validationErrors;
	}



	/**
	 * @param userName
	 * @param provideResourceId
	 * @return
	 * 
	 * to validate user name
	 * 
	 */
	private boolean validateUsernameForResourceId(String userName,
			Integer provideResourceId) {
		
		SecurityContext securityContext = null;
		securityContext = getSecurityContextForVendor(provideResourceId);
		if(null!=securityContext){
			String secUserName = securityContext.getUsername();
			if(secUserName!=null && secUserName.equalsIgnoreCase(userName)){
				return true;
			}

		}
		return false;
	}



	/**
	 * @param feedbackRequest
	 * @param resourceId 
	 * @param firmId 
	 * @return
	 * 
	 * to map the request object to VO
	 * 
	 */
	private FeedbackVO mapFeedbackRequestToVO(FeedbackRequest feedbackRequest, String firmId, Integer resourceId) {
		
			FeedbackVO feedbackVO = new FeedbackVO();
			feedbackVO.setFirmId(Integer.parseInt(firmId));
			feedbackVO.setResourceId(resourceId);
			feedbackVO.setUserName(feedbackRequest.getUserName());
			feedbackVO.setComments(feedbackRequest.getComments());
			feedbackVO.setEmail(feedbackRequest.getEmail());
			feedbackVO.setDeviceVersion(feedbackRequest.getDeviceVersion());
			feedbackVO.setSourceVersion(feedbackRequest.getSourceVersion());
			return feedbackVO;
			
	}



	// Mapping the response xml to SendMessageResponse class.
	public Object deserializeLpnResponse(String responseXml,Class<?> classz) {
		XStream xstreamResponse = new XStream(new DomDriver());
		xstreamResponse.processAnnotations(classz);
		LpnResponse response = (LpnResponse) xstreamResponse.fromXML(responseXml);
		return response;
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
	
	private List<Result> addSuccess(List<Result> sucesses, String code,
			String message) {
		Result result = new Result();
		result.setCode(code);
		result.setMessage(message);
		sucesses.add(result);
		return sucesses;
	}

	private List<ErrorResult> addError(List<ErrorResult> validationErrors,
			String code, String message) {
		ErrorResult result = new ErrorResult();
		result.setCode(code);
		result.setMessage(message);
		validationErrors.add(result);
		return validationErrors;
	}
	
	public IMobileSOManagementBO getMobileSOManagementBO() {
		return mobileSOManagementBO;
	}

	public void setMobileSOManagementBO(IMobileSOManagementBO mobileSOManagementBO) {
		this.mobileSOManagementBO = mobileSOManagementBO;
	}
	
}
