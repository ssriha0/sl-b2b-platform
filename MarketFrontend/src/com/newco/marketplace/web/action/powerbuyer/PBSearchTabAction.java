package com.newco.marketplace.web.action.powerbuyer;



import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.powerbuyer.ClaimVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.delegates.AdminBuyerSearchDelegate;
import com.newco.marketplace.web.delegates.IPowerBuyerDelegate;
import com.newco.marketplace.web.dto.ProviderWidgetResultsDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.opensymphony.xwork2.Preparable;


/**
 * $Revision: 1.10 $ $Author: glacy $ $Date: 2008/04/26 01:13:53 $
 */

/*
 * Maintenance History: See bottom of file
 */

public class PBSearchTabAction extends PBBaseAction implements
Preparable
{
	private static final long serialVersionUID = 1010;

	private IPowerBuyerDelegate pbDelegate;
	private AdminBuyerSearchDelegate buyerSearchDelegate;
	private static final Logger logger = Logger
	.getLogger(PBSearchTabAction.class.getName());
	
	//SL-19820
	String pbwfMessage;
	String gotoWFM;
	
	public String getGotoWFM() {
		return gotoWFM;
	}

	public void setGotoWFM(String gotoWFM) {
		this.gotoWFM = gotoWFM;
	}

	public String getPbwfMessage() {
		return pbwfMessage;
	}

	public void setPbwfMessage(String pbwfMessage) {
		this.pbwfMessage = pbwfMessage;
	}

	public AdminBuyerSearchDelegate getBuyerSearchDelegate() {
		return buyerSearchDelegate;
	}

	public void setBuyerSearchDelegate(AdminBuyerSearchDelegate buyerSearchDelegate) {
		this.buyerSearchDelegate = buyerSearchDelegate;
	}
	
	public PBSearchTabAction() {
		
	}
	
	public IPowerBuyerDelegate getPbDelegate() {
		return pbDelegate;
	}

	public void setPbDelegate(IPowerBuyerDelegate pbDelegate) {
		this.pbDelegate = pbDelegate;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public String claimNext() throws Exception {
		
		String soId = getRequest().getParameter("soId");
		String parentGroupId = StringUtils.trimToNull(getRequest().getParameter("groupId"));
		
		ClaimVO toClaimVO = new ClaimVO();
		toClaimVO.setSoId(soId);
		toClaimVO.setParentGroupId(parentGroupId);
		if (get_commonCriteria().getSecurityContext().isSlAdminInd())
			toClaimVO.setBuyerId(LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION);
		else
			toClaimVO.setBuyerId( get_commonCriteria().getCompanyId());
		toClaimVO.setResourceId( get_commonCriteria().getVendBuyerResId());
		
		try {
			toClaimVO.setQueueId(Integer.valueOf(getRequest().getParameter("queueId")));
		}
		catch (Exception e) {
		}
		
		ClaimVO claimedVO = pbDelegate.claimSO(toClaimVO);
		
		getSession().removeAttribute("GID");
		String dest = null;
		if (null != claimedVO){
			//SL-19820
			/*getSession().setAttribute(OrderConstants.SO_ID, claimedVO.getSoId());
			if (parentGroupId != null)
				getSession().setAttribute("GID", parentGroupId);*/
			/*getSession().setAttribute(OrderConstants.SERVICE_ORDER_WIZARD_INDICTATOR,"true");*/
			//getSession().setAttribute(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR, Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR);
			getSession().setAttribute(OrderConstants.SERVICE_ORDER_WIZARD_INDICTATOR+"_"+soId,"true");
			dest = determineDest(claimedVO);
			boolean isBuyer = get_commonCriteria().getSecurityContext().isBuyer();
			ProviderWidgetResultsDTO widget = buyerSearchDelegate.getInfoForProviderWidget(claimedVO.getSoId(),isBuyer,claimedVO.getBuyerId());
			//SL-19820
			//ServiceOrderDTO serviceOrderDTO=(ServiceOrderDTO) getSession().getAttribute(THE_SERVICE_ORDER);
			if(widget!=null) {
				widget.setSoId(soId);
				/*if(serviceOrderDTO!=null && serviceOrderDTO.getProviderContact()!=null && serviceOrderDTO.getProviderContact().getCompanyID()!=null && !"".equalsIgnoreCase(serviceOrderDTO.getProviderContact().getCompanyID()))
					widget.setProviderFirmId(serviceOrderDTO.getProviderContact().getCompanyID());
				if(serviceOrderDTO!=null && serviceOrderDTO.getProviderContact()!=null && serviceOrderDTO.getProviderContact().getCompanyName()!=null && !"".equalsIgnoreCase(serviceOrderDTO.getProviderContact().getCompanyName()))
					widget.setProviderFirmName(serviceOrderDTO.getProviderContact().getCompanyName());
				if(serviceOrderDTO!=null && serviceOrderDTO.getAcceptedResourceId()!=null && !"".equalsIgnoreCase(serviceOrderDTO.getAcceptedResourceId().toString()))
					widget.setProviderID(serviceOrderDTO.getAcceptedResourceId().toString());
				if(serviceOrderDTO!=null && serviceOrderDTO.getAcceptedResource()!=null && serviceOrderDTO.getAcceptedResource().getResourceContact()!=null)
				{
					Contact c = serviceOrderDTO.getAcceptedResource().getResourceContact();
					String providerName = "";
					if("".equals(c.getFirstName()))
						providerName = c.getFirstName();
					if("".equals(c.getLastName()))
						providerName = providerName +" "+c.getLastName();
					widget.setProviderName(providerName);
				}*/
				getSession().setAttribute("widgetProResultsDTOObj_"+soId,widget);
			}
		} 
		else{
			dest = ROUTETOWFMONITOR;
			//SL-19820
			//getSession().setAttribute(Constants.SESSION.PB_WF_MESSAGE, "pb.workflow.msg.so.claimed");
			//getSession().setAttribute(Constants.SESSION.GOTO_WORKFLOW_TAB, "true");
			pbwfMessage = "pb.workflow.msg.so.claimed";
			gotoWFM = "true";
		}
	   
	   boolean isSLAdmin = get_commonCriteria().getSecurityContext().isSlAdminInd();
		if (isSLAdmin) {
			// add the admin details to the security context when it
			// needs to be return to the Admin WorkFlow tabs
			SecurityContext securityContext = get_commonCriteria().getSecurityContext();
			securityContext.setAdminRoleId(securityContext.getRoleId());			
			securityContext.setAdminResId(securityContext.getVendBuyerResId());
			securityContext.setSlAdminUName(securityContext.getUsername());
		}

		return dest;
	}

	public String execute() throws Exception
	{
		return SUCCESS;
	}

	public void prepare() throws Exception {
		
		createCommonServiceOrderCriteria();
		
	}
}
/*
 * Maintenance History
 * $Log: PBSearchTabAction.java,v $
 * Revision 1.10  2008/04/26 01:13:53  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.7.14.1  2008/04/01 22:04:32  mhaye05
 * merged changed from Head into I18_Fin branch
 *
 * Revision 1.8  2008/03/14 16:04:31  awadhwa
 * Merge with I18 Enhancements branch
 *
 * Revision 1.7.12.1  2008/03/13 02:40:17  awadhwa
 * CR # Sears00047431: Wrong value (companyId) was getting set in resource id attribute of VO. Replaced getCompanyId() with getVendResourceId() while setting resourceId in claimVO.
 *
 * Revision 1.7  2008/01/31 17:34:56  mhaye05
 * added CAME_FROM_WORKFLOW_MONITOR to session
 *
 * Revision 1.6  2008/01/25 20:05:51  mhaye05
 * fixed spelling error
 *
 * Revision 1.5  2008/01/25 00:16:54  mhaye05
 * needed to set SOW session attribute
 *
 * Revision 1.4  2008/01/24 21:53:36  mhaye05
 * changed to now extend PBBaseAction
 *
 */
