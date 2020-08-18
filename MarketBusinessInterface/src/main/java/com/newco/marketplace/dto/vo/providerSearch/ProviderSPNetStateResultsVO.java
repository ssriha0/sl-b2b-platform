package com.newco.marketplace.dto.vo.providerSearch;

import com.sears.os.vo.SerializableBaseVO;

public class ProviderSPNetStateResultsVO extends SerializableBaseVO{

	private static final long serialVersionUID = 5234759801882894074L;
	
	private Integer spnId;
	private Integer providerResourceId;
	private String providerSpnetStateId;
	private Integer aliasSpnetId;
	private String aliasProviderSpnetStateId;
	private Integer performanceLevel;
	private String spnName;
	private String performanceLevelDesc;
	private Double performanceScore;
	
	/**
	 * @return the spnId
	 */
	public Integer getSpnId() {
		return spnId;
	}
	/**
	 * @param spnId the spnId to set
	 */
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	/**
	 * @return the providerResourceId
	 */
	public Integer getProviderResourceId() {
		return providerResourceId;
	}
	/**
	 * @param providerResourceId the providerResourceId to set
	 */
	public void setProviderResourceId(Integer providerResourceId) {
		this.providerResourceId = providerResourceId;
	}
	/**
	 * @return the providerSpnetStateId
	 */
	public String getProviderSpnetStateId() {
		return providerSpnetStateId;
	}
	/**
	 * @param providerSpnetStateId the providerSpnetStateId to set
	 */
	public void setProviderSpnetStateId(String providerSpnetStateId) {
		this.providerSpnetStateId = providerSpnetStateId;
	}
	/**
	 * @return the aliasSpnetId
	 */
	public Integer getAliasSpnetId() {
		return aliasSpnetId;
	}
	/**
	 * @param aliasSpnetId the aliasSpnetId to set
	 */
	public void setAliasSpnetId(Integer aliasSpnetId) {
		this.aliasSpnetId = aliasSpnetId;
	}
	/**
	 * @return the aliasProviderSpnetStateId
	 */
	public String getAliasProviderSpnetStateId() {
		return aliasProviderSpnetStateId;
	}
	/**
	 * @param aliasProviderSpnetStateId the aliasProviderSpnetStateId to set
	 */
	public void setAliasProviderSpnetStateId(String aliasProviderSpnetStateId) {
		this.aliasProviderSpnetStateId = aliasProviderSpnetStateId;
	}
	/**
	 * performance level of provider
	 * @return the performanceLevel
	 */
	public Integer getPerformanceLevel() {
		return performanceLevel;
	}
	/**
	 * 
	 * @param performanceLevel
	 */
	public void setPerformanceLevel(Integer performanceLevel) {
		this.performanceLevel = performanceLevel;
	}
	
	/**
	 * 
	 * @return spnName
	 */
	public String getSpnName() {
		return spnName;
	}
	/**
	 * 
	 * @param spnName
	 */
	public void setSpnName(String spnName) {
		this.spnName = spnName;
	}
	/**
	 * Description for performanceLevel
	 * @return performanceLevelDesc
	 */
	public String getPerformanceLevelDesc() {
		return performanceLevelDesc;
	}
	public void setPerformanceLevelDesc(String performanceLevelDesc) {
		this.performanceLevelDesc = performanceLevelDesc;
	}
	public Double getPerformanceScore() {
		return performanceScore;
	}
	public void setPerformanceScore(Double performanceScore) {
		this.performanceScore = performanceScore;
	}

}
