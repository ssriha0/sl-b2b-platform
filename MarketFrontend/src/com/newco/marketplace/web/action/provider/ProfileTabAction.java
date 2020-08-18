package com.newco.marketplace.web.action.provider;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.ISPNBuyerDelegate;
import com.newco.marketplace.web.delegates.provider.IPublicProfileDelegate;
import com.newco.marketplace.web.dto.SPNProviderProfileBuyerTable;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.opensymphony.xwork2.ActionContext;;


/**
 * @author KSudhanshu
 * $Revision: 1.15 $ $Author: glacy $ $Date: 2008/05/30 02:36:13 $
 */
public class ProfileTabAction extends SLBaseAction implements ServletResponseAware,ServletRequestAware {

	private static final long serialVersionUID = 7854683553168331013L;
	private static final Logger logger = Logger.getLogger(ProfileTabAction.class.getName());
	private BaseTabDto tabDto;
	private String tabView;
	private HttpServletResponse response;
	private HttpSession session;
	private String tabType = null;
	private String nexturl = null;
	private String resourceId;
	private IPublicProfileDelegate publicProfileDelegate;
	private ISPNBuyerDelegate spnDelegate;
	
	// Node Id which should be passed thru hidden Parameter
	private Integer nodeId;
	private HttpServletRequest request;
	private SecurityContext securityContext;
	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @param tabDto
	 */
	public ProfileTabAction(BaseTabDto tabDto,
							IPublicProfileDelegate publicProfileDelegate,
							ISPNBuyerDelegate spnDelegate) {
		super();
		this.tabDto = tabDto;
		this.publicProfileDelegate = publicProfileDelegate;
		this.spnDelegate = spnDelegate;
	}
	
	public String getTabType() {
		return tabType;
	}
	public void setTabType(String tabType) {
		this.tabType = tabType;
	}
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */

