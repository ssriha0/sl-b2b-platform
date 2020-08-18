package com.newco.marketplace.web.dto;

import java.util.Date;
import java.util.List;

import org.apache.struts.util.LabelValueBean;



public class ServiceOrderBidModel extends SerializedBaseDTO
{

	private static final long serialVersionUID = -3011464192349367652L;
	
	private String bidder;
	private Date dateOfBid;
	// bulletin board orders
	private Integer bidderResourceId;
	
	private String bidPriceType;
	
	private Boolean laborRateIsHourly = false;
	private Double totalLabor;
	private Double laborRate;
	private Double totalHours;
	private Double partsMaterials;

	private String bidExpirationDatepicker;
	private Integer bidExpirationHour;
	private Boolean bidExpired;
	
	// New Service Date & Time - Range
	private Boolean bidExpirationFieldsShow;
	private String newDateByRangeTo;
	private String newDateByRangeFrom;
	
	// New Service Date & Time - Specified Date
	private Boolean requestNewServiceDate;
	private String newDateBySpecificDate;
	private Integer hourFrom;
	private Integer hourTo;
	
		
	private String comment;
	private Boolean termsAndConditions;
	
	
	// For initial display
	private Date serviceDate1;
	private Date serviceDate2;
	private String serviceWindow;
	
	
	
	// Calculated from totalLabor + partsMaterials
	private Double total;
	
	// Calculated hourly rate totalLabor / hours
	private Double calculatedHourlyRate;
	
	
	// Dropdown menu items
	private List<LabelValueBean> hourDropdownList;
	
	//Sealed Bid Orders
	private boolean sealedBidInd;
	

	public boolean isSealedBidInd() {
		return sealedBidInd;
	}

	public void setSealedBidInd(boolean sealedBidInd) {
		this.sealedBidInd = sealedBidInd;
	}

	
	public Double getTotalLabor()
	{
		return totalLabor;
	}
	public void setTotalLabor(Double totalLabor)
	{
		this.totalLabor = totalLabor;
	}
	public Double getTotalHours()
	{
		return totalHours;
	}
	public void setTotalHours(Double totalHours)
	{
		this.totalHours = totalHours;
	}
	public Double getPartsMaterials()
	{
		return partsMaterials;
	}
	public void setPartsMaterials(Double partsMaterials)
	{
		this.partsMaterials = partsMaterials;
	}
	public Boolean getTermsAndConditions()
	{
		return termsAndConditions;
	}
	public void setTermsAndConditions(Boolean termsAndConditions)
	{
		this.termsAndConditions = termsAndConditions;
	}
	public String getComment()
	{
		return comment;
	}
	public void setComment(String comment)
	{
		this.comment = comment;
	}
	public Double getTotal()
	{
		if (this.total != null && this.total > 0) {
			return this.total;
		}
		
		total = 0.0d;
		
		if (this.laborRateIsHourly) {
			if (this.laborRate != null && this.totalHours != null) {
				this.total = this.laborRate * this.totalHours;
			}
		}
		else {
			this.total = this.totalLabor;
		}
				
		if (this.partsMaterials != null) {
			this.total += this.partsMaterials;
		}
		
		return this.total;		
	}
	
