package com.servicelive.esb.mapper;

import java.util.Date;

import com.newco.marketplace.translator.business.IClientService;
import com.newco.marketplace.translator.business.IIncidentService;
import com.newco.marketplace.translator.dao.Client;
import com.newco.marketplace.translator.dao.Incident;
import com.newco.marketplace.translator.dao.IncidentNote;
import com.newco.marketplace.translator.util.SpringUtil;
import com.servicelive.esb.constant.AssurantIncidentConstants;
import com.servicelive.esb.constant.MarketESBConstant;

public class AssurantIncidentNoteMapper implements Mapper {

	public IIncidentService incidentService = (IIncidentService) SpringUtil.factory.getBean("IncidentService");
	public IClientService clientService = (IClientService) SpringUtil.factory.getBean("ClientService");
	
	public Object mapData(Object input) throws Exception {
		String incidentNoteString = (String) input;
		IncidentNote incidentNote = new IncidentNote();
		//set up client object
		Client client = clientService.getClient(MarketESBConstant.Client.ASSURANT);
		String[] incidentNoteFields = incidentNoteString.split("~");
		incidentNote.setCreatedDate(new Date());
		String clientIncidentID = incidentNoteFields[AssurantIncidentConstants.NOTE_INCIDENT_ID];
		Incident incident = incidentService.getIncidentByClientIncident(clientIncidentID, client);
		if (null != incident) {
			incidentNote.setIncident(incident);
			incidentNote.setNote(incidentNoteFields[AssurantIncidentConstants.NOTE_NOTE]);
			incidentNote.setSource(client.getName());
			incidentService.saveIncidentNote(incidentNote);
		}
		return incidentNote;
	}

	public IIncidentService getIncidentService() {
		return incidentService;
	}

	public void setIncidentService(IIncidentService incidentService) {
		this.incidentService = incidentService;
	}

	public IClientService getClientService() {
		return clientService;
	}

	public void setClientService(IClientService clientService) {
		this.clientService = clientService;
	}

}
