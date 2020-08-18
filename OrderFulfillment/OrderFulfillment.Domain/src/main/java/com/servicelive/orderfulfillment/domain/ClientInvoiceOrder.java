package com.servicelive.orderfulfillment.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "client_invoice_order")
@XmlRootElement()
public class ClientInvoiceOrder implements Serializable {

	private static final long serialVersionUID = 3018046803841822928L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="client_invoice_order_id")
	private Integer clientInvoiceOrderId;
	
	@Column(name="sku_id")
	private Integer skuId;
	
	@Column(name="so_id")
	private String soId;
	
	@Column(name="client_id")
	private String clientId;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	@Column(name="created_date")
	private Date createdDate;
	
	@Column(name="client_incident_id")
	private String clientIncidentId;
	
	@Column(name="modified_date")
	private Date modifiedDate;
	
	@Column(name="modified_by")
	private String modifiedBy;
	
			
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}	
	public Integer getClientInvoiceOrderId() {
		return clientInvoiceOrderId;
	}
	public void setClientInvoiceOrderId(Integer clientInvoiceOrderId) {
		this.clientInvoiceOrderId = clientInvoiceOrderId;
	}
	public Integer getSkuId() {
		return skuId;
	}
	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getClientIncidentId() {
		return clientIncidentId;
	}
	public void setClientIncidentId(String clientIncidentId) {
		this.clientIncidentId = clientIncidentId;
	}
	
}

