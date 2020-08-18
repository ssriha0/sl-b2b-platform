package com.newco.marketplace.business.iBusiness.serviceorder;
import java.io.ByteArrayOutputStream;

import com.newco.marketplace.dto.vo.ValidationRulesVO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.api.mobile.beans.sodetails.AddonPayment;
import com.newco.marketplace.api.mobile.beans.sodetails.InvoiceDocumentVO;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Signature;
import com.newco.marketplace.api.mobile.beans.vo.WarrantyHomeReasonInfoVO;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistory;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.InitialPriceDetailsVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.SOWorkflowControlsVO;
import com.newco.marketplace.dto.vo.ValidationRulesVO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.incident.AssociatedIncidentVO;
import com.newco.marketplace.dto.vo.ledger.MarketPlaceTransactionVO;
import com.newco.marketplace.dto.vo.logging.SoAutoCloseDetailVo;
import com.newco.marketplace.dto.vo.logging.SoChangeDetailVo;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo2;
import com.newco.marketplace.dto.vo.price.PendingCancelPriceVO;import com.newco.marketplace.dto.vo.provider.FirmDetailRequestVO;
import com.newco.marketplace.dto.vo.provider.FirmDetailsResponseVO;
import com.newco.marketplace.dto.vo.provider.ProviderDetailWithSOAccepted;
import com.newco.marketplace.dto.vo.provider.ProviderFirmVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.CounterOfferReasonsVO;
import com.newco.marketplace.dto.vo.serviceorder.FundingVO;
import com.newco.marketplace.dto.vo.serviceorder.MarketMakerProviderResponse;
import com.newco.marketplace.dto.vo.serviceorder.ClosedOrdersRequestVO;
import com.newco.marketplace.dto.vo.serviceorder.ClosedServiceOrderVO;
//import com.newco.marketplace.web.dto.SOCompleteCloseDTO;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.PendingCancelHistoryVO;
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
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSpendLimitHistoryListVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSpendLimitHistoryVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.dto.vo.so.order.ServiceOrderRescheduleVO;
import com.newco.marketplace.dto.vo.so.order.SoCancelVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.ordermanagement.so.RescheduleVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.provider.LastClosedOrderVO;
import com.newco.marketplace.vo.provider.LicensesAndCertVO;
import com.newco.marketplace.vo.provider.WarrantyVO;
/**
 * @author Reen_Jossy
 *
 */
public interface IServiceOrderBO {
	
    public boolean isSOInEditMode(String soID) throws BusinessServiceException;
    public ProcessResponse processCreateDraftSO(ServiceOrder serviceOrder, SecurityContext securityContext);
	public ProcessResponse processCreateDraftSO(ServiceOrder so, Integer attentionRequired, SecurityContext securityContext) throws BusinessServiceException;
	public ProcessResponse processCreateDraftSOBatch(ServiceOrder so, Integer attentionRequired, SecurityContext securityContext) throws BusinessServiceException;
	public ProcessResponse processIncidentReopen(ServiceOrder so, Integer subStatus, SecurityContext securityContext) throws BusinessServiceException;
	public ProcessResponse processCloseSO(Integer buyerId, String serviceOrderID, double finalPartsPrice, double finalLaborPrice, SecurityContext securityContext) throws BusinessServiceException;
	public ProcessResponse processCancelSOInAccepted(int buyerId, String soId, String cancelComment, String buyerName, SecurityContext securityContext) throws BusinessServiceException;
	//public ProcessResponse processCancelSOInAccepted(int buyerId, String soId, String cancelComment, String buyerName, SecurityContext securityContext, String actionName) throws BusinessServiceException;
	public ProcessResponse processRouteSO(Integer buyerId, String serviceOrderID, List<Integer> routedResourcesIds, Integer tierId, SecurityContext securityContext);
	//Used for Simple Buyer CC Funding
	public ProcessResponse processRouteSO(Integer buyerId, String serviceOrderID, List<Integer> routedResourcesIds, Double fundingAmountCC, SecurityContext securityContext);
	public ProcessResponse processAddNote(Integer resourceId, Integer roleId, String serviceOrderId, String subject, String Message, Integer noteTypeId, String createdByName, String modifiedBy, Integer entityId,String notesInd, boolean isEmailToSent, boolean isEmptyNoteAllowed, SecurityContext securityContext ) throws BusinessServiceException;
	public ProcessResponse processSupportAddNote(Integer resourceId, Integer roleId, String serviceOrderId, String subject, String Message, Integer noteTypeId, String createdByName, String modifiedBy, Integer entityId,String notesInd, boolean isEmailToSent, SecurityContext securityContext) throws BusinessServiceException;
	public ProcessResponse processVoidSOForWS(int buyerId, String soId, SecurityContext securityContext) throws BusinessServiceException;
	public ProcessResponse processVoidSO(int buyerId, String soId, SecurityContext securityContext) throws BusinessServiceException;
	public ProcessResponse processCancelRequestInActive(int buyerId, String soId, double cancelAmt, String buyerName, SecurityContext securityContext) throws BusinessServiceException;
	//public ProcessResponse processCancelRequestInActive(int buyerId, String soId, double cancelAmt, String buyerName, String actionName, SecurityContext securityContext) throws BusinessServiceException;
	public void sendAllProviderRejectAlert(String soId, SecurityContext securityContext);
	public boolean isRejectAlertNeeded(String soId);
	public ProcessResponse processChangeOfScope(ServiceOrder so, SecurityContext securityContext);
	public List<SoLoggingVo> getSoRescheduleLogDetails(String soId) throws DataServiceException ;
	public Double calcUpsellAmount(ServiceOrder serviceOrder);
	/**
	 * If you want to get back all notes for only one note type, set the noteTypeId attribute
	 * in the input parameter.  If you want to bring back all notes for more than one type,
	 * Set the noteTypeIds attribute in the input parameter with a List<Integer>
	 * @param soNote
	 * @return
	 */
	public ProcessResponse processGetSONotes(ServiceOrderNote soNote);
	public ProcessResponse updateSOSchedule(String soId, Timestamp startDate, Timestamp endDate, String startTime, String endTime, int buyerId, boolean validate, SecurityContext securityContext);
	public ProcessResponse updateSOSpendLimit(String soId, Double totalSpendLimitLabor, Double totalSpendlimitParts, String increasedSpendLimitComment, int buyerId, boolean validate, boolean skipAlertLogging,SecurityContext securityContext)
	throws BusinessServiceException; 
	public ProcessResponse rejectServiceOrder(int resourceId, String soId, int reasonId, int responseId, String modifiedBy, String reasonDesc, SecurityContext securityContext) throws BusinessServiceException;
	public ArrayList<ReasonLookupVO> queryListRejectReason() throws BusinessServiceException;
	public ProcessResponse processAcceptServiceOrder(String serviceOrderID, Integer resourceID, Integer companyId,Integer termAndCond ,boolean validate,boolean isRescheduleReqeust,boolean isIndividualOrder,SecurityContext securityContext);
	public void sendallProviderResponseExceptAccepted(String soId,SecurityContext securityContext); 
	
