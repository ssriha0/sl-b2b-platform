package com.newco.marketplace.dto.vo.provider;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.sears.os.vo.SerializableBaseVO;

public class ProviderFirmVO extends SerializableBaseVO{
	
	private static final long serialVersionUID = -1714550463402553636L;
	private Integer vendorID;
	private String businessPhoneNumber;
	private String businessName;
	
	public Integer getVendorID() {
		return vendorID;
	}
	public void setVendorID(Integer vendorID) {
		this.vendorID = vendorID;
	}
	public String getBusinessPhoneNumber() {
		return businessPhoneNumber;
	}
	public void setBusinessPhoneNumber(String businessPhoneNumber) {
		this.businessPhoneNumber = businessPhoneNumber;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("vendorID", getVendorID())
		.append("businessPhoneNumber", getBusinessPhoneNumber())
		.append("businessName", getBusinessPhoneNumber())
		.toString();
	}
	
}
