package com.newco.marketplace.vo.receipts;

import java.io.Serializable;

public class ServiceLiveBucksCreditReceiptVO implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -862039409487894040L;
	private Integer entityID;
	private Integer entityTypeID;
	private Integer entryRuleID;
	private String baseTime;
	private String diffTime;
	/**
	 * @return the baseTime
	 */
	public String getBaseTime() {
		return baseTime;
	}
	/**
	 * @param baseTime the baseTime to set
	 */
	public void setBaseTime(String baseTime) {
		this.baseTime = baseTime;
	}
	/**
	 * @return the diffTime
	 */
	public String getDiffTime() {
		return diffTime;
	}
	/**
	 * @param diffTime the diffTime to set
	 */
	public void setDiffTime(String diffTime) {
		this.diffTime = diffTime;
	}
	/**
	 * @return the entityRuleID
	 */
	public Integer getEntryRuleID() {
		return entryRuleID;
	}
	/**
	 * @param entityRuleID the entityRuleID to set
	 */
	public void setEntryRuleID(Integer entityRuleID) {
		this.entryRuleID = entityRuleID;
	}
	/**
	 * @return the entityID
	 */
	public Integer getEntityID() {
		return entityID;
	}
	/**
	 * @param entityID the entityID to set
	 */
	public void setEntityID(Integer entityID) {
		this.entityID = entityID;
	}
	/**
	 * @return the entityTypeID
	 */
	public Integer getEntityTypeID() {
		return entityTypeID;
	}
	/**
	 * @param entityTypeID the entityTypeID to set
	 */
	public void setEntityTypeID(Integer entityTypeID) {
		this.entityTypeID = entityTypeID;
	}

}
