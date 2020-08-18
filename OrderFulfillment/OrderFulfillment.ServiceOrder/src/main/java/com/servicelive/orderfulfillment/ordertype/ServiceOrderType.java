package com.servicelive.orderfulfillment.ordertype;

import java.util.HashMap;
import java.util.Map;

import com.servicelive.orderfulfillment.common.ServiceOrderException;

public class ServiceOrderType implements IServiceOrderType {
	static private Map<Long, ServiceOrderType> soTypesByBuyer = new HashMap<Long, ServiceOrderType>();
	static private Map<String, ServiceOrderType> soTypesByName = new HashMap<String, ServiceOrderType>();
    static private Map<Integer, ServiceOrderType> soTypesByRole = new HashMap<Integer, ServiceOrderType>();

	private String WFProcessDefinitionName;
	private String soTypeName;
	private Long buyerId;
    private Integer buyerRoleId;
	
	// this should be mapped to String config
	// most of the time jBPM commands have the same meaning for all order type
	// sometimes depending on the order type it could be useful to change specific commands 
	// or skip them all together (i.e. run different rules, avoid sending e-mail, etc.)
	// to provide capability for order type to change behavior of jBPM commands we have
	// the following method
	// the idea here is to call out specific commands that are changed for the given order type
	// and map them to custom implementations or to empty strings (which suppresses the command)
	//
	// To use this facility order type object should:
	// - define a map in Spring configuration
	// - map command name to bean name containing order specific command implementation 
	// (or empty string to suppress the command)
	//
	public static ServiceOrderType getServiceOrderType(Long buyerId, Integer buyerRoleId) {
		ServiceOrderType sot = soTypesByBuyer.get(buyerId);
		if (sot == null) sot = getServiceOrderType(buyerRoleId);
		return sot;
	}
    public static ServiceOrderType getServiceOrderType(Integer buyerRoleId){
        ServiceOrderType sot = soTypesByRole.get(buyerRoleId);
		if (sot == null) sot = getServiceOrderType("");
		return sot;
    }
	public static ServiceOrderType getServiceOrderType(String soType) {
		ServiceOrderType sot = soTypesByName.get(soType);
		if (sot == null) sot = soTypesByBuyer.get(-1L);
		if (sot == null) throw new ServiceOrderException("There is no default Service Order Type");
		return sot;
	}

	public String getWFProcessDefinitionName() {
		return WFProcessDefinitionName;
	}
	public void setWFProcessDefinitionName(String pname){
		WFProcessDefinitionName=pname;
	}
	public String getSoTypeName() {
		return soTypeName;
	}
	public void setSoTypeName(String soTypeName) {
		this.soTypeName = soTypeName;
		soTypesByName.put(soTypeName, this);
	}
	public Long getBuyerId() {
		return buyerId;
	}
	public Integer getBuyerRoleId(){
		return buyerRoleId;
	}
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
		soTypesByBuyer.put(buyerId, this);
	}

    public void setBuyerRoleId(Integer buyerRoleId) {
        this.buyerRoleId = buyerRoleId;
        soTypesByRole.put(buyerRoleId, this);
    }
}
