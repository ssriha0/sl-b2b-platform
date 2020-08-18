/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Jun-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.so.reschedule.SORescheduleInfo;
import com.newco.marketplace.api.beans.so.reschedule.SORescheduleRequest;
import com.newco.marketplace.api.beans.so.reschedule.SORescheduleResponse;
import com.newco.marketplace.api.security.SecurityProcess;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.SORescheduleMapper;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;

/**
 * This class is a service class for rescheduling a Service Order.
 * 
 * @author Infosys
 * @version 1.0
 */
public class SORescheduleService implements ServiceConstants {
	private Logger logger = Logger.getLogger(SORescheduleService.class);

	private IServiceOrderBO serviceOrderBO;
	private SORescheduleMapper rescheduleMapper;
	private XStreamUtility conversionUtility;
	private SecurityProcess securityProcess;

	/**
	 * This method is for rescheduling service order.
	 * 
	 * @param soId String
	 * @param rescheduleRequest String
	 * @throws Exception Exception
	 * @return String
	 */
	public String dispatchRescheduleServiceRequest(String soId,
			String rescheduleRequest) throws Exception {
		logger.info("Entering SORescheduleService.dispatchRescheduleServiceRequest()");
		SORescheduleResponse rescheduleResponse = null;
		ProcessResponse processResponse = new ProcessResponse();
		boolean isError = false;
		ServiceOrder serviceOrder = null;
		SORescheduleRequest soRescheduleRequest = conversionUtility
				.getRescheduleRequestObject(rescheduleRequest);
		SecurityContext securityContext = securityProcess.getSecurityContext(
				soRescheduleRequest.getIdentification().getUsername(),
				soRescheduleRequest.getIdentification().getPassWord());
		SORescheduleInfo rescheduleInfo = rescheduleMapper
				.mapServiceOrder(soRescheduleRequest.getSoRescheduleInfo());
		if (rescheduleInfo.getScheduleType().equalsIgnoreCase(
				PublicAPIConstant.DATETYPE_RANGE)
				&& null == rescheduleInfo.getServiceDateTime2()) {
			setErrorMsg(processResponse, CommonUtility
					.getMessage(PublicAPIConstant.ENDDATE_ERROR_CODE));
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
			if (null != serviceOrder) {
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
									CommonUtility
										.getMessage(PublicAPIConstant
														.STARTDATE_ERROR_CODE));
							isError = true;
						}
						if (newStartTimeCombined.compareTo(now) < 0) {
							setErrorMsg(
									processResponse,
									CommonUtility
										.getMessage(PublicAPIConstant
														.STARTDATE_IMPROPER));
							isError = true;
						}
					}
				}

				if (!isError) {
					if (null != serviceOrder) {
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
							setErrorMsg(processResponse,CommonUtility.getMessage(
										PublicAPIConstant.STATUS_ERROR_CODE));
							}
						}
					}
				}
			} else {
				setErrorMsg(processResponse, CommonUtility
						.getMessage(PublicAPIConstant.RETRIEVE_ERROR_CODE));
			}

		}
		rescheduleResponse = rescheduleMapper.rescheduleSOResponseMapping(
				processResponse, serviceOrder);
		if (rescheduleResponse == null) {
			logger.error("rescheduleResponse came as null");
			throw new BusinessServiceException(CommonUtility
					.getMessage(PublicAPIConstant.RESPONSE_ERROR_CODE));
		}
		String stringResponse = conversionUtility
				.getRescheduleResponseXML(rescheduleResponse);
		logger.info("Leaving SORescheduleService.dispatchRescheduleServiceRequest()");
		return stringResponse;

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
		processResponse.setCode(USER_ERROR_RC);
		processResponse.setSubCode(USER_ERROR_RC);
		List<String> arr = new ArrayList<String>();
		arr.add(msg);
		processResponse.setMessages(arr);
		return processResponse;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public void setSecurityProcess(SecurityProcess securityProcess) {
		this.securityProcess = securityProcess;
	}

	public void setConversionUtility(XStreamUtility conversionUtility) {
		this.conversionUtility = conversionUtility;
	}

	public void setRescheduleMapper(SORescheduleMapper rescheduleMapper) {
		this.rescheduleMapper = rescheduleMapper;
	}

}
