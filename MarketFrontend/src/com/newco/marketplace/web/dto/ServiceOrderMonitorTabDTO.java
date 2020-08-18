package com.newco.marketplace.web.dto;

import java.util.ArrayList;

import test.newco.test.marketplace.mockdb.ServiceOrderStatus;
import test.newco.test.marketplace.mockdb.ServiceOrderSubStatus;



public class ServiceOrderMonitorTabDTO extends SerializedBaseDTO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2650172279841075524L;
	private String id="foo id";
	private String title="foo title";
	private ArrayList<ServiceOrderDTO> soList= new ArrayList<ServiceOrderDTO>();
	private ArrayList<ServiceOrderStatus> statusList = new ArrayList<ServiceOrderStatus>();
	private ArrayList<ServiceOrderSubStatus> substatusList = new ArrayList<ServiceOrderSubStatus>();
	private String statusDisabled="";
	private Integer status;
	private String tabSelected;
	
	
	//New DTO BEGIN
	private String tabTitle;
	private Integer tabCount = 0;

	public Integer getTabCount() {
		return tabCount;
	}
	public void setTabCount(Integer tabCount) {
		this.tabCount += tabCount;
	}
	public String getTabTitle() {
		return tabTitle;
	}
	public void setTabTitle(String tabTitle) {
		this.tabTitle = tabTitle;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return tabTitle + "\t" + tabCount;
	}
	//protected void
	//New DTO END
	
	
	
	
	
	public ArrayList<ServiceOrderDTO> getSoList()
	{
		return soList;
	}
	public void setSoList(ArrayList<ServiceOrderDTO> soList)
	{
		this.soList = soList;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public ArrayList<ServiceOrderSubStatus> getSubstatusList()
	{
		return substatusList;
	}
	public void setSubstatusList(ArrayList<ServiceOrderSubStatus> substatusList)
	{
		this.substatusList = substatusList;
	}
	public String getStatusDisabled()
	{
		return statusDisabled;
	}
	public void setStatusDisabled(boolean disabled)
	{
		if(disabled)
			this.statusDisabled = "disabled";
		else
			this.statusDisabled = "";
	}
	public ArrayList<ServiceOrderStatus> getStatusList()
	{
		return statusList;
	}
	public void setStatusList(ArrayList<ServiceOrderStatus> statusList)
	{
		this.statusList = statusList;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getTabSelected() {
		return tabSelected;
	}
	public void setTabSelected(String tabSelected) {
		this.tabSelected = tabSelected;
	}
}
