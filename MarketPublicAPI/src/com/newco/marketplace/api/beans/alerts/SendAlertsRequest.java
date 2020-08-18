package com.newco.marketplace.api.beans.alerts;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing request information for 
 * the SendAlertService
 * @author Infosys
 *
 */
@XStreamAlias("sendAlert")
public class SendAlertsRequest {
	
	@XStreamAlias("alertType")
	private String alertType;
	
	@XStreamAlias("templateID")
	private String templateID;
	
	@XStreamAlias("destination")
	private String destination;
	
	@XStreamAlias("parameters")
	private Parameters parameters;

	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	public String getTemplateID() {
		return templateID;
	}

	public void setTemplateID(String templateID) {
		this.templateID = templateID;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Parameters getParameters() {
		return parameters;
	}

	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}

}
