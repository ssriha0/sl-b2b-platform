package com.newco.marketplace.dto.vo.serviceorder;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.newco.marketplace.webservices.base.CommonVO;


public class Carrier extends CommonVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5790155182286709681L;
	private Integer carrierId;
	private String carrierName;
	private String trackingNumber;
	
	
	
	public Integer getCarrierId() {
		return carrierId;
	}
	public void setCarrierId(Integer carrierId) {
		this.carrierId = carrierId;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public void setCarrierName(final String carrierName) {
		this.carrierName = carrierName;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(final String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("carrierId", getCarrierId())
			.append("carrierName", getCarrierName())
			.append("trackingNumber", getTrackingNumber())
			.toString();
	}
	public Object getComparableString() {
		// TODO Auto-generated method stub
		return null;
	}
}
