package com.newco.marketplace.api.mobile.beans.addinvoicesoproviderpart;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing part information.
 * @author Infosys
 *
 */
@XSD(name = "addInvoiceSOProviderPartRequest.xsd", path = "/resources/schemas/mobile/v2_0/")
@XmlRootElement(name = "addInvoiceSOProviderPartRequest")
@XStreamAlias("addInvoiceSOProviderPartRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddInvoiceSOProviderPartRequest {
	
	@XStreamAlias("currentTripNo")
	private String currentTripNo;
	
	@XStreamAlias("tripStatus")
	private String tripStatus;
	
	@XStreamAlias("providerPartsList")
	private PartsInvoiceList providerPartsList;


	public String getCurrentTripNo() {
		return currentTripNo;
	}

	public void setCurrentTripNo(String currentTripNo) {
		this.currentTripNo = currentTripNo;
	}

	public PartsInvoiceList getProviderPartsList() {
		return providerPartsList;
	}

	public void setProviderPartsList(PartsInvoiceList providerPartsList) {
		this.providerPartsList = providerPartsList;
	}

	public String getTripStatus() {
		return tripStatus;
	}

	public void setTripStatus(String tripStatus) {
		this.tripStatus = tripStatus;
	}


	
}
