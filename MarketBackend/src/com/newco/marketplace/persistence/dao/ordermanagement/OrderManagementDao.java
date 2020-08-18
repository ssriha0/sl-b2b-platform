package com.newco.marketplace.persistence.dao.ordermanagement;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.eligibleproviders.EligibleProvider;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.CancellationsSubStatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.CurrentOrdersSubStatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.RevisitSubStatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.FetchSORequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.JobDoneSubStatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.MarketFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.ProviderFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.RoutedProvidersVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.ScheduleStatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.StatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.SubStatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getCallInfo.CallDetails;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistory;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistoryDetails;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.reasonCodes.ReasonCode;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.releaseInfo.ReleaseDetails;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateScheduleDetails.UpdateScheduleVO;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.PhoneVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.mobile.ProviderHistoryVO;
import com.newco.marketplace.vo.ordermanagement.so.AvailabilityDateVO;
import com.newco.marketplace.vo.ordermanagement.so.InvoicePartsVO;
import com.newco.marketplace.vo.ordermanagement.so.OMServiceOrder;
import com.newco.marketplace.vo.ordermanagement.so.RescheduleVO;
import com.newco.marketplace.vo.ordermanagement.so.SOScope;

public interface OrderManagementDao {

	public List<OMServiceOrder> getOrders(FetchSORequest request,String providerId, Map<String, String> sortCriteria) ;
	public List<MarketFilterVO> getMarkets(List<String> soIds,String providerId);
	public List<ProviderFilterVO> getProviders (List<String> soIds,String providerId); 
	public List<ProviderFilterVO> getRoutedProvidersForAssignTab(List<String> soIds, String providerId);
	public List<StatusFilterVO> getStatus (List<String> soIds,String providerId);
	public List<SubStatusFilterVO> getSubStatus  (List<String> soIds,String providerId);
	public List<ScheduleStatusFilterVO> getScheduleStatus  (List<String> soIds,String providerId);
	public List<String> fetchAllOrders(FetchSORequest filterVO,String providerId);
	public boolean hasViewOrderPricing(Integer resourceId) throws DataServiceException;
	public List<ProviderResultVO> getEligibleProviders(String firmId, String soId);
	public List<ProviderResultVO> getEligibleProvidersForGroup(String firmId,String groupId);
	public void assignRoutedProvider(Integer resourceId, String soId, SoLoggingVo loggingVo, ServiceOrderNote soNote) throws DataServiceException;
	public void saveContactInfoForAssign(Integer resourceId, String soId, Contact soContact, SoLocation location, PhoneVO phoneVo) throws DataServiceException;
	public void editSOLocNotes(String notes, String soId);
	public void editSOAppointmentTime(UpdateScheduleVO scheduleVO) throws DataServiceException;
	public void updateScheduleDetails(UpdateScheduleVO updateScheduleVO);
	public CallDetails getCallDetails(String firmId, String soId);
	public PreCallHistoryDetails getPrecallHistoryDetails(String firmId, String soId, String source);
	//get the current priority of so
	public int getSOPriority(String soId, String firmId);
	//update priority of so
	public void updateSOPriority(String soId, String firmId, int flag);
	//get the current priority of group
	public int getGroupPriority(String groupId, String firmId);
	//update priority of group
	public void updateGroupPriority(String groupId, String firmId, int flag);
	public List<ReasonCode> fetchReasonCodes(String reasonType);
	public Map<String, Integer> getCountOfTabs(List<String> tabList, Integer firmId, boolean viewOrderPricing) throws DataServiceException;
	public ReleaseDetails getReleaseDetails(Integer resourceId, String soId);
	public void doRelease(Integer resourceId, String soId, String firmId);
	public void updateScheduleStatus(String scheduleStatus);
	public void insertPrecallhistory(PreCallHistory callHistory);
	public void editSOLocNotes(String soNotes);
	public void editSpecialInstruction(String specialInstructions);
	public EligibleProvider getAssignedResource(String soId);
	public String fetchAssignmentType(String soId);
	public List<Integer> fetchProviderList(String soId,String vendorId);
	public Integer getTotalCountForTab(FetchSORequest request, Integer firmId) throws DataServiceException;
	public Contact getRoutedResources(String soId, Integer resourceId);
	public List<RoutedProvidersVO> getProviderForAllSOs(List<String> soidListwithLimit, String vendorId);
	public List<RescheduleVO> getRescheduleRole(List<String> soidListwithLimit);

	public List<SOScope> getScopeForAllSOs(List<String> soidListwithLimit, String vendorId);
	public OMServiceOrder checkIfResourceAcceptedServiceOrder(String soId) throws DataServiceException;
	public List<OMServiceOrder> getJobDoneForAllSOs(List<String> soidListwithLimit);
	public boolean checkFilters(FetchSORequest fetchSORequest);
	public List<AvailabilityDateVO> getProductAvailabilityDate(List<String> soIdList);
	public List<JobDoneSubStatusFilterVO> getSubStatusForJobDone(List<String> soIds, String providerId);
	public List<CurrentOrdersSubStatusFilterVO> getSubStatusForCurrentOrders(List<String> soIds,String providerId);
	
	//R12.0 Sprint3 : Adding substatus filter to cancellations tab
	public List<CancellationsSubStatusFilterVO> getSubStatusForCancellations(List<String> soIds,String providerId);
	
	//R12.0 Sprint4 Adding substatus filter to revisit needed tab
	public List<RevisitSubStatusFilterVO> getSubStatusForRevisit(List<String> soIds,String providerId);
	public void insertHistory(ProviderHistoryVO hisVO) throws DataServiceException;
	
	/**
	 * @param soidListwithLimit
	 * @return
	 * 
	 * get last trip date for all SOs
	 */
	public List<OMServiceOrder> getLastTripForAllSOs(
			List<String> soidListwithLimit);
	/**
	 * @param soId
	 * @throws DataServiceException
	 */
	public void updateSOSubstatusToScheduleConfirmed(String soId) throws DataServiceException;
	
	/**
	 * @param soidListwithLimit
	 * @return
	 * 
	 * get part details for all SOs
	 */
	public List<InvoicePartsVO> getPartDetails(List<String> soidListwithLimit);
	
	//SL-21645
	public List<Contact> getSOContactDetails(String serviceOrderID);
		
	public SoLocation getServiceLocDetails(String serviceOrderID);
}
