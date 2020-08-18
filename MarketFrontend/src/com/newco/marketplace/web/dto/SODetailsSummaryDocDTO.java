package com.newco.marketplace.web.dto;

public class SODetailsSummaryDocDTO extends SerializedBaseDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8271449852169728259L;
	private String name;
	private String description;
	private String size;
	
	public SODetailsSummaryDocDTO(String name, String desc, String size)
	{
		this.name = name;
		this.description = desc;
		this.size = size;
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
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}

}
