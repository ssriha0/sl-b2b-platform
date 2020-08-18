package com.newco.marketplace.vo.ordermanagement.so;

import com.sears.os.vo.SerializableBaseVO;

public class AvailabilityDateVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String soId;
	private String availabilityDate;
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getAvailabilityDate() {
		return availabilityDate;
	}
	public void setAvailabilityDate(String availabilityDate) {
		this.availabilityDate = availabilityDate;
	}
}