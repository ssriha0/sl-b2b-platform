package com.newco.marketplace.web.dto;

public class SPNBuilderCriteriaTableRowDTO extends SerializedBaseDTO{

	private static final long serialVersionUID = -2407776020605587397L;
	private Integer criteriaId;
	private String criteriaField;
	private String criteriaValue;
	
	public String getCriteriaValue() {
		return criteriaValue;
	}
	public void setCriteriaValue(String criteriaValue) {
		this.criteriaValue = criteriaValue;
	}
	public Integer getCriteriaId() {
		return criteriaId;
	}
	public void setCriteriaId(Integer criteriaId) {
		this.criteriaId = criteriaId;
	}
	public String getCriteriaField() {
		return criteriaField;
	}
	public void setCriteriaField(String criteriaField) {
		this.criteriaField = criteriaField;
	}
	
	

}
