package com.newco.marketplace.persistence.dao.buyersurvey;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.systemgeneratedemail.EmailConfigVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.EmailDataVO;
import com.newco.marketplace.persistence.dao.buyersurvey.BuyerSurveyManagerDAO;
import com.sears.os.dao.impl.ABaseImplDao;
import com.servicelive.common.exception.DataServiceException;

public class BuyerSurveyManagerDAOImpl extends ABaseImplDao implements BuyerSurveyManagerDAO {
	private static final Logger logger = Logger.getLogger("BuyerSurveyManagerDAOImpl");

	@SuppressWarnings("unchecked")
	@Override
	public List<EmailConfigVO> getEmailConfiguration() throws DataServiceException {
		logger.info("start SystemGeneratedEmailDaoImpl::getEmailConfiguration");
		List<EmailConfigVO> emailConfigVOList;
		try {
			Date start = new Date();
			emailConfigVOList = (List<EmailConfigVO>) queryForList("getEmailConfig.query");
			Date end = new Date();
			logger.info("getEmailConfig.query took : " + (end.getTime() - start.getTime()) + " ms");
		} catch (DataAccessException e) {
			logger.error("Exception occured in getEmailConfiguration.query due to " + e.getMessage());
			throw new DataServiceException("Exception occured at SystemGeneratedEmailDaoImpl-getEmailConfiguration", e);
		}
		return emailConfigVOList;
	}

	@Override
	public List<EmailDataVO> getEmailConfigurationForBuyer(String buyerId) throws DataServiceException {
		logger.info("start SystemGeneratedEmailDaoImpl::getEmailConfigurationForBuyer");
		List<EmailDataVO> emailDataVOList;
		try {
			Date start = new Date();
			emailDataVOList = (List<EmailDataVO>) queryForList("getEmailConfigForBuyer.query", buyerId);
			Date end = new Date();
			logger.info("getEmailConfigForBuyer.query took : " + (end.getTime() - start.getTime()) + " ms");
		} catch (DataAccessException e) {
			logger.error("Exception occured in getEmailConfigForBuyer.query to " + e.getMessage());
			throw new DataServiceException(
					"Exception occured at SystemGeneratedEmailDaoImpl-getEmailConfigurationForBuyer", e);
		}
		return emailDataVOList;
	}

	@Override
	public void saveBuyerEmailData(List<EmailConfigVO> emailConfigVO) throws DataServiceException {
		logger.info("start SystemGeneratedEmailDaoImpl::saveBuyerEmailData");
		try {
			Date start = new Date();
			batchInsert("saveBuyerEmailData.query", emailConfigVO);
			Date end = new Date();
			logger.info("saveBuyerEmailData.query took : " + (end.getTime() - start.getTime()) + " ms");
		} catch (DataAccessException e) {
			logger.error("Exception occured in saveBuyerEmailData.query due to " + e.getMessage());
			throw new DataServiceException("Exception occured at SystemGeneratedEmailDaoImpl-saveBuyerEmailData", e);
		}

	}

	@Override
	public void saveBuyerEventMapping(List<EmailConfigVO> emailConfigVO) throws DataServiceException {
		logger.info("start SystemGeneratedEmailDaoImpl::saveBuyerEventMapping");
		try {
			Date start = new Date();
			batchInsert("saveBuyerEventMapping.insert", emailConfigVO);
			Date end = new Date();
			logger.info("saveBuyerEventMapping.insert took : " + (end.getTime() - start.getTime()) + " ms");
		} catch (DataAccessException e) {
			logger.error("Exception occured in saveBuyerEventMapping.query due to " + e.getMessage());
			throw new DataServiceException("Exception occured at SystemGeneratedEmailDaoImpl-saveBuyerEventMapping", e);
		}

	}

	@Override
	public void saveBuyerEventEmailData(List<EmailConfigVO> emailConfigVO) throws DataServiceException {
		logger.info("start SystemGeneratedEmailDaoImpl::saveBuyerEventMapping");
		try {
			Date start = new Date();
			batchInsert("saveBuyerEventEmailData.query", emailConfigVO);
			Date end = new Date();
			logger.info("saveBuyerEventEmailData.query took : " + (end.getTime() - start.getTime()) + " ms");
		} catch (DataAccessException e) {
			logger.error("Exception occured in saveBuyerEventEmailData.query due to " + e.getMessage());
			throw new DataServiceException("Exception occured at SystemGeneratedEmailDaoImpl-saveBuyerEventEmailData",
					e);
		}

	}

