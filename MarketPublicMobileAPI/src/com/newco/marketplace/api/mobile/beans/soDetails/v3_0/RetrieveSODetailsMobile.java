package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing service order details.
 * @author Infosys
 *
 */
@XStreamAlias("serviceOrder")
@XmlAccessorType(XmlAccessType.FIELD)
public class RetrieveSODetailsMobile {
		
	
	@XStreamAlias("soDetails")
	private ServiceOrderDetails soDetails;

	/**
	 * @return the soDetails
	 */
	public ServiceOrderDetails getSoDetails() {
		return soDetails;
	}

	/**
	 * @param soDetails the soDetails to set
	 */
	public void setSoDetails(ServiceOrderDetails soDetails) {
		this.soDetails = soDetails;
	}
	
	
    
}
