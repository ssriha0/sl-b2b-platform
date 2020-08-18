package com.newco.marketplace.dto.vo;

public class LuServiceTypeTemplateVO {
	private Integer serviceTypeTemplateId;
    private Integer nodeId;
    private String descr;
    private Integer sortOrder;
	public Integer getServiceTypeTemplateId() {
		return serviceTypeTemplateId;
	}
	public void setServiceTypeTemplateId(Integer serviceTypeTemplateId) {
		this.serviceTypeTemplateId = serviceTypeTemplateId;
	}
	public Integer getNodeId() {
		return nodeId;
	}
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
    

}
