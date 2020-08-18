package com.newco.marketplace.web.action.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.api.IAPISecurity;
import com.newco.marketplace.dto.api.APIApplicationDTO;
import com.newco.marketplace.dto.api.APIDto;
import com.newco.marketplace.dto.api.APIGroup;
import com.newco.marketplace.dto.api.APIUrlPermission;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.security.AdminPageAction;
import com.opensymphony.xwork2.Preparable;

/**
 * @Author: Shekhar Nirkhe
 */
@AdminPageAction
public class AdminManageAPIAction extends SLBaseAction implements Preparable {

	private static final long serialVersionUID = -8816707461562441761L;
	private final String SHOW_EDIT_PAGE = "showEditPage";
	private final String SHOW_MANAGE_USER = "showManageUser";
	private String loggedUserName = "";
	private IAPISecurity apiSecurity;

	public AdminManageAPIAction(IAPISecurity apiSecurity) {
		this.apiSecurity = apiSecurity;
	}
	
	
	public void prepare() throws Exception {
		//createCommonServiceOrderCriteria();	
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute("SecurityContext");
		if (securityContext != null) {
			loggedUserName = securityContext.getUsername();
		}
	}	


	public String execute() throws Exception {
		List<APIApplicationDTO> appList = apiSecurity.getApplicationList();
		setAttribute("list", appList);

		SecurityContext securityContext = (SecurityContext) getSession().getAttribute("SecurityContext");
		if (securityContext != null) {
			boolean passwordResetForSLAdmin = securityContext.getRoles().isPasswordResetForSLAdmin();
			//boolean passwordResetForAllExternalUsers = securityContext.getRoles().isPasswordResetForAllExternalUsers();

			if (passwordResetForSLAdmin == true)
				setAttribute("passwordResetForSLAdmin", "true");
			//if (passwordResetForAllExternalUsers == true)
			//	setAttribute("passwordResetForAllExternalUsers", "true");
		}

		retrieveActionMessage();
		return SUCCESS;
	}

	public String displayEditPage() throws Exception {
		String consumerKey = (String)getParameter("consumerKey");
		APIDto apiDto = apiSecurity.getAPIList();
		if (apiDto != null) {
			List<APIGroup> apiDtoList = apiDto.getGroupList();
			APIApplicationDTO consumerDto = apiSecurity.getApplicationNoCache(consumerKey);			
			getSession().setAttribute("APIApplicationDTO", consumerDto);
			apiDtoList = prepareAppPermissionSet(apiDtoList, consumerDto.getApiGroupList());
			getSession().setAttribute("apiGroupList", apiDtoList);
		}
		return SHOW_EDIT_PAGE;
	}
	
	public String displayManageUserPage() throws Exception {
		String consumerKey = (String)getParameter("consumerKey");
		List<Integer> userList = apiSecurity.getAPIUserList(consumerKey);
		if (userList != null) {						
			setAttribute("userList", userList);
			setAttribute("consumerKey", consumerKey);
		}
		return SHOW_MANAGE_USER;
	}

	
	public String resetPassword() throws Exception {
		String consumerKey = (String)getParameter("consumerKey");
		String consumerName = (String)getParameter("consumerName");
		try {
			apiSecurity.resetPassword(consumerKey, loggedUserName);
			String msg = "Password has been successfully reset for " + consumerName;
			addActionMessage(msg);
		} catch (BusinessServiceException e) {
			addActionError(e.getMessage());
		}
		return execute();
	}

	
	public String removeApplication() throws Exception {
		String name = (String)getParameter("consumerName");
		String consumerKey = (String)getParameter("consumerKey");
		try {
			apiSecurity.removeApplication(consumerKey, loggedUserName);
			String msg = "Application \'" + name + "\' has been successfully removed.";
			addActionMessage(msg);
		} catch (BusinessServiceException e) {
			addActionError(e.getMessage());
		}
		return execute();
	}
	
	public String addApplication() throws Exception {
		String name = (String)getParameter("name");
		try {
			apiSecurity.addApplication(name, loggedUserName);
			String msg = "Application \'" + name + "\' has been successfully added.";
			addActionMessage(msg);
		} catch (BusinessServiceException e) {
			addActionError(e.getMessage());
		}
		return execute();
	}
	
