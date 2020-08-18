package com.servicelive.orderfulfillment.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.newco.marketplace.exception.BusinessServiceException;
import com.servicelive.domain.lookup.LookupServiceType;
import com.servicelive.domain.pendingcancel.PendingCancelHistory;
import com.servicelive.domain.so.BuyerOrderRetailPrice;
import com.servicelive.domain.so.BuyerOrderSpecialtyAddOn;
import com.servicelive.domain.so.BuyerSkuAddon;
import com.servicelive.domain.so.BuyerSkuGroups;
import com.servicelive.domain.so.SORoutingRuleAssoc;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.marketplatform.common.vo.ProviderIdVO;
import com.servicelive.marketplatform.common.vo.SPNetHdrVO;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.ClientInvoiceOrder;
import com.servicelive.orderfulfillment.domain.SOAdditionalPaymentHistory;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.SOLogging;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.SOOnSiteVisit;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.TierRouteProviders;
import com.servicelive.orderfulfillment.vo.BuyerCallBackNotificationVO;
import com.servicelive.orderfulfillment.vo.InvoicePartsPricingVO;
import com.servicelive.orderfulfillment.vo.OutboundNotificationVO;

public interface IServiceOrderDao {
	public ServiceOrder getServiceOrder(String soId);
    public void setEntityManager(EntityManager em);
	public void save(SOElement element);
	public void update(SOElement element);
	
	public void delete( SOElement element );
	public void refresh(SOElement element);
	public SOLogging getRescheduleRequestLog(ServiceOrder so);
	//new
	public void deleteSoRoutedProviders(String soId);
	public void deleteSoCounterOffers(String soId);
	
    public <T extends SOElement> T getElement(Class<T> clazz, Object primaryKey);
    public SOGroup getSOGroup (String groupId);
    public List<BuyerOrderSpecialtyAddOn> getSpecialtyAddOnListBySpecialtyAndDivision(final String specialtyCode, final String divisionCode);
    public BuyerOrderSpecialtyAddOn getCallCollectSpecialtyAddOn(final String specialtyCode, final String stockNumber);
    public BuyerOrderSpecialtyAddOn getSpecialtyAddOn(final String specialtyCode, final String stockNumber);
    public LookupServiceType getServiceTypeTemplate(Integer serviceTypeTemplateId);
    public LookupServiceType getServiceTypeTemplate(Integer primaryNodeId, String description);
    public List<LookupServiceType> getServiceTypeTemplates();
    public List<ServiceOrder> getPendingServiceOrders();
    public ServiceOrder findServiceOrderByCustomReference(Long buyerId, Integer customRefTypeId, String customRefValue, String soId);
    public void saveSOLocationGIS(Integer locationId);
    
    public List<Integer> autoCloseProviderExclusionList(final Long buyerId);
    public List<Integer> autoCloseFirmExclusionList(final Long buyerId);
    public List<String> autoCloseCriteriaValue(final Long buyerId);
    public void autoCloseUpdation(String soId,String autoCloseStatus);
    public void pendingCancelUpdation(PendingCancelHistory pendingCancelHistory);
    public PendingCancelHistory getPendingCancelHistory(Integer roleId,String soId);
    public PendingCancelHistory getPendingCancelCriteria(Integer roleId,String soId);
    public void clearPendingCancelHist(String soId);  

    
    public void withdrawPendingCancel(String soId,String userType);
    public Integer getAutoCloseId(String soId);
    public void autoCloseRuleUpdation(Integer autoCloseId,List<String> autoCloseRuleStatus,String ruleValue, final Long buyerID);
    public BuyerOrderRetailPrice findByStoreNoAndSKU(final String storeNo, final String sku, final Long buyerID,final String defaultStroreNo) throws Exception;
    public void saveClientInvoice(ClientInvoiceOrder invoice);
    public ClientInvoiceOrder getClientInvoiceForIncident(String incidentId,Integer skuId);
    public Integer getTemplateId(String sku,Integer buyerId);
	public String getTemplateData(String templateName,Integer buyerId);
	public String getTemplateNameFromTemplateId(Integer templateId);
	public SOOnSiteVisit getIVRDetails(String soId);
	public List<SOLogging> getLog(ServiceOrder serviceOrder, String log);
	public List<SONote> getNote(ServiceOrder serviceOrder, String note);
	public Double getCancelAmount(Integer buyerId);
	//SL 15642 Method to get auto Accept feature of the buyer
	public boolean getCarFeatureOfBuyer(Integer i);
	//SL 15642 Method to get auto Accept feature of the buyer
	public boolean getAutoAcceptFeatureOfBuyer(Integer i);
	//SL 15642 Start Method to enter type of acceptance in so_workflow_controls table on associating a rule id with so
	public void setMethodOfAcceptanceOfSo(String soId,String methodOfAcceptance);
	//SL 15642 Start Method to enter type of routing in so_workflow_controls table on associating a rule id with so
	public void setMethodOfRoutingOfSo(String soId,String methodOfRouting);
	//SL 15642 Start Method to update entry into so routing rule buyer association table on finding a suitable rule
	public void updateRuleIdForSo(String soId,Integer ruleId);
	//SL 15642 End Method to update entry into so routing rule buyer association table on finding a suitable rule

