package com.newco.marketplace.api.mobile.services.v2_0;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.v2_0.SORevisitNeededRequest;
import com.newco.marketplace.api.mobile.beans.v2_0.SORevisitNeededResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestMsgBody;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestOrder;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestOrders;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestReschedInformation;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestRescheduleInfo;
import com.newco.marketplace.buyeroutboundnotification.constatns.BuyerOutBoundConstants;
import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundNotificationJMSService;
import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundNotificationService;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundFailOverVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeRescheduleVO;
import com.newco.marketplace.mobile.api.utils.validators.v2_0.SORevisitNeededValidator;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.relayservicesnotification.service.IRelayServiceNotification;
import com.newco.marketplace.util.TimeChangeUtil;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.vo.mobile.ProviderHistoryVO;
import com.newco.marketplace.vo.mobile.v2_0.SORevisitNeededVO;

/**
 * This class would act as a Service for SO Revisit Needed
 * 
 * @author Infosys
 * @version 1.0
 */
@APIRequestClass(SORevisitNeededRequest.class)
@APIResponseClass(SORevisitNeededResponse.class)
public class SORevisitNeededService extends BaseService {
	private Logger logger = Logger.getLogger(SORevisitNeededService.class);
	private IMobileSOActionsBO mobileSOActionsBO;
	private SORevisitNeededValidator revisitNeededValidator;
	private IBuyerOutBoundNotificationService  buyerOutBoundNotificationService;
	private IBuyerOutBoundNotificationJMSService buyerOutBoundNotificationJMSService;
	private INotificationService notificationService;
	private IRelayServiceNotification relayNotificationService;
	
	public SORevisitNeededService() {
		// calling the BaseService constructor to initialize
		super();
	}

