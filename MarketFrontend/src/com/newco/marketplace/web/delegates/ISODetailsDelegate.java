package com.newco.marketplace.web.delegates;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newco.marketplace.dto.vo.ValidationRulesVO;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.api.beans.so.DepositionCodeDTO;
import com.newco.marketplace.api.mobile.beans.sodetails.InvoiceDocumentVO;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistory;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderUpsellBO;
import com.newco.marketplace.dto.vo.InitialPriceDetailsVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.LuProviderRespReasonVO;
import com.newco.marketplace.dto.vo.TermsAndConditionsVO;
import com.newco.marketplace.dto.vo.incident.AssociatedIncidentVO;
import com.newco.marketplace.dto.vo.logging.SoAutoCloseDetailVo;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.dto.vo.powerbuyer.PBFilterVO;
import com.newco.marketplace.dto.vo.price.PendingCancelPriceVO;
import com.newco.marketplace.dto.vo.provider.ProviderFirmVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.CounterOfferReasonsVO;
import com.newco.marketplace.dto.vo.serviceorder.PendingCancelHistoryVO;
import com.newco.marketplace.dto.vo.serviceorder.ProblemResolutionSoVO;
import com.newco.marketplace.dto.vo.serviceorder.ProviderInvoicePartsVO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.SOOnsiteVisitResultVO;
import com.newco.marketplace.dto.vo.serviceorder.SOOnsiteVisitVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceDatetimeSlot;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderPendingCancelHistory;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderStatusVO;
import com.newco.marketplace.dto.vo.so.order.ServiceOrderRescheduleVO;
import com.newco.marketplace.dto.vo.survey.SurveyVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeRescheduleVO;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.vo.mobile.v2_0.SOTripVO;
import com.newco.marketplace.web.dto.ConditionalOfferDTO;
import com.newco.marketplace.web.dto.IncreaseSpendLimitDTO;
import com.newco.marketplace.web.dto.ReleaseServiceOrderDTO;
import com.newco.marketplace.web.dto.RescheduleDTO;
import com.newco.marketplace.web.dto.ResponseStatusDTO;
import com.newco.marketplace.web.dto.ResponseStatusTabDTO;
import com.newco.marketplace.web.dto.RevisitNeededInfoDTO;
import com.newco.marketplace.web.dto.SOCancelDTO;
import com.newco.marketplace.web.dto.SOCompleteCloseDTO;
import com.newco.marketplace.web.dto.SOContactDTO;
import com.newco.marketplace.web.dto.SOLoggingDTO;
import com.newco.marketplace.web.dto.SOPartsDTO;
import com.newco.marketplace.web.dto.SOPendingCancelDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.ServiceOrderNoteDTO;
import com.newco.marketplace.web.dto.SoChangeDetailsDTO;
import com.newco.marketplace.web.dto.WFMBuyerQueueDTO;
import com.newco.marketplace.web.dto.ajax.SOQueueNoteDTO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.activitylog.domain.ActivityLog;
import com.newco.marketplace.dto.vo.logging.SoAutoCloseDetailVo;


public interface ISODetailsDelegate {

	public SurveyVO retrieveQuestions(SurveyVO surveyVO)
			throws DataServiceException;

	public String saveResponse(SurveyVO surveyVO)
			throws DataServiceException;
	
	public ProcessResponse serviceOrderAddNote(LoginCredentialVO lcv, ServiceOrderNoteDTO soNoteDTO, Integer resourceId) 
	throws BusinessServiceException;
	
