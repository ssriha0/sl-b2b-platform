package com.newco.marketplace.business.iBusiness.mobile;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.api.beans.so.assignSO.AssignVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.RescheduleDetailsVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.RetrieveSODetailsMobileVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.ServiceLocationVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.SoDetailsVO;
import com.newco.marketplace.api.beans.so.viewDashboard.MobileDashboardVO;
import com.newco.marketplace.api.criteria.vo.SoSearchCriteriaVO;
import com.newco.marketplace.api.mobile.beans.vo.AcceptVO;
import com.newco.marketplace.api.mobile.beans.vo.ForgetUnamePwdVO;
import com.newco.marketplace.api.mobile.beans.vo.ProviderParamVO;
import com.newco.marketplace.api.mobile.beans.vo.RecievedOrdersCriteriaVO;
import com.newco.marketplace.api.mobile.beans.vo.RecievedOrdersVO;
import com.newco.marketplace.api.mobile.beans.vo.RequestBidVO;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.eligibleproviders.EligibleProvider;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateScheduleDetails.UpdateScheduleVO;
import com.newco.marketplace.business.iBusiness.buyerOutBoundNotification.MobileOutBoundNotificationVo;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.reasonCode.ReasonCodeVO;
import com.newco.marketplace.dto.vo.serviceorder.FiltersVO;
import com.newco.marketplace.dto.vo.serviceorder.ProblemResolutionSoVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceDatetimeSlot;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.exception.AssignOrderException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.mobile.SoResultsVO;
import com.newco.marketplace.vo.mobile.v2_0.MobileSORejectVO;
import com.newco.marketplace.vo.mobile.v2_0.SOAdvanceSearchCriteriaVO;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchCriteriaVO;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchResultsVO;
import com.newco.marketplace.vo.mobile.v2_0.SecQuestAnsRequestVO;
import com.newco.marketplace.vo.ordermanagement.so.RescheduleVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/21/10
* BO layer for 
processing phase 2 mobile APIs 
*
*/
/**
 * @author Reen_Jossy
 *
 */
public interface IMobileGenericBO {

	/**
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean checkIfSOIsActive(String soId) throws BusinessServiceException;
    //public String reportAProblem(SecurityContext securityContext,ReportProblemVO reportProblemVO)throws BusinessServiceException;
	/**
	 * @param firmId
	 * @param soId
	 * @param groupIndParam
	 * @return
	 */
	public List<ProviderResultVO> getRoutedProviders(String firmId,
			String soId, String groupIndParam) throws BusinessServiceException;
	/**
	 * @param soId
	 * @return
	 */
	public EligibleProvider getAssignedResource(String soId)throws BusinessServiceException;
	
	/**
	 * @param searchRequestVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public SOSearchResultsVO getServiceOrderSearchResults(SOSearchCriteriaVO searchRequestVO)throws BusinessServiceException;
	public String getProblemTypeDescription(int problemStatus, Integer reasonCode)throws BusinessServiceException;
	//public String addNoteForProblem(SecurityContext securityContext, ReportProblemVO reportProblemVO) throws BusinessServiceException;
	
	/**
	 * 
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 * method to fetch the so details 
	 */
	public ServiceOrder getSoDetails(String soId)throws BusinessServiceException;
	/**
	 * 
	 * @param securityContext
	 * @param problemVO
	 * @throws BusinessServiceException
	 * @throws AssignOrderException
	 * method to assign or reassign the SO to a provider
	 */
	public ProcessResponse assignOrReassignServiceOrder(SecurityContext securityContext,AssignVO assignVO)throws BusinessServiceException,AssignOrderException;
	
	/**
	 * @param soId
	 * @param groupId
	 * @param resourceId
	 * @param acceptByFirmInd
	 * @param securityContext
	 * @return
	 * @throws BusinessServiceException
	 */
	//public ProcessResponse executeSOAccept(AcceptVO acceptVo, SecurityContext securityContext) throws BusinessServiceException;
	
