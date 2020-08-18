package com.newco.marketplace.api.mobile.services;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.newco.calendarPortal.Services.impl.VendorSlCalendarService;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.provider.calendarEvent.GetCalendarProviderEventListResponse;
import com.newco.marketplace.api.mobile.beans.provider.calendarEvent.ProviderCalendarEventDetails;
import com.newco.marketplace.api.mobile.beans.provider.calendarEvent.ProviderCalendarEventList;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.convertors.DateConvertor;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;

import com.newco.marketplace.vo.calendarPortal.CalendarEventVO;





@APIResponseClass(GetCalendarProviderEventListResponse.class)
public class ProviderCalendarEventService extends BaseService{
	private VendorSlCalendarService vendorSlCalendarService;
	private static final Logger logger = Logger.getLogger(ProviderCalendarEventService.class);

	
	/**
	 * This method is for fetching  Calendar Events for provider.
	 * 
	 * @param fromDate
	 *            String,toDate String
	 * @return String
	 */

	public ProviderCalendarEventService() {
		
				super();
	}
	


	private void mapVoToServiceResponse(List<CalendarEventVO> calendarEventVOs, GetCalendarProviderEventListResponse response){
		List<ProviderCalendarEventDetails> providerCalendarEvents = new ArrayList<ProviderCalendarEventDetails>();
		ProviderCalendarEventList providerCalendarEventList = new ProviderCalendarEventList();
		
		for (CalendarEventVO calendarEventVO:calendarEventVOs){
			ProviderCalendarEventDetails  providerCalendarEventDetails = new ProviderCalendarEventDetails();
			
			providerCalendarEventDetails.setPersonId(calendarEventVO.getPersonId());
			
			providerCalendarEventDetails.setEventId(calendarEventVO.getEventId());
			providerCalendarEventDetails.setEventName(calendarEventVO.getEventName());
			providerCalendarEventDetails.setMemberFirstName(calendarEventVO.getMemberFirstName());
			providerCalendarEventDetails.setMemberLastName(calendarEventVO.getMemberLastName());
			providerCalendarEventDetails.setMemberCity(calendarEventVO.getMemberCity());
			providerCalendarEventDetails.setMemberState(calendarEventVO.getMemberState());
			providerCalendarEventDetails.setMemberZip(calendarEventVO.getMemberZip());
			providerCalendarEventDetails.setType(calendarEventVO.getType());
			providerCalendarEventDetails.setStatus(calendarEventVO.getStatus());
			providerCalendarEventDetails.setSource(calendarEventVO.getSource());
			
			Date endDate =calendarEventVO.getEndDate();
			if (endDate!=null){
				providerCalendarEventDetails.setEndDate(DateConvertor.toString(endDate,"yyyy-MM-dd hh:mm:ss",OrderConstants.UTC_ZONE));

				logger.info("End Date"+ providerCalendarEventDetails.getEndDate());
			}
			Date startDate =calendarEventVO.getStartDate();
			if (startDate!=null){
				providerCalendarEventDetails.setStartDate(DateConvertor.toString(startDate,"yyyy-MM-dd hh:mm:ss",OrderConstants.UTC_ZONE));
				
			}
		
			providerCalendarEventDetails.setStartTime(calendarEventVO.getStartTime());
			providerCalendarEventDetails.setEndTime(calendarEventVO.getEndTime());
			
			if (calendarEventVO.getCreatedDate()!=null){
				providerCalendarEventDetails.setCreatedDate(DateConvertor.toString(calendarEventVO.getCreatedDate(),"yyyy-MM-dd hh:mm:ss",OrderConstants.UTC_ZONE));
				
			}
			
			providerCalendarEvents.add(providerCalendarEventDetails);
			
			
		}
		providerCalendarEventList.setProviderCalendarEventDetails(providerCalendarEvents);
		
		response.setProviderCalendarEventList(providerCalendarEventList);
		
	
		
	}

	
	/**
	 * @return the vendorSlCalendarService
	 */
	public VendorSlCalendarService getVendorSlCalendarService() {
		return vendorSlCalendarService;
	}

	/**
	 * @param vendorSlCalendarService the vendorSlCalendarService to set
	 */
	public void setVendorSlCalendarService(
			VendorSlCalendarService vendorSlCalendarService) {
		this.vendorSlCalendarService = vendorSlCalendarService;
	}

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute method");
		List<CalendarEventVO> calendarEventVO = new ArrayList<CalendarEventVO>();
		GetCalendarProviderEventListResponse response = new GetCalendarProviderEventListResponse(); 
		Results results = new Results();
		
		String startDate =apiVO.getRequestParamFromGetDelete("startDate");
		String endDate =apiVO.getRequestParamFromGetDelete("endDate");
		String startTime =apiVO.getRequestParamFromGetDelete("startTime");
		String endTime =apiVO.getRequestParamFromGetDelete("endTime");
		
		if(!isValidDate(startDate)) { 
			logger.error("Error occurred while parsing the startDate");
			setErrorResponse(ResultsCode.FAILURE.getMessage(), ResultsCode.FAILURE.getCode());
			 return response;
		}
		
		if(!isValidDate(endDate)) {
		
		logger.error("Error occurred while parsing the endDate");
		results = Results.getError(ResultsCode.FAILURE.getMessage(), ResultsCode.FAILURE.getCode());
		response.setResults(results);
		return response;
	}
		SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		Date startDateWithFormat = new Date();
		try {
			startDateWithFormat = sdf.parse(startDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Date endDateWithFormat = new Date();
		try {
			endDateWithFormat = sdf.parse(endDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
			
		
		
		logger.info("Provider ID_->" + apiVO.getProviderResourceId() +"StartDate->"+startDate+ "StartTime->"+startTime+ "EndDate->"+endDate+ "EndTime->"+endTime);
		try {
			calendarEventVO = vendorSlCalendarService.getProviderCalenderDetail(apiVO.getProviderResourceId(), startDateWithFormat, endDateWithFormat,startTime, endTime);
		
		System.out.println(calendarEventVO.toString());
		} catch (BusinessServiceException e) {
			logger.error("Error occurred while retrieving Provider Details...Track back there", e);
			return setErrorResponse(ResultsCode.FAILURE.getMessage(), ResultsCode.FAILURE.getCode());
			
			
		}
		mapVoToServiceResponse(calendarEventVO, response);
		
		results = Results.getSuccess();
		 
		if(null != results){
			response.setResults(results);
		}
		logger.info("Leaving execute method");
		return response;
	}
	
	
	private GetCalendarProviderEventListResponse setErrorResponse(String message,String code) {
		GetCalendarProviderEventListResponse response = new GetCalendarProviderEventListResponse();
		Results results = Results.getError(message, code);
		response.setResults(results);		
		return response;
	}
	
	private GetCalendarProviderEventListResponse setSuccessResponse(String message,String code) {
		GetCalendarProviderEventListResponse response = new GetCalendarProviderEventListResponse();
		Results results = Results.getSuccess(message, code);
		response.setResults(results);		
		return response;
	}

	private boolean  isValidDate(String date){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date actualDate = null;
		   try{
			   actualDate = sdf.parse(date);
		   }

		   catch (ParseException e){
			   return false;
		   }
		   if (!sdf.format(actualDate).equals(date)){
			   return false;
		   }

		return true;
	}
	
}
