package com.newco.marketplace.inhomeoutboundnotification.vo;

public class InhomeResponseVO {

	private String statusText;
	private int statusCode;
	private String responseXml;
	
	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getResponseXml() {
		return responseXml;
	}
	public void setResponseXml(String responseXml) {
		this.responseXml = responseXml;
	}
	
}
