package com.newco.marketplace.dto.vo;

import com.sears.os.vo.SerializableBaseVO;

public class LuProviderRespReasonVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8724673362543969676L;
	private int respReasonId;
	private int providerRespId;
	private String descr;
	private int sortOrder;
	private int searchByResponse;
	
	public int getRespReasonId() {
		return respReasonId;
	}
	public void setRespReasonId(int respReasonId) {
		this.respReasonId = respReasonId;
	}
	public int getProviderRespId() {
		return providerRespId;
	}
	public void setProviderRespId(int providerRespId) {
		this.providerRespId = providerRespId;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public int getSearchByResponse() {
		return searchByResponse;
	}
	public void setSearchByResponse(int searchByResponse) {
		this.searchByResponse = searchByResponse;
	}
	
}
