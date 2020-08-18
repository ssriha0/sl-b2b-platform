package com.newco.marketplace.translator.dao;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AbstractBuyerSkuTaskAssoc entity provides the base persistence definition of
 * the BuyerSkuTaskAssoc entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractBuyerSkuTaskAssoc implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4124841732705189637L;
	private Integer skuTaskId;
	private String sku;
	private Integer nodeId;
	private Integer serviceTypeTemplateId;
	private Date createdDate;
	private Integer buyerId;
	private Integer templateId;
	private String specialtyCode;

	// Constructors

	/** default constructor */
	public AbstractBuyerSkuTaskAssoc() {
		// intentionally blank
	}

	/** full constructor */
	public AbstractBuyerSkuTaskAssoc(Integer skuTaskId, String sku,
			Integer nodeId, Integer serviceTypeTemplateId, Date createdDate, Integer buyerId,
			Integer templateId, String specialtyCode) {
		this.skuTaskId = skuTaskId;
		this.sku = sku;
		this.nodeId = nodeId;
		this.serviceTypeTemplateId = serviceTypeTemplateId;
		this.createdDate = createdDate;
		this.buyerId = buyerId;
		this.templateId = templateId;
		this.specialtyCode = specialtyCode;
	}

	// Property accessors
	@Id
	@Column(name = "sku_task_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getSkuTaskId() {
		return this.skuTaskId;
	}

	public void setSkuTaskId(Integer skuTaskId) {
		this.skuTaskId = skuTaskId;
	}

	@Column(name = "sku", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	@Column(name = "node_id", unique = false, nullable = false, insertable = true, updatable = true)
	public Integer getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "service_type_template_id", unique = false, nullable = false, insertable = true, updatable = true)
	public Integer getServiceTypeTemplateId() {
		return this.serviceTypeTemplateId;
	}

	public void setServiceTypeTemplateId(Integer serviceTypeTemplateId) {
		this.serviceTypeTemplateId = serviceTypeTemplateId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "created_date", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "buyer_id", unique = false, nullable = false, insertable = true, updatable = true)
	public Integer getBuyerId() {
		return this.buyerId;
	}
	
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	
	@Column(name = "template_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	/**
	 * @return the spcialtyCode
	 */
	@Column(name = "specialty_cd", unique = true, nullable = false, insertable = true, updatable = true)
	public String getSpecialtyCode() {
		return specialtyCode;
	}

	/**
	 * @param spcialtyCode the spcialtyCode to set
	 */
	public void setSpecialtyCode(String specialtyCode) {
		this.specialtyCode = specialtyCode;
	}

}