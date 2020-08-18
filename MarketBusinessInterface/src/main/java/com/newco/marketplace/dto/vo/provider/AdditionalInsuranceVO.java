package com.newco.marketplace.dto.vo.provider;

import java.sql.Timestamp;
import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;

public class AdditionalInsuranceVO extends SerializableBaseVO{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = -1714550466902553636L;
	private Integer vendorCredId;
	private Integer vendorId;
	private Integer categoryId;
	private String categoryName;
	private Integer policyAmount;
	private Timestamp policyExpiryDate;
	private Integer policyStatus;
	private String policyDescr;
	
	
	
	public Integer getVendorCredId() {
		return vendorCredId;
	}
	public void setVendorCredId(Integer vendorCredId) {
		this.vendorCredId = vendorCredId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Integer getPolicyAmount() {
		return policyAmount;
	}
	public void setPolicyAmount(Integer policyAmount) {
		this.policyAmount = policyAmount;
	}
	public Timestamp getPolicyExpiryDate() {
		return policyExpiryDate;
	}
	public void setPolicyExpiryDate(Timestamp policyExpiryDate) {
		this.policyExpiryDate = policyExpiryDate;
	}
	public Integer getPolicyStatus() {
		return policyStatus;
	}
	public void setPolicyStatus(Integer policyStatus) {
		this.policyStatus = policyStatus;
	}
	public String getPolicyDescr() {
		return policyDescr;
	}
	public void setPolicyDescr(String policyDescr) {
		this.policyDescr = policyDescr;
	}
	

	
}