	/**
	 * Used to retrieve custom reference fields for given soId
	 * @param soId
	 * @return List<ServiceOrderCustomRefVO>
	 * @throws BusinessServiceException
	 */
	public List<ServiceOrderCustomRefVO> getCustomReferenceFields(String soId) throws BusinessServiceException;
	/**
	 * Used to retrieve initial prices for given soId
	 * @param soId
	 * @return InitialPriceDetailsVO
	 * @throws BusinessServiceException
	 */
	public InitialPriceDetailsVO getInitialPrice(String soId) throws BusinessServiceException;
	//public ProcessResponse processGetNotes(Integer buyerId, String soId) ;
	public List<SoChangeDetailVo> getSoChangeDetailVoList(String soId) throws DataServiceException;
	
	/**
	 * This method will get the basic information only from so_routed_providers table
	 * If you need conditional date or expiration date or market maker provider responese or posted so count then use
	 * the other method <i>getAllProviders</i>
	 * 
	 * @param soId
	 * @return A List of RoutedProvider
	 */
	public List<RoutedProvider> getRoutedProvidersWithBasicInfo(String soId);
	
	/**
	 * This method with get detailed information for routed providers
	 * @param soId
	 * @param buyerId
	 * @return A List of RoutedProvider
	 */
	public List<RoutedProvider> getAllProviders(String soId, Integer buyerId);
	/**
	 * This method returns a map of resource ID and corresponding market maker response for a particular SO ID
	 * @param soId
	 * @param resourceIds
	 * @return A Map of Resouce ID and Market Maker Response
	  * @throws BusinessServiceException
	 */
	public Map<Integer, MarketMakerProviderResponse> getMarketProvidersResponseMap(String soId, List<Integer> resourceIds) throws BusinessServiceException;

	public List<LookupVO> getCallStatusList();
	public ProcessResponse updateMktMakerComments(RoutedProvider routedProvider);
	public ProcessResponse reportProblem(String strSoId, int intSubStatusId, String strComment, int intEntityId, int intRoleType, String pbDesc, String loggedInUser, boolean skipAlertLogging, SecurityContext securityContext)
		throws BusinessServiceException;
	public ProcessResponse reportResolution(String strSoId, int intSubStatusId, String strComment, int intEntityId, int intRoleType, String strPbDesc, String strPbDetails, String strLoggedInUser, SecurityContext securityContext)
		throws BusinessServiceException;
	public ProblemResolutionSoVO getProblemDesc(String soId) throws BusinessServiceException;
	public ProcessResponse processCreateConditionalOffer(String serviceOrderID,Integer resourceID,Integer vendorOrBuyerID,Timestamp conditionalDate1,Timestamp conditionalDate2,String conditionalStartTime,String conditionalEndTime,Timestamp conditionalExpirationDate,Double incrSpendLimit, List<Integer> selectedCounterOfferReasonsList,SecurityContext securityContext,List<Integer> resourceIds);
	
	public ServiceOrder getServiceOrder(String soId ) throws BusinessServiceException;
	public SoCancelVO getServiceOrderForCancel(String soId ) throws BusinessServiceException;
	public ServiceOrder getGroupedServiceOrders(String groupId) throws BusinessServiceException;
	public ServiceOrder getServiceOrdersForGroup(String groupId,List<String> responseFilters) throws BusinessServiceException;
	public List<String> getServiceOrderIDsForGroup(String groupId) throws BusinessServiceException;
	public ByteArrayOutputStream getPDFForSO(String soId);
	public ByteArrayOutputStream getPDFForMobile(String soId, List<Signature> signatureList, AddonPayment addOnPaymentDetails, boolean customerCopyOnlyInd);
	
	//for SL_15642
	public ByteArrayOutputStream printPaperwork(List<String> checkedSoIds,
			List<String> checkedOptions);
	
