package com.servicelive.routingrulesengine.vo;

import java.util.Date;

public class RoutingRuleCacheStatusVO {
	Integer routingRuleHdrId;
	String action;
	Integer buyerAssocId;
	Date updateTime;
	public Integer getRoutingRuleHdrId() {
		return routingRuleHdrId;
	}
	public void setRoutingRuleHdrId(Integer routingRuleHdrId) {
		this.routingRuleHdrId = routingRuleHdrId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Integer getBuyerAssocId() {
		return buyerAssocId;
	}
	public void setBuyerAssocId(Integer buyerAssocId) {
		this.buyerAssocId = buyerAssocId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
