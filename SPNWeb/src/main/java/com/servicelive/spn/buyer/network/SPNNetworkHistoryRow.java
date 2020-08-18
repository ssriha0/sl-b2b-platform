package com.servicelive.spn.buyer.network;

import java.io.Serializable;

public class SPNNetworkHistoryRow implements Serializable
{
	private static final long serialVersionUID = -5000702768019809390L;
	
	private String date;
	private String name;
	private String action;
	
	public String getDate()
	{
		return date;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getAction()
	{
		return action;
	}
	public void setAction(String action)
	{
		this.action = action;
	}
	
	
	
}
