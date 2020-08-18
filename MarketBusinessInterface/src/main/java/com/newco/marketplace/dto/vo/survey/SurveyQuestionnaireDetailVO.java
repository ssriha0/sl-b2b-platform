package com.newco.marketplace.dto.vo.survey;

/**
 * Created by kjain on 1/2/2019.
 */
public class SurveyQuestionnaireDetailVO {

    Integer questionId;
    String text;
    String instruction;
    String surveyType;
    String subSurveyType;
    Integer buyerId;   

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
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

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
