package com.newco.marketplace.translator.business;

import java.util.List;

import com.newco.marketplace.translator.dto.SkuPrice;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.NoteRequest;

public interface PricingService {
	
	public CreateDraftRequest priceOrder(CreateDraftRequest request, boolean allTasksTranslated, List<NoteRequest> notes, List<SkuPrice> skus);

}
