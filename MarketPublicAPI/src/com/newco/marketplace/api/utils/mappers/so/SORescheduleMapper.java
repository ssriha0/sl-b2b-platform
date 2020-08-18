/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Jun-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.mappers.so;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.OrderStatus;
import com.newco.marketplace.api.beans.so.reschedule.SORescheduleInfo;
import com.newco.marketplace.api.beans.so.reschedule.SORescheduleResponse;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
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
	 * @param searchRequest
	 *            SearchRequest
	 * @param securityContext
	 *            SecurityContext
	 * @throws DataException
	 * @return SearchRequestVO
	 */
	public SORescheduleInfo mapServiceOrder(SORescheduleInfo 
										rescheduleInfo) throws DataException {
		logger.info("Entering SORescheduleMapper.mapServiceOrder()");
		rescheduleInfo.setServiceDate1(new Timestamp(
				DateUtils.defaultFormatStringToDate(
						rescheduleInfo.getServiceDateTime1()).getTime()));
		// setting the servicestartTime
		Date serviceStartTime = null;
		try {
			serviceStartTime = CommonUtility.sdfToDate.parse(rescheduleInfo
					.getServiceDateTime1());
		} catch (ParseException e) {
			logger.error("Exception Occurred while setting Service "
					+ "Start Time");
		}

		String serviceStartTimeStr = sdfToTime.format(serviceStartTime);
		rescheduleInfo.setServiceTimeStart(serviceStartTimeStr);
		
		if(null!=rescheduleInfo.getServiceDateTime2()){
		rescheduleInfo.setServiceDate2(new Timestamp(
				DateUtils.defaultFormatStringToDate(
						rescheduleInfo.getServiceDateTime2()).getTime()));
		// setting the serviceEndTime
		Date serviceEndDate = null;
		try {
			serviceEndDate = CommonUtility.sdfToDate.parse(rescheduleInfo
					.getServiceDateTime2());
		} catch (ParseException e) {
			logger.error("Parse Exception Occurred while "
					+ "setting ServiceEndDate");
		}
		String serviceEndTimeStr = sdfToTime.format(serviceEndDate);
		rescheduleInfo.setServiceTimeEnd(serviceEndTimeStr);
		}
		logger.info("Leaving SORescheduleMapper.mapServiceOrder()");
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
		List<Result> resultList=new ArrayList<Result>();
		List<ErrorResult> errorList=new ArrayList<ErrorResult>();
		Result result = new Result();
		ErrorResult errorResult = new ErrorResult();
		OrderStatus orderStatus = new OrderStatus();
		if (!processResponse.getCode().equals(ServiceConstants.VALID_RC)) {
			result.setCode(PublicAPIConstant.ZERO);
			result.setMessage("");
			errorResult.setCode(processResponse.getCode());
			if(null!=processResponse.getMessages().get(0)){
				errorResult.setMessage(processResponse.getMessages().get(0));
			}else{
					errorResult.setMessage("");
				}
		}else{
			result.setCode(PublicAPIConstant.ONE);
			result.setMessage(CommonUtility.getMessage(
									PublicAPIConstant.RESCHEDULE_RESULT_CODE));
			errorResult.setCode(PublicAPIConstant.ZERO);
			errorResult.setMessage("");
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
		resultList.add(result);
		errorList.add(errorResult);
		results.setResult(resultList);
		results.setError(errorList);
		rescheduleResponse.setResults(results);
		rescheduleResponse.setVersion(PublicAPIConstant.SORESPONSE_VERSION);
		rescheduleResponse.setSchemaLocation(PublicAPIConstant.SORESPONSE_SCHEMALOCATION);
		rescheduleResponse.setNamespace(PublicAPIConstant.SORESPONSE_NAMESPACE);
		rescheduleResponse.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		return rescheduleResponse;
	}
}
