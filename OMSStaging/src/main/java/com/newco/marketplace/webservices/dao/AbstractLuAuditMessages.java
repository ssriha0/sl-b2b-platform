package com.newco.marketplace.webservices.dao;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * AbstractLuAuditMessages entity provides the base persistence definition of
 * the LuAuditMessages entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractLuAuditMessages implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 4415003007196065554L;
	private Integer auditMessageId;
	private LuAuditOwners luAuditOwners;
	private String message;
	private String workInstruction;
	private String npsStatus;
	private Boolean successInd;
	private Boolean reportable;
	
	// Constructors

	/** default constructor */
	public AbstractLuAuditMessages() {
	}

	/** minimal constructor */
	public AbstractLuAuditMessages(LuAuditOwners luAuditOwners, String message,
			String npsStatus, Boolean successInd, Boolean reportable) {
		this.luAuditOwners = luAuditOwners;
		this.message = message;
		this.npsStatus = npsStatus;
		this.successInd = successInd;
		this.reportable = reportable;
	}

	/** full constructor */
	public AbstractLuAuditMessages(LuAuditOwners luAuditOwners, String message,
			String workInstruction, String npsStatus, Boolean successInd,
			Boolean reportable) {
		this.luAuditOwners = luAuditOwners;
		this.message = message;
		this.workInstruction = workInstruction;
		this.npsStatus = npsStatus;
		this.successInd = successInd;
		this.reportable = reportable;
		
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "audit_message_id", unique = true, nullable = false)
	public Integer getAuditMessageId() {
		return this.auditMessageId;
	}

	public void setAuditMessageId(Integer auditMessageId) {
		this.auditMessageId = auditMessageId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "audit_owner_id", nullable = false)
	public LuAuditOwners getLuAuditOwners() {
		return this.luAuditOwners;
	}

	public void setLuAuditOwners(LuAuditOwners luAuditOwners) {
		this.luAuditOwners = luAuditOwners;
	}

	@Column(name = "message", nullable = false, length = 100)
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "work_instruction", length = 100)
	public String getWorkInstruction() {
		return this.workInstruction;
	}

	public void setWorkInstruction(String workInstruction) {
		this.workInstruction = workInstruction;
	}

	@Column(name = "nps_status", nullable = false, length = 4)
	public String getNpsStatus() {
		return this.npsStatus;
	}

	public void setNpsStatus(String npsStatus) {
		this.npsStatus = npsStatus;
	}

	@Column(name = "success_ind", nullable = false)
	public Boolean getSuccessInd() {
		return this.successInd;
	}

	public void setSuccessInd(Boolean successInd) {
		this.successInd = successInd;
	}

	@Column(name = "reportable", nullable = false)
	public Boolean getReportable() {
		return this.reportable;
	}

	public void setReportable(Boolean reportable) {
		this.reportable = reportable;
	}
}