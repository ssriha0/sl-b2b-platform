package com.newco.marketplace.dto.vo.serviceorder;

import com.sears.os.vo.SerializableBaseVO;

public class ServiceOrderSubStatusVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8717136378084949867L;
	private int subStatusId;
	private String subStatusName;
	private int sortOrder;
	public int getSubStatusId() {
		return subStatusId;
	}
	public void setSubStatusId(int subStatusId) {
		this.subStatusId = subStatusId;
	}
	public String getSubStatusName() {
		return subStatusName;
	}
	public void setSubStatusName(String subStatusName) {
		this.subStatusName = subStatusName;
	}
	public int getSortorder() {
		return sortOrder;
	}
	public void setSortorder(int sortorder) {
		this.sortOrder = sortorder;
	}
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("[ ");
		sb.append("id: "+subStatusId);
		sb.append(", name: "+subStatusName);
		sb.append(",sortOrder: "+sortOrder);
		sb.append(" ]");
		return sb.toString();
		
	}
}
