package com.newco.marketplace.persistence.iDao.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.auth.ActionActivityVO;
import com.newco.marketplace.auth.UserActivityVO;
import com.newco.marketplace.dto.vo.ActivityVO;
import com.newco.marketplace.dto.vo.RoleActivityPermissionVO;
import com.newco.marketplace.dto.vo.UserActivityPermissionVO;
import com.newco.marketplace.dto.vo.account.AccountProfile;
import com.newco.marketplace.dto.vo.buyer.BuyerUserProfile;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.api.APISecurityVO;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.vo.provider.Location;
import com.newco.marketplace.vo.provider.UserProfile;
import com.newco.ofac.vo.BuyerOfacVO;
import com.newco.ofac.vo.ContactOfacVO;
import com.newco.ofac.vo.ProviderOfacVO;

public interface SecurityDAO {

	public String getPassword(LoginCredentialVO lc) throws DataServiceException;


	public boolean getRegComplete(int vendorId)throws DataServiceException;

	public String getPassword(String username) throws DataServiceException;

	public String getSecretQuestion(String username)
			throws DataServiceException;

	public String getSecretAnswer(String username) throws DataServiceException;

	public LoginCredentialVO getRoles(String userName)
			throws DataServiceException;

	public Location getProviderLocation(String username) throws DataServiceException;
	public Map<String,Long> getBuyerId(String userName) throws DataServiceException;

	public Map<String,Long> getVendorId(String userName) throws DataServiceException;

	public ArrayList<ActivityVO> getActivitiesByUserName(String username);

	public void assignActivitiesToUser(
			ArrayList<UserActivityPermissionVO> userActivityPermissions);

	public ArrayList<ActivityVO> getActivitiesForUserGroup(int roleId);

	public void assignActivitiesToUserGroup(
			ArrayList<RoleActivityPermissionVO> roleActivityPermissions);

	public void removeActivitiesFromUser(
			ArrayList<UserActivityPermissionVO> userActivityPermissions);

	public void removeActivitiesFromUserGroup(
			ArrayList<RoleActivityPermissionVO> roleActivityPermissions);

	public AccountProfile getAccountProfile(String username)
			throws DataServiceException;

	public void insertAdminProfile(UserProfile aUserProfile)throws DataServiceException;

	public void insertBuyerAdminProfile(BuyerUserProfile aUserProfile)throws DataServiceException;

	public List getRoleActivityIdList(String userName) throws DataServiceException;

	public HashMap getProviderIndicators(Integer resourceId) throws DataServiceException;
	public String getAdminUserName(Integer vendor_id) throws DataServiceException;
	public List<UserActivityVO> getUserActivityRolesList(String userName) throws DataServiceException;
	public List<ActionActivityVO> getActionActivities() throws DataServiceException;

	public BuyerOfacVO getBuyerOfacIndicators(int resourceId) throws DataServiceException;
	public boolean checkForBuyerAdmin(String userName) throws DataServiceException;
	public void updateBuyerOfacDbFlag(BuyerOfacVO buyerOfacVO)throws DataServiceException;

	public ProviderOfacVO getProviderOfacIndicators(int resourceId) throws DataServiceException;
	public void updateProviderOfacDbFlag(ProviderOfacVO providerOfacVO)throws DataServiceException;

	public ContactOfacVO getBuyerContactIdEin(Integer resourceID)throws DataServiceException;
	public ContactOfacVO getProviderContactIdEin(Integer resourceID)throws DataServiceException;

	public void addAdminDOBForOfac(ContactOfacVO contact)throws DataServiceException;

	public void addBuyerTaxID(ContactOfacVO contact)throws DataServiceException;

	public void addProviderTaxID(ContactOfacVO contact)throws DataServiceException;
	public Boolean hasResourceAdminPermission(Integer resourceId)throws DataServiceException;
	
	/**
	 * returns count if the given user name has permission to do given action
	 */
	public Integer hasPermissionForAction(String actionName, String userName, String roleId) throws DataServiceException;
	public String getVendorAdminName(Integer vendorResourceId) throws DataServiceException;
	public String getBuyerUserName(Integer buyerResourceId) throws DataServiceException;
	public String getBuyerAdminUserName(Integer buyerId) throws DataServiceException;
	public String getVendorUserName(Integer vendorResourceId) throws DataServiceException;
	public String getOAuthConsumerSecret(String consumerKey) throws DataServiceException;
	public Map<String,Long> verifyEntity(Integer entityId, String entityType) throws DataServiceException;

	//API Security methods
	public boolean isUserExisting(int userId, String consumerKey, int internalConsumerFlag) throws DataServiceException;
	public List<APISecurityVO> getAPIListForApplication(String consumerKey) throws DataServiceException;
	public List<String> getAPISecurityFieldRules(String consumerKey) throws DataServiceException;
	//SL-15642 check for firm id in order_management_permission
	public Integer getOmPermissionForFirm(Integer vendorId);
		
	String getFeatureAvailable(int buyerId) throws DataServiceException;
}// SecurityDAO

