package com.newco.marketplace.dto.vo.survey;

public class NPSSurveyQuestionAnswers {
	
	String ratingSelected;
	String comments;
    String question;
	
    public String getRatingSelected() {
		return ratingSelected;
	}
	public void setRatingSelected(String ratingSelected) {
		this.ratingSelected = ratingSelected;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NPSSurveyQuestionAnswers [ratingSelected=").append(ratingSelected).append(", comments=")
				.append(comments).append(", question=").append(question).append("]");
		return builder.toString();
	}
    
    

}
