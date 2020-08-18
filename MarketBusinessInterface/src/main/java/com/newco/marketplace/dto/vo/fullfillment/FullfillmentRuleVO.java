package com.newco.marketplace.dto.vo.fullfillment;

import java.sql.Timestamp;

import com.sears.os.vo.SerializableBaseVO;

public class FullfillmentRuleVO extends SerializableBaseVO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6492112416058222752L;
	
	private Integer fullfillmentEntryRuleId;
	private Integer busTransId;
	private String  description;
	private Integer sortOrder;
	private Integer  entityTypeId;
	private Integer entryTypeId;
	private Integer promoCodeId;
	private String promoCode;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private String modifiedBy;
	
	private Integer fundingTypeId;
	private String origAcctType;
	private Integer origEntityType;
	private String destAcctType;
	private Integer destEntityType;
	private Long fullfillmentEntryId;
	
	
	public Integer getBusTransId() {
		return busTransId;
	}
	public void setBusTransId(Integer busTransId) {
		this.busTransId = busTransId;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getEntityTypeId() {
		return entityTypeId;
	}
	public void setEntityTypeId(Integer entityTypeId) {
		this.entityTypeId = entityTypeId;
	}
	public Integer getEntryTypeId() {
		return entryTypeId;
	}
	public void setEntryTypeId(Integer entryTypeId) {
		this.entryTypeId = entryTypeId;
	}
	public Integer getFullfillmentEntryRuleId() {
		return fullfillmentEntryRuleId;
	}
	public void setFullfillmentEntryRuleId(Integer fullfillmentEntryRuleId) {
		this.fullfillmentEntryRuleId = fullfillmentEntryRuleId;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getPromoCode() {
		return promoCode;
	}
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
	public Integer getPromoCodeId() {
		return promoCodeId;
	}
	public void setPromoCodeId(Integer promoCodeId) {
		this.promoCodeId = promoCodeId;
	}
	public Integer getFundingTypeId() {
		return fundingTypeId;
	}
	public void setFundingTypeId(Integer fundingTypeId) {
		this.fundingTypeId = fundingTypeId;
	}
	public String getOrigAcctType() {
		return origAcctType;
	}
	public void setOrigAcctType(String origAcctType) {
		this.origAcctType = origAcctType;
	}
	public Integer getOrigEntityType() {
		return origEntityType;
	}
	public void setOrigEntityType(Integer origEntityType) {
		this.origEntityType = origEntityType;
	}
	public String getDestAcctType() {
		return destAcctType;
	}
	public void setDestAcctType(String destAcctType) {
		this.destAcctType = destAcctType;
	}
	public Integer getDestEntityType() {
		return destEntityType;
	}
	public void setDestEntityType(Integer destEntityType) {
		this.destEntityType = destEntityType;
	}
	public Long getFullfillmentEntryId() {
		return fullfillmentEntryId;
	}
	public void setFullfillmentEntryId(Long fullfillmentEntryId) {
		this.fullfillmentEntryId = fullfillmentEntryId;
	}
    
}