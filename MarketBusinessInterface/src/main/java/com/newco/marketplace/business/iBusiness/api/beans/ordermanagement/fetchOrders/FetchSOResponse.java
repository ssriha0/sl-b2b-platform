package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders;

import java.util.List;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.business.iBusiness.om.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


/**
 * This is a bean class for storing response information for 
 * the SOUpdateService
 * @author Infosys
 *
 */
@XStreamAlias("fetchOrderResponse")
public class FetchSOResponse implements IAPIResponse{
	
	
	
	 @XStreamAlias("version")   
	 @XStreamAsAttribute()   
	private String version=PublicAPIConstant.SORESPONSE_VERSION;
	 
	 @XStreamAlias("xsi:schemaLocation")   
	 @XStreamAsAttribute()   
	private String schemaLocation=PublicAPIConstant.SO_FETCH_RESPONSE_SCHEMALOCATION;
	 
	 @XStreamAlias("xmlns")   
	 @XStreamAsAttribute()   
	private String namespace=PublicAPIConstant.SO_FETCH_RESPONSE_NAMESPACE;
	 
	 @XStreamAlias("xmlns:xsi")   
	 @XStreamAsAttribute()   
	private String schemaInstance=PublicAPIConstant.SCHEMA_INSTANCE;
	
	@XStreamAlias("results")
	private Results results;
	
	@OptionalParam
	@XStreamAlias("soDetails")
	private ServiceOrderList soDetails;
	@OptionalParam
	@XStreamAlias("marketList")
	private MarketFilterList marketList;
	@OptionalParam
	@XStreamAlias("statusList")
	private StatusFilterList statusList;
	@OptionalParam
	@XStreamAlias("subStatusList")
	private SubStatusFilterList substatusList;
	@XStreamAlias("scheduleStatusList")
	@OptionalParam
	private ScheduleStatusFilterList scheduleFilterList;
	@XStreamAlias("providerList")
	private ProviderFilterList providerList;
	
	@XStreamAlias("routedProviderList")
	@OptionalParam
	private ProviderFilterList routedProviderList;
	
	
	@XStreamAlias("soCount")
	@OptionalParam
	private Integer totalSOCount;
	
	@XStreamAlias("soCountWithoutFilters")
	@OptionalParam
	private Integer soCountWithoutFilters;
	
	@XStreamAlias("jobDoneSubStatusFilterList")
	@OptionalParam
	private JobDoneSubStatusFilterList jobDoneSubsubstatusList;
	
	@XStreamAlias("currentOrdersSubStatusFilterList")
	@OptionalParam
	private CurrentOrdersSubStatusFilterList currentOrdersSubStatusList;
	
	//R12.0 Sprint3 Cancellations substatus filter

	@XStreamAlias("cancellationsSubStatusFilterList")
	@OptionalParam
	private CancellationsSubStatusFilterList cancellationsSubStatusList;
   
	//R12.0 Sprint4 Revisit substatus filter

	@XStreamAlias("revisitSubStatusFilterList")
	@OptionalParam
	private RevisitSubStatusFilterList revisitSubStatusList;
   
	public Integer getSoCountWithoutFilters() {
		return soCountWithoutFilters;
	}

	public void setSoCountWithoutFilters(Integer soCountWithoutFilters) {
		this.soCountWithoutFilters = soCountWithoutFilters;
	}

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
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

	public MarketFilterList getMarketList() {
		return marketList;
	}

	public void setMarketList(MarketFilterList marketList) {
		this.marketList = marketList;
	}

	public StatusFilterList getStatusList() {
		return statusList;
	}

	public void setStatusList(StatusFilterList statusList) {
		this.statusList = statusList;
	}

	
	public SubStatusFilterList getSubstatusList() {
		return substatusList;
	}

	public void setSubstatusList(SubStatusFilterList substatusList) {
		this.substatusList = substatusList;
	}

	public ScheduleStatusFilterList getScheduleFilterList() {
		return scheduleFilterList;
	}

	public void setScheduleFilterList(ScheduleStatusFilterList scheduleFilterList) {
		this.scheduleFilterList = scheduleFilterList;
	}
	public ProviderFilterList getProviderList() {
			return providerList;
		}

	public void setProviderList(ProviderFilterList providerList) {
			this.providerList = providerList;
		}

	public ProviderFilterList getRoutedProviderList() {
		return routedProviderList;
	}

	public void setRoutedProviderList(ProviderFilterList routedProviderList) {
		this.routedProviderList = routedProviderList;
	}

	public ServiceOrderList getSoDetails() {
		return soDetails;
	}

	public void setSoDetails(ServiceOrderList soDetails) {
		this.soDetails = soDetails;
	}

	public Integer getTotalSOCount() {
		return totalSOCount;
	}

	public void setTotalSOCount(Integer totalSOCount) {
		this.totalSOCount = totalSOCount;
	}

	public JobDoneSubStatusFilterList getJobDoneSubsubstatusList() {
		return jobDoneSubsubstatusList;
	}

	public void setJobDoneSubsubstatusList(
			JobDoneSubStatusFilterList jobDoneSubsubstatusList) {
		this.jobDoneSubsubstatusList = jobDoneSubsubstatusList;
	}

	public CurrentOrdersSubStatusFilterList getCurrentOrdersSubStatusList() {
		return currentOrdersSubStatusList;
	}

	public void setCurrentOrdersSubStatusList(
			CurrentOrdersSubStatusFilterList currentOrdersSubStatusList) {
		this.currentOrdersSubStatusList = currentOrdersSubStatusList;
	}

	public CancellationsSubStatusFilterList getCancellationsSubStatusList() {
		return cancellationsSubStatusList;
	}

	public void setCancellationsSubStatusList(CancellationsSubStatusFilterList cancellationsSubStatusList) {
		this.cancellationsSubStatusList = cancellationsSubStatusList;
	}

	public RevisitSubStatusFilterList getRevisitSubStatusList() {
		return revisitSubStatusList;
	}

	public void setRevisitSubStatusList(RevisitSubStatusFilterList revisitSubStatusList) {
		this.revisitSubStatusList = revisitSubStatusList;
	}

	
}

