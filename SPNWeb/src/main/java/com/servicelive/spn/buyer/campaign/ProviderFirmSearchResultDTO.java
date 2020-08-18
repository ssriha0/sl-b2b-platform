package com.servicelive.spn.buyer.campaign;

import java.io.Serializable;

public class ProviderFirmSearchResultDTO implements Serializable
{
	private static final long serialVersionUID = 4716930118684742178L;
	
	private Integer firmId;
	private String firmName;
	private String statusString;
	private String checked="1";
	
	public ProviderFirmSearchResultDTO(Integer firmId, String firmName, String status)
	{
		this.firmId = firmId;
		this.firmName = firmName;
		this.statusString = status;
	}
	
	
	public Integer getFirmId()
	{
		return firmId;
	}
	public void setFirmId(Integer firmId)
	{
		this.firmId = firmId;
	}
	public String getFirmName()
	{
		return firmName;
	}
	public void setFirmName(String firmName)
	{
		this.firmName = firmName;
	}


	public String getStatusString()
	{
		return statusString;
	}


	public void setStatusString(String statusString)
	{
		this.statusString = statusString;
	}


	public String getChecked() {
		return checked;
	}


	public void setChecked(String checked) {
		this.checked = checked;
	}
	
	
	
}
