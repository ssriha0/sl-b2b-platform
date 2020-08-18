package com.newco.marketplace.dto.vo.survey;

import java.util.Date;
import java.util.List;

public class ExtendedSaveSurveyVO {
		private Long responseHdrId;
		private int buyerId;
		private String serviceOrderID;
		private int surveyId;
		private String surveyType;
		private String surveyOptionID;
		private Date createdDate;
		private Date modifiedDate;
		private String modifiedBy;
		private int reviewedInd;
		private CSATSurvey csat;
		private NPSSurvey nps;
		private boolean agreed;
		private boolean submit;
		private int entityId;
		private int entityTypeId;
		private String subSurveyType;
		private String key;
		private String value;
		private int questionId;
		private int answer;
		private double rating;
		private String comments;
		private List<Integer> listOfSurveyIds;
		
		public int getBuyerId() {
			return buyerId;
		}
		public void setBuyerId(int buyerId) {
			this.buyerId = buyerId;
		}
		public String getServiceOrderID() {
			return serviceOrderID;
		}
		public void setServiceOrderID(String serviceOrderID) {
			this.serviceOrderID = serviceOrderID;
		}
		public int getSurveyId() {
			return surveyId;
		}
		public void setSurveyId(int surveyId) {
			this.surveyId = surveyId;
		}
		public String getSurveyType() {
			return surveyType;
		}
		public void setSurveyType(String surveyType) {
			this.surveyType = surveyType;
		}
		public Date getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}
		public Date getModifiedDate() {
			return modifiedDate;
		}
		public void setModifiedDate(Date modifiedDate) {
			this.modifiedDate = modifiedDate;
		}
		public String getModifiedBy() {
			return modifiedBy;
		}
		public void setModifiedBy(String modifiedBy) {
			this.modifiedBy = modifiedBy;
		}
		public int getReviewedInd() {
			return reviewedInd;
		}
		public void setReviewedInd(int reviewedInd) {
			this.reviewedInd = reviewedInd;
		}
		public CSATSurvey getCsat() {
			return csat;
		}
		public void setCsat(CSATSurvey csat) {
			this.csat = csat;
		}
		public NPSSurvey getNps() {
			return nps;
		}
		public void setNps(NPSSurvey nps) {
			this.nps = nps;
		}
		public boolean isAgreed() {
			return agreed;
		}
		public void setAgreed(boolean agreed) {
			this.agreed = agreed;
		}
		public boolean isSubmit() {
			return submit;
		}
		public void setSubmit(boolean submit) {
			this.submit = submit;
		}
		public int getEntityId() {
			return entityId;
		}
		public void setEntityId(int entityId) {
			this.entityId = entityId;
		}
		public int getEntityTypeId() {
			return entityTypeId;
		}
		public void setEntityTypeId(int entityTypeId) {
			this.entityTypeId = entityTypeId;
		}
		public String getSubSurveyType() {
			return subSurveyType;
		}
		public void setSubSurveyType(String subSurveyType) {
			this.subSurveyType = subSurveyType;
		}
		public Long getResponseHdrId() {
			return responseHdrId;
		}
		public void setResponseHdrId(Long responseHdrId) {
			this.responseHdrId = responseHdrId;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public int getQuestionId() {
			return questionId;
		}
		public void setQuestionId(int questionId) {
			this.questionId = questionId;
		}
		public int getAnswer() {
			return answer;
		}
		public void setAnswer(int answer) {
			this.answer = answer;
		}
		public double getRating() {
			return rating;
		}
		public void setRating(double rating) {
			this.rating = rating;
		}
		public String getComments() {
			return comments;
		}
		public void setComments(String comments) {
			this.comments = comments;
		}
		public String getSurveyOptionID() {
			return surveyOptionID;
		}
		public void setSurveyOptionID(String surveyOptionID) {
			this.surveyOptionID = surveyOptionID;
		}
		public List<Integer> getListOfSurveyIds() {
			return listOfSurveyIds;
		}
		public void setListOfSurveyIds(List<Integer> listOfSurveyIds) {
			this.listOfSurveyIds = listOfSurveyIds;
		}
}
