package com.servicelive.orderfulfillment.serviceinterface.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.servicelive.orderfulfillment.domain.ServiceOrder;

@XmlRootElement()
public class CreateOrderRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 75809144983175447L;

	private Identification identification;
	private ServiceOrder serviceOrder;
	private String buyerState;
	
	@XmlElement() 
	public void setIdentification(Identification identification) {
		this.identification = identification;
	}
	public Identification getIdentification() {
		return identification;
	}

	@XmlElement() 
	public void setServiceOrder(ServiceOrder serviceOrder) {
		this.serviceOrder = serviceOrder;
	}
	public ServiceOrder getServiceOrder() {
		return serviceOrder;
	}
	@XmlElement() 
	public void setBuyerState(String buyerState) {
		this.buyerState = buyerState;
	}
	public String getBuyerState() {
		return buyerState;
	}
}
