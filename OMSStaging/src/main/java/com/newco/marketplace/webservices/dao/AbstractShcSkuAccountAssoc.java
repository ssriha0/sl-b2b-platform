package com.newco.marketplace.webservices.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractShcSkuAccountAssoc entity provides the base persistence definition of
 * the ShcSkuAccountAssoc entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractShcSkuAccountAssoc implements
		java.io.Serializable {

	// Fields

	private String sku;
	private Integer glAccount;
	private String glDivision;
	private String glCategory;
	private String modifiedBy;

	// Constructors

	/** default constructor */
	public AbstractShcSkuAccountAssoc() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractShcSkuAccountAssoc(String sku) {
		this.sku = sku;
	}

	/** full constructor */
	public AbstractShcSkuAccountAssoc(String sku, Integer glAccount,
			String glDivision, String glCategory, Date createdDate,
			Date modifiedDate, String modifiedBy) {
		this.sku = sku;
		this.glAccount = glAccount;
		this.glDivision = glDivision;
		this.glCategory = glCategory;
		this.modifiedBy = modifiedBy;
	}

	// Property accessors
	@Id
	@Column(name = "sku", unique = true, nullable = false, insertable = true, updatable = true, length = 30)
	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	@Column(name = "gl_account", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getGlAccount() {
		return this.glAccount;
	}

	public void setGlAccount(Integer glAccount) {
		this.glAccount = glAccount;
	}

	@Column(name = "gl_division", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getGlDivision() {
		return this.glDivision;
	}

	public void setGlDivision(String glDivision) {
		this.glDivision = glDivision;
	}

	@Column(name = "gl_category", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getGlCategory() {
		return this.glCategory;
	}

	public void setGlCategory(String glCategory) {
		this.glCategory = glCategory;
	}

	@Column(name = "modified_by", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}