package com.servicelive.esb.mapper;

import java.util.Date;
import java.util.Hashtable;

import com.newco.marketplace.translator.business.IClientService;
import com.newco.marketplace.translator.business.IIncidentService;
import com.newco.marketplace.translator.dao.Client;
import com.newco.marketplace.translator.dao.Incident;
import com.newco.marketplace.translator.dao.IncidentAck;
import com.newco.marketplace.translator.util.SpringUtil;
import com.servicelive.esb.constant.AssurantIncidentConstants;
import com.servicelive.esb.constant.MarketESBConstant;

public class AssurantIncidentAckMapper implements Mapper {

	public IIncidentService incidentService = (IIncidentService) SpringUtil.factory.getBean("IncidentService");
	public IClientService clientService = (IClientService) SpringUtil.factory.getBean("ClientService");
	
	@SuppressWarnings("unchecked")
	public Object mapData(Object input) throws Exception {
		Hashtable<String,String> ackInfo = (Hashtable<String,String>)input;
		String incidentAckString = ackInfo.get(MarketESBConstant.INCIDENT_STRING);
		IncidentAck incidentAck = new IncidentAck();
		//set up client object
		Client client = clientService.getClient(MarketESBConstant.Client.ASSURANT);
		String[] incidentAckFields = incidentAckString.split("~");
		//incidentNote.setCreatedDate(new Date());
		String clientIncidentID = incidentAckFields[AssurantIncidentConstants.ACK_INCIDENT_ID];
		Incident incident = incidentService.getIncidentByClientIncident(clientIncidentID, client);
		if (null != incident) {
			incidentAck.setIncident(incident);
			incidentAck.setIncidentStatus(incidentAckFields[AssurantIncidentConstants.ACK_INCIDENT_STATUS]);
			incidentAck.setAckFileName(ackInfo.get(MarketESBConstant.FILE_FEED_NAME));
			incidentAck.setReceivedDateTime(new Date());
			incidentService.saveIncidentAck(incidentAck);
		}
		return incidentAck;
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