	public List<ServiceOrderNote> serviceOrderGetNotes(ServiceOrderNoteDTO soNoteDTO) throws DataServiceException;
	public List<ServiceOrderNote> serviceOrderGetAllNotes(ServiceOrderNoteDTO soNoteDTO) throws DataServiceException;
	public List<ServiceOrderNote> serviceOrderGetDeletedNotes(ServiceOrderNoteDTO soNoteDTO) throws DataServiceException;
	/**
	 * get notes for group given groupId
	 * @param soNoteDTO
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<ServiceOrderNote> serviceOrderGetAllGroupNotes(ServiceOrderNoteDTO soNoteDTO) throws BusinessServiceException;

	public List<ServiceOrderNote> getAllChildNotes(String groupId);
	
	
	public ArrayList<LuProviderRespReasonVO> getRejectReasons( LuProviderRespReasonVO luReasonVO ) throws BusinessServiceException;
	
	public ArrayList<SoChangeDetailsDTO> getSOLogs(String serviceOrderId) throws DataServiceException;

	public ResponseStatusTabDTO getResponseStatusDto(String soId,  Integer wfStateId, Integer buyerId);
	public List<LookupVO> getCallStatusList();
	
	public ServiceOrderDTO getServiceOrder(String soId, Integer roleId, Integer resId) throws DataServiceException;
	
	
	
	public String serviceOrderAccept(String soId, String userName, Integer resourceId, Integer companyId,Integer termAndCond, boolean validate,String assignee);
	public ProcessResponse serviceOrderVoid(SOCancelDTO soCancelDto) throws BusinessServiceException;
	public ProcessResponse serviceOrderClose(Integer buyerId, String serviceOrderId, SOCompleteCloseDTO soCloseDto) throws BusinessServiceException;
	
	public ArrayList queryListSoProblem() throws BusinessServiceException;
    public ArrayList queryListSoProblemStatus()throws BusinessServiceException;
	public String reportProblem(String strSoId, int intSubStatusId, String strComment, int intEntityId, int intRoleType,  String pbDesc,   String loggedInUser)
	throws BusinessServiceException;
	public String reportResolution(String strSoId, int intSubStatusId, String strComment, int intEntityId, int intRoleType, String strPbDesc, String strPbDetails, String strLoggedInUser)
	throws BusinessServiceException;
	public ProblemResolutionSoVO getProblemDesc(String soId) throws BusinessServiceException;
	public ProcessResponse serviceOrderCancel(SOCancelDTO soCancelDto) throws BusinessServiceException;
	public ProcessResponse serviceOrderPendingCancel(SOPendingCancelDTO soPendingCancelDto) throws BusinessServiceException;
	public ProcessResponse updateMktMakerComments(ResponseStatusDTO responseStatusDto);
	public ProcessResponse acceptConditionalOffer(String soId, Integer resourceId, Integer vendorId, Integer resReasonId, 
    		Date startDate, Date endDate, String startTime, String endTime, Double spendLimit, Integer buyerId);
	public ProcessResponse completeSO(SOCompleteCloseDTO soCompDto) throws BusinessServiceException;
	public ProcessResponse createProviderConditionalOffer(ConditionalOfferDTO conditionalOfferDTO);
	public ProcessResponse increaseSpendLimit(IncreaseSpendLimitDTO soIncSpendLimitDto) throws BusinessServiceException;
	public ProcessResponse withDrawCondAcceptance(RoutedProvider routedProviders)throws BusinessServiceException;
	public ArrayList getSurveyResults(SurveyVO surveyVO);
	
		
	public boolean hasCondOfferPending(String soId, Integer routedResourceId);
		
	public ArrayList<ServiceOrderStatusVO> getSubStatusDetails(Integer statusId, Integer roleId);
	
	public ProcessResponse updateSOSubStatus(String serviceOrderId, Integer subStatusId, SecurityContext context) throws BusinessServiceException;
	public ProcessResponse updateSOCustomReference(String soId, String referenceType, String referenceValue, String oldReferenceValue, SecurityContext securityContext)throws BusinessServiceException;
	public ArrayList<LookupVO> getShippingCarrier() throws BusinessServiceException;
	public String getAssignmentType(String soId) throws BusinessServiceException;
	public List <ProviderResultVO> getRoutedProviderListForFirm (String soId, String vendorId)throws BusinessServiceException;
	public ProcessResponse requestRescheduleSO(RescheduleDTO reschedule);
	public ServiceOrderRescheduleVO getRescheduleRequestInfo(String serviceOrderId);
	public ProcessResponse respondToRescheduleRequest(RescheduleDTO reschedule);
	public ProcessResponse cancelRescheduleRequest(RescheduleDTO reschedule);
	public TermsAndConditionsVO  getAcceptServiceOrderTermsAndCond(String acceptTermsandCond ) throws BusinessServiceException;
	public ProcessResponse releaseServiceOrder(ReleaseServiceOrderDTO release);
	public ProcessResponse editCompletionRecordForSo(String soId, SecurityContext ctx) throws BusinessServiceException;
    public List<SOOnsiteVisitVO> getTimeOnSiteResults(String soId)	throws BusinessServiceException;
    // R12.0 Sprint 3 : Time On Site Frontend changes to incorporate trip.
    public List<SOOnsiteVisitResultVO> getTimeOnSiteRecords(String soId) throws BusinessServiceException;
    // R12.0 Sprint 3 : Method to fetch Time On Site Reasons
    public List<String> fetchTimeOnSiteReasons() throws BusinessServiceException;
    public void UpdateTimeOnSiteRow(SOOnsiteVisitVO soOnsiteVisitVO) throws BusinessServiceException;
    public void  InsertVisitResult(SOOnsiteVisitVO soOnsiteVisitVO) throws BusinessServiceException;
	public Contact getVisitResourceName(Integer resourceId)	throws BusinessServiceException;
	public ArrayList<SOContactDTO> getRoutedResources(String soId) throws BusinessServiceException;
	public ProcessResponse saveReassignSO(SOLoggingDTO soLoggingDTO, String reassignReason) throws BusinessServiceException;
	public ProcessResponse updateSOPartsShippingInfo(String serviceOrderId, List<SOPartsDTO> shippingInfo, SecurityContext sc) throws BusinessServiceException;
	public int updateCompleteIndicator(SOQueueNoteDTO sOQueueNoteDTO)  throws BusinessServiceException;
	public int updateRequeueDateTime(SOQueueNoteDTO sOQueueNoteDTO)  throws BusinessServiceException;
	public int insertNewCallBackQueue(SOQueueNoteDTO sOQueueNoteDTO) throws BusinessServiceException;
	public boolean isMaxFollowUpCountReached(SOQueueNoteDTO sOQueueNoteDTO) throws BusinessServiceException;
	/*
	 * accept grouped Order
	 */
	public String groupedServiceOrderAccept(String soId, String userName,Integer resourceId,
			Integer companyId, Integer termAndCond,boolean validate,String assignee)throws BusinessServiceException ;

