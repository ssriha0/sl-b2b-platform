/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 21-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so.v1_1;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.reschedule.SOProviderRescheduleRequest;
import com.newco.marketplace.api.beans.so.reschedule.SORescheduleRequest;
import com.newco.marketplace.api.beans.so.reschedule.SORescheduleResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.SORescheduleMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
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
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeRescheduleVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.TimeChangeUtil;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.vo.ordermanagement.so.RescheduleVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.type.SOScheduleType;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.Parameter;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification.EntityType;
import com.servicelive.ordermanagement.services.OrderManagementService;

/**
 * This class is a service class for cancel Service Order
 * 
 * 
 * @author Seshu
 * @version 1.0
 */
public class SOProviderRescheduleService extends SOBaseService{

	private OrderManagementService managementService;
	private static final Logger LOGGER = Logger.getLogger(SORescheduleService.class);
	private IServiceOrderBO serviceOrderBO;
	private INotificationService notificationService;
	private SORescheduleMapper rescheduleMapper;
	private IBuyerOutBoundNotificationService  buyerOutBoundNotificationService;
	private IBuyerOutBoundNotificationJMSService buyerOutBoundNotificationJMSService;
	private static final String RESCHEDULE_XSD = "rescheduleProviderRequest.xsd";
	/**
	 * Constructor
	 */

	public SOProviderRescheduleService () {
		super (RESCHEDULE_XSD,
				PublicAPIConstant.SOPROVIDERRESPONSE_XSD, 
				PublicAPIConstant.SORESPONSE_PROVIDER_NAMESPACE, 
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				PublicAPIConstant.SOPRORESPONSE_SCHEMALOCATION,	
				SOProviderRescheduleRequest.class,
				SORescheduleResponse.class);
	}	



