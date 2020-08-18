/*
 *	Date        Project    	  Author       	 Version
 * -----------  --------- 	-----------  	---------
 * 24-Feb-2009	KMSTRSUP   Infosys				1.1
 * 
 *
 */
package com.newco.marketplace.translator.business;

import java.util.List;

import com.newco.marketplace.translator.dto.SkuPrice;
import com.newco.marketplace.webservices.dto.serviceorder.ClientServiceOrderNoteRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.UpdateIncidentTrackingRequest;
public interface ITranslationService {
	
	public CreateDraftRequest translateDraft(CreateDraftRequest request, List<SkuPrice> skus, String client) throws Exception;
	
	public ClientServiceOrderNoteRequest translateClientNote(ClientServiceOrderNoteRequest request) throws Exception;
	/**
	 * Adds the web service authentication information web service request
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public UpdateIncidentTrackingRequest translateIncidentAck(UpdateIncidentTrackingRequest request) throws Exception;
	
	public int getauthorizeBuyerId(String userName) throws Exception;
	
	/**
	 * Persists the SO Upsell Addons in staging database
	 * @param request
	 */
	public void createSOUpsells(CreateDraftRequest request);
	
}
