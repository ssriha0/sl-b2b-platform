package com.newco.marketplace.dto.vo.leadsmanagement;

import java.sql.Timestamp;

public class LeadPostedProVO {
	private String slLeadId;
	private Integer serviceLiveFirmId;
	private double serviceLiveFirmRating;
	private double serviceLiveFirmDistance;
	private int firmRank;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private String reviewerName;
	private String reviewComment;
	private double revieweRating;
	private Timestamp commentedDate;
	
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
	public double getRevieweRating() {
		return revieweRating;
	}
	public void setRevieweRating(double revieweRating) {
		this.revieweRating = revieweRating;
	}
	public Timestamp getCommentedDate() {
		return commentedDate;
	}
	public void setCommentedDate(Timestamp commentedDate) {
		this.commentedDate = commentedDate;
	}
	public String getSlLeadId() {
		return slLeadId;
	}
	public void setSlLeadId(String slLeadId) {
		this.slLeadId = slLeadId;
	}
	public Integer getServiceLiveFirmId() {
		return serviceLiveFirmId;
	}
	public void setServiceLiveFirmId(Integer serviceLiveFirmId) {
		this.serviceLiveFirmId = serviceLiveFirmId;
	}
	public double getServiceLiveFirmRating() {
		return serviceLiveFirmRating;
	}
	public void setServiceLiveFirmRating(double serviceLiveFirmRating) {
		this.serviceLiveFirmRating = serviceLiveFirmRating;
	}
	public double getServiceLiveFirmDistance() {
		return serviceLiveFirmDistance;
	}
	public void setServiceLiveFirmDistance(double serviceLiveFirmDistance) {
		this.serviceLiveFirmDistance = serviceLiveFirmDistance;
	}
	public int getFirmRank() {
		return firmRank;
	}
	public void setFirmRank(int firmRank) {
		this.firmRank = firmRank;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
}
