package com.newco.marketplace.business.buyersurvey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.newco.marketplace.business.businessImpl.survey.ExtendedSurveyBOImpl;
import com.newco.marketplace.dto.vo.buyersurvey.BuyerLogo;
import com.newco.marketplace.dto.vo.buyersurvey.BuyerSurveyDTO;
import com.newco.marketplace.dto.vo.buyersurvey.EmailEvent;
import com.newco.marketplace.dto.vo.survey.BuyerSurveyConfigVO;
import com.newco.marketplace.dto.vo.survey.SurveyOptionVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.EmailConfigVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.EmailDataVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.EmailPropertyKeyEnum;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.persistence.dao.buyersurvey.BuyerSurveyManagerDAO;
import com.newco.marketplace.persistence.iDao.buyer.IBuyerFeatureSetDAO;
import com.newco.marketplace.util.PropertiesUtils;
import com.servicelive.common.exception.DataServiceException;

public class BuyerSurveyManagerBOImpl implements BuyerSurveyManagerBO {

	private static final Logger logger = Logger.getLogger("BuyerSurveyManagerBOImpl");
	private static final String htmlBreak = "<br />";


	private BuyerSurveyManagerDAO surveyManagerDAO;
	private ExtendedSurveyBOImpl extendedSurveyBO;
	private IBuyerFeatureSetDAO buyerFeatureSetDAO;

	public ExtendedSurveyBOImpl getExtendedSurveyBO() {
		return extendedSurveyBO;
	}

	public void setExtendedSurveyBO(ExtendedSurveyBOImpl extendedSurveyBO) {
		this.extendedSurveyBO = extendedSurveyBO;
	}

	public BuyerSurveyManagerDAO getSurveyManagerDAO() {
		return surveyManagerDAO;
	}

	/**
	 * @return the buyerFeatureSetDAO
	 */
	public IBuyerFeatureSetDAO getBuyerFeatureSetDAO() {
		return buyerFeatureSetDAO;
	}

	/**
	 * @param buyerFeatureSetDAO the buyerFeatureSetDAO to set
	 */
	public void setBuyerFeatureSetDAO(IBuyerFeatureSetDAO buyerFeatureSetDAO) {
		this.buyerFeatureSetDAO = buyerFeatureSetDAO;
	}

	public void setSurveyManagerDAO(BuyerSurveyManagerDAO surveyManagerDAO) {
		this.surveyManagerDAO = surveyManagerDAO;
	}

