package com.newco.marketplace.persistence.iDao.survey;


import com.newco.marketplace.dto.vo.survey.BuyerConfig;
import com.newco.marketplace.dto.vo.survey.BuyerRating;
import com.newco.marketplace.dto.vo.survey.BuyerSurveyConfigVO;
import com.newco.marketplace.dto.vo.survey.EntityRating;
import com.newco.marketplace.dto.vo.survey.ExtendedSaveSurveyVO;
import com.newco.marketplace.dto.vo.survey.ExtendedSurveyResponseVO;
import com.newco.marketplace.dto.vo.survey.SODetailVO;
import com.newco.marketplace.dto.vo.survey.SurveyOptionVO;
import com.newco.marketplace.dto.vo.survey.SurveyQuestionAnswerDetailVO;
import com.newco.marketplace.dto.vo.survey.SurveyQuestionnaireDetailVO;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.exception.core.DataServiceException;

import java.util.Date;
import java.util.List;

/**
 * Created by kjain on 12/31/2018.
 */
public interface ExtendedSurveyDAO {
    boolean isSurveySubmitted(String serviceOrderId) throws DataServiceException;
    List<SurveyQuestionnaireDetailVO> getQuestionsForBuyer(String serviceOrderId, List<Integer> surveyIds) throws DataServiceException;
    void saveBuyerConfiguration(BuyerConfig config) throws DataServiceException;
    List<SurveyOptionVO> getSurveyOptions() throws DataServiceException;
   	BuyerSurveyConfigVO getBuyerSurveySelectedDetails(Integer buyerId)throws DataServiceException;
    
   	//SLT-1649
   	String saveSurveyDetails(ExtendedSurveyResponseVO extendedSurveyResponseVO) throws DataServiceException;
   	
   	String insertSurveyAnswers(ExtendedSaveSurveyVO saveSurveyVO) throws DataServiceException;
   	
   	String insertResponseSOMetadata(ExtendedSaveSurveyVO saveSurveyVO) throws DataServiceException;
   	String getSurveyOptionID(String surveyType) throws DataServiceException;
   	
   	String insertSurveyResponseSO(ExtendedSaveSurveyVO saveSurveyVO) throws DataServiceException;
   	
   	Integer getBuyerId(String soId) throws DataServiceException;
   	
   	ExtendedSaveSurveyVO getSurveyId(ExtendedSaveSurveyVO saveSurveyVO) throws DataServiceException;
   	
   	String updateSaveSurveyDetails(ExtendedSaveSurveyVO saveSurveyVO)  throws DataServiceException;
   	
   	String insertSaveSurveyDetails(ExtendedSaveSurveyVO saveSurveyVO)  throws DataServiceException;
   	
   	String fetchSubSurveyType(Integer surveyId) throws DataServiceException;
   	String updateResponseSOMetadata(ExtendedSaveSurveyVO saveSurveyVO) throws DataServiceException;
	Long getResponseHDRId(String soId) throws DataServiceException; 
	Integer fetchSurveyId(Long responseHDRId) throws DataServiceException;
	
	List<SurveyRatingsVO> getBuyerMyRatings(Integer buyerID, List<Integer> vendorResourceIDs) throws DataServiceException;	
	String getEventTypeBySoId(String soId) throws DataServiceException;
	 SODetailVO getSoDetails(String soId) throws DataServiceException;
	List<Integer> getSurveyForSO(String soId) throws DataServiceException;
   	List<BuyerSurveyConfigVO> getBuyerSurveyConfigDetails(Integer buyerId) throws DataServiceException;
   List<SurveyQuestionAnswerDetailVO> getSurveyQuestionsWithAnswers(String soId)throws DataServiceException;
   	public SurveyRatingsVO getBuyerRatings(int buyerId) throws DataServiceException;
   	public Double getBuyeCurrentRatings(int buyerId, int entityTypeId) throws DataServiceException;
  //SLT-1751
  	public SurveyRatingsVO getVendorResourceRatings(int companyId, int entityTypeId, String scoreTimelineType)throws DataServiceException;
  	//public boolean IsPrimaryInd(int resourceId)throws DataServiceException;
  	