	/**
	 * @param bidVO
	 * @param securityContext
	 * @return
	 * @throws BusinessServiceException
	 */
	//public ProcessResponse executeSOBid(RequestBidVO bidVO, SecurityContext securityContext) throws BusinessServiceException;
	
	/**
	 * @param firmId
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<ProviderResultVO> getEligibleProviders(String firmId, String soId) throws BusinessServiceException;
	
	/**
	 * @param firmId
	 * @param groupId
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<ProviderResultVO> getEligibleProvidersForGroup(String firmId, String groupId) throws BusinessServiceException;
	/**
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean checkIfSOInProblem(String soId)throws BusinessServiceException;
	//public String resolveProblem (SecurityContext securityContext, ReportProblemVO reportProblemVO)throws BusinessServiceException;
	//public String addNoteForResolveProblem(SecurityContext securityContext,ReportProblemVO reportProblemVO)throws BusinessServiceException;
	public String getProblemTypeDescription(String soId)throws BusinessServiceException;;
	
    //CounterOffer
    public List<ServiceOrderSearchResultsVO> getServiceOrdersForGroup(String groupSoId) throws BusinessServiceException;
    public ServiceOrder getServiceOrder(String soId) throws BusinessServiceException;
    public boolean isCARroutedSO(String soId) throws BusinessServiceException;
    public boolean checkNonFunded(String soId) throws BusinessServiceException;
    //public ProcessResponse createCounterOffer(CounterOfferVO counterOfferVO, SecurityContext securityContext) throws BusinessServiceException;
    //withdraw counter offer
    //public ProcessResponse withdrawCounterOffer(CounterOfferVO withdrawCounterOfferVO, SecurityContext securityContext) throws BusinessServiceException;
	//R14.0 GetReasonCodes
    public List<String> getResonCodeType()throws BusinessServiceException;
	public List<ReasonCodeVO> getProviderReasoncodes(String reasonType)throws BusinessServiceException;
/*
	public ProcessResponse reAssignServiceOrder(SecurityContext securityContext, AssignVO assignVO)throws BusinessServiceException;*/
	/**
	 * 
	 * @param problemVO
	 * @return
	 * @throws BusinessServiceException
	 * Method to get the resources under a firm routed to the given SO
	 */
	public List<Integer> getRoutedResourcesUnderFirm(AssignVO assignVO)throws BusinessServiceException;
	/**
	 * 
	 * @param securityContext
	 * @param rescheduleVO
	 * @return
	 * method to submit reschedule as a provider
	 */
	//public ProcessResponse submitReschedule(SecurityContext securityContext, SORescheduleVO rescheduleVO);
	public boolean checkIfValidReason(String reasonCode)throws BusinessServiceException;
	//public String respondToReschedule(RescheduleVO rescheduleVo,SecurityContext context)throws BusinessServiceException;
	public RescheduleVO checkIfRescheduleRequestExists(RescheduleVO rescheduleVo)throws BusinessServiceException;
	public MobileOutBoundNotificationVo getRescheduleInformations(String soId) throws BusinessServiceException;
	public void invokeChoiceWebService(RescheduleVO rescheduleVo,MobileOutBoundNotificationVo buyerOutboundNotificationVO)throws BusinessServiceException;;

	/**
	 * @param updateScheduleVO
	 * @throws BusinessServiceException
	 */
	//public void updateScheduleDetails(UpdateScheduleVO updateScheduleVO)throws BusinessServiceException;
	
