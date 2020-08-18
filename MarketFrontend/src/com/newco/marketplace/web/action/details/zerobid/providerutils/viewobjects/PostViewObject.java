package com.newco.marketplace.web.action.details.zerobid.providerutils.viewobjects;

import java.io.Serializable;


public class PostViewObject implements Serializable {

	private static final long serialVersionUID = 1211660052894289464L;
	
	private String postText;
	private String orderId;
	private Long providerId;
	private Long providerResourceId;
	private Long parentActivityId;

	// New
	private Long buyerId;
	private String providerName;
	
	
	public void setPostText(String postText) {
		this.postText = postText;
	}

	public String getPostText() {
		return postText;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public void setParentActivityId(Long parentActivityId) {
		this.parentActivityId = parentActivityId;
	}

	public Long getParentActivityId() {
		return parentActivityId;
	}

	public Long getBuyerId()
	{
		return buyerId;
	}

	public void setBuyerId(Long buyerId)
	{
		this.buyerId = buyerId;
	}


	public Long getProviderResourceId()
	{
		return providerResourceId;
	}

	public void setProviderResourceId(Long providerResourceId)
	{
		this.providerResourceId = providerResourceId;
	}

	public String getProviderName()
	{
		return providerName;
	}

	public void setProviderName(String providerName)
	{
		this.providerName = providerName;
	}
	
}