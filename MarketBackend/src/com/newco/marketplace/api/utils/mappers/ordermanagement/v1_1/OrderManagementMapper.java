package com.newco.marketplace.api.utils.mappers.ordermanagement.v1_1;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.withdrawOffer.WithdrawCondOfferResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.assignProvider.SOAssignProviderResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.editAppTime.SOEditAppointmentTimeResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.editSlNotes.SOEditSOLocationNotesResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.eligibleproviders.EligibleProvider;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.eligibleproviders.EligibleProviders;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.eligibleproviders.SOGetEligibleProviderResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.CancellationsSubStatusFilterList;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.CurrentOrdersSubStatusFilterList;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.FetchSOResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.FilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.JobDoneSubStatusFilterList;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.MarketFilterList;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.ProviderFilterList;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.RevisitSubStatusFilterList;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.ScheduleStatusFilterList;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.ServiceOrderList;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.StatusFilterList;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.SubStatusFilterList;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getCallInfo.CallDetails;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getCallInfo.GetCallInfoResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistoryDetails;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistoryResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getTabs.GetTabsResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getTabs.Tab;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getTabs.TabCountList;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.providerCall.SOProviderCallResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.reasonCodes.GetReasonCodesResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.reasonCodes.ReasonCode;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.reasonCodes.ReasonCodeBean;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.releaseInfo.GetReleaseResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.releaseInfo.ReleaseDetails;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.releaseSO.SOReleaseResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateFlag.SOPriorityResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateScheduleDetails.UpdateScheduleDetailsResponse;
import com.newco.marketplace.vo.ordermanagement.so.OMServiceOrder;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;

public class OrderManagementMapper {
	Logger LOGGER = Logger.getLogger(OrderManagementMapper.class);
	public FetchSOResponse mapFetchOrdersResponse (List<OMServiceOrder> serviceOrderVOList, FilterVO filterList) {
		FetchSOResponse fetchSOResponse = new FetchSOResponse();
		ServiceOrderList soList =  new ServiceOrderList();
		soList.setSoDetail(serviceOrderVOList);
		fetchSOResponse.setSoDetails(soList);
		
		MarketFilterList mlist = new MarketFilterList();
		if(filterList.getMarketList()!=null){
			mlist.setMarkets(filterList.getMarketList());
		}else{
			mlist = null;
		}
		
		fetchSOResponse.setMarketList(mlist);
		
		ProviderFilterList plist = new ProviderFilterList();
		if(filterList.getProviderList()!=null){
			plist.setProviders(filterList.getProviderList());
		}else{
			plist = null;
		}
		fetchSOResponse.setProviderList(plist);
		
		//Setting routed providers list for assigned Provider tab
		ProviderFilterList routerProviders = null;
		if(filterList.getRoutedProviderList() != null){
			routerProviders = new ProviderFilterList();
			routerProviders.setProviders(filterList.getRoutedProviderList());
		}
		fetchSOResponse.setRoutedProviderList(routerProviders);
		
		StatusFilterList statusList = new StatusFilterList();
		if(filterList.getStatusList()!=null){
			statusList.setStatus(filterList.getStatusList());
		}else{
			statusList = null;
		}
		fetchSOResponse.setStatusList(statusList);
		
		SubStatusFilterList subStatusList = new SubStatusFilterList();
		if(filterList.getSubStatusList()!=null){
			subStatusList.setSubstatus(filterList.getSubStatusList());
		}else{
			subStatusList = null;
		}
		fetchSOResponse.setSubstatusList(subStatusList);
		
		ScheduleStatusFilterList scheduleList = new ScheduleStatusFilterList();
		if(filterList.getScheduleList()!=null){
			scheduleList.setSchedulestatus(filterList.getScheduleList());
		}else{
			scheduleList = null;
		}
		fetchSOResponse.setScheduleFilterList(scheduleList);
		//Job done List
		JobDoneSubStatusFilterList jList= new JobDoneSubStatusFilterList();
		if(null !=filterList.getJobDoneSubStatusList()){
			jList.setJobDonesubstatus(filterList.getJobDoneSubStatusList());
		}else{
			jList= null;
		}
		fetchSOResponse.setJobDoneSubsubstatusList(jList);
		//Current Orders List
		CurrentOrdersSubStatusFilterList currentOrderList= new CurrentOrdersSubStatusFilterList();
		if(null != filterList.getCurrentOrdersSubStatusList()){
			currentOrderList.setCurrentOrdersSubStatus(filterList.getCurrentOrdersSubStatusList());
		}else{
			currentOrderList= null;
		}
		fetchSOResponse.setCurrentOrdersSubStatusList(currentOrderList);
		
		//R12.0 sprint3 cancellations substatus filter
		CancellationsSubStatusFilterList cancellationsList= new CancellationsSubStatusFilterList();
		if(null != filterList.getCancellationsSubStatusList()){
			cancellationsList.setCancellationsSubStatus(filterList.getCancellationsSubStatusList());
		}else{
			cancellationsList= null;
		}
		fetchSOResponse.setCancellationsSubStatusList(cancellationsList);
		
		//R12.0 sprint4 Revisit substatus filter
		RevisitSubStatusFilterList revisitList= new RevisitSubStatusFilterList();
			if(null != filterList.getRevisitSubStatusList()){
				revisitList.setRevisitSubStatus(filterList.getRevisitSubStatusList());
			}else{
				revisitList= null;
			}
				fetchSOResponse.setRevisitSubStatusList(revisitList);
		
		Results results = new Results();
		results = Results.getSuccess(ResultsCode.SUCCESS.getMessage());
		fetchSOResponse.setResults(results);
		
		return fetchSOResponse;
	}
	
