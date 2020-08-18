package com.newco.marketplace.persistence.iDao.so.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.ValidationRulesVO;
import com.newco.marketplace.api.mobile.beans.sodetails.InvoiceDocumentVO;
import com.newco.marketplace.api.mobile.beans.vo.WarrantyHomeReasonInfoVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistory;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.InitialPriceDetailsVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.RoleVO;
import com.newco.marketplace.dto.vo.SOWorkflowControlsVO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.incident.AssociatedIncidentVO;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmServiceVO;
import com.newco.marketplace.dto.vo.leadsmanagement.ReviewVO;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.dto.vo.price.PendingCancelPriceVO;
import com.newco.marketplace.dto.vo.price.ServiceOrderPriceVO;
import com.newco.marketplace.dto.vo.provider.BasicFirmDetailsVO;
import com.newco.marketplace.dto.vo.provider.ProviderDetailWithSOAccepted;
import com.newco.marketplace.dto.vo.provider.ProviderFirmVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.serviceorder.AdditionalPaymentVO;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.ClosedOrdersRequestVO;
import com.newco.marketplace.dto.vo.serviceorder.ClosedServiceOrderVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.CounterOfferReasonsVO;
import com.newco.marketplace.dto.vo.serviceorder.MarketMakerProviderResponse;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.PendingCancelHistoryVO;
import com.newco.marketplace.dto.vo.serviceorder.ProblemLookupVO;
import com.newco.marketplace.dto.vo.serviceorder.ProblemResolutionSoVO;
import com.newco.marketplace.dto.vo.serviceorder.ProviderDetail;
import com.newco.marketplace.dto.vo.serviceorder.ReasonLookupVO;
import com.newco.marketplace.dto.vo.serviceorder.ResponseSoVO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProviderResponseVO;
import com.newco.marketplace.dto.vo.serviceorder.SearchRequestVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceDatetimeSlot;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderPendingCancelHistory;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderRole;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSpendLimitHistoryListVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSpendLimitHistoryVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTabResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderWfStatesCountsVO;
import com.newco.marketplace.dto.vo.serviceorder.SoDocumentVO;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.dto.vo.serviceorder.WfStatesVO;
import com.newco.marketplace.dto.vo.so.order.ServiceOrderRescheduleVO;
import com.newco.marketplace.dto.vo.so.order.SoCancelVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.ordermanagement.so.RescheduleVO;
import com.newco.marketplace.vo.provider.LastClosedOrderVO;
import com.newco.marketplace.vo.provider.LicensesAndCertVO;
import com.newco.marketplace.vo.provider.WarrantyVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderIvrDetailsVO;

public interface ServiceOrderDao {

	public ServiceOrder getServiceOrder(String serviceOrderID) throws DataServiceException;
	
	public SoCancelVO getServiceOrderForCancel(String serviceOrderID) throws DataServiceException;
	
	public Map getTaskPrice(Integer entityId) throws DataServiceException;
	
	public ServiceOrder getGroupedServiceOrders(String parentGroupId) throws DataServiceException;
	
	public ServiceOrder getServiceOrdersForGroup(String parentGroupId,List<String> responseFilters) throws DataServiceException;
	
	public List<String> getServiceOrderIDsForGroup(String parentGroupId) throws DataServiceException;
	
	public Date getServiceOrderModifiedDate( String serviceOrderID ) throws DataServiceException;

	public String getServiceLocTimeZone_soHdr(String serviceOrderID) throws DataServiceException;

	public ServiceOrder insert(ServiceOrder so) throws DataServiceException;
	
	public ServiceOrder insertSOForWS(ServiceOrder so) throws DataServiceException;

	public ServiceOrder query(ServiceOrder so) throws DataServiceException;

	public ArrayList<ServiceOrder> queryList(ServiceOrder so)
			throws DataServiceException;

	public int update(ServiceOrder so) throws DataServiceException;

	public int updateSOReschedule(ServiceOrder so) throws DataServiceException;
	
	public int updatePartsSuppier(ServiceOrder so) throws DataServiceException;

	public String queryProviderEmail(Integer id) throws DataServiceException;
    public int resetResponseHistory(String soId) throws DataServiceException;
    public int removeConditionalFromRoutedProviders(String soId) throws DataServiceException;
	public void insertSoCustomReference(ServiceOrderCustomRefVO soRefVO) throws DataServiceException;	
	/**
	 * Updates the provided service order to the future workflow state, if the
	 * current workflow state matches an entry in the
	 * validWorkflowStateTransitions
	 * 
	 * @param serviceOrder
	 * @param futureWorkflowState
	 * @param validWorkflowStateTransitions
	 * @return <code>int</code> number of rows affected
	 * @throws DataServiceException
	 */
	public int updateServiceOrderWorkflowState(ServiceOrder serviceOrder,
			Integer futureWorkflowState,
			ArrayList<Integer> validWorkflowStateTransitions)
			throws DataServiceException;

