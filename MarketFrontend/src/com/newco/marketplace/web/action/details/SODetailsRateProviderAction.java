package com.newco.marketplace.web.action.details;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.survey.SurveyAnswerVO;
import com.newco.marketplace.dto.vo.survey.SurveyQuestionVO;
import com.newco.marketplace.dto.vo.survey.SurveyResponseVO;
import com.newco.marketplace.dto.vo.survey.SurveyVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.SurveyConstants;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.utils.SODetailsUtils;
import com.opensymphony.xwork2.Preparable;

/**
 * $Revision: 1.24 $ $Author: rgurra0 $ $Date: 2008/05/29 21:35:32 $
 */

/*
 * Maintenance History
 * $Log: SODetailsRateProviderAction.java,v $
 * Revision 1.24  2008/05/29 21:35:32  rgurra0
 * rate providers fix
 *
 * Revision 1.23  2008/05/21 23:33:10  akashya
 * I21 Merged
 *
 * Revision 1.22.6.1  2008/05/15 21:15:56  mjastr1
 * Fixed the typo.
 *
 * Revision 1.22  2008/04/26 01:13:48  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.19.28.1  2008/04/01 22:04:00  mhaye05
 * merged changed from Head into I18_Fin branch
 *
 * Revision 1.20  2008/03/27 18:57:51  mhaye05
 * Merged I18_ADM to Head
 *
 * Revision 1.19.32.1  2008/03/25 20:08:48  mhaye05
 * code cleanup
 *
 * Revision 1.19  2007/12/13 23:53:22  mhaye05
 * replaced hard coded strings with constants
 *
 * Revision 1.18  2007/11/14 21:58:51  mhaye05
 * changed reference to SOW_SO_ID or THE_CURRENT_SERVICE_ORDER_ID to be OrderConstants.SO_ID
 *
 */
public class SODetailsRateProviderAction extends SLDetailsBaseAction implements Preparable {
	
	//This class is the same for RateProvider and RateBuyer
	//The code checks for both roles when executing and executes the appropriate lines for that role
	//In future, this class may be renamed/refactored to SODetailsRatingAction
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1966818998831758227L;
	private static final Logger logger = Logger.getLogger(SODetailsRateProviderAction.class.getName());
	public SODetailsRateProviderAction(ISODetailsDelegate delegate) {
		this.detailsDelegate = delegate;
	}
	