	@Override
	public Map<String, String> getBuyerEmailData(String buyerId) throws DataServiceException {
		logger.info("start SystemGeneratedEmailDaoImpl::getBuyerEmailData");
		Map<String, String> eventMapping;
		try {
			Date start = new Date();
			eventMapping = (Map<String, String>) queryForMap("getBuyerEmailData.query", buyerId, "paramKey",
					"paramValue");
			Date end = new Date();
			logger.info("getBuyerEmailData.query took : " + (end.getTime() - start.getTime()) + " ms");
		} catch (DataAccessException e) {
			logger.error("Exception occured in getBuyerEmailData.query due to " + e.getMessage());
			throw new DataServiceException("Exception occured at SystemGeneratedEmailDaoImpl-getBuyerEmailData", e);
		}
		return eventMapping;
	}

	@Override
	public void deleteExistingData(Integer buyerId) throws DataServiceException {
		logger.info("Start SystemGeneratedEmailDaoImpl::deleteExistingData");
		deleteBuyerEmailData(buyerId);
		deleteBuyerEventEmailData(buyerId);
		deleteBuyerEventTemplate(buyerId);
		deleteBuyerEventMapping(buyerId);
		logger.info("End SystemGeneratedEmailDaoImpl::deleteExistingData");
	}

	private void deleteBuyerEventTemplate(Integer buyerId) throws DataServiceException {

		logger.info("Start SystemGeneratedEmailDaoImpl::deleteBuyerEventTemplate");
		try {
			Date start = new Date();
			delete("deleteBuyerEventTemplate.query", buyerId);
			Date end = new Date();
			logger.info("SystemGeneratedEmailDaoImpl::deleteBuyerEventMapping.query took : "
					+ (end.getTime() - start.getTime()) + " ms");
		} catch (DataAccessException e) {
			logger.error("Exception occured in deleteBuyerEventTemplate.query due to " + e.getMessage());
			throw new DataServiceException("Exception occured at SystemGeneratedEmailDaoImpl-deleteBuyerEventTemplate",
					e);
		}
		logger.info("End SystemGeneratedEmailDaoImpl::deleteBuyerEventTemplate");

	}

	private void deleteBuyerEventMapping(Integer buyerId) throws DataServiceException {
		logger.info("Start SystemGeneratedEmailDaoImpl::deleteBuyerEventMapping");
		try {
			Date start = new Date();
			delete("deleteBuyerEventMapping.query", buyerId);
			Date end = new Date();
			logger.info("SystemGeneratedEmailDaoImpl::deleteBuyerEventMapping.query took : "
					+ (end.getTime() - start.getTime()) + " ms");
		} catch (DataAccessException e) {
			logger.error("Exception occured in deleteBuyerEventMapping.query due to " + e.getMessage());
			throw new DataServiceException("Exception occured at SystemGeneratedEmailDaoImpl-deleteBuyerEventMapping",
					e);
		}
		logger.info("End SystemGeneratedEmailDaoImpl::deleteBuyerEventMapping");
	}

	private void deleteBuyerEventEmailData(Integer buyerId) throws DataServiceException {
		logger.info("Start SystemGeneratedEmailDaoImpl::deleteBuyerEventEmailData");
		try {
			Date start = new Date();
			delete("deleteBuyerEventEmailData.query", buyerId);
			Date end = new Date();
			logger.info("SystemGeneratedEmailDaoImpl::deleteBuyerEventEmailData.query took : "
					+ (end.getTime() - start.getTime()) + " ms");
		} catch (DataAccessException e) {
			logger.error("Exception occured in deleteBuyerEventEmailData.query due to " + e.getMessage());
			throw new DataServiceException("Exception occured at SystemGeneratedEmailDaoImpl-deleteBuyerEventEmailData",
					e);
		}
		logger.info("End SystemGeneratedEmailDaoImpl::deleteBuyerEventEmailData");
	}

	private void deleteBuyerEmailData(Integer buyerId) throws DataServiceException {
		logger.info("Start SystemGeneratedEmailDaoImpl::deleteBuyerEmailData");
		try {
			Date start = new Date();
			delete("deleteBuyerEmailData.query", buyerId);
			Date end = new Date();
			logger.info("SystemGeneratedEmailDaoImpl::deleteBuyerEmailData.query took : "
					+ (end.getTime() - start.getTime()) + " ms");
		} catch (DataAccessException e) {
			logger.error("Exception occured in deleteBuyerEmailData.query due to " + e.getMessage());
			throw new DataServiceException("Exception occured at SystemGeneratedEmailDaoImpl-deleteBuyerEmailData", e);
		}
		logger.info("End SystemGeneratedEmailDaoImpl::deleteBuyerEventMapping");
	}

