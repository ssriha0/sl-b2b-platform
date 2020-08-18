package com.newco.marketplace.api.services.survey;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.survey.SurveyRequest;
import com.newco.marketplace.api.beans.survey.SurveyResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.survey.ExtendedSurveyBO;
import com.newco.marketplace.dto.vo.survey.ExtendedSaveSurveyVO;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;

/**
 * SLT-1649
 * 
 * @author ababu0
 *
 */
@APIResponseClass(SurveyResponse.class)
@APIRequestClass(SurveyRequest.class)
public class ExtendedSurveyService extends BaseService {

	private ExtendedSurveyBO extendedSurveyBO;

	private static final Logger logger = Logger.getLogger(ExtendedSurveyService.class);

	public ExtendedSurveyService() {
		super(PublicAPIConstant.SURVEY_REQUEST_XSD, PublicAPIConstant.SURVEY_RESPONSE_XSD,
				PublicAPIConstant.SURVEY_RESPONSE_NAMESPACE, PublicAPIConstant.SURVEY_RESOURCES_SCHEMAS,
				PublicAPIConstant.SURVEY_RESPONSE_SCHEMALOCATION, SurveyRequest.class, SurveyResponse.class);
	}

	@SuppressWarnings("unchecked")
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering into method : surveyService.execute()");
		SurveyRequest request = (SurveyRequest) apiVO.getRequestFromPostPut();
		Results results = null;
		ExtendedSaveSurveyVO saveSurveyVO = null;
		Integer buyerId = null;
		Long responseHdrId = null;
		String subSurveyType = null;
		String soId = null;
		Integer surveyId = null;
		String surveyType= null;
		List<Integer> listOfSurveyIds= null;
		String encryptedKey= null;
		SecurityContext securityContext = null;
		SurveyResponse response = new SurveyResponse();
		String returnValue = null;
		if (request == null) {
			results = Results.getError(ResultsCode.INVALIDXML_ERROR_CODE.getMessage(),
					ResultsCode.INVALIDXML_ERROR_CODE.getCode());
			returnValue = PublicAPIConstant.API_RESULT_FAILURE;
		} else {
			try {
				encryptedKey=request.getKey();
				soId = extendedSurveyBO.getServiceOrderID(encryptedKey);
				buyerId = extendedSurveyBO.getBuyerId(soId);
				surveyType=extendedSurveyBO.getSurveyType(encryptedKey);
				listOfSurveyIds=extendedSurveyBO.getSurveyIds(encryptedKey);
				securityContext = super.getSecurityContextForBuyerAdmin(buyerId);
				saveSurveyVO = prepareSaveSurveyVO(request, securityContext, buyerId);
				saveSurveyVO.setServiceOrderID(soId);
				saveSurveyVO.setSurveyType(surveyType);
				saveSurveyVO.setListOfSurveyIds(listOfSurveyIds);
				responseHdrId = extendedSurveyBO.getResponseHDRId(soId);
				if (null != responseHdrId) {
					surveyId = extendedSurveyBO.fetchSurveyId(responseHdrId);
					subSurveyType = extendedSurveyBO.fetchSubSurveyType(surveyId);
					saveSurveyVO.setSubSurveyType(subSurveyType);
					switch (subSurveyType) {
					case PublicAPIConstant.CSAT:
						updateSurveyRatings(responseHdrId,saveSurveyVO,subSurveyType,saveSurveyVO.getCsat().getRating());
						returnValue = extendedSurveyBO.updateSaveSurveyDetails(PublicAPIConstant.CSAT, responseHdrId,
								saveSurveyVO);
						if (null != request.getNps())
							returnValue = extendedSurveyBO.insertSaveSurveyDetails(PublicAPIConstant.NPS, saveSurveyVO);
						break;

					case PublicAPIConstant.NPS:
						updateSurveyRatings(responseHdrId,saveSurveyVO,subSurveyType,saveSurveyVO.getNps().getRating());
						returnValue = extendedSurveyBO.updateSaveSurveyDetails(PublicAPIConstant.NPS, responseHdrId, saveSurveyVO);
						if (null != request.getCsat())
							returnValue = extendedSurveyBO.insertSaveSurveyDetails(PublicAPIConstant.CSAT,
									saveSurveyVO);
						break;

					default:
						break;
					}
					extendedSurveyBO.updateResponseSOMetadata(saveSurveyVO);
				} else {
					if (null != request.getCsat())
						returnValue = extendedSurveyBO.insertSaveSurveyDetails(PublicAPIConstant.CSAT, saveSurveyVO);
					else if (null != request.getNps())
						returnValue = extendedSurveyBO.insertSaveSurveyDetails(PublicAPIConstant.NPS, saveSurveyVO);
					extendedSurveyBO.insertResponseSOMetadata(saveSurveyVO);
				}

			} catch (BusinessServiceException e) {
				logger.error("ExtendedSurveyService-->execute()-->Exception-->" + e.getMessage(), e);
				results = Results.getError(e.getMessage(),
						ResultsCode.GENERIC_ERROR.getCode());
				returnValue=PublicAPIConstant.API_RESULT_FAILURE;
			}
			catch (Exception e) {
				logger.error("ExtendedSurveyService-->execute()-->Exception-->" + e.getMessage(), e);
				results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),
						ResultsCode.INTERNAL_SERVER_ERROR.getCode());
				returnValue=PublicAPIConstant.API_RESULT_FAILURE;
			}
		}

		if (returnValue.equalsIgnoreCase(PublicAPIConstant.API_RESULT_SUCCESS)) {
			results = Results.getSuccess(ResultsCode.SUCCESS.getMessage());

		}

		response.setResults(results);
		return response;
	}
	
	

	private ExtendedSaveSurveyVO prepareSaveSurveyVO(SurveyRequest request, SecurityContext securityContext,
			Integer buyerId) {
		ExtendedSaveSurveyVO extendedSaveSurveyVO = new ExtendedSaveSurveyVO();
		if (request != null) {
			extendedSaveSurveyVO.setBuyerId(buyerId);
			extendedSaveSurveyVO.setEntityId(securityContext.getVendBuyerResId());
			extendedSaveSurveyVO.setModifiedBy(securityContext.getUsername());
			extendedSaveSurveyVO.setCsat(request.getCsat());
			extendedSaveSurveyVO.setNps(request.getNps());
			extendedSaveSurveyVO.setAgreed(request.isAgreed());
			extendedSaveSurveyVO.setSubmit(request.isSubmit());
		}
		return extendedSaveSurveyVO;
	}
	
	private void updateSurveyRatings(Long responseHdrId,ExtendedSaveSurveyVO saveSurveyVO,String subSurveyType,double newSurveyRating) throws BusinessServiceException {
		Double surveyRating=null;
		SurveyRatingsVO surveyRatingsVO = null;
		Integer entityId=null;
		try {
			surveyRating=extendedSurveyBO.getSurveyEntityRatingDetails(responseHdrId);
			entityId=extendedSurveyBO.fetchVendorId(saveSurveyVO);
			if(surveyRating != null){
				surveyRatingsVO=extendedSurveyBO.getResourceRating(saveSurveyVO,subSurveyType,entityId);				
				double avgRating=getUpdatedAvgRating(surveyRatingsVO,surveyRating,newSurveyRating);				
				extendedSurveyBO.updateEntityRatings(avgRating,saveSurveyVO,subSurveyType,entityId);
				updateBuyerRating(saveSurveyVO,surveyRating,subSurveyType,newSurveyRating,entityId);
			}
		} catch (Exception ex) {
			logger.error("exception while fetching Entity Survey Rating details" + ex.getMessage());
			throw new BusinessServiceException(ex.getMessage(), ex);
		}
	}

	private void updateBuyerRating(ExtendedSaveSurveyVO saveSurveyVO,
			double surveyRating,String subSurveyType,double newSurveyRating,Integer entityId) throws BusinessServiceException {
		SurveyRatingsVO surveyRatingsVO = null;
		try {
			surveyRatingsVO=extendedSurveyBO.getSurveyBuyerRatingDetails(saveSurveyVO,subSurveyType,entityId);
			double avgBuyerRating=getUpdatedAvgRating(surveyRatingsVO,surveyRating,newSurveyRating);
			extendedSurveyBO.updateBuyerRatings(avgBuyerRating,saveSurveyVO,subSurveyType,entityId);
		} catch (Exception ex) {
			logger.error("exception while fetching Buyer Survey Rating details" + ex.getMessage());
			throw new BusinessServiceException(ex.getMessage(), ex);
		}
	}
	
	private double getUpdatedAvgRating(SurveyRatingsVO surveyRatingsVO,double surveyRating,double newSurveyRating){
		double historicalRatingTotal=surveyRatingsVO.getHistoricalRating()*surveyRatingsVO.getNumberOfRatingsReceived();
		double latestRatingTotal=historicalRatingTotal-surveyRating+newSurveyRating;
		int newNumberOfRatingRecieved=surveyRatingsVO.getNumberOfRatingsReceived();
		double avgBuyerRating=latestRatingTotal/newNumberOfRatingRecieved;
		return avgBuyerRating;
	}
	public ExtendedSurveyBO getExtendedSurveyBO() {
		return extendedSurveyBO;
	}

	public void setExtendedSurveyBO(ExtendedSurveyBO extendedSurveyBO) {
		this.extendedSurveyBO = extendedSurveyBO;
	}

}
