package com.newco.marketplace.web.dto;

import java.util.ArrayList;

public class LeadDashboardVitalStatsDTO extends SerializedBaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7407816252683064820L;
	static private ArrayList<String> buyerLabels=null;
	static private ArrayList<String> providerLabels=null;
	private ArrayList<String> labels;
	private ArrayList<Integer> values= new ArrayList<Integer>();
	private ArrayList<String> textColors = new ArrayList<String>();
	private ArrayList<String> idList = new ArrayList<String>();
	private ArrayList<String> tabStatus = new ArrayList<String>();
	private Double totalValueReceived;
	
	private Integer totalLeads;
	private Integer conversionRate;
	private Integer averageResponseTime;
	private String goal;
	
	
	public ArrayList<String> getTextColors() {
		return textColors;
	}

	public void setTextColors(ArrayList<String> textColors) {
		this.textColors = textColors;
	}

	public LeadDashboardVitalStatsDTO()
	{
		if(buyerLabels == null)
		{
			buyerLabels = new ArrayList<String>();
			buyerLabels.add("Unmatched");
			buyerLabels.add("Matched");			
			buyerLabels.add("Completed");
			buyerLabels.add("Cancelled");
			
		}
		if(providerLabels == null)
		{
			providerLabels = new ArrayList<String>();
			providerLabels.add("New");
			providerLabels.add("Completed");			
			providerLabels.add("Working");
			providerLabels.add("Cancelled");
			providerLabels.add("Scheduled");
			providerLabels.add("Stale");

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

	public Integer getTotalLeads() {
		return totalLeads;
	}

	public void setTotalLeads(Integer totalLeads) {
		this.totalLeads = totalLeads;
	}

	public Integer getConversionRate() {
		return conversionRate;
	}

	public void setConversionRate(Integer conversionRate) {
		this.conversionRate = conversionRate;
	}

	public Integer getAverageResponseTime() {
		return averageResponseTime;
	}

	public void setAverageResponseTime(Integer averageResponseTime) {
		this.averageResponseTime = averageResponseTime;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}
	
	
	
	
}
