package com.newco.marketplace.webservices.seiImpl.so;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.apache.log4j.Logger;

import com.newco.marketplace.webservices.dispatcher.so.order.OrderDispatchRequestor;
import com.newco.marketplace.webservices.dto.serviceorder.AddDocumentsRequest;
import com.newco.marketplace.webservices.dto.serviceorder.AddDocumentsResponse;
import com.newco.marketplace.webservices.dto.serviceorder.CancelSORequest;
import com.newco.marketplace.webservices.dto.serviceorder.CancelSOResponse;
import com.newco.marketplace.webservices.dto.serviceorder.ClientServiceOrderNoteRequest;
import com.newco.marketplace.webservices.dto.serviceorder.ClientServiceOrderNoteResponse;
import com.newco.marketplace.webservices.dto.serviceorder.CloseServiceOrderRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftResponse;
import com.newco.marketplace.webservices.dto.serviceorder.CreatePartRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreatePartsRequest;
import com.newco.marketplace.webservices.dto.serviceorder.EventRequest;
import com.newco.marketplace.webservices.dto.serviceorder.RescheduleSORequest;
import com.newco.marketplace.webservices.dto.serviceorder.RescheduleSOResponse;
import com.newco.marketplace.webservices.dto.serviceorder.RouteRequest;
import com.newco.marketplace.webservices.dto.serviceorder.RouteResponse;
import com.newco.marketplace.webservices.dto.serviceorder.ServiceOrderResponse;
import com.newco.marketplace.webservices.dto.serviceorder.UpdateIncidentTrackingRequest;
import com.newco.marketplace.webservices.dto.serviceorder.UpdateIncidentTrackingResponse;
import com.newco.marketplace.webservices.dto.serviceorder.UpdatePartResponse;
import com.newco.marketplace.webservices.dto.serviceorder.UpdatePartsResponse;
import com.newco.marketplace.webservices.sei.serviceorder.ServiceOrderSEI;
import com.newco.marketplace.webservices.dto.serviceorder.GetServiceOrderResponse;


/**
 *  Description of the Class
 *
 *@author     dmill03
 *@created    August 7, 2007
 */
@WebService(endpointInterface="com.newco.marketplace.webservices.sei.serviceorder.ServiceOrderSEI", serviceName="ServiceOrder")
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
public class ServiceOrderImpl implements ServiceOrderSEI {

    private static final Logger logger = Logger.getLogger(OrderDispatchRequestor.class.getName());

	private ServiceOrderSEI theServiceDispatch;
	

    /*
     * (non-Javadoc)
     * @see com.newco.services.order.sei.ServiceOrderSEI#closeServiceOrder(com.newco.services.order.request.CloseServiceOrderRequest)
     */
	public ServiceOrderResponse closeServiceOrder(CloseServiceOrderRequest request)
	{
		ServiceOrderResponse response = null;
        
        logger.info("Entering ServiceOrderImpl.closeServiceOrder()");
		
		response = theServiceDispatch.closeServiceOrder(request);

        logger.info("Exiting ServiceOrderImpl.closeServiceOrder()");
        return response;
		
	}//end method
	
	
	/**
	 *  Description of the Method
	 *
	 *@param  request  Description of the Parameter
	 *@return          Description of the Return Value
	 */
	public CreateDraftResponse createDraft(CreateDraftRequest request) {

		CreateDraftResponse response = theServiceDispatch.createDraft(request);
		return response;
	}
	
	public RouteResponse routeServiceOrder(RouteRequest request) {

		OrderDispatchRequestor requestor = new OrderDispatchRequestor();
		
		RouteResponse response =  requestor.routeServiceOrder(request);
		
		return response;
	}


	/**
	 *  Adds a feature to the NoteToService attribute of the ServiceOrderImpl
	 *  object
	 *
	 *@param  request  The feature to be added to the NoteToService attribute
	 *@return          Description of the Return Value
	 */
	/*public AddNoteResponse addNoteToService(AddNoteRequest request) {

		AddNoteResponse response =
				theServiceDispatch.addNoteToService(request);
		return response;
	}*/


	public CancelSOResponse cancelSO(CancelSORequest cancelRequest) {
		
		CancelSOResponse response = theServiceDispatch.cancelSO(cancelRequest);
	
		return response;
	}
    



	public RescheduleSOResponse rescheduleSO(RescheduleSORequest rescheduleRequest) {
		RescheduleSOResponse response = theServiceDispatch.rescheduleSO(rescheduleRequest);
		return response;
	}
	
	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Return Value
	 */
	public boolean ping() {

		boolean response = theServiceDispatch.ping();
		return response;
	}


	/**
	 *  Gets the serviceDispatch attribute of the ServiceOrderImpl object
	 *
	 *@return    The serviceDispatch value
	 */
	

	/**
	 *  Sets the serviceDispatch attribute of the ServiceOrderImpl object
	 *
	 *@param  theServiceDispatch  The new serviceDispatch value
	 */


	public UpdatePartResponse updatePart(CreatePartRequest request) {
		UpdatePartResponse response = theServiceDispatch.updatePart(request);
		return response;
	}

	public UpdatePartsResponse updateParts(CreatePartsRequest request) {
		UpdatePartsResponse response = theServiceDispatch.updateParts(request);
		return response;
	}


	public String insertEvent(EventRequest request) {
		String response = theServiceDispatch.insertEvent(request);
		return response;
	}

	public String insertEventString(String request) {
		return theServiceDispatch.insertEventString(request);
	}

	public String validateAServiceOrder(String resourceId, String soId) {
		String response = theServiceDispatch.validateAServiceOrder(resourceId, soId);
		return response;
	}
	
	public String[] validateServiceOrders(String resourceId, String last6digits) {
		String[] response = theServiceDispatch.validateServiceOrders(resourceId, last6digits);
		return response;
	}

	public String validateServiceOrdersString(String techid, String last6digits) {
		return theServiceDispatch.validateServiceOrdersString(techid, last6digits);
	}
	
	public AddDocumentsResponse addSODocuments(AddDocumentsRequest request) {
		AddDocumentsResponse response = theServiceDispatch.addSODocuments(request);
		return response;
	}


	public ServiceOrderSEI getTheServiceDispatch() {
		return theServiceDispatch;
	}


	public void setTheServiceDispatch(ServiceOrderSEI theServiceDispatch) {
		this.theServiceDispatch = theServiceDispatch;
	}


	public ClientServiceOrderNoteResponse addClientServiceOrderNote(
			ClientServiceOrderNoteRequest clientServiceOrderNoteRequest) {
		ClientServiceOrderNoteResponse response = theServiceDispatch.addClientServiceOrderNote(clientServiceOrderNoteRequest);
		
		return response;
	}

	public GetServiceOrderResponse getServiceOrder(String soId) {
		GetServiceOrderResponse response = theServiceDispatch.getServiceOrder(soId);
		return response;
	}


	public UpdateIncidentTrackingResponse updateIncidentTrackingWithAck(
			UpdateIncidentTrackingRequest soIncidentTrackingRequest) {
		UpdateIncidentTrackingResponse response = theServiceDispatch.updateIncidentTrackingWithAck(soIncidentTrackingRequest);
		
		return response;
	}
	
}

