package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("parts")
public class InvoicePartVO {
	
	
	@XStreamAlias("partStatus")
	private String partStatus;
	
	@XStreamAlias("partDesc")
	private String partDesc;


	public String getPartStatus() {
		return partStatus;
	}

	public void setPartStatus(String partStatus) {
		this.partStatus = partStatus;
	}

	public String getPartDesc() {
		return partDesc;
	}

	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}
	
	
	
}