	/**
	 * This method dispatches the reschedule Service order request.
	 * 
	 * @param apiVO APIRequestVO
	 * @return IAPIResponse
	 */
	public IAPIResponse execute(APIRequestVO apiVO) {		
		LOGGER.info("Entering SOProviderRescheduleService.execute()");	
		//OF CODE
		String soId = apiVO.getSOId();
		String timeZone = "";
		LOGGER.debug("soId :: in OF code"+soId);
		//String firmId = (String) apiVO.getProviderId();
		//com.servicelive.orderfulfillment.domain.ServiceOrder so = ofHelper.getServiceOrder(soId);
		//JIRA-19291- Edit reschedule request
		//fetching reschedule info to check if the SO has reschedule request
		RescheduleVO soRescheduleInfo=serviceOrderBO.getRescheduleInfo(soId);
		LOGGER.info("Reschedule Info"+soRescheduleInfo.getRescheduleServiceDate1());
		SOProviderRescheduleRequest soRescheduleRequest = (SOProviderRescheduleRequest) apiVO.getRequestFromPostPut();
		Integer resourceId = 0;
		if(null != soRescheduleRequest && null != soRescheduleRequest.getIdentification()){
			resourceId = soRescheduleRequest.getIdentification().getId();
		}
		SecurityContext securityContext = getSecurityContextForVendor(resourceId);
		Identification ident = createOFIdentityFromSecurityContext(securityContext);
		try{
			timeZone = serviceOrderBO.getServiceLocTimeZone(soId);
		}catch (Exception e ) {
			LOGGER.error("error in executeOrderFulfillmentService() of SORescheduleService.java" + e);
		}

		SORescheduleRequest req = new SORescheduleRequest();
		req.setSoRescheduleInfo(soRescheduleRequest.getSoRescheduleInfo());
		SOSchedule reschedule = rescheduleMapper.newSchedule(req);
		reschedule.setCreatedViaAPI(true);
		List<String> validateErrors =  new ArrayList<String>();
		validateErrors = reschedule.validateSheduleForAPI(timeZone);
		String reason = "";
		InHomeRescheduleVO result = new InHomeRescheduleVO();
		String format = OrderConstants.RESCHEDULE_DATE_FORMAT;
		try {
			reason = serviceOrderBO.getRescheduleReason(req.getSoRescheduleInfo().getReasonCode());		
			result.setRescheduleDate1(formatDate(format,reschedule.getServiceDate1())+" "+reschedule.getServiceTimeStart());
			if(SOScheduleType.DATERANGE == reschedule.getServiceDateTypeId()){
				result.setRescheduleDate2(formatDate(format,reschedule.getServiceDate2())+" "+reschedule.getServiceTimeEnd());
			}
		} catch (BusinessServiceException e) {
			LOGGER.error(e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e);
		}
		result.setServiceDate1(soRescheduleInfo.getServiceDate1());
		result.setServiceDate2(soRescheduleInfo.getServiceDate2());
		result.setStartTime(soRescheduleInfo.getStartTime());
		result.setEndTime(soRescheduleInfo.getEndTime());
		String rescheduleMessage = notificationService.createRescheduleMessageForProviderRescheduleAPI(result); 
		OrderFulfillmentResponse response = null;
		if(null == validateErrors || validateErrors.isEmpty()){
			SignalType signalType = SignalType.PROVIDER_REQUEST_RESCHEDULE;
			List<Parameter> parameters = new ArrayList<Parameter>();
			
			// Adding old and new reschedule date to so_logging data when a provider try to edit reschedule.
            
            HashMap<String, Object> rescheduleStartDate = null;
	    	HashMap<String, Object> rescheduleEndDate = null;
	    	
	    	StringBuffer reschedulePeriod = new StringBuffer();
	    	try{
	    		if(null != soRescheduleInfo && null != soRescheduleInfo.getRescheduleServiceStartDate()){
	    			if (null != soRescheduleInfo.getRescheduleServiceStartDate() && null != soRescheduleInfo.getRescheduleServiceTime1()) {
	    				rescheduleStartDate = TimeUtils.convertGMTToGivenTimeZone(soRescheduleInfo.getRescheduleServiceStartDate(), soRescheduleInfo.getRescheduleServiceTime1(), soRescheduleInfo.getServiceLocnTimeZone());
	            		if (null != rescheduleStartDate && !rescheduleStartDate.isEmpty()) {
	            			reschedulePeriod.append("<br/>Provider edited the reschedule request from ");
	            			reschedulePeriod.append(formatDate(format, (Date) rescheduleStartDate.get(OrderConstants.GMT_DATE)));
	            			reschedulePeriod.append(" ");
	            			reschedulePeriod.append((String) rescheduleStartDate.get(OrderConstants.GMT_TIME));
	            		}
	    			}
	            	if(null != soRescheduleInfo.getRescheduleServiceEndDate() && null != soRescheduleInfo.getRescheduleServiceTime2()){
	            		rescheduleEndDate = TimeUtils.convertGMTToGivenTimeZone(soRescheduleInfo.getRescheduleServiceEndDate(), soRescheduleInfo.getRescheduleServiceTime2(), soRescheduleInfo.getServiceLocnTimeZone());
	            		if (null != rescheduleEndDate && !rescheduleEndDate.isEmpty()) {
	            			reschedulePeriod.append(" - ");
	            			reschedulePeriod.append(formatDate(format, (Date) rescheduleEndDate.get(OrderConstants.GMT_DATE)));
	                		reschedulePeriod.append(" ");
	                		reschedulePeriod.append((String) rescheduleEndDate.get(OrderConstants.GMT_TIME));
	            		}
	            	}
	            	reschedulePeriod.append(" "+getTimeZone(formatDate(format, (Date) rescheduleStartDate.get(OrderConstants.GMT_DATE))+" "+(String) rescheduleStartDate.get(OrderConstants.GMT_TIME), OrderConstants.RESCHEDULE_DATE_TIME_STAMP_FORMAT1, soRescheduleInfo.getServiceLocnTimeZone()));
	            	if(null != reschedule.getServiceDate1() && null != reschedule.getServiceTimeStart()){
	            		reschedulePeriod.append(" to "+formatDate(format,reschedule.getServiceDate1())+" "+reschedule.getServiceTimeStart());
	            	}
	            	if(null != reschedule.getServiceDate2() && null != reschedule.getServiceTimeEnd()){
	            		reschedulePeriod.append(" - "+formatDate(format,reschedule.getServiceDate2())+" "+reschedule.getServiceTimeEnd());
	            	}
	            	reschedulePeriod.append(" "+getTimeZone(formatDate(format,reschedule.getServiceDate1())+" "+reschedule.getServiceTimeStart(), OrderConstants.RESCHEDULE_DATE_TIME_STAMP_FORMAT1, soRescheduleInfo.getServiceLocnTimeZone()));
	            }
	    	}
	    	catch(Exception e){
	    		LOGGER.error("Exception occured in edit reschedule:"+e);
		    }
			
			
			//JIRA-19291- Edit reschedule request
			//changing logging flow in case of edit reschedule request
			if(soRescheduleInfo.getRescheduleServiceStartDate()!=null)
        	{
        		parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_EDIT_RESCHEDULE_REQUEST, " edited reschedule request"));
        	}
			else{
				parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_EDIT_RESCHEDULE_REQUEST, " requested reschedule"));
			}
			
			parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RESCHEDULE_REQUEST_COMMENT, req.getSoRescheduleInfo().getComments()));
			parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RESCHEDULE_REQUEST_REASON_CODE,  " Reason: "+reason));
			parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_EDIT_RESCHEDULE_DATE_INFO, reschedulePeriod.toString()));
			LOGGER.info("requestRescheduleSO.rescheduleMessage : "+rescheduleMessage);
            parameters.add(new Parameter(OrderfulfillmentConstants.INHOME_OUTBOUND_NOTIFICATION_AUTOACCEPT_MESSAGE, rescheduleMessage));
            
            //Setting call code for creating request xml of Service Operation API
            parameters.add(new Parameter(OrderfulfillmentConstants.INHOME_OUTBOUND_NOTIFICATION_RESCHEDULE_CALL_CODE, InHomeNPSConstants.RESCHD_CALLCODE));
			response = ofHelper.runOrderFulfillmentProcess(soId, signalType, reschedule, ident, parameters);
		}
		return mapOFResponse(response,validateErrors,soId,reschedule,reason,req.getSoRescheduleInfo().getComments());

	}

	private SORescheduleResponse mapOFResponse(OrderFulfillmentResponse response,List<String> validateErrors,String soId,SOSchedule reschedule,String reason,String comment){
		SORescheduleResponse returnVal = new SORescheduleResponse();
		if(null!= validateErrors && validateErrors.size()>0){
			for(String error : validateErrors){        		
				returnVal.setResults(Results.getError(error, ResultsCode.FAILURE.getCode()));
			}
		}else if(null!= response){
			if(!response.getErrors().isEmpty()){
				returnVal.setResults(Results.getError(response.getErrorMessage(), ResultsCode.FAILURE.getCode()));
			}else{
				Boolean autoAccept=false;
				String message="Reschedule Request processed successfully.";
				com.servicelive.orderfulfillment.domain.ServiceOrder serviceOrder = ofHelper.getServiceOrder(soId);
				
/*				LOGGER.info("SO Serv Date1:"+serviceOrder.getSchedule().getServiceDate1());

				LOGGER.info("SO Serv Date2:"+serviceOrder.getSchedule().getServiceDate2());
				LOGGER.info("SO Serv Date2 long:"+serviceOrder.getSchedule().getServiceDate1().getTime());

				LOGGER.info("SO Serv Start:"+serviceOrder.getSchedule().getServiceTimeStart());
				LOGGER.info("SO Serv End:"+serviceOrder.getSchedule().getServiceTimeEnd());
				LOGGER.info("SO Serv date type id:"+serviceOrder.getSchedule().getServiceDateTypeId());
    			
				LOGGER.info("Schdeule Serv Date1:"+reschedule.getServiceDate1());
				LOGGER.info("SchdeuleDate2long:"+serviceOrder.getSchedule().getServiceDate1().getTime());

				LOGGER.info("Schdeule Date2:"+reschedule.getServiceDate2());
				LOGGER.info("Schdeule Start:"+reschedule.getServiceTimeStart());
				LOGGER.info("Schdeule End:"+reschedule.getServiceTimeEnd());
				LOGGER.info("Schdeule date type id:"+reschedule.getServiceDateTypeId());*/
				
				if(reschedule.getServiceDateTypeId().equals(serviceOrder.getSchedule().getServiceDateTypeId())&&
            			serviceOrder.getSchedule().getServiceDate1().equals(reschedule.getServiceDate1())&&
            			serviceOrder.getSchedule().getServiceTimeStart().equals(reschedule.getServiceTimeStart())){
					LOGGER.info("Entering auto accept true check in api");

            		if((reschedule.getServiceDateTypeId().equals(SOScheduleType.DATERANGE)&& 
            				serviceOrder.getSchedule().getServiceDate2()!=null&&
            						serviceOrder.getSchedule().getServiceDate2().equals(reschedule.getServiceDate2())&&
            						serviceOrder.getSchedule().getServiceTimeEnd().equals(reschedule.getServiceTimeEnd()))||
            						reschedule.getServiceDateTypeId().equals(SOScheduleType.SINGLEDAY)){

    					LOGGER.info("Entering auto accept true check in api 1");
            			autoAccept=true;
            			
            		}
            		
            	}else if(serviceOrder.getSchedule().getServiceDate1().equals(reschedule.getServiceDate1())&&
            			serviceOrder.getSchedule().getServiceTimeStart().equals(reschedule.getServiceTimeStart())){
					LOGGER.info("Entering auto accept true check in api 1");
            		autoAccept=true;
            		
            	}
				LOGGER.debug("Auto Accept:"+autoAccept);
				if(!autoAccept){
					LOGGER.info("Not auto accept in api");
            		if(serviceOrder.getReschedule()!=null&&serviceOrder.getReschedule().getServiceDate1()!=null){
            			message="Reschedule Request processed successfully.";
            		}else{
						returnVal.setResults(Results.getError("Reschedule Request has been rejected.", ResultsCode.FAILURE.getCode()));
						message="";
            		}
            	}else if(serviceOrder.getReschedule()!=null&&serviceOrder.getReschedule().getServiceDate1()!=null){
            		autoAccept=false;
            		message="Reschedule Request processed successfully.";
            	}else{
            		message="Reschedule Request has been accepted";
            	
					try
					{
						if(serviceOrder.getBuyerId().intValue()== 1000)
						{
							LOGGER.info("insideChoice");

							//BuyerOutboundNotificationVO buyerOutboundNotificationVO=null;
							RequestOrder order=new RequestOrder();
							//get reason Code & Comments from service order logging.
							BuyerOutboundNotificationVO logging=new BuyerOutboundNotificationVO();
							try {
								logging=buyerOutBoundNotificationService.getLoggingDetails(serviceOrder.getSoId());
							} catch (BusinessServiceException e) {
								LOGGER.info("error in getting so Logging"+e);
							}
							String modificationId=logging.getEntityId().toString();
							//String reasonLogging=logging.getReasonDescr();
							//String reasonCode="";
							//String reasonDescr="";
							/*  
                      int reasonCodeIndex= reasonLogging.lastIndexOf("Reason:");
                      reasonCodeIndex=reasonCodeIndex+7;
                      int reasonDescrIndex= reasonLogging.lastIndexOf("Comments:");
                      reasonDescrIndex=reasonDescrIndex+9;
                      int length=reasonLogging.length();
                      // get reasonCode from the logging comment
                      if(6!=reasonCodeIndex)
                      reasonCode= reasonLogging.subSequence(reasonCodeIndex, reasonDescrIndex-9).toString().trim();
                      // get reasonCode Description from the logging comment
                      if(8!=reasonDescrIndex)
                      reasonDescr= reasonLogging.subSequence(reasonDescrIndex, length).toString().trim() ;

                      if(StringUtils.isEmpty(reasonCode) && !StringUtils.isEmpty(reasonDescr)){
                    	  reasonCode = reasonDescr;
                      } */
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
							requestRescheduleInfo.setRescheduleRsnCdDescription(comment);	

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

						}
					}
					catch(Exception e)
					{
						LOGGER.info("ExceptioninChoice"+e);
					}

				}
				if(StringUtils.isNotBlank(message)){
					returnVal.setResults(Results.getSuccess(message));
				}
			}
		}
		return returnVal;
	}

	public Identification createOFIdentityFromSecurityContext(SecurityContext securityContext) {
		int loginRoleId = securityContext.getRoles().getRoleId();
		switch (loginRoleId) {
		case OrderConstants.BUYER_ROLEID :
		case OrderConstants.SIMPLE_BUYER_ROLEID :
			return getIdentification(securityContext, EntityType.BUYER);

		case OrderConstants.PROVIDER_ROLEID :
			return getIdentification(securityContext, EntityType.PROVIDER);

		case OrderConstants.NEWCO_ADMIN_ROLEID :
			return getIdentification(securityContext, EntityType.SLADMIN);
		}
		return null;
	}

	private Identification getIdentification(SecurityContext securityContext, EntityType entityType){
		Identification id = new Identification();
		id.setEntityType(entityType);
		id.setCompanyId((long)securityContext.getCompanyId());
		id.setResourceId((long)securityContext.getVendBuyerResId());
		id.setUsername(securityContext.getUsername());
		id.setFullname(securityContext.getRoles().getFirstName()+" "+securityContext.getRoles().getLastName());
		id.setRoleId(securityContext.getRoleId());
		return id;
	}

	/**
	 * This method is for setting error messages
	 * 
	 * @param processResponse ProcessResponse
	 * @param msg  String
	 * @return ProcessResponse
	 */
	public ProcessResponse setErrorMsg(ProcessResponse processResponse,
			String msg) {
		LOGGER.error("Error Occurred" + msg);
		//	processResponse.setCode(USER_ERROR_RC);
		//	processResponse.setSubCode(USER_ERROR_RC);
		List<String> arr = new ArrayList<String>();
		arr.add(msg);
		processResponse.setMessages(arr);
		return processResponse;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public SORescheduleMapper getRescheduleMapper() {
		return rescheduleMapper;
	}

	public void setRescheduleMapper(SORescheduleMapper rescheduleMapper) {
		this.rescheduleMapper = rescheduleMapper;
	}

	public OrderManagementService getManagementService() {
		return managementService;
	}

	public void setManagementService(OrderManagementService managementService) {
		this.managementService = managementService;
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



	@Override
	protected IAPIResponse executeLegacyService(APIRequestVO apiVO) {
		return null;
	}

	@Override
	protected IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO) {
		return null;
	}
	
	private String formatDate(String format, Date date){
		DateFormat formatter = new SimpleDateFormat(format);
		String formattedDate = null;
		try {
			formattedDate = formatter.format(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formattedDate;
	}
	
	public String getTimeZone(String modifiedDate, String format, String timeZone){
		Date newDate = null;
		String actualTimeZone = timeZone.substring(0, 3);
		String dayLightTimeZone = timeZone.substring(4, 7);
		try {
			newDate = new SimpleDateFormat(format, Locale.ENGLISH).parse(modifiedDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			LOGGER.info("Parse Exception SORescheduleService.java "+ e);
		}
        TimeZone gmtTime = TimeZone.getTimeZone(actualTimeZone);
        if(gmtTime.inDaylightTime(newDate)){
        	return "("+dayLightTimeZone+")";
        }
        return "("+actualTimeZone+")";
   }
	
	public INotificationService getNotificationService() {
		return notificationService;
	}
	
	public void setNotificationService(INotificationService notificationService) {
		this.notificationService = notificationService;
	}
}
