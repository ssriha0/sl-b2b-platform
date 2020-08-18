package com.newco.marketplace.web.action.details;

import java.util.ArrayList;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.survey.SurveyVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.SurveyConstants;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.opensymphony.xwork2.Preparable;

/**
 * $Revision: 1.13 $ $Author: rgurra0 $ $Date: 2008/05/29 21:36:08 $
 */

/*
 * Maintenance History
 * $Log: SODetailsViewRatingsAction.java,v $
 * Revision 1.13  2008/05/29 21:36:08  rgurra0
 * rate providers fix
 *
 * Revision 1.12  2008/04/26 01:13:47  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.10.28.1  2008/04/23 11:41:35  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.11  2008/04/23 05:19:31  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.10  2007/12/13 23:53:23  mhaye05
 * replaced hard coded strings with constants
 *
 * Revision 1.9  2007/11/14 21:58:51  mhaye05
 * changed reference to SOW_SO_ID or THE_CURRENT_SERVICE_ORDER_ID to be OrderConstants.SO_ID
 *
 */
public class SODetailsViewRatingsAction extends SLDetailsBaseAction implements Preparable {

	private static final long serialVersionUID = 10002;// arbitrary number to get rid
												// of warning

	public SODetailsViewRatingsAction(ISODetailsDelegate delegate) {
		this.detailsDelegate = delegate;
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
		
		//Sl-19820
		//soDTO is not used anywhere. Hence commenting
		//ServiceOrderDTO soDTO = (ServiceOrderDTO)getSession().getAttribute(THE_SERVICE_ORDER);
		int roleId = get_commonCriteria().getSecurityContext().getRoleId();
		//SL-19820
		//String serviceOrderId = getSession().getAttribute(OrderConstants.SO_ID).toString();
		String serviceOrderId = getParameter("soId");
		setAttribute(SO_ID, serviceOrderId);
		String msg = (String)getSession().getAttribute(Constants.SESSION.SOD_MSG+"_"+soId);
		getSession().removeAttribute(Constants.SESSION.SOD_MSG+"_"+soId);
		setAttribute(Constants.SESSION.SOD_MSG, msg);
		SurveyVO surveyVO = new SurveyVO();
		//String serviceOrderId = "001-6434041185-11";
		//String serviceOrderId = "001-7618-9501-2716";
		surveyVO.setServiceOrderID(serviceOrderId);
		if(roleId == OrderConstants.BUYER_ROLEID || roleId == OrderConstants.SIMPLE_BUYER_ROLEID){
			surveyVO.setSurveyType(SurveyConstants.SURVEY_TYPE_PROVIDER);
			surveyVO.setEntityType(SurveyConstants.ENTITY_BUYER);
			surveyVO.setEntityTypeId(SurveyConstants.ENTITY_BUYER_ID);
		}else if(roleId == OrderConstants.PROVIDER_ROLEID){
			surveyVO.setSurveyType(SurveyConstants.SURVEY_TYPE_BUYER);
			surveyVO.setEntityType(SurveyConstants.ENTITY_PROVIDER);
			surveyVO.setEntityTypeId(SurveyConstants.ENTITY_PROVIDER_ID);
		}
		if(surveyVO.getEntityType().equals(SurveyConstants.ENTITY_BUYER)) {
			ArrayList ratingsAL1 = detailsDelegate.getSurveyResults(surveyVO);
			//SL-19820
			//getSession().setAttribute(Constants.SESSION.SOD_RSLT_BUY_TO_PROV, detailsDelegate.getSurveyResults(surveyVO));
			getRequest().setAttribute(Constants.SESSION.SOD_RSLT_BUY_TO_PROV, detailsDelegate.getSurveyResults(surveyVO));
			surveyVO = new SurveyVO();
			surveyVO.setServiceOrderID(serviceOrderId);
			surveyVO.setEntityType(SurveyConstants.ENTITY_PROVIDER);
			surveyVO.setEntityTypeId(SurveyConstants.ENTITY_PROVIDER_ID);
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
			surveyVO.setEntityTypeId(SurveyConstants.ENTITY_BUYER_ID);
			ArrayList ratingsAL = detailsDelegate.getSurveyResults(surveyVO);
			if(ratingsAL != null)
				//SL-19820
				//getSession().setAttribute(Constants.SESSION.SOD_RSLT_BUY_TO_PROV, ratingsAL);
				getRequest().setAttribute(Constants.SESSION.SOD_RSLT_BUY_TO_PROV, ratingsAL);
		}
		
		//this.setDefaultTab(SODetailsUtils.ID_VIEW_RATINGS);
		return SUCCESS;
		//return GOTO_COMMON_DETAILS_CONTROLLER;
	}

	public ServiceOrderDTO getModel() {
		// TODO Auto-generated method stub
		return null;
	}
}
