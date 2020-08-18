/**
 * 
 */
package com.newco.marketplace.dto.vo.serviceorder;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author sahmad7
 *
 */
public class WfStatesVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3739981561523383423L;
	private Integer wfStateId;
	private String wfEntity;
	private String wfState;
	private String wfDescr;
	private Integer sortOrder;
	private Integer auditLinkId;
	
	public Integer getAuditLinkId() {
		return auditLinkId;
	}
	public void setAuditLinkId(Integer auditLinkId) {
		this.auditLinkId = auditLinkId;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getWfDescr() {
		return wfDescr;
	}
	public void setWfDescr(String wfDescr) {
		this.wfDescr = wfDescr;
	}
	public String getWfEntity() {
		return wfEntity;
	}
	public void setWfEntity(String wfEntity) {
		this.wfEntity = wfEntity;
	}
	public String getWfState() {
		return wfState;
	}
	public void setWfState(String wfState) {
		this.wfState = wfState;
	}
	public Integer getWfStateId() {
		return wfStateId;
	}
	public void setWfStateId(Integer wfStateId) {
		this.wfStateId = wfStateId;
	}

}
