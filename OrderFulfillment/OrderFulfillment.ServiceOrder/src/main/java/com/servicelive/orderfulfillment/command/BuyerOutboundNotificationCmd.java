package com.servicelive.orderfulfillment.command;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.interfaces.OrderConstants;
import com.servicelive.orderfulfillment.command.util.SOCommandArgHelper;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.dao.ICARAssociationDao;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.lookup.ApplicationFlagLookup;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.vo.InHomeNPSConstants;
import com.servicelive.orderfulfillment.vo.JobCodeData;
import com.servicelive.orderfulfillment.vo.OrderUpdateRequest;
import com.servicelive.orderfulfillment.vo.OutboundNotificationVO;
import com.servicelive.orderfulfillment.vo.RequestInHomeMessageDetails;
import com.servicelive.orderfulfillment.vo.TimeUtils;
import com.thoughtworks.xstream.XStream;

public class BuyerOutboundNotificationCmd extends SOCommand {
	QuickLookupCollection quickLookupCollection;
	ICARAssociationDao carAssociationDao;

	@Override
	public void execute(Map<String, Object> processVariables) {
		ServiceOrder serviceOrder = getServiceOrder(processVariables);
		// check if the out-bound application flag is enabled,
		// nps_inactive_indicator for the SO is false and buyer is Inhome(3000)
		String notificationType = SOCommandArgHelper.extractStringArg(processVariables, 1);
		String isOtherMajorChanges = (String) processVariables.get(ProcessVariableUtil.IS_OTHER_MAJOR_CHANGES);
		if(null == isOtherMajorChanges){
			isOtherMajorChanges = "false";
		}
		boolean outBoundFlag = isOutboundNotificationNeeded(serviceOrder);
		logger.info("Inside BuyerOutboundNotificationCmd");
		if (outBoundFlag) {
			// Fetch the messages from lu_inhome_outbound_notification_messages table
			String  message = "";
			try {
				// As per the requirement, status change to draft is not required for reposting from expired or posted status.
				// Hence checking the process variable isOtherMajorChanges for every status change to draft.
				if(!(InHomeNPSConstants.STATUS_DRAFT.equals(serviceOrder.getWfStateId()) && ("true").equals(isOtherMajorChanges))){
					if(null != notificationType){
						// Including entry for reschedule(FROM date & time to TO date &time), when rescheduling by provider in buyer_outbound_notification table.
						if(notificationType.equals(OrderfulfillmentConstants.INHOME_NOTIFICATION_TYPE_RESCHEDULE)){
							message = processVariables.get(OrderfulfillmentConstants.INHOME_OUTBOUND_NOTIFICATION_RESCHEDULE_MESSAGE).toString();
						}
						// Including entry for auto accept reschedule(FROM date & time to TO date &time), when rescheduling by provider in buyer_outbound_notification table.
						else if(notificationType.equals(OrderfulfillmentConstants.INHOME_NOTIFICATION_TYPE_AUTO_ACCEPT)){
							message = processVariables.get(OrderfulfillmentConstants.INHOME_OUTBOUND_NOTIFICATION_AUTOACCEPT_MESSAGE).toString();
						}
						// Including entry for sub status change when rescheduling by provider in buyer_outbound_notification table.
						else if(notificationType.equals(OrderfulfillmentConstants.INHOME_NOTIFICATION_TYPE_SUB_STATUS_CHANGE)){
							if(null != serviceOrder.getWfSubStatusId()){
								message = serviceOrderDao.getNotificationMessage(serviceOrder.getWfStateId(), serviceOrder.getWfSubStatusId());
							}
						}
						// CAR - When auto accepting a SO, the jpdl transition is from DRAFT to ACCEPTED. So including entry for 
						// status change in buyer_outbound_notification table for POSTED.
						else if(notificationType.equals(OrderfulfillmentConstants.INHOME_NOTIFICATION_TYPE_STATUS_POSTED)){
							String toReplace=InHomeNPSConstants.SUB_STATUS_MESSAGE;
							LegacySOStatus wfStatus = LegacySOStatus.valueOf(OrderfulfillmentConstants.INHOME_NOTIFICATION_TYPE_STATUS_POSTED);
							message = quickLookupCollection.getInHomeOutboundNotificationMessageLookup().getMessage(wfStatus.getId());
							message = message.replaceFirst(toReplace,"");
						}
						// Entry for status change.
						else{
							message = addSubStatus(quickLookupCollection.getInHomeOutboundNotificationMessageLookup().getMessage(serviceOrder.getWfStateId()),serviceOrder);	
						}
					}
					else{
						message = addSubStatus(quickLookupCollection.getInHomeOutboundNotificationMessageLookup().getMessage(serviceOrder.getWfStateId()),serviceOrder);
					}
				}
				else{
					processVariables.put(ProcessVariableUtil.IS_OTHER_MAJOR_CHANGES, "false");
				}
			} catch (Exception e) {
				logger.error("Exception in fetching message"+ e);
			}
			if(StringUtils.isNotBlank(message)){
				RequestInHomeMessageDetails requestDetails = new RequestInHomeMessageDetails();
				OutboundNotificationVO notificationVO = new OutboundNotificationVO();
				requestDetails = mapInHomeDetails(message, serviceOrder);
				String result = validateDetails(requestDetails);
				notificationVO = createRequestXML(serviceOrder,requestDetails, notificationVO, result);
				try {
					serviceOrderDao.insertInHomeOutBoundNotification(notificationVO);
				} catch (Exception e) {
					logger.error("Exception in inserting values into notification table"+ e.getMessage());
				}
			}
			
			//R12_0 : Inserting request xml of Service Operations API into
			//buyer_outbound_notification table for accept reschedule and auto accept reschedule
			//SLT-4048:Flag to decide whether to send  Reschedule event to NPS through Platform
			if(null!=notificationType && ( notificationType.equals(OrderfulfillmentConstants.INHOME_NOTIFICATION_TYPE_RESCHEDULE) || notificationType.equals(OrderfulfillmentConstants.INHOME_NOTIFICATION_TYPE_AUTO_ACCEPT))&& InHomeNPSConstants.ON.equalsIgnoreCase(InHomeNPSConstants.INHOME_OUTBOUND_STOP_RESCHEDULE_EVENT_FLAG_INPLATFORM)){
				try {
						String callCode="";
						callCode=processVariables.get(OrderfulfillmentConstants.INHOME_OUTBOUND_NOTIFICATION_RESCHEDULE_CALL_CODE).toString();
						OrderUpdateRequest requestDetails = new OrderUpdateRequest();
						OutboundNotificationVO notificationVO = new OutboundNotificationVO();
						requestDetails = mapInHomeDetails(requestDetails, serviceOrder,callCode);
						String result =validateInHomeDetails(requestDetails);
						notificationVO = populateNotification(result, requestDetails);
						
						serviceOrderDao.insertInHomeOutBoundNotification(notificationVO);
				} catch (Exception e) {
						logger.error("Exception in inserting values into notification table"+ e.getMessage());
				}
			}
			
		}
	}