	public ProcessResponse processCompleteSO(String strSoId, String resolutionDescr, int providerId, double partsFinalPrice, double laborFinalPrice, List<Part> partList,  List<BuyerReferenceVO> buyerRefs, SecurityContext securityContext) throws BusinessServiceException;
	public ProcessResponse acceptConditionalOffer(String soId, Integer resourceId, Integer vendorId, Integer resReasonId, 
    		Date startDate, Date endDate, String startTime, String endTime, Double spendLimit, Integer buyerId, boolean isIndividualOrder, SecurityContext securityContext);
	public ProcessResponse doProcessCreatePreDraftSO(ServiceOrder serviceOrder, SecurityContext securityContext);
	public ProcessResponse processReRouteSO(Integer buyerId, String serviceOrderID, boolean skipAlertLogging,SecurityContext securityContext);
	/**
	 * Function to get all the Notes for AL Administrator
	 * @param soNote
	 * @return
	 */
	public ProcessResponse processGetAllSONotes(ServiceOrderNote soNote);
	public ProcessResponse processGetDeletedSONotes(ServiceOrderNote soNote);
    public void insertSoCustomReference(ServiceOrderCustomRefVO soRefVO); 
    public void insertSoLogging(SoLoggingVo soLoggingVO); 
    
    public ProcessResponse updatePartsShippingInfo(String soId, List<Part> parts, SecurityContext securityContext) throws BusinessServiceException;


	/**
	 * Sets the requested reschedule date and times for the provided service order
	 * @param serviceOrderId
	 * @param newStartDate
	 * @param newEndDate
	 * @param newStartTime
	 * @param newEndTime
	 * @param subStatus
	 * @param requestorRole
	 * @param companyId
	 * @param scheduleType
	 * @return
	 * @throws BusinessServiceException
	 */
	public ProcessResponse requestRescheduleSO(String serviceOrderId, Timestamp newStartDate, Timestamp newEndDate, 
			String newStartTime, String newEndTime, Integer subStatus, Integer requestorRole, Integer companyId, String scheduleType, SecurityContext securityContext)throws BusinessServiceException;
	public ProcessResponse rescheduleSOComments(String serviceOrderId, Timestamp newStartDate, Timestamp newEndDate, 
			String newStartTime, String newEndTime, Integer subStatus, Integer requestorRole, Integer companyId, String scheduleType,String comments, SecurityContext securityContext)throws BusinessServiceException;
	
	public ProcessResponse requestRescheduleSO(String serviceOrderId, Timestamp newStartDate, Timestamp newEndDate, 
			String newStartTime, String newEndTime, Integer subStatus, Integer requestorRole, Integer companyId, String scheduleType, boolean convertGMT, SecurityContext securityContext)throws BusinessServiceException;
	
	public ServiceOrderRescheduleVO getRescheduleRequestInfo(String serviceOrderId)throws BusinessServiceException;
	public String getAssignmentType(String soId) throws BusinessServiceException;
	public ProcessResponse respondToRescheduleRequest(String serviceOrderId, boolean isRequestAccepted, Integer role, Integer companyId, SecurityContext securityContext)throws BusinessServiceException;
	
	
	public ProcessResponse acceptRescheduleRequest(String serviceOrderId, boolean isRequestAccepted, Integer role, Integer companyId, SecurityContext securityContext)throws BusinessServiceException;
	public ProcessResponse rejectRescheduleRequest(String serviceOrderId, boolean isRequestAccepted, Integer role, Integer companyId, SecurityContext securityContext)throws BusinessServiceException;
	
	public ProcessResponse cancelRescheduleRequest(String serviceOrderId, Integer role, Integer companyId, SecurityContext securityContext) throws BusinessServiceException;

	public ProcessResponse withdrawConditionalAcceptance(String serviceOrderId,Integer resourceID,Integer providerRespId, SecurityContext securityContext)throws BusinessServiceException;
	
	public ProcessResponse updateSOSubStatus(String serviceOrderId,Integer subStatusId, SecurityContext context) throws BusinessServiceException ;
	public ProcessResponse updateSOCustomReference(String soId, String referenceType, String referenceValue, String oldReferenceValue, SecurityContext securityContext)throws BusinessServiceException ;
	public void setLockMode(String serviceOrderId,Integer editLockMode)throws BusinessServiceException;
	public ProcessResponse processUpdateDraftSO(ServiceOrder serviceOrder,SecurityContext securityContext) throws BusinessServiceException;
	/**
	 * Sends Email Alert for Assurant update sub status with NEEDS ATTENTION 
	 * Using empty method "assurantAlertForNeedAttentionSubStatus" as method "updateSOSubStatus" already had  Alert Advice with FTP 
	 * @param serviceOrderId
	 * @param subStatusId
	 */
	public void assurantAlertForNeedsAttentionSubStatus(String serviceOrderId,Integer subStatusId,SecurityContext securityContext);
	
	public void updateSoHdrLogoDocument(String soId, Integer logoDocumentId) throws BusinessServiceException;
	
	public ProcessResponse releaseServiceOrderInActive(String soId, Integer reasonCode, String providerComment, Integer resourceId, SecurityContext securityContext) throws BusinessServiceException;
	public ProcessResponse releaseServiceOrderInAccepted(String soId, Integer reasonCode, String providerComment, Integer resourceId, SecurityContext securityContext) throws BusinessServiceException;
	public void releaseSOProviderAlert(String soId,SecurityContext securityContext);
	public ProcessResponse releaseServiceOrderInProblem(String soId, Integer reasonCode, String providerComment, Integer resourceId, SecurityContext securityContext) throws BusinessServiceException;
	
