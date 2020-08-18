package com.newco.marketplace.web.action.details;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.survey.SurveyAnswerVO;
import com.newco.marketplace.dto.vo.survey.SurveyQuestionVO;
import com.newco.marketplace.dto.vo.survey.SurveyResponseVO;
import com.newco.marketplace.dto.vo.survey.SurveyVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.SurveyConstants;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.opensymphony.xwork2.Preparable;

/**
 * 
 * $Revision: 1.5 $ $Author: glacy $ $Date: 2008/04/26 01:13:47 $
 */

/*
 * Maintenance History
 * $Log: ServiceOrderRateAction.java,v $
 * Revision 1.5  2008/04/26 01:13:47  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.2.28.1  2008/04/01 22:03:59  mhaye05
 * merged changed from Head into I18_Fin branch
 *
 * Revision 1.3  2008/03/27 18:57:50  mhaye05
 * Merged I18_ADM to Head
 *
 * Revision 1.2.32.1  2008/03/25 18:29:54  mhaye05
 * code cleanup
 *
 * Revision 1.2  2007/12/13 23:53:22  mhaye05
 * replaced hard coded strings with constants
 *
 */
public class ServiceOrderRateAction extends SLBaseAction implements Preparable{
	
	private static final Logger logger = Logger.getLogger(ServiceOrderRateAction.class.getName());
	private static final long serialVersionUID = 1L;
	private String serviceOrderID;
	private int surveyId;
	private int statusId; //SurveyStatusVO
	private int surveyTypeId; //SurveyTypeVO
	private String surveyType;
	private int entityTypeId; //EntityTypeVO
	private int userId;
	private boolean responseExists;
	private boolean missingReqAnswers;
	private boolean closed;
	private String buyerId;
	private String resourceId;
	
	private ISODetailsDelegate serviceOrderDetailDelegate;
	
	
	
	public ServiceOrderRateAction(ISODetailsDelegate delegate)
	{
		this.serviceOrderDetailDelegate = delegate;
	}
	
	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
	}
	public String retrieveQuestions(){
		
		SurveyVO surveyVO = new SurveyVO();
		int roleId = get_commonCriteria().getSecurityContext().getRoleId();
		String serviceOrderId = getServiceOrderID();
		if(roleId == OrderConstants.BUYER_ROLEID){
			surveyVO.setSurveyType(SurveyConstants.SURVEY_TYPE_PROVIDER);
			surveyVO.setEntityType(SurveyConstants.ENTITY_BUYER);
		}else if(roleId == OrderConstants.PROVIDER_ROLEID){
			surveyVO.setSurveyType(SurveyConstants.SURVEY_TYPE_BUYER);
			surveyVO.setEntityType(SurveyConstants.ENTITY_PROVIDER);
		}
		surveyVO.setServiceOrderID(serviceOrderId);
		try {
			surveyVO = serviceOrderDetailDelegate.retrieveQuestions(surveyVO);
			getRequest().setAttribute("surveyvo", surveyVO);
			if(surveyVO.isClosed() == false){
				logger.info("Service Order is not closed...");
			}
			else if(surveyVO.isResponseExists()){
				logger.info("Response Exists for Service Order...");
			}			
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		
		return "success";		
		
	}
	
	public String saveResponse(){
		
		SurveyVO surveyVO = new SurveyVO();
		
		int roleId = get_commonCriteria().getSecurityContext().getRoleId();
		String serviceOrderId = getServiceOrderID();
		if(roleId == (OrderConstants.BUYER_ROLEID)){
			surveyVO.setSurveyType(SurveyConstants.SURVEY_TYPE_PROVIDER);
			surveyVO.setEntityType(SurveyConstants.ENTITY_BUYER);
		}else if(roleId == (OrderConstants.PROVIDER_ROLEID)){
			surveyVO.setSurveyType(SurveyConstants.SURVEY_TYPE_BUYER);
			surveyVO.setEntityType(SurveyConstants.ENTITY_PROVIDER);
		}
		surveyVO.setServiceOrderID(serviceOrderId);

		surveyVO.setUserId(4);
		try {
			//TODO: Replace it from session 
			//Fetch the questions 
			surveyVO = serviceOrderDetailDelegate.retrieveQuestions(surveyVO);
			
			String[] userAnswers = {"1","11","31","41","51"};
		
			ArrayList questions = surveyVO.getQuestions();
			ArrayList responses = new ArrayList();
			int iQuesSize = questions.size();
			SurveyQuestionVO surveyQuestionVO = null;
			int iAnswerId = 0;
			SurveyResponseVO surveyResponseVO = null;
			ArrayList<SurveyAnswerVO> answers = null;
			SurveyAnswerVO surveyAnswerVO = null;
			
			for(int i=0; i<iQuesSize; i++){
				surveyQuestionVO = (SurveyQuestionVO)questions.get(i);
				//setting user answers
				iAnswerId = Integer.valueOf(userAnswers[i]);
				
				responses = new ArrayList();
				surveyResponseVO = new SurveyResponseVO();
				
				surveyResponseVO.setAnswerId(iAnswerId);
				surveyResponseVO.setSurveyId(surveyVO.getSurveyId());
				surveyResponseVO.setEntityTypeId(surveyVO.getEntityTypeId());
				surveyResponseVO.setEntityId(surveyVO.getUserId());
				surveyResponseVO.setQuestionId(surveyQuestionVO.getQuestionId());
				
				System.out.println("ServiceOrderRateAction-->QuestionId="+surveyResponseVO.getQuestionId()+"-->AnswerId="+iAnswerId);
				
				responses.add(surveyResponseVO);
				surveyQuestionVO.setResponses(responses);
			}
	
		
		
			return serviceOrderDetailDelegate.saveResponse(surveyVO);
		} 
		catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return "failure";
	}
	
	
	public String execute() throws Exception
	{
		return "success";
		
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

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public int getSurveyTypeId() {
		return surveyTypeId;
	}

	public void setSurveyTypeId(int surveyTypeId) {
		this.surveyTypeId = surveyTypeId;
	}

	public String getSurveyType() {
		return surveyType;
	}

	public void setSurveyType(String surveyType) {
		this.surveyType = surveyType;
	}

	public int getEntityTypeId() {
		return entityTypeId;
	}

	public void setEntityTypeId(int entityTypeId) {
		this.entityTypeId = entityTypeId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean isResponseExists() {
		return responseExists;
	}

	public void setResponseExists(boolean responseExists) {
		this.responseExists = responseExists;
	}

	public boolean isMissingReqAnswers() {
		return missingReqAnswers;
	}

	public void setMissingReqAnswers(boolean missingReqAnswers) {
		this.missingReqAnswers = missingReqAnswers;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public ISODetailsDelegate getServiceOrderDetailDelegate() {
		return serviceOrderDetailDelegate;
	}

	public void setServiceOrderDetailDelegate(
			ISODetailsDelegate serviceOrderDetailDelegate) {
		this.serviceOrderDetailDelegate = serviceOrderDetailDelegate;
		
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	}

