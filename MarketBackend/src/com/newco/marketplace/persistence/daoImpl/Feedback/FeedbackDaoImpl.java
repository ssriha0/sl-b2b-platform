package com.newco.marketplace.persistence.daoImpl.Feedback;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.feedback.FeedbackVO;
import com.newco.marketplace.dto.vo.feedback.MobileFeedbackVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.dao.feedback.FeedbackDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class FeedbackDaoImpl extends ABaseImplDao implements FeedbackDao {
	private static final Logger logger = Logger.getLogger(FeedbackDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	public List<FeedbackVO> loadFeedbackCategory()throws DataServiceException{
		List<FeedbackVO> categoryList = new ArrayList<FeedbackVO>();
		try{
		categoryList = (List<FeedbackVO>) queryForList("feedback.fetchCategory");
		}catch(Exception e){
			logger.error("Exception occured in FeedbackDaoImpl.loadFeedbackCategory() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
		return categoryList;
	}
	
	public void saveFeedback(FeedbackVO feedbcakVO)throws DataServiceException{
		
		try{
		insert("feedback.query", feedbcakVO);
		}catch(Exception e){
			logger.error("Exception occured in FeedbackDaoImpl.saveFeedback() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	public List<FeedbackVO> loadFeedback(int noOfDays) throws DataServiceException{
		logger.info("Inside loadFeedback()");
		List<FeedbackVO> feedbackList = new ArrayList<FeedbackVO>();
		try{
			feedbackList = (List<FeedbackVO>) queryForList("fetchfeedback.query",noOfDays);
		}catch(Exception e){
			logger.error("Exception occured in FeedbackDaoImpl.loadFeedback() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
		return feedbackList;
	}
	
	/**
	 * @param noOfDays
	 * @return MobileFeedbackVO
	 * @throws DataServiceException
	 */
	public List<MobileFeedbackVO> loadMobileFeedback(int noOfDays) throws DataServiceException{
		logger.info("Inside loadFeedback()");
		List<MobileFeedbackVO> mobileFeedbackList = new ArrayList<MobileFeedbackVO>();
		try{
			mobileFeedbackList = (List<MobileFeedbackVO>) queryForList("mobileFetchFeedback.query",noOfDays);
		}catch(Exception e){
			logger.error("Exception occured in FeedbackDaoImpl.loadFeedback() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
		return mobileFeedbackList;
	}
}
