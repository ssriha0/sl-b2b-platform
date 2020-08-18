package com.newco.marketplace.vo.api;

import java.util.HashMap;

public class APISecurityVO {

	private String consumerKey;
	private String consumerName;
	private String consumerPassword;
	private int internalConsumer;
	private String url;
	private String httpMethod;
	private String groupName;
	private int apiId;

	public String getConsumerKey() {
		return consumerKey;
	}
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}
	public String getConsumerPassword() {
		return consumerPassword;
	}
	public void setConsumerPassword(String consumerPassword) {
		this.consumerPassword = consumerPassword;
	}

	public int isInternalConsumer() {
		return internalConsumer;
	}
	public void setInternalConsumer(int internalConsumer) {
		this.internalConsumer = internalConsumer;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getHttpMethod() {
		return httpMethod;
	}
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	public int getInternalConsumer() {
		return internalConsumer;
	}
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getApiId() {
		return apiId;
	}
	public void setApiId(int apiId) {
		this.apiId = apiId;
	}
	public int getId() {
		return apiId;
	}

}
