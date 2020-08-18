package com.newco.marketplace.api.mobile.services;

import org.apache.log4j.Logger;

import com.newco.calendarPortal.Services.impl.VendorSlCalendarService;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.mobile.beans.provider.calendarEvent.DeleteCalendarProviderEventResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.exception.core.BusinessServiceException;

@APIResponseClass(DeleteCalendarProviderEventResponse.class)
public class DeleteCalendarEventService extends BaseService {

	public DeleteCalendarEventService() {
		super();

	}

	private VendorSlCalendarService vendorSlCalendarService;
	private static final Logger logger = Logger
			.getLogger(DeleteCalendarEventService.class);

	@Override
	public DeleteCalendarProviderEventResponse execute(APIRequestVO apiVO) {

		DeleteCalendarProviderEventResponse response = new DeleteCalendarProviderEventResponse();
		int deletedEvent = 0;
		try {

			deletedEvent=vendorSlCalendarService.providerCalendarEventDelete(
					apiVO.getProviderResourceId(), apiVO.getEventId());
			
			if(deletedEvent==0){
				logger.error("No Calendar Event "+apiVO.getEventId()+" is associated with provider "+apiVO.getProviderResourceId());
				response =setErrorResponse(ResultsCode.GET_CALENDAR_EVENT_ERROR.getMessage(),"0001");
			}else{
				response = setSuccessResponse();
			}
			

		} catch (BusinessServiceException e) {
			logger.error(
					"Error occurred while deleting Provider Calendar Events...Track back there",
					e);
			return setErrorResponse(ResultsCode.FAILURE.getMessage(),
					ResultsCode.FAILURE.getCode());

		}

		logger.info("Leaving execute method");
		return response;

	}

	private DeleteCalendarProviderEventResponse setErrorResponse(
			String message, String code) {
		DeleteCalendarProviderEventResponse response = new DeleteCalendarProviderEventResponse();
		Results results = Results.getError(message, code);
		response.setResults(results);
		return response;
	}

	private DeleteCalendarProviderEventResponse setSuccessResponse() {
		DeleteCalendarProviderEventResponse response = new DeleteCalendarProviderEventResponse();
		Results results = Results.getSuccess(ResultsCode.SUCCESS.getMessage());
		response.setResults(results);
		return response;
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
