package com.servicelive.spn.buyer.network;

import com.servicelive.spn.core.SPNBaseModel;

public class SPNMMNetworkDTO extends SPNBaseModel
{
	private static final long serialVersionUID = -6441821990345608697L;
	private String networkState;
	private String networkName;
	private String groupName;
	private Integer groupId;
	
	
	public String getNetworkState()
	{
		return networkState;
	}
	public void setNetworkState(String networkState)
	{
		this.networkState = networkState;
	}
	public String getNetworkName()
	{
		return networkName;
	}
	public void setNetworkName(String networkName)
	{
		this.networkName = networkName;
	}
	public String getGroupName()
	{
		return groupName;
	}
	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}
	public Integer getGroupId()
	{
		return groupId;
	}
	public void setGroupId(Integer groupId)
	{
		this.groupId = groupId;
	}
	
	
	
	
}
