package com.newco.marketplace.dto.vo.survey;

import java.sql.Date;

public class ExtendedSurveyResponseVO {
	private static final long serialVersionUID = 8365056398860346195L;
	//Table - survey_response_hdr
	private Long responseHdrId;
	private String serviceOrderID;
	private String surveyType;
	private int surveyOptionId;
	private int surveyId;
	private int entityTypeId;
	private int entityId;
	private double overallScore;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;
	//Table - survey_response
	private Long responseId;
	private int questionId;
	private int answer;
	private String comments;
	private Integer buyerId;
	private Integer reviewedInd;
	private String key;
	private String value;
	private String subSurveyType;
	public Long getResponseHdrId() {
		return responseHdrId;
	}
	public void setResponseHdrId(Long responseHdrId) {
		this.responseHdrId = responseHdrId;
	}
	public String getServiceOrderID() {
		return serviceOrderID;
	}
	public void setServiceOrderID(String serviceOrderID) {
		this.serviceOrderID = serviceOrderID;
	}
	public int getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}
	public int getEntityTypeId() {
		return entityTypeId;
	}
	public void setEntityTypeId(int entityTypeId) {
		this.entityTypeId = entityTypeId;
	}
	public int getEntityId() {
		return entityId;
	}
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}
	public double getOverallScore() {
		return overallScore;
	}
	public void setOverallScore(double overallScore) {
		this.overallScore = overallScore;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Long getResponseId() {
		return responseId;
	}
	public void setResponseId(Long responseId) {
		this.responseId = responseId;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	
	
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Integer getReviewedInd() {
		return reviewedInd;
	}
	public void setReviewedInd(Integer reviewedInd) {
		this.reviewedInd = reviewedInd;
	}
	public int getAnswer() {
		return answer;
	}
	public void setAnswer(int answer) {
		this.answer = answer;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getSurveyType() {
		return surveyType;
	}
	public void setSurveyType(String surveyType) {
		this.surveyType = surveyType;
	}
	public int getSurveyOptionId() {
		return surveyOptionId;
	}
	public void setSurveyOptionId(int surveyOptionId) {
		this.surveyOptionId = surveyOptionId;
	}
	public String getSubSurveyType() {
		return subSurveyType;
	}
	public void setSubSurveyType(String subSurveyType) {
		this.subSurveyType = subSurveyType;
	}
	
}
