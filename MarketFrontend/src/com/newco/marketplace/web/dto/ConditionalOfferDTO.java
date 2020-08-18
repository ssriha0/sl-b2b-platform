package com.newco.marketplace.web.dto;

import java.util.List;


public class ConditionalOfferDTO extends SerializedBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7166252972724633090L;
	private String soId;
	private Integer resourceId;
	private Integer vendorOrBuyerID;

	// Checkboxes
	private Boolean increaseMaxPrice=false;
	private Boolean rescheduleServiceDate=false;
	
	private String conditionalChangeDate1;
	private String conditionalChangeDate2;
	private String conditionalStartTime;
	private String conditionalEndTime; 
	private String conditionalExpirationDate;
	private String conditionalExpirationTime;
	private Double conditionalSpendLimit;
	private List<Integer> selectedCounterOfferReasonsList;
	private List<Integer> counterOfferedResources;
		
	// Needed because of the 2 different ways we can designate
	// reschedule dates.  Because of the way the back-end accepts
	// conditional offers, these 2 data members need to be put
	// into conditionalChangeDate1, and conditionalStartTime
	private String specificDate;
	private String specificTime;
	
	public List<Integer> getSelectedCounterOfferReasonsList() {
		return selectedCounterOfferReasonsList;
	}
	public void setSelectedCounterOfferReasonsList(
			List<Integer> selectedCounterOfferReasonsList) {
		this.selectedCounterOfferReasonsList = selectedCounterOfferReasonsList;
	}
	public String getConditionalChangeDate1() {
		return conditionalChangeDate1;
	}
	public void setConditionalChangeDate1(String conditionalChangeDate1) {
		this.conditionalChangeDate1 = conditionalChangeDate1;
	}
	public String getConditionalChangeDate2() {
		return conditionalChangeDate2;
	}
	public void setConditionalChangeDate2(String conditionalChangeDate2) {
		this.conditionalChangeDate2 = conditionalChangeDate2;
	}
	public String getConditionalEndTime() {
		return conditionalEndTime;
	}
	public void setConditionalEndTime(String conditionalEndTime) {
		this.conditionalEndTime = conditionalEndTime;
	}
	public String getConditionalExpirationDate() {
		return conditionalExpirationDate;
	}
	public void setConditionalExpirationDate(String conditionalExpirationDate) {
		this.conditionalExpirationDate = conditionalExpirationDate;
	}
	public Double getConditionalSpendLimit() {
		return conditionalSpendLimit;
	}
	public void setConditionalSpendLimit(Double conditionalSpendLimit) {
		this.conditionalSpendLimit = conditionalSpendLimit;
	}
	public String getConditionalStartTime() {
		return conditionalStartTime;
	}
	public void setConditionalStartTime(String conditionalStartTime) {
		this.conditionalStartTime = conditionalStartTime;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getConditionalExpirationTime() {
		return conditionalExpirationTime;
	}
	public void setConditionalExpirationTime(String conditionalExpirationTime) {
		this.conditionalExpirationTime = conditionalExpirationTime;
	}
	public Integer getVendorOrBuyerID() {
		return vendorOrBuyerID;
	}
	public void setVendorOrBuyerID(Integer vendorOrBuyerID) {
		this.vendorOrBuyerID = vendorOrBuyerID;
	}
	public Boolean getIncreaseMaxPrice()
	{
		return increaseMaxPrice;
	}
	public void setIncreaseMaxPrice(Boolean increaseMaxPrice)
	{
		this.increaseMaxPrice = increaseMaxPrice;
	}
	public Boolean getRescheduleServiceDate()
	{
		return rescheduleServiceDate;
	}
	public void setRescheduleServiceDate(Boolean rescheduleServiceDate)
	{
		this.rescheduleServiceDate = rescheduleServiceDate;
	}
	public String getSpecificDate()
	{
		return specificDate;
	}
	public void setSpecificDate(String specificDate)
	{
		this.specificDate = specificDate;
	}
	public String getSpecificTime()
	{
		return specificTime;
	}
	public void setSpecificTime(String specificTime)
	{
		this.specificTime = specificTime;
	}
	public List<Integer> getCounterOfferedResources() {
		return counterOfferedResources;
	}
	public void setCounterOfferedResources(List<Integer> counterOfferedResources) {
		this.counterOfferedResources = counterOfferedResources;
	}
	
}