	public void setTotal(Double total)
	{
		this.total = total;
	}
	public List<LabelValueBean> getHourDropdownList()
	{
		return hourDropdownList;
	}
	public void setHourDropdownList(List<LabelValueBean> hourDropdownList)
	{
		this.hourDropdownList = hourDropdownList;
	}
	public String getNewDateByRangeTo()
	{
		return newDateByRangeTo;
	}
	public void setNewDateByRangeTo(String newDateByRangeTo)
	{
		this.newDateByRangeTo = newDateByRangeTo;
	}
	public String getNewDateByRangeFrom()
	{
		return newDateByRangeFrom;
	}
	public void setNewDateByRangeFrom(String newDateByRangeFrom)
	{
		this.newDateByRangeFrom = newDateByRangeFrom;
	}
	public String getBidExpirationDatepicker()
	{
		return bidExpirationDatepicker;
	}
	public void setBidExpirationDatepicker(String bidExpirationDatepicker)
	{
		this.bidExpirationDatepicker = bidExpirationDatepicker;
	}
	public Integer getBidExpirationHour()
	{
		return bidExpirationHour;
	}
	public void setBidExpirationHour(Integer bidExpirationHour)
	{
		this.bidExpirationHour = bidExpirationHour;
	}
	public String getNewDateBySpecificDate()
	{
		return newDateBySpecificDate;
	}
	public void setNewDateBySpecificDate(String newDateBySpecificDate)
	{
		this.newDateBySpecificDate = newDateBySpecificDate;
	}
	public Integer getHourFrom()
	{
		return hourFrom;
	}
	public void setHourFrom(Integer hourFrom)
	{
		this.hourFrom = hourFrom;
	}
	public Integer getHourTo()
	{
		return hourTo;
	}
	public void setHourTo(Integer hourTo)
	{
		this.hourTo = hourTo;
	}
	public Date getDateOfBid()
	{
		return dateOfBid;
	}
	public void setDateOfBid(Date dateOfBid)
	{
		this.dateOfBid = dateOfBid;
	}
	public Double getCalculatedHourlyRate()
	{
		if(totalHours != null && totalHours > 0.0)
		{
			calculatedHourlyRate = totalLabor / totalHours;
		}
		else
		{
			calculatedHourlyRate = -1.0d;
		}
		
		return calculatedHourlyRate;
	}
	public void setCalculatedHourlyRate(Double calculatedHourlyRate)
	{
		this.calculatedHourlyRate = calculatedHourlyRate;
	}
	public String getBidder()
	{
		return bidder;
	}
	public void setBidder(String bidder)
	{
		this.bidder = bidder;
	}
	public Integer getBidderResourceId() {
		return bidderResourceId;
	}
	public void setBidderResourceId(Integer bidderResourceId) {
		this.bidderResourceId = bidderResourceId;
	}
	public Date getServiceDate2()
	{
		return serviceDate2;
	}
	public void setServiceDate2(Date serviceDate2)
	{
		this.serviceDate2 = serviceDate2;
	}
	public Date getServiceDate1()
	{
		return serviceDate1;
	}
	public void setServiceDate1(Date serviceDate1)
	{
		this.serviceDate1 = serviceDate1;
	}
	public String getServiceWindow()
	{
		return serviceWindow;
	}
	public void setServiceWindow(String serviceWindow)
	{
		this.serviceWindow = serviceWindow;
	}
	public Boolean getBidExpirationFieldsShow() {
		return bidExpirationFieldsShow;
	}
	public void setBidExpirationFieldsShow(Boolean bidExpirationFieldsShow) {
		this.bidExpirationFieldsShow = bidExpirationFieldsShow;
	}
	public Boolean getRequestNewServiceDate() {
		return requestNewServiceDate;
	}
	public void setRequestNewServiceDate(Boolean requestNewServiceDate) {
		this.requestNewServiceDate = requestNewServiceDate;
	}
	public void setBidPriceType(String bidPriceType) {
		this.bidPriceType = bidPriceType;
	}
	public String getBidPriceType() {
		return bidPriceType;
	}
	public void setLaborRate(Double laborRate) {
		this.laborRate = laborRate;
	}
	public Double getLaborRate() {
		return laborRate;
	}
	public void setLaborRateIsHourly(Boolean laborRateIsHourly) {
		this.laborRateIsHourly = laborRateIsHourly;
	}
	public Boolean getLaborRateIsHourly() {
		return laborRateIsHourly;
	}
	public void setBidExpired(Boolean bidExpired) {
		this.bidExpired = bidExpired;
	}
	public Boolean getBidExpired() {
		return bidExpired;
	}	

}