	/**
	 * Process the conditional offer for a group of orders provided by the service provider 
	 * 
	 * @param conditionalOffer
	 * @return the ProcessResponse
	 */
	public ProcessResponse createProviderConditionalOfferForGroup (
			ConditionalOfferDTO conditionalOffer);
	
	/**
	 * get provider Response status for given groupId 
	 * @param groupId
	 * @param wfStateId
	 * @param buyerId
	 * @return
	 */
	public ResponseStatusTabDTO getResponseStatusDtoForGroup(String groupId,
			Integer wfStateId, Integer buyerId); 
	
	/**
	 * Accepts the conditional offer for the group
	 * 
	 * @param groupId
	 * @param resourceId
	 * @param vendorId
	 * @param resReasonId
	 * @param startDate
	 * @param endDate
	 * @param startTime
	 * @param endTime
	 * @param spendLimit
	 * @param buyerId
	 * @return ProcessResponse
	 */
	public ProcessResponse acceptConditionalOfferForGroup(String groupId, Integer resourceId, Integer vendorId, Integer resReasonId, 
    		Date startDate, Date endDate, String startTime, String endTime, Double spendLimit, Integer buyerId);
	
	/**
	 * get first child order in group
	 * @param groupId
	 * @return
	 */
	public String getFirstChildInGroup(String groupId);
	
	/**
	 * get first child order in original group
	 * @param groupId
	 * @return
	 */
	public String getFirstChildInOrigGroup(String origGroupId);
	
	 /**
	 * Returns the list of the queues associated with a particular buyerId and soId
	 * @param buyerId The buyer_id
	 * @param soId The service order id
	 * @return the ArrayList of value object 'WFMBuyerQueueVO'
	 * @throws BusinessServiceException
	 */
	public List<WFMBuyerQueueDTO> getWFMQueueDetails(String buyerId,String soId) throws BusinessServiceException;
	
	
	public List<WFMBuyerQueueDTO> getWFMQueueAndTasks(String buyerId, String soId) throws BusinessServiceException;
	
	public List<WFMBuyerQueueDTO> getWFMQueueAndTasks(String buyerId, String soId, String groupId) throws BusinessServiceException;
	
	public WFMBuyerQueueDTO getWFMCallBackQueueAndTasks(String buyerId) throws BusinessServiceException;
	
	public IServiceOrderUpsellBO getUpsellBO();
	/**
	 * Returns the associated incidents
	 * @param soId
	 * @return List<AssociatedIncidentVO> associatedIncidents
	 * @throws BusinessServiceException
	 */
	public List<AssociatedIncidentVO> getAssociatedIncidents(String soId) throws BusinessServiceException;
	/**
	 * Returns all child So Ids associated with the group order
	 * @param groupId
	 * @return List <String> soIds
	 * @throws BusinessServiceException
	 */
	public List<String> getServiceOrderIDsForGroup(String groupId) throws BusinessServiceException;
	/**
	 * Returns first soId associated with a group order
	 * @param groupId
	 * @return <String> soId
	 * @throws BusinessServiceException
	 */	
	public String getFirstSoIdForGroup(String groupId) throws BusinessServiceException;