	public int updateProviderResponse(ResponseSoVO responseSoVo) throws DataServiceException;
	
	/**
	 * Update so_routed_providers table to set the email_sent flag to true for the routed providers
	 * @param serviceOrder
	 * @return
	 * @throws DataServiceException
	 */
	public int updateRoutedProvidersEmailSentFlag(ServiceOrder serviceOrder) throws DataServiceException;	
	
    public int updateLimit(ServiceOrder so) throws DataServiceException;
    
    /**
     * Updates the price details in so_price table.
	 * This reusable can be used to update different prices like conditional offer, original and discounted.
     * 
     * @param soPriceVO
     * @return no. of records updated
     * @throws DataServiceException
     */
    public int updateSOPrice(ServiceOrderPriceVO soPriceVO) throws DataServiceException;
    
    public int getRoutedProviderCount(String soId) throws DataServiceException;
    public int getRejectResponseCount(String soId) throws DataServiceException;
    
    public int updateAccepted(ServiceOrder so) throws DataServiceException;
    
    public ArrayList<ReasonLookupVO> queryListRejectReason() throws DataServiceException;	

	public ArrayList<ServiceOrderRole> queryListBuyer(RoleVO rvo)
			throws DataServiceException;

	public ArrayList<ServiceOrderRole> queryListProvider(RoleVO rvo)
			throws DataServiceException;

	public ArrayList<ServiceOrderWfStatesCountsVO> queryListSOWfStatesBuyerCounts(
			AjaxCacheVO ajaxCacheVo) throws DataServiceException;
	
	public ArrayList<ServiceOrderWfStatesCountsVO> queryListSOWfStatesSimpleBuyerCounts(
			AjaxCacheVO ajaxCacheVo) throws DataServiceException;

	public ArrayList<ServiceOrderWfStatesCountsVO> queryListSOWfStatesProviderCounts(
			AjaxCacheVO ajaxCacheVo) throws DataServiceException;

	public ArrayList<ServiceOrderTabResultsVO> getSOProviderReceived(
			Integer providerId) throws DataServiceException;
	
	public ArrayList<HashMap> queryRoutedSO(String serviceOrderID) 
			throws DataServiceException;
	
	public ArrayList<ServiceOrderSearchResultsVO> getDetailedCountsBuyer(
			AjaxCacheVO ajaxCacheVO) throws DataServiceException;
	
	public ArrayList<ServiceOrderSearchResultsVO> getDetailedCountsProvider(
			AjaxCacheVO ajaxCacheVO) throws DataServiceException;
	
	public ArrayList<ServiceOrderSearchResultsVO> getProviderCachedDetails(
			AjaxCacheVO ajaxCacheVO) throws DataServiceException;
		
	public HashMap getSummaryCountsBuyer(AjaxCacheVO ajaxCacheVO)
			throws DataServiceException;
	
	public HashMap getSummaryCountsSimpleBuyer(AjaxCacheVO ajaxCacheVO)
	throws DataServiceException;

	public HashMap getSummaryCountsProvider(AjaxCacheVO ajaxCacheVO)
			throws DataServiceException;
			
	public ServiceOrderNote insertNote(ServiceOrderNote note)
	    throws DataServiceException;
	
	public ServiceOrderNote queryMax(ServiceOrderNote note)
		throws DataServiceException;
	
	public List getSONotes(ServiceOrderNote note)
		throws DataServiceException;
	
	public List getAllSONotes(ServiceOrderNote note)
		throws DataServiceException;
	
	public List getSODeletedNotes(ServiceOrderNote note)
		throws DataServiceException;
	
	public Part insertPart(Part part)throws DataServiceException;
	
	public ServiceOrderTask insertTask(ServiceOrderTask task)
    throws DataServiceException;
	
	public ArrayList<ServiceOrder> queryFilterServiceOrderByStatus(String status) throws DataServiceException;
	
	public WfStatesVO getWfStatesVO(Integer wfStateID) throws DataServiceException;
	
	public int updateSOStatus(ServiceOrder serviceOrder) throws DataServiceException;
	
	public Integer checkWfState(String soId) throws DataServiceException;	
		
	//Report a problem functionality
	public ArrayList<ProblemLookupVO> queryListSoProblemType(int wfStateId) throws DataServiceException;
	public ProblemResolutionSoVO getProblemDesc(String soId) throws DataServiceException;
	public int reportProblemResolution(ServiceOrder so) throws DataServiceException;
	
	//	Provider Conditional Offer
	public int createConditionalOffer(RoutedProvider routedProvider) throws DataServiceException;
	public int updateSODateTime(ServiceOrderRescheduleVO reschedule) throws DataServiceException;
	
