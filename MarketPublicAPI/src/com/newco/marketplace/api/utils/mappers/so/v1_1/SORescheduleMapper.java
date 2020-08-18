/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 24-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.mappers.so.v1_1;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.OrderStatus;
import com.newco.marketplace.api.beans.so.reschedule.SORescheduleInfo;
import com.newco.marketplace.api.beans.so.reschedule.SORescheduleRequest;
import com.newco.marketplace.api.beans.so.reschedule.SORescheduleResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.type.SOScheduleType;
/**
 * This class is a mapper class for rescheduling a Service Order.
 * 
 * @author Infosys
 * @version 1.0
 */
public class SORescheduleMapper {
	
	private SimpleDateFormat sdfToTime = new SimpleDateFormat("HH:mm:ss");

	private Logger logger = Logger.getLogger(SORescheduleMapper.class);
	/**
	 * This method is for mapping Service date  values to
	 * SORescheduleInfo Object.
	 *
     * @param rescheduleInfo 
	 * @throws DataException
	 * @return SearchRequestVO
	 */
	public SORescheduleInfo mapSORescheduleInfo(SORescheduleInfo rescheduleInfo) {
		logger.debug("Entering SORescheduleMapper.mapServiceOrder()");
		rescheduleInfo.setServiceDate1(new Timestamp(DateUtils.defaultFormatStringToDate(rescheduleInfo.getServiceDateTime1()).getTime()));
		// setting the servicestartTime
		Date serviceStartTime = null;
		try {
			serviceStartTime = CommonUtility.sdfToDate.parse(rescheduleInfo.getServiceDateTime1());
		} catch (ParseException e) {
			logger.error("Exception Occurred while setting Service "
					+ "Start Time");
		}

		String serviceStartTimeStr = sdfToTime.format(serviceStartTime);
		rescheduleInfo.setServiceTimeStart(serviceStartTimeStr);
		
		if(null!=rescheduleInfo.getServiceDateTime2()){
			rescheduleInfo.setServiceDate2(new Timestamp(DateUtils.defaultFormatStringToDate(rescheduleInfo.getServiceDateTime2()).getTime()));
			// setting the serviceEndTime
			Date serviceEndDate = null;
			try {
				serviceEndDate = CommonUtility.sdfToDate.parse(rescheduleInfo.getServiceDateTime2());
			} catch (ParseException e) {
				logger.error("Parse Exception Occurred while "
						+ "setting ServiceEndDate");
			}
			String serviceEndTimeStr = sdfToTime.format(serviceEndDate);
			rescheduleInfo.setServiceTimeEnd(serviceEndTimeStr);
			rescheduleInfo.setReasonCode(rescheduleInfo.getReasonCode());
			rescheduleInfo.setComments(rescheduleInfo.getComments());
		} else {
			logger.warn("SORescheduleMapper.mapServiceOrder() ServiceDateTime2 is null");
		}
		logger.debug("Leaving SORescheduleMapper.mapServiceOrder()");
		return rescheduleInfo;
		
	}
	
	/**
	 * This method is for createSOResponse xml from ProcessResponse object.
	 * 
	 * @param processResponse ProcessResponse
	 * @return soCreateResponse
	 */
	public SORescheduleResponse rescheduleSOResponseMapping(ProcessResponse 
								processResponse,ServiceOrder serviceOrder ){
		logger.info("Inside rescheduleSOResponseMapping--->Start");
		SORescheduleResponse rescheduleResponse = new SORescheduleResponse();
		Results results = new Results();
		OrderStatus orderStatus = new OrderStatus();
		if (!processResponse.getCode().equals(ServiceConstants.VALID_RC)) {
			results= Results.getError(processResponse.getMessages().get(0),
					ResultsCode.FAILURE.getCode());
		}else{
			results=Results.getSuccess(ResultsCode.RESCHEDULE_REQUEST_SUBMITTED.getMessage());
			orderStatus.setSoId(serviceOrder.getSoId());
			if(null!=serviceOrder.getStatus()){
			orderStatus.setStatus(serviceOrder.getStatus());
			}else{
			orderStatus.setStatus("");
			}
			if(null!=serviceOrder.getSubStatus()){
			orderStatus.setSubstatus(serviceOrder.getSubStatus());
			}else{
			orderStatus.setSubstatus("");
			}
			if (null != serviceOrder.getCreatedDate()) {
				orderStatus.setCreatedDate(CommonUtility.sdfToDate
						.format(serviceOrder.getCreatedDate()));
			}
			if (null != serviceOrder.getRoutedDate()) {
				orderStatus.setPostedDate(CommonUtility.sdfToDate
						.format(serviceOrder.getRoutedDate()));
			}
			if (null != serviceOrder.getAcceptedDate()) {
				orderStatus.setAcceptedDate(CommonUtility.sdfToDate
						.format(serviceOrder.getAcceptedDate()));
			}
			rescheduleResponse.setOrderstatus(orderStatus);
		}
		rescheduleResponse.setResults(results);

		return rescheduleResponse;
	}
	
