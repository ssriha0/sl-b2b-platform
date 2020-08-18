
/*
 * This is a bean class for storing response information for 
 * the SOUpdateService
 * @author Infosys
 */

package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders;

import java.util.List;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.business.iBusiness.om.api.beans.common.UserIdentificationRequest;
import com.newco.marketplace.business.iBusiness.om.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


@XStreamAlias("soFetchRequest")
public class FetchSORequest extends UserIdentificationRequest{

	@XStreamAlias("version")   
	@XStreamAsAttribute()   
	private String version = PublicAPIConstant.SORESPONSE_VERSION;
	 
	@XStreamAlias("xsi:schemaLocation")   
	@XStreamAsAttribute()   
	private String schemaLocation;
	 
	@XStreamAlias("xmlns")   
	@XStreamAsAttribute()   
	private String namespace = PublicAPIConstant.FETCH_SO_NAMESPACE;
	
	@XStreamAlias("xmlns:xsi")   
	@XStreamAsAttribute()   
	private String schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
	
	@XStreamAlias("tabName")
	String tabName;
	
	@OptionalParam
	@XStreamAlias("markets")
	@XStreamImplicit(itemFieldName="markets")
	List<String> markets;
	
	@OptionalParam
	@XStreamAlias("providerIds")
	@XStreamImplicit(itemFieldName="providerIds")
	List<Integer> providerIds;
	
	@OptionalParam
	@XStreamAlias("routedProviderIds")
	@XStreamImplicit(itemFieldName="routedProviderIds")
	List<Integer> routedProviderIds;
	
	@OptionalParam
	@XStreamAlias("status")
	@XStreamImplicit(itemFieldName="status")
	List<String> status;
	
	@OptionalParam
	@XStreamAlias("substatus")
	@XStreamImplicit(itemFieldName="substatus")
	List<String> substatus;
	
	@OptionalParam
	@XStreamAlias("scheduleStatus")
	@XStreamImplicit(itemFieldName="scheduleStatus")
	List<String> scheduleStatus;
	
	@OptionalParam
	@XStreamAlias("appointmentType")
	String appointmentType;
	
	@OptionalParam
	@XStreamAlias("appointmentStartDate")
	String appointmentStartDate;
	
	@OptionalParam
	@XStreamAlias("appointmentEndDate")
	String appointmentEndDate;

	@XStreamAlias("endIndex")
	Integer endIndex;
	
	@XStreamAlias("sortOrder")
	String sortOrder;
	
	@XStreamAlias("sortBy")
	String sortBy;
	
	@OptionalParam
	@XStreamAlias("loadFiltersInd")
	Boolean loadFiltersInd;
	
	@OptionalParam
	@XStreamAlias("viewOrderPricing")
	Boolean viewOrderPricing;
	
	@OptionalParam
	@XStreamAlias("groupId")
	String groupId;
	
	@XStreamOmitField
	Boolean groupInd;
	
	@XStreamOmitField
	List<String> groupIds;
	
	@OptionalParam
	@XStreamAlias("initialLoadInd")
	Boolean initialLoadInd;
	
	@OptionalParam
	@XStreamAlias("includeUnassignedInd")
	Boolean includeUnassignedInd;	
	
	@OptionalParam
	@XStreamAlias("jobDoneSubstatus")
	@XStreamImplicit(itemFieldName="jobDoneSubstatus")
	List<String> jobDoneSubsubstatus;
	
	@OptionalParam
	@XStreamAlias("currentOrdersSubStatus")
	@XStreamImplicit(itemFieldName="currentOrdersSubStatus")
	List<String> currentOrdersSubStatus;
	//R12.0 Sprint3 substatus filter to Cancellations tab
	@OptionalParam
	@XStreamAlias("cancellationsSubStatus")
	@XStreamImplicit(itemFieldName="cancellationsSubStatus")
	List<String> cancellationsSubStatus;
	
