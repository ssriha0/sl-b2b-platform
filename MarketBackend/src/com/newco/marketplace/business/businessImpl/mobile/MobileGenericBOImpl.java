package com.newco.marketplace.business.businessImpl.mobile;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.assignSO.AssignVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.AppointmentVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.DocumentDetailsVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.DocumentsVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.LatestTripVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.NoteVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.NotesVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.PartVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.PickUpLocationVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.RescheduleDetailsVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.RetrieveSODetailsMobileVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.SOTripsVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.ServiceLocationVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.ServiceOrderDetailsVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.SoDetailsVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.SupportNoteVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.TripVO;
import com.newco.marketplace.api.beans.so.submitReschedule.SORescheduleVO;
import com.newco.marketplace.api.beans.so.viewDashboard.MobileDashboardVO;
import com.newco.marketplace.api.criteria.vo.SoSearchCriteriaVO;
import com.newco.marketplace.api.criteria.vo.SoStatusVO;
import com.newco.marketplace.api.mobile.beans.vo.AcceptVO;
import com.newco.marketplace.api.mobile.beans.vo.ForgetUnamePwdVO;
import com.newco.marketplace.api.mobile.beans.vo.ProviderParamVO;
import com.newco.marketplace.api.mobile.beans.vo.RecievedOrdersCriteriaVO;
import com.newco.marketplace.api.mobile.beans.vo.RecievedOrdersVO;
import com.newco.marketplace.api.mobile.beans.vo.RequestBidVO;
import com.newco.marketplace.api.mobile.beans.vo.UserDetailVO;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.inhomeOutBoundNotification.NotificationServiceImpl;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.eligibleproviders.EligibleProvider;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.rejectSO.Resource;
import com.newco.marketplace.business.iBusiness.buyerOutBoundNotification.MobileOutBoundNotificationVo;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOManagementBO;
import com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO;
import com.newco.marketplace.business.iBusiness.provider.IForgotUsernameBO;
import com.newco.marketplace.business.iBusiness.provider.IResourceBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.business.iBusiness.usermanagement.UserManagementBO;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestMsgBody;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestOrder;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestOrders;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestReschedInformation;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestRescheduleInfo;
import com.newco.marketplace.buyeroutboundnotification.constatns.BuyerOutBoundConstants;
import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundNotificationJMSService;
import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundNotificationService;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundFailOverVO;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundNotificationVO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.LuProviderRespReasonVO;
import com.newco.marketplace.dto.vo.dateTimeSlots.ScheduleServiceSlot;
import com.newco.marketplace.dto.vo.dateTimeSlots.ServiceDateTimeSlots;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.reasonCode.ReasonCodeVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.FilterCriteriaVO;
import com.newco.marketplace.dto.vo.serviceorder.FiltersVO;
import com.newco.marketplace.dto.vo.serviceorder.ProblemResolutionSoVO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.ServiceDatetimeSlot;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderStatusVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSubStatusVO;
import com.newco.marketplace.exception.AssignOrderException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.persistence.iDao.mobile.IMobileGenericDao;
import com.newco.marketplace.persistence.iDao.mobile.IMobileSOManagementDao;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;
import com.newco.marketplace.relayservicesnotification.service.IRelayServiceNotification;
import com.newco.marketplace.util.EsapiUtility;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.util.StemmingSearch;
import com.newco.marketplace.util.TimeChangeUtil;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.vo.mobile.ProviderSOSearchVO;
import com.newco.marketplace.vo.mobile.SOStatusVO;
import com.newco.marketplace.vo.mobile.SoResultsVO;
import com.newco.marketplace.vo.mobile.v2_0.MobileSORejectVO;
import com.newco.marketplace.vo.mobile.v2_0.ResourceIds;
import com.newco.marketplace.vo.mobile.v2_0.SOAdvanceSearchCriteriaVO;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchCriteriaVO;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchResultVO;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchResultsVO;
import com.newco.marketplace.vo.mobile.v2_0.SecQuestAnsRequestVO;
import com.newco.marketplace.vo.ordermanagement.so.RescheduleVO;
import com.newco.marketplace.vo.provider.LostUsernameVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.ordermanagement.services.OrderManagementService;
/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/21/10
 * BO layer for processing phase 2 mobile APIs 
 */
public class MobileGenericBOImpl implements  IMobileGenericBO {

	private static final Integer CONTACT_TYPE_ID_PRIMARY = 10;
	private static final Integer ENTITY_TYPE_ID_PROVIDER = 20;
	private IServiceOrderBO serviceOrderBo;
	private IOrderGroupBO orderGroupBo;
	private IResourceBO resourceBO;
	private ILookupBO lookupBO;
	private IForgotUsernameBO forgotUsernameBO;
	private UserManagementBO userManagementBO;
	private OrderManagementService managementService;
	private IMobileGenericDao mobileGenericDao;
	/*private OFHelper ofHelper;
	private OFMapper ofMapper;*/
	private NotificationServiceImpl notificationService;
	private IBuyerOutBoundNotificationService  buyerOutBoundNotificationService;
	private IBuyerOutBoundNotificationJMSService buyerOutBoundNotificationJMSService;
	private IMobileSOManagementBO mobileSOManagementBO;
	private IMobileSOManagementDao mobileSOManagementDao;
	private ServiceOrderDao serviceOrderDao;
	//SL-21848
	private IRelayServiceNotification relayNotificationService;
	private IMobileSOActionsBO mobileSOActionsBO;

	//private ISurveyBO surveyBO;
	//private IDashboardDisplayBO dashboardDisplay;
	private static final Logger LOGGER = Logger.getLogger(MobileGenericBOImpl.class);
	
	
	public boolean checkIfSOIsActive(String soId)throws BusinessServiceException {
		boolean isValidStatus = false;
		try{
			isValidStatus = mobileGenericDao.checkIfSOIsActive(soId);
		}catch (Exception e) {
			LOGGER.error("Exception Occured in MobileGenericBOImpl-->checkIfSOIsActiveAccepted()");
			throw new BusinessServiceException(e.getMessage());
		}
		return isValidStatus;
	}
	/*public String reportAProblem(SecurityContext securityContext, ReportProblemVO  reportProblemVO) throws BusinessServiceException {
		OrderFulfillmentResponse response =null;
		ProcessResponse pr = null;
		String returnVal= null;
		OrderFulfillmentRequest request = new OrderFulfillmentRequest();
		SignalType problemSignal = securityContext.isBuyer() ? SignalType.BUYER_REPORT_PROBLEM : SignalType.PROVIDER_REPORT_PROBLEM;
		request.setIdentification(ofMapper.createOFIdentityFromSecurityContext(securityContext));
		request.addMiscParameter(OrderfulfillmentConstants.PVKEY_PROBLEM_COMMENT, reportProblemVO.getProblemDescriptionComments());
		request.addMiscParameter(OrderfulfillmentConstants.PVKEY_PROBLEM_DESC, reportProblemVO.getProblemReasonCodeDescription());

		try{
			response=ofHelper.runOrderFulfillmentProcess(reportProblemVO.getSoId(), problemSignal, request);
			pr = ofMapper.mapProcessResponse(response);
			List<String> arrMsgList = pr.getMessages();
			for(String msg:arrMsgList){
				returnVal = pr.getCode() + msg + System.getProperty("line.separator","\n");
			}
			LOGGER.info("reportProblemResolution validation messages :" + returnVal);
		}catch (Exception e) {
			LOGGER.error("Exception in executing signal for reporting problem"+ "Error Messages:"+ returnVal);
			throw new BusinessServiceException(returnVal);
		}

		return returnVal;
	}*/


	/**
	 * @param firmId
	 * @param soId
	 * @param groupIndParam
	 * @return
	 */
	public List<ProviderResultVO> getRoutedProviders(String firmId,
			String soId, String groupIndParam) throws BusinessServiceException {
		List<ProviderResultVO> providers = null;
		try{
			if (StringUtils.isNotBlank(groupIndParam) && PublicAPIConstant.GROUPED_SO_IND.equalsIgnoreCase(groupIndParam)) {
				providers = managementService.getEligibleProvidersForGroup(firmId,soId);
			}else{
				providers = managementService.getEligibleProviders(firmId, soId);
			}
			sortProviderList(providers);
		}
		catch(Exception e){
			throw new BusinessServiceException(e);
		}
		return providers;

	}
	/**
	 * Method to sort the list of providers based on the distance from service location.
	 * @param providerDetails : List of {@link ProviderResultVO}
	 * **/
	private void sortProviderList(List<ProviderResultVO> providers) {
		Collections.sort(providers, new Comparator<Object>(){
			public int compare(final Object obj1, final Object obj2){
				final ProviderResultVO provider1 = (ProviderResultVO)obj1;
				final ProviderResultVO provider2 = (ProviderResultVO)obj2;
				final double distance1 = provider1.getDistanceFromBuyer();
				final double distance2 = provider2.getDistanceFromBuyer();
				if(distance1 < distance2){
					return -1;
				}
				else{
					return (distance1 > distance2)? 1 : 0;
				}
			}
		});
	}

	/**
	 * @param soId
	 * @return
	 * to get the assigned resource
	 */
	public EligibleProvider getAssignedResource(String soId) throws BusinessServiceException{
		EligibleProvider provider =null;
		try{
			provider = managementService.getAssignedResource(soId);
		}
		catch(Exception e){
			LOGGER.error("Exception inside getAssignedResource() inside MobileGenericBOImpl.java : "+e.getMessage());
			throw new BusinessServiceException(e);
		}
		return provider;
	}

	public String getProblemTypeDescription(int problemStatus,Integer reasonCode)throws BusinessServiceException {
		List<ServiceOrderStatusVO> serviceOrderStatusVOList = null;
		List<ServiceOrderSubStatusVO> subStatusVOList =null;
		ServiceOrderStatusVO statusVO =null;
		String problemTypeDescription=null;
		try{
			serviceOrderStatusVOList=mobileGenericDao.getProblemTypeDescription(problemStatus);
			if(null!= serviceOrderStatusVOList && !(serviceOrderStatusVOList.isEmpty())){
				statusVO =serviceOrderStatusVOList.get(0);
				if(null!=statusVO && null!= statusVO.getServiceOrderSubStatusVO() &&!(statusVO.getServiceOrderSubStatusVO().isEmpty())){
					subStatusVOList =  statusVO.getServiceOrderSubStatusVO();
				}
			}
			if(null!= subStatusVOList &&!(subStatusVOList.isEmpty())){
				for(ServiceOrderSubStatusVO sububStatusVO:subStatusVOList){
					if(null!= sububStatusVO &&(reasonCode.intValue()==sububStatusVO.getSubStatusId())){
						problemTypeDescription =sububStatusVO.getSubStatusName();
						break;
					}
				}
			}
		}catch(Exception e){
			LOGGER.error("Exception inside getProblemTypeDescription() inside MobileGenericBOImpl.java : "+e.getMessage());
			throw new BusinessServiceException(e);
		}
		return problemTypeDescription;
	}
	/*public String addNoteForProblem(SecurityContext securityContext,ReportProblemVO reportProblemVO)throws BusinessServiceException {
		ProcessResponse processResponse = null;
		OrderFulfillmentResponse ofResponse=null;
		String returnVal=null;
		try{  
			OrderFulfillmentRequest request = ofMapper.mapSONoteForProblem(reportProblemVO,securityContext);
			request.setIdentification(ofMapper.createOFIdentityFromSecurityContext(securityContext));
			ofResponse= ofHelper.runOrderFulfillmentProcess(reportProblemVO.getSoId(), SignalType.ADD_NOTE, request);
			processResponse = ofMapper.mapProcessResponse(ofResponse);
			List<String> arrMsgList = processResponse.getMessages();
			for(String msg:arrMsgList){
				returnVal = processResponse.getCode() + msg + System.getProperty("line.separator","\n");
			}
			LOGGER.info("Add Note  validation messages :" + returnVal);

		}catch (Exception e) {
			LOGGER.error("Exception inside addNoteForProblem() inside MobileGenericBOImpl.java : "+e.getMessage());
			throw new BusinessServiceException(e);
		}
		return returnVal;
	}*/
	/**
	 * Method to assign or reassign a provider for an so
	 * @param securityContext
	 * @param assignVO
	 * @return ProcessResponse	 
	 */
	public ProcessResponse assignOrReassignServiceOrder(SecurityContext securityContext,AssignVO assignVO)throws BusinessServiceException,AssignOrderException{

		ProcessResponse processResp = new ProcessResponse();
		Integer companyId = (Integer) securityContext.getCompanyId();
		Integer newResourceId=assignVO.getResourceId();
		boolean isValidResource = false;
		boolean isRoutedResource=false;

		try{		
			ArrayList<Contact> routedContactList = serviceOrderBo.getRoutedResources(assignVO.getSoId(),companyId.toString());		
			ServiceOrder serviceOrder=serviceOrderBo.getServiceOrder(assignVO.getSoId());
			Integer resourceId=serviceOrder.getAcceptedResourceId();


			if(MPConstants.ASSIGN_SO.equals(assignVO.getRequestFor())){
				assignVO = populateAssignDetails(securityContext, assignVO);
				//assign provider
				if(null != assignVO.getSoId() && null != assignVO.getResourceId()){
					managementService.assignProvider(assignVO.getResourceId(), assignVO.getSoId(), assignVO.getSoLoggingVO(), assignVO.getSoNote(), assignVO.getSoContact());
					processResp.setCode(ResultsCode.ASSIGN_SO_SUCCESS.getCode());					
					processResp.setMessage(ResultsCode.ASSIGN_SO_SUCCESS.getMessage());					
				}
			}
			else if(MPConstants.REASSIGN_SO.equals(assignVO.getRequestFor())){	
				Contact soContact = new Contact();
				assignVO.setServiceOrder(serviceOrder);	
				//checks whether the new provider is already assigned for the same SO
				if(resourceId.intValue() == newResourceId.intValue()){
					return createErrorProcessResponse(ResultsCode.SO_ALREADY_ASSIGN_ERROR.getCode(),ResultsCode.SO_ALREADY_ASSIGN_ERROR.getMessage());
				}

				//Checks whether the new provider is under the firm to which SO is already assigned
				List<Integer> resourcesUnderFirm = getRoutedResourcesUnderFirm(assignVO);
				if(resourcesUnderFirm != null){
					for(Integer resource : resourcesUnderFirm){
						if(resource.intValue() == newResourceId.intValue()){
							isValidResource = true;
							break;
						}
					}
				}
				if(!isValidResource){
					return createErrorProcessResponse(ResultsCode.RESOURCE_NOT_UNDER_FIRM.getCode(),ResultsCode.RESOURCE_NOT_UNDER_FIRM.getMessage());
				}
				//Checks whether the new resource is there in the routed resources 
				if(routedContactList!=null && newResourceId!=null)
				{
					for(int i=0; i<routedContactList.size(); i++)
					{
						soContact = (Contact) routedContactList.get(i);
						if(soContact != null && soContact.getResourceId().equals(newResourceId)){
							isRoutedResource=true;
							assignVO.setSoContact(soContact);
							break;
						}
					}
				}

				if(isRoutedResource==false){
					return createErrorProcessResponse(ResultsCode.SO_REASSIGN_WRONG_RESOURCE.getCode(),ResultsCode.SO_REASSIGN_WRONG_RESOURCE.getMessage());			
				}

				assignVO = populateReassignDetails(securityContext, assignVO);
				processResp = serviceOrderBo.saveReassignSO(assignVO.getSoLoggingVO(),assignVO.getSoNote(),assignVO.getReassignComment(), securityContext);		
				/*processResp.setCode(ResultsCode.SO_REASSIGN_SUCCESS.getCode());
				processResp.setMessage(ResultsCode.SO_REASSIGN_SUCCESS.getMessage());*/
				processResp = createErrorProcessResponse(ResultsCode.SO_REASSIGN_SUCCESS.getCode(),ResultsCode.SO_REASSIGN_SUCCESS.getMessage());
			}			
		}
		catch(AssignOrderException e){
			LOGGER.error("Exception inside assignReassignServiceOrder() inside MobileGenericBOImpl.java : "+e.getMessage());
			throw new AssignOrderException(e);
		}
		catch(Exception e){
			LOGGER.error("Exception inside assignReassignServiceOrder() inside MobileGenericBOImpl.java : "+e.getMessage());
			throw new BusinessServiceException(e);
		}
		return processResp;
	}
	/**
	 * method to get the contact details of routed resources
	 * @param resourceId
	 * @param soId
	 * @return Contact
	 */
	private Contact getsoContact(Integer resourceId, String soId) {
		Contact soContact = managementService.getRoutedResources(soId,resourceId);
		soContact.setSoId(soId);
		soContact.setEntityTypeId(ENTITY_TYPE_ID_PROVIDER);
		soContact.setContactTypeId(CONTACT_TYPE_ID_PRIMARY);
		soContact.setEntityId(resourceId);
		soContact.setCreatedDate(new Timestamp(new Date().getTime()));
		soContact.setModifiedDate(new Timestamp(new Date().getTime()));
		return soContact;
	}
	/**
	 * Method to populate the sologgingVo and soNote for assigning an SO
	 * @param securityContext
	 * @param assignVO
	 * @return AssignVO
	 */
	private AssignVO populateAssignDetails(SecurityContext securityContext,AssignVO assignVO){

		Contact soContact = new Contact();
		SoLoggingVo soLoggingVO = new SoLoggingVo();
		ServiceOrderNote soNote = new ServiceOrderNote();

		Integer role = securityContext.getRoleId();
		Integer entityId = securityContext.getVendBuyerResId();
		String userFirstName=  securityContext.getRoles().getFirstName();
		String userLastName =  securityContext.getRoles().getLastName();

		soContact = getsoContact(assignVO.getResourceId(),assignVO.getSoId());		
		//setting so logging object		
		soLoggingVO.setServiceOrderNo(assignVO.getSoId());
		soLoggingVO.setOldValue(null);
		soLoggingVO.setNewValue(assignVO.getResourceId().toString());
		if(null != soContact){
			soLoggingVO.setFirstName(soContact.getFirstName().trim());
			soLoggingVO.setLastName(soContact.getLastName().trim());
			soLoggingVO.setEmail(soContact.getEmail().trim());
			soLoggingVO.setContactId(soContact.getContactId());
			soLoggingVO.setComment("Has been assigned to " + soContact.getFirstName() +" " + soContact.getLastName() + 
					"(" +  soLoggingVO.getNewValue() + ")");
		}
		else{
			soLoggingVO.setComment("Has been assigned to " +  soLoggingVO.getNewValue());
		}
		soLoggingVO.setCreatedDate(Calendar.getInstance().getTime());
		soLoggingVO.setModifiedDate(Calendar.getInstance().getTime());
		soLoggingVO.setCreatedByName(userFirstName+" "+userLastName);
		soLoggingVO.setModifiedBy(securityContext.getUsername());
		soLoggingVO.setRoleId(role);
		soLoggingVO.setEntityId(entityId);
		soLoggingVO.setValueName(Constants.ASSIGNMENT_NOTE_SUBJECT);
		soLoggingVO.setActionId(Integer.parseInt(Constants.SO_ACTION.SERVICE_ORDER_ASSIGN));

		//setting notes object

		soNote.setSoId(soLoggingVO.getServiceOrderNo());
		soNote.setCreatedDate(soLoggingVO.getCreatedDate());
		soNote.setSubject(Constants.ASSIGNMENT_NOTE_SUBJECT);
		soNote.setRoleId(soLoggingVO.getRoleId());
		soNote.setNote(soLoggingVO.getComment());
		soNote.setCreatedByName(userLastName+", "+userFirstName);
		soNote.setModifiedBy(soLoggingVO.getModifiedBy());
		soNote.setModifiedDate(soLoggingVO.getModifiedDate());
		soNote.setNoteTypeId(Integer.valueOf(2));
		soNote.setEntityId(soLoggingVO.getEntityId());
		soNote.setPrivateId(Integer.valueOf(0));

		assignVO.setSoLoggingVO(soLoggingVO);
		assignVO.setSoNote(soNote);
		assignVO.setSoContact(soContact);
		return assignVO;

	}
	/**
	 * Method to populate the sologgingVo and soNote for reassigning an SO
	 * @param securityContext
	 * @param assignVO
	 * @return AssignVO
	 */
	private AssignVO populateReassignDetails(SecurityContext securityContext,AssignVO assignVO){
		SoLoggingVo soLoggingVO = new  SoLoggingVo();
		ServiceOrderNote soNote = new ServiceOrderNote();
		ServiceOrder serviceOrder = assignVO.getServiceOrder();
		List<com.newco.marketplace.dto.vo.serviceorder.RoutedProvider> routedResources=serviceOrder.getRoutedResources();	
		Contact soContact = assignVO.getSoContact();
		Contact soCurrentContact = null;
		Integer resourceId=serviceOrder.getAcceptedResourceId();
		if(routedResources!=null && resourceId!=null)
		{
			for(int i=0; i<routedResources.size(); i++)
			{
				soCurrentContact = (Contact) routedResources.get(i).getProviderContact();
				if(soCurrentContact != null && routedResources.get(i).getResourceId().equals(resourceId))
					break;
			}
		}		
		Integer role = securityContext.getRoleId();
		Integer entityId = securityContext.getVendBuyerResId();
		String userFirstName=  securityContext.getRoles().getFirstName();
		String userLastName =  securityContext.getRoles().getLastName();

		soLoggingVO.setFirstName(soContact.getFirstName().trim());
		soLoggingVO.setLastName(soContact.getLastName().trim());
		soLoggingVO.setEmail(soContact.getEmail().trim());
		soLoggingVO.setServiceOrderNo(assignVO.getSoId());
		soLoggingVO.setContactId(soContact.getContactId());
		soLoggingVO.setOldValue(resourceId.toString());
		soLoggingVO.setNewValue(assignVO.getResourceId().toString());
		if(null!=soCurrentContact){
			soLoggingVO.setComment("Has been reassigned from " + soCurrentContact.getFirstName() + " " + soCurrentContact.getLastName()+ "(" + soLoggingVO.getOldValue() + ") to " + soContact.getFirstName() +" " + soContact.getLastName() + 
					"(" +  soLoggingVO.getNewValue() + "). Reason for Reassigning is: " + assignVO.getReassignComment());
		}
		soLoggingVO.setCreatedDate(Calendar.getInstance().getTime());
		soLoggingVO.setModifiedDate(Calendar.getInstance().getTime());
		soLoggingVO.setCreatedByName(userFirstName+" "+userLastName);
		soLoggingVO.setModifiedBy(securityContext.getUsername());
		soLoggingVO.setRoleId(role);
		soLoggingVO.setEntityId(entityId);
		soLoggingVO.setValueName(Constants.REASSIGNMENT_NOTE_SUBJECT);
		soLoggingVO.setActionId(Integer.parseInt(Constants.SO_ACTION.SERVICE_ORDER_REASSIGN));

		soNote.setSoId(soLoggingVO.getServiceOrderNo());
		soNote.setCreatedDate(soLoggingVO.getCreatedDate());
		soNote.setSubject(Constants.REASSIGNMENT_NOTE_SUBJECT);
		soNote.setRoleId(soLoggingVO.getRoleId());
		soNote.setNote(soLoggingVO.getComment());
		soNote.setCreatedByName(userLastName+", "+userFirstName);
		soNote.setModifiedBy(securityContext.getUsername());
		soNote.setModifiedDate(soLoggingVO.getModifiedDate());
		soNote.setNoteTypeId(new Integer(2));
		soNote.setEntityId(soLoggingVO.getEntityId());
		soNote.setPrivateId(new Integer(0));

		assignVO.setSoLoggingVO(soLoggingVO);
		assignVO.setSoNote(soNote);

		return assignVO;
	}
	/**
	 * @param soId
	 * @return ServiceOrder
	 * Method to get the details of an SO
	 */
	public ServiceOrder getSoDetails(String soId)throws BusinessServiceException  {
		ServiceOrder so = new ServiceOrder();
		try{
			if(null != soId){
				so = serviceOrderBo.getServiceOrder(soId);
			}
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in MobileGenericBOImpl-->getSoDetails()");
			throw new BusinessServiceException(e.getMessage());
		}
		return so;
	}


	/* @param searchRequestVO
	 * @return
	 * to fetch service orders matching criteria
	 */
	public SOSearchResultsVO getServiceOrderSearchResults(
			SOSearchCriteriaVO searchRequestVO) throws BusinessServiceException {
		SOSearchResultsVO searchResultsVO = new SOSearchResultsVO();
		List<SOSearchResultVO> searchResultVOs =null;
		Integer totalCount = null;
		try{
			/*	Map<String, String> sort = ensureSort(searchRequestVO
					.getSortColumnName(), searchRequestVO.getSortOrder(), null);
			searchRequestVO.setSortColumnName(sort.get(SORT_COLUMN_KEY));
			searchRequestVO.setSortOrder(sort.get(SORT_ORDER_KEY));*/
			totalCount = mobileGenericDao.getTotalCountForServiceOrderSearchResults(searchRequestVO);
			if(!validatePageNumber(totalCount,searchRequestVO.getPageNo(),searchRequestVO.getPageSize())){
				searchResultsVO.setPageNumberExceededInd(true);
			}
			else{
				searchResultVOs = mobileGenericDao.getServiceOrderSearchResults(searchRequestVO);
				searchResultsVO.setSearchResultVOs(searchResultVOs);
				searchResultsVO.setTotalSOCount(totalCount);
			}
			// searchResults = mapGroupedOrders(searchResults,searchRequestVO.getFirmId());
		}
		catch (DataServiceException ex) {
			throw new BusinessServiceException(ex);
		}
		/*catch (BusinessServiceException ex) {
			throw ex;
		}*/
		return searchResultsVO;
	}

	/**
	 * @param totalCount
	 * @param searchRequestVO
	 * check if the page number has exceeded the page number max across which the orders can be distributed.
	 */
	private boolean validatePageNumber(Integer totalCount,
			Integer pageNo, Integer pageSize) {
		boolean pageNumNotExceeded = true;
		if(null!= totalCount && totalCount.intValue()> 0){
			// Checking the last page No from request and validate 
			if(null!= pageNo && null!=pageSize ){
				// This is the available last page
				double lastPage = Math.ceil((double) totalCount/ pageSize); 
				if(pageNo > lastPage){
					pageNumNotExceeded = false;
				}
			}
		}
		return pageNumNotExceeded;
	}

	/**
	 * This method will replace the first occurrence of the <br>
	 * Grouped SO with its List of Child Orders.
	 * <p>The rest of the SO with same Group Id will be removed from the list</p>
	 * @param searchResults
	 * @param groupIdChildrenMap
	 *//*
	private List<SOSearchResultVO> replaceChildWithGroup(List<SOSearchResultVO> searchResults,
			Map<String, List<SOSearchResultVO>> groupIdChildrenMap) {
		List<SOSearchResultVO> listProcessedSOs = new ArrayList<SOSearchResultVO>();
		List<SOSearchResultVO> groupedOrderList = new ArrayList<SOSearchResultVO>(); 
		for(SOSearchResultVO serviceOrder : searchResults){
			String groupId = serviceOrder.getGroupId();
			if (StringUtils.isNotBlank(groupId)){
				if(groupIdChildrenMap.containsKey(groupId)) { 
					//Replace the first occurrence of the group id wwith the Child So List
					List<SOSearchResultVO> childSOList = groupIdChildrenMap.get(groupId);
					serviceOrder.setSoId(groupId);
					serviceOrder.setGroupInd(Boolean.TRUE);
					if(null !=childSOList && childSOList.size()!=0){
						serviceOrder.setParentGroupTitle(childSOList.get(0).getParentGroupTitle());
						serviceOrder.setTitle(childSOList.get(0).getParentGroupTitle());

						serviceOrder.setGroupSpendLimit(childSOList.get(0).getGroupSpendLimit());
						serviceOrder.setGroupSpendLimitLabor(childSOList.get(0).getGroupSpendLimitLabor());
						serviceOrder.setGroupSpendLimitParts(childSOList.get(0).getGroupSpendLimitParts());
					}

					serviceOrder.setChildOrders(childSOList);
					groupIdChildrenMap.remove(groupId);
					groupedOrderList.add(serviceOrder);
				}else{
					If current iteration SO is a grouped SO and is already added 
	  * to the list as a child so of its parent
	  * the remove this from the so list 
					listProcessedSOs.add(serviceOrder);
				}
			}else{
				groupedOrderList.add(serviceOrder);
			}
		}
		//Removing all occurrence of grouped SO other than 
		//the first occurrence (for each Group Id)
		searchResults.removeAll(listProcessedSOs);
		return searchResults;
	}*/
	/**
	 * ensureSort sets the database fields that sorting will be performed on based on 
	 * data in the input parameter.  A Map is returned containing the key and sort order 
	 * @param sortColumn
	 * @param sortOrder
	 * @param statusId
	 * @return
	 *//*
	protected Map<String, String> ensureSort (String sortColumn, String sortOrder, Integer statusId[]) {
		Map<String, String> sort = new HashMap<String, String>();
		// boolean sortOrderSet = false;

		if( StringUtils.isNotEmpty(sortColumn) && !StringUtils.equals(sortColumn, OrderConstants.NULL_STRING) ) {
			if(MPConstants.STATUS.equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_STATUS);
			}
			else if(MPConstants.SUB_STATUS.equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SUB_STATUS);
			}
			else if(MPConstants.SO_ID.equalsIgnoreCase(sortColumn)){
				//sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_SOID);
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_SO_GRP_ID);
			} else if(MPConstants.SERVICE_DATE.equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_SERVICEDATE);
			} else if(MPConstants.SPEND_LIMIT.equalsIgnoreCase(sortColumn)){
				//sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_SPENDLIMIT);
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_SO_GRP_SPENDLIMIT);
			} else if(MPConstants.TIME_TO_APPOINTMENT.equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_SERVICEDATE);
			} else if(MPConstants.AGE_OF_ORDER.equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_CREATEDDATE);
			} else if(MPConstants.PROVIDER_LAST_NAME.equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_ROUTED_RESOURCE_LAST_NAME);
			} else if(MPConstants.CITY.equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_CITY);
			} else if(MPConstants.SPEND_LIMIT_TOTAL.equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_SPEND_LIMIT_TOTAL);					
			} else {
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_SERVICEDATE);
				sort.put(SORT_ORDER_KEY, OrderConstants.SORT_ORDER_DESC);
				sortOrderSet = true;
			}
		} else {
		sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_SERVICEDATE);
		sort.put(SORT_ORDER_KEY, OrderConstants.SORT_ORDER_DESC);
		// sortOrderSet = true;
				}

		if (!sortOrderSet) {
			if( StringUtils.isNotEmpty(sortOrder) && !StringUtils.equals(sortColumn, OrderConstants.NULL_STRING) ){
				sort.put(SORT_ORDER_KEY, sortOrder);
			} else {
				sort.put(SORT_ORDER_KEY, OrderConstants.SORT_ORDER_DESC);
			}
		}

		return sort;
	}*/


