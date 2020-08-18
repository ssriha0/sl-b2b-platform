package com.newco.marketplace.webservices.dao;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * LuAuditOwners entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "lu_audit_owners", catalog = "supplier_prod")
public class LuAuditOwners extends AbstractLuAuditOwners implements
		java.io.Serializable {

	// Constructors

	private static final long serialVersionUID = -1784732306705146609L;

	/** default constructor */
	public LuAuditOwners() {
	}

	/** minimal constructor */
	public LuAuditOwners(String ownerName, String emailIds) {
		super(ownerName, emailIds);
	}
}
