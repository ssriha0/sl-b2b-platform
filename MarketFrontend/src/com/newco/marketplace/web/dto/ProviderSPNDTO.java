package com.newco.marketplace.web.dto;

public class ProviderSPNDTO extends SerializedBaseDTO
{
	private static final long serialVersionUID = 1L;
	private String networkName;
	private Integer performanceLevelId;
	private String performanceLevelName;
	private Double performanceScore;
	
	public String getNetworkName()
	{
		return networkName;
	}
	public void setNetworkName(String networkName)
	{
		this.networkName = networkName;
	}
	public Integer getPerformanceLevelId()
	{
		return performanceLevelId;
	}
	public void setPerformanceLevelId(Integer performanceLevelId)
	{
		this.performanceLevelId = performanceLevelId;
	}
	public String getPerformanceLevelName()
	{
		return performanceLevelName;
	}
	public void setPerformanceLevelName(String performanceLevelName)
	{
		this.performanceLevelName = performanceLevelName;
	}
	public Double getPerformanceScore() {
		return performanceScore;
	}
	public void setPerformanceScore(Double performanceScore) {
		this.performanceScore = performanceScore;
	}
	
}