	public List<Integer>getMandatoryCustRef(Integer integer);
	//SL-17781 Method to get the mandatory custom Ref for the buyer.

	public String getDefaultTemplate(String key);
	// SL-18007 to set the provider response reason while placing counter offer in the reason comment field while persisting so price details history
	public List<String> fetchCounterOfferProviderResponseReason(List<Long> counterOfferRespIdList);
	
	public Integer getFundingTypeIdForBuyer(Integer buyerId);
	//SL-18162 to get funding type id from DB instead of getting from Cache
	
	//SL-18226
	public List<TierRouteProviders> findProvidersForTierRouting(String soId,Integer noOfProvInCurentTier,Integer noOfProvInPreviousTiers,List<Integer> perfScores);
	public List<Integer> findRanks(String soId,Integer noOfProvInCurentTier,Integer noOfProvInPreviousTiers);
	public SPNHeader fetchSpnDetails(Integer spnId);
	public SPNetHdrVO fetchSpnInfo(Integer spnId);
	public List<ProviderIdVO> getCompletedDate(List<Integer> duplicateList, String criteriaLevel, Integer buyerId);
	public List<ProviderIdVO> getCompletedDateForSo(List<Integer> duplicateList, String criteriaLevel, Integer buyerId);
	public void deleteTierRoutedProviders(String soId);
	public void deleteNotEligibleProviders(Integer vendorId,String soId);
	public List<ServiceOrder> fetchChildOrders(String groupId);
	public String getCorrelationId();
	public void insertInHomeOutBoundNotification(OutboundNotificationVO notificationVO);
	public String getNotificationMessage(Integer wfStateId, Integer wfSubStatusId);
	public HashMap<String,String> getCustomRefValues(String soId);
	
	// Code Added for Jira SL-19728
	// For Sending emails specific to NON FUNDED buyer
	// As per new reqirements
	public Integer getEmailTemplateSpecificToBuyer(Integer buyerId,Integer templateId);
	
	//Jira SL-18825
	//To fetch custom reference with display_no_value indicator equal to 1
	public List<Integer> getCustRefwithEmptyValue(Integer buyerId) ;
	
	//JIRA SL-17042
	public void updateCompletedSOCount(Long buyerId,Long resourceId);
	/*Commenting code SL-20167 : updating purchase amount for canceled tasks 
	public void updatePurchaseAmount(String sku,
				BigDecimal purchaseAmount,String soId);*/
	
	// R12.0 Spint 2
	public void updateSOTripForReschedule(SOSchedule reschedule, String soId);
	// R12.0 Sprint 5 for updating the provider invoice parts prices during completion.
    public void updateProviderInvoicePartsPricing(InvoicePartsPricingVO invoicePartsPricingVO);
    // R12_1 To insert for HSR autoclose table
    public void insertSOInhomeAutoclose(String soId, String status, Integer emailInd, Integer noOfRetries, Date processAfterDate, String subStatus, Integer activeInd);
    // R12_1 To fetch time interval from application propertie stable
	public Integer getInhomeAutoCloseTimeInterval(String timeInterval);
	public Map<String, String> getAdjudicationConstants();
	// R12_1 To fetch reimbursement rate.
	public Double getReimbursementRate(Integer vendorId);
	public Map<String, String> getReimbursementModelConstants();
	
	public Map<String, String> getPartsBuyerPriceCalcValues();
	//SL_20647
	public void insertSoWorkflowControl(String pendingAutoClose, String soId);
	//SL-20436
	public SORoutingRuleAssoc getCARAssociation(String soId);
    public List<Long> getVendorIdsToExclude(String ruleId);
    
    //	SL-20853 --PCI II
    public void updateHistory(SOAdditionalPaymentHistory sOAdditionalPaymentHistory);
    /**
	 * @param originalOrderNumber
	 * @param originalUnitNumber
	 * @return
	 * @throws ServiceOrderException
	 */
	public String getInhomeWarrantyOrder(String originalOrderNumber,String originalUnitNumber) throws ServiceOrderException;
	
	/**@Description: Getting the firm Name from vendor_hdr
	 * @param acceptedFirm
	 * @return
	 * @throws ServiceOrderException
	 */
	public String getProviderFirmName(Long acceptedFirm) throws ServiceOrderException;
	
	/**@Description: validating firm status in (3,26,34)
	 * @param inhomeAcceptedProviderFirm
	 * @return
	 * @throws ServiceOrderException
	 */
	public boolean validateFirmStatus(Long inhomeAcceptedProviderFirm) throws ServiceOrderException ;
	
