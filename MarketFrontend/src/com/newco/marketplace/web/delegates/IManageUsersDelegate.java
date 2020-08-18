package com.newco.marketplace.web.delegates;

import java.util.List;

import com.newco.marketplace.dto.vo.ActivityVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.permission.PermissionSetVO;
import com.newco.marketplace.dto.vo.permission.UserVO;
import com.newco.marketplace.vo.common.UserProfilePermissionSetVO;
import com.newco.marketplace.web.dto.AdminAddEditUserDTO;
import com.newco.marketplace.web.dto.AdminManageUsersUserDTO;
import com.newco.marketplace.web.dto.BuyerAdminManageUsersAddEditDTO;
import com.newco.marketplace.web.dto.BuyerAdminManageUsersUserDTO;

public interface IManageUsersDelegate {

	public Boolean isUserAvailable(String username);
	public void removeUser(String username, String modifierUserName);
	
	public void adminSaveUser(AdminAddEditUserDTO userDTO, String modifierUserName);
	public void buyerSaveUser(BuyerAdminManageUsersAddEditDTO userDTO, String modifierUserName);
	
	public List<BuyerAdminManageUsersUserDTO> getAllBuyerUsers(Integer buyerId);
	public List<AdminManageUsersUserDTO> getAllAdminUsers();
	
	public AdminAddEditUserDTO getAdminUser(String username);
	public BuyerAdminManageUsersAddEditDTO getBuyerUser(String username);
	public UserVO getBuyerUserWithZip(String username, String resourceId);
	public AdminAddEditUserDTO getProviderUser(String username);
	
	public boolean resetPassword(String userName);
	
	// Lookup items
	public List<LookupVO> getCompanyRoles(Integer roleId);
	/**
	 * @param roleId
	 * @param getInactive
	 * @return
	 */
	public List<PermissionSetVO> getUserPermissionSets(UserProfilePermissionSetVO userProfilePermissionSetVO); 
	
	/**
	 * @return
	 */
	public String getSuperAdminUserName();
	
	/**
	 * 
	 * @param userName
	 */
	public void unlockUser(String userName);
	
	/**
	 * 
	 * @param resourceId
	 * @param roleId
	 * @return
	 */
	public String getUserNameFromResourceId(String resourceId, int roleId);
	
	public boolean resendWelcomeMail(String userName, String email);
	public boolean isUserAssociatedWithLoggedUser(String userName, Integer buyerId);
	
	public List<ActivityVO> getProviderActivityList(String userName);
	
	public void expireMobileTokenforFrontEnd (Integer resourceId,String tokenStatus);
}
