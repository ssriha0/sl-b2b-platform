package com.newco.marketplace.webservices.sei.serviceorder;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

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
import com.newco.marketplace.webservices.dto.serviceorder.GetServiceOrderResponse;

@WebService(name="ServiceOrder", targetNamespace="http://market.serviceorder.soup.com")
public interface ServiceOrderSEI {

	@WebMethod(operationName = "addSODocuments")
	@WebResult(name = "AddDocumentsResponse")
	public AddDocumentsResponse addSODocuments(AddDocumentsRequest request);
	
	@WebMethod(operationName = "createDraft")
    @WebResult(name = "CreateDraftResponse")
    public CreateDraftResponse createDraft(CreateDraftRequest request );
	
	@WebMethod(operationName = "routeServiceOrder")
    @WebResult(name = "routeResponse")
	public RouteResponse routeServiceOrder(RouteRequest request );
	
	@WebMethod(operationName = "cancelSO")
    @WebResult(name = "CancelSOResponse")
	public CancelSOResponse cancelSO(CancelSORequest cancelRequest);
	
	@WebMethod(operationName = "rescheduleSO")
    @WebResult(name = "RescheduleSOResponse")
	public RescheduleSOResponse rescheduleSO(RescheduleSORequest rescheduleRequest);
	
	public boolean ping();
    
	@WebMethod(operationName = "addClientServiceOrderNote")
	@WebResult(name = "ClientNoteResponse")
	public ClientServiceOrderNoteResponse addClientServiceOrderNote(ClientServiceOrderNoteRequest clientServiceOrderNoteRequest);
	
	/**
	 * Calls the backend to update the So_incident_tracking table records with acknowledgment
	 * @param soIncidentTrackingRequest
	 * @return
	 */
	@WebMethod(operationName = "updateIncidentTrackingWithAck")
	@WebResult(name = "IncidentAckResponse")
	public UpdateIncidentTrackingResponse updateIncidentTrackingWithAck(UpdateIncidentTrackingRequest soIncidentTrackingRequest);
    
    /**
     * Attempts to close the service order specified in the request parameter.
     * @param request
     * @return
     */
    @WebMethod(operationName = "closeServiceOrder")
    @WebResult(name = "ServiceOrderResponse")
    public ServiceOrderResponse closeServiceOrder(CloseServiceOrderRequest request);
    
    @WebMethod(operationName = "updatePart")
    @WebResult(name = "UpdatePartResponse")
    public UpdatePartResponse updatePart(CreatePartRequest request);
    
    @WebMethod(operationName = "updateParts")
    @WebResult(name = "UpdatePartsResponse")
    public UpdatePartsResponse updateParts(CreatePartsRequest request);
    
    /**
     * This returns 0 or more accepted/active/problem service orders for this tech id 
     * @param techid
     * @param last6digits
     * @return
     */
    @WebMethod(operationName = "validateServiceOrders")
    @WebResult(name="String")
    String[] validateServiceOrders(String techid, String last6digits); 

    /**
     * This returns 0 or more accepted/active/problem service orders for this tech id
     * Response will be a "|" delimited service orders if more than 1 is found 
     * @param techid
     * @param last6digits
     * @return
     */
    @WebMethod(operationName = "validateServiceOrdersString")
    @WebResult(name="String")
    String validateServiceOrdersString(String techid, String last6digits);     
    
    /**
     * returns 1 or null accepted/active/problem service orders for this tech id 
     * @param techid
     * @param so_no
     * @return
     */
    @WebMethod(operationName = "validateAServiceOrder")
    @WebResult(name="String")
    String validateAServiceOrder(String techid , String so_no); 

    /**
     * reason codes are 0 for the first 2 ( NA ) and from a list for the second; 
     * 1. Cust not home 2. Project Out Of scope 3. Site not ready 4. Wrong Part
     *  activity is arrive,depart-complete,depart-issues)
     * @return
     */
    @WebMethod(operationName = "insertEvent")
    @WebResult(name="String")
    String insertEvent(EventRequest request); 

    /**
     * This operation has been added to support clients that choke on document/literal
     * WSDL bindings and the combination of complex types.
     * 
     * The input string will be of this format:
     * buyerId|eventReasonCode|eventTypeID|password|resourceID|serviceOrderID|userId
     * 
     */
    @WebMethod(operationName = "insertEventString")
    @WebResult(name="String")    
	String insertEventString(String request);
    
    
    /**
     * This operation has been added to getServiceOrder
     * using WSDL bindings.
     * 
     * 
     */
    @WebMethod(operationName = "getServiceOrder")
    @WebResult(name = "GetServiceOrderResponse")
    //CreateDraftResponse getServiceOrder(String soId);
    public GetServiceOrderResponse getServiceOrder(String soId);
}