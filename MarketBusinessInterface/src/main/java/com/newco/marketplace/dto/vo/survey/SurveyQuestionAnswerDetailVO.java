package com.newco.marketplace.dto.vo.survey;

public class SurveyQuestionAnswerDetailVO {
	Integer questionId;
    String text;
    Integer selected;
    Integer buyerId;
    String surveyType;
	String subSurveyType;
    String instruction;
    String ratingsSelected;
    String comments;
    
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getSelected() {
		return selected;
	}
	public void setSelected(Integer selected) {
		this.selected = selected;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public String getSubSurveyType() {
		return subSurveyType;
	}
	public void setSubSurveyType(String subSurveyType) {
		this.subSurveyType = subSurveyType;
	}
	public String getSurveyType() {
		return surveyType;
	}
	public void setSurveyType(String surveyType) {
		this.surveyType = surveyType;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public String getRatingsSelected() {
		return ratingsSelected;
	}
	public void setRatingsSelected(String ratingsSelected) {
		this.ratingsSelected = ratingsSelected;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
    
    


}
