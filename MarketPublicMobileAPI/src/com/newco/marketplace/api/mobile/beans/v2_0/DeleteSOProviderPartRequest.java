package com.newco.marketplace.api.mobile.beans.v2_0;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.InvoicePartIds;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing part information.
 * @author Infosys
 *
 */
@XSD(name = "deleteSOProviderPartRequest.xsd", path = "/resources/schemas/mobile/v2_0/")
@XmlRootElement(name = "deleteSOProviderPartRequest")
@XStreamAlias("deleteSOProviderPartRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeleteSOProviderPartRequest {
	
	@XStreamAlias("currentTripNo")
	private String currentTripNo;
	
	@XStreamAlias("tripStatus")
	private String tripStatus;
	
	@XStreamAlias("invoiceIdList")
	private InvoicePartIds invoiceIdList;

	public String getCurrentTripNo() {
		return currentTripNo;
	}

	public void setCurrentTripNo(String currentTripNo) {
		this.currentTripNo = currentTripNo;
	}

	public InvoicePartIds getInvoiceIdList() {
		return invoiceIdList;
	}

	public void setInvoiceIdList(InvoicePartIds invoiceIdList) {
		this.invoiceIdList = invoiceIdList;
	}

	public String getTripStatus() {
		return tripStatus;
	}

	public void setTripStatus(String tripStatus) {
		this.tripStatus = tripStatus;
	}

	
}
