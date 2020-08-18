package com.newco.marketplace.search.types;

import java.util.Date;

import com.newco.marketplace.dto.vo.survey.CustomerFeedbackVO;

public class CustomerFeedback {
	float rating;
	Date reviewDate;
	String comment;
	String reviewerName;
	Long responseHdrId;
	
	public CustomerFeedback() {
		// intentionally blank
	}
	
	public CustomerFeedback(CustomerFeedbackVO inVo) {
		this.comment = inVo.getComments();
		this.reviewerName = inVo.getRatedByFirstName() + " " + inVo.getRatedByLastName();
		this.reviewDate = inVo.getModifiedDate();
		this.rating = inVo.getOverallRatingScore().floatValue();
		this.responseHdrId = inVo.getResponseHdrId();
	}
	
	public Date getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getReviewerName() {
		return reviewerName;
	}
	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}

	public Long getResponseHdrId() {
		return responseHdrId;
	}

	public void setResponseHdrId(Long responseHdrId) {
		this.responseHdrId = responseHdrId;
	}
	
}
