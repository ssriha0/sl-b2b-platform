
package com.newco.marketplace.persistence.daoImpl.survey;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

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
import com.newco.marketplace.persistence.iDao.survey.SurveyDAO;
import com.newco.marketplace.util.SurveyVOHelper;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;
import com.newco.marketplace.interfaces.SurveyConstants;

/**
 * @author schavda
 * 
 */
public class SurveyDAOImpl extends ABaseImplDao implements SurveyDAO {

	private static final Logger logger = Logger.getLogger(SurveyDAOImpl.class
			.getName());

	public SurveyVO retrieveQuestionsBySurveyId(int iSurveyId)
			throws DataServiceException {
		SurveyVO surveyVO = new SurveyVO();
		try {
			List surveyQuesAnsList = queryForList(
					"getQuestionsBySurveyId.query", Integer.valueOf(iSurveyId));

			SurveyVOHelper helper = new SurveyVOHelper();
			helper.buildSurveyVO(surveyVO, surveyQuesAnsList);
		} catch (Exception ex) {
			logger
					.info("[SurveyDAOImpl.retrieveQuestionsBySurveyId - Exception] "
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return surveyVO;
	}

	public SurveyVO retrieveQuestions(SurveyVO surveyVO)
			throws DataServiceException {
		try {
			List surveyQuesAnsList = queryForList("getQuestions.query",
					surveyVO);

			SurveyVOHelper helper = new SurveyVOHelper();
			helper.buildSurveyVO(surveyVO, surveyQuesAnsList);
		} catch (Exception ex) {
			logger.info("[SurveyDAOImpl.retrieveQuestions - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return surveyVO;
	}
	
	public SurveyVO retrieveQuestionsForLead(SurveyVO surveyVO,AddRatingsAndReviewRequest addRatingsAndReviewRequest)
			throws DataServiceException {
		try {
			List surveyQuesAnsList = queryForList("getQuestions.query",
					surveyVO);

			SurveyVOHelper helper = new SurveyVOHelper();
			helper.buildSurveyVO(surveyVO, surveyQuesAnsList);
		} catch (Exception ex) {
			logger.info("[SurveyDAOImpl.retrieveQuestions - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return surveyVO;
	}

	public void saveResponseHeader(SurveyResponseVO surveyResponseVO)
			throws DataServiceException {
		try {
			// Save Reponse Header
			logger.debug("SurveyDAOImpl-->ResponseHdrId="
					+ surveyResponseVO.getResponseHdrId());
			surveyResponseVO.setBuyerId((Integer)this.queryForObject("soBuyerId.query",surveyResponseVO.getServiceOrderID()));
			insert("saveSurveyResponseHeader.insert", surveyResponseVO);
			// Save SO Reference
			insert("saveSurveyResponseSO.insert", surveyResponseVO);
		} catch (Exception ex) {
			logger.info("[SurveyDAOImpl.saveResponseHeader - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}
	
		//21513
	public boolean saveCSATData(String serviceOrderId) throws DataServiceException{
		logger.debug("  start saveCSATData of SurveyDAOImpl");
		boolean isPresentCsatSO=false;
		try {
			Integer count = (Integer) this.queryForObject("getCSAData.query",serviceOrderId);
			if(0 == count){
				isPresentCsatSO=true;
			}
		} catch (Exception ex) {
			logger.info("[saveCSATData of SurveyDAOImpl] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		logger.debug("  end of saveCSATData of SurveyDAOImpl");
		return isPresentCsatSO;
	}
	public ServiceOrder getInHomeBuyersDao(String  serviceOrderId) throws DataServiceException{
		ServiceOrder serviceOrder = new ServiceOrder();
		serviceOrder.setSoId(serviceOrderId);
		try {
			serviceOrder = (ServiceOrder) queryForObject("getInHomeBuyer.query", serviceOrderId);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return serviceOrder;
	}
	
	public int getAnswer(int questionId, Integer scoreValue) throws DataServiceException{
		int ansId=0;
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		try {
			map.put("questionId", questionId);
			map.put("scoreValue", scoreValue);
			ansId = (Integer) queryForObject("getAnswer.query", map);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return ansId;
	}
	public void saveResponseHeaderForLead(SurveyResponseVO surveyResponseVO)
			throws DataServiceException {
		try {
			// Save Reponse Header
			logger.debug("SurveyDAOImpl-->ResponseHdrId="
					+ surveyResponseVO.getResponseHdrId());
			surveyResponseVO.setBuyerId(7000);
			insert("saveSurveyResponseHeader.insert", surveyResponseVO);
			// Save Lead Reference
			insert("saveSurveyResponseLead.insert", surveyResponseVO);
		} catch (Exception ex) {
			logger.info("[SurveyDAOImpl.saveResponseHeader - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			//throw new DataServiceException("Error", ex);
		}
	}

	public void saveResponseDetails(SurveyResponseVO surveyResponseVO)
			throws DataServiceException {
		try {
			// Save Question's answer
			insert("saveSurveyResponse.insert", surveyResponseVO);
		} catch (Exception ex) {
			logger.info("[SurveyDAOImpl.saveResponseHeader - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}

	public ServiceOrder getServiceOrder(String serviceOrderID)
			throws DataServiceException {
		ServiceOrder serviceOrder = new ServiceOrder();
		serviceOrder.setSoId(serviceOrderID);
		try {
			serviceOrder = (ServiceOrder) queryForObject("so.query",
					serviceOrder);
		} catch (Exception ex) {
			logger.info("[SurveyDAOImpl.getServiceOrder - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return serviceOrder;
	}
	
	public Integer getCompletedResourceIdForLead(String leadId) 
			throws DataServiceException{
		Integer resourceId =null;
		try {
		resourceId = (Integer) queryForObject("getCompletedResourceIdForLead.query",
					leadId);
		} catch (Exception ex) {
			logger.info("[SurveyDAOImpl.getServiceOrder - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
		return null;
		}
		return resourceId;
		
	}
	
	public String getCompletedLead(String leadId) 
			throws DataServiceException{
		String resultleadId;
		try {
			resultleadId = (String) queryForObject("getCompletedLead.query",
					leadId);
		} catch (Exception ex) {
			logger.info("[SurveyDAOImpl.getServiceOrder - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
		return null;
		}
		return resultleadId;
		
	}


	public boolean responseExists(SurveyVO surveyVO)
			throws DataServiceException {
		boolean bExists = false;
		Long responseHdrId = new Long(0);
		try {
			responseHdrId = (Long) queryForObject("surveyResponseExists.query",
					surveyVO);
			if (responseHdrId != null && responseHdrId.longValue() > 0) {
				bExists = true;
			}
		} catch (Exception ex) {
			logger.info("[SurveyDAOImpl.responseExists - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return bExists;
	}

	public int getGivenRatings(int companyId, int entityTypeId) throws DataServiceException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("companyId", Integer.valueOf(companyId));
			final String queryTobeExecuted ; 
			if(SurveyConstants.ENTITY_BUYER_ID == entityTypeId) {
				queryTobeExecuted = "numberOfRatingsGiven_forbuyer.query";
			}
			else if (SurveyConstants.ENTITY_PROVIDER_ID == entityTypeId) {
				queryTobeExecuted = "numberOfRatingsGiven_forprovider.query";
			}else {
				return 0;
			}
			return  ((Integer) queryForObject(queryTobeExecuted, map)).intValue();
		}catch(Exception ex){
			logger.info("[SurveyDAOImpl.getbuyerRatings - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);			
		}
	}
	
	public int getReceivedRatings(int companyId, int entityTypeId, int ratingProviderEntityTypeId) throws DataServiceException{
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("entityTypeId", Integer.valueOf(entityTypeId));
			map.put("companyId", Integer.valueOf(companyId));
			map.put("ratingProviderEntityTypeId", Integer.valueOf(ratingProviderEntityTypeId));
			return((Integer) queryForObject("numberOfRatingsReceived.query",map)).intValue();			
		}catch(Exception ex){
			logger.info("[SurveyDAOImpl.getbuyerRatings - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);			
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<SurveyRatingSummaryVO> getVendorResourceRatings(int resourceId, int entityTypeId, Date date)
			throws DataServiceException {
		List<SurveyRatingSummaryVO> outSurveyVO = null;
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("resourceId", Integer.valueOf(resourceId));
			if(entityTypeId != 0){
				map.put("entityTypeId", Integer.valueOf(entityTypeId));
			}	
			if(date != null){
				map.put("createdDate", date);
			}			
			outSurveyVO = queryForList("vendorResourceSurveyScore.query", map);
		} catch (Exception ex) {
			logger.info("[SurveyDAOImpl.getproviderRatings - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}

		return outSurveyVO;
	}

	@SuppressWarnings("unchecked")
	public List<SurveyRatingSummaryVO> getVendorRatings(int companyId, int entityTypeId, Date date)
			throws DataServiceException {
		List<SurveyRatingSummaryVO> outSurveyVO = null;
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("vendorId", Integer.valueOf(companyId));		
			if(entityTypeId != 0){
				map.put("entityTypeId", Integer.valueOf(entityTypeId));
			}	
			if(date != null){
				map.put("createdDate", date);
			}			
			outSurveyVO = queryForList("vendorSurveyScore.query", map);
		} catch (Exception ex) {
			logger.info("[SurveyDAOImpl.getproviderRatings - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}

		return outSurveyVO;
	}

	@SuppressWarnings("unchecked")
	public List<SurveyQuesAnsVO> getSurveyResponse(SurveyVO surveyVO) throws DataServiceException {
		List<SurveyQuesAnsVO> outSurveyQuesAnsVO = null;
		try {
			outSurveyQuesAnsVO = queryForList("getSurveyResultsById.query", surveyVO);
		} catch (Exception ex) {
			logger.info("[SurveyDAOImpl.getSurveyResponse - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return outSurveyQuesAnsVO;	
	}

	public int updateBuyerRatedCountAndRating(int buyerID, int vendorResourceID,double currentScore) throws DataServiceException {
		logger.debug("----Start of SurveyDAOImpl.updateBuyerRatedCountAndRating()----");
		int updateCount = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {
			map.put("buyerID", Integer.valueOf(buyerID));
			map.put("vendorResourceId", Integer.valueOf(vendorResourceID));
			map.put("currentScore", Double.valueOf(currentScore));
			
			updateCount = update("updateBuyerVendorResourceRatingByVendorResource.update",map);
			
			updateCount = update("updateBuyerRatingByVendorResource.update",map);
		}	
		catch (Exception ex) {
			logger.info("[SurveyDAOImpl.updateBuyerRatedCountAndRating - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		logger.debug("----End of SurveyDAOImpl.updateBuyerRatedCountAndRating()----");
		return updateCount;
	}

	public int updateVendorResourceRatedCountAndRating(int buyerID, int vendorResourceID,double currentScore) throws DataServiceException {
		
		logger.debug("----Start of SurveyDAOImpl.updateVendorResourceRatedCountAndRating()----");
		int updateCount = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {
			map.put("buyerID", Integer.valueOf(buyerID));
			map.put("vendorResourceId", Integer.valueOf(vendorResourceID));
			map.put("currentScore", Double.valueOf(currentScore));
			
			updateCount = update("updateBuyerVendorResourceRatingByBuyer.update",map);
			
			updateCount = update("updateVendorResourceRatingByBuyer.update",map);
		}	
		catch (Exception ex) {
			logger.info("[SurveyDAOImpl.updateVendorResourceRatedCountAndRating - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		logger.debug("----End of SurveyDAOImpl.updateVendorResourceRatedCountAndRating()----");
		return updateCount;
	}

	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.survey.ISurveyDAO#updateVendorResourceSurveyRollup(Integer, java.lang.Integer, java.lang.Integer)
	 */
	public void updateVendorResourceSurveyRollup (Integer vendorResourceId, Integer questionId, Integer answerId) throws DataServiceException {
		Map<String, Integer> parameters = new HashMap<String, Integer>();
		parameters.put("resourceId", vendorResourceId);
		parameters.put("questionId", questionId);
		parameters.put("answerId", answerId);
		
		//update first
		int updateCount = update("updateVendorResourceRatingRollup.update", parameters);
		// if no rows updated do an insert
		if (updateCount == 0) {
			insert("insertVendorResourceRatingRollup.insert",parameters);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.survey.ISurveyDAO#getVendorRatingsGroupedByQuestion(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<SurveyRatingByQuestionVO> getVendorRatingsGroupedByQuestion(
			Integer vendorId) throws DataServiceException {

		List<SurveyRatingByQuestionVO> result = new ArrayList<SurveyRatingByQuestionVO>();
		
		try {
			result = queryForList("selectSummaryVendorRatings.select", vendorId);
		} catch (DataAccessException e) {
			logger.error("Unable to retrieve vendor rating summary data be vendor id",e);
			throw new DataServiceException("Unable to retrieve vendor rating summary data by vendor id",e);
		}
		
		return result;
	}

	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.survey.ISurveyDAO#getVendorResourceRatings(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<SurveyRatingByQuestionVO> getVendorResourceRatings(
			Integer resourceId) throws DataServiceException {
			
		List<SurveyRatingByQuestionVO> result = new ArrayList<SurveyRatingByQuestionVO>();
		
		try {
			result = queryForList("selectSummaryVendorResourceRatings.select", resourceId);
		} catch (DataAccessException e) {
			logger.error("Unable to retrieve vendor rating summary data be resource id",e);
			throw new DataServiceException("Unable to retrieve vendor rating summary data by resource id",e);
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<SurveyRatingsVO> getFastLookupRating(Integer buyerID, List<Integer> vendorResourceIDs) throws DataServiceException {
		
		if(vendorResourceIDs == null || vendorResourceIDs.isEmpty()) {
			logger.warn("vendoreResourcesIDs should not be blank");
			return new ArrayList<SurveyRatingsVO>(); 
		}

		logger.debug("----Start of SurveyDAOImpl.getFastLookupRating()----");
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<SurveyRatingsVO> surveyRatingsVOs = null;
		try {
			map.put("buyerId", buyerID);
			map.put("vendorResourceIds", vendorResourceIDs);
			
			surveyRatingsVOs = queryForList("getFastLookupRatingsReceived.query",map);			
		}
		catch (Exception ex) {
			logger.info("[SurveyDAOImpl.getFastLookupRating - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		logger.debug("----End of SurveyDAOImpl.getFastLookupRating()----");
		return surveyRatingsVOs;
	}

	@SuppressWarnings("unchecked")
	public List<CustomerFeedbackVO> getVendorResourceFeedback(Integer resourceId,Integer entitypedId, int count)
			throws DataServiceException {
		logger.debug("----Start of SurveyDAOImpl.getFastLookupRating()----");
		
		List<CustomerFeedbackVO> surveyFeedbackVOs = null ;
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("entityTypeId", entitypedId);
			map.put("vendorResourceId", resourceId);
			if (count > 0)
			  map.put("count", count);	
			surveyFeedbackVOs = queryForList("getVendorResourceFeedback.query", map);			
		}
		catch (Exception ex) {
			logger.info("[SurveyDAOImpl.getVendorResourceFeedback - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		logger.debug("----End of SurveyDAOImpl.getVendorResourceFeedback()----");
		return surveyFeedbackVOs;
	}
	
	@SuppressWarnings("unchecked")
	public Integer getVendorResourceFeedbackCount(Integer resourceId,Integer entitypedId) {
		logger.debug("----Start of getVendorResourceFeedbackCount()----");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("entityTypeId", entitypedId);
		map.put("vendorResourceId", resourceId);	
		Integer count = (Integer)queryForObject("getVendorResourceFeedbackCount.query", map);	
		return count;		
	}
	
	@SuppressWarnings("unchecked")
	public List<CustomerFeedbackVO> getVendorResourceFeedback(Integer resourceId,Integer entitypedId, 
			int startIndex, int endIndex, String sort, String order, 
			Date startDate, Date endDate,Double maxRating,Double minRating) throws DataServiceException {
		logger.debug("----Start of SurveyDAOImpl.getFastLookupRating()----");
		
		List<CustomerFeedbackVO> surveyFeedbackVOs = null ;
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("entityTypeId", entitypedId);
			map.put("vendorResourceId", resourceId);		
			map.put("limitStart", startIndex);
			map.put("limitEnd", endIndex);
			
			if (sort!= null && sort.equalsIgnoreCase("rating")){
				sort = "overall_score";			
			} else {
				sort = "modified_date";
			}
			
			map.put("sort", sort);	
			
			if (order != null && order.equalsIgnoreCase("Asc"))
				order = "asc";
			else
				order = "desc";
			
			map.put("order", order);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("maxRating", maxRating);
			map.put("minRating", minRating);
			
			surveyFeedbackVOs = queryForList("getVendorResourceFeedback2.query", map);			
		}
		catch (Exception ex) {
			logger.info("[SurveyDAOImpl.getVendorResourceFeedback - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		logger.debug("----End of SurveyDAOImpl.getVendorResourceFeedback()----");
		return surveyFeedbackVOs;
	}
	
	
}
