package com.newco.marketplace.webservices.dto.serviceorder;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.xfire.aegis.type.java5.XmlElement;

public class CancelSORequest extends ABaseWebserviceRequest{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1291050688073301285L;
	private String serviceOrderID;
	private String buyerName;
	private String cancelComment;
	private double cancelAmt;
	
	
	
	public String getCancelComment() {
		return cancelComment;
	}

	public void setCancelComment(String cancelComment) {
		this.cancelComment = cancelComment;
	}

	public double getCancelAmt() {
		return cancelAmt;
	}

	public void setCancelAmt(double cancelAmt) {
		this.cancelAmt = cancelAmt;
	}

	

	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("serviceOrderID", getServiceOrderID())
			.append("buyerId", getBuyerId())
			.append("cancelComment", getCancelComment())
			.append("cancelAmt", getCancelAmt())	
			.toString();
	}
	
	public String getServiceOrderID() {
		return serviceOrderID;
	}
	
	@XmlElement(minOccurs="1", nillable=false)
	public void setServiceOrderID(String serviceOrder) {
		this.serviceOrderID = serviceOrder;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

}
