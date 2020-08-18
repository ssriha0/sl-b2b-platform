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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.newco.marketplace.webservices.dao.SpecialtyAddOn;

/**
 * AbstractShcOrderAddOn entity provides the base persistence definition of the
 * ShcOrderAddOn entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractShcOrderAddOn implements java.io.Serializable {

	// Fields

	private Integer shcOrderAddOnId;
	private SpecialtyAddOn specialtyAddOn;
	private ShcOrder shcOrder;
	private Double retailPrice;
	private Double margin;
	private Integer qty;
	private Integer miscInd;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;
	private String coverage;

	// Constructors

	/** default constructor */
	public AbstractShcOrderAddOn() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractShcOrderAddOn(Integer shcOrderAddOnId) {
		this.shcOrderAddOnId = shcOrderAddOnId;
	}

	/** full constructor */
	public AbstractShcOrderAddOn(Integer shcOrderAddOnId,
			SpecialtyAddOn specialtyAddOn, ShcOrder shcOrder,
			Double retailPrice, Double margin, Integer qty, Integer miscInd,
			Date createdDate, Date modifiedDate, String modifiedBy) {
		this.shcOrderAddOnId = shcOrderAddOnId;
		this.specialtyAddOn = specialtyAddOn;
		this.shcOrder = shcOrder;
		this.retailPrice = retailPrice;
		this.margin = margin;
		this.qty = qty;
		this.miscInd = miscInd;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "shc_order_add_on_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getShcOrderAddOnId() {
		return this.shcOrderAddOnId;
	}

	public void setShcOrderAddOnId(Integer shcOrderAddOnId) {
		this.shcOrderAddOnId = shcOrderAddOnId;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.EAGER)
	@JoinColumn(name = "specialty_add_on_id", unique = false, nullable = true, insertable = true, updatable = true)
	public SpecialtyAddOn getSpecialtyAddOn() {
		return this.specialtyAddOn;
	}

	public void setSpecialtyAddOn(SpecialtyAddOn specialtyAddOn) {
		this.specialtyAddOn = specialtyAddOn;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.EAGER)
	@JoinColumn(name = "shc_order_id", unique = false, nullable = true, insertable = true, updatable = true)
	public ShcOrder getShcOrder() {
		return this.shcOrder;
	}

	public void setShcOrder(ShcOrder shcOrder) {
		this.shcOrder = shcOrder;
	}

	@Column(name = "retail_price", unique = false, nullable = true, insertable = true, updatable = true, precision = 9)
	public Double getRetailPrice() {
		return this.retailPrice;
	}

	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}

	@Column(name = "margin", unique = false, nullable = true, insertable = true, updatable = true, precision = 9, scale = 4)
	public Double getMargin() {
		return this.margin;
	}

	public void setMargin(Double margin) {
		this.margin = margin;
	}

	@Column(name = "qty", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getQty() {
		return this.qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	@Column(name = "misc_ind", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getMiscInd() {
		return this.miscInd;
	}

	public void setMiscInd(Integer miscInd) {
		this.miscInd = miscInd;
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

	@Column(name = "modified_by", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	@Column(name = "coverage", unique = false, nullable = false, insertable = true, updatable = true, length = 2)
	public String getCoverage() {
		return coverage;
	}

	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

}