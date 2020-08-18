package com.newco.marketplace.business.businessImpl.westsurvey;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.business.iBusiness.survey.ISurveyBO;
import com.newco.marketplace.business.iBusiness.westsurvey.IWestSurveyBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.survey.SurveyQuestionVO;
import com.newco.marketplace.dto.vo.survey.SurveyVO;
import com.newco.marketplace.dto.vo.survey.WestImportSummaryVO;
import com.newco.marketplace.dto.vo.survey.WestSurveyVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.SurveyConstants;
import com.newco.marketplace.persistence.iDao.westsurvey.WestSurveyDao;
import com.newco.marketplace.util.westsurvey.WestSurveyMapper;
import com.newco.marketplace.utils.SecurityUtil;

/**
 * This is the business implementation class for WestSurveyResponseProcessor.
 */
public class WestSurveyBO implements IWestSurveyBO {

	private final Logger logger = Logger.getLogger(WestSurveyBO.class);

	private IServiceOrderBO serviceOrderBO;
	private ISurveyBO surveyBO;
	private WestSurveyMapper surveyMapper;
	private WestSurveyDao westSurveyDaoImpl;

	/*
	 * SL-20908 Changes according to the new CSAT input file format
	 */
	public String saveSurveyResponse(WestSurveyVO westSurveyVO, Integer buyerId)
			throws BusinessServiceException {

		try{
			//Q2
			if(StringUtils.isNotBlank(westSurveyVO.getQuestion2())){
				westSurveyVO.setIntValueQuestion2(Integer.parseInt(westSurveyVO.getQuestion2()));
			}else{
				westSurveyVO.setIntValueQuestion2(SurveyConstants.INT_VALUE_ZERO);
			}
			
			//Q3
			if(StringUtils.isNotBlank(westSurveyVO.getQuestion3())){
				westSurveyVO.setIntValueQuestion3(Integer.parseInt(westSurveyVO.getQuestion3()));
			}else{
				westSurveyVO.setIntValueQuestion3(SurveyConstants.INT_VALUE_ZERO);
			}
			
			//Q4
			if(StringUtils.isNotBlank(westSurveyVO.getQuestion4())){
				westSurveyVO.setIntValueQuestion4(Integer.parseInt(westSurveyVO.getQuestion4()));
			}else{
				westSurveyVO.setIntValueQuestion4(SurveyConstants.INT_VALUE_ZERO);
			}
			
			//Q5
			if(StringUtils.isNotBlank(westSurveyVO.getQuestion5())){
				westSurveyVO.setIntValueQuestion5(Integer.parseInt(westSurveyVO.getQuestion5()));
			}else{
				westSurveyVO.setIntValueQuestion5(SurveyConstants.INT_VALUE_ZERO);
			}
			
			//Q6
			if(StringUtils.isNotBlank(westSurveyVO.getQuestion6())){
				westSurveyVO.setIntValueQuestion6(Integer.parseInt(westSurveyVO.getQuestion6()));
			}else{
				westSurveyVO.setIntValueQuestion6(SurveyConstants.INT_VALUE_ZERO);
			}
		}catch(NumberFormatException numEx){
			logger.error("West Survey record skipping; answer for one of Q2/Q3/Q4/Q5/Q6 is not a valid integer value for soId:"+westSurveyVO.getSoId());
			return null; 
		}
		//If Q2 answer is 0(zero), then ignore that record.
		if(westSurveyVO.getIntValueQuestion2().intValue() == SurveyConstants.INT_VALUE_ZERO){
			logger.error("West Survey record skipping; answer for Q2 is zero for soId:"+westSurveyVO.getSoId());
			return null; 
		}
		//If any of Q2 or Q3 answer is >10, ignore the record
		if(westSurveyVO.getIntValueQuestion2() > SurveyConstants.SURVEY_ANSWER_ID_TEN
		|| westSurveyVO.getIntValueQuestion3() > SurveyConstants.SURVEY_ANSWER_ID_TEN){
			logger.error("West Survey record skipping; answer for either of Q2 or Q3 is >10 for soId:"+westSurveyVO.getSoId());
			return null; 
		}
		
		//If any of Q4, Q5, Q6 answer is >2, ignore the record
		if(westSurveyVO.getIntValueQuestion4() > SurveyConstants.SURVEY_ANSWER_ID_TWO
		|| westSurveyVO.getIntValueQuestion5() > SurveyConstants.SURVEY_ANSWER_ID_TWO
		|| westSurveyVO.getIntValueQuestion6() > SurveyConstants.SURVEY_ANSWER_ID_TWO){
			logger.error("West Survey record skipping; answer for either of Q4 or Q5 or Q6 is >2 for soId:"+westSurveyVO.getSoId());
			return null; 
		}
		
		//If any of Q2, Q3, Q4, Q5, Q6 answerId is <0, ignore the record
		if(westSurveyVO.getIntValueQuestion2() < SurveyConstants.INT_VALUE_ZERO
		|| westSurveyVO.getIntValueQuestion3() < SurveyConstants.INT_VALUE_ZERO
		|| westSurveyVO.getIntValueQuestion4() < SurveyConstants.INT_VALUE_ZERO
		|| westSurveyVO.getIntValueQuestion5() < SurveyConstants.INT_VALUE_ZERO
		|| westSurveyVO.getIntValueQuestion6() < SurveyConstants.INT_VALUE_ZERO){
			logger.error("West Survey record skipping; answer for either of Q1/Q2/Q3/Q4/Q5/Q6 < 0 for soId:"+westSurveyVO.getSoId());
			return null; 
		}
		
		// If no matching SO is available in servicelive, then ignore the record.
		//get the soId by passing soid from the spreadsheet instead of fetching for external order number
		ServiceOrder serviceOrder = null;
		if(StringUtils.isNotBlank(westSurveyVO.getSoId())){
			serviceOrder = serviceOrderBO.getServiceOrder(westSurveyVO.getSoId());
			if (null == serviceOrder) {
				logger.error("West Survey record skipping; no matching service order found in ServiceLive for given soId# " + westSurveyVO.getSoId());
				return null;
			}
		}else{
			logger.error("West Survey record skipping; soId is empty or null " + westSurveyVO.getSoId());
			return null;
		}
		
		//R15_3 SL_20908
		westSurveyVO=updateWestSurveyConversionValues(westSurveyVO);

		// Save Providers ratings
		Integer buyerResourceId = serviceOrder.getBuyerResourceId();
		String soId = serviceOrder.getSoId();
		saveProviderRatings(buyerResourceId, soId, westSurveyVO, serviceOrder);
		return soId;
	}

