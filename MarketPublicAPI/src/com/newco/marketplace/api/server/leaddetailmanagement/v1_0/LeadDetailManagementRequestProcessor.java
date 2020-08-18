package com.newco.marketplace.api.server.leaddetailmanagement.v1_0;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.services.BaseService.RequestType;
import com.newco.marketplace.api.services.leadsdetailmanagement.AddNotesForLeadService;
import com.newco.marketplace.api.services.leadsdetailmanagement.GetAllLeadService;
import com.newco.marketplace.api.services.leadsdetailmanagement.GetIndividualLeadDetailsService;
import com.newco.marketplace.api.services.leadsdetailmanagement.LeadFirmDetailService;
import com.newco.marketplace.api.services.leadsdetailmanagement.UpdateLeadStatusService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.GetIndividualLeadDetailsResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LeadDetailsResponse;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;
/**
 * This class handles all the Lead management related requests. This is the
 * resource class for all the Leads related exposed webservices
 * 
 * @author Infosys
 * @version 1.0
 */

// Lead Management Request Processor class
@Path("v1.0")
public class LeadDetailManagementRequestProcessor {
	private Logger logger = Logger.getLogger(LeadDetailManagementRequestProcessor.class);

	// Below are all the services
	// service to get lead details
	protected GetIndividualLeadDetailsService getIndividualLeadDetailsService;
	// service to get ALL lead details
	protected GetAllLeadService getAllLeadService;
	//service to update status of lead and provided firm
	protected UpdateLeadStatusService updateLeadStatusService;
	
	protected AddNotesForLeadService addNotesForLeadService ;
    
	/**service class to fetch lead details for the buyer*/
	protected LeadFirmDetailService leadFirmDetailService;
	
	@Resource
	protected HttpServletRequest httpRequest;

	// Web Service API for get Individual Lead Details

	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	@GET
	@Path("/slleads/getIndividualLeadDetails")
	public GetIndividualLeadDetailsResponse getIndividualLeadDetails() {
		
		if (logger.isInfoEnabled()) {
			logger.info("Entering LeadManagementDetailRequestProcessor getIndividualLeadDetails()");
		}
		Map<String, String> reqMap = CommonUtility
				.getRequestMap(getHttpRequest().getParameterMap());
		return getIndividualLeadDetailsService.execute(reqMap); 
	}

	
	@GET
	@Path("/slleads/getAllLeadsByFirm/")
	public String getAllLeadsByFirm() {
		if (logger.isInfoEnabled()) {
			logger.info("Entering LeadManagementDetailRequestProcessor getAllLeadsByFirm()");
		}
		Map<String, String> reqMap = CommonUtility
				.getRequestMap(getHttpRequest().getParameterMap());
		return getAllLeadService.execute(reqMap);
	}
	
	
	@POST
	@Path("/slleads/updateLeadStatus")
	public Response updateLeadStatus(String request) {
		
		if (logger.isInfoEnabled()) {
			logger.info("Entering LeadManagementDetailRequestProcessor updateLeadStatus()");
		}
		String updateLeadStatusResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(request);
		apiVO.setRequestType(RequestType.Post);
		updateLeadStatusResponse = updateLeadStatusService.doSubmit(apiVO);
		updateLeadStatusResponse = PublicAPIConstant.XML_VERSION + updateLeadStatusResponse;
		if (logger.isInfoEnabled())
			logger.info("Leaving LeadDetailManagementRequestProcessor.updateLeadStatus()");
		return Response.ok(updateLeadStatusResponse).build();
	}

	
	/**
	 * This method returns a response for an Add Notes to service order request.
	 * 
	 * @param String addNoteRequest
	 * @return Response
	 */
	@POST
	@Path("/slleads/addNotes")
	public Response getResponseForAddNote(String addNoteRequest) {
		if (logger.isInfoEnabled())
			logger.info("Entering SORequestProcessor.getResponseForAddNote()");
		String addNoteResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(addNoteRequest);
		apiVO.setRequestType(RequestType.Post);
		addNoteResponse = addNotesForLeadService.doSubmit(apiVO);
		if (logger.isInfoEnabled())
			logger.info("Leaving LeadDetailManagementRequestProcessor.getResponseForAddNote()");
		return Response.ok(addNoteResponse).build();
	}
	
	
	@GET
	@Path("/buyers/{buyer_id}/leadId/{leadId}/getLeadDetails")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public LeadDetailsResponse getleadFirmDetails(@PathParam("buyer_id")
	String buyerId, @PathParam("leadId")String leadId){
		if (logger.isInfoEnabled())
			logger.info("Entering LeadDetailManagementRequestProcessor.getleadFirmDetails()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.setRequestType(RequestType.Get);
		apiVO.setBuyerId(buyerId);
		apiVO.setleadId(leadId);
		apiVO.setRequestFromGetDelete(reqMap);
		String responseXML = leadFirmDetailService.doSubmit(apiVO);
		if (logger.isInfoEnabled()){
		   logger.info("Response Xml as String:"+ responseXML);
		   logger.info("Leaving LeadDetailManagementRequestProcessor.getleadFirmDetails()");
		  }
		  return (LeadDetailsResponse) convertXMLStringtoRespObject(responseXML,LeadDetailsResponse.class);
		}
	
	
	/**
	 * call this method to convert XML string to object
	 * 
	 * @param request
	 * @param classz
	 * @return Object
	 */
	protected Object convertXMLStringtoRespObject(String request,
			Class<?> classz) {
		XStream xstream = this.getXstream(classz);
		return (Object) xstream.fromXML(request);
	}

	/**
	 * 
	 * @param classz
	 * @return
	 */
	protected XStream getXstream(Class<?> classz) {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		xstream.registerConverter(new DateConverter(PublicAPIConstant.DATE_FORMAT_YYYY_MM_DD, new String[0]));
		xstream.addDefaultImplementation(java.sql.Date.class,java.util.Date.class);
		xstream.processAnnotations(classz);
		return xstream;
	}
	public AddNotesForLeadService getAddNotesForLeadService() {
		return addNotesForLeadService;
	}


	public void setAddNotesForLeadService(
			AddNotesForLeadService addNotesForLeadService) {
		this.addNotesForLeadService = addNotesForLeadService;
	}


	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

	public GetIndividualLeadDetailsService getGetIndividualLeadDetailsService() {
		return getIndividualLeadDetailsService;
	}

	public void setGetIndividualLeadDetailsService(
			GetIndividualLeadDetailsService getIndividualLeadDetailsService) {
		this.getIndividualLeadDetailsService = getIndividualLeadDetailsService;
	}

	public GetAllLeadService getGetAllLeadService() {
		return getAllLeadService;
	}

	public void setGetAllLeadService(GetAllLeadService getAllLeadService) {
		this.getAllLeadService = getAllLeadService;
	}
	public UpdateLeadStatusService getUpdateLeadStatusService() {
		return updateLeadStatusService;
	}

	public void setUpdateLeadStatusService(
			UpdateLeadStatusService updateLeadStatusService) {
		this.updateLeadStatusService = updateLeadStatusService;
	}


	public LeadFirmDetailService getLeadFirmDetailService() {
		return leadFirmDetailService;
	}


	public void setLeadFirmDetailService(LeadFirmDetailService leadFirmDetailService) {
		this.leadFirmDetailService = leadFirmDetailService;
	}

}