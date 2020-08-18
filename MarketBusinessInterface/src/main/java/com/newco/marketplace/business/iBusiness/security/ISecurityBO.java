package com.newco.marketplace.business.iBusiness.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.auth.ActionActivityVO;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.auth.UserActivityVO;
import com.newco.marketplace.dto.vo.AccessControlList;
import com.newco.marketplace.dto.vo.ActivityTemplate;
import com.newco.marketplace.dto.vo.ActivityVO;
import com.newco.marketplace.dto.vo.UserActivityPermissionVO;
import com.newco.marketplace.dto.vo.account.AccountProfile;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.provider.Location;

public interface ISecurityBO {
	public AccessControlList getActivitiesByUserName(String username);
	public void assignActivitiesToUser(String username, ArrayList<ActivityVO> activityList);
	public void removeActivitiesFromUser(String username, ArrayList<ActivityVO> activityList);
	public void removeActivitiesFromUser(ArrayList<UserActivityPermissionVO> userActivityPermissionVOs);
	public ActivityTemplate getActivitiesForUserGroup(int roleId);
	public void assignActivitiesToUserGroup(int roleId, ArrayList<ActivityVO> activityList);
	public void removeActivitiesFromUserGroup(int roleId, ArrayList<ActivityVO> activityList);
	public AccountProfile getAccountProfile(String username) throws BusinessServiceException;
	public boolean authenticate(String username, String password) throws BusinessServiceException;
	public boolean authenticate(LoginCredentialVO lc)	throws BusinessServiceException;
	public List<ActionActivityVO> getActionActivities() ;
	public Map<String,Long> getBuyerId(String userName);
	public Map<String,Long> getVendorId(String userName);
	public LoginCredentialVO getUserRoles(LoginCredentialVO lvo); 
	public SurveyRatingsVO getLifetimeRatings(SecurityContext securityCtx) throws BusinessServiceException;	
	public Location getProviderLocation(String userName)throws DataServiceException;
	/**
	 * @param actionName
	 * @param userName
	 * @param roleId
	 * @return boolean if the given user name has permission to do given action
	 */
	public boolean hasPermissionForAction(String actionName, String userName, String roleId);
	public String getBuyerAdminUserName(Integer buyerId);
	public String getVendorAdminName(Integer vendorId);
	public String getBuyerUserName(Integer resourceId);
	public String getVendorUserName(Integer resourceId);
	public String getOAuthConsumerSecret(String consumerKey);	
	public boolean verifyEntity(Integer id, String type);
	
	//SL-15642 check for firm id in order_management_permission
	public Integer getOmPermissionForFirm(Integer vendorId);
	public List<UserActivityVO> getUserActivityRolesList(String userName);
	public boolean simpleAuthenticate(String username, String password)  throws BusinessServiceException;
}
