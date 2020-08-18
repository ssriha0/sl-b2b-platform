package com.newco.marketplace.dto.vo;

import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

public class FilterParamsVO extends SerializableBaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String vendorId;
	public List<String> soIds;

	
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public List<String> getSoIds() {
		return soIds;
	}
	public void setSoIds(List<String> soIds) {
		this.soIds = soIds;
	}
	
}
