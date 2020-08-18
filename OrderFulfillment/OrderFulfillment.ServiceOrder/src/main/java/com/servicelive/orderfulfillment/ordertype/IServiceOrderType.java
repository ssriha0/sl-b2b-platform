package com.servicelive.orderfulfillment.ordertype;

public interface IServiceOrderType {
	public String getWFProcessDefinitionName() ;
	public void setWFProcessDefinitionName(String pname);
	public String getSoTypeName();
	public void setSoTypeName(String soTypeName);
	public Long getBuyerId();
	public void setBuyerId(Long buyerId);
}
