package com.newco.marketplace.dto.vo.buyerskutask;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.sears.os.vo.SerializableBaseVO;

public class BuyerSkuTaskVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 440095070859706279L;
	private Integer skuCategoryId;
	private Integer skuId;
	private Integer skuTaskId;
	private Integer buyerId;
	private Integer templateId;
	private String sku;
	private String specCode;
	private Integer nodeId;
	private Integer skillId;
	private Timestamp createdDate;
	private String categoryName;
	private String subCategoryName;
	private String skillName;
	private Integer skillLevel;

	/**
	 * @return the skillId
	 */
	public Integer getSkillId() {
		return skillId;
	}
	/**
	 * @param skillId the skillId to set
	 */
	public void setSkillId(Integer skillId) {
		this.skillId = skillId;
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
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the skillLevel
	 */
	public Integer getSkillLevel() {
		return skillLevel;
	}
	/**
	 * @param skillLevel the skillLevel to set
	 */
	public void setSkillLevel(Integer skillLevel) {
		this.skillLevel = skillLevel;
	}
	/**
	 * @return the skillName
	 */
	public String getSkillName() {
		return skillName;
	}
	/**
	 * @param skillName the skillName to set
	 */
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	/**
	 * @return the subCategoryName
	 */
	public String getSubCategoryName() {
		return subCategoryName;
	}
	/**
	 * @param subCategoryName the subCategoryName to set
	 */
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}	
	
	@Override
	public boolean equals(Object obj) {		
		if (obj instanceof BuyerSkuTaskVO == false) {
		     return false;
		}
		if (this == obj) {
			return true;
		}
		
		BuyerSkuTaskVO buyerSkuTaskVo = (BuyerSkuTaskVO) obj;
		if(!equals(this.nodeId, buyerSkuTaskVo.getNodeId())) {
			return false;
		}
		
		if(!equals(this.skillId, buyerSkuTaskVo.getSkillId())) {
			return false;
		}
		if(!equals(this.sku, buyerSkuTaskVo.getSku())) {
			return false;
		}
		if(!equals(this.categoryName, buyerSkuTaskVo.getCategoryName())) {
			return false;
		}
		if(!equals(this.subCategoryName, buyerSkuTaskVo.getSubCategoryName())) {
			return false;
		}
		if(!equals(this.skillName, buyerSkuTaskVo.getSkillName())) {
			return false;
		}
		if(!equals(this.skillLevel, buyerSkuTaskVo.getSkillLevel())) {
			return false;
		}

		return true;
	}
	private boolean equals(Object obj1, Object obj2){
		if(obj1 == obj2) {
			return true;
		}
		if(obj1 == null && obj2 == null) {
			return true;
		}
		if(obj1 == null) {
			return false;
		}
		if(obj2 == null) {
			return false;
		}
		if(obj1 instanceof String && obj2 instanceof String) {
			return ((String) obj1).trim().equals(((String) obj2).trim());
		}
		return obj1.equals(obj2);
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(117, 311).
	       append(nodeId).
	       append(skillId).
	       append(sku).
	       append(categoryName).
	       append(subCategoryName).
	       append(skillName).
	       append(skillLevel).
	       toHashCode();
	}
	
	/**
	 * @return the specCode
	 */
	public String getSpecCode() {
		return specCode;
	}
	/**
	 * @param specCode the specCode to set
	 */
	public void setSpecCode(String specCode) {
		this.specCode = specCode;
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
	public Integer getSkuCategoryId() {
		return skuCategoryId;
	}
	public void setSkuCategoryId(Integer skuCategoryId) {
		this.skuCategoryId = skuCategoryId;
	}
	public Integer getSkuId() {
		return skuId;
	}
	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}
}