package com.newco.marketplace.web.dto;

import java.util.List;

public class SPNProviderProfileBuyerTable extends SerializedBaseDTO{

	private String companyName;
	private Integer companyID;
	private String logoFileName=null;
	private byte[] blobBytes;
	private List<SPNProviderProfileInfoRow> spnList;
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getLogoFileName() {
		return logoFileName;
	}
	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}
	public byte[] getBlobBytes() {
		return blobBytes;
	}
	public void setBlobBytes(byte[] blobBytes) {
		this.blobBytes = blobBytes;
	}
	public List<SPNProviderProfileInfoRow> getSpnList() {
		return spnList;
	}
	public void setSpnList(List<SPNProviderProfileInfoRow> spnList) {
		this.spnList = spnList;
	}
	public Integer getCompanyID() {
		return companyID;
	}
	public void setCompanyID(Integer companyID) {
		this.companyID = companyID;
	}
	
	
	
}