	public ProcessResponse deleteDraftSO(String soId, SecurityContext securityContext) throws BusinessServiceException;
	//public ProcessResponse deleteDraftSO(String soId,String actionName, SecurityContext securityContext) throws BusinessServiceException;
	public ProcessResponse activateAcceptedSO(String soId, SecurityContext securityContext) throws BusinessServiceException;
	public ProcessResponse expirePostedSO(String soId, SecurityContext securityContext) throws BusinessServiceException;
	public ProcessResponse expireConditionalOffer(String soId,Integer vendorResourceId,String groupId, SecurityContext securityContext) throws BusinessServiceException;
	
	public void deleteOldServiceOrders(Integer numberDaysOld) throws BusinessServiceException;
	
	/**
	 * isBuyerAssociatedToServiceOrder will check to see if the provided buyer id is associated with the given 
	 * service order
	 * @param serviceOrder
	 * @param buyerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean isBuyerAssociatedToServiceOrder (ServiceOrder serviceOrder, Integer buyerId) throws BusinessServiceException;
	
	/**
	 * isVendorAssociatedToServiceOrder will check to see if the provided vendor id is associated with the given 
	 * service order
	 * @param serviceOrder
	 * @param vendorId
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean isVendorAssociatedToServiceOrder (ServiceOrder serviceOrder, Integer vendorId) throws BusinessServiceException;
	public ProcessResponse processUpdatePart(Part part) throws BusinessServiceException;
	public ProcessResponse processUpdatePart(Part part, Integer partSuppiler) throws BusinessServiceException;
	public boolean isValidMainCategory(Integer mainCategoryId) throws DataServiceException;
	public boolean isValidServiceOrder(String soID);
	public boolean isAValidServiceOrder(Integer resourceId, String soId);
	public List<String> findValidServiceOrders(Integer resourceId, String last6SoId);
	
	/*
	 * This method is called from a Completion Record - EditCompletion 
	 * till buyer has not closed it to set it back to Active.
	 */
	public ProcessResponse editCompletionRecordForSo(String soId, SecurityContext ctx) throws BusinessServiceException;
	
	public ProcessResponse sendCancelEmailInActiveStatus(String soID, int buyerID);
	
	public ArrayList<Contact> getRoutedResources(String soId,String companyId) throws BusinessServiceException;
	
	/**
	 * Gets resourceId of the routed resources for the specified SO	 * 
	 * @param soId
	 * @return List<Integer>
	 * @throws BusinessServiceException
	 */
	public List<Integer> getRoutedResourceIds(String soId) throws com.newco.marketplace.exception.BusinessServiceException;
	
	/**
	 * Gets response details of the routed resources for the specified SO	 * 
	 * @param soId
	 * @return List<Integer>
	 * @throws BusinessServiceException
	 */
	public List<RoutedProviderResponseVO> getRoutedResourcesResponseInfo(String soId) throws BusinessServiceException;
	
	/**
	 * Gets routed date for given resourceId for the specified SO	 * 
	 * @param soId
	 * @return Date routed Date
	 * @throws BusinessServiceException
	 */
	public Date getRoutedDateForResourceId(String soId, Integer resourceId) throws BusinessServiceException;
	
	
	public ProcessResponse saveReassignSO(SoLoggingVo soLoggingVO,ServiceOrderNote soNote, String reassignReason, SecurityContext securityContext) throws BusinessServiceException;
	public Buyer getBuyerAttrFromBuyerId(Integer buyerId) throws BusinessServiceException;
	public ProcessResponse updateServiceOrder(ServiceOrder savedOrder, ServiceOrder updateSO, SecurityContext securityContext, String clientStatus, String templateName) throws BusinessServiceException;
	public ProcessResponse processChangePartPickupLocation(String soID, SecurityContext securityContext); 
	public ServiceOrder getByCustomReferenceValue(String customReferenceValue, Integer buyerID) throws BusinessServiceException;
	public boolean isAssociatedToViewSOAsPDF(String soId, Integer roleId,Integer requestingUserId) throws BusinessServiceException;
	public FundingVO checkBuyerFundsForIncreasedSpendLimit(ServiceOrder serviceOrder, Integer buyerId) throws BusinessServiceException;

	public String generateOrderNo(int buyerId) throws Exception ;
	public ServiceOrder getByCustomReferenceTypeValue(String customReferenceType, String customReferenceValue, Integer buyerID) throws BusinessServiceException;
	
	/**
	 * This method creates a RoutedProvider object using conditional offer data 
	 * to update the so_routed_providers table or so_group_routed_providers table
	 * @return RoutedProvider object
	 */
	public RoutedProvider createConditionalOffer(String serviceOrderId,
			Integer resourceId, Integer vendorId, Timestamp conditionalDate1,
			Timestamp conditionalDate2, String conditionalStartTime,
			String conditionalEndTime, Timestamp conditionalExpirationDate,
			Double conditionalPrice, String serviceLocationTimeZone);
	
	public boolean buyerOverMaxLimit(Double postingFee, Double labor, Double parts,SecurityContext securityContext) 
	throws BusinessServiceException;
	
	public boolean enoughBuyerFunds(AjaxCacheVO avo,
			ServiceOrder serviceOrder, MarketPlaceTransactionVO mvo, double increaseAmt)  throws BusinessServiceException;
	
	public void updateServiceLocation(String soId, SoLocation location);
	/**
	 * delete inserted routed providers
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public ProcessResponse deleteRoutedProviders(String soId) throws BusinessServiceException;
	
	public void updateServiceContact(ServiceOrder updatedServiceOrder, ServiceOrder so, SecurityContext securityContext);

	/**
	 * Converts starttime and endtime to GMT before storing to DB
	 * @param serviceOrder
	 */
	public void GivenTimeZoneToGMT(ServiceOrder serviceOrder);
	
