package com.newco.marketplace.webservices.dao;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * NpsAuditOrderMessages entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "nps_audit_order_messages", catalog = "supplier_prod")
public class NpsAuditOrderMessages extends AbstractNpsAuditOrderMessages
		implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public NpsAuditOrderMessages() {
	}

	/** full constructor */
	public NpsAuditOrderMessages(NpsAuditOrders npsAuditOrders,
			LuAuditMessages luAuditMessages, Timestamp createdDate) {
		super(npsAuditOrders, luAuditMessages, createdDate);
	}

}