	@Override
	public Map<Long, Long> getEventTemplateConfig() throws DataServiceException {
		logger.info("start SystemGeneratedEmailDaoImpl::getEventTemplateConfig");
		Map<Long, Long> eventMapping;
		try {
			Date start = new Date();
			eventMapping = (Map<Long, Long>) queryForMap("getEventTemplateConfig.query", null, "eventId",
					"templateId");
			Date end = new Date();
			logger.info("getEventTemplateConfig.query took : " + (end.getTime() - start.getTime()) + " ms");
		} catch (DataAccessException e) {
			logger.error("Exception occured in getEventTemplateConfig.query due to " + e.getMessage());
			throw new DataServiceException("Exception occured at SystemGeneratedEmailDaoImpl-getEventTemplateConfig", e);
		}
		return eventMapping;
	}

	@Override
	public void saveBuyerEventTemplate(List<EmailConfigVO> emailConfigVO) throws DataServiceException {
		logger.info("start SystemGeneratedEmailDaoImpl::saveBuyerEventTemplate");
		try {
			Date start = new Date();
			batchInsert("saveBuyerEventTemplate.query", emailConfigVO);
			Date end = new Date();
			logger.info("saveBuyerEventTemplate.query took : " + (end.getTime() - start.getTime()) + " ms");
		} catch (DataAccessException e) {
			logger.error("Exception occured in saveBuyerEventTemplate.query due to " + e.getMessage());
			throw new DataServiceException("Exception occured at SystemGeneratedEmailDaoImpl-saveBuyerEventTemplate",
					e);
		}

	}
	
	@Override
	public Integer getEventIdBySurveyOption(EmailConfigVO emailConfigVO) throws DataServiceException {
		logger.info("start SystemGeneratedEmailDaoImpl::getEventIdBySurveyOption");
		Integer eventId;
		try {
			Date start = new Date();
			eventId = (Integer) queryForObject("getEventIdBySurveyOption.query", emailConfigVO);
			Date end = new Date();
			logger.info("getEventIdBySurveyOption.query took : " + (end.getTime() - start.getTime()) + " ms");
		} catch (DataAccessException e) {
			logger.error("Exception occured in getEventIdBySurveyOption.query due to " + e.getMessage());
			throw new DataServiceException("Exception occured at SystemGeneratedEmailDaoImpl-getEventIdBySurveyOption", e);
		}
		return eventId;
	}

	@Override
	public String getEmailTemplate(String eventName) throws DataServiceException {
		logger.info("start SystemGeneratedEmailDaoImpl::getEventIdBySurveyOption");
		String templateResponse;
		try {
			Date start = new Date();
			templateResponse = (String) queryForObject("getEmailTemplateByName.query", eventName);
			Date end = new Date();
			logger.info("getEventIdBySurveyOption.query took : " + (end.getTime() - start.getTime()) + " ms");
		} catch (DataAccessException e) {
			logger.error("Exception occured in getEventIdBySurveyOption.query due to " + e.getMessage());
			throw new DataServiceException("Exception occured at SystemGeneratedEmailDaoImpl-getEventIdBySurveyOption", e);
		}
		return templateResponse;
	}

	@Override
	public Map<String, String> getTemplateDetails() throws DataServiceException {
		logger.info("start SystemGeneratedEmailDaoImpl::getBuyerEmailData");
		Map<String, String> templateMap;
		try {
			Date start = new Date();
			templateMap = (Map<String, String>) queryForMap("getTemplateDetails.query", null, "templateName",
					"templateSource");
			Date end = new Date();
			logger.info("getTemplateDetails.query took : " + (end.getTime() - start.getTime()) + " ms");
		} catch (DataAccessException e) {
			logger.error("Exception occured in getTemplateDetails.query due to " + e.getMessage());
			throw new DataServiceException("Exception occured at SystemGeneratedEmailDaoImpl-getBuyerEmailData", e);
		}
		return templateMap;
	}

	@Override
	public Map<String, String> getSurveyEventMapping() throws DataServiceException {
		logger.info("start SystemGeneratedEmailDaoImpl::getSurveyEventMapping");
		Map<String, String> surveyEventMap;
		try {
			Date start = new Date();
			surveyEventMap = (Map<String, String>) queryForMap("getSurveyEventMapping.query", null, "surveyOptionId",
					"eventName");
			Date end = new Date();
			logger.info("getSurveyEventMapping.query took : " + (end.getTime() - start.getTime()) + " ms");
		} catch (DataAccessException e) {
			logger.error("Exception occured in getSurveyEventMapping.query due to " + e.getMessage());
			throw new DataServiceException("Exception occured at SystemGeneratedEmailDaoImpl-getSurveyEventMapping", e);
		}
		return surveyEventMap;
	}
	
	
}
