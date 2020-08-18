package com.newco.marketplace.dto.vo.wallet;

import java.util.Date;
import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

public class WalletControlVO extends SerializableBaseVO {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1861038847768880491L;
	/**
	 * 
	 */
	private Integer id;
	private Integer entityId;
	private Integer walletControlId;
	private String walletControlType;
	private Double amount;
	private Double remainingAmount;
	private Boolean onHold;
	private Date holdDate;
	private Date releasedDate;
    private Date  modifiedDate;
	private String modifiedBy;
	
	private List<WalletControlDocumentVO>  walletControlDocumentVO;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getEntityId() {
		return entityId;
	}
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	public Integer getWalletControlId() {
		return walletControlId;
	}
	public void setWalletControlId(Integer walletControlId) {
		this.walletControlId = walletControlId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getRemainingAmount() {
		return remainingAmount;
	}
	public void setRemainingAmount(Double remainingAmount) {
		this.remainingAmount = remainingAmount;
	}
	public Boolean getOnHold() {
		return onHold;
	}
	public void setOnHold(Boolean onHold) {
		this.onHold = onHold;
	}
	
	
	
	public List<WalletControlDocumentVO> getWalletControlDocumentVO() {
		return walletControlDocumentVO;
	}
	public void setWalletControlDocumentVO(
			List<WalletControlDocumentVO> walletControlDocumentVO) {
		this.walletControlDocumentVO = walletControlDocumentVO;
	}
	public Date getHoldDate() {
		return holdDate;
	}
	public void setHoldDate(Date holdDate) {
		this.holdDate = holdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getWalletControlType() {
		return walletControlType;
	}
	public void setWalletControlType(String walletControlType) {
		this.walletControlType = walletControlType;
	}
	public Date getReleasedDate() {
		return releasedDate;
	}
	public void setReleasedDate(Date releasedDate) {
		this.releasedDate = releasedDate;
	}
}
