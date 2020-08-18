/**
 * 
 */
package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.IUserProfileDao;
import com.newco.marketplace.vo.provider.LostUsernameVO;
import com.newco.marketplace.vo.provider.UserProfile;
import com.newco.marketplace.interfaces.OrderConstants;

/**
 * $Revision: 1.25 $ $Author: paugus2 $ $Date: 2008/04/23 22:05:50 $
 */
public class UserProfileDaoImpl extends SqlMapClientDaoSupport implements
        IUserProfileDao {

    private static final Logger logger = Logger.getLogger(UserProfileDaoImpl.class.getName());

    
    public int update(UserProfile userProfile)throws DBException {
    	int result=0;
    	try{
    		result=getSqlMapClient().update("user_profile.update", userProfile);
    	}catch (SQLException ex) {
		     logger.info("SQL Exception @UserProfileDaoImpl.update()", ex);
		     throw new DBException("SQL Exception @UserProfileDaoImpl.update()", ex);
       }catch (Exception ex) {
		     logger.info("General Exception @UserProfileDaoImpl.update()", ex);
		     throw new DBException("General Exception @UserProfileDaoImpl.update()", ex);
      }
        return result;
    }

    public UserProfile query(UserProfile userProfile)throws DBException {
             
        UserProfile returnValue = null;
        try{
            returnValue =  (UserProfile) getSqlMapClient().queryForObject("user_profile.query", userProfile);
        }
        catch (SQLException ex) {
		     logger.info("SQL Exception @UserProfileDaoImpl.query()", ex);
		     throw new DBException("SQL Exception @UserProfileDaoImpl.query()", ex);
        }catch (Exception ex) {
		     logger.info("General Exception @UserProfileDaoImpl.query()", ex);
		     throw new DBException("General Exception @UserProfileDaoImpl.query()", ex);
       }
        
        return returnValue;
        
    }

    
    /**
     * Returns a userProfile object given a userName 
     * 
     * @param userProfile
     * @return
     */
    public UserProfile queryWithName (UserProfile userProfile)throws DBException {
    	
        UserProfile returnValue = null;
        try{
            returnValue =  (UserProfile) getSqlMapClient().queryForObject("user_profile.queryWithName", userProfile);
            
        } catch (SQLException ex) {
		     logger.info("SQL Exception @UserProfileDaoImpl.queryWithName()", ex);
		     throw new DBException("SQL Exception @UserProfileDaoImpl.queryWithName()", ex);
        }catch (Exception ex) {
		     logger.info("General Exception @UserProfileDaoImpl.queryWithName()",ex);
		     throw new DBException("General Exception @UserProfileDaoImpl.queryWithName()",ex);
       }
        
        return returnValue;
       }
        
    
    public UserProfile insert(UserProfile userProfile)throws DBException {

        UserProfile result=null;
    	try{
    		result=(UserProfile) getSqlMapClient().insert("user_profile.insert", userProfile);
    	}catch (SQLException ex) {
            logger.info("SQL Exception @UserProfileDaoImpl.insert()", ex);
		     throw new DBException("SQL Exception @UserProfileDaoImpl.insert()", ex);
       }catch (Exception ex) {
		     logger.info("General Exception @UserProfileDaoImpl.insert()", ex);
		     throw new DBException("General Exception @UserProfileDaoImpl.insert()", ex);
      }
        return result;
    }

	//R 6.1 Update method to update vendor id of user profile table
	public void updateUserProfile(Integer vendorId,String userName) throws DBException
	{
		try{
			java.util.Map map = new java.util.HashMap();
			map.put("vendorId", vendorId);
			map.put("userName", userName);
			logger.info("Before updating the table");
			getSqlMapClient().update("user_profile_vendorId.update", map);
			logger.info("Success on updating vendor id in user_profile R 6");
     	}catch (SQLException ex) {
			logger.info("SQL Exception @UserProfileDaoImpl.updateUserProfile()", ex);
			throw new DBException("SQL Exception @UserProfileDaoImpl.updateUserProfile()", ex);
		} catch (Exception ex) {
			logger.info("General Exception @UserProfileDaoImpl.updateUserProfile()", ex);
			throw new DBException("General Exception @UserProfileDaoImpl.updateUserProfile()", ex);
		}	
	}
	
    public List queryList(UserProfile userProfile)throws DBException  {

        List result=null;
    	try{
    		result=getSqlMapClient().queryForList("user_profile.query", userProfile);
    	}catch (SQLException ex) {
		     logger.info("SQL Exception @UserProfileDaoImpl.queryList()", ex);
		     throw new DBException("SQL Exception @UserProfileDaoImpl.queryList()", ex);
       }catch (Exception ex) {
		     logger.info("General Exception @UserProfileDaoImpl.queryList()", ex);
		     throw new DBException("General Exception @UserProfileDaoImpl.queryList()", ex);
      }
        return result;
    }
   
    public List loadLostUsernameList(String email, int roleId) throws DBException {
    	final String MULTI_USER_QUERY = "user_profile_lost_username.query_by_email";
    	List<LostUsernameVO> objList = new ArrayList<LostUsernameVO>();

    	try {
    		List retList = getSqlMapClient().queryForList(MULTI_USER_QUERY, email);
			if(retList!=null&&retList.size()>0){
    			LostUsernameVO loUVO = null;
				for(int i=0; i< retList.size(); i++){
    				loUVO = (LostUsernameVO)retList.get(i);		
    				if (loUVO != null) {
    					loUVO = 	loadLostUsername(loUVO.getUserName(), roleId);	
    					if (loUVO != null)
    						objList.add(loUVO);
    				}
    			}
    			return objList;
    		}
    	} catch (SQLException ex) {
    		logger.info("SQL Exception @UserProfileDaoImpl.queryList()", ex);
    		throw new DBException("SQL Exception @UserProfileDaoImpl.queryList()", ex);
    	}catch (Exception ex) {
    		logger.info("General Exception @UserProfileDaoImpl.queryList()", ex);
    		throw new DBException("General Exception @UserProfileDaoImpl.queryList()", ex);
    	}
				
    	return objList;
    }
				
    public LostUsernameVO loadLostUsernameByResourceId(String resourceId, int roleId) throws DBException {
    	if (roleId == OrderConstants.UNKNOWN_ROLEID) {
    		logger.info("Invalid RoldID provided");
    		return null;
				}

    	try {
    		String userName;
    		if (roleId == OrderConstants.PROVIDER_ROLEID) {
    			userName = (String) getSqlMapClient().queryForObject("provider_get_user_name_from_resource_id.query", 
    					resourceId);
    		} else {
    			userName = (String) getSqlMapClient().queryForObject("buyer_get_user_name_from_resource_id.query", 
    					resourceId);
			}
    		return loadLostUsername(userName, roleId);
		} catch (SQLException ex) {
    		logger.info("SQL Exception @UserProfileDaoImpl.queryList()", ex);
    		throw new DBException("SQL Exception @UserProfileDaoImpl.queryList()", ex);
    	} catch (Exception ex) {
    		logger.info("General Exception @UserProfileDaoImpl.queryList()", ex);
    		throw new DBException("General Exception @UserProfileDaoImpl.queryList()", ex);
    	}
    }
    
    public LostUsernameVO loadLostUsername(String userName, int roleId) throws DBException {
    	final String BUYER_DETAIL_QUERY = "buyer_get_detail_profile.query";
    	final String PROVIDER_DETAIL_QUERY = "provider_get_detail_profile.query";   	
    	
    	if (userName == null)
    		return null;
    	
    	try {
    		String query = BUYER_DETAIL_QUERY;
			
			if (roleId == OrderConstants.UNKNOWN_ROLEID) {
				Integer i = (Integer) getSqlMapClient().queryForObject("user_profile.rold_id", userName);
				roleId = i.intValue();
			}
			
			if (roleId == OrderConstants.PROVIDER_ROLEID) {
				query = PROVIDER_DETAIL_QUERY;	
			}
			
			LostUsernameVO newloUVO = (LostUsernameVO) getSqlMapClient().queryForObject(query, userName);	
			if (newloUVO != null){
				if (newloUVO.getQuestionId() == null) newloUVO.setQuestionId("0");
				String questionTxt = (String) getSqlMapClient().queryForObject("user_profile.getSecretQuestionForgetUserName", 
						new Integer(newloUVO.getQuestionId()));
				newloUVO.setQuestionTxt(questionTxt);			
			}
			return newloUVO;
			
		} catch (SQLException ex) {
			logger.info("SQL Exception @UserProfileDaoImpl.loadLostUsername()", ex);
			throw new DBException("SQL Exception @UserProfileDaoImpl.loadLostUsername()", ex);
		} catch (Exception ex) {
			logger.info("General Exception @UserProfileDaoImpl.loadLostUsername()", ex);
			throw new DBException("General Exception @UserProfileDaoImpl.loadLostUsername()", ex);
		}
	}
    
    public List loadLitLostUsereProfileList(String email, String userName) throws DBException{
    	try {
    		LostUsernameVO lostUserNameVO = new LostUsernameVO();
    		lostUserNameVO.setEmailAddress(email);
    		lostUserNameVO.setUserName(userName);
			
    		
    		 List retList = getSqlMapClient().queryForList("user_profile_lite_by_email_username.query", 
					lostUserNameVO);
			return retList;
		} catch (SQLException ex) {
			logger.info("SQL Exception @UserProfileDaoImpl.validateEmailUsername()", ex);
			throw new DBException("SQL Exception @UserProfileDaoImpl.validateEmailUsername()", ex);
		} catch (Exception ex) {
			logger.info("General Exception @UserProfileDaoImpl.validateEmailUsername()", ex);
			throw new DBException("General Exception @UserProfileDaoImpl.validateEmailUsername()", ex);
		}
	}
    
    public LostUsernameVO loadLitLostUsereProfile(String email, String userName) throws DBException{
    	
    	LostUsernameVO lostUserNameVO = new LostUsernameVO();
    	lostUserNameVO.setEmailAddress(email);
    	lostUserNameVO.setUserName(userName);
		
    	LostUsernameVO usernameVO = null;
    	
    	try {
			  		
    		
    		usernameVO = (LostUsernameVO) getSqlMapClient().queryForObject("user_profile_lite_by_email_username.query",
					lostUserNameVO);
			
		} catch (SQLException ex) {
			logger.info("SQL Exception @UserProfileDaoImpl.validateEmailUsername()", ex);
			throw new DBException("SQL Exception @UserProfileDaoImpl.validateEmailUsername()", ex);
		} catch (Exception ex) {
			logger.info("General Exception @UserProfileDaoImpl.validateEmailUsername()", ex);
			throw new DBException("General Exception @UserProfileDaoImpl.validateEmailUsername()", ex);
		}
		return usernameVO;
	}
    
    
    public LostUsernameVO getSecQuestionForUserName(LostUsernameVO lostUserNameVO)throws DBException{
    	LostUsernameVO lostUserNameVOReturn=null;
    	try{
    		lostUserNameVOReturn=(LostUsernameVO) getSqlMapClient().queryForObject("user_profile.getSecretQuestionAns", lostUserNameVO);
     	}catch (SQLException ex) {
			logger.info("SQL Exception @UserProfileDaoImpl.validateEmailUsername()", ex);
			throw new DBException("SQL Exception @UserProfileDaoImpl.validateEmailUsername()", ex);
		} catch (Exception ex) {
			logger.info("General Exception @UserProfileDaoImpl.validateEmailUsername()",ex);
			throw new DBException("General Exception @UserProfileDaoImpl.validateEmailUsername()",ex);
		}
		return lostUserNameVOReturn ;
    }
    /*
    public List validateAns(LostUsernameVO lostUserNameVO)throws DBException{
    	List result=null;
    	try{
    		result=(List) getSqlMapClient().queryForList("user_profile.validateAns", lostUserNameVO);
     	}catch (SQLException ex) {
			logger.info("SQL Exception @UserProfileDaoImpl.validateAns()", ex);
			throw new DBException("SQL Exception @UserProfileDaoImpl.validateAns()", ex);
		} catch (Exception ex) {
			logger.info("General Exception @UserProfileDaoImpl.validateAns()", ex);
			throw new DBException("General Exception @UserProfileDaoImpl.validateAns()", ex);
		}
		return result ;
    }
    */
    public LostUsernameVO getSecretAns(LostUsernameVO lostUserNameVO)throws DBException{
    	LostUsernameVO result=null;
    	try{
    		result=(LostUsernameVO) getSqlMapClient().queryForObject("user_profile.validateAns", lostUserNameVO);
     	}catch (SQLException ex) {
			logger.info("SQL Exception @UserProfileDaoImpl.getSecretAns()", ex);
			throw new DBException("SQL Exception @UserProfileDaoImpl.getSecretAns()", ex);
		} catch (Exception ex) {
			logger.info("General Exception @UserProfileDaoImpl.getSecretAns()", ex);
			throw new DBException("General Exception @UserProfileDaoImpl.getSecretAns()", ex);
		}
		return result;
    }
    
    public void updatePassword(UserProfile userProfile)throws DBException{
    	try{
    		getSqlMapClient().update("user_profile_password.update", userProfile);
     	}catch (SQLException ex) {
			logger.info("SQL Exception @UserProfileDaoImpl.updatePassword()", ex);
			throw new DBException("SQL Exception @UserProfileDaoImpl.updatePassword()", ex);
		} catch (Exception ex) {
			logger.info("General Exception @UserProfileDaoImpl.updatePassword()", ex);
			throw new DBException("General Exception @UserProfileDaoImpl.updatePassword()", ex);
		}
    }
    
    public void resetPassword(UserProfile userProfile)throws DBException{
    	try{
    		getSqlMapClient().update("user_profile_password.reset", userProfile);
     	}catch (SQLException ex) {
			logger.info("SQL Exception @UserProfileDaoImpl.resetPassword()", ex);
			throw new DBException("SQL Exception @UserProfileDaoImpl.resetPassword()", ex);
		} catch (Exception ex) {
			logger.info("General Exception @UserProfileDaoImpl.resetPassword()", ex);
			throw new DBException("General Exception @UserProfileDaoImpl.resetPassword()", ex);
		}
    }

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IUserProfileDao#insertTeamMemberUserProfile(com.newco.marketplace.vo.provider.UserProfile)
	 */
	public UserProfile insertTeamMemberUserProfile(UserProfile userProfile)
			throws DBException {
		UserProfile result = null;
		try {
			result = (UserProfile) getSqlMapClient().insert(
					"teammember.userprofile.insert", userProfile);
		} catch (SQLException ex) {
			logger.info("SQL Exception @UserProfileDaoImpl.insertTeamMemberUserProfile()", ex);
			throw new DBException("SQL Exception @UserProfileDaoImpl.insertTeamMemberUserProfile()", ex);
		} catch (Exception ex) {
			logger.info("General Exception @UserProfileDaoImpl.insertTeamMemberUserProfile()", ex);
			throw new DBException("General Exception @UserProfileDaoImpl.insertTeamMemberUserProfile()", ex);
		}
		return result;
	}
	
	public UserProfile getDetailsOnResourceId(String resourceId) throws DBException
	{
		UserProfile result = null;
		try {
			result = (UserProfile) getSqlMapClient().queryForObject(
					"user_profile_details.queryWithResourceId", resourceId);
		} catch (SQLException ex) {
			logger.info("SQL Exception @UserProfileDaoImpl.getDetailsOnResourceId()", ex);
			throw new DBException("SQL Exception @UserProfileDaoImpl.getDetailsOnResourceId()", ex);
		} catch (Exception ex) {
			logger.info("General Exception @UserProfileDaoImpl.getDetailsOnResourceId()", ex);
			throw new DBException("General Exception @UserProfileDaoImpl.getDetailsOnResourceId()", ex);
		}
		return result;
	}
    
	public UserProfile getProvAdminDetails(String provUserName) throws DBException
	{
		UserProfile result = null;
		try {
			result = (UserProfile) getSqlMapClient().queryForObject(
					"user_profile_details_admin.queryWithUserName", provUserName);
		} catch (SQLException ex) {
			logger.info("SQL Exception @UserProfileDaoImpl.getProvAdminDetails()", ex);
			throw new DBException("SQL Exception @UserProfileDaoImpl.getProvAdminDetails()", ex);
		} catch (Exception ex) {
			logger.info("General Exception @UserProfileDaoImpl.getProvAdminDetails()", ex);
			throw new DBException("General Exception @UserProfileDaoImpl.getProvAdminDetails()", ex);
		}
		return result;
	}
	
	public String getAdminAddress(String userName) throws DBException
	{
		String provEmailAddress = "";
		try
		{
			provEmailAddress = (String) getSqlMapClient().queryForObject("user_profile_details_admin.query", userName);
		} catch (SQLException ex) {
			logger.info("SQL Exception @UserProfileDaoImpl.getAdminAddress()", ex);
			throw new DBException("SQL Exception @UserProfileDaoImpl.getAdminAddress()", ex);
		} catch (Exception ex) {
			logger.info("General Exception @UserProfileDaoImpl.getAdminAddress()", ex);
			throw new DBException("General Exception @UserProfileDaoImpl.getAdminAddress()", ex);
		}
		return provEmailAddress;
	}
    
	public String getBuyerAdminAddress(String userName) throws DBException {
		String provEmailAddress = "";
		try
		{
			provEmailAddress = (String) getSqlMapClient().queryForObject("user_profile_buyer_details_admin.query", userName);
		} catch (SQLException ex) {
			logger.info("SQL Exception @UserProfileDaoImpl.getAdminAddress()", ex);
			throw new DBException("SQL Exception @UserProfileDaoImpl.getAdminAddress()", ex);
		} catch (Exception ex) {
			logger.info("General Exception @UserProfileDaoImpl.getAdminAddress()", ex);
			throw new DBException("General Exception @UserProfileDaoImpl.getAdminAddress()", ex);
		}
		return provEmailAddress;
	}
			
	public int getVerificationCount(String userName) throws DBException {
		try {
			Integer it = (Integer) getSqlMapClient().queryForObject("user_profile.verification_attempt_count", userName);
			if (it != null)
				return it.intValue();
		} catch (SQLException ex) {
			logger.info("SQL Exception @UserProfileDaoImpl.getVerificationCount()", ex);
			throw new DBException("SQL Exception @UserProfileDaoImpl.getVerificationCount()", ex);
		} catch (Exception ex) {
			logger.info("General Exception @UserProfileDaoImpl.getVerificationCount()", ex);
			throw new DBException("General Exception @UserProfileDaoImpl.getVerificationCount()", ex);
}
		return -1;
	}
	
	public void updateVerificationCount(String userName, int count)throws DBException{
		try{		
			java.util.Map map = new java.util.HashMap();
			map.put("count", new Integer(count));
			map.put("userName", userName);
			getSqlMapClient().update("user_profile.update_verification_attempt_count", map);
		}catch (SQLException ex) {
			logger.info("SQL Exception @UserProfileDaoImpl.updateVerificationCount()", ex);
			throw new DBException("SQL Exception @UserProfileDaoImpl.updateVerificationCount()", ex);
		} catch (Exception ex) {
			logger.info("General Exception @UserProfileDaoImpl.updateVerificationCount()", ex);
			throw new DBException("General Exception @UserProfileDaoImpl.updateVerificationCount()", ex);
		}
	}	
	
	
	public void lockProfile(String userName)throws DBException {
		try {
			java.util.Map map = new java.util.HashMap();			
			map.put("userName", userName);
			getSqlMapClient().update("user_profile.lock", map);
		}catch (SQLException ex) {
			logger.info("SQL Exception @UserProfileDaoImpl.lockProfile()", ex);
			throw new DBException("SQL Exception @UserProfileDaoImpl.lockProfile()", ex);
		} catch (Exception ex) {
			logger.info("General Exception @UserProfileDaoImpl.lockProfile()", ex);
			throw new DBException("General Exception @UserProfileDaoImpl.lockProfile()", ex);
		}
	}	
	
	public void unlockProfile(String userName)throws DBException {
		try {
			java.util.Map map = new java.util.HashMap();			
			map.put("userName", userName);
			getSqlMapClient().update("user_profile.unlock", map);
		}catch (SQLException ex) {
			logger.info("SQL Exception @UserProfileDaoImpl.lockProfile()", ex);
			throw new DBException("SQL Exception @UserProfileDaoImpl.lockProfile()", ex);
		} catch (Exception ex) {
			logger.info("General Exception @UserProfileDaoImpl.lockProfile()", ex);
			throw new DBException("General Exception @UserProfileDaoImpl.lockProfile()", ex);
		}
	}	
	
	public String getUserNameFromResourceId(String resourceId, int roleId) throws DBException{
		String userName = null;
		try {
			if (roleId == OrderConstants.PROVIDER_ROLEID) {
				userName = (String) getSqlMapClient().queryForObject("provider_get_user_name_from_resource_id.query", resourceId);
			} else if (roleId == OrderConstants.BUYER_ROLEID || roleId == OrderConstants.SIMPLE_BUYER_ROLEID) {
				userName = (String) getSqlMapClient().queryForObject("buyer_get_user_name_from_resource_id.query", resourceId);
			} else if (roleId == 0) { //admin type , write code here						
			}

		} catch (SQLException ex) {
			logger.info("SQL Exception @UserProfileDaoImpl.getUserNameFromResourceId()", ex);
			throw new DBException("SQL Exception @UserProfileDaoImpl.getUserNameFromResourceId()", ex);
		} catch (Exception ex) {
			logger.info("General Exception @UserProfileDaoImpl.getUserNameFromResourceId()", ex);
			throw new DBException("General Exception @UserProfileDaoImpl.getUserNameFromResourceId()", ex);
		}
		return userName;
	}
	
	
	public String getResourceIdFromUserName(String username) throws DBException{
		String resourceId = null;
		try {
			resourceId = (String) getSqlMapClient().queryForObject("provider_get_resource_id_from_user_name.query", username);
			

		} catch (SQLException ex) {
			logger.info("SQL Exception @UserProfileDaoImpl.getResourceIdFromUserName()", ex);
			throw new DBException("SQL Exception @UserProfileDaoImpl.getResourceIdFromUserName()", ex);
		} catch (Exception ex) {
			logger.info("General Exception @UserProfileDaoImpl.getResourceIdFromUserName()", ex);
			throw new DBException("General Exception @UserProfileDaoImpl.getResourceIdFromUserName()", ex);
		}
		return resourceId;
	}
	
}
