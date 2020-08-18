package com.newco.marketplace.web.ordermanagement.api.services;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.ReassignSORequest;
import com.newco.marketplace.api.beans.so.ReassignSOResponse;
import com.newco.marketplace.api.beans.so.addNote.SOAddNoteResponse;
import com.newco.marketplace.api.beans.so.addNote.v1_1.SOAddNoteRequest;
import com.newco.marketplace.api.beans.so.reschedule.SOProviderRescheduleRequest;
import com.newco.marketplace.api.beans.so.reschedule.SOProviderRescheduleResponse;
import com.newco.marketplace.api.beans.so.retrieve.v1_2.SOGetResponse;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LeadInfoResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.acceptSO.SOAcceptFirmRequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.acceptSO.SOAcceptResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.assignProvider.SOAssignProviderRequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.assignProvider.SOAssignProviderResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.editAppTime.SOEditAppointmentTimeRequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.editAppTime.SOEditAppointmentTimeResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.editSlNotes.SOEditSOLocationNotesRequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.editSlNotes.SOEditSOLocationNotesResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.eligibleproviders.SOGetEligibleProviderResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.FetchSORequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.FetchSOResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistoryResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getTabs.GetTabsResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.reasonCodes.GetReasonCodesResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.rejectSO.RejectSORequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.rejectSO.RejectSOResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.releaseInfo.GetReleaseResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.releaseSO.SOReleaseRequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.releaseSO.SOReleaseResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateFlag.SOPriorityResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateScheduleDetails.UpdateScheduleDetailsRequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateScheduleDetails.UpdateScheduleDetailsResponse;
import com.servicelive.JerseyRestClient;
import com.sun.jersey.api.client.ClientHandlerException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class OrderManagementRestClient{
   
	private static final Logger LOGGER = Logger.getLogger(OrderManagementRestClient.class);
    private String apiBaseUrl;
    private String omBaseUrl;
    private String oAuthEnabled;
	private String consumerKey;
	private String consumerSecret;
    private static final String GROUPED_SO_IND = "1";
    private static final String GROUP_IND_PARAM = "groupInd";
	private static final String INDIVIDUAL_SO_IND = "0";
	private static final String FIRM_ID="firmid";
   // private static final String OrderConstants.REQUEST_CONTENT_TYPE = "application/xml";
   
	
	

	/**
	 * @param fetchSoRequest
	 * @param providerId
	 * @param resourceId
	 * @return
	 */
	public FetchSOResponse getResponseForFetchOrders(FetchSORequest fetchSoRequest,String providerId, String resourceId) {
		FetchSOResponse fetchSOResponse = new FetchSOResponse();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, omBaseUrl);
		try{
				if((null != fetchSoRequest) && (StringUtils.isNotBlank(providerId))
						&& (StringUtils.isNotBlank(resourceId))){
				jerseyRestClient.setPath("v1.0/providers/"+providerId);
				jerseyRestClient.setPath("/resources/"+resourceId);
				jerseyRestClient.setPath("/ordermanagement/serviceorders");
				String request = serializeRequest(fetchSoRequest,FetchSORequest.class);
				String response = jerseyRestClient.put(String.class, request);
				fetchSOResponse = (FetchSOResponse) deserializeResponse(response,FetchSOResponse.class);

			}
				
		}		
		catch(ClientHandlerException clientHandlerException){
			mapError(fetchSOResponse,clientHandlerException.getMessage());
		}			
		catch (Exception e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			fetchSOResponse.setResults(results);
		} 
			
		/*try{
			
			PutMethod putMethod = new PutMethod(omBaseUrl+"v1.0/providers/"+providerId+"/resources/"+resourceId+"/ordermanagement/serviceorders");
			LOGGER.info("Sending request = " + providerId+":"+resourceId);
			String request = serializeRequest(fetchSoRequest,FetchSORequest.class);
			RequestEntity requestEntity = new StringRequestEntity(request, OrderConstants.REQUEST_CONTENT_TYPE, null);
			putMethod.setRequestEntity(requestEntity);
			putMethod.setContentChunked(true);
			
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();

			if (putMethod.getStatusCode() == 200){
				long startTime = System.currentTimeMillis();
				fetchSOResponse = (FetchSOResponse) deserializeResponse(response,FetchSOResponse.class);
				LOGGER.info(String.format("Completed the API call in deserializeResponse:::::: %1$d ms", System.currentTimeMillis() - startTime));
				return fetchSOResponse;
			}else {
				errorResult.setMessage(response);
				results.addError(errorResult);
				fetchSOResponse.setResults(results);
				return fetchSOResponse;
			}
			
		}
		 catch (HttpException e) {
				errorResult.setMessage(e.getMessage());
				results.addError(errorResult);
				fetchSOResponse.setResults(results);
				LOGGER.error(e);
			} catch (IOException e) {
				errorResult.setMessage(e.getMessage());
				results.addError(errorResult);
				fetchSOResponse.setResults(results);		
				LOGGER.error(e);
			}*/


		return fetchSOResponse;
	}

	

	/**
	 * @param firmId
	 * @param soId
	 * @param groupInd 
	 * @return
	 */
	public SOGetEligibleProviderResponse getResponseForGetEligibleProviders(String firmId, String soId, String groupInd) {
		SOGetEligibleProviderResponse eligibleProviderResponse = new SOGetEligibleProviderResponse();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, omBaseUrl);
		try{
			if((StringUtils.isNotBlank(firmId)) && (StringUtils.isNotBlank(soId))){
				jerseyRestClient.setPath("v1.0/providers/"+firmId);
				jerseyRestClient.setPath("/serviceorders/"+soId);
				jerseyRestClient.setPath("/soproviders");
				jerseyRestClient.addParameter(GROUP_IND_PARAM,groupInd);
				String response = jerseyRestClient.get(String.class);
				eligibleProviderResponse = (SOGetEligibleProviderResponse) deserializeResponse(response,SOGetEligibleProviderResponse.class);
			}

		}
		catch(ClientHandlerException clientHandlerException){
			mapError(eligibleProviderResponse,clientHandlerException.getMessage());
		}
		catch (Exception e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			eligibleProviderResponse.setResults(results);
		} 
		/*try {
			GetMethod getMethod = new GetMethod(omBaseUrl+"v1.0/providers/"+firmId+"/serviceorders/"+soId+"/soproviders?"+GROUP_IND_PARAM+"="+groupInd);
			LOGGER.info("Sending request = " + firmId+":"+soId);
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(getMethod);
			String response = getMethod.getResponseBodyAsString();

			if (getMethod.getStatusCode() == 200){
				eligibleProviderResponse = (SOGetEligibleProviderResponse) deserializeResponse(response,SOGetEligibleProviderResponse.class);
				return eligibleProviderResponse;
			}else {
				errorResult.setMessage(response);
				results.addError(errorResult);
				eligibleProviderResponse.setResults(results);
				return eligibleProviderResponse;
			}

		} catch (HttpException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			eligibleProviderResponse.setResults(results);
			LOGGER.error(e);
		} catch (IOException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			eligibleProviderResponse.setResults(results);		
			LOGGER.error(e);
		}*/
		return eligibleProviderResponse;
	}

	/**
	 * @param assignProviderRequest
	 * @param firmId
	 * @param soId
	 * @return
	 */
	public SOAssignProviderResponse getResponseForAssignProvider(SOAssignProviderRequest assignProviderRequest, String firmId, String soId) {
		SOAssignProviderResponse assignProviderResponse = new SOAssignProviderResponse();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, omBaseUrl);
		try{
			if((null!=assignProviderRequest)&&(StringUtils.isNotBlank(firmId)) && (StringUtils.isNotBlank(soId))){
				jerseyRestClient.setPath("v1.0/providers/"+firmId);
				jerseyRestClient.setPath("/serviceorders/"+soId);
				jerseyRestClient.setPath("/assign");
				String request = serializeRequest(assignProviderRequest,SOAssignProviderRequest.class);
				String response = jerseyRestClient.put(String.class, request);
				assignProviderResponse = (SOAssignProviderResponse) deserializeResponse(response,SOAssignProviderResponse.class);
			}

		}
		catch(ClientHandlerException clientHandlerException){
			mapError(assignProviderResponse,clientHandlerException.getMessage());
		}
		catch (Exception e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			assignProviderResponse.setResults(results);
		}
		/*try {
			PutMethod putMethod = new PutMethod(omBaseUrl+"v1.0/providers/"+firmId+"/serviceorders/"+soId+"/assign");
			LOGGER.info("Sending request = " + firmId+":"+soId);
			
			String request = serializeRequest(assignProviderRequest,SOAssignProviderRequest.class);
			RequestEntity requestEntity = new StringRequestEntity(request, OrderConstants.REQUEST_CONTENT_TYPE, null);			
			putMethod.setRequestEntity(requestEntity);
			putMethod.setContentChunked(true);
			
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();

			if (putMethod.getStatusCode() == 200){
				assignProviderResponse = (SOAssignProviderResponse) deserializeResponse(response,SOAssignProviderResponse.class);
				return assignProviderResponse;
			}else {
				errorResult.setMessage(response);
				results.addError(errorResult);
				assignProviderResponse.setResults(results);
				return assignProviderResponse;
			}

		} catch (HttpException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			assignProviderResponse.setResults(results);
			LOGGER.error(e);
		} catch (IOException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			assignProviderResponse.setResults(results);		
			LOGGER.error(e);
		}*/
		return assignProviderResponse;
	}

	/**
	 * @param reAssignProviderRequest
	 * @param firmId
	 * @param soId
	 * @return
	 */
	public ReassignSOResponse getResponseForReAssignProvider(ReassignSORequest reAssignProviderRequest,String firmId, String soId) {
		ReassignSOResponse reAssignProviderResponse  = new ReassignSOResponse();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, apiBaseUrl);
		try{
			if((null!=reAssignProviderRequest)&&(StringUtils.isNotBlank(firmId)) && (StringUtils.isNotBlank(soId))){
				jerseyRestClient.setPath("v1.1/providers/"+firmId);
				jerseyRestClient.setPath("/serviceorders/"+soId);
				jerseyRestClient.setPath("/assignment");
				String request = serializeRequest(reAssignProviderRequest,ReassignSORequest.class);
				String response = jerseyRestClient.put(String.class, request);
				reAssignProviderResponse = (ReassignSOResponse) deserializeResponse(response,ReassignSOResponse.class);
			}

		}
		catch(ClientHandlerException clientHandlerException){
			mapError(reAssignProviderResponse,clientHandlerException.getMessage());
		}
		catch (Exception e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			reAssignProviderResponse.setResults(results);
		}
		/*try {
			PutMethod putMethod = new PutMethod(apiBaseUrl+"v1.1/providers/"+firmId+"/serviceorders/"+soId+"/assignment");
			LOGGER.info("Sending request = " + firmId+":"+soId);
			
			String request = serializeRequest(reAssignProviderRequest,ReassignSORequest.class);
			RequestEntity requestEntity = new StringRequestEntity(request, OrderConstants.REQUEST_CONTENT_TYPE, null);				
			putMethod.setRequestEntity(requestEntity);
			putMethod.setContentChunked(true);
			
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();

			if (putMethod.getStatusCode() == 200){
				reAssignProviderResponse = (ReassignSOResponse) deserializeResponse(response,ReassignSOResponse.class);
				return reAssignProviderResponse;
			}else {
				errorResult.setMessage(response);
				results.addError(errorResult);
				reAssignProviderResponse.setResults(results);
				return reAssignProviderResponse;
			}

		} catch (HttpException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			reAssignProviderResponse.setResults(results);
			LOGGER.error(e);
		} catch (IOException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			reAssignProviderResponse.setResults(results);		
			LOGGER.error(e);
		}*/
		return reAssignProviderResponse;
	}
	
	
	/**
	 * @param acceptToFirmRequest
	 * @param firmId
	 * @param soId
	 * @param groupInd 
	 * @return
	 */
	public SOAcceptResponse getResponseForAcceptOrder(SOAcceptFirmRequest acceptOrderRequest,String firmId, String soId, String groupInd) {
		SOAcceptResponse acceptResponse  = new SOAcceptResponse();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		String groupedSO = "";
		if(StringUtils.isNotBlank(groupInd) && GROUPED_SO_IND.equalsIgnoreCase(groupInd)){
			groupedSO = GROUPED_SO_IND;
		}else{
			groupedSO = INDIVIDUAL_SO_IND;
		}
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, apiBaseUrl);
		try{
			if((null!=acceptOrderRequest)&&(StringUtils.isNotBlank(firmId)) && (StringUtils.isNotBlank(soId))){
				jerseyRestClient.setPath("v1.1/providers/"+firmId);
				jerseyRestClient.setPath("/serviceorders/"+soId);
				jerseyRestClient.setPath("/accept");
				jerseyRestClient.addParameter(GROUP_IND_PARAM,groupedSO);
				String request = serializeRequest(acceptOrderRequest,SOAcceptFirmRequest.class);
				String response = jerseyRestClient.post(String.class, request);
				acceptResponse = (SOAcceptResponse) deserializeResponse(response,SOAcceptResponse.class);
			}

		}
		catch(ClientHandlerException clientHandlerException){
			mapError(acceptResponse,clientHandlerException.getMessage());
		}
		catch (Exception e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			acceptResponse.setResults(results);
		}
		/*try {
			PostMethod postMethod = new PostMethod(apiBaseUrl+"v1.1/providers/"+firmId+"/serviceorders/"+soId+"/accept?"+GROUP_IND_PARAM+"="+groupedSO);
			LOGGER.info("Sending request = " + firmId+":"+soId);
			String request = serializeRequest(acceptOrderRequest,SOAcceptFirmRequest.class);
			RequestEntity requestEntity = new StringRequestEntity(request, OrderConstants.REQUEST_CONTENT_TYPE, null);				
			postMethod.setRequestEntity(requestEntity);
			postMethod.setContentChunked(true);
			
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(postMethod);
			String response = postMethod.getResponseBodyAsString();

			if (postMethod.getStatusCode() == 200){
				acceptResponse = (SOAcceptResponse) deserializeResponse(response,SOAcceptResponse.class);
				return acceptResponse;
			}else {
				errorResult.setMessage(response);
				results.addError(errorResult);
				acceptResponse.setResults(results);
				return acceptResponse;
			}

		} catch (HttpException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			acceptResponse.setResults(results);
			LOGGER.error(e);
		} catch (IOException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			acceptResponse.setResults(results);		
			LOGGER.error(e);
		}*/
		return acceptResponse;
	}

	/**
	 * @param addNoteRequest
	 * @param providerId
	 * @param soId
	 * @return SOAddNoteResponse
	 */
	public SOAddNoteResponse getResponseForAddNotes(SOAddNoteRequest addNoteRequest,String providerId, String soId) {
		SOAddNoteResponse addNoteResponse  = new SOAddNoteResponse();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, apiBaseUrl);
		try{
			if((null!=addNoteRequest)&&(StringUtils.isNotBlank(providerId)) && (StringUtils.isNotBlank(soId))){
				jerseyRestClient.setPath("v1.1//providers/"+providerId);
				jerseyRestClient.setPath("/serviceorders/"+soId);
				jerseyRestClient.setPath("/notes");
				String request = serializeRequest(addNoteRequest,SOAddNoteRequest.class);
				String response = jerseyRestClient.post(String.class, request);
				addNoteResponse = (SOAddNoteResponse) deserializeResponse(response,SOAddNoteResponse.class);
			}

		}
		catch(ClientHandlerException clientHandlerException){
			mapError(addNoteResponse,clientHandlerException.getMessage());
		}
		catch (Exception e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			addNoteResponse.setResults(results);
		}
