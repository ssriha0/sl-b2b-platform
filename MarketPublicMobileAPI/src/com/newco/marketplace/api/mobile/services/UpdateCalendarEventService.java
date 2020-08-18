package com.newco.marketplace.api.mobile.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.newco.calendarPortal.Services.impl.VendorSlCalendarService;
import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.provider.calendarEvent.GetCalendarProviderEventListResponse;
import com.newco.marketplace.api.mobile.beans.provider.calendarEvent.ProviderCalendarEventDetails;
import com.newco.marketplace.api.mobile.beans.provider.calendarEvent.UpdateCalendarProviderEventRequest;
import com.newco.marketplace.api.mobile.beans.provider.calendarEvent.UpdateCalendarProviderEventResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.calendarPortal.CalendarEventVO;

@APIRequestClass(UpdateCalendarProviderEventRequest.class)
@APIResponseClass(UpdateCalendarProviderEventResponse.class)
public class UpdateCalendarEventService 
extends BaseService {
	
	public UpdateCalendarEventService(){
		super();
	}

	private VendorSlCalendarService vendorSlCalendarService;
	private static final Logger logger = Logger
			.getLogger(AddCalendarEventService.class);

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		Results results = new Results();
		
		UpdateCalendarProviderEventResponse response = new UpdateCalendarProviderEventResponse();
		Integer eventId = null;
		ProviderCalendarEventDetails providerCalendarEventDetails = new ProviderCalendarEventDetails();
		CalendarEventVO calendarEventVO = new CalendarEventVO();
		
		
		UpdateCalendarProviderEventRequest updateCalendarProviderEventRequest = (UpdateCalendarProviderEventRequest) apiVO
				.getRequestFromPostPut();

		if(null!= updateCalendarProviderEventRequest){
		try {
			 
			// Mapping Request parameters to the VO object
			providerCalendarEventDetails = updateCalendarProviderEventRequest
					.getProviderCalendarEventDetails();

			calendarEventVO = mapRequestVo(providerCalendarEventDetails);
			if(vendorSlCalendarService.providerCalendarUpdate(calendarEventVO)>0){
				  
				return setSuccessResponse();
			 }

		} catch (BusinessServiceException e) {
			logger.error(
					"Error occurred while updating Provider Calendar Events...Track back there",
					e);
			
			setErrorResponse(ResultsCode.FAILURE.getMessage(), ResultsCode.FAILURE.getCode());
			return response;
		}
		} else{
			// Show Error Message
		}
		
		
		logger.info("Leaving execute method");
		return response;
		
		
	
	}
	
	private UpdateCalendarProviderEventResponse setErrorResponse(String message,String code) {
		UpdateCalendarProviderEventResponse response = new UpdateCalendarProviderEventResponse();
		Results results = Results.getError(message, code);
		response.setResults(results);		
		return response;
	}
	
	private UpdateCalendarProviderEventResponse setSuccessResponse() {
		UpdateCalendarProviderEventResponse response = new UpdateCalendarProviderEventResponse();
		Results results = Results.getSuccess(ResultsCode.SUCCESS.getMessage());
		response.setResults(results);		
		return response;
	}

	private CalendarEventVO mapRequestVo(
			
			ProviderCalendarEventDetails providerCalendarEventDetails) {
		SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd");
		CalendarEventVO calendarEventVO = new CalendarEventVO();
		calendarEventVO.setPersonId(providerCalendarEventDetails.getPersonId());
		calendarEventVO.setEventId(providerCalendarEventDetails.getEventId());
		calendarEventVO.setEventName(providerCalendarEventDetails
				.getEventName());
		calendarEventVO.setMemberFirstName(providerCalendarEventDetails
				.getMemberFirstName());
		calendarEventVO.setMemberLastName(providerCalendarEventDetails
				.getMemberLastName());
		calendarEventVO.setMemberCity(providerCalendarEventDetails
				.getMemberCity());
		calendarEventVO.setMemberState(providerCalendarEventDetails
				.getMemberState());
		calendarEventVO.setMemberZip(providerCalendarEventDetails
				.getMemberZip());
		calendarEventVO.setType(providerCalendarEventDetails.getType());
		calendarEventVO.setStatus(providerCalendarEventDetails.getStatus());
		calendarEventVO.setSource(providerCalendarEventDetails.getSource());
		
		
		
		
		if(providerCalendarEventDetails.getEndDate()!=null){
			try {
				calendarEventVO.setEndDate(sdf.parse(providerCalendarEventDetails.getEndDate()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		calendarEventVO.setEndTime(providerCalendarEventDetails.getEndTime());
		if(providerCalendarEventDetails
				.getStartDate()!=null){
			try {
				calendarEventVO.setStartDate(sdf.parse(providerCalendarEventDetails
						.getStartDate()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		calendarEventVO.setStartTime(providerCalendarEventDetails
				.getStartTime());

		
		return calendarEventVO;
	}

	/**
	 * @return the vendorSlCalendarService
	 */
	public VendorSlCalendarService getVendorSlCalendarService() {
		return vendorSlCalendarService;
	}

	/**
	 * @param vendorSlCalendarService
	 *            the vendorSlCalendarService to set
	 */
	public void setVendorSlCalendarService(
			VendorSlCalendarService vendorSlCalendarService) {
		this.vendorSlCalendarService = vendorSlCalendarService;
	}

}