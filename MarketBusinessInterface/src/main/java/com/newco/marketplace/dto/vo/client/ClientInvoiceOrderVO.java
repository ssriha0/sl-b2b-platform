package com.newco.marketplace.dto.vo.client;

import java.util.Date;

import com.newco.marketplace.webservices.base.CommonVO;

public class ClientInvoiceOrderVO extends CommonVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 0L;
	
	private Integer clientInvoiceOrderID;
	private Integer clientID;
	private Integer skuID;
	private String serviceOrderID;
	private Date createdDate;
	private String incidentID;
	private Date modifiedDate;
	private String modifiedBy;
	
	public Integer getClientInvoiceOrderID() {
		return clientInvoiceOrderID;
	}
	
	public Integer getClientID() {
		return clientID;
	}
	public void setClientID(Integer clientID) {
		this.clientID = clientID;
	}
	public Integer getSkuID() {
		return skuID;
	}
	public void setSku(Integer skuID) {
		this.skuID = skuID;
	}
	public String getServiceOrderID() {
		return serviceOrderID;
	}
	public void setServiceOrderID(String serviceOrderID) {
		this.serviceOrderID = serviceOrderID;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getIncidentID() {
		return incidentID;
	}

	public void setIncidentID(String incidentID) {
		this.incidentID = incidentID;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
}
