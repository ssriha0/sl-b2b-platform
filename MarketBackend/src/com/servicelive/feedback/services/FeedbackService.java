package com.servicelive.feedback.services;

import java.util.List;

import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.feedback.FeedbackVO;
import com.newco.marketplace.dto.vo.feedback.MobileFeedbackVO;
import com.newco.marketplace.exception.BusinessServiceException;


public interface FeedbackService{
	public List<FeedbackVO> loadFeedbackCategory() throws BusinessServiceException;	
	public void saveFeedback(FeedbackVO feedbcakVO)throws BusinessServiceException;
	public void uploadFeedbackDoc(DocumentVO documentVO)throws BusinessServiceException;
	public List<FeedbackVO> loadFeedback(int noOfDays) throws BusinessServiceException;
	
	/**
	 * @param noOfDays
	 * @return MobileFeedbackVO
	 * @throws BusinessServiceException
	 */
	public List<MobileFeedbackVO> loadMobileFeedback(int noOfDays) throws BusinessServiceException;
}