/*		try {
			PostMethod postMethod = new PostMethod(apiBaseUrl+"v1.1//providers/"+providerId+"/serviceorders/"+soId+"/notes");
			LOGGER.info("Sending request = " + providerId+":"+soId);
			
			String request = serializeRequest(addNoteRequest,SOAddNoteRequest.class);
			RequestEntity requestEntity = new StringRequestEntity(request, OrderConstants.REQUEST_CONTENT_TYPE, null);				
			postMethod.setRequestEntity(requestEntity);
			postMethod.setContentChunked(true);
			
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(postMethod);
			String response = postMethod.getResponseBodyAsString();

			if (postMethod.getStatusCode() == 200){
				addNoteResponse = (SOAddNoteResponse) deserializeResponse(response,SOAddNoteResponse.class);
				return addNoteResponse;
			}else {
				errorResult.setMessage(response);
				results.addError(errorResult);
				addNoteResponse.setResults(results);
				return addNoteResponse;
			}

		} catch (HttpException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			addNoteResponse.setResults(results);
			LOGGER.error(e);
		} catch (IOException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			addNoteResponse.setResults(results);		
			LOGGER.error(e);
		}*/
		return addNoteResponse;
	}

	
	/**
	 * @param editSlNoteRequest
	 * @param providerId
	 * @param soId
	 * @return
	 */
	public SOEditSOLocationNotesResponse getResponseForEditSLNotes(SOEditSOLocationNotesRequest editSlNoteRequest,String providerId, String soId) {
		SOEditSOLocationNotesResponse editSOLocationNotesResponse  = new SOEditSOLocationNotesResponse();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, omBaseUrl);
		try{
			if((null!=editSlNoteRequest)&&(StringUtils.isNotBlank(providerId)) && (StringUtils.isNotBlank(soId))){
				jerseyRestClient.setPath("v1.0/providers/"+providerId);
				jerseyRestClient.setPath("/serviceorders/"+soId);
				jerseyRestClient.setPath("/soLocationNotes");
				String request = serializeRequest(editSlNoteRequest,SOEditSOLocationNotesRequest.class);
				String response = jerseyRestClient.put(String.class, request);
				editSOLocationNotesResponse = (SOEditSOLocationNotesResponse) deserializeResponse(response,SOEditSOLocationNotesResponse.class);
			}

		}
		catch(ClientHandlerException clientHandlerException){
			mapError(editSOLocationNotesResponse,clientHandlerException.getMessage());
		}
		catch (Exception e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			editSOLocationNotesResponse.setResults(results);
		}
		/*try {
			PutMethod putMethod = new PutMethod(omBaseUrl+"v1.0/providers/"+providerId+"/serviceorders/"+soId+"/soLocationNotes");
			LOGGER.info("Sending request = " + providerId+":"+soId);
			
			String request = serializeRequest(editSlNoteRequest,SOEditSOLocationNotesRequest.class);
			RequestEntity requestEntity = new StringRequestEntity(request, OrderConstants.REQUEST_CONTENT_TYPE, null);		
			putMethod.setRequestEntity(requestEntity);
			putMethod.setContentChunked(true);
			
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();

			if (putMethod.getStatusCode() == 200){
				editSOLocationNotesResponse = (SOEditSOLocationNotesResponse) deserializeResponse(response,SOEditSOLocationNotesResponse.class);
				return editSOLocationNotesResponse;
			}else {
				errorResult.setMessage(response);
				results.addError(errorResult);
				editSOLocationNotesResponse.setResults(results);
				return editSOLocationNotesResponse;
			}

		} catch (HttpException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			editSOLocationNotesResponse.setResults(results);
			LOGGER.error(e);
		} catch (IOException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			editSOLocationNotesResponse.setResults(results);		
			LOGGER.error(e);
		}*/
		return editSOLocationNotesResponse;
	}

	/**
	 * @param updateAppointmentTimeRequest
	 * @param providerId
	 * @param soId
	 * @return
	 */
	public SOEditAppointmentTimeResponse getResponseForUpdateAppointmentTime(SOEditAppointmentTimeRequest updateAppointmentTimeRequest,String providerId, String soId) {
		SOEditAppointmentTimeResponse editAppointmentTimeResponse  = new SOEditAppointmentTimeResponse();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, omBaseUrl);
		try{
			if((null!= updateAppointmentTimeRequest)&&(StringUtils.isNotBlank(providerId)) && (StringUtils.isNotBlank(soId))){
				jerseyRestClient.setPath("v1.0/providers/"+providerId);
				jerseyRestClient.setPath("/serviceorders/"+soId);
				jerseyRestClient.setPath("/appointmentTime");
				String request = serializeRequest(updateAppointmentTimeRequest,SOEditAppointmentTimeRequest.class);
				String response = jerseyRestClient.put(String.class, request);
				editAppointmentTimeResponse = (SOEditAppointmentTimeResponse) deserializeResponse(response,SOEditAppointmentTimeResponse.class);
			}

		}
		catch(ClientHandlerException clientHandlerException){
			mapError(editAppointmentTimeResponse,clientHandlerException.getMessage());
		}
		catch (Exception e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			editAppointmentTimeResponse.setResults(results);
		}
		/*try {
			PutMethod putMethod = new PutMethod(omBaseUrl+"v1.0/providers/"+providerId+"/serviceorders/"+soId+"/appointmentTime");
			LOGGER.info("Sending request = " + providerId+":"+soId);
			
			String request = serializeRequest(updateAppointmentTimeRequest,SOEditAppointmentTimeRequest.class);
			RequestEntity requestEntity = new StringRequestEntity(request, OrderConstants.REQUEST_CONTENT_TYPE, null);	
			putMethod.setRequestEntity(requestEntity);
			putMethod.setContentChunked(true);
			
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();

			if (putMethod.getStatusCode() == 200){
				editAppointmentTimeResponse = (SOEditAppointmentTimeResponse) deserializeResponse(response,SOEditAppointmentTimeResponse.class);
				return editAppointmentTimeResponse;
			}else {
				errorResult.setMessage(response);
				results.addError(errorResult);
				editAppointmentTimeResponse.setResults(results);
				return editAppointmentTimeResponse;
			}

		} catch (HttpException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			editAppointmentTimeResponse.setResults(results);
			LOGGER.error(e);
		} catch (IOException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			editAppointmentTimeResponse.setResults(results);		
			LOGGER.error(e);
		}*/
		return editAppointmentTimeResponse;
	}
	
	/**
	 * This method is used to Update schedule details
	 * @param updateAppointmentTimeRequest
	 * @param providerId
	 * @param soId
	 * @return
	 */
	public UpdateScheduleDetailsResponse getResponseForUpdateSheduleDetails(UpdateScheduleDetailsRequest updateScheduleDetailsRequest,String providerId,String soId) {
		UpdateScheduleDetailsResponse updateScheduleDetailsResponse  = new UpdateScheduleDetailsResponse();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, omBaseUrl);
		try{
			if((null!=updateScheduleDetailsRequest)&&(StringUtils.isNotBlank(providerId)) && (StringUtils.isNotBlank(soId))){
				jerseyRestClient.setPath("v1.0/providers/"+providerId);
				jerseyRestClient.setPath("/serviceorders/"+soId);
				jerseyRestClient.setPath("/updateScheduleDetails");
				String request = serializeRequest(updateScheduleDetailsRequest,UpdateScheduleDetailsRequest.class);
				String response = jerseyRestClient.put(String.class, request);
				updateScheduleDetailsResponse = (UpdateScheduleDetailsResponse) deserializeResponse(response,UpdateScheduleDetailsResponse.class);
			}

		}
		catch(ClientHandlerException clientHandlerException){
			mapError(updateScheduleDetailsResponse,clientHandlerException.getMessage());
		}
		catch (Exception e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			updateScheduleDetailsResponse.setResults(results);
		}
		/*try {
			PutMethod putMethod = new PutMethod(omBaseUrl+"v1.0/providers/"+providerId+"/serviceorders/"+soId+"/updateScheduleDetails");
			LOGGER.info("Sending request = " + providerId+":"+soId);
			
			String request = serializeRequest(updateScheduleDetailsRequest,UpdateScheduleDetailsRequest.class);
			RequestEntity requestEntity = new StringRequestEntity(request, OrderConstants.REQUEST_CONTENT_TYPE, null);	
			putMethod.setRequestEntity(requestEntity);
			putMethod.setContentChunked(true);
			
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();

			if (putMethod.getStatusCode() == 200){
				updateScheduleDetailsResponse = (UpdateScheduleDetailsResponse) deserializeResponse(response,UpdateScheduleDetailsResponse.class);
				return updateScheduleDetailsResponse;
			}else {
				errorResult.setMessage(response);
				results.addError(errorResult);
				updateScheduleDetailsResponse.setResults(results);
				return updateScheduleDetailsResponse;
			}

		} catch (HttpException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			updateScheduleDetailsResponse.setResults(results);
			LOGGER.error(e);
		} catch (IOException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			updateScheduleDetailsResponse.setResults(results);		
			LOGGER.error(e);
		}*/
		return updateScheduleDetailsResponse;
	}
	
	/**
	 * This method is used to get all the information to be populated in 
	 * Precall popup
	 * @param providerId
	 * @param soId
	 * @param groupInd 
	 * @return SOGetResponse
	 */
	
	public SOGetResponse getResponseForGetInfoForCall (String providerId, String soId,  String groupInd, String responseFilterParam) {
		SOGetResponse soGetResponse = new SOGetResponse();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		String groupedSO = "";
		if(StringUtils.isNotBlank(groupInd) && GROUPED_SO_IND.equalsIgnoreCase(groupInd)){
			groupedSO = GROUPED_SO_IND;
		}else{
			groupedSO = INDIVIDUAL_SO_IND;
		}
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, apiBaseUrl);
		try{
			if((StringUtils.isNotBlank(providerId)) && (StringUtils.isNotBlank(soId))){
				jerseyRestClient.setPath("v1.1//providers/"+providerId);
				jerseyRestClient.setPath("/serviceorders/"+soId);
				jerseyRestClient.addParameter("responsefilter",responseFilterParam);
				jerseyRestClient.addParameter(GROUP_IND_PARAM,groupedSO);
				String response = jerseyRestClient.get(String.class);
				soGetResponse = (SOGetResponse) deserializeResponse(response,SOGetResponse.class);
			}

		}
		catch(ClientHandlerException clientHandlerException){
			mapError(soGetResponse,clientHandlerException.getMessage());
		}
		catch (Exception e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			soGetResponse.setResults(results);
		}
		/*try{
			GetMethod getMethod = new GetMethod(apiBaseUrl+"v1.1//providers/"+providerId+"/serviceorders/"+soId+"?responsefilter="+responseFilterParam+"&"+GROUP_IND_PARAM+"="+groupedSO);
			LOGGER.info("Sending request = " + providerId+":"+soId);
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(getMethod);
			String response = getMethod.getResponseBodyAsString();

			if (getMethod.getStatusCode() == 200){
				soGetResponse = (SOGetResponse) deserializeResponse(response,SOGetResponse.class);
				return soGetResponse;
			}else {
				errorResult.setMessage(response);
				results.addError(errorResult);
				soGetResponse.setResults(results);
				return soGetResponse;
			}

		} catch (HttpException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			soGetResponse.setResults(results);
			LOGGER.error(e);
		} catch (IOException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			soGetResponse.setResults(results);		
			LOGGER.error(e);
		}*/
		return soGetResponse;
	}
	/**
	 * This method is used to get precall History details 
*/
	/**
	 * @param providerId
	 * @param soId
	 * @param sourceInd
	 * @return PreCallHistoryResponse
	 */
	public PreCallHistoryResponse getResponseForPrecallHistoryDetails (String providerId, String soId, String sourceInd) {

		PreCallHistoryResponse preCallHistoryResponse = new PreCallHistoryResponse();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, omBaseUrl);
		try{
			if((StringUtils.isNotBlank(providerId)) && (StringUtils.isNotBlank(soId))){
				jerseyRestClient.setPath("v1.0/providers/"+providerId);
				jerseyRestClient.setPath("/serviceorders/"+soId);
				jerseyRestClient.setPath("/preCallHistory");
				jerseyRestClient.addParameter("source",sourceInd);
				String response = jerseyRestClient.get(String.class);
				preCallHistoryResponse = (PreCallHistoryResponse) deserializeResponse(response,PreCallHistoryResponse.class);
			}

		}
		catch(ClientHandlerException clientHandlerException){
			mapError(preCallHistoryResponse,clientHandlerException.getMessage());
		}
		catch (Exception e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			preCallHistoryResponse.setResults(results);
		}
		/*try {
			GetMethod getMethod = new GetMethod(omBaseUrl+"v1.0/providers/"+providerId+"/serviceorders/"+soId+"/preCallHistory?source="+sourceInd);
			LOGGER.info("Sending request = " + providerId+":"+soId);
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(getMethod);
			String response = getMethod.getResponseBodyAsString();

			if (getMethod.getStatusCode() == 200){
				preCallHistoryResponse = (PreCallHistoryResponse) deserializeResponse(response,PreCallHistoryResponse.class);
				return preCallHistoryResponse;
			}else {
				errorResult.setMessage(response);
				results.addError(errorResult);
				preCallHistoryResponse.setResults(results);
				return preCallHistoryResponse;
			}

		} catch (HttpException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			preCallHistoryResponse.setResults(results);
			LOGGER.error(e);
		} catch (IOException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			preCallHistoryResponse.setResults(results);		
			LOGGER.error(e);
		}*/
		return preCallHistoryResponse;
	}
	
	/*
	/**method to update the follow up flag
	 * @param providerId
	 * @param soId
	 * @return
	 */
	/**
	 * @param providerId
	 * @param soId
	 * @param groupInd
	 * @return
	 */
	public SOPriorityResponse getResponseForflagUpdate (String providerId, String soId, String groupInd) {
		SOPriorityResponse priorityResponse  = new SOPriorityResponse();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, omBaseUrl);
		try{
			if((StringUtils.isNotBlank(providerId)) && (StringUtils.isNotBlank(soId))){
				jerseyRestClient.setPath("v1.0/providers/"+providerId);
				jerseyRestClient.setPath("/serviceorders/"+soId);
				jerseyRestClient.setPath("/flag");
				jerseyRestClient.addParameter(GROUP_IND_PARAM,groupInd);
				String response = jerseyRestClient.get(String.class);
				priorityResponse = (SOPriorityResponse) deserializeResponse(response,SOPriorityResponse.class);
			}

		}
		catch(ClientHandlerException clientHandlerException){
			mapError(priorityResponse,clientHandlerException.getMessage());
		}
		catch (Exception e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			priorityResponse.setResults(results);
		}
		/*try {
			GetMethod getMethod = new GetMethod(omBaseUrl+"v1.0/providers/"+providerId+"/serviceorders/"+soId+"/flag?"+GROUP_IND_PARAM+"="+groupInd);
			LOGGER.info("Sending request = " + providerId+":"+soId);
			
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(getMethod);
			String response = getMethod.getResponseBodyAsString();

			if (getMethod.getStatusCode() == 200){
				priorityResponse = (SOPriorityResponse) deserializeResponse(response,SOPriorityResponse.class);
				return priorityResponse;
			}else {
				errorResult.setMessage(response);
				results.addError(errorResult);
				priorityResponse.setResults(results);
				return priorityResponse;
			}

		} catch (HttpException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			priorityResponse.setResults(results);
			LOGGER.error(e);
		} catch (IOException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			priorityResponse.setResults(results);		
			LOGGER.error(e);
		}*/
		return priorityResponse;
	}

	/**
	 * @param providerCallRequest
	 * @param providerId
	 * @param soId
	 * @return
	 *//*
	public SOProviderCallResponse getResponseForCall(SOProviderCallRequest providerCallRequest ,String providerId, String soId) {
		SOProviderCallResponse soProviderCallResponse  = new SOProviderCallResponse();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, omBaseUrl);
		try{
			if((null!=providerCallRequest)&&(StringUtils.isNotBlank(providerId)) && (StringUtils.isNotBlank(soId))){
				jerseyRestClient.setPath("v1.0/providers/"+providerId);
				jerseyRestClient.setPath("/serviceorders/"+soId);
				jerseyRestClient.setPath("/preCall");
				String request = serializeRequest(providerCallRequest,SOProviderCallRequest.class);
				String response = jerseyRestClient.put(String.class, request);
				soProviderCallResponse = (SOProviderCallResponse) deserializeResponse(response,SOProviderCallResponse.class);
			}

		}
		catch (Exception e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			soProviderCallResponse.setResults(results);
		}
		try {
			PutMethod putMethod = new PutMethod(omBaseUrl+"v1.0/providers/"+providerId+"/serviceorders/"+soId+"/preCall");
			LOGGER.info("Sending request = " + providerId+":"+soId);
			
			String request = serializeRequest(providerCallRequest,SOProviderCallRequest.class);
			RequestEntity requestEntity = new StringRequestEntity(request, OrderConstants.REQUEST_CONTENT_TYPE, null);	
			putMethod.setRequestEntity(requestEntity);
			putMethod.setContentChunked(true);
			
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();

			if (putMethod.getStatusCode() == 200){
				soProviderCallResponse = (SOProviderCallResponse) deserializeResponse(response,SOProviderCallResponse.class);
				return soProviderCallResponse;
			}else {
				errorResult.setMessage(response);
				results.addError(errorResult);
				soProviderCallResponse.setResults(results);
				return soProviderCallResponse;
			}

		} catch (HttpException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			soProviderCallResponse.setResults(results);
			LOGGER.error(e);
		} catch (IOException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			soProviderCallResponse.setResults(results);		
			LOGGER.error(e);
		}
		return soProviderCallResponse;
	}*/
	
	/**
	 * @param reasonType
	 * @return
	 */
	public GetReasonCodesResponse getResponseForReasonCodes(String reasonType) {
		GetReasonCodesResponse  reasonCodesResponse  = new GetReasonCodesResponse();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, omBaseUrl);
		try{
			if(StringUtils.isNotBlank(reasonType)){
				jerseyRestClient.setPath("v1.0/providers/reasoncodes/"+reasonType);
				String response = jerseyRestClient.get(String.class);
				reasonCodesResponse = (GetReasonCodesResponse) deserializeResponse(response,GetReasonCodesResponse.class);
			}

		}
		catch(ClientHandlerException clientHandlerException){
			mapError(reasonCodesResponse,clientHandlerException.getMessage());
		}
		catch (Exception e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			reasonCodesResponse.setResults(results);
		}
		/*try {
			GetMethod getMethod = new GetMethod(omBaseUrl+"v1.0/providers/reasoncodes/"+reasonType);
			LOGGER.info("Sending request = " +reasonType);
			
			
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(getMethod);
			String response = getMethod.getResponseBodyAsString();

			if (getMethod.getStatusCode() == 200){
				reasonCodesResponse = (GetReasonCodesResponse) deserializeResponse(response,GetReasonCodesResponse.class);
				return reasonCodesResponse;
			}else {
				errorResult.setMessage(response);
				results.addError(errorResult);
				reasonCodesResponse.setResults(results);
				return reasonCodesResponse;
			}

		} catch (HttpException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			reasonCodesResponse.setResults(results);
			LOGGER.error(e);
		} catch (IOException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			reasonCodesResponse.setResults(results);		
			LOGGER.error(e);
		}*/
		return reasonCodesResponse;
	}
	
	/**
	 * @param rejectRequest
	 * @param providerId
	 * @param soId
	 * @return
	 */
	public SOReleaseResponse getResponseForRejectSO(SOReleaseRequest rejectRequest,String providerId, String soId) {
		SOReleaseResponse  rejectResponse  = new SOReleaseResponse();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, apiBaseUrl);
		try{
			if((null!=rejectRequest)&&(StringUtils.isNotBlank(providerId)) && (StringUtils.isNotBlank(soId))){
				jerseyRestClient.setPath("/providers/"+providerId);
				jerseyRestClient.setPath("/serviceorders/"+soId);
				String request = serializeRequest(rejectRequest,SOReleaseRequest.class);
				String response = jerseyRestClient.put(String.class, request);
				rejectResponse = (SOReleaseResponse) deserializeResponse(response,SOReleaseResponse.class);
			}

		}
		catch(ClientHandlerException clientHandlerException){
			mapError(rejectResponse,clientHandlerException.getMessage());
		}
		catch (Exception e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			rejectResponse.setResults(results);
		}
		/*try {
			PutMethod putMethod = new PutMethod(apiBaseUrl+"/providers/"+providerId+"/serviceorders/"+soId);
			LOGGER.info("Sending request = " + providerId+":"+soId);
			
			String request = serializeRequest(rejectRequest,SOReleaseRequest.class);
			RequestEntity requestEntity = new StringRequestEntity(request, OrderConstants.REQUEST_CONTENT_TYPE, null);	
			putMethod.setRequestEntity(requestEntity);
			putMethod.setContentChunked(true);
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();

			if (putMethod.getStatusCode() == 200){
				rejectResponse = (SOReleaseResponse) deserializeResponse(response,GetTabsResponse.class);
				return rejectResponse;
			}else {
				errorResult.setMessage(response);
				results.addError(errorResult);
				rejectResponse.setResults(results);
				return rejectResponse;
			}

		} catch (HttpException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			rejectResponse.setResults(results);
			LOGGER.error(e);
		} catch (IOException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			rejectResponse.setResults(results);		
			LOGGER.error(e);
		}*/
		return rejectResponse;
	}
	
	/**
	 * @param providerId
	 * @param resourceId
	 * @return
	 */
	public GetTabsResponse getResponseForTablist(String providerId, String resourceId) {
		GetTabsResponse  getTabsResponse  = new GetTabsResponse();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, omBaseUrl);
		try {
			if(StringUtils.isNotBlank(providerId)&&
					StringUtils.isNotBlank(resourceId)){
				jerseyRestClient.setPath("v1.0/providers/" + providerId);
				jerseyRestClient.setPath("/resources/" + resourceId);
				jerseyRestClient.setPath("/tabs");
				String response = jerseyRestClient.get(String.class);
				getTabsResponse = (GetTabsResponse)deserializeResponse(response,GetTabsResponse.class);
			} 
		}
		catch(ClientHandlerException clientHandlerException){
			mapError(getTabsResponse,clientHandlerException.getMessage());
		}
			catch (Exception e) {
				errorResult.setMessage(e.getMessage());
				results.addError(errorResult);
			  //priceHistoryResponse.setResults(results);
				e.printStackTrace();
			}