	/**
	 * Logic for handing time on site API request
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Inside SORevisitNeededService.execute()");		
		long startTime = System.currentTimeMillis();
		
		Integer buyerId = null;
		Integer vendorId = null;
		Integer wfStateId = null;
		String timeZone = null;
		String userName = null;
		Integer entityId = null;
		Integer roleId = null;
		String createdBy = null;
		SORevisitNeededResponse soRevisitNeededResponse = null;
		SORevisitNeededVO revisitNeededVO = null;
		SORevisitNeededRequest revisitNeededRequest = null;
		boolean relayServicesNotifyFlag = false;
		
		// get the so Id from the apiVO
		String soId = (String) apiVO.getSOId();
		Integer providerId = apiVO.getProviderResourceId();
		SecurityContext securityContext = getSecurityContextForVendor(providerId);
		if(null != securityContext){
			userName = securityContext.getUsername();
			entityId = securityContext.getVendBuyerResId();
			roleId = securityContext.getRoleId();
			LoginCredentialVO lvRoles = securityContext.getRoles();
			createdBy = lvRoles.getFirstName() +" "+lvRoles.getLastName();
		}
		
		try {
			revisitNeededRequest = (SORevisitNeededRequest)apiVO.getRequestFromPostPut();
			
			// Fetching buyerId, wfStateId and service location time zone, current appointment data from so_hdr table for the given so_id.
			// Used to update sub-status change for in-home orders in NPS.
			ServiceOrder serviceOrder = mobileSOActionsBO.getServiceOrder(soId);
			buyerId = serviceOrder.getBuyerId();
			wfStateId = serviceOrder.getWfStateId();
			timeZone = serviceOrder.getServiceLocationTimeZone();
			vendorId = serviceOrder.getAcceptedVendorId();
						
			// Validate request details
			revisitNeededValidator.setTimeZone(timeZone);
			soRevisitNeededResponse = revisitNeededValidator.validateRequest(revisitNeededRequest, soId);
			
			// Return error response if there exists any validation errors.
			if(null != soRevisitNeededResponse){
				soRevisitNeededResponse.setSoId(soId);
				return soRevisitNeededResponse;
			}
			
			// Map Request details to revisitNeededVO.
			if(null != revisitNeededRequest){
				revisitNeededVO = mapSORevisitNeededVO(revisitNeededRequest, soId, providerId, createdBy, serviceOrder);
			}
			
			// Re-using updateSoCompletion() method to update sub-status and NPS
			// Resolution comments is set as null to avoid the update of the same in so_hdr. 
			if(MPConstants.CUSTOMER_NOT_HOME.equals(revisitNeededRequest.getRevisitReason())){
				mobileSOActionsBO.updateSoSubStatus(soId, MPConstants.CNH_REVISIT_NEEDED_SUBSTATUS_ID);
			}else{
				mobileSOActionsBO.updateSoSubStatus(soId, MPConstants.PARTIAL_SUBSTATUS_ID);
			}
			
			// Logging history for sub-status change in so_logging table. 
			historyLogging(roleId,revisitNeededVO,userName,entityId,MPConstants.SUBSTATUS_ACTION_ID,createdBy);
			
			// Updating so_schedule and so_schedule_history for revisit needed in so_trip table.
			if(MPConstants.CUSTOMER_NOT_HOME.equals(revisitNeededRequest.getRevisitReason())){
				updateSOSchedule(revisitNeededVO,vendorId,userName, MPConstants.SCHEDULE_NEEDED_SCHEDULE_STATUS);
			}else{
				// Update appointment date and time in so_hdr
				// Even though we have a fixed date, we are using date range as service date type
				// because we will not consider end time in fixed date type. Hence both start date and end date will be same.
				revisitNeededVO = updateAppointmentDateTime(revisitNeededVO, timeZone);
				
				// Insert an entry in so_logging table for reschedule change.
				historyLogging(roleId,revisitNeededVO,userName,entityId,MPConstants.REVISIT_NEEDED_ACTION_ID,createdBy);
				
				updateSOSchedule(revisitNeededVO,vendorId,userName, MPConstants.REVISIT_SCHEDULED_SCHEDULE_STATUS);
			}
			
			// Updating trip details for revisit needed in so_trip table.
			mobileSOActionsBO.updateRevisitNeeded(revisitNeededVO);
			
			// Update NPS for reschedule change for In-home
			if(MPConstants.HSR_BUYER_ID_INTEGER.equals(buyerId) ){
				updateNPSForInHome(soId,buyerId,revisitNeededRequest,revisitNeededVO);
				// Update NPS for In-home by calling Service Operations API
				try{
					//SLT-4048:Flag to decide whether to send  Reschedule event to NPS through Platform
					if(BuyerOutBoundConstants.ON.equalsIgnoreCase(BuyerOutBoundConstants.INHOME_OUTBOUND_STOP_RESCHEDULE_EVENT_FLAG_INPLATFORM)){
					updateNPSForInHomeServiceOperationsAPI(soId,buyerId,revisitNeededRequest,revisitNeededVO);
					}
				}catch(Exception e){
					logger.error("Exception caught in updateNPSForInHomeServiceOperationsAPI method ( SORevisitNeededService class)  :  "
							+ e.getMessage());
				}
			}
			// Update NPS for reschedule change for RI(Choice)
			else if(MPConstants.RI_BUYER_ID.equals(buyerId) && !MPConstants.CUSTOMER_NOT_HOME.equals(revisitNeededRequest.getRevisitReason())){
				updateNPSForRI(revisitNeededVO, timeZone);
			}
			
			// Senting Notification for Relay Services
			relayServicesNotifyFlag=relayNotificationService.isRelayServicesNotificationNeeded(buyerId,soId);
			
			if(relayServicesNotifyFlag){
				Map<String, String> metaData = new HashMap<String, String>();
				metaData.put("startdate", URLEncoder.encode(revisitNeededVO.getNextApptDateString(),"utf-8"));
				metaData.put("starttime", URLEncoder.encode(revisitNeededVO.getNextApptStartTime(),"utf-8"));
				metaData.put("endtime", URLEncoder.encode(revisitNeededVO.getNextApptEndTime(),"utf-8"));
				metaData.put("timezone", URLEncoder.encode(revisitNeededVO.getTimeZone(),"utf-8"));
				relayNotificationService.sentNotificationRelayServices(MPConstants.SO_REVISIT_NEEDED_API_EVENT, soId, metaData);
			}
			
			// return the success message
			soRevisitNeededResponse = new SORevisitNeededResponse(soId, Results.getSuccess());
		} catch (Exception exception) {
			logger.error("SORevisitNeededService.execute(): exception due to "
					+ exception);
			// set the error code as internal server error
			soRevisitNeededResponse = SORevisitNeededResponse
					.getInstanceForError(ResultsCode.INTERNAL_SERVER_ERROR,
							soId);
		}
		if (logger.isInfoEnabled()) {
			logger.info("Leaving SORevisitNeededService.execute()");
		}

		
		long endTime = System.currentTimeMillis();
		logger.info("Time taken for execute method for soRevisitNeeded :::"+(endTime- startTime));
		// return the response object
		return soRevisitNeededResponse;
	}
    // Method to update schedule details in so_schedule and so_schedule_history 
	private void updateSOSchedule(SORevisitNeededVO revisitNeededVO, Integer vendorId, String userName, Integer scheduleStatus) {
		revisitNeededVO.setVendorId(vendorId);
		revisitNeededVO.setScheduleSource(MPConstants.REVISIT_NEEDED_SCHEDULE_SOURCE);
		revisitNeededVO.setScheduleStatusId(scheduleStatus);
		revisitNeededVO.setResponseReasonId(null);
		revisitNeededVO.setCustomerApptConfirmInd(null);
		revisitNeededVO.setEta(null);
		revisitNeededVO.setUserName(userName);
		try {
			mobileSOActionsBO.updateSOScheduleForRevisit(revisitNeededVO);
		} catch (BusinessServiceException e) {
			logger.error("Exception Occured in SORevisitNeededService-->updateSOSchedule()"+e);
		}
	}

	private SORevisitNeededVO updateAppointmentDateTime(SORevisitNeededVO revisitNeededVO, String timeZone) throws BusinessServiceException {
		HashMap<String, Object> dateTimeMap = null;
		dateTimeMap = TimeUtils.convertToGMT(revisitNeededVO.getNextApptDate(), revisitNeededVO.getNextApptStartTime(), timeZone);
		revisitNeededVO.setServiceTimeStart(dateTimeMap.get(MPConstants.TIME_PARAMETER).toString());
		revisitNeededVO.setServiceDateStart((Timestamp) dateTimeMap.get(MPConstants.DATE_PARAMETER));
		dateTimeMap = TimeUtils.convertToGMT(revisitNeededVO.getNextApptDate(), revisitNeededVO.getNextApptEndTime(), timeZone);
		revisitNeededVO.setServiceTimeEnd(dateTimeMap.get(MPConstants.TIME_PARAMETER).toString());
		revisitNeededVO.setServiceDateEnd((Timestamp) dateTimeMap.get(MPConstants.DATE_PARAMETER));
		mobileSOActionsBO.updateAppointmentDateTime(revisitNeededVO);
		return revisitNeededVO;
	}

	private void updateNPSForInHome(String soId, Integer buyerId, SORevisitNeededRequest revisitNeededRequest, SORevisitNeededVO revisitNeededVO) {
    	InHomeRescheduleVO input=new InHomeRescheduleVO();
    	InHomeRescheduleVO result = null;
    	boolean isEligibleForNPSNotification = false;
    	input.setSoId(soId);
    	input.setServiceDate1(revisitNeededVO.getCurrentServiceDateStart());
    	input.setServiceDate2(revisitNeededVO.getCurrentServiceDateEnd());
    	input.setStartTime(revisitNeededVO.getCurrentServiceTimeStart());
    	input.setEndTime(revisitNeededVO.getCurrentServiceTimeEnd());
		input.setRescheduleDate1(revisitNeededRequest.getNextApptDate());
		input.setRescheduleDate2(revisitNeededRequest.getNextApptDate());
		input.setRescheduleStartTime(revisitNeededRequest.getNextApptStartTime());
		input.setRescheduleEndTime(revisitNeededRequest.getNextApptEndTime());
		input.setIsNPSMessageRequired(true);
		input.setRevisitReason(revisitNeededRequest.getRevisitReason());
    	try{
    		//call validation method for NPS notification
			isEligibleForNPSNotification = notificationService.validateNPSNotificationEligibility(buyerId,soId);
    		
			if(isEligibleForNPSNotification){
	    		//Creating message for NPS update
				result = notificationService.getSoDetailsForReschedule(input);
				
				//Call Insertion method for NPS notification
				notificationService.insertNotification(soId,result.getRescheduleMessage());
			}
    	}catch(Exception e){
			logger.info("Caught Exception while insertNotification"+e);
		}
	}

	//R12_0 inserting request xml of Service Operations API into buyer_outbound_notification table (revisit needed)
	private void updateNPSForInHomeServiceOperationsAPI(String soId, Integer buyerId, SORevisitNeededRequest revisitNeededRequest, SORevisitNeededVO revisitNeededVO) {
    	InHomeRescheduleVO input=new InHomeRescheduleVO();
    	boolean isEligibleForNPSNotification = false;
    	input.setSoId(soId);
    	input.setServiceDate1(revisitNeededVO.getCurrentServiceDateStart());
    	input.setServiceDate2(revisitNeededVO.getCurrentServiceDateEnd());
    	input.setStartTime(revisitNeededVO.getCurrentServiceTimeStart());
    	input.setEndTime(revisitNeededVO.getCurrentServiceTimeEnd());
		input.setRescheduleDate1(revisitNeededRequest.getNextApptDate());
		input.setRescheduleDate2(revisitNeededRequest.getNextApptDate());
		input.setRescheduleStartTime(revisitNeededRequest.getNextApptStartTime());
		input.setRescheduleEndTime(revisitNeededRequest.getNextApptEndTime());
		input.setIsNPSMessageRequired(true);
		input.setRevisitReason(revisitNeededRequest.getRevisitReason());
    	try{
    		//call validation method for NPS notification
			isEligibleForNPSNotification = notificationService.validateNPSNotificationEligibility(buyerId,soId);
    		
			if(isEligibleForNPSNotification){
				//Call Insertion method for NPS notification
				notificationService.insertNotificationServiceOperationsAPI(soId,input);
			}
    	}catch(Exception e){
			logger.info("Caught Exception while insertNotification"+e);
		}
	}
	
	
	private SORevisitNeededVO mapSORevisitNeededVO(SORevisitNeededRequest revisitNeededRequest, String soId, 
			Integer providerId, String createdBy, ServiceOrder serviceOrder) {
		DateFormat formatter = new SimpleDateFormat(MPConstants.DATE_FORMAT_DISPLAYED_FOR_ACCEPT);
		SORevisitNeededVO revisitNeededVO = new SORevisitNeededVO();
		revisitNeededVO.setSoId(soId);
		revisitNeededVO.setResourceId(providerId);
		revisitNeededVO.setTripNumber(revisitNeededRequest.getTripNumber());
		try {
			if(null != revisitNeededRequest.getNextApptDate()){
				revisitNeededVO.setNextApptDate(new Timestamp(formatter.parse(revisitNeededRequest.getNextApptDate()).getTime()));
			}
		} catch (ParseException e) {
			logger.error(e);
		}
		revisitNeededVO.setNextApptDateString(revisitNeededRequest.getNextApptDate());
		revisitNeededVO.setNextApptStartTime(revisitNeededRequest.getNextApptStartTime());
		revisitNeededVO.setNextApptEndTime(revisitNeededRequest.getNextApptEndTime());
		revisitNeededVO.setTimeZone(serviceOrder.getServiceLocationTimeZone());
		
		// If current service date and time details are null these details will be persisted in so_trip table.
		revisitNeededVO.setCurrentServiceDateStart(serviceOrder.getServiceDate1());
		revisitNeededVO.setCurrentServiceDateEnd(serviceOrder.getServiceDate2());
		revisitNeededVO.setCurrentServiceTimeStart(serviceOrder.getServiceTimeStart());
		revisitNeededVO.setCurrentServiceTimeEnd(serviceOrder.getServiceTimeEnd());
		
		revisitNeededVO.setRevisitReason(revisitNeededRequest.getRevisitReason());
		revisitNeededVO.setMerchDeliveredInd((null == revisitNeededRequest.getMerchDeliveredInd()?0:revisitNeededRequest.getMerchDeliveredInd()));
		revisitNeededVO.setWorkStartedInd((null == revisitNeededRequest.getWorkStartedInd()?0:revisitNeededRequest.getWorkStartedInd()));
		revisitNeededVO.setTripComments(revisitNeededRequest.getRevisitComments());
		revisitNeededVO.setModifiedDate(new Date());
		revisitNeededVO.setModifiedBy(createdBy);
		return revisitNeededVO;
	}

	private void historyLogging(Integer roleId, SORevisitNeededVO revisitNeededVO, String userName,
			Integer entityId,Integer actionId,String createdBy) {
		try{
			Date d=new Date(System.currentTimeMillis());
			Timestamp date = new Timestamp(d.getTime());
			ProviderHistoryVO hisVO=new ProviderHistoryVO();
			hisVO.setSoId(revisitNeededVO.getSoId());
			if(MPConstants.SUBSTATUS_ACTION_ID.equals(actionId)){
				hisVO.setActionId(MPConstants.SUBSTATUS_ACTION_ID);
				if(MPConstants.CUSTOMER_NOT_HOME.equals(revisitNeededVO.getRevisitReason())){
					hisVO.setDescription(MPConstants.SUBSTATUS_DESCRIPTION+" "+MPConstants.CNH_REVISIT_NEEDED);
				}else{
					hisVO.setDescription(MPConstants.SUBSTATUS_DESCRIPTION+" "+MPConstants.REVISIT_NEEDED);
				}
			}else if(MPConstants.REVISIT_NEEDED_ACTION_ID.equals(actionId)){
				hisVO.setActionId(MPConstants.REVISIT_NEEDED_ACTION_ID);
				hisVO.setDescription(generateRevisitLoggingComments(revisitNeededVO));
			}
			hisVO.setCreatedDate(date);
			hisVO.setModifiedDate(date);
			hisVO.setCreatedBy(createdBy);
			hisVO.setRoleId(roleId);
			hisVO.setModifiedBy(userName);
			hisVO.setEnitityId(entityId);
			mobileSOActionsBO.historyLogging(hisVO);
		}
		catch (Exception ex) {
			logger.error("Exception Occured in SORevisitNeededService-->historyLogging()");
		}
	}
	
	private String generateRevisitLoggingComments(SORevisitNeededVO revisitNeededVO){
		HashMap<String, Object> apptStartDate = null;
		HashMap<String, Object> apptEndDate = null;
		StringBuffer revisitDesc = new StringBuffer();
		if (null != revisitNeededVO.getCurrentServiceDateStart() && null != revisitNeededVO.getCurrentServiceTimeStart()) {
			apptStartDate = TimeUtils.convertGMTToGivenTimeZone(revisitNeededVO.getCurrentServiceDateStart(), revisitNeededVO.getCurrentServiceTimeStart(), revisitNeededVO.getTimeZone());
    		if (null != apptStartDate && !apptStartDate.isEmpty()) {
    			revisitDesc.append("Service order appointment date changed from ");
    			revisitDesc.append(formatDate(MPConstants.LOGGING_DATE_FORMAT, (Date) apptStartDate.get(MPConstants.DATE_PARAMETER)));
    			revisitDesc.append(" ");
    			revisitDesc.append((String) apptStartDate.get(MPConstants.TIME_PARAMETER));
    		}
		}
    	if(null != revisitNeededVO.getCurrentServiceDateEnd() && null != revisitNeededVO.getCurrentServiceTimeEnd()){
    		apptEndDate = TimeUtils.convertGMTToGivenTimeZone(revisitNeededVO.getCurrentServiceDateEnd(), revisitNeededVO.getCurrentServiceTimeEnd(), revisitNeededVO.getTimeZone());
    		if (null != apptEndDate && !apptEndDate.isEmpty()) {
    			revisitDesc.append(" - ");
    			revisitDesc.append(formatDate(MPConstants.LOGGING_DATE_FORMAT, (Date) apptEndDate.get(MPConstants.DATE_PARAMETER)));
    			revisitDesc.append(" ");
    			revisitDesc.append((String) apptEndDate.get(MPConstants.TIME_PARAMETER));
    			}
    		}
    	revisitDesc.append(" "+getTimeZone(formatDate(MPConstants.LOGGING_DATE_FORMAT, (Date) apptStartDate.get(MPConstants.DATE_PARAMETER))+" "+(String) apptStartDate.get(MPConstants.TIME_PARAMETER), MPConstants.LOGGING_DATE_TIME_STAMP_FORMAT1, revisitNeededVO.getTimeZone()));
    	if(null != revisitNeededVO.getNextApptDate() && null != revisitNeededVO.getNextApptStartTime()){
    		revisitDesc.append(" to "+formatDate(MPConstants.LOGGING_DATE_FORMAT, revisitNeededVO.getNextApptDate())+" "+revisitNeededVO.getNextApptStartTime());
    	}
    	if(null != revisitNeededVO.getNextApptDate() && null != revisitNeededVO.getNextApptEndTime()){
    		revisitDesc.append(" - "+formatDate(MPConstants.LOGGING_DATE_FORMAT, revisitNeededVO.getNextApptDate())+" "+revisitNeededVO.getNextApptEndTime());
    	}
    	revisitDesc.append(" "+getTimeZone(formatDate(MPConstants.LOGGING_DATE_FORMAT, revisitNeededVO.getNextApptDate())+" "+revisitNeededVO.getNextApptStartTime(), MPConstants.LOGGING_DATE_TIME_STAMP_FORMAT1, revisitNeededVO.getTimeZone()));
    	logger.info(revisitDesc.toString());
    	return revisitDesc.toString();
	}
	
	private void updateNPSForRI(SORevisitNeededVO revisitNeededVO, String serviceTimeZone){
    	RequestOrder order = new RequestOrder();
    	RequestRescheduleInfo requestRescheduleInfo=new RequestRescheduleInfo();
    	String xmlFormattedDate ="";
    	try {
    		//SL-21174 : Correcting Date format to yyyy-MM-dd as per xml
    		xmlFormattedDate = convertToXmlDateFormat(revisitNeededVO.getNextApptDateString());
    		order.setServiceScheduleDate(xmlFormattedDate);
			order.setServiceScheduleFromTime(revisitNeededVO.getNextApptStartTime());
			order.setServiceScheduletoTime(revisitNeededVO.getNextApptEndTime());
			order.setServiceOrderRescheduledFlag(BuyerOutBoundConstants.RESHEDULE_FLAG_YES);
				
			//get the current date in server timeZone 
			Calendar calender=Calendar.getInstance();
			SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
			TimeZone timeZone=TimeZone.getTimeZone(serviceTimeZone);
			SimpleDateFormat timeFormatter=new SimpleDateFormat("hh:mm a");
			String modifiedfromDate=timeFormatter.format(calender.getTime());
			Calendar modificationDateTime = TimeChangeUtil.getCalTimeFromParts(calender.getTime(), modifiedfromDate,calender.getTimeZone());
			Date modificationDate = TimeChangeUtil.getDate(modificationDateTime, timeZone);
			String modificationDateValue=formatter.format(modificationDate);
			String modificationTime = TimeChangeUtil.getTimeString(modificationDateTime, timeZone);
            //set the current date ,modified Id,reasonCode in reschedule Object
        	requestRescheduleInfo.setReschedCancelModificationDate(modificationDateValue);
        	requestRescheduleInfo.setReschedCancelModificationTime(modificationTime);
        	requestRescheduleInfo.setRescheduleModificationID(revisitNeededVO.getResourceId().toString());
        	requestRescheduleInfo.setRescheduleReasonCode(revisitNeededVO.getRevisitReason());
        	requestRescheduleInfo.setRescheduleRsnCdDescription(StringUtils.isEmpty(revisitNeededVO.getTripComments())?revisitNeededVO.getRevisitReason():revisitNeededVO.getTripComments());
        	
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
        	
        	BuyerOutboundFailOverVO failoverVO = buyerOutBoundNotificationService.callService(requestMsgBody, revisitNeededVO.getSoId());
        	if(null!=failoverVO){
       		 	buyerOutBoundNotificationJMSService.callJMSService(failoverVO);
        	}
	    } catch (BusinessServiceException e) {
			logger.error("error in fetching schedule Details"+e);
		}	
	}
	
	public String getTimeZone(String modifiedDate, String format, String timeZone){
		Date newDate = null;
		String actualTimeZone = timeZone.substring(0, 3);
		String dayLightTimeZone = timeZone.substring(4, 7);
		try {
			newDate = new SimpleDateFormat(format, Locale.ENGLISH).parse(modifiedDate);
		} catch (ParseException e) {
			logger.info("Parse Exception SODetailsDelegateImpl.java "+ e);
		}
        TimeZone gmtTime = TimeZone.getTimeZone(actualTimeZone);
        if(gmtTime.inDaylightTime(newDate)){
        	return "("+dayLightTimeZone+")";
        }
        return "("+actualTimeZone+")";
	}
	
	private String formatDate(String format, Date date){
		DateFormat formatter = new SimpleDateFormat(format);
		String formattedDate = null;
		try {
			formattedDate = formatter.format(date);
		} catch (Exception e) {
			logger.error("exception in formatDate()"+ e);
		}
		return formattedDate;
	}
	/**SL-21174 : Correcting Date format to yyyy-MM-dd as per xml
	 * Owner : Infosys
	 * @description : Check weather the Date is in yyyy-MM-dd/yyyy-M-d format
	 * @param nextApptDateString
	 * @return
	 */
	private String convertToXmlDateFormat(String nextApptDateString) {
		Date nextApptDate=null;
		DateFormat xmlDateFormatter = new SimpleDateFormat(MPConstants.DATE_FORMAT_DISPLAYED_FOR_ACCEPT);
		try{
			nextApptDate = xmlDateFormatter.parse(nextApptDateString);
			nextApptDateString = xmlDateFormatter.format(nextApptDate);
		}catch (ParseException e) {
			logger.error("Format of Date in the Request is not in yyyy-MM-dd format"+ nextApptDateString+" need to convert to the yyyy-MM-dd date");
		}
		return nextApptDateString;
	}
	
	
	
	
	public IMobileSOActionsBO getMobileSOActionsBO() {
		return mobileSOActionsBO;
	}

	public void setMobileSOActionsBO(IMobileSOActionsBO mobileSOActionsBO) {
		this.mobileSOActionsBO = mobileSOActionsBO;
	}

	public SORevisitNeededValidator getRevisitNeededValidator() {
		return revisitNeededValidator;
	}

	public void setRevisitNeededValidator(
			SORevisitNeededValidator revisitNeededValidator) {
		this.revisitNeededValidator = revisitNeededValidator;
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

	public INotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(INotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public IRelayServiceNotification getRelayNotificationService() {
		return relayNotificationService;
	}

	public void setRelayNotificationService(
			IRelayServiceNotification relayNotificationService) {
		this.relayNotificationService = relayNotificationService;
	}
	
}