	public ServiceOrderRescheduleVO getRescheduleRequestInfo(String soId) throws DataServiceException;
	
	public String getAssignmentType(String soId)throws DataServiceException;
	
	public int updateCancelReschedule(String soId) throws DataServiceException;  

	public int updateSODateTimeFinal(ServiceOrderRescheduleVO reschedule) throws DataServiceException;
	
	public int deleteSO(String soId) throws DataServiceException;
	public int deleteLogEntry(String soId) throws DataServiceException;
	public int updateProviderResponseConditionalOffer(RoutedProvider routedProvider) throws DataServiceException;
	
	public int updateProvRespGroupedConditionalOffer(RoutedProvider routedProvider) throws DataServiceException;
	
	public Integer checkConditionalOfferResp(RoutedProvider routedProvider)throws DataServiceException;
	
	public Integer insertRoutedResources(List<RoutedProvider> routedResources)throws DataServiceException;
	
	public int updateSOSubStatus(String serviceOrderId,Integer subStatusId)throws DataServiceException;
	
	public int updateSOCustomReference(String serviceOrderId, String referenceType, String referenceValue)throws DataServiceException;
	
	public int updateDocSizeTotal(String soId, long size)throws DataServiceException;
	
	public Integer updateTotalOrdersComplete(Integer buyerID,Integer vendorResourceId) throws DataServiceException;
	
	public int updateParts(List<Part> partList) throws DataServiceException;

	public void addOrUpdatePart(Part part) throws DataServiceException;
		
	public void updateLockEditMode(String soId, Integer setEditMode)throws DataServiceException;
	
	public void updateServiceOrder(ServiceOrder serviceOrder) throws DataServiceException;
	
	public ServiceOrder insertServiceOrderAsscoiates(ServiceOrder so) throws DataServiceException;
	
	public ServiceOrder insertSoHeader(ServiceOrder so) throws DataServiceException;

	public void updateSoHdrLogoDocument(String soId, Integer logoDocumentId) throws DataServiceException;
	
	public int updateSoHdrForReleaseSO(ServiceOrder so) throws DataServiceException;
	
	public int updateRoutedProviderRecords(RoutedProvider routedProvider) throws DataServiceException;
	
	/**
	 * This method with get detailed information for routed providers
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public List<RoutedProvider> getRoutedProviders(String soId)  throws DataServiceException;
	
	/**
	 * This method will get the basic information only from so_routed_providers table
	 * If you need conditional date or expiration date or market maker provider responese or posted so count then use
	 * the other method <i>getRoutedProvider</i>
	 * 
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public List<RoutedProvider> getRoutedProvidersWithBasicInfo(String soId)  throws DataServiceException;
	
	/**
	 * This methods gets the list of market maker providers response for a particular SO ID and specified resources.
	 *
	 * @param soId
	 * @param resourceIds
	 * @return
	 * @throws DataServiceException
	 */
	public List<MarketMakerProviderResponse> 
		getMarketMakerProvidersResponse(String soId, List<Integer> resourceIds)throws DataServiceException;
	
	/**
	 * This method gets the count of posted orders for a list of vendors
	 * @param vendorIds
	 * @return A map of vendor ID and the corresponding count of posted orders
	 * @throws DataServiceException
	 */
	public Map<Integer, Integer> getPostedSoCountForVendors(List<Integer> vendorIds) throws DataServiceException;
	
	public List<LookupVO> getCallStatusList();

	public String deleteServiceOrder(String soId,String groupId) throws DataServiceException;

	
	public void deleteOldServiceOrders(Integer numberDaysOld) throws DataServiceException;
	
	public Buyer getBuyerAttributes(Integer buyerId) throws DataServiceException;
	
	public Integer getMainServiceCategoryCount(Integer mainCategoryId) throws DataServiceException;
	
	public Integer checkForValidServiceOrderID(String so_id);
	
	public boolean checkIfResourceAcceptedServiceOrder(Integer resource_id, String so_id);
	
	public List<String> findValidServiceOrders(Integer resource_id, String last6SoId);
	
	public String getServiceLocationTimeZone(String serviceLocationZipCode);
	
	public int updateServiceOrderCancellationFee(String soId, Double cancellationFee)throws DataServiceException;
	
	public String getSubStatusDesc(Integer soSubStatusID) throws DataServiceException;
	
	public ServiceOrder getServiceOrder(String externalID, Integer buyerID) throws DataServiceException;
	
	public int updateServiceLocation(String soID, SoLocation location);
	
	public void deleteParts(ServiceOrder so) throws DataServiceException;
	
	public String getSubStatusDesc(String soID) throws DataServiceException;
	
	public ArrayList<Contact> getRoutedResources(String soId,String companyId) throws DataServiceException;
	
