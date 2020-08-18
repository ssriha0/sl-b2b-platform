package com.servicelive.esb.service;

import com.newco.marketplace.webservices.dto.serviceorder.ClientServiceOrderNoteRequest;
import com.newco.marketplace.webservices.dto.serviceorder.ClientServiceOrderNoteResponse;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftResponse;
import com.newco.marketplace.webservices.dto.serviceorder.GetSOStatusRequest;
import com.newco.marketplace.webservices.dto.serviceorder.GetServiceOrderResponse;
import com.newco.marketplace.webservices.dto.serviceorder.GetSOStatusResponse;
import com.newco.marketplace.webservices.dto.serviceorder.RouteRequest;
import com.newco.marketplace.webservices.dto.serviceorder.RouteResponse;
import com.newco.marketplace.webservices.dto.serviceorder.UpdateIncidentTrackingRequest;
import com.newco.marketplace.webservices.dto.serviceorder.UpdateIncidentTrackingResponse;

public interface SLOrderService {

	CreateDraftResponse createDraft(CreateDraftRequest createDraftReq) throws Exception;
	
	ClientServiceOrderNoteResponse addClientNote(ClientServiceOrderNoteRequest request) throws Exception;
	
	RouteResponse routeOrder(RouteRequest routeReq) throws Exception;
	
	public String getServiceOrderEndPointUrl();

	public void setServiceOrderEndPointUrl(String serviceOrderEndPointUrl);
	
	public GetServiceOrderResponse getServiceOrder(String soId) throws Exception;
	
	/**
	 * Calls the webservice method to update the so incident tracking table to update the incident out files
	 * with acknowledgment.
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	UpdateIncidentTrackingResponse updateIncidentTrackingWithAck(UpdateIncidentTrackingRequest request) throws Exception;
	/**
	 * This calls the webservice to get ServiceOrderId and Status Using CustomReference
	 * @param getSOStatusRequest
	 * @return GetServiceOrderResponse
	 */
	public GetSOStatusResponse getSOStatus (GetSOStatusRequest getSOStatusRequest) throws Exception;
	
	public CreateDraftResponse updateOrder(CreateDraftRequest createDraftReq) throws Exception;

}
