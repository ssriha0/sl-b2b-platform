package com.newco.marketplace.translator.dao;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BuyerSkuTaskAssoc entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "buyer_sku_task_assoc", uniqueConstraints = {})
public class BuyerSkuTaskAssocMT extends AbstractBuyerSkuTaskAssoc implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public BuyerSkuTaskAssocMT() {
		// intentionally blank
	}

	/** full constructor */
	public BuyerSkuTaskAssocMT(Integer skuTaskId, String sku, Integer nodeId,
			Integer serviceTypeTemplateId, Date createdDate, Integer buyerId, Integer templateId, String specialtyCode) {
		super(skuTaskId, sku, nodeId, serviceTypeTemplateId, createdDate, buyerId, templateId, specialtyCode);
	}

}