	/**
	 * Gets response details of the routed resources for the specified SO	 * 
	 * @param soId
	 * @return List<Integer>
	 * @throws BusinessServiceException
	 */
	public List<RoutedProviderResponseVO> getRoutedResourcesResponseInfo(String soId) throws DataServiceException;
	
	public void soLoggingInsert(SoLoggingVo soLogging) throws DataServiceException;
	
	public void soNotesInsert(ServiceOrderNote soNote) throws DataServiceException;
	
	public int soHdrTableUpdate(SoLoggingVo soLogging) throws DataServiceException;
	
	public int soContactTableUpdate(SoLoggingVo soLogging) throws DataServiceException;
	//This will update provider_resp_id in so_routed_provider after reassign
	public int soRoutedProviderUpdate(SoLoggingVo soLoggingVO)throws DataServiceException;;
	
	public void deleteParts(String soID) throws DataServiceException;

	public int deleteTask(ServiceOrderTask task);

	public void insertCustomRef(ServiceOrderCustomRefVO ref);

	public int updateServiceOrderContact(String soId, Contact serviceContact);

	public int updateProviderInstructions(String soId, String newInstructions);
	public void insertSoDocument(SoDocumentVO soDocVO);

	/**
	 * Deletes custom reference fields for given buyer reference field type id and given so_id
	 * 
	 * @param ServiceOrderCustomRefVO custRefVO containing refTypeId and soId
	 * @throws DataServiceException in case of any error
	 */
	public void deleteCustomRefByBuyerRefID(ServiceOrderCustomRefVO custRefVO) throws DataServiceException;

	public void deletePartPickupLocation(String soId, Integer locationId);
	public void deletePartPickupPhones(String soID, Integer contactId);

	public void deleteContactLocation(String soId, Integer contactId, Integer locationId);

	public void deletePartPickupContact(String soId, Integer contactId);

	public void deleteRoutedProviders(String soId);	
	
	public void updateFinalLaborPrice(String soID,Double cancellationFee) throws DataServiceException;

	public int updateSoCustomReference(ServiceOrderCustomRefVO socrVo) throws DataServiceException;
	public int updateMktMakerComments(MarketMakerProviderResponse mktMakerProvResp) throws DataServiceException;
	
	/**
	 * retirve price info for given SOId
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public ServiceOrderPriceVO getSoPrice(String soId)throws DataServiceException;
	
	public ServiceOrder getServiceOrder(String referenceType, String referenceValue, Integer buyerID) throws DataServiceException;

	public List<ServiceOrderCustomRefVO> getCustomReferenceFields(String soId) throws DataServiceException;
	
	public InitialPriceDetailsVO getInitialPrice(String soId) throws DataServiceException; 
	
	public ServiceOrderCustomRefVO getCustomReferenceObject(String customRefId, String soId) throws DataServiceException;
	
	public int updateSOPartsShippingInfo(List<Part> parts) throws DataServiceException;
	
	public List<ServiceOrderCustomRefVO>  getCustomReferenceList(String customRefType, String customRefValue, String soId, List statusIds) throws DataServiceException;
	/**
	 * Method to return the status id and the completed date of a service order.
	 * @param serviceOrderID
	 * @return
	 * @throws DataServiceException
	 */
	public ServiceOrder getServiceOrderStatusAndCompletedDate(String serviceOrderID) throws DataServiceException;
	
	/**
	 * Updates the description in scope of work section for service order
	 * 
	 * @param soId
	 * @param newDescription
	 * @return
	 * @throws DataServiceException
	 */
	public int updateSowDs(String soId, String newDescription)throws DataServiceException;
	
	/**
	 * Updates the title in scope of work section for service order
	 * 
	 * @param soId
	 * @param newTitle
	 * @return
	 * @throws DataServiceException
	 */
	public int updateSowTitle(String soId, String newTitle)throws DataServiceException;

	/**
	 * Returns the associated incidents
	 * @param soId
	 * @return List<AssociatedIncidentVO> associatedIncidents
	 * @throws DataServiceException
	 */
	public List<AssociatedIncidentVO> getAssociatedIncidents(String soId) throws DataServiceException;
	/**
	 * Calls a stored procedure which creates staging data if no staging data is available.
	 * @param soId
	 * @throws DataServiceException
	 */
	public void stageShcOrder(String soId)throws DataServiceException;
	/*
	 * This method retrieves all the service providers names under a firm.
	 * @param resourceId	
	 * @return ArrayList
	 * @throws BusinessServiceException
	 * 
	 */
	public ArrayList<ProviderDetail> queryServiceProviders(Integer resourceId) throws BusinessServiceException;
	/*
	 * This method retrieves all the Market names under a firm.
	 * @param resourceId	
	 * @return ArrayList
	 * @throws BusinessServiceException
	 * 
	 */
	public ArrayList<ProviderDetail> queryMarketNames(Integer resourceId) throws BusinessServiceException;
	/**
	 * Returns the list of service orders which satisfies the Search criteria specified in SearchRequestVO searchRequest
	 * @param searchRequest SearchRequestVO
	 * @return List<ServiceOrder>
	 * 
	 */
	public List<ServiceOrder> getSearchResultSet(
			SearchRequestVO searchRequest) throws DataServiceException;
	
