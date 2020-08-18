/**
 * 
 */
package com.newco.marketplace.dto.vo.survey;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author schavda
 *
 */
public class SurveyVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8802362288980376338L;
	private String serviceOrderID;
	private int surveyId;
	private int statusId; //SurveyStatusVO
	private int surveyTypeId; //SurveyTypeVO
	private String surveyType;
	private int entityTypeId; //EntityTypeVO
	private String entityType;
	private String surveyComments;
	private String title;
	private String author;
	private String instructions;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;
	
	private ArrayList<SurveyQuestionVO> questions;
	private double overallScore;
	
	private int userId;
	private boolean responseExists;
	private boolean missingReqAnswers;
	private boolean closed;
	
	private boolean isBatch;
	
	private String leadId;
	private Map<String,Integer> answerIdMap=new HashMap<String,Integer>();
	
	
	
	public boolean isBatch() {
		return isBatch;
	}

	public void setBatch(boolean isBatch) {
		this.isBatch = isBatch;
	}

	/**
	 * @return the closed
	 */
	public boolean isClosed() {
		return closed;
	}

	/**
	 * @param closed the closed to set
	 */
	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	/**
	 * @return the bResponseExists
	 */
	public boolean isResponseExists() {
		return responseExists;
	}

	/**
	 * @param responseExists the bResponseExists to set
	 */
	public void setResponseExists(boolean responseExists) {
		this.responseExists = responseExists;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
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
	 * @return the intructions
	 */
	public String getInstructions() {
		return instructions;
	}

	/**
	 * @param intructions the intructions to set
	 */
	public void setInstructions(String instructions) {
		this.instructions = instructions;
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
	 * @return the questions
	 */
	public ArrayList<SurveyQuestionVO> getQuestions() {
		return questions;
	}

	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(ArrayList<SurveyQuestionVO> questions) {
		this.questions = questions;
	}

	/**
	 * @return the statusId
	 */
	public int getStatusId() {
		return statusId;
	}

	/**
	 * @param statusId the statusId to set
	 */
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	/**
	 * @return the surveyTypeId
	 */
	public int getSurveyTypeId() {
		return surveyTypeId;
	}

	/**
	 * @param surveyTypeId the surveyTypeId to set
	 */
	public void setSurveyTypeId(int surveyTypeId) {
		this.surveyTypeId = surveyTypeId;
	}

	/**
	 * @return the suveryId
	 */
	public int getSurveyId() {
		return surveyId;
	}

	/**
	 * @param suveryId the suveryId to set
	 */
	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the serviceOrderID
	 */
	public String getServiceOrderID() {
		return serviceOrderID;
	}

	/**
	 * @param serviceOrderID the serviceOrderID to set
	 */
	public void setServiceOrderID(String serviceOrderID) {
		this.serviceOrderID = serviceOrderID;
	}

	/**
	 * @return the missingReqAnswers
	 */
	public boolean isMissingReqAnswers() {
		return missingReqAnswers;
	}

	/**
	 * @param missingReqAnswers the missingReqAnswers to set
	 */
	public void setMissingReqAnswers(boolean missingReqAnswers) {
		this.missingReqAnswers = missingReqAnswers;
	}

	/**
	 * @return the entityType
	 */
	public String getEntityType() {
		return entityType;
	}

	/**
	 * @param entityType the entityType to set
	 */
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	/**
	 * @return the surveyType
	 */
	public String getSurveyType() {
		return surveyType;
	}

	/**
	 * @param surveyType the surveyType to set
	 */
	public void setSurveyType(String surveyType) {
		this.surveyType = surveyType;
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

	public String getSurveyComments() {
		return surveyComments;
	}

	public void setSurveyComments(String surveyComments) {
		this.surveyComments = surveyComments;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public Map<String, Integer> getAnswerIdMap() {
		return answerIdMap;
	}

	public void setAnswerIdMap(Map<String, Integer> answerIdMap) {
		this.answerIdMap = answerIdMap;
	}
	
	
	

}