	public String modifyPermissions() throws Exception {
		String args[] = ServletActionContext.getRequest().getParameterValues("permission");
		if (args == null) {
			args = new String[0];
		}
		//List<String> urlList = new ArrayList<String>();		
		List<Integer> deniedUrlIds = new ArrayList<Integer>();
		List<Integer> allowdUrlIds = new ArrayList<Integer>();
		
		APIApplicationDTO consumerDto = (APIApplicationDTO)getSession().getAttribute("APIApplicationDTO");	
		getSession().setAttribute("APIApplicationDTO", consumerDto);

		APIDto apiDto = apiSecurity.getAPIList();

		for (String permision:args) {
			System.out.print(permision);
			String aa[] = permision.split("-:-");
			if (aa != null && aa.length >1){
				String url = APIApplicationDTO.getURLKey(aa[0], aa[1]);
				Integer id = apiDto.getMapping(url);
				if (id != null)
					allowdUrlIds.add(id);				
			}
		}		

		//do not check for size here. Even if size is 0 existing permissions needs to be removed

		for (Integer apiId: consumerDto.getApiIdMap().keySet()) { // api_id not id of permission table	
			if (apiId != null && allowdUrlIds.contains(apiId) == false) {
				deniedUrlIds.add(apiId);				
			}				
		}			
		apiSecurity.modifyPermissions(consumerDto.getConsumerKey(), allowdUrlIds, deniedUrlIds, loggedUserName);			
		String msg = "Application permissions has been successfully updated.";
		addActionMessage(msg);
		return execute();
	}
	
	private List<APIGroup>  prepareAppPermissionSet(List<APIGroup> apiDtoList, List<APIGroup> userPermissions) {
		for (APIGroup dto:apiDtoList) {
				APIGroup obj = null;
				int idx = userPermissions.indexOf(dto);
				if (idx >= 0) {
					obj = userPermissions.get(idx);

				}
				if (obj == null) continue;

				for (Integer id:dto.getPermissionsMap().keySet()) {
					APIUrlPermission templatePermission = dto.getPermissionsMap().get(id);
					Map<Integer, APIUrlPermission> pMap = obj.getPermissionsMap();
					APIUrlPermission appPermission = null;
					if (pMap != null) {
						appPermission = pMap.get(id);
					}

					if (appPermission != null) {
						templatePermission.setGet(appPermission.getGet());
						templatePermission.setPut(appPermission.getPut());
						templatePermission.setPost(appPermission.getPost());
						templatePermission.setDelete(appPermission.getDelete());
					}
				}
		}
		return apiDtoList;
	}

	public IAPISecurity getApiSecurity() {
		return apiSecurity;
	}

	public void setApiSecurity(IAPISecurity apiSecurity) {
		this.apiSecurity = apiSecurity;
	}
	
	public String removeUser() throws Exception {
		String consumerKey = (String)getParameter("consumerKey");
		String idStr = (String)getParameter("userId");

		if (idStr != null) {
			try{
				Integer id = Integer.parseInt(idStr);
				apiSecurity.removeAPIUser(consumerKey, id, loggedUserName);
				String msg = "User \'" + id + "\' has been successfully removed.";
				addActionMessage(msg);
			}catch (NumberFormatException e){
				e.printStackTrace();
				addActionError("Invalid UserId");
			}
		}
		return displayManageUserPage();
	}
	
	public String addUser() throws Exception {
		String consumerKey = (String)getParameter("consumerKey");
		String idStr = (String)getParameter("userId");

		if (idStr != null) {
			try{
				Integer id = Integer.parseInt(idStr);
				apiSecurity.addAPIUser(consumerKey, id, loggedUserName);
				String msg = "User \'" + id + "\' has been successfully added.";
				addActionMessage(msg);
			} catch (NumberFormatException e){
				e.printStackTrace();
				addActionError("Invalid UserId");
			} catch (BusinessServiceException e) {
				addActionError(e.getMessage());
			}
		}
		return displayManageUserPage();
	}
}
