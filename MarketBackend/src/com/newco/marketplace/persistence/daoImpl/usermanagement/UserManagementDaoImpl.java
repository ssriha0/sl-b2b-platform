package com.newco.marketplace.persistence.daoImpl.usermanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import com.newco.marketplace.mobile.authenticate.vo.MobileTokenVO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.ActivityVO;
import com.newco.marketplace.dto.vo.logging.UserProfileLoggingVO;
import com.newco.marketplace.dto.vo.permission.PermissionSetVO;
import com.newco.marketplace.dto.vo.permission.UserRoleVO;
import com.newco.marketplace.dto.vo.permission.UserVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.usermanagement.UserManagementDao;
import com.newco.marketplace.vo.common.UserProfilePermissionSetVO;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * $Revision: 1.7 $ $Author: glacy $ $Date: 2008/06/02 19:39:48 $
 */
public class UserManagementDaoImpl extends ABaseImplDao implements UserManagementDao{

	private static final Logger logger = Logger.getLogger(UserManagementDaoImpl.class.getName());

	public boolean addUpdateUser(UserVO user) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.usermanagement.UserManagementDao#getPermissionSets(java.lang.Integer, booelan)
	 */
	
	public List<ActivityVO> getActivitySet(Integer roleId, Integer entityId , Integer setId) {
		List<ActivityVO> newList;
		Map<String, Integer> parameters = new HashMap<String, Integer>();
		parameters.put("roleId", roleId);
		parameters.put("entityId", entityId); // this is meant to be vendor_id / buyer_id
		parameters.put("permissionSetId", setId);
		
		newList = queryForList("permissionSetActivities.query", parameters);
		
		return newList;
		
	}
	public Boolean isSuperAdminUser(String username)throws DataAccessException {

		Integer count = (Integer) queryForObject("isSuperAdminUser.query", username);
		if(count == 1){
			return true;
		}
		return false;
		}	
	public List<PermissionSetVO> getPermissionSets(UserProfilePermissionSetVO userProfilePermissionSetVO) {
		List<PermissionSetVO> list = null;
		Map<String, Integer> parameters = new HashMap<String, Integer>();
		Integer adminResourceId = 0;
		String adminUserName = "";
		boolean adminFlag = false;
		Integer legelHoldRoleActivityId = 0;
		String Legal_Hold_Disable_Wallet_Funds_Withdrawals = OrderConstants.Legal_Hold_Disable_Wallet_Funds_Withdrawals;
		boolean isSuperAdminUser = isSuperAdminUser(userProfilePermissionSetVO.getUserName());
		parameters.put("roleId", userProfilePermissionSetVO.getRoleId());
		parameters.put("entityId", userProfilePermissionSetVO.getEntityId()); // this is meant to be vendor_id / buyer_id
		
		
		list = queryForList("permissionSet.query", parameters);
	
			for (PermissionSetVO psVO : list) {
				List<ActivityVO> setList  = getActivitySet(userProfilePermissionSetVO.getRoleId(), userProfilePermissionSetVO.getEntityId(), psVO.getPermissionSetId());
				psVO.setActivities(setList);
				if (!userProfilePermissionSetVO.isGetInactive()) {
					List<ActivityVO> newList = new ArrayList<ActivityVO>();
					for (ActivityVO activity :psVO.getActivities()) {
						if(activity.getRemoveFlag() != null && activity.getRemoveFlag().equals(new Integer(1))){
							activity.setInactiveInd(new Integer(1));
						}
						if(activity.getAddFlag() != null && activity.getAddFlag().equals(new Integer(1))){
							activity.setInactiveInd(new Integer(0));
						}
						if (activity.getInactiveInd().intValue() == 0) {
							newList.add(activity);
						}
					}					
					
					psVO.setActivities(newList);
				}
				
				
			}
			//If roleId is a provider, check in vendor_hdr table if its a admin
			if(userProfilePermissionSetVO.getRoleId().intValue()==OrderConstants.PROVIDER_ROLEID)
			{
				adminResourceId = (Integer) queryForObject("vendorAdmin.select",userProfilePermissionSetVO.getEntityId());
				if(adminResourceId==userProfilePermissionSetVO.getVendBuyerResId())
					adminFlag = true;
			}
			else if(userProfilePermissionSetVO.getRoleId().intValue()==OrderConstants.BUYER_ROLEID || userProfilePermissionSetVO.getRoleId().intValue()==OrderConstants.SIMPLE_BUYER_ROLEID)
			{
				adminUserName = (String)queryForObject("buyerAdmin.select",userProfilePermissionSetVO.getEntityId());
				if(adminUserName.equalsIgnoreCase(userProfilePermissionSetVO.getUserName()))
					adminFlag = true;
			}
			else
			{
				Integer returnVal = (Integer) queryForObject("isSLAdmin.select",userProfilePermissionSetVO.getUserName());
				if(returnVal!=null && returnVal > 0)
					adminFlag = true;
			}
		// remove any permission sets that have no activities
			List<PermissionSetVO> finalList = new ArrayList<PermissionSetVO>();
			for (PermissionSetVO psvo : list) {
				if (null != psvo.getActivities() && psvo.getActivities().size() > 0) {					
					if(!adminFlag && psvo.getAdminInd()>0)
						continue;
					finalList.add(psvo);
				}				
			}
			
		legelHoldRoleActivityId = (Integer)queryForObject("activityIdLegalHold.select",Legal_Hold_Disable_Wallet_Funds_Withdrawals); 
			
		if (!isSuperAdminUser) {
			logger.info("Removing legal hold activity from newco permission set as login user is not super admin.");
			for (PermissionSetVO permissionSetVO : finalList) {
				if (permissionSetVO.getPermissionSetId() == OrderConstants.Admin_Only_Actions_Newco) {
					List<ActivityVO> activitiesList = new CopyOnWriteArrayList(permissionSetVO.getActivities());
					for (ActivityVO activities : activitiesList) {
						if (activities.getActivityId() == legelHoldRoleActivityId) {
							permissionSetVO.getActivities().remove(activities);
						}
					}
				}
			}
		}
			return finalList;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.usermanagement.UserManagementDao#getUser(java.lang.String, java.lang.Integer)
	 */
	public UserVO getUser(String userName, Integer roleId) throws DataAccessException{
		UserVO vo = null;
		
		if (roleId.intValue() == -1) {
            roleId = (Integer)queryForObject("userManagement.rold_id", userName);
		}

		if (roleId.intValue() == OrderConstants.BUYER_ROLEID) {
			vo = (UserVO)queryForObject("userManagement_buyerResource.query", userName);
		} else if (roleId.intValue() == OrderConstants.NEWCO_ADMIN_ROLEID) {
			vo = (UserVO)queryForObject("serviceLiveAdmin.query", userName);
		} else if (roleId.intValue() == OrderConstants.PROVIDER_ROLEID) {
			vo = (UserVO)queryForObject("serviceLiveProvider.query", userName);
		}
		
		if(vo != null)
			vo.setRoleTypeId(roleId.intValue());
		
		return vo;
	}
	
	
	/**
	 * @param userName
	 * @param roleId
	 * @return
	 * @throws DataAccessException
	 */
	public UserVO getUserWithZip(String userName, Integer roleId) throws DataAccessException{
		UserVO vo = null;

		if (roleId.intValue() == OrderConstants.BUYER_ROLEID) {
			vo = (UserVO)queryForObject("userManagement_buyerResource.query", userName);
		}
		return vo;
	}




	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.usermanagement.UserManagementDao#getBuyerResources(java.lang.Integer)
	 */
	public List<UserVO> getBuyerResources(Integer buyerId)
			throws DataAccessException {
		List<UserVO> list = queryForList("buyerResourcesForGivenBuyer.query",buyerId);
		if (null == list) {
			list = new ArrayList<UserVO>();
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.usermanagement.UserManagementDao#getServiceLiveAdmins()
	 */
	public List<UserVO> getServiceLiveAdmins() throws DataAccessException {
		List<UserVO> list = queryForList("serviceLiveAdminsAll.query",null);
		if (null == list) {
			list = new ArrayList<UserVO>();
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IUserProfileDao#logUserProfileCreation(java.lang.String, java.lang.String)
	 */
	public void logUserCreation(String newUserName, String createdBy)
			throws DataAccessException {
		logUserChange(newUserName, createdBy, Constants.UserProfileLoggingActions.CREATED);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IUserProfileDao#logUserProfileEdit(java.lang.String, java.lang.String)
	 */
	public void logUserEdit(String editedUserName, String modifiedBy)
			throws DataAccessException {
		logUserChange(editedUserName, modifiedBy, Constants.UserProfileLoggingActions.MODIFIED);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IUserProfileDao#logUserRemoval(java.lang.String, java.lang.String)
	 */
	public void logUserRemoval(String removedUserName, String removedBy)
			throws DataAccessException {
		logUserChange(removedUserName, removedBy, Constants.UserProfileLoggingActions.REMOVED);
	}

	private void logUserChange (String userName, String modifiedBy, String action) throws DataAccessException {
		UserProfileLoggingVO vo = new UserProfileLoggingVO();

		vo.setUserName(userName);
		vo.setModifiedBy(modifiedBy);
		vo.setAction(action);

		insert("userProfileLogging.insert", vo);
	}

	/** (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.usermanagement.UserManagementDao#checkUserAvailable(java.lang.String)
	 */
	public Boolean isUserAvailable(String username)
			throws DataAccessException {

		Integer count = (Integer) queryForObject("userNameAvailable.query", username);

		if(count == 0){
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.usermanagement.UserManagementDao#removeUser(java.lang.String)
	 */
	public Boolean removeUser(String username) throws DataAccessException {

		try{
			update("disableUserName.update", username);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.usermanagement.UserManagementDao#updateOpenServiceOrders(java.lang.String)
	 */
	public List<String> updateOpenServiceOrders(String username, Integer buyerId)
			throws DataAccessException {
		try{
			logger.debug("retrieving services orders that may need reassigning");
			List<String> affectedServiceOrders = (List<String>) queryForList("reAssignableServiceOrders.query", username);
			if(affectedServiceOrders.size()>0){
				Map<String, Object> paramMap = new HashMap<String,Object>();
				paramMap.put("username", username);
				paramMap.put("buyerId", buyerId);
				logger.debug("reassigning buyer_resource " + username + " to buyer admin for " + affectedServiceOrders.size() + " service orders");
				update("reassignServiceOrders.update", paramMap);
				return affectedServiceOrders;

			}
			return new ArrayList<String>();
		}catch (Exception e){
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.usermanagement.UserManagementDao#isUserBuyerType(java.lang.String)
	 */
	public Boolean isUserBuyerType(String username) throws DataAccessException {

		try{
			Integer roleId = (Integer)queryForObject("retrieveUserType.query", username);
			if(roleId == OrderConstants.BUYER_ROLEID){
				return true;
			}
		}catch (Exception e){
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.usermanagement.UserManagementDao#saveSLAdminUser(com.newco.marketplace.dto.vo.permission.UserVO)
	 */
	public Boolean saveUser(UserVO user, Boolean isNewUser,Boolean isSuperAdmin) throws DataAccessException {
		if(user == null || isNewUser == null){
			logger.warn("saveSLAdminUser(user,isNewUser) has one of the parameters null. Exiting method");
			return false;
		}
		try{

			if(isNewUser){
				Integer contactId = (Integer)insert("user_management.contact.insert", user);
				user.setContactId(contactId);
				insert("user_management.user.insert", user);
				if(user.getRoleTypeId() == OrderConstants.BUYER_ROLEID){
					insert("user_management.user.buyer.insert", user);
				}/*Inserting a row in to admin_resource table if role_id = 2
			   	 To fix the defect# 5286*/
				else if(user.getRoleTypeId() == OrderConstants.NEWCO_ADMIN_ROLEID){
					user.setCompanyId(OrderConstants.NEWCO_ADMIN_COMPANY_ROLE);
					user.setModifiedBy(OrderConstants.ADMIN_MODIFIED_BY);
					insert("user_management.user.admin.insert", user);
				}

			} else{
				update("user_management.user.update", user);
			}

			saveRoles(user.getUserName(), user.getUserRoles());
			saveActivities(user.getUserName(), user.getUserActivities(),isSuperAdmin);

			return true;
		}catch (Exception e){
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}
		return false;
	}
	
	/**
	 * Update user profile with the new password. This is getting called for re sending welcome email. 
	 * @param user
	 * @return
	 * @throws DataAccessException
	 */
	public Boolean updatePassword(UserVO user) throws DataAccessException {
		if(user == null){
			logger.warn("updateUser(user) has null user. Exiting method");
			return false;
		}
		try{
			update("user_management_password.update", user);
			return true;
		}catch (Exception e){
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}
		return false;
	}

	private void saveRoles(String username, List<UserRoleVO> userRoles){

		try{
			logger.debug("updating roles for user " + username);
			//remove existing roles. This is safe, because the assoc table only has contact_id and company_role_id
			delete("userManagementRemoveUserRole.delete", username);

			Map<String,Object> userRolesMap = new HashMap<String, Object>();
			userRolesMap.put("username", username);
			Iterator<UserRoleVO> i = userRoles.iterator();
			while(i.hasNext()){
				userRolesMap.put("companyRoleId", i.next().getCompanyRoleId());
				insert("userManagementAddUserRole.insert", userRolesMap);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}


	}

	private void saveActivities(String username, List<ActivityVO> userActivities,Boolean isSuperAdmin){

		try{
			logger.debug("updating roles for user " + username);


			Map<String,Object> userActivitiesMap = new HashMap<String, Object>();
			userActivitiesMap.put("username", username);
			List<Integer> activityIds = new ArrayList<Integer>();
			for (ActivityVO vo : userActivities) {
				activityIds.add(new Integer(vo.getActivityId()));
			}
			userActivitiesMap.put("userActivities", activityIds);

			if(isSuperAdmin)  // Case of super admin
			{	
					//clean up activities that have been removed first
					delete("userManagementRemoveUserActivities_superadmin.delete", userActivitiesMap);
			}
			else
			{
				    delete("userManagementRemoveUserActivities.delete", userActivitiesMap);
			}

			//clean up the map as userActivities is no longer needed
			userActivitiesMap.remove("userActivities");
			//add new/updated activities if any
			if(!userActivities.isEmpty()){
				Iterator<ActivityVO> i = userActivities.iterator();
				while(i.hasNext()){
					userActivitiesMap.put("userActivity", i.next().getActivityId());
					insert("userManagementInsertUserActivities.insert", userActivitiesMap);
				}
			}

		}catch(Exception e){
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}

	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.usermanagement.UserManagementDao#getUserAdminInfo(java.lang.String, java.lang.Integer)
	 */
	public UserVO getBuyerAdminInfo(Integer buyerId,Integer resourceId) throws DataAccessException {
		try{
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("resourceId", resourceId);
			paramMap.put("buyerId", buyerId);
			return (UserVO) queryForObject("user_management.user.buyer.query", paramMap);
		}catch (Exception e){
			logger.error(e.getMessage());
			logger.error(e.getCause());
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.usermanagement.UserManagementDao#getUserResourceId(java.lang.String)
	 */
	public Integer getUserResourceId(String username)
			throws DataAccessException {
		try{
			return (Integer)queryForObject("user_management.user.buyer.resource.query", username);
		} catch (Exception e){
			logger.error(e.getMessage() + "\n" + e.getCause());
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.usermanagement.UserManagementDao#getBuyerAdminId(java.lang.String)
	 */
	public Integer getBuyerAdminId(String username) throws DataServiceException {
		try{
			return (Integer)queryForObject("user_management.user.buyer.admin.query", username);
		}catch (Exception e){
			logger.error(e.getMessage());
			logger.error(e.getCause());
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.usermanagement.UserManagementDao#getSuperAdminUserName()
	 */
	public String getSuperAdminUserName() throws DataServiceException {
		return (String)queryForObject("user_management.super_admin_user_name.query", null);
	}

	public void unlockUser(String userName) {
		String errorTxt = "SQL Exception @UserManagementDaoImpl.unlockUser()";

		try {
			getSqlMapClient().update("user_management.user.unlock", userName);
		} catch (Exception e) {
			logger.error("unlockUser Error:" + e.getMessage() + "\n" + e.getCause());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.usermanagement.UserManagementDao#getSLAdminUserId(java.lang.String)
	 */
	public Integer getSLAdminUserId(String username) throws DataServiceException {
		try{
			return (Integer)queryForObject("isSLAdminResourceId.select", username);
		}catch (Exception e){
			logger.error(e.getMessage());
			logger.error(e.getCause());
		}
		return null;
	}

	public List<ActivityVO> getProviderActivityList(String userName)throws DataServiceException {
		List<ActivityVO> newProviderList;
	
		newProviderList = queryForList("userActivities.query", userName);
		
		return newProviderList;
	}
    
	
	public void expireMobileTokenforFrontEnd (Integer resourceId,String tokenStatus) throws DataServiceException{
		MobileTokenVO mobileTokenVO = new MobileTokenVO();
	
		mobileTokenVO.setResourceId(resourceId);
		mobileTokenVO.setTokenStatus(tokenStatus);

		update("token_authenticate_frontend.update", mobileTokenVO);
	}
    
}
