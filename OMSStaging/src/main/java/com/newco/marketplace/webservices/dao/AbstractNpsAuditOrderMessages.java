package com.newco.marketplace.webservices.dao;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * AbstractNpsAuditOrderMessages entity provides the base persistence definition
 * of the NpsAuditOrderMessages entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractNpsAuditOrderMessages implements
		java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 4177924351368827427L;
	private Integer auditOrderMessageId;
	private NpsAuditOrders npsAuditOrders;
	private LuAuditMessages luAuditMessages;
	private Timestamp createdDate;

	// Constructors

	/** default constructor */
	public AbstractNpsAuditOrderMessages() {
	}

	/** full constructor */
	public AbstractNpsAuditOrderMessages(NpsAuditOrders npsAuditOrders,
			LuAuditMessages luAuditMessages, Timestamp createdDate) {
		this.npsAuditOrders = npsAuditOrders;
		this.luAuditMessages = luAuditMessages;
		this.createdDate = createdDate;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "audit_order_message_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getAuditOrderMessageId() {
		return this.auditOrderMessageId;
	}

	public void setAuditOrderMessageId(Integer auditOrderMessageId) {
		this.auditOrderMessageId = auditOrderMessageId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "audit_order_id", nullable = true, insertable = true, updatable = true)
	public NpsAuditOrders getNpsAuditOrders() {
		return this.npsAuditOrders;
	}

	public void setNpsAuditOrders(NpsAuditOrders npsAuditOrders) {
		this.npsAuditOrders = npsAuditOrders;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "audit_message_id", nullable = false)
	public LuAuditMessages getLuAuditMessages() {
		return this.luAuditMessages;
	}

	public void setLuAuditMessages(LuAuditMessages luAuditMessages) {
		this.luAuditMessages = luAuditMessages;
	}

	@Column(name = "created_date", nullable = false, length = 19)
	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

}