package com.newco.marketplace.web.dto;

public class DropdownOptionDTO extends SerializedBaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6187897933879712765L;
	private String label;
	private String value;
	private String selected;
	
	public DropdownOptionDTO(String label, String value, String selected)
	{
		this.label = label;
		this.value = value;
		this.selected = selected;
	}
	
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
}
