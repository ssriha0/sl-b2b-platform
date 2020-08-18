package com.servicelive.marketplatform.common.vo;

import java.util.List;

//import com.servicelive.domain.routingrules.RoutingRuleVendor;

public class RoutingRuleHdrVO {
	private Integer routingRuleHdrId;
	private List<RoutingRuleVendorVO> routingRuleVendor;
	/**
	 * @return the routingRuleHdrId
	 */
	public Integer getRoutingRuleHdrId() {
		return routingRuleHdrId;
	}
	/**
	 * @param routingRuleHdrId the routingRuleHdrId to set
	 */
	public void setRoutingRuleHdrId(Integer routingRuleHdrId) {
		this.routingRuleHdrId = routingRuleHdrId;
	}
	/**
	 * @return the routingRuleVendor
	 */
	public List<RoutingRuleVendorVO> getRoutingRuleVendor() {
		return routingRuleVendor;
	}
	/**
	 * @param routingRuleVendor the routingRuleVendor to set
	 */
	public void setRoutingRuleVendor(List<RoutingRuleVendorVO> routingRuleVendor) {
		this.routingRuleVendor = routingRuleVendor;
	}
	

}
