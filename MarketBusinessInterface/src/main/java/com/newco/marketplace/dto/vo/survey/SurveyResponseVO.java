/**
 * 
 */
package com.newco.marketplace.dto.vo.survey;

import java.sql.Date;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author schavda
 *
 */
public class SurveyResponseVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8365056398860346195L;
	//Table - survey_response_hdr
	private Long responseHdrId;
	private String serviceOrderID;
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
	private int answerId;
	private String comment;
	private Integer buyerId;
	private String leadId;

	
	public Integer getBuyerId(){
		return buyerId;
	}
	
	public void setBuyerId(Integer buyerId){
		this.buyerId=buyerId;
	}
	/**
	 * @return the answerId
	 */
	public int getAnswerId() {
		return answerId;
	}
	/**
	 * @param answerId the answerId to set
	 */
	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the entityId
	 */
	public int getEntityId() {
		return entityId;
	}
	/**
	 * @param entityId the entityId to set
	 */
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}
	/**
	 * @return the entityTypeId
	 */
	public int getEntityTypeId() {
		return entityTypeId;
	}
	/**
	 * @param entityTypeId the entityTypeId to set
	 */
	public void setEntityTypeId(int entityTypeId) {
		this.entityTypeId = entityTypeId;
	}
	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @return the overallScore
	 */
	public double getOverallScore() {
		return overallScore;
	}
	/**
	 * @param overallScore the overallScore to set
	 */
	public void setOverallScore(double overallScore) {
		this.overallScore = overallScore;
	}
	/**
	 * @return the questionId
	 */
	public int getQuestionId() {
		return questionId;
	}
	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	/**
	 * @return the responseHdrId
	 */
	public Long getResponseHdrId() {
		return responseHdrId;
	}
	/**
	 * @param responseHdrId the responseHdrId to set
	 */
	public void setResponseHdrId(Long responseHdrId) {
		this.responseHdrId = responseHdrId;
	}
	/**
	 * @return the surveyId
	 */
	public int getSurveyId() {
		return surveyId;
	}
	/**
	 * @param surveyId the surveyId to set
	 */
	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}
	/**
	 * @return the responseId
	 */
	public Long getResponseId() {
		return responseId;
	}
	/**
	 * @param responseId the responseId to set
	 */
	public void setResponseId(Long responseId) {
		this.responseId = responseId;
	}
	/**
	 * @return the soId
	 */
	public String getServiceOrderID() {
		return serviceOrderID;
	}
	/**
	 * @param soId the soId to set
	 */
	public void setServiceOrderID(String serviceOrderID) {
		this.serviceOrderID = serviceOrderID;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}
	
	
	
	
}
