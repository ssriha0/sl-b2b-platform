package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing bid information.
 * @author Infosys
 *
 */

@XStreamAlias("bid")
@XmlAccessorType(XmlAccessType.FIELD)
public class Bid {
	
	@XStreamAlias("bidder")
	private String bidder;

	@XStreamAlias("bidPlacedResource")
	private Integer bidPlacedResource;
	
	@XStreamAlias("bidPlacedVendor")
	private Integer bidPlacedVendor;
	
	@XStreamAlias("bidDate")
	private String bidDate;
	
	@XStreamAlias("expirationDate")
	private String expirationDate;
	
	@XStreamAlias("totalBidPrice")
	private String totalBidPrice;
	
	@XStreamAlias("hourlyRate")
	private String hourlyRate;
	
	@XStreamAlias("estimatedTime")
	private Integer estimatedTime;
	
	@XStreamAlias("totalLabor")
	private String totalLabor;
	
	@XStreamAlias("materialEstimate")
	private String materialEstimate;
	
	@XStreamAlias("newServiceDateType")
	private String newServiceDateType;
	
	@XStreamAlias("newServiceStartDate")
	private String newServiceStartDate;
	
	@XStreamAlias("newServiceEndDate")
	private String newServiceEndDate;
	
	@XStreamAlias("newServiceStartTime")
	private String newServiceStartTime;
	
	@XStreamAlias("newServiceEndTime")
	private String newServiceEndTime;
	
	@XStreamAlias("comment")
	private String comment;

	public String getBidder() {
		return bidder;
	}

	public void setBidder(String bidder) {
		this.bidder = bidder;
	}

	public Integer getBidPlacedResource() {
		return bidPlacedResource;
	}

	public void setBidPlacedResource(Integer bidPlacedResource) {
		this.bidPlacedResource = bidPlacedResource;
	}

	public Integer getBidPlacedVendor() {
		return bidPlacedVendor;
	}

	public void setBidPlacedVendor(Integer bidPlacedVendor) {
		this.bidPlacedVendor = bidPlacedVendor;
	}

	public String getBidDate() {
		return bidDate;
	}

	public void setBidDate(String bidDate) {
		this.bidDate = bidDate;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getTotalBidPrice() {
		return totalBidPrice;
	}

	public void setTotalBidPrice(String totalBidPrice) {
		this.totalBidPrice = totalBidPrice;
	}


	public String getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(String hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public Integer getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(Integer estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	public String getTotalLabor() {
		return totalLabor;
	}

	public void setTotalLabor(String totalLabor) {
		this.totalLabor = totalLabor;
	}

	public String getMaterialEstimate() {
		return materialEstimate;
	}

	public void setMaterialEstimate(String materialEstimate) {
		this.materialEstimate = materialEstimate;
	}

	public String getNewServiceDateType() {
		return newServiceDateType;
	}

	public void setNewServiceDateType(String newServiceDateType) {
		this.newServiceDateType = newServiceDateType;
	}

	public String getNewServiceStartDate() {
		return newServiceStartDate;
	}

	public void setNewServiceStartDate(String newServiceStartDate) {
		this.newServiceStartDate = newServiceStartDate;
	}

	public String getNewServiceEndDate() {
		return newServiceEndDate;
	}

	public void setNewServiceEndDate(String newServiceEndDate) {
		this.newServiceEndDate = newServiceEndDate;
	}

	public String getNewServiceStartTime() {
		return newServiceStartTime;
	}

	public void setNewServiceStartTime(String newServiceStartTime) {
		this.newServiceStartTime = newServiceStartTime;
	}

	public String getNewServiceEndTime() {
		return newServiceEndTime;
	}

	public void setNewServiceEndTime(String newServiceEndTime) {
		this.newServiceEndTime = newServiceEndTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
