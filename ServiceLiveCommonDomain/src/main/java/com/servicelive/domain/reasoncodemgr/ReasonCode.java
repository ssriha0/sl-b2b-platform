package com.servicelive.domain.reasoncodemgr;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * ReasonCode entity.
 * 
 */
@Entity
@Table(name = "reason_codes")
public class ReasonCode {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5405708666300608558L;

	/**
	 * 
	 */
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "reason_code_id", unique = true, nullable = false)
	private Integer reasonCodeId;
	
	@Column(name = "reason_code")
	private String reasonCode;
	
	@Column(name = "general_ind")
	private int general;
	
	@Column(name = "buyer_id")
	private Integer buyerId;
	
	@Column(name = "reason_code_type")
	private String reasonCodeType;
	
	@Column(name="created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Column(name = "modified_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;
	
	@Column(name="modified_by")
	private String modifiedBy;
	
	@Column(name="reason_code_status")
	private String reasonCodeStatus;
	
	@Transient
	private String fmtCreatedDate;
	
	
	
	// Constructors

	public String getFmtCreatedDate() {
		return fmtCreatedDate;
	}

	public void setFmtCreatedDate(String fmtCreatedDate) {
		this.fmtCreatedDate = fmtCreatedDate;
	}

	/** default constructor */
	public ReasonCode() {
		super();
	}

	public Integer getReasonCodeId() {
		return reasonCodeId;
	}

	public void setReasonCodeId(Integer reasonCodeId) {
		this.reasonCodeId = reasonCodeId;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}	

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public int getGeneral() {
		return general;
	}

	public void setGeneralInd(int general) {
		this.general = general;
	}

	public String getReasonCodeType() {
		return reasonCodeType;
	}

	public void setReasonCodeType(String reasonCodeType) {
		this.reasonCodeType = reasonCodeType;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getReasonCodeStatus() {
		return reasonCodeStatus;
	}

	public void setReasonCodeStatus(String reasonCodeStatus) {
		this.reasonCodeStatus = reasonCodeStatus;
	}
	
}