	/**
	 * Returns the list of service orders which satisfies the Search criteria specified in SearchRequestVO searchRequest
	 * @param searchRequest SearchRequestVO
	 * @return List<ServiceOrder>
	 * 
	 */
	public List<ServiceOrder> getSearchResultSetPaged(
			SearchRequestVO searchRequest) throws DataServiceException;
	
	
	/**
	 * Returns the list of language Ids corresponding to language names
	 * @param languageNames List<String>
	 * @return List<Integer>
	 * 
	 */
	public List<Integer> getLanguageIds(List<String> languageNames)throws DataServiceException;
	/**
	 * This method inserts the initial_posted_labor_spend_limit and initial_posted_parts_spend_limit
	 * of service order while its Posted 
	 * @param serviceOrderId
	 * @return integer 
	 */	
	public int updateInitialPostedSOPrice(ServiceOrder serviceOrder)throws DataServiceException;
	/**
	 * This method gets the initial_posted_labor_spend_limit and initial_posted_parts_spend_limit
	 * of service order while its Posted 
	 * @param serviceOrderId
	 * @return integer 
	 * throws DataServiceException
	 */
	public ServiceOrder getInitialPostedSOPrices(ServiceOrder serviceOrder);
	/**
	 * This method updates the final price data
	 * @param so
	 * @return
	 * @throws DataServiceException
	 */
	public int updateFinalPrice(ServiceOrder so) throws DataServiceException ;
    /**
     * updates the routed date in so_routed_providers for given soId
     * @param soId
     */
	public void updateRoutedDateForProviders(String soId) throws DataServiceException ;

	/*
	 * get routed date for given resourceId & soId
	 */
	public Date getRoutedDateForResourceId(String soId, Integer resourceId) throws DataServiceException ;
	
	public Date getRoutedDateForFirm(String soId, Integer vendorId) throws DataServiceException;
	/**
	 * This method gets the Substatus id for the given substatus description
	 * @param subStatus,status
	 * @return Integer 
	 * throws DataServiceException
	 */
	public Integer getSubStatusId(String subStatus,int status) throws DataServiceException;
	/**
	 * Method gets the reasons list for the selected counter offer condition
	 * @param providerRespId
	 * @return List<CounterOfferReasonsVO>
	 * @throws DataServiceException
	 */
	public List<CounterOfferReasonsVO> getReasonsForSelectedCounterOffer(int providerRespId) throws DataServiceException;
	/**
	 * Method to insert the counter offer reason code
	 * @param conditionalOffer
	 * @throws DataServiceException
	 */
	public void insertReasonCdForCounterOffer(RoutedProvider conditionalOffer)throws DataServiceException;
	/**
	 * Method to delete the existing counter offer reason code
	 * @param conditionalOffer
	 * @return int 
	 * @throws DataServiceException
	 */
	public int deleteReasonCdForCounterOffer(RoutedProvider conditionalOffer) throws DataServiceException;
	/**
	 * Method fetches unit no, order no, buyer id and so contact phone no for a given SO ID
	 * @param String soId
	 * @return ServiceOrderIvrDetailsVO
	 * @throws BusinessServiceException
	 */
	public ServiceOrderIvrDetailsVO getSoCustomReferencesWS(String soId)throws DataServiceException;	
	public ArrayList<LookupVO> getSubstatusDesc(List<Integer> id)  throws DataServiceException;
	
	public ArrayList<LookupVO> getRescheduleReasonCodes(Integer roleId)  throws DataServiceException;
	
	public ArrayList<LookupVO> getPermitTypes()  throws DataServiceException;
	/**
	 * This method gets the routed providers for a specific SO and provider firm
	 * @param String soId, String vendorId, String resourceId, Boolean manageSoFlag
	 * @return List <ProviderResultVO> 
	 * throws DataServiceException
	 */
	public List <ProviderResultVO> getRoutedResourcesForFirm(String soId, String vendorId, String resourceId, Boolean manageSOFlag) 
		throws DataServiceException;
	/**
	 * This method gets the routed providers for a Group SO and provider firm
	 * @param String soId, String vendorId, String resourceId, Boolean manageSoFlag, ServiceOrder serviceOrder
	 * @return List <ProviderResultVO> 
	 * throws DataServiceException
	 */
	public List <ProviderResultVO> getRoutedResourcesListForFirmForGroup(String soId, String vendorId, String resourceId, Boolean manageSOFlag, ServiceOrder serviceOrder) 
		throws DataServiceException;
	
