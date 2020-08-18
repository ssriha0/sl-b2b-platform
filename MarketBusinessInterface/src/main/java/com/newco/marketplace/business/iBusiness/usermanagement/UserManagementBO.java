package com.newco.marketplace.business.iBusiness.usermanagement;

import java.util.List;

import com.newco.marketplace.dto.vo.ActivityVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.permission.PermissionSetVO;
import com.newco.marketplace.dto.vo.permission.UserVO;
import com.newco.marketplace.vo.common.UserProfilePermissionSetVO;

public interface UserManagementBO {
	
	/**
	 * @param roleId
	 * @param getInactive
	 * @return
	 */
	public List<PermissionSetVO> getUserPermissionSets(UserProfilePermissionSetVO userProfilePermissionSetVO);
	
	public List<UserVO> getSLAdminUserList();
	
	public List<UserVO> getBuyerUserList(Integer buyerId);
	
	public UserVO getUser(String username, Integer roleId);
	
	/**
	 * 
	 * @param user - the user object to modify/save
	 * @param modifierUserName - user who's invoking the modify/save
	 * @return
	 */
	public Boolean saveUser(UserVO user, String modifierUserName);
	
	/**
	 * 
	 * @param username - the username to remove
	 * @param modifierUserName - user who's invoking the remove
	 * @return
	 */
	public Boolean removeUser(String username, String modifierUserName);
	
	public Boolean isUserAvailable(String username);
	
	public List<LookupVO> getCompanyRoles(Integer roleId);
	
	/**
	 * @return
	 */
	public String getSuperAdminUserName ();
	
	/**
	 * 
	 * @param userName
	 */
	public void unlockUser(String userName);
	
	public boolean resendWelcomeMail(String userName, String email);
	public boolean isUserAssociatedWithLoggedUser(String userName, Integer buyerId);
	public void optOutSMS(String smsNo,String firstName,String lastName);
	
	public List<ActivityVO> getProviderActivityList(String userName);
	public void expireMobileTokenforFrontEnd (Integer resourceId,String tokenStatus);
	
	
}
