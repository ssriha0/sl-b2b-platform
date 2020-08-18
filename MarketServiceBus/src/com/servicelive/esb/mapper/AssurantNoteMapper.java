package com.servicelive.esb.mapper;

import java.util.ResourceBundle;

import com.newco.marketplace.translator.dao.IncidentNote;
import com.newco.marketplace.webservices.dto.serviceorder.ClientServiceOrderNoteRequest;
import com.servicelive.esb.constant.MarketESBConstant;

public class AssurantNoteMapper implements Mapper {

	private ResourceBundle resourceBundle = ResourceBundle.getBundle("servicelive_esb_" + System.getProperty("sl_app_lifecycle"));
	
	public Object mapData(Object obj) throws Exception {
		IncidentNote note = (IncidentNote) obj;
		ClientServiceOrderNoteRequest noteRequest = new ClientServiceOrderNoteRequest();
		
		noteRequest.setUserId((String) resourceBundle.getObject(MarketESBConstant.Client.ASSURANT + "_USER_ID"));
		noteRequest.setNote(note.getNote());
		noteRequest.setOrderIDString(note.getIncident().getClientIncidentID());
		noteRequest.setSubject("Assurant Info Data");
		return noteRequest;
	}

}
