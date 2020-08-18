package com.servicelive.esb.dto;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("LogisticsMerchandise")
public class LogisticsMerchandise implements Serializable {

	/** generated serialVersionUID */
	private static final long serialVersionUID = 7265944414847448728L;

	@XStreamAlias("LastMaintenanceDate")
	private String lastMaintenanceDate;
	
	@XStreamAlias("PendCode")
    private String pendCode;
	
	@XStreamAlias("PendDescription")
    private String pendDescription;
	
	@XStreamAlias("SCIMHandlingCode")
    private String scimHandlingCode;
	
	@XStreamAlias("SCIMHandlingDescription")
    private String scimHandlingDescription;
	
    public String getLastMaintenanceDate() {
		return lastMaintenanceDate;
	}

	public void setLastMaintenanceDate(String lastMaintenanceDate) {
		this.lastMaintenanceDate = lastMaintenanceDate;
	}

	public String getPendCode() {
		return pendCode;
	}

	public void setPendCode(String pendCode) {
		this.pendCode = pendCode;
	}

	public String getPendDescription() {
		return pendDescription;
	}

	public void setPendDescription(String pendDescription) {
		this.pendDescription = pendDescription;
	}

	public String getScimHandlingCode() {
		return scimHandlingCode;
	}

	public void setScimHandlingCode(String scimHandlingCode) {
		this.scimHandlingCode = scimHandlingCode;
	}

	public String getScimHandlingDescription() {
		return scimHandlingDescription;
	}

	public void setScimHandlingDescription(String scimHandlingDescription) {
		this.scimHandlingDescription = scimHandlingDescription;
	}

}
