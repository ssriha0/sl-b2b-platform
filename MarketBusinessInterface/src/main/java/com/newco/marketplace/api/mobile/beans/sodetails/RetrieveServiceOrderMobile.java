package com.newco.marketplace.api.mobile.beans.sodetails;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing service order details.
 * @author Infosys
 *
 */

@XStreamAlias("serviceOrder")
@XmlRootElement(name = "serviceOrder")
@XmlAccessorType(XmlAccessType.FIELD)
public class RetrieveServiceOrderMobile {
	
	
	@XStreamAlias("soDetails")
	private ServiceOrderDetails soDetails;	
		
	@XStreamAlias("completionDetails")
	private CompletionDetails completionDetails;

	public ServiceOrderDetails getSoDetails() {
		return soDetails;
	}

	public void setSoDetails(ServiceOrderDetails soDetails) {
		this.soDetails = soDetails;
	}

	public CompletionDetails getCompletionDetails() {
		return completionDetails;
	}

	public void setCompletionDetails(CompletionDetails completionDetails) {
		this.completionDetails = completionDetails;
	}

    
}
