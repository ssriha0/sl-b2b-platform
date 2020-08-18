/**
 * 
 */
package com.newco.marketplace.persistence.iDao.survey;



import java.util.Date;
import java.util.List;

import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.AddRatingsAndReviewRequest;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.survey.CustomerFeedbackVO;
import com.newco.marketplace.dto.vo.survey.SurveyCSATVO;
import com.newco.marketplace.dto.vo.survey.SurveyQuesAnsVO;
import com.newco.marketplace.dto.vo.survey.SurveyRatingByQuestionVO;
import com.newco.marketplace.dto.vo.survey.SurveyRatingSummaryVO;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.dto.vo.survey.SurveyResponseVO;
import com.newco.marketplace.dto.vo.survey.SurveyVO;
import com.newco.marketplace.exception.core.DataServiceException;

/**
 * @author schavda
 *
 */
public interface SurveyDAO {

	public SurveyVO retrieveQuestionsBySurveyId(int iSurveyId)  throws DataServiceException;
	
	public SurveyVO retrieveQuestions(SurveyVO surveyVO)  throws DataServiceException;
	public SurveyVO retrieveQuestionsForLead(SurveyVO surveyVO,AddRatingsAndReviewRequest addRatingsAndReviewRequest)  throws DataServiceException;

	
	
	public void saveResponseHeader(SurveyResponseVO surveyResponseVO)  throws DataServiceException;
	
	public void saveResponseHeaderForLead(SurveyResponseVO surveyResponseVO)  throws DataServiceException;
	
	public void saveResponseDetails(SurveyResponseVO surveyResponseVO)  throws DataServiceException;
	
	public List<SurveyQuesAnsVO> getSurveyResponse(SurveyVO surveyVO) throws DataServiceException;

	public ServiceOrder getServiceOrder(String serviceOrderID) throws DataServiceException;
	
	public Integer getCompletedResourceIdForLead(String leadId) throws DataServiceException;

	public String getCompletedLead(String leadId) throws DataServiceException;    
				
	public boolean responseExists(SurveyVO surveyVO) throws DataServiceException;
	
	public List<SurveyRatingSummaryVO> getVendorRatings(int companyId, int entityTypeId, Date date) throws DataServiceException;
	
	public List<SurveyRatingSummaryVO> getVendorResourceRatings(int resourceId, int entityTypeId, Date date) throws DataServiceException;
	
	public int getGivenRatings(int companyId, int entityTypeId) throws DataServiceException;
	
	public int getReceivedRatings(int companyId, int entityTypeId, int ratingProviderEntityTypeId) throws DataServiceException;
	
	public int updateVendorResourceRatedCountAndRating(int buyerID, int vendorResourceID,double currentScore) throws DataServiceException;
	
	public int updateBuyerRatedCountAndRating(int buyerID, int vendorResourceID,double currentScore) throws DataServiceException;
	
	public List<SurveyRatingsVO> getFastLookupRating(Integer buyerID,List<Integer> vendorResourceIDs) throws DataServiceException;
	
	/**
	 * This method is used to update the rolled up vendor resources survey question table
	 * @param vendorResourceId
	 * @param questionId
	 * @param answerId
	 * @throws DataServiceException
	 */
	public  void updateVendorResourceSurveyRollup (Integer vendorResourceId, Integer questionId, Integer answerId) throws DataServiceException;
	
	/**
	 * This method will get the average by survey question and summary of survey responses for all service pro
	 * associated to the vendor given
	 * @param vendorId
	 * @return
	 * @throws DataServiceException
	 */
	public List<SurveyRatingByQuestionVO> getVendorRatingsGroupedByQuestion (Integer vendorId)  throws DataServiceException;

	/**
	 * This method will get the average by survey question and summary of survey responses for the service pro given
	 * @param resourceId
	 * @return
	 * @throws DataServiceException
	 */
	public List<SurveyRatingByQuestionVO> getVendorResourceRatings (Integer resourceId)  throws DataServiceException;
	
	/**
	* Return the list of Customer feed back for the given vendor resource/ buyer resource based on the entity type id
	* @param resourceId
	* @return
	* @throws DataServiceException
	*/
	public List<CustomerFeedbackVO> getVendorResourceFeedback (Integer resourceId, Integer entitypedId, int count) throws DataServiceException;
	
	public List<CustomerFeedbackVO> getVendorResourceFeedback(Integer resourceId,Integer entitypedId, int startIndex, 
			int endIndex, String sort, String order, Date startDate, Date endDate,Double maxRating,Double minRating) throws DataServiceException; 
	public Integer getVendorResourceFeedbackCount(Integer resourceId,Integer entitypedId) throws DataServiceException;
	//21513
	public ServiceOrder getInHomeBuyersDao(String  serviceOrderId) throws DataServiceException;
	public int getAnswer(int questionId, Integer scoreValue) throws DataServiceException;
	public boolean saveCSATData(String serviceOrderId) throws DataServiceException;
	
	
	
}
