package com.newco.marketplace.webservices.dispatcher.so.order;

import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftResponse;

public interface IOrderDispatchRequestor {
	public CreateDraftResponse createDraft(CreateDraftRequest request);
	
	public CreateDraftResponse updateOrder(CreateDraftRequest request);
}
