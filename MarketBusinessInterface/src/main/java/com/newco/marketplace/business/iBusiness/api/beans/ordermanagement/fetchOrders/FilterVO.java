package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders;

import java.util.List;

public class FilterVO {
	public List<MarketFilterVO> marketList;
	public List<ProviderFilterVO> providerList;
	public List<StatusFilterVO> statusList;
	public List<SubStatusFilterVO> subStatusList;
	public List<ScheduleStatusFilterVO> scheduleList;
	public List<ProviderFilterVO> routedProviderList;
	//Job Done Substatus
	public List<JobDoneSubStatusFilterVO> jobDoneSubStatusList;
	//Current Orders SubStatus
	public List<CurrentOrdersSubStatusFilterVO> currentOrdersSubStatusList;
	
	//R12.0 Sprint3 substatus filter in cancellations tab
	public List<CancellationsSubStatusFilterVO> cancellationsSubStatusList;
	
	//R12.0 Sprint4 substatus filter in Revisit Needed tab
	public List<RevisitSubStatusFilterVO> revisitSubStatusList;

	
	
	public List<RevisitSubStatusFilterVO> getRevisitSubStatusList() {
		return revisitSubStatusList;
	}
	public void setRevisitSubStatusList(
			List<RevisitSubStatusFilterVO> revisitSubStatusList) {
		this.revisitSubStatusList = revisitSubStatusList;
	}
	public List<CancellationsSubStatusFilterVO> getCancellationsSubStatusList() {
		return cancellationsSubStatusList;
	}
	public void setCancellationsSubStatusList(
			List<CancellationsSubStatusFilterVO> cancellationsSubStatusList) {
		this.cancellationsSubStatusList = cancellationsSubStatusList;
	}
	public List<MarketFilterVO> getMarketList() {
		return marketList;
	}
	public void setMarketList(List<MarketFilterVO> marketList) {
		this.marketList = marketList;
	}
	public List<ProviderFilterVO> getProviderList() {
		return providerList;
	}
	public void setProviderList(List<ProviderFilterVO> providerList) {
		this.providerList = providerList;
	}
	public List<JobDoneSubStatusFilterVO> getJobDoneSubStatusList() {
		return jobDoneSubStatusList;
	}
	public void setJobDoneSubStatusList(
			List<JobDoneSubStatusFilterVO> jobDoneSubStatusList) {
		this.jobDoneSubStatusList = jobDoneSubStatusList;
	}
	public List<StatusFilterVO> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<StatusFilterVO> statusList) {
		this.statusList = statusList;
	}
	public List<SubStatusFilterVO> getSubStatusList() {
		return subStatusList;
	}
	public void setSubStatusList(List<SubStatusFilterVO> subStatusList) {
		this.subStatusList = subStatusList;
	}
	public List<ScheduleStatusFilterVO> getScheduleList() {
		return scheduleList;
	}
	public void setScheduleList(List<ScheduleStatusFilterVO> scheduleList) {
		this.scheduleList = scheduleList;
	}
	public List<ProviderFilterVO> getRoutedProviderList() {
		return this.routedProviderList;
	}
	public void setRoutedProviderList(List<ProviderFilterVO> routedProviderList) {
		this.routedProviderList = routedProviderList;
	}
	public List<CurrentOrdersSubStatusFilterVO> getCurrentOrdersSubStatusList() {
		return currentOrdersSubStatusList;
	}
	public void setCurrentOrdersSubStatusList(
			List<CurrentOrdersSubStatusFilterVO> currentOrdersSubStatusList) {
		this.currentOrdersSubStatusList = currentOrdersSubStatusList;
	}
	

}