/*		try{
			GetMethod getMethod = new GetMethod(omBaseUrl+"v1.0/providers/"+providerId+"/resources/"+resourceId+"/tabs");
			LOGGER.info("Sending request = " +providerId+":"+resourceId);
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(getMethod);
			String response = getMethod.getResponseBodyAsString();

			if (getMethod.getStatusCode() == 200){
				getTabsResponse = (GetTabsResponse) deserializeResponse(response,GetTabsResponse.class);
				return getTabsResponse;
			}else {
				errorResult.setMessage(response);
				results.addError(errorResult);
				getTabsResponse.setResults(results);
				return getTabsResponse;
			}

		} catch (HttpException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			getTabsResponse.setResults(results);
			LOGGER.error(e);
		} catch (IOException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			getTabsResponse.setResults(results);		
			LOGGER.error(e);
		}*/
		return getTabsResponse;
	}
	
	/**
	 * @param providerId
	 * @param resourceId
	 * @param soId
	 * @return
	 */
	public GetReleaseResponse getResponseForGetInfoForRelease (String providerId, String resourceId,String soId) {
		GetReleaseResponse  releaseResponse  = new GetReleaseResponse();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, omBaseUrl);
		try {
			if(StringUtils.isNotBlank(providerId)&&
					StringUtils.isNotBlank(resourceId)&& StringUtils.isNotBlank(soId)){
				jerseyRestClient.setPath("v1.0/providers/" + providerId);
				jerseyRestClient.setPath("/resources/" + resourceId);
				jerseyRestClient.setPath("/serviceorders/" + soId);
				jerseyRestClient.setPath("/releaseInfo");
				String response = jerseyRestClient.get(String.class);
				releaseResponse = (GetReleaseResponse)deserializeResponse(response,GetReleaseResponse.class);
			} 
		}
		catch(ClientHandlerException clientHandlerException){
			mapError(releaseResponse,clientHandlerException.getMessage());
		}
			catch (Exception e) {
				errorResult.setMessage(e.getMessage());
				results.addError(errorResult);
			  //priceHistoryResponse.setResults(results);
				e.printStackTrace();
			}
		/*try {
			GetMethod getMethod = new GetMethod(omBaseUrl+"v1.0/providers/"+providerId+"/resources/"+resourceId+"/serviceorders/"+soId+"/releaseInfo");
			LOGGER.info("Sending request = " +providerId+":"+resourceId);
			
			
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(getMethod);
			String response = getMethod.getResponseBodyAsString();

			if (getMethod.getStatusCode() == 200){
				releaseResponse = (GetReleaseResponse) deserializeResponse(response,GetReleaseResponse.class);
				return releaseResponse;
			}else {
				errorResult.setMessage(response);
				results.addError(errorResult);
				releaseResponse.setResults(results);
				return releaseResponse;
			}

		} catch (HttpException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			releaseResponse.setResults(results);
			LOGGER.error(e);
		} catch (IOException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			releaseResponse.setResults(results);		
			LOGGER.error(e);
		}*/
		return releaseResponse;
	}
	
	/**
	 * @param releaseRequest
	 * @param providerId
	 * @param resourceId
	 * @param soId
	 * @return
	 */
	public GetReleaseResponse getResponseForRelease(SOReleaseRequest releaseRequest,String providerId, String resourceId,String soId) {
		GetReleaseResponse  releaseResponse  = new GetReleaseResponse();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, omBaseUrl);
		try{
			if((null!=releaseRequest)&&(StringUtils.isNotBlank(providerId)) && (StringUtils.isNotBlank(soId))){
				jerseyRestClient.setPath("v1.0/providers/"+providerId);
				jerseyRestClient.setPath("/serviceorders/"+soId);
				jerseyRestClient.setPath("/release");
				String request = serializeRequest(releaseRequest,SOReleaseRequest.class);
				String response = jerseyRestClient.put(String.class, request);
				releaseResponse = (GetReleaseResponse) deserializeResponse(response,GetReleaseResponse.class);
			}

		}
		catch(ClientHandlerException clientHandlerException){
			mapError(releaseResponse,clientHandlerException.getMessage());
		}
		catch (Exception e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			releaseResponse.setResults(results);
		}
		/*try {
			PutMethod putMethod = new PutMethod(omBaseUrl+"v1.0/providers/"+providerId+"/providerresources/"+resourceId+"/serviceorders/"+soId+"/release");
			LOGGER.info("Sending request = " + providerId+":"+soId);
			
			String request = serializeRequest(releaseRequest,SOReleaseRequest.class);
			RequestEntity requestEntity = new StringRequestEntity(request, OrderConstants.REQUEST_CONTENT_TYPE, null);	
			putMethod.setRequestEntity(requestEntity);
			putMethod.setContentChunked(true);			
			
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();

			if (putMethod.getStatusCode() == 200){
				releaseResponse = (GetReleaseResponse) deserializeResponse(response,GetReleaseResponse.class);
				return releaseResponse;
			}else {
				errorResult.setMessage(response);
				results.addError(errorResult);
				releaseResponse.setResults(results);
				return releaseResponse;
			}

		} catch (HttpException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			releaseResponse.setResults(results);
			LOGGER.error(e);
		} catch (IOException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			releaseResponse.setResults(results);		
			LOGGER.error(e);
		}*/
		return releaseResponse;
	}
	
	/**
	 * @param releaseRequest
	 * @param providerId
	 * @param resourceId
	 * @param soId
	 * @return
	 *//*
	public CounterOfferResponse getResponseForCounterOffer(CreateCounterOfferRequest counterOfferRequest,String providerId, String soId) {
		CounterOfferResponse  counterOfferResponse  = new CounterOfferResponse();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, apiBaseUrl);
		try{
			if((null!=counterOfferRequest)&&(StringUtils.isNotBlank(providerId)) && (StringUtils.isNotBlank(soId))){
				jerseyRestClient.setPath("v1.1//providers/"+providerId);
				jerseyRestClient.setPath("/serviceorders/"+soId);
				jerseyRestClient.setPath("/offers");
				String request = serializeRequest(counterOfferRequest,CreateCounterOfferRequest.class);
				String response = jerseyRestClient.post(String.class, request);
				counterOfferResponse = (CounterOfferResponse) deserializeResponse(response,CounterOfferResponse.class);
			}

		}
		catch (Exception e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			counterOfferResponse.setResults(results);
		}
		try {
			PostMethod postMethod = new PostMethod(apiBaseUrl+"v1.1//providers/"+providerId+"/serviceorders/"+soId+"/offers");
			LOGGER.info("Sending request = " + providerId+":"+soId);
			
			String request = serializeRequest(counterOfferRequest,CreateCounterOfferRequest.class);
			RequestEntity requestEntity = new StringRequestEntity(request, OrderConstants.REQUEST_CONTENT_TYPE, null);	
			postMethod.setRequestEntity(requestEntity);
			postMethod.setContentChunked(true);			
			
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(postMethod);
			String response = postMethod.getResponseBodyAsString();

			if (postMethod.getStatusCode() == 200){
				counterOfferResponse = (CounterOfferResponse) deserializeResponse(response,CounterOfferResponse.class);
				return releaseResponse;
			}else {
				errorResult.setMessage(response);
				results.addError(errorResult);
				counterOfferResponse.setResults(results);
				return releaseResponse;
			}

		} catch (HttpException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			counterOfferResponse.setResults(results);
			LOGGER.error(e);
		} catch (IOException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			counterOfferResponse.setResults(results);		
			LOGGER.error(e);
		}
		return counterOfferResponse;
	}
	*/
	/**
	 * @param releaseRequest
	 * @param providerId
	 * @param resourceId
	 * @param soId
	 * @return
	 */
	public SOProviderRescheduleResponse getResponseForReschedule(SOProviderRescheduleRequest rescheduleRequest,String providerId, String resourceId,String soId) {
		SOProviderRescheduleResponse  rescheduleResponse  = new SOProviderRescheduleResponse();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, apiBaseUrl);
		try{
			if((null!=rescheduleRequest)&&(StringUtils.isNotBlank(providerId)) && (StringUtils.isNotBlank(soId))){
				jerseyRestClient.setPath("v1.1/providers/"+providerId);
				jerseyRestClient.setPath("/serviceorders/"+soId);
				jerseyRestClient.setPath("/reschedulerequest");
				String request = serializeRequest(rescheduleRequest,SOProviderRescheduleRequest.class);
				String response = jerseyRestClient.post(String.class, request);
				rescheduleResponse = (SOProviderRescheduleResponse) deserializeResponse(response,SOProviderRescheduleResponse.class);
			}

		}
		catch(ClientHandlerException clientHandlerException){
			mapError(rescheduleResponse,clientHandlerException.getMessage());
		}
		catch (Exception e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			rescheduleResponse.setResults(results);
		}
		/*try {
			PostMethod postMethod = new PostMethod(apiBaseUrl+"v1.1/providers/"+providerId+"/serviceorders/"+soId+"/reschedulerequest");
			LOGGER.info("Sending request = " + providerId+":"+soId);
			String request = serializeRequest(rescheduleRequest,SOProviderRescheduleRequest.class);
			RequestEntity requestEntity = new StringRequestEntity(request, OrderConstants.REQUEST_CONTENT_TYPE, null);	
			postMethod.setRequestEntity(requestEntity);
			postMethod.setContentChunked(true);			
			
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(postMethod);
			String response = postMethod.getResponseBodyAsString();

			if (postMethod.getStatusCode() == 200){
				rescheduleResponse = (SOProviderRescheduleResponse) deserializeResponse(response,SOProviderRescheduleResponse.class);
				return rescheduleResponse;
			}else {
				errorResult.setMessage(response);
				results.addError(errorResult);
				rescheduleResponse.setResults(results);
				return rescheduleResponse;
			}

		} catch (HttpException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			rescheduleResponse.setResults(results);
			LOGGER.error(e);
		} catch (IOException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			rescheduleResponse.setResults(results);		
			LOGGER.error(e);
		}*/
		return rescheduleResponse;
	}
	
	
	public RejectSOResponse getResponseForRejectSO(RejectSORequest rejectSORequest,
			String resourceId, String soId, String groupInd) {
		RejectSOResponse rejectSOResponse = new RejectSOResponse();
		Results results = Results.getSuccess("Success");
		ErrorResult errorResult = new ErrorResult();
		if(StringUtils.isNotBlank(groupInd) && GROUPED_SO_IND.equalsIgnoreCase(groupInd)){
			groupInd = GROUPED_SO_IND;
		}else{
			groupInd = INDIVIDUAL_SO_IND;
		}
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, apiBaseUrl);
		try{
			if((null!=rejectSORequest)&&(StringUtils.isNotBlank(resourceId)) && (StringUtils.isNotBlank(soId))){
				jerseyRestClient.setPath("/buyerresources/serviceorders/"+soId);
				jerseyRestClient.setPath("/reject/"+resourceId);
				jerseyRestClient.addParameter(GROUP_IND_PARAM, groupInd);
				String request = serializeRequest(rejectSORequest,RejectSORequest.class);
				String response = jerseyRestClient.post(String.class, request);
				rejectSOResponse = (RejectSOResponse) deserializeResponse(response,RejectSOResponse.class);
			}

		}
		catch(ClientHandlerException clientHandlerException){
			mapError(rejectSOResponse,clientHandlerException.getMessage());
		}
		catch (Exception e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			rejectSOResponse.setResults(results);
		}
		
		/*PostMethod postMethod = new PostMethod(apiBaseUrl+"/buyerresources/serviceorders/"+soId+"/reject/"+resourceId+"?"+GROUP_IND_PARAM+"="+groupInd);
		LOGGER.info("Sending request = " + resourceId+":"+soId);
		String request = serializeRequest(rejectSORequest,RejectSORequest.class);
		RequestEntity requestEntity;
		HttpClient httpclient;
		String response = "";
		try {
			requestEntity = new StringRequestEntity(request, OrderConstants.REQUEST_CONTENT_TYPE, null);
			postMethod.setRequestEntity(requestEntity);
			postMethod.setContentChunked(true);			
			httpclient = new HttpClient();
			httpclient.executeMethod(postMethod);
			response = postMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
		} catch (UnsupportedEncodingException e1) {
			errorResult.setMessage(e1.getMessage());
			results.addError(errorResult);
		} catch (IOException e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
		}	
		if (postMethod.getStatusCode() == 200){
			rejectSOResponse = (RejectSOResponse) deserializeResponse(response,RejectSOResponse.class);
			return rejectSOResponse;
		}else {
			errorResult.setMessage(response);
			results.addError(errorResult);
		}
		rejectSOResponse.setResults(results);*/
		return rejectSOResponse;
	}

	/**
	 * @param request
	 * @param classz
	 * @return
	 */
	private String serializeRequest(Object request, Class<?> classz){
		XStream xstream = this.getXstream(classz);		
		//String requestXml  = xstream.toXML(request).toString();
		return xstream.toXML(request).toString();
		/*try {
			OutputStream outputStream = new ByteArrayOutputStream();
		    JAXBContext ctx = JAXBContext.newInstance(classz);
		    Marshaller marshaller = ctx.createMarshaller();
		    marshaller.marshal(request, outputStream);
		    return outputStream.toString();
		}
		catch (JAXBException e) {
		    LOGGER.error(e);
		}
		return null;*/
	}
	
	
	/**
	 * @param objectXml
	 * @param c
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	private Object deserializeResponse( String objectXml , Class<?> clazz) {
		//return this.<IAPIResponse>deserializeRequest(objectXml,c);
		Object obj = new Object();
		try {		
			XStream xstream = getXstream(clazz);
			obj = (Object) xstream.fromXML(objectXml);
			LOGGER.info("Exiting deserializeResponse()");		
					
		} catch (Exception e) {
			LOGGER.error(e);
		}
		return obj;
	}
	
	private XStream getXstream(Class<?> classz) {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		xstream.registerConverter(new DateConverter("yyyy-MM-dd", new String[0]));
		xstream.addDefaultImplementation(java.sql.Date.class, java.util.Date.class);
		xstream.processAnnotations(classz);
		return xstream;
	}
	
	/**
	 * @param objectXml
	 * @param c
	 * @return
	 */
	@SuppressWarnings({"unchecked", "rawtypes" })
	private <T> T deserializeRequest(String objectXml, Class c){
		try {
			InputStream is = new ByteArrayInputStream(objectXml.getBytes("UTF-8"));
		    JAXBContext ctx = JAXBContext.newInstance(c);
		    Unmarshaller unmarshaller = ctx.createUnmarshaller();
		    return (T) unmarshaller.unmarshal(is); 
		}
		catch (JAXBException e) {
		    LOGGER.error(e);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e);
		}
		return null;
	}

	public String getOmBaseUrl() {
		return omBaseUrl;
	}

	public void setOmBaseUrl(String omBaseUrl) {
		this.omBaseUrl = omBaseUrl;
	}
	public String getApiBaseUrl() {
		return apiBaseUrl;
	}

	public void setApiBaseUrl(String apiBaseUrl) {
		this.apiBaseUrl = apiBaseUrl;
	}

	public String getoAuthEnabled() {
		return oAuthEnabled;
	}

	public void setoAuthEnabled(String oAuthEnabled) {
		this.oAuthEnabled = oAuthEnabled;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}
	
	private void mapError(IAPIResponse response,
			String errorMessage) {
		LOGGER.error(errorMessage);
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		errorResult.setMessage(PublicAPIConstant.API_TIME_OUT_ERROR);
		results.addError(errorResult);
		response.setResults(results);		
	}



	
}
