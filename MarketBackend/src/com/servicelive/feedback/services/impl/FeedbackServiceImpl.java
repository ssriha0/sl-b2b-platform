package com.servicelive.feedback.services.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.feedback.FeedbackVO;
import com.newco.marketplace.dto.vo.feedback.MobileFeedbackVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.dao.feedback.FeedbackDao;
import com.newco.marketplace.persistence.service.document.DocumentService;
import com.servicelive.feedback.services.FeedbackService;



public class FeedbackServiceImpl implements FeedbackService{
	private FeedbackDao feedbackDao;
	private DocumentService documentService;
	private static final Logger logger = Logger.getLogger(FeedbackServiceImpl.class);

	public List<FeedbackVO> loadFeedbackCategory() throws BusinessServiceException{
		
		try{
		return feedbackDao.loadFeedbackCategory();
		}catch (DataServiceException dse) {
			throw new BusinessServiceException("Exception in getting feedback category due to " + dse.getMessage(), dse);
		}
	}
	public List<FeedbackVO> loadFeedback(int noOfDays) throws BusinessServiceException{
		logger.info("Inside loadFeedback()");
		try{
		return feedbackDao.loadFeedback(noOfDays);
		}catch (DataServiceException dse) {
			throw new BusinessServiceException("Exception in getting feedback due to " + dse.getMessage(), dse);
		}
	}
	
	public void saveFeedback(FeedbackVO feedbcakVO)throws BusinessServiceException{
		
		try{
			feedbackDao.saveFeedback(feedbcakVO);
		}catch (DataServiceException dse) {
			throw new BusinessServiceException("Exception in saving feedback due to " + dse.getMessage(), dse);
		}
	}
	
	public void uploadFeedbackDoc(DocumentVO documentVO)throws BusinessServiceException{
		try{
			documentService.createDocument(documentVO);
		}catch (DataServiceException dse) {
			throw new BusinessServiceException("Exception in saving feedback due to " + dse.getMessage(), dse);
		}
	}
	
	/**
	 * @param noOfDays
	 * @return MobileFeedbackVO
	 * @throws BusinessServiceException
	 */
	public List<MobileFeedbackVO> loadMobileFeedback(int noOfDays) throws BusinessServiceException{
		logger.info("Inside loadFeedback()");
		try{
		return feedbackDao.loadMobileFeedback(noOfDays);
		}catch (DataServiceException dse) {
			throw new BusinessServiceException("Exception in getting feedback due to " + dse.getMessage(), dse);
		}
	}
	
	public FeedbackDao getFeedbackDao() {
		return feedbackDao;
	}
	public void setFeedbackDao(FeedbackDao feedbackDao) {
		this.feedbackDao = feedbackDao;
	}
	
	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

}