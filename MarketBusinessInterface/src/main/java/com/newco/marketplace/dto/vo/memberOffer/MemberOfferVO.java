package com.newco.marketplace.dto.vo.memberOffer;

import java.io.Serializable;
import java.sql.Timestamp;

import com.newco.marketplace.dto.vo.DocumentVO;

public class MemberOfferVO implements Serializable
{
	private static final long serialVersionUID = -1527770441184997895L;
	private int offerId;
	private String companyName;
	private String offerImagePath;
	private int clickCount;
	private boolean dealOfDayInd;
	private Timestamp createdDate;
	private String description;

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	private DocumentVO offerImageDetails;
	
	public DocumentVO getOfferImageDetails() {
		return offerImageDetails;
	}
	public void setOfferImageDetails(DocumentVO offerImageDetails) {
		this.offerImageDetails = offerImageDetails;
	}
	public int getOfferId() {
		return offerId;
	}
	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getOfferImagePath() {
		return offerImagePath;
	}
	public void setOfferImagePath(String offerImagePath) {
		this.offerImagePath = offerImagePath;
	}
	public int getClickCount() {
		return clickCount;
	}
	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}
	public boolean isDealOfDayInd() {
		return dealOfDayInd;
	}
	public void setDealOfDayInd(boolean dealOfDayInd) {
		this.dealOfDayInd = dealOfDayInd;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
}