	/**
	 * Update the service order flag for a firm
	 * @param firmId
	 * @param soId
	 * @throws BusinessServiceException
	 */
	public void updateServiceOrderFlag(String firmId,String soId, Boolean followupFlag)throws BusinessServiceException;
	public String getServiceLocTimeZone(String soId)throws BusinessServiceException;
	/**
	 * @param rejectRequestVO
	 * @param securityContext
	 * @return
	 */
	/*public ProcessResponse rejectServiceOrder(MobileSORejectVO rejectRequestVO,
			SecurityContext securityContext)throws BusinessServiceException;*/
	/**
	 * @param rejectRequestVO
	 * @param soId
	 * @param groupId
	 * @param roleId 
	 * @return
	 */
	public ProcessResponse validateRejectRequest(
			MobileSORejectVO rejectRequestVO);
	/**
	 * @param soId
	 * @param source
	 * @return
	 */
	//public boolean validateServiceOrder(String soId, String source)  throws BusinessServiceException;
	public RequestBidVO fetchServiceOrderDetailsForBid(RequestBidVO bidVO)throws BusinessServiceException;
	//public boolean validateServiceOrderAssignment(String soId) throws BusinessServiceException;
	//Forget username password Service 1
	public ForgetUnamePwdVO  loadUserProfile(final ForgetUnamePwdVO forgetUnamePwdVO) throws BusinessServiceException;
	public ForgetUnamePwdVO getResponseForPasswordReset(ForgetUnamePwdVO forgetUnamePwdVO) throws BusinessServiceException;
	public ForgetUnamePwdVO getResponseForForgetUserNameForEmail(ForgetUnamePwdVO forgetUnamePwdVO) throws BusinessServiceException;
	public ForgetUnamePwdVO getResponseForForgetUserName(ForgetUnamePwdVO forgetUnamePwdVO) throws BusinessServiceException;
	/**
	 * @param soId
	 * @param custAvailableRespCode
	 * @return
	 */
	//public boolean validateServiceOrderConfirmedAppointment(String soId,
	//		Integer custAvailableRespCode) throws BusinessServiceException ;
	/**
	 * 
	 * @param firmId
	 * @param resourceId
	 * @param securityContext
	 * @return
	 * @throws BusinessServiceException
	 */
	//public MobileDashboardVO getDashboardDetails(String firmId, String resourceId, SecurityContext securityContext) throws BusinessServiceException;
	public boolean determineAcceptablity(String soId, String groupId)throws BusinessServiceException;
	
	/**
	 * @param acceptVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public ResultsCode validateSoAccept(AcceptVO acceptVO) throws BusinessServiceException;
	public boolean isGroupedOrderInEditMode(String groupId)throws BusinessServiceException;
	/**
	 * Delete Mobile filter flow
	 * @param resourceId
	 * @param filterId
	 * @throws BusinessServiceException
	 */
	public int deleteFilter(Integer resourceId, String filterId) throws BusinessServiceException;
	/**@param soStatusList 
	 * @Description: Getting Service Order status Id for the status Specified in List 
	 * @return  List<Integer>
	 * @throws BusinessServiceException
	 */
	public List<Integer> getServiceOrderStatus(List<String> soStatusList)throws BusinessServiceException;
	
	/**@Description: Fetching List of service orders available for providers
	 * @param providerParamVO
	 * @return List<ProviderSOSearchVO>
	 * @throws BusinessServiceException
	 */
	
	/**
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getServiceOrderStatus(String soId) throws BusinessServiceException;
	
	public SoResultsVO getProviderSOList(ProviderParamVO providerParamVO)throws BusinessServiceException;
	/**
	 * @param criteriaVO
	 * @return recievedOrdersCount
	 */
	public Integer getRecievedOrdersCount(RecievedOrdersCriteriaVO criteriaVO) throws BusinessServiceException;
	
	
	/**
	 * @param criteriaVO
	 * @return List<RecievedOrdersVO>
	 */
	public List<RecievedOrdersVO> getRecievedOrdersList(RecievedOrdersCriteriaVO criteriaVO) throws BusinessServiceException;

	/**@Description Fetching the Filter Details associated with a provider
	 * @param providerId
	 * @return Map<Integer, FiltersVO>
	 * @throws BusinessServiceException
	 */
	public Map<Integer, FiltersVO> getSearchFilterDetails(Integer providerId) throws BusinessServiceException;

