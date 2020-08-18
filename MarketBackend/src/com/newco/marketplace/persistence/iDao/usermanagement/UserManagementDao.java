package com.newco.marketplace.persistence.iDao.usermanagement;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.ActivityVO;
import com.newco.marketplace.dto.vo.permission.PermissionSetVO;
import com.newco.marketplace.dto.vo.permission.UserVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.common.UserProfilePermissionSetVO;

/**
 * $Revision: 1.6 $ $Author: glacy $ $Date: 2008/06/02 19:39:49 $
 */
/**
 * @author spate05
 *
 */
public interface UserManagementDao {
	
	/**
	 * Returns the permissions sets available for the given role.
	 * If no permission sets are found an empty list is returned
	 * @param roleId
	 * @param getInactive
	 * @return
	 */
	public List<PermissionSetVO> getPermissionSets(UserProfilePermissionSetVO userProfilePermissionSetVO);
	
	/**
	 * @param username
	 * @param roleId
	 * @return
	 * @throws DataAccessException
	 */
	public UserVO getUser(String username, Integer roleId) throws DataAccessException;
	public Boolean isSuperAdminUser(String username) throws DataAccessException; //JIRA
	
	public boolean addUpdateUser(UserVO user);
	
	/**
	 * Returns a List of active buyer resource for the given buyer id
	 * @param buyerId
	 * @return
	 * @throws DataAccessException
	 */
	public List<UserVO> getBuyerResources(Integer buyerId) throws DataAccessException;
	
	/**
	 * Returns a List of active ServiceLive administrators.  If there are no admins, 
	 * an empty list is returned
	 * @return
	 * @throws DataAccessException
	 */
	public List<UserVO> getServiceLiveAdmins() throws DataAccessException;
	
	/**
	 * @param newUserName - user_name of new user
	 * @param createdBy - user_name of person who created the new user
	 * @throws DataAccessException
	 */
	public void logUserCreation(String newUserName, String createdBy) throws DataAccessException;
	
	/**
	 * @param editedUserName - user_name of user who was edited
	 * @param modifiedBy - user_name of person who edited the new user
	 * @throws DataAccessException
	 */
	public void logUserEdit(String editedUserName, String modifiedBy) throws DataAccessException;
	
	/**
	 * @param removedUserName - user_name of user who was removed
	 * @param removedBy - user_name of person who removed the user
	 * @throws DataAccessException
	 */
	public void logUserRemoval(String removedUserName, String removedBy) throws DataAccessException;

	/**
	 * @param username - check availability of this user_name
	 * @throws DataAccessException
	 */
	public Boolean isUserAvailable(String username) throws DataAccessException;
	
	/**
	 * @param username - the username to remove/disable. 
	 * @throws DataAccessException
	 */
	public Boolean removeUser(String username) throws DataAccessException;
	
	/**
	 * @param username - the username who has active/open service orders that need to get set to buyer admin
	 * @throws DataAccessException
	 */
	public List<String> updateOpenServiceOrders(String username, Integer buyerId) throws DataAccessException;
	
	/**
	 * 
	 * @param username - check if this username is of buyer type
	 * @return
	 * @throws DataAccessException
	 */
	public Boolean isUserBuyerType(String username) throws DataAccessException;
	
	/**
	 * 
	 * @param user
	 * @param isNewUser
	 * @return
	 * @throws DataAccessException
	 */
	public Boolean saveUser(UserVO user, Boolean isNewUser,Boolean isSuperAdmin) throws DataAccessException;
	
	
	/**
	 * @param user
	 * @return
	 * @throws DataAccessException
	 */
	public Boolean updatePassword(UserVO user) throws DataAccessException;
	/**
	 * 
	 * @param resourceId - the resource who's parent/admin details we want
	 * @return 
	 * @throws DataAccessException
	 * We want to get back the Company/Firm admin info back
	 */
	public UserVO getBuyerAdminInfo(Integer buyerId, Integer resourceId) throws DataAccessException;
	
	/**
	 * 
	 * @param username of buyer resource
	 * @return
	 * @throws DataAccessException
	 */
	public Integer getUserResourceId(String username) throws DataAccessException;
	
	/**
	 * 
	 * @param username - get this buyer admin's id
	 * @return
	 * @throws DataServiceException
	 */
	public Integer getBuyerAdminId(String username) throws DataServiceException;
	
	/**
	 * @return
	 * @throws DataServiceException
	 */
	public String getSuperAdminUserName() throws DataServiceException;
	
	/**
	 * @return
	 * @throws DataServiceException
	 */
	public Integer getSLAdminUserId(String username) throws DataServiceException;
	
	/**
	 * 
	 * @param userName
	 * @throws DBException
	 */
	public void unlockUser(String userName);
	
	public List<ActivityVO> getProviderActivityList(String userName) throws DataServiceException;
	
	public void expireMobileTokenforFrontEnd (Integer resourceId,String tokenStatus) throws DataServiceException;
}
