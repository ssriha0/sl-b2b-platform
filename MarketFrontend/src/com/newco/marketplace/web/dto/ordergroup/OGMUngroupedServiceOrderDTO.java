package com.newco.marketplace.web.dto.ordergroup;

import com.newco.marketplace.web.dto.SerializedBaseDTO;

public class OGMUngroupedServiceOrderDTO extends SerializedBaseDTO
{
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String description;
	private String checked;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	
	
	
}
