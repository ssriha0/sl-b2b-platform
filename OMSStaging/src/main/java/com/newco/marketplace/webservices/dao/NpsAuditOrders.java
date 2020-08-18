package com.newco.marketplace.webservices.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * NpsAuditOrders entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "nps_audit_orders", catalog = "supplier_prod")
public class NpsAuditOrders extends AbstractNpsAuditOrders implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public NpsAuditOrders() {
	}

	/** minimal constructor */
	public NpsAuditOrders(NpsAuditFiles npsAuditFiles, Timestamp createdDate,
			Integer processId, Integer returnCode, Integer stagingOrderId) {
		super(npsAuditFiles, createdDate, processId, returnCode, stagingOrderId);
	}

	/** full constructor */
	public NpsAuditOrders(NpsAuditFiles npsAuditFiles, Timestamp createdDate,
			Integer processId, Integer returnCode, Integer stagingOrderId,
			List<NpsAuditOrderMessages> npsAuditOrderMessageses) {
		super(npsAuditFiles, createdDate, processId, returnCode,
				stagingOrderId, npsAuditOrderMessageses);
	}

}
