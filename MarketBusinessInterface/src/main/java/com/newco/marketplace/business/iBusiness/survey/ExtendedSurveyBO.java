package com.newco.marketplace.business.iBusiness.survey;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;

import java.util.Date;
import java.util.List;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.survey.BuyerSurveyConfigVO;
import com.newco.marketplace.dto.vo.survey.ExtendedSaveSurveyVO;
import com.newco.marketplace.dto.vo.survey.SurveyBuyerQuestionDetailsVO;
import com.newco.marketplace.dto.vo.survey.SurveyOptionVO;
import com.newco.marketplace.dto.vo.survey.SurveyQuestionsAnswersResponse;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;

/**
 * Created by kjain on 12/31/2018.
 */
public interface ExtendedSurveyBO {

	public boolean validateSurvey(String serviceOrderId) throws BusinessServiceException;

	public SurveyBuyerQuestionDetailsVO fetchQuestionnaire(String encryptedKey) throws BusinessServiceException;

	public void saveBuyerConfiguration(Integer buyerid, Integer surveyOptionId, String surveyType, String user)
			throws BusinessServiceException;

	public List<SurveyOptionVO> getSurveyOptions() throws BusinessServiceException;

	public BuyerSurveyConfigVO getBuyerSurveySelectedDetails(int buyerId) throws BusinessServiceException;

	// SLT-1649
	//public String saveSurveyDetails(ExtendedSaveSurveyVO surveyVO) throws Exception;

	public Integer getBuyerId(String soId) throws Exception;
	
	public String updateSaveSurveyDetails(String subSurveyType, Long responseHDRId, ExtendedSaveSurveyVO saveSurveyVO) throws Exception;
		
	
	public String insertSaveSurveyDetails(String subSurveyType, ExtendedSaveSurveyVO saveSurveyVO) throws Exception;
	
	public Integer fetchSurveyId(Long responseHDRId) throws Exception;
	
	public Long getResponseHDRId(String soId) throws Exception;
	
	public String fetchSubSurveyType(Integer surveyId) throws Exception;
	
	public String getServiceOrderID(String key) throws BusinessServiceException;
	
	public String getSurveyType(String key) throws BusinessServiceException;
	
	public List<Integer> getSurveyIds(String key) throws BusinessServiceException; 
	
	public String insertResponseSOMetadata(ExtendedSaveSurveyVO saveSurveyVO) throws BusinessServiceException;
	
	public String updateResponseSOMetadata(ExtendedSaveSurveyVO saveSurveyVO) throws BusinessServiceException;

	public String getServiceOrderFlowType(String soId) throws BusinessServiceException;

	void processRatings() throws BusinessServiceException;

	public SurveyQuestionsAnswersResponse getSurveyQuestionsWithAnswers(String soId) throws BusinessServiceException;
	
	public String decryptSoId(String encryptedSoId);

	public Double getSurveyEntityRatingDetails(Long responseHdrId) throws BusinessServiceException;

	public SurveyRatingsVO getResourceRating(ExtendedSaveSurveyVO saveSurveyVO,String subSurveyType,Integer entityId) throws BusinessServiceException;

	public void updateEntityRatings(double avgRating,ExtendedSaveSurveyVO saveSurveyVO, String subSurveyType,Integer entityId) throws BusinessServiceException, DataServiceException;

	public SurveyRatingsVO getSurveyBuyerRatingDetails(ExtendedSaveSurveyVO saveSurveyVO,String subSurveyType,Integer entityId) throws BusinessServiceException, DataServiceException;

	public void updateBuyerRatings(double avgBuyerRating,ExtendedSaveSurveyVO saveSurveyVO, String subSurveyType,Integer entityId) throws DataServiceException;

	public Integer fetchVendorId(ExtendedSaveSurveyVO saveSurveyVO) throws DataServiceException;

}
