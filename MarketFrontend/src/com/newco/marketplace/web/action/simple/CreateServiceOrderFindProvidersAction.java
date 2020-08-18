package com.newco.marketplace.web.action.simple;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.ISimpleServiceOrderAction;
import com.newco.marketplace.web.action.base.SLSimpleBaseAction;
import com.newco.marketplace.web.delegates.ICreateServiceOrderDelegate;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderFindProvidersDTO;
import com.newco.marketplace.web.security.NonSecurePage;
import com.newco.marketplace.web.utils.SSoWSessionFacility;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * $Revision: 1.9 $ $Author: rgurra0 $ $Date: 2008/05/28 16:40:34 $
 */
/**
 * @author Administrator
 *
 */
@NonSecurePage
public class CreateServiceOrderFindProvidersAction extends SLSimpleBaseAction 
					implements Preparable, SessionAware, ModelDriven<CreateServiceOrderFindProvidersDTO>, ISimpleServiceOrderAction
{
	private ICreateServiceOrderDelegate createServiceOrderDelegate;
	private static final Logger logger = Logger.getLogger("CreateServiceOrderFindProvidersAction");
	private CreateServiceOrderFindProvidersDTO model = new CreateServiceOrderFindProvidersDTO();
	private Map sessionMap;
	
	private static final long serialVersionUID = 0L;

	public CreateServiceOrderFindProvidersAction(ICreateServiceOrderDelegate delegate){
		createServiceOrderDelegate = delegate;
	}
	
	public void prepare() throws Exception
	{
		//USED THIS METHOD TO DETERMINE IF THE USER
		// IS LOGGED INTO THE SITE
		createCommonServiceOrderCriteria();
		
		try {
			// SINCE THE SESSIONFACTILITY HAVE CREATE ALL DTO'S AND PLACED THEM INTO THE SESSION
			// WE JUST NEED TO RETRIEVE THEM AT THIS POINT. PLEASE NOTE THE getModel method below
			 setModel((CreateServiceOrderFindProvidersDTO) SSoWSessionFacility.getInstance().getTabDTO(OrderConstants.SSO_FIND_PROVIDERS_DTO));
			
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring", e);
		}
	}
	
	public String execute() throws Exception {
		return displayPage();
	}
	
	public String displayPage() throws Exception
	{
		
		// set variable which indicates SO has started
		Map<String, Object> sessionMap = (Map<String, Object>)ActionContext.getContext().getSession();
		sessionMap.put(OrderConstants.SSO_WIZARD_IN_PROGRESS, OrderConstants.SSO_WIZARD_IN_PROGRESS_VALUE);
		
		getRequest().setAttribute("FIND_PROVIDERS_PAGE", true);
		return SUCCESS;
	}
	
	public String next() throws Exception {
		
		// IF YOU HAVE SCREEN VALIDATIONS CALL THE evaluateSSOWBeanState METHOD
		// ON THE SSoWSessionFacility INSTANCE.
		getModel().clearAllErrors();
		SSoWSessionFacility.getInstance().evaluateSSOWBeanState( getModel());
		//AFTER CALLING evaluateSSOWBeanState() THE DTO WILL HAVE A LIST OF 
		// IERROR MESSAGES POPULATED. IN ORDER TO GET THIS TO WORK YOU WILL NEED TO 
		// IMPLEMENT THE validate METHOD LOCATED IN YOUR DTO. PLEASE NOTE: YOUR DTO MUST
		// EXTEND THE SOWBaseTabDTO CLASS.
		List<IError> errorsList = getModel().getErrorsOnly();
		if(errorsList.size()> 0) {
			return TO_SSO_FIND_PROVIDERS_VIEW;
		}
		return "to_next_action";
	}

	public String previous() throws Exception {

		getModel().clearAllErrors();
		return TO_SSO_HOMEPAGE_VIEW;
	}
	
	
	public CreateServiceOrderFindProvidersDTO getModel()
	{
		model = (CreateServiceOrderFindProvidersDTO)
							SSoWSessionFacility.getInstance().getTabDTO(OrderConstants.SSO_FIND_PROVIDERS_DTO);
		return model;
	}

	
	public void setModel(CreateServiceOrderFindProvidersDTO model){
		this.model = model;
	}

	public ICreateServiceOrderDelegate getCreateServiceOrderDelegate() {
		return createServiceOrderDelegate;
	}

	public void setCreateServiceOrderDelegate(
			ICreateServiceOrderDelegate createServiceOrderDelegate) {
		this.createServiceOrderDelegate = createServiceOrderDelegate;
	}

	public void setSession(Map map) {
		this.sessionMap = map;
	}

}