	public SOGetEligibleProviderResponse  mapEligibleProviderResponse(List<EligibleProvider>  providerList){
		SOGetEligibleProviderResponse eligibleProviderResponse =  new SOGetEligibleProviderResponse();
		EligibleProviders eligibleProviders = new EligibleProviders();
		eligibleProviders.setEligibleProviderList(providerList);
		eligibleProviderResponse.setEligibleProviders(eligibleProviders);
		Results results = Results.getSuccess("Success");
		if(null == providerList || (null != providerList && 0 == providerList.size())){
			results = Results.getError("Eligible providers are not available", ServiceConstants.USER_ERROR_RC);
		}
		eligibleProviderResponse.setResults(results);
		return eligibleProviderResponse;
	}
	
	public SOAssignProviderResponse mapAssignProviderResponse() {
		SOAssignProviderResponse soAssignProviderResponse = new SOAssignProviderResponse();
		Results results = new Results();
		results = Results.getSuccess(ResultsCode.SUCCESS.getMessage());
		soAssignProviderResponse.setResults(results);
		return soAssignProviderResponse;
	}
	
	public SOEditSOLocationNotesResponse mapEditSOLocationNotesResponse() {
		SOEditSOLocationNotesResponse editNotesResponse = new SOEditSOLocationNotesResponse();		
		Results results = new Results();
		results = Results.getSuccess(ResultsCode.SUCCESS.getMessage());
		editNotesResponse.setResults(results);
		return editNotesResponse;
	}
	
	public SOEditAppointmentTimeResponse mapEditAppointmentTimeResponse() {
		SOEditAppointmentTimeResponse updateTimeResponse = new SOEditAppointmentTimeResponse();
		Results results = new Results();
		results = Results.getSuccess(ResultsCode.SUCCESS.getMessage());
		updateTimeResponse.setResults(results);
		return updateTimeResponse;
	}
	public UpdateScheduleDetailsResponse mapScheduleDetailsResponse() {
		// TODO Auto-generated method stub
		UpdateScheduleDetailsResponse updateScheduleDetailsResponse = new UpdateScheduleDetailsResponse();
		Results results = new Results();
		results = Results.getSuccess(ResultsCode.SUCCESS.getMessage());
		updateScheduleDetailsResponse.setResults(results);
		return updateScheduleDetailsResponse;
	}
	public GetCallInfoResponse mapGetCallDetailsResponse(CallDetails callDetails) {
		// TODO Auto-generated method stub
		GetCallInfoResponse callInfoResponse = new GetCallInfoResponse();
		callInfoResponse.setCallDetails(callDetails);
		return  callInfoResponse;
	}
	public PreCallHistoryResponse preCallHistoryDetailsResponse(PreCallHistoryDetails preCallHistoryDetails) {
		// TODO Auto-generated method stub
		PreCallHistoryResponse preCallHistoryResponse = new PreCallHistoryResponse();
		preCallHistoryResponse.setPreCallHistoryDetails(preCallHistoryDetails);
		Results results = new Results();
		results = Results.getSuccess(ResultsCode.SUCCESS.getMessage());
		preCallHistoryResponse.setResults(results);
		return  preCallHistoryResponse;
	}
	
	//setting the response for priority flag update
	public SOPriorityResponse mapPriorityResponse(int flag) {	
		SOPriorityResponse priorityResponse = new SOPriorityResponse();		
		Results results = new Results();
		results = Results.getSuccess(ResultsCode.SUCCESS.getMessage());
		priorityResponse.setResults(results);
		priorityResponse.setFlag(flag);		
		return priorityResponse;
	}
	
