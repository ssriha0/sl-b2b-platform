package com.newco.marketplace.api.services.search;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.SurveyConstants;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.search.providerProfile.AddReviewRequest;
import com.newco.marketplace.api.beans.search.providerProfile.AddReviewResponse;
import com.newco.marketplace.api.beans.search.providerProfile.Cleanliness;
import com.newco.marketplace.api.beans.search.providerProfile.Communication;
import com.newco.marketplace.api.beans.search.providerProfile.Professionalism;
import com.newco.marketplace.api.beans.search.providerProfile.Quality;
import com.newco.marketplace.api.beans.search.providerProfile.Timeliness;
import com.newco.marketplace.api.beans.search.providerProfile.Value;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.validators.RequestValidator;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.survey.ISurveyBO;
import com.newco.marketplace.dto.vo.survey.SurveyQuestionVO;
import com.newco.marketplace.dto.vo.survey.SurveyResponseVO;
import com.newco.marketplace.dto.vo.survey.SurveyVO;

enum SurveyCategories {
    Timeliness, Communication, Professionalism, Quality, Value, Cleanliness 
}

@Namespace("http://www.servicelive.com/namespaces/addReview")
@APIRequestClass(AddReviewRequest.class)
@APIResponseClass(AddReviewResponse.class)
public class AddProviderReviewService extends BaseService {
	private ISurveyBO surveyBO;
	private RequestValidator requestValidator;
	
	private Logger logger = Logger.getLogger(AddProviderReviewService.class);
	/**
	 * This method is for adding funds to  wallet.
	 * 
	 * @param fromDate String,toDate String
	 * @return String
	 */
	
	public AddProviderReviewService () {
		/*
		super (PublicAPIConstant.ProviderReviews.ADD_REVIEW_REQUEST_XSD,
				PublicAPIConstant.ProviderReviews.ADD_REVIEW_RESPONSE_XSD, 
				PublicAPIConstant.ProviderReviews.ADD_REVIEW_NAMESPACE, 
				PublicAPIConstant.ProviderReviews.RESOURCES_SCHEMAS,
				PublicAPIConstant.ProviderReviews.ADD_REVIEW_SCHEMALOCATION,	
				AddReviewRequest.class,
				AddReviewResponse.class);
		*/
	}	

