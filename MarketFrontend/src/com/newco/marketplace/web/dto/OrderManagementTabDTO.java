package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.vo.ordermanagement.so.OMServiceOrder;
import com.newco.marketplace.web.dto.ordermanagement.PrecallScheduleUpdateDTO;




public class OrderManagementTabDTO extends SerializedBaseDTO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2650172279841075524L;
	private String appointmentEndDate;
	private String appointmentStartDate;
	private String appointmentType;
	private Integer endIndex;
	private String error;
	private List<String> filterList= new ArrayList<String>();
	private String filterScheduleStatus;
	private String filterStatus;
	private String filterSubStatus;
	private String id;
	private Boolean loadFiltersInd;
	private Boolean resetInd;
	private Integer marketResetInd;
	private Integer providerResetInd;
	private Integer routedProviderResetInd;
	private Integer scheduleResetInd;
	private Integer statusResetInd;
	private Integer subStatusResetInd;
	//Jobdone substatus
	private Integer jobDoneSubStatusResetInd;
	private String filterJobDoneSubStatus;
	
	//Current Order SubStatus
	private Integer currentOrdersSubStatusResetInd;
	private String filterCurrentOrdersSubStatus;
	
	//R12.0 Sprint3 Cancellations substatus
	private Integer cancellationsSubStatusResetInd;
	//private String filterCancellationsSubStatus;
	
	//R12.0 Sprint3 Revisit substatus
	private Integer revisitSubStatusResetInd;
	
	private List<SOWError> omErrors = new ArrayList<SOWError>();

	public List<SOWError> getOmErrors() {
		return omErrors;
	}
	public void setOmErrors(List<SOWError> omErrors) {
		this.omErrors = omErrors;
	}
	public Integer getStatusResetInd() {
		return statusResetInd;
	}
	public void setStatusResetInd(Integer statusResetInd) {
		this.statusResetInd = statusResetInd;
	}
	public Integer getSubStatusResetInd() {
		return subStatusResetInd;
	}
	public void setSubStatusResetInd(Integer subStatusResetInd) {
		this.subStatusResetInd = subStatusResetInd;
	}
	public Integer getScheduleResetInd() {
		return scheduleResetInd;
	}
	public void setScheduleResetInd(Integer scheduleResetInd) {
		this.scheduleResetInd = scheduleResetInd;
	}
	public Integer getMarketResetInd() {
		return marketResetInd;
	}
	public void setMarketResetInd(Integer marketResetInd) {
		this.marketResetInd = marketResetInd;
	}
	public Integer getProviderResetInd() {
		return providerResetInd;
	}
	public void setProviderResetInd(Integer providerResetInd) {
		this.providerResetInd = providerResetInd;
	}
	public Integer getRoutedProviderResetInd() {
		return routedProviderResetInd;
	}
	public void setRoutedProviderResetInd(Integer routedProviderResetInd) {
		this.routedProviderResetInd = routedProviderResetInd;
	}
	public Boolean getResetInd() {
		return resetInd;
	}
	public void setResetInd(Boolean resetInd) {
		this.resetInd = resetInd;
	}
	private List<String> marketlist;
	/**
	 * DTO for precall save
	 * */
	private PrecallScheduleUpdateDTO precallScheduleUpdateDTO;
	private List<String> providerList;
	/**
	 * DTO for Request a Reschedule
	 * */
	private RescheduleDTO rescheduleDTO;
	private String result;
	private List<String> scheduleStatusList;
	//for filters
	private List<String> selectedMarkets;
	private List<Integer> selectedProviders; 
	private List<Integer> selectedRoutedProviders; 
	private List<String> selectedScheduleStatus; 
	private List<String> selectedStatus; 
	private List<String> selectedSubStatus; 
	private Boolean includeUnassigned;
	private List<OMServiceOrder> soList= new ArrayList<OMServiceOrder>(); 
	private String sortBy;
	private Integer startIndex;
	private Integer status;
	//Job Done status
	private List<String> selectedJobDoneSubStatus;
	//For Current Orders SubStatus
	private List<String> selectedCurrentOrdersSubStatus;
	//R12.0 Sprint3 Cancellations Substatus
	private List<String> selectedCancellationsSubStatus;
	
	//R12.0 Sprint4 Revisit Substatus
	private List<String> selectedRevisitSubStatus;
	
	
	private String statusDisabled="";
	private List<String> statusList;
	private List<String> subStatusList;
	private Integer tabCount = 0;
	private String tabSelected;
	
	private String tabTitle;
	private Boolean viewOrderPricing;
	private String displayTab;
	private String criteria;
	private String sortOrder;
	private Integer currentOrderCount;
	
	public String getAppointmentEndDate() {
		return appointmentEndDate;
	}
	public String getAppointmentStartDate() {
		return appointmentStartDate;
	}
	public String getAppointmentType() {
		return appointmentType;
	}
	public Integer getEndIndex() {
		return endIndex;
	}
	public String getError() {
		return error;
	}
	public List<String> getFilterList() {
		return filterList;
	}
	public String getFilterScheduleStatus() {
		return filterScheduleStatus;
	}
	public String getFilterStatus() {
		return filterStatus;
	}
	public String getFilterSubStatus() {
		return filterSubStatus;
	}
	public String getId() {
		return id;
	}
	public Boolean getLoadFiltersInd() {
		return loadFiltersInd;
	}
	public List<String> getMarketlist() {
		return marketlist;
	}
	public PrecallScheduleUpdateDTO getPrecallScheduleUpdateDTO() {
		return precallScheduleUpdateDTO;
	}
	public List<String> getProviderList() {
		return providerList;
	}
	public RescheduleDTO getRescheduleDTO() {
		return rescheduleDTO;
	}
	public String getResult() {
		return result;
	}
	public List<String> getScheduleStatusList() {
		return scheduleStatusList;
	}
	public List<String> getSelectedMarkets() {
		return selectedMarkets;
	}
	public List<Integer> getSelectedProviders() {
		return selectedProviders;
	}
	public List<String> getSelectedScheduleStatus() {
		return selectedScheduleStatus;
	}
	public List<String> getSelectedStatus() {
		return selectedStatus;
	}

	public List<String> getSelectedSubStatus() {
		return selectedSubStatus;
	}


	public List<OMServiceOrder> getSoList() {
		return soList;
	}
	public String getSortBy() {
		return sortBy;
	}
	public Integer getStartIndex() {
		return startIndex;
	}

	public Integer getStatus() {
		return status;
	}

	public String getStatusDisabled()
	{
		return statusDisabled;
	}

	public List<String> getStatusList() {
		return statusList;
	}
	public List<String> getSubStatusList() {
		return subStatusList;
	}
	public Integer getTabCount() {
		return tabCount;
	}
	public String getTabSelected() {
		return tabSelected;
	}
	public String getTabTitle() {
		return tabTitle;
	}
	public Boolean getViewOrderPricing() {
		return viewOrderPricing;
	}
	public void setAppointmentEndDate(String appointmentEndDate) {
		this.appointmentEndDate = appointmentEndDate;
	}
	public void setAppointmentStartDate(String appointmentStartDate) {
		this.appointmentStartDate = appointmentStartDate;
	}
	public void setAppointmentType(String appointmentType) {
		this.appointmentType = appointmentType;
	}
	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}
	public void setError(String error) {
		this.error = error;
	}
	public void setFilterList(List<String> filterList) {
		this.filterList = filterList;
	}
	public void setFilterScheduleStatus(String filterScheduleStatus) {
		this.filterScheduleStatus = filterScheduleStatus;
	}
	public void setFilterStatus(String filterStatus) {
		this.filterStatus = filterStatus;
	}
	public void setFilterSubStatus(String filterSubStatus) {
		this.filterSubStatus = filterSubStatus;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public void setLoadFiltersInd(Boolean loadFiltersInd) {
		this.loadFiltersInd = loadFiltersInd;
	}
	public void setMarketlist(List<String> marketlist) {
		this.marketlist = marketlist;
	}
	public void setPrecallScheduleUpdateDTO(
			PrecallScheduleUpdateDTO precallScheduleUpdateDTO) {
		this.precallScheduleUpdateDTO = precallScheduleUpdateDTO;
	}
	public void setProviderList(List<String> providerList) {
		this.providerList = providerList;
	}
	public void setRescheduleDTO(RescheduleDTO rescheduleDTO) {
		this.rescheduleDTO = rescheduleDTO;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public void setScheduleStatusList(List<String> scheduleStatusList) {
		this.scheduleStatusList = scheduleStatusList;
	}
	public void setSelectedMarkets(List<String> selectedMarkets) {
		this.selectedMarkets = selectedMarkets;
	}
	public void setSelectedProviders(List<Integer> selectedProviders) {
		this.selectedProviders = selectedProviders;
	}
	public List<Integer> getSelectedRoutedProviders() {
		return selectedRoutedProviders;
	}
	public void setSelectedRoutedProviders(List<Integer> selectedRoutedProviders) {
		this.selectedRoutedProviders = selectedRoutedProviders;
	}
	public void setSelectedScheduleStatus(List<String> selectedScheduleStatus) {
		this.selectedScheduleStatus = selectedScheduleStatus;
	}
	public void setSelectedStatus(List<String> selectedStatus) {
		this.selectedStatus = selectedStatus;
	}
	public Boolean getIncludeUnassigned() {
		return includeUnassigned;
	}
	public void setIncludeUnassigned(Boolean includeUnassigned) {
		this.includeUnassigned = includeUnassigned;
	}
	public void setSelectedSubStatus(List<String> selectedSubStatus) {
		this.selectedSubStatus = selectedSubStatus;
	}
	public void setSoList(List<OMServiceOrder> soList) {
		this.soList = soList;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setStatusDisabled(boolean disabled)
	{
		if(disabled){
			this.statusDisabled = "disabled";
		}else{
			this.statusDisabled = "";
		}
	}
	public void setStatusDisabled(String statusDisabled) {
		this.statusDisabled = statusDisabled;
	}
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}
	public void setSubStatusList(List<String> subStatusList) {
		this.subStatusList = subStatusList;
	}
	public void setTabCount(Integer tabCount) {
		this.tabCount += tabCount;
	}

	public void setTabSelected(String tabSelected) {
		this.tabSelected = tabSelected;
	}
	public void setTabTitle(String tabTitle) {
		this.tabTitle = tabTitle;
	}
	public void setViewOrderPricing(Boolean viewOrderPricing) {
		this.viewOrderPricing = viewOrderPricing;
	}
	public String getDisplayTab() {
		return this.displayTab;
	}
	public void setDisplayTab(String displayTab) {
		this.displayTab = displayTab;
	}
	public String getCriteria() {
		return this.criteria;
	}
	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
	public String getSortOrder() {
		return this.sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Integer getCurrentOrderCount() {
		return currentOrderCount;
	}
	public void setCurrentOrderCount(Integer currentOrderCount) {
		this.currentOrderCount = currentOrderCount;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return tabTitle + "\t" + tabCount;
	}
	//protected void
	//New DTO END
	public List<String> getSelectedJobDoneSubStatus() {
		return selectedJobDoneSubStatus;
	}
	public void setSelectedJobDoneSubStatus(List<String> selectedJobDoneSubStatus) {
		this.selectedJobDoneSubStatus = selectedJobDoneSubStatus;
	}
	public Integer getJobDoneSubStatusResetInd() {
		return jobDoneSubStatusResetInd;
	}
	public void setJobDoneSubStatusResetInd(Integer jobDoneSubStatusResetInd) {
		this.jobDoneSubStatusResetInd = jobDoneSubStatusResetInd;
	}
	public String getFilterJobDoneSubStatus() {
		return filterJobDoneSubStatus;
	}
	public void setFilterJobDoneSubStatus(String filterJobDoneSubStatus) {
		this.filterJobDoneSubStatus = filterJobDoneSubStatus;
	}
	public Integer getCurrentOrdersSubStatusResetInd() {
		return currentOrdersSubStatusResetInd;
	}
	public void setCurrentOrdersSubStatusResetInd(
			Integer currentOrdersSubStatusResetInd) {
		this.currentOrdersSubStatusResetInd = currentOrdersSubStatusResetInd;
	}
	public String getFilterCurrentOrdersSubStatus() {
		return filterCurrentOrdersSubStatus;
	}
	public void setFilterCurrentOrdersSubStatus(String filterCurrentOrdersSubStatus) {
		this.filterCurrentOrdersSubStatus = filterCurrentOrdersSubStatus;
	}
	public List<String> getSelectedCurrentOrdersSubStatus() {
		return selectedCurrentOrdersSubStatus;
	}
	public void setSelectedCurrentOrdersSubStatus(
			List<String> selectedCurrentOrdersSubStatus) {
		this.selectedCurrentOrdersSubStatus = selectedCurrentOrdersSubStatus;
	}
	
	public Integer getCancellationsSubStatusResetInd() {
		return cancellationsSubStatusResetInd;
	}
	public void setCancellationsSubStatusResetInd(
			Integer cancellationsSubStatusResetInd) {
		this.cancellationsSubStatusResetInd = cancellationsSubStatusResetInd;
	}
	/*public String getFilterCancellationsSubStatus() {
		return filterCancellationsSubStatus;
	}
	public void setFilterCancellationsSubStatus(String filterCancellationsSubStatus) {
		this.filterCancellationsSubStatus = filterCancellationsSubStatus;
	}*/
	public List<String> getSelectedCancellationsSubStatus() {
		return selectedCancellationsSubStatus;
	}
	public void setSelectedCancellationsSubStatus(
			List<String> selectedCancellationsSubStatus) {
		this.selectedCancellationsSubStatus = selectedCancellationsSubStatus;
	}
	public Integer getRevisitSubStatusResetInd() {
		return revisitSubStatusResetInd;
	}
	public void setRevisitSubStatusResetInd(Integer revisitSubStatusResetInd) {
		this.revisitSubStatusResetInd = revisitSubStatusResetInd;
	}
	public List<String> getSelectedRevisitSubStatus() {
		return selectedRevisitSubStatus;
	}
	public void setSelectedRevisitSubStatus(List<String> selectedRevisitSubStatus) {
		this.selectedRevisitSubStatus = selectedRevisitSubStatus;
	}
}