	private boolean isOutboundNotificationNeeded(
			ServiceOrder serviceOrder) {
		logger.info("Entering BuyerOutboundNotificationCmd.isOutboundNotificationNeeded()...");
		ApplicationFlagLookup applicationFlagLookup = quickLookupCollection.getApplicationFlagLookup();
		if (!applicationFlagLookup.isInitialized()) {
			throw new ServiceOrderException(
					"Unable to lookup ApplicationFlags. ApplicationFlagLookup not initialized.");
		}
		String outBoundFlag = applicationFlagLookup
				.getPropertyValue("inhome_outbound_flag");
		Long buyerId = serviceOrder.getBuyerId();
		if (3000 == buyerId && ("ON").equals(outBoundFlag)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean getNpsInactiveIndicator(ServiceOrder serviceOrder) {
		logger.info("Entering BuyerOutboundNotificationCmd.getNpsInactiveIndicator()...");
		boolean npsInactiveInd = true;
		if (null != serviceOrder
				&& null != serviceOrder.getSOWorkflowControls()) {
			npsInactiveInd = serviceOrder.getSOWorkflowControls()
					.getNpsInactiveInd();
		}
		return npsInactiveInd;
	}

	public RequestInHomeMessageDetails mapInHomeDetails(String message,ServiceOrder serviceOrder) {
		RequestInHomeMessageDetails requestDetails=new RequestInHomeMessageDetails();
		requestDetails.setFromFunction(InHomeNPSConstants.INHOME_FROM_FUNCTION);
		requestDetails.setToFunction(InHomeNPSConstants.INHOME_TO_FUNCTION);
		requestDetails.setOrderType(InHomeNPSConstants.INHOME_ORDER_TYPE);
		String unitNo = serviceOrder.getCustomRefValue(InHomeNPSConstants.INHOME_UNIT_NUMBER);
		logger.info("UnitNumber CustomReference value:"+unitNo);
		String orderNum =serviceOrder.getCustomRefValue(InHomeNPSConstants.INHOME_ORDER_NUMBER);
		logger.info("OrderNumber CustomReference value:"+orderNum);
		String empId = serviceOrder.getCustomRefValue(InHomeNPSConstants.INHOME_EMP_ID);
		logger.info("EmpId CustomReference value:"+orderNum);
		//if the custom references are not available in SO object, get it from db
		if(StringUtils.isBlank(unitNo) && StringUtils.isBlank(orderNum) && StringUtils.isBlank(empId)){
			HashMap<String,String> refValues = serviceOrderDao.getCustomRefValues(serviceOrder.getSoId());
			if(null != refValues){
				unitNo = refValues.get(InHomeNPSConstants.INHOME_UNIT_NUMBER);
				logger.info("UnitNumber :"+unitNo);
				orderNum = refValues.get(InHomeNPSConstants.INHOME_ORDER_NUMBER);
				logger.info("OrderNumber :"+orderNum);
				empId = refValues.get(InHomeNPSConstants.INHOME_EMP_ID);
				logger.info("EmpId :"+orderNum);
			}
		}
		if(StringUtils.isNotBlank(unitNo)){
			requestDetails.setUnitNum(unitNo);
		}else{
			requestDetails.setUnitNum(null);
		}
		if(StringUtils.isNotBlank(orderNum)){
			requestDetails.setOrderNum(orderNum);
		}else{
			requestDetails.setOrderNum(null);
		}
		if(StringUtils.isNotBlank(empId)){
			requestDetails.setEmpId(empId);
		}else{
			requestDetails.setEmpId(null);
		}
		requestDetails.setMessage(message);
		return requestDetails;
	}
	
	/** Description:Validate mandatory request parameters for web service
	 * @param requestDetails
	 * @return
	 */
	public String validateDetails(RequestInHomeMessageDetails requestDetails) {
		StringBuilder error = new StringBuilder(InHomeNPSConstants.VALIDATION_MESSAGE);
		StringBuilder invalidData = new StringBuilder(InHomeNPSConstants.INVALID_DATA);
	    if(null!= requestDetails && (StringUtils.isBlank(requestDetails.getOrderType())||(!InHomeNPSConstants.orderTypeList().contains(requestDetails.getOrderType())))){
        	error.append(InHomeNPSConstants.ORDER_TYPE_ERROR);
        }
        if(null!= requestDetails && StringUtils.isBlank(requestDetails.getUnitNum())){
        	error.append(InHomeNPSConstants.UNIT_NUMBER_ERROR);
        }else if(StringUtils.isNumeric(requestDetails.getUnitNum())){
        	if(requestDetails.getUnitNum().length()> InHomeNPSConstants.UNIT_NUMBER_LENGTH){
        		invalidData.append(InHomeNPSConstants.UNIT_NUMBER_INVALID_LENGTH);
        	}
        }else{
        	invalidData.append(InHomeNPSConstants.UNIT_NUMBER_INVALID);
        }
        if(null!= requestDetails && StringUtils.isBlank(requestDetails.getOrderNum())){
        	error.append(InHomeNPSConstants.ORDER_NUMBER_ERROR);
        }else if(StringUtils.isNumeric(requestDetails.getOrderNum())){
        	if(requestDetails.getOrderNum().length()> InHomeNPSConstants.ORDER_NUMBER_LENGTH){
        		invalidData.append(InHomeNPSConstants.ORDER_NUMBER_INVALID_LENGTH);
        	}
        }else{
        	invalidData.append(InHomeNPSConstants.ORDER_NUMBER_INVALID);
        }
        if(null!= requestDetails &&((StringUtils.isBlank(requestDetails.getFromFunction()))||(!InHomeNPSConstants.fromAndToFunction().contains(requestDetails.getFromFunction())))){
        	error.append(InHomeNPSConstants.FROM_FUNCTION_ERROR);
        }
        if(null!= requestDetails &&StringUtils.isNotBlank(requestDetails.getToFunction())){
        	error.append(InHomeNPSConstants.TO_FUNCTION_ERROR);
        }
        if(null!= requestDetails && StringUtils.isBlank(requestDetails.getMessage())){
        	error.append(InHomeNPSConstants.MESSAGE_ERROR);
        }else{
        	if(requestDetails.getMessage().trim().length() > 2000){
        		invalidData.append(InHomeNPSConstants.INVALID_MESSAGE_LENGTH);
        	}
        }
        if(null!= requestDetails && (StringUtils.isBlank(requestDetails.getEmpId())|| (!StringUtils.isAlphanumeric(requestDetails.getEmpId())))){
        	error.append(InHomeNPSConstants.EMP_ID_ERROR);
        }else if(InHomeNPSConstants.EMP_ID_LENGTH < requestDetails.getEmpId().length()){
        	invalidData.append(InHomeNPSConstants.INVALID_EMP_ID_LENGTH);
        }
		String errorInfo = error.toString();
		String validDataResult = invalidData.toString();
		if(InHomeNPSConstants.VALIDATION_MESSAGE.equalsIgnoreCase(errorInfo)
				&& InHomeNPSConstants.INVALID_DATA.equalsIgnoreCase(validDataResult)){
			return InHomeNPSConstants.VALIDATION_SUCCESS;
		}
		else{
			String result = "";
			if(!InHomeNPSConstants.VALIDATION_MESSAGE.equalsIgnoreCase(errorInfo)){
				errorInfo = errorInfo.substring(0, error.toString().length()-2);
			}else{
				errorInfo = InHomeNPSConstants.NO_DATA;
			}
			if(!InHomeNPSConstants.INVALID_DATA.equalsIgnoreCase(validDataResult)){
				validDataResult = validDataResult.substring(0, invalidData.toString().length()-2);
			}else{
				validDataResult = InHomeNPSConstants.NO_DATA;
			}
			if(StringUtils.isNotBlank(errorInfo) && StringUtils.isNotBlank(validDataResult)){
				result = errorInfo + InHomeNPSConstants.SEPERATOR + validDataResult;
			}
			else if(StringUtils.isNotBlank(errorInfo)){
				result = errorInfo;
			}
			else if(StringUtils.isNotBlank(validDataResult)){
				result = validDataResult;
			}
			
			return result;
		}
	}
	/** Description:creating outbound vo for insertion
	 * @param soId
	 * @param requestDetails
	 * @param notificationVO
	 * @param result
	 * @return
	 */
	private OutboundNotificationVO createRequestXML(ServiceOrder serviceOrder, RequestInHomeMessageDetails requestDetails,OutboundNotificationVO notificationVO, String result){
		XStream xstream = new XStream();
		String seqNo = getCorrelationId();
		requestDetails.setCorrelationId(seqNo);
		xstream.processAnnotations(RequestInHomeMessageDetails.class);
		String createRequestXml = (String) xstream.toXML(requestDetails);
		notificationVO.setSeqNo(seqNo);
		notificationVO.setSoId(serviceOrder.getSoId());
		notificationVO.setXml(createRequestXml);
		notificationVO.setCreatedDate(new Date());
		notificationVO.setModifiedDate(new Date());
		notificationVO.setNoOfRetries(InHomeNPSConstants.NO_OF_RETRIES);
		notificationVO.setServiceId(InHomeNPSConstants.SUBSTATUS_SERVICE_ID_INT);
		notificationVO.setActiveInd(InHomeNPSConstants.DEFAULT_ACTIVE_IND);
		if(result.equals(InHomeNPSConstants.VALIDATION_SUCCESS)){
			notificationVO.	setStatus(InHomeNPSConstants.STATUS_WAITING);
			notificationVO.setException(null);
		}
		else{
			notificationVO.setStatus(InHomeNPSConstants.STATUS_ERROR);
			notificationVO.setException(result);
		}
		if(getNpsInactiveIndicator(serviceOrder)){
			notificationVO.setStatus(InHomeNPSConstants.STATUS_EXCLUDED);
			notificationVO.setException(InHomeNPSConstants.INACTIVE);
		}
		return notificationVO;
    }
	private String getCorrelationId(){
		String seqNo = "";
		try {
			seqNo = serviceOrderDao.getCorrelationId();
			int length = 8 - seqNo.length();
			for (int i = 0; i < length; i++) {
				seqNo ="0" + seqNo;
			}

		} catch (Exception e) {
			logger.error("Exception in NotificationServiceCoordinator.getSequenceNo() "+ e.getMessage(), e);
		}
		return seqNo;
	}
	
	private String addSubStatus(String message, ServiceOrder serviceOrder) {
		String newMessage = message;
		String toReplace=InHomeNPSConstants.SUB_STATUS_MESSAGE;
		String replaceSubStatus="";
		logger.info("ServiceOrder Id:"+ serviceOrder.getSoId());
		logger.info("Service order sub status:"+serviceOrder.getWfSubStatus());
		logger.info("Service order wf substatus:"+serviceOrder.getWfSubStatusId());
		if(StringUtils.isNotBlank(serviceOrder.getWfSubStatus())){
			logger.info("SubStaus Of order: "+ replaceSubStatus);
			replaceSubStatus = serviceOrder.getWfSubStatus()== null? "":serviceOrder.getWfSubStatus()+" ";
			newMessage = newMessage.replaceFirst(toReplace, replaceSubStatus);
			logger.info("Message to be returned:"+newMessage);
		}else{
			newMessage = newMessage.replaceFirst(toReplace,"");
		}
		logger.info("Message returning from addSubStatus():"+newMessage );
		return newMessage;
	}
	
	/**R12_0
	 * Description:map the request fields for accept/auto accept reschedule
	 * @param inHomeDetails
	 * @param serviceOrder
	 * @return
	 */
	public OrderUpdateRequest mapInHomeDetails(OrderUpdateRequest inHomeDetails, ServiceOrder serviceOrder,String callCode) {
		
		logger.info("Entering mapInHomeDetails");
		HashMap<String, Object> rescheduleStartDate = null;
		HashMap<String, Object> rescheduleStartDateEndTime = null;
		try{
			inHomeDetails.setSoId(serviceOrder.getSoId());
			//mandatory fields
			//TODO Clarification on mandatory fields
			inHomeDetails.setCorrelationId(getCorrelationId());
			inHomeDetails.setOrderType(InHomeNPSConstants.IN_HOME_ORDER_TYPE);
			if(null != serviceOrder.getCustomRefValue(InHomeNPSConstants.UNIT_NUMBER)){
				inHomeDetails.setUnitNum(serviceOrder.getCustomRefValue(InHomeNPSConstants.UNIT_NUMBER));
			}else{
				inHomeDetails.setUnitNum(InHomeNPSConstants.NO_DATA);
			}
			if(null != serviceOrder.getCustomRefValue(InHomeNPSConstants.ORDER_NUMBER)){
				inHomeDetails.setOrderNum(serviceOrder.getCustomRefValue(InHomeNPSConstants.ORDER_NUMBER));
			}else{
				inHomeDetails.setOrderNum(InHomeNPSConstants.NO_DATA);
			}
			inHomeDetails.setRouteDate(formatDate(new Date()));
			if(null != serviceOrder.getCustomRefValue(InHomeNPSConstants.TECH_ID)){
				inHomeDetails.setTechId(serviceOrder.getCustomRefValue(InHomeNPSConstants.TECH_ID));
			}else{
				inHomeDetails.setTechId(InHomeNPSConstants.NO_DATA);
			}
		    //Setting model No and Brand No in request
			String modelNo = InHomeNPSConstants.NOT_APPLICABLE;
			String brand = InHomeNPSConstants.NOT_APPLICABLE;
			if(StringUtils.isNotBlank(serviceOrder.getCustomRefValue(InHomeNPSConstants.BRAND))){
				inHomeDetails.setApplBrand(serviceOrder.getCustomRefValue(InHomeNPSConstants.BRAND));
			}else{
				inHomeDetails.setApplBrand(brand);
			}
			if(StringUtils.isNotBlank(serviceOrder.getCustomRefValue(InHomeNPSConstants.MODEL))){
				inHomeDetails.setModelNum(serviceOrder.getCustomRefValue(InHomeNPSConstants.MODEL));
			}else{
				inHomeDetails.setModelNum(modelNo);
			}
			//Setting call code after fetching the value from OF parameter
			if(null!=callCode){
				inHomeDetails.setCallCd(callCode);
			}
			else{
				inHomeDetails.setCallCd(InHomeNPSConstants.NO_DATA);
			}
			
			inHomeDetails.setServiceFromTime(InHomeNPSConstants.DEFAULT_START_TIME);			
			inHomeDetails.setServiceToTime(InHomeNPSConstants.DEFAULT_END_TIME);
			
			
			//job code details
			inHomeDetails.setJobCodeData(mapJobCodeData(serviceOrder));			
			
			logger.info("Entering Reschedule Logic:");
			//reschedule start date and start time
			if(null!=serviceOrder.getSchedule().getServiceDateGMT1() && null!=serviceOrder.getSchedule().getServiceTimeStartGMT())
			{
				rescheduleStartDate=getRescheduleDateTime(serviceOrder.getSchedule().getServiceDateGMT1(),serviceOrder.getSchedule().getServiceTimeStartGMT(),serviceOrder.getServiceLocationTimeZone());
				logger.info("rescheduleStartDate:"+rescheduleStartDate);
			}
			
			
			if(null!=rescheduleStartDate && null != rescheduleStartDate.get(OrderConstants.GMT_DATE)){
				inHomeDetails.setReschdDate(formatDate((Date)rescheduleStartDate.get(OrderConstants.GMT_DATE)));
			}else{
				inHomeDetails.setReschdDate(InHomeNPSConstants.NO_DATA);
			}
			
			if(null!=rescheduleStartDate && null != rescheduleStartDate.get(OrderConstants.GMT_TIME)){
				inHomeDetails.setReschdFromTime(formatTime((String)rescheduleStartDate.get(OrderConstants.GMT_TIME)));
			}else{
				inHomeDetails.setReschdFromTime(InHomeNPSConstants.NO_DATA);
			}
			//Setting model No and Brand No in request.
			
			//reschedule end time
			if(null!=serviceOrder.getSchedule().getServiceDateGMT1() && null!=serviceOrder.getSchedule().getServiceTimeEndGMT())
			{
				rescheduleStartDateEndTime=getRescheduleDateTime(serviceOrder.getSchedule().getServiceDateGMT1(),serviceOrder.getSchedule().getServiceTimeEndGMT(),serviceOrder.getServiceLocationTimeZone());
				logger.info("rescheduleStartDateEndTime:"+rescheduleStartDateEndTime);
			}
			
			
			if(null!=rescheduleStartDateEndTime && null != rescheduleStartDateEndTime.get(OrderConstants.GMT_TIME)){
				inHomeDetails.setReschdToTime(formatTime((String)rescheduleStartDateEndTime.get(OrderConstants.GMT_TIME)));
			}
			// R12.0: SL- 20405, if end time is not present (in case of reschedule to a specific date scenario), set the start time to end time.
			else if(null!=rescheduleStartDate && null != rescheduleStartDate.get(OrderConstants.GMT_TIME)){
				inHomeDetails.setReschdToTime(formatTime((String)rescheduleStartDate.get(OrderConstants.GMT_TIME)));
			}else{
				inHomeDetails.setReschdToTime(InHomeNPSConstants.NO_DATA);
			}
			

		}catch (Exception e){
			logger.error("Exception in BuyerOutboundNotificationCmd.mapInHomeDetails() " + e);
		}
		logger.info("Leaving mapInHomeDetails");
		return inHomeDetails;
	}
	
	/**R12_0
	 * construct jobCodeData object
	 * @param serviceOrder
	 * @return
	 */
	private JobCodeData mapJobCodeData(ServiceOrder serviceOrder) {
		
		logger.info("Mapping Job fields");
		try{
			//Assuming that there will be only one task for InHome order
			JobCodeData job = new JobCodeData();
			setJobeCode(serviceOrder.getPrimarySkillCatId(),job);
			job.setJobCalcPrice(InHomeNPSConstants.JOB_CALC_PRICE);
			if(null != serviceOrder.getCustomRefValue(InHomeNPSConstants.COVERAGE_TYPE_LABOR)){
				job.setJobCoverageCd(serviceOrder.getCustomRefValue(InHomeNPSConstants.COVERAGE_TYPE_LABOR));
			}else{
				job.setJobCoverageCd(InHomeNPSConstants.NO_DATA);
			}

			return job;
		}
		catch(Exception e){
			logger.error("Exception in BuyerOutboundNotificationCmd.mapJobCodeData() due to "+e);
		}
		return null;
	}

	
	

	/**R12_0
	 * validate mandatory fields for accept/auto accept reschedule
	 * @param inHomeDetails
	 * @return
	 */
	public String validateInHomeDetails(OrderUpdateRequest inHomeDetails) {
		
		logger.info("Entering validateInHomeDetails");
		StringBuilder error = new StringBuilder(InHomeNPSConstants.VALIDATION_MESSAGE);
		StringBuilder invalid = new StringBuilder(InHomeNPSConstants.INVALID_MESSAGE);
		if(StringUtils.isBlank(inHomeDetails.getCorrelationId())){
			error.append(InHomeNPSConstants.CORRELATION_ERROR);
		}
		else if(inHomeDetails.getCorrelationId().length() > InHomeNPSConstants.EIGHT){
			invalid.append(InHomeNPSConstants.CORRELATION_INVALID);
		}
		if(StringUtils.isBlank(inHomeDetails.getOrderType())){
			error.append(InHomeNPSConstants.ORDERTYPE_ERROR);
		}
		else if(!InHomeNPSConstants.orderTypeList().contains(inHomeDetails.getOrderType())){
			invalid.append(InHomeNPSConstants.ORDERTYPE_INVALID);
		}
		if(StringUtils.isBlank(inHomeDetails.getUnitNum())){
			error.append(InHomeNPSConstants.UNITNUM_ERROR);
		}
		else if(StringUtils.isNumeric(inHomeDetails.getUnitNum())){
			if(inHomeDetails.getUnitNum().length() > InHomeNPSConstants.SEVEN){
				invalid.append(InHomeNPSConstants.UNITNUM_INVALID);
			}
		}
		else{
			invalid.append(InHomeNPSConstants.UNITNUM_NUMERIC);
		}
		if(StringUtils.isBlank(inHomeDetails.getOrderNum())){
			error.append(InHomeNPSConstants.ORDERNUM_ERROR);
		}
		else if(StringUtils.isNumeric(inHomeDetails.getOrderNum())){
			if(inHomeDetails.getOrderNum().length() > InHomeNPSConstants.EIGHT){
				invalid.append(InHomeNPSConstants.ORDER_NUMBER_INVALID);
			}
		}
		else{
			invalid.append(InHomeNPSConstants.ORDERNUM_NUMERIC);
		}
		if(StringUtils.isBlank(inHomeDetails.getRouteDate())){
			error.append(InHomeNPSConstants.ROUTEDATE_ERROR);
		}
		else if(StringUtils.isNumeric(inHomeDetails.getRouteDate())){
			if(inHomeDetails.getRouteDate().length() > InHomeNPSConstants.EIGHT){
				invalid.append(InHomeNPSConstants.ROUTEDATE_INVALID);
			}
		}
		else {
			invalid.append(InHomeNPSConstants.ROUTEDATE_INVALID);
		}
		
		
		if(StringUtils.isBlank(inHomeDetails.getReschdDate())){
			error.append(InHomeNPSConstants.RESCHDATE_ERROR);
		}
		else if(StringUtils.isNumeric(inHomeDetails.getReschdDate())){
			if(inHomeDetails.getRouteDate().length() > InHomeNPSConstants.EIGHT){
				invalid.append(InHomeNPSConstants.RESCHDATE_INVALID);
			}
		}
		else {
			invalid.append(InHomeNPSConstants.RESCHDATE_INVALID);
		}
		
		//validation for reschedule start and end time
		if(StringUtils.isBlank(inHomeDetails.getReschdFromTime())){
			error.append(InHomeNPSConstants.FROM_RESCHD_TIME_ERROR);
		}
	
		if(StringUtils.isBlank(inHomeDetails.getReschdToTime())){
			error.append(InHomeNPSConstants.TO_RESCHD_TIME_ERROR);
		}
		
		
		if(StringUtils.isBlank(inHomeDetails.getTechId())){
			error.append(InHomeNPSConstants.TECHID_ERROR);
		}
		else if(inHomeDetails.getTechId().length() > InHomeNPSConstants.FIFTY){
			invalid.append(InHomeNPSConstants.TECHID_INVALID);
		}
		if(StringUtils.isBlank(inHomeDetails.getCallCd())){
			error.append(InHomeNPSConstants.CALLCODE_ERROR);
		}
		/*else if(!InHomeNPSConstants.callCodeList().contains(inHomeDetails.getCallCd())){
			invalid.append(InHomeNPSConstants.CALLCODE_INVALID);
		}*/
		if(StringUtils.isBlank(inHomeDetails.getServiceFromTime())){
			error.append(InHomeNPSConstants.FROM_TIME_ERROR);
		}
	
		if(StringUtils.isBlank(inHomeDetails.getServiceToTime())){
			error.append(InHomeNPSConstants.TO_TIME_ERROR);
		}
		
		//mandatory job code fields
		if(null != inHomeDetails.getJobCodeData()){
			JobCodeData job = inHomeDetails.getJobCodeData();
			if(StringUtils.isBlank(job.getJobCalcPrice())){
				error.append(InHomeNPSConstants.JOB_PRICE_ERROR);
			}
			else if(!Pattern.matches(InHomeNPSConstants.PRICE_REGEX, job.getJobCalcPrice())){
				invalid.append(InHomeNPSConstants.JOB_PRICE_INVALID);
			}
			if(StringUtils.isBlank(job.getJobChargeCd())){
				error.append(InHomeNPSConstants.JOB_CHARGECD_ERROR);
			}
			else if(job.getJobChargeCd().length() > InHomeNPSConstants.ONE){
				invalid.append(InHomeNPSConstants.JOB_CHARGECD_INVALID);
			}
			if(StringUtils.isBlank(job.getJobCode())){
				error.append(InHomeNPSConstants.JOB_CODE_ERROR);
			}
			/*else if(InHomeNPSConstants.FIVE != job.getJobCode().length()){
				invalid.append(InHomeNPSConstants.JOB_CODE_INVALID);
			}*/
			if(StringUtils.isBlank(job.getJobCoverageCd())){
				error.append(InHomeNPSConstants.JOB_COVERAGECD_ERROR);
			}
			else if(!InHomeNPSConstants.coverageCodeList().contains(job.getJobCoverageCd())){
				invalid.append(InHomeNPSConstants.JOB_COVERAGECD_INVALID);
			}
			if(StringUtils.isBlank(job.getJobCodePrimaryFl())){
				error.append(InHomeNPSConstants.JOB_PRIMFL_ERROR);
			}
			else if(!InHomeNPSConstants.validateSet().contains(job.getJobCodePrimaryFl())){
				invalid.append(InHomeNPSConstants.JOB_PRIMFL_INVALID);
			}
			if(StringUtils.isBlank(job.getJobRelatedFl())){
				error.append(InHomeNPSConstants.JOB_RELFL_ERROR);
			}
			else if(!InHomeNPSConstants.validateSet().contains(job.getJobRelatedFl())){
				invalid.append(InHomeNPSConstants.JOB_RELFL_INVALID);
			}
		}
		else{
			error.append(InHomeNPSConstants.JOB_DATA_ERROR);
		}
	
		String errorInfo = error.toString();
		String invalidInfo = invalid.toString();
		logger.info("Leaving validateInHomeDetails");
		if(InHomeNPSConstants.VALIDATION_MESSAGE.equalsIgnoreCase(errorInfo) &&
				InHomeNPSConstants.INVALID_MESSAGE.equalsIgnoreCase(invalidInfo)){
			return InHomeNPSConstants.VALIDATION_SUCCESS;
		}
		else{
			String result = "";
			if(!InHomeNPSConstants.VALIDATION_MESSAGE.equalsIgnoreCase(errorInfo)){
				errorInfo = errorInfo.substring(0, error.toString().length()-2);
			}else{
				errorInfo = InHomeNPSConstants.NO_DATA;
			}
			if(!InHomeNPSConstants.INVALID_MESSAGE.equalsIgnoreCase(invalidInfo)){
				invalidInfo = invalidInfo.substring(0, invalid.toString().length()-2);
			}else{
				invalidInfo = InHomeNPSConstants.NO_DATA;
			}
			if(StringUtils.isNotBlank(errorInfo) && StringUtils.isNotBlank(invalidInfo)){
				result = errorInfo + InHomeNPSConstants.SEPERATOR + invalidInfo;
			}
			else if(StringUtils.isNotBlank(errorInfo)){
				result = errorInfo;
			}
			else if(StringUtils.isNotBlank(invalidInfo)){
				result = invalidInfo;
			}
			
			return result;
		}
	}
	
	/**
	 * Format the date as MMddyyyy
	 * @param format
	 * @param date
	 * @return
	 */
	private String formatDate(Date date){
		DateFormat formatter = new SimpleDateFormat(InHomeNPSConstants.TIME_FORMAT);
		String formattedDate = InHomeNPSConstants.NO_DATA;
		try {
			if(null != date){
				formattedDate = formatter.format(date);
				//to get the format as MMddyyyy
				formattedDate = formattedDate.substring(0,formattedDate.indexOf(","));
				String dates[] = formattedDate.split("-");
				formattedDate = dates[1] + dates[2] + dates[0];
			}
		} catch (Exception e) {
			logger.error("Exception in BuyerOutboundNotificationCmd.formatDate() "+ e.getMessage(), e);
			formattedDate = InHomeNPSConstants.NO_DATA;
		}
		return formattedDate;
	}
	
	/**R12_0
	 * Format the time as HHmm
	 * @param format
	 * @param date
	 * @return
	 */
	private String formatTime(String time){
		String formattedTime = InHomeNPSConstants.NO_DATA;
		try {
			if(null != time){
			
				String times[] = time.split(":");
				String[] timeFormatted=times[1].split(" ");
				//to get the time format as HHmm
				formattedTime = times[0] + timeFormatted[0] + timeFormatted[1];
			}
		} catch (Exception e) {
			logger.error("Exception in BuyerOutboundNotificationCmd.formatTime() "+ e.getMessage(), e);
			formattedTime = InHomeNPSConstants.NO_DATA;
		}
		return formattedTime;
	}
	
	// Replace < &lt; > &gt;  & &amp; " &quot; ' &apos;
	private String replaceSpecialCharacters(String value) {
		if(StringUtils.isNotBlank(value)){
			value = value.replaceAll("&","&amp;");
			value = value.replaceAll("<","&lt;");
			value = value.replaceAll(">","&gt;");
			value = value.replaceAll("\"","&quot;");
			value = value.replaceAll("'", "&apos;");
		}
		return value;
	}
	
	
	//R12_0 :setting the values to be inserted to buyer_outbound_notification table 
		private OutboundNotificationVO populateNotification(String result, OrderUpdateRequest inHomeDetails) {
			
			logger.info("Inside populateNotification");
			//generating the request xml as string
			XStream xstream = new XStream();
			xstream.processAnnotations(OrderUpdateRequest.class);
			String requestXml = (String) xstream.toXML(inHomeDetails);
			
			//setting notification object
			OutboundNotificationVO outBoundNotificationVO = new OutboundNotificationVO();
			outBoundNotificationVO.setSeqNo(inHomeDetails.getCorrelationId());
			outBoundNotificationVO.setSoId(inHomeDetails.getSoId());
			// Service Id, defaulted to '2'
			outBoundNotificationVO.setServiceId(InHomeNPSConstants.IN_HOME_SERVICE_ID);
			outBoundNotificationVO.setXml(requestXml);
			// retry count, defaulted to '-1'
			outBoundNotificationVO.setNoOfRetries(InHomeNPSConstants.IN_HOME_RETRY_COUNT);
			outBoundNotificationVO.setCreatedDate(new Date());
			outBoundNotificationVO.setModifiedDate(new Date());
			outBoundNotificationVO.setActiveInd(0);
			if (InHomeNPSConstants.VALIDATION_SUCCESS.equalsIgnoreCase(result)) {
				// no validation error
				outBoundNotificationVO.setStatus(InHomeNPSConstants.STATUS_WAITING);
			}
			else if(InHomeNPSConstants.INACTIVE.equalsIgnoreCase(result)){
				//if SO is inactive
				outBoundNotificationVO.setStatus(InHomeNPSConstants.STATUS_EXCLUDED);
				outBoundNotificationVO.setException(result);
			}
			else {
				// Changes starts for SL-20602
				String temp = result;
				temp = temp.replaceAll(",", "");
				boolean exclude = true;
				String searchString = "Missing Information :callCd partCoverageCode for Part 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 | Invalid Information :jobCoverageCd : Not in valid set ";
				String[] words = temp.split("\\s+"); // splits by whitespace
				for (String word : words) {
				    if (!searchString.contains(word))
				    	exclude = false;
				}
				if (exclude){
					outBoundNotificationVO.setStatus(InHomeNPSConstants.STATUS_EXCLUDED);
					outBoundNotificationVO.setException(result);
				}
				else {
					// validation error
					outBoundNotificationVO.setStatus(InHomeNPSConstants.STATUS_ERROR);
					outBoundNotificationVO.setException(result);
				}
				//Changes ends for SL-20602
			}
			return outBoundNotificationVO;
		}

		
		private HashMap<String, Object> getRescheduleDateTime(Date rescheduleDate,String time,String timeZone){
			
			HashMap<String, Object> rescheduleDateTime =null;
			
			Timestamp ts;
			try {
					ts = new Timestamp(TimeUtils.combineDateTime(rescheduleDate, time).getTime());
					if(null!=ts && null!=timeZone)
					{
						rescheduleDateTime=TimeUtils.convertGMTToGivenTimeZone(ts, time, timeZone);
					}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.error("Exception in BuyerOutboundNotificationCmd.getRescheduleDateTime() "+ e.getMessage(), e);
			}catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Exception in BuyerOutboundNotificationCmd.getRescheduleDateTime() "+ e.getMessage(), e);
			}
			
			return rescheduleDateTime;
		}
		
	/**Owner  : Infosys
	 * module : Inhome Service Operation Notificatios
	 * purpose: Set the Job code from lu table if exists for the main service category in notification xml
	 * Jira : SL-21241
	 * @description: Set the Job code from lu table if exists for the main service category
	 * @param primarySkillCatId
	 * @param job
	 */
	private void setJobeCode(Integer primarySkillCatId, JobCodeData job) {
		try{
			if(null!= primarySkillCatId){
				String jobCode = serviceOrderDao.getJobCodeForNodeId(primarySkillCatId); 
				if(StringUtils.isNotBlank(jobCode)){
					job.setJobCode(jobCode);
				}
			}
		}catch (Exception e) {
			logger.error("Exception in getting job code from the lu table for the main service category"+primarySkillCatId+ 
					"retaining the default one in job code and ignoring the exception" );
		}
			
	}	
	// //////////////////////////////////////////////////////////////////////////
	// SETTERS FOR SPRING INJECTION
	// //////////////////////////////////////////////////////////////////////////
	public void setQuickLookupCollection(
			QuickLookupCollection quickLookupCollection) {
		this.quickLookupCollection = quickLookupCollection;
	}

	public void setCarAssociationDao(ICARAssociationDao carAssociationDao) {
		this.carAssociationDao = carAssociationDao;
	}
}
