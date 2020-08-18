package com.newco.marketplace.webservices.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

/**
 * AbstractShcOrderTransaction entity provides the base persistence definition
 * of the ShcOrderTransaction entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractShcOrderTransaction implements
		java.io.Serializable {

	// Fields

	private Integer shcOrderTransactionId;
	private ShcOrder shcOrder;
	private String transactionType;
	private String xmlFragment;
	private String inputFileName;
	private String modifiedBy;
	private Set<ShcErrorLogging> shcErrorLoggings = new HashSet<ShcErrorLogging>(
			0);

	// Constructors

	/** default constructor */
	public AbstractShcOrderTransaction() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractShcOrderTransaction(Integer shcOrderTransactionId) {
		this.shcOrderTransactionId = shcOrderTransactionId;
	}

	/** full constructor */
	public AbstractShcOrderTransaction(Integer shcOrderTransactionId,
			ShcOrder shcOrder, String transactionType, String xmlFragment,
			String inputFileName, Date createdDate, Date modifiedDate,
			String modifiedBy, Set<ShcErrorLogging> shcErrorLoggings) {
		this.shcOrderTransactionId = shcOrderTransactionId;
		this.shcOrder = shcOrder;
		this.transactionType = transactionType;
		this.xmlFragment = xmlFragment;
		this.inputFileName = inputFileName;
		this.modifiedBy = modifiedBy;
		this.shcErrorLoggings = shcErrorLoggings;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "shc_order_transaction_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getShcOrderTransactionId() {
		return this.shcOrderTransactionId;
	}

	public void setShcOrderTransactionId(Integer shcOrderTransactionId) {
		this.shcOrderTransactionId = shcOrderTransactionId;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "shc_order_id", unique = false, nullable = true, insertable = true, updatable = true)
	public ShcOrder getShcOrder() {
		return this.shcOrder;
	}

	public void setShcOrder(ShcOrder shcOrder) {
		this.shcOrder = shcOrder;
	}

	@Column(name = "transaction_type", unique = false, nullable = true, insertable = true, updatable = true, length = 25)
	public String getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Column(name = "xml_fragment", unique = false, nullable = true, insertable = true, updatable = true)
	public String getXmlFragment() {
		return this.xmlFragment;
	}

	public void setXmlFragment(String xmlFragment) {
		this.xmlFragment = xmlFragment;
	}

	@Column(name = "input_file_name", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getInputFileName() {
		return this.inputFileName;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	@Column(name = "modified_by", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "shcOrderTransaction")
	public Set<ShcErrorLogging> getShcErrorLoggings() {
		return this.shcErrorLoggings;
	}

	public void setShcErrorLoggings(Set<ShcErrorLogging> shcErrorLoggings) {
		this.shcErrorLoggings = shcErrorLoggings;
	}

}