package com.newco.marketplace.webservices.dispatcher.so.order;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftResponse;
import com.newco.marketplace.webservices.sei.serviceorder.ServiceOrderSEI;

/**
 * Acts as a controller for the webservice operations
 */
public class OrderDispatchRequestor extends BaseOrderCreator implements ServiceOrderSEI {

	private static final long serialVersionUID = -20090826L;
	private static final Logger logger = Logger.getLogger(OrderDispatchRequestor.class.getName());
	private OrderDispatchRequestorLocator orderDispatchRequestorLocator;

	
	public OrderDispatchRequestorLocator getOrderDispatchRequestorLocator() {
		return orderDispatchRequestorLocator;
	}


	public void setOrderDispatchRequestorLocator(
			OrderDispatchRequestorLocator orderDispatchRequestorLocator) {
		this.orderDispatchRequestorLocator = orderDispatchRequestorLocator;
	}

	/**
	 * Create Draft Implementation
	 *
	 * @param request
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public CreateDraftResponse createDraft(CreateDraftRequest request) {
		logger.info("Starting CreateDraftResponse");
		String clientId = request.getClientId();
		// This ugly if ensures absolute backward compatibility
		// will be re-factored at the later time to the locator below
		if (StringUtils.isBlank(clientId) || clientId.equals("OMS")) {
			return processOMSCreateDraft(request);
		}
		return orderDispatchRequestorLocator.getDispatcher(clientId).createDraft(request);

	}
	
	
	/**
	 * Create Draft Implementation
	 *
	 * @param request
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public CreateDraftResponse updateOrder(CreateDraftRequest request) {
		logger.info("Starting CreateDraftResponse");
		String clientId = request.getClientId();
		return orderDispatchRequestorLocator.getDispatcher(clientId).updateOrder(request);

	}

}
