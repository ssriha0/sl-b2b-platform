/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 21-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so.v1_1;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.lang.StringUtils;
import com.newco.marketplace.exception.core.BusinessServiceException;
import org.apache.log4j.Logger;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.reschedule.SORescheduleInfo;
import com.newco.marketplace.api.beans.so.reschedule.SORescheduleRequest;
import com.newco.marketplace.api.beans.so.reschedule.SORescheduleResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.OFMapper;
import com.newco.marketplace.api.utils.mappers.so.v1_1.SORescheduleMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.vo.ordermanagement.so.RescheduleVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.Parameter;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * This class is a service class for cancel Service Order
 * 
 * 
 * @author Infosys
 * @version 1.0
 */
public class SORescheduleService extends SOBaseService{

	private Logger logger = Logger.getLogger(SORescheduleService.class);
	private IServiceOrderBO serviceOrderBO;
	private SORescheduleMapper rescheduleMapper;

	/**
	 * Constructor
	 */

	public SORescheduleService () {
		super (PublicAPIConstant.RESCHEDULE_XSD,
				PublicAPIConstant.SORESPONSE_XSD, 
				PublicAPIConstant.SORESPONSE_NAMESPACE, 
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION,	
				SORescheduleRequest.class,
				SORescheduleResponse.class);
	}	