	@Override
	public BuyerSurveyDTO getEmailConfiguration(int buyerId) throws BusinessServiceException {
		BuyerSurveyDTO buyerSurveyDTO = new BuyerSurveyDTO();
		try {
			String buyerLogoBaseURL = PropertiesUtils.getPropertyValue("buyer_email_logo_base_url");
			buyerSurveyDTO.setBuyerLogoBaseURL(buyerLogoBaseURL);
			
			Map<String, String> buyerEmailDataMap = surveyManagerDAO.getBuyerEmailData(String.valueOf(buyerId));
			buyerSurveyDTO.setBuyerEmailDataMap(buyerEmailDataMap);
			
			List<EmailConfigVO> emailConfigVOList = surveyManagerDAO.getEmailConfiguration();
			List<SurveyOptionVO> surveyOptionVOs = extendedSurveyBO.getSurveyOptions();
			String feature = buyerFeatureSetDAO.getFeature(buyerId, "CONSUMER_SURVEY");
			BuyerSurveyConfigVO buyerSurveyConfigVO = extendedSurveyBO.getBuyerSurveySelectedDetails(buyerId);

			Map<Integer, String> surveyTypeMap = populateSurveyOption(surveyOptionVOs);
			buyerSurveyDTO.setSurveyTypeMap(surveyTypeMap);

			List<EmailDataVO> buyerEmailDataVOList = surveyManagerDAO
					.getEmailConfigurationForBuyer(String.valueOf(buyerId));
			logger.info("buyerEmailDataVOList size" + buyerEmailDataVOList.size());
			Map<Integer, EmailEvent> buyerEmailConfigMap = populateBuyerEmailConfiguration(buyerEmailDataVOList);

			List<EmailEvent> emailEventList = populateEventList(emailConfigVOList, surveyOptionVOs, buyerEmailConfigMap,
					buyerSurveyConfigVO, feature);
			buyerSurveyDTO.setEmailEventList(emailEventList);

			Map<String, String> templateMap = surveyManagerDAO.getTemplateDetails();
			trimTemplateSource(templateMap);
			buyerSurveyDTO.setTemplateMap(templateMap);

			Map<String, String> surveyEventMap = surveyManagerDAO.getSurveyEventMapping();
			buyerSurveyDTO.setSurveyEventMap(surveyEventMap);

		} catch (DataServiceException | com.newco.marketplace.exception.core.DataServiceException e) {
			logger.error("Error while accessing EmailConfiguration :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
		return buyerSurveyDTO;
	}

	private void trimTemplateSource(Map<String, String> templateMap) {
		for (String key : templateMap.keySet()) {
			String templateSource=templateMap.get(key);
			templateSource = StringUtils.substringBetween(templateSource, "<div id=\"body\">", "</div>");
			templateMap.put(key, templateSource);
		}
	}

	private Map<Integer, String> populateSurveyOption(List<SurveyOptionVO> surveyOptionVOList) {
		Map<Integer,String> surveyTypeMap= new HashMap<Integer,String>();
		if(!CollectionUtils.isEmpty(surveyOptionVOList)) {
			for (SurveyOptionVO surveyOptionVO : surveyOptionVOList) {
				surveyTypeMap.put(surveyOptionVO.getId(),surveyOptionVO.getType());
			}
		}
		return surveyTypeMap;
	}

	private Map<Integer, EmailEvent> populateBuyerEmailConfiguration(List<EmailDataVO> buyerEmailDataVOList) {
		Map<Integer, EmailEvent> buyerEmailConfigMap = new HashMap<Integer, EmailEvent>();
		if (!CollectionUtils.isEmpty(buyerEmailDataVOList)) {
			EmailEvent emailEvent;
			for (EmailDataVO emailDataVO : buyerEmailDataVOList) {
				emailEvent = new EmailEvent();
				emailEvent.setEventId(emailDataVO.getEventId());
				emailEvent.setActive(emailDataVO.isActive());
				if (EmailPropertyKeyEnum.BUYERSIGNATURE.getKey().equals(emailDataVO.getParamKey())) {
					emailEvent.setSignature(emailDataVO.getParamValue());
				}
				buyerEmailConfigMap.put(emailDataVO.getEventId(), emailEvent);
			}
		}
		return buyerEmailConfigMap;
	}

	private List<EmailEvent> populateEventList(List<EmailConfigVO> emailConfigVOList,
			List<SurveyOptionVO> surveyOptionVOs, Map<Integer, EmailEvent> buyerEmailConfigMap,
			BuyerSurveyConfigVO buyerSurveyConfigVO, String feature) {
		List<EmailEvent> emailEventList = new ArrayList<EmailEvent>();
		EmailEvent emailEvent = null;
		Map<String, EmailEvent> emailEventMap = new HashMap<String, EmailEvent>();
		List<EmailEvent> statusEventList;
		for (EmailConfigVO emailConfigVO : emailConfigVOList) {

			if (emailConfigVO.getEventType().equalsIgnoreCase("CONSUMER_SURVEY")
					|| emailConfigVO.getSubEventName() != null) {

				String key = getEmailEvent(emailEventMap, emailConfigVO);
				emailEvent = emailEventMap.get(key);

				if (emailEvent == null) {
					emailEvent = populateEventDetails(emailConfigVO);
					statusEventList = new ArrayList<EmailEvent>();
					if (emailConfigVO.getEventType().equalsIgnoreCase("CONSUMER_SURVEY")) {
						emailEvent.setSurveyOptionList(surveyOptionVOs);
						if(null != buyerSurveyConfigVO) {
							emailEvent.setSurveyOptionId(buyerSurveyConfigVO.getSurveyOptionId());
						}
						if(StringUtils.isBlank(feature)) {
							emailEvent.setSurveyFeature("Disabled");
						}
					}
				} else {
					statusEventList = emailEvent.getStatusEvent();
					emailEventList.remove(emailEvent);
				}
				EmailEvent statusEvent = populateSubEvent(emailConfigVO, buyerEmailConfigMap);
				statusEventList.add(statusEvent);
				emailEvent.setStatusEvent(statusEventList);
				emailEventMap.put(key, emailEvent);
			} else {
				emailEvent = populateEventDetails(emailConfigVO);
			}

			Integer eventId = emailEvent.getEventId().equals(emailConfigVO.getEventId()) ? emailEvent.getEventId()
					: emailConfigVO.getEventId();

			if (buyerEmailConfigMap.get(eventId) != null) {
				EmailEvent buyerEvent = buyerEmailConfigMap.get(eventId);
				emailEvent.setActive(buyerEvent.isActive());
				emailEvent.setSignature(buyerEvent.getSignature().replaceAll(htmlBreak, "\r\n"));
				if (emailConfigVO.getEventType().equalsIgnoreCase("CONSUMER_SURVEY")) {
					emailEvent.setStatusOption(emailConfigVO.getSubEventName());
					if(!emailEvent.isActive()) {
						emailEvent.setSurveyOptionId(null);
					}
				}
			}
			emailEventList.add(emailEvent);

		}
		return emailEventList;
	}

	private String getEmailEvent(Map<String, EmailEvent> emailEventMap, EmailConfigVO emailConfigVO) {
		String key;
		if (emailConfigVO.getEventType().equalsIgnoreCase("CONSUMER_SURVEY")) {
			key = emailConfigVO.getEventType();
		} else {
			key = emailConfigVO.getEventName();
		}
		return key;
	}

	private EmailEvent populateEventDetails(EmailConfigVO emailConfigVO) {
		EmailEvent emailEvent;
		emailEvent = new EmailEvent();
		emailEvent.setEventId(emailConfigVO.getEventId());
		emailEvent.setEventName(emailConfigVO.getEventName());
		emailEvent.setEventDescr(emailConfigVO.getEventDescr());
		emailEvent.setEventType(emailConfigVO.getEventType());
		emailEvent.setActive(emailConfigVO.isActive());
		return emailEvent;
	}

	private EmailEvent populateSubEvent(EmailConfigVO emailConfigVO, Map<Integer, EmailEvent> buyerEmailConfigMap) {
		EmailEvent statusEvent = new EmailEvent();
		statusEvent.setEventId(emailConfigVO.getEventId());
		statusEvent.setEventName(emailConfigVO.getSubEventName());
		statusEvent.setEventDescr(emailConfigVO.getEventDescr());
		EmailEvent buyerEmailEvent =buyerEmailConfigMap.get(emailConfigVO.getEventId());
		if(null != buyerEmailEvent) {
			statusEvent.setActive(buyerEmailEvent.isActive());
		}
		return statusEvent;
	}

	@Override
	public void saveBuyerConfiguration(BuyerSurveyDTO buyerSurveyDTO) throws BusinessServiceException {
		List<EmailConfigVO> emailConfigVOList = populateBuyerEmailData(buyerSurveyDTO);
		try {
			surveyManagerDAO.deleteExistingData(buyerSurveyDTO.getBuyerId());
			surveyManagerDAO.saveBuyerEmailData(emailConfigVOList);
			Map<Long, Long> eventConfigMap = surveyManagerDAO.getEventTemplateConfig();
			Map<String, String> eventTemplateConfigMap = new HashMap<>();
			for (Long key : eventConfigMap.keySet()) {
				eventTemplateConfigMap.put(String.valueOf(key), String.valueOf(eventConfigMap.get(key)));
			}
			List<EmailConfigVO> buyerEventList = populateBuyerEvent(buyerSurveyDTO, eventTemplateConfigMap);
			surveyManagerDAO.saveBuyerEventMapping(buyerEventList);
			surveyManagerDAO.saveBuyerEventEmailData(buyerEventList);
			surveyManagerDAO.saveBuyerEventTemplate(buyerEventList);
		} catch (DataServiceException e) {
			logger.error("Error in saveBuyerConfiguration :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
	}

	private List<EmailConfigVO> populateBuyerEmailData(BuyerSurveyDTO buyerSurveyDTO) {
		List<EmailConfigVO> emailConfigVOList = new ArrayList<>();
		EmailConfigVO emailConfigVO = populateEmailConfigVO(buyerSurveyDTO.getBuyerId(),
				EmailPropertyKeyEnum.BUYERNAME.getKey(), buyerSurveyDTO.getBuyerName(),buyerSurveyDTO.getUserName());
		emailConfigVOList.add(emailConfigVO);

		emailConfigVO = populateEmailConfigVO(buyerSurveyDTO.getBuyerId(), EmailPropertyKeyEnum.BUYERLOGO.getKey(),
				buyerSurveyDTO.getBuyerLogo(),buyerSurveyDTO.getUserName());
		emailConfigVOList.add(emailConfigVO);

		emailConfigVO = populateEmailConfigVO(buyerSurveyDTO.getBuyerId(), EmailPropertyKeyEnum.FACEBOOKLINK.getKey(),
				buyerSurveyDTO.getEmailFooterDTO().getFacebookLink(),buyerSurveyDTO.getUserName());
		emailConfigVOList.add(emailConfigVO);

		emailConfigVO = populateEmailConfigVO(buyerSurveyDTO.getBuyerId(), EmailPropertyKeyEnum.TWITTERLINK.getKey(),
				buyerSurveyDTO.getEmailFooterDTO().getTwitterLink(),buyerSurveyDTO.getUserName());
		emailConfigVOList.add(emailConfigVO);

		emailConfigVO = populateEmailConfigVO(buyerSurveyDTO.getBuyerId(), EmailPropertyKeyEnum.INSTAGRAMLINK.getKey(),
				buyerSurveyDTO.getEmailFooterDTO().getInstagramLink(),buyerSurveyDTO.getUserName());
		emailConfigVOList.add(emailConfigVO);

		emailConfigVO = populateEmailConfigVO(buyerSurveyDTO.getBuyerId(), EmailPropertyKeyEnum.PINTERESTLINK.getKey(),
				buyerSurveyDTO.getEmailFooterDTO().getPinterestLink(),buyerSurveyDTO.getUserName());
		emailConfigVOList.add(emailConfigVO);

		emailConfigVO = populateEmailConfigVO(buyerSurveyDTO.getBuyerId(), EmailPropertyKeyEnum.COMPANYLINK.getKey(),
				buyerSurveyDTO.getEmailFooterDTO().getCompanyLink(),buyerSurveyDTO.getUserName());
		emailConfigVOList.add(emailConfigVO);

		emailConfigVO = populateEmailConfigVO(buyerSurveyDTO.getBuyerId(),
				EmailPropertyKeyEnum.SUPPORTPAGELINK.getKey(),
				buyerSurveyDTO.getEmailFooterDTO().getCustomerSupportLink(),buyerSurveyDTO.getUserName());
		emailConfigVOList.add(emailConfigVO);

		emailConfigVO = populateEmailConfigVO(buyerSurveyDTO.getBuyerId(),
				EmailPropertyKeyEnum.STOREPAGELINK.getKey(),
				buyerSurveyDTO.getEmailFooterDTO().getStorePageLink(),buyerSurveyDTO.getUserName());
		emailConfigVOList.add(emailConfigVO);

		emailConfigVO = populateEmailConfigVO(buyerSurveyDTO.getBuyerId(),
				EmailPropertyKeyEnum.TERMSANDCONDITIONSLINK.getKey(),
				buyerSurveyDTO.getEmailFooterDTO().getTermsLink(),buyerSurveyDTO.getUserName());
		emailConfigVOList.add(emailConfigVO);

		emailConfigVO = populateEmailConfigVO(buyerSurveyDTO.getBuyerId(),
				EmailPropertyKeyEnum.PRIVACYPOLICYLINK.getKey(),
				buyerSurveyDTO.getEmailFooterDTO().getPrivacyPolicyLink(),buyerSurveyDTO.getUserName());
		emailConfigVOList.add(emailConfigVO);

		emailConfigVO = populateEmailConfigVO(buyerSurveyDTO.getBuyerId(), EmailPropertyKeyEnum.ADDRESS.getKey(),
				buyerSurveyDTO.getEmailFooterDTO().getAddress(),buyerSurveyDTO.getUserName());
		emailConfigVOList.add(emailConfigVO);

		return emailConfigVOList;
	}

	private EmailConfigVO populateEmailConfigVO(Integer buyerId, String key, String value, String modifiedBy) {
		EmailConfigVO emailConfigVO = new EmailConfigVO();
		emailConfigVO.setBuyerId(buyerId);
		emailConfigVO.setParamKey(key);
		emailConfigVO.setParamValue(StringEscapeUtils.escapeHtml(value));
		emailConfigVO.setModifiedBy(modifiedBy);
		return emailConfigVO;
	}


	private List<EmailConfigVO> populateBuyerEvent(BuyerSurveyDTO buyerSurveyDTO,
			Map<String, String> eventTemplateConfigMap) throws BusinessServiceException, DataServiceException {
		List<EmailConfigVO> emailConfigVOList = new ArrayList<EmailConfigVO>();
		EmailConfigVO emailConfigVO;
		for (EmailEvent emailEvent : buyerSurveyDTO.getEmailEventList()) {
			if (emailEvent.isActive()) {
				emailConfigVO = new EmailConfigVO();
				emailConfigVO.setBuyerId(buyerSurveyDTO.getBuyerId());
				emailConfigVO.setModifiedBy(buyerSurveyDTO.getUserName());
				if (null != emailEvent.getStatusEvent()) {
					emailConfigVO.setEventId(emailEvent.getStatusEventOption());
				} else {
					emailConfigVO.setEventId(emailEvent.getEventId());
				}
				emailConfigVO.setActive(emailEvent.isActive());
				emailConfigVO.setEventParamKey(EmailPropertyKeyEnum.BUYERSIGNATURE.getKey());
				emailConfigVO.setEventParamValue(emailEvent.getSignature().replaceAll("\\r\\n|\\r|\\n", htmlBreak));
				
				if (emailEvent.getEventType().equalsIgnoreCase("CONSUMER_SURVEY")) {
					emailConfigVO.setSurveyOptionId(emailEvent.getSurveyOptionId());
					emailConfigVO.setEventStatus(emailEvent.getStatusOption());
					
					extendedSurveyBO.saveBuyerConfiguration(buyerSurveyDTO.getBuyerId(), emailEvent.getSurveyOptionId(),
							buyerSurveyDTO.getSurveyTypeMap().get(emailEvent.getSurveyOptionId()),buyerSurveyDTO.getUserName());
					Integer eventId=surveyManagerDAO.getEventIdBySurveyOption(emailConfigVO);
					emailConfigVO.setEventId(eventId);
				}
				String templateId = eventTemplateConfigMap.get(String.valueOf(emailConfigVO.getEventId()));
				emailConfigVO.setTemplateId(Integer.valueOf(templateId));
				emailConfigVOList.add(emailConfigVO);
			}
		}
		return emailConfigVOList;
	}

	@Override
	public BuyerLogo getBuyerLogoProperties() throws BusinessServiceException {
		BuyerLogo buyerLogo = new BuyerLogo();
		String maxWidth=PropertiesUtils.getPropertyValue("email_logo_max_width");
		String maxHeight=PropertiesUtils.getPropertyValue("email_logo_max_height");
		String maxSize=PropertiesUtils.getPropertyValue("email_logo_max_size");
		String uploadPath=PropertiesUtils.getPropertyValue("buyer_email_logo_path");
		
		buyerLogo.setMaxHeight(Integer.valueOf(maxHeight));
		buyerLogo.setMaxWidth(Integer.valueOf(maxWidth));
		buyerLogo.setMaxSize(Integer.valueOf(maxSize));
		buyerLogo.setUploadPath(uploadPath);
		return buyerLogo;
	}
	
	@Override
	public String getEmailTemplate(BuyerSurveyDTO buyerSurveyDTO, String eventName) throws BusinessServiceException {
		String templateResponse;
		try {
			
			templateResponse = surveyManagerDAO.getEmailTemplate(eventName);
			String uploadPath=PropertiesUtils.getPropertyValue("buyer_email_logo_base_url");
			templateResponse = populateTemplate(templateResponse,"${BUYER_NAME}",buyerSurveyDTO.getBuyerName());
			templateResponse = populateTemplate(templateResponse,"[BUYER_NAME]",buyerSurveyDTO.getBuyerName());
			templateResponse = populateTemplate(templateResponse,"${BUYER_LOGO}",uploadPath+buyerSurveyDTO.getBuyerLogo());
			templateResponse = populateTemplate(templateResponse,"${MAX_WIDTH}",PropertiesUtils.getPropertyValue("email_logo_max_width"));
			templateResponse = populateTemplate(templateResponse,"${MAX_HEIGHT}",PropertiesUtils.getPropertyValue("email_logo_max_height"));
			templateResponse = populateTemplate(templateResponse,"${BUYER_SIGNATURE}",buyerSurveyDTO.getPreviewSignature());
			templateResponse = populateTemplate(templateResponse,"${FACEBOOK_LINK}",buyerSurveyDTO.getEmailFooterDTO().getFacebookLink());
			templateResponse = populateTemplate(templateResponse,"${TWITTER_LINK}",buyerSurveyDTO.getEmailFooterDTO().getTwitterLink());
			templateResponse = populateTemplate(templateResponse,"${INSTAGRAM_LINK}",buyerSurveyDTO.getEmailFooterDTO().getInstagramLink());
			templateResponse = populateTemplate(templateResponse,"${PINTEREST_LINK}",buyerSurveyDTO.getEmailFooterDTO().getPinterestLink()); 
			templateResponse = populateTemplate(templateResponse,"${ADDRESS}",buyerSurveyDTO.getEmailFooterDTO().getAddress());
			templateResponse = populateTemplate(templateResponse,"${COMPANY_LINK}",buyerSurveyDTO.getEmailFooterDTO().getCompanyLink());
			templateResponse = populateTemplate(templateResponse,"${SUPPORTPAGE_LINK}",buyerSurveyDTO.getEmailFooterDTO().getCustomerSupportLink());
			templateResponse = populateTemplate(templateResponse,"${STOREPAGE_LINK}",buyerSurveyDTO.getEmailFooterDTO().getStorePageLink());
			templateResponse = populateTemplate(templateResponse,"${TERMSANDCONDITIONS_LINK}",buyerSurveyDTO.getEmailFooterDTO().getTermsLink());
			templateResponse = populateTemplate(templateResponse,"${PRIVACYPOLICY_LINK}",buyerSurveyDTO.getEmailFooterDTO().getPrivacyPolicyLink());
			
		} catch (DataServiceException e) {
			logger.error("Error in getEmailTemplate :" + e.getMessage());
			throw new BusinessServiceException(e);
		}

		return templateResponse;
	}
	
	private String populateTemplate(String data,String oldValue, String newValue) {
		if(StringUtils.isBlank(newValue)) {
			int startIndex=data.indexOf(oldValue);
			int endIndex=data.indexOf("<a", startIndex);
			if(oldValue.equalsIgnoreCase("${PINTEREST_LINK}") || oldValue.equalsIgnoreCase("${PRIVACYPOLICY_LINK}")) {
				endIndex=data.indexOf("</a>", startIndex);
			}
			endIndex = endIndex >  0 ? endIndex : data.indexOf("</a>", startIndex);
			data = data.replace(data.substring(startIndex-9, endIndex), "");
			
			if (oldValue.equalsIgnoreCase("${PRIVACYPOLICY_LINK}")) {
				int lastIndex = data.lastIndexOf("|");
				if (lastIndex > 0) {
					data = replaceChar(data,' ',lastIndex);
				}
			}
			
			return data;
		}
		return data.replace(oldValue, newValue);
	}
	
	private String replaceChar(String str, char ch, int index) {
	    StringBuilder dataBuilder = new StringBuilder(str);
	    dataBuilder.setCharAt(index, ch);
	    return dataBuilder.toString();
	}
}
