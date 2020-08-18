package com.newco.marketplace.dto.vo.survey;

public class BuyerConfigVO {	
	private Integer id;
	private Integer surveyId;
	private Integer surveyOptionID;	
	private Boolean priority;
	private String modifiedBy;
	
	
	public BuyerConfigVO(Integer id, Integer surveyId,Integer surveyOptionID, Boolean priority, String modifiedBy) {	
		this.id = id;
		this.surveyId = surveyId;
		this.surveyOptionID= surveyOptionID;
		this.priority = priority;
		this.modifiedBy= modifiedBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Integer getId() {
		return id;
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	public Boolean getPriority() {
		return priority;
	}

	public Integer getSurveyOptionID() {
		return surveyOptionID;
	}	
}