	/**
	 * Converts starttime and endtime for slots to GMT before storing to DB
	 * @param serviceOrder
	 */
	public void GivenTimeZoneToGMTForSlots(ServiceOrder serviceOrder);
	
	/**
	 * get CustomRef value for given soId and customRef Key
	 * @param customRefTypeId
	 * @param soId
	 * @return
	 */
	public  ServiceOrderCustomRefVO getCustomRefValue(String customRefTypeId, String soId);
	
	/**
	 * get the custom ref values of all incident id
	 * @param customRefType
	 * @param customRefValue
	 * @param soId
	 * @return
	 */
	
	public List<ServiceOrderCustomRefVO> getServiceOrdersByCustomRefValue(String customRefType, String customRefValue, String soId, List<Integer> statusIds);
	
	public ServiceOrder getServiceOrderStatusAndCompletdDate(String soId ) throws BusinessServiceException;
	
	/**
	 * Returns the associated incidents
	 * @param soId
	 * @return List<AssociatedIncidentVO> associatedIncidents
	 * @throws BusinessServiceException
	 */
	public List<AssociatedIncidentVO> getAssociatedIncidents(String soId) throws BusinessServiceException;
	
	public ProcessResponse processRouteSO(Integer buyerId,
			String serviceOrderID, SecurityContext securityContext);
	
	public ProcessResponse processReRouteSO(Integer buyerId,
			String serviceOrderID, List<Integer> routedResourcesIds, Double fundingAmountCC, SecurityContext securityContext);
	
	public void associateBuyerDocumentsToSO(String soId,int buyerId, int roleId,int userId)throws BusinessServiceException;
	/**
	 * Returns the list of service orders which satisfies the Search criteria specified in SearchRequestVO searchRequest
	 * @param searchRequest SearchRequestVO
	 * @return List<ServiceOrder>
	 * 
	 */
	public List<ServiceOrder> getSearchResultSet(
			SearchRequestVO searchRequest);
	/**
	 * Returns the list of service orders which satisfies the Search criteria specified in SearchRequestVO searchRequest
	 * @param searchRequest SearchRequestVO
	 * @return List<ServiceOrder>
	 * 
	 */
	public List<ServiceOrder> getSearchResultSetPaged(
			SearchRequestVO searchRequest);
	/**
	 * Returns the list of language Ids corresponding to language names
	 * @param languageNames List<String>
	 * @return List<Integer>
	 * 
	 */
	public List<Integer> getLanguageIds(
			List<String> languageNames);
	
	/*
	 * This method retrieves all the service providers names under a firm.
	 * @param resourceId	
	 * @return ArrayList
	 * @throws BusinessServiceException
	 * 
	 */
	public ArrayList<ProviderDetail> queryServiceProviders(Integer resourceId) throws BusinessServiceException;
	/*
	 * This method retrieves all the market names under a firm.
	 * @param resourceId	
	 * @return ArrayList
	 * @throws BusinessServiceException
	 * 
	 */
	public ArrayList<ProviderDetail> queryMarketNames(Integer resourceId) throws BusinessServiceException;
	/**
	 * This method retrives a boolean value after checking if the Order can be routed.
	 * @param serviceOrderId
	 * @return boolean
	 */
	public boolean checkStateForRoute(String serviceOrderId);

	/**
	 * Returns the list of logged objects for a serviceOrder based on soId
	 * @param  soId String
	 * @return soLogList List<SoLoggingVo>
	 * 
	 */
	public List<SoLoggingVo> getSoLogDetails(String soId)throws DataServiceException; 
	/**
	 * This method reschedules the service dates of an SO in Posted status.
	 */
	public ProcessResponse processRescheduleForRoutedStatus(String serviceOrderId, Timestamp newStartDate, Timestamp newEndDate, 
	String newStartTime, String newEndTime, Integer companyId,boolean convertGMT, SecurityContext securityContext)throws BusinessServiceException;
	/**
	 * Method to update spend limit for WS
	 * @param updatedSO
	 * @param changed
	 * @param securityContext
	 * @return ProcessResponse
	 * @throws BusinessServiceException
	 */
	public ProcessResponse processUpdateSpendLimitforWS(ServiceOrder updatedSO,Map<String, Object> changed,SecurityContext securityContext)throws BusinessServiceException;
	/**
	 * Method to Reroute SO for WS
	 * @param so
	 * @param securityContext
	 * @return ProcessResponse
	 */
	public ProcessResponse processReRouteSOForWS(ServiceOrder so,SecurityContext securityContext);
	
	/**
	 * This method updates the tasks for OMS orders.
	 * @param ServiceOrder
	 * @param SecurityContext
	 * @return void
	 */
	public void processUpdateTask(ServiceOrder so, SecurityContext securityContext);
	
	/**
	 * get the remaining time left for provider to wait to Accept So
	 * @param soId
	 * @param resourceId
	 * @return
	 * @throws BusinessServiceException
	 */
	public int getTheRemainingTimeToAcceptSO(String soId, Integer resourceId) throws BusinessServiceException;
	
	public int getTheRemainingTimeToAcceptSOForFirm(String soId, Integer vendorId) throws BusinessServiceException;
	
	/**
	 * get the remaining time left for provider to wait to Accept So, given the routed Dt of SO
	 * @param routedDate
	 * @return
	 * @throws BusinessServiceException
	 */
	public int getTheRemainingTimeToAcceptSO(Date routedDate) throws BusinessServiceException;
	/**
	 * getProviderDistanceFromServiceLocation is used to fetch the distance of each provider from
	 * service location
	 * @param soId
	 * @param List<RoutedProvider>
	 * @return List<RoutedProvider>
	 */
	public List<RoutedProvider>  getProviderDistanceFromServiceLocation(String soId,
			List<RoutedProvider> routedProviderList);
	
