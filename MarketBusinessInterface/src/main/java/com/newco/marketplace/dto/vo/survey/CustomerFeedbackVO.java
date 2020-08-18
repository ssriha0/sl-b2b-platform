/**
 * 
 */
package com.newco.marketplace.dto.vo.survey;

import java.sql.Date;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author hoza
 *
 */
public class CustomerFeedbackVO extends SerializableBaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6716512178834062631L;

	private Long responseHdrId;
	private String serviceOrderId;
	private Integer surveyId;
	private Integer ratedByEntityTypeId; //Type of the rating entity.. 10 or 20 buyer or Provider
	private Integer ratedByEntityId; //--> resource id for the given ratedentityTypeId
	private String ratedByFirstName;
	private String ratedByLastName;
	private String ratedByState;
	private String ratedByCity;
	private Double overallRatingScore; //Rating scroe given to the reciver
	private Integer overallRatingScoreImageId; //holds the image id for the rating given to this feedback by the buyer.. should be between 1 to 20
	private Date createdDate;
	private Date modifiedDate; //use this date for checking the age of the feedback
	private String modifiedBy;
	private Integer receivedByEntityTypeId;
	private Integer receivedByEntityId;
	private String receivedByFirstName;
	private String receivedByLastName;
	private String feedbackPrimaryCategory;
	private Integer feedbackPrimaryCategoryId;
	private Integer feedbackAge;

	
	private String comments;


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
	 * @return the serviceOrderId
	 */
	public String getServiceOrderId() {
		return serviceOrderId;
	}


	/**
	 * @param serviceOrderId the serviceOrderId to set
	 */
	public void setServiceOrderId(String serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}


	/**
	 * @return the surveyId
	 */
	public Integer getSurveyId() {
		return surveyId;
	}


	/**
	 * @param surveyId the surveyId to set
	 */
	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}


	/**
	 * @return the ratedByEntityTypeId
	 */
	public Integer getRatedByEntityTypeId() {
		return ratedByEntityTypeId;
	}


	/**
	 * @param ratedByEntityTypeId the ratedByEntityTypeId to set
	 */
	public void setRatedByEntityTypeId(Integer ratedByEntityTypeId) {
		this.ratedByEntityTypeId = ratedByEntityTypeId;
	}


	/**
	 * @return the ratedByEntityId
	 */
	public Integer getRatedByEntityId() {
		return ratedByEntityId;
	}


	/**
	 * @param ratedByEntityId the ratedByEntityId to set
	 */
	public void setRatedByEntityId(Integer ratedByEntityId) {
		this.ratedByEntityId = ratedByEntityId;
	}


	/**
	 * @return the ratedByFirstName
	 */
	public String getRatedByFirstName() {
		return ratedByFirstName;
	}


	/**
	 * @param ratedByFirstName the ratedByFirstName to set
	 */
	public void setRatedByFirstName(String ratedByFirstName) {
		this.ratedByFirstName = ratedByFirstName;
	}


	/**
	 * @return the ratedByLastName
	 */
	public String getRatedByLastName() {
		return ratedByLastName;
	}


	/**
	 * @param ratedByLastName the ratedByLastName to set
	 */
	public void setRatedByLastName(String ratedByLastName) {
		this.ratedByLastName = ratedByLastName;
	}


	/**
	 * @return the ratedByState
	 */
	public String getRatedByState() {
		return ratedByState;
	}


	/**
	 * @param ratedByState the ratedByState to set
	 */
	public void setRatedByState(String ratedByState) {
		this.ratedByState = ratedByState;
	}


	/**
	 * @return the ratedByCity
	 */
	public String getRatedByCity() {
		return ratedByCity;
	}


	/**
	 * @param ratedByCity the ratedByCity to set
	 */
	public void setRatedByCity(String ratedByCity) {
		this.ratedByCity = ratedByCity;
	}


	/**
	 * @return the overallRatingScore
	 */
	public Double getOverallRatingScore() {
		return overallRatingScore;
	}


	/**
	 * @param overallRatingScore the overallRatingScore to set
	 */
	public void setOverallRatingScore(Double overallRatingScore) {
		this.overallRatingScore = overallRatingScore;
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
	 * @return the receivedByEntityTypeId
	 */
	public Integer getReceivedByEntityTypeId() {
		return receivedByEntityTypeId;
	}


	/**
	 * @param receivedByEntityTypeId the receivedByEntityTypeId to set
	 */
	public void setReceivedByEntityTypeId(Integer receivedByEntityTypeId) {
		this.receivedByEntityTypeId = receivedByEntityTypeId;
	}


	/**
	 * @return the receivedByEntityId
	 */
	public Integer getReceivedByEntityId() {
		return receivedByEntityId;
	}


	/**
	 * @param receivedByEntityId the receivedByEntityId to set
	 */
	public void setReceivedByEntityId(Integer receivedByEntityId) {
		this.receivedByEntityId = receivedByEntityId;
	}


	/**
	 * @return the receivedByFirstName
	 */
	public String getReceivedByFirstName() {
		return receivedByFirstName;
	}


	/**
	 * @param receivedByFirstName the receivedByFirstName to set
	 */
	public void setReceivedByFirstName(String receivedByFirstName) {
		this.receivedByFirstName = receivedByFirstName;
	}


	/**
	 * @return the receivedByLastName
	 */
	public String getReceivedByLastName() {
		return receivedByLastName;
	}


	/**
	 * @param receivedByLastName the receivedByLastName to set
	 */
	public void setReceivedByLastName(String receivedByLastName) {
		this.receivedByLastName = receivedByLastName;
	}


	/**
	 * @return the feedbackPrimaryCategory
	 */
	public String getFeedbackPrimaryCategory() {
		return feedbackPrimaryCategory;
	}


	/**
	 * @param feedbackPrimaryCategory the feedbackPrimaryCategory to set
	 */
	public void setFeedbackPrimaryCategory(String feedbackPrimaryCategory) {
		this.feedbackPrimaryCategory = feedbackPrimaryCategory;
	}


	/**
	 * @return the feedbackPrimaryCategoryId
	 */
	public Integer getFeedbackPrimaryCategoryId() {
		return feedbackPrimaryCategoryId;
	}


	/**
	 * @param feedbackPrimaryCategoryId the feedbackPrimaryCategoryId to set
	 */
	public void setFeedbackPrimaryCategoryId(Integer feedbackPrimaryCategoryId) {
		this.feedbackPrimaryCategoryId = feedbackPrimaryCategoryId;
	}


	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}


	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}


	/**
	 * @return the feedbackAge
	 */
	public Integer getFeedbackAge() {
		return feedbackAge;
	}


	/**
	 * @param feedbackAge the feedbackAge to set
	 */
	public void setFeedbackAge(Integer feedbackAge) {
		this.feedbackAge = feedbackAge;
	}


	/**
	 * @return the overallRatingScoreImageId
	 */
	public Integer getOverallRatingScoreImageId() {
		return overallRatingScoreImageId;
	}


	/**
	 * @param overallRatingScoreImageId the overallRatingScoreImageId to set
	 */
	public void setOverallRatingScoreImageId(Integer overallRatingScoreImageId) {
		this.overallRatingScoreImageId = overallRatingScoreImageId;
	}

}
