package com.servicelive.orderfulfillment.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "buyer_email_mapping")
public class BuyerEmailMapping implements Serializable {

	private static final long serialVersionUID = 3018046803841822928L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="buyer_email_mapping_id")
	private Integer buyerEmailMappingId;
	
	@Column(name="template_id")
	private Integer templateId;
	
	@Column(name="buyer_id")
	private String buyerId;
	
	@Column(name="original_template_id")
	private String originalTemplateId;

	public Integer getBuyerEmailMappingId() {
		return buyerEmailMappingId;
	}

	public void setBuyerEmailMappingId(Integer buyerEmailMappingId) {
		this.buyerEmailMappingId = buyerEmailMappingId;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getOriginalTemplateId() {
		return originalTemplateId;
	}

	public void setOriginalTemplateId(String originalTemplateId) {
		this.originalTemplateId = originalTemplateId;
	}
	
	
}

