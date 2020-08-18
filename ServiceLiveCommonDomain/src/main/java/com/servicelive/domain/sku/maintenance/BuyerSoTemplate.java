package com.servicelive.domain.sku.maintenance;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * BuyerSoTemplate entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity(name = "BuyerSoTemplateEntity")
@Table(name = "buyer_so_template", uniqueConstraints = { @UniqueConstraint(columnNames = {"buyer_id", "template_name" }) })
public class BuyerSoTemplate extends AbstractBuyerSoTemplate implements java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = -804237669997830726L;

	/** default constructor */
	public BuyerSoTemplate() {
		// intentionally blank
	}

	/** minimal constructor */
	public BuyerSoTemplate(Integer templateId, String templateName,
			Integer buyerId) {
		super(templateId, templateName, buyerId);
	}

	/** full constructor */
	public BuyerSoTemplate(Integer templateId, String templateName,
			Integer buyerId, Integer primarySkillCategoryId, String templateData) {
		super(templateId, templateName, buyerId, primarySkillCategoryId,
				templateData);
	}

}
