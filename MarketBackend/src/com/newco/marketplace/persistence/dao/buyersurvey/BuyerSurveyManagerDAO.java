package com.newco.marketplace.persistence.dao.buyersurvey;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.systemgeneratedemail.EmailConfigVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.EmailDataVO;
import com.servicelive.common.exception.DataServiceException;

public interface BuyerSurveyManagerDAO {

	List<EmailConfigVO> getEmailConfiguration() throws DataServiceException;

	List<EmailDataVO> getEmailConfigurationForBuyer(String buyerId) throws DataServiceException;

	void saveBuyerEventMapping(List<EmailConfigVO> emailConfigVO) throws DataServiceException;

	void saveBuyerEmailData(List<EmailConfigVO> emailConfigVO) throws DataServiceException;

	Map<String, String> getBuyerEmailData(String buyerId) throws DataServiceException;

	void deleteExistingData(Integer buyerId) throws DataServiceException;

	void saveBuyerEventEmailData(List<EmailConfigVO> emailConfigVO) throws DataServiceException;

	Map<Long, Long> getEventTemplateConfig() throws DataServiceException;

	void saveBuyerEventTemplate(List<EmailConfigVO> buyerEventList) throws DataServiceException;

	Integer getEventIdBySurveyOption(EmailConfigVO emailConfigVO) throws DataServiceException;

	String getEmailTemplate(String eventName) throws DataServiceException;

	Map<String, String> getTemplateDetails() throws DataServiceException;

	Map<String, String> getSurveyEventMapping() throws DataServiceException;

	//Map<String, Integer> getTemplateConfig(List<String> eventNameList) throws DataServiceException;
}
