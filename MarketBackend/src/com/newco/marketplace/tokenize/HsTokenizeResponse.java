package com.newco.marketplace.tokenize;

public class HsTokenizeResponse {
	private String soId;
	private String maskedAccountNo;
	private String token;
	private String responseXml;

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getMaskedAccountNo() {
		return maskedAccountNo;
	}

	public void setMaskedAccountNo(String maskedAccountNo) {
		this.maskedAccountNo = maskedAccountNo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getResponseXml() {
		return responseXml;
	}

	public void setResponseXml(String responseXml) {
		this.responseXml = responseXml;
	}
}
