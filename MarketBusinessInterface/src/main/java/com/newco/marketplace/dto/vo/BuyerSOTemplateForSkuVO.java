package com.newco.marketplace.dto.vo;
/* VO to map  details related to template for the selected sku id. 
* @author Infosys 
*
*/
public class BuyerSOTemplateForSkuVO {
	private Integer buyerID;
	private Integer templateID;
	private String templateName;
	private Integer primarySkillCategoryId;
	private String templateData;
	public Integer getBuyerID() {
		return buyerID;
	}
	public void setBuyerID(Integer buyerID) {
		this.buyerID = buyerID;
	}
	public Integer getTemplateID() {
		return templateID;
	}
	public void setTemplateID(Integer templateID) {
		this.templateID = templateID;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public Integer getPrimarySkillCategoryId() {
		return primarySkillCategoryId;
	}
	public void setPrimarySkillCategoryId(Integer primarySkillCategoryId) {
		this.primarySkillCategoryId = primarySkillCategoryId;
	}
	public String getTemplateData() {
		return templateData;
	}
	public void setTemplateData(String templateData) {
		this.templateData = templateData;
	}
	
	

}
