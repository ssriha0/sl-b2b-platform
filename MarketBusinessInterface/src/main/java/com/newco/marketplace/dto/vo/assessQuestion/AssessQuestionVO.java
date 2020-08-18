package com.newco.marketplace.dto.vo.assessQuestion;

import java.util.ArrayList;

import com.sears.os.vo.SerializableBaseVO;

public class AssessQuestionVO extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3741577868630966872L;
	private int questionId;
	private int questionText;
	private AssessTemplateVO assessTemplate;
	private ArrayList<AssessAnswerVO> answers;
	private String requiredInd;
	private int sortOrder;
	
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public int getQuestionText() {
		return questionText;
	}
	public void setQuestionText(int questionText) {
		this.questionText = questionText;
	}
	public AssessTemplateVO getAssessTemplate() {
		return assessTemplate;
	}
	public void setAssessTemplate(AssessTemplateVO assessTemplate) {
		this.assessTemplate = assessTemplate;
	}
	public ArrayList<AssessAnswerVO> getAnswers() {
		return answers;
	}
	public void setAnswers(ArrayList<AssessAnswerVO> answers) {
		this.answers = answers;
	}
	public String getRequiredInd() {
		return requiredInd;
	}
	public void setRequiredInd(String requiredInd) {
		this.requiredInd = requiredInd;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
}
