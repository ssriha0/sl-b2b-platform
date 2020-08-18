/**
 * 
 */
package com.newco.marketplace.business.businessImpl.survey;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.survey.SurveyCSATVO;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.survey.ISurveyBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.survey.SurveyAnswerVO;
import com.newco.marketplace.dto.vo.survey.SurveyQuesAnsVO;
import com.newco.marketplace.dto.vo.survey.SurveyQuestionVO;
import com.newco.marketplace.dto.vo.survey.SurveyRatingSummaryVO;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.dto.vo.survey.SurveyResponseVO;
import com.newco.marketplace.dto.vo.survey.SurveyVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.SurveyConstants;
import com.newco.marketplace.persistence.iDao.survey.ExtendedSurveyDAO;
import com.newco.marketplace.persistence.iDao.survey.SurveyDAO;
import com.newco.marketplace.utils.RandomGUID;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.business.ABaseBO;
import com.sears.os.service.ServiceConstants;

/**
 * @author schavda
 * 
 */
public class SurveyBOImpl extends ABaseBO implements ISurveyBO,
		ServiceConstants, OrderConstants, SurveyConstants {

	private SurveyDAO surveyDAO;
	private ExtendedSurveyDAO extendedSurveyDAO;
	private static final Logger logger = Logger.getLogger(SurveyBOImpl.class
			.getName());
	

	//21513
	public boolean saveCSATData(String serviceOrderId) throws DataServiceException{
		logger.debug(" start of saveCSATData of SurveyBOImpl");
		boolean soPresent=false;
		try {
			soPresent = surveyDAO.saveCSATData(serviceOrderId);
		}
		catch(DataServiceException e) {
			e.printStackTrace();
		}
		logger.debug(" end of saveCSATData of SurveyBOImpl");
		return soPresent;
	}
	
	public ServiceOrder getInHomeBuyers(String serviceOrderId) throws DataServiceException{
		ServiceOrder order=null;
		logger.debug(" start of getInHomeBuyers of SurveyBOImpl");
		try {
			order=surveyDAO.getInHomeBuyersDao(serviceOrderId);
		}
		catch(DataServiceException e) {
			e.printStackTrace();
		}
		logger.debug(" end of getInHomeBuyers of SurveyBOImpl");
		return order;
	}
	
	public int getAnswer(int questionId, Integer scoreValue) throws DataServiceException{
		int ansId = 0;
		logger.debug(" start of getAnswer of SurveyBOImpl");
		try {
			ansId = surveyDAO.getAnswer(questionId,scoreValue);
		}
		catch(DataServiceException e) {
			e.printStackTrace();
		}
		logger.debug(" end of getAnswer of SurveyBOImpl");
		return ansId;
	}
	
	/**
	 * Retrieve All the Questions based on SurveyId
	 */
	public SurveyVO retrieveQuestionsBySurveyId(int iSurveyId)
			throws DataServiceException {
		logger.debug("SurveyBOImpl-->SurveyId=" + iSurveyId);
		SurveyVO surveyVO = null;
		ProcessResponse processResp = new ProcessResponse();
		try {
			surveyVO = surveyDAO.retrieveQuestionsBySurveyId(iSurveyId);
		} catch (Throwable t) {
			logger.error(
					"SurveyBOImpl-->retrieveQuestionsBySurveyId-->Exception-->"
							+ t.getMessage(), t);
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage(t.getMessage());
			processResp.setObj(FAILED_SERVICE_ORDER_NO);
		}

		return surveyVO;
	}

	/**
	 * Retrieve All the Questions based on SurveyTypeId and EntityTypeId
	 * SurveyType like Provider Rating, Buyer Rating EntityType like Buyer,
	 * Provider, MarketPlace
	 * 
	 */
	public SurveyVO retrieveQuestions(SurveyVO pSurveyVO, ServiceOrder serviceOrderFromBatch)
			throws DataServiceException {
		SurveyVO surveyVO = pSurveyVO;
		logger.debug("SurveyBOImpl-->SurveyTypeId="
				+ surveyVO.getSurveyTypeId() + "-->EntityTypeId="
				+ surveyVO.getEntityTypeId());
		ProcessResponse processResp = new ProcessResponse();
		try {

			if (surveyVO.getEntityType() == ENTITY_BUYER) {
				surveyVO.setEntityTypeId(ENTITY_BUYER_ID);
			} else if (surveyVO.getEntityType() == ENTITY_PROVIDER) {
				surveyVO.setEntityTypeId(ENTITY_PROVIDER_ID);
			} else if (surveyVO.getEntityType() == ENTITY_MARKETPLACE) {
				surveyVO.setEntityTypeId(ENTITY_MARKETPLACE_ID);
			}

			if (surveyVO.getSurveyType() == SURVEY_TYPE_BUYER) {
				surveyVO.setSurveyTypeId(SURVEY_TYPE_BUYER_ID);
			} else if (surveyVO.getSurveyType() == SURVEY_TYPE_PROVIDER) {
				surveyVO.setSurveyTypeId(SURVEY_TYPE_PROVIDER_ID);
			}

			// Get the service order object
			ServiceOrder serviceOrder=null;
			if(null != serviceOrderFromBatch){
				serviceOrder = serviceOrderFromBatch;
			}else{
				serviceOrder = getSurveyDAO().getServiceOrder(
						surveyVO.getServiceOrderID());
			}
			if (serviceOrder == null) {
				logger.info("Service Order Not Found...");
				return surveyVO;
				// return setErrorMsg(processResp, SERVICE_ORDER_OBJ_NOT_FOUND);
			}

			// Check if the User is authorized to rate/survey the service order
			if (surveyVO.getEntityType() == ENTITY_BUYER) {
				if (!isAuthorizedBuyer(serviceOrder.getBuyer().getBuyerId(), serviceOrder)) {
					logger.info("Buyer is not authorized...");
					return surveyVO;
					// return setErrorMsg(processResp, BUYER_IS_NOT_AUTHORIZED);
				}
			} else if (surveyVO.getEntityType() == ENTITY_PROVIDER) {
				if (!isAuthorizedProvider(serviceOrder.getAcceptedResource().getResourceId(),
						serviceOrder)) {
					logger.info("Provider is not authorized...");
					return surveyVO;
					// return setErrorMsg(processResp,
					// PROVIDER_IS_NOT_AUTHORIZED);
				}
			}

			// Check if the Order is closed
			int orderStatus = serviceOrder.getWfStateId().intValue();
			if (orderStatus < 0) {
				logger.info("Service Order State not Found...");
				return surveyVO;
				// return setErrorMsg(processResp,
				// SERVICE_ORDER_WFSTATE_NOT_FOUND);
			}
			boolean bClose = checkCloseStatus(orderStatus);
			logger.debug("SurveyBOImpl-->bClose=" + bClose);
			if (!bClose) {
				logger.info("Service Order is not Closed...");
				surveyVO.setClosed(false);
				return surveyVO;
				// return setErrorMsg(processResp, SERVICE_ORDER_NOT_CLOSED);
			}
			surveyVO.setClosed(true);

			// Check rating already exist for the service order
			boolean bResponseExists = getSurveyDAO().responseExists(surveyVO);
			surveyVO.setResponseExists(bResponseExists);
			logger.debug("SurveyBOImpl-->ResponseExists=" + bResponseExists);
			if (bResponseExists) {
				return surveyVO;
			}

			surveyVO = surveyDAO.retrieveQuestions(surveyVO);
		} catch (Throwable t) {
			t.printStackTrace();
			logger.error("SurveyBOImpl-->retrieveQuestions-->Exception-->"
					+ t.getMessage(), t);
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage(t.getMessage());
			processResp.setObj(FAILED_SERVICE_ORDER_NO);
		}
		return surveyVO;
	}

	public String saveResponse(SurveyVO surveyVO,SecurityContext securityContext, ServiceOrder serviceOrderFromBatch) throws DataServiceException {
		logger.debug("SurveyBOImpl-->SurveyId=" + surveyVO.getSurveyId());
		SurveyResponseVO surveyResponseVOHdr = new SurveyResponseVO();
		SurveyResponseVO surveyResponseVO = null;
		ProcessResponse processResp = new ProcessResponse();
		Long responseHdrId = new Long(0);

		java.util.Date today = new java.util.Date();
		java.sql.Date now = new java.sql.Date(today.getTime());

		try {
			// Validate that at least one Reponse for each question
			if (!validateQuestions(surveyVO)) {
				logger
						.info("SurveyBOImpl-->validateQuestions--REQUIRED QUESTION'S ANSWER MISSING");
				return null;
			}
			// Response Header & SO
			RandomGUID randomGUID = new RandomGUID();
			responseHdrId = randomGUID.generateGUID();
			surveyResponseVOHdr.setServiceOrderID(surveyVO.getServiceOrderID());
			surveyResponseVOHdr.setResponseHdrId(responseHdrId);
			surveyResponseVOHdr.setSurveyId(surveyVO.getSurveyId());
			surveyResponseVOHdr.setEntityTypeId(surveyVO.getEntityTypeId());
			surveyResponseVOHdr.setEntityId(surveyVO.getUserId());
			surveyResponseVOHdr.setOverallScore(0.0);
			surveyResponseVOHdr.setCreatedDate(now);
			surveyResponseVOHdr.setModifiedDate(now);
			surveyResponseVOHdr.setModifiedBy(String.valueOf(surveyVO
					.getUserId()));

			// Calculate Overall Score
			surveyResponseVOHdr.setOverallScore(overallScore(surveyVO));
			surveyResponseVOHdr.setComment(surveyVO.getSurveyComments());
			surveyVO.setOverallScore(surveyResponseVOHdr.getOverallScore());
			
			surveyDAO.saveResponseHeader(surveyResponseVOHdr);

			Map<Integer, Integer> answers = new HashMap<Integer, Integer>();
			
			// Question's answers
			if (surveyVO.getQuestions() != null
					&& surveyVO.getQuestions().size() > 0) {
				ArrayList<SurveyQuestionVO> questions = surveyVO.getQuestions();
				SurveyQuestionVO surveyQuestionVO = null;
				int iQuesSize = questions.size();
				for (int i = 0; i < iQuesSize; i++) {
					surveyQuestionVO = questions.get(i);
					if (surveyQuestionVO.getResponses() != null
							&& surveyQuestionVO.getResponses().size() > 0) {
						ArrayList<SurveyResponseVO> responses = surveyQuestionVO
								.getResponses();
						int iResSize = responses.size();
						for (int j = 0; j < iResSize; j++) {
							surveyResponseVO = responses.get(j);
							surveyResponseVO.setResponseHdrId(responseHdrId);
							surveyResponseVO.setResponseId(randomGUID
									.generateGUID());
							surveyResponseVO.setCreatedDate(now);
							surveyResponseVO.setModifiedDate(now);
							surveyResponseVO.setModifiedBy(String
									.valueOf(surveyVO.getUserId()));
							surveyDAO.saveResponseDetails(surveyResponseVO);
							answers.put(Integer.valueOf(surveyQuestionVO.getQuestionId()), Integer.valueOf(surveyResponseVO.getAnswerId()));
						}
					}
				}
			}
			ServiceOrder serviceOrder=null;
			if(null != serviceOrderFromBatch){
				serviceOrder = serviceOrderFromBatch;
			}else{
				serviceOrder  = getSurveyDAO().getServiceOrder(surveyVO.getServiceOrderID());
			}
			if(surveyVO.getEntityTypeId() == SurveyConstants.ENTITY_BUYER_ID)
			{
				getSurveyDAO().updateVendorResourceRatedCountAndRating(serviceOrder.getBuyer().getBuyerId().intValue(),serviceOrder.getAcceptedResourceId().intValue()
						,surveyResponseVOHdr.getOverallScore());
				 
				if (!answers.isEmpty()) {
					Set<Integer> questions = answers.keySet();
					for (Integer question : questions) {
						getSurveyDAO().updateVendorResourceSurveyRollup(
								serviceOrder.getAcceptedResourceId(), question, answers.get(question));
					}
				}
			}
			else if(surveyVO.getEntityTypeId() == SurveyConstants.ENTITY_PROVIDER_ID)
			{
				getSurveyDAO().updateBuyerRatedCountAndRating(serviceOrder.getBuyer().getBuyerId().intValue(),serviceOrder.getAcceptedResourceId().intValue()
						,surveyResponseVOHdr.getOverallScore());
			}
		} catch (Throwable t) {
			logger.error("SurveyBOImpl-->saveResponse-->Exception-->"
					+ t.getMessage(), t);
			t.printStackTrace();
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage(t.getMessage());
			processResp.setObj(FAILED_SERVICE_ORDER_NO);
			return null;
		} finally {
			
		}
		
		return "SUCCESS";
	}
	
	public ArrayList<SurveyQuesAnsVO> getSurveyResponse(SurveyVO surveyVO) throws DataServiceException
	{
		try {
			ArrayList<SurveyQuesAnsVO> srvyResponseList = (ArrayList<SurveyQuesAnsVO>) surveyDAO.getSurveyResponse(surveyVO);
			this.populateUIScore(srvyResponseList);
			return srvyResponseList;
		}
		catch(DataServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Method to calculate scoreNumber value of SurveyQuesAnsVO, which is linked with UI for rating to show
	 * appropriate start image based on the score value available in the VO object
	 * @param surveyResponseList List of SurveyQuesAnsVO objects containing survey response provided by user
	 */
	private void populateUIScore(ArrayList<SurveyQuesAnsVO> surveyResponseList) {
		int scoreValue = 0;
		for(SurveyQuesAnsVO surveyQAVO : surveyResponseList) {
			scoreValue = UIUtils.calculateScoreNumber(surveyQAVO.getScore());
			surveyQAVO.setScoreNumber(scoreValue);
		}
		
	}
	
	/**
	 * Method to calculate a int score value in the range {4-20} for a given decimal or int value in range {1-5}.
	 * For ex. if a given score value is 1.69, then an image with 1 full star and a star 3/4 filled need to be shown.
	 * The way it is calculated, a full star is shown whenever score value is between 1 through 5. The remaining decimal
	 * value (0.69 in this case), is checked to find which range it fits in among the below ranges - 
	 * {less than 0.12} = Star image not filled at all
	 * {0.13-0.37} = Star image 1/4 filled
	 * {0.38-0.62} = Star image 1/2 filled
	 * {0.63-0.87} = Star image 3/4 filled
	 * {greater than 0.88} = Star image fully filled
	 * Hence, for the score of 1.69, a star image is shown with a full star and a star half filled.
	 * 
	 * The given score is converted from a decimal number to a whole number. Logic is given below.
	 * ScoreNumber = ( { Math.floor(given score) / 0.25 } + {number value corresponding to the range shown above for the decimal part}
	 * 
	 * To calculate the scoreNumber value for the decimal part use below ranges and corresponding values
	 * {< 0.12} => Add 0
	 * {0.13-0.37} => Add 1
	 * {0.38-0.62} => Add 2
	 * {0.63-0.87} => Add 3
	 * {> 0.88} => Add 4
	 * 
	 * The ranges above are derived by adding and subtracting 0.12 from values {0, 0.25, 0.50, 0.75, 1}.
	 *  
	 * For ex. 1.69 is processed to calculate scoreNumber value as below -
	 * For the integer number value in a given score :- Math.floor(1.69) = 1 => (1/0.25) = 4
	 * For the remaining decimal value in the score  :- Value associated with it's range. The value 0.69 is part of range
	 * {0.63-0.87}, hence add 3.
	 * Hence scoreNumber = 4 + 3 = 7.
	 * 
	 * @param score Decimal score value in range {1-5}
	 * @return scoreNumber Int score value in range {4-20}
	 */
//	public int calculateScoreNumber(double score) {
		//Formula for scoreNumber => {intValue / 0.25} + {number associated with fractionalValue based on which range it belongs to}
/**		
		//Math.floor returns the whole value except the fractional part, hence downcast it to int
		int intValue = (int)(Math.floor(score));
		//Get the fractional value
		double fractionalValue = score - intValue; //Or fractionalValue = score % intValue OR fractionalValue = score % 1
		
		int intValueScore = intValue * 4; //or => intValue / 0.25

		int fractionalValueScore = 0;
		if(fractionalValue <= 0.12) {
			fractionalValueScore = 0;
		} else if(fractionalValue >= 0.13 && fractionalValue <= 0.37) {
			fractionalValueScore = 1;
		} else if(fractionalValue >= 0.38 && fractionalValue <= 0.62) {
			fractionalValueScore = 2;
		} else if(fractionalValue >= 0.63 && fractionalValue <= 0.87) {
			fractionalValueScore = 3;
		} else if(fractionalValue >= 0.87) {
			fractionalValueScore = 4;
		}
		
		int scoreNumber = intValueScore + fractionalValueScore;
**/
/*		int scoreNumber = (int)(score / 0.25);
		double fractionValue = score % 0.25;
		
		if(fractionValue > 0.12)
			scoreNumber++;
		
		return scoreNumber;
	}
*/
	public SurveyRatingsVO getRatings(int pEntityTypeId,
		int companyId, int resourceId) throws DataServiceException {
		int entityTypeId = pEntityTypeId;
		entityTypeId = mapEntityTypeId(entityTypeId);
		SurveyRatingsVO surveyRatingsVO = null;
		logger.debug("SurveyBOImpl-->buyerId=" + companyId);
		SurveyRatingsVO buyerGivenScores=null;
		try {
			if (entityTypeId == SurveyConstants.ENTITY_BUYER_ID) {
				surveyRatingsVO = getExtendedSurveyDAO().getVendorResourceRatings(companyId, entityTypeId, SurveyConstants.LIFETIMESCORE);
				buyerGivenScores= getExtendedSurveyDAO().getBuyerRatings(companyId);
				if(surveyRatingsVO!=null){
					Double currentScore = getExtendedSurveyDAO().getBuyeCurrentRatings(companyId, entityTypeId);
					if(currentScore!=null)
						surveyRatingsVO.setCurrentRating(currentScore);
				}
				if(buyerGivenScores!=null){
					if(surveyRatingsVO==null)
						surveyRatingsVO=new SurveyRatingsVO();
					surveyRatingsVO.setBuyerID(buyerGivenScores.getBuyerID());
					surveyRatingsVO.setMyOrdersComplete(buyerGivenScores.getMyOrdersComplete());
					surveyRatingsVO.setNumberOfRatingsGiven(buyerGivenScores.getNumberOfRatingsGiven());
					surveyRatingsVO.setGivenRating(buyerGivenScores.getGivenRating());
				}
			} else {
				surveyRatingsVO = getVendorResourceRatings(companyId);				
			}
		} catch (Throwable t) {
			logger.error("SurveyBOImpl-->getbuyerRatings-->Exception-->"
					+ t.getMessage(), t);
		}
		
		return surveyRatingsVO;

	}
	
	/**
	 * For SLT-1751
	 * To fetch vendor ratings, companyId will be passed and entitytypeId=40.
	 * 
	 * @param resourceId
	 * @return
	 */
	private SurveyRatingsVO getVendorResourceRatings(int companyId) {
		SurveyRatingsVO surveyRatingsVO = null;
		SurveyRatingsVO surveyRatingsVOCurrent = null;
		logger.debug("SurveyBOImpl-->companyId=" + companyId); 

		try {
			if(companyId != 0){
				//boolean IsPrimaryInd=extendedSurveyDAO.IsPrimaryInd(resourceId);
				//if(IsPrimaryInd){
					surveyRatingsVO = extendedSurveyDAO.getVendorResourceRatings(companyId, SurveyConstants.ENTITY_TYPE_ID_VENDOR, SurveyConstants.LIFETIMESCORE);
					System.out.println("print the Id for firm " + companyId);
					//same query is using for current rating as well
					//changing the type="CURRENT" and setting it to currentRating in surveyRatingsVO
					surveyRatingsVOCurrent = extendedSurveyDAO.getVendorResourceRatings(companyId,SurveyConstants.ENTITY_TYPE_ID_VENDOR,SurveyConstants.CURRENTSCORE);
					if(null !=surveyRatingsVO && null !=surveyRatingsVOCurrent && null != surveyRatingsVOCurrent.getHistoricalRating()){
						surveyRatingsVO.setCurrentRating(surveyRatingsVOCurrent.getHistoricalRating());
					}
				/*}else{
					surveyRatingsVO = extendedSurveyDAO.getVendorResourceRatings(resourceId, ENTITY_TYPE_ID_RESOURCE, SurveyConstants.LIFETIMESCORE);
					System.out.println("print the Id for provider " + resourceId);
					//same query is using for current rating as well
					//changing the type="CURRENT" and setting it to currentRating in surveyRatingsVO
					surveyRatingsVOCurrent = extendedSurveyDAO.getVendorResourceRatings(resourceId,ENTITY_TYPE_ID_RESOURCE,SurveyConstants.CURRENTSCORE);
					if(null !=surveyRatingsVO && null !=surveyRatingsVOCurrent && null != surveyRatingsVOCurrent.getHistoricalRating()){
						surveyRatingsVO.setCurrentRating(surveyRatingsVOCurrent.getHistoricalRating());
					}
				}*/
			}
						
		} catch (Throwable t) {
			logger.error("SurveyBOImpl-->getVendorRatingsNew-->Exception-->"
					+ t.getMessage(), t);
		}
		return surveyRatingsVO;
	
}

	public List<SurveyRatingsVO> getQuickRatings(Integer buyerID,List<Integer> vendorResourceIDs)
	{
		List<SurveyRatingsVO> surveyRatingsVOs = null;
		logger.debug("Start of ---- SurveyBOImpl-->getLookupRatings()");
		
		try {
			surveyRatingsVOs = getSurveyDAO().getFastLookupRating(buyerID, vendorResourceIDs);
		} catch (DataServiceException e) {
			logger.error("SurveyBOImpl-->getLookupRatings-->Exception-->"
					+ e.getMessage(), e);
		}		
		logger.debug("End of ---- SurveyBOImpl-->getLookupRatings() ");
		return surveyRatingsVOs;
	}
	
	private SurveyRatingsVO getVendorRatings(int companyId,
			int resourceId) throws DataServiceException {
		SurveyRatingsVO surveyRatingsVO = null;
		GregorianCalendar gc = new GregorianCalendar();
		gc.add(Calendar.DATE, -60);
		Date date = gc.getTime();
		logger.debug("SurveyBOImpl-->resourceId=" + resourceId);
		List<SurveyRatingSummaryVO> surveySummary = null;
		List<SurveyRatingSummaryVO> surveySummaryCurrent = null;

		try {
			if (resourceId == 0) {
				surveySummary = surveyDAO.getVendorRatings(companyId, 0, null);
				System.out.println("print the Id for provider " + resourceId);
				surveySummaryCurrent = surveyDAO.getVendorRatings(companyId, 10, date);
			} else {
				surveySummary = surveyDAO.getVendorResourceRatings(resourceId, 0, null);
				surveySummaryCurrent = surveyDAO.getVendorResourceRatings(resourceId, 10, date);
			}
			surveyRatingsVO = populateSurveyRatingVO(surveySummary, SurveyConstants.ENTITY_PROVIDER_ID);
			Iterator<SurveyRatingSummaryVO> iter = surveySummaryCurrent.iterator();
			while(iter.hasNext()){
				SurveyRatingSummaryVO surveyRatingSummaryVO = iter.next();
				surveyRatingsVO.setCurrentRating(Double.valueOf(surveyRatingSummaryVO.getOverallScore()));
			}			
		} catch (Throwable t) {
			logger.error("SurveyBOImpl-->getproviderRatings-->Exception-->"
					+ t.getMessage(), t);
		}
		return surveyRatingsVO;

	}

	private int mapEntityTypeId(int entityTypeId){
		if(entityTypeId == 1){
			return SurveyConstants.ENTITY_PROVIDER_ID;
		} else if(entityTypeId == 3){
			return SurveyConstants.ENTITY_BUYER_ID;
		}else{
			return entityTypeId; 
		}
		
	}
	
	private SurveyRatingsVO populateSurveyRatingVO(List<SurveyRatingSummaryVO> surveySummary, int userType){
		SurveyRatingsVO surveyRatingsVO = new SurveyRatingsVO();
		if(surveySummary != null && surveySummary.size() != 0){
			Iterator<SurveyRatingSummaryVO> iter = surveySummary.iterator();
			while(iter.hasNext()){
				SurveyRatingSummaryVO surveyRatingSummaryVO = iter.next();
				double overallScore = surveyRatingSummaryVO.getOverallScore();
				int ratedBy = surveyRatingSummaryVO.getRatedBy();
				if(ratedBy == userType){
					surveyRatingsVO.setGivenRating(Double.valueOf(overallScore));
				}else{
					surveyRatingsVO.setHistoricalRating(Double.valueOf(overallScore));
				}
			}
		}
		return surveyRatingsVO;
	}
	
	/**
	 * @return the surveyDao
	 */
	public SurveyDAO getSurveyDAO() {
		return surveyDAO;
	}

	/**
	 * @param surveyDAO
	 *            the surveyDao to set
	 */
	public void setSurveyDAO(SurveyDAO surveyDAO) {
		this.surveyDAO = surveyDAO;
	}

/*	private ProcessResponse setErrorMsg(ProcessResponse processResponse,
			String msg) {
		if (logger.isDebugEnabled())
			logger.debug(msg);
		processResponse.setCode(USER_ERROR_RC);
		processResponse.setSubCode(USER_ERROR_RC);
		List<String> arr = new ArrayList<String>();
		arr.add(msg);
		processResponse.setMessages(arr);

		return processResponse;
	}*/

	/**
	 * Checks whether Buyer is authorized for SO
	 */
	private boolean isAuthorizedBuyer(Integer buyerID, ServiceOrder serviceOrder) {
		if (serviceOrder.getBuyer().getBuyerId().equals(buyerID)) {
			return true;
		} 
		return false;
	}

	/**
	 * Checks whether Provider/Technician is authorized for SO
	 */
	private boolean isAuthorizedProvider(Integer resourceID,
			ServiceOrder serviceOrder) {
		if (serviceOrder.getAcceptedResource().getResourceId().equals(resourceID)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks the Service Order Status to make sure its Closed
	 */
	private boolean checkCloseStatus(int orderStatus) {
		boolean result = false;

		if (orderStatus == CLOSED_STATUS) {
			result = true;
		}

		return result;
	}

	/**
	 * Validates - Required Questions are answered
	 * 
	 */
	private boolean validateQuestions(SurveyVO surveyVO) {
		boolean bValid = true;
		if (surveyVO.getQuestions() != null
				&& surveyVO.getQuestions().size() > 0) {
			ArrayList<SurveyQuestionVO> questions = surveyVO.getQuestions();
			SurveyQuestionVO surveyQuestionVO = null;
			SurveyResponseVO surveyResponseVO = null;
			int iQuesSize = questions.size();
			int iRespSize = 0;
			ArrayList<SurveyResponseVO> response = null;
			for (int i = 0; i < iQuesSize; i++) {
				if(surveyVO.isBatch() && i == 4)
					continue;
				surveyQuestionVO = questions.get(i);
				if (surveyQuestionVO.getResponses() == null
						|| surveyQuestionVO.getResponses().size() == 0) {
					// If required Question is not answered
					if (surveyQuestionVO.isRequired()) {
						bValid = false;
						break;
					}
				} else {
					response = surveyQuestionVO.getResponses();
					iRespSize = response.size();
					for (int j = 0; j < iRespSize; j++) {
						surveyResponseVO = response.get(j);
						if (surveyResponseVO.getAnswerId() == 0) {
							bValid = false;
							break;
						}
					}
					// break outer loop
					if (!bValid) {
						break;
					}
				}
			}
		}

		return bValid;
	}

	/**
	 * Validates - Required Questions are answered
	 * 
	 */
	private double overallScore(SurveyVO surveyVO) {
		double totalScore = 0.0;
		logger.info("SurveyBOImpl-->overallScore()");
		if (surveyVO.getQuestions() != null
				&& surveyVO.getQuestions().size() > 0) {
			ArrayList<SurveyQuestionVO> questions = surveyVO.getQuestions();
			ArrayList<SurveyResponseVO> response = null;
			ArrayList<SurveyAnswerVO> answers = null;
			SurveyQuestionVO surveyQuestionVO = null;
			SurveyResponseVO surveyResponseVO = null;
			SurveyAnswerVO surveyAnswerVO = null;
			int iQuesSize = questions.size();
			int iRespSize = 0;
			int iAnsSize = 0;
			for (int i = 0; i < iQuesSize; i++) {
				//SL-20908 consider all scores 
				/*if(surveyVO.isBatch() && i == 4)
					continue;*/
				surveyQuestionVO = questions.get(i);
				if (surveyQuestionVO.getResponses() != null
						&& surveyQuestionVO.getResponses().size() > 0) {
					answers = surveyQuestionVO.getAnswers();
					iAnsSize = answers.size();
					response = surveyQuestionVO.getResponses();
					iRespSize = response.size();
					for (int j = 0; j < iRespSize; j++) {
						surveyResponseVO = response.get(j);
						if (surveyResponseVO.getAnswerId() > 0) {
							for (int k = 0; k < iAnsSize; k++) {
								surveyAnswerVO = answers.get(k);
								if (surveyResponseVO.getAnswerId() == surveyAnswerVO
										.getAnswerId()) {
									totalScore = totalScore
											+ surveyAnswerVO.getScore();
									logger
											.debug("SurveyBOImpl-->overallScore()-->Score="
													+ surveyAnswerVO.getScore());
									break;
								}
							}

						}
					}
				}
			}
			totalScore = totalScore /iQuesSize;//SL-20908 consider all scores in batch 

		}
		logger.info("SurveyBOImpl-->overallScore()-->OverallScore="
				+ totalScore);
		return totalScore;
	}

	public ExtendedSurveyDAO getExtendedSurveyDAO() {
		return extendedSurveyDAO;
	}

	public void setExtendedSurveyDAO(ExtendedSurveyDAO extendedSurveyDAO) {
		this.extendedSurveyDAO = extendedSurveyDAO;
	}
	
	

}
