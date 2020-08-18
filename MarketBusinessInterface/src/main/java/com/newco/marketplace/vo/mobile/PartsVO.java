package com.newco.marketplace.vo.mobile;
public class PartsVO {
	
	private String soId;
	private Integer partId;
	private String partName;
	private Integer carrierId;
	private String trackingNumber;
	private Integer coreCarrierId;
	private String coreTrackingNumber;
	
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Integer getPartId() {
		return partId;
	}
	public void setPartId(Integer partId) {
		this.partId = partId;
	}
	public Integer getCarrierId() {
		return carrierId;
	}
	public void setCarrierId(Integer carrierId) {
		this.carrierId = carrierId;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public Integer getCoreCarrierId() {
		return coreCarrierId;
	}
	public void setCoreCarrierId(Integer coreCarrierId) {
		this.coreCarrierId = coreCarrierId;
	}
	public String getCoreTrackingNumber() {
		return coreTrackingNumber;
	}
	public void setCoreTrackingNumber(String coreTrackingNumber) {
		this.coreTrackingNumber = coreTrackingNumber;
	}
	
	

}