	public List<PendingCancelHistoryVO> getPendingCancelHistory(String soId) throws DataServiceException;
	
	public PendingCancelPriceVO getPendingCancelBuyerDetails(String soId) throws DataServiceException;
	
	public PendingCancelPriceVO getPendingCancelBuyerPriceDetails(String soId) throws DataServiceException;
	
	public PendingCancelPriceVO getPendingCancelProviderDetails(String soId) throws DataServiceException;
	
	public Double getBuyerMaxTransactionLimit(Integer resourceId,Integer buyerId) throws DataServiceException;
	
	/**
	 * Get the response based on the response filter
	 * @param serviceOrderID
	 *  @param responseFilters
	 * @return
	 * @throws DataServiceException
	 */
	public ServiceOrder getServiceOrder(String serviceOrderID,List<String> responseFilters) throws DataServiceException;
	/**
	 * Get the response for spendLimitIncrease 
	 * 
	 * @param soIdList
	 *                List<String>
	 * @return 
	 *           List<ServiceOrderSpendLimitHistoryVO>
	 * @throws DataServiceException
	 */
	public HashMap<String,ServiceOrderSpendLimitHistoryVO> getSpendLimitIncreaseForAPI(List<String>soIdList) throws DataServiceException;
	/**
	 * Get the service order with a reduced set of result map
	 * @param serviceOrderID
	 * @return
	 * @throws DataServiceException
	 */
	public ServiceOrder getServiceOrderForAPI(String serviceOrderID) throws DataServiceException;
	
	
	//SL 17504 Creating a method to update the sku indicator column of  so_workflow_controls table for edit serive order
	public void updateSkuIndicator(String soId) ;
	//This  method to fetch the sku indicator column of  so_workflow_controls table to identify the type of  serive order
	public Boolean fetchSkuIndicatorFromSoWorkFlowControl(String soID);
	public List<Integer> getRoutedProvidersForFirm(String soId,Integer vendorId)
			throws DataServiceException;

	//SL-15642: check whether so is car routed
	public boolean isCARroutedSO(String soId) throws DataServiceException;
	public boolean isAuthorizedToViewSODetls(String soId,String providerId) throws DataServiceException;
	public boolean isAuthorizedToViewGroupSODetls(String groupId,String providerId) throws DataServiceException;;


	public ServiceOrder getServiceOrderPriceDetails(ServiceOrder serviceOrder,Integer infolevel) throws DataServiceException;

	public String getVendorBusinessName(Integer vendorId)throws DataServiceException;
	
	public String getRescheduleReason(Integer reasonCode)throws DataServiceException;
	
	//SL 18418 Changes for CAR routed Orders the substatus is not set as Exclusive
	public String getMethodOfRouting(String soID) throws DataServiceException;

	public List<PreCallHistory> getScheduleHistory(String soId,
			Integer acceptedVendorId);
	
	public ProviderFirmVO getAcceptedFirmDetails(Integer vendorId) throws DataServiceException;

	public ProviderFirmVO getAcceptedFirmDetailsSoId(String soId)throws DataServiceException;

	//Sl-18226 Response status tab changes
	public boolean checkTierRoute(String soId);

	public List<ServiceOrderTask> getActiveTasks(String soId);
	
	public RescheduleVO getRescheduleInfo(String soId) throws DataServiceException;
	
	public Integer getBuyerIdForSo(String soid)throws DataServiceException;

	public RescheduleVO getBuyerRescheduleInfo(String soId) throws DataServiceException;
	/*
	 * PDF batch regeneration
	 */
	
	public Integer getCountOfMobileSignatureDocuments(String soId) throws DataServiceException;
	
	public Integer getSignaturePDFDocument(DocumentVO documentVO) throws DataServiceException;
	
	public void deleteSODocumentMapping(DocumentVO documentVO) throws DataServiceException;
	
	public void updatePDFBatchParamaters(DocumentVO documentVO) throws DataServiceException;
	
	//For checking Non Funded feature for an SO
	public boolean checkNonFunded(String soId);
	
	public boolean isNonFundedBuyer(Integer buyerId)throws DataServiceException;
	
	//SL-19050
	//marking note as Read
	public void markSOAsRead(String noteId) throws DataServiceException;
	
	
	//SL-19050
	//marking note as UnRead
	public void markSOAsUnRead(String noteId) throws DataServiceException;
	
	 
	public List<InvoiceDocumentVO> getInvoiceDocumentList(List <Integer> invoiceIds) throws DataServiceException;
	/**
	* get closure method
	* @param soId
	* @return
	* @throws DataServiceException
	*/
	public String getMethodOfClosure(String soId)throws DataServiceException;
	
