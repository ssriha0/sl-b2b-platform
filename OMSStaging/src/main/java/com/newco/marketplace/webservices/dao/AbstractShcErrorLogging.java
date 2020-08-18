package com.newco.marketplace.webservices.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * AbstractShcErrorLogging entity provides the base persistence definition of
 * the ShcErrorLogging entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractShcErrorLogging implements java.io.Serializable {

	// Fields

	private Integer shcErrorLoggingId;
	private ShcOrderTransaction shcOrderTransaction;
	private String errorCode;
	private String errorMessage;
	private String modifiedBy;

	// Constructors

	/** default constructor */
	public AbstractShcErrorLogging() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractShcErrorLogging(Integer shcErrorLoggingId) {
		this.shcErrorLoggingId = shcErrorLoggingId;
	}

	/** full constructor */
	public AbstractShcErrorLogging(Integer shcErrorLoggingId,
			ShcOrderTransaction shcOrderTransaction, String errorCode,
			String errorMessage, Date createdDate, Date modifiedDate,
			String modifiedBy) {
		this.shcErrorLoggingId = shcErrorLoggingId;
		this.shcOrderTransaction = shcOrderTransaction;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.modifiedBy = modifiedBy;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "shc_error_logging_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getShcErrorLoggingId() {
		return this.shcErrorLoggingId;
	}

	public void setShcErrorLoggingId(Integer shcErrorLoggingId) {
		this.shcErrorLoggingId = shcErrorLoggingId;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "shc_order_transaction_id", unique = false, nullable = true, insertable = true, updatable = true)
	public ShcOrderTransaction getShcOrderTransaction() {
		return this.shcOrderTransaction;
	}

	public void setShcOrderTransaction(ShcOrderTransaction shcOrderTransaction) {
		this.shcOrderTransaction = shcOrderTransaction;
	}

	@Column(name = "error_code", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Column(name = "error_message", unique = false, nullable = true, insertable = true, updatable = true, length = 1000)
	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Column(name = "modified_by", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}