	public SOProviderCallResponse mapResponseForProviderCall() {
		// TODO Auto-generated method stub
		return new SOProviderCallResponse(); 
	}
	public GetReasonCodesResponse mapReasonCodeResponse(List<ReasonCode> reasoncodes) {
		GetReasonCodesResponse reasonCodesResponse =new GetReasonCodesResponse();
		ReasonCodeBean reasonCodeBean = new ReasonCodeBean();
		reasonCodeBean.setReasonList(reasoncodes);
		reasonCodesResponse.setReasonCodeBean(reasonCodeBean);
		Results results = Results.getSuccess("Success");
		if(null == reasoncodes || (null != reasoncodes && 0 == reasoncodes.size())){
			results = Results.getError("No Reason codes", ServiceConstants.USER_ERROR_RC);
		}
		reasonCodesResponse.setResults(results);
		return reasonCodesResponse;
	}
	
	/**
	 * The method to create response for "Get SO Count for Lists" API.
	 * @param : tabsWithCount : A HashMap with Tab Name as key and TabCount as Value
	 * @return {@link GetTabsResponse} : The response with tabNames and corresponding TabCount.
	 * **/
	public GetTabsResponse mapGetTabsResponse(
			LinkedHashMap<String, Integer> tabsWithCount) {
		GetTabsResponse orderManagementTabResponse = new GetTabsResponse();	
		List<Tab> tablist = null;
		Results results = new Results();
		results = Results.getSuccess(ResultsCode.SUCCESS.getMessage());
		TabCountList countList = new TabCountList();
		if(null != tabsWithCount && !tabsWithCount.isEmpty()){
			tablist = new ArrayList<Tab>();
			Set<String> keys = tabsWithCount.keySet();  
			Tab tab = null;
			for (String key : keys) {  
				tab = new Tab();
				tab.setTabName(key);
				tab.setSoCount(tabsWithCount.get(key)); 
				tablist.add(tab);
			}  
		}else{
			results = Results.getError("tabsWithCount is null or empty", ServiceConstants.USER_ERROR_RC);
			LOGGER.info("tabsWithCount is null or empty");
		}
		orderManagementTabResponse.setResults(results);
		countList.setTabCountList(tablist);
		orderManagementTabResponse.setTabCount(countList);
		return orderManagementTabResponse;
	}
	
	public GetReleaseResponse mapReleaseInfoResponse(
			ReleaseDetails releaseDetails) {
		// TODO Auto-generated method stub
		return new GetReleaseResponse();
	}
	public SOReleaseResponse mapReleaseResponse(List<String> errors) {
		SOReleaseResponse returnVal = new SOReleaseResponse();
		Results results = new Results();
		returnVal.setResults(results);
		if(errors!=null && !errors.isEmpty()){
			results = Results.getError(ResultsCode.SO_RELEASE_ERROR.getMessage(),ResultsCode.SO_RELEASE_ERROR.getCode());
		}else{
			Results.getSuccess(ResultsCode.RELEASE_SUCCESSFUL.getMessage());
		}
		return returnVal;
	}
	
	/**
	 * Response for withdraw counter offer API
	 * @param : pResponse
	 * @return withdrawOfferResponse
	 * **/
	public WithdrawCondOfferResponse mapSOWithdrawCondOfferResponse(ProcessResponse pResponse){
		WithdrawCondOfferResponse withdrawOfferResponse = new WithdrawCondOfferResponse();
		Results results = new Results();
		if (ServiceConstants.VALID_RC.equalsIgnoreCase(pResponse.getCode())) {
			//results = Results.getSuccess(ResultsCode.WITHDRAW_OFFER_SUBMITTED.getMessage());
			results = Results.getSuccess("Counter Offer Request Withdrawn");
		}else{
			//results = Results.getError(ResultsCode.WITHDRAW_OFFER_ERROR.getMessage(),
					//ResultsCode.WITHDRAW_OFFER_ERROR.getCode());
			results = Results.getError("Withdraw Offer Failed","0006");
		}
		withdrawOfferResponse.setResults(results);
		return withdrawOfferResponse;
	}

	public boolean validateManageSoPermission(String providerId){
		return false;
	}
	public boolean validateCounterOfferStatus(String providerId,String soId){
		return false;
	}
	public boolean validateScheduleStatus(String providerId,String soId){
		return false;
	}
	public boolean validateReasonCodes(String providerId,String soId,String reasonCode,String reasonType){
		return false;
	}
	
}