	/**
	 * This method gets the Substatus id for the given substatus description
	 * @param subStatus,status
	 * @return Integer 
	 */
	public Integer getSubStatusId(String subStatus,int status);
	
	public ProcessResponse processCreateBid(String soId, Integer resourceId,
			Date bidDate, BigDecimal totalLabor, BigDecimal totalHours, 
			BigDecimal partsMaterials, Date bidExpirationDate,  String bidExpirationTime,
			Date newServiceDateRangeTo, Date newServiceDateRangeFrom,
			String newServiceStartTime, String newServiceEndTime,
			String comment, BigDecimal totalLaborParts, SecurityContext securityContext);
	
	/**
	 * Method gets the reasons list for the selected counter offer condition
	 * @param providerRespId
	 * @return List<CounterOfferReasonsVO>
	 * @throws BusinessServiceException
	 */
	public List<CounterOfferReasonsVO> getReasonsForSelectedCounterOffer(int providerRespId) throws BusinessServiceException;
	
	public void fundConsumerFundedOrder(ProcessResponse processResp,
			String soId, List<String> errorMessages)
			throws BusinessServiceException, DataServiceException;
	/**
	 * Method process the cancel request for an accepted service order from OMS 
	 * @param String soId
	 * @param SecurityContext
	 * @return void
	 * @throws BusinessServiceException
	 */
	public void processCancelSOInAcceptedForWS(String soId, SecurityContext securityContext) throws BusinessServiceException;
	/**
	 * Method process the cancel request for an accepted service order from OMS 
	 * @param String soId
	 * @param SecurityContext
	 * @return void
	 * @throws BusinessServiceException
	 */
	public void processCancelSOInActiveForWS(String soId, SecurityContext securityContext) throws BusinessServiceException;
	public Double getSOProjectBalance(String soId) throws BusinessServiceException;
	/**
	 * Method fetches unit no, order no, buyer id and so contact phone no for a given SO ID
	 * @param String soId
	 * @return String
	 * @throws BusinessServiceException
	 */
	public String getSoCustomReferencesWS(String soId);
	public ArrayList<LookupVO> getSubstatusDesc(List<Integer> id) throws BusinessServiceException;
	public ArrayList<LookupVO> getRescheduleReasonCodes(Integer roleId) throws BusinessServiceException;
	
	public ArrayList<LookupVO> getPermitTypes() throws BusinessServiceException;
	
	/**
	 * Method fetches task price for a particular taskId
	 * @param Integer taskId
	 * @return Map
	 * @throws BusinessServiceException,DataServiceException
	 */
	public Map getTaskPrice(Integer taskId) throws BusinessServiceException, DataServiceException;
	
	/**
	 * Method fetches timezone information for a so from so_hdr table 
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	
	public String getServiceLocTimeZone(String soId) throws DataServiceException;
	/**
	 * This method gets the routed providers for a specific SO and provider firm
	 * @param String soId, String vendorId, String resourceId, Boolean manageSoFlag
	 * @return List <ProviderResultVO> 
	 * throws DataServiceException
	 */
	public List <ProviderResultVO> getRoutedResourcesForFirm(String soId, String vendorId, String resourceId, Boolean manageSOFlag, ServiceOrder serviceOrder) 
	throws BusinessServiceException;
	
	public List <ProviderResultVO> getRoutedProviderListForFirm (String soId, String vendorId)throws BusinessServiceException;
	public List<SoAutoCloseDetailVo> getSoAutoCloseCompletionList(String soId) throws DataServiceException;
	
	public String  getUserName(Integer roleId,Integer resourceId) throws DataServiceException;

	
	public boolean determineAcceptability(boolean isIndividualOrder, ProcessResponse processResp, ServiceOrder soObj);
	public boolean determineAcceptabilityForMobile(boolean isIndividualOrder,String groupId,int wfStatus);
	public Double getBuyerMaxTransactionLimit(Integer resourceId,Integer buyerId) throws BusinessServiceException;	
	
	public List<PendingCancelHistoryVO> getPendingCancelHistory(String soId) throws DataServiceException;
	
	public PendingCancelPriceVO getPendingCancelBuyerDetails(String soId) throws DataServiceException;
	
	public PendingCancelPriceVO getPendingCancelBuyerPriceDetails(String soId) throws DataServiceException;
	
	public PendingCancelPriceVO getPendingCancelProviderDetails(String soId) throws DataServiceException;
	
	/**
	 * Get the response based on the response filter
	 * @param soId
	 * @param responseFilters
	 * @return
	 * @throws BusinessServiceException
	 */
	public ServiceOrder getServiceOrder(String soId,List<String> responseFilters) throws BusinessServiceException;
	
	/**
	 * 
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */

	public boolean isAuthorizedToViewSODetls(String soId,String providerId) throws BusinessServiceException;
	
	/**
	 * Get the service order details for API
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */

	public ServiceOrder getServiceOrderForAPI(String soId) throws BusinessServiceException;
	
	/**
	 * Get the spendLimit details for API
	 * @param soIdList
	 *        List<String>
	 * @return
	 * @throws BusinessServiceException
	 */
	public HashMap<String,ServiceOrderSpendLimitHistoryVO> getSpendLimitForAPI(List<String>soIdList
			) throws BusinessServiceException;
//SL 17504 Creating a method to update the sku indicator column of  so_workflow_controls table for edit serive order
	public void updateSkuIndicator(String soId) ;
	//SL 17504 Creating a method to fetch the sku indicator column of  so_workflow_controls table to identify the type of  serive order
	public Boolean fetchSkuIndicatorFromSoWorkFlowControl(String soID);
	public List<Integer> getRoutedProvidersForFirm(String soId,Integer vendorId)
			throws BusinessServiceException;

