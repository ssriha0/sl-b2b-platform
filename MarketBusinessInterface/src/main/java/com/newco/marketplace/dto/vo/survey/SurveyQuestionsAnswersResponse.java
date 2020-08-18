package com.newco.marketplace.dto.vo.survey;

public class SurveyQuestionsAnswersResponse {
	
	CSATSurveyQuestionAnswers csat = null;
	NPSSurveyQuestionAnswers nps = null;
	Integer rating = 0;
	String surveyType = null;
	
	public CSATSurveyQuestionAnswers getCsat() {
		return csat;
	}
	public void setCsat(CSATSurveyQuestionAnswers csat) {
		this.csat = csat;
	}
	public NPSSurveyQuestionAnswers getNps() {
		return nps;
	}
	public void setNps(NPSSurveyQuestionAnswers nps) {
		this.nps = nps;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public boolean isCsat() {
		return this.csat != null;
	}
	public boolean isNps() {
		return this.nps != null;
	}
	public String getSurveyType() {
		return surveyType;
	}
	public void setSurveyType(String surveyType) {
		this.surveyType = surveyType;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SurveyQuestionsAnswersResponse [csat=").append(csat).append(", nps=").append(nps)
				.append(", rating=").append(rating).append(", surveyType=").append(surveyType).append("]");
		return builder.toString();
	}
	

	
	

}
