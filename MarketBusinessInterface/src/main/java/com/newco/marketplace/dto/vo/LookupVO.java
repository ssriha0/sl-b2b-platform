package com.newco.marketplace.dto.vo;

import com.sears.os.vo.SerializableBaseVO;

public class LookupVO extends SerializableBaseVO{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4927791978329617794L;
	Integer id = null;
    String type = null;
    String descr = null;
    Integer sortOrder = null;
    String action = null;
    String respCd = null;
    Double dId = null;
    Double limitValue = 0.0;
    
    
	public Double getLimitValue() {
		return limitValue;
	}
	public void setLimitValue(Double limitValue) {
		this.limitValue = limitValue;
	}
	public String getRespCd() {
		return respCd;
	}
	public void setRespCd(String respCd) {
		this.respCd = respCd;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString()
	{
		return "LookupVO [action=" + action + ", descr=" + descr + ", id=" + id + ", respCd=" + respCd + ", sortOrder=" + sortOrder + ", type="
				+ type + "]";
	}
	/**
	 * @return the dId
	 */
	public Double getdId() {
		return dId;
	}
	/**
	 * @param dId the dId to set
	 */
	public void setdId(Double dId) {
		this.dId = dId;
	}
	
}
