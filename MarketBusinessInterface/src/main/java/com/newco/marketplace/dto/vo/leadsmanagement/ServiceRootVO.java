package com.newco.marketplace.dto.vo.leadsmanagement;

import java.util.List;

public class ServiceRootVO {
	private String rootCategory;
	private List<ServiceVO> serviceVOs;
	
	public String getRootCategory() {
		return rootCategory;
	}
	public void setRootCategory(String rootCategory) {
		this.rootCategory = rootCategory;
	}
	public List<ServiceVO> getServiceVOs() {
		return serviceVOs;
	}
	public void setServiceVOs(List<ServiceVO> serviceVOs) {
		this.serviceVOs = serviceVOs;
	}
	
}