	//R12.0 Sprint4 substatus filter to Revisit Needed tab
	@OptionalParam
	@XStreamAlias("revisitSubStatus")
	@XStreamImplicit(itemFieldName="revisitSubStatus")
	List<String> revisitSubStatus;
	
	
	public List<String> getCancellationsSubStatus() {
		return cancellationsSubStatus;
	}
	public void setCancellationsSubStatus(List<String> cancellationsSubStatus) {
		this.cancellationsSubStatus = cancellationsSubStatus;
	}
	public Boolean getInitialLoadInd() {
		return initialLoadInd;
	}
	public void setInitialLoadInd(Boolean initialLoadInd) {
		this.initialLoadInd = initialLoadInd;
	}
	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	public List<String> getMarkets() {
		return markets;
	}
	public void setMarkets(List<String> markets) {
		this.markets = markets;
	}
	public List<Integer> getProviderIds() {
		return providerIds;
	}
	public void setProviderIds(List<Integer> providerIds) {
		this.providerIds = providerIds;
	}
	public List<Integer> getRoutedProviderIds() {
		return this.routedProviderIds;
	}
	public void setRoutedProviderIds(List<Integer> routedProviderIds) {
		this.routedProviderIds = routedProviderIds;
	}
	public List<String> getStatus() {
		return status;
	}
	public void setStatus(List<String> status) {
		this.status = status;
	}
	public List<String> getSubstatus() {
		return substatus;
	}
	public void setSubstatus(List<String> substatus) {
		this.substatus = substatus;
	}
	public List<String> getScheduleStatus() {
		return scheduleStatus;
	}
	public void setScheduleStatus(List<String> scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}
	public String getAppointmentType() {
		return appointmentType;
	}
	public void setAppointmentType(String appointmentType) {
		this.appointmentType = appointmentType;
	}
	public String getAppointmentStartDate() {
		return appointmentStartDate;
	}
	public void setAppointmentStartDate(String appointmentStartDate) {
		this.appointmentStartDate = appointmentStartDate;
	}
	public String getAppointmentEndDate() {
		return appointmentEndDate;
	}
	public void setAppointmentEndDate(String appointmentEndDate) {
		this.appointmentEndDate = appointmentEndDate;
	}

	public Integer getEndIndex() {
		return endIndex;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public Boolean getLoadFiltersInd() {
		return loadFiltersInd;
	}
	public void setLoadFiltersInd(Boolean loadFiltersInd) {
		this.loadFiltersInd = loadFiltersInd;
	}
	public Boolean getIncludeUnassignedInd() {
		return this.includeUnassignedInd;
	}
	public void setIncludeUnassignedInd(Boolean includeUnassignedInd) {
		this.includeUnassignedInd = includeUnassignedInd;
	}
	public Boolean getViewOrderPricing() {
		return viewOrderPricing;
	}
	public void setViewOrderPricing(Boolean viewOrderPricing) {
		this.viewOrderPricing = viewOrderPricing;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSchemaLocation() {
		return schemaLocation;
	}
	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public String getSchemaInstance() {
		return schemaInstance;
	}
	public void setSchemaInstance(String schemaInstance) {
		this.schemaInstance = schemaInstance;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public Boolean getGroupInd() {
		return groupInd;
	}
	public void setGroupInd(Boolean groupInd) {
		this.groupInd = groupInd;
	}
	public List<String> getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(List<String> groupIds) {
		this.groupIds = groupIds;
	}
	public List<String> getJobDoneSubsubstatus() {
		return jobDoneSubsubstatus;
	}
	public void setJobDoneSubsubstatus(List<String> jobDoneSubsubstatus) {
		this.jobDoneSubsubstatus = jobDoneSubsubstatus;
	}
	public List<String> getCurrentOrdersSubStatus() {
		return currentOrdersSubStatus;
	}
	public void setCurrentOrdersSubStatus(List<String> currentOrdersSubStatus) {
		this.currentOrdersSubStatus = currentOrdersSubStatus;
	}
	public List<String> getRevisitSubStatus() {
		return revisitSubStatus;
	}
	public void setRevisitSubStatus(List<String> revisitSubStatus) {
		this.revisitSubStatus = revisitSubStatus;
	}

}
