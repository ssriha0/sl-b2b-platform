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
import javax.persistence.GenerationType;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

/**
 * AbstractNpsAuditFiles entity provides the base persistence definition of the
 * NpsAuditFiles entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractNpsAuditFiles implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -5134289665367975712L;
	private Integer auditFileId;
	private Timestamp createdDate;
	private String fileName;
	private Integer numSuccess;
	private Integer numFailure;
	private List<NpsAuditOrders> npsAuditOrderses = new ArrayList<NpsAuditOrders>();

	// Constructors

	/** default constructor */
	public AbstractNpsAuditFiles() {
	}

	/** minimal constructor */
	public AbstractNpsAuditFiles(Timestamp createdDate, String fileName,
			Integer numSuccess, Integer numFailure) {
		this.createdDate = createdDate;
		this.fileName = fileName;
		this.numSuccess = numSuccess;
		this.numFailure = numFailure;
	}

	/** full constructor */
	public AbstractNpsAuditFiles(Timestamp createdDate, String fileName,
			Integer numSuccess, Integer numFailure,
			List<NpsAuditOrders> npsAuditOrderses) {
		this.createdDate = createdDate;
		this.fileName = fileName;
		this.numSuccess = numSuccess;
		this.numFailure = numFailure;
		this.npsAuditOrderses = npsAuditOrderses;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "audit_file_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getAuditFileId() {
		return this.auditFileId;
	}

	public void setAuditFileId(Integer auditFileId) {
		this.auditFileId = auditFileId;
	}

	@Column(name = "created_date", nullable = false, length = 19)
	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "file_name", nullable = false, length = 100)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "num_success", nullable = false)
	public Integer getNumSuccess() {
		return this.numSuccess;
	}

	public void setNumSuccess(Integer numSuccess) {
		this.numSuccess = numSuccess;
	}

	@Column(name = "num_failure", nullable = false)
	public Integer getNumFailure() {
		return this.numFailure;
	}

	public void setNumFailure(Integer numFailure) {
		this.numFailure = numFailure;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "npsAuditFiles")
	public List<NpsAuditOrders> getNpsAuditOrderses() {
		return this.npsAuditOrderses;
	}

	public void setNpsAuditOrderses(List<NpsAuditOrders> npsAuditOrderses) {
		this.npsAuditOrderses = npsAuditOrderses;
	}

}