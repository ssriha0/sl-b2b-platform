package com.newco.marketplace.web.action.powerbuyer;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.powerbuyer.ClaimVO;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.action.details.SODetailsSessionCleanup;
import com.newco.marketplace.web.delegates.IPowerBuyerDelegate;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.dto.SLTabDTO;
import com.newco.marketplace.web.validator.sow.SOWSessionFacility;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.15 $ $Author: glacy $ $Date: 2008/04/26 01:13:53 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class PBControllerAction extends SLBaseAction implements Preparable
{
	private IPowerBuyerDelegate pbDelegate;
	private ISOWizardFetchDelegate fetchDelegate;
	private static final Logger logger = Logger.getLogger(PBControllerAction.class.getName());
	private static final long serialVersionUID = 1L;
	private static final String TAB_WORKFLOW = "Workflow";
	private static final String TAB_CLAIMED = "Current Claimed";
	private static final String TAB_SEARCH = "Search";
	
	public ISOWizardFetchDelegate getFetchDelegate() {
		return fetchDelegate;
	}

	public void setFetchDelegate(ISOWizardFetchDelegate fetchDelegate) {
		this.fetchDelegate = fetchDelegate;
	}
	
	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();
		//From WorkFlow
		logger.debug("Invoking getStartPointAndInvalidate()" );
		//Added for  Incidents 4131093 (Sl-19820)
		String soId = getParameter("soId");
		setAttribute(OrderConstants.SO_ID, soId);
		SOWSessionFacility.getInstance().getStartPointAndInvalidate(fetchDelegate, get_commonCriteria().getSecurityContext());
		getSession().removeAttribute(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR);
		//Clearing the session objects of Service Order Detail
		SODetailsSessionCleanup.getInstance().cleanUpSession();
		//SL-19820
		String pbwfMessage = getParameter(Constants.SESSION.PB_WF_MESSAGE);
		setAttribute(Constants.SESSION.PB_WF_MESSAGE, pbwfMessage);
		
		String showErrors = getParameter("showErrors");
		if("true".equals(showErrors)){
			setAttribute("ShowErrors", "true");
		}
	}	

	
	
	// EXECUTE METHODS SHOULD BE CONSIDERED AS AN ENTRY POINT 
	public String execute() throws Exception
	{
		HttpSession session = getSession();
		
		session.setAttribute(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR, Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR);
		//SL-19820
		/*String gotoWorkflowTab = (String)session.getAttribute(Constants.SESSION.GOTO_WORKFLOW_TAB);
		session.removeAttribute(Constants.SESSION.GOTO_WORKFLOW_TAB);*/
		String gotoWorkflowTab = getParameter(Constants.SESSION.GOTO_WORKFLOW_TAB);
		//SL-19820
		//TODO : not sure what to do
		String wfmDisplayTab = getRequest().getParameter(Constants.SESSION.WORKFLOW_DISPLAY_TAB);
		/*if(wfmDisplayTab != null){
			getSession().setAttribute(Constants.SESSION.WORKFLOW_DISPLAY_TAB, wfmDisplayTab);
		}else{
			wfmDisplayTab = (String)getSession().getAttribute(Constants.SESSION.WORKFLOW_DISPLAY_TAB);
		}*/
		
		// params for filtering
		boolean filterChanged;
		try {
			int refType = Integer.parseInt(getRequest().getParameter("refType"));
			if (refType > 0) {
				String searchByBuyerId = getRequest().getParameter("searchByBuyerId");
				String buyerRefValue = getRequest().getParameter("refVal");
				// UNIT NUMBER should consist of 7 characters
				if (buyerRefValue != null && OrderConstants.BUYER_REFERENCE_ID_ONE.equals(getRequest().getParameter("refType"))) {
					buyerRefValue = OrderConstants.BUYER_REFERENCE_UNIT_NUMBER_FORMAT + buyerRefValue;
					buyerRefValue = buyerRefValue.substring(buyerRefValue.length() - OrderConstants.BUYER_REFERENCE_UNIT_NUMBER_LENGTH);
				}
				getSession().setAttribute("buyerRefTypeId", new Integer(refType));
				getSession().setAttribute("buyerRefValue", buyerRefValue);
				getSession().setAttribute("searchByBuyerId", searchByBuyerId);
			} else {
				session.removeAttribute("buyerRefTypeId");
				session.removeAttribute("buyerRefValue");
				session.removeAttribute("searchByBuyerId");
			}
			filterChanged = true;
		} catch (NumberFormatException e) {
			filterChanged = false;
		}
		
		if (wfmDisplayTab != null) {
			session.removeAttribute(Constants.SESSION.WORKFLOW_DISPLAY_TAB);
			_setTabs(null, wfmDisplayTab);
		} else {
			if (filterChanged) {
				_setTabs(null, TAB_WORKFLOW);
			} else {
				// get claimed service orders
				// if user has open claimed orders goto the Claimed tab
				// otherwise go to the Work flow tab
				
				List<ClaimVO> claimedSO = pbDelegate.getClaimedSO(get_commonCriteria().getVendBuyerResId());
				
				if (claimedSO.isEmpty() || StringUtils.isNotEmpty(gotoWorkflowTab)) {
					_setTabs(null, TAB_WORKFLOW);
				} else {
					_setTabs(null, TAB_CLAIMED);
				}
			}
		}
		return "success";
	}
	
	
	public void _setTabs(String role, String selected)
	{
		List<SLTabDTO> tabList = new ArrayList<SLTabDTO>();
		SLTabDTO tab;
		String icon=null;
		String wfSodFlag=getRequest().getParameter("wfmSodFlag");
		String soId=getRequest().getParameter("soId");
		String pbFilterNameVar= getRequest().getParameter("pbFilterName");
		if(wfSodFlag!=null && (wfSodFlag.equals("Provider")||wfSodFlag.equals("Firm")))
		
			selected=TAB_SEARCH;
			
				
		//if(selected == null)
		{
			icon = null;
			
			tab = new SLTabDTO("", "pbWorkflowTab_execute.action", TAB_WORKFLOW, icon, selected, null);		
			tabList.add(tab);

			tab = new SLTabDTO("tab2", "pbClaimedTab_execute.action", TAB_CLAIMED, icon, selected, null);		
			tabList.add(tab);
			
			if(wfSodFlag!=null && (wfSodFlag.equals("Provider")||wfSodFlag.equals("Firm")))
			{
				tab = new SLTabDTO("Search","tab2", "/MarketFrontend/monitor/gridHolder.action?tab=Search&wfFlag=1&pbFilterName="+pbFilterNameVar+"&wfmSodFlag="+wfSodFlag+"&soId="+soId
			,"Search", icon, selected, null);
			}
			else	
			tab = new SLTabDTO("Search","tab2", "/MarketFrontend/monitor/gridHolder.action?tab=Search&wfFlag=1", "Search", icon, selected, null);		
			
			tabList.add(tab);
		}
			
		
		setAttribute("tabList", tabList);				
	}


	public IPowerBuyerDelegate getPbDelegate() {
		return pbDelegate;
	}


	public void setPbDelegate(IPowerBuyerDelegate pbDelegate) {
		this.pbDelegate = pbDelegate;
	}
	
	
	public String backToAdminWorkFlow() throws Exception
	{
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute("SecurityContext");
		if (!securityContext.isAdopted()) {
			securityContext.setRoleId(securityContext.getAdminRoleId());
			securityContext.setUsername(securityContext.getSlAdminUName());
			securityContext.setRole(OrderConstants.NEWCO_ADMIN);
			//Refer to LoginAction where companyId is set to 99 by default
			securityContext.setCompanyId(OrderConstants.NEWCO_ADMIN_COMPANY_ROLE);
			//Set by default
			securityContext.setVendBuyerResId(securityContext.getAdminResId());
			
			// set the common criteria
			get_commonCriteria().setRoleId(get_commonCriteria().getSecurityContext().getAdminRoleId());
			get_commonCriteria().getSecurityContext().setRoleId(get_commonCriteria().getSecurityContext().getAdminRoleId());
			get_commonCriteria().setCompanyId(LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION);
			get_commonCriteria().getSecurityContext().setCompanyId(LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION);
			get_commonCriteria().setVendBuyerResId(get_commonCriteria().getSecurityContext().getAdminResId());
			get_commonCriteria().getSecurityContext().setVendBuyerResId(get_commonCriteria().getSecurityContext().getAdminResId());
	
			this.createCommonServiceOrderCriteria();
			clearBuyerInfo();
			clearProviderInfo();
			clearAccountDetailsFromSession();
		}
		
		return execute();
	}
	
	
}
/*
 * Maintenance History
 * $Log: PBControllerAction.java,v $
 * Revision 1.15  2008/04/26 01:13:53  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.13.20.1  2008/04/23 11:41:47  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.14  2008/04/23 05:19:33  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.13  2008/01/24 19:35:52  dmill03
 * Updated for PowerBuyer feature
 *
 * Revision 1.12  2008/01/23 20:01:48  pkoppis
 * updated with search tab and displaytab as workflow tab
 *
 * Revision 1.11  2008/01/21 23:02:15  mhaye05
 * needed to check flag to see if we need to go to the work flow tab even though the user may have claimed service orders
 *
 * Revision 1.10  2008/01/21 20:32:39  cgarc03
 * _setTabs() : Set onclick tab parameters to null.
 *
 * Revision 1.9  2008/01/21 19:48:34  cgarc03
 * Added onclick parameter to tab initialization.
 *
 * Revision 1.8  2008/01/17 18:40:10  mhaye05
 * added the setting of the Session attribute to indicate that the user
 * is coming from the workflow monitor
 *
 * Revision 1.7  2008/01/17 18:04:08  dmill03
 * added workflow flag
 *
 * Revision 1.6  2008/01/16 16:30:34  mhaye05
 * updated to pull and display dynamicly pulled claimed so data
 *
 * Revision 1.5  2008/01/16 15:53:31  mhaye05
 * added logic to retrieve the claimed service orders
 *
 * Revision 1.4  2008/01/16 15:15:20  mhaye05
 * added java doc
 *
 */
