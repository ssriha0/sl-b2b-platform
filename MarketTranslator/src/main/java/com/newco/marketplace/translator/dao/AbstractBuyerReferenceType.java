package com.newco.marketplace.translator.dao;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AbstractBuyerReferenceType entity provides the base persistence definition of
 * the BuyerReferenceType entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractBuyerReferenceType implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 205261106992138014L;
	private Integer buyerRefTypeId;
	private BuyerMT buyer;
	private String refType;
	private String refDescr;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;
	private Integer sortOrder;
	private Byte activeInd;

	// Constructors

	/** default constructor */
	public AbstractBuyerReferenceType() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractBuyerReferenceType(Integer buyerRefTypeId, BuyerMT buyer,
			String refType) {
		this.buyerRefTypeId = buyerRefTypeId;
		this.buyer = buyer;
		this.refType = refType;
	}

	/** full constructor */
	public AbstractBuyerReferenceType(Integer buyerRefTypeId, BuyerMT buyer,
			String refType, String refDescr, Date createdDate,
			Date modifiedDate, String modifiedBy, Integer sortOrder,
			Byte activeInd) {
		this.buyerRefTypeId = buyerRefTypeId;
		this.buyer = buyer;
		this.refType = refType;
		this.refDescr = refDescr;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.sortOrder = sortOrder;
		this.activeInd = activeInd;
	}

	// Property accessors
	@Id
	@Column(name = "buyer_ref_type_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getBuyerRefTypeId() {
		return this.buyerRefTypeId;
	}

	public void setBuyerRefTypeId(Integer buyerRefTypeId) {
		this.buyerRefTypeId = buyerRefTypeId;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id", unique = false, nullable = false, insertable = true, updatable = true)
	public BuyerMT getBuyer() {
		return this.buyer;
	}

	public void setBuyer(BuyerMT buyer) {
		this.buyer = buyer;
	}

	@Column(name = "ref_type", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getRefType() {
		return this.refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

	@Column(name = "ref_descr", unique = false, nullable = true, insertable = true, updatable = true)
	public String getRefDescr() {
		return this.refDescr;
	}

	public void setRefDescr(String refDescr) {
		this.refDescr = refDescr;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "created_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "modified_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "modified_by", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "sort_order", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Column(name = "active_ind", unique = false, nullable = true, insertable = true, updatable = true)
	public Byte getActiveInd() {
		return this.activeInd;
	}

	public void setActiveInd(Byte activeInd) {
		this.activeInd = activeInd;
	}

}