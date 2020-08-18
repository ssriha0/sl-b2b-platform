package com.newco.marketplace.webservices.dto.serviceorder;

import org.codehaus.xfire.aegis.type.java5.XmlElement;

public class AddPartRequest extends CreatePartRequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4550265842282052063L;

	@Override
	@XmlElement(minOccurs="1", nillable=false)
	public String getSoId() {
		return super.getSoId();
	}

}
