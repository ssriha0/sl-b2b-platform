package com.newco.batch.updaterating;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.survey.ExtendedSurveyBO;



public class UpdateRatingProcessor {
	private static final Logger logger = Logger.getLogger(UpdateRatingProcessor.class.getName());

	private ExtendedSurveyBO extendedSurveyBO;

	public void process() throws Exception {
		logger.info("started process() method of UpdateRatingProcessor");
		
		extendedSurveyBO.processRatings();
		
		logger.info("Leaving process() method of UpdateRatingProcessor");
	}

	public ExtendedSurveyBO getExtendedSurveyBO() {
		return extendedSurveyBO;
	}

	public void setExtendedSurveyBO(ExtendedSurveyBO extendedSurveyBO) {
		this.extendedSurveyBO = extendedSurveyBO;
	}

}
