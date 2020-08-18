package com.newco.marketplace.translator.business.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.translator.business.PricingService;
import com.newco.marketplace.translator.dto.SkuPrice;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.NoteRequest;

public class PricingServiceAssurantImpl implements PricingService {

	private static final Logger logger = Logger.getLogger(PricingServiceAssurantImpl.class);
	
	public CreateDraftRequest priceOrder(CreateDraftRequest request,
			boolean allTasksTranslated, List<NoteRequest> notes,
			List<SkuPrice> skus) {
		if (!allTasksTranslated) {
			logger.warn("Not all tasks translated");
			NoteRequest note = new NoteRequest();
			note.setSubject("Error pricing Service Order");
			note.setNote("Service Order has SKUs that could not be mapped to Skill Nodes. The order could not be priced.");
			notes.add(note);
			request.setAutoRoute(new Boolean(false));
		}
		return request;
	}

}
