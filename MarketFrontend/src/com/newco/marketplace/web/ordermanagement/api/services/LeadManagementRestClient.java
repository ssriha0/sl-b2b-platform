package com.newco.marketplace.web.ordermanagement.api.services;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.leaddetailmanagement.addNotes.LeadAddNoteRequest;
import com.newco.marketplace.api.beans.leaddetailmanagement.addNotes.LeadAddNoteResponse;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.AssignOrReassignProviderRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.AssignOrReassignProviderResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.CancelLeadRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.CancelLeadResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.GetEligibleProviderResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.GetIndividualLeadDetailsResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LeadInfoResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.ScheduleAppointmentRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.ScheduleAppointmentResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.UpdateLeadStatusRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.UpdateLeadStatusResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.CompleteLeadsRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.CompleteLeadsResponse;
import com.servicelive.JerseyRestClient;
import com.sun.jersey.api.client.ClientHandlerException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class LeadManagementRestClient{
   
	private static final Logger LOGGER = Logger.getLogger(LeadManagementRestClient.class);
    private String apiBaseUrl;
    private String oAuthEnabled;
	private String consumerKey;
	private String consumerSecret;
	private static final String FIRM_ID="firmid";
	private static final String STATUS="status";
	private static final String COUNT="count";
	public static final String SL_LEAD_ID="slleadid";
	// private static final String OrderConstants.REQUEST_CONTENT_TYPE = "application/xml";



	
	/**
	 * @param vendorId
	 * @param status
	 * @param count
	 * @return
	 */
	public LeadInfoResponse getResponseForLeadDetails(Integer vendorId,String status,String count) {
		Results results = Results.getSuccess("Success");
		ErrorResult errorResult = new ErrorResult();
		LeadInfoResponse infoResponse=new LeadInfoResponse();
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, apiBaseUrl);
		try{
			if( null!= vendorId){
				jerseyRestClient.setPath("v1.0/slleads/getAllLeadsByFirm");
				jerseyRestClient.addParameter(FIRM_ID,vendorId.toString());
				jerseyRestClient.addParameter(STATUS,status);
				jerseyRestClient.addParameter(COUNT,count);
				String response = jerseyRestClient.get(String.class);
				infoResponse = (LeadInfoResponse) deserializeResponse(response,LeadInfoResponse.class);
			}
		}
		catch(ClientHandlerException clientHandlerException){
			mapError(infoResponse,clientHandlerException.getMessage());
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage());
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			infoResponse.setResults(results);
		}
		return infoResponse;
	}
	
	/**
	 * @param vendorId
	 * @param leadId
	 * @return
	 */
	public GetIndividualLeadDetailsResponse getResponseForIndividualLeadDetails(Integer vendorId,String leadId) {
		Results results = Results.getSuccess("Success");
		ErrorResult errorResult = new ErrorResult();
		GetIndividualLeadDetailsResponse individualLeadDetailsResponse=new GetIndividualLeadDetailsResponse();
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, apiBaseUrl);
		try{
			if( null!= vendorId){
				jerseyRestClient.setPath("v1.0/slleads/getIndividualLeadDetails");
				jerseyRestClient.addParameter(FIRM_ID,vendorId.toString());
				jerseyRestClient.addParameter(SL_LEAD_ID,leadId);
				String response = jerseyRestClient.get(String.class);
				individualLeadDetailsResponse = (GetIndividualLeadDetailsResponse) deserializeResponse(response,GetIndividualLeadDetailsResponse.class);
			}
		}
		catch(ClientHandlerException clientHandlerException){
			mapError(individualLeadDetailsResponse,clientHandlerException.getMessage());
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage());
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			individualLeadDetailsResponse.setResults(results);
		}
		return individualLeadDetailsResponse;
	}
	
	/**
	 * This method is used to Cancel a Lead
	 * @param cancelLeadRequest
	 * @return
	 */
	public CancelLeadResponse cancelLead(CancelLeadRequest cancelLeadRequest) {
		CancelLeadResponse cancelLeadresponse  = new CancelLeadResponse();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, apiBaseUrl);
		try{
			if((null!=cancelLeadRequest)){
				jerseyRestClient.setPath("v1.0/slleads/cancelLead");
				String request = serializeRequest(cancelLeadRequest,CancelLeadRequest.class);
				String response = jerseyRestClient.post(String.class, request);
				cancelLeadresponse = (CancelLeadResponse) deserializeResponse(response,CancelLeadResponse.class);
			}

		}
		catch(ClientHandlerException clientHandlerException){
			mapError(cancelLeadresponse,clientHandlerException.getMessage());
		}
		catch (Exception e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			cancelLeadresponse.setResults(results);
		}

		return cancelLeadresponse;
	}
	
	/**To get eligibile providersList
	 * @param vendorId
	 * @param leadId
	 * @return
	 */
	public GetEligibleProviderResponse getResponseForEligibleProviders(String vendorId,String leadId) {
		Results results = Results.getSuccess("Success");
		ErrorResult errorResult = new ErrorResult();
		GetEligibleProviderResponse eligibleProviderResponse=new GetEligibleProviderResponse();
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, apiBaseUrl);
		try{
			if( null!= vendorId){
				jerseyRestClient.setPath("v1.0/slleads/firmId/"+vendorId);
				jerseyRestClient.setPath("/leadId/"+leadId);
				jerseyRestClient.setPath("/getEligibleProviders");
				String response = jerseyRestClient.get(String.class);
				eligibleProviderResponse = (GetEligibleProviderResponse) deserializeResponse(response,GetEligibleProviderResponse.class);
			}
		}
		catch(ClientHandlerException clientHandlerException){
			mapError(eligibleProviderResponse,clientHandlerException.getMessage());
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage());
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			eligibleProviderResponse.setResults(results);
		}
		return eligibleProviderResponse;
	}
	
	/**
	    * This method is used to Add note to a Lead
	    * @param cancelLeadRequest
	    * @return
	    */
		public LeadAddNoteResponse getResponseForLeadAddNote(LeadAddNoteRequest leadAddNoteRequest) {
			Results results = Results.getSuccess("Success");
			ErrorResult errorResult = new ErrorResult();
			LeadAddNoteResponse leadAddNoteResponse=new LeadAddNoteResponse();
			JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, apiBaseUrl);
			try{
				if( null!= leadAddNoteRequest){
					jerseyRestClient.setPath("v1.0/slleads/addNotes");
					String request = serializeRequest(leadAddNoteRequest,LeadAddNoteRequest.class);
					String response = jerseyRestClient.post(String.class, request);
					leadAddNoteResponse = (LeadAddNoteResponse) deserializeResponse(response,LeadAddNoteResponse.class);
				}
			}
			catch(ClientHandlerException clientHandlerException){
				mapError(leadAddNoteResponse,clientHandlerException.getMessage());
			}
			catch (Exception e) {
				LOGGER.error(e.getMessage());
				errorResult.setMessage(e.getMessage());
				results.addError(errorResult);
				leadAddNoteResponse.setResults(results);
			}
			return leadAddNoteResponse;
		}
		/**To invoke assign/Reassign API
		 * @param assignOrReassignProviderRequest
		 * @return
		 */
		public AssignOrReassignProviderResponse getResponseForAssignOrReassignProvider(AssignOrReassignProviderRequest assignOrReassignProviderRequest) {
			Results results = Results.getSuccess("Success");
			ErrorResult errorResult = new ErrorResult();
			AssignOrReassignProviderResponse assignOrReassignProviderResponse=new AssignOrReassignProviderResponse();
			JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, apiBaseUrl);
			try{
				if( null!= assignOrReassignProviderRequest){
					jerseyRestClient.setPath("v1.0/slleads/assignProvider");
					String request = PublicAPIConstant.XML_VERSION + serializeRequest(assignOrReassignProviderRequest,AssignOrReassignProviderRequest.class);
					String response = jerseyRestClient.post(String.class, request);
					assignOrReassignProviderResponse = (AssignOrReassignProviderResponse) deserializeResponse(response,AssignOrReassignProviderResponse.class);
				}
			}
			catch(ClientHandlerException clientHandlerException){
				mapError(assignOrReassignProviderResponse,clientHandlerException.getMessage());
			}
			catch (Exception e) {
				LOGGER.error(e.getMessage());
				errorResult.setMessage(e.getMessage());
				results.addError(errorResult);
				assignOrReassignProviderResponse.setResults(results);
			}
			return assignOrReassignProviderResponse;
		}
		/**Description:This method will schedule/Reschedule the lead 
		 * @param scheduleRequest
		 * @return ScheduleAppointmentResponse
		 * @throws ClientHandlerException
		 * @throws Exception
		 */
		public ScheduleAppointmentResponse scheduleLead(ScheduleAppointmentRequest scheduleRequest) throws ClientHandlerException,Exception{
			Results results = Results.getSuccess("Success");
			ErrorResult errorResult = new ErrorResult();
			ScheduleAppointmentResponse scheduleResponse=new ScheduleAppointmentResponse();
			JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, apiBaseUrl);
			try{
				if( null!= scheduleRequest){
					jerseyRestClient.setPath("v1.0/slleads/scheduleAppointment");
					String request = PublicAPIConstant.XML_VERSION + serializeRequest(scheduleRequest,ScheduleAppointmentRequest.class);
					String response = jerseyRestClient.post(String.class, request);
					scheduleResponse = (ScheduleAppointmentResponse) deserializeResponse(response,ScheduleAppointmentResponse.class);
					
				}
			}	catch(ClientHandlerException clientHandlerException){
				mapError(scheduleResponse,clientHandlerException.getMessage());
			}
			catch (Exception e) {
				LOGGER.error(e.getMessage());
				errorResult.setMessage(e.getMessage());
				results.addError(errorResult);
				scheduleResponse.setResults(results);
			}
			return scheduleResponse;
			
		}
		public UpdateLeadStatusResponse updateLeadFirmStatus(UpdateLeadStatusRequest updateLeadStatusRequest)throws ClientHandlerException,Exception{
			Results results = Results.getSuccess("Success");
			ErrorResult errorResult = new ErrorResult();
			UpdateLeadStatusResponse updateLeadFirmStatusResponse=new UpdateLeadStatusResponse();
			JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, apiBaseUrl);
			try{
				if( null!= updateLeadStatusRequest){
					jerseyRestClient.setPath("v1.0/slleads/updateLeadStatus");
					String request = PublicAPIConstant.XML_VERSION + serializeRequest(updateLeadStatusRequest,UpdateLeadStatusRequest.class);
					String response = jerseyRestClient.post(String.class, request);
					updateLeadFirmStatusResponse = (UpdateLeadStatusResponse) deserializeResponse(response,UpdateLeadStatusResponse.class);
					
				}
			}	catch(ClientHandlerException clientHandlerException){
				mapError(updateLeadFirmStatusResponse,clientHandlerException.getMessage());
			}
			catch (Exception e) {
				LOGGER.error(e.getMessage());
				errorResult.setMessage(e.getMessage());
				results.addError(errorResult);
				updateLeadFirmStatusResponse.setResults(results);
			}
			return updateLeadFirmStatusResponse;
			
		}
	
	  	/**
	  	 * This method will complete the lead order.
	  	 * @param completeLeadsRequest
	  	 * @return CompleteLeadsResponse
	  	 * @throws ClientHandlerException
	  	 * @throws Exception
	  	 */
	  	public CompleteLeadsResponse completeLead(CompleteLeadsRequest completeLeadsRequest) throws ClientHandlerException,Exception{
	  		Results results = Results.getSuccess("Success");
	  		ErrorResult errorResult = new ErrorResult();
	  		CompleteLeadsResponse completeLeadsResponse=new CompleteLeadsResponse();
	  		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, apiBaseUrl);
	  		try{
	  			if( null!= completeLeadsRequest){
	  				jerseyRestClient.setPath("v1.0/slleads/completeLeads");
	  				String request = PublicAPIConstant.XML_VERSION + serializeRequest(completeLeadsRequest,CompleteLeadsRequest.class);
	  				String response = jerseyRestClient.post(String.class, request);
	  				completeLeadsResponse = (CompleteLeadsResponse) deserializeResponse(response,CompleteLeadsResponse.class);
	  				
	  			}
	  		}	catch(ClientHandlerException clientHandlerException){
	  			mapError(completeLeadsResponse,clientHandlerException.getMessage());
	  		}
	  		catch (Exception e) {
	  			LOGGER.error(e.getMessage());
	  			errorResult.setMessage(e.getMessage());
	  			results.addError(errorResult);
	  			completeLeadsResponse.setResults(results);
	  		}
	  		return completeLeadsResponse;
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
	
	/**
	 * @param classz
	 * @return
	 */
	private XStream getXstream(Class<?> classz) {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		xstream.registerConverter(new DateConverter("yyyy-MM-dd HH:mm:ss",
				new String[0]));
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

	
	
	/**
	 * @param infoResponse
	 * @param errorMessage
	 */
	private void mapError(LeadInfoResponse infoResponse,String errorMessage) {
		LOGGER.error(errorMessage);
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		errorResult.setMessage(PublicAPIConstant.API_TIME_OUT_ERROR);
		results.addError(errorResult);
		infoResponse.setResults(results);		
	}
	/**
	 * @param scheduleResponse
	 * @param errorMessage
	 */
	private void mapError(ScheduleAppointmentResponse scheduleResponse,String errorMessage) {
		LOGGER.error(errorMessage);
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		errorResult.setMessage(PublicAPIConstant.API_TIME_OUT_ERROR);
		results.addError(errorResult);
		scheduleResponse.setResults(results);	
		
	}
	/**
	 * @param updateLeadFirmStatusResponse
	 * @param errorMessage
	 */
	private void mapError(UpdateLeadStatusResponse updateLeadFirmStatusResponse,String errorMessage) {
		LOGGER.error(errorMessage);
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		errorResult.setMessage(PublicAPIConstant.API_TIME_OUT_ERROR);
		results.addError(errorResult);
		updateLeadFirmStatusResponse.setResults(results);	
		
	}
	/**
	 * @param individualLeadDetailsResponse
	 * @param errorMessage
	 */
	private void mapError(GetIndividualLeadDetailsResponse individualLeadDetailsResponse,String errorMessage) {
		LOGGER.error(errorMessage);
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		errorResult.setMessage(PublicAPIConstant.API_TIME_OUT_ERROR);
		results.addError(errorResult);
		individualLeadDetailsResponse.setResults(results);		
	}
	
	/**
	 * @param leadAddNoteResponse
	 * @param errorMessage
	 */
	private void mapError(LeadAddNoteResponse leadAddNoteResponse,String errorMessage) {
		LOGGER.error(errorMessage);
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		errorResult.setMessage(PublicAPIConstant.API_TIME_OUT_ERROR);
		results.addError(errorResult);
		leadAddNoteResponse.setResults(results);		
	}
	
	/**
	 * @param cancelLeadResponse
	 * @param errorMessage
	 */
	private void mapError(CancelLeadResponse cancelLeadResponse,String errorMessage) {
		LOGGER.error(errorMessage);
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		errorResult.setMessage(PublicAPIConstant.API_TIME_OUT_ERROR);
		results.addError(errorResult);
		cancelLeadResponse.setResults(results);		
	}

	/**
	 * @param eligibleProviderResponse
	 * @param errorMessage
	 */
	private void mapError(GetEligibleProviderResponse eligibleProviderResponse,String errorMessage) {
		LOGGER.error(errorMessage);
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		errorResult.setMessage(PublicAPIConstant.API_TIME_OUT_ERROR);
		results.addError(errorResult);
		eligibleProviderResponse.setResults(results);		
	}
	/**
	 * @param assignOrReassignProviderResponse
	 * @param errorMessage
	 */
	private void mapError(AssignOrReassignProviderResponse assignOrReassignProviderResponse,String errorMessage) {
		LOGGER.error(errorMessage);
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		errorResult.setMessage(PublicAPIConstant.API_TIME_OUT_ERROR);
		results.addError(errorResult);
		assignOrReassignProviderResponse.setResults(results);		
	}
	
	
	/**
	 * @param completeLeadsResponse
	 * @param errorMessage
	 */
	private void mapError(CompleteLeadsResponse completeLeadsResponse,String errorMessage) {
		LOGGER.error(errorMessage);
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		errorResult.setMessage(PublicAPIConstant.API_TIME_OUT_ERROR);
		results.addError(errorResult);
		completeLeadsResponse.setResults(results);		
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

	

}
