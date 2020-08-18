package com.newco.marketplace.web.dto;

import java.util.ArrayList;

public class ServiceOrderDashboardVitalStatsDTO extends SerializedBaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7407816252683064820L;
	private ArrayList<String> buyerLabels=null;
	private ArrayList<String> providerLabels=null;
	private ArrayList<String> labels;
	private ArrayList<Integer> values= new ArrayList<Integer>();
	private ArrayList<String> textColors = new ArrayList<String>();
	private ArrayList<String> idList = new ArrayList<String>();
	private ArrayList<String> tabStatus = new ArrayList<String>();
	private Double totalValueReceived;
	private String isNonFundedBuyer= "false";
	public ArrayList<String> getTextColors() {
		return textColors;
	}

	public void setTextColors(ArrayList<String> textColors) {
		this.textColors = textColors;
	}

	public ServiceOrderDashboardVitalStatsDTO()
	{
		if(buyerLabels == null)
		{
			buyerLabels = new ArrayList<String>();
			buyerLabels.add("Draft");
			buyerLabels.add("Accepted");
			buyerLabels.add("Posted");
			buyerLabels.add("Problem");
			buyerLabels.add("Pending Cancel");
			//R12_1
			//SL-20362
			buyerLabels.add("Pending Reschedule");
		
		}
		if(providerLabels == null)
		{
			providerLabels = new ArrayList<String>();
			providerLabels.add("Today");
			providerLabels.add("Accepted");			
			providerLabels.add("Received");
			providerLabels.add("Bid Requests");
			providerLabels.add("Problem");
			providerLabels.add("Bulletin Board");			
			providerLabels.add("Pending Cancel");
			//R12_1
			//SL-20362
			providerLabels.add("Pending Reschedule");
			//SL-21465
			providerLabels.add("Estimation Requests");
		}
		
	}
	public ServiceOrderDashboardVitalStatsDTO(String nonFundedBuyer)
	{
		if(buyerLabels == null)
		{
			buyerLabels = new ArrayList<String>();
			buyerLabels.add("Draft");
			buyerLabels.add("Accepted");
			buyerLabels.add("Posted");
			buyerLabels.add("Problem");
			//R12_1
			//SL-20362
			buyerLabels.add("Pending Reschedule");
		}
		if(providerLabels == null)
		{
			providerLabels = new ArrayList<String>();
			providerLabels.add("Today");
			providerLabels.add("Accepted");			
			providerLabels.add("Received");
			providerLabels.add("Bid Requests");
			providerLabels.add("Problem");
			providerLabels.add("Bulletin Board");
			providerLabels.add("Pending Cancel");
			//R12_1
			//SL-20362
			providerLabels.add("Pending Reschedule");
		}
		
	}
	public ArrayList<String> getLabels() {
		return labels;
	}

	public void setLabels(ArrayList<String> labels) {
		this.labels = labels;
	}

	public ArrayList<Integer> getValues() {
		return values;
	}

	public void setValues(ArrayList<Integer> values) {
		this.values = values;
	}
	
	public void setBuyerLabels()
	{
		labels = buyerLabels;
	}
	
	public void setProviderLabels()
	{
		labels = providerLabels;
	}

	public ArrayList<String> getIdList() {
		return idList;
	}

	public void setIdList(ArrayList<String> idList) {
		this.idList = idList;
	}

	public Double getTotalValueReceived() {
		return totalValueReceived;
	}

	public void setTotalValueReceived(Double totalValueReceived) {
		this.totalValueReceived = totalValueReceived;
	}

	public ArrayList<String> getTabStatus() {
		return tabStatus;
	}

	public void setTabStatus(ArrayList<String> tabStatus) {
		this.tabStatus = tabStatus;
	}

	public String getIsNonFundedBuyer() {
		return isNonFundedBuyer;
	}

	public void setIsNonFundedBuyer(String isNonFundedBuyer) {
		this.isNonFundedBuyer = isNonFundedBuyer;
	}
	
}