	public boolean loadSOMswitch() throws DataServiceException;
	
	
	/**Description : SL-20400 ->To find out the duplicate service orders with the given unit number and 
	 * order number combination in ServiceLive database for InHome Buyer (3000).
	 * @param customUnitNumber
	 * @param customOrderNumber
	 * @return
	 * @throws DataServiceException
	 */
	public ServiceOrder getDuplicateSOInHome(String customUnitNumber,String customOrderNumber)
			throws DataServiceException;
	
	/**
	 * Used to log request and response Duplicate SO's for InHome Buyer (3000).
	 * @param String request,String response, String buyerId
	 * @return Integer
	 */	
	public Integer logDuplicateSORequestResponse(String request,String response, Integer buyerId,String apiName)throws DataServiceException;
	/**
	 * SL-21070
	 * Method fetches the lock_edit_ind of the so
	 * @param soId
	 * @return int lockEditInd
	 */
	public int getLockEditInd(String soId);
	/**@Description: Fetching original OrderId and warranty provider for the service order
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public SOWorkflowControlsVO getSoWorkflowControls(String soId) throws DataServiceException;
	
	/**Priority 5B changes
	 * update invalid_model_serial_ind column in so_workflow_controls
	 * @param soId
	 * @param ind
	 * @throws BusinessServiceException
	 */
	public void updateModelSerialInd(String soId, String ind) throws DataServiceException;
	
	/**
	 * priority 5B changes
	 * get buyer first name & last name
	 * @param buyerResId
	 * @return 
	 * @throws BusinessServiceException
	 */
	public String getBuyerName(Integer buyerResId) throws DataServiceException;

	/**
	 * priority 5B changes
	 * get the validation rules for the fields
	 * @param fields
	 * @return List<ValidationRulesVO>
	 * @throws BusinessServiceException
	 */
	public List<ValidationRulesVO> getValidationRules(List<String> fields) throws DataServiceException;
	/**
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
//	public boolean isLessThanSpendLimitLabour(String soId) throws DataServiceException;

	/** Get Estimate Details
	 * @param soId
	 * @param estimationId
	 * @return EstimateVO
	 */
	public EstimateVO getEstimate(String soId, Integer estimationId) throws DataServiceException;
	
	
	public EstimateVO getEstimateMainDetails(String soId, Integer estimationId) throws DataServiceException;
	
	public void insertEstimateHistory(EstimateVO estimateVO) throws DataServiceException;


	
	/**
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	//	public boolean isLessThanSpendLimitLabour(String soId) throws DataServiceException;


	/**
	 * @param soId
	 * @param estimateId
	 * @return boolean
	 * @throws DataServiceException
	 */
	public boolean validateEstimate(String soId, Integer estimateId) throws DataServiceException;

	/**
	 * @param soId
	 * @param estId
	 * @param status
	 * @param comments
	 * @param source
	 * @param modifiedBy
	 * @param customerName
	 * @return
	 * @throws DataServiceException
	 */
	public void updateEstimateStatus(String soId, Integer estId, String status, 
			String comments,String source, String modifiedBy,String customerName)throws DataServiceException;

	/**
	 * Fetch the estimation details of an SO
	 * @param soId
	 * @param vendorId
	 * @return List<EstimateVO>
	 * @throws DataServiceException
	 */
	public List<EstimateVO> getEstimationDetails(String soId,Integer vendorId) throws DataServiceException;

	/** method for retrieving closed SO Details for provider.
	 * @param closedOrdersRequestVO
	 * @return List<ClosedServiceOrderVO> 
	 * @throws DataServiceException
	 */
	public List<ClosedServiceOrderVO> getClosedOrders(
			ClosedOrdersRequestVO closedOrdersRequestVO) throws DataServiceException ;
	/**
	 * Method to fetch the valid firms
	 * @param firmIds
	 * @return
	 * @throws DataServiceException
	 */
	public List<String> getValidProviderFirms(List<String> firmIds)throws DataServiceException ;
	/**
	 * method to fetch the basic minimum details of firm
	 * @param firmId
	 * @return
	 * @throws DataServiceException
	 */
	public List<BasicFirmDetailsVO> getBasicFirmDetails(List<String> firmIds)throws DataServiceException ;
	/**
	 * method tpo fetch the  insurance details
	 * @param firmId
	 * @return
	 * @throws DataServiceException
	 */
	public List<LicensesAndCertVO> getVendorInsuranceDetails(List<String> firmIdList) throws DataServiceException;
	/**
	 * method to fetch the credential details
	 * @param vendorId
	 * @return
	 * @throws DataServiceException
	 */

	public List<LicensesAndCertVO> getVendorLicenseDetails(List<String> firmIdList) throws DataServiceException;
	/**
	 * method to last closed order details
	 * @param vendorId
	 * @return
	 * @throws DataServiceException
	 */

