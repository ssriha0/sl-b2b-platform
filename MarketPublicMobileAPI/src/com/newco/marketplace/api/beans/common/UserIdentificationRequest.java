package com.newco.marketplace.api.beans.common;

import com.newco.marketplace.api.common.Identification;
import com.thoughtworks.xstream.annotations.XStreamAlias;

public class UserIdentificationRequest {
	
	@XStreamAlias("identification")
	Identification identification;

	/**
	 * Protected to make sure no one can create instance.
	 */
	protected UserIdentificationRequest() {
		
	}
	public Identification getIdentification() {
		return identification;
	}

	public void setIdentification(Identification identification) {
		this.identification = identification;
	}
	
	

}
