package com.newco.marketplace.dto.vo.assessQuestion;

import com.sears.os.vo.SerializableBaseVO;

public class AssessAnswerVO extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8895849364147344912L;
	private int answerId;
	private int questionId;
	private int answerText;
	private String defaultFlag;
	private int minVal;
	private int maxVal;
	private int sortOrder;
	private String requiredInd;
	
	public int getAnswerId() {
		return answerId;
	}
	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public int getAnswerText() {
		return answerText;
	}
	public void setAnswerText(int answerText) {
		this.answerText = answerText;
	}
	public String getDefaultFlag() {
		return defaultFlag;
	}
	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}
	public int getMinVal() {
		return minVal;
	}
	public void setMinVal(int minVal) {
		this.minVal = minVal;
	}
	public int getMaxVal() {
		return maxVal;
	}
	public void setMaxVal(int maxVal) {
		this.maxVal = maxVal;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getRequiredInd() {
		return requiredInd;
	}
	public void setRequiredInd(String requiredInd) {
		this.requiredInd = requiredInd;
	}
	
}
