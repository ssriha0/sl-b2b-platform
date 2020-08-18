package com.newco.marketplace.dto.vo.survey;

import com.sears.os.vo.SerializableBaseVO;

public class SurveyRatingSummaryVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3059369371225403950L;
	private double overallScore;
	private int ratedBy;
	public double getOverallScore() {
		return overallScore;
	}
	public void setOverallScore(double overallScore) {
		this.overallScore = overallScore;
	}
	public int getRatedBy() {
		return ratedBy;
	}
	public void setRatedBy(int ratedBy) {
		this.ratedBy = ratedBy;
	}
	
	
}
