package com.servicelive.orderfulfillment.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "lead_process_map") // so to process mapping table
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class LeadProcess {
	
	@Id @Column(name = "sl_lead_id")
	private String slLeadId;
	
	@Column(name = "buyer_id")
	private Long buyerId;
	
	@Column(name = "created_date")
	private Date createdDate = new Date();
		
	@Column(name = "process_id")
	private String processId;

	public String getSlLeadId() {
		return slLeadId;
	}

	public void setSlLeadId(String slLeadId) {
		this.slLeadId = slLeadId;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}
	
	public LeadProcess(String leadId, String processId){
		this.slLeadId=leadId;
		this.processId=processId;
	}

	public LeadProcess() {
		
	}
	
}
