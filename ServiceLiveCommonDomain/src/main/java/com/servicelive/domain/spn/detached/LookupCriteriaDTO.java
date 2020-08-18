package com.servicelive.domain.spn.detached;

public class LookupCriteriaDTO {

	private Integer criteriaId;
	private String criteriaName;
	private String description;
	private Double performanceValue;
	private Integer memberId;
	
	
	public Integer getCriteriaId() {
		return criteriaId;
	}
	public void setCriteriaId(Integer criteriaId) {
		this.criteriaId = criteriaId;
	}
	public String getCriteriaName() {
		return criteriaName;
	}
	public void setCriteriaName(String criteriaName) {
		this.criteriaName = criteriaName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPerformanceValue() {
		return performanceValue;
	}
	public void setPerformanceValue(Double performanceValue) {
		this.performanceValue = performanceValue;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
	
	
}
