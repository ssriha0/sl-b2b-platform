package com.newco.marketplace.persistence.dao.feedback;

import java.util.List;

import com.newco.marketplace.dto.vo.feedback.FeedbackVO;
import com.newco.marketplace.dto.vo.feedback.MobileFeedbackVO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface FeedbackDao {
	public List<FeedbackVO> loadFeedbackCategory() throws DataServiceException;
	public void saveFeedback(FeedbackVO feedbcakVO)throws DataServiceException;
	public List<FeedbackVO> loadFeedback(int noOfDays) throws DataServiceException;
	
	/**
	 * @param noOfDays
	 * @return MobileFeedbackVO
	 * @throws DataServiceException
	 */
	public List<MobileFeedbackVO> loadMobileFeedback(int noOfDays) throws DataServiceException;
}
