package com.newco.marketplace.api.mobile.beans.getRecievedOrders;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.ReceivedOrderEstimateDetails;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/04/10
* for fetching response 0f eligible providers forSO for mobile
*/

@XStreamAlias("recievedOrder") 
@XmlAccessorType(XmlAccessType.FIELD)
public class RecievedOrder {

	@XStreamAlias("soId")
	private String soId;
	
	@XStreamAlias("soStatus")
	private String soStatus;
	
	@XStreamAlias("orderType")
	private String orderType;
	
	@XStreamAlias("buyerId")
	private Integer buyerId;
	
	@XStreamAlias("buyerName")
	private String buyerName;
	
	@XStreamAlias("recievedDate")
	private String recievedDate;
	
	@XStreamAlias("soTitle")
	private String soTitle;
	
	@XStreamAlias("city")
	private String city;
	
	@XStreamAlias("state")
	private String state;
	
	@XStreamAlias("zip")
	private String zip;
	
	@XStreamAlias("routedProvider")
	private String routedProvider;
	
	@XStreamAlias("serviceStartDate")
	private String serviceStartDate;
	
	@XStreamAlias("serviceEndDate")
	private String serviceEndDate;
	
	@XStreamAlias("serviceWindowStartTime")
	private String serviceWindowStartTime;
	
	@XStreamAlias("serviceWindowEndTime")
	private String serviceWindowEndTime;
	
	@XStreamAlias("timeZone")
	private String timeZone;
	
	@XStreamAlias("soPrice")
	private Double soPrice;
	
	@XStreamAlias("productAvailability")
	private String productAvailability;
	
	@XStreamAlias("pickUpLocationDetails")
	private PickUpLocationDetails pickUpLocationDetails;
	
	//SL-20838
	@XStreamAlias("followupFlag")
	private Boolean followUpFlag;

	@XStreamAlias("bidDetails")
	private BidDetails bidDetails;
	
	@XStreamAlias("relayOrderType")
	private String relayOrderType;
	
	@XStreamAlias("estimationFlag")
	private Boolean estimationFlag;
	
	@XStreamAlias("estimateDetails")
	private ReceivedOrderEstimateDetails estimateDetails;
	
	
	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getSoStatus() {
		return soStatus;
	}

	public void setSoStatus(String soStatus) {
		this.soStatus = soStatus;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getRecievedDate() {
		return recievedDate;
	}

	public void setRecievedDate(String recievedDate) {
		this.recievedDate = recievedDate;
	}

	public String getSoTitle() {
		return soTitle;
	}

	public void setSoTitle(String soTitle) {
		this.soTitle = soTitle;
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

	public String getRoutedProvider() {
		return routedProvider;
	}

	public void setRoutedProvider(String routedProvider) {
		this.routedProvider = routedProvider;
	}

	public String getServiceStartDate() {
		return serviceStartDate;
	}

	public void setServiceStartDate(String serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}

	
	public String getServiceEndDate() {
		return serviceEndDate;
	}

	public void setServiceEndDate(String serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
	}

	public String getServiceWindowStartTime() {
		return serviceWindowStartTime;
	}

	public void setServiceWindowStartTime(String serviceWindowStartTime) {
		this.serviceWindowStartTime = serviceWindowStartTime;
	}

	public String getServiceWindowEndTime() {
		return serviceWindowEndTime;
	}

	public void setServiceWindowEndTime(String serviceWindowEndTime) {
		this.serviceWindowEndTime = serviceWindowEndTime;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public Double getSoPrice() {
		return soPrice;
	}

	public void setSoPrice(Double soPrice) {
		this.soPrice = soPrice;
	}

	public String getProductAvailability() {
		return productAvailability;
	}

	public void setProductAvailability(String productAvailability) {
		this.productAvailability = productAvailability;
	}

	public PickUpLocationDetails getPickUpLocationDetails() {
		return pickUpLocationDetails;
	}

	public void setPickUpLocationDetails(PickUpLocationDetails pickUpLocationDetails) {
		this.pickUpLocationDetails = pickUpLocationDetails;
	}

	public Boolean getFollowUpFlag() {
		return followUpFlag;
	}

	public void setFollowUpFlag(Boolean followUpFlag) {
		this.followUpFlag = followUpFlag;
	}
	
	
	public BidDetails getBidDetails() {
		return bidDetails;
	}

	public void setBidDetails(BidDetails bidDetails) {
		this.bidDetails = bidDetails;
	}

	public String getRelayOrderType() {
		return relayOrderType;
	}

	public void setRelayOrderType(String relayOrderType) {
		this.relayOrderType = relayOrderType;
	}

	public Boolean getEstimationFlag() {
		return estimationFlag;
	}

	public void setEstimationFlag(Boolean estimationFlag) {
		this.estimationFlag = estimationFlag;
	}

	public ReceivedOrderEstimateDetails getEstimateDetails() {
		return estimateDetails;
	}

	public void setEstimateDetails(ReceivedOrderEstimateDetails estimateDetails) {
		this.estimateDetails = estimateDetails;
	}
	
	
	
	
}
