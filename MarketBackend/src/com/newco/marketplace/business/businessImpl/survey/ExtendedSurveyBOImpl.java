package com.newco.marketplace.business.businessImpl.survey;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.survey.ExtendedSurveyBO;
import com.newco.marketplace.business.iBusiness.survey.IAnalyticalSurveyRatingBO;
import com.newco.marketplace.business.iBusiness.systemgeneratedemail.ISystemGeneratedBO;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.dto.vo.survey.BuyerConfig;
import com.newco.marketplace.dto.vo.survey.BuyerSurveyConfigVO;
import com.newco.marketplace.dto.vo.survey.CSATSurveyQuestionAnswers;
import com.newco.marketplace.dto.vo.survey.CsatOptionsSelected;
import com.newco.marketplace.dto.vo.survey.CsatQuestion;
import com.newco.marketplace.dto.vo.survey.ExtendedSaveSurveyVO;
import com.newco.marketplace.dto.vo.survey.NPSSurveyQuestionAnswers;
import com.newco.marketplace.dto.vo.survey.SODetailVO;
import com.newco.marketplace.dto.vo.survey.SurveyBuyerQuestionDetailsVO;
import com.newco.marketplace.dto.vo.survey.SurveyOptionVO;
import com.newco.marketplace.dto.vo.survey.SurveyQuestionAnswerDetailVO;
import com.newco.marketplace.dto.vo.survey.SurveyQuestionnaireDetailVO;
import com.newco.marketplace.dto.vo.survey.SurveyQuestionsAnswersResponse;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.EmailDataVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.EmailPropertyKeyEnum;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.SurveyConstants;
import com.newco.marketplace.persistence.iDao.buyer.IBuyerFeatureSetDAO;
import com.newco.marketplace.persistence.iDao.survey.ExtendedSurveyDAO;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.util.SurveyCryptographyUtil;
import com.newco.marketplace.util.constants.SystemGeneratedEmailConstants;
import com.newco.marketplace.utils.RandomGUID;
import com.sears.os.business.ABaseBO;
import com.sears.os.service.ServiceConstants;
import com.servicelive.common.util.DateUtils;

/**
 * Created by kjain on 12/28/2018.
 */