	/**
	 * This method returns the AddProviderReviewResponse
	 * @param APIRequestVO
	 * @return AddProviderReviewResponse
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {		
		logger.info("Entering execute method");	
		AddReviewRequest request  =  (AddReviewRequest) apiVO.getRequestFromPostPut();
		Integer buyerId =  0; 
		Integer providerId =  0; 
		Integer roleType =  (Integer)apiVO.getProperty("RoleType");
		Results results = null;
		AddReviewResponse response = null;
		
		if (request == null){
			results = Results.getError(ResultsCode.INVALIDXML_ERROR_CODE.getMessage(), ResultsCode.INVALIDXML_ERROR_CODE.getCode());
		} else {
			SecurityContext securityContext = null;
			
			
			if (roleType == OrderConstants.BUYER_ROLEID || roleType == OrderConstants.SIMPLE_BUYER_ROLEID) {				
				buyerId = apiVO.getBuyerIdInteger();
				securityContext = super.getSecurityContextForBuyerAdmin(buyerId);
				
			} else {
				providerId = apiVO.getProviderIdInteger();
				securityContext = super.getSecurityContextForVendorAdmin(providerId);
				super.namespace=PublicAPIConstant.ProviderReviews.ADD_REVIEW_PRO_NAMESPACE;
				super.requestXsd=PublicAPIConstant.ProviderReviews.ADD_REVIEW_PRO_REQUEST_XSD;
				super.responseXsd=PublicAPIConstant.ProviderReviews.ADD_REVIEW_PRO_RESPONSE_XSD;
				super.schemaLocationReq=PublicAPIConstant.SO_RESOURCES_SCHEMAS_PRO;
				super.schemaLocationRes=PublicAPIConstant.SO_RESOURCES_SCHEMAS_PRO;
			}
			
			//Prepare SurveyVO
			try{
				 if(StringUtils.isEmpty(request.getComment())){
					 results = Results.getError(ResultsCode.RATE_BUYER_COMMENTS_EMPTY.getMessage(), ResultsCode.RATE_BUYER_COMMENTS_EMPTY.getCode());
				 }
				 else{
					 SurveyVO surveyVO = prepareSurveyVO(request,securityContext);
					 if (surveyVO.isClosed()){
						 String ret = surveyBO.saveResponse(surveyVO, securityContext, null);
						 if (ret == null) {
							 results = Results.getError("Unable to add reviews. Either information is invalid or review is already present." , ResultsCode.GENERIC_ERROR.getCode());
						 } else {
						   results = Results.getSuccess();
						 }
					 } else {
						 throw (new DataServiceException("Service Order: "+surveyVO.getServiceOrderID()+" was not closed."));
					 }
				 }
				 } catch(DataServiceException exp) {
				results = Results.getError(exp.getMessage(), ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			}
		}

		//ISurveyBO		
		response = new AddReviewResponse(results);		
		logger.info("Exiting execute method");	
		return response;
	}
 
	/**
	 * This method returns the SurveyVO
	 * @param AddReviewRequest
	 * @param SecurityContext
	 * @return SurveyVO
	 */
	private SurveyVO prepareSurveyVO(AddReviewRequest request,SecurityContext securityContext)  throws DataServiceException{
		SurveyVO surveyVO = new SurveyVO();		
		int roleId = securityContext.getRoleId();
		String serviceOrderId = request.getSoId();
		if (roleId == (OrderConstants.BUYER_ROLEID) || roleId == (OrderConstants.SIMPLE_BUYER_ROLEID)){
			surveyVO.setSurveyType(SurveyConstants.SURVEY_TYPE_PROVIDER);
			surveyVO.setEntityType(SurveyConstants.ENTITY_BUYER);
			surveyVO.setUserId(securityContext.getVendBuyerResId());
		} else if(roleId == (OrderConstants.PROVIDER_ROLEID)){
			surveyVO.setSurveyType(SurveyConstants.SURVEY_TYPE_BUYER);
			surveyVO.setEntityType(SurveyConstants.ENTITY_PROVIDER);
			surveyVO.setUserId(securityContext.getVendBuyerResId());			
		}
		
		surveyVO.setServiceOrderID(serviceOrderId);
		try {
			//Fetch the questions 
			surveyVO = surveyBO.retrieveQuestions(surveyVO,null);
		
			ArrayList<SurveyQuestionVO> questions = surveyVO.getQuestions();
			if(null!=questions && questions.size() > 0){
				int iQuesSize = questions.size();
				SurveyQuestionVO surveyQuestionVO = null;
				int iAnswerId = 0;
				SurveyResponseVO surveyResponseVO = null;

				for(int i=0; i<iQuesSize; i++){
					surveyQuestionVO = (SurveyQuestionVO)questions.get(i);
					iAnswerId = getAnswerId(request,surveyQuestionVO.getQuestionText());
				
					ArrayList<SurveyResponseVO> responses = new ArrayList<SurveyResponseVO>();
					surveyResponseVO = new SurveyResponseVO();
					
					surveyResponseVO.setAnswerId(iAnswerId);
					surveyResponseVO.setSurveyId(surveyVO.getSurveyId());
					surveyResponseVO.setEntityTypeId(surveyVO.getEntityTypeId());
					surveyResponseVO.setEntityId(surveyVO.getUserId());
					surveyResponseVO.setQuestionId(surveyQuestionVO.getQuestionId());
					responses.add(surveyResponseVO);
					surveyQuestionVO.setResponses(responses);
				}
			}	
			surveyVO.setSurveyComments(UIUtils.encodeSpecialChars(request.getComment()));
		}catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring",e);
			throw e;
		}
		return surveyVO;
	}

	/**
	 * This method returns the one of the AnswerId 
	 * @param AddReviewRequest
	 * @param questionText
	 * @return int
	 */
	private int getAnswerId(AddReviewRequest request, String questionText){
		if(questionText!=null){
			switch(SurveyCategories.valueOf(questionText)){
				case Timeliness:
					return Timeliness.getAnswerId(request.getTimeliness());
				case Communication:
					return Communication.getAnswerId(request.getCommunication());
				case Professionalism:
					return Professionalism.getAnswerId(request.getProfessionalism());
				case Quality:
					return Quality.getAnswerId(request.getQuality());
				case Value:
					return Value.getAnswerId(request.getValue());
				case Cleanliness:
					return Cleanliness.getAnswerId(request.getcleanliness());					
			}
		}
		return 0;
	}
	
  	public ISurveyBO getSurveyBO() {
		return surveyBO;
	}

	public void setSurveyBO(ISurveyBO surveyBO) {
		this.surveyBO = surveyBO;
	}

	public RequestValidator getRequestValidator() {
		return requestValidator;
	}

	public void setRequestValidator(RequestValidator requestValidator) {
		this.requestValidator = requestValidator;
	}
}



