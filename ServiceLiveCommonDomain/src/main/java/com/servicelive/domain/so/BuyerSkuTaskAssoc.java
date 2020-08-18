package com.servicelive.domain.so;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * BuyerSkuTaskAssoc entity.
 * 
 */
@Entity
@Table(name = "buyer_sku_task_assoc", uniqueConstraints = {})
public class BuyerSkuTaskAssoc implements java.io.Serializable {

	private static final long serialVersionUID = 20090823L;

	/**
	 * 
	 */
	public BuyerSkuTaskAssoc() {
		super();
	}

	@Id
	@Column(name = "sku_task_id", unique = true, nullable = false, insertable = true, updatable = true)
	private Integer skuTaskId;

	@Column(name = "sku", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	private String sku;

	@Column(name = "node_id", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer nodeId;

	@Column(name = "service_type_template_id", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer serviceTypeTemplateId;

	@Temporal(TemporalType.DATE)
	@Column(name = "created_date", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	private Date createdDate;

	@Column(name = "buyer_id", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer buyerId;

	@Column(name = "template_id", unique = true, nullable = false, insertable = true, updatable = true)
	private Integer templateId;

	@Column(name = "specialty_cd", unique = true, nullable = false, insertable = true, updatable = true)
	private String specialtyCode;

	/**
	 * @return the skuTaskId
	 */
	public Integer getSkuTaskId() {
		return skuTaskId;
	}
	/**
	 * @param skuTaskId the skuTaskId to set
	 */
	public void setSkuTaskId(Integer skuTaskId) {
		this.skuTaskId = skuTaskId;
	}
	/**
	 * @return the sku
	 */
	public String getSku() {
		return sku;
	}
	/**
	 * @param sku the sku to set
	 */
	public void setSku(String sku) {
		this.sku = sku;
	}
	/**
	 * @return the nodeId
	 */
	public Integer getNodeId() {
		return nodeId;
	}
	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
	/**
	 * @return the serviceTypeTemplateId
	 */
	public Integer getServiceTypeTemplateId() {
		return serviceTypeTemplateId;
	}
	/**
	 * @param serviceTypeTemplateId the serviceTypeTemplateId to set
	 */
	public void setServiceTypeTemplateId(Integer serviceTypeTemplateId) {
		this.serviceTypeTemplateId = serviceTypeTemplateId;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the buyerId
	 */
	public Integer getBuyerId() {
		return buyerId;
	}
	/**
	 * @param buyerId the buyerId to set
	 */
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	/**
	 * @return the templateId
	 */
	public Integer getTemplateId() {
		return templateId;
	}
	/**
	 * @param templateId the templateId to set
	 */
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}
	/**
	 * @return the specialtyCode
	 */
	public String getSpecialtyCode() {
		return specialtyCode;
	}
	/**
	 * @param specialtyCode the specialtyCode to set
	 */
	public void setSpecialtyCode(String specialtyCode) {
		this.specialtyCode = specialtyCode;
	}
}
