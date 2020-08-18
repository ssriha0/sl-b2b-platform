package com.newco.marketplace.persistence.daoImpl.survey;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.survey.BuyerConfig;
import com.newco.marketplace.dto.vo.survey.BuyerConfigVO;
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
import com.newco.marketplace.interfaces.SurveyConstants;
import com.newco.marketplace.persistence.iDao.survey.ExtendedSurveyDAO;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * Created by kjain on 12/31/2018.
 */
public class ExtendedSurveyDAOImpl extends ABaseImplDao implements ExtendedSurveyDAO {

	private static final Logger logger = Logger.getLogger(ExtendedSurveyDAOImpl.class.getName());
	

	
	@Override
	public boolean isSurveySubmitted(String serviceOrderId) throws DataServiceException {
		logger.debug("  start isSurveySubmitted of ExtendedSurveyDAOImpl");
		boolean submitted = false;
		try {
			String flag = (String) queryForObject("validateSurvey.query", serviceOrderId);
			if ("true".equalsIgnoreCase(flag)) {
				submitted = true;
			}
		} catch (Exception ex) {
			logger.info("[isSurveySubmitted of ExtendedSurveyDAOImpl] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		logger.debug("  end of isSurveySubmitted of ExtendedSurveyDAOImpl");
		return submitted;
	}

	@Override
	public List<SurveyQuestionnaireDetailVO> getQuestionsForBuyer(String serviceOrderId, List<Integer> surveyIds) throws DataServiceException {
		logger.debug("  start getQuestionsForBuyer of ExtendedSurveyDAOImpl");
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<SurveyQuestionnaireDetailVO> list = null;
		try {
			map.put("soId", serviceOrderId);
			map.put("surveyIds", surveyIds);
			list = (List<SurveyQuestionnaireDetailVO>) queryForList("getQuestionnaire.query", map);
		} catch (Exception ex) {
			logger.info("[getQuestionsForBuyer of ExtendedSurveyDAOImpl] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		logger.debug("  end of getQuestionsForBuyer of ExtendedSurveyDAOImpl");
		return list;
	}

	@Override
	public void saveBuyerConfiguration(BuyerConfig config) throws DataServiceException {
		logger.debug("  start saveBuyerConfiguration of SurveyDAOImpl");
		try {
			delete("deletebBuyerSurveyConfig.delete", config.getId());

			batchInsert("saveBuyerSurveyConfig.insert", getSurveyDetails(config));

		} catch (Exception ex) {
			logger.info("[saveBuyerConfiguration of SurveyDAOImpl] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		logger.debug("  end of saveBuyerConfiguration of SurveyDAOImpl");
	}

	private List<BuyerConfigVO> getSurveyDetails(BuyerConfig config) {
		logger.info("details: " + config);
		List<BuyerConfigVO> list = new ArrayList<>();
		if (config.getSurveyTitle().split(SurveyConstants.SEPERATOR).length > 1) {
			String[] surveyOptions = config.getSurveyTitle().split("_");
			list.add(
					new BuyerConfigVO(config.getId(),
							Integer.valueOf(PropertiesUtils
									.getPropertyValue(config.getId() + SurveyConstants.SEPERATOR + surveyOptions[0]) == null
											? PropertiesUtils.getPropertyValue(SurveyConstants.DEFAULT + SurveyConstants.SEPERATOR + surveyOptions[0])
											: PropertiesUtils
													.getPropertyValue(config.getId() + SurveyConstants.SEPERATOR + surveyOptions[0])),
							config.getSurveyOptionID(), true, config.getUser()));
			list.add(
					new BuyerConfigVO(config.getId(),
							Integer.valueOf(PropertiesUtils
									.getPropertyValue(config.getId() + SurveyConstants.SEPERATOR + surveyOptions[1]) == null
											? PropertiesUtils.getPropertyValue(SurveyConstants.DEFAULT + SurveyConstants.SEPERATOR + surveyOptions[1])
											: PropertiesUtils
													.getPropertyValue(config.getId() + SurveyConstants.SEPERATOR + surveyOptions[1])),
							config.getSurveyOptionID(), false, config.getUser()));
		} else {
			list.add(new BuyerConfigVO(config.getId(),
					Integer.valueOf(PropertiesUtils
							.getPropertyValue(config.getId() + SurveyConstants.SEPERATOR + config.getSurveyTitle()) == null
									? PropertiesUtils.getPropertyValue(SurveyConstants.DEFAULT + SurveyConstants.SEPERATOR + config.getSurveyTitle())
									: PropertiesUtils
											.getPropertyValue(config.getId() + SurveyConstants.SEPERATOR + config.getSurveyTitle())),
					config.getSurveyOptionID(), false, config.getUser()));
		}
		return list;
	}

	public List<SurveyOptionVO> getSurveyOptions() throws DataServiceException {
		List<SurveyOptionVO> surveyOptionVOList = null;

		try {
			surveyOptionVOList = (List<SurveyOptionVO>) queryForList("getSurveyOptions.query");
		} catch (Exception ex) {
			logger.info("exception while fetching the all survey options " + ex.getMessage());
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return surveyOptionVOList;
	}
	
	public BuyerSurveyConfigVO getBuyerSurveySelectedDetails(Integer buyerId) throws DataServiceException {
		List<BuyerSurveyConfigVO> buyerSurveyConfigVOList = null;

		try {
			buyerSurveyConfigVOList = (List<BuyerSurveyConfigVO>) queryForList("getBuyerSurveySelectedDetails.query",
					buyerId);
		} catch (Exception ex) {
			logger.info("exception while fetching the buyer survey details " + ex.getMessage());
			throw new DataServiceException(ex.getMessage(), ex);
		}
		if (buyerSurveyConfigVOList != null && buyerSurveyConfigVOList.size() > 0)
			return buyerSurveyConfigVOList.get(0);
		return null;
	}
	
	public String getEventTypeBySoId(String soId) throws DataServiceException {
		String eventType = null;
		try {
			eventType = (String)queryForObject("getSurveyEventType.query", soId);
		} catch (Exception ex) {
			logger.info("exception while fetching the event type "+ex.getMessage());
			throw new DataServiceException(ex.getMessage(), ex);
		}
		
		return eventType;
	}
	
	@Override
	public SODetailVO getSoDetails(String soId) throws DataServiceException {
		SODetailVO soHdr = null;
		try {
			soHdr = (SODetailVO) queryForObject("getSoDetails.query",soId);
		} catch (Exception ex) {
			logger.info("exception while getSoDetails() "+ex.getMessage());
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return soHdr;
	}
	
	@Override
	public List<Integer> getSurveyForSO(String soId) throws DataServiceException {
		List<Integer> surveyIdList = null;
		try {
			surveyIdList = queryForList("getSurveyForSO.query",soId);
		} catch (Exception ex) {
			logger.error("exception while getSurveyForSO() "+ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return surveyIdList;
	}
	
	@Override
	public List<SurveyRatingsVO> getBuyerMyRatings(Integer buyerID, List<Integer> vendorResourceIDs) throws DataServiceException {
		
		if(vendorResourceIDs == null || vendorResourceIDs.isEmpty()) {
			logger.warn("vendoreResourcesIDs should not be blank");
			return new ArrayList<SurveyRatingsVO>(); 
		}

		logger.debug("----Start of ExtendedSurveyDAOImpl.getFastLookupRating()----");
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<SurveyRatingsVO> surveyRatingsVOs = null;
		try {
			map.put("buyerId", buyerID);
			map.put("vendorResourceIds", vendorResourceIDs);
			
			surveyRatingsVOs = queryForList("getBuyerMyRatings.query",map);			
		}
		catch (Exception ex) {
			logger.info("[ExtendedSurveyDAOImpl.getFastLookupRating - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		logger.debug("----End of ExtendedSurveyDAOImpl.getFastLookupRating()----");
		return surveyRatingsVOs;
	} 
	
	
	//SLT-1649 CSAT NPS save survey API
	@Override
	public String saveSurveyDetails(ExtendedSurveyResponseVO extendedSurveyResponseVO)
			throws DataServiceException {
		logger.debug("  start saveSurveyDetails of SurveyDAOImpl");

		try {
			insert("saveExtendedSurveyDetails.insert", extendedSurveyResponseVO);
		} catch (Exception ex) {
			logger.info("[saveSurveyDetails of SurveyDAOImpl] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error");
		}
		logger.debug("  end of saveSurveyDetails of SurveyDAOImpl");
		return "success";
	}
	
	@Override
	public String insertSurveyAnswers(ExtendedSaveSurveyVO saveSurveyVO) throws DataServiceException {
		logger.debug("  start insertSurveyAnswers of SurveyDAOImpl");
		try {
			insert("insertSurveyAnswers.insert", saveSurveyVO);
		} catch (Exception ex) {
			logger.info("[insertSurveyAnswers of SurveyDAOImpl] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error");
		}
		return "success";
	}
	
	@Override
	public String insertResponseSOMetadata(ExtendedSaveSurveyVO saveSurveyVO)
			throws DataServiceException {
		logger.debug("  start insertResponseSOMetadata of SurveyDAOImpl");
		try {

			insert("insertResponseSOMetadata.insert", saveSurveyVO);
		} catch (Exception ex) {
			logger.info("[insertResponseSOMetadata of SurveyDAOImpl] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error");
		}
		return "success";
	}
	
	@Override
	public String getSurveyOptionID(String surveyType) throws DataServiceException {
		logger.debug("  start getSurveyOptionID of SurveyDAOImpl");
		Integer surveyOptionID= null;
		try {
			surveyOptionID=(Integer) queryForObject("getSurveyOptionID.query", surveyType);
		} catch (Exception ex) {
			logger.info("[getSurveyOptionID of SurveyDAOImpl] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error");
		}
		return surveyOptionID.toString();
	}

	@Override
	public String insertSurveyResponseSO(ExtendedSaveSurveyVO saveSurveyVO)
			throws DataServiceException {
		logger.debug("  start insertSurveyResponseSO of SurveyDAOImpl");
		try {

			insert("insertSurveyResponseSO.insert", saveSurveyVO);
		} catch (Exception ex) {
			logger.info("[insertSurveyResponseSO of SurveyDAOImpl] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error");
		}
		return "success";
	}
	
	@Override
	public Integer getBuyerId(String soId) throws DataServiceException {
		logger.debug("  start getBuyerId of SurveyDAOImpl");
		Integer buyerId;
		try {
			buyerId = (Integer) queryForObject("soBuyerId.query", soId);
		} catch (Exception ex) {
			logger.info("[getBuyerId of SurveyDAOImpl] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error");
		}
		return buyerId;
	}
	@Override
	public ExtendedSaveSurveyVO getSurveyId(ExtendedSaveSurveyVO saveSurveyVO) throws DataServiceException {
		logger.debug("  start getSurveyId of SurveyDAOImpl");
		ExtendedSaveSurveyVO extendedSaveSurveyVO=null;
		try {

			extendedSaveSurveyVO=(ExtendedSaveSurveyVO) queryForObject("getSurveyId.query", saveSurveyVO);
		} catch (Exception ex) {
			logger.info("[getSurveyId of SurveyDAOImpl] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error");
		}
		return extendedSaveSurveyVO;
	}
	
	@Override
	public String updateSaveSurveyDetails(ExtendedSaveSurveyVO saveSurveyVO) throws DataServiceException {
		logger.debug("  start updateSaveSurveyDetails of SurveyDAOImpl");

		try {
			update("updateSurveyResponseHdr.update", saveSurveyVO);
		} catch (Exception ex) {
			logger.info("[updateSaveSurveyDetails of SurveyDAOImpl] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error");
		}
		logger.debug("  end of updateSaveSurveyDetails of SurveyDAOImpl");
		return "success";
	}
	
	

	@Override
	public String updateResponseSOMetadata(ExtendedSaveSurveyVO saveSurveyVO) throws DataServiceException {
		logger.debug("  start updateResponseSOMetadata of SurveyDAOImpl");

		try {
			update("updateResponseSOMetadata.update",saveSurveyVO);
		} catch (Exception ex) {
			logger.info("[updateResponseSOMetadata of SurveyDAOImpl] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error");
		}
		logger.debug("  end of updateResponseSOMetadata of SurveyDAOImpl");
		return "success";
	}

	@Override
	public String insertSaveSurveyDetails(ExtendedSaveSurveyVO saveSurveyVO) throws DataServiceException {
		logger.debug("  start insertSaveSurveyDetails of SurveyDAOImpl");

		try {
			insert("saveExtendedSurveyDetails.insert", saveSurveyVO);
			insert("insertSurveyResponseSO.insert", saveSurveyVO);

		} catch (Exception ex) {
			logger.info("[insertSaveSurveyDetails of SurveyDAOImpl] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error");
		}
		logger.debug("  end of insertSaveSurveyDetails of SurveyDAOImpl");
		return "success";
	}

	@Override
	public String fetchSubSurveyType(Integer surveyId) throws DataServiceException {
		logger.debug("  start fetchSubSurveyType of SurveyDAOImpl");
		String subSurveyType=null;
		try {
			subSurveyType=(String) queryForObject("fetchSubSurveyType.query", surveyId);

		} catch (Exception ex) {
			logger.info("[fetchSubSurveyType of SurveyDAOImpl] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error");
		}
		logger.debug("  end of fetchSubSurveyType of SurveyDAOImpl");
		return subSurveyType;
	}
	
	@Override
	public Long getResponseHDRId(String soId) throws DataServiceException {
		logger.debug("  start getResponseHDRId of SurveyDAOImpl");
		Long responseHdrId=null;
		try {
			responseHdrId= (Long) queryForObject("getResponseHDRId.query", soId);

		} catch (Exception ex) {
			logger.info("[getResponseHDRId of SurveyDAOImpl] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error");
		}
		logger.debug("  end of getResponseHDRId of SurveyDAOImpl");
		return responseHdrId;
	}

	@Override
	public Integer fetchSurveyId(Long responseHdrId) throws DataServiceException {
		logger.debug("  start fetchSurveyId of SurveyDAOImpl");
		Integer surveyId=null;
		try {
			surveyId= (Integer) queryForObject("fetchSurveyId.query", responseHdrId);

		} catch (Exception ex) {
			logger.info("[fetchSurveyId of SurveyDAOImpl] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error");
		}
		logger.debug("  end of fetchSurveyId of SurveyDAOImpl");
		return surveyId;
	}

	@Override
	public List<BuyerSurveyConfigVO> getBuyerSurveyConfigDetails(Integer buyerId) throws DataServiceException {
		logger.info("Starting ExtendedSurveyDAOImpl :: getBuyerSurveyConfigDetails");
		List<BuyerSurveyConfigVO> buyerSurveyConfigVOList = null;

		try {
			Date start = new Date();
			buyerSurveyConfigVOList = (List<BuyerSurveyConfigVO>) queryForList("getBuyerSurveyOptionDetails.query",
					buyerId);
			Date end= new Date();
			logger.info("getBuyerSurveyOptionDetails.query : " + (end.getTime() - start.getTime()) + " ms");
		} catch (Exception ex) {
			logger.info("exception while fetching the buyer survey details " + ex.getMessage());
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return buyerSurveyConfigVOList;
	}

	@Override
	public List<SurveyQuestionAnswerDetailVO> getSurveyQuestionsWithAnswers(String soId)throws DataServiceException {
		List<SurveyQuestionAnswerDetailVO> surveyQuestionAnswerDetailVOList = null;
		
		try {
			surveyQuestionAnswerDetailVOList = (List<SurveyQuestionAnswerDetailVO>)queryForList("getSurveyQuestionsWithAnswers.query", soId);
		} catch (Exception ex) {
			logger.info("exception while fetching the survey question and answers details "+ex.getMessage());
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return surveyQuestionAnswerDetailVOList;
	}
	
	public SurveyRatingsVO getBuyerRatings(int buyerId) throws DataServiceException {
		SurveyRatingsVO outSurveyVO = null;
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("buyerId", Integer.valueOf(buyerId));
			map.put("surveyType", SurveyConstants.CSATSCORE);
			outSurveyVO = (SurveyRatingsVO)queryForObject("buyerSurveyScore.query", map);
		} catch (Exception ex) {
			logger.info("[SurveyDAOImpl.getbuyerRatings - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return outSurveyVO;

	}
	
	public Double getBuyeCurrentRatings(int buyerId, int entityTypeId) throws DataServiceException {
		Double currentScore=null;
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("buyerId", Integer.valueOf(buyerId));
			map.put("entityTypeId", Integer.valueOf(entityTypeId));
			map.put("surveyType", SurveyConstants.CSATSCORE);
			map.put("scoreType", SurveyConstants.CURRENTSCORE);	
			currentScore = (Double)queryForObject("buyerCurrentnScore.query", map);
		} catch (Exception ex) {
			logger.info("[SurveyDAOImpl.getbuyerRatings - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return currentScore;

	}
	
	//SLT-1751
		
		public SurveyRatingsVO getVendorResourceRatings(int companyId, int entityTypeId, String scoreTimelineType)throws DataServiceException{
			SurveyRatingsVO surveyRatingsVO=null;
			try {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id", Integer.valueOf(companyId));		
				map.put("entityTypeId", Integer.valueOf(entityTypeId));
				map.put("surveyType", SurveyConstants.CSATSCORE);
				map.put("scoreTimelineType",scoreTimelineType);
				surveyRatingsVO = (SurveyRatingsVO) queryForObject("surveyScoreResourceRatings.query", map);
			} catch (Exception ex) {
				logger.info("[SurveyDAOImpl.getVendorResourceRatings - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
				throw new DataServiceException("Error", ex);
			}

			return surveyRatingsVO;
		}
		
		/*public boolean IsPrimaryInd(int resourceId)throws DataServiceException{
			boolean IsPrimaryInd;
			Integer primaryIndValue;
			try {
						
				primaryIndValue = (Integer) queryForObject("getPrimaryIndForResource.query",resourceId);
				if(primaryIndValue == 1){
					IsPrimaryInd=true;
				}
				else{
					IsPrimaryInd=false;
				}
			} catch (Exception ex) {
				logger.info("[SurveyDAOImpl.IsPrimaryInd - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
				throw new DataServiceException("Error", ex);
			}

			return IsPrimaryInd;
		}*/

	@Override
	public List<BuyerRating> fetchSoDetails(String endDate) throws DataServiceException {
		List<BuyerRating> buyerRatings = null;
		try {
			logger.info("Start fetching So Details Buyer Rating details.");
			buyerRatings = (List<BuyerRating>) queryForList("fetchSODetailBuyerRatings.query", endDate);
			logger.info("Sucessfully fetched So Details Rating details.");
			return buyerRatings;
		} catch (Exception ex) {
			logger.error("exception while fetching So Details Rating details " + ex.getMessage());
			throw new DataServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public List<BuyerRating> fetchCSATSurveyResponseRatings(String endDate) throws DataServiceException {
		List<BuyerRating> buyerRatings = null;
		try {
			logger.info("Start fetching CSAT Survey Response Rating details.");
			buyerRatings = (List<BuyerRating>) queryForList("fetchCSATSurveyResponseRatings.query", endDate);
			logger.info("Sucessfully fetched CSAT Survey Response Rating details.");
			return buyerRatings;
		} catch (Exception ex) {
			logger.error("exception while fetching CSAT Survey Response Rating details " + ex.getMessage());
			throw new DataServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public List<BuyerRating> fetchNPSSurveyResponseRatings(String endDate) throws DataServiceException {
		List<BuyerRating> buyerRatings = null;
		try {
			logger.info("Start fetching NPS Survey Response Rating details.");
			buyerRatings = (List<BuyerRating>) queryForList("fetchNPSSurveyResponseRatings.query", endDate);
			logger.info("Sucessfully fetched NPS Survey Response Rating details.");
			return buyerRatings;
		} catch (Exception ex) {
			logger.error("exception while fetching NPS Survey Response Rating details " + ex.getMessage());
			throw new DataServiceException(ex.getMessage(), ex);
		}
	}


		@Override
		public void updateLastUpdatedSurveyRatingKey(String date) throws DataServiceException {
			update("updatesurveylastupdate.query",date);			
		}

		@Override
		public List<BuyerRating> calulateTotalSOforBuyer() throws DataServiceException {
			List<BuyerRating> buyerRatings = null;
			try {
				logger.info("Start calulating total SO for Buyer details.");
				buyerRatings = (List<BuyerRating>) queryForList("calulateTotalSOforBuyer.query");
				logger.info("Sucessfully calulated total SO for Buyer.");
				return buyerRatings;
			} catch (Exception ex) {
				logger.error("exception while calulating total SO for Buyer " + ex.getMessage());
				throw new DataServiceException(ex.getMessage(), ex);
			}
		}
		
		@Override
		public List<BuyerRating> calulateTotalRatingsforBuyer() throws DataServiceException{
			List<BuyerRating> buyerRatings = null;
			try {
				logger.info("Start calulating total Ratings for Buyer details.");
				buyerRatings = (List<BuyerRating>) queryForList("calulateTotalRatingsforBuyer.query");
				logger.info("Sucessfully calulated total ratings for Buyer.");
				return buyerRatings;
			} catch (Exception ex) {
				logger.error("exception while calulating total ratings for Buyer " + ex.getMessage());
				throw new DataServiceException(ex.getMessage(), ex);
			}
		}
		
		@Override
		public List<Integer> getBuyerRatingExt(BuyerRating buyerRating) throws DataServiceException {
			List<Integer> buyerRatings = null;
			try {
				logger.debug("Start getting BuyerRatingExt for ."+ buyerRating);
				buyerRatings = (List<Integer>) queryForList("getBuyerRating.query",buyerRating);
				logger.debug("Sucessfully got BuyerRatingExt details.");
				return buyerRatings;
			} catch (Exception ex) {
				logger.error("exception while getting BuyerRatingExt details " + ex.getMessage());
				throw new DataServiceException(ex.getMessage(), ex);
			}
		}
		
		@Override
		public Integer getSurveyBuyerRatingId(BuyerRating buyerRating) throws DataServiceException{
			Integer buyerRatingId = null;
			try {
				logger.debug("Start getting BuyerRating for ."+ buyerRating);
				buyerRatingId = (Integer) queryForObject("getBuyerRatingId.query",buyerRating);
				logger.debug("Sucessfully got BuyerRating details.");
				return buyerRatingId;
			} catch (Exception ex) {
				logger.error("exception while getting BuyerRating details " + ex.getMessage());
				throw new DataServiceException(ex.getMessage(), ex);
			}
		}
		
		@Override
		public void updateBuyerRatingTotalSOCount(BuyerRating buyerRating) throws DataServiceException {
			try {
				logger.debug("Start updateBuyerRatingTotalSOCount ."+ buyerRating);
				update("updateBuyerRatingTotalSOCount.update", buyerRating);
				logger.debug("Sucessfully updated total SO count.");
			} catch (Exception ex) {
				logger.error("exception while updating total SO count " + ex.getMessage());
				throw new DataServiceException(ex.getMessage(), ex);
			}
		}
		@Override
		public void updateBuyerRatingSurveyDetails(BuyerRating buyerRating) throws DataServiceException{
			try {
				logger.debug("Start updateBuyerRatingSurveyDetails ."+ buyerRating);
				update("updateBuyerRatingSurveyDetails.update", buyerRating);
				logger.debug("Sucessfully updated survey details.");
			} catch (Exception ex) {
				logger.error("exception while updating survey details " + ex.getMessage());
				throw new DataServiceException(ex.getMessage(), ex);
			}
		}
		
		@Override
		public void replaceBuyerRatingTotalSOCount(BuyerRating buyerRating) throws DataServiceException {
			try {
				logger.debug("Start replaceBuyerRatingTotalSOCount ."+ buyerRating);
				update("replaceBuyerRatingTotalSOCount.update", buyerRating);
				logger.debug("Sucessfully replaced total SO count.");
			} catch (Exception ex) {
				logger.error("exception while replacing total SO count " + ex.getMessage());
				throw new DataServiceException(ex.getMessage(), ex);
			}
		}
		@Override
		public void replaceBuyerRatingSurveyDetails(BuyerRating buyerRating) throws DataServiceException{
			try {
				logger.debug("Start replaceBuyerRatingSurveyDetails ."+ buyerRating);
				update("replaceBuyerRatingSurveyDetails.update", buyerRating);
				logger.debug("Sucessfully replaced survey details.");
			} catch (Exception ex) {
				logger.error("exception while replacing survey details " + ex.getMessage());
				throw new DataServiceException(ex.getMessage(), ex);
			}
		}
		@Override
		public void insertBuyerRatings(List<BuyerRating> buyerRatings) throws DataServiceException{
			try {
				logger.debug("Start insertBuyerRatings ."+ buyerRatings);
				batchInsert("saveBuyerRating.insert", buyerRatings);
				logger.debug("Sucessfully inserted Buyer ratings.");
			} catch (Exception ex) {
				logger.error("exception while inserting buyer ratings " + ex.getMessage());
				throw new DataServiceException(ex.getMessage(), ex);
			}
		}
		
		@Override
		public void insertSurveyBuyerRatings(List<BuyerRating> buyerRatings) throws DataServiceException{
			try {
				logger.debug("Start insertSurveyBuyerRatings ."+ buyerRatings);
				batchInsert("saveSurveyBuyerRating.insert", buyerRatings);
				logger.debug("Sucessfully inserted Survey Buyer ratings.");
			} catch (Exception ex) {
				logger.error("exception while inserting survey buyer ratings " + ex.getMessage());
				throw new DataServiceException(ex.getMessage(), ex);
			}
		}
		
		@Override
		public void insertBuyerRatingExt(BuyerRating buyerRating) throws DataServiceException{
			try {
				logger.debug("Start insertSurveyBuyerRatingExt ."+ buyerRating);
				insert("saveSurveyBuyerRatingExt.insert", buyerRating);
				logger.debug("Sucessfully inserted Survey Buyer rating Ext.");
			} catch (Exception ex) {
				logger.error("exception while inserting survey buyer rating ext " + ex.getMessage());
				throw new DataServiceException(ex.getMessage(), ex);
			}
		}
		
		@Override
		public List<EntityRating> getDeltaBuyerCSATScore(String endDate) throws DataServiceException{
			List<EntityRating> ratingList=null;
			try {				
				ratingList = queryForList("getDeltaBuyerCSATScore.query", endDate);
			} catch (Exception ex) {
				logger.error("[ExtendedSurveyDAOImpl.getDeltaBuyerCSATScore - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
				throw new DataServiceException("Error", ex);
			}
			return ratingList;
		}
		
		@Override
		public List<EntityRating> getDeltaBuyerCSATCurrentScore(String endDate) throws DataServiceException{
			List<EntityRating> ratingList=null;
			try {				
				ratingList = queryForList("getDeltaBuyerCSATCurrentScore.query", endDate);
			} catch (Exception ex) {
				logger.error("[ExtendedSurveyDAOImpl.getDeltaBuyerCSATCurrentScore - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
				throw new DataServiceException("Error", ex);
			}
			return ratingList;
		}	
		
		@Override
		public EntityRating getEntityRating(int entityId, int entityTypeId, String scoreType, String scoreTimeLineType) throws DataServiceException{
			EntityRating entityRating = null;
			try {			
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("entityId", Integer.valueOf(entityId));		
				map.put("entityTypeId", Integer.valueOf(entityTypeId));
				map.put("scoreType", scoreType);
				map.put("scoreTimeLineType",scoreTimeLineType);
				entityRating = (EntityRating) queryForObject("getEntityRating1.query", map);
			} catch (Exception ex) {
				logger.error("[ExtendedSurveyDAOImpl.getEntityRating - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
				throw new DataServiceException("Error", ex);
			}
			return entityRating;
		}

		
	
		@Override
		public List<EntityRating> getDeltaProviderCSATScore(String endDate) throws DataServiceException{
			List<EntityRating> ratingList=null;
			try {				
				ratingList = queryForList("getDeltaProviderCSATScore.query", endDate);
			} catch (Exception ex) {
				logger.error("[ExtendedSurveyDAOImpl.getDeltaProviderCSATScore - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
				throw new DataServiceException("Error", ex);
			}
			return ratingList;
		}
		
		@Override
		public List<EntityRating> getDeltaProviderCSATCurrentScore(String endDate) throws DataServiceException{
			List<EntityRating> ratingList=null;
			try {				
				ratingList = queryForList("getDeltaProviderCSATCurrentScore.query", endDate);
			} catch (Exception ex) {
				logger.error("[ExtendedSurveyDAOImpl.getDeltaProviderCSATCurrentScore - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
				throw new DataServiceException("Error", ex);
			}
			return ratingList;
		}
		
		@Override
		public List<EntityRating> getDeltaProviderNPSScore(String endDate) throws DataServiceException{
			List<EntityRating> ratingList=null;
			try {				
				ratingList = queryForList("getDeltaProviderNPSScore.query", endDate);
			} catch (Exception ex) {
				logger.error("[ExtendedSurveyDAOImpl.getDeltaProviderNPSScore - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
				throw new DataServiceException("Error", ex);
			}
			return ratingList;
		}
		
		@Override
		public List<EntityRating> getDeltaProviderNPSCurrentScore(String endDate) throws DataServiceException{
			List<EntityRating> ratingList=null;
			try {				
				ratingList = queryForList("getDeltaProviderNPSCurrentScore.query", endDate);
			} catch (Exception ex) {
				logger.error("[ExtendedSurveyDAOImpl.getDeltaProviderNPSCurrentScore - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
				throw new DataServiceException("Error", ex);
			}
			return ratingList;
		}
		
		@Override
        public List<EntityRating> getDeltaFrimCSATScore(String endDate) throws DataServiceException {
			List<EntityRating> ratingList=null;
			try {				
				ratingList = queryForList("getDeltaFrimCSATScore.query", endDate);
			} catch (Exception ex) {
				logger.error("[ExtendedSurveyDAOImpl.getDeltaFrimCSATScore - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
				throw new DataServiceException("Error", ex);
			}
			return ratingList;
        }

        @Override
        public List<EntityRating> getDeltaFrimCSATCurrentScore(String endDate) throws DataServiceException {
        	List<EntityRating> ratingList=null;
			try {				
				ratingList = queryForList("getDeltaFrimCSATCurrentScore.query", endDate);
			} catch (Exception ex) {
				logger.error("[ExtendedSurveyDAOImpl.getDeltaFrimCSATCurrentScore - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
				throw new DataServiceException("Error", ex);
			}
			return ratingList;
        }

        @Override
        public List<EntityRating> getDeltaFrimNPSScore(String endDate) throws DataServiceException {
        	List<EntityRating> ratingList=null;
			try {				
				ratingList = queryForList("getDeltaFrimNPSScore.query", endDate);
			} catch (Exception ex) {
				logger.error("[ExtendedSurveyDAOImpl.getDeltaFrimNPSScore - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
				throw new DataServiceException("Error", ex);
			}
			return ratingList;
        }

        @Override
        public List<EntityRating> getDeltaFrimNPSCurrentScore(String endDate) throws DataServiceException {
        	List<EntityRating> ratingList=null;
			try {				
				ratingList = queryForList("getDeltaFrimNPSCurrentScore.query", endDate);
			} catch (Exception ex) {
				logger.error("[ExtendedSurveyDAOImpl.getDeltaFrimNPSCurrentScore - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
				throw new DataServiceException("Error", ex);
			}
			return ratingList;
        }
       
        @Override
        public boolean updateSurveyScoreEntityRating(EntityRating entityRating) throws DataServiceException {
        	List<EntityRating> ratingList=null;
			try {				
				update("updateSurveyScoreEntityRating.update", entityRating);
			} catch (Exception ex) {
				logger.error("[ExtendedSurveyDAOImpl.updateSurveyScoreEntityRating - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
				throw new DataServiceException("Error", ex);
			}
			return true;
        }
        
        @Override
        public List<EntityRating> getEntityRatingByIdAndType(Integer entityId, Integer entityTypeId) throws DataServiceException {
        	List<EntityRating> ratingList=null;
			try {		
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("entityId", Integer.valueOf(entityId));		
				map.put("entityTypeId", Integer.valueOf(entityTypeId));
				ratingList = queryForList("getEntityRatingByIdAndType.query", map);
			} catch (Exception ex) {
				logger.error("[ExtendedSurveyDAOImpl.getEntityRatingByIdAndType - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
				throw new DataServiceException("Error", ex);
			}
			return ratingList;
        }     
        
        @Override
        public void saveEntityRating(EntityRating newEntity) throws DataServiceException {
			try {
				insert("saveEntityRating.insert", newEntity);
			} catch (Exception ex) {
				logger.error("[ExtendedSurveyDAOImpl.saveEntityRating - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
				throw new DataServiceException("Error", ex);
			}
        }
        @Override
		public Double fetchEntityRating(Long responseHdrId) throws DataServiceException {
        	logger.info("Start fetch Survey Rating details.");
			Double surveyRating=null;
			try {				
				surveyRating=(Double) queryForObject("fetchSurveyRatingPrice.query",responseHdrId);
				logger.info("Sucessfully fetched Survey Rating");
			} catch (Exception ex) {
				logger.info("exception while fetch Entity Rating details " + ex.getMessage());
				throw new DataServiceException(ex.getMessage(), ex);
			}
			return surveyRating;
		}
		@Override
		public void updateOverallSurveyRatings(double avgRating,
				ExtendedSaveSurveyVO saveSurveyVO, String subSurveyType,Integer entityId) throws DataServiceException {
			logger.info("Start updating Survey Rating details.");
			try{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("entityId", Integer.valueOf(entityId));		
			map.put("entityTypeId", Integer.valueOf(SurveyConstants.ENTITY_PROVIDER_ID));
			map.put("surveyType", subSurveyType);
			map.put("scoreTimelineType",SurveyConstants.LIFETIMESCORE);
			map.put("ratingScore",avgRating);
			update("updatesurveyentityRating.update", map);
			}
			catch(Exception ex) {
				logger.info("[update Overall Survey Ratings of SurveyDAOImpl] " + StackTraceHelper.getStackTrace(ex));
				throw new DataServiceException(ex.getMessage(), ex);
			}
		}
		@Override
		public SurveyRatingsVO getAvgSurveyBuyerRatings(Integer entityId, int buyerId, String subSurveyType) throws DataServiceException {
			SurveyRatingsVO surveyRatingsVO=null;
			logger.info("Start fetch Survey Rating details.");
			try {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("entityId", Integer.valueOf(entityId));		
				map.put("buyerId", Integer.valueOf(buyerId));
				map.put("surveyType", subSurveyType);
				surveyRatingsVO = (SurveyRatingsVO) queryForObject("buyerSurveyRating.query", map);
			} catch (Exception ex) {
				logger.info("[SurveyDAOImpl.getAvgSurveyBuyerRatings - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
				throw new DataServiceException(ex.getMessage(), ex);
			}
			return surveyRatingsVO;
		}
		@Override
		public void updateAvgBuyerRating(double avgBuyerRating,
				ExtendedSaveSurveyVO saveSurveyVO, String subSurveyType,Integer entityId) throws DataServiceException {
				logger.info("Start updated buyer Rating details.");
				try{
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("entityId", Integer.valueOf(entityId));		
				map.put("buyerId", Integer.valueOf(saveSurveyVO.getBuyerId()));
				map.put("surveyType", subSurveyType);
				map.put("ratingScore",avgBuyerRating);
				update("updateSurveyBuyerRating.update", map);
				}
				catch(Exception ex) {
					logger.info("[updateAvgBuyerRating of SurveyDAOImpl] " + StackTraceHelper.getStackTrace(ex));
					throw new DataServiceException(ex.getMessage(), ex);
				}
		}
		@Override
		public SurveyRatingsVO getOverallSurveyRatings(Integer entityId, int entityTypeId, String scoreTimelineType,String subSurveyType)throws DataServiceException{
			SurveyRatingsVO surveyRating=null;
			logger.info("fetch Overall Survey Ratings details.");
			try {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("entityId", entityId);	
				map.put("entityTypeId", Integer.valueOf(entityTypeId));
				map.put("surveyType", subSurveyType);
				map.put("scoreTimelineType",scoreTimelineType);
				surveyRating = (SurveyRatingsVO) queryForObject("surveyAggregateRatings.query", map);
			} catch (Exception ex) {
				logger.info("[SurveyDAOImpl.getOverallSurveyRatings - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
				throw new DataServiceException("Error", ex);
			}
			return surveyRating;
		}
		@Override
		public Integer getVendorId(String soId)throws DataServiceException{
			Integer entityId=null;
			logger.info("fetch Vendor id details.");
			try {
				entityId = (Integer) queryForObject("fetchEntityId.query", soId);
			} catch (Exception ex) {
				logger.info("[SurveyDAOImpl.get Vendor Id - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
				throw new DataServiceException("Error", ex);
			}
			return entityId;
		}
}
