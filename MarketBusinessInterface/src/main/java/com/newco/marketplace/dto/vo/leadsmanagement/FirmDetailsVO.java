package com.newco.marketplace.dto.vo.leadsmanagement;

import java.sql.Timestamp;
import java.util.List;


public class FirmDetailsVO {
	
	private String firmId;
	private Double rating;
	private String rank;
	private Double distance;
	private String firmName;
	private String firmOwner;
	private String reviewerName;
	private String reviewComment;
	private String reviewRating;
	private String reviewdDate;
	private Timestamp businessStartDate;
	private Double leadPrice;
	private List<ReviewVO> reviewVO;
	private String lmsPartnerId;
	//Added for Contact Details for Firm,used in post Response
	private String email;
	private String phoneNo;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String leadProfileEmail;
	//Added for spnValidation
	private Integer spnId;
	//To display in order in mails
	private Integer providerFirmRank;
	//SL_19727
	private String locationTypeDesc;
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Double getLeadPrice() {
		return leadPrice;
	}

	public void setLeadPrice(Double leadPrice) {
		this.leadPrice = leadPrice;
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

	

	public Timestamp getBusinessStartDate() {
		return businessStartDate;
	}

	public void setBusinessStartDate(Timestamp businessStartDate) {
		this.businessStartDate = businessStartDate;
	}
    public String getFirmId() {
		return firmId;
	}

	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Integer getSpnId() {
		return spnId;
	}

	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public String getFirmOwner() {
		return firmOwner;
	}

	public void setFirmOwner(String firmOwner) {
		this.firmOwner = firmOwner;
	}

	public List<ReviewVO> getReviewVO() {
		return reviewVO;
	}

	public void setReviewVO(List<ReviewVO> reviewVO) {
		this.reviewVO = reviewVO;
	}

	public String getLmsPartnerId() {
		return lmsPartnerId;
	}

	public void setLmsPartnerId(String lmsPartnerId) {
		this.lmsPartnerId = lmsPartnerId;
	}

	public String getLeadProfileEmail() {
		return leadProfileEmail;
	}

	public void setLeadProfileEmail(String leadProfileEmail) {
		this.leadProfileEmail = leadProfileEmail;
	}

	public Integer getProviderFirmRank() {
		return providerFirmRank;
	}

	public void setProviderFirmRank(Integer providerFirmRank) {
		this.providerFirmRank = providerFirmRank;
	}

	public String getLocationTypeDesc() {
		return locationTypeDesc;
	}

	public void setLocationTypeDesc(String locationTypeDesc) {
		this.locationTypeDesc = locationTypeDesc;
	}
	
}