	/**
	 * Method to get the firmId of resource passed
	 * @param providerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer validateProviderId(Integer providerId)throws BusinessServiceException;
	/**
	 * Method to verify the logged in user is authorized to view the so details considering the aspects of role and status
	 * @param detailsVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean isAuthorizedToViewSODetails(SoDetailsVO detailsVO)throws BusinessServiceException;
	/**
	 * Method to fetch the details of the SO for mobile v3.0
	 * @param detailsVO
	 * @return RetrieveSODetailsMobileVO
	 * @throws BusinessServiceException
	 * @throws ParseException 
	 */
	public RetrieveSODetailsMobileVO getServiceOrderDetails(SoDetailsVO detailsVO)throws BusinessServiceException, ParseException;
	/**
	 * @param searchRequestVO
	 * @return
	 */
	public SOSearchResultsVO getServiceOrderAdvanceSearchResults(
			SOAdvanceSearchCriteriaVO searchRequestVO)throws BusinessServiceException;
	/**
	 * method to get the wfstateId,routed resources,and assignement type of the SO
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public AssignVO getProviderSODetails(String soId)throws BusinessServiceException;
	
	public void saveFilter(FiltersVO filtersVO) throws BusinessServiceException;
	public boolean isFilterExists(Integer resourceId, String filterName) throws BusinessServiceException;
	
	/**@Description : Fetch Count of service order in different status.
	 * @param dashboardVO
	 * @return MobileDashboardVO
	 * @throws BusinessServiceException
	 */
	public MobileDashboardVO getDashboardCount(MobileDashboardVO dashboardVO)throws BusinessServiceException;
	/**
	 * Method to fetch the search criterias
	 * @param roleId
	 * @param resourceId
	 * @param vendorId
	 * @return
	 * @throws BusinessServiceException
	 */
	public SoSearchCriteriaVO getSearchCriteria(Integer roleId,Integer resourceId, Integer vendorId) throws BusinessServiceException;
	
	/**
	 * Method to check whether resource is authorized to view SO details in accepted,active,problem status
	 * @param detailsVO
	 * @return
	 * @throws DataServiceException
	 */
	public boolean isAuthorizedToViewBeyondPosted(SoDetailsVO detailsVO)throws BusinessServiceException;
	
	/**
	 * @param userName
	 * @param email
	 * @return
	 */
	public SecQuestAnsRequestVO getExistingUserProfileDetails(SecQuestAnsRequestVO secQuestAnsRequestVO) throws BusinessServiceException;
	/**
	 * @param secQuestAnsRequestVO
	 * @return
	 */
	public SecQuestAnsRequestVO validateAnsForForgotPassword(SecQuestAnsRequestVO secQuestAnsRequestVO)throws BusinessServiceException;
	
	/**
	 * @param secQuestAnsRequestVO
	 */
	public void resetMobileOauthToken(SecQuestAnsRequestVO secQuestAnsRequestVO)throws BusinessServiceException ;
	
	/**
	 * @param secQuestAnsRequestVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public SecQuestAnsRequestVO validateAnsForForgotUserName(SecQuestAnsRequestVO secQuestAnsRequestVO) throws BusinessServiceException;
	
	/**
	 * @param secQuestAnsRequestVO
	 * @return
	 */
	public SecQuestAnsRequestVO validateSecQuestAnsRequest(
			SecQuestAnsRequestVO secQuestAnsRequestVO) throws BusinessServiceException ;
	
	/**
	 * @param secQuestAnsRequestVO
	 * @return
	 */
	public SecQuestAnsRequestVO validateAdditionalVerification(
			SecQuestAnsRequestVO secQuestAnsRequestVO)throws BusinessServiceException ;

