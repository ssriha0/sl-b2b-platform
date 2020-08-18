package com.newco.marketplace.dto.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.web.dto.SOWError;


public class ManageTaskVO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private Integer id;
	private String taskName;
	private String taskComments;
	private String priceInteger;
	private String priceFraction;
	private String sku;
	private String skill;
	private Long serviceTypeTemplateId;
	private BigDecimal finalPrice;
	private BigDecimal maxTotalPrice;
	private BigDecimal maxLabourPrice;
	private Long skillNodeId; 
	private List<LookupVO> mainCategorySkills;
	private String skuId;
	
	private List<SOWError> taskErrors = new ArrayList<SOWError>();
	private BigDecimal permitPrice = new BigDecimal(0.00).setScale(2);
	private BigDecimal partsPrice = new BigDecimal(0.00).setScale(2);
	private BigDecimal labourPrice = new BigDecimal(0.00).setScale(2);
	private BigDecimal totalPrice = new BigDecimal(0.00).setScale(2);
	
	
	public List<SOWError> getTaskErrors() {
		return taskErrors;
	}
	public void setTaskErrors(List<SOWError> taskErrors) {
		this.taskErrors = taskErrors;
	}
	public BigDecimal getPermitPrice() {
		return permitPrice;
	}
	public void setPermitPrice(BigDecimal permitPrice) {
		this.permitPrice = permitPrice;
	}
	public BigDecimal getPartsPrice() {
		return partsPrice;
	}
	public void setPartsPrice(BigDecimal partsPrice) {
		this.partsPrice = partsPrice;
	}
	public BigDecimal getLabourPrice() {
		return labourPrice;
	}
	public void setLabourPrice(BigDecimal labourPrice) {
		this.labourPrice = labourPrice;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Long getSkillNodeId() {
		return skillNodeId;
	}
	public void setSkillNodeId(Long skillNodeId) {
		this.skillNodeId = skillNodeId;
	}
	public BigDecimal getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskComments() {
		return taskComments;
	}
	public void setTaskComments(String taskComments) {
		this.taskComments = taskComments;
	}
	public String getPriceInteger() {
		return priceInteger;
	}
	public void setPriceInteger(String priceInteger) {
		this.priceInteger = priceInteger;
	}
	public String getPriceFraction() {
		return priceFraction;
	}
	public void setPriceFraction(String priceFraction) {
		this.priceFraction = priceFraction;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public Long getServiceTypeTemplateId() {
		return serviceTypeTemplateId;
	}
	public void setServiceTypeTemplateId(Long serviceTypeTemplateId) {
		this.serviceTypeTemplateId = serviceTypeTemplateId;
	}
	public BigDecimal getMaxTotalPrice() {
		return maxTotalPrice;
	}
	public void setMaxTotalPrice(BigDecimal maxTotalPrice) {
		this.maxTotalPrice = maxTotalPrice;
	}
	public BigDecimal getMaxLabourPrice() {
		return maxLabourPrice;
	}
	public void setMaxLabourPrice(BigDecimal maxLabourPrice) {
		this.maxLabourPrice = maxLabourPrice;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<LookupVO> getMainCategorySkills() {
		return mainCategorySkills;
	}
	public void setMainCategorySkills(List<LookupVO> mainCategorySkills) {
		this.mainCategorySkills = mainCategorySkills;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	
	
}