	/**
	 * This method dispatches the reschedule Service order request.
	 * 
	 * @param apiVO APIRequestVO
	 * @return IAPIResponse
	 */
    @Override
    protected IAPIResponse executeLegacyService(APIRequestVO apiVO) {
		
		logger.info("Entering SORescheduleService.execute()");	
		SORescheduleRequest soRescheduleRequest = new SORescheduleRequest();
		SORescheduleResponse rescheduleResponse = new SORescheduleResponse();
		String soId = apiVO.getSOId();
		int buyerId =apiVO.getBuyerIdInteger();
		try {
			soRescheduleRequest  = (SORescheduleRequest) apiVO.getRequestFromPostPut();
			
			SecurityContext securityContext = 
				getSecurityContextForBuyerAdmin(buyerId);
			
			ProcessResponse processResponse = new ProcessResponse();
			boolean isError = false;
			ServiceOrder serviceOrder = null;
			SORescheduleInfo rescheduleInfo = rescheduleMapper.mapSORescheduleInfo(
									soRescheduleRequest.getSoRescheduleInfo());
			if (rescheduleInfo.getScheduleType().equalsIgnoreCase(
					PublicAPIConstant.DATETYPE_RANGE)
					&& null == rescheduleInfo.getServiceDateTime2()) {
				setErrorMsg(processResponse, ResultsCode.ENDDATE_REQUIRED.getMessage());
				isError = true;

			} else if (rescheduleInfo.getScheduleType().equalsIgnoreCase(
					PublicAPIConstant.DATETYPE_FIXED)
					&& null != rescheduleInfo.getServiceDateTime2()) {
				rescheduleInfo.setServiceDate2(null);
				rescheduleInfo.setServiceTimeEnd(null);
			}

			if (!isError) {
				serviceOrder = serviceOrderBO.getServiceOrder(soId);

				HashMap<String, Object> serviceDate1Map = new HashMap<String, Object>();
				HashMap<String, Object> serviceDate2Map = new HashMap<String, Object>();
						if (serviceOrder.getWfStateId().intValue() == OrderConstants.DRAFT_STATUS
							||serviceOrder.getWfStateId().intValue() == OrderConstants.ROUTED_STATUS) {
						java.util.Date today = new java.util.Date();
						java.sql.Date now = new java.sql.Date(today.getTime());
						serviceDate1Map = TimeUtils.convertToGMT(rescheduleInfo
								.getServiceDate1(), rescheduleInfo
								.getServiceTimeStart(), serviceOrder
								.getServiceLocationTimeZone());
						rescheduleInfo.setServiceDate1((Timestamp) serviceDate1Map
								.get(OrderConstants.GMT_DATE));
						rescheduleInfo.setServiceTimeStart((String) serviceDate1Map
								.get(OrderConstants.GMT_TIME));

						if (null != rescheduleInfo.getServiceDate2()) {
							serviceDate2Map = TimeUtils.convertToGMT(rescheduleInfo
									.getServiceDate2(), rescheduleInfo
									.getServiceTimeEnd(), serviceOrder
									.getServiceLocationTimeZone());
							rescheduleInfo
							.setServiceDate2((Timestamp) serviceDate2Map
									.get(OrderConstants.GMT_DATE));
							rescheduleInfo
							.setServiceTimeEnd((String) serviceDate2Map
									.get(OrderConstants.GMT_TIME));


							// check if start < end
							Timestamp newStartTimeCombined = new Timestamp(
									TimeUtils.combineDateAndTime(
											rescheduleInfo.getServiceDate1(),
											rescheduleInfo.getServiceTimeStart(),
											serviceOrder
											.getServiceLocationTimeZone())
											.getTime());
							Timestamp newEndTimeCombined = new Timestamp(TimeUtils
									.combineDateAndTime(
											rescheduleInfo.getServiceDate2(),
											rescheduleInfo.getServiceTimeEnd(),
											serviceOrder
											.getServiceLocationTimeZone())
											.getTime());
							if (newStartTimeCombined.compareTo(newEndTimeCombined) >= 0) {
								setErrorMsg(
										processResponse,
										ResultsCode.START_DATE_NOT_PRIOR.getMessage());
								isError = true;
							}
							if (newStartTimeCombined.compareTo(now) < 0) {
								setErrorMsg(
										processResponse,
										ResultsCode.START_DATE_PAST.getMessage());
								isError = true;
							}
						}
					}

					if (!isError) {
								//Check the status
							if (serviceOrder.getWfStateId().intValue() 
									== OrderConstants.ACCEPTED_STATUS
									|| serviceOrder.getWfStateId().intValue()
									== OrderConstants.ACTIVE_STATUS
									|| serviceOrder.getWfStateId().intValue() 
									== OrderConstants.PROBLEM_STATUS
									|| serviceOrder.getWfStateId().intValue()
									== OrderConstants.DRAFT_STATUS) {
								processResponse = serviceOrderBO
								.requestRescheduleSO(soId, rescheduleInfo
										.getServiceDate1(), rescheduleInfo
										.getServiceDate2(), rescheduleInfo
										.getServiceTimeStart(),
										rescheduleInfo.getServiceTimeEnd(),
										null, securityContext.getRoleId(),
										securityContext.getCompanyId(),
										rescheduleInfo.getScheduleType(),
										true, securityContext);

							} else {
								if(serviceOrder.getWfStateId().intValue() 
										== OrderConstants.ROUTED_STATUS){
									processResponse = serviceOrderBO
									.processRescheduleForRoutedStatus(soId, rescheduleInfo
											.getServiceDate1(), rescheduleInfo
											.getServiceDate2(), rescheduleInfo
											.getServiceTimeStart(),
											rescheduleInfo.getServiceTimeEnd(),
											securityContext.getCompanyId(),
											true, securityContext);
								}else{
									setErrorMsg(processResponse,ResultsCode.
										INVALID_STATUS_TO_RESCHEDULE.getMessage());
								}
							}
						
					}
				
			}
			rescheduleResponse = rescheduleMapper.rescheduleSOResponseMapping(
					processResponse, serviceOrder);
			
		}
		catch(NumberFormatException nme){
			logger.error("SORescheduleService.execute(): Number Format Exception "
								+"occurred for resourceId:" + buyerId);
			rescheduleResponse.setResults( Results.getError(
					ResultsCode.INVALID_RESOURCE_ID
					.getMessage(), ResultsCode.INVALID_RESOURCE_ID.getCode()));
		}
		catch (Exception e ) {
			rescheduleResponse.setResults(
					Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
							ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
		}
		if(logger.isInfoEnabled()){
			logger.info("Exiting  SORescheduleService.execute()");
		}	
		return rescheduleResponse;



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
		logger.error("Error Occurred" + msg);
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


    @Override
    protected IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO) {
        String soId = apiVO.getSOId();
        String timeZone = "";
        String rescheduleReason=null;
        logger.debug("soId :: in OF code"+soId);
        int buyerId =apiVO.getBuyerIdInteger();
        SecurityContext securityContext = getSecurityContextForBuyerAdmin(buyerId);
        Identification ident = OFMapper.createBuyerIdentFromSecCtx(securityContext);
       // com.servicelive.orderfulfillment.domain.ServiceOrder so = ofHelper.getServiceOrder(soId);
      //JIRA-19291- Edit reschedule request
      //fetching reschedule info to check if the SO has reschedule request
      		RescheduleVO soRescheduleInfo=serviceOrderBO.getRescheduleInfo(soId);
      		logger.info("Reschedule Info"+soRescheduleInfo.getRescheduleServiceDate1());
        try{
        timeZone = serviceOrderBO.getServiceLocTimeZone(soId);
        }catch (Exception e ) {
			logger.error("error in executeOrderFulfillmentService() of SORescheduleService.java" + e);
		}
        SOSchedule reschedule = rescheduleMapper.newSchedule((SORescheduleRequest) apiVO.getRequestFromPostPut());
        reschedule.setCreatedViaAPI(true);
        List<String> validateErrors =  new ArrayList<String>();
        validateErrors = reschedule.validateSheduleForAPI(timeZone);
        //SL-21230 : Validate the reason codes entered by the user for the reschedule
        if(StringUtils.isNotBlank(reschedule.getReason())){
        	try {
        		rescheduleReason = serviceOrderBO.validateReasonCodes(reschedule.getReason(),validateErrors);
        		if(StringUtils.isNotBlank(rescheduleReason)){
        		   reschedule.setReason(rescheduleReason);
        		}
			} catch (BusinessServiceException e) {
				logger.error("error in executeOrderFulfillmentService() of SORescheduleService.java while validating reeason code" + e);
			}
        }
        OrderFulfillmentResponse response = null;
        if(null == validateErrors || validateErrors.isEmpty()){
        	  SignalType signalType = SignalType.BUYER_REQUEST_RESCHEDULE;
              if(securityContext.getRoleId() == OrderConstants.BUYER_ROLEID
              		&& ofHelper.isSignalAvailable(soId, SignalType.EDIT_SCHEDULE)){
              	//check if the edit schedule is available and if so call it
              	signalType = SignalType.EDIT_SCHEDULE;
              }

              List<Parameter> parameters = new ArrayList<Parameter>();
              // Adding old and new reschedule date to so_logging data when a buyer try to edit reschedule.
             
              HashMap<String, Object> rescheduleStartDate = null;
	    		HashMap<String, Object> rescheduleEndDate = null;
	    		String format = OrderConstants.RESCHEDULE_DATE_FORMAT;
	    		StringBuffer reschedulePeriod = new StringBuffer();
	    		 try{
	            if(null != soRescheduleInfo && null != soRescheduleInfo.getRescheduleServiceStartDate()){
	            	if (null != soRescheduleInfo.getRescheduleServiceStartDate() && null != soRescheduleInfo.getRescheduleServiceTime1()) {
	            		rescheduleStartDate = TimeUtils.convertGMTToGivenTimeZone(soRescheduleInfo.getRescheduleServiceStartDate(), soRescheduleInfo.getRescheduleServiceTime1(), soRescheduleInfo.getServiceLocnTimeZone());
	            		if (null != rescheduleStartDate && !rescheduleStartDate.isEmpty()) {
	            			reschedulePeriod.append("<br/>Buyer edited the reschedule request from ");
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
	    		 }catch(Exception e){
		            	logger.error("Exception occured in edit reschedule:"+e);
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
  			 //SL-21230 : Setting reason code and comments in misc params
			  if(StringUtils.isNotBlank(reschedule.getReason())){
			     parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RESCHEDULE_REQUEST_REASON_CODE," Reason: "+ reschedule.getReason()));
			  }
              parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RESCHEDULE_REQUEST_COMMENT, reschedule.getComments()));
              parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_EDIT_RESCHEDULE_DATE_INFO, reschedulePeriod.toString()));
              response = ofHelper.runOrderFulfillmentProcess(soId, signalType, reschedule, ident, parameters);
        }
        return mapOFResponse(response,validateErrors);
      
    }
    /**
     * Create the response for the API
     * @param response
     * @param validateErrors
     * @return
     */
    private SORescheduleResponse mapOFResponse(OrderFulfillmentResponse response,List<String> validateErrors){
        SORescheduleResponse returnVal = new SORescheduleResponse();
        if(null!= validateErrors && validateErrors.size()>0){
        	for(String error : validateErrors){        		
        		returnVal.setResults(Results.getError(error, ResultsCode.FAILURE.getCode()));
        	}
        }else if(null!= response){
        	 if(!response.getErrors().isEmpty()){
                 returnVal.setResults(Results.getError(response.getErrorMessage(), ResultsCode.FAILURE.getCode()));
             }else{
                 returnVal.setResults(Results.getSuccess(ResultsCode.RESCHEDULE_REQUEST_SUBMITTED.getMessage()));
             }
        }
        return returnVal;
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
		try {
			newDate = new SimpleDateFormat(format, Locale.ENGLISH).parse(modifiedDate);
		} catch (ParseException e) {
			logger.info("Parse Exception SODetailsDelegateImpl.java "+ e);
		}
        TimeZone gmtTime = TimeZone.getTimeZone(timeZone);
        if(gmtTime.inDaylightTime(newDate)){
        	return "("+getDSTTimezone(timeZone)+")";
	    }
	    return "("+getStandardTimezone(timeZone)+")";
   }

	public String getDSTTimezone(String timeZone) {
		
		if ("EST5EDT".equals(timeZone)) {
			timeZone = "EDT";
		}
		if ("AST4ADT".equals(timeZone)) {
			timeZone = "ADT";
		}
		if ("CST6CDT".equals(timeZone)) {
			timeZone = "CDT";
		}
		if ("MST7MDT".equals(timeZone)) {
			timeZone = "MDT";
		}
		if ("PST8PDT".equals(timeZone)) {
			timeZone = "PDT";
		}
		if ("HST".equals(timeZone)) {
			timeZone = "HADT";
		}
		if ("Etc/GMT+1".equals(timeZone)) {
			timeZone = "CEDT";
		}
		if ("AST".equals(timeZone)) {
			timeZone = "AKDT";
		}
		return timeZone;
	}

	public String getStandardTimezone(String timeZone) {
		if ("EST5EDT".equals(timeZone)) {
			timeZone = "EST";
		}
		if ("AST4ADT".equals(timeZone)) {
			timeZone = "AST";
		}
		if ("CST6CDT".equals(timeZone)) {
			timeZone = "CST";
		}
		if ("MST7MDT".equals(timeZone)) {
			timeZone = "MST";
		}
		if ("PST8PDT".equals(timeZone)) {
			timeZone = "PST";
		}
		if ("HST".equals(timeZone)) {
			timeZone = "HAST";
		}
		if ("Etc/GMT+1".equals(timeZone)) {
			timeZone = "CET";
		}
		if ("AST".equals(timeZone)) {
			timeZone = "AKST";
		}
		if ("Etc/GMT-9".equals(timeZone)) {
			timeZone = "PST-7";
		}
		if ("MIT".equals(timeZone)) {
			timeZone = "PST-3";
		}
		if ("NST".equals(timeZone)) {
			timeZone = "PST-4";
		}
		if ("Etc/GMT-10".equals(timeZone)) {
			timeZone = "PST-6";
		}
		if ("Etc/GMT-11".equals(timeZone)) {
			timeZone = "PST-5";
		}
		return timeZone;
	}
}