	/**
	 * This method is for cancelRequestServiceResponse from ProcessResponse object.
	 * 
	 * @param processResponse ProcessResponse
	 * @return soCreateResponse
	 */
	public SORescheduleResponse cancelRescheduleSOResponseMapping(ProcessResponse 
								processResponse,ServiceOrder serviceOrder ){
		logger.info("Inside cancelRescheduleSOResponseMapping--->Start");
		SORescheduleResponse rescheduleResponse = new SORescheduleResponse();
		Results results = new Results();
		OrderStatus orderStatus = new OrderStatus();
		if (!processResponse.getCode().equals(ServiceConstants.VALID_RC)) {
			results= Results.getError(processResponse.getMessages().get(0),
					ResultsCode.FAILURE.getCode());
		}else{
			results=Results.getSuccess(ResultsCode.CANCEL_RESCHEDULE_REQUEST_SUBMITTED.getMessage());
			orderStatus.setSoId(serviceOrder.getSoId());
			if(null!=serviceOrder.getStatus()){
			orderStatus.setStatus(serviceOrder.getStatus());
			}else{
			orderStatus.setStatus("");
			}
			if(null!=serviceOrder.getSubStatus()){
			orderStatus.setSubstatus(serviceOrder.getSubStatus());
			}else{
			orderStatus.setSubstatus("");
			}
			if (null != serviceOrder.getCreatedDate()) {
				orderStatus.setCreatedDate(CommonUtility.sdfToDate
						.format(serviceOrder.getCreatedDate()));
			}
			if (null != serviceOrder.getRoutedDate()) {
				orderStatus.setPostedDate(CommonUtility.sdfToDate
						.format(serviceOrder.getRoutedDate()));
			}
			if (null != serviceOrder.getAcceptedDate()) {
				orderStatus.setAcceptedDate(CommonUtility.sdfToDate
						.format(serviceOrder.getAcceptedDate()));
			}
			rescheduleResponse.setOrderstatus(orderStatus);
		}
		rescheduleResponse.setResults(results);

		return rescheduleResponse;
	}
	
	private SORescheduleInfo populateInfoFields(SORescheduleInfo rescheduleInfo) {

		SimpleDateFormat sdfToTime = new SimpleDateFormat("hh:mm a");
		logger.debug("Entering SORescheduleMapper.mapServiceOrder()");
		rescheduleInfo.setServiceDate1(new Timestamp(DateUtils.defaultFormatStringToDate(rescheduleInfo.getServiceDateTime1()).getTime()));
		// setting the servicestartTime
		Date serviceStartTime = null;
		try {
			serviceStartTime = CommonUtility.sdfToDate.parse(rescheduleInfo.getServiceDateTime1());
		} catch (ParseException e) {
			logger.error("Exception Occurred while setting Service "
					+ "Start Time");
		}

		String serviceStartTimeStr = sdfToTime.format(serviceStartTime);
		rescheduleInfo.setServiceTimeStart(serviceStartTimeStr);
		
		if(null!=rescheduleInfo.getServiceDateTime2()){
			rescheduleInfo.setServiceDate2(new Timestamp(DateUtils.defaultFormatStringToDate(rescheduleInfo.getServiceDateTime2()).getTime()));
			// setting the serviceEndTime
			Date serviceEndDate = null;
			try {
				serviceEndDate = CommonUtility.sdfToDate.parse(rescheduleInfo.getServiceDateTime2());
			} catch (ParseException e) {
				logger.error("Parse Exception Occurred while "
						+ "setting ServiceEndDate");
			}
			String serviceEndTimeStr = sdfToTime.format(serviceEndDate);
			rescheduleInfo.setServiceTimeEnd(serviceEndTimeStr);
		}
		logger.debug("Leaving SORescheduleMapper.mapServiceOrder()");
		return rescheduleInfo;
		
	}
		
	public SOSchedule newSchedule(SORescheduleRequest request){
	    SOSchedule returnVal = new SOSchedule();
	    
	    //populate the broken up date and time
	    SORescheduleInfo requestedRescheduleInfo = populateInfoFields(request.getSoRescheduleInfo());
	    
	    //retrieve values
        if(StringUtils.equalsIgnoreCase(PublicAPIConstant.DATETYPE_FIXED,  requestedRescheduleInfo.getScheduleType())){
	        returnVal.setServiceDateTypeId(SOScheduleType.SINGLEDAY);
	    }else if(StringUtils.equalsIgnoreCase(PublicAPIConstant.DATETYPE_RANGE,  requestedRescheduleInfo.getScheduleType())){
	        returnVal.setServiceDateTypeId(SOScheduleType.DATERANGE);
	    }
        
	    returnVal.setServiceDate1(requestedRescheduleInfo.getServiceDate1());
	    returnVal.setServiceDate2(requestedRescheduleInfo.getServiceDate2());
	    returnVal.setServiceTimeStart(requestedRescheduleInfo.getServiceTimeStart());
	    returnVal.setServiceTimeEnd(requestedRescheduleInfo.getServiceTimeEnd());
	    if(null!=requestedRescheduleInfo.getReasonCode()){
	           returnVal.setReason(requestedRescheduleInfo.getReasonCode().toString());
		    }
		//SL-21230 : adding code to set comments from the request
		 if(StringUtils.isNotBlank(requestedRescheduleInfo.getComments())){
		    returnVal.setComments(requestedRescheduleInfo.getComments());
		 }
	    return returnVal;
	}
}
