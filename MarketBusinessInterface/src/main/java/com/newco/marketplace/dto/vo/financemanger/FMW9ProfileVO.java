package com.newco.marketplace.dto.vo.financemanger;

import java.util.Date;

import com.newco.marketplace.vo.provider.BaseVO;

public class FMW9ProfileVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer vendorId;
	private String businessName;
	private String dbaName;
	private String street1;
	private String street2;
	private String zip;
	private String zip4;
	private String city;
	private String state;
	private String encryptedTaxPayerId ;
	private String taxPayerId;
	private int taxPayerType;
	private String exempt;
	private String taxPayerTypeDescr;
	private Date createdDate;
	private Date modifiedDate;
	private Date nextModifiedDate;
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getEncryptedTaxPayerId() {
		return encryptedTaxPayerId;
	}
	public void setEncryptedTaxPayerId(String encryptedTaxPayerId) {
		this.encryptedTaxPayerId = encryptedTaxPayerId;
	}
	public String getTaxPayerId() {
		return taxPayerId;
	}
	public void setTaxPayerId(String taxPayerId) {
		this.taxPayerId = taxPayerId;
	}
	public int getTaxPayerType() {
		return taxPayerType;
	}
	public void setTaxPayerType(int taxPayerType) {
		this.taxPayerType = taxPayerType;
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
	public String getTaxPayerTypeDescr() {
		return taxPayerTypeDescr;
	}
	public void setTaxPayerTypeDescr(String taxPayerTypeDescr) {
		this.taxPayerTypeDescr = taxPayerTypeDescr;
	}
	public Date getNextModifiedDate() {
		return nextModifiedDate;
	}
	public void setNextModifiedDate(Date nextModifiedDate) {
		this.nextModifiedDate = nextModifiedDate;
	}
	public String getExempt() {
		return exempt;
	}
	public void setExempt(String exempt) {
		this.exempt = exempt;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getDbaName() {
		return dbaName;
	}
	public void setDbaName(String dbaName) {
		this.dbaName = dbaName;
	}
	public String getStreet1() {
		return street1;
	}
	public void setStreet1(String street1) {
		this.street1 = street1;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getZip4() {
		return zip4;
	}
	public void setZip4(String zip4) {
		this.zip4 = zip4;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
