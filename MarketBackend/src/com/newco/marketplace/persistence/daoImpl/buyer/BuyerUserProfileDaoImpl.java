/**
 * 
 */
package com.newco.marketplace.persistence.daoImpl.buyer;

import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.dto.vo.buyer.BuyerUserProfile;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.buyer.IBuyerUserProfileDao;

/**
 * $Revision: 1.6 $ $Author: glacy $ $Date: 2008/04/26 00:40:29 $
 */
public class BuyerUserProfileDaoImpl extends SqlMapClientDaoSupport implements
	IBuyerUserProfileDao {

   	private static final Logger logger = Logger.getLogger(BuyerUserProfileDaoImpl.class.getName());

    public BuyerUserProfile insert(BuyerUserProfile userProfile)throws DBException {

    	BuyerUserProfile result=null;
    	try{
    		result=(BuyerUserProfile) getSqlMapClient().insert("user_profile_buyer.insert", userProfile);
    		if (userProfile.getInterimPassword() != null) { //Added by Shekhar for deep link support.
    			getSqlMapClient().insert("interim_password.insert", userProfile.getInterimPassword());
    		}
    		
    	}catch (SQLException ex) {
            logger.info("SQL Exception @UserProfileDaoImpl.insert()", ex);
		     throw new DBException("SQL Exception @UserProfileDaoImpl.insert()", ex);
       }catch (Exception ex) {
		     logger.info("General Exception @UserProfileDaoImpl.insert()", ex);
		     throw new DBException("General Exception @UserProfileDaoImpl.insert()", ex);
      }
        return result;
    }
    
    public BuyerUserProfile query(BuyerUserProfile userProfile)throws DBException {
        
    	BuyerUserProfile returnValue = null;
        try{
            returnValue =  (BuyerUserProfile) getSqlMapClient().queryForObject("user_profile_buyer.query", userProfile);
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

   

}
