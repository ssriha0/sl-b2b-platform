package com.newco.marketplace.dto.vo.pagination;

import com.sears.os.vo.SerializableBaseVO;

public class WorkflowStatusVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4078491621966957063L;
	private int count; 
	private Integer [] workFlowStatusIds ;
	private Integer soSubstatusId; 
	private String userId;
	private boolean routedTab;
	private String serviceProName;
	private Integer buyerRoleId;
	private String marketName;
	private String resourceId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getSoSubstatusId() {
		return soSubstatusId;
	}

	public void setSoSubstatusId(Integer soSubstatusId) {
		this.soSubstatusId = soSubstatusId;
	}

	public Integer [] getWorkFlowStatusIds() {
		return workFlowStatusIds;
	}

	public void setWorkFlowStatusIds(Integer [] workFlowStatusIds) {
		this.workFlowStatusIds = workFlowStatusIds;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isRoutedTab() {
		return routedTab;
	}

	public void setRoutedTab(boolean routedTab) {
		this.routedTab = routedTab;
	}

	public String getServiceProName() {
		return serviceProName;
	}

	public void setServiceProName(String serviceProName) {
		this.serviceProName = serviceProName;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public Integer getBuyerRoleId()
	{
		return buyerRoleId;
	}

	public void setBuyerRoleId(Integer buyerRoleId)
	{
		this.buyerRoleId = buyerRoleId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
}
