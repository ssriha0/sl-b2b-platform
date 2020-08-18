package com.newco.marketplace.dto.vo.so.order;

import com.sears.os.vo.SerializableBaseVO;

public class SLPartsOrderFileVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2170983298487378635L;
	private Integer buyerId;
	private Integer slPartOrderId;
	private String soId;
	private String incidentId;
	private String incidentComments;
	private String status;
	private String firstName;
	private String lastName;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zipCode;
	private String zipCodeExt;
	private String phone;
	private String phoneExt;
	private String altPhone;
	private String altPhoneExt;
	private String productLine;
	private Integer quantity;
	private String partCategoryId;
	private String partCategoryName;
	private String partNumber;
	private String oemNumber;
	private String partDesc;
	private String manufacturer;
	private String modelNumber;
	private String serialNumber;
	private String classCode;
	private String classComments;
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public String getIncidentId() {
		return incidentId;
	}
	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}
	public String getIncidentComments() {
		return incidentComments;
	}
	public void setIncidentComments(String incidentComments) {
		this.incidentComments = incidentComments;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAltPhone() {
		return altPhone;
	}
	public void setAltPhone(String altPhone) {
		this.altPhone = altPhone;
	}
	public String getAltPhoneExt() {
		return altPhoneExt;
	}
	public void setAltPhoneExt(String altPhoneExt) {
		this.altPhoneExt = altPhoneExt;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getModelNumber() {
		return modelNumber;
	}
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	public Integer getSlPartOrderId() {
		return slPartOrderId;
	}
	public void setSlPartOrderId(Integer slPartOrderId) {
		this.slPartOrderId = slPartOrderId;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getZipCodeExt() {
		return zipCodeExt;
	}
	public void setZipCodeExt(String zipCodeExt) {
		this.zipCodeExt = zipCodeExt;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhoneExt() {
		return phoneExt;
	}
	public void setPhoneExt(String phoneExt) {
		this.phoneExt = phoneExt;
	}
	public String getProductLine() {
		return productLine;
	}
	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getPartCategoryId() {
		return partCategoryId;
	}
	public void setPartCategoryId(String partCategoryId) {
		this.partCategoryId = partCategoryId;
	}
	public String getPartCategoryName() {
		return partCategoryName;
	}
	public void setPartCategoryName(String partCategoryName) {
		this.partCategoryName = partCategoryName;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public String getOemNumber() {
		return oemNumber;
	}
	public void setOemNumber(String oemNumber) {
		this.oemNumber = oemNumber;
	}
	public String getPartDesc() {
		return partDesc;
	}
	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getClassComments() {
		return classComments;
	}
	public void setClassComments(String classComments) {
		this.classComments = classComments;
	}

}
