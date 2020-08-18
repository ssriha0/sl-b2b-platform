package com.newco.marketplace.dto.vo.leadsmanagement;

import java.util.List;

public class ServiceVO {
	private String serviceName;
	private Boolean scope;
	public Boolean getScope() {
		return scope;
	}
	public void setScope(Boolean scope) {
		this.scope = scope;
	}
	//private List<String> typeList;
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	/*public List<String> getTypeList() {
		return typeList;
	}
	public void setTypeList(List<String> typeList) {
		this.typeList = typeList;
	}*/


}
