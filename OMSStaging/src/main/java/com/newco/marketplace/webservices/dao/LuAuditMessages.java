package com.newco.marketplace.webservices.dao;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * LuAuditMessages entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "lu_audit_messages", catalog = "supplier_prod")
public class LuAuditMessages extends AbstractLuAuditMessages implements
		java.io.Serializable {

	// Constructors

	private static final long serialVersionUID = 3643849815400628429L;

	/** default constructor */
	public LuAuditMessages() {
	}

	/** minimal constructor */
	public LuAuditMessages(LuAuditOwners luAuditOwners, String message,
			String npsStatus, Boolean successInd, Boolean reportable) {
		super(luAuditOwners, message, npsStatus, successInd, reportable);
	}
}