	/**
	 * Method to accept service order.
	 * @throws BusinessServiceException 
	 */
	/*private ProcessResponse acceptServiceOrder(String soId, Integer resourceId, boolean acceptByFirmInd, SecurityContext securityContext) throws BusinessServiceException {
		ProcessResponse processResponse = null;
		Integer vendorId = securityContext.getCompanyId();
		boolean isSOInEditMode = false;
		SignalType signalType = null;
		OrderFulfillmentRequest acceptRequest = null;
		OrderFulfillmentResponse responseOF = null;
		try{
			//Check whether SO in Edit Mode
			isSOInEditMode = serviceOrderBo.isSOInEditMode(soId);
			if (isSOInEditMode) {
				    processResponse = new ProcessResponse();
					//Set warning message when SO in Edit Mode.
					processResponse.setCode(ResultsCode.SO_ACCEPT_IN_EDIT_MODE.getCode());
					processResponse.setMessage(ResultsCode.SO_ACCEPT_IN_EDIT_MODE.getMessage());
			} else {	
					//Create OF request for SO Accept
					acceptRequest = createOFRequestForAcceptance(vendorId, resourceId,securityContext,acceptByFirmInd);
					//Set signals according to acceptedByfirmIndicator value.
					signalType = acceptByFirmInd?SignalType.ACCEPT_FOR_FIRM : SignalType.ACCEPT_ORDER;
					responseOF = ofHelper.runOrderFulfillmentProcess(soId, signalType, acceptRequest);
					//Create Process response from Of response 
					processResponse = ofMapper.mapProcessResponse(responseOF);
				}
			} catch (Exception bse) {
			LOGGER.error("Exception thrown accepting SO", bse);
			throw new BusinessServiceException(bse.getMessage());
		}
		return processResponse;
	}*/

	/**
	 * Method to accept grouped order.
	 * @throws BusinessServiceException 
	 */
	/*private ProcessResponse acceptGroupedOrder(String groupId, Integer resourceId, boolean  acceptByFirmInd, SecurityContext securityContext) throws BusinessServiceException {
		Integer vendorId = securityContext.getCompanyId();
		SignalType signalType;
		OrderFulfillmentResponse ofResponse;
		ProcessResponse processResponse = null;
		OrderFulfillmentRequest acceptRequest = null;
		boolean isSOInEditMode = false;
        try{
        	Currently none of the child gets on locked in edit mode once a grouped order is edited.
        	  Need to remove this code later getting confirmation 
        	isSOInEditMode = mobileGenericDao.isGroupedOrderInEditMode(groupId);
        	if(isSOInEditMode){
        		processResponse = new ProcessResponse();
        		//Set warning message when SO in Edit Mode.
				processResponse.setCode(ResultsCode.SO_ACCEPT_IN_EDIT_MODE.getCode());
				processResponse.setMessage(ResultsCode.SO_ACCEPT_IN_EDIT_MODE.getMessage());
        	}else{
				acceptRequest = createOFRequestForAcceptance(vendorId, resourceId,securityContext,acceptByFirmInd);
				//Use proper Signals based on whether SO to be accepted for resource / Firm.
				signalType = acceptByFirmInd?SignalType.ACCEPT_GROUP_FOR_FIRM : SignalType.ACCEPT_GROUP;
				ofResponse = ofHelper.runOrderFulfillmentGroupProcess(groupId,signalType, acceptRequest);
				//Create Process response from OFResponse
				processResponse = ofMapper.mapProcessResponse(ofResponse);
        	}
		}catch (Exception e) {
			//Create Error Response when Order Processing fails.
			LOGGER.info("error occured while accepting grouped Order grouped Order-->" + groupId);
			throw new BusinessServiceException(e.getMessage());

		}
		return processResponse;
	}*/

	/**
	 * Method to create OF Request For Acceptance.
	 */
	/*private OrderFulfillmentRequest createOFRequestForAcceptance(Integer vendorId, Integer resourceId, SecurityContext securityContext,boolean acceptByFirmInd) throws BusinessServiceException {
		OrderFulfillmentRequest acceptOfrequest = new OrderFulfillmentRequest();
		com.servicelive.orderfulfillment.domain.ServiceOrder serviceOrder = new com.servicelive.orderfulfillment.domain.ServiceOrder();
		Contact vendorContact = null;
		serviceOrder = setBasicFirmDetails(vendorId,serviceOrder);
		if(acceptByFirmInd){
			//When assignment type is Firm, set Assignment type misc params
			serviceOrder.setAssignmentType(OrderConstants.SO_ASSIGNMENT_TYPE_FIRM);
			String name = null;
			try{
				name = serviceOrderBo.getVendorBusinessName(securityContext.getCompanyId());
			}catch (Exception bse) {
				LOGGER.error("error fetching business name of firm", bse);
				throw new BusinessServiceException(bse.getMessage());
			}            		
			acceptOfrequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_ACCEPTED_NAME,name);
			acceptOfrequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_SEND_PROVIDER_EMAIL, "false");
		}else{
			//When Assignment type is Resource
			serviceOrder.setAssignmentType(OrderConstants.SO_ASSIGNMENT_TYPE_PROVIDER);
			serviceOrder.setAcceptedProviderResourceId(resourceId.longValue());
			Get the Contact info for Vendor Resource!!!
			// get the state (location e.g. MI,IL,etc.) for the provider that accepted
			try {
				vendorContact = resourceBO.getVendorResourceContact(resourceId);
			} catch (Exception bse) {
				LOGGER.error(bse);
				throw new BusinessServiceException(bse.getMessage());
			}
			//Set contact and address of Vendor Resource
			serviceOrder = setContactLocation(vendorContact,serviceOrder);
			acceptOfrequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_ACCEPTED_PROVIDER_STATE, vendorContact.getStateCd());
		}
		acceptOfrequest.setElement(serviceOrder);
		acceptOfrequest.setIdentification(ofMapper.createProviderIdentFromSecCtx(securityContext));
		return acceptOfrequest;
	}*/

	/*private com.servicelive.orderfulfillment.domain.ServiceOrder setContactLocation(Contact vendorContact,
			com.servicelive.orderfulfillment.domain.ServiceOrder serviceOrder) {
		SOContact contact = ofMapper.mapContact(vendorContact);
		serviceOrder.addContact(contact);
		SOLocation soLocation = ofMapper.mapLocation(vendorContact);
		serviceOrder.addLocation(soLocation);
		return serviceOrder;
	}*/
	/*private com.servicelive.orderfulfillment.domain.ServiceOrder setBasicFirmDetails(Integer vendorId,com.servicelive.orderfulfillment.domain.ServiceOrder serviceOrder) {
		serviceOrder.setAcceptedProviderId(vendorId.longValue());
		serviceOrder.setSoTermsCondId( PublicAPIConstant.acceptSO.TERMS_AND_COND_IND_PROVIDER_ACCEPT);	
		serviceOrder.setProviderSOTermsCondInd(1);
		serviceOrder.setProviderTermsCondDate(Calendar.getInstance().getTime());
		return serviceOrder;
	}*/
	/**
	 * Method to create Error ProcessResponse.
	 */
	public ProcessResponse createErrorProcessResponse(String code, String message){
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setCode(code);
		processResponse.setMessage(message);
		return processResponse;
	}

	/**
	 * Method to get Eligible Providers for a service order and a firm
	 */
	public List<ProviderResultVO> getEligibleProviders(String firmId, String soId) throws BusinessServiceException{
		List<ProviderResultVO> providers = null;
		try{
			providers = managementService.getEligibleProviders(firmId, soId);
		}		
		catch (Exception exp) {
			LOGGER.error(this.getClass().getName()+ " : routed providers :" + exp.getMessage());
			throw new BusinessServiceException(exp);
		}
		return providers;
	}

	/**
	 * Method to get Eligible Providers for a grouped order and a firm
	 */
	public List<ProviderResultVO> getEligibleProvidersForGroup(String firmId, String groupId) throws BusinessServiceException{
		List<ProviderResultVO> providers = null;
		try{
			providers = managementService.getEligibleProvidersForGroup(firmId, groupId);
		}		
		catch (Exception exp) {
			LOGGER.error(this.getClass().getName()+ " : routed providers :" + exp.getMessage());
			throw new BusinessServiceException(exp);
		}
		return providers;
	}



	/**
	 * Method for rejecting service orders for providers
	 * @return ProcessResponse
	 * **/
	/*public ProcessResponse rejectServiceOrder(MobileSORejectVO requestVO, SecurityContext securityContext ) throws BusinessServiceException{

		OrderFulfillmentRequest ofRequest = null;
		OrderFulfillmentResponse ofResponse = null;
		try{
			String groupId=requestVO.getGroupId();
			String soId=requestVO.getSoId();

			//If process response code is 1 proceed. Else throw error response
				ofRequest = createOrderFulfillmentRejectSORequest(securityContext, requestVO);
				//groupId = so.getGroupId();
				//If Group order
				if (StringUtils.isNotBlank(groupId)) {
					if (ofHelper.isNewGroup(groupId)){
						ofResponse = ofHelper.runOrderFulfillmentGroupProcess(groupId, SignalType.REJECT_GROUP,ofRequest);
						return ofMapper.mapProcessResponse(ofResponse);
					}else{
						processResponse = getOrderGroupBo().rejectGroupedOrder(checkedResourceID.get(0), groupId, reasonId,
								OrderConstants.PROVIDER_RESP_REJECTED, securityContext.getVendBuyerResId().toString(), requestVO.getReasonDesc(),securityContext);
						return processResponse;
					}
				}else if(ofHelper.isNewSo(soId)){
					boolean isSOInEditMode = isSOInEditMode(soId);						
					ofResponse = ofHelper.runOrderFulfillmentProcess(soId, SignalType.REJECT_ORDER, ofRequest);
					return ofMapper.mapProcessResponse(ofResponse, isSOInEditMode );
				}else{
					processResponse = getServiceOrderBo().rejectServiceOrder(checkedResourceID.get(0), soId, reasonId,
							OrderConstants.PROVIDER_RESP_REJECTED, securityContext.getVendBuyerResId().toString(), requestVO.getReasonDesc(),securityContext);
					return processResponse;
				}

			}
		catch(BusinessServiceException e){
			throw e;
		}

	}*/


	/**
	 * Creates OF Request from API Request. Sets only the list of resource for whom
	 * the service order is requested to be rejected.
	 * @return {@link OrderFulfillmentRequest}
	 * **/
	/*private OrderFulfillmentRequest createOrderFulfillmentRejectSORequest(SecurityContext securityContext, MobileSORejectVO request){
		OrderFulfillmentRequest rejectOfrequest = new OrderFulfillmentRequest();
		Identification idfn = ofMapper.createProviderIdentFromSecCtx(securityContext);
		rejectOfrequest.setIdentification(idfn);
		List<Integer> checkedResourceID=null;
		if(null != request.getResourceList()){
			checkedResourceID = request.getResourceList().getResourceId();
		}else{
			return null;
		}
		List<RoutedProvider> rejectProviders = getRejectedProviders(checkedResourceID, request.getReasonId(), request.getReasonCommentDesc(), OrderConstants.PROVIDER_RESP_REJECTED, securityContext.getVendBuyerResId());
		SOElementCollection collection=new SOElementCollection();
		collection.addAllElements(rejectProviders);				
		rejectOfrequest.setElement(collection);
		return rejectOfrequest;
	}*/

	private ArrayList<LuProviderRespReasonVO> getProviderRespReason(LuProviderRespReasonVO luProviderRespReasonVO){
		try {
			return getLookupBO().getProviderRespReason(luProviderRespReasonVO);
		} catch (DataServiceException e) {
			LOGGER.error(this.getClass().getName()+ " " + e.getMessage());
		}
		return null;

	}

	/**
	 * Method which validates the request for Reject Service Order API. <p>
	 * @param request : The request VO object. {@link MobileSORejectVO}
	 * @param soId which is to be rejected for some selected Resources.
	 * @param securityContext 
	 * @param groupId 
	 * @return {@link ProcessResponse} <p>
	 * ProcessResponse.code will be "1" when all the request parameters are valid.
	 * */
	public ProcessResponse validateRejectRequest(MobileSORejectVO request) {
		ProcessResponse response = new ProcessResponse();
		//Integer reasonId =null;
		String warnMessage =null;
		// Set response code as 1 for success (default)
		response.setCode(OrderConstants.ONE);
		if(null != request){
			//Validates Reason code for SO Rejection
			try{
				Integer.valueOf(request.getReasonId()); 
			}
			catch(NumberFormatException e){
				response.setMessage(ResultsCode.REJECT_INVALID_REASON_CODE.getMessage());
				response.setCode(ResultsCode.REJECT_INVALID_REASON_CODE.getCode());	
				return response;
			}
			if(!validateReasonCode(request.getReasonId())){
				response.setMessage(ResultsCode.REJECT_INVALID_REASON_CODE.getMessage());
				response.setCode(ResultsCode.REJECT_INVALID_REASON_CODE.getCode());
			}
			else if(OrderConstants.OTHER_REASON_CODE.equals(request.getReasonId()) && StringUtils.isBlank(request.getReasonCommentDesc())){
				response.setMessage(ResultsCode.REJECT_COMMENT_REQUIRED.getMessage());
				response.setCode(ResultsCode.REJECT_COMMENT_REQUIRED.getCode());
			}
			else{
				//Validate resource Ids
				warnMessage =  validateResourceIds(request);

				if(StringUtils.isNotBlank(warnMessage)){
					if(ResultsCode.REJECT_RESOURCE_ID_INVALID.getMessage().equals(warnMessage)){
						response.setMessage(ResultsCode.REJECT_RESOURCE_ID_INVALID.getMessage());
						response.setCode(ResultsCode.REJECT_RESOURCE_ID_INVALID.getCode());	
					}
					else if(ResultsCode.REJECT_RESOURCE_ID_REQUIRED.getMessage().equals(warnMessage)){
						response.setMessage(ResultsCode.REJECT_RESOURCE_ID_REQUIRED.getMessage());
						response.setCode(ResultsCode.REJECT_RESOURCE_ID_REQUIRED.getCode());		
					}
				}			
			}


		}
		return response;
	}

	/**
	 * This method validates the reason code submitted to reject a Service Order.
	 * <p>
	 * Method validates both reason code Id and description. (lu_so_provider_resp_reason = 3)
	 * @param reasonId: Reason code id
	 * @param reasonDesc : Reason code description.
	 * @return true: when both reason code id and description are valid.<p>
	 * false : When either of the args is invalid.
	 * **/
	private boolean validateReasonCode(Integer reasonId) {
		LuProviderRespReasonVO luReasonVO = new LuProviderRespReasonVO();
		luReasonVO.setSearchByResponse(OrderConstants.PROVIDER_RESP_REJECTED);
		ArrayList<LuProviderRespReasonVO> listReasonCode;
		try {
			listReasonCode = getProviderRespReason(luReasonVO);
			if(null == listReasonCode || 0 == listReasonCode.size()){
				return false;
			}else{
				int reasonCode = reasonId;
				for(LuProviderRespReasonVO reasonVO : listReasonCode){
					if(reasonCode == reasonVO.getRespReasonId()){
						return true;
					}
				}
			}

		} catch (Exception e) {
			LOGGER.error(e);
		}
		return false;
	}

	/**
	 * This method validates resources submitted to reject an SO
	 * @param resourceList : List of resources {@link Resource} which is to be validated.
	 * @param firmId : Firm id of the provider
	 * @param soId : SO Id/Group Id which is requested to be rejected for the selected resource ids.
	 * @param groupId : Whether so is grouped.
	 * @param providerResId 
	 * @param roleId 
	 * @param roleId 
	 * @return Error/warning message. Empty string is returned when all resources are valid.
	 * */
	private String validateResourceIds(MobileSORejectVO request){
		ResourceIds resourceList = request.getResourceList();
		Integer firmId = Integer.parseInt(request.getFirmId());
		String soId = request.getSoId();
		String groupId = request.getGroupId();
		Integer roleId = request.getRoleId();
		Integer providerResId = request.getResourceId();
		String warnMessage = StringUtils.EMPTY;

		if(null != resourceList){
			List<Integer> resourceIds =  resourceList.getResourceId();
			if(null == resourceIds || resourceIds.isEmpty()){
				//When no resources are submitted
				warnMessage = ResultsCode.REJECT_RESOURCE_ID_REQUIRED.getMessage();
			}else{
				//Validate resource Ids.
				if(MPConstants.ROLE_LEVEL_TWO.equals(roleId.intValue())){
					// Level two providers can reject only for self, so resource id list size should be 1
					if(resourceIds.size()>1){
						warnMessage = ResultsCode.REJECT_RESOURCE_ID_INVALID.getMessage();
					}
					// Level two providers can reject only for self
					else{
						// Only one resource id in the list
						Integer resourceId = resourceIds.get(0);
						if(!(null!= resourceId && (providerResId.intValue() ==  resourceId.intValue()))){
							warnMessage = ResultsCode.REJECT_RESOURCE_ID_INVALID.getMessage();
						}
					}
				}
				// If role 3
				else{
					List<Integer> invalidIds = checkResources(resourceIds, firmId, soId, groupId);
					if(invalidIds.size()>0){
						//Format warning message. Message contains invalid resource ids.
						warnMessage = ResultsCode.REJECT_RESOURCE_ID_INVALID.getMessage();
					}
				}				
			}
		}
		//When all resource ids are valid.
		return warnMessage;
	}


	/**
	 * Validates the resource Ids against the service order id and its firm id.<p>
	 * @param resourceIds : List of resource id whcih is to be validated.
	 * @param firmId : Firm id of the provider
	 * @param soId : SO Id whcih is requested to be rejected for the selected resource ids.
	 * @param groupId 
	 * @return List of Invalid resource Ids.
	 * */
	private List<Integer> checkResources(List<Integer> resourceIds, Integer firmId, String soId, String groupId) {
		List<Integer> invalidIds = new ArrayList<Integer>();
		try {
			if (null != firmId && StringUtils.isNotBlank(soId)) {
				List<ProviderResultVO> providers;
				if(StringUtils.isNotBlank(groupId)){
					providers = getEligibleProvidersForGroup(String.valueOf(firmId),groupId);
				}else{
					providers = getEligibleProviders(String.valueOf(firmId),soId);
				}
				if(null == providers || providers.isEmpty()){
					//Assume that all given resource Ids are invalid.
					return resourceIds;
				}else{
					List<Integer> validIds = new ArrayList<Integer>();
					for(ProviderResultVO resultVO : providers){
						validIds.add(resultVO.getResourceId());
					}
					/*Return empty list as a list of invalid Ids when all resource ids are valid.
					 * Find all invalid Ids and return them as list of integer
					 */
					if(!validIds.containsAll(resourceIds)){
						for(Integer id : resourceIds){
							if(!validIds.contains(id)){
								invalidIds.add(id);
							}
						}
					}
				}
			}

		} catch (BusinessServiceException e) {
			LOGGER.error(this.getClass().getName()+ " " + e.getMessage());
		}
		return invalidIds;

	}

	public boolean checkIfSOInProblem(String soId)throws BusinessServiceException {
		boolean isValidStatus = false;
		try{
			isValidStatus = mobileGenericDao.checkIfSOIsInProblem(soId);
		}catch (Exception e) {
			LOGGER.error("Exception Occured in MobileGenericBOImpl-->checkIfSOIsActiveAccepted()");
			throw new BusinessServiceException(e.getMessage());
		}
		return isValidStatus;
	}
	/*public String resolveProblem(SecurityContext securityContext,ReportProblemVO reportProblemVO)throws BusinessServiceException {
		OrderFulfillmentResponse response =null;
		ProcessResponse pr = null;
		String returnVal= null;
		OrderFulfillmentRequest request = new OrderFulfillmentRequest();
		SignalType resolveSignal = securityContext.isBuyer() ? SignalType.BUYER_RESOLVE_PROBLEM : SignalType.PROVIDER_RESOLVE_PROBLEM;
		request.setIdentification(ofMapper.createOFIdentityFromSecurityContext(securityContext));
		request.addMiscParameter(OrderfulfillmentConstants.PVKEY_PROBLEM_COMMENT, reportProblemVO.getResolutionComments());
		request.addMiscParameter(OrderfulfillmentConstants.PVKEY_PROBLEM_DESC, reportProblemVO.getProblemReasonCodeDescription());
		try{
			response = ofHelper.runOrderFulfillmentProcess(reportProblemVO.getSoId(), resolveSignal, request);
			pr = ofMapper.mapProcessResponse(response);
			List<String> arrMsgList = pr.getMessages();
			for(String msg:arrMsgList){
				returnVal = pr.getCode() + msg + System.getProperty("line.separator","\n");
			}
			LOGGER.info("reportProblemResolution validation messages :" + returnVal);
		}catch (Exception e) {
			LOGGER.error("Exception in executing signal for resolving the problem"+"Error Messages:"+ returnVal);
			throw new BusinessServiceException(e);
		}
		return returnVal;
	}*/
	/*public String addNoteForResolveProblem(SecurityContext securityContext,ReportProblemVO reportProblemVO)throws BusinessServiceException {
		ProcessResponse processResponse = null;
		OrderFulfillmentResponse ofResponse=null;
		String returnVal=null;
		try{  
			OrderFulfillmentRequest request = ofMapper.mapSONoteForProblem(reportProblemVO,securityContext);
			request.setIdentification(ofMapper.createOFIdentityFromSecurityContext(securityContext));
			ofResponse= ofHelper.runOrderFulfillmentProcess(reportProblemVO.getSoId(), SignalType.ADD_NOTE, request);
			processResponse = ofMapper.mapProcessResponse(ofResponse);
			List<String> arrMsgList = processResponse.getMessages();
			for(String msg:arrMsgList){
				returnVal = processResponse.getCode() + msg + System.getProperty("line.separator","\n");
			}
			LOGGER.info("Add Note  validation messages :" + returnVal);

		}catch (Exception e) {
			LOGGER.error("Exception inside addNoteForProblem() inside MobileGenericBOImpl.java : "+e.getMessage());
			throw new BusinessServiceException(e);
		}
		return returnVal;
	}*/
	public String getProblemTypeDescription(String soId)throws BusinessServiceException {
		String problemDescription =null;
		try{
			problemDescription = mobileGenericDao.getProblemTypeDescription(soId);
		}catch (Exception e) {
			LOGGER.error("Exception inside getProblemTypeDescription() inside MobileGenericBOImpl.java : "+e.getMessage());
			throw new BusinessServiceException(e);
		}
		return problemDescription;
	}