	public List<LastClosedOrderVO> getLastClosedOrder(List<String> firmIdList) throws DataServiceException;

	/**
	 * fetch the service details of the firm
	 * @param firmIdList
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<FirmServiceVO> getVendorServiceDetails(List<String> firmIdList) throws DataServiceException;

	/**
	 * fetch the review details of the firm
	 * @param firmIdList
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<ReviewVO> getVendorReviewDetails(List<String> firmIdList) throws DataServiceException;
	/**
	 * method to fetch the warranty details 
	 * @param firmIdList
	 * @return
	 * @throws DataServiceException
	 */
	public List<WarrantyVO> getWarrantyDetails(List<String> firmIdList)throws DataServiceException;
	/**
	 * method to fetch the number of employees
	 * @param firmIds
	 * @return
	 * @throws DataServiceException
	 */
	public Map<Long, Long> getNoOfEmployees(List<String> firmIds)throws DataServiceException;
	/**
	 * method to fetch the aggregateRating of the firm
	 * @param firmIds
	 * @return
	 * @throws DataServiceException
	 */
	public Map<Integer, BigDecimal> getAggregateRating(List<String> firmIds)throws DataServiceException;
	/**
	 * method to fetch the over all review count of a firm
	 * @param firmIds
	 * @return
	 * @throws DataServiceException
	 */
	public Map<Integer, Long> getOverallReviewCount(List<String> firmIds)throws DataServiceException;

	
	/**
	 * fetch the sealed bid ind of SO
	 * @param soId
	 * @return Boolean
	 * @throws BusinessServiceException
	 */
	public Boolean fetchSealedBidIndicator(String soId) throws DataServiceException;

	/** R16_1_1: SL-21270:Fetching finalLaborPrice and finalPartsPrice from so_hdr
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public ServiceOrderPriceVO getFinalPrice(String soId) throws DataServiceException;

	/** R16_1_1: SL-21270:Fetching basic addon details
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public AdditionalPaymentVO getAddonDetails(String soId) throws DataServiceException;

	
	
	public ServiceOrderCustomRefVO getCustomReferenceValue(String customRefId,String soId) throws DataServiceException;

	/**@Description: Fetching the logo document id for the service order
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public Integer getLogoDocumentId(String soId) throws DataServiceException;;
	
	/** SL-21446-Relay Services: Fetching the company logo if available 
	 * @param firmIds
	 * @return
	 */
	public Map<Long, String> getCompanyLogo(List<String> firmIds) throws DataServiceException;

	/** SL-21446- Fetching the default logo
	 * @param defaultFirmLogo
	 * @return
	 * @throws DataServiceException
	 */
	public String getDefaultLogo(String defaultFirmLogo) throws DataServiceException;

	/** SL-21446- Fetching the firm logo path from application properties
	 * @param firmLogoPath
	 * @return
	 * @throws DataServiceException
	 */
	public String getStaticUrl(String firmLogoPath) throws DataServiceException;
	
	/** SL-21446- Fetching the firm logo save location from application properties
	 * @param firmLogoSavePath
	 * @return
	 * @throws DataServiceException
	 */
	public String getFirmLogoSaveLocation(String firmLogoSavePath) throws DataServiceException;
	
	public HashMap<String, Object> getDocumentDetails(String soId,String documentName)throws DataServiceException;
	
	
	public String getConstantValueFromDB(String appkey)
			throws DataServiceException ;
	
   public  List<ServiceDatetimeSlot> getSODateTimeSlots(String soId) throws DataServiceException;
   
   public  void updateAcceptedServiceDatetimeSlot(ServiceDatetimeSlot serviceDatetimeSlot) throws BusinessServiceException;
	
   public  ServiceDatetimeSlot getSODateTimeSlot(String soId, Integer preferenceInd) throws DataServiceException;

   public void updateCorelationIdWithSoId(HashMap<String, Object> corelationIdAndSoidMap) throws DataServiceException;

   public void saveWarrantyHomeReasons(WarrantyHomeReasonInfoVO warrantyHomeReasonInfoVO) throws BusinessServiceException;
   public List<ProviderDetailWithSOAccepted> getAvailableProviderAcceptedSO(Integer buyerId, Integer days)throws DataServiceException;
   public String isServiceOrderUnique(Integer buyerId, String uniqueId) throws DataServiceException;
   
   //SLT-2138: Send Push notification for Service Order Cancellation
   public Integer getPrimaryResourceId(Integer acceptedVendorId) throws BusinessServiceException;
   public Integer getAcceptedVendorIdForPushNotfcn(String soId) throws BusinessServiceException;
   
   //SLT-4491
   public Integer getValidProvider(Integer providerId) throws DataServiceException;
}