	private void saveProviderRatings(Integer buyerResourceId, String soId, WestSurveyVO westSurveyVO, ServiceOrder serviceOrder) throws BusinessServiceException {
		SecurityContext securityContext = SecurityUtil.getSystemSecurityContext();
		SurveyVO surveyVO = new SurveyVO();
		surveyVO.setUserId(buyerResourceId);
		surveyVO.setServiceOrderID(soId);
		surveyVO.setSurveyType(SurveyConstants.SURVEY_TYPE_PROVIDER);
		surveyVO.setEntityType(SurveyConstants.ENTITY_BUYER);
		surveyVO.setEntityTypeId(SurveyConstants.ENTITY_BUYER_ID);
		surveyVO.setSurveyId(SurveyConstants.SURVEY_BUYER_ID);
		surveyVO.setSurveyTypeId(SurveyConstants.SURVEY_TYPE_PROVIDER_ID);
		surveyVO.setSurveyComments(StringUtils.EMPTY);
		try {
			surveyVO = surveyBO.retrieveQuestions(surveyVO, serviceOrder);
			ArrayList<SurveyQuestionVO> surveyQuestions = surveyVO.getQuestions();
			if (surveyQuestions == null || surveyQuestions.isEmpty()) {
				throw new BusinessServiceException("West Survey Unexpected error: Either service order is not closed yet for ratings or survey response already exists for this so; SO_ID: " + soId);
			}
			surveyMapper.mapWestSurveyVOToSurveyVO(westSurveyVO, surveyVO);
			surveyVO.setBatch(true);
			surveyBO.saveResponse(surveyVO, securityContext, serviceOrder);
		} catch (DataServiceException dsEx) {
			// Don't log, exception should have logged at root
			throw new BusinessServiceException(dsEx.getMessage(), dsEx);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.westsurvey.IWestSurveyBO#saveWestImportResults(com.newco.marketplace.dto.vo.survey.WestImportSummaryVO)
	 */
	public void saveWestImportResults(WestImportSummaryVO westImportSummaryVO) throws BusinessServiceException {
		try {
			int fileId = westSurveyDaoImpl.saveWestImportSummary(westImportSummaryVO);
			List<String> soIds = westImportSummaryVO.getSoIds();
			westSurveyDaoImpl.saveWestImportDetails(fileId, soIds);
		} catch (DataServiceException dsEx) {
			// Don't log, exception should have logged at root
			throw new BusinessServiceException(dsEx.getMessage(), dsEx);
		}
	}
	
	 /**
     * R15_4 SL-20988 Get the CSAT delimiter configured in DB
     * @param line
     * @param delimiter
     * @return WestSurveyVO
     */
	public String getCSATDelimiter() throws BusinessServiceException{
		String delimiter=null;
		try{
			delimiter = westSurveyDaoImpl.getCSATDelimiter();
		}catch (DataServiceException dsEx) {
			throw new BusinessServiceException(dsEx.getMessage(), dsEx);
		}
		return delimiter;
	}
	
	/**
	 * SL-20908 New CSAT input file mapping logic
	 * 
	 * Q1 Q2 Q3 Innovel to SL Conversion Rating as below
	 * Question		Innovel Rating		SL Rating
	 * Q1, Q2, Q3		9 or 10				5
	 * Q1, Q2, Q3		7 or 8				4
	 * Q1, Q2, Q3		5 or 6				3
	 * Q1, Q2, Q3		3 or 4				2
	 * Q1, Q2, Q3		1 or 2				1
	 * 
	 * 
	 * Q4 Q5 Q6 Innovel to SL Conversion Rating as below
	 * Question		Innovel Rating		SL Rating
	 * Q4, Q5, Q6			1				5
	 * Q4, Q5, Q6			2				2
	 * 
	 * 
	 * Calculation of Score
	 * 	CSAT (Provider)			SL CSAT Conversion Category Calculation
	 * 		Timeliness						(Q4 + Q5)/2
 	 * 		Communications						Q3 
 	 * 		Professional						Q3 
 	 * 		Quality							(Q2 + Q6)/2
 	 * 		Value							(Q2 + Q6)/2
 	 * 		Cleanliness							Q2
 	 * 		OVERALL							Average in SL (Timeliness+Communications+Professional+Quality+Value+Cleanliness)/2

	 * @param westSurveyList
	 * @return updatedWestSurveyList
	 * 
	 */
	private WestSurveyVO updateWestSurveyConversionValues(WestSurveyVO westSurveyVO){
		
		Integer slCSATConvertedQ2=getSLCSATConvertedQ2Q3(westSurveyVO.getIntValueQuestion2());
		Integer slCSATConvertedQ3=getSLCSATConvertedQ2Q3(westSurveyVO.getIntValueQuestion3());
		
		Integer slCSATConvertedQ4=getSLCSATConvertedQ4(westSurveyVO.getIntValueQuestion4());
		Integer slCSATConvertedQ5=getSLCSATConvertedQ5Q6(westSurveyVO.getIntValueQuestion5());
		Integer slCSATConvertedQ6=getSLCSATConvertedQ5Q6(westSurveyVO.getIntValueQuestion6());
		
		westSurveyVO.setSlCSATConvertedQ2(slCSATConvertedQ2);
		westSurveyVO.setSlCSATConvertedQ3(slCSATConvertedQ3);
		
		westSurveyVO.setSlCSATConvertedQ4(slCSATConvertedQ4);
		westSurveyVO.setSlCSATConvertedQ5(slCSATConvertedQ5);
		westSurveyVO.setSlCSATConvertedQ6(slCSATConvertedQ6);
		
		//If Q2!=0 and Q3||Q4||Q5||Q6=0, then Q3=Q4=Q5=Q6= Q2
		if((null != westSurveyVO.getSlCSATConvertedQ2() && westSurveyVO.getSlCSATConvertedQ2().intValue()>0) 
				&& ((null == westSurveyVO.getSlCSATConvertedQ3() || westSurveyVO.getSlCSATConvertedQ3().intValue()==0)
				||  (null == westSurveyVO.getSlCSATConvertedQ4() || westSurveyVO.getSlCSATConvertedQ4().intValue()==0)
				||  (null == westSurveyVO.getSlCSATConvertedQ5() || westSurveyVO.getSlCSATConvertedQ5().intValue()==0)
				||  (null == westSurveyVO.getSlCSATConvertedQ6() || westSurveyVO.getSlCSATConvertedQ6().intValue()==0))){
			westSurveyVO.setSlCSATConvertedQ3(westSurveyVO.getSlCSATConvertedQ2());
			westSurveyVO.setSlCSATConvertedQ4(westSurveyVO.getSlCSATConvertedQ2());
			westSurveyVO.setSlCSATConvertedQ5(westSurveyVO.getSlCSATConvertedQ2());
			westSurveyVO.setSlCSATConvertedQ6(westSurveyVO.getSlCSATConvertedQ2());
		}
		
		double initialTimelinessRating;
		double initialQualityRating;
		
		int timelinessRating;
		int communicationsRating;
		int professionalRating;
		int qualityRating;
		int valueRating;
		int cleanlinessRating;

		//(Q4+Q5)/2
		initialTimelinessRating=(double)(westSurveyVO.getSlCSATConvertedQ4().intValue()+westSurveyVO.getSlCSATConvertedQ5().intValue())/2;
		timelinessRating=getAvgRoundedRating(initialTimelinessRating);
		//Q3
		communicationsRating=westSurveyVO.getSlCSATConvertedQ3().intValue();
		//professionalRating=communicationsRating=Q3
		professionalRating=communicationsRating;
		//(Q2+Q6)/2
		initialQualityRating=(double)(westSurveyVO.getSlCSATConvertedQ2().intValue()+westSurveyVO.getSlCSATConvertedQ6().intValue())/2;
		qualityRating=getAvgRoundedRating(initialQualityRating);
		//valueRating=qualityRating=(Q2+Q6)/2
		valueRating=qualityRating;
		//Q2
		cleanlinessRating=westSurveyVO.getSlCSATConvertedQ2().intValue();
		
		westSurveyVO.setTimelinessRating(timelinessRating);
		westSurveyVO.setCommunicationsRating(communicationsRating);
		westSurveyVO.setProfessionalRating(professionalRating);
		westSurveyVO.setQualityRating(qualityRating);
		westSurveyVO.setValueRating(valueRating);
		westSurveyVO.setCleanlinessRating(cleanlinessRating);
		
		logger.info("timelinessRating:"+westSurveyVO.getTimelinessRating());
		logger.info("communicationsRating:"+westSurveyVO.getCommunicationsRating());
		logger.info("professionalRating:"+westSurveyVO.getProfessionalRating());
		logger.info("qualityRating:"+westSurveyVO.getQualityRating());
		logger.info("valueRating:"+westSurveyVO.getValueRating());
		logger.info("cleanlinessRating:"+westSurveyVO.getCleanlinessRating());
		
		return westSurveyVO;
	}
	
	private Integer getSLCSATConvertedQ2Q3(Integer value){
		Integer convertedValue=null;
		if(null != value && value.intValue()>0){
			if(value.equals(9) || value.equals(10)){
				convertedValue=5;
			}else if(value.equals(7) || value.equals(8)){
				convertedValue=4;
			}else if(value.equals(5) || value.equals(6)){
				convertedValue=3;
			}else if(value.equals(3) || value.equals(4)){
				convertedValue=2;
			}else if(value.equals(1) || value.equals(2)){
				convertedValue=1;
			}
		}
		return convertedValue;
	}
	
	private Integer getSLCSATConvertedQ4(Integer value){
		Integer convertedValue=null;
		if(null != value && value.intValue()>0){
			if(value.equals(1)){
				convertedValue=2;
			}else if(value.equals(2)){
				convertedValue=5;
			}
		}
		return convertedValue;
	}
	
	private Integer getSLCSATConvertedQ5Q6(Integer value){
		Integer convertedValue=null;
		if(null != value && value.intValue()>0){
			if(value.equals(1)){
				convertedValue=5;
			}else if(value.equals(2)){
				convertedValue=2;
			}
		}
		return convertedValue;
	}
	
	private int getAvgRoundedRating(final  double value){
		int finalAvgRating=0;
		if(value%1!=0){
			finalAvgRating = (int)value+1;
		}else{
			finalAvgRating=(int)value;
		}
		return finalAvgRating;
	}
	
	
	
	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public ISurveyBO getSurveyBO() {
		return surveyBO;
	}

	public void setSurveyBO(ISurveyBO surveyBO) {
		this.surveyBO = surveyBO;
	}

	public WestSurveyMapper getSurveyMapper() {
		return surveyMapper;
	}

	public void setSurveyMapper(WestSurveyMapper surveyMapper) {
		this.surveyMapper = surveyMapper;
	}

	public WestSurveyDao getWestSurveyDaoImpl() {
		return westSurveyDaoImpl;
	}

	public void setWestSurveyDaoImpl(WestSurveyDao westSurveyDaoImpl) {
		this.westSurveyDaoImpl = westSurveyDaoImpl;
	}

}
