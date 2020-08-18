package com.newco.marketplace.web.dto;

public class AvailableLinkedAcctDTO extends SerializedBaseDTO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8060046077031138555L;
	Boolean selected;
	String description;
	String type;
	String institute;
	String account;
	String status;
	
	public AvailableLinkedAcctDTO(
			Boolean selected,
			String description,
			String type,
			String institute,
			String account,
			String status
	)
	{
		this.selected = selected;
		this.description = description;
		this.type = type;
		this.institute = institute;
		this.account = account;
		this.status = status;				
	}
	
	
	public Boolean getSelected()
	{
		return selected;
	}
	public void setSelected(Boolean selected)
	{
		this.selected = selected;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getInstitute()
	{
		return institute;
	}
	public void setInstitute(String institute)
	{
		this.institute = institute;
	}
	public String getAccount()
	{
		return account;
	}
	public void setAccount(String account)
	{
		this.account = account;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
}