	List<BuyerRating> fetchSoDetails(String endDate) throws DataServiceException;
	List<BuyerRating> fetchCSATSurveyResponseRatings(String endDate) throws DataServiceException;
	List<BuyerRating> fetchNPSSurveyResponseRatings(String endDate)throws DataServiceException;

  	void updateLastUpdatedSurveyRatingKey(String date) throws DataServiceException;
	List<BuyerRating> calulateTotalSOforBuyer() throws DataServiceException;
	List<BuyerRating> calulateTotalRatingsforBuyer() throws DataServiceException;
	List<Integer> getBuyerRatingExt(BuyerRating buyerRating) throws DataServiceException;
	void updateBuyerRatingTotalSOCount(BuyerRating hdr) throws DataServiceException ;
	void updateBuyerRatingSurveyDetails(BuyerRating survey) throws DataServiceException ;
	void replaceBuyerRatingTotalSOCount(BuyerRating hdr) throws DataServiceException ;
	void replaceBuyerRatingSurveyDetails(BuyerRating survey) throws DataServiceException ;
	void insertBuyerRatings(List<BuyerRating> result) throws DataServiceException ;
	List<EntityRating> getDeltaBuyerCSATScore(String endDate) throws DataServiceException;
  	List<EntityRating> getDeltaBuyerCSATCurrentScore(String endDate) throws DataServiceException;
  	EntityRating getEntityRating(int entityId, int entityTypeId, String scoreType, String scoreTimelineType) throws DataServiceException;
  	List<EntityRating> getDeltaProviderCSATScore(String endDate) throws DataServiceException;
  	List<EntityRating> getDeltaProviderCSATCurrentScore(String endDate) throws DataServiceException;
  	List<EntityRating> getDeltaProviderNPSScore(String endDate) throws DataServiceException;  	
  	List<EntityRating> getDeltaProviderNPSCurrentScore(String endDate) throws DataServiceException;
  	List<EntityRating> getDeltaFrimCSATScore(String date) throws DataServiceException;
  	List<EntityRating> getDeltaFrimCSATCurrentScore(String endDate) throws DataServiceException ;
  	List<EntityRating> getDeltaFrimNPSScore(String date) throws DataServiceException;
  	List<EntityRating> getDeltaFrimNPSCurrentScore(String endDate) throws DataServiceException;
  	boolean updateSurveyScoreEntityRating(EntityRating entityRating) throws DataServiceException;
  	List<EntityRating> getEntityRatingByIdAndType(Integer entityId, Integer entityTypeId) throws DataServiceException;
  	void saveEntityRating(EntityRating newEntity) throws DataServiceException;
	void insertSurveyBuyerRatings(List<BuyerRating> surveyBuyerRatings) throws DataServiceException;
	Integer getSurveyBuyerRatingId(BuyerRating survey) throws DataServiceException;
	void insertBuyerRatingExt(BuyerRating survey) throws DataServiceException;
	public Double fetchEntityRating(Long responseHdrId) throws DataServiceException;
	public SurveyRatingsVO getOverallSurveyRatings(Integer entityId, int entityTypeId, String lifetimescore,String subSurveyType) throws DataServiceException;
	void updateOverallSurveyRatings(double avgRating,
			ExtendedSaveSurveyVO saveSurveyVO, String subSurveyType,Integer entityId) throws DataServiceException;
	public SurveyRatingsVO getAvgSurveyBuyerRatings(Integer entityId, int buyerId, String subSurveyType) throws DataServiceException;
	void updateAvgBuyerRating(double avgBuyerRating, ExtendedSaveSurveyVO saveSurveyVO,
			String subSurveyType,Integer entityId) throws DataServiceException;
	public Integer getVendorId(String soId) throws DataServiceException;
}
