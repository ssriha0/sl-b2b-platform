package com.newco.marketplace.webservices.dao;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractLuAuditOwners entity provides the base persistence definition of the
 * LuAuditOwners entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractLuAuditOwners implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 2112068461344440440L;
	private Integer auditOwnerId;
	private String ownerName;
	private String emailIds;
	
	// Constructors

	/** default constructor */
	public AbstractLuAuditOwners() {
	}

	/** minimal constructor */
	public AbstractLuAuditOwners(String ownerName, String emailIds) {
		this.ownerName = ownerName;
		this.emailIds = emailIds;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "audit_owner_id", unique = true, nullable = false)
	public Integer getAuditOwnerId() {
		return this.auditOwnerId;
	}

	public void setAuditOwnerId(Integer auditOwnerId) {
		this.auditOwnerId = auditOwnerId;
	}

	@Column(name = "owner_name", nullable = false, length = 25)
	public String getOwnerName() {
		return this.ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	@Column(name = "email_ids", nullable = false, length = 256)
	public String getEmailIds() {
		return this.emailIds;
	}

	public void setEmailIds(String emailIds) {
		this.emailIds = emailIds;
	}
}