	public String execute() throws Exception {

		createCommonServiceOrderCriteria();
		boolean isPopup = false;
		boolean chkStatus = false;
		String vendorId;
		int roleID = 0;
		setContextDetails();
		// This would be the special case when we reach to this Action outside
		// the TAB Env.
		// especially from Find providers page.
		if (securityContext == null) {
			roleID = BUYER_ROLEID;
			vendorId = "-1";
			if (request.getParameter("companyId") != null) {
				vendorId = "1";
				// Setting stuff to session so that we can retrive in the
				// embedded next actions
				request.getSession().setAttribute("findProvider_companyId",
						vendorId);
			}

		} else {

			if (securityContext.getRoleId() == OrderConstants.BUYER_ROLEID) {
				vendorId = request.getParameter("companyId");

				if (vendorId == null) {
					vendorId = (String) ActionContext.getContext().getSession()
							.get(VENDOR_ID);
				}
				if (vendorId == null) {
					// Trying to retrieve VENDOR_ID parameter from request
					vendorId = request.getParameter(VENDOR_ID);
				}
			} else {
				vendorId = securityContext.getCompanyId() + "";
			}

			roleID = securityContext.getRoleId();
			logger.debug("inside profile tab-----" + vendorId + "---reId--"
					+ resourceId + "role here is ----"
					+ securityContext.getRoleId());
		}// Bad workaround !!!!!!!!!!!!

		ActionContext.getContext().getSession().put(VENDOR_ID, vendorId);
		ActionContext.getContext().getSession().put("loginRoleId", roleID);
		if (resourceId != null && resourceId.trim().length() > 0) {
			ActionContext.getContext().getSession().put(RESOURCE_ID,
					resourceId);
		}

		chkStatus = publicProfileDelegate.checkVendorResource(resourceId,
				vendorId);
		logger.debug("flag status here is----" + chkStatus);

		ActionContext.getContext().getSession().put("companyFlag", chkStatus);
		if (nodeId != null) {
			ActionContext.getContext().getSession().put("selectedNodeId",
					nodeId);
		}
		if (ActionContext.getContext().getSession().get("tabDto") == null
				|| tabView == null) {
			ActionContext.getContext().getSession().put("tabDto", tabDto);
			logger.debug("inside here after setting tabdto1"
					+ ActionContext.getContext().getSession().get("tabDto"));
			vendorId = (String) ActionContext.getContext().getSession().get(
					VENDOR_ID);

			tabDto = (BaseTabDto) ActionContext.getContext().getSession().get(
					"tabDto");
		}

		// Integer providerId = get_commonCriteria().getVendBuyerResId();

		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Expires", "0");
		if (request.getParameter("popup") != null) {
			String popup = request.getParameter("popup");
			isPopup = "true".equalsIgnoreCase(popup) ? true : false;
		}
		if (isPopup) {
			
			setResourceId((String) ActionContext.getContext().getSession().get(RESOURCE_ID));
			return "successpopup";
		}
		return SUCCESS;
	}
	public String loadCompleteProfile() throws Exception {
		setContextDetails();
		if(resourceId!=null&&resourceId.trim().length()>0){
			ActionContext.getContext().getSession().put("resourceId", resourceId);
		}
		String vendorId="";
		boolean chkStatus = false;
		if(securityContext.getRoleId()==OrderConstants.BUYER_ROLEID)
		{
			vendorId = request.getParameter("companyId");
		}
		else
		{
			vendorId = securityContext.getCompanyId() + "";
		}
		logger.debug("inside completeprofile tab-----"+vendorId+"---reId--"+resourceId+"role here is ----"+securityContext.getRoleId());
		
		
		
		if(nodeId!=null){
			ActionContext.getContext().getSession().put("selectedNodeId", nodeId);
		}
		chkStatus = publicProfileDelegate.checkVendorResource(resourceId, vendorId);
		logger.debug("flag status here is----"+chkStatus);
		
		ActionContext.getContext().getSession().put("companyFlag", chkStatus);
		if (ActionContext.getContext().getSession().get("tabDto") == null || tabView == null){
			ActionContext.getContext().getSession().put("tabDto", tabDto);
			logger.debug("inside here after setting tabdto"+ActionContext.getContext().getSession().get("tabDto"));
			 vendorId = (String)ActionContext.getContext().getSession().get("vendorId");
		
		tabDto = (BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		}
		
		
		// SPN
		Integer providerId = get_commonCriteria().getVendBuyerResId();
		List<SPNProviderProfileBuyerTable> spnList = spnDelegate.getProviderProfileBuyers(providerId);
		setAttribute("spnList",spnList);
		
		
		tabView = "tab1";
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Expires", "0"); 
		return "load";
	}
	private void setContextDetails() {
		session = ServletActionContext.getRequest().getSession();
		securityContext = (SecurityContext) session
				.getAttribute("SecurityContext");
	}
	
	/**
	 * @return the tabView
	 */
	public String getTabView() {
		return tabView;
	}
	/**
	 * @param tabView the tabView to set
	 */
	public void setTabView(String tabView) {
		this.tabView = tabView;
	}
	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
		
	}
	/**
	 * @return the tabDto
	 */
	public BaseTabDto getTabDto() {
		return tabDto;
	}
	
	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}
	/**
	 * @param response the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @return the nexturl
	 */
	public String getNexturl() {
		return nexturl;
	}

	/**
	 * @param nexturl the nexturl to set
	 */
	public void setNexturl(String nexturl) {
		this.nexturl = nexturl;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		request=arg0;
	}

	public IPublicProfileDelegate getPublicProfileDelegate() {
		return publicProfileDelegate;
	}

	public void setPublicProfileDelegate(
			IPublicProfileDelegate publicProfileDelegate) {
		this.publicProfileDelegate = publicProfileDelegate;
	}

	public ISPNBuyerDelegate getSpnDelegate() {
		return spnDelegate;
	}

	public void setSpnDelegate(ISPNBuyerDelegate spnDelegate) {
		this.spnDelegate = spnDelegate;
	}
	
	
}
/*
 * Maintenance History
 * $Log: ProfileTabAction.java,v $
 * Revision 1.15  2008/05/30 02:36:13  glacy
 * SecurityChanges
 *
 * Revision 1.14  2008/05/28 23:10:43  hoza
 * openurl for the profileTabAction on findprovider page
 *
 * Revision 1.13  2008/05/28 01:23:12  hoza
 * openurl for the profileTabAction on findprovider page
 *
 * Revision 1.12  2008/05/02 21:23:20  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.11  2008/04/26 01:13:49  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.9.20.2  2008/05/01 20:45:06  mhaye05
 * updated to display membership date
 *
 * Revision 1.9.20.1  2008/04/30 20:10:46  cgarc03
 * Added spnBuyerProfileDelegate.
 *
 * Revision 1.9.2.1  2008/04/23 11:41:39  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.10  2008/04/23 05:19:35  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.9  2008/03/04 21:16:10  mhaye05
 * merged from infosys_defect_branch_3_5_release
 *
 * Revision 1.8.2.1  2008/02/28 23:31:15  glacy
 * CR ID Sears00047668-SOD-Response History Provider link pop up missing
 * change: retrieved the vendor id from the request if its null
 * changed by sgopala
 *
 * Revision 1.8  2008/02/26 18:18:04  mhaye05
 * Merged Iteration 17 Branch into Head
 *
 * Revision 1.7.2.1  2008/02/25 18:58:48  mhaye05
 * replaced system out println with logger.debug statements and some general code cleanup
 *
 */