	public PBFilterVO getDestinationTabForSO(String soId) throws BusinessServiceException;

	public String getGroupId(String soId) throws BusinessServiceException;
	/**
	 * Method to fetch the service order status and completed date 
	 * @param String soId
	 * @return ServiceOrder 
	 * @throws DelegateException
	 */
	public ServiceOrder getServiceOrderStatusAndCompletdDate(String soId ) throws DelegateException;
	
	public boolean validateFeature(Integer buyerId, String feature);
	
	public boolean validateServiceOrderFeature(String soId, String feature);
	
	public void setServiceOrderFeature(String soId, String feature);
	
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
	 * get the remaining time left for provider to wait to Accept So
	 * @param soId
	 * @param resourceId
	 * @return
	 * @throws BusinessServiceException
	 */
	public int getTheRemainingTimeToAcceptGrpOrder(String groupId, Integer resourceId) throws BusinessServiceException;


	public ProcessResponse processCreateBid(String soId, Integer resourceId,
			Integer vendorId, Date bidDate, BigDecimal totalLabor, BigDecimal totalHours, 
			BigDecimal partsMaterials, Date bidExpirationDate,  String bidExpirationTime,
			Date newServiceDateRangeTo, Date newServiceDateRangeFrom,
			String newServiceStartTime, String newServiceEndTime,
			String comment, BigDecimal totalLaborParts, SecurityContext securityContext);
	
	public int getBidNoteCount(String soID);
	public int getNewBidNoteCount(String soID);
	public int getNewBidCount(String soID);
	
	public ActivityLog getMostRecentCounterOffer(String soID, Integer providerResourceId);
	public void markBidAsReadByBuyer(Long bidId, String reason, String userId);
	public void markBidAsRequiringFollowUpByBuyer(Long bidId, String reason,String userId);
	
	public void markPostAsReadByBuyer(Long postId, String associationType, String reason, String userId);
	public void markPostAsRequiringFollowUpByBuyer(Long postId, String associationType, String reason,String userId);
	
	/**
	 * Method gets the reasons list for the selected counter offer condition
	 * @param providerRespId
	 * @return List<CounterOfferReasonsVO>
	 * @throws DelegatException
	 */
	public List<CounterOfferReasonsVO> getReasonsForSelectedCounterOffer(int providerRespId) throws DelegateException;
	
	public void sendEmailForNoteOrQuestion(String emailTo,  String soID, String soTitle, String message, String roleInd);

	public List<ActivityLog> getAllBidsForOrder(String soId);
	
	public ArrayList<LookupVO> getSubstatusDesc(List<Integer> id) throws BusinessServiceException;
	
	public ArrayList<LookupVO> getRescheduleReasonCodes(Integer roleId)	throws BusinessServiceException;
	
	public ArrayList<LookupVO> getPermitTypes() throws BusinessServiceException;
	public List<SoAutoCloseDetailVo> getSoAutoCloseCompletionList(String soId) throws DataServiceException;
	
	public String  getUserName(Integer roleId,Integer ResourceId) throws DataServiceException;
	
	public List<PendingCancelHistoryVO> getPendingCancelHistory(String soId) throws DataServiceException;
	
	public PendingCancelPriceVO getPendingCancelBuyerDetails(String soId) throws DataServiceException;
	
	public PendingCancelPriceVO getPendingCancelBuyerPriceDetails(String soId) throws DataServiceException;
	
	public PendingCancelPriceVO getPendingCancelProviderDetails(String soId) throws DataServiceException;

	public Double getCancelFeeForBuyer(Integer buyerId);
	/**
	 * This method retrieves the count of document types for a buyer
	 * @return Integer count
	 */
	public Integer retrieveDocTypesCountByBuyerId(Integer buyerId,Integer source) throws BusinessServiceException;

	public ArrayList<LuProviderRespReasonVO> getReleaseReasonCodes(LuProviderRespReasonVO luReasonVO)throws BusinessServiceException;
	//SL 18418 Changes for CAR routed Orders the substatus is not set as Exclusive
	//Calling method when the so is in posted state
	public String  getMethodOfRouting(String soId)throws BusinessServiceException;

	public List<PreCallHistory> getScheduleHistory(String soId,
			Integer acceptedVendorId);

	public ProviderFirmVO getAcceptedFirmDetails(String soId);

	public Integer getBuyerId(String soId);
	
	//For checking Non Funded feature for an SO
	public boolean checkNonFunded(String soId);
	
	//SL-19050
	//For marking note as Read
	public void markSOAsRead(String noteId) throws DataServiceException;
	
