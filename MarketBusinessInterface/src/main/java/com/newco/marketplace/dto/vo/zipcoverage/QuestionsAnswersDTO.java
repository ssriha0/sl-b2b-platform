package com.newco.marketplace.dto.vo.zipcoverage;

public class QuestionsAnswersDTO {
	
	private Integer questionId;
	private String  question;
	private String  answer;
	
	public QuestionsAnswersDTO(){
		
	}
	
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	

}
