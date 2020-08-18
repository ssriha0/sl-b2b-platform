package com.newco.marketplace.api.mobile.beans.so.placeBid;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing request information for the 
 * MobilePlaceBid Request
 * 
 * @author Infosys
 * 
 */
@XSD(name = "mobilePlaceBidRequest.xsd", path = "/resources/schemas/mobile/")
@XmlRootElement(name = "soPlaceBidRequest")
@XStreamAlias("soPlaceBidRequest")
public class MobilePlaceBidRequest {
	
	@XStreamAlias("bidResourceId")
	private Integer bidResourceId;
	
	@XStreamAlias("priceType")   
	private String priceType;
	
	@XStreamAlias("laborPrice")   
	private Double laborPrice;
	
	@XStreamAlias("laborHourlyRate")
	private Double laborHourlyRate;
	
	@XStreamAlias("laborHours")
	private Integer laborHours;

	@XStreamAlias("partsPrice")
	private Double partsPrice;
	
	@XStreamAlias("bidExpirationDate")
	private String bidExpirationDate;
	
	@XStreamAlias("bidExpirationTime")
	private String bidExpirationTime;
	
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
	
	@XStreamAlias("bidComments")
	private String bidComments;

	public Integer getBidResourceId() {
		return bidResourceId;
	}

	public void setBidResourceId(Integer bidResourceId) {
		this.bidResourceId = bidResourceId;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public Double getLaborPrice() {
		return laborPrice;
	}

	public void setLaborPrice(Double laborPrice) {
		this.laborPrice = laborPrice;
	}

	public Double getLaborHourlyRate() {
		return laborHourlyRate;
	}

	public void setLaborHourlyRate(Double laborHourlyRate) {
		this.laborHourlyRate = laborHourlyRate;
	}

	public Integer getLaborHours() {
		return laborHours;
	}

	public void setLaborHours(Integer laborHours) {
		this.laborHours = laborHours;
	}

	public Double getPartsPrice() {
		return partsPrice;
	}

	public void setPartsPrice(Double partsPrice) {
		this.partsPrice = partsPrice;
	}

	public String getBidExpirationDate() {
		return bidExpirationDate;
	}

	public void setBidExpirationDate(String bidExpirationDate) {
		this.bidExpirationDate = bidExpirationDate;
	}

	public String getBidExpirationTime() {
		return bidExpirationTime;
	}

	public void setBidExpirationTime(String bidExpirationTime) {
		this.bidExpirationTime = bidExpirationTime;
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

	public String getBidComments() {
		return bidComments;
	}

	public void setBidComments(String bidComments) {
		this.bidComments = bidComments;
	}
}
