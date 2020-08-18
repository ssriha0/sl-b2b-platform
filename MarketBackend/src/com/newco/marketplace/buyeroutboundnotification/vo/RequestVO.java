package com.newco.marketplace.buyeroutboundnotification.vo;

public class RequestVO {
	
	private Integer serviceId;
	private String method;
	private String url;
	private String httpHeaders;
	
	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getHttpHeaders() {
		return httpHeaders;
	}
	public void setHttpHeaders(String httpheaders) {
		this.httpHeaders = httpheaders;
	}
}