public class ExtendedSurveyBOImpl extends ABaseBO implements ExtendedSurveyBO,
        ServiceConstants, OrderConstants, SurveyConstants {

    private static final Logger logger = Logger.getLogger(ExtendedSurveyBOImpl.class
            .getName());

    private ExtendedSurveyDAO extendedSurveyDAO;
    
    private ISystemGeneratedBO iSystemGeneratedBO;
    
    private SurveyCryptographyUtil surveyCryptographyUtil;
    
    private IBuyerFeatureSetDAO buyerFeatureSetDAO;
    
    private IAnalyticalSurveyRatingBO analyticalSurveyRating;
    
 

	@Override
	public void processRatings() throws BusinessServiceException {
	logger.info(" start of processRatings of ExtendedSurveyBOImpl");    
    String date =DateUtils.getFormatedDate(new java.util.Date(), DateUtils.YYYYMMDDHHMMSS_format);
    try {
		analyticalSurveyRating.updateBuyerGivenRating(date);
		logger.info("Updated Buyer Given Rating ExtendedSurveyBOImpl");     
		analyticalSurveyRating.updateEntityRating(date);
		logger.info("Entity Ratings are updated Sucessfully.[ExtendedSurveyBOImpl]"); 
		extendedSurveyDAO.updateLastUpdatedSurveyRatingKey(date);
    	}catch (DataServiceException e) {				
    		logger.error(e.toString());
			throw new BusinessServiceException(e);
		}catch(Exception e){
			logger.error(e);
			throw new BusinessServiceException(e);
		}
    	logger.info(" end of processRatings of ExtendedSurveyBOImpl");
    }

    @Override
    public boolean validateSurvey(String encryptedKey) throws BusinessServiceException{
        logger.debug(" start of validateSurvey of SurveyBOImpl");
        boolean soSubmitted=false;
        try {
        	String serviceOrderId = getSoIdFromEncryptedKey(encryptedKey);
            soSubmitted = extendedSurveyDAO.isSurveySubmitted(serviceOrderId);
        }catch (DataServiceException e) {
			logger.error("Error in validate Survey :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
        logger.debug(" end of validateSurvey of ExtendedSurveyBOImpl");
        return soSubmitted;

    }
    
    private String getSoIdFromEncryptedKey(String encryptedKey) throws BusinessServiceException{
    	List<String> fixedLenghtLis;
    	try{
    		String decryptedKey=decryptSoId(encryptedKey);
        	if(decryptedKey == null)
        		throw new BusinessServiceException(ResultsCode.SAVE_SURVEY_INVALID_KEY.getMessage());
        	fixedLenghtLis = Arrays.asList(decryptedKey.split("\\|"));
    	}
    	catch(BusinessServiceException e){
    		logger.info("Error in decrypting :" + e.getMessage());
    		throw new BusinessServiceException(ResultsCode.SAVE_SURVEY_INVALID_KEY.getMessage());
    	}
        return fixedLenghtLis.get(0);
    }
    
    private String getSurveyTypeFromEncryptedKey(String encryptedKey) throws BusinessServiceException{
    	List<String> fixedLenghtLis;
        try{
        	String decryptedKey=decryptSoId(encryptedKey);
        	if(decryptedKey == null)
        		throw new BusinessServiceException(ResultsCode.SAVE_SURVEY_INVALID_KEY.getMessage());
        	fixedLenghtLis = Arrays.asList(decryptedKey.split("\\|"));
        	if(fixedLenghtLis.size()<3)
            	return null;
        }
        catch(BusinessServiceException e){
    		logger.info("Error in decrypting :" + e.getMessage());
    		throw new BusinessServiceException(ResultsCode.SAVE_SURVEY_INVALID_KEY.getMessage());
    	}
    	
    	return fixedLenghtLis.get(1);
    }
    
    private List<String> getSurveyIdFromEncryptedKey(String encryptedKey) throws BusinessServiceException{
    	List<String> surveyIdListOfString=null;
    	try{
    		String decryptedKey=decryptSoId(encryptedKey);
        	if(decryptedKey == null)
        		throw new BusinessServiceException(ResultsCode.SAVE_SURVEY_INVALID_KEY.getMessage());
        	List<String> fixedLenghtLis = Arrays.asList(decryptedKey.split("\\|"));
        	if(fixedLenghtLis.size()<3)
            	return null;
        	String decryptedSurveyIds=fixedLenghtLis.get(2);
        	if(decryptedSurveyIds != null)
        		surveyIdListOfString=Arrays.asList(decryptedSurveyIds.split("_"));
    	}
    	catch(BusinessServiceException e){
    		logger.info("Error in decrypting :" + e.getMessage());
			throw new BusinessServiceException(ResultsCode.SAVE_SURVEY_INVALID_KEY.getMessage());
    	}
    	return surveyIdListOfString;
    }

    @Override
    public SurveyBuyerQuestionDetailsVO fetchQuestionnaire(String encryptedKey) throws BusinessServiceException{
        logger.debug(" start of getQuestions of SurveyBOImpl");
        List<String> fixedLenghtLis = Arrays.asList(decryptSoId(encryptedKey).split("\\|"));
        String serviceOrderId = fixedLenghtLis.get(0);
        String surveyType = fixedLenghtLis.get(1);        
        SurveyBuyerQuestionDetailsVO completeData = new SurveyBuyerQuestionDetailsVO();
        try {
        	Pattern pattern = Pattern.compile("_");
        	List<Integer> surveyIds = pattern.splitAsStream(fixedLenghtLis.get(2))
        	                            .map(Integer::valueOf)
        	                            .collect(Collectors.toList());
        	List<SurveyQuestionnaireDetailVO> surveyDetailVoList = extendedSurveyDAO.getQuestionsForBuyer(serviceOrderId, surveyIds);
        	surveyDetailVoList.stream().forEach((surveyDetailVo) -> surveyDetailVo.setSurveyType(surveyType));
            completeData.setSurveyQuestion(surveyDetailVoList);
            
            //Get Buyer Details
            if(surveyDetailVoList != null && !surveyDetailVoList.isEmpty()){
	            completeData.setBuyerId(surveyDetailVoList.get(0).getBuyerId());
	            Set<Integer> buyerIds = new HashSet<Integer>();
	            buyerIds.add(surveyDetailVoList.get(0).getBuyerId());
	            List<EmailDataVO> emailDataVOList = iSystemGeneratedBO.getEmailDataForBuyer(buyerIds);
	            for(EmailDataVO emailDataVO: emailDataVOList){
	            	if(EmailPropertyKeyEnum.BUYERLOGO.getKey().equalsIgnoreCase(emailDataVO.getParamKey())){
	            		completeData.setLogo(PropertiesUtils.getPropertyValue(SystemGeneratedEmailConstants.LOGO_URL) + emailDataVO.getParamValue());
	            	}else if(EmailPropertyKeyEnum.BUYERNAME.getKey().equalsIgnoreCase(emailDataVO.getParamKey())){
	            		completeData.setBuyerName(emailDataVO.getParamValue());
	            	}
	            }       
            }
        }catch (DataServiceException e) {
			logger.error("Error in fatching Questionnaire :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
        logger.debug(" end of fetchQuestionnaire of ExtendedSurveyBOImpl");
        return completeData;
    }
    
    @Override
	public void saveBuyerConfiguration(Integer buyerid, Integer surveyOptionId, String surveyType, String user) throws BusinessServiceException {
    	try {
    		extendedSurveyDAO.saveBuyerConfiguration(new BuyerConfig(buyerid, surveyOptionId, surveyType, user));		
    	}catch(DataServiceException e) {
			logger.error("Error in fatching saveBuyerConfiguration :" + e.getMessage());
			throw new BusinessServiceException(e);
    	}		
	}
    
    @Override
    public List<SurveyOptionVO> getSurveyOptions() throws BusinessServiceException{
		List<SurveyOptionVO> surveyOptionVOList=null;
		try {
			surveyOptionVOList=extendedSurveyDAO.getSurveyOptions();
		} catch (DataServiceException e) {
			logger.info("exception while fetching the all survey options "+e.getMessage());
			throw new BusinessServiceException(e);
		}
		return surveyOptionVOList;
	}   
    
	public BuyerSurveyConfigVO getBuyerSurveySelectedDetails(int buyerId) throws BusinessServiceException{
		BuyerSurveyConfigVO buyerSurveyConfigVO=null;		
		try {
			buyerSurveyConfigVO=extendedSurveyDAO.getBuyerSurveySelectedDetails(buyerId);
		} catch (DataServiceException e) {
			logger.info("exception while fetching the buyer selected survey details "+e.getMessage());
			throw new BusinessServiceException(e);
		}
		return buyerSurveyConfigVO;
	}
	
	public String getServiceOrderFlowType(String soId) throws BusinessServiceException {
		try {
			
			SODetailVO soHdr = extendedSurveyDAO.getSoDetails(soId);
			if(soHdr != null && soHdr.getBuyerId()==null)
				return "Invalid so_id";
			
			String csatNpsSurveyFlowStartDateString=PropertiesUtils.getPropertyValue(CSAT_NPS_NEWFLOW_APPKEY);
			String feature = buyerFeatureSetDAO.getFeature(soHdr.getBuyerId(), BuyerFeatureConstants.CONSUMER_SURVEY);
			Integer status = soHdr.getSoStatus();			
			if (Integer.valueOf(OrderConstants.CLOSED_STATUS).equals(status)) {
				return getClosedSurveyFlowType(soId, soHdr, csatNpsSurveyFlowStartDateString, feature);
			} else if (Integer.valueOf(OrderConstants.COMPLETED_STATUS).equals(status)) {
				return getCompletedSurveyFlow(soId, soHdr, csatNpsSurveyFlowStartDateString, feature);				
			} else {
				return NO_TAB;
			}
		} catch (DataServiceException e) {
			logger.info("exception while checking servce order survey flow type "+e.getMessage());
			throw new BusinessServiceException(e);
		}
	}

	private String getClosedSurveyFlowType(String soId, SODetailVO soHdr, String csatNpsSurveyFlowStartDateString,
			String feature) throws DataServiceException {
		List<Integer> surveyIdList = extendedSurveyDAO.getSurveyForSO(soId);
		if(surveyIdList.isEmpty()){
			if(StringUtils.isBlank(feature))
				return CSAT_NPS_OLDFLOW;
			else{
				Date csatNpsSurveyFlowStartDate=null;
				if(StringUtils.isNotBlank(csatNpsSurveyFlowStartDateString)){
					csatNpsSurveyFlowStartDate = DateUtils.getDateFromString(csatNpsSurveyFlowStartDateString, DATFORMAT);
					Date soDate=soHdr.getClosedDate();
					if(csatNpsSurveyFlowStartDate!=null && soDate!=null){
						if(soDate.before(csatNpsSurveyFlowStartDate)){
							return CSAT_NPS_OLDFLOW;
						}else{
							// check for configuration 
							String eventType=extendedSurveyDAO.getEventTypeBySoId(soId);
							if(StringUtils.isNotBlank(eventType)){
								if(CSAT_NPS_EVENT_COMPLETED.equalsIgnoreCase(eventType)){
									soDate=soHdr.getCompletedDate();
									if(soDate.before(csatNpsSurveyFlowStartDate)){
										return CSAT_NPS_OLDFLOW;
									}else{												
										return NOT_RATED;
									}
								}else {
									return NOT_RATED;
								}
								
							}else{
								return SURVEY_NOT_CONFIGURED;
							}									
						}									
					}else{
						return CSAT_NPS_OLDFLOW;
					}
				}else{
					return CSAT_NPS_OLDFLOW;
				}
			}
			
		}else{
			//new survey
			if(surveyIdList.contains(30) || surveyIdList.contains(40) 
					|| surveyIdList.contains(50) || surveyIdList.contains(60)){
				return CSAT_NPS_NEWFLOW;
			}
			//old survey
			return CSAT_NPS_OLDFLOW;
		}
	}

	private String getCompletedSurveyFlow(String soId, SODetailVO soHdr, String csatNpsSurveyFlowStartDateString,
			String feature) throws DataServiceException {
		if(StringUtils.isBlank(feature))
			return NO_TAB;
		else{
			//survey available
			List<Integer> surveyIdList = extendedSurveyDAO.getSurveyForSO(soId);
			if(surveyIdList.isEmpty()){
				Date csatNpsSurveyFlowStartDate=null;
				if(StringUtils.isNotBlank(csatNpsSurveyFlowStartDateString)){
					csatNpsSurveyFlowStartDate = DateUtils.getDateFromString(csatNpsSurveyFlowStartDateString, DATFORMAT);
					Date soDate=soHdr.getCompletedDate();
					if(csatNpsSurveyFlowStartDate!=null && soDate!=null){
						if(soDate.before(csatNpsSurveyFlowStartDate)){
							return NO_TAB;
						}else{
							String eventType=extendedSurveyDAO.getEventTypeBySoId(soId);
							if(StringUtils.isNotBlank(eventType)){
								if(CSAT_NPS_EVENT_COMPLETED.equalsIgnoreCase(eventType)){
									return NOT_RATED;
								}else{
									return NO_TAB;
								}
							}else{
								return SURVEY_NOT_CONFIGURED;
							}
						}
					}else{
						return NO_TAB;
					}									
				}else{
					return NO_TAB;
				}
				
			}else{	
				return CSAT_NPS_NEWFLOW;
			}
		}
	}
	
	
	// SLT-1649
	@Override
	public String updateSaveSurveyDetails(String subSurveyType, Long responseHDRId, ExtendedSaveSurveyVO saveSurveyVO)
			throws BusinessServiceException {
		try {
			logger.info("[Entering updateSaveSurveyDetails of ExtendedSurveyBOImpl]"); 
			saveSurveyVO.setSubSurveyType(subSurveyType);
			saveSurveyVO.setResponseHdrId(responseHDRId);

			if (subSurveyType.equalsIgnoreCase(PublicAPIConstant.CSAT)) {
				saveSurveyVO.setRating(saveSurveyVO.getCsat().getRating());
				saveSurveyVO.setComments((saveSurveyVO.getCsat().getComments()));
			} else if (subSurveyType.equalsIgnoreCase(PublicAPIConstant.NPS)) {
				saveSurveyVO.setRating(saveSurveyVO.getNps().getRating());
				saveSurveyVO.setComments(saveSurveyVO.getNps().getComments());
			}
			extendedSurveyDAO.updateSaveSurveyDetails(saveSurveyVO);
			if (subSurveyType.equalsIgnoreCase(PublicAPIConstant.CSAT)) {
				mapSurveyAnswers(saveSurveyVO);
			}
		} catch (DataServiceException e) {
			logger.info("[updateSaveSurveyDetails of ExtendedSurveyBOImpl] : Error in updating survey details" + e.getMessage());
			throw new BusinessServiceException(ResultsCode.INTERNAL_SERVER_ERROR.getMessage());
		}
		return "success";
	}

	@Override
	public String insertSaveSurveyDetails(String subSurveyType, ExtendedSaveSurveyVO saveSurveyVO)
			throws BusinessServiceException {
		try {
			logger.info("[Entering insertSaveSurveyDetails of ExtendedSurveyBOImpl]"); 
			Long responseHdrId = responseHdrIdGenerator();
			saveSurveyVO.setSubSurveyType(subSurveyType);
			saveSurveyVO.setResponseHdrId(responseHdrId);
			ExtendedSaveSurveyVO extendedSaveSurveyVO = new ExtendedSaveSurveyVO();
			extendedSaveSurveyVO = extendedSurveyDAO.getSurveyId(saveSurveyVO);
			if (extendedSaveSurveyVO == null) {
				throw new BusinessServiceException("Buyer has not configured the survey");
			}
			saveSurveyVO.setSurveyId(extendedSaveSurveyVO.getSurveyId());
			saveSurveyVO.setEntityTypeId(extendedSaveSurveyVO.getEntityTypeId());
			if (subSurveyType.equalsIgnoreCase(PublicAPIConstant.CSAT)) {
				saveSurveyVO.setRating(saveSurveyVO.getCsat().getRating());
				saveSurveyVO.setComments((saveSurveyVO.getCsat().getComments()));
			} else if (subSurveyType.equalsIgnoreCase(PublicAPIConstant.NPS)) {
				saveSurveyVO.setRating(saveSurveyVO.getNps().getRating());
				saveSurveyVO.setComments(saveSurveyVO.getNps().getComments());
			}
			extendedSurveyDAO.insertSaveSurveyDetails(saveSurveyVO);
			if (subSurveyType.equalsIgnoreCase(PublicAPIConstant.CSAT)) {
				mapSurveyAnswers(saveSurveyVO);
			}

		} catch (DataServiceException e) {
			logger.info("[insertSaveSurveyDetails of ExtendedSurveyBOImpl] : " + e.getMessage());
			throw new BusinessServiceException(ResultsCode.INTERNAL_SERVER_ERROR.getMessage());
		}
		return "success";
	}

	@Override
	public Integer fetchSurveyId(Long responseHDRId) throws BusinessServiceException {
		Integer fetchSurveyId = null;
		try {
			logger.info("[Entering fetchSurveyId of ExtendedSurveyBOImpl]");
			fetchSurveyId = extendedSurveyDAO.fetchSurveyId(responseHDRId);
		} catch (DataServiceException e) {
			logger.info("[Leaving fetchSurveyId() of ExtendedSurveyBOImpl] :Exception occured while fetching surveyId " + e.getMessage());
			throw new BusinessServiceException(ResultsCode.SAVE_SURVEY_INVALID_KEY.getMessage());
		}
		return fetchSurveyId;
	}

	@Override
	public Long getResponseHDRId(String soId) throws BusinessServiceException {
		Long responseHdrId = null;
		try {
			logger.info("[Entering getResponseHDRId of ExtendedSurveyBOImpl]"); 
			responseHdrId = extendedSurveyDAO.getResponseHDRId(soId);
		} catch (DataServiceException e) {
			logger.info("[Leaving getResponseHDRId() of ExtendedSurveyBOImpl] : " + e.getMessage());
			throw new BusinessServiceException(ResultsCode.SAVE_SURVEY_INVALID_KEY.getMessage());
		}
		return responseHdrId;
	}

	@Override
	public String fetchSubSurveyType(Integer surveyId) throws BusinessServiceException {
		String subSurveyType = null;
		try {
			logger.info("[Entering fetchSubSurveyType of ExtendedSurveyBOImpl]");
			subSurveyType = extendedSurveyDAO.fetchSubSurveyType(surveyId);
			if(subSurveyType == null){
				logger.info("[fetchSubSurveyType() of ExtendedSurveyBOImpl] : Invalid subSurveyType ");
				throw new BusinessServiceException(ResultsCode.SAVE_SURVEY_INVALID_KEY.getMessage());
			}
		} catch (DataServiceException e) {
			logger.info("[Leaving fetchSubSurveyType() of ExtendedSurveyBOImpl] : " + e.getMessage());
			throw new BusinessServiceException(ResultsCode.SAVE_SURVEY_INVALID_KEY.getMessage());
		}
		return subSurveyType;
	}

	public String mapSurveyAnswers(ExtendedSaveSurveyVO saveSurveyVO) throws BusinessServiceException {
		try {
			logger.info("[Entering mapSurveyAnswers of ExtendedSurveyBOImpl]"); 

			List<Integer> questionIDList;
			if (null != saveSurveyVO.getCsat().getOptions()) {
				questionIDList = saveSurveyVO.getCsat().getOptions().getOptionID();
				if (null != questionIDList) {
					for (Integer questionId : questionIDList) {
						if (saveSurveyVO.getCsat().getRating() > 3) {
							saveSurveyVO.setQuestionId(questionId);
							saveSurveyVO.setAnswer(PublicAPIConstant.ANSWER_BIT_ONE);
						} else {
							saveSurveyVO.setQuestionId(questionId);
							saveSurveyVO.setAnswer(PublicAPIConstant.ANSWER_BIT_ZERO);
						}
						extendedSurveyDAO.insertSurveyAnswers(saveSurveyVO);
					}
				}
			}
		} catch (DataServiceException e) {
			logger.info("[Leaving mapSurveyAnswers() of ExtendedSurveyBOImpl] : " + e.getMessage());
			throw new BusinessServiceException(ResultsCode.SAVE_SURVEY_INVALID_KEY.getMessage());
		}
		return "success";
	}

	public Long responseHdrIdGenerator() throws BusinessServiceException {
		Long responseHdrId = null;
		try {
			logger.info("[Entering responseHdrIdGenerator of ExtendedSurveyBOImpl]");
			RandomGUID randomGUID = new RandomGUID();
			responseHdrId = randomGUID.generateGUID();
		} catch (Exception e) {
			logger.info("[Leaving responseHdrIdGenerator() of ExtendedSurveyBOImpl] : " + e.getMessage());
			throw new BusinessServiceException(ResultsCode.SAVE_SURVEY_INVALID_KEY.getMessage());
		}
		return responseHdrId;
	}
	
	@Override
	public String insertResponseSOMetadata(ExtendedSaveSurveyVO saveSurveyVO) throws BusinessServiceException {
		String surveyType = null;
		HashMap<String, String> hmap = new HashMap<String, String>();
		String surveyOptionID= null;
		try {
			logger.info("[Entering insertResponseSOMetadata of ExtendedSurveyBOImpl]"); 
			surveyType=saveSurveyVO.getSurveyType();
			surveyOptionID=extendedSurveyDAO.getSurveyOptionID(surveyType);
			if(surveyOptionID == null){
				logger.info("[Error][Inside insertResponseSOMetadata of ExtendedSurveyBOImpl] : Invalid Survey type");
				throw new BusinessServiceException(ResultsCode.SAVE_SURVEY_INVALID_KEY.getMessage());
			}
			
			hmap.put(PublicAPIConstant.SURVEY_OPTION_ID, surveyOptionID);
			if (saveSurveyVO.isAgreed()) {
				hmap.put(PublicAPIConstant.AGREED, PublicAPIConstant.TRUE);
			} else {
				hmap.put(PublicAPIConstant.AGREED, PublicAPIConstant.FALSE);
			}
			
			if (saveSurveyVO.isSubmit()) {
				hmap.put(PublicAPIConstant.SUBMITTED, PublicAPIConstant.TRUE);
			} else {
				hmap.put(PublicAPIConstant.SUBMITTED, PublicAPIConstant.FALSE);
			}
			
			for (String key : hmap.keySet()) {
				saveSurveyVO.setKey(key);
				saveSurveyVO.setValue(hmap.get(key));
				extendedSurveyDAO.insertResponseSOMetadata(saveSurveyVO);
			}
		} catch (DataServiceException e) {
			logger.info("[Leaving insertResponseSOMetadata() of ExtendedSurveyBOImpl] : " + e.getMessage());
			throw new BusinessServiceException(ResultsCode.SAVE_SURVEY_INVALID_KEY.getMessage());
		}
		return "success";

	}
	
	@Override
	public String updateResponseSOMetadata(ExtendedSaveSurveyVO saveSurveyVO) throws BusinessServiceException {
		HashMap<String, String> hmap = new HashMap<String, String>();
		try {
			logger.info("[Entering updateResponseSOMetadata of ExtendedSurveyBOImpl]"); 
			if (saveSurveyVO.isAgreed()) {
				hmap.put(PublicAPIConstant.AGREED, PublicAPIConstant.TRUE);
			} else {
				hmap.put(PublicAPIConstant.AGREED, PublicAPIConstant.FALSE);
			}
			
			if (saveSurveyVO.isSubmit()) {
				hmap.put(PublicAPIConstant.SUBMITTED, PublicAPIConstant.TRUE);
			} else {
				hmap.put(PublicAPIConstant.SUBMITTED, PublicAPIConstant.FALSE);
			}
			
			for (String key : hmap.keySet()) {
				saveSurveyVO.setKey(key);
				saveSurveyVO.setValue(hmap.get(key));
				extendedSurveyDAO.updateResponseSOMetadata(saveSurveyVO);
			}
		} catch (DataServiceException e) {
			logger.info("[Leaving updateResponseSOMetadata() of ExtendedSurveyBOImpl] : " + e.getMessage());
			throw new BusinessServiceException(ResultsCode.SAVE_SURVEY_INVALID_KEY.getMessage());
		}
		return "success";

	}
	
	
	
	@Override
	public String getServiceOrderID(String key) throws BusinessServiceException {
		String soId= getSoIdFromEncryptedKey(key);
		if(soId == null){
			logger.info("[Error][Inside getSurveyType of ExtendedSurveyBOImpl] : soId is null");
			throw new BusinessServiceException(ResultsCode.SAVE_SURVEY_INVALID_KEY.getMessage());
		}
		return soId;
	}
	
	@Override
	public String getSurveyType(String key) throws BusinessServiceException {
		String surveyType= getSurveyTypeFromEncryptedKey(key);
		if(surveyType == null){
			logger.info("[Error][Inside getSurveyType of ExtendedSurveyBOImpl]  : surveyType is null");
			throw new BusinessServiceException(ResultsCode.SAVE_SURVEY_INVALID_KEY.getMessage());
		}
		return surveyType;
	}
	
	@Override
	public List<Integer> getSurveyIds(String key) throws BusinessServiceException {
		List<String> surveyIdListOfString= getSurveyIdFromEncryptedKey(key);
		if(surveyIdListOfString == null){
			logger.info("[Error][Inside getSurveyIds of ExtendedSurveyBOImpl] : surveyId is null");
			throw new BusinessServiceException(ResultsCode.SAVE_SURVEY_INVALID_KEY.getMessage());
		}
		List<Integer> surveyIdListOfInteger = surveyIdListOfString.stream().map(stringValue -> Integer.parseInt(stringValue))
				.collect(Collectors.toList());
		return surveyIdListOfInteger;
	}
	
	public SurveyQuestionsAnswersResponse getSurveyQuestionsWithAnswers(String soId) throws BusinessServiceException {
		SurveyQuestionsAnswersResponse surveyQuestionsAnswersResponse =null;
		try {
			List<SurveyQuestionAnswerDetailVO> surveyQuestionAnswerDetailVOList=extendedSurveyDAO.getSurveyQuestionsWithAnswers(soId);
			if(surveyQuestionAnswerDetailVOList!=null){
				surveyQuestionsAnswersResponse =new SurveyQuestionsAnswersResponse();
				mapVoToSurveyResponse(surveyQuestionAnswerDetailVOList, surveyQuestionsAnswersResponse);
			}
		} catch (DataServiceException e) {
			logger.info("exception while fetching survey questions and answers"+e.getMessage());
			throw new BusinessServiceException(e);
		}
		return surveyQuestionsAnswersResponse;
	}
	
	private void mapVoToSurveyResponse(List<SurveyQuestionAnswerDetailVO> surveyQuestionAnswerDetailVOList, SurveyQuestionsAnswersResponse response) throws BusinessServiceException{
        logger.debug(" start of mapVoToSurveyResponse");
       
        if(surveyQuestionAnswerDetailVOList != null && !surveyQuestionAnswerDetailVOList.isEmpty()) {
        	CSATSurveyQuestionAnswers csat = null;
            NPSSurveyQuestionAnswers nps = null;
	        ArrayList<CsatOptionsSelected> list = new ArrayList<CsatOptionsSelected>();
	        SurveyQuestionAnswerDetailVO surveyQuestionAnswerDetailVO=surveyQuestionAnswerDetailVOList.get(0);
	        Integer buyerId=surveyQuestionAnswerDetailVO.getBuyerId();
	        if(buyerId==null){
	        	logger.info("buyerId is null");
				throw new BusinessServiceException("Buyer not configured survey or not provide the survey details yet");
	        }
	        String buyerName=null;
	        String surveyType = surveyQuestionAnswerDetailVO.getSurveyType();
	        
	        //get buyer name (SLT-1769)
        	Set<Integer> buyerIds = new HashSet<Integer>();
            buyerIds.add(buyerId);	        	
        	List<EmailDataVO> emailDataVOList = iSystemGeneratedBO.getEmailDataForBuyer(buyerIds);
            for(EmailDataVO emailDataVO: emailDataVOList){
            	if(EmailPropertyKeyEnum.BUYERNAME.getKey().equalsIgnoreCase(emailDataVO.getParamKey())){
            		buyerName = emailDataVO.getParamValue();
            	}
            }
	       
	        for (SurveyQuestionAnswerDetailVO listItem : surveyQuestionAnswerDetailVOList) {
	        	CsatOptionsSelected option = new CsatOptionsSelected();
	            if (SURVEYTYPECSAT.equalsIgnoreCase(listItem.getSubSurveyType())){
	            	option.setId(listItem.getQuestionId().toString());
	            	option.setText(listItem.getText());
	            	if(listItem.getSelected() == 1)
	            		option.setSelected(true);
	            	else
	            		option.setSelected(false);
	            	list.add(option);
	            	if(csat == null){
	            		csat = new CSATSurveyQuestionAnswers();
	            		csat.setRatingSelected(listItem.getRatingsSelected());
	    	        	csat.setComments(listItem.getComments());
	    	        	CsatQuestion csatQuestion = new CsatQuestion();
	    	        	csatQuestion.setGeneral(listItem.getInstruction());  
	    	        	csat.setQuestions(csatQuestion);
	            	}
	            } else if(SURVEYTYPENPS.equalsIgnoreCase(listItem.getSubSurveyType())){
	            	nps = new NPSSurveyQuestionAnswers();
	            	nps.setRatingSelected(listItem.getRatingsSelected());
	            	nps.setComments(listItem.getComments());
	                nps.setQuestion(listItem.getText().replace("[]", buyerName));   
	                response.setNps(nps);
	            }
	        }
	        
	        response.setSurveyType(surveyType);
	        
	        if (!list.isEmpty()){	
	        	String buyerDateFound = null;
	        	try{
	        		buyerDateFound = PropertiesUtils.getPropertyValue(buyerId+BELOWSTR);
	        	}catch(Exception e){
	        		logger.warn(" Key not found in  application properties"+ (buyerId+BELOWSTR));
	        	}
	        	if(buyerDateFound==null){
	        		if(Integer.parseInt(csat.getRatingSelected())<=3)
	        			csat.getQuestions().setBelowAverage(PropertiesUtils.getPropertyValue("default"+BELOWSTR));
	        		else
	        			csat.getQuestions().setAboveAverage(PropertiesUtils.getPropertyValue("default"+ABOVESTR));
	        	}else{
	        		if(Integer.parseInt(csat.getRatingSelected())<=3)
	        			csat.getQuestions().setBelowAverage(PropertiesUtils.getPropertyValue(buyerId+BELOWSTR));
	        		else
	        			csat.getQuestions().setAboveAverage(PropertiesUtils.getPropertyValue(buyerId+ABOVESTR));
	        	}	        	
	        	csat.setOptions(list);
	        	response.setCsat(csat);
	        }
	        
	        // To display main rating (Prioritized one)
	        if(csat != null || nps != null) {
		        if (SURVEYTYPECSAT.equalsIgnoreCase(surveyType) || SURVEYTYPECSAT_NPS.equalsIgnoreCase(surveyType)) {
		        	response.setRating(Integer.valueOf(response.getCsat().getRatingSelected()));
		        } else {
		        	response.setRating(Integer.valueOf(response.getNps().getRatingSelected()));
		        }
	        }
        }
    }
	
	public String decryptSoId(String encryptedSoId) {
		CryptographyVO cryptographyVO = new CryptographyVO();
		cryptographyVO.setInput(encryptedSoId);
		cryptographyVO.setKAlias((MPConstants.ENCRYPTION_KEY_FOR_SOID));
		CryptographyVO decryptedSoId = surveyCryptographyUtil.decryptKey(cryptographyVO);
		return decryptedSoId.getResponse();

	}
	
	public Integer getBuyerId(String soId) throws BusinessServiceException{
		Integer buyerId;
		try{
			buyerId=extendedSurveyDAO.getBuyerId(soId);
			if(buyerId == null)
				throw new BusinessServiceException(ResultsCode.SAVE_SURVEY_INVALID_KEY.getMessage());
		}
		catch(DataServiceException ex){
			logger.info("[Error]: Invalid soId");
			throw new BusinessServiceException(ResultsCode.SAVE_SURVEY_INVALID_KEY.getMessage());
		}
		return buyerId;
	}
	
	public IAnalyticalSurveyRatingBO getAnalyticalSurveyRating() {
		return analyticalSurveyRating;
	}

	public void setAnalyticalSurveyRating(
			IAnalyticalSurveyRatingBO analyticalSurveyRating) {
		this.analyticalSurveyRating = analyticalSurveyRating;
	}

    public ExtendedSurveyDAO getExtendedSurveyDAO() {
        return extendedSurveyDAO;
    }

    public void setExtendedSurveyDAO(ExtendedSurveyDAO extendedSurveyDAO) {
        this.extendedSurveyDAO = extendedSurveyDAO;
    }
    
    public ISystemGeneratedBO getiSystemGeneratedBO() {
		return iSystemGeneratedBO;
	}

	public void setiSystemGeneratedBO(ISystemGeneratedBO iSystemGeneratedBO) {
		this.iSystemGeneratedBO = iSystemGeneratedBO;
	}

	public SurveyCryptographyUtil getSurveyCryptographyUtil() {
		return surveyCryptographyUtil;
	}

	public void setSurveyCryptographyUtil(SurveyCryptographyUtil surveyCryptographyUtil) {
		this.surveyCryptographyUtil = surveyCryptographyUtil;
	}

	public IBuyerFeatureSetDAO getBuyerFeatureSetDAO() {
		return buyerFeatureSetDAO;
	}

	public void setBuyerFeatureSetDAO(IBuyerFeatureSetDAO buyerFeatureSetDAO) {
		this.buyerFeatureSetDAO = buyerFeatureSetDAO;
	}
	@Override
	public Double getSurveyEntityRatingDetails(Long responseHdrId) throws BusinessServiceException{
		try {
			return extendedSurveyDAO.fetchEntityRating(responseHdrId);			
		} catch (DataServiceException e) {
			logger.info("exception while fetching survey entity rating"+e.getMessage());
			throw new BusinessServiceException(e);
		}
	}

	@Override
	public SurveyRatingsVO getResourceRating(ExtendedSaveSurveyVO saveSurveyVO,String subSurveyType,Integer entityId) throws BusinessServiceException {
		try {
			return extendedSurveyDAO.getOverallSurveyRatings(entityId,
					SurveyConstants.ENTITY_PROVIDER_ID,SurveyConstants.LIFETIMESCORE,subSurveyType);
		} catch (DataServiceException e) {
			logger.info("exception while fetching survey aggregate rating"+e.getMessage());
			throw new BusinessServiceException(e);
		}
	}
	@Override
	public void updateEntityRatings(double avgRating,ExtendedSaveSurveyVO saveSurveyVO, String subSurveyType,Integer entityId) throws BusinessServiceException, DataServiceException {
		extendedSurveyDAO.updateOverallSurveyRatings(avgRating,saveSurveyVO,subSurveyType,entityId);
	}

	@Override
	public SurveyRatingsVO getSurveyBuyerRatingDetails(ExtendedSaveSurveyVO saveSurveyVO,String subSurveyType,Integer entityId) throws BusinessServiceException, DataServiceException{
		return extendedSurveyDAO.getAvgSurveyBuyerRatings(entityId,saveSurveyVO.getBuyerId(),subSurveyType);
		
	}

	@Override
	public void updateBuyerRatings(double avgBuyerRating,ExtendedSaveSurveyVO saveSurveyVO, String subSurveyType,Integer entityId) throws DataServiceException {
		extendedSurveyDAO.updateAvgBuyerRating(avgBuyerRating,saveSurveyVO,subSurveyType,entityId);		
	}

	@Override
	public Integer fetchVendorId(ExtendedSaveSurveyVO saveSurveyVO) throws DataServiceException {		
		return extendedSurveyDAO.getVendorId(saveSurveyVO.getServiceOrderID());
	}
}
