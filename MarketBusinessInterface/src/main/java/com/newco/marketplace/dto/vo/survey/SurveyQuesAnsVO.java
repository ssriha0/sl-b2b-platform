/**
 * 
 */
package com.newco.marketplace.dto.vo.survey;

import com.sears.os.vo.SerializableBaseVO;


/**
 * @author schavda
 *
 */
public class SurveyQuesAnsVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4837201525736368172L;
	private int surveyId;
	private int statusId; 
	private int surveyTypeId; 
	private int entityTypeId; 
	
	private String title;
	private String author;
	private String instructions;
	
	private int questionId;
	private int categoryId; 
	private String questionText;
	private String questionDescription;
	
	//Table - survey_questions
	private int weight;
	private boolean required;
	private String instructionsQ;
	private int sortOrderQ;
	
//	Table - lu_survey_answers
	private int answerId;
	private String answerText;
	private int priorityId;
	private int score;
	private int sortOrderA;
	
	private String comments;
	private String overallScore;
	
	//Score value used to show appropriate star image on UI screen
	private int scoreNumber;
	
	/**
	 * @return the answerId
	 */
	public int getAnswerId() {
		return answerId;
	}
	/**
	 * @param answerId the answerId to set
	 */
	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
	/**
	 * @return the answerText
	 */
	public String getAnswerText() {
		return answerText;
	}
	/**
	 * @param answerText the answerText to set
	 */
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return the categoryId
	 */
	public int getCategoryId() {
		return categoryId;
	}
	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * @return the entityTypeId
	 */
	public int getEntityTypeId() {
		return entityTypeId;
	}
	/**
	 * @param entityTypeId the entityTypeId to set
	 */
	public void setEntityTypeId(int entityTypeId) {
		this.entityTypeId = entityTypeId;
	}
	/**
	 * @return the instructions
	 */
	public String getInstructionsQ() {
		return instructionsQ;
	}
	/**
	 * @param instructions the instructions to set
	 */
	public void setInstructionsQ(String instructionsQ) {
		this.instructionsQ = instructionsQ;
	}
	/**
	 * @return the instructions
	 */
	public String getInstructions() {
		return instructions;
	}
	/**
	 * @param instructions the intructions to set
	 */
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	/**
	 * @return the priorityId
	 */
	public int getPriorityId() {
		return priorityId;
	}
	/**
	 * @param priorityId the priorityId to set
	 */
	public void setPriorityId(int priorityId) {
		this.priorityId = priorityId;
	}
	/**
	 * @return the questionId
	 */
	public int getQuestionId() {
		return questionId;
	}
	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	/**
	 * @return the questionText
	 */
	public String getQuestionText() {
		return questionText;
	}
	/**
	 * @param questionText the questionText to set
	 */
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	/**
	 * @return the required
	 */
	public boolean isRequired() {
		return required;
	}
	/**
	 * @param required the required to set
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/**
	 * @return the sortOrderA
	 */
	public int getSortOrderA() {
		return sortOrderA;
	}
	/**
	 * @param sortOrderA the sortOrderA to set
	 */
	public void setSortOrderA(int sortOrderA) {
		this.sortOrderA = sortOrderA;
	}
	/**
	 * @return the sortOrderQ
	 */
	public int getSortOrderQ() {
		return sortOrderQ;
	}
	/**
	 * @param sortOrderQ the sortOrderQ to set
	 */
	public void setSortOrderQ(int sortOrderQ) {
		this.sortOrderQ = sortOrderQ;
	}
	/**
	 * @return the statusId
	 */
	public int getStatusId() {
		return statusId;
	}
	/**
	 * @param statusId the statusId to set
	 */
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	/**
	 * @return the surveyTypeId
	 */
	public int getSurveyTypeId() {
		return surveyTypeId;
	}
	/**
	 * @param surveyTypeId the surveyTypeId to set
	 */
	public void setSurveyTypeId(int surveyTypeId) {
		this.surveyTypeId = surveyTypeId;
	}
	/**
	 * @return the suveryId
	 */
	public int getSurveyId() {
		return surveyId;
	}
	/**
	 * @param suveryId the suveryId to set
	 */
	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getQuestionDescription() {
		return questionDescription;
	}
	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getOverallScore() {
		return overallScore;
	}
	public void setOverallScore(String overallScore) {
		this.overallScore = overallScore;
	}
	public int getScoreNumber() {
		return scoreNumber;
	}
	public void setScoreNumber(int scoreNumber) {
		this.scoreNumber = scoreNumber;
	}

}
