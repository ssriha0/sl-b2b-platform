package com.newco.marketplace.translator.dao;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractBuyerSoTemplate entity provides the base persistence definition of
 * the BuyerSoTemplate entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractBuyerSoTemplate implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7534121417571116365L;
	private Integer templateId;
	private String templateName;
	private Integer buyerId;
	private Integer primarySkillCategoryId;
	private String templateData;

	// Constructors

	/** default constructor */
	public AbstractBuyerSoTemplate() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractBuyerSoTemplate(Integer templateId, String templateName,
			Integer buyerId) {
		this.templateId = templateId;
		this.templateName = templateName;
		this.buyerId = buyerId;
	}

	/** full constructor */
	public AbstractBuyerSoTemplate(Integer templateId, String templateName,
			Integer buyerId, Integer primarySkillCategoryId, String templateData) {
		this.templateId = templateId;
		this.templateName = templateName;
		this.buyerId = buyerId;
		this.primarySkillCategoryId = primarySkillCategoryId;
		this.templateData = templateData;
	}

	// Property accessors
	@Id
	@Column(name = "template_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	@Column(name = "template_name", unique = false, nullable = false, insertable = true, updatable = true)
	public String getTemplateName() {
		return this.templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	@Column(name = "buyer_id", unique = false, nullable = false, insertable = true, updatable = true)
	public Integer getBuyerId() {
		return this.buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	@Column(name = "primary_skill_category_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getPrimarySkillCategoryId() {
		return this.primarySkillCategoryId;
	}

	public void setPrimarySkillCategoryId(Integer primarySkillCategoryId) {
		this.primarySkillCategoryId = primarySkillCategoryId;
	}

	@Column(name = "template_data", unique = false, nullable = true, insertable = true, updatable = true)
	public String getTemplateData() {
		return this.templateData;
	}

	public void setTemplateData(String templateData) {
		this.templateData = templateData;
	}

}