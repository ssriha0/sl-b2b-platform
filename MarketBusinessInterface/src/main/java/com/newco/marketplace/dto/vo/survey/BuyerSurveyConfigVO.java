package com.newco.marketplace.dto.vo.survey;

import com.sears.os.vo.SerializableBaseVO;

public class BuyerSurveyConfigVO extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5115461188680890723L;
	
	private int id;
	private int surveyId;
	private boolean priority;
	private int surveyOptionId;
	private String surveyTitle;
	private Integer buyerId;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getSurveyId() {
		return surveyId;
	}
	
	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}
	
	public boolean getPriority() {
		return priority;
	}
	
	public void setPriority(boolean priority) {
		this.priority = priority;
	}
	
	public int getSurveyOptionId() {
		return surveyOptionId;
	}
	
	public void setSurveyOptionId(int surveyOptionId) {
		this.surveyOptionId = surveyOptionId;
	}

	public String getSurveyTitle() {
		return surveyTitle;
	}

	public void setSurveyTitle(String surveyTitle) {
		this.surveyTitle = surveyTitle;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

}
