package com.newco.marketplace.webservices.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * NpsAuditFiles entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "nps_audit_files", catalog = "supplier_prod")
public class NpsAuditFiles extends AbstractNpsAuditFiles implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public NpsAuditFiles() {
	}

	/** minimal constructor */
	public NpsAuditFiles(Timestamp createdDate, String fileName,
			Integer numSuccess, Integer numFailure) {
		super(createdDate, fileName, numSuccess, numFailure);
	}

	/** full constructor */
	public NpsAuditFiles(Timestamp createdDate, String fileName,
			Integer numSuccess, Integer numFailure,
			List<NpsAuditOrders> npsAuditOrderses) {
		super(createdDate, fileName, numSuccess, numFailure, npsAuditOrderses);
	}

}
