/**
 * 
 */
package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.provider.UserProfile;
import com.newco.marketplace.vo.provider.LostUsernameVO;
 
/**
 * @author sahmad7
 */
public interface IUserProfileDao {
	public int update(UserProfile userProfile) throws DBException;

	/**
	 * Returns a userProfile object given a userName
	 * 
	 * @param userProfile
	 * @return @
	 */
	public UserProfile query(UserProfile userProfile) throws DBException;

	/**
	 * Returns a userProfile object given a userName
	 * 
	 * @param userProfile
	 * @return @
	 */
	public UserProfile queryWithName(UserProfile userProfile)
			throws DBException;

//	public UserProfile queryWithVendorId(String vendorId) throws DBException;

	public UserProfile insert(UserProfile userProfile) throws DBException;
	//R 6.1 Update method to update vendor id of user profile table
	public void updateUserProfile(Integer vendorId,String userName) throws DBException;
	public List queryList(UserProfile vo) throws DBException;
	//public List loadLostUsername(LostUsernameVO lostUsernameVO) throws DBException;
	public List loadLitLostUsereProfileList(String email, String userName) throws DBException;
	public LostUsernameVO getSecQuestionForUserName(LostUsernameVO lostUserNameVO)throws DBException;
	public LostUsernameVO getSecretAns(LostUsernameVO lostUserNameVO)throws DBException;
	public void updatePassword(UserProfile userProfile)throws DBException;
	public UserProfile insertTeamMemberUserProfile(UserProfile userProfile) throws DBException;
	public void resetPassword(UserProfile userProfile)throws DBException;
	public LostUsernameVO loadLitLostUsereProfile(String email, String userName) throws DBException;
	public UserProfile getDetailsOnResourceId(String resourceId) throws DBException;
	public UserProfile getProvAdminDetails(String userName) throws DBException;
	public String getAdminAddress(String resourceUserName) throws DBException;
	public String getBuyerAdminAddress(String resourceUserName) throws DBException;	
	public void unlockProfile(String userName) throws DBException;
	public void lockProfile(String userName) throws DBException;
	public String getUserNameFromResourceId(String resourceId, int roleId) throws DBException;
	public void updateVerificationCount(String userName, int count)throws DBException;
	public int getVerificationCount(String userName) throws DBException;
	public List loadLostUsernameList(String email, int roleId) throws DBException;
	public LostUsernameVO loadLostUsernameByResourceId(String resourceId, int roleId) throws DBException;
	public LostUsernameVO loadLostUsername(String userName, int roleId) throws DBException;
	public String getResourceIdFromUserName(String username) throws DBException;
}
