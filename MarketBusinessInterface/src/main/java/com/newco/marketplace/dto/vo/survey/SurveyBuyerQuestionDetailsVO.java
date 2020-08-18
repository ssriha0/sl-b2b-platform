package com.newco.marketplace.dto.vo.survey;

import java.util.List;

public class SurveyBuyerQuestionDetailsVO {
	
	List<SurveyQuestionnaireDetailVO> surveyQuestion;
	Integer buyerId;
	String buyerName;
	String logo;	

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public List<SurveyQuestionnaireDetailVO> getSurveyQuestion() {
		return surveyQuestion;
	}

	public void setSurveyQuestion(List<SurveyQuestionnaireDetailVO> surveyQuestion) {
		this.surveyQuestion = surveyQuestion;
	}
}
