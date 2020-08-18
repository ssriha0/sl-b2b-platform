package com.servicelive.domain.d2cproviderportal;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



@Entity
@Table(name = "buyer_so_template", uniqueConstraints = { @UniqueConstraint(columnNames = {"buyer_id", "template_name" }) })
public class BuyerSoTemplateD2C implements java.io.Serializable {

	private static final long serialVersionUID = -7534121517571116365L;
	@Id
	@Column(name = "template_id", unique = true, nullable = false, insertable = true, updatable = true)
	private Integer templateId;
	@Column(name = "template_name", unique = false, nullable = false, insertable = true, updatable = true)
	private String templateName;
	@Column(name = "buyer_id", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer buyerId;
	@Column(name = "template_data", unique = false, nullable = true, insertable = true, updatable = true)
	private String templateData;
	@ManyToOne( fetch=FetchType.EAGER )
	@JoinColumn (name = "primary_skill_category_id")
	private com.servicelive.domain.lookup.LookupPrimaryIndustry lookupPrimaryIndustry;
	
	 //@OneToMany(mappedBy="buyerSoTemplate",targetEntity=BuyerSku.class)
	 //private List<BuyerSku> buyerSkus;
	
	public Integer getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public String getTemplateData() {
		return templateData;
	}
	public void setTemplateData(String templateData) {
		this.templateData = templateData;
	}
	public com.servicelive.domain.lookup.LookupPrimaryIndustry getLookupPrimaryIndustry() {
		return lookupPrimaryIndustry;
	}
	public void setLookupPrimaryIndustry(com.servicelive.domain.lookup.LookupPrimaryIndustry lookupPrimaryIndustry) {
		this.lookupPrimaryIndustry = lookupPrimaryIndustry;
	}
	//public List<BuyerSku> getBuyerSkus() {
	//	return buyerSkus;
	//}
	//public void setBuyerSkus(List<BuyerSku> buyerSkus) {
	//	this.buyerSkus = buyerSkus;
	//}
	
	
		
}
