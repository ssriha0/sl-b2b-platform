package com.newco.marketplace.dto.vo.survey;

import java.util.ArrayList;



public class CSATSurveyQuestionAnswers {
	String ratingSelected;
    CsatQuestion questions;
    String comments;
    ArrayList<CsatOptionsSelected> options;
    
	public String getRatingSelected() {
		return ratingSelected;
	}
	public void setRatingSelected(String ratingSelected) {
		this.ratingSelected = ratingSelected;
	}
	public CsatQuestion getQuestions() {
		return questions;
	}
	public void setQuestions(CsatQuestion questions) {
		this.questions = questions;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public ArrayList<CsatOptionsSelected> getOptions() {
		return options;
	}
	public void setOptions(ArrayList<CsatOptionsSelected> options) {
		this.options = options;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CSATSurveyQuestionAnswers [ratingSelected=").append(ratingSelected).append(", questions=")
				.append(questions.toString()).append(", comments=").append(comments).append(", options=").append(options.toString())
				.append("]");
		return builder.toString();
	} 	
   
}
