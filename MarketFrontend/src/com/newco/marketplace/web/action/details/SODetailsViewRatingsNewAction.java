package com.newco.marketplace.web.action.details;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.survey.ExtendedSurveyBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.interfaces.SurveyConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.dto.vo.survey.SurveyQuestionsAnswersResponse;
import com.newco.marketplace.dto.vo.survey.SurveyVO;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.opensymphony.xwork2.Preparable;

/**
 /**
 * Created by sshariq on 01/23/2019.
 */

public class SODetailsViewRatingsNewAction extends SLDetailsBaseAction implements Preparable, SurveyConstants {
	private Logger logger = Logger.getLogger(SODetailsViewRatingsNewAction.class);

	private static final long serialVersionUID = 10005;// arbitrary number to get rid
												// of warning
	protected ExtendedSurveyBO extendedSurveyBO;
	
	private SurveyQuestionsAnswersResponse surveyData;

	public SODetailsViewRatingsNewAction(ISODetailsDelegate delegate) {
		this.detailsDelegate = delegate;
	}

	public String execute() throws Exception {
		
		String serviceOrderId = getParameter("soId");
		String sodRoutedResourceId = getParameter("resId");
		String id = null;
		if(!StringUtils.isBlank(serviceOrderId)){
			id = serviceOrderId;
		}
		setAttribute(OrderConstants.SO_ID, serviceOrderId);
		setAttribute("routedResourceId", sodRoutedResourceId);
		
		String groupId = getParameter("groupId");
		if(StringUtils.isBlank(groupId)){
			groupId = null;
		}else{
			id = groupId;
		}
		
		String msg = (String)getSession().getAttribute(Constants.SESSION.SOD_MSG+"_"+id);
		getSession().removeAttribute(Constants.SESSION.SOD_MSG+"_"+id);
		setAttribute(Constants.SESSION.SOD_MSG, msg);
		
		ServiceOrderDTO soDTO = null;
		try{
			if(StringUtils.isNotBlank(sodRoutedResourceId)) {
				Integer resId = Integer.parseInt(sodRoutedResourceId);
				soDTO = getDetailsDelegate().getServiceOrder(soId, get_commonCriteria().getRoleId(), resId);
			}
			else
				soDTO = getDetailsDelegate().getServiceOrder(soId, get_commonCriteria().getRoleId(), null);
			
			setAttribute(THE_SERVICE_ORDER, soDTO);
		}
		catch(Exception e){
			logger.error("Error while fetching service order");
		}
		
		// int roleId = get_commonCriteria().getSecurityContext().getRoleId();
		Integer theCStatus = (Integer) (soDTO.getStatus());
		getRequest().setAttribute(Constants.SESSION.SOD_RSLT_PROV_TO_BUY, null);

		try {
			String surveyStatus = extendedSurveyBO.getServiceOrderFlowType(serviceOrderId);
			getRequest().setAttribute(SOD_RSLT_STATUS, surveyStatus);
			if (CSAT_NPS_NEWFLOW.equalsIgnoreCase(surveyStatus)) {
				surveyData = extendedSurveyBO.getSurveyQuestionsWithAnswers(serviceOrderId);
				getRequest().setAttribute(SOD_RSLT_BUYER, surveyData);

				if (theCStatus != null && theCStatus.equals(OrderConstants.CLOSED_STATUS)
						&& soDTO.isProviderHasRatedBuyer()) {
					SurveyVO surveyVO = new SurveyVO();
					surveyVO.setServiceOrderID(serviceOrderId);
					surveyVO.setSurveyType(SurveyConstants.SURVEY_TYPE_BUYER);
					surveyVO.setEntityType(SurveyConstants.ENTITY_PROVIDER);
					surveyVO.setEntityTypeId(SurveyConstants.ENTITY_PROVIDER_ID);
					getRequest().setAttribute(Constants.SESSION.SOD_RSLT_PROV_TO_BUY,
							detailsDelegate.getSurveyResults(surveyVO));
				}
			}
		} catch (Exception e) {
			logger.error("Exception Occurred: " + e);
			throw new RuntimeException(e);
		}

		return SUCCESS;
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

	public ExtendedSurveyBO getExtendedSurveyBO() {
		return extendedSurveyBO;
	}	

	public void setExtendedSurveyBO(ExtendedSurveyBO extendedSurveyBO) {
		this.extendedSurveyBO = extendedSurveyBO;
	}
}