	public List<ServiceOrderSearchResultsVO> getServiceOrdersForGroup(String groupSoId) throws BusinessServiceException{
		List<ServiceOrderSearchResultsVO> serviceOrders = null;
		try{
			serviceOrders= orderGroupBo.getServiceOrdersForGroup(groupSoId);
		}catch(Exception ex){
			LOGGER
			.error("MobileGenericBOImpl.getServiceOrdersForGroup(): " +
					"Exception occured while fetching GroupServiceOrder list from OF:"+ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		}
		return serviceOrders;
	}
	public ServiceOrder getServiceOrder(String soId) throws BusinessServiceException{
		ServiceOrder so = null;
		try{
			so= serviceOrderBo.getServiceOrder(soId);
		}catch(Exception ex){
			LOGGER
			.error("MobileGenericBOImpl.getServiceOrder(): " +
					"Exception occured while fetching Service Order details:"+ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		}
		return so;
	}
	public boolean isCARroutedSO(String soId) throws BusinessServiceException{
		boolean isCARrouted=false;
		try{
			isCARrouted=serviceOrderBo.isCARroutedSO(soId);
		}catch(Exception ex){
			LOGGER
			.error("MobileGenericBOImpl.isCARroutedSO(): "+ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		}
		return isCARrouted;
	}
	public boolean checkNonFunded(String soId) throws BusinessServiceException{
		boolean isNonFunded=false;
		try{
			isNonFunded=serviceOrderBo.checkNonFunded(soId);
		}catch(Exception ex){
			LOGGER
			.error("MobileGenericBOImpl.checkNonFunded(): "+ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		}
		return isNonFunded;
	}

	/**
	 * R14.0 BO method for invoking the counterOffer OF signal
	 * Grouped order, call signal CREATE_GROUP_CONDITIONAL_OFFER, 
	 * which internally calls the CREATE_CONDITIONAL_OFFER, corresponding to each SO in the group
	 * Non grouped order, call signal CREATE_CONDITIONAL_OFFER
	 * 
	 * @param CounterOfferVO
	 * @param SecurityContext
	 * @return ProcessResponse
	 */
	/*public ProcessResponse createCounterOffer(final CounterOfferVO offerVO, final SecurityContext securityContext) throws BusinessServiceException{

		OrderFulfillmentResponse ofResponse = null;
		//create mapper object for OF
		List<com.servicelive.orderfulfillment.domain.RoutedProvider> routedProvidersList = createConditionalOfferMapper(offerVO);
		SOElementCollection soeCollection = new SOElementCollection();
		soeCollection.addAllElements(routedProvidersList);
		Identification identification = ofMapper.createProviderIdentFromSecCtx(securityContext);

		if(PublicAPIConstant.GROUPED_SO_IND.equals(offerVO.getGroupInd())){
			LOGGER.info("Grouped order OF flow CREATE_GROUP_CONDITIONAL_OFFER starts...");
			ofResponse = ofHelper.runOrderFulfillmentGroupProcess(offerVO.getGroupId(), SignalType.CREATE_GROUP_CONDITIONAL_OFFER, soeCollection, identification);
			LOGGER.info("Grouped order OF flow CREATE_GROUP_CONDITIONAL_OFFER ends...");
		}else{
			LOGGER.info("Single service order OF flow CREATE_CONDITIONAL_OFFER starts...");
			ofResponse = ofHelper.runOrderFulfillmentProcess(offerVO.getSoId(), SignalType.CREATE_CONDITIONAL_OFFER, soeCollection, identification);
			LOGGER.info("Single service order OF flow CREATE_CONDITIONAL_OFFER ends...");
		}

		if(ofResponse == null){
			throw new BusinessServiceException("OF response is null...");
		}
		return ofMapper.mapProcessResponse(ofResponse);
	}*/

	/**
	 * R14.0 BO method for invoking the withdrawCounterOffer OF signal
	 * Grouped order, call signal WITHDRAW_GROUP_CONDITIONAL_OFFER_RESOURCE_LIST,
	 * which internally calls the WITHDRAW_CONDITIONAL_OFFER_RESOURCE_LIST, corresponding to each SO in the group
	 * Non grouped order, call signal WITHDRAW_CONDITIONAL_OFFER_RESOURCE_LIST
	 * 
	 * @param CounterOfferVO
	 * @param SecurityContext
	 * @return ProcessResponse
	 */
	/*public ProcessResponse withdrawCounterOffer(final CounterOfferVO offerVO, final SecurityContext securityContext) throws BusinessServiceException{

		OrderFulfillmentResponse ofResponse = null;
		//create mapper object for OF
		List<com.servicelive.orderfulfillment.domain.RoutedProvider> routedProvidersList = withdrawConditionalOfferMapper(offerVO);
		SOElementCollection soeCollection = new SOElementCollection();
		soeCollection.addAllElements(routedProvidersList);
		Identification identification = ofMapper.createProviderIdentFromSecCtx(securityContext);

		if(PublicAPIConstant.GROUPED_SO_IND.equals(offerVO.getGroupInd())){
			LOGGER.info("Grouped order OF flow WITHDRAW_GROUP_CONDITIONAL_OFFER_RESOURCE_LIST starts...");
			ofResponse = ofHelper.runOrderFulfillmentGroupProcess(offerVO.getGroupId(), SignalType.WITHDRAW_GROUP_CONDITIONAL_OFFER_RESOURCE_LIST, soeCollection, identification);
			LOGGER.info("Grouped order OF flow WITHDRAW_GROUP_CONDITIONAL_OFFER_RESOURCE_LIST ends...");
		}else{
			LOGGER.info("Single service order OF flow WITHDRAW_CONDITIONAL_OFFER_RESOURCE_LIST starts...");
			ofResponse = ofHelper.runOrderFulfillmentProcess(offerVO.getSoId(), SignalType.WITHDRAW_CONDITIONAL_OFFER_RESOURCE_LIST, soeCollection, identification);
			LOGGER.info("Single service order OF flow WITHDRAW_CONDITIONAL_OFFER_RESOURCE_LIST ends...");
		}

		if(ofResponse == null){
			throw new BusinessServiceException("OF response is null...");
		}
		return ofMapper.mapProcessResponse(ofResponse);
	}*/
	//	WITHDRAW_GROUP_CONDITIONAL_OFFER_RESOURCE_LIST



	/*public ProcessResponse releaseServiceOrder(MobileSOReleaseVO releaseRequestVO,SecurityContext securityContext) {
		String assignmentType = managementService.fetchAssignmentType(releaseRequestVO.getSoId());
		List<Integer> providerList = new ArrayList<Integer>();
		SOElementCollection soec = new SOElementCollection();
		OrderFulfillmentResponse response = new OrderFulfillmentResponse();
		Identification identity = ofMapper.createOFIdentityFromSecurityContext(securityContext);
		if(assignmentType!=null && assignmentType.equals("FIRM") || releaseRequestVO.isReleaseByFirmIndicator()){
			try{
				providerList = managementService.fetchProviderList(releaseRequestVO.getSoId(),releaseRequestVO.getFirmId());
			}catch (Exception e) {
				LOGGER.error("error fetching routed providers for firm", e);
			}
			List<Parameter> parameters = new ArrayList<Parameter>();
			parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RELEASE_COMMENT,releaseRequestVO.getComments()));
			soec = createSOElementForProviderList(providerList,releaseRequestVO);
			response = ofHelper.runOrderFulfillmentProcess(releaseRequestVO.getSoId(), SignalType.PROVIDER_FIRM_RELEASE_ORDER, soec, identity, parameters);
		}else{
			com.servicelive.orderfulfillment.domain.RoutedProvider routedProvider = createReleaseElement((long)releaseRequestVO.getResourceId(), releaseRequestVO.getComments(),Integer.parseInt(releaseRequestVO.getReason()));
			response = ofHelper.runOrderFulfillmentProcess(releaseRequestVO.getSoId(), SignalType.PROVIDER_RELEASE_ORDER, routedProvider,identity);
		}
		ProcessResponse processResponse = ofMapper.mapProcessResponse(response);
		return processResponse;
	}

	public RoutedProvider createReleaseElement(Long resourceId, String providerComment,Integer reasonCode){
		RoutedProvider routedProvider = new RoutedProvider();
		routedProvider.setProviderResourceId(resourceId);
		routedProvider.setProviderResponse(ProviderResponseType.RELEASED);
		if(reasonCode.equals(-2)){
			routedProvider.setProviderRespReasonId(null);
		} else {
			routedProvider.setProviderRespReasonId(reasonCode);
		}
		routedProvider.setProviderRespComment(providerComment);
		Timestamp providerRespDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
		routedProvider.setProviderRespDate(providerRespDate);

		return routedProvider;
	}


	public SOElementCollection createSOElementForProviderList(List<Integer> providerList,MobileSOReleaseVO releaseRequestVO){
		SOElementCollection soElementCollection = new SOElementCollection();
		for(Integer providerId :providerList){
			Long id = (long)providerId;
			RoutedProvider routedProvider = new RoutedProvider();
			routedProvider.setProviderResourceId(id);
			routedProvider.setProviderResponse(ProviderResponseType.RELEASED_BY_FIRM);
			if(releaseRequestVO.getReason().equals("-2")){
				routedProvider.setProviderRespReasonId(null);
			} else {
				routedProvider.setProviderRespReasonId(Integer.parseInt(releaseRequestVO.getReason()));
			}
			routedProvider.setProviderRespComment(releaseRequestVO.getComments());
			Timestamp providerRespDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
			routedProvider.setProviderRespDate(providerRespDate);
			soElementCollection.addElement(routedProvider);
		}
		return soElementCollection;
	}*/
	/**@Description: This method fetches  reasonCodes specified by provider
	 * @param  reasonType
	 * @return 
	 */
	public List<ReasonCodeVO> getProviderReasoncodes(String reasonType) throws BusinessServiceException{
		List<ReasonCodeVO> reasonCode=null;
		try{
			reasonCode=mobileGenericDao.getRespReasonCodes(reasonType);
		}
		catch (DataServiceException e) {
			LOGGER.error(this.getClass().getName()+ "Exception in fetching specific reasonCodes for provider " + e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return reasonCode;

	}
	/**@Description: This method fetches all the reasonCodes available
	 * @param 
	 * @return
	 */
	public List<String> getResonCodeType() throws BusinessServiceException {
		List<String> reasonCodesType = null;
		try {
			reasonCodesType=mobileGenericDao.getProviderRespReason();

		} catch (DataServiceException e) {
			LOGGER.error(this.getClass().getName()+ "Exception in fetching reasonCodes " + e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return reasonCodesType;

	}

	/**
	 * method to get the routedresources 
	 */
	public ArrayList<Contact> getRoutedResources(String soId, Integer firmId) {
		ArrayList<Contact> routedContactList = null;
		try{
			if(null!=soId && null != firmId){
				routedContactList = serviceOrderBo.getRoutedResources(soId,firmId.toString());
			}
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in MobileGenericBOImpl-->getRoutedResources()");
			//throw new BusinessServiceException(e.getMessage());	
		}
		return routedContactList;
	}
	/**
	 * Method to get the routed resources under the firm 
	 * @param assignVO
	 * @return List<Integer>	 
	 */
	public List<Integer> getRoutedResourcesUnderFirm(AssignVO assignVO)throws BusinessServiceException{
		List<Integer> resourcesUnderFirm = null;
		try{
			if(null!=assignVO.getSoId() && null != assignVO.getFirmId()){
				resourcesUnderFirm = mobileGenericDao.getRoutedResourcesUnderFirm(assignVO);
			}
		}
		catch(Exception  e){
			LOGGER.error(this.getClass().getName()+ " " + e.getMessage());
			throw new BusinessServiceException(e);
		}
		return resourcesUnderFirm;
	}

	public boolean checkIfValidReason(String reasonCode)throws BusinessServiceException {
		boolean isValidReasonCode = false;
		try{
			isValidReasonCode = mobileGenericDao.checkIfReasonIsValid(reasonCode);
		}catch (Exception e) {
			LOGGER.error("Exception Occured in MobileGenericBOImpl-->checkIfValidReason()");
			throw new BusinessServiceException(e.getMessage());
		}
		return isValidReasonCode;
	}

	/*public ProcessResponse executeSOBid(RequestBidVO bidVO, SecurityContext securityContext) throws BusinessServiceException{
	 *//** Code commented as business for logging activity client is moved to service.
	 *  The commented code will be removed after testing*//*
		ProcessResponse processResponse = null;
		boolean saveBidToActivityLogFailed = bidVO.isSaveBidToActivityLogFailed();
//Already commented out portion START
		String expireHourTmpStr = "05:00 PM";
		Long buyerResourceId = null;
		boolean serviceDateSpecifiedAsARange = false;
		boolean saveBidToActivityLogFailed = false;

		Long bidId = null;
		// TODO : Confirm securityContext is correct.
		String userName = securityContext.getUsername();
		try{
			bidVO = mobileGenericDao.fetchServiceOrderDetailsForBid(bidVO);
		}catch(DataServiceException e){
			LOGGER.error("Exception Occured in mobileGenericBO-->executeSOBid()"+e);
			throw new BusinessServiceException(e);
		}

		if(null == bidVO.getBidExpirationDate()){
			if(null != bidVO.getServiceDateEnd()){
				bidVO.setBidExpirationDate(bidVO.getServiceDateEnd());
				bidVO.setBidExpirationHour(bidVO.getServiceTimeEnd());
			} else if(null != bidVO.getServiceDateStart()){
				bidVO.setBidExpirationDate(bidVO.getServiceDateStart());
				bidVO.setBidExpirationHour(expireHourTmpStr);
			} else if(currentSO.getServiceOrderDate() != null){
					bidExpirationDate = currentSO.getServiceOrderDate();
		}
		}
		try {
			activityLogHelper.markBidsFromProviderAsExpired(bidVO.getSoId(), new Long(bidVO.getRoutedResourceId()));
			bidId = activityLogHelper.logBid(bidVO.getSoId(), new Long(bidVO.getVendorId()), new Long(bidVO.getRoutedResourceId()), new Long(bidVO.getBuyerId()), buyerResourceId, bidVO.getTotalHours(), bidVO.getLaborRate(),
					bidVO.getTotalLaborPrice(), bidVO.getPartsPrice(), bidVO.getBidExpirationDate(), bidVO.getNewServiceDateStart(), bidVO.getNewServiceDateEnd(), serviceDateSpecifiedAsARange,
					bidVO.getComment(), userName);
		} catch (RuntimeException e){
			LOGGER.error("Failed to save bid to ActivityLog:" + "soID=" + bidVO.getSoId() + "\n" + "vendorId=" + bidVO.getVendorId() + "\n" + "resourceId="
					+ bidVO.getRoutedResourceId() + "\n" + "buyerId=" + bidVO.getBuyerId() + "\n" + "buyerResourceId=" + buyerResourceId + "\n" + "totalHours="
					+ bidVO.getTotalHours() + "\n" + "laborRate=" + bidVO.getLaborRate() + "\n" + "totalLabor=" + bidVO.getTotalLaborPrice() + "\n" + "partsMaterials="
					+ bidVO.getPartsPrice() + "\n" + "bidExpirationDate=" + bidVO.getBidExpirationDate() + "\n" + "newDateRangeFrom="
					+ bidVO.getNewServiceDateStart() + "\n" + "newDateRangeTo=" + bidVO.getNewServiceDateEnd() + "\n" + "comment=" + bidVO.getComment() + "\n"
					+ "username=" + userName + "\n"+"Exception :"+e);
			saveBidToActivityLogFailed = true;
		}		
		if(null == bidId || bidId < 0){
			saveBidToActivityLogFailed = true;
		}
//Already commented out portion END

		if(saveBidToActivityLogFailed == false){
			try {
				BigDecimal totalHoursBD = null;
				if (null != bidVO.getTotalHours()){
					totalHoursBD = new BigDecimal(bidVO.getTotalHours());
				}
				RoutedProvider routedProvider = createBid(bidVO);
				List<com.servicelive.orderfulfillment.domain.RoutedProvider> firmRoutedProviders = new ArrayList<com.servicelive.orderfulfillment.domain.RoutedProvider>();
				firmRoutedProviders.add(routedProvider);
				SOElementCollection routedProviders=new SOElementCollection();
				routedProviders.addAllElements(firmRoutedProviders);
				OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(bidVO.getSoId(), SignalType.CREATE_CONDITIONAL_OFFER, routedProviders, ofMapper.createProviderIdentFromSecCtx(securityContext));
				processResponse = ofMapper.mapProcessResponse(response);
				if(response.isError()){
					throw new BusinessServiceException("Save Bid To Database Failed");
				}
			}
			catch (RuntimeException e1){
				throw new BusinessServiceException(e1);
			}
		}
		if(saveBidToActivityLogFailed){
			processResponse = createErrorProcessResponse(ResultsCode.SO_BID_ACTIVITY_LOG_FAILED.getCode(), ResultsCode.SO_BID_ACTIVITY_LOG_FAILED.getMessage());
		}
		return processResponse;
	}
	public static RoutedProvider createBid(RequestBidVO bidVO) {

		SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		RoutedProvider returnVal = new RoutedProvider();
		returnVal.setProviderResourceId((long)bidVO.getRoutedResourceId());
		returnVal.setVendorId(bidVO.getVendorId());
		returnVal.setProviderResponse(ProviderResponseType.CONDITIONAL_OFFER);

		try{
			returnVal.setOfferExpirationDate( formatDateTime.parse(formatDate.format(bidVO.getBidExpirationDate()) + " " + bidVO.getBidExpirationHour()));
		}catch(ParseException e){
			//Should not happen.
		}

		if(bidVO.getNewServiceDateStart() != null){
			returnVal.setSchedule(new SOScheduleDate());
			returnVal.getSchedule().setServiceDate1(bidVO.getNewServiceDateStart());
			returnVal.getSchedule().setServiceTimeStart(bidVO.getNewServiceTimeStart());
			//should not be setting end date without start date
			if(bidVO.getNewServiceDateEnd() != null){
				returnVal.getSchedule().setServiceDate2(bidVO.getNewServiceDateEnd());
				returnVal.getSchedule().setServiceTimeEnd(bidVO.getNewServiceTimeEnd());
			}
		}
		if(null != bidVO.getTotalHours()) returnVal.setTotalHours(bidVO.getTotalHours());
		if(null != bidVO.getTotalLaborPrice()) returnVal.setTotalLabor(new BigDecimal(bidVO.getTotalLaborPrice()));
		if(null != bidVO.getPartsPrice()) returnVal.setPartsMaterials(new BigDecimal(bidVO.getPartsPrice()));
		if(null != bidVO.getTotalLaborPartsPrice()) returnVal.setIncreaseSpendLimit(new BigDecimal(bidVO.getTotalLaborPartsPrice()));
		returnVal.setProviderRespComment(bidVO.getComment());
		if (bidVO.getNewServiceDateStart() != null && bidVO.getTotalLaborPartsPrice() != null)
			returnVal.setProviderRespReasonId(OrderConstants.RESCHEDULE_SERVICE_DATE_AND_SPEND_LIMIT);
		else if (bidVO.getTotalLaborPartsPrice() != null)
			returnVal.setProviderRespReasonId(OrderConstants.SPEND_LIMIT);
		else if (bidVO.getNewServiceDateStart() != null)
			returnVal.setProviderRespReasonId(OrderConstants.RESCHEDULE_SERVICE_DATE);

		return returnVal;
	}

*/
	/*public String respondToReschedule(RescheduleVO rescheduleVo,SecurityContext securityContext)throws BusinessServiceException {
			String returnVal= "";
			OrderFulfillmentResponse response =null;
			List<Parameter> parameters = null;
			ProcessResponse processResponse = null;
			Identification identification =ofMapper.createOFIdentityFromSecurityContext(securityContext);
			try{
				//Accept Reschedule Request
				if(null!= rescheduleVo && MPConstants.RESCHEDULE_ACCEPT.equals(rescheduleVo.getRescheduleRespondType())){
					// Method to set Of parameters required for notifying NPS for inhome orders when reschedule accepts.
					  parameters = setParametersForNPSNotification(rescheduleVo);
					  response = ofHelper.runOrderFulfillmentProcess(rescheduleVo.getSoId(), SignalType.PROVIDER_ACCEPT_RESCHEDULE, new SOSchedule(),identification,parameters);
					  returnVal = setOFResults(response, processResponse);
				   }//Reject Reschedule Request	
				   else if(null!= rescheduleVo && MPConstants.RESCHEDULE_REJECT.equals(rescheduleVo.getRescheduleRespondType())){
					  response = ofHelper.runOrderFulfillmentProcess(rescheduleVo.getSoId(), SignalType.PROVIDER_REJECT_RESCHEDULE, new SOSchedule(),identification);
					  returnVal = setOFResults(response, processResponse);
				  }//Cancel Reschedule Request.
				else{
					  response = ofHelper.runOrderFulfillmentProcess(rescheduleVo.getSoId(), SignalType.PROVIDER_CANCEL_RESCHEDULE, new SOSchedule(),identification);
					  returnVal = setOFResults(response, processResponse);
				 }
			}catch (Exception e) {
				LOGGER.error("Exception in processing OF signal for accept/reject/cancel reschedule request"+ e.getMessage());
				throw new BusinessServiceException("Exception in processing OF signal"+ e.getMessage());
			}
			LOGGER.info("Results Message after OF SIGNAL"+ returnVal);
			return returnVal;
		}*/

	/*private List<Parameter> setParametersForNPSNotification(RescheduleVO rescheduleVo) throws BusinessServiceException {
			List<Parameter> parameters = new ArrayList<Parameter>();
			String rescheduleMessage =null;
			InHomeRescheduleVO input=new InHomeRescheduleVO();
        	InHomeRescheduleVO result=new InHomeRescheduleVO();
        	input.setSoId(rescheduleVo.getSoId());
        	input.setIsNPSMessageRequired(true);
        	try {
				result=notificationService.getSoDetailsForReschedule(input);
				rescheduleMessage = result.getRescheduleMessage();
				//Setting message  for creating request xml of Send Message API
				parameters.add(new Parameter(OrderfulfillmentConstants.INHOME_OUTBOUND_NOTIFICATION_RESCHEDULE_MESSAGE, rescheduleMessage));
	        	//Setting call code for creating request xml of Service Operation API
	        	parameters.add(new Parameter(OrderfulfillmentConstants.INHOME_OUTBOUND_NOTIFICATION_RESCHEDULE_CALL_CODE, InHomeNPSConstants.RESCHD_CALLCODE));
			} catch (Exception e) {
				LOGGER.error("Exception in setting messages for NPS for reschedule accept");
				throw new BusinessServiceException(e.getMessage());
			}
			return parameters;
		}*/
	/*public String setOFResults(OrderFulfillmentResponse response,ProcessResponse processResponse) throws BusinessServiceException{
			String returnVal = "";
			try{
				processResponse = ofMapper.mapProcessResponse(response);
				List<String> arrMsgList = processResponse.getMessages();
				for(String msg:arrMsgList){
					returnVal = processResponse.getCode() + msg + System.getProperty("line.separator","\n");
				}
			}catch (Exception e) {
				LOGGER.error("Exception in mapping of response to process response"+ e.getMessage());
				throw new BusinessServiceException(e.getCause());
			}
			return returnVal;

		}*/
	public RescheduleVO checkIfRescheduleRequestExists(RescheduleVO rescheduleVo)throws BusinessServiceException {
		RescheduleVO loggingVo = null;
		int roleId = 0;
		Date rescheduleServiceDate =null;
		try{
			loggingVo = mobileGenericDao.checkIfRescheduleRequestExists(rescheduleVo);
				if(null!= loggingVo){
					roleId = loggingVo.getRoleId();
					rescheduleServiceDate = loggingVo.getRescheduleServiceDate1();
					if(rescheduleVo.getRescheduleRespondType().equalsIgnoreCase(MPConstants.RESCHEDULE_ACCEPT)
						||(rescheduleVo.getRescheduleRespondType().equalsIgnoreCase(MPConstants.RESCHEDULE_REJECT))){
					// Check the existence of any existing reschedule request placed by the buyer for the order.
					if((roleId ==5 || roleId == 3)&& (null != rescheduleServiceDate)){
						loggingVo.setValidSignal(true);
					}else{
						loggingVo.setValidSignal(false);
					}
				}else if(rescheduleVo.getRescheduleRespondType().equalsIgnoreCase(MPConstants.RESCHEDULE_CANCEL)){
					//validating roleId and setting the url parameters from to loggingVo which is paased to the validating function
					loggingVo.setUrlRoleId(rescheduleVo.getUrlRoleId());
					loggingVo.setResourceId(rescheduleVo.getResourceId());
					loggingVo.setFirmId(rescheduleVo.getFirmId());
					boolean isValidRole = validateRoleForRespondReschedule(loggingVo);
					// check the existence of any reschedule request placed by the provider	.
					if(roleId == 1 && null != rescheduleServiceDate && isValidRole){
						loggingVo.setValidSignal(true);
					}else{
						loggingVo.setValidSignal(false);
					}
				}
			}
			else{
				loggingVo = new RescheduleVO(); 
				loggingVo.setValidSignal(false);

			}
			
		}catch (Exception e) {
			LOGGER.error("Exception in checking reschedule request exists for the provider"+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		if(null != loggingVo){
			loggingVo.setRescheduleRespondType(rescheduleVo.getRescheduleRespondType());
			loggingVo.setSoId(rescheduleVo.getSoId());
			loggingVo.setResourceId(rescheduleVo.getResourceId());
		}

		return loggingVo;
	}

	
	private boolean validateRoleForRespondReschedule(RescheduleVO rescheduleVo)throws BusinessServiceException{
		boolean isValidRole = false;
		String vendorIdOfEntity = null;
		if(null!=rescheduleVo){
		//If role 1 or 2, cancel action can be done only if the reschedule request was submitted by himself
		if(rescheduleVo.getUrlRoleId() == MPConstants.ROLE_LEVEL_TWO.intValue() ||rescheduleVo.getUrlRoleId() == MPConstants.ROLE_LEVEL_ONE.intValue()){
			if(null!=rescheduleVo.getEntityId() && ((rescheduleVo.getEntityId()).equals(rescheduleVo.getResourceId()))){
				isValidRole = true;
			}
		}else if(rescheduleVo.getUrlRoleId() == MPConstants.ROLE_LEVEL_THREE.intValue()){
			//If role3, cancel action can be done if the vendorId of the resource who submitted reschedule request earlier is same as the vendorId present in the request
			try{
				vendorIdOfEntity = mobileGenericDao.getVendorIdFromVendorResource(rescheduleVo.getEntityId().toString());
			if(null!=vendorIdOfEntity && vendorIdOfEntity.equals(rescheduleVo.getFirmId())){
				isValidRole = true;
			}
			}catch (Exception e) {
				LOGGER.error("Exception in validateRoleForRespondReschedule()"+ e.getMessage());
				throw new BusinessServiceException(e.getCause());
			}
		}
	}
		return isValidRole;
	}
	public MobileOutBoundNotificationVo getRescheduleInformations(String soId) throws BusinessServiceException {
		BuyerOutboundNotificationVO buyerOutboundNotificationVO = null;
		MobileOutBoundNotificationVo notificationVo = null;
		try{
			buyerOutboundNotificationVO = buyerOutBoundNotificationService.getScheduleDetails(soId);
			if(null != buyerOutboundNotificationVO){
				notificationVo = mapMobileOutboundNotification(buyerOutboundNotificationVO);
			}
		}catch (Exception e) {
			LOGGER.error("Exception in fetching reschedule details for RI choice invocation"+ e.getMessage());
		}
		return notificationVo;
	}

	private MobileOutBoundNotificationVo mapMobileOutboundNotification(BuyerOutboundNotificationVO input) {
		MobileOutBoundNotificationVo output = new MobileOutBoundNotificationVo();
		output.setSoId(input.getSoId());
		output.setEntityId(input.getEntityId());
		output.setReasonDescr(input.getReasonDescr());
		output.setTimeZone(input.getTimeZone());
		output.setServiceOrderRescheduleFromDate(input.getServiceOrderRescheduleFromDate());
		output.setServiceOrderRescheduleFromTime(input.getServiceOrderRescheduleFromTime());
		output.setServiceOrderRescheduleToDate(input.getServiceOrderRescheduleToDate());
		output.setServiceOrderRescheduleToTime(input.getServiceOrderRescheduleToTime());
		return output;
	}

	public void invokeChoiceWebService(RescheduleVO rescheduleVo,MobileOutBoundNotificationVo buyerOutboundNotificationVO) throws BusinessServiceException {
		String modificationId= null;
		if(null!= rescheduleVo && null!= rescheduleVo.getEntityId()){
			modificationId=	rescheduleVo.getEntityId().toString();
		}
		String reasonLogging=rescheduleVo.getReasonCodeDescription();
		String reasonCode="";
		String reasonDescr="";
		RequestRescheduleInfo requestRescheduleInfo=new RequestRescheduleInfo();
		RequestOrder order=new RequestOrder();
		int reasonCodeIndex= reasonLogging.lastIndexOf("Reason:");
		reasonCodeIndex=reasonCodeIndex+7;
		int reasonDescrIndex= reasonLogging.lastIndexOf("Comments:");
		reasonDescrIndex=reasonDescrIndex+9;
		int length=reasonLogging.length();
		if(6!=reasonCodeIndex)
			reasonCode= reasonLogging.subSequence(reasonCodeIndex, reasonDescrIndex-9).toString().trim();
		if(8!=reasonDescrIndex){
			reasonDescr= reasonLogging.subSequence(reasonDescrIndex, length).toString().trim() ;
		}
		if(StringUtils.isEmpty(reasonCode) && !StringUtils.isEmpty(reasonDescr)){
			reasonCode = reasonDescr;
		}
		//get the rescheduled dates in service order Timezone.
		TimeZone timeZone=TimeZone.getTimeZone(buyerOutboundNotificationVO.getTimeZone());
		Calendar startDateTime = TimeChangeUtil.getCalTimeFromParts(buyerOutboundNotificationVO.getServiceOrderRescheduleFromDate(), buyerOutboundNotificationVO.getServiceOrderRescheduleFromTime(),TimeZone.getTimeZone("GMT"));
		Date serviceFromDate = TimeChangeUtil.getDate(startDateTime, timeZone);
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
		String fromDate=formatter.format(serviceFromDate);
		String serviceTimeStart = TimeChangeUtil.getTimeString(startDateTime, timeZone);

		Calendar endDateTime=null;
		if(null!=buyerOutboundNotificationVO.getServiceOrderRescheduleToDate() && null!=buyerOutboundNotificationVO.getServiceOrderRescheduleToTime())
			endDateTime= TimeChangeUtil.getCalTimeFromParts(buyerOutboundNotificationVO.getServiceOrderRescheduleToDate(), buyerOutboundNotificationVO.getServiceOrderRescheduleToTime(),TimeZone.getTimeZone("GMT"));
		String serviceTimeEnd=""; 
		if(null!=endDateTime)
			serviceTimeEnd= TimeChangeUtil.getTimeString(endDateTime, timeZone);
		//set reschedule information in request Object.
		order.setServiceScheduleDate(fromDate);
		order.setServiceScheduleFromTime(serviceTimeStart);
		if(StringUtils.isBlank(serviceTimeEnd)){
			order.setServiceScheduletoTime(serviceTimeStart);
		}else{
			order.setServiceScheduletoTime(serviceTimeEnd);
		}
		order.setServiceOrderRescheduledFlag(BuyerOutBoundConstants.RESHEDULE_FLAG_YES);
		//get the current date in server timeZone 
		Calendar calender=Calendar.getInstance();
		SimpleDateFormat timeFormatter=new SimpleDateFormat("hh:mm a");
		String modifiedfromDate=timeFormatter.format(calender.getTime());
		Calendar modificationDateTime = TimeChangeUtil.getCalTimeFromParts(calender.getTime(), modifiedfromDate,calender.getTimeZone());
		Date modificationDate = TimeChangeUtil.getDate(modificationDateTime, timeZone);
		String modificationDateValue=formatter.format(modificationDate);
		String modificationTime = TimeChangeUtil.getTimeString(modificationDateTime, timeZone);
		//set the current date ,modified Id,reasonCode in reschedule Object
		requestRescheduleInfo.setReschedCancelModificationDate(modificationDateValue);
		requestRescheduleInfo.setReschedCancelModificationTime(modificationTime);
		requestRescheduleInfo.setRescheduleModificationID(modificationId);
		requestRescheduleInfo.setRescheduleReasonCode(reasonCode);
		requestRescheduleInfo.setRescheduleRsnCdDescription(reasonDescr);
		RequestMsgBody requestMsgBody=new RequestMsgBody();
		RequestOrders orders=new RequestOrders();
		List<RequestOrder> orderList=new ArrayList<RequestOrder>();
		RequestReschedInformation requestReschedInformation=new RequestReschedInformation();
		List<RequestRescheduleInfo> requestRescheduleInf=new ArrayList<RequestRescheduleInfo>();
		requestRescheduleInf.add(requestRescheduleInfo);
		requestReschedInformation.setRequestRescheduleInf(requestRescheduleInf);
		order.setRequestReschedInformation(requestReschedInformation);
		orderList.add(order);
		orders.setOrder(orderList);
		requestMsgBody.setOrders(orders);
		try {
			BuyerOutboundFailOverVO failoverVO = buyerOutBoundNotificationService.callService(requestMsgBody, rescheduleVo.getSoId());
			if(null!=failoverVO)
				buyerOutBoundNotificationJMSService.callJMSService(failoverVO);
		} catch (BusinessServiceException e) {
			LOGGER.error("Error in NPS update for reschedule"+e);
		}
	}


	public IServiceOrderBO getServiceOrderBo() {
		return serviceOrderBo;
	}

	public void setServiceOrderBo(IServiceOrderBO serviceOrderBo) {
		this.serviceOrderBo = serviceOrderBo;
	}

	public IOrderGroupBO getOrderGroupBo() {
		return orderGroupBo;
	}

	public void setOrderGroupBo(IOrderGroupBO orderGroupBo) {
		this.orderGroupBo = orderGroupBo;
	}

	public IResourceBO getResourceBO() {
		return resourceBO;
	}

	public void setResourceBO(IResourceBO resourceBO) {
		this.resourceBO = resourceBO;
	}

	public ILookupBO getLookupBO() {
		return lookupBO;
	}

	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}

	public IForgotUsernameBO getForgotUsernameBO() {
		return forgotUsernameBO;
	}

	public void setForgotUsernameBO(IForgotUsernameBO forgotUsernameBO) {
		this.forgotUsernameBO = forgotUsernameBO;
	}

	public UserManagementBO getUserManagementBO() {
		return userManagementBO;
	}

	public void setUserManagementBO(UserManagementBO userManagementBO) {
		this.userManagementBO = userManagementBO;
	}
	public OrderManagementService getManagementService() {
		return managementService;
	}

	public void setManagementService(OrderManagementService managementService) {
		this.managementService = managementService;
	}

	public IMobileGenericDao getMobileGenericDao() {
		return mobileGenericDao;
	}

	public void setMobileGenericDao(IMobileGenericDao mobileGenericDao) {
		this.mobileGenericDao = mobileGenericDao;
	}

	/*public OFHelper getOfHelper() {
			return ofHelper;
		}

		public void setOfHelper(OFHelper ofHelper) {
			this.ofHelper = ofHelper;
		}
		public OFMapper getOfMapper() {
			return ofMapper;
		}
		public void setOfMapper(OFMapper ofMapper) {
			this.ofMapper = ofMapper;
		}*/
	public NotificationServiceImpl getNotificationService() {
		return notificationService;
	}
	public void setNotificationService(NotificationServiceImpl notificationService) {
		this.notificationService = notificationService;
	}
	public IBuyerOutBoundNotificationService getBuyerOutBoundNotificationService() {
		return buyerOutBoundNotificationService;
	}
	public void setBuyerOutBoundNotificationService(
			IBuyerOutBoundNotificationService buyerOutBoundNotificationService) {
		this.buyerOutBoundNotificationService = buyerOutBoundNotificationService;
	}
	public IBuyerOutBoundNotificationJMSService getBuyerOutBoundNotificationJMSService() {
		return buyerOutBoundNotificationJMSService;
	}
	public void setBuyerOutBoundNotificationJMSService(
			IBuyerOutBoundNotificationJMSService buyerOutBoundNotificationJMSService) {
		this.buyerOutBoundNotificationJMSService = buyerOutBoundNotificationJMSService;
	}

	/**
	 * @param updateScheduleVO
	 * @throws BusinessServiceException
	 * 
	 * method to update schedule details
	 * 
	 *//*
	public void updateScheduleDetails(UpdateScheduleVO updateScheduleVO)
			throws BusinessServiceException {
		try{
			if(null!=updateScheduleVO){
				updateScheduleVO = mapUpdateScheduleDetails(updateScheduleVO);
				managementService.updateScheduleDetails(updateScheduleVO);
				// for updating so substatus to schedule confirmd when schedule status is pre call completed confirmed
				if(updateScheduleVO.getScheduleStatusId() == OrderConstants.PRE_CALL_COMPLETED && (updateScheduleVO.getCustomerConfirmedInd()!=null && updateScheduleVO.getCustomerConfirmedInd().intValue()==1)){
					managementService.updateSOSubstatusToScheduleConfirmed(updateScheduleVO.getSoId());
				}
			}
		}
		catch(Exception ex){
			LOGGER.error("Exception inside MobileGenricBO updateScheduleDetails:"+ex.getMessage());
			throw new BusinessServiceException(ex);
		}
	}
	*//**
	 * @param updateScheduleVO
	 * 
	 * to map various parameters
	 *//*
	private UpdateScheduleVO mapUpdateScheduleDetails(UpdateScheduleVO updateScheduleVO) {
		Boolean custAvailableFlag = updateScheduleVO.getCustomerAvailableFlag();
		String customerNotAvailableReasonCode = updateScheduleVO.getReasonId();
		String source = updateScheduleVO.getSource();
		Integer custAvailableResponseReasonCode = updateScheduleVO.getCustAvailableRespCode();
		updateScheduleVO = mapScheduleStatus(updateScheduleVO,custAvailableFlag,source);
		updateScheduleVO = mapUpdateScheduleReason(updateScheduleVO,customerNotAvailableReasonCode,custAvailableFlag,source);
		updateScheduleVO = mapCustomerConfirmInd(updateScheduleVO,custAvailableFlag,custAvailableResponseReasonCode);
		if(custAvailableFlag && null!= custAvailableResponseReasonCode && MPConstants.UPDATE_SERVICE_WINDOW.intValue() == custAvailableResponseReasonCode.intValue()){
			updateScheduleVO = mapServiceWindowDetails(updateScheduleVO);
		}
		return updateScheduleVO;
	}
	*//**
	 * @param updateScheduleVO
	 * @param updateScheduleVO
	 * @return
	 * 
	 * mapping of service windows in case of Update Service Window
	 * 
	 *//*
	private UpdateScheduleVO mapServiceWindowDetails(
			UpdateScheduleVO updateScheduleVO) {
		if (!StringUtils.isBlank(updateScheduleVO
				.getServiceTimeStart())
				&& !StringUtils
				.isBlank(updateScheduleVO
						.getServiceDateStart())) {
			// convert to GMT
			Timestamp startAppDate = TimestampUtils
					.getTimestampFromString(
							updateScheduleVO
							.getServiceDateStart(),
							MPConstants.DATE_FORMAT_IN_DB);
			String startTime = TimeUtils
					.convertToGMT(
							startAppDate,
							updateScheduleVO
							.getServiceTimeStart(),
							updateScheduleVO
							.getServiceTimeZone())
							.get(MPConstants.TIME_PARAMETER).toString();
			updateScheduleVO
			.setServiceTimeStart(startTime);


			String startDate = "";
			startAppDate = TimestampUtils.
					getTimestampFromString(getDateAndTimeAppended(updateScheduleVO.getServiceDateStart(),
							updateScheduleVO.getServiceTimeStart()), 
							MPConstants.TIME_STAMP_FORMAT_IN_DB_TWENTY_FOUR);

			startDate = TimeUtils.convertToGMT(startAppDate, 
					updateScheduleVO.getServiceTimeStart(), 
					updateScheduleVO.getServiceTimeZone()).get(MPConstants.DATE_PARAMETER).toString();
			updateScheduleVO.setServiceDateStart(startDate);
		}
		if (!StringUtils.isBlank(updateScheduleVO
				.getServiceTimeEnd())
				&& !StringUtils
				.isBlank(updateScheduleVO
						.getServiceDateEnd())) {
			// convert to GMT
			Timestamp endAppDate = TimestampUtils
					.getTimestampFromString(
							updateScheduleVO
							.getServiceDateEnd(),
							MPConstants.DATE_FORMAT_IN_DB);
			String endTime = TimeUtils
					.convertToGMT(
							endAppDate,
							updateScheduleVO
							.getServiceTimeEnd(),
							updateScheduleVO
							.getServiceTimeZone())
							.get(MPConstants.TIME_PARAMETER).toString();
			if(StringUtils.isNotBlank(endTime)){
				updateScheduleVO
				.setServiceTimeEnd(endTime);
			}

			//SL 18896 R8.2 START
			//pass the endDate parameter also with the request, along with the endTime
			//need to check whether date goes to the next day, hence appending time+12 to date for PM
			String endDate = "";
			endAppDate = TimestampUtils.
					getTimestampFromString(getDateAndTimeAppended(updateScheduleVO.getServiceDateEnd(),
							updateScheduleVO.getServiceTimeEnd()), 
							MPConstants.TIME_STAMP_FORMAT_IN_DB_TWENTY_FOUR);

			endDate = TimeUtils.convertToGMT(endAppDate, 
					updateScheduleVO.getServiceTimeEnd(), 
					updateScheduleVO.getServiceTimeZone()).get(MPConstants.DATE_PARAMETER).toString();
			if(StringUtils.isNotBlank(endDate)){
				updateScheduleVO.setServiceDateEnd(endDate);
			}

		}
		return updateScheduleVO;
	}

	*//**
	 * @param date
	 * @param time
	 * @return
	 *//*
	private String getDateAndTimeAppended(String date, String time){
		if(StringUtils.isNotBlank(date) && StringUtils.isNotBlank(time)){

			String hr = time.substring(0,2);
			int hrIntValue=0;
			try{
				hrIntValue = Integer.parseInt(hr);
			}catch (NumberFormatException e) {
				// Exception caught and nothing to do
			}
			if(time.contains("PM") && hrIntValue != 12){
				hrIntValue = hrIntValue+12;
			}
			String convertedHour = new Integer(hrIntValue).toString();
			if(convertedHour.length()<2){
				convertedHour = "0"+convertedHour;
			}

			return date+ " "+convertedHour+time.substring(2,5)+":00";
		}
		return null;
	}	

	*//**
	 * @param updateScheduleVO
	 * @param customerNotAvailableReasonCode
	 * @param custAvailableFlag
	 * @param source
	 * 
	 * 	 * map the reason for schedule changes
	 * @return
	 *//*
	private UpdateScheduleVO mapUpdateScheduleReason(
			UpdateScheduleVO updateScheduleVO, String customerNotAvailableReasonCode, Boolean custAvailableFlag,
			String source) {

		if(custAvailableFlag){
			if(MPConstants.PRE_CALL.equals(source)){
				updateScheduleVO.setReasonId(MPConstants.PRE_CALL_COMPLETED_REASON);
			}
			else if(MPConstants.CONFIRM_APPOINTMENT.equals(source)){
				updateScheduleVO.setReasonId(MPConstants.TIME_WINDOW_CALL_COMPLETED_REASON);
			}
		}
		else{
			if(null != customerNotAvailableReasonCode){
				updateScheduleVO.setReasonId(customerNotAvailableReasonCode.toString());
			}
		}

		return updateScheduleVO;
	}

	*//**
	 * @param updateScheduleVO
	 * @param customerAvailableFlag
	 * @param custAvailableResponseReasonCode
	 * @return
	 * 
	 * to map customer confirm ind
	 * 
	 *//*
	private UpdateScheduleVO mapCustomerConfirmInd(
			UpdateScheduleVO updateScheduleVO, Boolean customerAvailableFlag,
			Integer custAvailableResponseReasonCode) {

		if(customerAvailableFlag){
			if(MPConstants.RESCHEDULE_REASON.intValue() == custAvailableResponseReasonCode){
				updateScheduleVO.setCustomerConfirmedInd(0);
			}
			else{
				updateScheduleVO.setCustomerConfirmedInd(1);
			}
		}
		else{
			updateScheduleVO.setCustomerConfirmedInd(0);
		}

		return updateScheduleVO;
	}

	*//**
	 * @param updateScheduleVO
	 * @param customerAvailableFlag
	 * @param source
	 * @return
	 * to map the schedule status
	 *//*
	private UpdateScheduleVO mapScheduleStatus(
			UpdateScheduleVO updateScheduleVO, Boolean customerAvailableFlag,
			String source) {
		if(MPConstants.PRE_CALL.equals(source)){
			if(customerAvailableFlag){
				updateScheduleVO.setScheduleStatusId(MPConstants.PRE_CALL_COMPLETED);
			}
			else{
				updateScheduleVO.setScheduleStatusId(MPConstants.PRE_CALL_ATTEMPTED);
			}
		}
		else if(MPConstants.CONFIRM_APPOINTMENT.equals(source)){
			if(customerAvailableFlag){
				updateScheduleVO.setScheduleStatusId(MPConstants.CONFIRM_APPOINTMENT_COMPLETED);
			}
			else{
				updateScheduleVO.setScheduleStatusId(MPConstants.CONFIRM_APPOINTMENT_ATTEMPTED);
			}
		}
		return updateScheduleVO;
	}*/

	/**
	 * @param firmId
	 * @param soId
	 */	
	public void updateServiceOrderFlag(String firmId, String soId, Boolean followupFlag) throws BusinessServiceException {
		int flagSOPriority = 0;
		if(followupFlag){
			flagSOPriority = 1;
			}
			// update priority
		try{
			managementService.updateSOPriority(soId, firmId, flagSOPriority);
		}catch(Exception e){
			LOGGER.error("Exception inside MobileGenricBO updateServiceOrderFlag:"+e.getMessage());
			throw new BusinessServiceException(e);
		}
	}




	/**
	 * Method to submit reschedule request for provider
	 */
	/*public ProcessResponse submitReschedule(SecurityContext securityContext,SORescheduleVO rescheduleVO) {			
		SOSchedule reschedule = new SOSchedule();
		InHomeRescheduleVO result = new InHomeRescheduleVO();
		//String format = OrderConstants.RESCHEDULE_DATE_FORMAT;
		ProcessResponse processResponse = null;

		String reason = "";	
		try {
			RescheduleVO soRescheduleInfo = serviceOrderBo.getRescheduleInfo(rescheduleVO.getSoId());
			reason = serviceOrderBo.getRescheduleReason(rescheduleVO.getRescheduleReasonCode());
			Identification ident = ofMapper.createOFIdentityFromSecurityContext(securityContext);	
			reschedule = ofMapper.mapSOSchedule(rescheduleVO);
			reschedule.setCreatedViaAPI(true);
			result = setInHomeRescheduleVO(rescheduleVO, soRescheduleInfo,reschedule);			
			String rescheduleMessage = notificationService.createRescheduleMessageForProviderRescheduleAPI(result);
			OrderFulfillmentResponse response = null;

			SignalType signalType = SignalType.PROVIDER_REQUEST_RESCHEDULE;
			List<Parameter> parameters = new ArrayList<Parameter>();		
			StringBuffer reschedulePeriod = new StringBuffer();
			reschedulePeriod = createMessageForReschedule(soRescheduleInfo, rescheduleVO);

			if (soRescheduleInfo.getRescheduleServiceStartDate() != null) {
				parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_EDIT_RESCHEDULE_REQUEST," edited reschedule request"));
			} 
			else {
				parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_EDIT_RESCHEDULE_REQUEST," requested reschedule"));
			}
			parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RESCHEDULE_REQUEST_COMMENT,rescheduleVO.getRescheduleComments()));
			parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RESCHEDULE_REQUEST_REASON_CODE," Reason: " + reason));
			parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_EDIT_RESCHEDULE_DATE_INFO,reschedulePeriod.toString()));
			LOGGER.info("requestRescheduleSO.rescheduleMessage : "
					+ rescheduleMessage);
			parameters.add(new Parameter(OrderfulfillmentConstants.INHOME_OUTBOUND_NOTIFICATION_AUTOACCEPT_MESSAGE,rescheduleMessage));
			parameters.add(new Parameter(OrderfulfillmentConstants.INHOME_OUTBOUND_NOTIFICATION_RESCHEDULE_CALL_CODE,InHomeNPSConstants.RESCHD_CALLCODE));
			response = ofHelper.runOrderFulfillmentProcess(rescheduleVO.getSoId(), signalType, reschedule, ident,parameters);

			String strSuccessCode = "00";
			String error = null;
			processResponse = ofMapper.mapProcessResponse(response);
			List<String> arrMsgList = processResponse.getMessages();

			for(String msg:arrMsgList){
				error = processResponse.getCode() + msg + System.getProperty("line.separator","\n");								
			}
			strSuccessCode = error.substring(0,2);

			if (strSuccessCode.equalsIgnoreCase(ResultsCode.SUCCES_OF_CALL.getCode())){

				Boolean autoAccept=false;
				String message="Reschedule Request processed successfully.";
				com.servicelive.orderfulfillment.domain.ServiceOrder serviceOrder = ofHelper.getServiceOrder(rescheduleVO.getSoId());	
				LOGGER.info("Service Date in DB  : "
						+ serviceOrder.getSchedule().getServiceDate1());
				LOGGER.info("Service Date in request  : "
						+ reschedule.getServiceDate1());

				if(reschedule.getServiceDateTypeId().equals(serviceOrder.getSchedule().getServiceDateTypeId())&&
						serviceOrder.getSchedule().getServiceDate1().equals(reschedule.getServiceDate1())&&
						serviceOrder.getSchedule().getServiceTimeStart().equals(reschedule.getServiceTimeStart())){

					if((reschedule.getServiceDateTypeId().equals(SOScheduleType.DATERANGE)&& 
							serviceOrder.getSchedule().getServiceDate2()!=null&&
							serviceOrder.getSchedule().getServiceDate2().equals(reschedule.getServiceDate2())&&
							serviceOrder.getSchedule().getServiceTimeEnd().equals(reschedule.getServiceTimeEnd()))||
							reschedule.getServiceDateTypeId().equals(SOScheduleType.SINGLEDAY)){
						autoAccept=true;
					}
				}
				else if(serviceOrder.getSchedule().getServiceDate1().equals(reschedule.getServiceDate1())&&
						serviceOrder.getSchedule().getServiceTimeStart().equals(reschedule.getServiceTimeStart())){					
					autoAccept=true;           		
				}				
				if(!autoAccept){
					//LOGGER.info("Not auto accept in api");
					if(serviceOrder.getReschedule()!=null&&serviceOrder.getReschedule().getServiceDate1()!=null){
						processResponse.setCode("001");
					}
					else{
						processResponse.setCode("003");
					}
				}
				else if(serviceOrder.getReschedule()!=null&&serviceOrder.getReschedule().getServiceDate1()!=null){
					autoAccept=false;
					processResponse.setCode("001");
				}
				else{
					processResponse.setCode("002");
					try
					{
						if(serviceOrder.getBuyerId().intValue()== 1000)
						{
							sendNotificationForRI(serviceOrder,rescheduleVO,reason);							
						}
					}
					catch(Exception e)
					{
						LOGGER.info("ExceptioninChoice"+e);
					}
				}
				if(StringUtils.isNotBlank(message)){
					processResponse.setMessage(message);					
				}
			}			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return processResponse;
	}*/

	private String formatDate(String format, Date date) {
		DateFormat formatter = new SimpleDateFormat(format);
		String formattedDate = null;
		try {
			formattedDate = formatter.format(date);
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return formattedDate;
	}

	public String getTimeZone(String modifiedDate, String format,
			String timeZone) {
		Date newDate = null;
		String actualTimeZone = timeZone.substring(0, 3);
		String dayLightTimeZone = timeZone.substring(4, 7);
		try {
			newDate = new SimpleDateFormat(format, Locale.ENGLISH)
			.parse(modifiedDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			LOGGER.info("Parse Exception SORescheduleService.java " + e);
		}
		TimeZone gmtTime = TimeZone.getTimeZone(actualTimeZone);
		if (gmtTime.inDaylightTime(newDate)) {
			return "(" + dayLightTimeZone + ")";
		}
		return "(" + actualTimeZone + ")";
	}


	public String getServiceLocTimeZone(String soId)throws BusinessServiceException {
		String timeZone="";
		try {
			timeZone = serviceOrderBo.getServiceLocTimeZone(soId);
		} catch (DataServiceException e) {
			LOGGER.error(this.getClass().getName()+ " " + e.getMessage());
		}
		return timeZone;
	}

	/*public InHomeRescheduleVO setInHomeRescheduleVO(SORescheduleVO rescheduleVO,RescheduleVO soRescheduleInfo,SOSchedule reschedule){
		InHomeRescheduleVO result = new InHomeRescheduleVO();
		String format = OrderConstants.RESCHEDULE_DATE_FORMAT;
		try{
			result.setRescheduleDate1(formatDate(format, rescheduleVO.getServiceDate1())+ " " + rescheduleVO.getRescheduleStartTime());
			if (SOScheduleType.DATERANGE == reschedule.getServiceDateTypeId()) {
				result.setRescheduleDate2(formatDate(format, rescheduleVO.getServiceDate2())+ " "+ rescheduleVO.getRescheduleEndTime());
			}
			result.setServiceDate1(soRescheduleInfo.getServiceDate1());
			result.setServiceDate2(soRescheduleInfo.getServiceDate2());
			result.setStartTime(soRescheduleInfo.getStartTime());
			result.setEndTime(soRescheduleInfo.getEndTime());
		} 
		catch (Exception e) {
			LOGGER.error("Error occured in MobilegenericBOImpl.setInHomeRescheduleVO()"+ e.getMessage());
		}
		return result;
	}*/

	public StringBuffer createMessageForReschedule(RescheduleVO soRescheduleInfo,SORescheduleVO rescheduleVO){

		HashMap<String, Object> rescheduleStartDate = null;
		HashMap<String, Object> rescheduleEndDate = null;
		StringBuffer reschedulePeriod = new StringBuffer();
		String format = OrderConstants.RESCHEDULE_DATE_FORMAT;
		try {
			if (null != soRescheduleInfo && null != soRescheduleInfo.getRescheduleServiceStartDate()) {
				if (null != soRescheduleInfo.getRescheduleServiceStartDate()&& null != soRescheduleInfo.getRescheduleServiceTime1()) {
					rescheduleStartDate = TimeUtils.convertGMTToGivenTimeZone(soRescheduleInfo.getRescheduleServiceStartDate(),soRescheduleInfo.getRescheduleServiceTime1(),
							soRescheduleInfo.getServiceLocnTimeZone());
					if (null != rescheduleStartDate && !rescheduleStartDate.isEmpty()) {
						reschedulePeriod.append("<br/>Provider edited the reschedule request from ");
						reschedulePeriod.append(formatDate(format,(Date) rescheduleStartDate.get(OrderConstants.GMT_DATE)));
						reschedulePeriod.append(" ");
						reschedulePeriod.append((String) rescheduleStartDate.get(OrderConstants.GMT_TIME));
					}
				}
				if (null != soRescheduleInfo.getRescheduleServiceEndDate()&& null != soRescheduleInfo.getRescheduleServiceTime2()) {
					rescheduleEndDate = TimeUtils.convertGMTToGivenTimeZone(soRescheduleInfo.getRescheduleServiceEndDate(),soRescheduleInfo.getRescheduleServiceTime2(),
							soRescheduleInfo.getServiceLocnTimeZone());
					if (null != rescheduleEndDate && !rescheduleEndDate.isEmpty()) {
						reschedulePeriod.append(" - ");
						reschedulePeriod.append(formatDate(format,(Date) rescheduleEndDate.get(OrderConstants.GMT_DATE)));
						reschedulePeriod.append(" ");
						reschedulePeriod.append((String) rescheduleEndDate.get(OrderConstants.GMT_TIME));
					}
				}
				reschedulePeriod.append(" "+ getTimeZone(formatDate(format,(Date) rescheduleStartDate.get(OrderConstants.GMT_DATE))+ " "+ (String) rescheduleStartDate.get(OrderConstants.GMT_TIME),
						OrderConstants.RESCHEDULE_DATE_TIME_STAMP_FORMAT1,soRescheduleInfo.getServiceLocnTimeZone()));
				if (null != rescheduleVO.getServiceDate1() && null != rescheduleVO.getRescheduleStartTime()) {
					reschedulePeriod.append(" to "+ formatDate(format, rescheduleVO.getServiceDate1())+ " "+ rescheduleVO.getRescheduleStartTime());
				}
				if (null != rescheduleVO.getServiceDate2() && null != rescheduleVO.getRescheduleEndTime()) {
					reschedulePeriod.append(" - "+ formatDate(format, rescheduleVO.getServiceDate2())+ " "+ rescheduleVO.getRescheduleEndTime());
				}
				reschedulePeriod.append(" "+ getTimeZone(formatDate(format, rescheduleVO.getServiceDate1())+ " "+ rescheduleVO.getRescheduleStartTime(),
						OrderConstants.RESCHEDULE_DATE_TIME_STAMP_FORMAT1,soRescheduleInfo.getServiceLocnTimeZone()));
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in edit reschedule:" + e);
		}
		return reschedulePeriod;
	}

	/*public void sendNotificationForRI(com.servicelive.orderfulfillment.domain.ServiceOrder serviceOrder,SORescheduleVO rescheduleVO,String reason){

		RequestOrder order=new RequestOrder();

		BuyerOutboundNotificationVO logging=new BuyerOutboundNotificationVO();
		try {
			logging=buyerOutBoundNotificationService.getLoggingDetails(serviceOrder.getSoId());
		} catch (BusinessServiceException e) {
			LOGGER.info("error in getting so Logging"+e);
		}
		String modificationId=logging.getEntityId().toString();							
		if(null!=logging.getEntityId())
			modificationId=logging.getEntityId().toString();

		// convert the reschedule date date in service order Timezone.
		TimeZone timeZone=TimeZone.getTimeZone(serviceOrder.getServiceLocationTimeZone());
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
		String fromDate=formatter.format(serviceOrder.getSchedule().getServiceDate1());
		String serviceTimeStart = serviceOrder.getSchedule().getServiceTimeStart();
		String serviceTimeEnd = "";
		if(null!=serviceOrder.getSchedule().getServiceTimeEnd())
			serviceTimeEnd=serviceOrder.getSchedule().getServiceTimeEnd();

		//set rescheduled Date for request Xml.
		order.setServiceScheduleDate(fromDate);
		order.setServiceScheduleFromTime(serviceTimeStart);
		if(StringUtils.isBlank(serviceTimeEnd))
		{
			order.setServiceScheduletoTime(serviceTimeStart);
		}
		else
		{
			order.setServiceScheduletoTime(serviceTimeEnd);
		}
		order.setServiceOrderRescheduledFlag(BuyerOutBoundConstants.RESHEDULE_FLAG_YES);

		// get the Modified date(current date) in server TimeZone.
		Calendar calender=Calendar.getInstance();
		SimpleDateFormat timeFormatter=new SimpleDateFormat("hh:mm a");
		String modifiedfromDate=timeFormatter.format(calender.getTime());
		Calendar modificationDateTime = TimeChangeUtil.getCalTimeFromParts(calender.getTime(), modifiedfromDate,calender.getTimeZone());
		Date modificationDate = TimeChangeUtil.getDate(modificationDateTime, timeZone);
		String modificationDateValue=formatter.format(modificationDate);
		String modificationTime = TimeChangeUtil.getTimeString(modificationDateTime, timeZone);

		RequestRescheduleInfo requestRescheduleInfo=new RequestRescheduleInfo();

		//set the modified date for request xml.
		requestRescheduleInfo.setReschedCancelModificationDate(modificationDateValue);
		requestRescheduleInfo.setReschedCancelModificationTime(modificationTime);
		requestRescheduleInfo.setRescheduleModificationID(modificationId);
		requestRescheduleInfo.setRescheduleReasonCode(reason);
		requestRescheduleInfo.setRescheduleRsnCdDescription(rescheduleVO.getRescheduleComments());	

		//set the reschedule Information for request xml
		RequestMsgBody requestMsgBody=new RequestMsgBody();
		RequestOrders orders=new RequestOrders();
		List<RequestOrder> orderList=new ArrayList<RequestOrder>();
		RequestReschedInformation requestReschedInformation=new RequestReschedInformation();
		List<RequestRescheduleInfo> requestRescheduleInf=new ArrayList<RequestRescheduleInfo>();
		requestRescheduleInf.add(requestRescheduleInfo);
		requestReschedInformation.setRequestRescheduleInf(requestRescheduleInf);
		order.setRequestReschedInformation(requestReschedInformation);
		orderList.add(order);
		orders.setOrder(orderList);
		requestMsgBody.setOrders(orders);
		LOGGER.info("callservicChoice");
		//call service by passing the request Object.
		try {
			BuyerOutboundFailOverVO failoverVO = buyerOutBoundNotificationService.callService(requestMsgBody, serviceOrder.getSoId());
			if(null!=failoverVO)
				buyerOutBoundNotificationJMSService.callJMSService(failoverVO);
		} catch (BusinessServiceException e) {
			LOGGER.info("Error in NPS update for reschedule"+e);
		}
	}*/
	public RequestBidVO fetchServiceOrderDetailsForBid(RequestBidVO bidVO)throws BusinessServiceException {
		try{
			bidVO = mobileGenericDao.fetchServiceOrderDetailsForBid(bidVO);
		}catch (Exception e) {
			LOGGER.info("Error in fetching bid details for the order"+e);
			throw new BusinessServiceException(e.getMessage());
		}
		return bidVO;
	}
	public IMobileSOManagementBO getMobileSOManagementBO() {
		return mobileSOManagementBO;
	}
	public void setMobileSOManagementBO(IMobileSOManagementBO mobileSOManagementBO) {
		this.mobileSOManagementBO = mobileSOManagementBO;
	}
	/**
	 * @param soId
	 * @param source
	 * method to validate if service order is ready for pre-call/confirm appointment
	 * 
	 * @return
	 *//*
	public boolean validateServiceOrder(String soId, String source) throws BusinessServiceException{

		try{
			if(MPConstants.PRE_CALL.equals(source)){
				if(checkIfReadyForPreCall(soId)){
					return true;
				}
			}
			else if(MPConstants.CONFIRM_APPOINTMENT.equals(source)){
				if(checkIfReadyForConfirmAppointment(soId)){
					return true;
				}
			}
		}
		catch(BusinessServiceException e){
			throw e;
		}
		return false;
	}

	*//**
	 * @param soId
	 * @return
	 *//*
	private boolean checkIfReadyForConfirmAppointment(String soId) throws BusinessServiceException{
		try{
			String soIdFetched = mobileGenericDao.checkIfReadyForConfirmAppointment(soId);
			if(null!=soIdFetched){
				return true;
			}
			else{
				return false;
			}
		}
		catch(DataServiceException e){
			throw new BusinessServiceException(e);
		}
	}
	*//**
	 * @param soId
	 * @return
	 *//*
	private boolean checkIfReadyForPreCall(String soId) throws BusinessServiceException {
		try{
			String soIdFetched = mobileGenericDao.checkIfReadyForPreCall(soId);
			if(null!=soIdFetched){
				return true;
			}
			else{
				return false;
			}
		}
		catch(DataServiceException e){
			throw new BusinessServiceException(e);
		}
	}
	*//**
	 * @param soId
	 * @return
	 * method to check if the order is scheduled for today and if so, whether it is assigned
	 *//*
	public boolean validateServiceOrderAssignment(String soId) throws BusinessServiceException {
		ServiceOrder serviceOrder = null;
		try{
			serviceOrder = mobileGenericDao.getServiceOrderAssignmentAndScheduleDetails(soId);
			if(checkIfApptIsToday(serviceOrder)){
				if(null == serviceOrder.getAcceptedResourceId()){
					return true;
				}
			}
		}
		catch(BusinessServiceException e){
			throw e;
		}
		catch(Exception e){
			LOGGER.error("Error Occured Inside validateServiceOrderAssignment() for So: "+soId+" : "+e.getMessage());
		}
		return false;
	}

	*//**
	 * @param serviceOrder
	 * @return
	 * @throws BusinessServiceException
	 * method to check if appointment is scheduled for today
	 *//*
	private boolean checkIfApptIsToday(ServiceOrder serviceOrder) throws BusinessServiceException{
		String timeZone =null;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try{
			if (serviceOrder != null) {
				GMTToGivenTimeZone(serviceOrder);
			}
			if (serviceOrder.getServiceDate1() != null) {

				timeZone = serviceOrder.getServiceLocationTimeZone();
				Calendar calendarNow = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
				Timestamp serviceDate1 = serviceOrder.getServiceDate1();
				String dateNowString = dateFormat.format(calendarNow.getTime());
				Date dateNow = dateFormat.parse(dateNowString);
				Date serviceDate = new Date(serviceDate1.getTime());
				String serviceDateString = dateFormat.format(serviceDate);
				serviceDate = dateFormat.parse(serviceDateString);
				Calendar serviceCalendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
				serviceCalendar.setTime(serviceDate);
				calendarNow.setTime(dateNow);
				if(0 == serviceCalendar.compareTo(calendarNow)){
					return true;
				}
			}
		}
		catch(Exception e){
			LOGGER.error("Error Occured Inside validateServiceOrderAssignment() for So: "+serviceOrder+" : "+e.getMessage());
		}
		return false;
	}
	*//**
	 * @param serviceOrder
	 * to convert to given time zone
	 *//*
	private static void GMTToGivenTimeZone(ServiceOrder serviceOrder) {
		HashMap<String, Object> serviceDate1 = null;
		HashMap<String, Object> serviceDate2 = null;
		String date1 = "";
		String date2 = "";
		String time1 = "";
		String time2 = "";
		DateFormat sdfReturn = new SimpleDateFormat("MM/dd/yy");
		Date sDate1 = null;
		Date sDate2 =null;

		HashMap<String, String> formattedDates = new HashMap<String, String>();
		serviceDate1 = TimeUtils.convertGMTToGivenTimeZone(serviceOrder.getServiceDate1(), serviceOrder.getServiceTimeStart(), serviceOrder.getServiceLocationTimeZone());
		if (serviceDate1 != null && !serviceDate1.isEmpty()) {
			date1 = sdfReturn.format((serviceDate1.get(OrderConstants.GMT_DATE)));
			time1 = (String) serviceDate1.get(OrderConstants.GMT_TIME);
			formattedDates.put("date1", date1);
			formattedDates.put("time1", time1);
			try {
				sDate1 = sdfReturn.parse(date1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			Timestamp serviceDateStamp1 = new Timestamp(sDate1.getTime());
			serviceOrder.setServiceDate1(serviceDateStamp1);
			serviceOrder.setServiceTimeStart(time1);
		}
		if (serviceOrder.getServiceDate2() != null && serviceOrder.getServiceTimeEnd() != null) {
			serviceDate2 = TimeUtils.convertGMTToGivenTimeZone(serviceOrder.getServiceDate2(), serviceOrder.getServiceTimeEnd(), serviceOrder.getServiceLocationTimeZone());
			if (serviceDate2 != null && !serviceDate2.isEmpty()) {
				date2 = sdfReturn.format(serviceDate2.get(OrderConstants.GMT_DATE));
				time2 = (String) serviceDate2.get(OrderConstants.GMT_TIME);
				formattedDates.put("date2", date2);
				formattedDates.put("time2", time2);
				try {
					sDate2 = sdfReturn.parse(date2);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Timestamp serviceDateStamp2 = new Timestamp(sDate2.getTime());
				serviceOrder.setServiceDate2(serviceDateStamp2);
				serviceOrder.setServiceTimeEnd(time2);
			}	
		}
	}
	*//**
	 * @param soId
	 * @param custAvailableRespCode
	 * @return
	 *//*
	public boolean validateServiceOrderConfirmedAppointment(String soId,
			Integer custAvailableRespCode) throws BusinessServiceException{
		ServiceOrder serviceOrder = null;
		try{
			serviceOrder = mobileGenericDao.getServiceOrderAssignmentAndScheduleDetails(soId);
			if(checkIfApptIsToday(serviceOrder)){
				if(null == custAvailableRespCode){
					return true;
				}
			}
		}
		catch(BusinessServiceException e){
			throw e;
		}
		catch(Exception e){
			LOGGER.error("Error Occured Inside validateServiceOrderAssignment() for So: "+soId+" : "+e.getMessage());
		}
		return false;

	}*/

	public ForgetUnamePwdVO  loadUserProfile(ForgetUnamePwdVO forgetUnamePwdVO) throws BusinessServiceException{

		LostUsernameVO lostUsernameVO=null;
		try{
			lostUsernameVO = forgotUsernameBO.loadLitLostUsereProfile(forgetUnamePwdVO.getEmail(),
					forgetUnamePwdVO.getUserName());
			if(lostUsernameVO!= null){
				forgetUnamePwdVO = mapUserProfile(forgetUnamePwdVO,lostUsernameVO);
			}
		} catch(Exception ex) {
			LOGGER
			.error("MobileGenericBOImpl.loadUserProfile(): "+forgetUnamePwdVO.getUserName());
			LOGGER
			.error("MobileGenericBOImpl.loadUserProfile(): "+ex.getMessage());
			throw new BusinessServiceException(ex);
		}
		return forgetUnamePwdVO;
	}

	private ForgetUnamePwdVO mapUserProfile(ForgetUnamePwdVO forgetUnamePwdVO, LostUsernameVO lostUsernameVO) throws BusinessServiceException{

		forgetUnamePwdVO.setEmail(lostUsernameVO.getEmailAddress());
		forgetUnamePwdVO.setUserName(lostUsernameVO.getUserName());

		forgetUnamePwdVO.setQuestionId(lostUsernameVO.getQuestionId());
		forgetUnamePwdVO.setQuestionTxt(lostUsernameVO.getQuestionTxt());
		forgetUnamePwdVO.setQuestionTxtAnswer(lostUsernameVO.getQuestionTxtAnswer());

		forgetUnamePwdVO.setPwdInd(lostUsernameVO.getPwdInd());

		//if no question id exists, then set generate pwd indicator 1 for sending interim pwd email
		if (forgetUnamePwdVO.getPwdInd() == PublicAPIConstant.INTEGER_ZERO
				&& (forgetUnamePwdVO.getQuestionId() == null || forgetUnamePwdVO.getQuestionId().equalsIgnoreCase(PublicAPIConstant.ZERO))){
			forgetUnamePwdVO.setPwdInd(PublicAPIConstant.INTEGER_ONE);
		}

		forgetUnamePwdVO.setZip(lostUsernameVO.getZip());
		forgetUnamePwdVO.setBusinessName(lostUsernameVO.getBusinessName());
		forgetUnamePwdVO.setPhoneNo(lostUsernameVO.getPhoneNo());
		forgetUnamePwdVO.setLockedInd(lostUsernameVO.getLockedInd());		
		forgetUnamePwdVO.setModifiedDate(lostUsernameVO.getModifiedDate());

		forgetUnamePwdVO.setUserProfileExists(true);
		forgetUnamePwdVO.setUserId(String.valueOf(lostUsernameVO.getUserId()));
		try{
			// get verification count
			int count = forgotUsernameBO.getVerificationCount(forgetUnamePwdVO.getUserName());
			forgetUnamePwdVO.setVerificationCount(count);
		}
		catch(Exception e){
			LOGGER.error("Fatal error occured in MobileGenericBOImpl.mapUserProfile:username:"+forgetUnamePwdVO.getUserName());
			LOGGER.error("Fatal error occured in MobileGenericBOImpl.mapUserProfile():"+e.getMessage());
			//error in forget password
			throw new BusinessServiceException(e);
		}
		
		return forgetUnamePwdVO;
	}

	
	/** SL-20452: Method to get Response for Forget Password Service1
	 * @param forgetUnamePwdVO
	 * @return ForgetUnamePwdVO
	 */
	public ForgetUnamePwdVO getResponseForPasswordReset(ForgetUnamePwdVO forgetUnamePwdVO) throws BusinessServiceException{
		Results results = new Results(); 
		try{
			LostUsernameVO lostUsernameVO= new LostUsernameVO();
			lostUsernameVO.setUserName(forgetUnamePwdVO.getUserName());
			lostUsernameVO.setPwdInd(forgetUnamePwdVO.getPwdInd());
			lostUsernameVO.setEmailAddress(forgetUnamePwdVO.getEmail());

			if(1==forgetUnamePwdVO.getPwdInd()){
				//reset password and send email
				lostUsernameVO=(LostUsernameVO)forgotUsernameBO.validateAns(lostUsernameVO);

				if(lostUsernameVO.getSuccessAns()){
					String userName=forgetUnamePwdVO.getUserName();
					if (StringUtils.isNotBlank(userName)) {
						String resourceId = forgotUsernameBO.getResourceIdFromUserName(userName);
						if (StringUtils.isNotBlank(resourceId)) {
							userManagementBO.expireMobileTokenforFrontEnd(Integer.parseInt(resourceId),MPConstants.ACTIVE);
						}
					}
					forgetUnamePwdVO.setQuestionId(null);
					results = Results.getSuccess(ResultsCode.RESET_PASSWORD_EMAIL_SEND_SUCCESS.getCode(), ResultsCode.RESET_PASSWORD_EMAIL_SEND_SUCCESS.getMessage());
					forgetUnamePwdVO.setResults(results);
				}else{
					//error in reset password & send email
					//throw new BusinessServiceException("Error occured while reset password and send email functionality MobileGenericBOImpl.getResponseForPasswordReset()");
					results = Results.getError(
							ResultsCode.FORGET_UNAME_PWD_RESET_EMAIL_ERROR
							.getMessage(),
							ResultsCode.FORGET_UNAME_PWD_RESET_EMAIL_ERROR
							.getCode());
					forgetUnamePwdVO.setResults(results);
				}
			}else{
				//get security question
				lostUsernameVO=(LostUsernameVO)forgotUsernameBO.getSecQuestionForUserName(lostUsernameVO);
				forgetUnamePwdVO.setQuestionId(lostUsernameVO.getQuestionId());
				forgetUnamePwdVO.setQuestionTxt(lostUsernameVO.getQuestionTxt());

				results = Results.getSuccess(ResultsCode.FORGET_UNAME_PWD_SECURITY_QN
						.getMessage());
				forgetUnamePwdVO.setResults(results);
			}

		}catch(Exception ex){
			LOGGER.error("Fatal error occured in MobileGenericBOImpl.getResponseForPasswordReset():username:"+forgetUnamePwdVO.getUserName());
			LOGGER.error("Fatal error occured in MobileGenericBOImpl.getResponseForPasswordReset():"+ex.getMessage());
			//error in reset password & send email
			throw new BusinessServiceException(ex);
		}
		return forgetUnamePwdVO;
	}
	
	/** SL-20452: Method to get Response for Forget UserName Service1 with only email passed 
	 * @param forgetUnamePwdVO
	 * @return ForgetUnamePwdVO
	 */
	public ForgetUnamePwdVO getResponseForForgetUserNameForEmail(
			ForgetUnamePwdVO forgetUnamePwdVO) throws BusinessServiceException {
		Results results = new Results(); 
		try{
			List<LostUsernameVO> usersList = forgotUsernameBO.loadLostUsernameList(forgetUnamePwdVO.getEmail(),1);
			//if no user exists for the email, error
			if(usersList.isEmpty()){
				results = Results.getError(
						ResultsCode.FORGET_UNAME_PWD_NO_USER_EXISTS
						.getMessage(),
						ResultsCode.FORGET_UNAME_PWD_NO_USER_EXISTS
						.getCode());
				forgetUnamePwdVO.setResults(results);
			}
			//if more than one user exists, return the user list
			else if(usersList.size()>PublicAPIConstant.INTEGER_ONE){
				forgetUnamePwdVO.setMultipleUserExists(true);
				forgetUnamePwdVO = getUserDetailList(forgetUnamePwdVO,usersList);
			}
			// if only one user exists, return the corresponding security question of the user.
			else{
				LostUsernameVO lostUsernameVO =  usersList.get(0);
				forgetUnamePwdVO.setUserName(lostUsernameVO.getUserName());
				forgetUnamePwdVO= getSecurityQuestion(forgetUnamePwdVO,lostUsernameVO.getUserName(),null);
			}
		}catch (Exception e) {
			LOGGER.error("Fatal error occured in MobileGenericBOImpl.getResponseForForgetUserNameForEmail():username:"+forgetUnamePwdVO.getUserName());
			LOGGER.error("Fatal error occured in MobileGenericBOImpl.getResponseForForgetUserNameForEmail():"+e.getMessage());
			//error in forget username
			throw new BusinessServiceException(e);
		}
		return forgetUnamePwdVO;
	}

	/** SL-20452: Method to get Response for Forget UserName Service1 with email, UserId and firstName in request 
	 * @param forgetUnamePwdVO
	 * @return ForgetUnamePwdVO
	 */
	public ForgetUnamePwdVO getResponseForForgetUserName(
			ForgetUnamePwdVO forgetUnamePwdVO) throws BusinessServiceException {
		try{
			// if only one user exists, return the corresponding security question of the user.
			forgetUnamePwdVO= getSecurityQuestion(forgetUnamePwdVO,null, forgetUnamePwdVO.getUserId());
		}catch (Exception e) {
			LOGGER.error("Fatal error occured in MobileGenericBOImpl.getResponseForForgetUserName():userId:"+forgetUnamePwdVO.getUserId());
			LOGGER.error("Fatal error occured in MobileGenericBOImpl.getResponseForForgetUserName()"+e.getMessage());
			//error in forget username
			throw new BusinessServiceException(e);
		}
		return forgetUnamePwdVO;
	}

	/** SL-20452: private method to get userDetailList for Forget UserName Service1 with email in request when more than 1 user exist
	 * @param forgetUnamePwdVO
	 * @return ForgetUnamePwdVO
	 */
	private ForgetUnamePwdVO getUserDetailList(ForgetUnamePwdVO forgetUnamePwdVO, List<LostUsernameVO> usersList){
		Results results = new Results(); 
		List<UserDetailVO> userDetailList = new ArrayList<UserDetailVO>();
		for(LostUsernameVO lostUsernameVO: usersList){
			UserDetailVO userDetailVO = new UserDetailVO();
			if (StringUtils.isNotBlank(lostUsernameVO.getResourceId())){
				userDetailVO.setUserId(Integer.parseInt(lostUsernameVO.getResourceId()));
			}
			userDetailVO.setUserFirstName(lostUsernameVO.getFirstName()+" "+lostUsernameVO.getLastName());
			userDetailList.add(userDetailVO);
		}
		forgetUnamePwdVO.setUserDetailVOs(userDetailList);
		results = Results.getSuccess(ResultsCode.FORGET_UNAME_PWD_MULTIPLE_USERS
				.getCode(),ResultsCode.FORGET_UNAME_PWD_MULTIPLE_USERS
				.getMessage());
		forgetUnamePwdVO.setResults(results);
		return forgetUnamePwdVO;
	}

	/** SL-20452: private method to get security question for Forget UserName/Password Service1 
	 * @param forgetUnamePwdVO
	 * @return ForgetUnamePwdVO
	 */
	private ForgetUnamePwdVO getSecurityQuestion(ForgetUnamePwdVO forgetUnamePwdVO, String userName, String resourceId) throws BusinessServiceException{
		Results results = new Results(); 
		final int maxSecretQuestionAttempts = Integer
				.parseInt(PropertiesUtils
						.getPropertyValue(Constants.AppPropConstants.MAX_SECRET_QUESTION_ATTEMPTS_LIMIT));
	    int count = 0;
	    
		LostUsernameVO lostUsernameVO = forgotUsernameBO.loadLostUsername(userName, resourceId, 1);
		//fetching verification count
		if (null!=lostUsernameVO && StringUtils.isNotBlank(lostUsernameVO.getUserName())){
			count = forgotUsernameBO.getVerificationCount(lostUsernameVO.getUserName());
		}
	
		// if user does not exist
		if(null == lostUsernameVO){
			results = Results.getError(
					ResultsCode.FORGET_UNAME_PWD_NO_USER_EXISTS
					.getMessage(),
					ResultsCode.FORGET_UNAME_PWD_NO_USER_EXISTS
					.getCode());
			forgetUnamePwdVO.setResults(results);
		}
		// if user profile is locked
		else if (PublicAPIConstant.INTEGER_ONE== lostUsernameVO.getLockedInd()){
			results = Results.getError(
					ResultsCode.FORGET_UNAME_PWD_LOCKED_USER
					.getMessage(),
					ResultsCode.FORGET_UNAME_PWD_LOCKED_USER
					.getCode());
			forgetUnamePwdVO.setResults(results);
		}
		//if security question does not exist or verification count exceeded maximum limit
		else if (StringUtils.isBlank(lostUsernameVO.getQuestionTxt()) || count >= maxSecretQuestionAttempts || count == -1){
			results = Results.getError(
					ResultsCode.COUNT_EXCEEDED_SHOW_VERIFICATION_ZIP
					.getMessage(),
					ResultsCode.COUNT_EXCEEDED_SHOW_VERIFICATION_ZIP
					.getCode());
			forgetUnamePwdVO.setResults(results);
			forgetUnamePwdVO.setUserId(lostUsernameVO.getResourceId());
		}else{
			forgetUnamePwdVO.setQuestionId(lostUsernameVO.getQuestionId());
			forgetUnamePwdVO.setQuestionTxt(lostUsernameVO.getQuestionTxt());
			forgetUnamePwdVO.setUserName(lostUsernameVO.getUserName());
			forgetUnamePwdVO.setUserId(lostUsernameVO.getResourceId());
			results = Results.getSuccess(ResultsCode.FORGET_UNAME_PWD_SECURITY_QN
					.getMessage());
			forgetUnamePwdVO.setResults(results);
		}
		return forgetUnamePwdVO;
	}
	/**
	 * Method to accept service order or grouped order.
	 * @param soId
	 * @param groupId
	 * @param resourceId
	 * @param acceptByFirmInd
	 * @param securityContext
	 * @return
	 * @throws BusinessServiceException
	 */
	/*public ProcessResponse executeSOAccept(AcceptVO acceptVo,SecurityContext securityContext ) throws BusinessServiceException{
		ProcessResponse response =null;
		try{
			if(null!=acceptVo){
				if (StringUtils.isNotBlank(acceptVo.getGroupId())) {
					//Accept Grouped SO
					response= acceptGroupedOrder(acceptVo.getGroupId(), acceptVo.getAcceptedResourceId(), acceptVo.isAcceptByFirmInd(), securityContext);
				}else{
					//Accept Individual SO
					response= acceptServiceOrder(acceptVo.getSoId(),  acceptVo.getAcceptedResourceId(), acceptVo.isAcceptByFirmInd(), securityContext);
				}
			}

		}catch (BusinessServiceException e) {
			LOGGER.error("Exception in processing request for accepting service order"+ acceptVo.getSoId()+ e.getMessage());
		    throw new BusinessServiceException(e.getMessage());
		}
		return response;
	}*/

	public Integer getServiceOrderStatus(String soId) throws BusinessServiceException{
		Integer wfStateId = null;
		try{
			wfStateId = mobileGenericDao.getServiceOrderStatus(soId);	
		}catch (Exception e) {
			LOGGER.error("Exception in fetching service order status"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return wfStateId;

	}
	public boolean determineAcceptablity(String soId, String groupId)throws BusinessServiceException {
		boolean isAcceptable =false;
		int wfStatus =0;
		boolean isIndividualOrder =false;
		try{
			wfStatus = mobileGenericDao.getServiceOrderStatus(soId);
			if(StringUtils.isBlank(groupId)){
				isIndividualOrder =true;
			}
			isAcceptable = serviceOrderBo.determineAcceptabilityForMobile(isIndividualOrder, groupId, wfStatus);
		}catch (Exception e) {
			LOGGER.error("Exception in validating acceptablity"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return isAcceptable;
	}
	public ResultsCode validateSoAccept(AcceptVO acceptVO) throws BusinessServiceException {
		ResultsCode validationCode =null;
		String soId =null;
		String groupId=null;
		boolean isAcceptable= false;
		try{
			if(null!=acceptVO && null!= acceptVO.getSoId() && null!=  acceptVO.getFirmId()){
				soId = acceptVO.getSoId();
				groupId =acceptVO.getGroupId();

			}
			isAcceptable = determineAcceptablity(soId, groupId);
			if(isAcceptable){
				validationCode = validateRoleLevelAcceptance(acceptVO);
			}// Set ErrorCode if so is not acceptable at this stage.
			else{
				validationCode = ResultsCode.SO_ACCEPT_NOT_IN_ACCEPTABLE_STATE;
			}

		}catch (Exception e) {
			LOGGER.error("Exception in validating service order "+acceptVO.getSoId()+ "error"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return validationCode;
	}
	private List<ProviderResultVO> getRoutedProvidersForSo(String firmId,String soId,String groupId) throws BusinessServiceException {
		List<ProviderResultVO> routedProviders =null;
		//Fetching routed providers for single order
		if(StringUtils.isNotBlank(soId) && StringUtils.isBlank(groupId)){
			routedProviders = getEligibleProviders(firmId,soId);
		}//Fetching routed providers for grouped order
		else if(StringUtils.isNotBlank(soId) && StringUtils.isNotBlank(groupId)){
			routedProviders = getEligibleProvidersForGroup(firmId, groupId);
		}
		return routedProviders;
	}
	public ResultsCode validateRoleLevelAcceptance(AcceptVO acceptVO) throws BusinessServiceException{
		List<ProviderResultVO> routedProviders =null;
		Integer resourseIdUrl=null;
		Integer resourceIdRequest =null;
		boolean validId = Boolean.FALSE;
		boolean acceptByFirmInd =false;
		String vendorId=null;
		int roleId = 0;
		ResultsCode validationCode =null;
		String soId =null;
		String groupId=null;
		try{
			if(null!=acceptVO && null!= acceptVO.getResourceIdRequest()){
				acceptByFirmInd = acceptVO.isAcceptByFirmInd();
				resourseIdUrl = acceptVO.getResourceIdUrl();
				resourceIdRequest =acceptVO.getResourceIdRequest();
				vendorId = acceptVO.getFirmId().toString();
				roleId =  acceptVO.getRoleId();
				soId = acceptVO.getSoId();
				groupId =acceptVO.getGroupId();
			}
			// Fetching routed providers for role level validations.
			routedProviders = getRoutedProvidersForSo(vendorId,soId,groupId);
			if(roleId == MPConstants.ROLE_LEVEL_TWO.intValue()){
				if(acceptByFirmInd){
					validationCode = ResultsCode.PERMISSION_ERROR;
				}else{
					if(resourseIdUrl.equals(resourceIdRequest)){
						validId = validateRoutedProviders(routedProviders,resourceIdRequest);
						if(!validId){
							validationCode = ResultsCode.SO_ACCEPT_INVALID_PROVIDER_ID;
						}
					}// resourceId in url != resourceId in request
					else{
						validationCode = ResultsCode.SO_ACCEPT_INVALID_PROVIDER_ID;
					}
				}

			}//Role level 3 providers validations.
			else{
				if(!acceptByFirmInd){
					validId = validateRoutedProviders(routedProviders,resourceIdRequest);
					if(!validId){
						validationCode = ResultsCode.SO_ACCEPT_INVALID_PROVIDER_ID;
					}
				}
			}
		}catch (Exception e) {
			LOGGER.error("Exception in validating role level permission  service order "+acceptVO.getSoId()+ "error"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return validationCode;

	}
	private boolean validateRoutedProviders(List<ProviderResultVO> routedProviders, Integer resourceIdRequest) {
		boolean validId = Boolean.FALSE;
		if (null != routedProviders &&!routedProviders.isEmpty()) {
			for (ProviderResultVO providerResultVO : routedProviders) {
				if (providerResultVO.getResourceId() == resourceIdRequest.intValue()) {
					validId = Boolean.TRUE;
					break;
				}
			}
		}
		return validId;
	}
	public boolean isGroupedOrderInEditMode(String groupId)throws BusinessServiceException{
		boolean isSOInEditMode = false;

		try{
			isSOInEditMode = mobileGenericDao.isGroupedOrderInEditMode(groupId);
		}catch (Exception e) {
			LOGGER.error("error occured while accepting grouped Order grouped Order isGroupedOrderInEditMode()-->" + groupId);
			throw new BusinessServiceException(e.getMessage());
		}
		return isSOInEditMode;
	}

	/**
	 * Delete Mobile filter flow
	 * @param resourceId
	 * @param filterId
	 * @throws BusinessServiceException
	 */
	public int deleteFilter(Integer resourceId, String filterId) throws BusinessServiceException{
		int deletedFilters = 0;
		try{
			deletedFilters = mobileGenericDao.deleteFilter(resourceId, filterId);
		}catch (Exception e) {
			LOGGER.error("error occured while deleting the filter in MobileGenericBOImpl.deleteFilter()");
			throw new BusinessServiceException(e.getMessage());
		}
		return deletedFilters;
	}

	/**@param soStatusList 
	 * @Description: Getting Service Order status Id for the status Specified in List 
	 * @return  List<Integer>
	 * @throws BusinessServiceException
	 */
	public List<Integer> getServiceOrderStatus(List<String> soStatusList)throws BusinessServiceException {
		List<Integer> wfStatusList =new ArrayList<Integer>();
		List<SOStatusVO> statusVO = null;
		try{
			statusVO = mobileSOManagementBO.getServiceOrderStatus();
			if(null!= statusVO && !(statusVO.isEmpty() && null!= soStatusList &&!(soStatusList.isEmpty()))){
				for (String status : soStatusList) {
					for (SOStatusVO soStatusVO : statusVO) {
						if (status.equals(soStatusVO.getStatusString()))
							wfStatusList.add(soStatusVO.getStatusId());
					}
				}
			}
		}catch (Exception e) {
			LOGGER.error("Exception Occured in MobileGenericBOImpl-->getServiceOrderStatus()"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return wfStatusList;
	}
	/**@Description: Fetching List of service orders available for providers
	 * @param providerParamVO
	 * @return List<ProviderSOSearchVO>
	 * @throws BusinessServiceException
	 */ 
	public SoResultsVO getProviderSOList(ProviderParamVO providerParamVO) throws BusinessServiceException {
		SoResultsVO soResultsVo =null;
		Integer countOfRecords = null;
		try{
			countOfRecords = getCountOfRecords(providerParamVO);
			// Validating count of records against the last page available
			countOfRecords = validateCountOfRecords(countOfRecords, providerParamVO);
			soResultsVo = geSOListProvider(countOfRecords, providerParamVO);

		}catch (DataServiceException e) {
			LOGGER.error("Exception Occured in MobileGenericBOImpl-->getProviderSOSearchList()"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}catch (Exception e) {
			LOGGER.error("Exception Occured in MobileGenericBOImpl-->getProviderSOSearchList() while fetching CountOfRecords"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return soResultsVo;
	}

	private SoResultsVO geSOListProvider(Integer countOfRecords,ProviderParamVO providerParamVO) throws  Exception {
		SoResultsVO soResultsVo =null;
		List<ProviderSOSearchVO> soSearchVOs = null;
		if(null!= countOfRecords){
			//Setting Limit and Start Index for the Query
			providerParamVO.setNumberOfRecords(providerParamVO.getPageSize().intValue());
			providerParamVO.setStartIndex(providerParamVO.getNumberOfRecords());
			soSearchVOs = mobileGenericDao.getProviderSOList(providerParamVO);
			if(null!= soSearchVOs && !(soSearchVOs.isEmpty())){
				//Converting Service Date from GMT to Service Location 
				for(ProviderSOSearchVO result:soSearchVOs){
					result = mobileSOManagementBO.convertServiceDate(result);}
				soResultsVo = new SoResultsVO();
				//Setting Total Count of So
				soResultsVo.setTotalSoCount(countOfRecords);
				//Setting soList
				soResultsVo.setSoResultList(soSearchVOs);
			}
			else{
				soResultsVo = new SoResultsVO();
				//Setting Total Count of So
				soResultsVo.setTotalSoCount(0);
			}
		}else{
			/*Count becomes  Null if and only if the  page No requested is greater than
					    the available page no calculated using pageNo and pageSize **/
			soResultsVo = new SoResultsVO();
			//Setting Total Count of So
			soResultsVo.setTotalSoCount(null);

		}
		return soResultsVo;
	}
	/**
	 * @param providerParamVO
	 * @return
	 * @throws BusinessServiceException
	 */
	private Integer getCountOfRecords(ProviderParamVO providerParamVO) throws BusinessServiceException {
		Integer countOfRecords= null;
		try{
			countOfRecords = mobileGenericDao.validateCountOfRecords(providerParamVO);
		}catch (Exception e) {
			LOGGER.error("Exception Occured in MobileGenericBOImpl-->validateCountOfRecords()"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return countOfRecords;
	}


	/**
	 * @param countOfRecords
	 * @param providerParamVO
	 * @return
	 */
	private Integer validateCountOfRecords(Integer countOfRecords,ProviderParamVO providerParamVO){
		Integer count = null;
		if(null!= countOfRecords && countOfRecords.intValue() > 0){
			// Checking the last page No from request and validate 
			if(null!= providerParamVO.getPageNo() && null!=providerParamVO.getPageSize() ){
				// This is the available last page
				double lastPage = Math.ceil((double) countOfRecords/ providerParamVO.getPageSize()); 
				if(providerParamVO.getPageNo() > lastPage){
					return count;
				}else{
					count =  countOfRecords;
				}
			}
		}else if(null== countOfRecords || countOfRecords.intValue() == 0){
			// Count of records is null.
			count = 0;

		}
		return count;
	}
	/**
	 * Get the count of recieved orders
	 * If provider role is 2, get the count of all orders received for him
	 * If provider role is 3, get the count of all orders recieved for his firm admin
	 * @param criteriaVO
	 * @return recievedOrdersCount
	 */
	public Integer getRecievedOrdersCount(RecievedOrdersCriteriaVO criteriaVO) throws BusinessServiceException{
		Integer recievedOrdersCount = 0;
		try{
			recievedOrdersCount = mobileGenericDao.getRecievedOrdersCount(criteriaVO);
		}catch (Exception e) {
			LOGGER.error("Exception Occured in MobileGenericBOImpl-->getRecievedOrdersCount()"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return recievedOrdersCount;
	}

	/**
	 * Get the recieved orders list
	 * @param criteriaVO
	 * @return List<RecievedOrdersVO>
	 */
	public List<RecievedOrdersVO> getRecievedOrdersList(RecievedOrdersCriteriaVO criteriaVO) throws BusinessServiceException{
		List<RecievedOrdersVO> recievedOrdersList = null;
		try{
			recievedOrdersList = mobileGenericDao.getRecievedOrdersList(criteriaVO);
		}catch (Exception e) {
			LOGGER.error("Exception Occured in MobileGenericBOImpl-->getRecievedOrdersList()"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return recievedOrdersList;
	}
	/**
	 * Method to get the firmId of resource passed
	 * @param providerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer validateProviderId(Integer providerId)throws BusinessServiceException {
		Integer firmId = mobileSOManagementBO.validateProviderId(providerId.toString());
		return firmId;
	}

	/**
	 * Method to verify the logged in user is authorized to view the so details considering the aspects of role and status
	 * @param detailsVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean isAuthorizedToViewSODetails(SoDetailsVO detailsVO)throws BusinessServiceException {

		boolean isAuthorized = true;
		Integer soStatus = null;	
		try {
			if(null != detailsVO.getSoId()){
				soStatus = getServiceOrderStatus(detailsVO.getSoId());
			}
			if(OrderConstants.ROUTED_STATUS==soStatus.intValue()){
				isAuthorized = mobileGenericDao.isAuthorizedToViewSOInPosted(detailsVO);
			} 
			else if(OrderConstants.ACCEPTED_STATUS==soStatus.intValue() || OrderConstants.ACTIVE_STATUS==soStatus.intValue()
					||OrderConstants.PROBLEM_STATUS==soStatus.intValue()){
				isAuthorized = mobileGenericDao.isAuthorizedToViewBeyondPosted(detailsVO);
			}
		}
		catch (DataServiceException e) {
			LOGGER.error("error occured in isAuthorizedToViewSODetails()"+e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return isAuthorized;				
	}
	/**
	 * Method to fetch the details of the SO for mobile v3.0
	 * @param detailsVO
	 * @return RetrieveSODetailsMobileVO
	 * @throws BusinessServiceException
	 */
	public RetrieveSODetailsMobileVO getServiceOrderDetails(SoDetailsVO detailsVO) throws BusinessServiceException {
		RetrieveSODetailsMobileVO retrieveSODetailsMobileVO = new RetrieveSODetailsMobileVO();
		ServiceOrderDetailsVO serviceOrderDetails = null;
		List<ServiceDatetimeSlot> serviceDatetimeSlot = null;
		ScheduleServiceSlot scheduleServiceSlot  = null;
		ServiceDateTimeSlots serviceDatetimeSlots = null;
		String timezone = null;

		try {
			serviceOrderDetails = mobileGenericDao.getServiceOrderDetailsWithTrip(detailsVO);
			serviceDatetimeSlot = serviceOrderDao.getSODateTimeSlots(detailsVO.getSoId());
			String timezoneForSlots = serviceOrderDetails.getAppointment().getTimeZone();
			serviceOrderDetails = mapServiceOrderDetailsWithTrip(serviceOrderDetails);
			retrieveSODetailsMobileVO.setSoDetails(serviceOrderDetails);
			if(null !=serviceDatetimeSlot && serviceDatetimeSlot.size()>0 && null != serviceOrderDetails.getAppointment()){
				scheduleServiceSlot = new ScheduleServiceSlot();
				serviceDatetimeSlots = new ServiceDateTimeSlots();
				scheduleServiceSlot.setServiceDatetimeSlots(serviceDatetimeSlots);
				retrieveSODetailsMobileVO.getSoDetails().setScheduleServiceSlot(scheduleServiceSlot);
				mapSlots(retrieveSODetailsMobileVO,serviceDatetimeSlot,timezoneForSlots);
				GMTToGivenTimeZoneForSlots(retrieveSODetailsMobileVO);
			}
			

			timezone = retrieveSODetailsMobileVO.getSoDetails().getAppointment().getTimeZone();
			retrieveSODetailsMobileVO.setTimeZone(timezone);
			if (retrieveSODetailsMobileVO.getSoDetails()!=null){// time window
				GMTToGivenTimeZone(retrieveSODetailsMobileVO.getSoDetails().
						getAppointment(),retrieveSODetailsMobileVO.getSoDetails().getServiceLocation());

			}
			if (retrieveSODetailsMobileVO.getSoDetails() != null && retrieveSODetailsMobileVO.getSoDetails().getAppointment() != null
					&& retrieveSODetailsMobileVO.getSoDetails().getNotes() != null){

				try {
					GMTToGivenTimeZone(retrieveSODetailsMobileVO.getSoDetails().
							getAppointment(),retrieveSODetailsMobileVO.getSoDetails().getNotes(),retrieveSODetailsMobileVO.getSoDetails().getServiceLocation());
				} catch (ParseException e) {
					LOGGER.error("error occured in GMTToGivenTimeZone(appointment,notes,serviceLocation)"+e.getMessage());
					e.printStackTrace();
				}				
			}
			if(retrieveSODetailsMobileVO.getSoDetails() != null && retrieveSODetailsMobileVO.getSoDetails().getAppointment() != null&&
					retrieveSODetailsMobileVO.getSoDetails().getDocuments()!=null){

				try {
					GMTToGivenTimeZone(retrieveSODetailsMobileVO.getSoDetails().
							getAppointment(), retrieveSODetailsMobileVO.getSoDetails().getDocuments());
				} catch (ParseException e) {
					LOGGER.error("error occured in GMTToGivenTimeZone(appointment,documents)"+e.getMessage());
					e.printStackTrace();
				}				
			}
			if(null != retrieveSODetailsMobileVO.getSoDetails() && null != retrieveSODetailsMobileVO.getSoDetails().getAppointment()
					&& null != retrieveSODetailsMobileVO.getSoDetails().getTripDetails()){
				GMTToGivenTimeZone(retrieveSODetailsMobileVO.getSoDetails().getTripDetails(),
						timezone,retrieveSODetailsMobileVO.getSoDetails().getServiceLocation());
			}
			if(null != retrieveSODetailsMobileVO.getSoDetails() && null != retrieveSODetailsMobileVO.getSoDetails().getAppointment()
					&& null != retrieveSODetailsMobileVO.getSoDetails().getLatestTrip()){
				GMTToGivenTimeZone(retrieveSODetailsMobileVO.getSoDetails().getLatestTrip(),
						timezone,retrieveSODetailsMobileVO.getSoDetails().getServiceLocation());
			}
		} catch (DataServiceException e) {
			LOGGER.error("error occured in isAuthorizedToViewSODetails()"+e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}

		return retrieveSODetailsMobileVO;
	}
	/**
	 * Map service order details to the ServiceOrderRescheduleVO object
	 * @param serviceOrderDetails
	 * @return
	 */
	public RescheduleDetailsVO getSORescheduleDetails(String soId,String zipCode)throws BusinessServiceException{
		RescheduleDetailsVO rescheduleDetailsVO =  new RescheduleDetailsVO();
		//to map reschedule object to SO time zome
		try {
			rescheduleDetailsVO = mobileGenericDao.getRescheduleDetailsForSO(soId);
			if(null!=rescheduleDetailsVO && rescheduleDetailsVO.getRescheduleServiceDate1() !=null){
				GMTToGivenTimeZone(rescheduleDetailsVO, zipCode);
			}
		} catch (DataServiceException e) {
			LOGGER.error("error occured in getSORescheduleDetails()"+e.getMessage());
			throw new BusinessServiceException(e);
		}
		return rescheduleDetailsVO;
	}
	
	/**
	 * Fetch counter offer details of the SO
	 * @param soId firmId
	 * @return List<ProviderResultVO>
	 */
	public List<ProviderResultVO> getCounteredResourceDetailsList(String soId, String firmId) throws BusinessServiceException{
		List<ProviderResultVO> counteredResourceDetailsList=  null;
		try{
			counteredResourceDetailsList = mobileGenericDao.getCounteredResourceDetailsList(soId,firmId);
		}catch (DataServiceException e) {
			LOGGER.error("error occured in getCounteredResourceDetailsList()"+e);
			throw new BusinessServiceException(e);
		}
		return counteredResourceDetailsList;
		
	}
	private ServiceOrderDetailsVO mapServiceOrderDetailsWithTrip(ServiceOrderDetailsVO serviceOrderDetails) {

		if (null != serviceOrderDetails.getBuyer()) {
			if (null != serviceOrderDetails.getBuyer().getMaxTimeWindow()) {
				serviceOrderDetails.getAppointment().setMaxTimeWindow(
						serviceOrderDetails.getBuyer().getMaxTimeWindow());
			}
			if (null != serviceOrderDetails.getBuyer().getMinTimeWindow()) {
				serviceOrderDetails.getAppointment().setMinTimeWindow(
						serviceOrderDetails.getBuyer().getMinTimeWindow());
			}
			//format Buyer phone numbers
			String buyerPrimaryPhone=serviceOrderDetails.getBuyer().getPrimaryphone();
			serviceOrderDetails.getBuyer().setPrimaryphone(formatPhoneNumber(buyerPrimaryPhone));
			String buyerAltPhone = serviceOrderDetails.getBuyer().getAlternatePhone();
			serviceOrderDetails.getBuyer().setAlternatePhone(formatPhoneNumber(buyerAltPhone));
			String fax = serviceOrderDetails.getBuyer().getFax();
			serviceOrderDetails.getBuyer().setFax(formatPhoneNumber(fax));
		}		
		Map<String, String> param = new HashMap<String, String>();
		param.put("soid", serviceOrderDetails.getOrderDetails().getSoId());
		String primaryPhone = "";
		String alternatePhone = "";
		// for getting primary phone number and alternate phone number
		if (null != serviceOrderDetails.getServiceLocation()
				&& null != serviceOrderDetails.getServiceLocation()
				.getSoContactId()) {
			// putting so contactId in the map
			param.put("socontactid", serviceOrderDetails.getServiceLocation()
					.getSoContactId());
			// calling common method for getting primary phone number and
			// alternate phone number
			primaryPhone = getContactPrimaryPhone(param);
			// setting retrieved primary phone number and alternate phone number
			// into
			// serviceOrderDetails object
			alternatePhone = getContactAlternatePhone(param);
			serviceOrderDetails.getServiceLocation().setPrimaryPhone(primaryPhone);
			serviceOrderDetails.getServiceLocation().setAlternatePhone(alternatePhone);
		}
		// for getting service location alternate phone number
		if (null != serviceOrderDetails.getAlternateServiceLocation()
				&& null != serviceOrderDetails.getAlternateServiceLocation()
				.getSoContactId()) {

			// putting so contactId in the map
			param.remove("socontactid");
			param.put("socontactid", serviceOrderDetails
					.getAlternateServiceLocation().getSoContactId());
			// calling common method for getting primary phone number and
			// alternate phone number
			primaryPhone = getContactPrimaryPhone(param);
			alternatePhone = getContactAlternatePhone(param);
			// setting retrieved primary phone number and alternate phone number
			// into
			// serviceOrderDetails object
			serviceOrderDetails.getAlternateServiceLocation()
			.setPrimaryPhone(primaryPhone);
			serviceOrderDetails.getAlternateServiceLocation()
			.setAlternatePhone(alternatePhone);
		}
		//-----------------------------------------------
		//--> Finishing Formatting Service Location and Alternate service Location

		//--> Formatting BuyerReferences

		// checking BuyerReferences and setting BuyerRefPresentInd appropriately
		if (null != serviceOrderDetails.getBuyerReferences()
				&& null != serviceOrderDetails.getBuyerReferences()
				.getBuyerReference()
				&& serviceOrderDetails.getBuyerReferences().getBuyerReference()
				.size() != 0) {
			serviceOrderDetails.setBuyerRefPresentInd(MPConstants.YES);
		} else {
			serviceOrderDetails.setBuyerRefPresentInd(MPConstants.NO);
		}
		//-----------------------------------------------
		//--> Finished Formatting BuyerReferences

		//-->Formatting document types
		//iterating fetched Documents and setting  document types as empty if
		//not an allowed document type		
		if (null != serviceOrderDetails.getDocuments()
				&& null != serviceOrderDetails.getDocuments().getDocument()
				&& serviceOrderDetails.getDocuments().getDocument().size() != 0&& null!=serviceOrderDetails.getBuyer().getBuyerUserId()) {
			//List<Document> docList=formatDocumentTypes(serviceOrderDetails.getDocuments().getDocument(),serviceOrderDetails.getBuyer().getBuyerUserId());
			// serviceOrderDetails.getDocuments().setDocument(docList);
			
			// To remove documents in case of customer signature or provider signature while fetching so details v2.0
						List<DocumentDetailsVO> removeList = new ArrayList<DocumentDetailsVO>();
						for(DocumentDetailsVO document: serviceOrderDetails.getDocuments().getDocument()){
							if(null != document){
								if(MPConstants.CUSTOMER_SIGNATURE.equalsIgnoreCase(document.getDocumentType())||MPConstants.PROVIDER_SIGNATURE.equalsIgnoreCase(document.getDocumentType())){
									removeList.add(document);
								}
							}
						}
						serviceOrderDetails.getDocuments().getDocument().removeAll(removeList);
		}
		//-----------------------------------------------
		//-->Finished Formatting document types
		//-- > Formatting Part details
		//setting Count of parts

		if (null != serviceOrderDetails.getParts()
				&& null != serviceOrderDetails.getParts().getPart()) {
			serviceOrderDetails.getParts().setCountofParts(
					serviceOrderDetails.getParts().getPart().size());
		}

		//format provider phone numbers
		if(null!=serviceOrderDetails.getProvider()){
			String providerPrimaryPhone=serviceOrderDetails.getProvider().getProviderPrimaryPhone();
			serviceOrderDetails.getProvider().setProviderPrimaryPhone(formatPhoneNumber(providerPrimaryPhone));
			String providerAltPhone = serviceOrderDetails.getProvider().getProviderAltPhone();
			serviceOrderDetails.getProvider().setProviderAltPhone(formatPhoneNumber(providerAltPhone));
			String firmPrimaryPhone=serviceOrderDetails.getProvider().getFirmPrimaryPhone();
			serviceOrderDetails.getProvider().setFirmPrimaryPhone(formatPhoneNumber(firmPrimaryPhone));
			String firmAltPhone=serviceOrderDetails.getProvider().getFirmAltPhone();
			serviceOrderDetails.getProvider().setFirmAltPhone(formatPhoneNumber(firmAltPhone));
			String smsNum=serviceOrderDetails.getProvider().getSmsNumber();
			serviceOrderDetails.getProvider().setSmsNumber(smsNum);
		}

		if(null!=serviceOrderDetails.getParts()&& null!=serviceOrderDetails.getParts().getPart()){
			List<PartVO> parts = serviceOrderDetails.getParts().getPart();
			for(PartVO part : parts){
				PickUpLocationVO locn = part.getPickupLocation();
				if(null==locn||(null!=locn && (null == locn.getPickupLocationStreet1() || locn.getPickupLocationStreet1().equals(MPConstants.EMPTY_STRING)) && (null == locn.getPickupLocationStreet2() || locn.getPickupLocationStreet2().equals(MPConstants.EMPTY_STRING)) && ( null == locn.getPickupLocationCity() || locn.getPickupLocationCity().equals(MPConstants.EMPTY_STRING)))){
					part.setPickupLocationAvailability(MPConstants.NOT_AVAILABLE);
					part.setPickupLocation(null);
				}else{
					part.setPickupLocationAvailability(MPConstants.AVAILABLE);
				}
			}
		}

		// Decode the HTML encoded notes and support notes
		if(null!= serviceOrderDetails.getNotes()){
			for(NoteVO note : serviceOrderDetails.getNotes().getNote()){
				if(StringUtils.isNotEmpty(note.getNoteSubject())){
					String decodedSub = EsapiUtility.getDecodedString(note.getNoteSubject());
					note.setNoteSubject(decodedSub);
				}
				if(StringUtils.isNotEmpty(note.getNoteBody())){
					String decodedBody = EsapiUtility.getDecodedString(note.getNoteBody());
					note.setNoteBody(decodedBody);
				}
			}
		}

		// Decode the HTML encoded notes and support notes
		if(null!= serviceOrderDetails.getSupportNotes()){
			for(SupportNoteVO supportNote : serviceOrderDetails.getSupportNotes().getSupportNote()){
				if(StringUtils.isNotEmpty(supportNote.getNoteSubject())){
					String decodedSub = EsapiUtility.getDecodedString(supportNote.getNoteSubject());
					supportNote.setNoteSubject(decodedSub);
				}
				if(StringUtils.isNotEmpty(supportNote.getNote())){
					String decodedBody = EsapiUtility.getDecodedString(supportNote.getNote());
					supportNote.setNote(decodedBody);
				}
			}
		}
		return serviceOrderDetails;
	}
	/**
	 * Method to format the phone number
	 * @param number
	 * @return
	 */
	private String formatPhoneNumber(String number){
		String formattedNum = StringUtils.EMPTY;;
		if(null!=number){
			formattedNum = UIUtils.formatPhoneNumber(number.replaceAll("-", ""));
		}
		return formattedNum;
	}
	/**
	 * method for getting primary phone number
	 * @param param
	 * @return
	 */
	private String getContactPrimaryPhone(Map<String, String> param){
		String phoneNo="";
		try {
			phoneNo=mobileSOManagementDao.getContactPrimaryPhone(param);
			if(null!=phoneNo){
				phoneNo=formatPhoneNumber(phoneNo);
			}
		} catch (DataServiceException e) {
			return phoneNo;
		}
		return phoneNo;
	}
	/**
	 * method for getting alternate phone number
	 * @param param
	 * @return
	 */
	private String getContactAlternatePhone(Map<String, String> param){
		String phoneNo="";
		try {			
			phoneNo=mobileSOManagementDao.getContactAlternatePhone(param);
			if(null!=phoneNo){
				phoneNo=formatPhoneNumber(phoneNo);
			}
		} catch (DataServiceException e) {
			return phoneNo;
		}
		return phoneNo;
	}

	/**
	 *Time conversion based on so time zone for current appointment start/end date/time and 
	 * next appointment start/end date/time
	 * @param appointment
	 * @param serviceLocation
	 * @return
	 */
	public  RescheduleDetailsVO GMTToGivenTimeZone(RescheduleDetailsVO rescheduleDetailsVO,String zip) {
		if(rescheduleDetailsVO!=null){
			HashMap<String, Object> serviceDate1 = null;
			HashMap<String, Object> serviceDate2 = null;
			String startTime = null;
			String endTime = null;
			String widgetDateFormat = "yyyy-MM-dd"; // Needed for dojo widget.  Bug in dojo 0.9 allows only this format.

			//String startDate= rescheduleDetailsVO.getRescheduleServiceDate1();
			Timestamp startDate= rescheduleDetailsVO.getRescheduleServiceDate1();

			startTime = rescheduleDetailsVO.getRescheduleServiceTime1(); 
			endTime =rescheduleDetailsVO.getRescheduleServiceTime2();
			if (rescheduleDetailsVO.getRescheduleServiceDate1() != null && startTime != null) {

				serviceDate1 = TimeUtils.convertGMTToGivenTimeZone(rescheduleDetailsVO.getRescheduleServiceDate1(), startTime, rescheduleDetailsVO.getTimeZone());
				if (serviceDate1 != null && !serviceDate1.isEmpty()) {
					//rescheduleDetailsVO.setRescheduleServiceDate1(Timestamp.valueOf(DateUtils.getFormatedDate((Timestamp)serviceDate1.get(OrderConstants.GMT_DATE),widgetDateFormat)));
					
					rescheduleDetailsVO.setRescheduleTimeZoneDate1(DateUtils.getFormatedDate((Timestamp)serviceDate1.get(OrderConstants.GMT_DATE),widgetDateFormat));
					rescheduleDetailsVO.setRescheduleServiceTime1((String) serviceDate1.get(OrderConstants.GMT_TIME));
					LOGGER.info("rescheduleDetailsVO.setRescheduleTimeZoneDate1 "+rescheduleDetailsVO.getRescheduleTimeZoneDate1());
					LOGGER.info("rescheduleDetailsVO.getRescheduleServiceTime1 "+rescheduleDetailsVO.getRescheduleServiceTime1());
				}
			}
			if (rescheduleDetailsVO.getRescheduleServiceTime2() != null && endTime != null) {
				serviceDate2 = TimeUtils.convertGMTToGivenTimeZone(rescheduleDetailsVO.getRescheduleServiceDate2(), endTime, rescheduleDetailsVO.getTimeZone());
				if (serviceDate2 != null && !serviceDate2.isEmpty()) {
					//rescheduleDetailsVO.setRescheduleServiceDate2(Timestamp.valueOf(DateUtils.getFormatedDate((Timestamp)serviceDate2.get(OrderConstants.GMT_DATE),widgetDateFormat)));
					rescheduleDetailsVO.setRescheduleTimeZoneDate2(DateUtils.getFormatedDate((Timestamp)serviceDate2.get(OrderConstants.GMT_DATE),widgetDateFormat));
					rescheduleDetailsVO.setRescheduleServiceTime2((String) serviceDate2.get(OrderConstants.GMT_TIME));
				}
			}
			String dlsFlag = "N";
			if (zip != null) {
				try {
					dlsFlag = lookupBO.getDaylightSavingsFlg(zip);
				} catch (DataServiceException e) {
					// TODO Auto-generated catch block
					LOGGER.error("Exception inside details mapper: "+e.getMessage());
				}
			}
			String newTimeZone;
			if ("Y".equals(dlsFlag)) {
				TimeZone tz = TimeZone.getTimeZone(rescheduleDetailsVO.getTimeZone());
				Timestamp timeStampDate = null;
				try {
					if (rescheduleDetailsVO.getRescheduleServiceDate1() != null 
							&& (StringUtils.isNotBlank(startTime))) {
						java.util.Date dt = (java.util.Date) TimeUtils
								.combineDateTime((startDate),
										startTime);
						timeStampDate = new Timestamp(dt.getTime());
					}
				} catch (ParseException pe) {
					pe.printStackTrace();
				}
				if (null != timeStampDate) {
					boolean isDLSActive = tz.inDaylightTime(timeStampDate);
					LOGGER.info("tz "+tz);
					LOGGER.info("timeStampDate "+timeStampDate);
					LOGGER.info("isDLSActive "+isDLSActive);
					if (isDLSActive) {
						newTimeZone = TimeUtils.getDSTTimezone(rescheduleDetailsVO.getTimeZone());
						rescheduleDetailsVO.setTimeZone(newTimeZone);
					} else {
						newTimeZone = TimeUtils.getNormalTimezone(rescheduleDetailsVO.getTimeZone());
						rescheduleDetailsVO.setTimeZone(newTimeZone);
					}
				}
			} 
			else {
				newTimeZone =TimeUtils.getNormalTimezone(rescheduleDetailsVO.getTimeZone());
				rescheduleDetailsVO.setTimeZone(newTimeZone);
			}
			LOGGER.info("rescheduleDetailsVO.getTimeZone "+rescheduleDetailsVO.getTimeZone());
		}
		return rescheduleDetailsVO;

	}
	
	/**
	 *Time conversion based on so time zone for current appointment start/end date/time and 
	 * next appointment start/end date/time
	 * @param appointment
	 * @param serviceLocation
	 * @return
	 */
	public  AppointmentVO GMTToGivenTimeZone(AppointmentVO appointment, ServiceLocationVO serviceLocation) {
		if(appointment!=null){
			HashMap<String, Object> serviceDate1 = null;
			HashMap<String, Object> serviceDate2 = null;
			String startTime = null;
			String endTime = null;
			String widgetDateFormat = "yyyy-MM-dd"; // Needed for dojo widget.  Bug in dojo 0.9 allows only this format.

			String startDate= appointment.getServiceStartDate();

			startTime = appointment.getServiceWindowStartTime(); 
			endTime = appointment.getServiceWindowEndTime();
			if (appointment.getServiceStartDate() != null && startTime != null) {

				serviceDate1 = TimeUtils.convertGMTToGivenTimeZone(Timestamp.valueOf(appointment.getServiceStartDate()), startTime, appointment.getTimeZone());
				if (serviceDate1 != null && !serviceDate1.isEmpty()) {
					appointment.setServiceStartDate(DateUtils.getFormatedDate((Timestamp)serviceDate1.get(OrderConstants.GMT_DATE),widgetDateFormat));
					appointment.setServiceWindowStartTime((String) serviceDate1.get(OrderConstants.GMT_TIME));
				}
			}
			if (appointment.getServiceEndDate() != null && endTime != null) {
				serviceDate2 = TimeUtils.convertGMTToGivenTimeZone(Timestamp.valueOf(appointment.getServiceEndDate()), endTime, appointment.getTimeZone());
				if (serviceDate2 != null && !serviceDate2.isEmpty()) {
					appointment.setServiceEndDate(DateUtils.getFormatedDate((Timestamp)serviceDate2.get(OrderConstants.GMT_DATE),widgetDateFormat));
					appointment.setServiceWindowEndTime((String) serviceDate2.get(OrderConstants.GMT_TIME));
				}
			}
			String dlsFlag = "N";

			if (serviceLocation != null
					&& !StringUtils.isBlank(serviceLocation
							.getZip())) {
				String zip = serviceLocation
						.getZip();
				try {
					dlsFlag = lookupBO.getDaylightSavingsFlg(zip);
				} catch (DataServiceException e) {
					// TODO Auto-generated catch block
					LOGGER.error("Exception inside details mapper: "+e.getMessage());

				}
			}

			String newTimeZone;
			if ("Y".equals(dlsFlag)) {
				TimeZone tz = TimeZone.getTimeZone(appointment.getTimeZone());
				Timestamp timeStampDate = null;
				try {
					if (appointment.getServiceStartDate() != null 
							&& (StringUtils.isNotBlank(startTime))) {
						java.util.Date dt = (java.util.Date) TimeUtils
								.combineDateTime(
										Timestamp.valueOf(startDate),
										startTime);
						timeStampDate = new Timestamp(dt.getTime());
					}
				} catch (ParseException pe) {
					pe.printStackTrace();
				}

				if (null != timeStampDate) {
					boolean isDLSActive = tz.inDaylightTime(timeStampDate);

					if (isDLSActive) {
						newTimeZone = TimeUtils.getDSTTimezone(appointment.getTimeZone());
						appointment.setTimeZone(newTimeZone);
					} else {
						newTimeZone = TimeUtils.getNormalTimezone(appointment.getTimeZone());
						appointment.setTimeZone(newTimeZone);
					}
				}
			} 
			else {
				newTimeZone =TimeUtils.getNormalTimezone(appointment.getTimeZone());
				appointment.setTimeZone(newTimeZone);
			}

		}
		return appointment;

	}
	/**
	 * Method to convert the time based on so time zone while adding notes
	 * @param appointment
	 * @param notes
	 * @param serviceLocation
	 * @return
	 * @throws ParseException
	 */
	public NotesVO GMTToGivenTimeZone(AppointmentVO appointment, NotesVO notes, ServiceLocationVO serviceLocation) throws ParseException {		
		if(appointment != null && appointment.getTimeZone() != null && notes!=null && notes.getNote() != null && notes.getNote().size() > 0){

			List<NoteVO> noteslst =  notes.getNote();
			String startDate = null;
			String startDateMod = null;
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			Date dateObj = null;		
			Timestamp createdDateTimeStamp = null;
			// convert DB date to GMT
			Timestamp gmtTimeStamp = null;
			for(NoteVO noteObj : noteslst){
				if(noteObj.getCreatedDate() != null){
					startDate = noteObj.getCreatedDate();
					dateObj = formatter.parse(startDate);
					createdDateTimeStamp = new Timestamp(dateObj.getTime());
					gmtTimeStamp =	TimeUtils.convertToGMT(createdDateTimeStamp,"CDT"); // Server Time to GMT					 

					// GMT to service order local time zone
					startDateMod = TimeUtils.convertGMTtoTimezone(gmtTimeStamp, TimeUtils.getBasicTimezone(appointment.getTimeZone()),sdf);
					noteObj.setCreatedDate(startDateMod);
				}
			}
			return notes;
		}
		return notes;
	}
	/**
	 * Method to convert the time based on so time zone while adding documents
	 * @param appointment
	 * @param document
	 * @return
	 * @throws ParseException
	 */
	public DocumentsVO GMTToGivenTimeZone(AppointmentVO appointment, DocumentsVO document) throws ParseException{

		if(null!=document.getDocument()){			
			List<DocumentDetailsVO> documentList=document.getDocument();
			String startDate = null;
			String startDateMod = null;
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			Date dateObj = null;
			Timestamp createdDateTimeStamp = null;
			// convert DB date to GMT
			Timestamp gmtTimeStamp = null;
			for(DocumentDetailsVO docObj : documentList){
				if(docObj.getUploadDateTime()!= null){
					startDate = docObj.getUploadDateTime() ;
					dateObj = formatter.parse(startDate);
					createdDateTimeStamp = new Timestamp(dateObj.getTime());
					gmtTimeStamp =	TimeUtils.convertToGMT(createdDateTimeStamp,"CDT"); // Server Time to GMT

					// GMT to service order local time zone
					startDateMod = TimeUtils.convertGMTtoTimezone(gmtTimeStamp, TimeUtils.getBasicTimezone(appointment.getTimeZone()),sdf);
					docObj.setUploadDateTime(startDateMod);
				}
			}
			return document;

		}
		return document;
	}
	/**
	 * Method to convert the time based on so time zone for each trips
	 * @param soTrip
	 * @param timeZone
	 * @param serviceLocation
	 * @return
	 */
	public  SOTripsVO GMTToGivenTimeZone(SOTripsVO soTrip, String timeZone,ServiceLocationVO serviceLocation) {

		if(null != soTrip){
			List<TripVO> tripList = soTrip.getTrip();
			if(null != tripList){
				for(TripVO trip: tripList){
					String widgetDateFormat = "yyyy-MM-dd";
					HashMap<String, Object> currentApptStartDateMap = null;
					HashMap<String, Object> currentApptEndDateMap = null;
					HashMap<String, Object> nextApptStartDateMap = null;
					HashMap<String, Object> nextApptEndDateMap = null;
					HashMap<String, Object> checkInTimeMap = null;
					HashMap<String, Object> checkOutTimeMap = null;

					//Current appointment start date/start time conversion to so time zone
					String currentApptStartDate = trip.getCurrentApptStartDate();
					String currentApptStartTime = trip.getCurrentApptStartTime();
					if(StringUtils.isNotEmpty(currentApptStartDate) && StringUtils.isNotEmpty(currentApptStartTime)){
						currentApptStartDateMap = TimeUtils.convertGMTToGivenTimeZone(Timestamp.valueOf(currentApptStartDate), currentApptStartTime, timeZone);
						if (currentApptStartDateMap != null && !currentApptStartDateMap.isEmpty()) {
							trip.setCurrentApptStartDate((DateUtils.getFormatedDate((Timestamp)currentApptStartDateMap.get(OrderConstants.GMT_DATE),widgetDateFormat)));
							trip.setCurrentApptStartTime((String) currentApptStartDateMap.get(OrderConstants.GMT_TIME));
						}
					}
					//Current appointment end date/end time conversion to so time zone
					String currentApptEndDate = trip.getCurrentApptEndDate();
					String currentApptEndTime = trip.getCurrentApptEndTime();
					if(StringUtils.isNotEmpty(currentApptEndDate) && StringUtils.isNotEmpty(currentApptEndTime)){
						currentApptEndDateMap = TimeUtils.convertGMTToGivenTimeZone(Timestamp.valueOf(currentApptEndDate), currentApptEndTime, timeZone);
						if (currentApptEndDateMap != null && !currentApptEndDateMap.isEmpty()) {
							trip.setCurrentApptEndDate((DateUtils.getFormatedDate((Timestamp)currentApptEndDateMap.get(OrderConstants.GMT_DATE),widgetDateFormat)));
							trip.setCurrentApptEndTime((String) currentApptEndDateMap.get(OrderConstants.GMT_TIME));
						}
					}
					//Next appointment end date/end time conversion to so time zone
					String nextApptStartDate = trip.getNextApptStartDate();
					String nextApptStartTime = trip.getNextApptStartTime();
					if(StringUtils.isNotEmpty(nextApptStartDate) && StringUtils.isNotEmpty(nextApptStartTime)){
						nextApptStartDateMap = TimeUtils.convertGMTToGivenTimeZone(Timestamp.valueOf(nextApptStartDate), nextApptStartTime, timeZone);
						if (nextApptStartDateMap != null && !nextApptStartDateMap.isEmpty()) {
							trip.setNextApptStartDate((DateUtils.getFormatedDate((Timestamp)nextApptStartDateMap.get(OrderConstants.GMT_DATE),widgetDateFormat)));
							trip.setNextApptStartTime((String) nextApptStartDateMap.get(OrderConstants.GMT_TIME));
						}
					}
					//Next appointment end date/end time conversion to so time zone
					String nextApptEndDate = trip.getNextApptEndDate();
					String nextApptEndTime = trip.getNextApptEndTime();
					if(StringUtils.isNotEmpty(nextApptEndDate) && StringUtils.isNotEmpty(nextApptEndTime)){
						nextApptEndDateMap = TimeUtils.convertGMTToGivenTimeZone(Timestamp.valueOf(nextApptEndDate), nextApptEndTime, timeZone);
						if (nextApptEndDateMap != null && !nextApptEndDateMap.isEmpty()) {
							trip.setNextApptEndDate((DateUtils.getFormatedDate((Timestamp)nextApptEndDateMap.get(OrderConstants.GMT_DATE),widgetDateFormat)));
							trip.setNextApptEndTime((String) nextApptEndDateMap.get(OrderConstants.GMT_TIME));
						}
					}
					//Check In time conversion to so time zone
					String checkInTime = trip.getCheckInTime(); //"10/11/2014 08:00:00 00";
					if(StringUtils.isNotEmpty(checkInTime)){
						checkInTimeMap = TimeUtils.convertGMTToGivenTimeZone(Timestamp.valueOf(checkInTime),timeZone);
						if (checkInTimeMap != null && !checkInTimeMap.isEmpty()) {
							String newCheckInTimezone = getTimeZone(serviceLocation,timeZone,checkInTime);
							trip.setCheckInTime(checkInTimeMap.get(OrderConstants.GMT_TIME).toString().replaceAll("T", " ")+" "+newCheckInTimezone);
						}
					}
					//Check Out time conversion to so time zone
					String checkOutTime = trip.getCheckOutTime(); //"10/11/2014 08:00:00 00";
					if(StringUtils.isNotEmpty(checkOutTime)){
						checkOutTimeMap = TimeUtils.convertGMTToGivenTimeZone(Timestamp.valueOf(checkOutTime),timeZone);
						if (checkOutTimeMap != null && !checkOutTimeMap.isEmpty()) {
							String newCheckOutTimezone = getTimeZone(serviceLocation,timeZone,checkOutTime);
							trip.setCheckOutTime(checkOutTimeMap.get(OrderConstants.GMT_TIME).toString().replaceAll("T", " ")+" "+newCheckOutTimezone);
						}
					}
				}
			}

		}
		return soTrip;
	}
	public  LatestTripVO GMTToGivenTimeZone(LatestTripVO latestTrip, String timeZone,ServiceLocationVO serviceLocation) {
		if(null !=latestTrip ){
			HashMap<String, Object> checkInTimeMap = null;
			HashMap<String, Object> checkOutTimeMap = null;
			//Check In time conversion to so time zone
			String checkInTime = latestTrip.getCheckInTime(); //"10/11/2014 08:00:00 00";
			if(StringUtils.isNotEmpty(checkInTime)){
				checkInTimeMap = TimeUtils.convertGMTToGivenTimeZone(Timestamp.valueOf(checkInTime),timeZone);
				if (checkInTimeMap != null && !checkInTimeMap.isEmpty()) {
					String newCheckInTimezone = getTimeZone(serviceLocation,timeZone,checkInTime);
					latestTrip.setCheckInTime(checkInTimeMap.get(OrderConstants.GMT_TIME).toString().replaceAll("T", " ")+" "+newCheckInTimezone);
				}
			}
			//Check Out time conversion to so time zone
			String checkOutTime = latestTrip.getCheckOutTime(); //"10/11/2014 08:00:00 00";
			if(StringUtils.isNotEmpty(checkOutTime)){
				checkOutTimeMap = TimeUtils.convertGMTToGivenTimeZone(Timestamp.valueOf(checkOutTime),timeZone);
				if (checkOutTimeMap != null && !checkOutTimeMap.isEmpty()) {
					String newCheckOutTimezone = getTimeZone(serviceLocation,timeZone,checkOutTime);
					latestTrip.setCheckOutTime(checkOutTimeMap.get(OrderConstants.GMT_TIME).toString().replaceAll("T", " ")+" "+newCheckOutTimezone);
				}
			}
		}
		return latestTrip;
	}



	/**
	 * Method to get the time in so timezone with servicelocation as parameter
	 * @param serviceLocation
	 * @param timeZone
	 * @param checkTime
	 * @return
	 */
	public String getTimeZone(ServiceLocationVO serviceLocation, String timeZone ,String checkTime){

		String dlsFlag = "N";
		String newTimezone = null;
		try {
			if (serviceLocation != null
					&& !StringUtils.isBlank(serviceLocation
							.getZip())) {
				String zip = serviceLocation
						.getZip();

				dlsFlag = lookupBO.getDaylightSavingsFlg(zip);		
			}

			if ("Y".equals(dlsFlag)) {
				TimeZone tz = TimeZone.getTimeZone(timeZone);
				Timestamp timeStampDate = null;
				try {
					if (StringUtils.isNotBlank(checkTime)) {

						DateFormat dfFull = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");							
						Date combinedDate = dfFull.parse(checkTime);															
						timeStampDate = new Timestamp(combinedDate.getTime());

					}
				} catch (ParseException pe) {
					pe.printStackTrace();
				}
				if (null != timeStampDate) {
					boolean isDLSActive = tz.inDaylightTime(timeStampDate);
					if (isDLSActive) {
						newTimezone = TimeUtils.getDSTTimezone(timeZone);
					} else {
						newTimezone = TimeUtils.getNormalTimezone(timeZone);
					}
				}
			} else {
				newTimezone = TimeUtils.getNormalTimezone(timeZone);
			}

		}
		catch (Exception e) {
			LOGGER.error("error occured in getTimeZone(serviceLocation,timeZone,checkTime)"+e.getMessage());
			e.printStackTrace();
		}
		return newTimezone;

	}


	public IMobileSOManagementDao getMobileSOManagementDao() {
		return mobileSOManagementDao;
	}


	public void setMobileSOManagementDao(
			IMobileSOManagementDao mobileSOManagementDao) {
		this.mobileSOManagementDao = mobileSOManagementDao;
	}

	/**
	 * Method to fetch the filter details associated with a provider
	 * The filter id and filter details will fetch from the database and it will put into a map as keyvalue pair
	 * @param providerId
	 * @return Map<Integer, FiltersVO>
	 * @throws BusinessServiceException 
	 * @throws Exception 
	 */
	public Map<Integer, FiltersVO> getSearchFilterDetails(Integer providerId) throws BusinessServiceException{

		List<FiltersVO> filterDetails = null;
		Map<Integer, FiltersVO> filterMap=new HashMap<Integer, FiltersVO>();
		Map<String,List<FilterCriteriaVO>> filterDetailsMap=null;

		try {

			filterDetails = mobileGenericDao.getSearchFilterDetails(providerId);

			if(null != filterDetails && ! filterDetails.isEmpty()){

				for(FiltersVO filtersVO:filterDetails){

					if(null != filtersVO){

						filterDetailsMap=mapFilterDetails(filtersVO);
						filtersVO.setCriteriaMap(filterDetailsMap);
						filterMap.put(filtersVO.getFilterId(), filtersVO);
					}
				}
			}

		}catch (Exception e) {
			LOGGER.error("Error while getting the search filters");
			throw new BusinessServiceException(e.getMessage());

		}
		return filterMap;

	}

	/**
	 * Method to set the details of the filters such as search criterias and its values associated witha filter     
	 * Each criteria from the criteria list will be checked and the corresponding value will be added to its corresponding list
	 * Each criteria and its corresponding list will put into a map as a key value pair           
	 * @param filtersVO
	 * @return Map<String, List<FilterCriteriaVO>>
	 * @throws Exception
	 */
	 private Map<String, List<FilterCriteriaVO>> mapFilterDetails(FiltersVO filtersVO) throws BusinessServiceException {

		Map<String, List<FilterCriteriaVO>> filterCriteriasMap=null;
		List<FilterCriteriaVO> filterCritreiaList=null;
		List<FilterCriteriaVO>criteriaValueMarketList =null;
		List<FilterCriteriaVO>criteriaValueStatusList =null;
		List<FilterCriteriaVO>criteriaValueSubStatusList =null;
		List<FilterCriteriaVO>criteriaValueServiceProNamesList =null;
		List<FilterCriteriaVO>criteriaValueAppoinmentValueList =null;
		List<FilterCriteriaVO>criteriaValueAppoinmentStartDateList =null;
		List<FilterCriteriaVO>criteriaValueAppoinmentEndDateList =null;
		List<FilterCriteriaVO>criteriaValueFlaggedSOList =null;
		List<FilterCriteriaVO>criteriaValueUnAssignedList =null;

		try {

			filterCritreiaList=filtersVO.getCriteriaList();

			if(null !=filterCritreiaList && ! filterCritreiaList.isEmpty()){

				criteriaValueMarketList=new ArrayList<FilterCriteriaVO>();
				criteriaValueStatusList=new ArrayList<FilterCriteriaVO>();
				criteriaValueSubStatusList=new ArrayList<FilterCriteriaVO>();
				criteriaValueServiceProNamesList=new ArrayList<FilterCriteriaVO>();
				criteriaValueAppoinmentValueList=new ArrayList<FilterCriteriaVO>();
				criteriaValueAppoinmentStartDateList=new ArrayList<FilterCriteriaVO>();
				criteriaValueAppoinmentEndDateList=new ArrayList<FilterCriteriaVO>();
				criteriaValueFlaggedSOList=new ArrayList<FilterCriteriaVO>();
				criteriaValueUnAssignedList=new ArrayList<FilterCriteriaVO>();
				filterCriteriasMap=new HashMap<String, List<FilterCriteriaVO>>();

				for(FilterCriteriaVO filterCriteriaVO:filterCritreiaList){

					if(null != filterCriteriaVO){

						if(filterCriteriaVO.getSearchCriteria().equals(MPConstants.MARKET)){

							criteriaValueMarketList.add(filterCriteriaVO);

						}else if(filterCriteriaVO.getSearchCriteria().equals(MPConstants.ORDERSTATUS)){

							criteriaValueStatusList.add(filterCriteriaVO);

						}else if(filterCriteriaVO.getSearchCriteria().equals(MPConstants.ORDERSUBSTATUS)){
							criteriaValueSubStatusList.add(filterCriteriaVO);
						}else if(filterCriteriaVO.getSearchCriteria().equals(MPConstants.PROVIDER_RESOURCE)){
							criteriaValueServiceProNamesList.add(filterCriteriaVO);
						}else if(filterCriteriaVO.getSearchCriteria().equals(MPConstants.APPOINTMENT_TYPE)){
							criteriaValueAppoinmentValueList.add(filterCriteriaVO);
						}else if(filterCriteriaVO.getSearchCriteria().equals(MPConstants.APPOINTMENT_START_DATE)){
							criteriaValueAppoinmentStartDateList.add(filterCriteriaVO);
						}else if(filterCriteriaVO.getSearchCriteria().equals(MPConstants.APPOINTMENT_END_DATE)){
							criteriaValueAppoinmentEndDateList.add(filterCriteriaVO);
						}else if(filterCriteriaVO.getSearchCriteria().equals(MPConstants.ORDER_FLAGGED)){

							if(StringUtils.isNotBlank(filterCriteriaVO.getSearchCriteriaValueId())
									&& filterCriteriaVO.getSearchCriteriaValueId().equals(MPConstants.FLAG_VALUE)){
								filterCriteriaVO.setFlaggedSo(true);
							}else{
								filterCriteriaVO.setFlaggedSo(false);
							}
							criteriaValueFlaggedSOList.add(filterCriteriaVO);
						}else if(filterCriteriaVO.getSearchCriteria().equals(MPConstants.ORDER_UNASSIGNED)){

							if(StringUtils.isNotBlank(filterCriteriaVO.getSearchCriteriaValueId())&& filterCriteriaVO.getSearchCriteriaValueId().equals(MPConstants.UNASSIGNED_VALUE)){
								filterCriteriaVO.setUnAssigned(true);
							}else{
								filterCriteriaVO.setUnAssigned(false);
							}
							criteriaValueUnAssignedList.add(filterCriteriaVO);
						}
					}
				}
				filterCriteriasMap.put(MPConstants.MARKET, criteriaValueMarketList);
				filterCriteriasMap.put(MPConstants.ORDERSTATUS, criteriaValueStatusList);
				filterCriteriasMap.put(MPConstants.ORDERSUBSTATUS, criteriaValueSubStatusList);
				filterCriteriasMap.put(MPConstants.PROVIDER_RESOURCE, criteriaValueServiceProNamesList);
				filterCriteriasMap.put(MPConstants.APPOINTMENT_TYPE, criteriaValueAppoinmentValueList);
				filterCriteriasMap.put(MPConstants.APPOINTMENT_START_DATE, criteriaValueAppoinmentStartDateList);
				filterCriteriasMap.put(MPConstants.APPOINTMENT_END_DATE, criteriaValueAppoinmentEndDateList);
				filterCriteriasMap.put(MPConstants.ORDER_FLAGGED, criteriaValueFlaggedSOList);
				filterCriteriasMap.put(MPConstants.ORDER_UNASSIGNED, criteriaValueUnAssignedList);
			}
		}
		catch (Exception e) {
			LOGGER.error("Error in mapping filter details");
			throw new BusinessServiceException(e.getMessage());

		}
		return filterCriteriasMap;
	}


	/**
	 * method to get the wfstateId,routed resources,and assignment type of the SO
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public AssignVO getProviderSODetails(String soId)throws BusinessServiceException {
		AssignVO assignVO = new AssignVO();
		try{
			assignVO = mobileGenericDao.getProviderSODetails(soId);
		}
		catch(Exception e){
			LOGGER.error("Error in the method getProviderSODetails(soId)"+soId);
			throw new BusinessServiceException(e.getMessage());
		}
		return assignVO;
	}

	/**
	 * @param searchRequestVO
	 * @return
	 */
	public SOSearchResultsVO getServiceOrderAdvanceSearchResults(
			SOAdvanceSearchCriteriaVO searchRequestVO)throws BusinessServiceException {
		SOSearchResultsVO searchResultsVO = new SOSearchResultsVO();
		List<SOSearchResultVO> searchResultVOs =null;
		Integer totalCount = null;
		try{
			/*Map<String, String> sort = ensureSort(searchRequestVO
					.getSortColumnName(), searchRequestVO.getSortOrder(), null);
			searchRequestVO.setSortColumnName(sort.get(SORT_COLUMN_KEY));
			searchRequestVO.setSortOrder(sort.get(SORT_ORDER_KEY));*/
			totalCount = mobileGenericDao.getTotalCountForServiceOrderAdvanceSearchResults(searchRequestVO);
			if(!validatePageNumber(totalCount,searchRequestVO.getPageNo(),searchRequestVO.getPageSize())){
				searchResultsVO.setPageNumberExceededInd(true);
			}
			else{
				searchResultVOs = mobileGenericDao.getServiceOrderAdvanceSearchResults(searchRequestVO);
				searchResultsVO.setSearchResultVOs(searchResultVOs);
				searchResultsVO.setTotalSOCount(totalCount);
			}
			// searchResults = mapGroupedOrders(searchResults,searchRequestVO.getFirmId());
		}
		catch (DataServiceException ex) {
			throw new BusinessServiceException(ex);
		}
		/*catch (BusinessServiceException ex) {
			throw ex;
		}*/
		return searchResultsVO;
	}

	public void saveFilter(FiltersVO filtersVO) throws BusinessServiceException{
		try{
			mobileGenericDao.saveFilter(filtersVO);
		}catch (Exception e) {
			LOGGER.error("error occured while saving the filter in MobileGenericBOImpl.saveFilter()");
			throw new BusinessServiceException(e.getMessage());
		}
	}

	public boolean isFilterExists(Integer resourceId, String filterName) throws BusinessServiceException{
		try{
			return mobileGenericDao.isFilterExists(resourceId,filterName);
		}catch (Exception e) {
			LOGGER.error("error occured in MobileGenericBOImpl.isFilterExists()");
			throw new BusinessServiceException(e.getMessage());
		}
	}
	/**@Description : Fetch Count of service order in different status.
	 * @param dashboardVO
	 * @return MobileDashboardVO
	 * @throws BusinessServiceException
	 */
	public MobileDashboardVO getDashboardCount(MobileDashboardVO dashboardVO)throws BusinessServiceException {
        try{
        	dashboardVO = mobileGenericDao.getDashboardCount(dashboardVO);
        }catch (DataServiceException e) {
        	LOGGER.error("error occured in MobileGenericBOImpl.getDashboardCount()"+ e.getMessage());
			throw new BusinessServiceException(e);
		}
		return dashboardVO;
	}
	/**
	 * Method to fetch the search criterias like market,service providers,status and subStatus
	 * @param roleId
	 * @param resourceId
	 * @param vendorId
	 * @return
	 * @throws BusinessServiceException
	 */
	public SoSearchCriteriaVO getSearchCriteria(Integer roleId,Integer resourceId, Integer vendorId)throws BusinessServiceException {

		SoSearchCriteriaVO searchCriteriaVOs = new SoSearchCriteriaVO();
		Map<Integer , List<SoStatusVO>> statusMap = new HashMap<Integer ,List<SoStatusVO>>();
		List<Integer>wfStatus = Arrays.asList(MPConstants.STATUS_ARRAY_WITH_RECIEVED);
		
		try{
			searchCriteriaVOs = mobileGenericDao.getSearchCriteria(roleId,resourceId,vendorId);			
			List<SoStatusVO>soStatusVOs = searchCriteriaVOs.getSoStatusVOs();			
			
			for(Integer wfState : wfStatus){
				
				List<SoStatusVO> resultVO = new ArrayList<SoStatusVO>();
				
				for(SoStatusVO statusVO : soStatusVOs){				
					if(wfState.equals(statusVO.getSoStatusId())){
						resultVO.add(statusVO);
					}
				}
				statusMap.put(wfState, resultVO);
			}
			searchCriteriaVOs.setStatusMap(statusMap);
		}
		catch(Exception e){
			LOGGER.error("error occured in MobileGenericBOImpl.getSearchCriteria()");
			throw new BusinessServiceException(e.getMessage());
		}
		return searchCriteriaVOs;
	}


	/**
	 * Method to check whether resource is authorized to view SO details in accepted,active,problem status
	 * @param detailsVO
	 * @return
	 * @throws DataServiceException
	 */
	public boolean isAuthorizedToViewBeyondPosted(SoDetailsVO detailsVO)
			throws BusinessServiceException {
		boolean authSuccess = false;
		try{
			authSuccess = mobileGenericDao.isAuthorizedToViewBeyondPosted(detailsVO);
		}
		catch(Exception e){
			throw new BusinessServiceException(e);
		}
		return authSuccess;
	}
	

	
	/*public boolean isLessThanSpendLimitLabour(String soId) throws BusinessServiceException{
		boolean isLessThanSpendLimitLabour=false;
		try{
			isLessThanSpendLimitLabour=serviceOrderBo.isLessThanSpendLimitLabour(soId);
		}catch(Exception ex){
			LOGGER
			.error("MobileGenericBOImpl.isLessThanSpendLimitLabour(): "+ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		}
		return isLessThanSpendLimitLabour;
	}*/
	
	public void sendBuyerOutBoundNotification(/*OrderFulfillmentResponse response,*/String soId,/*SOSchedule reschedule,*/String reason,String comment,RescheduleVO rescheduleVo){
		//com.servicelive.orderfulfillment.domain.ServiceOrder serviceOrder = ofHelper.getServiceOrder(soId);
		LOGGER.info("Inside sendBuyerOutBoundNotification");
		try
		{
			if(rescheduleVo.getBuyerId()== 1000){
				LOGGER.info("insideChoice");
				RequestOrder order = new RequestOrder();
				BuyerOutboundNotificationVO logging = new BuyerOutboundNotificationVO();
				try {
					logging = buyerOutBoundNotificationService.getLoggingDetails(rescheduleVo.getSoId());
				} catch (BusinessServiceException e) {
					LOGGER.info("error in getting so Logging"+e);
				}
				String modificationId = null;
				if(null!=logging.getEntityId()){
					modificationId=logging.getEntityId().toString();
				}
				// convert the reschedule date date in service order Timezone.
				TimeZone timeZone=TimeZone.getTimeZone(rescheduleVo.getServiceLocnTimeZone());
				SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
				String fromDate=formatter.format(rescheduleVo.getDBServiceDate1());
				String serviceTimeStart = rescheduleVo.getStartTime();
				String serviceTimeEnd = "";
					serviceTimeEnd=rescheduleVo.getEndTime();
				//set rescheduled Date for request Xml.
				order.setServiceScheduleDate(fromDate);
				order.setServiceScheduleFromTime(serviceTimeStart);
				if(StringUtils.isBlank(serviceTimeEnd))
				{
					order.setServiceScheduletoTime(serviceTimeStart);
				}
				else
				{
					order.setServiceScheduletoTime(serviceTimeEnd);
				}
				order.setServiceOrderRescheduledFlag(BuyerOutBoundConstants.RESHEDULE_FLAG_YES);
				// get the Modified date(current date) in server TimeZone.
				//setting the modifiedTime,modificationDate,modificationId for buyerOutBoundNotificationService
				Calendar calender=Calendar.getInstance();
				SimpleDateFormat timeFormatter=new SimpleDateFormat("hh:mm a");
				String modifiedfromDate=timeFormatter.format(calender.getTime());
				Calendar modificationDateTime = TimeChangeUtil.getCalTimeFromParts(calender.getTime(), modifiedfromDate,calender.getTimeZone());
				Date modificationDate = TimeChangeUtil.getDate(modificationDateTime, timeZone);
				String modificationDateValue=formatter.format(modificationDate);
				String modificationTime = TimeChangeUtil.getTimeString(modificationDateTime, timeZone);

				RequestRescheduleInfo requestRescheduleInfo=new RequestRescheduleInfo();
				//set the modified date for request xml.
				requestRescheduleInfo.setReschedCancelModificationDate(modificationDateValue);
				requestRescheduleInfo.setReschedCancelModificationTime(modificationTime);
				requestRescheduleInfo.setRescheduleModificationID(modificationId);
				requestRescheduleInfo.setRescheduleReasonCode(reason);
				requestRescheduleInfo.setRescheduleRsnCdDescription(comment);	
				
				//set the reschedule Information for request xml for buyerOutBoundNotificationService
				RequestMsgBody requestMsgBody=new RequestMsgBody();
				RequestOrders orders=new RequestOrders();
				List<RequestOrder> orderList=new ArrayList<RequestOrder>();
				RequestReschedInformation requestReschedInformation=new RequestReschedInformation();
				List<RequestRescheduleInfo> requestRescheduleInf=new ArrayList<RequestRescheduleInfo>();
				requestRescheduleInf.add(requestRescheduleInfo);
				requestReschedInformation.setRequestRescheduleInf(requestRescheduleInf);
				order.setRequestReschedInformation(requestReschedInformation);
				orderList.add(order);
				orders.setOrder(orderList);
				requestMsgBody.setOrders(orders);
				LOGGER.info("callservicChoice");
				//call buyerOutBoundNotificationService by passing the request Object.
				try {
					BuyerOutboundFailOverVO failoverVO = buyerOutBoundNotificationService.callService(requestMsgBody, rescheduleVo.getSoId());
					if(null!=failoverVO)
						buyerOutBoundNotificationJMSService.callJMSService(failoverVO);
				} catch (BusinessServiceException e) {
					LOGGER.info("Error in NPS update for reschedule"+e);
				}
			}
			
		}catch(Exception e)
		{
			LOGGER.info("ExceptioninChoice"+e);
		}
	}
	
	/**
	 * @param secQuestAnsRequestVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public SecQuestAnsRequestVO getExistingUserProfileDetails(SecQuestAnsRequestVO secQuestAnsRequestVO) throws BusinessServiceException{

		LostUsernameVO lostUsernameVO=null;
		String resId = null;
		if(null != secQuestAnsRequestVO.getUserId()){
			resId = secQuestAnsRequestVO.getUserId().toString();
		}
		try{
			lostUsernameVO = forgotUsernameBO.loadLostUsername(secQuestAnsRequestVO.getUserName(),resId,OrderConstants.PROVIDER_ROLEID);
			if(lostUsernameVO!= null){
				secQuestAnsRequestVO = mapUserProfileDetails(secQuestAnsRequestVO,lostUsernameVO);
			}
			else{
				secQuestAnsRequestVO.setUserProfileExists(false);
			}
		} catch(Exception ex) {
			LOGGER
			.error("MobileGenericBOImpl.isUserProfileExists(): "+ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		}
		return secQuestAnsRequestVO;
	}
	
	/**
	 * @param secQuestAnsRequestVO
	 * @param lostUsernameVO
	 * @return
	 * @throws com.newco.marketplace.exception.BusinessServiceException 
	 */
	private SecQuestAnsRequestVO mapUserProfileDetails(
			SecQuestAnsRequestVO secQuestAnsRequestVO,
			LostUsernameVO lostUsernameVO) throws com.newco.marketplace.exception.BusinessServiceException {

		secQuestAnsRequestVO.setEmailFromDB(lostUsernameVO.getEmailAddress());
		secQuestAnsRequestVO.setUserNameFromDB(lostUsernameVO.getUserName());
		if(StringUtils.isNotEmpty(lostUsernameVO.getUserName())){
			int verificationAttemptCount = forgotUsernameBO.getVerificationCount(lostUsernameVO.getUserName());
			secQuestAnsRequestVO.setVerificationAttemptCount(verificationAttemptCount);
		}

		if(StringUtils.isNotBlank(lostUsernameVO.getQuestionId())){
			secQuestAnsRequestVO.setActualQuestionId(Integer.parseInt(lostUsernameVO.getQuestionId()));
		}
		secQuestAnsRequestVO.setQuestionTxt(lostUsernameVO.getQuestionTxt());
		secQuestAnsRequestVO.setQuestionTxtAnswer(lostUsernameVO.getQuestionTxtAnswer());

		secQuestAnsRequestVO.setPwdInd(lostUsernameVO.getPwdInd());

		//if no question id exists, then set generate pwd indicator 1 for sending interim pwd email
		if (secQuestAnsRequestVO.getPwdInd() == 0
				&& (secQuestAnsRequestVO.getActualQuestionId() == null || secQuestAnsRequestVO.getActualQuestionId().equals(0))){
			secQuestAnsRequestVO.setPwdInd(1);
		}
		secQuestAnsRequestVO.setDetailedProfile(lostUsernameVO.isDetailedProfile());

		secQuestAnsRequestVO.setZip(lostUsernameVO.getZip());
		secQuestAnsRequestVO.setBusinessName(lostUsernameVO.getBusinessName());
		secQuestAnsRequestVO.setPhoneNo(lostUsernameVO.getPhoneNo());
		secQuestAnsRequestVO.setLockedInd(lostUsernameVO.getLockedInd());		
		secQuestAnsRequestVO.setModifiedDate(lostUsernameVO.getModifiedDate());
		secQuestAnsRequestVO.setRoleId(lostUsernameVO.getUserId());
		secQuestAnsRequestVO.setUserProfileExists(true);
		
		return secQuestAnsRequestVO;
	}
	
	/**
	 * @param secQuestAnsRequestVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public SecQuestAnsRequestVO validateAnsForForgotPassword(SecQuestAnsRequestVO secQuestAnsRequestVO) throws BusinessServiceException {
		

		try{
			if(secQuestAnsRequestVO.getPwdInd()==1)
			{
				// in case of users with auto generated passwords
				processRequestForAutoGeneratedPasswordUsers(secQuestAnsRequestVO);
			
			}
			else
			{
				// in case of users without auto generated passwords
				processRequestForNonAutoGeneratedPasswordUsers(secQuestAnsRequestVO);
				
			}
			// check if success/error and correspondingly set the verification count
			processVerificationRetryCountAndRespondBack(secQuestAnsRequestVO);
			

		}catch(BusinessServiceException bse){
			throw new BusinessServiceException(bse);
		}
		return secQuestAnsRequestVO;
	}

	/**
	 * @param secQuestAnsRequestVO
	 * @throws com.newco.marketplace.exception.BusinessServiceException 
	 */
	private void processVerificationRetryCountAndRespondBack(
			SecQuestAnsRequestVO secQuestAnsRequestVO) throws com.newco.marketplace.exception.BusinessServiceException {
		String userName = null;
		final int maxSecretQuestionAttempts = Integer
				.parseInt(PropertiesUtils
						.getPropertyValue(Constants.AppPropConstants.MAX_SECRET_QUESTION_ATTEMPTS_LIMIT));
		// get verification count
		int attemptCount = 0;
		// check if available in VO
		if(null!=secQuestAnsRequestVO.getVerificationAttemptCount()){
			attemptCount=secQuestAnsRequestVO.getVerificationAttemptCount().intValue();
		}
		else{
			attemptCount = forgotUsernameBO.getVerificationCount(secQuestAnsRequestVO.getUserName());;
		}
		boolean success = secQuestAnsRequestVO.isSuccess();
		if (success) {
			// if answer is correct, update verification count as 0
			attemptCount = 0;
			secQuestAnsRequestVO.setSuccess(true);
			secQuestAnsRequestVO.setSuccess((Results.getSuccess(ResultsCode.RESET_PASSWORD_EMAIL_SEND_SUCCESS.getCode(), ResultsCode.RESET_PASSWORD_EMAIL_SEND_SUCCESS.getMessage())));// display message
		}
		else{

			attemptCount++;
			if (attemptCount >= maxSecretQuestionAttempts || attemptCount == -1) {
				// ask for additional verification
				secQuestAnsRequestVO.setSuccess(false);
				secQuestAnsRequestVO.setError(Results.getError(ResultsCode.COUNT_EXCEEDED_SHOW_VERIFICATION_ZIP.getMessage(), ResultsCode.COUNT_EXCEEDED_SHOW_VERIFICATION_ZIP.getCode()));// display error		}
			}
			else{
				// ask for reentering secret answer
				secQuestAnsRequestVO.setSuccess(false);
				secQuestAnsRequestVO.setError(Results.getError(ResultsCode.INVALID_SECRET_QUESTION_ANSWER.getMessage(), ResultsCode.INVALID_SECRET_QUESTION_ANSWER.getCode()));// display error
			}

		}

		userName = secQuestAnsRequestVO.getUserNameFromDB();
		if (userName == null) {
			userName = secQuestAnsRequestVO.getUserName();
		}
		// update verification count
		forgotUsernameBO.updateVerificationCount(userName, attemptCount);

	}


	/**
	 * @param secQuestAnsRequestVO
	 * @throws com.newco.marketplace.exception.BusinessServiceException
	 */
	private void processRequestForNonAutoGeneratedPasswordUsers(
			SecQuestAnsRequestVO secQuestAnsRequestVO) throws com.newco.marketplace.exception.BusinessServiceException {
		LostUsernameVO lostUsernameVO = new LostUsernameVO();
		lostUsernameVO=(LostUsernameVO)formLostUserNameVO(secQuestAnsRequestVO);
		lostUsernameVO=(LostUsernameVO)forgotUsernameBO.validateAns(lostUsernameVO);
		secQuestAnsRequestVO=(SecQuestAnsRequestVO)convertToSecurityQuestAnsVO(lostUsernameVO,secQuestAnsRequestVO);
		if(!secQuestAnsRequestVO.isSuccess()){
			secQuestAnsRequestVO.setError(Results.getError(ResultsCode.INVALID_SECRET_QUESTION_ANSWER.getMessage(), ResultsCode.INVALID_SECRET_QUESTION_ANSWER.getCode()));
		}
	}
	/**
	 * @param secQuestAnsRequestVO
	 * @throws com.newco.marketplace.exception.BusinessServiceException
	 */
	private void processRequestForAutoGeneratedPasswordUsers(
			SecQuestAnsRequestVO secQuestAnsRequestVO) throws com.newco.marketplace.exception.BusinessServiceException {
		LostUsernameVO lostUsernameVO = new LostUsernameVO();
		lostUsernameVO.setUserName(secQuestAnsRequestVO.getUserName());
		lostUsernameVO.setPwdInd(secQuestAnsRequestVO.getPwdInd());
		lostUsernameVO.setEmailAddress(secQuestAnsRequestVO.getEmail());
		lostUsernameVO=(LostUsernameVO)forgotUsernameBO.validateAns(lostUsernameVO);
		secQuestAnsRequestVO.setSuccess(lostUsernameVO.getSuccessAns());
		if(!secQuestAnsRequestVO.isSuccess()){
			secQuestAnsRequestVO.setError(Results.getError(ResultsCode.FORGET_UNAME_PWD_RESET_EMAIL_ERROR.getMessage(), ResultsCode.FORGET_UNAME_PWD_RESET_EMAIL_ERROR.getCode()));
		}
	}
	/**
	 * @param secQuestAnsRequestVO
	 * @return
	 * method to validate security question answer
	 */
	public SecQuestAnsRequestVO validateAnsForForgotUserName(SecQuestAnsRequestVO secQuestAnsRequestVO) throws BusinessServiceException {

		try{

			if (StringUtils.isNotBlank(secQuestAnsRequestVO.getUserAnswer())) {
				if (StemmingSearch.compare(secQuestAnsRequestVO.getQuestionTxtAnswer(), secQuestAnsRequestVO.getUserAnswer())) {
					processValidAnswerForValidateAnsForForgotUserName(secQuestAnsRequestVO);
					
				}
				else{
					// in case of invalid secret question answer update retry count
					processInvalidAnswerForValidateAnsForForgotUserName(secQuestAnsRequestVO);
				}
			} else {
				secQuestAnsRequestVO.setSuccess(false);
				secQuestAnsRequestVO.setError(Results.getError(ResultsCode.EMPTY_SECRET_QUESTION_ANSWER.getMessage(), ResultsCode.EMPTY_SECRET_QUESTION_ANSWER.getCode()));// display error
			}


		}
		catch(Exception e){
			throw new BusinessServiceException(e);
		}
		return secQuestAnsRequestVO;
	}

	private void processInvalidAnswerForValidateAnsForForgotUserName(
			SecQuestAnsRequestVO secQuestAnsRequestVO) throws com.newco.marketplace.exception.BusinessServiceException {
		final int maxSecretQuestionAttempts = Integer
				.parseInt(PropertiesUtils
						.getPropertyValue(Constants.AppPropConstants.MAX_SECRET_QUESTION_ATTEMPTS_LIMIT));

		// get verification count
		int attemptCount = 0;
		// check if available in VO
		if(null!=secQuestAnsRequestVO.getVerificationAttemptCount()){
			attemptCount=secQuestAnsRequestVO.getVerificationAttemptCount().intValue();
		}
		else{
			attemptCount = forgotUsernameBO.getVerificationCount(secQuestAnsRequestVO.getUserName());;
		}

		forgotUsernameBO.updateVerificationCount(
				secQuestAnsRequestVO.getUserName(), ++attemptCount);


		if (attemptCount >= (maxSecretQuestionAttempts) || attemptCount == -1) {
			LOGGER.info("Secret Question attempt limit exceeded for user:"
					+ secQuestAnsRequestVO.getUserName());
			secQuestAnsRequestVO.setSuccess(false);
			secQuestAnsRequestVO.setError(Results.getError(ResultsCode.COUNT_EXCEEDED_SHOW_VERIFICATION_ZIP.getMessage(), ResultsCode.COUNT_EXCEEDED_SHOW_VERIFICATION_ZIP.getCode()));// display error		}
		}
		else{
			secQuestAnsRequestVO.setSuccess(false);
			secQuestAnsRequestVO.setError(Results.getError(ResultsCode.INVALID_SECRET_QUESTION_ANSWER.getMessage(), ResultsCode.INVALID_SECRET_QUESTION_ANSWER.getCode()));// display error
		}

	}


	/**
	 * @param secQuestAnsRequestVO
	 * @throws com.newco.marketplace.exception.BusinessServiceException 
	 */
	private void processValidAnswerForValidateAnsForForgotUserName(
			SecQuestAnsRequestVO secQuestAnsRequestVO) throws com.newco.marketplace.exception.BusinessServiceException {
		LostUsernameVO lostUsernameVO = formLostUserNameVO(secQuestAnsRequestVO);

		if (forgotUsernameBO.sendForgotUsernameMail(lostUsernameVO))// email
			// successfully sent
		{
			forgotUsernameBO.updateVerificationCount(secQuestAnsRequestVO.getUserName(), 0);

			secQuestAnsRequestVO.setSuccess(true);
			secQuestAnsRequestVO.setSuccess((Results.getSuccess(ResultsCode.RESET_USERNAME_EMAIL_SEND_SUCCESS.getCode(), ResultsCode.RESET_USERNAME_EMAIL_SEND_SUCCESS.getMessage())));// display message
		}
		else{
			// message on page// email send failure
			secQuestAnsRequestVO.setSuccess(false);
			secQuestAnsRequestVO.setError(Results.getError(ResultsCode.FORGET_UNAME_EMAIL_SEND_ERROR.getMessage(), ResultsCode.FORGET_UNAME_EMAIL_SEND_ERROR.getCode()));// display error
		}

		
	}
	/**
	 * @param lostUsernameVO
	 * @param secQuestAnsRequestVO
	 * @return
	 */
	private SecQuestAnsRequestVO convertToSecurityQuestAnsVO(
			LostUsernameVO lostUsernameVO, SecQuestAnsRequestVO secQuestAnsRequestVO) {
	
		
		secQuestAnsRequestVO.setEmail(lostUsernameVO.getEmailAddress());
		secQuestAnsRequestVO.setRoleId(lostUsernameVO.getUserId());
		if(StringUtils.isNotBlank(lostUsernameVO.getQuestionId())){
			secQuestAnsRequestVO.setQuestionId(Integer.parseInt(lostUsernameVO.getQuestionId()));
		}
		secQuestAnsRequestVO.setQuestionTxt(lostUsernameVO.getQuestionTxt());
		secQuestAnsRequestVO.setQuestionTxtAnswer(lostUsernameVO.getQuestionTxtAnswer());
		secQuestAnsRequestVO.setSuccess(lostUsernameVO.getSuccessAns());
		secQuestAnsRequestVO.setUserNameFromDB(lostUsernameVO.getUserName());
		secQuestAnsRequestVO.setPwdInd(lostUsernameVO.getPwdInd());
		secQuestAnsRequestVO.setZip(lostUsernameVO.getZip());
		secQuestAnsRequestVO.setBusinessName(lostUsernameVO.getBusinessName());
		secQuestAnsRequestVO.setPhoneNo(lostUsernameVO.getPhoneNo());
		secQuestAnsRequestVO.setDetailedProfile(lostUsernameVO.isDetailedProfile());
		secQuestAnsRequestVO.setLockedInd(lostUsernameVO.getLockedInd());		
		secQuestAnsRequestVO.setModifiedDate(lostUsernameVO.getModifiedDate());
		return secQuestAnsRequestVO;
	}


	/**
	 * @param secQuestAnsRequestVO
	 * @param lostUsernameVO
	 * @return
	 */
	private LostUsernameVO formLostUserNameVO(
			SecQuestAnsRequestVO secQuestAnsRequestVO) {
		LostUsernameVO lostUsernameVO = new LostUsernameVO();
		lostUsernameVO.setUserId(secQuestAnsRequestVO.getRoleId());
		lostUsernameVO.setEmailAddress(secQuestAnsRequestVO.getEmail());
		lostUsernameVO.setUserName(secQuestAnsRequestVO.getUserName());
		lostUsernameVO.setQuestionTxtAnswer(secQuestAnsRequestVO.getUserAnswer());
		lostUsernameVO.setQuestionTxt(secQuestAnsRequestVO.getQuestionTxt());
		if(null != secQuestAnsRequestVO.getQuestionId() ){
			lostUsernameVO.setQuestionId(secQuestAnsRequestVO.getQuestionId().toString());
		}
		lostUsernameVO.setZip(secQuestAnsRequestVO.getZip());
		lostUsernameVO.setBusinessName(secQuestAnsRequestVO.getBusinessName());
		lostUsernameVO.setPhoneNo(secQuestAnsRequestVO.getPhoneNo());
		lostUsernameVO.setLockedInd(secQuestAnsRequestVO.getLockedInd());
		lostUsernameVO.setDetailedProfile(secQuestAnsRequestVO.isDetailedProfile());
		return lostUsernameVO;
	}


	
	/**
	 * @param secQuestAnsRequestVO
	 * reset oAuth token
	 * @throws BusinessServiceException 
	 */
	public void resetMobileOauthToken(SecQuestAnsRequestVO secQuestAnsRequestVO) throws BusinessServiceException {
		try {
			if(secQuestAnsRequestVO.isSuccess()){
				String resourceId = null;
				resourceId = forgotUsernameBO
						.getResourceIdFromUserName(secQuestAnsRequestVO.getUserName());

				if (StringUtils.isNotBlank(resourceId)) {
					userManagementBO.expireMobileTokenforFrontEnd(
							Integer.parseInt(resourceId),
							MPConstants.ACTIVE);
				}
			}
		}
		catch (com.newco.marketplace.exception.BusinessServiceException e) {
			throw new BusinessServiceException(e);
		}
	}


	/**
	 * @param secQuestAnsRequestVO
	 * @return
	 * @throws BusinessServiceException 
	 */
	public SecQuestAnsRequestVO validateSecQuestAnsRequest(
			SecQuestAnsRequestVO secQuestAnsRequestVO) throws BusinessServiceException {

		// routing to two different business logic based on request type
		if(MPConstants.REQUEST_FOR_PASSWORD.equals(secQuestAnsRequestVO.getRequestFor())){
			secQuestAnsRequestVO = validateAnsForForgotPassword(secQuestAnsRequestVO);

		}else{
			secQuestAnsRequestVO = validateAnsForForgotUserName(secQuestAnsRequestVO);
		}
		// on Success reset the mobile oauth token
		resetMobileOauthToken(secQuestAnsRequestVO);
		return secQuestAnsRequestVO;
	}


	/**
	 * @param secQuestAnsRequestVO
	 * @return
	 */
	public SecQuestAnsRequestVO validateAdditionalVerification(
			SecQuestAnsRequestVO secQuestAnsRequestVO)throws BusinessServiceException {
		try{
		final int maxSecretQuestionAttempts = Integer
				.parseInt(PropertiesUtils
						.getPropertyValue(Constants.AppPropConstants.MAX_SECRET_QUESTION_ATTEMPTS_LIMIT));
		String userName = secQuestAnsRequestVO.getUserName();
		int count = forgotUsernameBO.getVerificationCount(userName);
		forgotUsernameBO.updateVerificationCount(userName, ++count);
		LostUsernameVO lostUsernameVO = new LostUsernameVO();
		lostUsernameVO= (LostUsernameVO)formLostUserNameVO(secQuestAnsRequestVO);
		boolean flag = forgotUsernameBO.doValidatePhoneAndZip(
				lostUsernameVO, secQuestAnsRequestVO.getUserPhoneNumber(),  secQuestAnsRequestVO.getUserZipCode(),
				secQuestAnsRequestVO.getUserCompanyName());

		if (flag) {
			LOGGER.info("Zip Validation successful");
			dispatchEmail(secQuestAnsRequestVO,lostUsernameVO);
		} else {
			if (count < (maxSecretQuestionAttempts * 2) && count != -1) {
				secQuestAnsRequestVO.setSuccess(false);
				secQuestAnsRequestVO.setError(Results.getError(ResultsCode.FORGET_UNAME_PWD_NO_USER_EXISTS.getMessage(), ResultsCode.FORGET_UNAME_PWD_NO_USER_EXISTS.getCode()));// display error
			} else {
				forgotUsernameBO.lockProfile(userName);
				secQuestAnsRequestVO.setSuccess(false);
				secQuestAnsRequestVO.setError(Results.getError(ResultsCode.FORGET_UNAME_PWD_LOCKED_USER.getMessage(), ResultsCode.FORGET_UNAME_PWD_LOCKED_USER.getCode()));// display error

			}
		}
		resetMobileOauthToken(secQuestAnsRequestVO);
		}
		catch(Exception e){
			throw new BusinessServiceException(e);
		}
		return secQuestAnsRequestVO;
	}
	private SecQuestAnsRequestVO dispatchEmail(SecQuestAnsRequestVO secQuestAnsRequestVO, LostUsernameVO lostUsernameVO) throws com.newco.marketplace.exception.BusinessServiceException {
		boolean statusEmail = false;
		if (MPConstants.REQUEST_FOR_USERNAME.equals(secQuestAnsRequestVO.getRequestFor())) {
			statusEmail = forgotUsernameBO
					.sendForgotUsernameMail(lostUsernameVO);
			if (statusEmail) {// email successfully sent
				secQuestAnsRequestVO.setSuccess(true);
				secQuestAnsRequestVO.setSuccess((Results.getSuccess(ResultsCode.RESET_USERNAME_EMAIL_SEND_SUCCESS.getCode(), ResultsCode.RESET_USERNAME_EMAIL_SEND_SUCCESS.getMessage())));// display error
			}
			else{
				secQuestAnsRequestVO.setSuccess(false);
				secQuestAnsRequestVO.setError(Results.getError(ResultsCode.FORGET_UNAME_EMAIL_SEND_ERROR.getMessage(), ResultsCode.FORGET_UNAME_EMAIL_SEND_ERROR.getCode()));// display error
			}
			
		} else { // resetpassword action
			LostUsernameVO lostUsernameVOForResetPassword= new LostUsernameVO();
			lostUsernameVOForResetPassword.setUserName(secQuestAnsRequestVO.getUserName());
			statusEmail = forgotUsernameBO.resetPassword(lostUsernameVOForResetPassword);
			LOGGER.info("Password has been reset");
			// message on page// email send success
			if (statusEmail) {// email successfully sent
				secQuestAnsRequestVO.setSuccess(true);
				secQuestAnsRequestVO.setSuccess((Results.getSuccess(ResultsCode.RESET_PASSWORD_EMAIL_SEND_SUCCESS.getCode(), ResultsCode.RESET_PASSWORD_EMAIL_SEND_SUCCESS.getMessage())));// display error
			}
			else{
				secQuestAnsRequestVO.setSuccess(false);
				secQuestAnsRequestVO.setError(Results.getError(ResultsCode.FORGET_UNAME_PWD_RESET_EMAIL_ERROR.getMessage(), ResultsCode.FORGET_UNAME_PWD_RESET_EMAIL_ERROR.getCode()));// display error
			}
		}
		
		return secQuestAnsRequestVO;

	}
	
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
	public ProblemResolutionSoVO getProblemDesc(String soId) throws BusinessServiceException{
		ProblemResolutionSoVO pbResVo = null;
		try{
			pbResVo = mobileGenericDao.getProblemDesc(soId);
		}catch(Exception e){
			throw new BusinessServiceException(e);
		}
		return pbResVo;
	}
	
	/**
	 * 
	 * @param soId
	 * @param estimationId
	 * @return boolean
	 * @throws BusinessServiceException
	 */
	public Boolean isEstimationIdExists(String soId, Integer estimationId) throws BusinessServiceException{
		Boolean isEstimationIdExist =null;
		try {
			isEstimationIdExist=mobileGenericDao.isEstimationIdExists(soId, estimationId);
		} catch(Exception e){
			throw new BusinessServiceException(e);
		}
		return isEstimationIdExist;
	}
	/**
	 * B2C : method to fetch the estimate details 
	 * @param soId
	 * @param estimateId
	 * @return
	 * @throws BusinessServiceException
	 */
	public EstimateVO getEstimateDetails(String soId, Integer estimateId)throws BusinessServiceException {
		
		EstimateVO estimateVO = new EstimateVO();
		
		try {
			estimateVO = mobileGenericDao.getEstimate(soId,estimateId);
			if(null!= estimateVO && StringUtils.isNotBlank(estimateVO.getStatus())
					&& (estimateVO.getStatus().equals(MPConstants.ACCEPTED_STATUS) 
							|| estimateVO.getStatus().equals(MPConstants.DECLINED_STATUS)
							|| estimateVO.getStatus().equals(MPConstants.UPDATED_STATUS))){
				String timeZone = mobileGenericDao.getServiceLocnTimeZone(soId);
				estimateVO.setTimeZone(timeZone);
				
			}
		}
		catch(Exception e){
			throw new BusinessServiceException(e);
		}
		return estimateVO;
	}
	
	public EstimateVO getEstimateByVendorAndResource(Map<String, Object> param) throws BusinessServiceException{
		EstimateVO estimateVO = null;
		try{
			estimateVO = mobileGenericDao.getEstimateByVendorAndResource(param);

		}catch(Exception e){
			throw new BusinessServiceException(e);
		}
		return estimateVO;
	}
    

	/***@Description: This method will either add or update a an Estimate for the service order
	 * @param estimateVO
	 * @param estimationId
	 * @return
	 * @throws BusinessServiceException
	 */
	public EstimateVO saveEditEstimate(EstimateVO estimateVO,Integer estimationId)throws BusinessServiceException {
		try{
			estimateVO = mobileGenericDao.saveEstimationDetails(estimateVO,estimationId);
		}catch (Exception e) {
			LOGGER.error("Exception in saving/updating estimate details for the service order"+ e);
			throw new BusinessServiceException(e);
		}
		return estimateVO;
	}
	
	/**
	 * Fetch the estimation details of an SO
	 * @param soId
	 * @param vendorId
	 * @return List<EstimateVO>
	 * @throws BusinessServiceException
	 */
	public List<EstimateVO> getEstimationDetails(String soId,Integer vendorId) throws BusinessServiceException{
		
		List<EstimateVO> estimateDetails = null;
		try {
			estimateDetails = serviceOrderBo.getEstimationDetails(soId,vendorId);
		}
		catch(BusinessServiceException e){
			throw new BusinessServiceException(e);
		}
		return estimateDetails;
	}

	/**@Description : This method will fetch the status of estimate 
	 * @param soId
	 * @param estimationId
	 * @return
	 * @throws BusinessServiceException
	 */
	public String validateEstimateStatus(String soId, Integer estimationId) throws BusinessServiceException {
		String status = null;
		try{
			status = mobileGenericDao.validateEstimateStatus(soId,estimationId);
		}catch (Exception e) {
			LOGGER.error("Exception in validating Estimate Status for the service order Estimate"+e);
			throw new BusinessServiceException(e);
		}
		
		return status;
	}
	
		public Map<Long, String> retrieveRoutedProvidersNameMap(String soId) throws BusinessServiceException {
		Map<Long, String> providerNamesMap = null;
		try{
			List<RoutedProvider> routedResources = serviceOrderBo.getRoutedProviders(soId);
			if (routedResources == null || routedResources.isEmpty()) return Collections.emptyMap();

		    providerNamesMap = new HashMap<Long, String>(routedResources.size());
			for (RoutedProvider provider : routedResources) {
				StringBuilder providerName = new StringBuilder(50);
				if (provider.getProviderContact() != null) {
					String providerFirstName = provider.getProviderContact().getFirstName();
					if (StringUtils.isNotEmpty(providerFirstName)) {
						providerName.append(providerFirstName).append(" ");
					}
					
					String providerLastName = provider.getProviderContact().getLastName();
					if (StringUtils.isNotEmpty(providerLastName)) {
						providerName.append(providerLastName);
					}
				}
				else {
					providerName.append("Error getting Provider Name");
				}
				providerNamesMap.put(provider.getResourceId().longValue(),
						providerName.toString().trim());
			}
			
		}
		catch(BusinessServiceException e){
			throw new BusinessServiceException(e);
		}
		return providerNamesMap;
	}
	
	
	/**
	 * Fetch the sealed bid ind of SO
	 * @param soId
	 * @return Boolean
	 * @throws BusinessServiceException
 	*/
	public Boolean fetchSealedBidIndicator(String soId) throws BusinessServiceException{
		
		Boolean sealedBidInd=false;
		try {
			sealedBidInd = serviceOrderBo.fetchSealedBidIndicator(soId);
		}
		catch(BusinessServiceException e){
			throw new BusinessServiceException(e);
		}
		return sealedBidInd;
	}
	
	/** Returns service date time zone offset for an so
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getServiceDateTimeZoneOffset(String soId)throws BusinessServiceException {
		Integer timeZone=null;
		try {
			timeZone = mobileGenericDao.getServiceDateTimeZoneOffset(soId);
		} catch(Exception e){
			throw new BusinessServiceException(e);
		}
		return timeZone;
	}
	/**
	 * @param bidResourceId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getVendorId(Integer bidResourceId)
			throws BusinessServiceException {
		Integer vendorId=null;
		try {
			vendorId = mobileGenericDao.getVendorId(bidResourceId);
		} catch(Exception e){
			throw new BusinessServiceException(e);
		}
		return vendorId;
	}

	/**SL-21451: fetching bidRequests
	 * @param dashboardVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getBidRequestCount(MobileDashboardVO dashboardVO)
			throws BusinessServiceException {
		Integer bidRequests = null;
		  try{
	        	bidRequests = mobileGenericDao.getBidRequestCount(dashboardVO);
	        }catch (DataServiceException e) {
	        	LOGGER.error("error occured in MobileGenericBOImpl.getBidRequestCount()"+ e.getMessage());
				throw new BusinessServiceException(e);
			}
			return bidRequests;
	}
	
	
	public Map<String,String> getOrderTypes(List<String> serviceOrderIds)throws BusinessServiceException{
		Map<String,String> orderTypes=new HashMap<String,String>();
		  try{
			  orderTypes = mobileGenericDao.getOrderTypes(serviceOrderIds);
	        }catch (DataServiceException e) {
	        	LOGGER.error("error occured in MobileGenericBOImpl.getOrderTypes()"+ e.getMessage());
				throw new BusinessServiceException(e);
			}
			return orderTypes;
	}
	
	public Map<String,List<EstimateVO>> getEstimationList(RecievedOrdersCriteriaVO criteriaVO)throws BusinessServiceException{
		Map<String,List<EstimateVO>> estimationMap=new HashMap<String,List<EstimateVO>>();
		  try{
			  List<EstimateVO> estimateList =new ArrayList<EstimateVO>();			  
			  estimateList = mobileGenericDao.getEstimationList(criteriaVO);			  
			  if(null!=estimateList && estimateList.size()>0){
				  for(EstimateVO estimateVO:estimateList){
					  if(null!=estimationMap && estimationMap.containsKey(estimateVO.getSoId())){
						  List<EstimateVO> estimationList= estimationMap.get(estimateVO.getSoId());
						  estimationList.add(estimateVO);
						  estimationMap.put(estimateVO.getSoId(), estimationList);
					  }else{
						  List<EstimateVO> estimationList= new ArrayList<EstimateVO>();
						  estimationList.add(estimateVO);
						  estimationMap.put(estimateVO.getSoId(), estimationList);
					  }
				  }
			  }
	        }catch (DataServiceException e) {
	        	LOGGER.error("error occured in MobileGenericBOImpl.getEstimationList()"+ e.getMessage());
				throw new BusinessServiceException(e);
			}
		  return estimationMap;
	}

	public boolean isvalidSoState(String soId) throws BusinessServiceException{
		boolean  isvalidSoState=false;

    try{
    	Integer validSoCount = mobileGenericDao.isvalidSoState(soId);
    	if(null!=validSoCount && validSoCount.intValue()==1){
    		isvalidSoState=true;
    	}
	   }catch (DataServiceException e) {
	        	LOGGER.error("error occured in MobileGenericBOImpl.isvalidSoState()"+ e.getMessage());
				throw new BusinessServiceException(e);
			}
	return isvalidSoState; 
	}
	
	//SL-21848
	public void updateSOEstimateAsDeclined(Map<String, Object> param){
		LOGGER.info("started the execution to update estimate status to declined when SO is released");
		//boolean relayServicesNotifyFlag = false;
		Integer buyerId = null;
		
		EstimateVO estimateVO = null;
			try{
				estimateVO = getEstimateByVendorAndResource(param);

				if(null != estimateVO){
					estimateVO.setModifiedBy((String)param.get("userName"));
					estimateVO.setStatus("soReleased");
					estimateVO.setAcceptSource((String)param.get("acceptSource"));
					estimateVO = saveEditEstimate(estimateVO, estimateVO.getEstimationId());
					LOGGER.info("ended the execution to update estimate status to declined when SO is released");

					/*

					ServiceOrder serviceOrder = mobileSOActionsBO.getServiceOrder(estimateVO.getSoId());

					if (null != serviceOrder) {
						buyerId = serviceOrder.getBuyerId();
						relayServicesNotifyFlag = relayNotificationService.isRelayServicesNotificationNeeded(buyerId, estimateVO.getSoId());

						if (relayServicesNotifyFlag) {
							LOGGER.info("relayServicesNotifyFlag true to send the notification and update the estimate status to true when SO released ");

							estimateVO.setModifiedBy((String)param.get("userName"));
							estimateVO.setStatus("DECLINED");
							estimateVO.setAcceptSource((String)param.get("acceptSource"));
							estimateVO = saveEditEstimate(estimateVO, estimateVO.getEstimationId());

							relayNotificationService.sentNotificationRelayServices(MPConstants.UPDATE_ESTIMATE_API_EVENT,
									estimateVO.getSoId());

						}
					}



					LOGGER.info("ended the execution to update estimate status to declined when SO is released");

				*/}else{
					LOGGER.info("EstimationVO is null for the order :"+(String)param.get("soId"));
				}

			}catch(Exception e){
				LOGGER.error("Exception thrown in execute of updateSOEstimateAsDeclined "+ e.getMessage());
			}
		




	}


	public IRelayServiceNotification getRelayNotificationService() {
		return relayNotificationService;
	}


	public void setRelayNotificationService(
			IRelayServiceNotification relayNotificationService) {
		this.relayNotificationService = relayNotificationService;
	}

	public IMobileSOActionsBO getMobileSOActionsBO() {
		return mobileSOActionsBO;
	}

	public void setMobileSOActionsBO(IMobileSOActionsBO mobileSOActionsBO) {
		this.mobileSOActionsBO = mobileSOActionsBO;
	}
	
	public  void GMTToGivenTimeZoneForSlots(RetrieveSODetailsMobileVO detailsMobileVO) {
		if(null != detailsMobileVO.getSoDetails().getScheduleServiceSlot().getServiceDatetimeSlots().getServiceDatetimeSlot() 
				&& detailsMobileVO.getSoDetails().getScheduleServiceSlot().getServiceDatetimeSlots().getServiceDatetimeSlot().size()>0){
			
			List<com.newco.marketplace.dto.vo.dateTimeSlots.ServiceDatetimeSlot> serviceDatetimeSlot = detailsMobileVO.getSoDetails()
					.getScheduleServiceSlot()
					.getServiceDatetimeSlots()
					.getServiceDatetimeSlot();
			
			for(com.newco.marketplace.dto.vo.dateTimeSlots.ServiceDatetimeSlot slot : serviceDatetimeSlot){
				HashMap<String, Object> serviceDate1 = null;
				HashMap<String, Object> serviceDate2 = null;
				String startTime = null;
				String endTime = null;
				String widgetDateFormat = "yyyy-MM-dd"; // Needed for dojo widget.  Bug in dojo 0.9 allows only this format.

				String startDate= slot.getServiceStartDate();

				startTime = slot.getServiceStartTime(); 
				endTime = slot.getServiceEndTime();
				if (slot.getServiceStartDate() != null && startTime != null) {
				

					serviceDate1 = TimeUtils.convertGMTToGivenTimeZone(Timestamp.valueOf(slot.getServiceStartDate()), startTime, slot.getTimeZone());
					if (serviceDate1 != null && !serviceDate1.isEmpty()) {
						slot.setServiceStartDate(DateUtils.getFormatedDate((Timestamp)serviceDate1.get(OrderConstants.GMT_DATE),widgetDateFormat));
						slot.setServiceStartTime((String) serviceDate1.get(OrderConstants.GMT_TIME));
					}
				}
				serviceDate2 = TimeUtils.convertGMTToGivenTimeZone(Timestamp.valueOf(slot.getServiceEndDate()), endTime, slot.getTimeZone());
				if (serviceDate2 != null && !serviceDate2.isEmpty()) {
					slot.setServiceEndTime((String) serviceDate2.get(OrderConstants.GMT_TIME));
					slot.setServiceEndDate(null);//it is not required in response
				}
				
				String dlsFlag = "N";

				if (detailsMobileVO.getSoDetails().getServiceLocation() != null
						&& !StringUtils.isBlank(detailsMobileVO.getSoDetails().getServiceLocation().getZip())) {
					String zip = detailsMobileVO.getSoDetails().getServiceLocation().getZip();
					try {
						dlsFlag = lookupBO.getDaylightSavingsFlg(zip);
					} catch (DataServiceException e) {
						// TODO Auto-generated catch block
						LOGGER.error("Exception GMTToGivenTimeZoneForSlots: "+e.getMessage());

					}
				}
				String newTimeZone;
				if ("Y".equals(dlsFlag)) {
					TimeZone tz = TimeZone.getTimeZone(slot.getTimeZone());
					Timestamp timeStampDate = null;
					try {
						if (slot.getServiceStartDate() != null 
								&& (StringUtils.isNotBlank(startTime))) {
							java.util.Date dt = (java.util.Date) TimeUtils
									.combineDateTime(
											Timestamp.valueOf(startDate),
											startTime);
							timeStampDate = new Timestamp(dt.getTime());
						}
					} catch (ParseException pe) {
						pe.printStackTrace();
					}

					if (null != timeStampDate) {
						boolean isDLSActive = tz.inDaylightTime(timeStampDate);

						if (isDLSActive) {
							newTimeZone = TimeUtils.getDSTTimezone(slot.getTimeZone());
							slot.setTimeZone(newTimeZone);
						} else {
							newTimeZone = TimeUtils.getNormalTimezone(slot.getTimeZone());
							slot.setTimeZone(newTimeZone);
						}
					}
				} 
				else {
					newTimeZone =TimeUtils.getNormalTimezone(slot.getTimeZone());
					slot.setTimeZone(newTimeZone);
				}
				
			}

				

				

			
			}
			
			

		}
		

  public void mapSlots(RetrieveSODetailsMobileVO detailsMobileVO,List<ServiceDatetimeSlot> serviceDatetimeSlotSrc, String timeZone){
	 
	  List<com.newco.marketplace.dto.vo.dateTimeSlots.ServiceDatetimeSlot> serviceDatetimeSlot = null;
	  
	  if(detailsMobileVO.getSoDetails().getScheduleServiceSlot() != null && null != serviceDatetimeSlotSrc && serviceDatetimeSlotSrc.size() >0){
		  serviceDatetimeSlot= new ArrayList<com.newco.marketplace.dto.vo.dateTimeSlots.ServiceDatetimeSlot>();
		  for(ServiceDatetimeSlot slotSrc:serviceDatetimeSlotSrc){
			 com.newco.marketplace.dto.vo.dateTimeSlots.ServiceDatetimeSlot slot = new com.newco.marketplace.dto.vo.dateTimeSlots.ServiceDatetimeSlot ();
			 if(null !=slotSrc.getServiceStartDate()){
				 slot.setServiceStartDate(slotSrc.getServiceStartDate().toString());
			 }
			 if(null !=slotSrc.getServiceEndDate()){
				 slot.setServiceEndDate(slotSrc.getServiceEndDate().toString());
			 }
			 slot.setServiceStartTime(slotSrc.getServiceStartTime());
			 slot.setServiceEndTime(slotSrc.getServiceEndTime());
			 if("1".equals(slotSrc.getSlotSelected())){
				 slot.setSlotSeleted("true"); 
			 }else{
				 slot.setSlotSeleted("false"); 
			 }
			
			 slot.setPreferenceInd(slotSrc.getPreferenceInd());
			 slot.setTimeZone(timeZone);
			 
			 serviceDatetimeSlot.add(slot);
			
			
		  }
		  detailsMobileVO.getSoDetails().getScheduleServiceSlot().getServiceDatetimeSlots().setServiceDatetimeSlot(serviceDatetimeSlot);
	  }
	
	
  }


public ServiceOrderDao getServiceOrderDao() {
	return serviceOrderDao;
}


public void setServiceOrderDao(ServiceOrderDao serviceOrderDao) {
	this.serviceOrderDao = serviceOrderDao;
}
  
  
  
  }
