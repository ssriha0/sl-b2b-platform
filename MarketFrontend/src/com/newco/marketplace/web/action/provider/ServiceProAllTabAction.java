package com.newco.marketplace.web.action.provider;

import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.AUDIT_TIME_LOGGING_ID;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.provider.IAuditStates;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.web.action.base.SLAuditableBaseAction;
import com.newco.marketplace.web.delegates.adminlogging.IAdminLoggingDelegate;
import com.newco.marketplace.web.delegates.provider.IActivityRegistryDelegate;
import com.newco.marketplace.web.delegates.provider.IResourceDelegate;
import com.newco.marketplace.web.dto.adminlogging.AdminLoggingDto;
import com.newco.marketplace.web.dto.ajax.AjaxRequestModelDTO;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.dto.provider.ResourceDto;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @author KSudhanshu
 * $Revision: 1.25 $ $Author: glacy $ $Date: 2008/04/26 01:13:51 $
 */
public class ServiceProAllTabAction extends SLAuditableBaseAction 
									implements  ServletResponseAware, 
												ServletRequestAware,
												ModelDriven<AjaxRequestModelDTO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8229258901092205715L;

	private static final Logger logger = Logger.getLogger("ServiceProAllTabAction");
	private AjaxRequestModelDTO theModel = new AjaxRequestModelDTO();
	private IActivityRegistryDelegate activityRegistryDelegate;
	private IResourceDelegate iResourceDelegate;
	private BaseTabDto tabDto;
	private String tabView;
	private HttpServletResponse response;
	private HttpServletRequest request;
	private String tabType = null;
	private String nexturl = null;
	private String resourceId;
	private Map sSessionMap;
	
	
	private IAdminLoggingDelegate adminLoggingDelegate;
	private AdminLoggingDto adminLoggingDto;
	/**
	 * @param activityRegistryDelegate
	 * @param tabDto
	 */
	public ServiceProAllTabAction(
			IActivityRegistryDelegate activityRegistryDelegate,
			IResourceDelegate iResourceDelegate,
			BaseTabDto tabDto,
			IAdminLoggingDelegate adminLoggingDelegate) {
		this.activityRegistryDelegate = activityRegistryDelegate;
		this.iResourceDelegate = iResourceDelegate;
		this.tabDto = tabDto;
		this.adminLoggingDelegate =adminLoggingDelegate;
	}

	/**
	 * @param tabDto
	 */
	public ServiceProAllTabAction(BaseTabDto tabDto) {
		super();
		this.tabDto = tabDto;
	}

	public String getTabType() {
		return tabType;
	}

	public void setTabType(String tabType) {
		this.tabType = tabType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	//SL-20645 : modified the method get and set the audit logging id in request
	public String execute() throws Exception {

		String auditTimeLoggingId =  (String) ServletActionContext.getRequest().getParameter(AUDIT_TIME_LOGGING_ID);
		ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditTimeLoggingId);
		
		logger.debug("ServiceProAllTabAction.execute()");
		// calling the method to refresh the sidewar status
		refreshResourceStatus();
		
		
		if(resourceId!=null){
			logger.debug("----------------------------Resource Id not null-------");
			
			String result = doLoad();
			if((null!=result) && (result.equals(ERROR))){
				getSession().setAttribute("ErrorMessage", "Not Authorized");				
				return ERROR;
			}
			/**
			 * Implementing header in genderal info page 
			 * setting a resource full name into session variable
			 * implementation starts
			 */
			ResourceDto resourceDTO=new ResourceDto();
			resourceDTO.setResourceId(Long.parseLong(resourceId));
			resourceDTO=iResourceDelegate.getResourceName(resourceDTO);
			ActionContext.getContext().getSession().put("resourceName", resourceDTO.getResourceName());
		}
		// New service live admin feature
		// determine is SLAdmin features are enabled
		determineSLAdminFeature();
		
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Expires", "0");
		return SUCCESS;
	}

	public String doLoad() throws Exception {
		
		logger.debug("resourceID from request= " + resourceId);
		ActionContext.getContext().getSession().put("popUpInd", 0);
		String vendorId = (String)ActionContext.getContext().getSession().get("vendorId");
		ActionContext.getContext().getSession().put("resourceId", resourceId);
		ActionContext.getContext().getSession().put("tabDto", tabDto);
		// New service live admin feature
		// determine is SLAdmin features are enabled
		determineSLAdminFeature();
		
		insertAdminLogging();
		// To check whether the resource is associated with the logged in/adopted user
		// if not, return error.
		Integer loggedInUserCompanyId = getSecurityContext().getCompanyId();
		boolean isAuthorized = iResourceDelegate.isResourceAssociatedWithLoggedUser(resourceId,loggedInUserCompanyId);
		if(!isAuthorized){			
			ActionContext.getContext().getSession().put(ACTION_ERROR, "Not Authorized");
			return ERROR;
		}
		if (resourceId != null && resourceId.length() > 0) {
			Map activityStatus = activityRegistryDelegate
					.getResourceActivityStatus(resourceId);

			if (activityStatus != null) {
				for (Object key : activityStatus.keySet()) {
					boolean value = (Boolean) activityStatus.get(key);
					if (value)
						tabDto.getTabStatus().put((String) key,
								ActivityRegistryConstants.COMPLETE);
					else
						tabDto.getTabStatus().put((String) key,
								ActivityRegistryConstants.INCOMPLETE);
				}
			}
		}
		
		/**
		 * Added to include Provider Status.
		 */
		ActionContext.getContext().getSession().put("providerStatus", activityRegistryDelegate.getProviderStatus(vendorId));
		ActionContext.getContext().getSession().put("resourceStatus", activityRegistryDelegate.getResourceStatus(resourceId));
		
		String status = activityRegistryDelegate.getVendorResourceStateStatus(resourceId);
		ActionContext.getContext().getSession().put("resourceState", status);
		
		if ( status == null || status.trim().length() == 0 || IAuditStates.RESOURCE_INCOMPLETE.equals(status) || IAuditStates.RESOURCE_OUT_OF_COMPLIANCE.equals(status) ) {
			ActionContext.getContext().getSession().put("userStatus", ActivityRegistryConstants.USER_STATUS_NEW);
			logger.info("doLoad() - addUser loop");
		} else {
			ActionContext.getContext().getSession().put("userStatus", ActivityRegistryConstants.USER_STATUS_EDIT);
			logger.info("doLoad() - editUser loop");
		}
		
		/**
		 * Added to get the 'Primary Indicator for the resources'
		 */
		if (resourceId != null && resourceId.trim().length() > 0)
		{
			String primaryInd = activityRegistryDelegate.getResPrimaryIndicator(resourceId);
			ActionContext.getContext().getSession().put("PrimaryIndicator", primaryInd);
			
			/*
			 * Implementing header in genderal info page 
			 * setting a resource full name into session variable
			 * implementation starts
			 */
			ResourceDto resourceDTO=new ResourceDto();
			resourceDTO.setResourceId(Long.parseLong(resourceId));
			resourceDTO=iResourceDelegate.getResourceName(resourceDTO);
			ActionContext.getContext().getSession().put("resourceName", resourceDTO.getResourceName());
			/* implemention ends here*/
			
		}
		
		refreshResourceStatus();
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Expires", "0");
		return SUCCESS;
	}

	
	/**
	 * @return
	 * @throws Exception
	 */
	public String doNew() throws Exception {
		logger.debug("ServiceProAllTabAction.doNew()");
		logger.debug("resourceID from request= " + resourceId);
		String vendorId = (String)ActionContext.getContext().getSession().get("vendorId");
		
		logger.debug("Getting status of resourceId--" + resourceId);
		logger.debug("Getting status of vendorId--" + vendorId);

		ActionContext.getContext().getSession().put("resourceId", resourceId);
		ActionContext.getContext().getSession().put("tabDto", tabDto);
		
		ActionContext.getContext().getSession().put("resourceStatus", activityRegistryDelegate.getResourceStatus(resourceId));
		ActionContext.getContext().getSession().put("userStatus", ActivityRegistryConstants.USER_STATUS_NEW);
		ActionContext.getContext().getSession().put("PrimaryIndicator", null);
		ActionContext.getContext().getSession().put("popUpInd", 0);
		
		refreshResourceStatus();
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Expires", "0");
		return SUCCESS;
	}
	
	/**
	 * @Desciption: We are checking the status of tab status and if all tab are complete then settign the side war indicator as complete 
	 */
	private void refreshResourceStatus() {		
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		
		boolean generalInfo = false;
		boolean backgourndCheck = false;
		boolean credentials = false;
		boolean skills = false;
		boolean termsAndCond = false;
		boolean marketplace = false;
		
		if (baseTabDto!= null) {
			generalInfo = ActivityRegistryConstants.COMPLETE.equals(baseTabDto.getTabStatus().get(ActivityRegistryConstants.RESOURCE_GENERALINFO));
			backgourndCheck = ActivityRegistryConstants.COMPLETE.equals(baseTabDto.getTabStatus().get(ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK));
			credentials = ActivityRegistryConstants.COMPLETE.equals(baseTabDto.getTabStatus().get(ActivityRegistryConstants.RESOURCE_CREDENTIALS));
			skills = ActivityRegistryConstants.COMPLETE.equals(baseTabDto.getTabStatus().get(ActivityRegistryConstants.RESOURCE_SKILLS));
			termsAndCond = ActivityRegistryConstants.COMPLETE.equals(baseTabDto.getTabStatus().get(ActivityRegistryConstants.RESOURCE_TERMSANDCOND));
			marketplace = ActivityRegistryConstants.COMPLETE.equals(baseTabDto.getTabStatus().get(ActivityRegistryConstants.RESOURCE_MARKETPLACE));
		}
		
		if (generalInfo && backgourndCheck && credentials && marketplace && skills && termsAndCond)
			ActionContext.getContext().getSession().put("resourceStatus",ActivityRegistryConstants.COMPLETE);
		else	
			ActionContext.getContext().getSession().put("resourceStatus",ActivityRegistryConstants.INCOMPLETE);		
	}

	
	//Added for Admin Logging
	private void insertAdminLogging(){
		createCommonServiceOrderCriteria();
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute("SecurityContext");
		
		adminLoggingDto = new AdminLoggingDto();
		adminLoggingDto.setUserId(securityContext.getSlAdminUName());
		adminLoggingDto.setCompanyId(get_commonCriteria().getCompanyId());

		adminLoggingDto.setActivityName("Manage Providers");
		AdminLoggingDto admLoggingDto = adminLoggingDelegate.getActivityId(adminLoggingDto);
		if(admLoggingDto != null)
		adminLoggingDto.setActivityId(admLoggingDto.getActivityId());
		
		adminLoggingDto.setRoleId(securityContext.getRoleId());
		adminLoggingDto = adminLoggingDelegate.insertAdminLogging(adminLoggingDto);
		ActionContext.getContext().getSession().put("ProviderLog",adminLoggingDto.getLoggId());
		
	}
	/**
	 * @return the tabView
	 */
	public String getTabView() {
		return tabView;
	}

	/**
	 * @param tabView
	 *            the tabView to set
	 */
	public void setTabView(String tabView) {
		this.tabView = tabView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;

	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;

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
	 * @param response
	 *            the response to set
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
	 * @param nexturl
	 *            the nexturl to set
	 */
	public void setNexturl(String nexturl) {
		this.nexturl = nexturl;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	
	public Map getSSessionMap() {
		return this.sSessionMap;
	}

	public void setSSessionMap(Map sessionMap) {
		this.sSessionMap = sessionMap;
	}

	public AjaxRequestModelDTO getModel() {
		// TODO Auto-generated method stub
		return this.theModel;
	}
	
	public void setModel(AjaxRequestModelDTO theModel) {
		this.theModel = theModel;
	}
}
/*
 * Maintenance History
 * $Log: ServiceProAllTabAction.java,v $
 * Revision 1.25  2008/04/26 01:13:51  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.22.6.1  2008/04/01 22:04:23  mhaye05
 * merged changed from Head into I18_Fin branch
 *
 * Revision 1.23  2008/03/27 18:58:06  mhaye05
 * Merged I18_ADM to Head
 *
 * Revision 1.22.10.7  2008/03/24 17:54:59  dmill03
 * cp
 *
 * Revision 1.22.10.6  2008/03/19 13:19:36  dmill03
 * dmill03 check-point check in
 *
 * Revision 1.22.10.5  2008/03/17 03:54:33  dmill03
 * added ajax based auditing actions
 *
 * Revision 1.22.10.4  2008/03/14 05:32:33  dmill03
 * added audit widget features updates
 *
 * Revision 1.22.10.3  2008/03/14 00:58:40  dmill03
 * update admin apprval widget feature
 *
 * Revision 1.22.10.2  2008/03/12 23:20:08  dmill03
 * updated by dmil03 for audit widgets and ajax support
 *
 * Revision 1.22.10.1  2008/03/11 23:06:29  dmill03
 * updated for auditor functions
 *
 * Revision 1.22  2008/02/26 18:18:07  mhaye05
 * Merged Iteration 17 Branch into Head
 *
 * Revision 1.21.2.1  2008/02/25 19:16:17  mhaye05
 * replaced system out println with logger.debug statements and some general code cleanup
 *
 */