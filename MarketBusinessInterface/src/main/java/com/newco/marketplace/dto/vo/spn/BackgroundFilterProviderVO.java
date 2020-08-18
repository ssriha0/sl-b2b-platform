package com.newco.marketplace.dto.vo.spn;

import java.io.Serializable;
import java.util.List;


//SL-19387
//Value Object class for fetching Advance Filter details for Background Check.
public class BackgroundFilterProviderVO  implements Serializable
{

	private static final long serialVersionUID = 5254692630787236707L;
	private String selectedReCertification;
	private String selectedSLBackgroundStatus;
	private String selectedSystemAction;
	private String selectedProviderFirmIds;
	private String selectedSpnMonitor;
	
	public String getSelectedProviderFirmIds() {
		return selectedProviderFirmIds;
	}
	public void setSelectedProviderFirmIds(String selectedProviderFirmIds) {
		this.selectedProviderFirmIds = selectedProviderFirmIds;
	}
	public String getSelectedReCertification() {
		return selectedReCertification;
	}
	public void setSelectedReCertification(String selectedReCertification) {
		this.selectedReCertification = selectedReCertification;
	}
	public String getSelectedSLBackgroundStatus() {
		return selectedSLBackgroundStatus;
	}
	public void setSelectedSLBackgroundStatus(String selectedSLBackgroundStatus) {
		this.selectedSLBackgroundStatus = selectedSLBackgroundStatus;
	}
	public String getSelectedSystemAction() {
		return selectedSystemAction;
	}
	public void setSelectedSystemAction(String selectedSystemAction) {
		this.selectedSystemAction = selectedSystemAction;
	}
	public String getSelectedSpnMonitor() {
		return selectedSpnMonitor;
	}
	public void setSelectedSpnMonitor(String selectedSpnMonitor) {
		this.selectedSpnMonitor = selectedSpnMonitor;
	}

	
	
	
	
	
}