	//SL-19820
	String soId;

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	private String retrieveQuestions(){
		SurveyVO surveyVO = new SurveyVO();
		int roleId = get_commonCriteria().getSecurityContext().getRoleId();
		//SL-19820
		//String serviceOrderId = getSession().getAttribute(OrderConstants.SO_ID).toString();
		String serviceOrderId = getParameter("soId");
		setAttribute(SO_ID, serviceOrderId);
		this.soId = serviceOrderId;
		
		String msg = (String)getSession().getAttribute(Constants.SESSION.SOD_MSG+"_"+this.soId);
		getSession().removeAttribute(Constants.SESSION.SOD_MSG+"_"+this.soId);
		setAttribute(Constants.SESSION.SOD_MSG, msg);
		
		String errorMsg = (String)getSession().getAttribute(DETAILS_ERROR_MSG+"_"+this.soId);
		getSession().removeAttribute(DETAILS_ERROR_MSG+"_"+this.soId);
		setAttribute(DETAILS_ERROR_MSG, errorMsg);
		
		surveyVO.setServiceOrderID(serviceOrderId);
		if(roleId == OrderConstants.BUYER_ROLEID || roleId == OrderConstants.SIMPLE_BUYER_ROLEID){
			surveyVO.setSurveyType(SurveyConstants.SURVEY_TYPE_PROVIDER);
			surveyVO.setEntityType(SurveyConstants.ENTITY_BUYER);
		}else if(roleId == OrderConstants.PROVIDER_ROLEID){
			surveyVO.setSurveyType(SurveyConstants.SURVEY_TYPE_BUYER);
			surveyVO.setEntityType(SurveyConstants.ENTITY_PROVIDER);
		}
		//surveyVO.setServiceOrderID(serviceOrderId);
		try {
			surveyVO = detailsDelegate.retrieveQuestions(surveyVO);
			getRequest().setAttribute("surveyvo", surveyVO);
			if(surveyVO.isClosed() == false){
				System.out.println("Service Order is not closed...");
			} else if(surveyVO.isResponseExists()){
				if(surveyVO.getEntityType().equals(SurveyConstants.ENTITY_BUYER)) {
					//SL-19820
					//getSession().setAttribute(Constants.SESSION.SOD_RSLT_BUY_TO_PROV, detailsDelegate.getSurveyResults(surveyVO));
					getRequest().setAttribute(Constants.SESSION.SOD_RSLT_BUY_TO_PROV, detailsDelegate.getSurveyResults(surveyVO));
					surveyVO = new SurveyVO();
					surveyVO.setServiceOrderID(serviceOrderId);
					surveyVO.setEntityType(SurveyConstants.ENTITY_PROVIDER);
					ArrayList ratingsAL = detailsDelegate.getSurveyResults(surveyVO);
					if(ratingsAL != null)
						//SL-19820
						//getSession().setAttribute(Constants.SESSION.SOD_RSLT_PROV_TO_BUY, ratingsAL);
						getRequest().setAttribute(Constants.SESSION.SOD_RSLT_PROV_TO_BUY, ratingsAL);
				}
				else if(surveyVO.getEntityType().equals(SurveyConstants.ENTITY_PROVIDER)) {
					//SL-19820
					//getSession().setAttribute(Constants.SESSION.SOD_RSLT_PROV_TO_BUY, detailsDelegate.getSurveyResults(surveyVO));
					getRequest().setAttribute(Constants.SESSION.SOD_RSLT_PROV_TO_BUY, detailsDelegate.getSurveyResults(surveyVO));
					surveyVO = new SurveyVO();
					surveyVO.setServiceOrderID(serviceOrderId);
					surveyVO.setEntityType(SurveyConstants.ENTITY_BUYER);
					ArrayList ratingsAL = detailsDelegate.getSurveyResults(surveyVO);
					if(ratingsAL != null)
						//SL-19820
						//getSession().setAttribute(Constants.SESSION.SOD_RSLT_BUY_TO_PROV, ratingsAL);
						getRequest().setAttribute(Constants.SESSION.SOD_RSLT_BUY_TO_PROV, ratingsAL);
				}
				System.out.println("Response Exists for Service Order...");
			}			
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		
		return SUCCESS;				
	}
	
	public String saveResponse(){
		
			
		SurveyVO surveyVO = new SurveyVO();		
		int roleId = get_commonCriteria().getSecurityContext().getRoleId();
		//SL-19820
		//String serviceOrderId = getSession().getAttribute(OrderConstants.SO_ID).toString();
		String serviceOrderId = getParameter("soId");
		setAttribute(SO_ID, serviceOrderId);
		this.soId = serviceOrderId;
		//String serviceOrderId = getServiceOrderID();
		//String serviceOrderId = "001-7618-9501-2716";
		if(roleId == (OrderConstants.BUYER_ROLEID) || roleId == (OrderConstants.SIMPLE_BUYER_ROLEID)){
			surveyVO.setSurveyType(SurveyConstants.SURVEY_TYPE_PROVIDER);
			surveyVO.setEntityType(SurveyConstants.ENTITY_BUYER);
			surveyVO.setUserId(get_commonCriteria().getSecurityContext().getVendBuyerResId());
		}else if(roleId == (OrderConstants.PROVIDER_ROLEID)){
			surveyVO.setSurveyType(SurveyConstants.SURVEY_TYPE_BUYER);
			surveyVO.setEntityType(SurveyConstants.ENTITY_PROVIDER);
			surveyVO.setUserId(get_commonCriteria().getSecurityContext().getVendBuyerResId());			
		}
		
		surveyVO.setServiceOrderID(serviceOrderId);
		//surveyVO.setUserId(get_commonCriteria().getSecurityContext().getCompanyId());
		try {
			//TODO: Replace it from session 
			//Fetch the questions 
			surveyVO = detailsDelegate.retrieveQuestions(surveyVO);
			
			//String[] userAnswers = {"1","11","31","41","51","61"};
		
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
				//System.out.println(getParameter("q-"+surveyQuestionVO.getQuestionId()));
				//setting user answers
				//iAnswerId = Integer.valueOf(userAnswers[i]);
				HttpServletRequest request = ServletActionContext.getRequest();
				String ansr = getParameter("q-"+surveyQuestionVO.getQuestionId());
				if(ansr != null)
					iAnswerId = Integer.parseInt(getParameter("q-"+surveyQuestionVO.getQuestionId()));
				else {
					if(roleId == (OrderConstants.BUYER_ROLEID))
						this.setDefaultTab(SODetailsUtils.ID_RATE_PROVIDER);
					else if(roleId == (OrderConstants.PROVIDER_ROLEID))
						this.setDefaultTab(SODetailsUtils.ID_RATE_BUYER);
					//this.setRedirectURL("soDetialsController.action");
					//getSession().setAttribute(DETAILS_ERROR_MSG, "Please provide answers to all the questions.");
					getSession().setAttribute(DETAILS_ERROR_MSG+"_"+this.soId, "Please provide answers to all the questions.");
					setAttribute(DETAILS_ERROR_MSG, "Please provide answers to all the questions.");
					return ERROR;
				}
				
				responses = new ArrayList();
				surveyResponseVO = new SurveyResponseVO();
				
				surveyResponseVO.setAnswerId(iAnswerId);
				surveyResponseVO.setSurveyId(surveyVO.getSurveyId());
				surveyResponseVO.setEntityTypeId(surveyVO.getEntityTypeId());
				surveyResponseVO.setEntityId(surveyVO.getUserId());
				surveyResponseVO.setQuestionId(surveyQuestionVO.getQuestionId());
				
				//System.out.println("ServiceOrderRateAction-->QuestionId="+surveyResponseVO.getQuestionId()+"-->AnswerId="+iAnswerId);
				
				responses.add(surveyResponseVO);
				surveyQuestionVO.setResponses(responses);
			}
			String comments = getParameter("surveyComments");			
			surveyVO.setSurveyComments(UIUtils.encodeSpecialChars(comments));
			detailsDelegate.saveResponse(surveyVO);
		} 
		catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		
	//	try {
			//this.setDefaultTab(SODetailsUtils.ID_RATE_PROVIDER);
		this.setDefaultTab(SODetailsUtils.ID_VIEW_RATINGS);
		//SL-19820
		//getSession().setAttribute(REFETCH_SERVICE_ORDER, new Boolean(true));
		return GOTO_COMMON_DETAILS_CONTROLLER;
		//return "detailsSuccess";
	}
	
	/**
	 * Description of the Method
	 * 
	 * @exception Exception
	 *                Description of the Exception
	 */
	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
		clearSessionAttributes();
	}

	public String execute() throws Exception {
		
		retrieveQuestions();
		return SUCCESS;
	}

	public ServiceOrderDTO getModel() {
		return null;
	}
	
}
