package com.newco.marketplace.web.dto;

import java.io.Serializable;

public class CheckboxDTO implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	private Integer value;
	private String label;
	private Boolean selected;
	
	public Integer getValue()
	{
		return value;
	}
	public void setValue(Integer value)
	{
		this.value = value;
	}
	public String getLabel()
	{
		return label;
	}
	public void setLabel(String label)
	{
		this.label = label;
	}
	public Boolean getSelected()
	{
		return selected;
	}
	public void setSelected(Boolean selected)
	{
		this.selected = selected;
	}
	
}
