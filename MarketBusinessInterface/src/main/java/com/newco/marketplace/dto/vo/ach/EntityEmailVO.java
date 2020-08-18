package com.newco.marketplace.dto.vo.ach;

import com.sears.os.vo.SerializableBaseVO;

public class EntityEmailVO extends SerializableBaseVO {
	private Integer entityId;
	private Long ledgerEntryId;
	private Integer entityTypeId;
	private Double transAmount;
	private String  email;
	public Integer getEntityId() {
		return entityId;
	}
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	public Long getLedgerEntryId() {
		return ledgerEntryId;
	}
	public void setLedgerEntryId(Long ledgerEntryId) {
		this.ledgerEntryId = ledgerEntryId;
	}
	public Integer getEntityTypeId() {
		return entityTypeId;
	}
	public void setEntityTypeId(Integer entityTypeId) {
		this.entityTypeId = entityTypeId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Double getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(Double transAmount) {
		this.transAmount = transAmount;
	}
	
	
}
