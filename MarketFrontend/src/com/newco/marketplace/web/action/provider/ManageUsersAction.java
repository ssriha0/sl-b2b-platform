package com.newco.marketplace.web.action.provider;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.enums.UserRole;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.IManageUsersDelegate;
import com.newco.marketplace.web.delegates.provider.ITeamMemberDelegate;
import com.newco.marketplace.web.dto.provider.TeamProfileDTO;
import com.newco.marketplace.web.utils.Config;
import com.newco.marketplace.web.utils.SLStringUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;


public class ManageUsersAction extends SLBaseAction implements ServletRequestAware, Preparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5219349790144525876L;
	private ITeamMemberDelegate iTeamMemberDelegate;
	private IManageUsersDelegate manageUsersDelegate;
	private TeamProfileDTO teamProfileDTO;
	//private Map sSessionMap;
	private HttpSession session;
	private HttpServletRequest request;

	private static final Logger logger = Logger.getLogger(ManageUsersAction.class.getName()); 
	private SecurityContext securityContext;
	private String resourceId;
	

	public ManageUsersAction(ITeamMemberDelegate iTeamMemberDelegate, IManageUsersDelegate manageUsersDelegate,
			TeamProfileDTO teamProfileDTO) {
		this.iTeamMemberDelegate = iTeamMemberDelegate;
		this.teamProfileDTO = teamProfileDTO;
		this.manageUsersDelegate = manageUsersDelegate;
	}
	
	public String doLoadUsers() throws Exception {
		return loadUsers();
	}
	
	public String loadUsers() throws Exception {
		try{
			retrieveActionMessage();
			List resourceActKey =null;
			setContextDetails();
			
			teamProfileDTO.setPrimaryInd(securityContext.isPrimaryInd());
			teamProfileDTO.setUserName((String)ActionContext.getContext().getSession().get("username"));

			Integer entityIdInt = null;
			
			Integer roleId = securityContext.getRoleId(); 
			if(roleId == OrderConstants.BUYER_ROLEID)
			{
				//entityIdInt = (Integer)ActionContext.getContext().getSession().get("buyerId");				
			}
			else if(roleId == OrderConstants.PROVIDER_ROLEID)
			{
				String entityId = (String)ActionContext.getContext().getSession().get("vendorId");
				
				if(SLStringUtils.IsParsableNumber(entityId))
				{
					entityIdInt = Integer.parseInt(entityId);
				}
				
				teamProfileDTO.setVendorId(entityIdInt);
				teamProfileDTO.setAdminResourceId(securityContext.getAdminResId());		
				logger.info("vendor ID here is "+teamProfileDTO.getVendorId());	
				resourceActKey = iTeamMemberDelegate.getResourceActivityStatus(teamProfileDTO);
				if(resourceActKey.size()==0)
				{			
					return "addServicePro";
				}
				teamProfileDTO=iTeamMemberDelegate.getTeamMemberList(teamProfileDTO);
				
				//if there is not a team member that is "green"  ( all steps in act reg are = 1 ) then forward to "addUsersAction";
				teamProfileDTO.setAdminResourceId(securityContext.getAdminResId());	
				teamProfileDTO.setLoggedResourceId(securityContext.getVendBuyerResId());
				
			}
			else
			{
				return ERROR;
			}
			
			
	
		}catch(DelegateException ex){
			ex.printStackTrace();
			logger.info("Exception Occured in loadUsers() of ManageUsersAction while processing the request due to"+ex.getMessage());
			addActionError("Exception Occured loadUsers() of ManageUsersAction while processing the request due to"+ex.getMessage());
			return ERROR;
		}
		
		return SUCCESS;
	}
	private void setContextDetails(){
		session = ServletActionContext.getRequest().getSession();
		securityContext = (SecurityContext) session.getAttribute("SecurityContext");
	}
	public SecurityContext getSecurityContext() {
		return securityContext;
	}
//	public void setSession(Map ssessionMap) {
//		// TODO Auto-generated method stub
//		this.sSessionMap=ssessionMap;		
//	}	
//	public Map getSession() {
//		// TODO Auto-generated method stub
//		return this.sSessionMap;		
//	}

	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		request=arg0;
	}
	public void prepare() throws Exception {
		
	}

	public TeamProfileDTO getTeamProfileDTO() {
		return teamProfileDTO;
	}

	public void setTeamProfileDTO(TeamProfileDTO teamProfileDTO) {
		this.teamProfileDTO = teamProfileDTO;
	}
	
	/**
	 * Added by Shekhar to perform Reset Password functionality
	 * @return
	 * @throws Exception
	 */
	public String resetPassword() throws Exception{
		return resetPassword(OrderConstants.PROVIDER_ROLEID);		
	}
	
	public String resetPasswordProvider() throws Exception{
		return resetPassword(OrderConstants.PROVIDER_ROLEID);		
	}
	
	public String resetPasswordBuyer() throws Exception{
		return resetPassword(OrderConstants.BUYER_ROLEID);		
	}
	
	/**
	 * Added by Shekhar to perform Reset Password functionality
	 * @return
	 * @throws Exception
	 */
	public String resetPassword(int roleId) throws Exception{
		
		System.out.println("resourceId:" + resourceId);
		if(resourceId == null)
			return "reset_success";
		String username = manageUsersDelegate.getUserNameFromResourceId(resourceId, roleId);
		boolean flag = manageUsersDelegate.resetPassword(username);
		if (flag) {
			if(roleId==1)
			{
				manageUsersDelegate.expireMobileTokenforFrontEnd(Integer.parseInt(resourceId), MPConstants.ACTIVE);
			}
			
			System.out.println("Password has been reset for user:" + username);
			setActionMessage(Config.getResouceBundle().getString("resetPassword.adminResetPassword.success"));
			return "reset_success";
		} else {
			logger.info("Error in reseting password for user " + username);
			setActionError(Config.getResouceBundle().getString("resetPassword.adminResetPassword.error"));
			return "reset_success";
		}
	}
	
	public String unlockUser() throws Exception{
		
		System.out.println("resourceId:" + resourceId);
		if(resourceId == null)
			return "reset_success";
		String username = manageUsersDelegate.getUserNameFromResourceId(resourceId, UserRole.PROVIDER.getType());
		
		manageUsersDelegate.unlockUser(username);		
		System.out.println("Account has been unlocked for user:" + username);
		return "reset_success";		
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
}