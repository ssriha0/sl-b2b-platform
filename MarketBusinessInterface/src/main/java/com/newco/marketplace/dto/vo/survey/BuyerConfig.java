package com.newco.marketplace.dto.vo.survey;

public class BuyerConfig {	
	
	private Integer id;
	
	private Integer surveyOptionID;
	
	private String surveyTitle;
	
	private String user;

	
	public BuyerConfig(Integer id, Integer surveyOptionID, String surveyTitle, String user) {
		this.id = id;
		this.surveyOptionID = surveyOptionID;
		this.surveyTitle = surveyTitle;
		this.user=user;
	}
	
	public String getUser() {
		return user;
	}


	public Integer getId() {
		return id;
	}

	public Integer getSurveyOptionID() {
		return surveyOptionID;
	}

	public String getSurveyTitle() {
		return surveyTitle;
	}

	@Override
	public String toString() {
		return "BuyerConfig [id=" + id + ", surveyOptionID=" + surveyOptionID + ", surveyTitle=" + surveyTitle + "]";
	}	
	
}
