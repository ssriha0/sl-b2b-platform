package com.servicelive.orderfulfillment.domain;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlTransient;

@MappedSuperclass
public class SOChild extends SORelative {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5644744945380061732L;
	
	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "so_id")
	protected ServiceOrder serviceOrder;


	@Override
	public ServiceOrder getServiceOrder() {
		return serviceOrder;
	}

	@Override
	@XmlTransient()
	public void setServiceOrder(ServiceOrder serviceOrder) {
		this.serviceOrder = serviceOrder;
	}	
}
