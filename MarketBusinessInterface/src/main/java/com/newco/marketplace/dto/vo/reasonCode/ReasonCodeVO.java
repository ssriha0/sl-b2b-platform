/*
 * for new API call update
 * 
 */

package com.newco.marketplace.dto.vo.reasonCode;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;

public class ReasonCodeVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
    private Integer reasonCodeId;
    private String reasonCode;
    private Integer buyerId;
    private int general;
    private String reasonCodeType;
    private Date createdDate ;
    private Date modifiedDate;
    private String modifiedBy;
    private String reasonCodeStatus;
    
	public Integer getReasonCodeId() {

		return reasonCodeId;
	}

	public void setReasonCodeId(Integer reasonCodeId) {
		this.reasonCodeId = reasonCodeId;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}	

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public int getGeneral() {
		return general;
	}

	public void setGeneralInd(int general) {
		this.general = general;
	}

	public String getReasonCodeType() {
		return reasonCodeType;
	}

	public void setReasonCodeType(String reasonCodeType) {
		this.reasonCodeType = reasonCodeType;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public String getReasonCodeStatus() {
		return reasonCodeStatus;
	}

	public void setReasonCodeStatus(String reasonCodeStatus) {
		this.reasonCodeStatus = reasonCodeStatus;
	}
}
