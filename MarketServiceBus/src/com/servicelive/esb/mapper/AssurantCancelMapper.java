package com.servicelive.esb.mapper;

import java.util.Date;

import com.newco.marketplace.translator.business.IClientService;
import com.newco.marketplace.translator.business.IIncidentService;
import com.newco.marketplace.translator.dao.Client;
import com.newco.marketplace.translator.dao.Incident;
import com.newco.marketplace.translator.dao.IncidentEvent;
import com.newco.marketplace.translator.util.SpringUtil;
import com.servicelive.esb.constant.AssurantIncidentConstants;
import com.servicelive.esb.constant.MarketESBConstant;

public class AssurantCancelMapper extends IncidentEventMapper implements Mapper {

	public IIncidentService incidentService = (IIncidentService) SpringUtil.factory.getBean("IncidentService");
	public IClientService clientService = (IClientService) SpringUtil.factory.getBean("ClientService");
	
	public IncidentEvent mapData(Object input) throws Exception {
		
		IncidentEvent incidentEvent = null;
		//the input should be a ~ delimited file with ^ being the second delimiter
		String incidentString = (String) input;
		if (null != incidentString) {
			incidentEvent = new IncidentEvent();
			//set up client object
			Client client = clientService.getClient(MarketESBConstant.Client.ASSURANT);
			String[] incidentFields = incidentString.split("~");
			String clientIncidentID = incidentFields[AssurantIncidentConstants.INCIDENT_ID];
			incidentEvent.setCreatedDate(new Date());

			Incident incident = incidentService.getIncidentByClientIncident(clientIncidentID, client);
			incidentEvent.setIncident(incident);
			
			incidentEvent.setIncidentComment(incidentFields[AssurantIncidentConstants.COMMENTS]);
			incidentEvent.setStatus(incidentFields[AssurantIncidentConstants.STATUS]);
			if( ! incidentService.saveCancelIncidentEvent(incidentEvent) )
				throw new Exception( "IncidnetEvent not saved" );
		}
		return incidentEvent;
	}

	public IIncidentService getIncidentService() {
		return incidentService;
	}

	public void setIncidentBO(IIncidentService incidentService) {
		this.incidentService = incidentService;
	}

}
