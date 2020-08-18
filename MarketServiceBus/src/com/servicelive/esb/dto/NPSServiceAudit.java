package com.servicelive.esb.dto;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("InstallationServiceAudit")
public class NPSServiceAudit implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@XStreamImplicit(itemFieldName="AuditRecord")
	private List<NPSAuditRecord> auditRecords;

	public List<NPSAuditRecord> getAuditRecords() {
		return auditRecords;
	}

	public void setAuditRecords(List<NPSAuditRecord> auditRecords) {
		this.auditRecords = auditRecords;
	}




}
