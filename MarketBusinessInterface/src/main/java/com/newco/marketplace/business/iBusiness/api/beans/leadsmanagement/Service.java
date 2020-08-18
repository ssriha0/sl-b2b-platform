package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
@XStreamAlias("Service")
public class Service {
	@XStreamAlias("ServiceScope")
	private Boolean serviceScope;
	@XStreamAlias("Name")
	@XStreamAsAttribute() 
	private String serviceName;
	public Boolean getServiceScope() {
		return serviceScope;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public void setServiceScope(Boolean serviceScope) {
		this.serviceScope = serviceScope;
	}
    public String getServiceName() {
			return serviceName;
		}


}
