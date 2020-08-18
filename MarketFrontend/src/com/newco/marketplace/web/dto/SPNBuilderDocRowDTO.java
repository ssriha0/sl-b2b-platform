package com.newco.marketplace.web.dto;

public class SPNBuilderDocRowDTO extends SerializedBaseDTO
{

	private static final long serialVersionUID = 1200683118105951708L;
	private String id;
	private String name;
	private String description;
	private String checked;
	
	public SPNBuilderDocRowDTO(String id, String name, String description, boolean checked)
	{
		this.id = id;
		this.name = name;
		this.description = description;
		
		if(checked)
			this.checked = "checked";
		else
			this.checked = "";
	}
	
	public SPNBuilderDocRowDTO(){
		
	}
	
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
