package com.newco.marketplace.dto.vo.survey;

import com.sears.os.vo.SerializableBaseVO;

/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 */
public class SurveyRatingByQuestionVO extends SerializableBaseVO {

	private static final long serialVersionUID = -8670372755536738571L;

	private Integer questionId;
	private String question;
	private Double rating;
	private Integer surveyCount;
	private int starImageId;
	/**
	 * @return the questionId
	 */
	public Integer getQuestionId() {
		return questionId;
	}
	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	/**
	 * @return the rating
	 */
	public Double getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(Double rating) {
		this.rating = rating;
	}
	/**
	 * @return the surveyCount
	 */
	public Integer getSurveyCount() {
		return surveyCount;
	}
	/**
	 * @param surveyCount the surveyCount to set
	 */
	public void setSurveyCount(Integer surveyCount) {
		this.surveyCount = surveyCount;
	}
	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}
	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}
	public int getStarImageId() {
		return starImageId;
	}
	public void setStarImageId(int starImageId) {
		this.starImageId = starImageId;
	}
	
	
}
