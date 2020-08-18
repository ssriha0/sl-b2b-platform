package com.servicelive.domain.spn.network;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



/**
 * Class to hold exceptions types,gracePeriod values (for SL_18018)
 *
 */
public class SPNComplianceFilterVO  implements Serializable
{

	private static final long serialVersionUID = 5254692630787236707L;
	private List<String> selectedRequirements;
	private List<String> selectedComplianceStatus; 
	private List<String> selectedMarkets; 
	private List<String> selectedStates;
	
	public List<String> getSelectedRequirements() {
		return selectedRequirements;
	}
	public void setSelectedRequirements(List<String> selectedRequirements) {
		this.selectedRequirements = selectedRequirements;
	}
	
	public List<String> getSelectedComplianceStatus() {
		return selectedComplianceStatus;
	}
	public void setSelectedComplianceStatus(List<String> selectedComplianceStatus) {
		this.selectedComplianceStatus = selectedComplianceStatus;
	}
	public List<String> getSelectedMarkets() {
		return selectedMarkets;
	}
	public void setSelectedMarkets(List<String> selectedMarkets) {
		this.selectedMarkets = selectedMarkets;
	}
	public List<String> getSelectedStates() {
		return selectedStates;
	}
	public void setSelectedStates(List<String> selectedStates) {
		this.selectedStates = selectedStates;
	} 

	
	
}
