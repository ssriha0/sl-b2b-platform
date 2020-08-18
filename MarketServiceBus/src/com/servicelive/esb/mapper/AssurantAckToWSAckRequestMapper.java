package com.servicelive.esb.mapper;

import java.util.ResourceBundle;

import com.newco.marketplace.translator.dao.IncidentAck;
import com.newco.marketplace.webservices.dto.serviceorder.UpdateIncidentTrackingRequest;
import com.servicelive.esb.constant.MarketESBConstant;

public class AssurantAckToWSAckRequestMapper implements Mapper {

	private ResourceBundle resourceBundle = ResourceBundle.getBundle("servicelive_esb_" + System.getProperty("sl_app_lifecycle"));
	
	public Object mapData(Object obj) throws Exception {
		IncidentAck incidentAck = (IncidentAck) obj;
		UpdateIncidentTrackingRequest trackingRequest = new UpdateIncidentTrackingRequest();
		
		trackingRequest.setUserId((String) resourceBundle.getObject(MarketESBConstant.Client.ASSURANT + "_USER_ID"));
		trackingRequest.setIncidentId(incidentAck.getIncident().getIncidentID());
		trackingRequest.setIncidentStatus(incidentAck.getIncidentStatus());
		trackingRequest.setIncidentAckId(incidentAck.getIncidentAckId());
		trackingRequest.setClientIncidentid(incidentAck.getIncident().getClientIncidentID());
		return trackingRequest;
	}

}
