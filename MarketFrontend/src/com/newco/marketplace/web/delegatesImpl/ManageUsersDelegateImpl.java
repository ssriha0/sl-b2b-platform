package com.newco.marketplace.web.delegatesImpl;

import java.util.List;

import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.business.iBusiness.provider.IForgotUsernameBO;
import com.newco.marketplace.business.iBusiness.usermanagement.UserManagementBO;
import com.newco.marketplace.dto.vo.ActivityVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.permission.PermissionSetVO;
import com.newco.marketplace.dto.vo.permission.UserVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.common.UserProfilePermissionSetVO;
import com.newco.marketplace.vo.provider.LostUsernameVO;
import com.newco.marketplace.web.delegates.IManageUsersDelegate;
import com.newco.marketplace.web.dto.AdminAddEditUserDTO;
import com.newco.marketplace.web.dto.AdminManageUsersUserDTO;
import com.newco.marketplace.web.dto.BuyerAdminManageUsersAddEditDTO;
import com.newco.marketplace.web.dto.BuyerAdminManageUsersUserDTO;
import com.newco.marketplace.web.utils.ManageUsersMapper;

public class ManageUsersDelegateImpl implements IManageUsersDelegate {

	private UserManagementBO userManagementBO;
	private IForgotUsernameBO forgotUserNameBO;

	private ILookupBO lookupBO;

	private ManageUsersDelegateImpl(UserManagementBO userManagementBO,
			ILookupBO lookupBO, IForgotUsernameBO forgotUserNameBO) {
		this.userManagementBO = userManagementBO;
		this.lookupBO = lookupBO;
		this.forgotUserNameBO = forgotUserNameBO;
	}

	public void removeUser(String username, String modifierUserName) {
		userManagementBO.removeUser(username, modifierUserName);
	}

	public void adminSaveUser(AdminAddEditUserDTO userDTO,
			String modifierUserName) {
		UserVO vo;
		vo = ManageUsersMapper.convertAdminUserDTOToUserVO(userDTO);
		userManagementBO.saveUser(vo, modifierUserName);

	}

	public void buyerSaveUser(BuyerAdminManageUsersAddEditDTO userDTO,
			String modifierUserName) {
		UserVO vo = new UserVO();
		vo = ManageUsersMapper.convertBuyerUserDTOToUserVO(userDTO);
		if (vo != null) {
			userManagementBO.saveUser(vo, modifierUserName);
		}
	}

	public AdminAddEditUserDTO getAdminUser(String username) {

		AdminAddEditUserDTO dto;
		UserVO userVO = userManagementBO.getUser(username,
				OrderConstants.NEWCO_ADMIN_ROLEID);
		dto = ManageUsersMapper.convertUserVOToUserDTO(userVO);

		return dto;
	}

	public AdminAddEditUserDTO getProviderUser(String username) {

		AdminAddEditUserDTO dto;
		UserVO userVO = userManagementBO.getUser(username,
				OrderConstants.PROVIDER_ROLEID);
		dto = ManageUsersMapper.convertUserVOToUserDTO(userVO);

		return dto;
	}

	public BuyerAdminManageUsersAddEditDTO getBuyerUser(String username) {
		BuyerAdminManageUsersAddEditDTO dto;
		UserVO userVO = userManagementBO.getUser(username,
				OrderConstants.BUYER_ROLEID);
		dto = ManageUsersMapper.convertUserVOToBuyerUserDTO(userVO);
		return dto;
	}

	/*
	 * For child account of buyer (in case of flag 'check to show buyer agent'),
	 * gather display data for reset password modal box. This is only used for
	 * reset password modal box.
	 */
	public UserVO getBuyerUserWithZip(String username, String resourceId) {
		UserVO userVO = null;
		try {
			userVO = userManagementBO.getUser(username,
					OrderConstants.BUYER_ROLEID);
			userVO.setZip(forgotUserNameBO.loadLostUsername(username,
					resourceId, OrderConstants.BUYER_ROLEID).getZip());
		} catch (BusinessServiceException ex) {

		}
		return userVO;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.delegates.IManageUsersDelegate#
	 * getUserPermissionSets(java.lang.Integer, boolean)
	 */
	public List<PermissionSetVO> getUserPermissionSets(
			UserProfilePermissionSetVO userProfilePermissionSetVO) {
		return userManagementBO
				.getUserPermissionSets(userProfilePermissionSetVO);
	}

	public List<BuyerAdminManageUsersUserDTO> getAllBuyerUsers(Integer buyerId) {

		List<BuyerAdminManageUsersUserDTO> usersList;
		usersList = ManageUsersMapper
				.convertUserListToBuyerList(userManagementBO
						.getBuyerUserList(buyerId));

		return usersList;
	}

	public List<AdminManageUsersUserDTO> getAllAdminUsers() {
		List<AdminManageUsersUserDTO> usersList;
		List<UserVO> voList = userManagementBO.getSLAdminUserList();
		usersList = ManageUsersMapper.convertUserListToAdminList(voList);

		return usersList;
	}

	public List<LookupVO> getCompanyRoles(Integer roleId) {
		if (roleId == null)
			return null;

		try {
			return lookupBO.getCompanyRoles(roleId);
		} catch (Exception e) {
		}

		return null;
	}

	public Boolean isUserAvailable(String username) {
		return userManagementBO.isUserAvailable(username);
	}

	public String getSuperAdminUserName() {
		return userManagementBO.getSuperAdminUserName();
	}

	/**
	 * Added by Shekhar.This functionality is required by Admin to reset the
	 * password.
	 */
	public boolean resetPassword(String userName) {

		LostUsernameVO lostUsernameVO = new LostUsernameVO();
		lostUsernameVO.setUserName(userName);
		try {
			return forgotUserNameBO.resetPassword(lostUsernameVO);
		} catch (BusinessServiceException be) {
			return false;
		}
	}

	/**
	 * Added by Shekhar.This functionality is required by Admin to reset the
	 * password.
	 */
	public void unlockUser(String userName) {
		userManagementBO.unlockUser(userName);
	}

	public String getUserNameFromResourceId(String resourceId, int roleId) {
		try {
			return forgotUserNameBO.getUserNameFromResourceId(resourceId,
					roleId);
		} catch (BusinessServiceException be) {
			return null;
		}
	}

	public boolean resendWelcomeMail(String userName, String email) {
		return userManagementBO.resendWelcomeMail(userName, email);
	}

	public boolean isUserAssociatedWithLoggedUser(String userName,
			Integer buyerId) {
		return userManagementBO.isUserAssociatedWithLoggedUser(userName,
				buyerId);
	}

	public List<ActivityVO> getProviderActivityList(String userName) {

		try {

			return userManagementBO.getProviderActivityList(userName);
		} catch (Exception e) {
			return null;
		}
	}
	
	public void expireMobileTokenforFrontEnd (Integer resourceId,String tokenStatus)
	{
		try {
			
			userManagementBO.expireMobileTokenforFrontEnd(resourceId,tokenStatus);
		} catch (Exception e) {
			
		}
	}
}