	/**
	 * @param soId
	 * @return ProblemResolutionSoVO
	 * @throws BusinessServiceException
	 */
	/*-----------------------------------------------------------------
	 * Get the problem details to display on resolution screen
	 * @param 		soId - ServiceOrder Id
	 * @returns 	ProblemResolutionSoVO
	 *-----------------------------------------------------------------*/
	ProblemResolutionSoVO getProblemDesc(String soId) throws BusinessServiceException ;
	
	/**
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	//public boolean isLessThanSpendLimitLabour(String soId) throws BusinessServiceException;
	
	public void sendBuyerOutBoundNotification(/*OrderFulfillmentResponse response,*/String soId,/*SOSchedule reschedule,*/String reason,String comment,RescheduleVO rescheduleVo);
	
	public RescheduleDetailsVO getSORescheduleDetails(String soId,String zipCode) throws BusinessServiceException;
	
	public List<ProviderResultVO> getCounteredResourceDetailsList(String soId, String firmId) throws BusinessServiceException;;
	
	/**
	 * B2C : method to fetch the estimate details 
	 * @param soId
	 * @param estimateId
	 * @return
	 * @throws BusinessServiceException
	 */
	public EstimateVO getEstimateDetails(String soId, Integer estimateId)throws BusinessServiceException;
	
	/**
	 * 
	 * @param soId
	 * @param estimationId
	 * @return boolean
	 * @throws BusinessServiceException
	 */
	
	
	public EstimateVO getEstimateByVendorAndResource(Map<String, Object> param) throws BusinessServiceException;
	
	public Boolean isEstimationIdExists(String soId, Integer estimationId) throws BusinessServiceException;
	
	/***@Description: This method will either add or update a an Estimate for the service order
	 * @param estimateVO
	 * @param estimationId
	 * @return
	 * @throws BusinessServiceException
	 */
	public EstimateVO saveEditEstimate(EstimateVO estimateVO,Integer estimationId) throws BusinessServiceException;
	
	/**
	 * Fetch the estimation details of an SO
	 * @param soId
	 * @param vendorId
	 * @return List<EstimateVO>
	 * @throws BusinessServiceException
	 */
	public List<EstimateVO> getEstimationDetails(String soId,Integer vendorId) throws BusinessServiceException;
	
	/**@Description : This method will fetch the status of estimate 
	 * @param soId
	 * @param estimationId
	 * @return
	 * @throws BusinessServiceException
	 */
	public String validateEstimateStatus(String soId, Integer estimationId) throws BusinessServiceException;
	
	public Map<Long, String> retrieveRoutedProvidersNameMap(String soId) throws BusinessServiceException;
	
	/**
	 * Fetch the sealed bid ind of SO
	 * @param soId
	 * @return Boolean
	 * @throws BusinessServiceException
 	*/
	public Boolean fetchSealedBidIndicator(String soId) throws BusinessServiceException;
	
	/** Returns service date time zone offset for an so
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getServiceDateTimeZoneOffset(String soId)throws BusinessServiceException;
	
	/**
	 * @param bidResourceId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getVendorId(Integer bidResourceId)throws BusinessServiceException;

	
	/**SL-21451: 
	 * @Description : Fetch Count of bid requests
	 * @param dashboardVO
	 * @return MobileDashboardVO
	 * @throws BusinessServiceException
	 */
	public Integer getBidRequestCount(MobileDashboardVO dashboardVO)throws BusinessServiceException;
	
	public Map<String,String> getOrderTypes(List<String> serviceOrderIds)throws BusinessServiceException;
	
	public Map<String,List<EstimateVO>> getEstimationList(RecievedOrdersCriteriaVO criteriaVO)throws BusinessServiceException;
	
	public boolean isvalidSoState(String soId) throws BusinessServiceException;

	public void updateSOEstimateAsDeclined(Map<String, Object> param);
	
	public void GMTToGivenTimeZoneForSlots(RetrieveSODetailsMobileVO detailsMobileVO);
	
	public void mapSlots(RetrieveSODetailsMobileVO detailsMobileVO,List<ServiceDatetimeSlot> serviceDatetimeSlot, String timeZone);
	
}
