package com.newco.marketplace.webservices.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

/**
 * AbstractNpsAuditOrders entity provides the base persistence definition of the
 * NpsAuditOrders entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractNpsAuditOrders implements java.io.Serializable {

	// Fields

	private Integer auditOrderId;
	private NpsAuditFiles npsAuditFiles;
	private Timestamp createdDate;
	private Integer processId;
	private Integer returnCode;
	private Integer stagingOrderId;
	private List<NpsAuditOrderMessages> npsAuditOrderMessageses = new ArrayList<NpsAuditOrderMessages>();

	// Constructors

	/** default constructor */
	public AbstractNpsAuditOrders() {
	}

	/** minimal constructor */
	public AbstractNpsAuditOrders(NpsAuditFiles npsAuditFiles,
			Timestamp createdDate, Integer processId, Integer returnCode,
			Integer stagingOrderId) {
		this.npsAuditFiles = npsAuditFiles;
		this.createdDate = createdDate;
		this.processId = processId;
		this.returnCode = returnCode;
		this.stagingOrderId = stagingOrderId;
	}

	/** full constructor */
	public AbstractNpsAuditOrders(NpsAuditFiles npsAuditFiles,
			Timestamp createdDate, Integer processId, Integer returnCode,
			Integer stagingOrderId,
			List<NpsAuditOrderMessages> npsAuditOrderMessageses) {
		this.npsAuditFiles = npsAuditFiles;
		this.createdDate = createdDate;
		this.processId = processId;
		this.returnCode = returnCode;
		this.stagingOrderId = stagingOrderId;
		this.npsAuditOrderMessageses = npsAuditOrderMessageses;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "audit_order_id", unique = true, nullable = false)
	public Integer getAuditOrderId() {
		return this.auditOrderId;
	}

	public void setAuditOrderId(Integer auditOrderId) {
		this.auditOrderId = auditOrderId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "audit_file_id", nullable = true, insertable = true, updatable = true)
	public NpsAuditFiles getNpsAuditFiles() {
		return this.npsAuditFiles;
	}

	public void setNpsAuditFiles(NpsAuditFiles npsAuditFiles) {
		this.npsAuditFiles = npsAuditFiles;
	}

	@Column(name = "created_date", nullable = false, length = 19)
	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "process_id", nullable = false)
	public Integer getProcessId() {
		return this.processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	@Column(name = "return_code", nullable = false)
	public Integer getReturnCode() {
		return this.returnCode;
	}

	public void setReturnCode(Integer returnCode) {
		this.returnCode = returnCode;
	}

	@Column(name = "staging_order_id", nullable = false)
	public Integer getStagingOrderId() {
		return this.stagingOrderId;
	}

	public void setStagingOrderId(Integer stagingOrderId) {
		this.stagingOrderId = stagingOrderId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "npsAuditOrders")
	public List<NpsAuditOrderMessages> getNpsAuditOrderMessageses() {
		return this.npsAuditOrderMessageses;
	}

	public void setNpsAuditOrderMessageses(
			List<NpsAuditOrderMessages> npsAuditOrderMessageses) {
		this.npsAuditOrderMessageses = npsAuditOrderMessageses;
	}

}