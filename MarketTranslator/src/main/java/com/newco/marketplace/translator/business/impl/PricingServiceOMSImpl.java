package com.newco.marketplace.translator.business.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.translator.business.IStagingService;
import com.newco.marketplace.translator.business.PricingService;
import com.newco.marketplace.translator.dto.SkuPrice;
import com.newco.marketplace.translator.util.Constants;
import com.newco.marketplace.translator.util.SpringUtil;
import com.newco.marketplace.translator.util.TranslatorUtils;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateTaskRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CustomRef;
import com.newco.marketplace.webservices.dto.serviceorder.NoteRequest;

public class PricingServiceOMSImpl implements PricingService {

	private static final Logger logger = Logger.getLogger(PricingServiceOMSImpl.class);
	
	public CreateDraftRequest priceOrder(CreateDraftRequest request, boolean allTasksTranslated, List<NoteRequest> notes, List<SkuPrice> skus) {
		
		BuyerSkuService skuService = (BuyerSkuService) SpringUtil.factory.getBean("BuyerSkuService");
		String storeCode = extractStoreCode(request.getCustomRef().toArray(new CustomRef[0]));
		
		IStagingService stagingService = (IStagingService) SpringUtil.factory.getBean(Constants.SL_STAGING_SERVICE);
		String orderNumber = TranslatorUtils.getOMSOrderNumberFromReferenceFields(request);
		String unitNumber = TranslatorUtils.getOMSUnitNumberFromReferenceFields(request);
		
		//price order
		if (allTasksTranslated && storeCode != null && request.getServiceLocation() != null && request.getServiceLocation().getZip() != null) {
			
			try {
				// This method modifies the request object by setting the price
				skuService.priceServiceOrder(skus, storeCode, request);
			} catch (Exception e) {
				logger.info("Error Pricing Order: "+e.getMessage()+" Injecting order with a note on pricing in it.");//Don't log stack trace unnecessarily
				NoteRequest note = new NoteRequest();
				note.setSubject("Error pricing Service Order");
				note.setNote("Service Order could not be priced. Exception: " + e.getMessage());
				notes.add(note);
				request.setAutoRoute(new Boolean(false));
				stagingService.persistErrors(Constants.EX_PRICING_ERROR, Constants.EX_PRICING_ERROR_MESSAGE,orderNumber,unitNumber);
				//TODO set attn required flag on request when developed (I19)
			}
		}
		else {
			// SL-11258 IF WE ARE HERE, THE TASKS MAY NOT HAVE A PRICE
			// DEFAULT THE PRICE TO 0.0 IF IT IS EMPTY
			List<CreateTaskRequest> tasks = request.getTasks();
			for(CreateTaskRequest t : tasks) {
				if(t.getLaborPrice() == null) {
					logger.warn("Setting labor price to 0.00 for task: "+t.toString());
					t.setLaborPrice(0.0);
				}
			}
			
			if (storeCode == null) {
				logger.warn("Store code could not be found.");
				NoteRequest note = new NoteRequest();
				note.setSubject("Error getting store code for Service Order");
				note.setNote("The store code could not be found. The order could not be priced.");
				notes.add(note);
				request.setAutoRoute(new Boolean(false));
				stagingService.persistErrors(Constants.EX_STORE_CODE_ERROR, Constants.EX_STORE_CODE_ERROR_MESSAGE,orderNumber,unitNumber);
			}
			if (request.getServiceLocation() == null || request.getServiceLocation().getZip() == null) {
				logger.warn("Service location could not be found.");
				NoteRequest note = new NoteRequest();
				note.setSubject("Error getting service location for Service Order");
				note.setNote("The service location could not be found. The order could not be priced.");
				notes.add(note);
				request.setAutoRoute(new Boolean(false));
				stagingService.persistErrors(Constants.EX_LOCATION_ERROR, Constants.EX_LOCATION_ERROR_MESSAGE,orderNumber,unitNumber);
			}
			if (!allTasksTranslated) {
				logger.warn("Not all tasks translated");
				NoteRequest note = new NoteRequest();
				note.setSubject("Error pricing Service Order");
				note.setNote("Service Order has SKUs that could not be mapped to Skill Nodes. The order could not be priced.");
				notes.add(note);
				request.setAutoRoute(new Boolean(false));
				stagingService.persistErrors(Constants.EX_SKU_SKILL_MAPPING_ERROR, Constants.EX_SKU_SKILL_MAPPING_ERROR_MESSAGE,orderNumber,unitNumber);
			}
			
		}
		return request;
	}
	
	public String extractStoreCode(CustomRef[] customRefs) {
		String storeCode = null;
		if (customRefs != null) {
			for (int refIndex = 0; refIndex < customRefs.length; refIndex++) {
				if (customRefs[refIndex].getKey().equals(TranslationService.STORE_CODE_KEY)) {
					storeCode = customRefs[refIndex].getValue();
					break;
				}
			}
		}	
		return storeCode;
	}
	
}
