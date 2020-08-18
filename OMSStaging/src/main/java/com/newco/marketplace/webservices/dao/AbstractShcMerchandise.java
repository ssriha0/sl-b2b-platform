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
 * AbstractShcMerchandise entity provides the base persistence definition of the
 * ShcMerchandise entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractShcMerchandise implements java.io.Serializable {

	// Fields

	private Integer shcMerchandiseId;
	private ShcOrder shcOrder;
	private String itemNo;
	private String code;
	private String description;
	private String specialty;
	private String brand;
	private String model;
	private String serialNumber;
	private String divisionCode;
	private String modifiedBy;

	// Constructors

	/** default constructor */
	public AbstractShcMerchandise() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractShcMerchandise(Integer shcMerchandiseId, String itemNo) {
		this.shcMerchandiseId = shcMerchandiseId;
		this.itemNo = itemNo;
	}

	/** full constructor */
	public AbstractShcMerchandise(Integer shcMerchandiseId, ShcOrder shcOrder,
			String itemNo, String code, String description, String specialty,
			String brand, String model,String serialNumber, String divisionCode, Date createdDate, Date modifiedDate,
			String modifiedBy) {
		this.shcMerchandiseId = shcMerchandiseId;
		this.shcOrder = shcOrder;
		this.itemNo = itemNo;
		this.code = code;
		this.description = description;
		this.specialty = specialty;
		this.brand = brand;
		this.model = model;
		this.serialNumber = serialNumber;
		this.divisionCode = divisionCode;
		this.modifiedBy = modifiedBy;
	}
 
	// Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "shc_merchandise_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getShcMerchandiseId() {
		return this.shcMerchandiseId;
	}

	public void setShcMerchandiseId(Integer shcMerchandiseId) {
		this.shcMerchandiseId = shcMerchandiseId;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "shc_order_id", unique = true, nullable = true, insertable = true, updatable = true)
	public ShcOrder getShcOrder() {
		return this.shcOrder;
	}

	public void setShcOrder(ShcOrder shcOrder) {
		this.shcOrder = shcOrder;
	}

	@Column(name = "item_no", unique = false, nullable = false, insertable = true, updatable = true, length = 30)
	public String getItemNo() {
		return this.itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	@Column(name = "code", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "description", unique = false, nullable = true, insertable = true, updatable = true)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "specialty", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getSpecialty() {
		return this.specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	@Column(name = "brand", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Column(name = "model", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	@Column(name = "serial_number", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	@Column(name = "division_code", unique = false, nullable = true, insertable = true, updatable = true, length = 3)
	public String getDivisionCode() {
		return divisionCode;
	}
	
	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}
	
	@Column(name = "modified_by", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}