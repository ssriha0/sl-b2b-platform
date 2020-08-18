package com.newco.marketplace.vo.provider;

import java.sql.Date;

import com.sears.os.vo.SerializableBaseVO;


public class LanguageVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1668479473658731641L;
	private Long resourceId= null;
	private Long languageId = null;
	private Date createdDate;
    private Date modifiedDate;
    private boolean active;
    private String descr;
    private String languageType;
    
	public Long getResourceId() {
		return resourceId;
	}
	public Long getLanguageId() {
		return languageId;
	}
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getLanguageType() {
		return languageType;
	}
	public void setLanguageType(String languageType) {
		this.languageType = languageType;
	}
	
}
