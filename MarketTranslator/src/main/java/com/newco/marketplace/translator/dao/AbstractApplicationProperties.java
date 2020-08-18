package com.newco.marketplace.translator.dao;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AbstractApplicationProperties entity provides the base persistence definition
 * of the ApplicationProperties entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractApplicationProperties implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5504738613392374073L;
	private String appKey;
	private String appValue;
	private String appDesc;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;

	// Constructors

	/** default constructor */
	public AbstractApplicationProperties() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractApplicationProperties(String appKey, String appValue,
			String appDesc) {
		this.appKey = appKey;
		this.appValue = appValue;
		this.appDesc = appDesc;
	}

	/** full constructor */
	public AbstractApplicationProperties(String appKey, String appValue,
			String appDesc, Date createdDate, Date modifiedDate,
			String modifiedBy) {
		this.appKey = appKey;
		this.appValue = appValue;
		this.appDesc = appDesc;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}

	// Property accessors
	@Id
	@Column(name = "app_key", unique = true, nullable = false, insertable = true, updatable = true, length = 50)
	public String getAppKey() {
		return this.appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	@Column(name = "app_value", unique = false, nullable = false, insertable = true, updatable = true)
	public String getAppValue() {
		return this.appValue;
	}

	public void setAppValue(String appValue) {
		this.appValue = appValue;
	}

	@Column(name = "app_desc", unique = false, nullable = false, insertable = true, updatable = true)
	public String getAppDesc() {
		return this.appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
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

}