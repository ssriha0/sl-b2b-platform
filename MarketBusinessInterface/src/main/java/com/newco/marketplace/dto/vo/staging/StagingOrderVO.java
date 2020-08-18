package com.newco.marketplace.dto.vo.staging;

import com.newco.marketplace.webservices.base.CommonVO;

public class StagingOrderVO 
	extends CommonVO
{

	private static final long serialVersionUID = 0L;

	private Integer orderID;
	private String orderNumber;
	private String serviceOrderID;
	private String unitNumber;
	private String storeNumber = "0001";
	
	public String getServiceOrderID()
	{
		return serviceOrderID;
	}
	public void setServiceOrderID(String serviceOrderID)
	{
		this.serviceOrderID = serviceOrderID;
	}
	public String getStoreNumber()
	{
		return storeNumber;
	}
	public void setStoreNumber(String storeNumber)
	{
		this.storeNumber = storeNumber;
	}
	public String getUnitNumber()
	{
		return unitNumber;
	}
	public void setUnitNumber(String unitNumber)
	{
		this.unitNumber = unitNumber;
	}
	public Integer getOrderID()
	{
		return orderID;
	}
	public void setOrderID(Integer orderID)
	{
		this.orderID = orderID;
	}
	public String getOrderNumber()
	{
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber)
	{
		this.orderNumber = orderNumber;
	}
	
	
}