	/**
	 * @param spnId
	 * @param inhomeAcceptedFirm
	 * @return
	 * @throws ServiceOrderException
	 */
	public boolean validateFirmSpnCompliance(Integer spnId,Long inhomeAcceptedFirm) throws ServiceOrderException ;
	
	/**@Description: check for logs exits for No Matching CAR rule and Recall provider Not Available
	 * @param soId
	 * @return
	 * @throws ServiceOrderException
	 */
	public boolean checkNoMatchCarRuleOrRecallProviderExists(String soId)throws ServiceOrderException;
	
	/**@Description: check for logs exits Normal Draft Order Creation
	 * @param soId
	 * @return
	 * @throws ServiceOrderException
	 */
	public boolean checkDraftLogExists(String soId) throws ServiceOrderException;
	
	/**@Description: Removing Normal Draft Logging from the Order. 
	 * @param soId
	 * @throws ServiceOrderException
	 */
	public void deleteDraftLogging(String soId) throws ServiceOrderException;
	
	/**Description : Check the order is associated with a warranty Order
	 * @param serviceOrder
	 * @return
	 * @throws ServiceOrderException
	 */
	public boolean validateRecallOrder(ServiceOrder serviceOrder) throws ServiceOrderException;
    
    /**SL-21126
	 * Description :To fetch the value for an app key from the application_constants table
	 * @param appConstantKey
	 * @return
	 */
	public String getApplicationConstantValue(String appConstantKey);
	
	 /**SL-21126
		 * Description :To fetch the Routing Rule Vendor Id based on the CAR rule
		 * @param so
		 * @param vendorId
		 * @return String
		 */
	public Integer getRoutingRuleVendorId(Integer ruleId);
	 /**SL-21126
	 * Description :To fetch the SO with same location, service date 
	 * @param so
	 * @param vendorId
	 * @return String
	 */
	public List<ServiceOrder> getServiceOrdersForPrimarySo(ServiceOrder so,String validWfStates);
	/**SL-21126
	 * Description :To fetch the unique vendorId for posted orders
	 * @param so
	 * @param vendorId
	 * @return String
	 */
	public Integer getVendorIdForRoutedSo(String soId, Integer secondaryVendorId);
	/**
	 * @param providerIds
	 * @return
	 * SL-18979 AddrFetcher for SMSchanges
	 */
	public List<Long> getProviderIdsForSMS(List<Long> providerIds) throws BusinessServiceException;
	/**
	 * @param providerFirmIds
	 * @return
	 *  SL-18979 AddrFetcher for SMSchanges
	 */
	public List<Long> getFirmIdsForSMS(List<Long> providerFirmIds) throws BusinessServiceException;
	
	/**Owner  : Infosys
	 * module : Inhome Service Operation Notificatios
	 * purpose: Set the Job code from lu table if exists for the main service category in notification xml
	 * Jira : SL-21241
	 * @description: Set the Job code from lu table if exists for the main service category
	 * @param primarySkillCatId
	 * @param job
	 */
	public String getJobCodeForNodeId(Integer primarySkillCatId)throws BusinessServiceException;
	
	/**logging notifications to the relay services 
	 * @param soId
	 * @param string
	 * @param responseCode
	 * @throws BusinessServiceException
	 */
	public void loggingRelayServicesNotification(String soId, String request,
			int responseCode) throws BusinessServiceException;
	// SL-21455 to check whether the order is release by firm
	public boolean isReleaseByFirm(String soId) ;

	public List<Integer> getFirmIdList(String soId);
	
	public List<Integer> getFirmIdNotReleaseSO(String soId);

	public String getApplicationFlagValue(String appConstantKey);
	
	public String getVendorBNameList(List<Integer> uniqueNewVendorsList);
	
	public boolean isEstimationSO(String soId);
	
	public List<BuyerSkuAddon> getBuyerSkuAddOnList(Long catId, String primarySku, Long buyerId) throws BusinessServiceException;
	
	public List<BuyerSkuAddon> getBuyerSkuAddOnList(String commonAddon,  Long buyerId) throws BusinessServiceException;
	
	public BuyerSkuGroups getBuyerSkuGroups(Long SkuId) throws BusinessServiceException;
	
	public void insertCallBackNotification(BuyerCallBackNotificationVO buyerCallbackNotificationVO);
	
	public String  getSequenceId();
	public boolean checkBuyerCallbackExist(Long buyerId) throws ServiceOrderException;
	//public List<Integer> getBuyerRegisteredEventName(Long buyerId) throws ServiceOrderException;
	public void updateSohdrAndSlotSelectedValues(String soId,int preferenceInd)throws ServiceOrderException;
	
	public Double getRefundPostingFee(ServiceOrder serviceOrder, long businessTransactionId);
	
	//SLT-3835
	public String getCorrespondingActionId(String stateName);
	
	}