	//SL-15642: check whether so is car routed
	public boolean isCARroutedSO(String soId) throws BusinessServiceException;
	public boolean isAuthorizedToViewGroupSODetls(String groupId, String providerId)  throws BusinessServiceException;
	public ServiceOrder getServiceOrderGroup(String groupId,
			List<String> responseFilters) throws BusinessServiceException;
	
	/**
	 * Get the price details for API
	 * @param soId,infoLevelList
	 * 				List<Integer>
	 * @return
	 * @throws BusinessServiceException
	 */
	public ServiceOrder getServiceOrderPriceDetails(ServiceOrder serviceOrder,Integer infolevel) throws BusinessServiceException;
	public String getVendorBusinessName(Integer vendorId)throws BusinessServiceException;
	public String getRescheduleReason(Integer reasonCode)throws BusinessServiceException;
	//SL 18418 Changes for CAR routed Orders the substatus is not set as Exclusive
	public String getMethodOfRouting(String soID)throws BusinessServiceException;
	public List<PreCallHistory> getScheduleHistory(String soId,
			Integer acceptedVendorId);
	
	public ProviderFirmVO getAcceptedFirmDetails(Integer acceptedVendorId) throws BusinessServiceException;
	public ProviderFirmVO getFirmLevelDetailsSoId(String soId)throws BusinessServiceException;
	//Sl-18226 Response status tab
	public boolean checkTierRoute(String soId);

	public List<ServiceOrderTask> getActiveTasks(String soId);
	
	public RescheduleVO getRescheduleInfo(String soId);
	
	public RescheduleVO getBuyerRescheduleInfo(String soId);
	public Integer getBuyerId(String soId)throws BusinessServiceException;
	
	//For checking Non Funded feature for an SO
	public boolean checkNonFunded(String soId);
	
	/**Description:Checking non funded feature set is enabled for the buyer
	 * @param buyerId
	 * @return
	 * @throws BusinessServiceException 
	 */
	public boolean isNonFundedBuyer(Integer buyerId)throws BusinessServiceException;
	
	
	//SL-19050
	//For marking note as Read
	public void markSOAsRead(String noteId) throws BusinessServiceException;
	
	
	//For marking note as UnRead
	public void markSOAsUnRead(String noteId) throws BusinessServiceException;
	
	/**
	 * 
	 * @param invoiceIds
	 * @return
	 * @throws BusinessServiceException
	 * method to retrieve documents for invoiceids
	 */
	
	public List<InvoiceDocumentVO>  getInvoiceDocuments(List<Integer> invoiceIds)throws BusinessServiceException;
	
	/**
	 * get closure method
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public String getMethodOfClosure(String soId)throws BusinessServiceException;
	
	/**SL-20400 ->To find out the duplicate service orders with the given unit number and 
	order number combination in ServiceLive database for InHome Buyer (3000).*/	
	public ServiceOrder getDuplicateSOInHome(String customUnitNumber,String customOrderNumber) throws BusinessServiceException;
	
	/**
	 * Used to log request and response Duplicate SO's for InHome Buyer (3000).
	 * @param String request,String response, String buyerId
	 * @return Integer
	 */	
	public Integer logDuplicateSORequestResponse(String request,String response, Integer buyerId,String apiName) throws BusinessServiceException;
	
	/**SL 20853 to fetch additional_payment_encryption flag
	 * @return
	 * @throws BusinessServiceException
	 */
	public String getAdditionalPaymentEncryptFlag(String flag) throws BusinessServiceException;
	/**
	 * SL-21070
	 * Method fetches the lock_edit_ind of the so
	 * @param soId
	 * @return int lockEditInd
	 */
	public int getLockEditInd(String soId);
	
	/**@Description: Fetching original OrderId and warranty provider for the service order
	 * @param serviceOrderId
	 * @return
	 * @throws BusinessServiceException
	 */
	public SOWorkflowControlsVO getSoWorkflowControl(String serviceOrderId)throws BusinessServiceException;

	/**Priority 5B changes
	 * update invalid_model_serial_ind column in so_workflow_controls
	 * @param soId
	 * @param ind
	 * @throws BusinessServiceException
	 */
	public void updateModelSerialInd(String soId, String ind) throws BusinessServiceException;
	
	/**
	 * priority 5B changes
	 * get buyer first name & last name
	 * @param buyerId
	 * @return 
	 * @throws BusinessServiceException
	 */
	public String getBuyerName(Integer buyerResId) throws BusinessServiceException;
	
	/**
	 * priority 5B changes
	 * get the validation rules for the fields
	 * @param fields
	 * @return List<ValidationRulesVO>
	 * @throws BusinessServiceException
	 */
	public List<ValidationRulesVO> getValidationRules(List<String> fields) throws BusinessServiceException;
	
	/**
	 * priority 5B changes
	 * delete custom reference by type
	 * @param soId
	 * @param refType
	 * @throws BusinessServiceException
	 */
	public void deleteSOCustomReference(String soId, String refType) throws BusinessServiceException;
	/** Get Estimate Details
	 * @param soId
	 * @param estimationId
	 * @return
	 */
	public EstimateVO getEstimate(String soId, Integer estimationId) throws BusinessServiceException;
	
	
	public EstimateVO getEstimateMainDetails(String soId, Integer estimationId) throws BusinessServiceException;
	
