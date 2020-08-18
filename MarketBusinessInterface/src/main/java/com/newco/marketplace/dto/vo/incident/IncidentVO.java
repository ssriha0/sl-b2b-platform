package com.newco.marketplace.dto.vo.incident;

import com.sears.os.vo.SerializableBaseVO;

public class IncidentVO extends SerializableBaseVO {
	
	private static final long serialVersionUID = -2985790346301101841L;
	
	private Integer incidentId;
	private String clientIncidentId;
	private Integer clientId;
	private Integer warrantyContractId;
	
	public Integer getIncidentId() {
		return incidentId;
	}
	public void setIncidentId(Integer incidentId) {
		this.incidentId = incidentId;
	}
	public String getClientIncidentId() {
		return clientIncidentId;
	}
	public void setClientIncidentId(String clientIncidentId) {
		this.clientIncidentId = clientIncidentId;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public Integer getWarrantyContractId() {
		return warrantyContractId;
	}
	public void setWarrantyContractId(Integer warrantyContractId) {
		this.warrantyContractId = warrantyContractId;
	}
}
