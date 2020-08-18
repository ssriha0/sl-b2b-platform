package com.newco.marketplace.web.dto;


public class SOWBrandingInfoDTO extends SOWBaseTabDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1401223592471454107L;
	private String companyName;
	private String logoId;
	private Integer documentId;
	private byte[] blobBytes;
	
	
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
	//	return null;
	}

	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getLogoId() {
		return logoId;
	}


	public void setLogoId(String logoId) {
		this.logoId = logoId;



	}
	


	public Integer getDocumentId() {
		return documentId;

	}


	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	



	public byte[] getBlobBytes() {
		return blobBytes;
	}

	public void setBlobBytes(byte[] blobBytes) {
		this.blobBytes = blobBytes;
	}

	@Override
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
