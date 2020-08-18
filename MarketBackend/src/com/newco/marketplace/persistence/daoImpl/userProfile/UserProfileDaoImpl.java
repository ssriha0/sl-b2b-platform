package com.newco.marketplace.persistence.daoImpl.userProfile;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.persistence.iDao.userProfile.IUserProfileDao;
import com.newco.marketplace.vo.apiUserProfile.FirmDetailsVO;
import com.newco.marketplace.vo.apiUserProfile.LocationResponseDate;
import com.newco.marketplace.vo.apiUserProfile.UserProfileData;
import com.newco.marketplace.vo.leadprofile.LeadProfileDetailsVO;


import com.sears.os.dao.impl.ABaseImplDao;


public class UserProfileDaoImpl extends ABaseImplDao implements IUserProfileDao{

	public UserProfileData findById(String userName) throws DBException {
             
		UserProfileData returnValue = null;
        try{
            returnValue =  (UserProfileData) getSqlMapClient().queryForObject("user_auth_profile.query", userName);
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
	public List<LocationResponseDate> getLocationDetails(int contactId) throws DBException {
        
		List<LocationResponseDate>  result = new ArrayList<LocationResponseDate>();
        try{
        	result = (List<LocationResponseDate> )queryForList("user_location_details.query", contactId);
        }
        catch (Exception ex) {
		     logger.info("General Exception @UserProfileDaoImpl.query()", ex);
		     throw new DBException("General Exception @UserProfileDaoImpl.query()", ex);
       }
        
        return result;
	}

	public FirmDetailsVO getFirmDetails(int contactId) throws DBException {
        
		FirmDetailsVO result = new FirmDetailsVO();
        try{
        	result = (FirmDetailsVO) getSqlMapClient().queryForObject("firm_details.query", contactId);
        	 
        }
        catch (Exception ex) {
		     logger.info("General Exception @UserProfileDaoImpl.query()", ex);
		     throw new DBException("General Exception @UserProfileDaoImpl.query()", ex);
       }
        
        return result;
	}
	public LeadProfileDetailsVO getLeadDetails(int vendorId) throws Exception{
		LeadProfileDetailsVO result = new LeadProfileDetailsVO();
        try{
        	result = (LeadProfileDetailsVO) getSqlMapClient().queryForObject("lead_profile_details.query", vendorId);
        	 
        }
        catch (Exception ex) {
		     logger.info("General Exception @UserProfileDaoImpl.query()", ex);
		     throw new DBException("General Exception @UserProfileDaoImpl.query()", ex);
       }
        
        return result;
	}
	public int lauchMarketCheck(String stateCd) throws Exception {
		// TODO Auto-generated method stub
		Integer launch_market_ind = 0;
		try{
			launch_market_ind = (Integer) queryForObject("launch_market_ind.query", stateCd);
		}catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return launch_market_ind;
	}
}
