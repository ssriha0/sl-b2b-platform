package com.newco.marketplace.dto.vo.buyersurvey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyerSurveyDTO {

	private Integer buyerId;
	private String buyerName;
	private String buyerLogo;
	private String buyerLogoBaseURL;
	private String userName;
	private EmailFooterDTO emailFooterDTO;
	private List<EmailEvent> emailEventList;
	private Map<String, String> buyerEmailDataMap;
	private Map<Integer,String> surveyTypeMap;
	private String templateResponse;
	private Map<String, String> templateMap;
	private Map<String, String> surveyEventMap;
	private String previewSignature;
	
	
	/**
	 * @return the buyerId
	 */
	public Integer getBuyerId() {
		return buyerId;
	}

	/**
	 * @param buyerId the buyerId to set
	 */
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	/**
	 * @return the buyerName
	 */
	public String getBuyerName() {
		return buyerName;
	}

	/**
	 * @param buyerName the buyerName to set
	 */
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	/**
	 * @return the buyerLogo
	 */
	public String getBuyerLogo() {
		return buyerLogo;
	}

	/**
	 * @param buyerLogo the buyerLogo to set
	 */
	public void setBuyerLogo(String buyerLogo) {
		this.buyerLogo = buyerLogo;
	}

	/**
	 * @return the buyerLogoBaseURL
	 */
	public String getBuyerLogoBaseURL() {
		return buyerLogoBaseURL;
	}

	/**
	 * @param buyerLogoBaseURL the buyerLogoBaseURL to set
	 */
	public void setBuyerLogoBaseURL(String buyerLogoBaseURL) {
		this.buyerLogoBaseURL = buyerLogoBaseURL;
	}

	/**
	 * @return the emailFooterDTO
	 */
	public EmailFooterDTO getEmailFooterDTO() {
		return emailFooterDTO;
	}

	/**
	 * @param emailFooterDTO the emailFooterDTO to set
	 */
	public void setEmailFooterDTO(EmailFooterDTO emailFooterDTO) {
		this.emailFooterDTO = emailFooterDTO;
	}

	/**
	 * @return the emailEventList
	 */
	public List<EmailEvent> getEmailEventList() {
		return emailEventList;
	}

	/**
	 * @param emailEventList the emailEventList to set
	 */
	public void setEmailEventList(List<EmailEvent> emailEventList) {
		this.emailEventList = emailEventList;
	}

	public Map<String, String> getBuyerEmailDataMap() {
		if(buyerEmailDataMap == null) {
			buyerEmailDataMap = new HashMap<String, String>();
		}
		return buyerEmailDataMap;
	}

	public void setBuyerEmailDataMap(Map<String, String> buyerEmailDataMap) {
		this.buyerEmailDataMap = buyerEmailDataMap;
	}

	/**
	 * @return the surveyTypeMap
	 */
	public Map<Integer,String> getSurveyTypeMap() {
		return surveyTypeMap;
	}

	/**
	 * @param surveyTypeMap the surveyTypeMap to set
	 */
	public void setSurveyTypeMap(Map<Integer,String> surveyTypeMap) {
		this.surveyTypeMap = surveyTypeMap;
	}

	/**
	 * @return the templateResponse
	 */
	public String getTemplateResponse() {
		return templateResponse;
	}

	/**
	 * @param templateResponse the templateResponse to set
	 */
	public void setTemplateResponse(String templateResponse) {
		this.templateResponse = templateResponse;
	}

	/**
	 * @return the templateMap
	 */
	public Map<String, String> getTemplateMap() {
		return templateMap;
	}

	/**
	 * @param templateMap the templateMap to set
	 */
	public void setTemplateMap(Map<String, String> templateMap) {
		this.templateMap = templateMap;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the surveyEventMap
	 */
	public Map<String, String> getSurveyEventMap() {
		return surveyEventMap;
	}

	/**
	 * @param surveyEventMap the surveyEventMap to set
	 */
	public void setSurveyEventMap(Map<String, String> surveyEventMap) {
		this.surveyEventMap = surveyEventMap;
	}

	/**
	 * @return the previewSignature
	 */
	public String getPreviewSignature() {
		return previewSignature;
	}

	/**
	 * @param previewSignature the previewSignature to set
	 */
	public void setPreviewSignature(String previewSignature) {
		this.previewSignature = previewSignature;
	}

}