	public void insertEstimateHistory(EstimateVO estimateVO) throws BusinessServiceException;


	/**
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
//	public boolean isLessThanSpendLimitLabour(String soId) throws BusinessServiceException;

	/**
	 * @param soId
	 * @param estimateId
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean validateEstimate(String soId, Integer estimateId) throws BusinessServiceException;
	
	
	/**
	 * @param soId
	 * @param estId
	 * @param status
	 * @param comments
	 * @param source
	 * @param modifiedBy
	 * @param customerName
	 * @return
	 * @throws BusinessServiceException
	 */
	 
	public void updateEstimateStatus(String soId, Integer estId, String status, 
			String comments,String source, String modifiedBy,String customerName) throws BusinessServiceException;
	/**
	 * Fetch the estimation details of an SO
	 * @param soId
	 * @param vendorId
	 * @return List<EstimateVO>
	 * @throws BusinessServiceException
	 */
	public List<EstimateVO> getEstimationDetails(String soId,Integer vendorId) throws BusinessServiceException;
	
	/** method for retrieving closed SO Details for provider.
	 * @param closedOrdersRequestVO
	 * @return
	 * @throws BusinessServiceException 
	 */
	public List<ClosedServiceOrderVO> getClosedServiceOrders(
			ClosedOrdersRequestVO closedOrdersRequestVO) throws BusinessServiceException;
	
	/** method for retrieving insurance details for firms.
	 * @param firmIdList
	 * @return Map
	 * @throws BusinessServiceException 
	 */
	public Map<String,List<LicensesAndCertVO>> getVendorInsuranceDetails(List<String> firmIdList) throws BusinessServiceException;
	 
	/** method for retrieving license details for firms.
	 * @param firmIdList
	 * @return Map
	 * @throws BusinessServiceException 
	 */
	public Map<String,List<LicensesAndCertVO>> getVendorLicenseDetails(List<String> firmIdList) throws BusinessServiceException;
	
	
	
	/** method for retrieving last closed order for firms.
	 * @param firmIdList
	 * @return Map
	 * @throws BusinessServiceException 
	 */
	public Map<String,LastClosedOrderVO> getLastClosedOrder(List<String> firmIdList) throws BusinessServiceException;
	
	/** method for retrieving the warranty and Policies & Procedures information for firms
	 * @param firmIdList
	 * @return Map
	 * @throws BusinessServiceException 
	 */
	public Map<String,WarrantyVO> getWarrantyPolicies(List<String> firmIdList) throws BusinessServiceException;
	
	/**
	 * Method to fetch the details of the requested firms
	 * @param firmDetailRequestVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public FirmDetailsResponseVO getFirmDetails(FirmDetailRequestVO firmDetailRequestVO)throws BusinessServiceException;
	/**
	 * Method to fetch the valid firms
	 * @param firmId
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<String> getValidProviderFirms(List<String> firmIds) throws BusinessServiceException;
	
	/**
	 * Fetch the routed provider details of an SO
	 * @param soId
	 * @return List<RoutedProvider>
	 * @throws BusinessServiceException
	 */
	public List<RoutedProvider> getRoutedProviders(String soId) throws BusinessServiceException;
	
	/**
	 * Fetch the sealed bid ind of SO
	 * @param soId
	 * @return Boolean
	 * @throws BusinessServiceException
 	*/
	public Boolean fetchSealedBidIndicator(String soId) throws BusinessServiceException;
	
	/**@Description : validate the reason code entered by the buyer for the reschedule
	 * @param reason
	 * @param validateErrors
	 * @return
	 * @throws BusinessServiceException
	 */
	public String validateReasonCodes(String reason,List<String> validateErrors)throws BusinessServiceException;
	
	
	/**
	 * get CustomRef value for given soId and customRef Key
	 * @param customRefTypeId
	 * @param soId
	 * @return
	 */
	public  ServiceOrderCustomRefVO getCustomReferenceValue(String customRefTypeId, String soId);
	
	/**@Description: Fetching the logo document id for the service order
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getLogoDocumentId(String soId)throws BusinessServiceException;
	
	
	public HashMap<String, Object> getDocumentDetails(String soId,String documentName)throws BusinessServiceException;
	
    public  List<ServiceDatetimeSlot> getSODateTimeSlots(String soId) throws DataServiceException;
    
    public  void updateAcceptedServiceDatetimeSlot(ServiceDatetimeSlot serviceDatetimeSlot) throws BusinessServiceException;
    
    public ServiceDatetimeSlot getSODateTimeSlot(String soId, Integer preferenceInd) throws DataServiceException;
	
    public void saveWarrantyHomeReasons(WarrantyHomeReasonInfoVO warrantyHomeReasonInfoVO) throws BusinessServiceException;
    
    public void updateCorelationIdWithSoId(HashMap<String, Object> corelationIdAndSoidMap) throws DataServiceException;
    

	public List<ProviderDetailWithSOAccepted> providerDetailWithSOAccepted(Integer buyerId, Integer noOfDays) throws BusinessServiceException;
	
	// SLT-896
	public String isUniqueSo(Integer buyerId, String uniqueId) throws DataServiceException;
	
	//SLT-2138: Push notification for cancelled SO
	public ServiceOrder getServiceOrderForPushNotfcn(String soId) throws BusinessServiceException;
	public Integer getPrimaryResourceIdForPushNotfcn(Integer acceptedVendorId ) throws BusinessServiceException;
	
	//SLT-4491
	public Integer getValidProvider(Integer providerId) throws BusinessServiceException;
	public List<SoLoggingVo2> getSoRescheduleLogDetailsAnyRoles(String soId) throws DataServiceException;
}
