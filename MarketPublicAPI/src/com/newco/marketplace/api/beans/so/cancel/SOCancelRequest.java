package com.newco.marketplace.api.beans.so.cancel;

import com.newco.marketplace.api.beans.so.Identification;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing request information for 
 * the SOCancelService
 * @author Infosys
 *
 */
@XStreamAlias("cancelRequest")
public class SOCancelRequest {

	@XStreamAlias("identification")
	private Identification identification;

	public Identification getIdentification() {
		return identification;
	}

	public void setIdentification(Identification identification) {
		this.identification = identification;
	}

}
