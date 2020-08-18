package com.newco.marketplace.dto.vo.spn;

import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

/**
 * 
 * @author Rajitha Gurram
 *
 */

public class SPNProviderProfileBuyerVO extends SerializableBaseVO {

	private static final long serialVersionUID = -8162616568052426000L;
	
	private String companyName;
	private Integer buyerId;
	private String logoFileName;
	private byte[] logoBlobBytes;
	private List<SPNProviderProfileVO> providerSPNList;
	
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
	public List<SPNProviderProfileVO> getProviderSPNList() {
		return providerSPNList;
	}
	public void setProviderSPNList(List<SPNProviderProfileVO> providerSPNList) {
		this.providerSPNList = providerSPNList;
	}
	public byte[] getLogoBlobBytes() {
		return logoBlobBytes;
	}
	public void setLogoBlobBytes(byte[] logoBlobBytes) {
		this.logoBlobBytes = logoBlobBytes;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	
	

}
