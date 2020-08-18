package com.newco.marketplace.webservices.dto.serviceorder;


public class UpdateIncidentTrackingRequest extends ABaseWebserviceRequest {
	
	private static final long serialVersionUID = -2464125030975434362L;
	
	private Integer incidentId;
	private String clientIncidentid;
	private Integer incidentAckId;
	private String incidentStatus;
	public Integer getIncidentId() {
		return incidentId;
	}
	public void setIncidentId(Integer incidentId) {
		this.incidentId = incidentId;
	}
	public Integer getIncidentAckId() {
		return incidentAckId;
	}
	public void setIncidentAckId(Integer incidentAckId) {
		this.incidentAckId = incidentAckId;
	}
	public String getIncidentStatus() {
		return incidentStatus;
	}
	public void setIncidentStatus(String incidentStatus) {
		this.incidentStatus = incidentStatus;
	}
	public String getClientIncidentid() {
		return clientIncidentid;
	}
	public void setClientIncidentid(String clientIncidentid) {
		this.clientIncidentid = clientIncidentid;
	}
}
