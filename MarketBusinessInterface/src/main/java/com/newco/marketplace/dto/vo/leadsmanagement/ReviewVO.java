package com.newco.marketplace.dto.vo.leadsmanagement;

import java.util.Date;

public class ReviewVO {
	
	private Integer firmId;
	private String reviewerName;
	private String reviewComment;
	private String reviewRating;
	private String reviewdDate;
	private Date reviewDateObj;
	private Double reviewRatingObj;
	private String question;
	private Integer score;
	
	public Integer getFirmId() {
		return firmId;
	}
	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}
	public String getReviewerName() {
		return reviewerName;
	}
	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}
	public String getReviewComment() {
		return reviewComment;
	}
	public void setReviewComment(String reviewComment) {
		this.reviewComment = reviewComment;
	}
	public String getReviewRating() {
		return reviewRating;
	}
	public void setReviewRating(String reviewRating) {
		this.reviewRating = reviewRating;
	}
	public String getReviewdDate() {
		return reviewdDate;
	}
	public void setReviewdDate(String reviewdDate) {
		this.reviewdDate = reviewdDate;
	}
	
	public Date getReviewDateObj() {
		return reviewDateObj;
	}
	public void setReviewDateObj(Date reviewDateObj) {
		this.reviewDateObj = reviewDateObj;
	}
	public Double getReviewRatingObj() {
		return reviewRatingObj;
	}
	public void setReviewRatingObj(Double reviewRatingObj) {
		this.reviewRatingObj = reviewRatingObj;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
}