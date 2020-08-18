/**
 * 
 */
package com.newco.marketplace.process;

import com.newco.marketplace.webservices.dispatcher.so.order.OrderDispatchRequestor;
import com.newco.marketplace.webservices.dto.serviceorder.ClientServiceOrderNoteRequest;
import com.newco.marketplace.webservices.dto.serviceorder.ClientServiceOrderNoteResponse;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftResponse;
import com.newco.marketplace.webservices.dto.serviceorder.GetSOStatusRequest;
import com.newco.marketplace.webservices.dto.serviceorder.GetSOStatusResponse;
import com.newco.marketplace.webservices.dto.serviceorder.GetServiceOrderResponse;
import com.newco.marketplace.webservices.dto.serviceorder.RouteRequest;
import com.newco.marketplace.webservices.dto.serviceorder.RouteResponse;
import com.newco.marketplace.webservices.dto.serviceorder.UpdateIncidentTrackingRequest;
import com.newco.marketplace.webservices.dto.serviceorder.UpdateIncidentTrackingResponse;

/**
 * @author mjoshi1
 *
 */
public class SOFileProcessingBridge {
	
	
	private OrderDispatchRequestor orderDispatchRequestor;

	/**
	 * @return the orderDispatchRequestor
	 */
	public OrderDispatchRequestor getOrderDispatchRequestor() {
		return orderDispatchRequestor; 
		
	}

	/**
	 * @param orderDispatchRequestor the orderDispatchRequestor to set
	 */
	public void setOrderDispatchRequestor(
			OrderDispatchRequestor orderDispatchRequestor) {
		this.orderDispatchRequestor = orderDispatchRequestor;
	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.esb.service.SLOrderService#createDraft(com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest)
	 */
	public CreateDraftResponse createDraft(CreateDraftRequest request) {
		
		return orderDispatchRequestor.createDraft(request);
		
	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.esb.service.SLOrderService#createDraft(com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest)
	 */
	public CreateDraftResponse updateOrder(CreateDraftRequest request) {
		
		return orderDispatchRequestor.updateOrder(request);
		
	}
	
	
	/**
	 * Route a service order based on criteria in the request
	 */
	public RouteResponse routeServiceOrder(RouteRequest request) {
		return orderDispatchRequestor.routeServiceOrder(request);
		
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public ClientServiceOrderNoteResponse addClientServiceOrderNote(ClientServiceOrderNoteRequest request) {
		return orderDispatchRequestor.addClientServiceOrderNote(request);
	}
	
	
	/**
	 * 
	 * @param soId
	 * @return
	 */
	public GetServiceOrderResponse getServiceOrder(String soId) {
		return orderDispatchRequestor.getServiceOrder(soId);
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	public UpdateIncidentTrackingResponse updateIncidentTrackingWithAck(UpdateIncidentTrackingRequest request) {
		return orderDispatchRequestor.updateIncidentTrackingWithAck(request);
	}
	
	
	/**
	 * This operation has been added to get ServiceOrderId and Status Using CustomReference
	 * @param request
	 * @return GetSOStatusResponse
	 */
	public GetSOStatusResponse getSOStatus(GetSOStatusRequest request) {
		return orderDispatchRequestor.getSOStatus(request);
	}
	
	

}
