package com.newco.marketplace.web.dto;

public class FMHistoryTabDTO extends SerializedBaseDTO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5822135831655950631L;
	private String selectedDropdown;

	public String getSelectedDropdown()
	{
		return selectedDropdown;
	}

	public void setSelectedDropdown(String selectedDropdown)
	{
		this.selectedDropdown = selectedDropdown;
	}
	
}