	//SL-19050
	//For marking note as UnRead
		public void markSOAsUnRead(String noteId) throws DataServiceException;

	//SL-19820
	public String getGroupedId(String soId) throws BusinessServiceException;

	//get the remaining time left for firm to wait to Accept SO
	public int getTheRemainingTimeToAcceptGrpOrderFirm(String groupId,
			Integer companyId)throws BusinessServiceException;
	
	//check whether SO is CAR routed
	public boolean isCARroutedSO(String soId) throws BusinessServiceException;
	
	/**
	 * R12.0: Sprint 3 - Method to fetch trip details.
	 * @param soId
	 * @param soTripNo
	 * @return
	 * @throws BusinessServiceException
	 */
	public RevisitNeededInfoDTO getTripRevisitDetails(String soId,Integer soTripNo) throws BusinessServiceException;
	
	
	public String fetchUserName(String resourceId) throws BusinessServiceException;
	
	public void insertNewTripWeb(SOTripVO trip) throws BusinessServiceException;
	
	public void updateTripWeb(SOTripVO trip) throws BusinessServiceException;
	
	public String findlatestTripStatus(String soId, int tripNo) throws BusinessServiceException;
	
	public Integer fetchLatestTripSO(String soId) throws BusinessServiceException;
	
	public Long findLatestOnsiteVisitEntry(String soId) throws BusinessServiceException;
	
	public boolean isRevisitNeededTrip(String soId, Integer currentTripNo) throws BusinessServiceException;
	
	public String getTimeZone(Date modifiedDate, String timeZone);
	
	public String getTimeZone(String modifiedDate, String format, String timeZone);
	
	
	public void  setInvoiceDocuments(SOCompleteCloseDTO soCompleteCloseDTO);
//R12_0 updating substatus to empty
	public void updateSubStatusOfSO(String soId, Integer substatusValue)throws BusinessServiceException;

	//autoadjudication : method to fetch the closure method
	public String getMethodOfClosure(String soId)throws BusinessServiceException;
	
	/**
	 * priority 5B changes
	 * Insert order history 
	 * @param soLoggingVO
	 * @return 
	 * @throws BusinessServiceException
	 */
	public void insertSoLogging(SoLoggingVo soLoggingVO) throws BusinessServiceException;

	/**
	 * priority 5B changes
	 * Insert a new row in so_custom_reference
	 * @param soLoggingVO
	 * @return 
	 * @throws BusinessServiceException
	 */
	public void insertSOCustomReference(String soId, String refType,
			String refVal, SecurityContext securityContext) throws BusinessServiceException;

	/**
	 * priority 5B changes
	 * get buyer first name & last name
	 * @param buyerResId
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
	public List<ValidationRulesVO> getValidationRules(List<String> fields)throws BusinessServiceException;

	/**
	 * priority 5B changes
	 * update invalid_model_serial_ind in so_workflow_controls
	 * @param soId
	 * @return ind
	 * @throws BusinessServiceException
	 */
	public void updateModelSerialInd(String soId, String ind) throws BusinessServiceException;

	/**
	 * priority 5B changes
	 * get CustomReferences for SO
	 * @param soId
	 * @param soDTO
	 * @return ServiceOrderDTO
	 * @throws BusinessServiceException
	 */
	public ServiceOrderDTO getCustomReferences(String soId, ServiceOrderDTO soDTO) throws BusinessServiceException;

	/**
	 * priority 5B changes
	 * delete custom reference by type
	 * @param soId
	 * @param refType
	 * @throws BusinessServiceException
	 */
	public void deleteSOCustomReference(String soId, String refType) throws BusinessServiceException;
	/**
	 * SL-21070
	 * Method fetches the lock_edit_ind of the so
	 * @param soId
	 * @return int lockEditInd
	 */
	public int getLockEditInd(String soId);
	/**
	 * This method is used to fetch spend limit details of so
	 * 
	 * @param soid
	 * @return InitialPriceDetailsVO
	 * @throws BusinessServiceException
	 */
	public InitialPriceDetailsVO getInitialPrice(String soId) throws BusinessServiceException;
	
	public  List<ServiceDatetimeSlot> getSODateTimeSlots(String soId) throws DataServiceException ;
	
	public  void updateAcceptedServiceDatetimeSlot(ServiceDatetimeSlot serviceDatetimeSlot) throws BusinessServiceException;

	List<DepositionCodeDTO> getAllDepositionCodes();
	boolean insertOrUpdateDepositionCode(String soID, String depositionCode);
}
