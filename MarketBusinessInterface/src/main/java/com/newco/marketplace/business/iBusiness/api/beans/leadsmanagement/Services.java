package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("Services")
public class Services {
	
	@XStreamImplicit(itemFieldName = "SubService")
	private List<SubService> subService;
    public List<SubService> getSubService() {
		return subService;
	}

	public void setSubService(List<SubService> subService) {
		this.subService = subService;
	}
	

}
