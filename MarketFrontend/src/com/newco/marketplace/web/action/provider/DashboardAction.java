package com.newco.marketplace.web.action.provider;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.provider.IAuditStates;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.web.delegates.adminlogging.IAdminLoggingDelegate;
import com.newco.marketplace.web.delegates.provider.IActivityRegistryDelegate;
import com.newco.marketplace.web.dto.adminlogging.AdminLoggingDto;
import com.newco.marketplace.web.dto.provider.VendorHdrDto;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;


/**
 * @author KSudhanshu
 *
 */
public class DashboardAction extends ActionSupport implements SessionAware, ServletRequestAware, Preparable{

	/**
	 * 
	 */
	private static final Logger logger = Logger.getLogger(DashboardAction.class.getName());
	private static final long serialVersionUID = -7674243058885950323L;
	private IActivityRegistryDelegate activityRegistryDelegate;
	private Map sSessionMap;
	private HttpSession session;
	private HttpServletRequest request;
	private SecurityContext securityContext;
	private AdminLoggingDto adminLoggingDto;
	private IAdminLoggingDelegate adminLoggingDelegate;
	
	/**
	 * @param activityRegistryDelegate
	 */
	public DashboardAction(IActivityRegistryDelegate activityRegistryDelegate,
			IAdminLoggingDelegate adminLoggingDelegate) {
		this.activityRegistryDelegate = activityRegistryDelegate;
		this.adminLoggingDelegate = adminLoggingDelegate;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() throws Exception {
		setContextDetails();
		
		String resourceId = securityContext.getVendBuyerResId().toString();
		String vendorId = securityContext.getCompanyId().toString();
		ActionContext.getContext().getSession().put("resourceId",resourceId);
		
				
		ActionContext.getContext().getSession().put("providerStatus", activityRegistryDelegate.getProviderStatus(vendorId));
		ActionContext.getContext().getSession().put("resourceStatus", activityRegistryDelegate.getResourceStatus(resourceId));
		
		String status = activityRegistryDelegate.getVendorResourceStateStatus(resourceId);
		
		if ( status == null || status.trim().length() == 0 || IAuditStates.RESOURCE_INCOMPLETE.equals(status) || IAuditStates.RESOURCE_OUT_OF_COMPLIANCE.equals(status) ) {
			ActionContext.getContext().getSession().put("userStatus", ActivityRegistryConstants.USER_STATUS_NEW);
			logger.info("execute() - addUser loop");
		} else {
			ActionContext.getContext().getSession().put("userStatus", ActivityRegistryConstants.USER_STATUS_EDIT);
			logger.info("execute() - editUser loop");
		}
		
		/**
		 * Added to get the 'Primary Indicator for the resources'
		 */
		if (resourceId != null && resourceId.trim().length() > 0)
		{
			String primaryInd = activityRegistryDelegate.getResPrimaryIndicator(resourceId);
			ActionContext.getContext().getSession().put("PrimaryIndicator", primaryInd);
		}
		
		return SUCCESS;
	}
	
	
	public String doSubmitRegistration() throws Exception{
		
		setContextDetails();
		
		String vendorId = securityContext.getCompanyId().toString();
		String vendorStatus = null;
		Integer resourceId=-1;
		List resourceActKey = null;
		
		VendorHdrDto vendorHdrDto = new VendorHdrDto();
		vendorHdrDto.setVendorId(Integer.parseInt(vendorId));
		String sResourceId = (String)ActionContext.getContext().getSession().get("resourceId");
		boolean bStatus = false;
		if(sResourceId != null)
		{
			resourceId = Integer.parseInt(sResourceId.trim());
		}
		
		// Setting session dto and resourceId as null
		ActionContext.getContext().getSession().put("tabDto", null);
		String userStatus =(String) ActionContext.getContext().getSession().get("userStatus");
		if(userStatus!=null && userStatus.equals(ActivityRegistryConstants.USER_STATUS_EDIT))
		{
			bStatus=true;
		}
		
		resourceActKey = activityRegistryDelegate.checkActivityStatus(Integer.parseInt(vendorId));
		vendorStatus = activityRegistryDelegate.getVendorHeaderStatus(vendorId);
		String status = activityRegistryDelegate.getVendorResourceStateStatus(resourceId.toString());
		
		//Gets the Provider Admin's User Name
		String provUserName = securityContext.getUsername();
		
		if ( status == null || status.trim().length() == 0 || IAuditStates.RESOURCE_INCOMPLETE.equals(status) || IAuditStates.RESOURCE_OUT_OF_COMPLIANCE.equals(status) ) {
				activityRegistryDelegate.submitRegistration(vendorHdrDto, resourceId, provUserName);
			ActionContext.getContext().getSession().put("userStatus", ActivityRegistryConstants.USER_STATUS_NEW);
			logger.info("doSubmitRegistration() - addUser loop");
		} else {
			ActionContext.getContext().getSession().put("userStatus", ActivityRegistryConstants.USER_STATUS_EDIT);
			logger.info("doSubmitRegistration() - editUser loop");
			
		}
		/////////update Admin Logging
//		session = ServletActionContext.getRequest().getSession();
//		securityContext = (SecurityContext) session.getAttribute("SecurityContext");
		if(securityContext.isSlAdminInd())
		updateAdminLogging();
		if(resourceActKey != null && resourceActKey.size()==1 )
		{
			if(bStatus)
			{
				return "pendingapproval";
			}
			else
			{
				//Sets the Registration Complete status in Security Context to 'true'
				securityContext.setRegComplete(true);
				session.setAttribute("SecurityContext", securityContext);
				
				return "congrats";
			}	
		}
		
		
		if(vendorStatus != null && vendorStatus.equals(IAuditStates.VENDOR_PENDING_APPROVAL))
		{
			
			return "pendingapproval";
		}
				
		
		return "pendingapproval";
	}
	
	/**
	 * GGanapathy
	 * **############################## Added for Admin Logging -starts#################################**
	 */
	public String updateAdminLogging(){
		adminLoggingDto = new AdminLoggingDto();
		adminLoggingDto.setLoggId((Integer)ActionContext.getContext().getSession().get("ProviderLog"));
		adminLoggingDto = adminLoggingDelegate.updateAdminLogging(adminLoggingDto);
		return SUCCESS;
	}
	/**############################## Added for Admin Logging -ends#################################**/
	private void setContextDetails(){
		session = ServletActionContext.getRequest().getSession();
		securityContext = (SecurityContext) session.getAttribute("SecurityContext");
	}
	public void setSession(Map ssessionMap) {
		// TODO Auto-generated method stub
		this.sSessionMap=ssessionMap;		
	}	
	public Map getSession() {
		// TODO Auto-generated method stub
		return this.sSessionMap;		
	}

	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		request=arg0;
	}
	public void prepare() throws Exception {
		
	}
}